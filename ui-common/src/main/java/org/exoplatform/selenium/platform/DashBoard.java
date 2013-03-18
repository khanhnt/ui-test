package org.exoplatform.selenium.platform;

import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import static org.exoplatform.selenium.TestLogger.info;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class DashBoard extends PlatformBase {

	NavigationToolbar nav = new NavigationToolbar(driver);
	Dialog dialog = new Dialog(driver);
	ManageAlert alt = new ManageAlert(driver);
	
	/* Dashboard Page*/
	public  String MESSAGE_DRAG_GADGETS_HERE = "Drag your gadgets here.";
	public  By ELEMENT_ADD_GADGETS_LINK = By.xpath("//a[text()='Add Gadgets']");
	
	// Getget Directory form
	public  By ELEMENT_GADGET_URI_INPUT = By.xpath("//input[@id='url']");
	public  By ELEMENT_ADD_GADGET_BUTTON = By.xpath("//img[@title='Add Gadget']");

	/*------------- Data for Dashboard tab --------------------------------*/
	public final String ELEMENT_DASHBOARD_NEW_ICON = "//div[@id='UITabPaneDashboard']/a[@class='AddDashboard']";
	public final String ELEMENT_DASHBOARD_NEW_INPUT = "//div[@id='UITabPaneDashboard']//div[contains(@class, 'UITab SelectedTab')]/input";
	public final String ELEMENT_DASHBOARD_SELECTED_PAGE_WITH_SPECIFIED_NAME = "//div[@id='UITabPaneDashboard']//span[text()='${dashboardName}']";
	public final String ELEMENT_DASHBOARD_SELECTED = "//div[contains(@class, 'SelectedTab')]//span";
	public final String ELEMENT_DASHBOARD_SELECTED_DELETE = "//div[contains(@class, 'SelectedTab')]//a[@class='CloseIcon']";
	public final String ELEMENT_DASHBOARD_HOME_TAB = "div[@class='UITab SelectedTab']";
	public final String ELEMENT_TAB_LINK = "//div[@id='UITabPaneDashboard']//span[text()='${tabName}']";
	/*------------ End of data for Dashboard tab --------------------------*/

	//Add new page on Dashboard
	public void addNewTabOnDashboard(String displayName, boolean verify) {
		info("--Add new page on Dashboard--");
		click(ELEMENT_DASHBOARD_NEW_ICON);
		type(ELEMENT_DASHBOARD_NEW_INPUT, displayName, true);
		WebElement element = waitForAndGetElement(ELEMENT_DASHBOARD_NEW_INPUT);
		element.sendKeys(Keys.RETURN);
		if (verify) {
			waitForAndGetElement("//span[text()='" + displayName + "']");
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
			uncheck(ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE);
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
		Utils.pause(500);
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForTextNotPresent("Page Editor");
		if (verify) {
			waitForAndGetElement("//span[text()='" + nodeName + "']");
		}
	}

	//Edit a tab name
	public void editTabNameOnDashboard(String currentName, String newName) {
		Actions actions = new Actions(driver);
		info("--Edit page name on dashboard--");
		WebElement element;
		element = waitForAndGetElement("//a[@class='Tablabel' and text()='" + currentName + "']");
		actions.moveToElement(element).click(element).build().perform();

		doubleClickOnElement(ELEMENT_DASHBOARD_SELECTED);

		type(ELEMENT_DASHBOARD_NEW_INPUT, newName, true);
		WebElement elementbis = waitForAndGetElement(ELEMENT_DASHBOARD_NEW_INPUT);
		elementbis.sendKeys(Keys.RETURN);

		waitForAndGetElement("//span[text()='" + newName + "']");
		waitForElementNotPresent("//span[text()='" + currentName + "']");
	}

	//Delete a tab
	public void deleteTabOnDashboard(String currentName, boolean confirm){
		Actions actions = new Actions(driver);
		info("--Delete selected page on dashboard--");

		if(confirm){ 
			WebElement element;
			element = waitForAndGetElement("//a[@class='Tablabel' and text()='" + currentName + "']");
			actions.moveToElement(element).click(element).build().perform();
			click(ELEMENT_DASHBOARD_SELECTED_DELETE);
		} else {
			click(ELEMENT_DASHBOARD_SELECTED_DELETE);
		}	
		alt.waitForConfirmation("Are you sure to remove this dashboard?");
		waitForElementNotPresent("//span[text()='" + currentName + "']");
	}

	public void deleteGadgetOnDashboard(String gadgetTitleDisplay)
	{
		String action = "Delete Gadget";
		By deleteGadgetIcon = By.xpath("//span[text()='"+gadgetTitleDisplay+"']/preceding::span[@title='"+action+"']");
		waitForAndGetElement(deleteGadgetIcon);
		click(deleteGadgetIcon);
		alt.waitForConfirmation("Are you sure to delete this gadget?");
		waitForTextNotPresent(gadgetTitleDisplay);
	}
}
