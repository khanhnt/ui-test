package org.exoplatform.selenium.platform.ecms;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ecms.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.ContextMenu;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class EcmsBase extends PlatformBase {
	
	public EcmsBase(WebDriver dr) {
		driver = dr;
		// TODO Auto-generated constructor stub
	}

	ContentTemplate contentTemp = new ContentTemplate(driver);
	ContextMenu contMenu = new ContextMenu(driver);
	ManageAccount account = new ManageAccount(driver);
	/*
	 * Portal Acme - http://localhost:8080/portal/acme
	 * */
	public final By ELEMENT_LOGIN_LINK = By.xpath("//*[@id='AcmeWebSiteLogInLogOut']");

	//Sign-in form
	public final By ELEMENT_LOGIN_BUTTON = By.xpath("//*[@id='UIPortalLoginFormAction']");

	//New Folder
	public final By ELEMENT_NEW_FOLDER_LINK = By.linkText("New Folder");
	public final By ELEMENT_FOLDER_TITLE_TEXTBOX = By.id("title");
	public final By ELEMENT_FOLDER_NAME_TEXTBOX = By.id("name");
	public final By ELEMENT_FOLDER_TYPE_OPTION = By.id("type");
	public final String ELEMENT_CONTENT_FOLDER_TYPE = "nt:unstructured";
	public final String ELEMENT_DOCUMENT_FOLDER_TYPE = "nt:folder";

	//Add new Page
	public final By ELEMENT_NEWPAGE_NAME_TEXTBOX = By.id("pageName");	
	public final By ELEMENT_NEWPAGE_LAYOUT_OPTION = By.xpath("//div[@class='DropDownSelectLabel']") ;

	//Page Creation Wizard -> Page Configs
	public final By ELEMENT_NEWPAGE_LAYOUT_COLUMN_PAGE_OPTION = By.linkText("Column Page Configs") ;
	public final By ELEMENT_NEWPAGE_LAYOUT_ROW_PAGE_OPTION = By.linkText("Row Page Configs");
	public final By ELEMENT_NEWPAGE_LAYOUT_TAB_PAGE_OPTION = By.linkText("Tabs Page Config");
	public final By ELEMENT_NEWPAGE_LAYOUT_MIX_PAGE_OPTION = By.linkText("Mix Page Configs");
	public final By ELEMENT_NEWPAGE_LAYOUT_DEFAULT_OPTION = By.linkText("Page Configs");

	public final By ELEMENT_ADD_CONTENT_DETAIL_PORTLET = By.xpath("//div[contains(text(),'Content Detail')]");
	public final By ELEMENT_DROP_TARGET_NO_LAYOUT = By.xpath("//div[@id='UIPage']");
	public final By ELEMENT_DROP_TARGET_HAS_LAYOUT = By.xpath("//div[@class='UIRowContainer EmptyContainer']");
	public final By ELEMENT_ADD_CONTENT_LIST_PORTLET = By.xpath("//div[text()='Content List']");
	public final By ELEMENT_FRAME_CONTAIN_PORTLET = By.xpath("//div[contains(@id,'UIPortlet')]");
	public final By ELEMENT_SELECT_CONTENT_PATH_LINK = By.xpath("//img[@class='AddIcon16x16 SelectFolderPathIcon']");

	public final By ELEMENT_CONTENTS_BY_QUERY_PORTLET = By.xpath("//div[contains(text(),'Content By Query')]");
	public final By ELEMENT_BY_QUERY_TEXTAREA = By.xpath("//textarea[@id='UICLVConfigContentByQueryTextArea']");
	public final By ELEMENT_WORKSPACE_SELECT = By.xpath("//select[@id='UICLVConfigWorkspaceFormSelectBox']");
	public final By ELEMENT_ACME_CATEGORY = By.xpath("//*[@id='ListRecords']/thead/tr[2]/td/a");
	public final By ELEMENT_FLIGHT = By.xpath("//a[@title='Flight']");
	public final By ELEMENT_SELECT_BY_CONTENT_PATH = By.xpath("//input[@id='UICLVConfigDisplayModeFormRadioBoxInput_ManualViewerMode']");
	public final By ELEMENT_BLOCK_LAYOUT = By.xpath("//div[@class='LAYOUT-BLOCK LAYOUT-PORTLET']");
	public final By ELEMENT_PAGE_EDIT_ABORT = By.xpath("//a[@title='Abort']");

	public final By ELEMENT_SELECT_CONTENT_PATH = By.xpath("//a[@title='offices.jpg']");
	public final By ELEMENT_ADD_TARGET = By.xpath("//img[@class='AddIcon16x16 SelectTargetPageIcon']");
	public final By ELEMENT_HEADER_PORTLET = By.id("UICLVConfigHeaderFormStringInput");
	public final By ELEMENT_TEMPLATE_PORTLET = By.id("UICLVConfigDisplayTemplateFormSelectBox");
	public final By ELEMENT_ADVANCE_PORTLET = By.linkText("Advanced");
	public final By ELEMENT_NEW_TARGET_PATH = By.xpath("//div[text()='news']/../../td/a/div[@class='Select16x16Icon']");
	public final By ELEMENT_CONTENT_BYURL_PORTLET = By.xpath("//div[text()='Content by URL']");
	public final By ELEMENT_NEWS_PORTLET = By.xpath("//div[text()='News']/../../../../../..");
	public final By ELEMENT_NEW_EDIT_PORTLET = By.xpath("//div[text()='News']/../a[@class='EditIcon']");
	public final By ELEMENT_HOMEPATH_ROOT = By.xpath("//div[@class='BreadcumbsPortlet']/div[2]/div[1]/a");
	public final By ELEMENT_FOLDER_BROWSER = By.xpath("//div[contains(text(),'Folder Browser')]");

	//-------------News page form---------------------------------------
	public final By ELEMENT_CATEGORY_CONTAINER = By.xpath("//div[text()='Browse by']");
	public final By ELEMENT_CATEGORY_PREFER = By.xpath("//div[text()='Browse by']/../../../*//a[@title='Preferences']");
	public final By ELEMENT_CATEGORY_ACME = By.xpath("//div[text()='Browse by']/../../*//a[text()='acme']");
	public final By ELEMENT_CATEGORY_DEFENCE = By.xpath("//div[text()='Browse by']/../../*//a[text()='Defense']");
	public final By ELEMENT_CATEGORY_VISION = By.xpath("//h5/a[text()='Defense']/../../*//a[text()='Vision']");
	public final By ELEMENT_CATEGORY_VISIBILITY = By.xpath("//h5/a[text()='Defense']/../../*//a[text()='Invisibility']");
	public final By ELEMENT_CATEGORY_HEALING = By.xpath("//h5/a[text()='Defense']/../../*//a[text()='Healing']");
	public final By ELEMENT_CATEGORY_IMMUNITY = By.xpath("//h5/a[text()='Defense']/../../*//a[text()='Immunity']");
	public final By ELEMENT_CATEGORY_MOVEMENT = By.xpath("//div[text()='Browse by']/../*//a[text()='Movement']");
	public final By ELEMENT_CATEGORY_NATURAL = By.xpath("//div[text()='Browse by']/../*//a[text()='Natural Elements']");
	public final By ELEMENT_UPLEVEL = By.xpath("//a[@class='LevelUpArrowIcon']");
	public final By ELEMENT_PCLV_CONTAINER = By.xpath("//div[text()='Top News']");
	public final By ELEMENT_PCLV_PREFER = By.xpath("//div[text()='Top News']/../../../../../*//a[@title='Preferences']");
	public final By ELEMENT_ORDER_BY = By.id("UICLVConfigOrderByFormSelectBox");
	public final By ELEMENT_ASCE_RADIO = By.id("UICLVConfigOrderTypeFormRadioBoxInput_ASC");
	public final By ELEMENT_DESC_RADIO = By.id("UICLVConfigOrderTypeFormRadioBoxInput_DESC");
	//--------------End News page form---------------------------------------------

	//Locator of SetPermission
	public final By ELEMENT_SELECT_USER = By.xpath("//img[@alt='Select User']");
	public final By ELEMENT_SEARCH_TEXTBOX = By.id("QuickSearch");
	public final By ELEMENT_SEARCH_LINK = By.xpath("//a[@class='SearchIcon' and @title='Quick Search']");
	public final By ELEMENT_SEARCH_CHOOSE = By.xpath("//img[@class='SelectPageIcon']");
	public final By ELEMENT_READ_CHECKBOX = By.id("read");
	public final By ELEMENT_ADD_NODE_CHECKBOX = By.id("add_node");
	public final By ELEMENT_DELETE_PERMISSION = By.xpath("//*[@id='PermissionInfo']/table/tbody/tr[4]/td[6]/div/img[2]");

	//DMS Administration - Simple View - Add Category Form
	public final By ELEMENT_BUTTON_ADD_CATEGORY = By.linkText("Add Category");
	public final By ELEMENT_ADD_CATEGORY_FORM = By.xpath("//span[text()='Add Category']");
	public final By ELEMENT_INPUT_CATEGORY_NAME = By.id("name");

	//For symlink
	public final By ELEMENT_ALERT = By.xpath("//div[@class='UIPopupWindow UIDragObject ExoMessageDecorator' and contains(@style, 'visible')]");
	public final By ELEMENT_MESSAGE = By.xpath("//span[@class='PopupIcon WarningMessageIcon']");
	public final By ELEMENT_ADD_SYMLINK_POPUP = By.id("UIPopupWindow");
	public final By ELEMENT_PATH_NODE = By.id("pathNode0");
	public final By ELEMENT_SYMLINK_NAME = By.id("symLinkName");
	public final By ELEMENT_SYMLINK_WORKSPACE = By.id("workspaceName");
	public final By ELEMENT_SITE_CONTENT = By.xpath("//div[@title='sites content']");
	public final By ELEMENT_LIVE_DIV = By.xpath("//div[@title='live']");
	public By ELEMENT_ADD_SYMLINK = By.linkText("Add Symlink");
	public final By ELEMENT_INFO = By.xpath("//span[@class='PopupIcon InfoMessageIcon']");

	//Rename Form in Sites Explorer (Right-click -> Rename)
	public final By ELEMENT_INPUT_TITLE_NODE = By.xpath("//input[@id = 'titleField']");
	public final By ELEMENT_INPUT_NAME_NODE = By.xpath("//input[@id = 'nameField']");

	/* Default Data (Document and folder like: acme, Document,....) */
	//Sidebar - Tree node
	public final By ELEMENT_SIDEBAR_ACME = By.xpath("//a[@title='acme ']");
	public final By ELEMENT_SIDEBAR_ACME_WEB_CONTENT = By.xpath("//a[@title='web contents ']");
	public final By ELEMENT_SIDEBAR_ACME_DOCUMENTS = By.linkText("documents");
	public final By ELEMENT_COLLABORATION_DRIVE_LIVE = By.xpath("//a[@title='Live ']");

	//View Area
	public final By ELEMENT_VIEWAREA_ACME = By.xpath("//div[@title='acme']");

	//Action bar > publication TAB
	public By ELEMENT_ADD_ITEM	 = By.xpath("//img[@title='Add Item']");
	
	//Content template
	public final By ELEMENT_EDIT_NODE_CHECKBOX = By.id("set_property");
	public final By ELEMENT_REMOVE_NODE_CHECKBOX = By.id("remove");
	public final By ELEMENT_UPLOAD_ID = By.id("file");
	public final By ELEMENT_UPLOAD_FRAME_EDIT = By.xpath("//iframe[contains(@id,'uploadFrame')]");
	public final By ELEMENT_UPLOAD_REMOVE = By.xpath("//img[@alt='Remove Item' and @class='ActionIcon Remove16x16Icon']");
	public final By ELEMENT_PIC_FILE_REMOVE = By.xpath("//img[@class='ActionIcon Remove16x16Icon']");
	
	////////////////////////////////
	//Log-in ECMS
	public void loginEcms(String username, String password) {
		driver.manage().window().maximize();
		click(ELEMENT_GO_TO_ACME);
		click(ELEMENT_LOGIN_LINK);
		pause(500);
		type(By.name("username"),username, false);
		pause(500);
		type(By.name("password"),password, false);
		click(ELEMENT_LOGIN_BUTTON);	
	}

	//Log-out ECMS
	public void logoutEcms (){
		//	mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		//	mouseOver(ELEMENT_SIGN_OUT_LINK, true);
		//click(ELEMENT_SIGN_OUT_LINK);
		account.signOut();
		driver.get(baseUrl);
	}

	// Go to content administration
	public void goToContentAdministration()
	{
		for(;;){
			mouseOver(ELEMENT_LINK_SETUP, true);
			pause(500);
			if (waitForAndGetElement(ELEMENT_MENU_CONTENT_LINK,5000,0)!=null) {	
				mouseOver(ELEMENT_MENU_CONTENT_LINK, true);
				if (waitForAndGetElement(ELEMENT_LINK_CONTENT_ADMIN,5000,0)!=null){
					click(ELEMENT_LINK_CONTENT_ADMIN);
					break;
				}
			}
		}
	}

	//Enter Sites Management Form 
	public void goToSiteExplorer(){
		mouseOver(ELEMENT_LINK_SETUP,true);
		mouseOver(ELEMENT_MENU_CONTENT_LINK,true);
		click(ELEMENT_MENU_SITE_EXPLORER);
	}

	//go to a node
	//input: path: path of a node, split by  "/" character 
	public void goToNodeByPath(String path)
	{
		String[] nodes = path.split("/");
		for (String node: nodes)
		{
			goToNode(By.xpath("//a[@title='" + node + " ']"));
			pause(500);
		}

	}

	//Go To Content Administration / Advanced Configuration / Manage Lock Tab
	public void goToContentAdminManageLockTab(){
		goToContentAdministration();
		click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
		click(ELEMENT_MANAGE_LOCKS);
		click(ELEMENT_MANAGE_LOCK_TAB);
	}

	public void goToNode(Object locator){	
		if (locator instanceof By) 
			click(locator);
		else 
		{
			By by = By.xpath("//a[@title='"+ locator +" ']");
			for (int i =0;; i++){
				if (i > ACTION_REPEAT){
					Assert.fail("Timeout");
				}
				click(by);
				if (waitForAndGetElement(By.xpath("//input[contains(@value,'/"+locator+"')]"),5000,0) !=null) return;
				info("Action repeated: goToNode");
			}	
		}
	}

	//Function to add data to frame
	public void inputDataToFrame (By framelocator, String data, boolean...validate){
		try {
			WebElement inputsummary = null;

			for (int repeat = 0;; repeat++) {
				if (repeat >= DEFAULT_TIMEOUT/WAIT_INTERVAL) {
					//Assert.fail("Fail to input data to frame " + framelocator);//Lientm comment
					info("Fail to input data to frame " + framelocator);
					break;
				}
				driver.switchTo().frame(waitForAndGetElement(framelocator));
				inputsummary = driver.switchTo().activeElement();
				if (validate.length >0)
					if (validate[0])
						inputsummary.clear();
				inputsummary.sendKeys(data);
				if (data.equals(inputsummary.getText())) break;
				switchToParentWindow();
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			inputDataToFrame (framelocator, data);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			inputDataToFrame (framelocator,data);
		} finally {
			loopCount = 0;
		}
	}

	//delete node of page, node is subnode of node
	//	public void deleteNode(By node, String pageTitle){
	//		actions.moveToElement(waitForAndGetElement(ELEMENT_MENU_EDIT_LINK)).build().perform();
	//		actions.moveToElement(waitForAndGetElement(ELEMENT_MENU_SITE_LINK)).build().perform();
	//		click(ELEMENT_MENU_NAVIGATION_LINK);
	//
	//		waitForElementPresent(By.id(ELEMENT_SITE_NAVIGATION_POPUP));
	//		if (node != null){
	//			click(node);
	//		}
	//		actions.contextClick(waitForAndGetElement(By.linkText(pageTitle))).perform();
	//		driver.findElement(By.partialLinkText(ELEMENT_SITE_NAVIGATION_DELETE_NODE_PARTIALLINK)).click();
	//		acceptAlert();	
	//		click(ELEMENT_SITE_NAVIGATION_SAVE_BUTTON);
	//		assert isElementNotPresent(ELEMENT_SITE_NAVIGATION_DELETE_ERROR_ALERT):"Have message error";
	//		info("Delete node of page successfully");
	//		pause(1000);
	//	}

	///////for Symlink

	//Go to target node in root when add symlink
	public void goToTargetNodeInRoot(){
		waitForElementPresent(ELEMENT_ADD_SYMLINK);
		click(ELEMENT_ADD_SYMLINK);
		waitForElementPresent(ELEMENT_ADD_SYMLINK_POPUP);
		click(ELEMENT_ADD_ITEM);
		click(ELEMENT_SITE_CONTENT);
		click(ELEMENT_LIVE_DIV);
		//click(ELEMENT_ACME_DIV);  
	}

	//Delete data
	public void deleteData(By data){
		goToNode(data);
		contMenu.deleteDocument(data);
		pause(1000);
		waitForElementNotPresent(data);
	}

	//Check alert
	public void checkAlertWarning(String message){
		waitForElementPresent(ELEMENT_ALERT);
		assert isElementPresent(ELEMENT_ALERT):"Not found alert";
		assert getText(ELEMENT_MESSAGE).contains(message):"Message is wrong";
		click(By.linkText("OK"));
	}

	public void checkAlertInfo(String message){
		waitForElementPresent(ELEMENT_ALERT);
		assert isElementPresent(ELEMENT_ALERT):"Not found alert";
		assert getText(ELEMENT_INFO).contains(message):"Message is wrong";
		click(By.linkText("OK"));
	}

	//Rename a node
	public void renameNode(By nodePath, String newName, By nodePathNew){
		rightClickOnElement(nodePath);
		click(By.xpath("//a[contains(text(),'Rename')]"));
		type(By.id("titleField"),newName,true);
		type(By.id("nameField"),newName,true);
		click(ELEMENT_SAVE_BUTTON);
		//check rename successfully
		waitForElementPresent(nodePathNew);
		assert isElementPresent(nodePathNew):"Rename node unsuccessfully";
		info("Rename node successfully");
	}

	//Create content folder and check
	public void createAndCheckContentFolder(String name, By path){
		info("Create new content folder with name: "+name);
		contentTemp.createNewContentFolder(name, name);
		waitForElementPresent(path);
		assert isElementPresent(path):"Create new content folder unsuccessfully";
		info("Create new content folder successfully"); 
	}	  

	//Copy and paste node
	public void copyAndPasteNode(By source, By target){
		goToNode(source);
		rightClickOnElement(source);
		pause(500);
		click(By.xpath("//a[contains(text(),'Copy')]"));
		goToNode(target);
		rightClickOnElement(target);
		pause(500);
		click(By.xpath("//a[contains(text(),'Paste')]"));
		pause(1000);
	}

	//Cut node
	public void cutAndPasteNode(By source, By target){
		cutNode(source);
		pasteNode(target);
	}

	//function enable edit mode
	public void enableEditMode(boolean enable){
		mouseOver(ELEMENT_MENU_EDIT_LINK, true);
		waitForAndGetElement(ELEMENT_MENU_CONTENT_LINK);
		if ((enable == true && isElementPresent(ELEMENT_MENU_EDIT_CONTENT) == true) || 
				(enable == false && isElementPresent(ELEMENT_MENU_EDIT_CONTENT) == false)){
			click(ELEMENT_MENU_EDIT_CONTENT); 
		}
	}

	//Function to go to SEO management
	public void goToSeoManagement(){
		info("Go to SEO management form");
		mouseOver(ELEMENT_MENU_EDIT_LINK, true);
		mouseOver(ELEMENT_MENU_PAGE_LINK, true);
		mouseOverAndClick(ELEMENT_MENU_SEO_LINK);		
	}

	//Function to go to acme/overview page
	public void goToOverView(){
		info("Go to OverView form");
		mouseOver(ELEMENT_MYSITE, true);
		mouseOver(ELEMENT_MYSITE_ACME, true);
		mouseOverAndClick(ELEMENT_OVERVIEW);
	}

	//Function to go to acme/new
	public void goToNews(){
		info("Go to News form");
		mouseOver(ELEMENT_MYSITE, true);
		mouseOver(ELEMENT_MYSITE_ACME, true);
		mouseOverAndClick(ELEMENT_NEWS);
	}

	/**
	 * @param anchor: Button's label to open form
	 * @param formTitle: Form's title
	 */
	public void openForm(String anchor, String formTitle){
		click(By.linkText(anchor));     
		waitForElementPresent(By.xpath("//span[@class='PopupTitle' and text()='" + formTitle + "']"));
	}
}
