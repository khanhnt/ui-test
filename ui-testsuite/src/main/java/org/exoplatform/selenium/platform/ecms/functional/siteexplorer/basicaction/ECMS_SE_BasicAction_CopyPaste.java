package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.BrowserPreferences;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author HaKT
 * @date: 24/09/2012
 */
public class ECMS_SE_BasicAction_CopyPaste extends PlatformBase {

	//Platform
	Button button;
	ManageAlert alt;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	EcmsBase ecms;
	EcmsPermission ecmsPer;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	BrowserPreferences bPre;
	SitesExplorer siteExp;

	public String SHOW_DMS_STRUCTURE="enableStructure";
	public String UPLOADED_FILE_PATH ="TestData/Winter.jpg";
	
	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		button = new Button(driver);
		alt = new ManageAlert(driver);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		ecmsPer = new EcmsPermission(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		bPre = new BrowserPreferences(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn("john","gtn");
	}

	@AfterMethod
	public void afterTest() {
		//logoutEcms ();
		driver.quit();
	}

	/* Go to Sites Management, stand at root path
	 * Create 1 Content folder
	 * Copy it
	 * Paste at root path
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test01_CopyFolderToRootPath()
	{ 
		String CONTENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Content_Folder_01";

		By VERIFY_AT_ROOT_PATH2=By.xpath(cMenu.ELEMENT_FILE_TITLE_AUX.replace("${title1}", CONTENT_FOLDER_NAME).replace("${title2}", CONTENT_FOLDER_NAME));
		By VERIFY_AT_ROOT_PATH1=By.xpath(cMenu.ELEMENT_FILE_TITLE_AUX.replace("${title1}", "acme").replace("${title2}", CONTENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Verify folder is created at root path");
		waitForElementPresent(VERIFY_AT_ROOT_PATH1);

		info("Copy content folder & paste at root path");
		cMenu.copyAndPasteNode(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME), siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		info("Verify copied folder is at root path");
		waitForElementPresent(VERIFY_AT_ROOT_PATH2);

		info("Delete data");
		ecms.goToNode(VERIFY_AT_ROOT_PATH2);
		cMenu.deleteData(VERIFY_AT_ROOT_PATH2);

		ecms.goToNode(By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME)));
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}",CONTENT_FOLDER_NAME));
	}

	/* Go to Sites Management, stand at root path
	 * Create 2 Content folder
	 * Copy folder 1
	 * Paste to folder 2
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test02_CopyContentFolderToContentFolder()
	{
		String CONTENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Content_Folder_02";
		By CONTENT_FOLDER_PATH=By.linkText(CONTENT_FOLDER_NAME);

		String CONTENT_FOLDER_NAME_01="ECMS_DMS_SE_BasicAction_CopyPaste_ContentFolder_021";
		By CONTENT_FOLDER_PATH_01=By.linkText(CONTENT_FOLDER_NAME_01);
		By CONTENT_FOLDER_PATH_01_NEW = By.xpath(cMenu.ELEMENT_VERIFY.replace("${destination}", CONTENT_FOLDER_NAME).replace("${source}", CONTENT_FOLDER_NAME_01));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create Content Folder 01");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME_01, folderType.Content);

		info("Copy content folder 1 to content folder");
		cMenu.copyAndPasteNode(CONTENT_FOLDER_PATH_01, CONTENT_FOLDER_PATH);

		info("Verify Content folder 01 is in content folder");
		ecms.goToNode(CONTENT_FOLDER_PATH);
		waitForElementPresent(CONTENT_FOLDER_PATH_01_NEW);

		info("Delete data");
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
		ecms.goToNode(CONTENT_FOLDER_NAME_01);
		cMenu.deleteDocument(CONTENT_FOLDER_PATH_01);
	}

	/* Go to Sites Management
	 * Create 1 Content folder, 1 document folder
	 * Copy content folder
	 * Paste to document folder
	 * Verify warning message
	 * Delete data
	 */
	@Test
	public void test03_CopyContentFolderToDocumentFolder()
	{
		String CONTENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Content_Folder_03";
		By CONTENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME));

		String DOCUMENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Document_Folder_03";
		By DOCUMENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME, folderType.Document);

		info("Copy content folder to document folder");
		cMenu.copyAndPasteNode(CONTENT_FOLDER_PATH, DOCUMENT_FOLDER_PATH);

		info("Verify warning message");
		waitForTextPresent(cMenu.WARNING_MESSAGE_CANNOT_PASTE);
		click(button.ELEMENT_OK_BUTTON);

		info("Delete data");
		ecms.goToNode(CONTENT_FOLDER_PATH);
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
		ecms.goToNode(DOCUMENT_FOLDER_PATH);
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH);
	}

	/* Go to Sites Management
	 * Create 1 Content folder, 1 article
	 * Copy content folder
	 * Paste to article
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test04_CopyContentFolderToDocument()
	{
		String ARTICLE_NAME_TITLE = "ECMS_DMS_SE_BasicAction_CopyPaste_Article_Document_04";
		By ARTICLE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", ARTICLE_NAME_TITLE));

		String CONTENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Content_Folder_04";
		By CONTENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME));
		By CONTENT_FOLDER_PATH_NEW = By.xpath(cMenu.ELEMENT_VERIFY.replace("${destination}", ARTICLE_NAME_TITLE).replace("${source}", CONTENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create 1 article");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(ARTICLE_NAME_TITLE, ARTICLE_NAME_TITLE, ARTICLE_NAME_TITLE);

		info("Copy folder to article");
		cMenu.copyAndPasteNode(CONTENT_FOLDER_PATH, ARTICLE_PATH);

		info("Check content folder in Article");
		ecms.goToNode(ARTICLE_PATH);
		bPre.setUpPreferenceOption(SHOW_DMS_STRUCTURE);
		waitForElementPresent(CONTENT_FOLDER_PATH_NEW);

		info("Delete data");
		cMenu.deleteDocument(ARTICLE_PATH);
		ecms.goToNode(CONTENT_FOLDER_PATH);
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
	}

	/* Go to Sites Management
	 * Create 1 Content folder, 1 document folder
	 * Copy document folder
	 * Paste to content folder
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test08_CopyDocumentFolderToContentFolder()
	{
		String CONTENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Content_Folder_08";
		By CONTENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME));

		String DOCUMENT_FOLDER_NAME ="ECMS_DMS_SE_BasicAction_CopyPaste_Document_Folder_08";
		By DOCUMENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME));
		By CONTENT_FOLDER_PATH_NEW = By.xpath(cMenu.ELEMENT_VERIFY.replace("${destination}", CONTENT_FOLDER_NAME).replace("${source}", DOCUMENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME, folderType.Document);

		info("Copy document folder to content folder");
		cMenu.copyAndPasteNode(DOCUMENT_FOLDER_PATH, CONTENT_FOLDER_PATH);

		info("Verify document folder is in content folder");
		waitForElementPresent(CONTENT_FOLDER_PATH);
		ecms.goToNode(CONTENT_FOLDER_PATH);
		waitForElementPresent(CONTENT_FOLDER_PATH_NEW);

		info("Delete data");
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
		ecms.goToNode(DOCUMENT_FOLDER_PATH);
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH);
	}

	/* Go to Sites Management
	 * Create 2 document folders
	 * Copy document folder 2 to document folder 1
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test09_CopyDocumentFolderToDocumentFolder()
	{
		String DOCUMENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Document_Folder_09";
		By DOCUMENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME));

		String DOCUMENT_FOLDER_NAME_01="ECMS_DMS_SE_BasicAction_CopyPaste_Document_Folder_091";
		By DOCUMENT_FOLDER_PATH_01=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME_01));
		By DOCUMENT_FOLDER_PATH_NEW = By.xpath(cMenu.ELEMENT_VERIFY.replace("${destination}", DOCUMENT_FOLDER_NAME_01).replace("${source}", DOCUMENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME, folderType.Document);

		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME_01, folderType.Document);

		info("Copy document folder to document folder 1");
		cMenu.copyAndPasteNode(DOCUMENT_FOLDER_PATH, DOCUMENT_FOLDER_PATH_01);

		info("Verify document folder is pasted");
		waitForElementPresent(DOCUMENT_FOLDER_PATH_01);
		ecms.goToNode(DOCUMENT_FOLDER_PATH_01);
		waitForElementPresent(DOCUMENT_FOLDER_PATH_NEW);

		info("Delete data");
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH_01);
		ecms.goToNode(DOCUMENT_FOLDER_PATH);
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH);
	}

	/*case17: Copy folder and paste into locked node by user is not locker
	 * login with John: create new node (folder/document/upload file). Lock node
	 * login with mary: create new node. Check can not copy this node to locked node
	 */
	@Test
	public void test17_CopyFolderAndPasteIntoLockedNodeByUserIsNotLock(){		
		String DOCUMENT_FOLDER_NAME_1 = "ecmscopypastefolder171";
		By DOCUMENT_FOLDER_PATH_1 = By.linkText(DOCUMENT_FOLDER_NAME_1);

		String DOCUMENT_FOLDER_NAME_2 = "ecmscopypastefolder172";
		By DOCUMENT_FOLDER_PATH_2 = By.linkText(DOCUMENT_FOLDER_NAME_2);

		//create new document folder
		navToolBar.goToSiteExplorer();
		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME_1, folderType.Document);
		waitForElementPresent(DOCUMENT_FOLDER_PATH_1);

		//lock node
		ecms.goToNode(DOCUMENT_FOLDER_PATH_1);
		cMenu.contextMenuAction(DOCUMENT_FOLDER_PATH_1, actionType.LOCK);

		//check lock node
		assert cMenu.isLockedNode(DOCUMENT_FOLDER_PATH_1):"Lock node unsuccessfully";
		driver.close();

		//login with mary
		info("login with mary");
		initSeleniumTest();
		driver.get(baseUrl);
		alt = new ManageAlert(driver);
		ecms = new EcmsBase(driver);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		cMenu = new ContextMenu(driver);
		cTemplate = new ContentTemplate(driver);
		magAcc.signIn("mary", "gtn");
		navToolBar.goToSiteExplorer();

		//create new document folder
		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME_2, folderType.Document);
		waitForElementPresent(DOCUMENT_FOLDER_PATH_2);

		//Check can not copy this node to locked node
		cMenu.copyAndPasteNode(DOCUMENT_FOLDER_PATH_2, DOCUMENT_FOLDER_PATH_1);
		alt.verifyAlertMessage("The item with the path collaboration:/sites/"+ DOCUMENT_FOLDER_NAME_1 + " or upper level is locked.");
		info("Can not copy a node to locked node by user is not locker");
		cMenu.deleteData(DOCUMENT_FOLDER_PATH_2);
		magAcc.signOut();

		//delete data with john
		magAcc.signIn("john", "gtn");
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(DOCUMENT_FOLDER_PATH_1);
	}

	/* Go to Sites Management
	 * Create document folder
	 * Copy it
	 * Create content folder then set James does not have add node right
	 * Paste document folder to this
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test18_CopyFolderPasteIntoNodeUserNotHaveAddNodeRight()
	{
		String DOCUMENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Document_Folder_18";
		By DOCUMENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME));

		String CONTENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Content_Folder_18";
		By CONTENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();
		
		actBar.addViewPermissionToActionBar();

		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME, folderType.Document);

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);
		
		ecms.goToNode(CONTENT_FOLDER_PATH);

		info("Click Permission");
		click(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		click(ecms.ELEMENT_PERMISSION_LINK);

		info("Delete permisson of all except John");
		ecmsPer.removeDefaultPermissionOfNode();

		info("Choose James");
		ecms.selectUser("james");

		info("Choose all rights accept Add Node");
		ecmsPer.setPermissionForNode(true, false, true);

		info("Save then close");
		click(button.ELEMENT_SAVE_BUTTON);
		click(button.ELEMENT_CLOSE_BUTTON);

		info("Log out, log in as james, go to CE");
		magAcc.signOut();
		magAcc.signIn("james","gtn");
		navToolBar.goToSiteExplorer();		

		info("Copy document folder to content folder");
		cMenu.copyAndPasteNode(DOCUMENT_FOLDER_PATH, CONTENT_FOLDER_PATH);

		info("Verify cannot paste");
		waitForTextPresent(ecmsPer.MESSAGE_NO_RIGHT_TO_PASTE_NODE);
		click(button.ELEMENT_OK_BUTTON);

		info("Log out, log in as john to delete data");
		magAcc.signOut();
		magAcc.signIn("john","gtn");
		navToolBar.goToSiteExplorer();	
		ecms.goToNode(CONTENT_FOLDER_PATH);
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
		ecms.goToNode(DOCUMENT_FOLDER_PATH);
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH);
	}

	/* Go to Sites Management, stand at root path
	 * Create 1 Document folder
	 * Copy it
	 * Paste at root path
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test20_CopyDocumentFolderToRootPath()
	{ 
		String DOCUMENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Document_Folder_20";
		By DOCUMENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME));
		By VERIFY_AT_ROOT_PATH2=By.xpath(cMenu.ELEMENT_FILE_TITLE_AUX.replace("${title1}", DOCUMENT_FOLDER_NAME).replace("${title2}", DOCUMENT_FOLDER_NAME));
		By VERIFY_AT_ROOT_PATH1=By.xpath(cMenu.ELEMENT_FILE_TITLE_AUX.replace("${title1}", "acme").replace("${title2}", DOCUMENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Document Folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME, folderType.Document);

		info("Verify folder is created at root path");
		waitForElementPresent(VERIFY_AT_ROOT_PATH1);

		info("Copy & paste at root path");
		cMenu.copyAndPasteNode(DOCUMENT_FOLDER_PATH, siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		info("Verify copied folder is at root path");
		waitForElementPresent(VERIFY_AT_ROOT_PATH2);

		info("Delete data");
		ecms.goToNode(VERIFY_AT_ROOT_PATH2);
		cMenu.deleteDocument(VERIFY_AT_ROOT_PATH2);

		ecms.goToNode(DOCUMENT_FOLDER_PATH);
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH);
	}
	
	/* Go to Sites Management
	 * Create 1 content folder
	 * Create 1 file, Copy it
	 * Paste in content folder
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test31_CopyFileToContentFolder()
	{ 
		String CONTENT_FOLDER_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Content_Folder_31";
		By CONTENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME));
		String FILE_NAME_TITLE="ECMS_DMS_SE_BasicAction_CopyPaste_File_Document_31";
		String FILE_NAME_CONTENT="Content of File";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", FILE_NAME_TITLE));
		By FILE_PATH_NEW = By.xpath(cMenu.ELEMENT_VERIFY.replace("${destination}", CONTENT_FOLDER_NAME).replace("${source}", FILE_NAME_TITLE));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create File & copy paste to content folder");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME_TITLE, FILE_NAME_CONTENT, FILE_NAME_TITLE);
		cMenu.copyAndPasteNode(FILE_PATH, CONTENT_FOLDER_PATH);

		info("Verify result");
		ecms.goToNode(CONTENT_FOLDER_PATH);
		waitForElementPresent(FILE_PATH_NEW);

		info("Delete data");
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
		ecms.goToNode(FILE_PATH);
		cMenu.deleteDocument(FILE_PATH);
	}

	/* Go to Sites Management
	 * Create 1 Article
	 * Create 1 file, Copy it
	 * Paste in Article
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test32_CopyFileToOtherDocument()
	{ 
		String ARTICLE_NAME_TITLE="ECMS_DMS_SE_BasicAction_CopyPaste_Article_Document_32";
		String ARTICLE_CONTENT="Content of article 32";
		By ARTICLE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", ARTICLE_NAME_TITLE));

		String FILE_NAME_TITLE="ECMS_DMS_SE_BasicAction_CopyPaste_File_Document_32";
		String FILE_NAME_CONTENT="Content of File";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", FILE_NAME_TITLE));
		By FILE_PATH_NEW = By.xpath(cMenu.ELEMENT_VERIFY.replace("${destination}", ARTICLE_NAME_TITLE).replace("${source}", FILE_NAME_TITLE));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create a new File");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME_TITLE, FILE_NAME_CONTENT, FILE_NAME_TITLE);

		info("Choose Site Management");
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		info("Create a new Announcement");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(ARTICLE_NAME_TITLE, ARTICLE_CONTENT);

		info("Copy-paste");
		cMenu.copyAndPasteNode(FILE_PATH, ARTICLE_PATH);

		info("Verify");
		ecms.goToNode(ARTICLE_PATH);		
		bPre.setUpPreferenceOption(SHOW_DMS_STRUCTURE);
		waitForElementPresent(FILE_PATH_NEW);

		info("Delete data");
		ecms.goToNode(ARTICLE_PATH);
		cMenu.deleteDocument(ARTICLE_PATH);
		ecms.goToNode(FILE_PATH);
		cMenu.deleteDocument(FILE_PATH);
	}

	/* Go to Sites Management
	 * Create Upload a file
	 * Create 1 file, Copy it
	 * Paste in uploaded file
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test33_CopyFileToUploadedFile()
	{ 
		//String UPLOADED_FILE_NAME="ECMS_DMS_SE_BasicAction_CopyPaste_Uploaded_Image";
		By UPLOADED_FILE_XPATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "Winter.jpg"));

		String FILE_NAME_TITLE="ECMS_DMS_SE_BasicAction_CopyPaste_File_Document_33";
		String FILE_NAME_CONTENT="Content of File";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", FILE_NAME_TITLE));
		By FILE_PATH_NEW = By.xpath(cMenu.ELEMENT_VERIFY.replace("${destination}", "Winter.jpg").replace("${source}", FILE_NAME_TITLE));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Upload a File");
		ecms.uploadFile(UPLOADED_FILE_PATH);

		info("Create File");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME_TITLE, FILE_NAME_CONTENT, FILE_NAME_TITLE);
		
		info("Copy paste");
		cMenu.copyAndPasteNode(FILE_PATH, UPLOADED_FILE_XPATH);

		info("Verify result");
		ecms.goToNode(UPLOADED_FILE_XPATH);
		bPre.setUpPreferenceOption(SHOW_DMS_STRUCTURE);
		waitForElementPresent(FILE_PATH_NEW);

		info("Delete data");
		ecms.goToNode(UPLOADED_FILE_XPATH);
		cMenu.deleteDocument(UPLOADED_FILE_XPATH);
		ecms.goToNode(FILE_PATH);
		cMenu.deleteDocument(FILE_PATH);
	}			
}