package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.NavigationToolbar;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ManageApplications extends PlatformBase {
	
	NavigationToolbar nav = new NavigationToolbar(driver);
	
	/* Manage Application Page */
	public By ELEMENT_IMPORT_APPLICATION = By.xpath("//div[text()='Import Applications']");
	public By ELEMENT_CATEGORIES_AREA_TITLE = By.xpath("//div[text()='Categories']");

	//Gadget
	
	//Application Registry portlet -> Edit Portlet
	public By SHOW_IMPORT_UNCHECK = By.xpath("//input[@id='showImport' and @value='false']");
	
	public  By ELEMENT_GADGET_LINK = By.xpath("//a[contains(text(),'Gadget')]");
	public  By ELEMENT_ADD_REMOTE_GADGET_LINK = By.xpath("//div[text()='Add a remote gadget']");
	public  By ELEMENT_URL_TEXBOX = By.id("url");
	public  By ELEMENT_ADD_BUTTON = By.xpath("//button[text()='Add']");
	public  String ELEMENT_GADGET_ICON = "//a[@title='{$gadget}']";
	public  String ELEMENT_GADGET_DELETE_ICON = "//a[@title='{$gadget}']/..//a[@title='Delete Gadget']";
	
	//Application Registry portlet -> Edit Portlet
	public  By ELEMENT_APPS_REG_PORTLET = By.className("PortletLayoutDecorator");
	public  By ELEMENT_SHOW_IMPORT_CHECKBOX = By.xpath("//input[@id='showImport']");
	public  By SHOW_IMPORT_CHECKED = By.xpath("//input[@id='showImport' and @checked='']");
	public  By ELEMENT_ACCESS_PERMISSION_TAB = By.xpath("//div[text()='Access Permission']");
	
	
	//Category
	public  final By ELEMENT_ADD_NEW_CATEGORY = By.xpath("//div[@id = 'UIApplicationOrganizer']//div//div[@class = 'IconControl AddCategoryIcon']");
	public  final By ELEMENT_FIELD_CATEGORY_NAME = By.id("name");
	public  final By ELEMENT_FIELD_DISPLAY_NAME = By.id("displayName");
	public  final By ELEMENT_FIELD_DESCRIPTION = By.id("description");
	public  final By ELEMENT_CATEGORY_REMOVE_ICON = By.xpath("//div[@id='UIApplicationOrganizer']//div[@class='TabRepeat ClearFix']/a[@class='ControlIcon DeleteIcon']");
	public  final String ELEMENT_CATEGORY_EDIT_ICON = "//div[@id='UIApplicationOrganizer']//div[@class='TabRepeat ClearFix']/a[contains(@title,'${categoryName}')]/../a[@class='ControlIcon EditIcon']";
	public  final String MESSAGE_EMPTY_CATEGORY = "This category is empty. Click the (+) button to add an application.";
	public  final String MESSAGE_CONFIRM_DELETE_CATEGORY = "Are you sure to delete this category and all applications on it?";
	public  final String ELEMENT_CATEGORY_NAME = "//a[@title='${categoryName}']";
	public  final By ELEMENT_CATEGORIES_FORM = By.xpath("//div[text()='Categories']"); 
	public  final String IMPORT_APPLICATION_CONFIRMATION="This will automatically import all gadgets and portlets into new categories.";

	public ManageApplications(WebDriver dr){
		driver = dr;
	}
	// Gadget functions
	public void addRemoteGadget (String Url) {
		for (int i =0;; i++)
		{
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL) 
			{
				Assert.fail("Timeout");
			}
			click(ELEMENT_ADD_REMOTE_GADGET_LINK);
			if (isElementPresent(ELEMENT_URL_TEXBOX))
			{
				type(ELEMENT_URL_TEXBOX, Url, true);
				click(ELEMENT_ADD_BUTTON);
				return;
			}
		}
	}


	public  void deleteGadget (String gadgetName) {
		waitForElementPresent(ELEMENT_GADGET_ICON.replace("{$gadget}", gadgetName));
		click(By.xpath(ELEMENT_GADGET_DELETE_ICON.replace("{$gadget}",gadgetName)));
		waitForConfirmation("Are you sure to delete this gadget?");
		pause(1000);
		waitForElementNotPresent(ELEMENT_GADGET_ICON.replace("{$gadget}", gadgetName));
		info("'"+gadgetName+"' is deleted successfully");
	}

	//Category
	//Add a new category in Manage Applications
	public void addNewCategoryAtManageApplications(String categoryName, String displayName, String categoryDescription,
			boolean publicMode, Map<String, String> permissions, boolean verify){

		info("-- Add a new category --");
		String ELEMENT_CURRENT_CATEGORY_NAME = ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryName);
		click(ELEMENT_ADD_NEW_CATEGORY);
		waitForTextPresent("Category name:");
		type(ELEMENT_FIELD_CATEGORY_NAME, categoryName, true);
		type(ELEMENT_FIELD_DISPLAY_NAME, displayName, true);
		type(ELEMENT_TEXTAREA_DESCRIPTION, categoryDescription, true);
		click(ELEMENT_PERMISSION_SETTING_TAB);
		waitForTextPresent("Make it public");
		if (publicMode){
			check(ELEMENT_CHECKBOX_PUBLIC_MODE);
			waitForElementNotPresent(ELEMENT_ADD_PERMISSION_BUTTON);
		} else {
			for (String key : permissions.keySet()) {
				setViewPermissions(key, permissions.get(key));
			}
		}
		save();	
		if (verify) {
			waitForTextPresent(MESSAGE_EMPTY_CATEGORY);
			waitForAndGetElement(ELEMENT_CURRENT_CATEGORY_NAME);
		}
	}

	//Edit a category at Manage Applications
	public void editCategoryAtManageApplications(String categoryName, String newDisplayName, String newCategoryDescription,
			boolean publicMode, Map<String, String> permissions, boolean verify){

		info("--Edit category (" + categoryName + ")--");
		String ELEMENT_EDIT_CATEGORY_NAME = ELEMENT_CATEGORY_NAME.replace("${categoryName}", newDisplayName);
		String editIcon = ELEMENT_CATEGORY_EDIT_ICON.replace("${categoryName}",categoryName);
		click(ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryName));
		click(editIcon);
		type(ELEMENT_FIELD_DISPLAY_NAME, newDisplayName, true);
		type(ELEMENT_TEXTAREA_DESCRIPTION, newCategoryDescription, true);
		click(ELEMENT_PERMISSION_SETTING_TAB);
		waitForTextPresent("Permission Settings");
		if (publicMode){
			check(ELEMENT_CHECKBOX_PUBLIC_MODE);
			waitForElementNotPresent(ELEMENT_ADD_PERMISSION_BUTTON);
		} else {
			makeItPublic(false);
			for (String key : permissions.keySet()) {
				setViewPermissions(key, permissions.get(key));
			}
		}
		save();
		if (verify) {
			waitForTextPresent(MESSAGE_EMPTY_CATEGORY);
			waitForAndGetElement(ELEMENT_EDIT_CATEGORY_NAME);
		}
	}

	//Delete a category at Manage Applications
	public void deleteCategoryAtManageApplications(String categoryName, boolean verify){
		info("--Delete category (" + categoryName + ")--");
		//		String ELEMENT_CURRENT_CATEGORY_NAME = ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryName);
		By ELEMENT_CURRENT_CATEGORY_NAME = By.xpath(ELEMENT_CATEGORY_NAME.replace("${category}", categoryName)) ;
		click(ELEMENT_CURRENT_CATEGORY_NAME);
		pause(500);
		click(ELEMENT_CATEGORY_REMOVE_ICON);
		waitForConfirmation(MESSAGE_CONFIRM_DELETE_CATEGORY);
		if (verify) {
			waitForElementNotPresent(ELEMENT_CURRENT_CATEGORY_NAME);
		}
		pause(500);
	}

	//Select a category
	public void selectCategoryAtManageApplications(String categoryName) {
		info("--Select category (" + categoryName + ")--");
		String ELEMENT_CURRENT_CATEGORY_NAME = ELEMENT_CATEGORY_NAME.replace("${categoryName}", categoryName);
		waitForAndGetElement(ELEMENT_CURRENT_CATEGORY_NAME);
		click(ELEMENT_CURRENT_CATEGORY_NAME);
		pause(500);
	}

	public void makeItPublic(boolean checked){
		By ELEMNT_PUBLIC_OPTION = By.xpath("//input[@id='publicMode']");
		WebElement element = waitForAndGetElement(ELEMNT_PUBLIC_OPTION);
		String status = element.getAttribute("checked"); // checked if check otherwise blank

		if (checked) {
			if (!"true".equalsIgnoreCase(status)) check(ELEMNT_PUBLIC_OPTION);
		} else {
			if ("true".equalsIgnoreCase(status)) uncheck(ELEMNT_PUBLIC_OPTION);
		}
	}

	public void addApplicationToCategory (String categoryTitle, boolean addNewApps, String newDisplayName, String applicationType, String displayName, boolean publicMode, String groupId, String membership ) {
		By ELEMENT_ADD_APPS_BUTTON = By.xpath("//a[@title='"+categoryTitle+"']/following::a[@title='Add an Application to this Category']");
		By ELEMENT_DISPLAY_NAME_TEXTBOX = By.id("displayName");
		By ELEMNET_ADD_BUTTON = By.linkText("Add");
		By ELEMENT_APPS_TYPE = By.id("type");
		By ELEMENT_APPS_EXISTING = By.xpath("//span[@class='label' and text()='"+displayName+"']/../..//input[@name='application']");
		String ELEMENT_APP_LOCATOR = "//span[@id='label' and text()='"+displayName+"']";

		waitForElementPresent(ELEMENT_ADD_APPS_BUTTON);
		click(ELEMENT_ADD_APPS_BUTTON);
		waitForElementPresent(ELEMNET_ADD_BUTTON);

		//Add new application
		if (addNewApps) {
			waitForElementPresent(ELEMENT_DISPLAY_NAME_TEXTBOX);
			type(ELEMENT_DISPLAY_NAME_TEXTBOX, newDisplayName, true);
			select(ELEMENT_APPS_TYPE, applicationType);
		}
		else
		{
			select(ELEMENT_APPS_TYPE, applicationType);
			usePaginator(ELEMENT_APP_LOCATOR, "Application "+displayName+" is not found");
			click(ELEMENT_APPS_EXISTING);
		}
		click(ELEMNET_ADD_BUTTON);

		//Set permission
		if (publicMode) makeItPublic(true);
		else {
			makeItPublic(false);
			setViewPermissions(groupId, membership);
		}
	}

	public void deleteApplication(String categoryTitle, String applicationName) {
		By CATEGORY_XPATH = By.xpath("//a[@title='"+categoryTitle+"']");
		By DELETE_APP_ICON = By.xpath("//a[@title='"+applicationName+"']/following::a[@title='Delete Application']");
		//By DELETE_APP_ICON= By.xpath("//span[@class='label' and text()='"+applicationName+"']/../..//input[@name='application']");
		waitForElementPresent(CATEGORY_XPATH);
		click(CATEGORY_XPATH);
		waitForElementPresent(DELETE_APP_ICON);
		click(DELETE_APP_ICON);
		waitForConfirmation("Are you sure to delete this application?");
		pause(1000);
		waitForTextNotPresent(applicationName);
		info("'"+applicationName+"' is deleted successfully");
	}


	//Check show import
	public void showImportApplication (boolean checkShowImport) {

		//goto Application
		nav.goToApplicationRegistry();

		//Verify Categories display as default
		waitForElementPresent(ELEMENT_CATEGORIES_FORM);

		//goto Edit Page
		nav.goToEditPageEditor();

		//Click on Edit Portlet icon
		mouseOver(ELEMENT_APPS_REG_PORTLET, false);
		click(ELEMENT_EDIT_PORTLET_ICON);
		WebElement element = waitForAndGetElement(ELEMENT_SHOW_IMPORT_CHECKBOX);
		String status = element.getAttribute("value");

		if (checkShowImport)
		{
			if (!(status.equalsIgnoreCase("true"))) check(ELEMENT_SHOW_IMPORT_CHECKBOX);    				
		} 
		else 
		{
			if (status.equalsIgnoreCase("true")) click(ELEMENT_SHOW_IMPORT_CHECKBOX);
		}
		save();
		close();
		click(ELEMENT_FINISH_ICON);
		pause(1000);

		//Verify after changing show import
		if (checkShowImport)
		{
			waitForElementPresent(ELEMENT_IMPORT_APPLICATION);
		} 
		else 
		{
			waitForElementNotPresent(ELEMENT_IMPORT_APPLICATION);
		}
	}
	
	public void importApplication () {
		click(ELEMENT_IMPORT_APPLICATION);
		waitForConfirmation(IMPORT_APPLICATION_CONFIRMATION);
		pause(1000);
	}
}
