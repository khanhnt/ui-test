package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.*;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author NhungVT
 *@date: 02/10/2012
 */
public class ECMS_SE_BasicAction_Delete extends PlatformBase {

	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	EcmsBase ecms;
	EcmsPermission ecmsPer;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;

	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		button = new Button(driver);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		ecmsPer = new EcmsPermission(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn("john","gtn");
	}

	//Delete node when user has right to remove node
	@Test()
	public void test01_DeleteNodeWhenUserHasRightToRemove(){	

		String DATA_CONTENT_FOLDER = "ECMS_DMS_SE_BasicAction_Delete_content_01";
		By CONTENT_FOLDER = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_CONTENT_FOLDER));

		String DATA_FILE_NAME = "ECMS_DMS_SE_BasicAction_Delete_File_01";
		By FILE_DOCUMENT = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));

		String DATA_UPLOAD_FILE_PATH ="TestData/Winter.jpg";
		By UPLOAD_FILE_NAME = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "Winter.jpg"));

		//goto Site Explorer
		navToolBar.goToSiteExplorer();

		//Create a Content Folder
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		//Create a File Document
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);

		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		waitForElementPresent(FILE_DOCUMENT);

		//Upload a File
		ecms.uploadFile(DATA_UPLOAD_FILE_PATH);
		waitForElementPresent(UPLOAD_FILE_NAME);

		//Delete Nodes
		cMenu.deleteDocument(CONTENT_FOLDER);
		cMenu.deleteDocument(FILE_DOCUMENT);
		cMenu.deleteDocument(UPLOAD_FILE_NAME);

		//Goto Trash driver
		actBar.chooseDrive(ecms.ELEMENT_TRASH_DRIVE);

		//Verify Delete Nodes: Search by Name
		assert siteExp.simpleSearch(DATA_CONTENT_FOLDER): "Can not found deleted content folder in Trash";
		assert siteExp.simpleSearch(DATA_FILE_NAME): "Can not found deleted content folder in Trash";
		assert siteExp.simpleSearch("Winter.jpg"): "Can not found deleted content folder in Trash";
	}

	//Delete node in 'check in' status
	@Test()
	public void test02_DeleteCheckInNode(){	
		String DATA_UPLOAD_FILE_PATH ="TestData/Winter.jpg";
		By UPLOAD_FILE_NAME = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "Winter.jpg"));

		//goto Site Explorer
		navToolBar.goToSiteExplorer();

		//Upload a File
		ecms.uploadFile(DATA_UPLOAD_FILE_PATH);
		waitForElementPresent(UPLOAD_FILE_NAME);

		//Right click and check-in node
		cMenu.contextMenuAction(UPLOAD_FILE_NAME, actionType.CHECKIN);

		//Verify item Delete item not exist
		rightClickOnElement(UPLOAD_FILE_NAME);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_DELETE);
		info("Cannot delete node being in check in status");

		//Delete data
		ecms.goToNode(UPLOAD_FILE_NAME);
		cMenu.contextMenuAction(UPLOAD_FILE_NAME, actionType.CHECKOUT);
		cMenu.deleteDocument(UPLOAD_FILE_NAME);
	}

	/*case07: Delete locked node by user is not locker
	 * create node by user John: content folder
	 * lock node by user John
	 * check user mary cannot delete this node
	 */
	@Test
	public void test07_DeleteLockedNodeByUserIsNotLocker(){
		String DATA_CONTENT_FOLDER = "ECMS_DMS_SE_BasicAction_Delete_07";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//create new content folder by John
		navToolBar.goToSiteExplorer();

		info("Create new content folder by user John");
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);
		info("Create new content folder successfully");

		//lock node
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, actionType.LOCK);

		info("Lock node successfully");
		assert cMenu.isLockedNode(ELEMENT_CONTENT_FOLDER):"Lock node unsuccessfully";
		driver.close();

		//login with user mary
		initSeleniumTest();
		driver.get(baseUrl);
		ecms = new EcmsBase(driver);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		cMenu = new ContextMenu(driver);
		magAcc.signIn("mary", "gtn");
		navToolBar.goToSiteExplorer();

		//check user mary can not delete node
		rightClickOnElement(ELEMENT_CONTENT_FOLDER);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_DELETE);
		info("User mary cannot delete this locker node");
		magAcc.signOut();

		//delete data
		magAcc.signIn("john", "gtn");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/*case09: Delete child node while parent node is being locked by user is not locker
	 * create parent node: content folder by user John
	 * create child node: article document by user John
	 * lock parent node
	 * check user Mary can delete child node
	 */
	@Test
	public void test09_DeleteChildNodeWhileParentNodeIsBeingLockedByUserIsNotLocker(){
		String DATA_CONTENT_FOLDER = "ECMS_DMS_SE_BasicAction_Delete_content_folder_09";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_ARTICLE = "ECMS_DMS_SE_BasicAction_Delete_article_09";
		By ELEMENT_ARTICLE = By.linkText(DATA_ARTICLE);

		//create new content folder by John
		navToolBar.goToSiteExplorer();
		info("Create new content folder by user John");
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);
		info("Create new content folder successfully");

		//create new child node in content folder: article document
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToAddNewContent();
		info("Create new article document");
		cTemplate.createNewFile(DATA_ARTICLE, DATA_ARTICLE, DATA_ARTICLE);

		//lock node
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, actionType.LOCK);
		info("Lock parent node");
		assert cMenu.isLockedNode(ELEMENT_CONTENT_FOLDER);
		driver.close();

		//login with mary
		initSeleniumTest();
		driver.get(baseUrl);
		ecms = new EcmsBase(driver);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		cMenu = new ContextMenu(driver);
		magAcc.signIn("mary", "gtn");
		navToolBar.goToSiteExplorer();

		//check user mary can delete child node
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		cMenu.deleteDocument(ELEMENT_ARTICLE);
		info("Delete child node successfully");
		magAcc.signOut();

		//delete data
		magAcc.signIn("john", "gtn");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);		
	}

	//case10: Delete node while user does not have remove right
	@Test()
	public void test10_DeleteNodeByUserNotHaveRemoveRight(){
		String DATA_CONTENT_FOLDER = "ECMS_DMS_SE_BasicAction_Delete_content_10";
		By CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//goto Site Explorer
		navToolBar.goToSiteExplorer();

		//Create a Content Folder
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		//Add a view permission to Action Bar 
		actBar.addViewPermissionToActionBar();

		//Goto System > Permissions > Uncheck Remove Right for Group *:/platform/web-contributor
		doubleClickOnElement(CONTENT_FOLDER);
		actBar.goToNodePermissionManagement();

		ecms.selectMembership("platform/web-contributors", "*", "Select Membership");
		ecmsPer.setPermissionForNode(true, true, false);

		info("Save then close");
		button.save();
		button.close();

		//Sign out and Sign in as mary
		magAcc.signOut();
		magAcc.signIn("mary", "gtn");

		//goto Site Explorer
		navToolBar.goToSiteExplorer();

		//Verify Deleted item not existed
		waitForElementPresent(CONTENT_FOLDER);
		rightClickOnElement(CONTENT_FOLDER);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_DELETE);

		//Delete data
		magAcc.signOut();
		magAcc.signIn("john", "gtn");

		//goto Site Explorer
		navToolBar.goToSiteExplorer();

		//Verify Deleted item not existed
		waitForElementPresent(CONTENT_FOLDER);
		cMenu.deleteDocument(CONTENT_FOLDER);
	}

	//	//Delete public folder
	//	@Test()
	//	public void test16_DeletePublicDriver()
	//	{
	//		//goto Site Explorer
	//		goToSiteExplorer();
	//		
	//		//Click Show Drives > collaboration > Users 
	//		waitForElementPresent(SHOW_DRIVES_ICON);
	//		click(SHOW_DRIVES_ICON);
	//		waitForElementPresent(COLLABORATION_ICON);
	//		doubleClickOnElement(COLLABORATION_ICON_XPATH);
	//		waitForElementPresent(USERS_FOLDER);
	//		doubleClickOnElement(USERS_FOLDER_XPATH);
	//		
	//		waitForElementPresent(ADDRESS_INPUT);
	//		type(ADDRESS_INPUT, "/Users/j___/jo___/joh___/john", true);
	//		WebElement element = waitForAndGetElement(ADDRESS_INPUT);
	//		//actions.sendKeys(element, Keys.ENTER).build().perform();
	//		element.sendKeys(Keys.ENTER);
	//		
	//	}
	//	
	//	//Delete private folder
	//	@Test()
	//	public void test17_DeletePrivateDriver()
	//	{
	//		
	//	}

	@AfterMethod()
	public void afterTest()
	{
		//logoutEcms();
		driver.quit();
	}
}
