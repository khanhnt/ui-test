package org.exoplatform.selenium.platform.ecms;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import org.exoplatform.selenium.platform.ecms.ActionBar;
import static org.exoplatform.selenium.TestLogger.*;

public class SiteExplorer extends ActionBar {
	
	public SiteExplorer(WebDriver dr) {
		super(dr);

		// TODO Auto-generated constructor stub
	}

	//Show Drives link
	public final By ELEMENT_SHOW_DRIVES = By.xpath("//a[@title='Show Drives']");
	public final By ELEMENT_DRIVE_MANAGE_SITE = By.xpath("//a[contains(text(),'Sites Management')]");
	public final By ELEMENT_ACME_DRIVE = By.linkText("acme-category");
	public final By ELEMENT_SITES_MANAGEMENT_DRIVE = By.xpath("//a[@class='DriveLabel' and @title = 'Sites Management']");
	public final By ELEMENT_DMS_ADMIN_DRIVE = By.linkText("DMS Administration");
	public final By ELEMENT_TRASH_DRIVE = By.xpath("//a[@title='Trash']");
	public final By ELEMENT_PRIVATE_DRIVER = By.linkText("Private");
	public final By ELEMENT_COLLABORATION_DRIVE= By.xpath("//a[@title='collaboration']");
	
	// Preference
	public final By ELEMENT_PREFERENCE_LINK =By.xpath("//a[@class='SetupPreferencesButton']");

	// Edit Tag Form
	public By ELEMENT_TAG_COULD = By.xpath("//div[@class='ItemIcon DefaultIcon TagExplorerIcon']");
	public By ELEMENT_EDIT_PUBLIC_TAG = By.xpath("//div[@title='Edit Public Tags']");
	public By ELEMENT_EDIT_PRIVATE_TAG = By.xpath("//div[@title='Edit Private Tags']");
	public String REMOVE_TAG = "//div[text()='${TagsName}']/../../td/div/img[@title='Remove Tag']";
//	public By ELEMENT_CLOSE_WINDOW = By.xpath("//a[@title='Close Window']");
	public String MESSAGE_WARNING_AFTER_DELETE_TAG = "Are you sure to delete this tag?";
	public By ELEMENT_EDIT_TAGS_FORM = By.xpath("//span[contains(text(),'Edit Tag')]");
	public final By ELEMENT_MANAGE_TAGS = By.linkText("Manage Tags");
	public final By ELEMENT_TAG_PERMISSION = By.xpath("//div[contains(text(),'Tag Permission Manager')]");

	//Tag Manager Form
	public By ELEMENT_ADD_TAGS_BUTTON = By.xpath("//a[contains(text(),'Add Tags')]");
	public By ELEMENT_CLOSE_TAG_FORM = By.xpath("//a[contains(text(),'Close')]");
	public By ELEMENT_TAG_NAME = By.id("names");
	public By ELEMENT_TAG_SCOPE = By.id("tagScopes");

	//Advanced search form
	public By ELEMENT_ADVANCED_SEARCH_ICON = By.xpath("//div[@title='Advanced Search']");
	public By ELEMENT_ADVANCED_SEARCH_TAB = By.xpath("//div[contains(text(),'Advanced Search')]");

	//Simple Search
	public final By ELEMENT_SIMPLESEARCH_TEXTBOX = By.id("simpleSearch");
	public final By ELEMENT_SIMPLESEARCH_SUBMIT = By.id("SimpleSearch");

	//publication form
	public By ELEMENT_PUBLIC_STATUS = By.xpath("//a[contains(text(), 'Published')]");
	public By EMENET_CURRENT_STATUS = By.xpath("//a[@class='CurrentStatus']");
	public By ELEMENT_CURRENT_PUBLIC_STATUS = By.xpath("//a[@class='CurrentStatus' and contains(text(), 'Published')]");

	public By ELEMENT_MORE_LINK = By.xpath("//div[@class='MoreLabel' and contains(text(),'More')]");

	/* sidebar */
	public By ELEMENT_SIDEBAR_SITES_MANAGEMENT = By.xpath("//div[@title='Sites Management']");
	//File Explorer - relation -clipboard - tag clould - saved search
	public By ELEMENT_TAG_CLOUD = By.xpath("//div[3]/div[4]/div");
	public By ELEMENT_SIDEBAR_FILE_EXPLORER = By.xpath("//div[@title='File Explorer']");
	public By ELEMENT_SAVED_SEARCH_ICON = By.xpath("//div[@title='Saved Searches']");
	public By ELEMENT_CLIPBOARD_ICON = By.xpath("//div[@title='Clipboard']");
	
	//choose a drive
	public void chooseDrive(By locator)
	{
		click(ELEMENT_SHOW_DRIVES);
		pause(1000);
		click(locator);
	}

	//Enable preferences option
	public void checkPreferenceOption(String optionId){
		By option = By.id(optionId);
		By advanced = By.linkText("Advanced");
		
		for (int repeat = 0;; repeat ++){
			if (repeat >= ACTION_REPEAT) {
				Assert.fail("Cannot enable reference option after " + ACTION_REPEAT + " tries");
			}
			waitForElementPresent(ELEMENT_PREFERENCE_LINK, 10000, 0);
			click(ELEMENT_PREFERENCE_LINK);
			waitForElementPresent(advanced, 5000, 0);
			if (getElement(advanced) != null){
				click(advanced);
				WebElement check = waitForAndGetElement(option);
				if (check.isSelected()!= true){
					click(option);
				}
				save();
				waitForElementNotPresent(advanced);
				break;
			}
			info("Retry...[" + repeat + "]");
		}
	}

	//Simple search
	public boolean simpleSearch(String keyword){
		waitForElementPresent(ELEMENT_SIMPLESEARCH_TEXTBOX);
		type(ELEMENT_SIMPLESEARCH_TEXTBOX, keyword, true);
		click(ELEMENT_SIMPLESEARCH_SUBMIT);
		if (waitForAndGetElement(By.xpath("//div[contains(text(),'"+keyword+"')]"),30000,0) != null){
			return true;
		}
		return false;
	}

	//Simple search not return result
	public boolean notSimpleSearch(String keyword) {
		return !simpleSearch(keyword);
	}

	public void addTagForNode(String name, boolean isPublic) {
		// Go to collaboration tab
		info("Go to Collaboration tab");
		goToCollaboration();

		// Click Tags
		waitForElementPresent(ELEMENT_TAG);
		click(ELEMENT_TAG);

		// Input information
		type(ELEMENT_TAG_NAME, name, true);

		// Save
		if (isPublic){
			selectOption(ELEMENT_TAG_SCOPE, "Public");
		} else 
			selectOption(ELEMENT_TAG_SCOPE, "Private");

		click(ELEMENT_ADD_TAGS_BUTTON);

		//Verify new tag
		waitForElementPresent(By.linkText(name));

		//Close tag form
		click(ELEMENT_CLOSE_TAG_FORM);

		//Verify add new tag
		click(ELEMENT_TAG_CLOUD);

		// waitForTextPresent("Private Tags");
		waitForTextPresent(name);
	}

	public void deleteTag(String name, boolean isPublic){
		// Delete tags
		By ELEMENT_REMOVE_TAG = By.xpath(REMOVE_TAG.replace("${TagsName}", name));
		if (isPublic){
			waitForElementPresent(ELEMENT_EDIT_PUBLIC_TAG);
			click(ELEMENT_EDIT_PUBLIC_TAG);
		}
		else {
			waitForElementPresent(ELEMENT_EDIT_PRIVATE_TAG);
			click(ELEMENT_EDIT_PRIVATE_TAG);
		}
		waitForElementPresent(ELEMENT_REMOVE_TAG);
		click(ELEMENT_REMOVE_TAG);
		waitForConfirmation(MESSAGE_WARNING_AFTER_DELETE_TAG);
		click(ELEMENT_CLOSE_WINDOW);
		waitForTextNotPresent(name);
	}

	/**
	 * Remove permission 
	 * @param groupPath Group string separated by slash
	 * @param membership Membership 
	 */
	public void removeTagPermission(String groupPath,String membership) {
		String str = membership + ":/" + groupPath;
		By locator = By.xpath("//div[@class='Text' and contains(text(),'" + str + "')]/ancestor::tr//img[@class='DeleteIcon']");
		By permission = By.xpath("//div[@class='Text' and contains(text(),'" + str + "')]");
		click(locator);
		waitForConfirmation("Are you sure to delete this permission?");
		waitForElementNotPresent(permission);
	}

	public void editPublicTag(){
		goToSiteExplorer();     
		waitForElementPresent(ELEMENT_TAG_CLOUD);
		click(ELEMENT_TAG_CLOUD);
		waitForElementPresent(ELEMENT_EDIT_PUBLIC_TAG);
		click(ELEMENT_EDIT_PUBLIC_TAG);
		waitForElementPresent(ELEMENT_EDIT_TAGS_FORM);
	}

	//Go to advanced search in content explorer
	public void goToAdvancedSearch(){
		click(ELEMENT_SAVED_SEARCH_ICON);
		click(ELEMENT_ADVANCED_SEARCH_ICON);
		click(ELEMENT_ADVANCED_SEARCH_TAB);
	}

	//Go to Tag Permission Screen
	public void gotoTagPermission(){
		goToContentAdministration();
		click(ELEMENT_MANAGE_TAGS);
		click(ELEMENT_TAG_PERMISSION);
	}

	//function public a document
	public void publishDocument(){
		info("Public this document");
		if ((waitForAndGetElement(ELEMENT_PUBLICATION,30000,0) == null ))
			click(ELEMENT_MORE_LINK);
		click(ELEMENT_PUBLICATION);
		WebElement current = waitForAndGetElement(EMENET_CURRENT_STATUS);
		if (current.getText().contains("Published") == false){
			click(ELEMENT_PUBLIC_STATUS);
		}
		waitForElementPresent(ELEMENT_CURRENT_PUBLIC_STATUS);
		save();
		info("Public document is successful");
	}
}
