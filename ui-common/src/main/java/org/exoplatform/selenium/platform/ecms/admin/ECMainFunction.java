package org.exoplatform.selenium.platform.ecms.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * <li>This class manage access to feature pages 
 * in ECM Admin </li>
 * @author vuna2
 *
 */
public class ECMainFunction extends EcmsBase{

	public ECMainFunction(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	NavigationToolbar navToolbar = new NavigationToolbar(driver);
	
	//Template tab
	public final By ELEMENT_TEMPLATE_TAB = By.xpath("//*[text()='Templates']");
	public final By ELEMENT_DOCUMENT_LINK = By.className("uiIconEcmsTemplatesManager");
	public final By ELEMENT_MANAGE_TEMPLATE_SCREEN = By.xpath("//*[@class='title' and contains(text(),'Documents')]");

	//Explorer Tab
	public final By ELEMENT_EXPLORER_TAB = By.xpath("//*[text()='Explorer']");
	public final By ELEMENT_DRIVES_TAB = By.className("uiIconEcmsDriveManager");

	//Explorer Tags
	public final By ELEMENT_WCM_CATEGORY_TAG = By.xpath("//div[contains(text(),'Categories & Tags')]");

	//Repository > Node Type
	public final By ELEMENT_CONTENT_TYPES = By.xpath("//div[contains(text(),'Content Types')]");
	public final By ELEMENT_MANAGE_NODETYPE = By.className("uiIconEcmsNodeTypeManager");
	//		public final By ELEMENT_NODETYPE_TEXT = By.id("NodeTypeText");
	//		public final By ELEMENT_NODETYPE_SEARCH = By.xpath("//a[@title = 'Search']");

	//Repository > Locks
	public final String ELEMENT_UNLOCK_NODE = "//*[text()='${lockedNode}']/../..//*[@class='uiIconUnlockMini']";
	
	public final By ELEMENT_MANAGE_CATEGORIES_LINK = By.className("uiIconEcmsTaxonomyManagerTrees");

	/*=============================================================*/

	//Template
	//Open Manage Template Screen
	public void goToTemplateTab(){	    
		navToolbar.goToContentAdministration();
		if (isTextNotPresent("Documents")){
			click(ELEMENT_TEMPLATE_TAB);
			click(ELEMENT_DOCUMENT_LINK);
		}
		waitForElementPresent(ELEMENT_MANAGE_TEMPLATE_SCREEN);	    
	}

	/////////////////
	//Go To Content Administration / Repository / Manage Lock Tab
	public void goToLockTabInContentAdmin(){
		navToolbar.goToContentAdministration();
		click(ELEMENT_REPOSITORY_TAB);
		click(ELEMENT_MANAGE_LOCKS);
		click(ELEMENT_MANAGE_LOCK_TAB);
	}

	//Go To Content Administration / Repository / Node Types Tab
	public void goToNodeTypeTabInContentAdmin(){
		navToolbar.goToContentAdministration();
		if (isTextNotPresent("Node Types")){
			click(ELEMENT_REPOSITORY_TAB);
			click(ELEMENT_MANAGE_NODETYPE);
		}
		waitForElementPresent(By.xpath("//*[@class='title' and contains(text(), 'Node Types')]"));
	}

	//Explorer: Driver/Views/Tags
	/////////

	//Function to go to Manage Driver from main screen
	public void goToManageDriver(){
		info("Go to manage driver form");
		navToolbar.goToContentAdministration();
		if (isElementNotPresent(By.xpath("//*[text()='Add Drive']"))){
			click(ELEMENT_EXPLORER_TAB);
			click(ELEMENT_DRIVES_TAB);
		}
		Utils.pause(500);
	}

	//Go to Manage View Screen
	public void goToManageViews(){
		navToolbar.goToContentAdministration();
		if (isTextNotPresent("Explorer Templates")){
			click(ELEMENT_EXPLORER_TAB);
			click(ELEMENT_MANAGEMENT_VIEW);
		}
		Utils.pause(500);
	}

	//View > Explorer templates
	public void goToExplorerTemplates(){
		if (isTextNotPresent("Explorer Templates")){
			goToManageViews();
		}
		click(ELEMENT_TAB_EXPLORER_TEMPLATE);
		Utils.pause(500);
	}

	//Go to Tags 
	public void goToTagsTab(){
		info("Go to Tags tab");
		navToolbar.goToContentAdministration();
		click(ELEMENT_EXPLORER_TAB);
		click(ELEMENT_MANAGE_TAGS);
		waitForTextPresent("Tag Manager");
	}

	//Go to Tag Permission Screen
	public void goToTagPermissionManager(){
		goToTagsTab();
		click(ELEMENT_TAG_PERMISSION);
		waitForTextPresent("Memberships");
	}
	/////////////

	//Advanced: Categories/Queries/Scripts/Actions
	/////
	//Go to Category Tab in Content Admin
	public void goToCategoriesTabInContentAdmin(){
		navToolbar.goToContentAdministration();
		if (isTextNotPresent("Category Tree")){
			click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
			click(ELEMENT_MANAGE_CATEGORIES_LINK);
		}
		Utils.pause(500);
	}

	////////

}
