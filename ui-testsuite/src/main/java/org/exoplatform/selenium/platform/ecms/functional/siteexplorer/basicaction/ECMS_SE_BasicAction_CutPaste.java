package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.admin.ManageView;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.BrowserPreferences;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import static org.exoplatform.selenium.TestLogger.*;

/*
 * @author: Thuntn
 * @date: 08/10/2012
 */
public class ECMS_SE_BasicAction_CutPaste extends PlatformBase {
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
	ManageView magView;
	SitesExplorer siteExp;

	public String USER = "john";
	public String PASS = "gtn";

	@BeforeMethod
	public void beforeMethod() {
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
		magView = new ManageView(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn("john","gtn");
		navToolBar.goToSiteExplorer();
	}

	//cut a content folder and paste it to another node
	@Test(groups={"ecms"})
	public void test01_CutContentFolderPasteInOtherNode() {
		String title = "ECMS_DMS_SE_BasicAction_CutPaste_01";
		By bCont = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title)); 

		info("Cut a content folder and paste it to another node!");
		//create a content folder 
		cTemplate.createNewFolder(title, folderType.Content);
		
		//cut and paste content folder to acme folder
		cMenu.cutAndPasteNode(bCont, ecms.ELEMENT_SIDEBAR_ACME);

		//verify if paste the content folder successfully in acme folder 
		waitForElementNotPresent(bCont);
		ecms.goToNode("acme");
		waitForElementPresent(bCont);

		//delete data
		cMenu.deleteDocument(bCont);
	}

	//cut a document folder and paste it to Document folder
	@Test
	public void test02_CutDocumentFolderPasteInDocumentFolder() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_02";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_02_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a document folder and paste it to another node!");
		//create a document folder - source
		cTemplate.createNewFolder(title, folderType.Document);

		//create a document folder -destination
		cTemplate.createNewFolder(titleDes, folderType.Document);

		//cut and paste the document folder to acme folder
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste document folder successfully in destination folder 
		ecms.goToNode(bDocDes);
		waitForElementPresent(bDocDes);

		//delete data
		cMenu.deleteDocument(bDocDes);
	}

	//cut a document folder and paste it to another node
	@Test
	public void test02_CutDocumentFolderPasteInContentFolder() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_02";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_02_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a document folder and paste it to another node!");
		//create a document folder - source
		cTemplate.createNewFolder(title, folderType.Document);

		//create a document folder -destination
		cTemplate.createNewFolder(titleDes, folderType.Content);

		//cut and paste the document folder to acme folder
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste document folder successfully in destination folder 
		ecms.goToNode(bDocDes);
		waitForElementPresent(bDocDes);

		//delete data
		cMenu.deleteDocument(bDocDes);
	}

	//Cut a document folder and paste it to file document!
	/*@Test
	public void test04_CutDocumentFolderPasteIntoFileDocument() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_04";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_04_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a document folder and paste it to file document!");
		//create a document folder - source
		cTemplate.createNewFolder(title, folderType.Document);

		//create a file document -destination
		actBar.goToAddNewContent();
		cTemplate.createNewFile(titleDes, titleDes, titleDes);

		//cut and paste the document folder to file document
		cMenu.cutAndPasteNode(bDoc, bDocDes);
		
		//verify if paste document folder successfully in a file document
		waitForElementNotPresent(bDoc);
		bPre.setUpPreferenceOption("enableStructure");
		ecms.goToNode(bDocDes);
		ecms.goToNode(bDoc);
		waitForElementPresent(By.xpath("//input[contains(@value,'"+titleDes+ "/" + title + "')]"));
		
		//delete data
		cMenu.deleteDocument(bDocDes);
	}*/

	//Cut a document folder and paste it to uploaded file!
	/*@Test
	public void test04_CutDocumentFolderPasteToUploadedFile() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_04";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_04_des.zip";
		String img="TestData/ECMS_DMS_SE_BasicAction_CutPaste_04_des.zip";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", img));

		info("Cut a document folder and paste it to uploaded file!");
		//create a document folder - source
		cTemplate.createNewFolder(title, folderType.Document);

		//create an uploaded file -destination
		ecms.uploadFile(img);
		waitForElementPresent(bDocDes);

		//cut and paste the document folder to uploaded file
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste document folder successfully in an uploaded file
		waitForElementNotPresent(bDoc);
		bPre.setUpPreferenceOption("enableStructure");
		ecms.goToNode(bDocDes);
		ecms.goToNode(bDoc);
		waitForElementPresent(By.xpath("//*[contains(@value,'"+ titleDes + "/" + title + "')]"));

		//delete data
		cMenu.deleteDocument(bDocDes);
	}*/

	//Cut a file document and paste it to a node!
	@Test
	public void test09_CutFilePasteToANode() {
		String title="ecmsbasicactio09";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ecmsbasicactio09des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a file document and paste it to a node!");
		//create a content folder -destination
		cTemplate.createNewFolder(titleDes, folderType.Content);

		//create a file document - source
		actBar.goToAddNewContent();
		cTemplate.createNewFile(title, title, title);

		//cut and paste the file document to a node
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste file document in a node
		waitForElementNotPresent(bDoc);
		ecms.goToNode(bDocDes);
		ecms.goToNode(bDoc);
		waitForElementPresent(By.xpath("//*[contains(@value,'"+ titleDes + "/" + title + "')]"));

		//delete data
		cMenu.deleteDocument(bDocDes);
	}

	//Cut an uploaded file and paste it to a Node
	@Test
	public void test20_CutUploadedFilePasteToANode() {
		String img = "TestData/ECMS_DMS_SE_BasicAction_CutPaste.png";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "ECMS_DMS_SE_BasicAction_CutPaste.png"));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_20_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));
		
		info("Cut an uploaded file and paste it to an article!");

		//upload an uploaded file - source
		ecms.uploadFile(img);
		waitForElementPresent(bDoc);

		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		//create a node -destination
		//actBar.goToAddNewContent();
		cTemplate.createNewFolder(titleDes, folderType.Content);

		//cut and paste the uploaded file to an article
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste uploaded file in an article
		waitForElementNotPresent(bDoc);
		bPre.setUpPreferenceOption("enableStructure");
		ecms.goToNode(titleDes);
		waitForElementPresent(bDoc);

		//delete data
		cMenu.deleteDocument(bDocDes);
	}

	//Cut a checked in node and paste it to a content folder!
	@Test
	public void test21_CutCheckInNodePasteToContentFolder() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_21";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_21_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a checkin node and paste it to a content folder!");

		//create a file document
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(title, title);
		cMenu.contextMenuAction(bDoc, actionType.CHECKIN);

		//add icon Version to action bar
		magView.setup2ShowViewAction("manageVersions");
		navToolBar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_SITES_MANAGEMENT_DRIVE);
		actBar.addVersionForNode(bDoc, "version file");

		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		//create a content folder -destination
		cTemplate.createNewFolder(titleDes, folderType.Content);

		//cut and paste the uploaded file to an article
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste the uploaded file in an article
		waitForElementNotPresent(bDoc);
		ecms.goToNode(titleDes);
		waitForElementPresent(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));

		//delete data
		cMenu.deleteDocument(bDocDes);
	}	

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}
}
