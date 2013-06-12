package org.exoplatform.selenium.platform.ecms.regression;

import static org.exoplatform.selenium.TestLogger.info;
import static org.exoplatform.selenium.platform.NavigationToolbar.goToAddPageEditor;
import static org.exoplatform.selenium.platform.ecms.ContextMenu.ELEMENT_MENU_ADD_SYMLINK;
import static org.exoplatform.selenium.platform.ecms.SiteExplorer.chooseDrive;

import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class REG_PLF305_ECMS_002 extends EcmsBase {
	/*-- Data for these test cases --*/
	public String DATA_USER = "john";
	public String DATA_PASS = "gtn";
	By DRIVER_SITES_MANAGEMENT = By.xpath("//a[@class='DriveLabel' and @title = 'Sites Management']");
	String DOCUMENT_TITLE = "Power Pack 1";
	String DOCUMENT_NAME = "acme-news-3";
	By SELETED_CONTENT = By.xpath("//div[@title='" + DOCUMENT_TITLE + " ']");
	By CONTENT_SYMLINK = By.xpath("//div[@title='" + DOCUMENT_NAME + ".lnk']");
	By ELEMENT_MENU_CUT = By.xpath("//div[@class='MenuItem']/a[contains(text(),'Cut')]");
	By MENU_PASTE = By.xpath("//a[contains(text(),'Paste')]");
	String TARGET_FOLDER = "documents";
	By FOLDER_NEWS = By.xpath("//div[@title='News ']");
	String PAGE_NAME = "TestCLV";
	String PORTLET_NAME = "Content List";
	
	@BeforeMethod
	public void beforeMethods(){
		info("\n\nBEGIN Test case REG_PLF305_ECMS_002 - Check form to Select Category. \n\n");
		initSeleniumTest();
		driver.get(baseUrl);
		actions = new Actions(driver);
		info("Login ECMS with " + DATA_USER);
		loginEcms(DATA_USER, DATA_PASS);
	}
	
	@AfterMethod
	public void afterMethods(){
		info("Logout ECMS");
		info("\n\nEND Test case REG_PLF305_ECMS_002 \n\n");
		driver.quit();
		actions = null;
	}
	
	/**
	 * Go to folder News in acme/web contents
	 *
	 * Description: mouse over folder "News" and double click on it
	 * @param folder folder to go into
	 */
	private void goToFolderNews(By folder){
		waitForElementPresent(folder);
		mouseOver(folder,false);
		doubleClickOnElement(folder);		
	}
	
	/**
	 * Add Symlink for a content
	 * 
	 * @param content Content to add Symlink 
	 */
	private void addSymlink(By content){
		waitForElementPresent(content);
		mouseOver(content,false);
		rightClickOnElement(content);
		click(ELEMENT_MENU_ADD_SYMLINK);
		pause(5000);
	}
	
	/**
	 * Cut Symlink of a content
	 * 
	 * @param content Content to cut Symlink 
	 */
	private void cutSymlink(By content){
		waitForElementPresent(content);
		mouseOver(content,false);
		rightClickOnElement(content);
		waitForElementPresent(ELEMENT_MENU_CUT);
		mouseOver(ELEMENT_MENU_CUT,false);
		click(ELEMENT_MENU_CUT);
		pause(6000);
		info("\n === Symlink is cut ===");
	}
	
	/**
	 * Paste Symlink of a content
	 * 
	 * @param target Target content to paste Symlink 
	 * @param symlink Symlink to check the result
	 */
	private void pasteSymlink(String targetFolderName, By symlink) {
		By targetContent = By.xpath("//a[@title='" + targetFolderName + " ']"); 
		// Mouse over documents folder		
		mouseOver(targetContent,false);
		rightClickOnElement(targetContent);
		// Click Paste
		waitForElementPresent(MENU_PASTE);
		click(MENU_PASTE);
		pause(6000);
		goToNodeByPath(targetFolderName);
		waitForElementPresent(symlink);
		info("\n === Symlink pasted ===");
	}
	
	/**
	 * Go to Intranet Home page
	 */
	private void goToIntranet(){
		waitForElementPresent(By.xpath("//a[@class='ArrowIcon TBIcon' and contains(text(),'My Sites')]"));
		mouseOver(By.xpath("//a[@class='ArrowIcon TBIcon' and contains(text(),'My Sites')]"),true);
		pause(500);
		click(By.xpath("//a[contains(text(),'intranet')]"));
		pause(3000);
		waitForElementPresent(By.linkText("All Updates"));
	}
	
	/**
	 * Create new page and stop on step 3
	 * Steps: 
	 * - Mouse over Edit > Page and click Add Page
	 * - Fill in page name
	 * - Click Next to go to step 2
	 * - Click Next another time to go to step 3
	 * 
	 *  @param pageName name of page
	 *  @param portletName name of portlet
	 */
	private void addNewPage(String pageName){
		info("\n=== Mouser over Edit > Page and click Add Page ===");
		goToAddPageEditor();
		// Fill in page name
		info("\n=== Fill in page name ===");
		// Wait Node Name appears
		pause(3000);
		waitForElementPresent(By.id("pageName"));
		type(By.id("pageName"),pageName,false);		
		// Pass to second step
		info("\n=== Pass to second step ===");
		next();
		pause(3000);
		waitForElementPresent(By.xpath("//div[@class='ActionBar']"));
		waitForElementPresent(By.linkText("Next"));
		// Pass to third step
		info("\n=== Pass to third step ===");
		next();
		pause(4000);
	}
	
	/**
	 * Add CLV portlet by drag and drop CLV portlet to main content
	 * Prerequisite: we are on step 3 of creating new page.
	 */
	private void addCLVPortlet(){
		String CLVid = "Content/ContentListViewerPortlet";
		By CLVcontainer = By.id(CLVid);
		// ==== Add new CLV portlet ====
		info("\n\n=== Add new CLV portlet ===");
		pause(2000);
		waitForElementPresent(By.id("UIPageEditor"));
		waitForElementPresent(By.xpath("//a[@title='Content']"));
		click(By.xpath("//a[@title='Content']"));
		waitForElementPresent(CLVcontainer);		
		mouseOver(CLVcontainer,true);
		pause(1000);
		click(CLVcontainer);
		pause(1000);
		dragAndDropToObject("//div[@id='" + CLVid + "']//img", "//div[@id='UIPage']");
		pause(1000);
	}
	
	/**
	 * Mouse over Edit icon of portlet and click on it
	 */
	private void editCLVPortlet(){
		info("\n=== Edit CLV portlet ===");
		waitForElementPresent(ELEMENT_FRAME_CONTAIN_PORTLET);
		mouseOver(ELEMENT_FRAME_CONTAIN_PORTLET,true);	
		waitForElementPresent(ELEMENT_EDIT_PORTLET_ICON);
		click(ELEMENT_EDIT_PORTLET_ICON);
		pause(1000);	
	}
	
	/**
	 * Choose "By Content" option
	 */
	private void clickByContentOption(){
		info("\n=== Choose \"By Content\" option === ");
		waitForElementPresent(By.xpath("//label[contains(text(),'By Content')]"));
		click(By.id("UICLVConfigDisplayModeFormRadioBoxInput_ManualViewerMode"));
		pause(500);
	}
	
	/**
	 * Click on + icon to select folder path
	 */
	private void clickOnSelectFolderPathIcon(){
		// Click on + icon to select folder path
		info("\n=== Click on + icon to select folder path === ");
		waitForElementPresent(ELEMENT_SELECT_CONTENT_PATH_LINK);
		click(ELEMENT_SELECT_CONTENT_PATH_LINK);
		waitForElementPresent(By.xpath("//th[contains(text(),'List Content')]"));
	}
	
	/**
	 * Select content 'Power Pack 1' in acme/web contents/News
	 */
	private void selectFirstContent(){
		info("\n=== Select Power Pack 1 in News folder === ");
		waitForElementPresent(By.xpath("//a[contains(text(),'General Drives')]"));
		click(By.xpath("//a[contains(text(),'General Drives')]"));
		waitForElementPresent(By.xpath("//a[contains(text(),'Sites Management')]"));
		click(By.xpath("//a[contains(text(),'Sites Management')]"));
		waitForElementPresent(By.linkText("acme"));
		click(By.linkText("acme"));
		waitForElementPresent(By.linkText("web contents"));
		click(By.linkText("web contents"));
		waitForElementPresent(By.linkText("News"));
		click(By.linkText("News"));
		waitForElementPresent(By.xpath("//div[@id='RightWorkspace']/div/div/table[@id='ListRecords']/thead/tr/td/a[@title='Power Pack 1']"));
		click(By.xpath("//table[@id='ListRecords']/thead/tr/td/a[@title='Power Pack 1']"));
		waitForElementPresent(By.xpath("//div[@id='RightWorkspace']/div/div/table[@id='ListFilesContent']/thead/tr/td/a[contains(text(),'acme-news-3')]"));
		pause(5000);
		info("\n=== 'Power Pack 1' is added in the list ! === ");
	}
	
	/**
	 * Select content 'Power Pack 1' in acme/documents
	 */
	private void selectSecondContent(){
		info("\n=== Select Power Pack 1 in documents folder === ");
		click(By.linkText("acme"));		
		waitForElementPresent(By.id("GeneralDrives_ManagedSites__acme"));
		click(By.id("GeneralDrives_ManagedSites__acme"));
		info("\n=== Go to documents folder === ");
		waitForElementPresent(By.id("GeneralDrives_ManagedSites__acme_documents"));
		click(By.id("GeneralDrives_ManagedSites__acme_documents"));
		info("\n=== Click on symlink === ");
		waitForElementPresent(By.xpath("//div[@id='RightWorkspace']/div/div/table[@id='ListRecords']/thead/tr/td/a[@title='acme-news-3.lnk']"));
		click(By.xpath("//div[@id='RightWorkspace']/div/div/table[@id='ListRecords']/thead/tr/td/a[@title='acme-news-3.lnk']"));
	}
	
	/**
	 * Check alert popup is shown
	 */
	private void checkAlertPresent(){
		pause(3000);
		info("\n=== Check alert present === ");
		acceptAlert();
		info("\n=== Cannot add symlink of the same content ==> OK !!! ===");
		pause(2000);
	}
	
	/**
	 * Cancel creating new page
	 */
	private void cancelCreatingNewPage(){
		// Close select folder path popup window
		waitForElementPresent(By.linkText("Cancel"));
		cancel();
		// Close TabPaneContent window
		waitForElementPresent(By.linkText("Close"));
		close();		
		// Click on Abort button to cancel creating new page
		waitForElementPresent(By.xpath("//a[@title='Abort']"));
		click(By.xpath("//a[@title='Abort']"));
		// Confirm to cancel
		waitForElementPresent(By.xpath("//a[contains(text(),'Yes')]"));
		click(By.xpath("//a[contains(text(),'Yes')]"));
		pause(5000);
	}
	
	/**
	 * Delete symlink in acme/documents
	 */
	private void cleanData(){
		By contentPath = By.xpath("//div[contains(text(),'Power Pack 1')]");
		// Go to acme/document and delete symlink
		info("\n=== Go Sites Explorer ===");
		goToSiteExplorer();
		chooseDrive(DRIVER_SITES_MANAGEMENT);
		pause(6000);
		waitForElementPresent(By.xpath("//div[contains(text(),'Sites Management')]"));
		info("\n=== Go to /acme/documents ===");
		waitForElementPresent(By.linkText("acme"));
		goToNode("acme");
		pause(2000);
		waitForElementPresent(By.linkText("documents"));
		goToNode("documents");
		pause(4000);
		// Delete symlink
		waitForElementPresent(contentPath);
		mouseOver(contentPath,false);
		rightClickOnElement(contentPath);
		waitForElementPresent(By.xpath("//a[contains(text(),'Delete')]"));
		click(By.xpath("//a[contains(text(),'Delete')]"));
		// Confirm delete
		waitForElementPresent(By.xpath("//a[contains(text(),'OK')]"));
		click(By.xpath("//a[contains(text(),'OK')]"));
		pause(3000);
		waitForElementNotPresent(contentPath);	
	}
	
	/*-- Case No 005 / ID REG_PLF305_ECMS_005 
	 *-- Check Duplicity Content Filter in CLV
	 *	- Go to Sites Explorer
		- Go to /acme/web contents/News
		- Add a symlink of "Power Pack 1"
		- Cut and paste symlink to /acme/documents
		- Go to intranet home page
		- Add new page "TestCLV"
		- Add portlet "Content List"
		- Edit portlet "Content List"
		-- Choose "By Content" option
		-- Select content /acme/web contents/News/Power Pack 1
		   This content appears in the list ==> OK
		-- Select content /shared/web contents/Power Pack 1 symlink
		   Expected result: Alert popup appears to say that the content is already in the list.		
	 * --*/
	@Test
	public void test02_CheckDuplicityContentFilterInCLV(){
		// Go to Sites Explorer
		info("\n=== Go Sites Explorer ===");
		goToSiteExplorer();
		chooseDrive(DRIVER_SITES_MANAGEMENT);
		waitForElementPresent(By.xpath("//div[contains(text(),'Sites Management')]"));
		
		// Go to /acme/web contents/News
		info("\n=== Go to /acme/web contents ===");
		goToNodeByPath("acme/web contents");
		info("\n=== Go to folder News ===");
		goToFolderNews(FOLDER_NEWS);
				
		// Add a symlink of "Power Pack 1"
		info("\n=== Add a symlink of \"Power Pack 1\" ===");
		addSymlink(SELETED_CONTENT);		
		
		// Cut new symlink
		info("\n === Cut new symlink ===");
		cutSymlink(CONTENT_SYMLINK);
					
		// Paste symlink into documents folder
		info("\n=== Right click and paste symlink ===");
		pasteSymlink(TARGET_FOLDER, CONTENT_SYMLINK);
			
		// Go to intranet home page
		info("\n=== Go to intranet home page ===");
		goToIntranet();
		
		// === Add a new page TestCLV ===
		info("\n=== Add a new page TestCLV ===");
		addNewPage(PAGE_NAME);
		
		// Add new Content List portlet
		addCLVPortlet();
		pause(5000);
		
		// Edit CLV portlet
		editCLVPortlet();	
		
		// Choose "By Content" option
		clickByContentOption();
		
		// === Add multiple content == 
		clickOnSelectFolderPathIcon();
		// Select Power Pack 1 in News folder
		selectFirstContent();
		
		// Select Power Pack 1 in documents folder
		selectSecondContent();
		checkAlertPresent();
		
		// Cancel creating new page
		cancelCreatingNewPage();		
		// Clean data
		cleanData();
	}
}
