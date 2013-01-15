package org.exoplatform.selenium.platform;

import org.exoplatform.selenium.platform.NavigationToolbar;
import static org.exoplatform.selenium.TestLogger.info;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class DashBoard extends PlatformBase {
	

	NavigationToolbar nav = new NavigationToolbar(driver);
	
	/* Dashboard Page*/
	public  String MESSAGE_DRAG_GADGETS_HERE = "Drag your gadgets here.";
	public  By ELEMENT_ADD_GADGETS_LINK = By.xpath("//a[text()='Add Gadgets']");
	
	// Getget Directory form
	public  By ELEMENT_GADGET_URI_INPUT = By.xpath("//input[@id='url']");

	public String MSG_DELETE_GADGET = "Are you sure to delete this gadget?";
	public String ELEMENT_DELETE_GADGET_ICON = "//span[text()='{$gadget}']/preceding::span[@title='Delete Gadget']";
	public By ELEMENT_GADGET_URL_INPUT = By.xpath("//input[@id='url']");
    public By ELEMENT_ADD_GADGET_BUTTON = By.xpath("//img[@title='Add Gadget']");
	/*------------- Data for Dashboard tab --------------------------------*/
	public final String ELEMENT_DASHBOARD_NEW_ICON = "//div[@id='UITabPaneDashboard']/a[@class='AddDashboard']";
	public final String ELEMENT_DASHBOARD_NEW_INPUT = "//div[@id='UITabPaneDashboard']//div[contains(@class, 'UITab SelectedTab')]/span";
	public final String ELEMENT_DASHBOARD_INPUT = "//input[@name='{$tab}']";
	public final String ELEMENT_DASHBOARD_SELECTED_PAGE_WITH_SPECIFIED_NAME = "//div[@id='UITabPaneDashboard']//span[text()='${dashboardName}']";
	public  final String ELEMENT_DASHBOARD_SELECTED = "//div[contains(@class, 'SelectedTab')]//span";
	public  final String ELEMENT_DASHBOARD_SELECTED_DELETE = "//div[contains(@class, 'SelectedTab')]//a[@class='CloseIcon']";
	public  final String ELEMENT_DASHBOARD_HOME_TAB = "div[@class='UITab SelectedTab']";
	public  final String ELEMENT_TAB_LINK = "//div[@id='UITabPaneDashboard']//span[text()='${tabName}']";
	public  final By ELEMENT_CLOSE_DASHBOARD = By.xpath("//div[@id='UIDashboard']//a[@title='Close Window']");
	public  final String ELEMENT_TAB_NAME = "//span[text()='{$tab}']";
	public  final String MSG_DELETE_TAB = "Really want to remove this dashboard?";

	/*------------ End of data for Dashboard tab --------------------------*/

	public DashBoard(WebDriver dr){
		driver = dr;
	}
	//Add new page on Dashboard
	public void addNewTabOnDashboard(String displayName, boolean verify) {
		info("--Add new page on Dashboard--");
		click(ELEMENT_DASHBOARD_NEW_ICON);
		type(ELEMENT_DASHBOARD_NEW_INPUT, displayName, true);
		WebElement element = waitForAndGetElement(ELEMENT_DASHBOARD_NEW_INPUT);
		element.sendKeys(Keys.RETURN);
		if (verify) {
			waitForAndGetElement(ELEMENT_TAB_LINK.replace("{$tab}", displayName));
		}
	}

	//Add new page in Dashboard with Editor
	public void addNewTabOnDashboardWithEditor(String nodeName, boolean extendedLabelMode, String displayName, 
			String language, String categoryTitle, Map<String, String> portletIds, boolean verify){

		nav.goToAddPageEditor();
		type(ELEMENT_INPUT_NODE_NAME, nodeName, true);
		WebElement element = waitForAndGetElement(ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE);
		if (extendedLabelMode){
			Assert.assertTrue(element.isSelected());
			select(ELEMENT_SELECT_LANGUAGE, language);
		}else {
			uncheck(ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE,false);
			type(ELEMENT_INPUT_PAGE_DISPLAY_NAME, displayName, true);
		}
		click(ELEMENT_PAGE_EDITOR_NEXT_STEP);
		waitForTextPresent("Empty Layout");
		click(ELEMENT_PAGE_EDITOR_NEXT_STEP);

		String category = ELEMENT_EDIT_PAGE_CATEGORY_MENU.replace("${categoryLabel}", categoryTitle);
		click(category);

		for (String portletId : portletIds.keySet()) {
			String elementEditPagePage = ELEMENT_EDIT_PAGE_PAGE;
			//String verification = PORTLET_LABEL.replace("${portletName}", portletIdsAndVerifications.get(portletId));
			dragAndDropToObject("//div[@id='" + portletId + "']//img", elementEditPagePage);
		}
		pause(500);
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForTextNotPresent("Page Editor");
		if (verify) {
			waitForAndGetElement(ELEMENT_TAB_NAME.replace("{$tab}", nodeName));
		}
	}

	//Edit a tab name
	public void editTabNameOnDashboard(String currentName, String newName) {
		Actions actions = new Actions(driver);
		info("--Edit page name on dashboard--");
		WebElement element;
		element = waitForAndGetElement(ELEMENT_TAB_NAME.replace("{$tab}", currentName));
		actions.moveToElement(element).click(element).build().perform();

		doubleClickOnElement(ELEMENT_DASHBOARD_SELECTED);

		type(ELEMENT_DASHBOARD_INPUT.replace("{$tab}", currentName), newName, true);
		WebElement elementbis = waitForAndGetElement(ELEMENT_DASHBOARD_INPUT);
		elementbis.sendKeys(Keys.RETURN);

		waitForAndGetElement(ELEMENT_TAB_NAME.replace("{$tab}", newName));
		waitForElementNotPresent(ELEMENT_TAB_NAME.replace("{$tab}", currentName));
	}

	//Delete a tab
	public void deleteTabOnDashboard(String currentName, boolean confirm){

		info("--Delete selected page on dashboard--");
		Actions actions = new Actions(driver);
		if(confirm){ 
			WebElement element;
			element = waitForAndGetElement(ELEMENT_TAB_NAME.replace("{$tab}", currentName));
			actions.moveToElement(element).click(element).build().perform();
			click(ELEMENT_DASHBOARD_SELECTED_DELETE);
		} else {
			click(ELEMENT_DASHBOARD_SELECTED_DELETE);
		}	
		waitForConfirmation(MSG_DELETE_TAB);
		waitForElementNotPresent(ELEMENT_TAB_NAME.replace("{$tab}", currentName));
	}

	public void deleteGadgetOnDashboard(String gadgetTitleDisplay)
	{
		By deleteGadgetIcon = By.xpath(ELEMENT_DELETE_GADGET_ICON.replace("{$gadget}", gadgetTitleDisplay));
		waitForAndGetElement(deleteGadgetIcon);
		click(deleteGadgetIcon);
		waitForConfirmation(MSG_DELETE_GADGET);
		waitForTextNotPresent(gadgetTitleDisplay);
	}
	
}
