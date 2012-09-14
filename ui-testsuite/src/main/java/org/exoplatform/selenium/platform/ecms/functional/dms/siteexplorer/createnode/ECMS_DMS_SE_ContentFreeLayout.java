package org.exoplatform.selenium.platform.ecms.functional.dms.siteexplorer.createnode;

import java.util.concurrent.TimeUnit;

import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;

public class ECMS_DMS_SE_ContentFreeLayout extends EcmsBase{
	public static final String DATA_USER = "john";
	public static final String DATA_PASS = "gtn";
	
	public static final By ELEMENT_ACME_LINK = By.xpath("//a[@title='acme ']");
	public static final By ELEMENT_WEB_CONTENT_LINK = By.xpath("//a[@title='web contents ']");
	
	
	 @BeforeMethod
	  public void beforeMethods() throws Exception {
		initSeleniumTest();
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    driver.get(baseUrl);
	    actions = new Actions(driver);
	  }

	  @AfterMethod
	  public void afterMethods() throws Exception {
	    driver.manage().deleteAllCookies();
		driver.quit();
	    actions = null;
	  }
	  
	  /*case1: Add free layout content in Web Content folder
	   * login
	   * goto acme/web content
	   * add new free layout web content
	   * Check web content having Free layout template is listed with status=Draft
	   * delete free layout
	   * login
	   */
	  @Test
	  public void test01_AddFreeLayoutInWebContentfolder(){
		  String DATA_FREE_LAYOUT_TITLE = "Freelayout_01";
		  By ELEMENT_FREE_LAYOUT = By.xpath("//a[@title='"+DATA_FREE_LAYOUT_TITLE+" "+"']");
		  String DATA_IMG = "TestData/ECMS_DMS_SE_ContentFreeLayout.jpg";
		  By ELEMENT_STATUS = By.xpath("//div[@title='"+DATA_FREE_LAYOUT_TITLE+" "+"']/../../..//div[@title='status']");
		  
		  //login
		  loginEcms(DATA_USER, DATA_PASS);
		  //go to web content folder
		  info("Go to web content folder");
		  goToSiteExplorerForm();
		  goToNode(ELEMENT_ACME_LINK);
		  goToNode(ELEMENT_WEB_CONTENT_LINK);
		  goToAddNewContent();
		  //add new free layout web content
		  debug("Add new free layout webcontent with title: "+DATA_FREE_LAYOUT_TITLE );
		  createNewFreeLayoutWebContent(DATA_FREE_LAYOUT_TITLE, DATA_FREE_LAYOUT_TITLE, "", DATA_IMG, DATA_FREE_LAYOUT_TITLE, "", "");
		  waitForElementPresent(ELEMENT_FREE_LAYOUT);
		  assert isElementPresent(ELEMENT_FREE_LAYOUT):"Can not add new free layout webcontent";
		  info("Add new free layout webcontent successfully");
		  //check status of free layout is draft
		  goToNode(ELEMENT_WEB_CONTENT_LINK);
		  assert getText(ELEMENT_STATUS).contains("Draft"):"Status of file is not true";
		  //delete data
		  goToNode(ELEMENT_FREE_LAYOUT);
		  deleteDocument(ELEMENT_FREE_LAYOUT);
		  waitForElementNotPresent(ELEMENT_FREE_LAYOUT);
		  assert isElementNotPresent(ELEMENT_FREE_LAYOUT):"Can node delete free layput webcontent";
		  //logout
		  logoutEcms();		  	  
	  }
	  
	  /*case2: Add free layout content in web content folder with blank Name
	   * login
	   * go to acme/web content
	   * add new free layout content with name blank
	   * check can not add
	   * logout
	   */
	  @Test
	  public void test02_AddFreeLayoutWithNameBlank(){
		  String DATA_FREE_LAYOUT_TITLE = "Freelayout_02";
		  By ELEMENT_ALERT = By.xpath("//div[@class='UIPopupWindow UIDragObject ExoMessageDecorator']");
		  By ELEMENT_TEXT_ALERT = By.xpath("//span[@class='PopupIcon WarningMessageIcon']");
		  
		  //login
		  loginEcms(DATA_USER, DATA_PASS);
		  //go to web content
		  info("Go to web content folder");
		  goToSiteExplorerForm();
		  goToNode(ELEMENT_ACME_LINK);
		  goToNode(ELEMENT_WEB_CONTENT_LINK);
		  goToAddNewContent();
		  //add new free layout webcontent with name blank
		  debug("Add new free layout webcontent with name blank");
		  createNewFreeLayoutWebContent(DATA_FREE_LAYOUT_TITLE, "", "", "", "", "", "");
		  assert isElementPresent(ELEMENT_ALERT):"Has not message";
		  assert getText(ELEMENT_TEXT_ALERT).contains("The field \"Name\" is required."):"Wrong message";		  
		  click(By.linkText("OK"));
		  //logout
		  logoutEcms();
	  }
	  
	  /*case3: Add free layout content in Web Content folder with special characters in Name
	   * login
	   * go to acme/web content folder
	   * add new free layout content with special characters in Name (! @  #  $  %  &  *  (  )  .  /  :  [  ] { } < >  "  ' , ; ~ `)
	   * check can not add 
	   * logout
	   */
	  @Test
	  public void test03_AddFreeLayoutWithNameContainsSpecialCharacters(){
		  String DATA_FREE_LAYOUT_TITLE = "Freelayout_03";
		  String DATA_FREE_LAYOUT_NAME = "Freelayout_!@#$%^&*()`~<>,./?'{}[]|\"\\";
		  By ELEMENT_ALERT = By.xpath("//div[@class='UIPopupWindow UIDragObject ExoMessageDecorator']");
		  By ELEMENT_TEXT_ALERT = By.xpath("//span[@class='PopupIcon WarningMessageIcon']");
		  
		  //login
		  loginEcms(DATA_USER, DATA_PASS);
		  //go to web content
		  info("Go to web content folder");
		  goToSiteExplorerForm();
		  goToNode(ELEMENT_ACME_LINK);
		  goToNode(ELEMENT_WEB_CONTENT_LINK);
		  goToAddNewContent();
		  //add new free layout webcontent with name blank
		  debug("Add new free layout webcontent with name blank");
		  createNewFreeLayoutWebContent(DATA_FREE_LAYOUT_TITLE, DATA_FREE_LAYOUT_NAME, "", "", "", "", "");
		  assert isElementPresent(ELEMENT_ALERT):"Has not message";
		  assert getText(ELEMENT_TEXT_ALERT).contains("The field 'Name' contains some invalid characters. Please enter another value."):"Wrong message";		  
		  click(By.linkText("OK"));
		  //logout
		  logoutEcms();  
	  }
	  
	  /*case4: Create free layout content in Web Content folder with Name in different languages
	   * login
	   * go to acme/web content folder
	   * add new free layout web content with name = vietnamese, France
	   * check add successfully
	   * delete free layout
	   * logout
	   */
	  @Test
	  public void test04_CreateFreeLayoutWithNameInDifferentLanguage(){
		  String DATA_FREE_LAYOUT_TITLE = "Freelayout_04";
		  By ELEMENT_FREE_LAYOUT = By.xpath("//a[@title='"+DATA_FREE_LAYOUT_TITLE+" "+"']");
		  String DATA_FREE_LAYOUT_NAME_VIETNAMESE = "kiểm thử";
		  String DATA_FREE_LAYOUT_NAME_FRANCE  = "qui sest formé";
		  
		  //login
		  loginEcms(DATA_USER, DATA_PASS);
		  //go to web content
		  info("Go to web content folder");
		  goToSiteExplorerForm();
		  goToNode(ELEMENT_ACME_LINK);
		  goToNode(ELEMENT_WEB_CONTENT_LINK);
		  goToAddNewContent();
		  //add new free layout webcontent with name in Vietnamese
		  debug("Create new free layout webcontent with name in vietnamese: "+DATA_FREE_LAYOUT_NAME_VIETNAMESE);
		  createNewFreeLayoutWebContent(DATA_FREE_LAYOUT_TITLE, DATA_FREE_LAYOUT_NAME_VIETNAMESE, "", "", "", "", "");
		  goToNode(ELEMENT_FREE_LAYOUT);
		  assert isElementPresent(ELEMENT_FREE_LAYOUT):"Can not add free layout content with name in viettelese";
		  info("Create new free layout webcontent with name in vietnamese successfully");
		  deleteDocument(ELEMENT_FREE_LAYOUT);
		  waitForElementNotPresent(ELEMENT_FREE_LAYOUT);
		  assert isElementNotPresent(ELEMENT_FREE_LAYOUT):"Can not delete free layout webcontent with name in vietnamese";
		  //add new free layout webcontent with name in France
		  goToNode(ELEMENT_WEB_CONTENT_LINK);
		  goToAddNewContent();
		  debug("Create new free layout webcontent with name in france: "+DATA_FREE_LAYOUT_NAME_FRANCE);
		  createNewFreeLayoutWebContent(DATA_FREE_LAYOUT_TITLE, DATA_FREE_LAYOUT_NAME_FRANCE, "", "", "", "", "");
		  goToNode(ELEMENT_FREE_LAYOUT);
		  assert isElementPresent(ELEMENT_FREE_LAYOUT):"Can not add free layout content with name in france";
		  info("Create new free layout webcontent with name in france successfully");
		  deleteDocument(ELEMENT_FREE_LAYOUT);
		  waitForElementNotPresent(ELEMENT_FREE_LAYOUT);
		  assert isElementNotPresent(ELEMENT_FREE_LAYOUT):"Can not delete free layout webcontent with name in france";
		  //logout
		  logoutEcms();
		  }
}
