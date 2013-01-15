package org.exoplatform.selenium.platform.gatein.functional.portalnavigation.edit;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.PortalManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.exoplatform.selenium.TestLogger.*;

public class GateIn_PortalNavigation_Edit_EditLayout extends PlatformBase{

	ManageAccount account;
	NavigationToolbar navigation;
	NavigationManagement naviManage;
	PageEditor pageEditor;
	PortalManagement portal;
	
	//Application Tab in Edit Layout
	
	public final By ELEMENT_CONFIRMATION = By.id("UIConfirmation");
	public final By ELEMENT_CONFIRMATION_YES_OPTION = By.xpath("//div[@id='UIConfirmation']//div[contains(@class, 'UIAction')]//a[contains(text(), 'Yes')]");
			
	public final String ELEMENT_EDIT_PAGE_COMPONENT_FIRST = pageEditor.ELEMENT_EDIT_PAGE_COMPONENT.replace("${portletNumber}", "1");
	public final String EMPTY_CONTAINER = pageEditor.ELEMENT_EDIT_PAGE_COMPONENT + "//div[@class='UIRowContainer EmptyContainer']";
	public final String ELEMENT_EMPTY_CONTAINER_2 = EMPTY_CONTAINER.replace("${portletNumber}", "2");
	public final String APPLICATION_DRAG_ICON = pageEditor.ELEMENT_EDIT_PAGE_COMPONENT_DRAG_ICON.replace("${number}", "1");
	public final String ELEMENT_EDIT_CONTAINER = pageEditor.ELEMENT_EDIT_PAGE_COMPONENT.replace("${portletNumber}", "1");
	
	
	public final String ELEMENT_CONTENT_CATEGORY = ELEMENT_EDIT_PAGE_CATEGORY_MENU.replace("${categoryLabel}", "Content");
	public final String ELEMENT_ADMIN_CATEGORY = ELEMENT_EDIT_PAGE_CATEGORY_MENU.replace("${categoryLabel}", "Administration");
	
	
	// This locator is different from Platform Base Save & Close
	public final By ELEMENT_SAVE_CLOSE_BUTTON = By.linkText("Save And Close");
	@BeforeMethod
	public void setUpBeforeTest(){

		initSeleniumTest();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		portal = new PortalManagement(driver);
		naviManage = new NavigationManagement(driver);
		pageEditor = new PageEditor(driver);
		driver.get(baseUrl);		
	}

	@AfterMethod
	public void afterTest(){
		driver.quit();
	}

	/*--Case 050 Portal\Portal Navigation\Edit\Edit Layout 
	 *  Edit current portal with no one for edit right
	 * --*/
	@Test(groups={"later"})
	public void test07_EditCurrentPortalWithNoOneForEditRight(){

		account.signIn("root", "gtn");

		navigation.goToPortalSites();

		info("-- Edit layout of current Portal --");
		String editLayout = naviManage.ELEMENT_EDIT_LAYOUT.replace("${navigation}", "intranet");
		click(editLayout);

		info("-- Click on Site's Config button --");
		click(portal.ELEMENT_SITE_CONFIG_LINK);
		click(ELEMENT_PERMISSION_SETTING_TAB);

		info("-- Delete Permission --");
		click(ELEMENT_EDIT_PERMISSION_SETTING);
		click(ELELENT_LINK_DELETE_PERMISSION);

		save();

		waitForMessage(portal.MSG_EDIT_EMPTY_PERMISSION_SETTING);
		closeMessageDialog();
		cancel();

		click(ELEMENT_ABORTEDIT_BUTTON);
		waitForAndGetElement(ELEMENT_CONFIRMATION);
		waitForTextPresent(MESSAGE_QUIT_EDIT_LAYOUT);
		waitForAndGetElement(ELEMENT_CONFIRMATION_YES_OPTION);
		click(ELEMENT_CONFIRMATION_YES_OPTION);
		waitForElementPresent(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", "intranet"));
		account.signOut();				
	}


	/*--Case 060 Portal\Portal Navigation\Edit\Edit Layout 
	 *  Check when drag & drop Page Body in edit current portal
	 * --*/
	@Test(groups={"later"})
	public void test17_CheckWhenDragAndDropPageBodyInEditCurrentPortal(){		

		account.signIn("root", "gtn");

		navigation.goToPortalSites();

		info("--Edit layout of current Portal--");
		String editLayout = naviManage.ELEMENT_EDIT_LAYOUT.replace("${navigation}", "intranet");
		click(editLayout);

		waitForTextPresent("Applications");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		waitForAndGetElement(pageEditor.ELEMENT_ROWS_LAYOUT);
		click(pageEditor.ELEMENT_ROWS_LAYOUT);

		info("--Move PageBody to the new palce--");
		dragAndDropToObject(pageEditor.ELEMENT_ONE_ROW_LAYOUT, pageEditor.ELEMENT_PORTAL_PAGE_COMPONENT);
		click(pageEditor.ELEMENT_TAB_APPLICATIONS); 
		waitForTextPresent("Administration");
		mouseOver(pageEditor.ELEMENT_PAGE_BODY, true);
		dragAndDropToObject(pageEditor.ELEMENT_PAGE_BODY, pageEditor.ELEMENT_EMPTY_CONTAINER);

		info("--Switch view mode portal--");
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		waitForTextPresent("Applications");
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);

		info("--Check when delete component contains Page Body--");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		mouseOver(pageEditor.ELEMENT_PAGE_BODY, true);
		mouseOver(pageEditor.ELEMENT_DELETE_CONTAINER_ICON, true);
		click(pageEditor.ELEMENT_DELETE_CONTAINER_ICON);

		waitForConfirmation(pageEditor.MESSAGE_DELETE_CONTAINER);
		waitForTextPresent(pageEditor.MESSAGE_WARNING_CONTAINER);
		closeMessageDialog();

		info("--SignOut--");
		click(ELEMENT_ABORTEDIT_BUTTON);
		waitForAndGetElement(ELEMENT_CONFIRMATION);
		waitForTextPresent(MESSAGE_QUIT_EDIT_LAYOUT);
		waitForAndGetElement(ELEMENT_CONFIRMATION_YES_OPTION);
		click(ELEMENT_CONFIRMATION_YES_OPTION);
		waitForTextNotPresent(MESSAGE_QUIT_EDIT_LAYOUT);

		account.signOut();		
	}	

	/*--Case 062 Portal\Portal Navigation\Edit\Edit Layout
	 * Check Finish function after changing container layout
	 * --*/
	@Test(groups={"later"})
	public void test19_CheckFinishFunctionAfterChangingContainerLayout(){

		account.signIn("root", "gtn");
		String username = "Root Root";
		waitForTextPresent(username);

		info("--View layout of portal before changing container in portal--");
		captureScreen("case062_BeforeChange");

		info("--Edit layout of current Portal--");
		navigation.goToPortalSites();
		String editLayout = naviManage.ELEMENT_EDIT_LAYOUT.replace("${navigation}", "intranet");
		click(editLayout);

		info("--Select Container tab on editting inline composer--");
		waitForTextPresent("Applications");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		waitForAndGetElement(pageEditor.ELEMENT_ROWS_LAYOUT);
		click(pageEditor.ELEMENT_ROWS_LAYOUT);	
		dragAndDropToObject(pageEditor.ELEMENT_ONE_ROW_LAYOUT, pageEditor.ELEMENT_PORTAL_PAGE_COMPONENT);

		info("--Edit container layout of current portal--");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		mouseOver(pageEditor.ELEMENT_BLANK_CONTAINER, true);
		mouseOver(pageEditor.ELEMENT_EDIT_BLANK_CONTAINER, true);
		click(pageEditor.ELEMENT_EDIT_BLANK_CONTAINER);

		waitForTextPresent("Container Setting");	
		type(pageEditor.ELEMENT_INPUT_WIDTH, "900px", true);
		type(pageEditor.ELEMENT_INPUT_HEIGHT, "600px", true);
		save();
		waitForTextNotPresent("Container Settings");
		click(ELEMENT_EDIT_LAYOUT_FINISH_BUTTON);
		waitForTextPresent(username);
		account.signOut();

		info("--Verify that changes on container is saved--");
		driver.get(baseUrl);
		account.signIn("root", "gtn");
		waitForTextPresent(username);
		captureScreen("case062_AfterChange");

		info("--Delete data--");
		navigation.goToPortalSites();
		click(editLayout);
		click(pageEditor.ELEMENT_TAB_CONTAINERS) ;
		mouseOver(pageEditor.ELEMENT_BLANK_CONTAINER, true);
		mouseOver(pageEditor.ELEMENT_DELETE_CONTAINER_ICON, true);
		click(pageEditor.ELEMENT_DELETE_CONTAINER_ICON);
		waitForConfirmation(pageEditor.MESSAGE_DELETE_CONTAINER);

		info("--SignOut--");
		click(ELEMENT_EDIT_LAYOUT_FINISH_BUTTON);
		waitForTextPresent(username);
		account.signOut();		
	}

	/*--Case 064 Portal\Portal Navigation\Edit\Edit Layout
	 * Check editing container with new valid title while edit current portal
	 * --*/
	@Test(groups={"later"})
	public void test21_CheckEditingContainerWithNewValidTitleWhileEditCurrentPorta(){

		account.signIn("root", "gtn");

		navigation.goToPortalSites();

		info("--Edit layout of current Portal--");
		String editLayout = naviManage.ELEMENT_EDIT_LAYOUT.replace("${navigation}", "intranet");
		click(editLayout);

		info("--Select Container tab on editing inline composer--");
		waitForTextPresent("Applications");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		waitForAndGetElement(pageEditor.ELEMENT_ROWS_LAYOUT);
		click(pageEditor.ELEMENT_ROWS_LAYOUT);	
		dragAndDropToObject(pageEditor.ELEMENT_ONE_ROW_LAYOUT, pageEditor.ELEMENT_PORTAL_PAGE_COMPONENT);

		info("--Edit container layout of current portal--");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		mouseOver(pageEditor.ELEMENT_BLANK_CONTAINER, true);
		mouseOver(pageEditor.ELEMENT_EDIT_BLANK_CONTAINER, true);
		click(pageEditor.ELEMENT_EDIT_BLANK_CONTAINER);

		info("--Edit current title with valid value--");
		waitForTextPresent("Container Setting");
		type(pageEditor.ELEMENT_CONTAINER_TITLE, "test21_CheckEditingContainer", true); //ELEMENT_INPUT_TITLE
		save();
		mouseOver(pageEditor.ELEMENT_BLANK_CONTAINER, true);
		waitForTextPresent("test21_CheckEditingContainer");

		info("--SignOut--");
		click(ELEMENT_ABORTEDIT_BUTTON);
		waitForAndGetElement(ELEMENT_CONFIRMATION);
		waitForTextPresent(pageEditor.MESSAGE_QUIT_EDIT_LAYOUT);
		waitForAndGetElement(ELEMENT_CONFIRMATION_YES_OPTION);
		click(ELEMENT_CONFIRMATION_YES_OPTION);
		waitForTextNotPresent(pageEditor.MESSAGE_QUIT_EDIT_LAYOUT);

		account.signOut();			
	}

	/*--Case 069 Portal\Portal Navigation\Edit\Edit Layout
	 *  Check Finish function after editing container
	 * --*/
	@Test
	public void test26_CheckFinishFunctionAfterEditingContainer(){
		account.signIn("root", "gtn");
		String username = "Root Root";
		waitForTextPresent(username);

		info("--View layout of portal before changing container in portal--");
		captureScreen("case069_BeforeChange");

		info("--Edit layout of current Portal--");
		navigation.goToPortalSites();
		String editLayout = naviManage.ELEMENT_EDIT_LAYOUT.replace("${navigation}", "intranet");
		click(editLayout);

		info("--Select Container tab on editing inline composer--");
		waitForTextPresent("Applications");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		waitForAndGetElement(pageEditor.ELEMENT_ROWS_LAYOUT);
		click(pageEditor.ELEMENT_ROWS_LAYOUT);	
		dragAndDropToObject(pageEditor.ELEMENT_ONE_ROW_LAYOUT, pageEditor.ELEMENT_PORTAL_PAGE_COMPONENT);
		/*click(ELEMENT_TAB_APPLICATIONS); 
		waitForTextPresent("Administration");
		click(ELEMENT_CONTENT_CATEGORY);
		dragAndDropToObject(ELEMENT_APPLICATION_CONTENT_LIST, ELEMENT_EMPTY_CONTAINER);*/

		info("--Edit container layout of current portal--");
		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		mouseOver(pageEditor.ELEMENT_BLANK_CONTAINER, true);
		mouseOver(pageEditor.ELEMENT_EDIT_BLANK_CONTAINER, true);
		click(pageEditor.ELEMENT_EDIT_BLANK_CONTAINER);
		waitForTextPresent("Container Setting");	
		type(pageEditor.ELEMENT_INPUT_WIDTH, "900px", true);
		save();
		click(ELEMENT_EDIT_LAYOUT_FINISH_BUTTON);
		waitForTextPresent(username);
		account.signOut();

		info("--Verify that changes on container are saved--");
		driver.get(baseUrl)  ;
		account.signIn("root", "gtn");
		waitForTextPresent(username);
		captureScreen("case026_AfterChange");

		info("--Delete data--");
		navigation.goToPortalSites();
		click(editLayout);
		click(pageEditor.ELEMENT_TAB_CONTAINERS) ;
		mouseOver(pageEditor.ELEMENT_BLANK_CONTAINER, true);
		mouseOver(pageEditor.ELEMENT_DELETE_CONTAINER_ICON, true);
		click(pageEditor.ELEMENT_DELETE_CONTAINER_ICON);
		waitForConfirmation(pageEditor.MESSAGE_DELETE_CONTAINER);

		info("--Sign out--");
		click(ELEMENT_EDIT_LAYOUT_FINISH_BUTTON);
		waitForTextPresent(username);
		account.signOut();
	}

	/*--Case 075 Portal\Portal Navigation\Edit\Edit Layout
	 *  Check Finish function after changing portlet layout
	 * --*/
	@Test(groups={"later"})
	public void test32_CheckFinishFunctionAfterChangingPortletLayout(){
		account.signIn("root", "gtn");
		String username = "Root Root";
		waitForTextPresent(username);

		info("--View layout of portal before changing portlet layout--");
		captureScreen("case32_BeforeChange");

		info("--Edit layout of current Portal--");
		navigation.goToPortalSites();
		String editLayout = naviManage.ELEMENT_EDIT_LAYOUT.replace("${navigation}", "intranet");
		click(editLayout);

		info("--Select application tab on edit inline composer --");
		waitForTextPresent("Administration") ;
		click(ELEMENT_ADMIN_CATEGORY);
		dragAndDropToObject(pageEditor.ELEMENT_APPLICATION_ACCOUNT, pageEditor.ELEMENT_PORTAL_PAGE_COMPONENT);

		click(pageEditor.ELEMENT_TAB_CONTAINERS);
		waitForAndGetElement(pageEditor.ELEMENT_ROWS_LAYOUT);
		click(pageEditor.ELEMENT_ROWS_LAYOUT);	
		dragAndDropToObject(pageEditor.ELEMENT_ONE_ROW_LAYOUT, pageEditor.ELEMENT_PORTAL_PAGE_COMPONENT);

		click(pageEditor.ELEMENT_TAB_APPLICATIONS); 	
		mouseOver(pageEditor.ELEMENT_APPLICATION_ACCOUNT, true);
		dragAndDropToObject(pageEditor.ELEMENT_APPLICATION_ACCOUNT_DRAG, pageEditor.ELEMENT_BLANK_CONTAINER);

		click(ELEMENT_EDIT_LAYOUT_FINISH_BUTTON);
		waitForTextPresent(username);
		account.signOut();

		info("--Verify changes on container are saved--");	
		driver.get(baseUrl);
		account.signIn("root", "gtn");
		waitForTextPresent(username);
		captureScreen("case32_AfterChange");

		info("--Delete data--");	
		navigation.goToPortalSites();
		click(editLayout);
		click(pageEditor.ELEMENT_TAB_CONTAINERS) ;
		mouseOver(pageEditor.ELEMENT_APPLICATION_ACCOUNT, true);
		click(pageEditor.ELEMENT_DELETE_CONTAINER_ICON);
		waitForConfirmation(MESSAGE_DELETE_CONTAINER);

		info("--Sign out--");
		click(ELEMENT_EDIT_LAYOUT_FINISH_BUTTON);
		waitForTextPresent(username);
		account.signOut();		
	}

	/*--Case 080 Portal\Portal Navigation\Edit\Edit Layout
	 *  Check when change width/height of portlet with valid value while editing portal
	 * --*/
	@Test(groups={"later"})
	public void test37_CheckWhenChangeWidthHeightOfPortletWithValidValueWhileEditingPortal(){
		account.signIn("root", "gtn");
		String username = "Root Root";
		waitForTextPresent(username);

		info("--View layout of portal before changing portlet layout--");
		captureScreen("case080_BeforeChange");

		info("--Edit width/height of portlet of current Portal--");
		navigation.goToPortalSites();
		String editLayout = naviManage.ELEMENT_EDIT_LAYOUT.replace("${navigation}", "intranet");
		click(editLayout);

		waitForTextPresent("Administration") ;
		click(ELEMENT_ADMIN_CATEGORY);
		dragAndDropToObject(pageEditor.ELEMENT_APPLICATION_ACCOUNT, pageEditor.ELEMENT_PORTAL_PAGE_COMPONENT);
		mouseOver(pageEditor.ELEMENT_APPLICATION_ACCOUNT, true);
		mouseOver(ELEMENT_EDIT_PORTLET_ICON, true);
		click(ELEMENT_EDIT_PORTLET_ICON);

		info("--Edit width/height--");
		waitForTextPresent("Portlet Setting");
		type(pageEditor.ELEMENT_INPUT_WIDTH, "300px", true) ;
		type(pageEditor.ELEMENT_INPUT_HEIGHT, "300px", true);
		saveAndClose();
		waitForTextNotPresent("Portlet Setting");

		info("--Switch view mode portal--");
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		waitForTextPresent("Applications");
		captureScreen("case37_EditWidthHeightOfPortlet");
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);

		info("--Change width/height of portlet with blank while editing portal--");

		mouseOver(pageEditor.ELEMENT_APPLICATION_ACCOUNT, true);
		mouseOver(ELEMENT_EDIT_PORTLET_ICON, true);
		click(ELEMENT_EDIT_PORTLET_ICON);
		waitForTextPresent("Portlet Setting");
		type(pageEditor.ELEMENT_INPUT_WIDTH, "", true);
		type(pageEditor.ELEMENT_INPUT_HEIGHT,"", true);
		saveAndClose();

		info("--Switch to view mode portal--");
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		waitForTextPresent("Applications");
		captureScreen("case37_DefaultWidthHeight");	
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);

		info("--SignOut--");
		click(ELEMENT_ABORTEDIT_BUTTON);
		waitForAndGetElement(ELEMENT_CONFIRMATION);
		waitForTextPresent(pageEditor.MESSAGE_QUIT_EDIT_LAYOUT);
		waitForAndGetElement(ELEMENT_CONFIRMATION_YES_OPTION);
		click(ELEMENT_CONFIRMATION_YES_OPTION);
		waitForTextNotPresent(pageEditor.MESSAGE_QUIT_EDIT_LAYOUT);

		account.signOut();	
	}

	/*--Case 092 Portal\Portal Navigation\Edit\Edit Layout
	 *  Check Finish function after editing portlet
	 * --*/
	@Test(groups={"later"})
	public void test49_CheckFinishFunctionAfterEditedPortlet(){
		account.signIn("root", "gtn");
		String username = "Root Root";
		waitForTextPresent(username);

		info("--View layout of portal before changing portlet layout--");
		captureScreen("case49_BeforeChange");

		info("--Edit width/height of portlet of current Portal--");
		navigation.goToPortalSites();
		String editLayout = naviManage.ELEMENT_EDIT_LAYOUT.replace("${navigation}", "intranet");
		click(editLayout);

		waitForTextPresent("Administration") ;
		click(ELEMENT_ADMIN_CATEGORY);
		dragAndDropToObject(pageEditor.ELEMENT_APPLICATION_ACCOUNT, pageEditor.ELEMENT_PORTAL_PAGE_COMPONENT);
		mouseOver(pageEditor.ELEMENT_ADDED_APPLICATION_ACCOUNT, true);
		mouseOver(pageEditor.ELEMENT_EDIT_PORTLET.replace("{$portlet}","Account Portlet"), true);
		click(pageEditor.ELEMENT_EDIT_PORTLET.replace("{$portlet}","Account Portlet"));

		info("--Edit width/height--");
		waitForTextPresent("Portlet Setting");
		type(pageEditor.ELEMENT_INPUT_WIDTH, "300px", true) ;
		type(pageEditor.ELEMENT_INPUT_HEIGHT, "300px", true);
		saveAndClose();
		waitForTextNotPresent("Portlet Setting") ;	
		click(ELEMENT_EDIT_LAYOUT_FINISH_BUTTON) ;
		waitForTextPresent(username);
		account.signOut();

		info("--Verify changes on portlet are saved--");	
		driver.get(baseUrl);
		account.signIn("root", "gtn");
		waitForTextPresent(username);
		captureScreen("case49_AfterChange");

		info("--Delete portlet--");	
		navigation.goToPortalSites();
		click(editLayout);
		waitForTextPresent("Applications") ;
		mouseOver(pageEditor.ELEMENT_ADDED_APPLICATION_ACCOUNT, true);
		click(ELEMENT_DELETE_PORTLET_ICON);
		waitForConfirmation(MESSAGE_DELETE_PORTLET);

		info("--Sign out--");
		click(ELEMENT_EDIT_LAYOUT_FINISH_BUTTON);
		waitForTextPresent(username);
		account.signOut();			
	}

	public void saveAndClose(){
		waitForAndGetElement(ELEMENT_SAVE_CLOSE_BUTTON);
		click(ELEMENT_SAVE_CLOSE_BUTTON);
	}
}