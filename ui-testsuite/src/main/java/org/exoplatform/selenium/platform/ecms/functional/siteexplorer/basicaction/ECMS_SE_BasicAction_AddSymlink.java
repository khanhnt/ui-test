package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * <li>PLF4: Update by
 * @author vuna2
 * </li>
 *
 */
/*
 * @author: Lientm
 * @date: 24/09/2012
 */
public class ECMS_SE_BasicAction_AddSymlink extends PlatformBase{
	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	Dialog dialog;
	ManageAlert magAlert;

	//Ecms
	EcmsBase ecms;
	EcmsPermission ecmsPer;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;

	public final String DATA_USER = "john";
	public final String DATA_PASS = "gtn";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		dialog = new Dialog(driver);
		button = new Button(driver);
		magAlert = new ManageAlert(driver);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		ecmsPer = new EcmsPermission(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//		logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * case01: Open form to add Symlink for node using icon in action bar
	 * set view add symlink
	 * go to add symlink
	 * check form
	 */
	@Test
	public void test01_CheckFormAddSymlinkForNode(){		  
		//go to add symlink
		info("Add symlink for acme node");
		navToolBar.goToSiteExplorer();

		//set view add symlink
		actBar.addViewSymlinkToActionBar();

		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);
		actBar.goToAddSymlinkTab();

		//check Add symlink form
		waitForElementPresent(ecms.ELEMENT_ADD_SYMLINK_POPUP);
		assert isElementPresent(ecms.ELEMENT_ADD_SYMLINK_POPUP):"Not found Add symlink form";
		assert isTextPresent("Symlink Manager"):"Add symlink form is not true";
		assert isElementPresent(ecms.ELEMENT_SYMLINK_PATH_NODE):"Add symlink form is not true";
		assert isElementPresent(ecms.ELEMENT_SYMLINK_NAME):"Add symlink form is not true";	  
		info("Check add symlink popup successfully");
		dialog.closeMessageDialog();
	}

	/**
	 * case02: Add Symlink when right click on node but do not select this node
	 * go to acme
	 * right click on document -> add symlink
	 * check symlink
	 */
	@Test
	public void test02_AddSymlinkWhenRightClick(){
		String DATA_FOLDER = "ecmsaddsymlinkfolder02";
		By ELEMENT_FOLDER = By.linkText(DATA_FOLDER);

		//create node: content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(DATA_FOLDER, folderType.Content);
		info("Create new content folder successfully");

		// add symlink
		info("Add symlink for node");
		cMenu.contextMenuAction(ELEMENT_FOLDER, actionType.SYMLINK);

		// check symlink
		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_FOLDER));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_FOLDER)): "Add new symlink unsuccessfully";
		info("Add symlink for documents successful");

		//delete symlink
		cMenu.deleteData(ELEMENT_FOLDER);
	}

	/*case03: Add Symlink for some nodes at the same time
	 * go to acme
	 * choose 2 node
	 * add symlink
	 * check symlink
	 */
	//	  @Test(groups={"pending"})
	//	  public void test03_AddSymlinkForSomenode(){
	//		  By CATEGORIES = By.xpath("//div[@title='categories ']");
	//		  By CATEGORIES_SYMLINK = By.xpath("//div[@title='categories.lnk ']");
	//		  By CSS = By.xpath("//div[@title='css ']");
	//		  By CSS_SYMLINK = By.xpath("//div[@title='css.lnk ']");
	//
	//		  //go to acme
	//		  navToolBar.goToSiteExplorer();
	//		  goToNode(ELEMENT_ACME);
	//
	//		  //choose 2 node
	//		  WebElement ELEMENT_CATEGORIES = waitForAndGetElement(CATEGORIES);
	//		  WebElement ELEMENT_CSS = waitForAndGetElement(CSS);
	//		  //actions.keyDown(Keys.CONTROL).click(ELEMENT_CATEGORIES).keyDown(Keys.SHIFT).click(ELEMENT_CSS).keyUp(Keys.CONTROL).build().perform();
	//		  //click(CATEGORIES);
	//		  actions.keyDown(Keys.CONTROL).click(ELEMENT_CSS).release().perform();
	//		  //actions.keyDown(Keys.CONTROL).moveToElement(ELEMENT_CATEGORIES).click().moveToElement(ELEMENT_CSS).click().keyUp(Keys.CONTROL).build().perform();
	//
	//		  // add symlink
	//		  rightClickOnElement(CATEGORIES);
	//		  click(ELEMENT_ADD_SYMLINK);
	//		  //check symlink
	//		  waitForElementPresent(CATEGORIES_SYMLINK);
	//		  assert isElementPresent(CATEGORIES_SYMLINK):"Add symlink for categories unsuccessfully";
	//		  waitForElementPresent(CSS_SYMLINK);
	//		  assert isElementPresent(CSS_SYMLINK):"Add symlink for css unsuccessfully";
	//		  info("Add symlink for categories and symlink successfully");
	//		  //delete symlink
	//		  goToNode(CATEGORIES_SYMLINK);
	//		  deleteDocument(CATEGORIES_SYMLINK);
	//		  waitForElementNotPresent(CATEGORIES_SYMLINK);
	//		  goToNode(CSS_SYMLINK);
	//		  deleteDocument(CSS_SYMLINK);
	//		  waitForElementNotPresent(CSS_SYMLINK);
	//	  }

	/**
	 * case04: Add Symlink for root
	 * go to site explorer
	 * add symlink
	 */
	@Test
	public void test04_AddSymlinkForRoot(){	
		String DATA_SYMLINK = "acme.lnk"; //"ECMS_DMS_SE_Addsymlink_symlink_04";
		By ELEMENT_DOCUMENT_SYMLINK = By.linkText(DATA_SYMLINK);

		//go to add symlink
		navToolBar.goToSiteExplorer();

		//add symlink for root
		info("Add symlink for root");
		actBar.addSymlink("collaboration", "sites/acme", DATA_SYMLINK);

		//check symlink
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		click(ELEMENT_DOCUMENT_SYMLINK);
		waitForElementPresent(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "documents"));
		assert isElementPresent(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "documents")):"Add symlink for documents node unsuccessfully";
		info("Add symlink for document node successfully");

		//delete symlink
		cMenu.deleteData(ELEMENT_DOCUMENT_SYMLINK);
	}

	/**
	 * case05: Add Symlink for node is Content Folder
	 * create new content folder
	 * add symlink for node with target node = documents
	 * check symlink
	 */
	@Test
	public void test05_AddSymlinkForContentFolder(){
		String DATA_CONTENT_FOLDER = "contentfolder05";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "contentfolder05.lnk";

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for content folder");
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER , DATA_SYMLINK);

		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER)):"Add symlink for content folder unsuccessfully";

		//delete data
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * case06: Add Symlink for node is Document Folder
	 * create new document folder
	 * add symlink for node with target node = documents
	 * check symlink
	 */
	@Test
	public void test06_AddSymlinkForDocumentFolder(){
		String DATA_DOCUMENT_FOLDER = "documentfolder06";
		By ELEMENT_DOCUMENT_FOLDER = By.linkText(DATA_DOCUMENT_FOLDER);
		String DATA_SYMLINK = "documentfolder06.lnk";

		//create new document folder
		navToolBar.goToSiteExplorer();

		info("Create new document folder");
		cTemplate.createNewFolder(DATA_DOCUMENT_FOLDER, folderType.Document);
		waitForElementPresent(ELEMENT_DOCUMENT_FOLDER);
		assert isElementPresent(ELEMENT_DOCUMENT_FOLDER):"Create new document folder unsuccessfully";
		info("Create new document folder successfully");

		//add symlink
		ecms.goToNode(ELEMENT_DOCUMENT_FOLDER);

		info("Add symlink for document folder");
		actBar.addSymlink("collaboration", "sites/" + DATA_DOCUMENT_FOLDER , DATA_SYMLINK);

		ecms.goToNode(ELEMENT_DOCUMENT_FOLDER);
		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_DOCUMENT_FOLDER));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_DOCUMENT_FOLDER)): "Add symlink for document folder unsuccessfully";

		//delete data
		cMenu.deleteData(ELEMENT_DOCUMENT_FOLDER);
	}

	/**
	 * case08: Add Symlink for node is File document
	 * create new file document
	 * add symlink to file document
	 * check add successfully
	 */
	/*@Test
	public void test08_AddSymlinkForFileDocument(){
		String DATA_FILE_DOCUMENT = "filedocument08";
		By ELEMENT_FILE_DOCUMENT = By.linkText(DATA_FILE_DOCUMENT);
		String DATA_SYMLINK = "filedocument08.lnk";

		//create new file document
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();

		info("Create new file document");
		cTemplate.createNewFile(DATA_FILE_DOCUMENT, DATA_FILE_DOCUMENT, DATA_FILE_DOCUMENT);
		assert isElementPresent(ELEMENT_FILE_DOCUMENT):"Create new file document unsuccessfully";
		info("Create new file document successfully");

		//add symlink
		info("Add symlink for file document");
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		actBar.addSymlink("collaboration", "sites/" + DATA_FILE_DOCUMENT, DATA_SYMLINK);

		//bPre.setUpPreferenceOption("enableStructure");
		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_FILE_DOCUMENT));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_FILE_DOCUMENT)): "Add symlink for file document unsuccessfully";

		//delete data
		cMenu.deleteData(ELEMENT_FILE_DOCUMENT);
	}*/

	/**
	 * case13: Add Symlink for node is uploaded file
	 * upload file
	 * add symlink
	 * check add symlink successfully
	 */
	/*@Test
	public void test13_AddSymlinkForUploadedFile(){
		String DATA_UPLOAD_FILE_LINK = "TestData/ECMS_DMS_SE_Addsymlink.jpg";
		String DATA_SYMLINK = "ECMS_DMS_SE_Addsymlink.jpg.lnk";
		By ELEMENT_DOCUMENT_SYMLINK = By.linkText("ECMS_DMS_SE_Addsymlink.jpg");

		//create a new file document
		navToolBar.goToSiteExplorer();

		info("upload new file");
		ecms.uploadFile(DATA_UPLOAD_FILE_LINK);
		waitForTextPresent("ECMS_DMS_SE_Addsymlink.jpg");
		assert isElementPresent(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "ECMS_DMS_SE_Addsymlink.jpg")): "Create new file unsuccessfully";
		info("Create new file successfully");

		//add symlink
		actBar.addSymlink("collaboration", "sites/ECMS_DMS_SE_Addsymlink.jpg", DATA_SYMLINK);
		//goToNode(ELEMENT_UPLOAD_FILE);

		info("Add symlink for uploaded file");
		//checkPreferenceOption("enableStructure");
		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "ECMS_DMS_SE_Addsymlink.jpg"));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "ECMS_DMS_SE_Addsymlink.jpg")): "Add symlink for upload file unsuccessfully";
		info("Add symlink for upload file successfully");

		//delete data
		cMenu.deleteData(ELEMENT_DOCUMENT_SYMLINK);
	}*/

	/**
	 * case14: Add Symlink for node is Symlink
	 * create new node - content folder
	 * add symlink for this node
	 * add symlink for symlink node
	 * check add successfully
	 */
	@Test
	public void test14_AddSymlinkForSymlinkNode(){
		String DATA_CONTENT_FOLDER = "contentfolder14";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "contentfolder14.lnk";

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		//add symlink for content folder
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for content folder");
		actBar.addSymlink("collaboration", "sites/contentfolder14", DATA_SYMLINK);

		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER)): "Add symlink for content folder unsuccessfully";
		info("Add symlink for content folder successfully");

		//add symlink for symlink node
		doubleClickOnElement(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER));
		info("Add symlink for exiting symlink");
		actBar.addSymlink("collaboration", "Users", "Users.lnk");

		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "Users"));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "Users")): "Add symlink for symlink unsuccessfully";
		info("Add symlink for symlink node successfully");

		//delete data
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * case15: Add Symlink for node to the node is Symlink
	 * add symlink for acme/documents -> documents.lnk
	 * add symlink for acme  
	 * check cannot found target node is symlink documents.lnk
	 */
	@Test
	public void test15_AddSymlinkForNodeToSymlinkNode(){
		String DATA_CONTENT_FOLDER = "contentfolder15";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		By ELEMENT_DOCUMENT_SYMLINK = By.linkText("documents.lnk");

		//create new node - content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		//create symlink for acme/document
		click(ecms.ELEMENT_SIDEBAR_ACME);
		info("Add symlink for documents");
		cMenu.contextMenuAction(ecms.ELEMENT_SIDEBAR_ACME_DOCUMENTS, actionType.SYMLINK);	
		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "documents"));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "documents")): "Add new symlink unsuccessfully";
		info("Add symlink for documents successfully");

		//check cannot add symlink for node with target node is symlink documents.lnk
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToAcmeNodeInAddSymlink();
		waitForElementNotPresent(ELEMENT_DOCUMENT_SYMLINK);
		info("cannot select target node is a symlink node");
		click(ecms.ELEMENT_ADD_SYMLINK_CLOSE_WINDOWS);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);
		cMenu.deleteDocument(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "documents"));
	}

	/**
	 * case16: Add Symlink when 'Symlink Name' field is blank
	 * create symlink for acme with name symlink blank
	 */
	@Test
	public void test16_AddSymlinkWithBlankName(){
		//go to acme
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		//add symlink with blank name
		info("Add symlink with blank name");
		actBar.goToAcmeNodeInAddSymlink();
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		type(ecms.ELEMENT_SYMLINK_NAME, "", true);
		button.save();

		//check alert
		magAlert.verifyAlertMessage("The field \"Symlink Name\" is required.");
		button.cancel();
		info("cannot add symlink with blank name");
	}

	/**
	 * case17: Add Symlink when “Path Node” field is blank
	 * create symlink for acme with path node blank
	 */
	@Test
	public void test17_AddSymlinkWithBlankPathNode(){		  
		//go to acme
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		//add symlink with path node blank
		info("Add symlink with blank path node");
		actBar.goToAddSymlinkTab();
		waitForElementPresent(ecms.ELEMENT_ADD_SYMLINK_POPUP);
		type(ecms.ELEMENT_SYMLINK_NAME, "Test", true);
		button.save();

		//check alert
		magAlert.verifyAlertMessage("Please enter the path node.");
		button.cancel();
		info("cannot add symlink with blank path node");
	}

	/**
	 * case18: Add Symlink when 'Symlink Name' field contains special characters like @, #,$..
	 * 
	 */
	/*@Test(groups={"pending"})
	public void test18_AddSymlinkWithNameContainsSpecialCharacter(){
		//go to acme
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		//add symlink with blank name
		info("Add symlink with name contains special characters");
		actBar.goToAcmeNodeInAddSymlink();
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		for(int i = 0; i < cTemplate.DATA_SPECIAL_CHARACTER.length; i++){
			info("Input symlink name contains character: " + cTemplate.DATA_SPECIAL_CHARACTER[i]);
			//addSymlink(ELEMENT_SELECT_PATH_DOCUMENTS, DATA_SPECIAL_CHARACTER[i]);
			type(ecms.ELEMENT_SYMLINK_NAME, cTemplate.DATA_SPECIAL_CHARACTER[i], true);
			button.save();

			//check alert
			magAlert.verifyAlertMessage("An error occurred while creating the symlink.");
			info("cannot add symlink with name contains qspecial characters");
		}
		button.cancel();
	}*/

	/**
	 * case19: Check the displaying of workspace when user dose not permission to view it when add Symlink 
	 * login with mary
	 * create new node - content folder in acme/document
	 * check cannot add symlink for this node with system workspace
	 */
	@Test
	public void test19_CheckDisplayWorkspaceWhenUserHasNotPermission(){
		String DATA_CONTENT_FOLDER = "contentfolder19";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//login with mary
		magAcc.signOut();
		info("Login ECMS with user: mary");
		magAcc.signIn("mary", "gtn");
		navToolBar.goToSiteExplorer();

		//create new node - content folder
		info("create new content folder");
		click(ecms.ELEMENT_SIDEBAR_ACME);
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		//check cannot add symlink for this node with system workspace
		click(ELEMENT_CONTENT_FOLDER);
		actBar.goToAddSymlinkTab();
		click(ecms.ELEMENT_ADD_ITEM);
		selectOption(ecms.ELEMENT_SYMLINK_WORKSPACE, "dms-system");
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "exo:ecm"));
		button.save();

		//check alert
		magAlert.verifyAlertMessage("Access denied! You do not have the permission to add symlink to this node.");
		info("cannot add symlink with user has not permission to view it");
		button.cancel();

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * case20: Check the displaying of node when user dose not permission to view it when add Symlink 
	 * login with john
	 * create new node - content folder
	 * set permission for this node: user mary does not have permission view node
	 * login with mary
	 * add symlink and choose target node
	 * check user does not see that content node
	 */
	@Test
	public void test20_CheckDisplayNodeWhenUserNotHaveViewPermission(){	  
		String DATA_CONTENT_FOLDER = "contentfolder20";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//create new content folder
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//set permission for this node: user mary does not have permission view node 
		info("Set for user mary does not have view permission");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToNodePermissionManagement();
		ecmsPer.removeDefaultPermissionOfNode();
		button.close();
		magAcc.signOut();

		//login with mary
		info("Login with mary");
		magAcc.signIn("mary", "gtn");
		navToolBar.goToSiteExplorer();

		//add symlink and choose target node
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);
		info("Add symlink for document");
		actBar.goToAcmeNodeInAddSymlink();

		//check user does not see that content node
		//usePaginator(ELEMENT_CONTENT_DIV, "cannot found content folder node");
		waitForElementNotPresent(ecms.ELEMENT_TARGET_NODE.replace("${node}", DATA_CONTENT_FOLDER));
		info("cannot see node when user does not have view permission");
		click(ecms.ELEMENT_ADD_SYMLINK_CLOSE_WINDOWS);

		//delete data
		info("Delete content folder");
		magAcc.signOut();
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * case21: Add new Symlink has the same name with existing Symlink in Content Folder
	 * create new content folder
	 * add 2 symlink have same name for content folder
	 * check add successfully
	 */
	@Test
	public void test21_Add2SymlinksHaveSameNameInContentFolder(){
		String DATA_CONTENT_FOLDER = "contentfolder21";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "addsymlink21[2]"));

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add 2 symlink have same name for content folder
		info("Add 2 symlinks have same name in content folder");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);

		actBar.addSymlink("collaboration", "sites/contentfolder21", "addsymlink21");
		waitForElementPresent(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "addsymlink21"));

		actBar.addSymlink("collaboration", "sites/contentfolder21", "addsymlink21");
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		info("Add 2 symlink same name in content folder successfully");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);		  
	}

	/**
	 * case22: Add new Symlink has the same name with existing Symlink in Document Folder
	 * create new document folder
	 * add 2 symlink have same name for document folder
	 * check cannot add
	 */
	@Test
	public void test22_Add2SymlinksHaveSameNameInDocumentFolder(){
		String DATA_DOCUMENT_FOLDER = "documentfolder22";
		By ELEMENT_DOCUMENT_FOLDER = By.linkText(DATA_DOCUMENT_FOLDER);

		//create new document folder
		navToolBar.goToSiteExplorer();
		info("Create new document folder with user john");
		cTemplate.createNewFolder(DATA_DOCUMENT_FOLDER, folderType.Document);
		assert isElementPresent(ELEMENT_DOCUMENT_FOLDER):"Create new document folder unsuccessfully";
		info("Create new document folder successfully");

		//add 2 symlink have same name for document folder
		info("Add 2 symlinks have same name in document folder");
		ecms.goToNode(ELEMENT_DOCUMENT_FOLDER);
		actBar.addSymlink("collaboration", "sites/documentfolder22", "addsymlink22");
		waitForElementPresent(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "addsymlink22"));	
		actBar.addSymlink("collaboration", "sites/documentfolder22", "addsymlink22");

		//check alert
		magAlert.verifyAlertMessage("The node name already exists.");
		button.cancel();
		info("cannot add 2 symlink same name in document folder");

		//delete data
		cMenu.deleteDocument(ELEMENT_DOCUMENT_FOLDER);		  
	}

	/**
	 * case23: Add new Symlink has the same name with existing Symlink in document  
	 * create new document - file document
	 * check cannot add 2 symlinks have samve name in document
	 */
	/*@Test
	public void test23_Add2SymlinksHaveSameNameInDocument(){
		String DATA_FILE = "filedocument23";
		By ELEMENT_FILE = By.linkText(DATA_FILE);
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", DATA_FILE));

		//create new file document
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();

		info("Create new file document with user john");
		cTemplate.createNewFile(DATA_FILE, DATA_FILE, DATA_FILE);
		assert isElementPresent(ELEMENT_FILE):"Create new file document unsuccessfully";
		info("Create new file document successfully");

		//add 2 symlink have same name for file document
		info("Add 2 symlinks have same name in file document");
		actBar.addSymlink("collaboration", "sites/filedocument23", DATA_FILE);

		//info("enable DMS");
		//checkPreferenceOption("enableStructure");
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);

		actBar.addSymlink("collaboration", "sites/filedocument23", DATA_FILE);

		//check alert
		magAlert.verifyAlertMessage("The node name already exists.");
		button.cancel();

		//delete data
		cMenu.deleteDocument(ELEMENT_FILE);
	}*/

	/**
	 * case24: Add new Symlink has the same name with existing Symlink in uploaded file
	 * upload new file
	 * check cannot add 2 symlinks have same name in uploaded file
	 */
	/*@Test
	public void test24_Add2SymlinksHaveSameNameInUploadFile(){
		String DATA_UPLOAD_FILE_LINK = "TestData/ECMS_DMS_SE_Addsymlink.jpg"; 
		By ELEMENT_UPLOAD_FILE = By.linkText("ECMS_DMS_SE_Addsymlink.jpg");
		By ELEMENT_DOCUMENT_SYMLINK =  By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "ECMS_DMS_SE_Addsymlink.jpg"));

		//upload new file
		navToolBar.goToSiteExplorer();

		info("Upload new file");
		ecms.uploadFile(DATA_UPLOAD_FILE_LINK);
		waitForTextPresent("ECMS_DMS_SE_Addsymlink.jpg");

		ecms.goToNode(ELEMENT_UPLOAD_FILE);
		assert isElementPresent(ELEMENT_UPLOAD_FILE):"Upload file unsuccessfully";
		info("Upload file successfully");

		//add 2 symlink have same name for uploaded file
		info("Add 2 symlinks have same name in upload file");
		//addSymlink(ELEMENT_SELECT_PATH_DOCUMENTS, DATA_SYMLINK);
		actBar.addSymlink("collaboration", "sites/ECMS_DMS_SE_Addsymlink.jpg", "");

		//checkPreferenceOption("enableStructure");
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		//addSymlink(ELEMENT_SELECT_PATH_DOCUMENTS,DATA_SYMLINK);
		actBar.addSymlink("collaboration", "sites/ECMS_DMS_SE_Addsymlink.jpg", "");

		//check alert
		magAlert.verifyAlertMessage("The node name already exists.");
		button.cancel();
		info("cannot add 2 symlink same name in uploaded file");

		//delete data
		cMenu.deleteData(ELEMENT_UPLOAD_FILE);
	}*/

	/**
	 * case25: Add Symlink for node when user does not have permission to add in this node
	 * create new node - content folder
	 * set for user mary does not have add node permission
	 * login with mary
	 * check cannot add symlink for node
	 */
	@Test
	public void test25_AddSymlinkForNodeWhenUserNotHasAddNodePermission(){
		String DATA_CONTENT_FOLDER = "contentfolder25";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER); 

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//set for user mary does not have add node permission 
		info("Set for user mary does not have add node permission");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToNodePermissionManagement();
		ecmsPer.removeDefaultPermissionOfNode();
		ecms.selectUser("mary");
		ecmsPer.setPermissionForNode(true, false, false);

		button.save();
		magAcc.signOut();

		//login with mary
		info("Login with mary");
		magAcc.signIn("mary", "gtn");
		navToolBar.goToSiteExplorer();

		//check cannot add symlink for node
		info("Check cannot add symlink for node");		
		actBar.addSymlink("collaboration", "sites/contentfolder25", "contentfolder25.lnk");	
		magAlert.verifyAlertMessage("Access denied! You do not have the permission to add symlink to this node.");
		button.cancel();

		info("Cannot add symlink for node because user does not have add node permission");
		magAcc.signOut();

		//delete data
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/*Case26: Add Symlink for node when node is locked by locker
	 * create new node - content folder
	 * lock node
	 * add symlink for node
	 * check add node successfully
	 */
	/*@Test
	public void test26_AddSymlinkForNodeIsLockedByLocker(){
		String DATA_CONTENT_FOLDER = "contentfolder26";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER); 
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER));

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//lock node
		info("Lock node");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, actionType.LOCK);
		assert cMenu.isLockedNode(ELEMENT_CONTENT_FOLDER): "Lock node unsuccessfully";

		//add symlink for node
		info("Add symlink for locked node");
		//addSymlink(ELEMENT_SELECT_PATH_DOCUMENTS,DATA_SYMLINK);
		actBar.addSymlink("collaboration", "sites/contentfolder26", "contentfolder26.lnk");

		//check add symlink successful
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		assert isElementPresent(ELEMENT_DOCUMENT_SYMLINK):"Add symlink for content folder unsuccessfully";
		info("Add symlink for content folder successfully");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}*/

	/**
	 * case27: Add Symlink for node when node is locked by user is not locker
	 * create new node with user John
	 * lock node by John
	 * login with mary
	 * check user mary can not add symlink for node
	 */
	@Test
	public void test27_AddSymlinkForNodeIsLockedByUserIsNotLocker(){
		String DATA_CONTENT_FOLDER = "contentfolder27";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER); 

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//lock node
		info("Lock node");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, actionType.LOCK);
		assert cMenu.isLockedNode(ELEMENT_CONTENT_FOLDER): "Lock node unsuccessfully";
		driver.close();

		//login with mary
		info("Init new session");
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);

		info("Login with Mary");
		magAcc.signIn("mary", "gtn");

		//check cannot add symlink for node by user mary
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		waitForElementNotPresent(ecms.ELEMENT_ADD_SYMLINK);

		rightClickOnElement(ELEMENT_CONTENT_FOLDER);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_ADD_SYMLINK);
		info("cannot add symlink for node by user is not locker");
		magAcc.signOut();

		//delete data
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);			
	}

	/**
	 * case28: Add Symlink for node to the node is being locked
	 * create new node - content folder
	 * lock this node
	 * go to acme/document to add symlink
	 * add symlink to locked node
	 * check add successful
	 */
	/*@Test(groups={"pending"})
	public void test28_AddSymlinkForNodeToLockedNode(){
		String DATA_CONTENT_FOLDER = "contentfolder28";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		By ELEMENT_CONTENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER));

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//lock node
		info("Lock node");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, actionType.LOCK);
		assert cMenu.isLockedNode(ELEMENT_CONTENT_FOLDER): "Lock node unsuccessfully";

		//go to acme/documents
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME_DOCUMENTS);

		//add symlink for node to locked content node
		info("Add symlink for documents to locked node");
		actBar.goToAcmeNodeInAddSymlink();
		//usePaginator(ELEMENT_CONTENT_FOLDER_DIV,"cannot choose target node");
		//click(ELEMENT_CONTENT_FOLDER_DIV);
		//click(ELEMENT_SAVE_BUTTON);

		//check add symlink successfully
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		waitForElementPresent(ELEMENT_CONTENT_SYMLINK);
		assert isElementPresent(ELEMENT_CONTENT_SYMLINK):"Add symlink unsuccessfully";
		info("Add symlink to locked node successfully");

		//delete data
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);	  
	}*/

	/**
	 * case29: Add Symlink for node when node is check in status
	 * create new content folder
	 * check in node
	 * check cannot add symlink for node
	 */
	@Test
	public void test29_AddSymlinkForCheckInNode(){
		String DATA_FILE = "ECMS_DMS_SE_Addsymlink_FILE_29";
		By ELEMENT_FILE = By.linkText(DATA_FILE);

		//create new article
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();

		info("Create new file document with user john");
		cTemplate.createNewFile(DATA_FILE, DATA_FILE, DATA_FILE);
		assert isElementPresent(ELEMENT_FILE):"Create new file document unsuccessfully";
		info("Create new file document successfully");

		//check in node
		info("Check in node");
		cMenu.contextMenuAction(ELEMENT_FILE, actionType.CHECKIN);

		//check cannot add symlink
		rightClickOnElement(ELEMENT_FILE);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_ADD_SYMLINK);
		ecms.goToNode(ELEMENT_FILE);
		waitForElementNotPresent(ecms.ELEMENT_ADD_SYMLINK);
		info("Cannot add symlink for checked in node");

		//delete data
		cMenu.contextMenuAction(ELEMENT_FILE, actionType.CHECKOUT);
		cMenu.deleteDocument(ELEMENT_FILE);
	}

	/*Case30: Add Symlink for node using drag and drop
	 * create new node - content folder
	 * create new document - article
	 * drag drop article to content folder
	 * check symlink of article in content folder
	 */
	//	  @Test
	//	  public void test30_AddSymlinkForNodeUsingDragDrop(){
	//		  String DATA_CONTENT_FOLDER = "ECMS_DMS_SE_Addsymlink_contentfolder_30";
	//		  By ELEMENT_CONTENT_FOLDER = By.xpath("//a[contains(@title,'"+DATA_CONTENT_FOLDER+"')]"); 
	//		  String DATA_ARTICLE = "ECMS_DMS_SE_Addsymlink_article_30";
	//		  By ELEMENT_ARTICLE = By.xpath("//a[@title='"+DATA_ARTICLE+" "+"']");
	//		  By ELEMENT_ARTICLE_DIV = By.xpath("//a[@title='"+DATA_ARTICLE+" "+"']");
	//		  
	//		  //create new content folder
	//		  navToolBar.goToSiteExplorer();
	//		  createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);
	//		  
	//		  //create new article document
	//		  goToAddNewContent();
	//		  createNewArticle(DATA_ARTICLE, DATA_ARTICLE, "", "");
	//		  waitForElementPresent(ELEMENT_ARTICLE);
	//		  assert isElementPresent(ELEMENT_ARTICLE):"Create new article unsuccessfully";
	//		  info("Create new article successfully");
	//		  
	//		  //drag and drop article to content folder
	//		  WebElement ELEMENT_ARTICLE_DOCUMENT = waitForAndGetElement(ELEMENT_ARTICLE);
	//		  WebElement ELEMENT_CONTENT = waitForAndGetElement(ELEMENT_CONTENT_FOLDER);
	//		  pause(1000);
	//		  actions.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).moveToElement(ELEMENT_ARTICLE_DOCUMENT).clickAndHold().moveToElement(ELEMENT_CONTENT).release().keyUp(Keys.SHIFT).keyUp(Keys.CONTROL).build().perform();
	//		  pause(1000);
	//		  
	//		  //check add symlink
	//		  goToNode(ELEMENT_CONTENT_FOLDER);
	//		  pause(5000);
	//		  waitForElementPresent(ELEMENT_ARTICLE_DIV);
	//		  assert isElementPresent(ELEMENT_ARTICLE_DIV):"Add symlink unsuccessfully";
	//		  info("Add symlink for node successfully");
	//		  
	//		  //delete data
	//		  deleteData(ELEMENT_CONTENT_FOLDER);
	//		  deleteData(ELEMENT_ARTICLE);
	//	  }

	/**
	 * Case31: Edit the name of added link node in “Symlink Name”
	 * create node - content folder
	 * add symlink with input name
	 */
	@Test
	public void test31_AddSymlinkWithInputName(){
		String DATA_CONTENT_FOLDER = "contentfolder31";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK_NAME = "addsymlink31";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_SYMLINK_NAME));

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink
		info("Add symlink for content folder with name is node default name");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToAcmeNodeInAddSymlink();
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		type(ecms.ELEMENT_SYMLINK_NAME, "addsymlink31.lnk", true);
		button.save();

		//check add successfully
		waitForElementPresent(ELEMENT_SYMLINK);
		assert isElementPresent(ELEMENT_SYMLINK): "cannot add symlink";

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * case32: View content of Symlink
	 * create new node - content folder
	 * add symlink for node with target node is folder: documents
	 * check can view all subnode of documents in document.lnk
	 */
	@Test
	public void test32_ViewContentOfSymlink(){
		String DATA_CONTENT_FOLDER = "contentfolder32";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "documents"));

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink with target node - documents for this content folder
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for node with target node is documents");
		actBar.goToAcmeNodeInAddSymlink();
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		button.save();

		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		doubleClickOnElement(ELEMENT_DOCUMENT_SYMLINK);

		//check subnode of symlink
		waitForElementPresent(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "conditions.doc")));
		assert isElementPresent(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "conditions.doc"))): "check subnode of element is not true";
		assert isElementPresent(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "metro.pdf"))): "check subnode of element is not true";
		assert isElementPresent(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "offices.jpg"))): "check subnode of element is not true";

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * case33: Remove Symlink of node
	 * create new node - content folder
	 * add symlink for node
	 * add path node -> remove path node
	 */
	@Test
	public void test33_RemoveSymlinkOfNode(){
		String DATA_CONTENT_FOLDER = "contentfolder33";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink for node
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToAcmeNodeInAddSymlink();
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		waitForElementPresent(ecms.ELEMENT_REMOVE_PATH_NODE);
		click(ecms.ELEMENT_REMOVE_PATH_NODE);

		//check after remove
		assert getValue(ecms.ELEMENT_SYMLINK_PATH_NODE).isEmpty(): "Path target node is not empty";
		assert getValue(ecms.ELEMENT_SYMLINK_NAME).isEmpty(): "Name symlink is not empty";
		button.cancel();

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * case34: Rename the node which is the target node
	 * create 2 new node - content folder 1, content folder 2
	 * add symlink for content folder 1 with target node is content folder 2
	 * rename content folder 2
	 * check symlink is not change name
	 */
	@Test
	public void test34_RenameNodeIsTargetNodeOfSymlink(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder341";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder342";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));

		By ELEMENT_CONTENT_FOLDER_2_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER_2));
		String DATA_CONTENT_FOLDER_2_NEW = "contentfolder342edited";
		By ELEMENT_CONTENT_FOLDER_2_NEW = By.linkText(DATA_CONTENT_FOLDER_2_NEW);

		//create new content folder 1
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2, ELEMENT_CONTENT_FOLDER_2);

		//add symlink for content folder 1 with target node = content folder 2
		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/contentfolder342", "contentfolder342.lnk");

		//check add successfully
		waitForElementPresent(ELEMENT_CONTENT_FOLDER_2_SYMLINK);
		assert isElementPresent(ELEMENT_CONTENT_FOLDER_2_SYMLINK): "Add symlink for content folder 1 unsuccessfully";
		info("Add symlink for content folder 1 successfully");

		//rename content folder 2
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_2);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER_2, actionType.RENAME, DATA_CONTENT_FOLDER_2_NEW);

		//check name of symlink is not change
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		waitForElementPresent(ELEMENT_CONTENT_FOLDER_2_SYMLINK);
		assert isElementPresent(ELEMENT_CONTENT_FOLDER_2_SYMLINK): "Symlink's name is changed";
		info("Symlink is kept old name");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2_NEW);		  
	}

	/**
	 * case35: Delete the node which is the target node
	 * create 2 content folder: 1 and 2
	 * add symlink for content folder 1 with target node = content folder 2
	 * delete folder 2
	 * check symlink is deleted too
	 */
	@Test
	public void test35_DeleteNodeWithIsTargetNodeOfSymlink(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder351";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder352";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));
		By ELEMENT_CONTENT_FOLDER_2_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER_2));

		//create new content folder 1
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2, ELEMENT_CONTENT_FOLDER_2);

		//add symlink for content folder 1 with target node = content folder 2
		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/contentfolder352", "contentfolder352.lnk");

		//check add successfully
		waitForElementPresent(ELEMENT_CONTENT_FOLDER_2_SYMLINK);
		assert isElementPresent(ELEMENT_CONTENT_FOLDER_2_SYMLINK):"Add symlink for content folder 1 unsuccessfully";
		info("Add symlink for content folder 1 successfully");

		//delete content folder 2
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER_2);

		//check symlink is deleted 
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		waitForElementNotPresent(ELEMENT_CONTENT_FOLDER_2_SYMLINK);
		info("Symlink is deleted too");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
	}

	/**
	 * case36: Rename Symlink node
	 * create new node - content folder
	 * add symlink for node
	 * rename symlink
	 */
	@Test
	public void test36_RenameSymlink(){
		String DATA_CONTENT_FOLDER = "contentfolder36";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "documents"));

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink for node
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for node");
		actBar.goToAcmeNodeInAddSymlink();
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		button.save();
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);

		//rename symlink
		cMenu.contextMenuAction(ELEMENT_DOCUMENT_SYMLINK, actionType.RENAME, "editname");
		waitForTextPresent("editname");
		waitForElementPresent(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "editname")); 

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);	  
	}

	/**
	 * case37: Delete Symlink node
	 * create new node - content folder
	 * add symlink for node
	 * delete symlink
	 */
	@Test
	public void test37_DeleteSymlinkOfNode(){
		String DATA_CONTENT_FOLDER = "contentfolder37";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "documents"));

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink for node
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for node");
		actBar.goToAcmeNodeInAddSymlink();
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		button.save();
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);

		//delete symlink
		cMenu.deleteData(ELEMENT_DOCUMENT_SYMLINK);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/**
	 * case38: Copy Symlink node to other node
	 * create 2 node: content folder 1 and 2
	 * create symlink for content folder 1
	 * copy symlink from content folder 1 to content folder 2
	 */
	@Test
	public void test38_CopySymlinkNodeToOtherNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder381";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder382";
		By ELEMENT_CONTENT_FOLDER_2 = By.linkText(DATA_CONTENT_FOLDER_2);		
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "documents"));

		//create new content folder 1
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2,ELEMENT_CONTENT_FOLDER_2);

		//create symlink for content folder 1
		info("Add symlink for content folder 1");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.goToAcmeNodeInAddSymlink();
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		button.save();
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		info("Add symlink successfully");

		//copy symlink from content folder 1 to content folder 2
		cMenu.copyAndPasteNode(ELEMENT_DOCUMENT_SYMLINK, ELEMENT_CONTENT_FOLDER_2);

		//check copy successfully
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_2);
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		assert isElementPresent(ELEMENT_DOCUMENT_SYMLINK): "Copy symlink unsuccessfully";
		info("Copy symlink successfully");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);		  
	}

	/**
	 * case39: Cut Symlink node to other node
	 * create 2 node: content folder 1 and 2
	 * create symlink for content folder 1
	 * cut symlink form content folder 1 to content folder 2 
	 */
	@Test
	public void test39_CutSymlinkNodeToOtherNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder391";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder392";
		By ELEMENT_CONTENT_FOLDER_2 = By.linkText(DATA_CONTENT_FOLDER_2);		
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "documents"));

		//create new content folder 1
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2,ELEMENT_CONTENT_FOLDER_2);

		//create symlink for content folder 1
		info("Add symlink for content folder 1");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.goToAcmeNodeInAddSymlink();
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		button.save();
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		info("Add symlink successfully");

		//cut symlink from content folder 1 to content folder 2
		cMenu.cutAndPasteNode(ELEMENT_DOCUMENT_SYMLINK, ELEMENT_CONTENT_FOLDER_2);

		//check cut successfully
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_2);
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		assert isElementPresent(ELEMENT_DOCUMENT_SYMLINK): "Copy symlink unsuccessfully";
		info("Copy symlink successfully");

		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		waitForElementNotPresent(ELEMENT_DOCUMENT_SYMLINK);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);
	}

	/**
	 * case40: Create child nodes in Symlink node
	 * create new node: content folder
	 * add symlink for node
	 * add content folder for symlink
	 * check add successfully
	 */
	@Test
	public void test40_CreateChildNodeInSymlinkNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder401";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder402";
		By ELEMENT_CONTENT_FOLDER_2 =  By.linkText(DATA_CONTENT_FOLDER_2);

		By ELEMENT_CONTENT_FOLDER_2_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER_2));
		String DATA_CONTENT_FOLDER_3 = "contentfolder403";
		By ELEMENT_CONTENT_FOLDER_3 = By.linkText(DATA_CONTENT_FOLDER_3);

		//create new content folder 1
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2,ELEMENT_CONTENT_FOLDER_2);

		//add symlink for content folder 1 with target node = content folder 2
		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/contentfolder402", "contentfolder402.lnk");

		//check add successfully
		waitForElementPresent(ELEMENT_CONTENT_FOLDER_2_SYMLINK);
		assert isElementPresent(ELEMENT_CONTENT_FOLDER_2_SYMLINK): "Add symlink for content folder 1 unsuccessfully";
		info("Add symlink for content folder 1 successfully");

		//add content folder for symlink
		doubleClickOnElement(ELEMENT_CONTENT_FOLDER_2_SYMLINK);
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_3, ELEMENT_CONTENT_FOLDER_3);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);
	}
}