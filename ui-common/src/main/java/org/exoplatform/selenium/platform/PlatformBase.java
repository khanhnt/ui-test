package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.exoplatform.selenium.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;


public class PlatformBase extends TestBase {
	/*
	 * Default Page - http://localhost:8080/portal/default/
	 * */
	public final String ELEMENT_GO_TO_PORTAL = "//a[text()='Login to the ACME social intranet']";
	public final By ELEMENT_GO_TO_ACME = By.linkText("Login to the ACME website");
	/*
	 * Intranet
	 * */
	public final String ELEMENT_SIGN_IN_LINK = "//b[contains(text(),'Sign in')]";

	/*
	 * Log in Form - Sign-out 
	 */
	public final String ELEMENT_INPUT_USERNAME = "//input[@name='username']";
	public final String ELEMENT_INPUT_PASSWORD = "//input[@name='password']";

	/*
	 * Navigation Bar /Administration Bar
	 */

	//My site
	public final By ELEMENT_MYSITE = By.linkText("My Sites");
	public final By ELEMENT_MYSITE_ACME = By.linkText("acme");
	public final By ELEMENT_OVERVIEW = By.linkText("Overview");
	public final By ELEMENT_NEWS = By.linkText("News");
	public final By ELEMENT_INTRANET_SITE_LINK = By.linkText("intranet");

	public final By ELEMENT_PORTAL_CHANGE_LANGUAGE= By.xpath("//a[@class='LanguageIcon']");

	// My spaces
	// Dashboard

	/* Edit */
	public final By ELEMENT_MENU_EDIT_LINK = By.linkText("Edit");
	//Content
	public final By ELEMENT_MENU_EDIT_CONTENT = By.xpath("//a[@class='ItemIcon QuickEditUnchecked']");
	//Page
	public final By ELEMENT_MENU_PAGE_LINK = By.linkText("Page");
	public final By ELEMENT_MENU_EDIT_LAYOUT = By.linkText("Layout");
	public final By ELEMENT_MENU_SEO_LINK = By.xpath("//a[@title='SEO Management']");
	public final By ELEMENT_MENU_ADD_PAGE_LINK = By.linkText("Add Page");
	//site

	/* End Edit*/

	/* Setting Icon */
	public final String ELEMENT_LINK_SETUP ="//img[@alt='Setup']";
	//Users
	public final String ELEMENT_LINK_USERS ="//a[text()='Users']";
	public final String ELEMENT_LINK_ADD_USERS="//a[text()='Add Users']";
	//Application
	public By ELEMENT_APPLICATIONS_LINK = By.linkText("Applications");
	//Content
	public final By ELEMENT_MENU_CONTENT_LINK = By.linkText("Content");
	public final By ELEMENT_MENU_SITE_EXPLORER = By.linkText("Sites Explorer");
	public final By ELEMENT_LINK_CONTENT_ADMIN = By.linkText("Content administration");
	//Portal
	public final String ELEMENT_LINK_PORTAL = "//a[text()='Portal']";
	public final String ELEMENT_LINK_PAGES   = "//a[text()='Pages']";
	public final String ELEMENT_LINK_SITES   = "//a[text()='Sites']";
	public final String ELEMENT_LINK_GROUP = "//a[text()='Group Sites']";
	//Administration
	//IDE
	/* End Setting Icon*/

	/* Username link -BEGIN */
	//My Account form [Username] -> My Account
	public final String ELEMENT_CHANGE_PASSWORD_TAB = "//a[text()='Change Password' and @class='Icon ChangePass']";
	public final String ELEMENT_ACCOUNT_PROFILE_TAB = "//a[text()='Account Profiles' and @class='Icon AccountProfiles']";
	//Account Profile Tab

	//Change PasswordTab
	public final String ELEMENT_INPUT_CURRENTPASSWORD = "//input[@name='currentpass']";
	public final String ELEMENT_INPUT_NEW_PASSWORD_MYACCOUNT = "//input[@id='newpass']";
	public final String ELEMENT_INPUT_NEW_CONFIRM_PASSWORD_MYACCOUNT = "//input[@id='confirmnewpass']";
	//End My Account Form

	//Add user Form - (Setting -> User -> add User)
	public  final String ELEMENT_INPUT_FIRSTNAME_ADD = "//input[@id='firstName']";
	public  final String ELEMENT_INPUT_LASTNAME_ADD = "//input[@id='lastName']";
	public  final String ELEMENT_INPUT_EMAIL_ADD = "//input[@id='email']";
	//Account setting
	public  final String ELEMENT_ACCOUNT_SETTING_TAB = "//div[text()='Account Settings' and @class='MiddleTab']";
	public  final String ELEMENT_INPUT_CONFIRM_PASSWORD = "//input[@id='Confirmpassword']";
	public  final String ELEMENT_INPUT_NEW_PASSWORD = "//input[@id='newPassword']";
	public  final String ELEMENT_INPUT_NEW_CONFIRM_PASSWORD = "//input[@id='confirmPassword']";

	public  final String ELEMENT_INPUT_FIRSTNAME = "//form[@id='UIAccountForm']//input[@id='firstName']";
	public  final String ELEMENT_INPUT_LASTNAME = "//form[@id='UIAccountForm']//input[@id='lastName']";
	public  final String ELEMENT_INPUT_EMAIL = "//form[@id='UIAccountForm']//input[@id='email']";
	//User Profile
	public  final String ELEMENT_USER_PROFILE_TAB = "//a[text()='User Profile']";	
	public  final String ELEMENT_INPUT_USER_NAME_GIVEN = "//input[@id='user.name.given']";
	public  final String ELEMENT_SELECT_USER_LANGUAGE = "//select[@name='user.language']";

	//End User Profile
	//End - Add User Form
	//Setting -> user -> Groups and roles
	public final String ELEMENT_GROUP_AND_ROLE_LINK = "//a[contains(text(),'Groups and Roles')]";

	//Sign-out
	public  final String ELEMENT_ACCOUNT_NAME_LINK = "//*[@id='UserNavigationTabsContainer']/a";
	public  final String ELEMENT_SIGN_OUT_LINK = "//a[@class='LogoutIcon']";

	/* Username link - END*/

	/*
	 * Context menu
	 * */
	public final By ELEMENT_CUT_NODE = By.xpath("//a[contains(text(),'Cut')]"); 
	public final By ELEMENT_PASTE_NODE = By.xpath(".//*[@id='NavigationNodePopupMenu']//a[@class='ItemIcon PasteNode16x16Icon']");
	public final By ELEMENT_COPY_NODE = By.xpath("//a[contains(text(),'Copy')]");
	public final By ELEMENT_CLONE_NODE = By.xpath("//a[contains(text(),'Clone')]");
	public final By ELEMENT_EDIT_NODE_PAGE = By.xpath("//a[contains(text(),'Edit Node')]");
	public final By ELEMENT_NODE_EDIT_PAGE = By.xpath("//div[@id='NavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon EditPageNode16x16Icon']");
	/*
	 * User and Group Management
	 * */
	//User Management TAB
	public final String ELEMENT_USER_MANAGEMENT = "//div[@class='UserManagementIcon']/..";
	public final String ELEMENT_USER_DELETE_ICON ="//div[@id='UIListUsersGird']//div[text()='${username}']/../..//img[@class='DeleteUserIcon']";
	public final String ELEMENT_INPUT_SEARCH_USER_NAME = "//input[@name='searchTerm']";
	public final String ELEMENT_SEARCH_ICON_USERS_MANAGEMENT = "//form[@id='UISearchForm']/div[2]/a";

	//Group Management TAB
	public final String ELEMENT_GROUP_MANAGEMENT_TAB = "//div[@class='GroupManagementIcon']/..";
	public final String ELEMENT_GROUP_ADD_NEW_ICON = "//div[@id='UIOrganizationPortlet']//div[@class='TitleBar']/a[@class='TreeActionIcon AddGroupIcon']";
	public final String ELEMENT_GROUP_REMOVE_ICON = "//div[@id='UIOrganizationPortlet']//div[@class='TitleBar']/a[@class='TreeActionIcon RemoveGroupIcon']";
	public final String ELEMENT_GROUP_EDIT_ICON = "//div[@id='UIOrganizationPortlet']//div[@class='TitleBar']/a[@class='TreeActionIcon EditGroupIcon']";

	//Add Group Form
	public final String ELEMENT_INPUT_GROUP_NAME = "//input[@name='groupName']";
	public final String ELEMENT_INPUT_LABEL = "//input[@id='label']";
	public final String ELEMENT_TEXTAREA_DESCRIPTION = "//textarea[@id='description']";

	public final String ELEMENT_GROUP_SEARCH_USER_ICON = "//form[@id='UIGroupMembershipForm']/div[2]/div/table/tbody/tr[1]/td[2]/a";
	public final String ELEMENT_GROUP_SEARCH_POPUP_ADD_ICON = "//form[@id='UIUserSelector']//div[@class='UIAction']//a[@class='ActionButton LightBlueStyle']";
	public final String ELEMENT_SELECT_MEMBERSHIP = "//select[@name='membership']";
	public final String ELEMENT_GROUP_USER_IN_TABLE = "//div[@class='UIUserInGroup']//div[@title='${username}']";

	//Membership Management
	public final String ELEMENT_TAB_MEMBERSHIP_MANAGEMENT = "//div[@class='MembershipManagementIcon']/..";
	public final String ELEMENT_MEMBERSHIP_EDIT_ICON = "//div[@class='UIListMembershipType']//table//tr/td/div[text()='${membership}']/../../td[5]//img[@class='EditMembershipIcon']";
	public final String ELEMENT_MEMBERSHIP_DELETE_ICON = "//div[@class='UIListMembershipType']//table//tr/td/div[text()='${membership}']/../../td[5]//img[@class='DeleteMembershipIcon']";
	public final String ELEMENT_NEXT_PAGE_ICON = "//a[@title='Next Page']";
	public final String ELEMENT_INPUT_NAME = "//input[@id='name']";

	/*
	 * Manage Account
	 * */

	public  final String ELEMENT_SIGN_IN_CONFIRM_BUTTON = "//form[@id='UIPortalComponentLogin']//div[@class='UIAction']/*[@name='signIn']";
	public  final String ELEMENT_SELECT_SEARCH_OPTION = "//select[@name='searchOption']";
	public  final String ELEMENT_USER_EDIT_ICON = "//div[@id='UIListUsersGird']/table//tr/td/div[text()='${username}']/../../td[5]//img[@class='ViewUserInfoIcon']";
	public  final String ELEMENT_SEARCH_ICON_REGISTER = "//img[@class='SearchIcon']";
	public  final String ELEMENT_ADD_NEW_PORTAL_LINK = "//a[text()='Add New Portal']";	
	public  final String ELEMENT_CHECKBOX_SHOW_INFO_BAR_BY_DEFAULT = "//input[@name='showInfobar']";
	public  final String ELEMENT_PORTAL_IN_LIST = "//td[@class='Content']/div[@class='Label' and text()='${portalName}']";
	public  final String ELEMENT_PORTAL_DELETE_ICON = "//div[@id='UISiteManagement']//table//tr/td/div[text()='${portalName}']/../..//a[@class='DeleteIcon']";
	public  final String ELEMENT_PORTAL_EDIT_ICON = "//td[@class='Content']/div[@class='Label' and text()='${portalName}']/../../td[3]/a[@class='EditNavIcon'][2]";
	public  final String ELEMENT_EDIT_FIRST_PORTAL_CONFIG = "//div[@id='UISiteManagement']//a[@class='EditNavIcon'][2]";
	public  final String ELEMENT_SWITCH_VIEW_MODE_PORTAL = "//a[text()='Switch View Mode']";

	/* Add New Portal Form */
	//Portal Setting TAB
	public final String ELEMENT_SELECT_LOCALE = "//select[@name='locale']";
	public final String ELEMENT_SELECT_SKIN 	 = "//select[@name='skin']";

	//Property TAB
	public final String ELEMENT_SELECT_SESSION_ALIVE= "//select[@name='sessionAlive']"; 

	public final String ELEMENT_PROPERTIES_TAB = "//a[text()='Properties']";

	//Permission Setting TAB
	public  final By ELEMENT_PERMISSION_SETTING_TAB= By.linkText("Permission Setting");
	public  final String ELEMENT_CHECKBOX_PUBLIC_MODE = "//input[@name='publicMode']";
	public  final String ELEMENT_EDIT_PERMISSION_SETTING = "//a[text()='Edit Permission Setting']";
	public  final String ELEMENT_SELECT_ACCESS_MEMBERSHIP_ITEM = "//a[text()='${membership}']";
	public  final String ELEMENT_SELECTED_ACCESS_PERMISSION_GROUP = "//div[@id='PermissionGrid']/table/tbody//div[text()='/${groupId}']";
	public  final String ELEMENT_SELECTED_ACCESS_PERMISSION_MEMBERSHIP = "//div[@id='PermissionGrid']/table/tbody//div[text()='${membership}']";
	public  final String ELEMENT_ADD_PERMISSION_BUTTON = "//a[text()='Add Permission']";
	public  final String ELEMENT_SELECT_EDIT_MEMBERSHIP_ITEM = "//div[@id='UIPermissionSelector']//a[text()='${membership}']";
	public  final String ELEMENT_SELECTED_EDIT_PERMISSION_GROUP = "// div[@class='SelectedPermissionInfo']/div[2]/div[.='/${groupId}']";
	public  final String ELEMENT_SELECTED_EDIT_PERMISSION_MEMBERSHIP = "//div[@class='SelectedPermissionInfo']/div[3]/div[.='${membership}']";
	public  final String ELEMENT_SELECT_PERMISSION_BUTTON = "//a[text()='Select Permission']";
	public  final String ELEMENT_SELECT_ACCESS_GROUP_ITEM = ".//*[@id='ListPermissionSelector']//a[@title='${group}']";
	public  final String ELEMENT_SELECT_EDIT_GROUP_ITEM = "//div[@id='UIPermissionSelector']//a[text()='${group}']";
	public  final String ELEMENT_SELECT_EDIT_PORTAL_CONFIG = "//div[@id='UISiteManagement']//table//tr/td/div[text()='${portalName}']/../../td[2]//a[@class='EditPortIcon']";
	public final By ELELENT_LINK_DELETE_PERMISSION = By.linkText("Delete Permission");
	////
	public  final String ELEMENT_PAGINATOR_PAGE_LINK = "//a[contains(@class, 'Number') and text()='${number}']";
	public  final String ELEMENT_PAGINATOR_TOTAL_NUMBER = "//a[@class='PagesTotalNumber']";
	public  final String ELEMENT_PAGINATOR_NEXT_ICON = "//a[@class='Icon NextPageIcon']";
	public  final String ELEMENT_PAGINATOR_SELECTED_PAGE = "//a[@class='Number PageSelected' and text()='${number}']";
	public  final String ELEMENT_MESSAGE_TEXT = "//li[@class='MessageContainer']/span[contains(@class, 'PopupIcon')]";
	public  final String ELEMENT_MESSAGE_DIALOG_CLOSE_ICON_IE = ELEMENT_MESSAGE_TEXT + "/../../../../../..//a";
	public  final String ELEMENT_MESSAGE_DIALOG_CLOSE_ICON = "//div[contains(@class, 'UIPopupWindow') and contains(@style, 'visibility: visible')]//a[@title='Close Window']";

	/* Add New Page Form */
	public  final String ELEMENT_CHECKBOX_MAX_WINDOWS = "//input[@name='showMaxWindow']";
	public  final String ELEMENT_LINK_EDITOR = "//a[@class='EditorIcon TBIcon' and text() = 'Edit']";
	public  final String ELEMENT_LINK_EDITOR_PAGE = "//a[text()='Page']";
	public  final String ELEMENT_LINK_EDITOR_ADD_PAGE = "//a[text()='Add Page']";	
	public  final String ELEMENT_INPUT_NODE_NAME = "//input[@id='pageName']";
	public  final String ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE = "//input[@name='switchmode']";
	public  final String ELEMENT_LINK_EDITOR_PAGE_LAYOUT="//a[@class='ItemIcon EditPageIcon' and text()='Layout']";

	public  final String ELEMENT_SELECT_LANGUAGE = "//select[@name='languages']";
	public  final String ELEMENT_INPUT_PAGE_DISPLAY_NAME = "//td[contains(text(),'Display Name:')]/..//input";
	public  final String ELEMENT_PAGE_EDITOR_NEXT_STEP = "//div[@class='ActionBar']//a[text()='Next']";
	public  final String ELEMENT_EDIT_PAGE_CATEGORY_MENU = "//a[@title='${categoryLabel}']";
	public  final String ELEMENT_EDIT_PAGE_PAGE = "//div[@id='UIPage']";
	public  final String ELEMENT_PORTLET_LABEL = "//div[@class='CPortletLayoutDecorator' and contains(text(), '${portletName}')]";	
	public  final String ELEMENT_PAGE_FINISH_BUTTON = "//div[@id='UIPageEditor']//a[@title='Finish']";
	public  final By ELEMENT_PAGE_EDIT_FINISH = By.xpath("//a[@title='Finish']");
	public  final By ELEMENT_PAGE_CLOSE = By.xpath("//a[@title='Abort']");
	public  final String ELEMENT_OWNER_ID = "//*[@id='ownerId' and @value='{$page}']"; 
	public final String MESSAGE_DELETE_CONTAINER= "Are you sure you want to delete this portlet?";
	public final String MESSAGE_WARNING_CONTAINER = "You cannot delete the body container.";
	public final String MESSAGE_QUIT_EDIT_LAYOUT = "Modifications have been made. Are you sure you want to close without saving ?"; 
	//PortalNavigation - http://localhost:8080/portal/g/:platform:administrators/portalnavigation
	public  final String ELEMENT_NODE_LINK = "//div[@id='UINavigationNodeSelector']//a[@title='${nodeLabel}']";
	public  final String ELEMENT_EDIT_NAVIGATION = "//div[@class='Label' and text()='${navigation}']/../..//a[@class='EditNavIcon' and text()='Edit Navigation']";
	public  final String ELEMENT_ADD_NODE_LINK = "//a[text()='Add Node']";
	public  final String ELEMENT_PAGE_SELECTOR_TAB = "//a[text()='Page Selector']";
	public  final String ELEMENT_INPUT_PAGE_NAME = "//input[@id='pageName']";
	public  final String ELEMENT_INPUT_PAGE_TITLE = "//input[@id='pageTitle']";
	public  final String ELEMENT_CREATE_PAGE_LINK = "//a[text()='Create Page']";
	public  final String ELEMENT_SEARCH_SELECT_PAGE_LINK = "//a[text()='Search and Select Page']";
	public  final String ELEMENT_CLEAR_PAGE_LINK = "//a[text()='Clear Page']";

	public  final String ELEMENT_SELECT_HOME_PAGE = "//div[@id='UIRepeater']//table//tbody/tr/td[5]/div[@class='ActionContainer']/img";
	public  final String ELEMENT_NAVIGATION_HOME_NODE = "//div[@class='HomeNode']";				 
	public  final String ELEMENT_NODE_ADD_NEW_TOP_NODE = "//div[@id='UINavigationNodeSelectorPopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon AddNode16x16Icon']";
	public  final String ELEMENT_NODE_ADD_NEW = "//div[@id='NavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon AddNode16x16Icon']";
	public  final String ELEMENT_NODE_DELETE = "//div[@id='NavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon DeleteNode16x16Icon']";
	public  final String ELEMENT_NODE_EDIT = "//div[@id='NavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon EditSelectedNode16x16Icon']";
	//	public  final String ELEMENT_NAVIGATION_NODE_AREA= "//div[@class='Node']"; 

	/*
	 * END Page Management
	 * */

	/*
	 * General
	 * */

	public  final By ELEMENT_SAVE_CLOSE_BUTTON = By.linkText("Save & Close");
	public  final By ELEMENT_OK_BUTTON = By.xpath("//a[text()='OK']");
	public  final By ELEMENT_APPLY_BUTTON = By.linkText("Apply");
	public  final By ELEMENT_SAVE_BUTTON = By.xpath("//*[text()='Save']");
	public  final By ELEMENT_CANCEL_BUTTON = By.linkText("Cancel");
	public  final By ELEMENT_CLOSE_BUTTON = By.linkText("Close");
	public  final By ELEMENT_CLOSE_WINDOW = By.xpath("//a[@title='Close Window']");
	public  final By ELEMENT_FINISH_ICON = By.xpath("//a[@title='Finish']"); //Finish editing portlet icon
	public  final By ELEMENT_NEXT_BUTTON = By.linkText("Next");	
	public  final By ELEMENT_ABORT_BUTTON = By.xpath("//a[text()='Abort']");

	/*Portlet in general*/
	public final By ELEMENT_EDIT_PORTLET_ICON = By.xpath("//a[@title='Edit Portlet']");
	public final By ELEMENT_DELETE_PORTLET_ICON = By.xpath("//a[@title='Delete Portlet']");
	public final By ELEMENT_PORTLET_CONTAINER = By.className("PortletLayoutDecorator");
	public final By ELEMENT_ABORTEDIT_BUTTON = By.xpath("//a[@title='Abort']");
	
	public final String MESSAGE_DELETE_PORTLET = "Are you sure you want to delete this portlet?";
	//Edit portlet Form
	public final By ELEMENT_WINDOW_SETTINGS_TAB = By.xpath("//div[@id='UIMaskWorkspace']//div[text()='Window Settings']");
	public final By ELEMENT_WINDOWS_TITLE = By.xpath("//*[@id='title']");
	/*Portlet in general*/

	/* End General
	 * */

	//Account Portlet
	public final By ELEMENT_REGISTER_ACCOUNT_PORTLET = By.className("PortletLayoutDecorator");
	public final By ELEMENT_CHECK_BOX_USE_CAPTCHA = By.id("useCaptcha");
	public final By ELEMENT_EDIT_LAYOUT_FINISH_BUTTON = By.xpath("//div[@id='UIPortalComposer']//a[@class='EdittedSaveButton']");
	public final By ELEMENT_PAGE_FINISH_BUTTON_INFRENCH = By.xpath("//div[@id='UIPageEditor']//a[@title='Terminer']");
	public final By ELEMENT_EDIT_ACCOUNT_PORTLET_ICON_INFRENCH = By.xpath("//a[@title='Editer la Portlet']");
	public final By ELEMENT_ERROR_ICON=By.xpath("//span[@class='PopupIcon ErrorMessageIcon']");
	/* End General
	 * */

	//Content Administration / Advanced Configuration 
	public final By ELEMENT_ADVANCED_CONFIGURATION_TAB = By.xpath("//div[@class = 'TabLabel' and @title = 'Advanced Configuration']");
	public final By ELEMENT_MANAGE_LOCK_TAB = By.xpath("//div[@class = 'MiddleTab' and text() = 'Manage Lock']");
	public final By ELEMENT_MANAGE_LOCKS = By.linkText("Manage Locks");

	//Edit In-line a SCV
	public final By ELEMENT_PREFERENCE_TITLE=By.xpath("//span[contains(text(),'Content Detail Preferences')]");
	public final By ELEMENT_CONTAINER_CONTENT = By.xpath("//div[@class='NavigationContainer']");
	public final By ELEMENT_BUTTON_BACK = By.xpath("//a[@class='URLBackToButton']");

	//Others
	//Content template > upload
	public final By ELEMENT_UPLOAD_LINK_XPATH = By.xpath("//a[@title='Upload']");
	public final By ELEMENT_UPLOAD_FILE_NAME_ID = By.id("name");
	public final By ELEMENT_UPLOAD_IMG_FRAME_XPATH = By.xpath("//iframe[contains(@id,'uploadFrame')]");
	public final By ELEMENT_UPLOAD_IMG_ID = By.id("file");

	//Space > Wiki link
	public final By ELEMENT_WIKI_LINK_IN_SPACE = By.xpath("//*[@id='spaceMenuTab']/li[3]/a/span[text()='Wiki']");

	///////////////////
			public PlatformBase(){
		this.baseUrl = super.baseUrl;
	}

	//Set view permissions for portal
	public void setViewPermissions(String groupId, String membership) {
		String membershipToSelect = ELEMENT_SELECT_ACCESS_MEMBERSHIP_ITEM.replace("${membership}", membership);
		//String selectedGroup = ELEMENT_SELECTED_ACCESS_PERM_GROUP.replace("${groupId}", groupId.replace(" ", "-").toLowerCase());
		String selectedMembership = ELEMENT_SELECTED_ACCESS_PERMISSION_MEMBERSHIP.replace("${membership}", membership);

		info("--Setting view permission to " + groupId + ", " + membership + "--");
		String[] groups = groupId.split("/");
		pause(500);
		click(ELEMENT_ADD_PERMISSION_BUTTON);
		waitForTextPresent("Group and Membership Selector");
		for (String group : groups) {
			String groupToSelect = ELEMENT_SELECT_ACCESS_GROUP_ITEM.replace("${group}", group);
			click(groupToSelect);
		}
		pause(500);
		click(membershipToSelect);
		pause(500);
		waitForTextNotPresent("Select Permission");
		//waitForAndGetElement(selectedGroup);
		waitForAndGetElement(selectedMembership);
	}

	//Set edit permissions for portal
	public void setEditPermissions(String groupId, String membership) {
		String membershipToSelect = ELEMENT_SELECT_EDIT_MEMBERSHIP_ITEM.replace("${membership}", membership);
		//String selectedGroup = ELEMENT_SELECTED_EDIT_PERM_GROUP.replace("${groupId}", groupId.replace(" ", "-").toLowerCase());
		String selectedMembership = ELEMENT_SELECTED_EDIT_PERMISSION_MEMBERSHIP.replace("${membership}", membership);

		info("--Setting edit permission to " + groupId + ", " + membership + "--");
		String[] groups = groupId.split("/");
		click(ELEMENT_SELECT_PERMISSION_BUTTON);
		pause(500);
		waitForTextPresent("Permission Selector");
		for (String group : groups) {
			String groupToSelect = ELEMENT_SELECT_EDIT_GROUP_ITEM.replace("${group}", group);
			click(groupToSelect);
		}
		click(membershipToSelect);
		waitForTextNotPresent("Permission Selector");
		//waitForAndGetElement(selectedGroup);
		waitForAndGetElement(selectedMembership);
	}

	//Define a type of page
	public enum PageType {
		PORTAL, GROUP;
	}

	//Link to Edit a navigation
	public void editNavigation(String currentNavigation) {
		String navigation = ELEMENT_EDIT_NAVIGATION.replace("${navigation}", currentNavigation);
		click(navigation);
		waitForTextPresent("Navigation Management");
	}

	//Close message pop-up
	public void closeMessageDialog() {
		info("--Closing message dialog--");
		if (ieFlag) {
			click(ELEMENT_MESSAGE_DIALOG_CLOSE_ICON_IE);
		} else {
			click(ELEMENT_MESSAGE_DIALOG_CLOSE_ICON);
		}
	}

	//Copy value from Source and paste to Target
	public void copyPaste(Object Source, String value, Object Target){ 	
		Actions actions = new Actions(driver);
		WebElement element = waitForAndGetElement(Source);
		element.sendKeys(value);
		actions.doubleClick(element).perform();
		element.sendKeys(Keys.LEFT_CONTROL + "a");
		element.sendKeys(Keys.LEFT_CONTROL + "c");
		pause(3000);
		WebElement b = waitForAndGetElement(Target);
		b.sendKeys(Keys.LEFT_CONTROL + "v");
	}

	//Go to the desired locator
	public void goToPage(String verification, String... navigation) {
		Actions actions = new Actions(driver);
		String page = makeLink(navigation[navigation.length - 1]);
		boolean needToBeVerified = true;

		List<String> navigationList = new ArrayList<String>();

		for (int i = 0; i < (navigation.length - 1); i++) {
			String node = navigation[i];
			node = makeLink(node);
			navigationList.add(node);
		}

		try {
			for (String node : navigationList) {
				if (ieFlag) {
					actions.moveToElement(getElement(node));
				} else {
					mouseOver(node, false);
				}
			}
			mouseOverAndClick(page);
		} catch (StaleElementReferenceException e) {
			debug("StaleElementReferenceException, Retrying... :" + loopCount + "time(s)");
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			goToPage(verification, navigation);
			needToBeVerified = false;
		} finally {
			loopCount = 0;
		}

		if (verification != null && needToBeVerified) {
			pause(500);
			verifyLocation(verification, navigationList, page);
		}
	}

	private String makeLink(String node) {
		if (!node.contains("//")) {
			String label = node;
			node = "//a[text()='" + label + "']";
		}
		return node;
	}

	private void verifyLocation(String locator, List<String> navigation, String page) {
		Actions actions = new Actions(driver);
		info("verifyLocation, element: " + locator);
		int seconds = 0;
		if (isElementNotPresent(locator)) {
			pause(1000);
		}
		for (; isElementNotPresent(locator); seconds++) {
			if (seconds >= (DEFAULT_TIMEOUT/WAIT_INTERVAL) ) {
				Assert.fail("Timeout at goToPage");
			}
			pause(500);
			try {
				for (String node : navigation) {
					if (ieFlag) {
						actions.moveToElement(getElement(locator));
					} else {
						mouseOver(node, false);
					}
				}
				mouseOverAndClick(page);
			} catch (StaleElementReferenceException e) {
				debug("StaleElementReferenceException, Retrying... :" + loopCount + "time(s)");
				checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
				verifyLocation(locator, navigation, page);
				break;
			} finally {
				loopCount = 0;
			}
		}
		seconds = 0;
	}

	public void usePaginator(Object locator, String exceptionMessage) {
		String page1 = ELEMENT_PAGINATOR_PAGE_LINK.replace("${number}", "1");

		if (waitForAndGetElement(page1, 5000, 0) != null){
			click(page1);
			pause(500);
			int totalPages = isElementPresent(ELEMENT_PAGINATOR_TOTAL_NUMBER) ? Integer.valueOf(getText(ELEMENT_PAGINATOR_TOTAL_NUMBER)) : 1;
			int i = 1;
			while (isElementNotPresent(locator)) {
				if (i == totalPages) {
					//Assert.fail(exceptionMessage);
					info(exceptionMessage);
					break;
				}
				click(ELEMENT_PAGINATOR_NEXT_ICON);
				waitForAndGetElement(ELEMENT_PAGINATOR_SELECTED_PAGE.replace("${number}", String.valueOf((++i))));
				pause(500);
			}
		}
	}

	public void usePaginator(By locator, String exceptionMessage) {
		String page1 = ELEMENT_PAGINATOR_PAGE_LINK.replace("${number}", "1");

		click(page1);
		pause(500);
		int totalPages = isElementPresent(ELEMENT_PAGINATOR_TOTAL_NUMBER) ? Integer.valueOf(getText(ELEMENT_PAGINATOR_TOTAL_NUMBER)) : 1;
		int i = 1;
		while (isElementNotPresent(locator)) {
			if (i == totalPages) {
				Assert.fail(exceptionMessage);
			}
			click(ELEMENT_PAGINATOR_NEXT_ICON);
			waitForAndGetElement(ELEMENT_PAGINATOR_SELECTED_PAGE.replace("${number}", String.valueOf((++i))));
			pause(500);
		}
	}

	public void save() {
		waitForAndGetElement(ELEMENT_SAVE_BUTTON);
		click(ELEMENT_SAVE_BUTTON);
	}

	public void close(){
		waitForAndGetElement(ELEMENT_CLOSE_BUTTON);
		click(ELEMENT_CLOSE_BUTTON);
	}

	public void cancel(){
		waitForAndGetElement(ELEMENT_CANCEL_BUTTON);
		click(ELEMENT_CANCEL_BUTTON);
	}

	public void saveAndClose(){
		waitForAndGetElement(ELEMENT_SAVE_CLOSE_BUTTON);
		click(ELEMENT_SAVE_CLOSE_BUTTON);
	}

	public void apply(){
		waitForAndGetElement(ELEMENT_APPLY_BUTTON);
		click(ELEMENT_APPLY_BUTTON);
	}

	// Check UnexpectedError
	public boolean checkUnexpectedError() {
		try {
			driver.findElement(ELEMENT_ERROR_ICON);
			return false;

		} catch (NoSuchElementException e) {
			return true;
		}
	}

	public void cloneNode(By locator)	{
		for (int i =0;; i++){
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL) {
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_CLONE_NODE,30000,0)!=null){
				click((ELEMENT_CLONE_NODE));
				return;
			}
			pause(WAIT_INTERVAL);
		}
	}

	public void cutNode(By locator)	{
		for (int i =0;; i++){
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL) {
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_CUT_NODE, 5000, 0)!=null){
				debug("==Cut node " + locator + "==");
				click((ELEMENT_CUT_NODE));
				return;
			}
			pause(WAIT_INTERVAL);
		}
	}

	public void copyNode(By locator){
		for (int i =0;; i++){
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL) {
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_COPY_NODE, 5000, 0) != null){
				click((ELEMENT_COPY_NODE));
				return;
			}
			pause(WAIT_INTERVAL);
		}
	}

	public void pasteNode(By locator) {
		for (int i =0;; i++){
			if (i>DEFAULT_TIMEOUT/WAIT_INTERVAL){
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_PASTE_NODE, 5000, 0) != null){
				click(ELEMENT_PASTE_NODE);
				return;
			}
			pause(WAIT_INTERVAL);
		}
	}

	//Set to use captcha when registry a new account in public mode
	public void setUseCaptcha(boolean useCaptcha, boolean useFrench){
		waitForTextPresent("Register Account");
		mouseOver(ELEMENT_REGISTER_ACCOUNT_PORTLET, true);
		if (useFrench){
			mouseOverAndClick(ELEMENT_EDIT_ACCOUNT_PORTLET_ICON_INFRENCH);
			waitForTextPresent("Mode d'édition");
		}else{
			mouseOverAndClick(ELEMENT_EDIT_PORTLET_ICON);
			waitForTextPresent("Edit Mode");
		}

		WebElement element = waitForAndGetElement(ELEMENT_CHECK_BOX_USE_CAPTCHA);
		if (useCaptcha){
			if(!element.isSelected()){
				check(ELEMENT_CHECK_BOX_USE_CAPTCHA);
			}
		}
		else{
			if(element.isSelected()){
				uncheck(ELEMENT_CHECK_BOX_USE_CAPTCHA);
			}
		}
		save();
		close();
		if (useFrench){
			click(ELEMENT_PAGE_FINISH_BUTTON_INFRENCH);
			waitForTextNotPresent("Editeur de page");
		}else{
			click(ELEMENT_PAGE_FINISH_BUTTON);
			waitForTextNotPresent("Page Editor");
		}
	}

	public void next(){
		waitForAndGetElement(ELEMENT_NEXT_BUTTON);
		click(ELEMENT_NEXT_BUTTON);	
	}

	/**
	 *
	 * Get cookies of current browser then delete all cookies
	 * @return set of cookies of browser
	 */
	public Set<Cookie> getBrowserCookies(){
		Set<Cookie> cookies = driver.manage().getCookies();
		driver.manage().deleteAllCookies();
		return cookies;
	}

	/**
	 * Set cookies for current browser
	 * @param cookies : Set of cookies
	 */
	public void setBrowserCookies(Set<Cookie> cookies){
		for(Cookie cookie : cookies){
			driver.manage().addCookie(cookie);
		}
	}

	/**
	 * Add by @author vuna2
	 * Open a new browser by Javascript
	 */
	public void openNewBrowser(){
		//Open new browser by Javascript
		//String handlesBefore = driver.getWindowHandle();
		((JavascriptExecutor) driver).executeScript("window.open()");
		//driver.manage().deleteAllCookies();
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
		driver.navigate().to(baseUrl);
	}

	/**
	 * Add by @author vuna2
	 * @param cookies: Set of cookies (browsers)
	 * @param handlesBefore: handle the current browser
	 */
	public void backToPreviousBrowser(Set<Cookie> cookies, String handlesBefore){
		driver.manage().deleteAllCookies();

		//Add cookies back to previous browser
		setBrowserCookies(cookies);

		//Switch back to previous browser
		driver.switchTo().window(handlesBefore);
	}

	//Function to add data to frame
	public void inputDataToFrame (By framelocator, String data, boolean...validate){
		try {
			WebElement inputsummary = null;

			for (int repeat = 0;; repeat++) {
				if (repeat >= DEFAULT_TIMEOUT/WAIT_INTERVAL) {
					Assert.fail("Fail to input data to frame " + framelocator);
				}
				driver.switchTo().frame(waitForAndGetElement(framelocator));
				inputsummary = driver.switchTo().activeElement();

				inputsummary.click();

				if (validate.length >0)
					if (validate[0]){
						((JavascriptExecutor) driver).executeScript("document.body.innerHTML='" + data + "'");
						if (data.equals(inputsummary.getText())) break;
					}
					else{
						inputsummary.sendKeys(data);
						break;
					}
				else {
					inputsummary.sendKeys(data);
					if (data.equals(inputsummary.getText())) break;
				}

				switchToParentWindow();
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			inputDataToFrame (framelocator, data);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			inputDataToFrame (framelocator,data);
		}catch (WebDriverException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			inputDataToFrame (framelocator,data);
		}
		finally {
			loopCount = 0;
		}
	}

	// Select option from combo box
	public void selectOption(Object locator, String option) {
		try {
			for (int second = 0;; second++) {
				if (second >= DEFAULT_TIMEOUT/WAIT_INTERVAL) {
					Assert.fail("Timeout at select: " + option + " into " + locator);
				}
				Select select = new Select(waitForAndGetElement(locator));
				select.selectByValue(option);
				if (option.equals(select.getFirstSelectedOption().getAttribute("value"))) {
					break;
				}
				pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			select(locator, option);
		} finally {
			loopCount = 0;
		}
	}

	////////
	//Common code for test cases using 2 popup windows / browsers
	////////
	/**
	 * Add by @author vuna2
	 * <li> Switch to a new browser/ Popup window</li> 
	 */
	public void switchToNewWindow(){
		Set<String> windowids = driver.getWindowHandles(); 
		Iterator<String> iter= windowids.iterator();
		while(iter.hasNext()) {
			String windowHandle = iter.next(); 
			driver.switchTo().window(windowHandle);
		} 
	}

	/**
	 * Add by @author vuna2
	 * @param previousWindowHandle: handle the previous (current) browser (String)
	 */
	/*public void backToPreviousBrowser(String previousWindowHandle){
		// Close the popup window
		driver.close(); 
		// Switch back to previous window.
		driver.switchTo().window(previousWindowHandle);
	}*/	  

	/////////
	//
}
