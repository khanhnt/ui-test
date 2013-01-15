package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.BrowserPreferences;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ECMS_SE_BasicAction_Lock extends PlatformBase {
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
	BrowserPreferences bPre;

	String DATA_USER = "john";
	String DATA_PASS = "gtn";

	@BeforeMethod
	public void beforeMethods() {
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
		bPre = new BrowserPreferences(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//		logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
		//actions = null;
	}

	/*Case 01: Lock a node
	 * Create a node
	 * Lock this node
	 */
	@Test
	public void test01_LockANode() {
		String DATA_ARTICLE_TITLE = "EMCS_SE_BasicAction_Lock_Case01";
		By ARTICLE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_ARTICLE_TITLE));
		By ARTICLE_PATH_LOCKED = By.linkText(DATA_ARTICLE_TITLE);

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Go to amce node");
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Create new article");
		cTemplate.createNewFile(DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE);

		info("Lock node");
		cMenu.contextMenuAction(ARTICLE_PATH, actionType.LOCK);

		info("Check locked node");
		cMenu.isLockedNode(ARTICLE_PATH_LOCKED);

		//Delete data
		cMenu.deleteDocument(ARTICLE_PATH_LOCKED);
	}

	/*Case02: Unlock a node by locker
	 * Create a node
	 * Lock node
	 * Unlock node
	 */
	@Test
	public void test02_UnlockNodeByLocker () {
		String DATA_ARTICLE_TITLE = "EMCS_SE_BasicAction_Lock_Case02";
		By ARTICLE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_ARTICLE_TITLE));
		By ARTICLE_PATH_LOCKED = By.linkText(DATA_ARTICLE_TITLE);

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Go to amce node");
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Create new article");
		cTemplate.createNewFile(DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE);

		info("Lock node");
		cMenu.contextMenuAction(ARTICLE_PATH, actionType.LOCK);

		info("Check locked node");
		cMenu.isLockedNode(ARTICLE_PATH_LOCKED);

		info("Unlock node by locker");
		cMenu.contextMenuAction(ARTICLE_PATH_LOCKED, actionType.UNLOCK);
		waitForElementPresent(ARTICLE_PATH);

		//Delete data
		cMenu.deleteDocument(ARTICLE_PATH);
	}

	/*Case03: Unlock a node by user is  not locker 
	 * create new node
	 * lock node by user John
	 * login with mary
	 * check mary can not unlock this node
	 */
	@Test
	public void test03_UnlockNodeByUserIsNotLocker(){
		String DATA_ARTICLE_TITLE = "EMCS_SE_BasicAction_Lock_Case03";
		By ARTICLE_PATH = By.linkText(DATA_ARTICLE_TITLE);

		//create new node: File document
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();
		info("Create a new File document");
		cTemplate.createNewFile(DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE);

		//lock node
		ecms.goToNode(ARTICLE_PATH);
		cMenu.contextMenuAction(ARTICLE_PATH, actionType.LOCK);

		//check lock node
		assert cMenu.isLockedNode(ARTICLE_PATH):"Lock node is not successful";
		driver.close();

		//login with mary
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		cMenu = new ContextMenu(driver);
		ecms = new EcmsBase(driver);
		magAcc.signIn("mary", "gtn");
		navToolBar.goToSiteExplorer();

		//check mary cannot unlock this node
		ecms.goToNode(ARTICLE_PATH);
		rightClickOnElement(ARTICLE_PATH);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_UNLOCK);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_LOCK);
		info("User cannot lock or unlock node");
		magAcc.signOut();

		//delete data with user John
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(ARTICLE_PATH);
	}

	/*Case 04: Lock a node while parent node is being in locked status
	 * Login
	 * Create node and its child node
	 * Lock parent node
	 * Lock child node
	 */
	@Test
	public void test04_LockANodeWhileParentNodeIsBeingLocked() {
		String DATA_ARTICLE_TITLE = "EMCS_SE_BasicAction_Lock_Case04";
		By ARTICLE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_ARTICLE_TITLE));
		By ARTICLE_PATH_LOCKED = By.linkText(DATA_ARTICLE_TITLE);

		String DATA_FILE_NAME = "test04";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));
		By FILE_PATH_LOCKED = By.linkText(DATA_FILE_NAME);
		String ENABLE_DMS_STRUC_ID = "enableStructure";

		/*Step 1: Create parent node and child node, lock parent node*/
		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Go to amce node");
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Create new announcement document");
		cTemplate.createNewAnnouncement(DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Add new File document");
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);

		info("Lock parent node");
		cMenu.contextMenuAction(ARTICLE_PATH, actionType.LOCK);

		info("Check locked node");
		cMenu.isLockedNode(ARTICLE_PATH_LOCKED);

		/*Step 2: Lock child node*/
		info("Enable DMS structure");
		bPre.setUpPreferenceOption(ENABLE_DMS_STRUC_ID);

		waitForElementPresent(ARTICLE_PATH_LOCKED);
		ecms.goToNode(ARTICLE_PATH_LOCKED);

		info("Lock child node");
		cMenu.contextMenuAction(FILE_PATH, actionType.LOCK);

		info("Check locked node");
		cMenu.isLockedNode(FILE_PATH_LOCKED);

		//Delete data
		cMenu.deleteDocument(ARTICLE_PATH_LOCKED);
	}

	/*Case05: Unlock a node while parent node is being in lock status
	 * Login
	 * Create node and its child node
	 * Lock parent node
	 * Lock child node
	 * Unlock child node
	 */
	@Test
	public void test05_UnLockANodeWhileParentNodeIsBeingLocked() {
		String DATA_ARTICLE_TITLE = "EMCS_SE_BasicAction_Lock_Case05";
		By ARTICLE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_ARTICLE_TITLE));
		By ARTICLE_PATH_LOCKED = By.linkText(DATA_ARTICLE_TITLE);

		String DATA_FILE_NAME = "test05";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));
		By FILE_PATH_LOCKED = By.linkText(DATA_FILE_NAME);
		String ENABLE_DMS_STRUC_ID = "enableStructure";

		/*Step 1: Create parent node and child node, lock parent node*/
		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Go to amce node");
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Create new article document");
		cTemplate.createNewAnnouncement(DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Add new file document");
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);

		info("Lock parent node");
		cMenu.contextMenuAction(ARTICLE_PATH, actionType.LOCK);

		info("Check locked node");
		cMenu.isLockedNode(ARTICLE_PATH_LOCKED);

		/*Step 2: Lock child node and unlock child node*/
		info("Enable DMS structure");
		bPre.setUpPreferenceOption(ENABLE_DMS_STRUC_ID);

		waitForElementPresent(ARTICLE_PATH_LOCKED);
		ecms.goToNode(ARTICLE_PATH_LOCKED);

		info("Lock child node");
		cMenu.contextMenuAction(FILE_PATH, actionType.LOCK);

		info("Check locked node");
		cMenu.isLockedNode(FILE_PATH_LOCKED);

		info("Unlock child node");
		cMenu.contextMenuAction(FILE_PATH_LOCKED, actionType.UNLOCK);
		waitForElementPresent(FILE_PATH);

		//Delete data
		cMenu.deleteDocument(ARTICLE_PATH_LOCKED);
	}

	/*Case 06: Lock node while it is being checked-in
	 * Create node
	 * Check in this node
	 * Verify cannot lock check in node
	 */
	@Test
	public void test06_LockNodeWhileItIsBeingCheckedIn() {
		String DATA_FILE_NAME = "EMCS_DMS_SE_Lock_Case06";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Go to amce node");
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Create new file document");
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);

		info("Check in file document");
		cMenu.contextMenuAction(FILE_PATH, actionType.CHECKIN);

		info("Verify cannot lock checked-in node");
		rightClickOnElement(FILE_PATH);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_LOCK);

		//Delete data
		cMenu.contextMenuAction(FILE_PATH, actionType.CHECKOUT);
		cMenu.deleteDocument(FILE_PATH);
	}

	/*Case 07: Lock child node while its parent is being check in status
	 * Create parent node
	 * Create child node
	 * Check in parent node
	 * Lock child node
	 */
	/*@Test
	public void test07_LockChildNodeWhileItsParentIsInCheckinStatus() {
		String DATA_ARTICLE_NAME = "EMCS_DMS_SE_Lock_Case07";
		By ARTICLE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_ARTICLE_NAME));

		String DATA_FILE_NAME = "dataTest";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));
		By FILE_PATH_LOCKED = By.linkText(DATA_FILE_NAME);
		String ENABLE_DMS_STRUC_ID = "enableStructure";

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Go to amce node");
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Create new announcement document");
		cTemplate.createNewAnnouncement(DATA_ARTICLE_NAME, DATA_ARTICLE_NAME);

		info("Create child node of new document");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);

		info("Check in parent node");
		cMenu.contextMenuAction(ARTICLE_PATH, actionType.CHECKIN);

		info("Enable DMS structure");
		bPre.setUpPreferenceOption(ENABLE_DMS_STRUC_ID);

		info("Lock child node");
		cMenu.contextMenuAction(FILE_PATH, actionType.LOCK);

		info("Check unlock node");
		cMenu.isLockedNode(FILE_PATH_LOCKED);

		//Delete data
		ecms.goToNode(ARTICLE_PATH);
		cMenu.contextMenuAction(ARTICLE_PATH, actionType.CHECKOUT);
		cMenu.deleteDocument(ARTICLE_PATH);
	}*/

	/*Case 08: Lock a node when user has not set property right
	 * Login John
	 * Create document
	 * Set right for this document: James has all permission except set property
	 * Login as James
	 * James cannot lock this documents
	 */
	@Test
	public void test08_LockNodeWhenUserHasNotSetPropertyRight() {
		String DATA_ARTICLE_NAME = "ECMS_DMS_SE_BasicAction_Lock_Case08";
		By ARTICLE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_ARTICLE_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Click New Content");
		actBar.goToAddNewContent();

		info("Create a new file");
		cTemplate.createNewFile(DATA_ARTICLE_NAME, DATA_ARTICLE_NAME, DATA_ARTICLE_NAME); 

		//Add a view permission to Action Bar 
		actBar.addViewPermissionToActionBar();
		
		doubleClickOnElement(ARTICLE_PATH);
		actBar.goToNodePermissionManagement();

		info("Delete permissons of all, except John");
		ecmsPer.removeDefaultPermissionOfNode();

		info("Choose James");
		ecms.selectUser("james");

		info("Choose all rights accept Set property");
		ecmsPer.setPermissionForNode(true, false, true);

		info("Save then close");
		button.save();
		button.close();

		info("Logout then login as James");
		magAcc.signOut();
		magAcc.signIn("james", "gtn");

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Right click on node");
		rightClickOnElement(ARTICLE_PATH);

		info("Verify user cannot lock document");
		waitForElementNotPresent(cMenu.ELEMENT_MENU_LOCK);

		info("Logout");
		magAcc.signOut();

		//Login as John and delete data
		magAcc.signIn(DATA_USER, DATA_PASS);

		navToolBar.goToSiteExplorer();

		cMenu.deleteDocument(ARTICLE_PATH);
	}

	/*Case 09: Automatically unlock a node when user logout
	 * Login
	 * Create node
	 * Lock this node
	 * Logout
	 * Login --> Locked node is unlock
	 */
	@Test
	public void test09_AutomaticallyUnlockANodeWhenUserLogout () {
		String DATA_ARTICLE_TITLE = "EMCS_SE_BasicAction_Lock_Case09";
		By ARTICLE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_ARTICLE_TITLE));
		By ARTICLE_PATH_LOCKED = By.linkText(DATA_ARTICLE_TITLE);

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Go to amce node");
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Create new article");
		cTemplate.createNewFile(DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE, DATA_ARTICLE_TITLE);

		info("Lock node");
		cMenu.contextMenuAction(ARTICLE_PATH, actionType.LOCK);

		info("Check locked node");
		cMenu.isLockedNode(ARTICLE_PATH_LOCKED);

		info("Logout");
		magAcc.signOut();

		info("Login again");
		magAcc.signIn(DATA_USER, DATA_PASS);

		navToolBar.goToSiteExplorer();
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);
		rightClickOnElement(ARTICLE_PATH_LOCKED);
		waitForElementPresent(cMenu.ELEMENT_MENU_LOCK);

		//Delete data
		cMenu.deleteDocument(ARTICLE_PATH);
	}
}