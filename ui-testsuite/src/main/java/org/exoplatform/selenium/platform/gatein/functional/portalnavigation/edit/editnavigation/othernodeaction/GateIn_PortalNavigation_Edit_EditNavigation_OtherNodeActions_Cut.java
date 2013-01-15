package org.exoplatform.selenium.platform.gatein.functional.portalnavigation.edit.editnavigation.othernodeaction;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;


/**
 *@author HangNTT
 * @date: 18/09/2012
 */
public class GateIn_PortalNavigation_Edit_EditNavigation_OtherNodeActions_Cut extends PlatformBase {
	/**
	 * @param args
	 */
	ManageAccount account;
	NavigationToolbar navigation;
	PageEditor pageEditor;
	PageManagement page;
	NavigationManagement naviManage;
	String CURRENT_NAV = "intranet";
	String CURRENT_NODE = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Home");
	String PAGE_NAME  = null; 
	String PAGE_TITLE = null; 
	By UP_LEVEL = By.xpath("//a[@title='Up Level']");

	public WebElement element = null;
	
	public void cutNode(String nodename) {
		rightClickOnElement(ELEMENT_NODE_LINK.replace("${nodeLabel}",nodename));
		waitForAndGetElement(By.partialLinkText("Cut Node")).click();
	}

	public void pasteNode(String nodename) {
		rightClickOnElement(ELEMENT_NODE_LINK.replace("${nodeLabel}",nodename));
		waitForAndGetElement(By.partialLinkText("Paste Node")).click();
	}

	@BeforeMethod()
	public void beforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		pageEditor = new PageEditor(driver);
		page = new PageManagement(driver);
		naviManage = new NavigationManagement(driver);
		driver.manage().window().maximize();
	}

	// Cut Paste Node To Same Place
	@Test
	public void test05_CutPasteNodeToSamePlace () {
		account.signIn("root", "gtn");
		
		//Go to Portal navigation
		info("Go to Poral naviagtion");
		navigation.goToPortalSites();
		
		//Click Edit Navigation of intranet's portal
		info("Go to Edit navigation of intranet");
		editNavigation(CURRENT_NAV);
		
		// Cut a node
		info("Right click on a node");
		info("Cut a node");
		cutNode("Home");
		
		//Paste to same place
		info("Paste to same place");
		pasteNode("Home");
		//Show message
		waitForMessage(naviManage.MSG_SAME_SOURCE);
		click(ELEMENT_OK_BUTTON);
		info("Close navigation form");
		save();
		waitForTextPresent(CURRENT_NAV);
	}
	
	// Cut Paste Node To New Place In Same Navigation
	@Test
	public void test02_CutPasteNodeToNewPlaceInSameNavigation(){

		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		PAGE_NAME = "CutPaste02";
		PAGE_TITLE = "CutPaste02";
		info("main program");	  
		account.signIn("root", "gtn");
		
		//Go to Portal navigation
		info("Go to Poral naviagtion");
		navigation.goToPortalSites();
		
		//Click Edit Navigation of intranet's portal
		info("Go to Edit navigation of intranet");
		editNavigation(CURRENT_NAV);
		
		//Add new node
		info("add new node");
		naviManage.addNodeForPortal("intranet", "Home", false, "PORNAV_14_04_002", true, languages, "PORNAV_14_04_002", PAGE_NAME, PAGE_TITLE, true, true);
		editNavigation("intranet");
		
		//Cut new node
		info("Cut node");
		cutNode("PORNAV_14_04_002");
		click(UP_LEVEL);   
		
		//Paste new node to new place
		info("Paste to new place");
		pasteNode("Wiki");
		save();
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON);
		//Delete new node
		info("Delete node");
		editNavigation("intranet");
		naviManage.deleteNode("intranet","Wiki","PORNAV_14_04_002",false);
		
		info("Verify node is deleted");
		editNavigation("intranet");
		waitForTextNotPresent("PORNAV_14_04_002");
		save();
		waitForTextPresent(CURRENT_NAV);
		
		navigation.goToManagePages();
		page.deletePage(PageType.PORTAL, PAGE_TITLE);
	}
	
	//Cut Paste Node To Same Place
	@Test
	public void test01_CutPasteNodeToSamePlace(){

		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		PAGE_NAME="CutPaste01";
		PAGE_TITLE="CutPaste01";
		info("main program");	  
		account.signIn("root", "gtn");
		
		//Go to Portal navigation
		info("Go to Poral naviagtion");
		navigation.goToPortalSites();
		
		//Click Edit Navigation of intranet's portal
		info("Go to Edit navigation of intranet");
		editNavigation(CURRENT_NAV);
		
		//Add new node
		info("add new node");
		naviManage.addNodeForPortal("intranet", "Home", false, "PORNAV_14_04_001", true, languages, "PORNAV_14_04_001", PAGE_NAME, PAGE_TITLE, true, true);
		editNavigation("intranet");
		
		// Cut new node
		
		info("Cut node");
		cutNode("PORNAV_14_04_001");
		
		// Paste node to same place
		info("Paste node");
		
		info("Paste to same place");
		pasteNode("Home");
		waitForMessage(naviManage.MSG_ADD_SAME_NODE);
		closeMessageDialog();
		
		info("Close navigation form");
		info("Delete node");
		//Delete node
		naviManage.deleteNode("intranet", "Home", "PORNAV_14_04_001", true);
		editNavigation("intranet");
		waitForTextNotPresent("PORNAV_14_04_001");
		save();
		waitForTextPresent(CURRENT_NAV);
		
		navigation.goToManagePages();
		page.deletePage(PageType.PORTAL, PAGE_TITLE);
	}
	
	//Cut Paste Node Created Automatically By Add Page
	@Test
	public void test04_CutPasteANodeCreatedAutomaticallyByAddPage () {
		String NODE_NAME = "PORNAV_14_04_004"; 
		String DISPLAY_NAME = "PORNAV_14_04_004";		
		String LANGUAGE = "English";	
		Map<String, String> PORTLET_IDS = new HashMap<String, String>();
		PORTLET_IDS.put("Administration/AccountPortlet","");
		String CATEGORY_TITLE = "Administration";

		info("main program");	  
		account.signIn("root", "gtn");
		
		//Add new page by wizard
		navigation.goToAddPageEditor();
		page.addNewPageEditor(NODE_NAME, DISPLAY_NAME, LANGUAGE, CATEGORY_TITLE, PORTLET_IDS, true);
		
		// Go to Portal Navigation
		navigation.goToPortalSites();
		info("Go to Edit navigation");
		
		// Click Edit navigation of intranet's portal
		editNavigation("intranet");
		
		//Cut new node
		
		info("Cut node");
		cutNode("PORNAV_14_04_004");
		
		// Paste new node
		info("Paste node");
		
		info("Paste new node ");
		pasteNode("Wiki");
		
		// Delete new node
		info("Delete node");
		naviManage.deleteNode("intranet", "Wiki", "PORNAV_14_04_004", true);
		editNavigation("intranet");
		waitForTextNotPresent("PORNAV_14_04_004");
		save();
		waitForTextPresent(CURRENT_NAV);
		
		navigation.goToManagePages();
		page.deletePage(PageType.PORTAL, NODE_NAME);
	}
	
	@AfterMethod()
	public void afterTest(){
		account.signOut();
		driver.quit();
	}
}