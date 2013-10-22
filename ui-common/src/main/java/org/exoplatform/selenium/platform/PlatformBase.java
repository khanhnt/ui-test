package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.debug;
import static org.exoplatform.selenium.TestLogger.info;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.TestBase;
import org.exoplatform.selenium.Utils;
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

	public final String DATA_USER1 = "john";
	public final String DATA_PASS = "gtn";
	public final String DATA_USER2 = "mary";
	
	public ManageAlert alert = new ManageAlert(driver);
	public Button button = new Button(driver);

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
	public final By ELEMENT_INPUT_USERNAME = By.name("username"); 
	//"//input[@name='username']";
	public final By ELEMENT_INPUT_PASSWORD = By.name("password");
	//"//input[@name='password']";
	/*
	 * Company Navigation
	 */
	public final By ELEMENT_PERSONAL_DOCUMENTS = By.className("uiIconPLFDocuments");
	public final By ELEMENT_HOME_PAGE = By.className("uiIconHome");
	public final By ELEMENT_CONNECTION_PAGE = By.className("uiIconUser");

	/*
	 * Navigation Bar /Administration Bar
	 */

	//My site
	public final By ELEMENT_MYSITE = By.linkText("My Sites");
	public final By ELEMENT_MYSITE_ACME = By.linkText("acme");
	public final By ELEMENT_OVERVIEW = By.xpath("//*[@class='uiCompanyNavigations']//*[contains(text(), 'Overview')]");
	//By.linkText("Overview");
	public final By ELEMENT_NEWS = By.linkText("News");
	public final By ELEMENT_INTRANET_SITE_LINK = By.linkText("intranet");

	// My spaces
	// Dashboard

	/* Edit */
	public final By ELEMENT_MENU_EDIT_LINK = By.linkText("Edit");
	//Content
	public final By ELEMENT_MENU_EDIT_CONTENT = By.xpath("//i[contains(@class,'quickEdit')]"); 
	//By.className("quickEditUnchecked");
	//By.xpath("//a[@class='ItemIcon QuickEditUnchecked']");

	//Page
	public final By ELEMENT_MENU_PAGE_LINK = By.linkText("Page");
	public final By ELEMENT_MENU_EDIT_LAYOUT = By.linkText("Edit Layout");
	public final By ELEMENT_MENU_SEO_LINK = By.xpath("//span[text()='SEO']");
	public final By ELEMENT_MENU_ADD_PAGE_LINK = By.linkText("Add Page");
	//site

	/* End Edit*/

	/* Setting Icon */
	public final String ELEMENT_LINK_SETUP = "//*[@id='UISetupPlatformToolBarPortlet']/a"; 
	//"//img[@alt='Setup']";
	//Users
	public final String ELEMENT_LINK_USERS ="//a[text()='Users']";
	public final String ELEMENT_LINK_ADD_USERS="//a[text()='Add Users']";
	//Application
	public By ELEMENT_APPLICATIONS_LINK = By.linkText("Applications");
	//Content
	public final By ELEMENT_MENU_CONTENT_LINK = By.xpath("//*[text()= 'Content']");
	public final By ELEMENT_MENU_SITE_EXPLORER = By.xpath("//*[text()='Sites Explorer']");
	public final By ELEMENT_SITE_EXPLORER_HOME = By.className("uiIconEcmsHome");
	//By.linkText("Sites Explorer");
	public final By ELEMENT_LINK_CONTENT_ADMIN = By.xpath("//*[text()='Content Administration']");
	//By.linkText("Content administration");
	public final String ELEMENT_DATA_ORIGINAL_TITLE ="//*[@data-original-title='${title}']";

	//Portal
	public final String ELEMENT_LINK_PORTAL = "//a[text()='Portal']";
	public final String ELEMENT_LINK_PAGES   = "//a[text()='Pages']";
	public final String ELEMENT_LINK_SITES   = "//a[text()='Sites']";
	public final String ELEMENT_LINK_GROUP = "//a[text()='Group Sites']";
	public final String ELEMENT_LINK_BRANDING = "//a[text()='Branding']";
	//Administration
	//IDE
	/* End Setting Icon*/
	/*--------------- User account Management (Click from user name) ---------------------*/
	public final By ELEMENT_ACCOUNT_NAME_LINK = By.xpath("//*[@id='UIUserPlatformToolBarPortlet']/a");
	public final By ELEMENT_SIGN_OUT_LINK = By.className("uiIconPLFLogout");
	public final By ELEMENT_CHANGE_LANGUAGE_LINK = By.xpath("//a[text()='Change Language']");
	public final By ELEMENT_CHANGE_LANGUAGE_LINK_FRENCH = By.xpath("//a[text()='Changer de Langue']");
	public final By ELEMENT_MY_PROFILE_LINK = By.xpath("//i[@class='uiIconPLFProfile']/..");
	public final By ELEMENT_DASHBROARD_LINK = By.className("uiIconPLFDashboard");
	public final By ELEMENT_MY_SETTING = By.linkText("Settings");

	//User -> Change Language
	public final By ELEMENT_CHANGE_LANGUAGE_POPUP = By.xpath("//*[@id='UIMaskWorkspace']//*[text()='Interface Language Setting']");
	public final By ELEMENT_CHANGE_LANGUAGE_POPUP_FRENCH = By.xpath("//*[@id='UIMaskWorkspace']//*[text()='Langues Disponibles']");

	//User -> My profile
	public final By ELEMENT_EDIT_POSITION = By.xpath("//*[@id='UIHeaderSection']//*[@class='uiIconEdit']");
	public final By ELEMENT_POSITION_TEXTBOX_EDIT = By.id("position");
	public final By ELEMENT_EDIT_POSITION_SAVE_BUTTON = By.id("savePosition");
	public final By ELEMENT_EDIT_BASIC_INFORMATION = By.xpath("//*[@id='UIBasicInfoSection']//*[@class='uiIconEdit']");
	public final By ELEMENT_FIRST_NAME_TEXTBOX_EDIT = By.id("firstName");
	public final By ELEMENT_LAST_NAME_TEXTBOX_EDIT = By.id("lastName");
	public final By ELEMENT_EMAIL_TEXTBOX_EDIT = By.id("email");
	public final By ELEMENT_EDIT_BASIC_INFO_SAVE_BUTTON = By.xpath("//*[@id='UIBasicInfoSection']//button[contains(text(), 'Save')]");

	//User-> Setting
	public final By ELEMENT_CHANGE_PASSWORD_TAB = By.linkText("Change Password");
	public final By ELEMENT_ACCOUNT_PROFILE_TAB = By.linkText("Account Profiles");
	public final By ELEMENT_INPUT_CURRENTPASSWORD = By.id("currentpass");
	public final By ELEMENT_INPUT_NEW_PASSWORD_MYACCOUNT = By.id("newpass");
	public final By ELEMENT_INPUT_NEW_CONFIRM_PASSWORD_MYACCOUNT = By.id("confirmnewpass");
	public final By ELEMENT_SAVE_CHANGE_PASS_BUTTON = By.xpath("//*[@id='UIAccountChangePass']//button[text()='Save']");
	public final String MESSAGE_UPDATE_ACCOUNT = "The account information has been updated.";
	public final String MESSAGE_UPDATE_PASSWORD = "The password has been changed.";

	//Add user Form - (Setting -> User -> add User)
	//Account setting
	public final By ELEMENT_ACCOUNT_SETTING_TAB = By.xpath("//*[text()='Account Setting' and @data-toggle='tab']");
	public final By ELEMENT_INPUT_CONFIRM_PASSWORD = By.id("Confirmpassword");
	public final By ELEMENT_INPUT_NEW_PASSWORD = By.id("newPassword");
	public final By ELEMENT_INPUT_NEW_CONFIRM_PASSWORD = By.id("confirmPassword");
	public final By ELEMENT_INPUT_FIRSTNAME = By.id("firstName");
	public final By ELEMENT_INPUT_LASTNAME = By.id("lastName");
	public final By ELEMENT_INPUT_DISPLAY_NAME = By.id("displayName");
	public final By ELEMENT_INPUT_EMAIL = By.id("email");

	//User Profile
	public final By ELEMENT_USER_PROFILE_TAB = By.xpath("//*[text()='User Profile' and @data-toggle='tab']");
	public final By ELEMENT_INPUT_USER_NAME_GIVEN = By.name("user.name.given");
	public final By ELEMENT_SELECT_USER_LANGUAGE = By.name("user.language");

	//End User Profile
	//End - Add User Form
	//Setting -> user -> Groups and roles
	public final String ELEMENT_GROUP_AND_ROLE_LINK = "//a[contains(text(),'Groups and Roles')]";

	/* Username link - END*/

	/*
	 * Context menu
	 * */
	public final By ELEMENT_CUT_NODE = By.className("uiIconEcmsCut");
			//By.xpath("//*[@class='uiContextMenuContainer']//*[@class='uiIconEcmsCut']"); 
	public final By ELEMENT_PASTE_NODE = By.className("uiIconEcmsPaste");
			//By.xpath("//*[@class='uiContextMenuContainer']//*[@class='uiIconEcmsPaste']"); 
	public final By ELEMENT_COPY_NODE = By.className("uiIconEcmsCopy");
			//By.xpath("//*[@class='uiContextMenuContainer']//*[@class='uiIconEcmsCopy']"); 
	public final By ELEMENT_CLONE_NODE = By.xpath("//a[contains(text(),'Clone')]");
	public final By ELEMENT_EDIT_NODE_PAGE = By.className("uiIconEcmsEditDocument");
	public final By ELEMENT_NODE_EDIT_PAGE = By.xpath("//div[@id='NavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon EditPageNode16x16Icon']");
	
	/*
	 * User and Group Management
	 * */
	//User Management TAB
	public final String ELEMENT_USER_MANAGEMENT = "//div[@class='UserManagementIcon']/..";
	public final String ELEMENT_USER_DELETE_ICON ="//*[@id='UIListUsersGird']//*[text()='${username}']/../..//*[@data-original-title='Delete User']";
	public final String ELEMENT_INPUT_SEARCH_USER_NAME = "//input[@name='searchTerm']";
	public final String ELEMENT_SEARCH_ICON_USERS_MANAGEMENT = "//form[@id='UISearchForm']/div[2]/a";
	public final String ELEMENT_USER_EDIT_ICON = "//*[@id='UIListUsersGird']//*[text()='${username}']/../..//*[@data-original-title='Edit User Info']";

	//Group Management TAB
	public final By ELEMENT_GROUP_MANAGEMENT_TAB = By.xpath("//*[contains(@class, 'uiIconGroup')]");
	//"//div[@class='GroupManagementIcon']/..";
	public final By ELEMENT_GROUP_ADD_NEW_ICON = By.xpath("//*[@id='UIOrganizationPortlet']//*[@data-original-title='Add New Group']");
	public final By ELEMENT_GROUP_REMOVE_ICON = By.xpath("//*[@id='UIOrganizationPortlet']//*[@data-original-title='Delete Selected Group']");
	public final By ELEMENT_GROUP_EDIT_ICON = By.xpath("//*[@id='UIOrganizationPortlet']//*[@data-original-title='Edit Selected Group']");

	//Add Group Form
	public final By ELEMENT_INPUT_GROUP_NAME = By.name("name");
	public final By ELEMENT_INPUT_GROUP_NAME_2 = By.name("groupName");
	public final By ELEMENT_INPUT_LABEL = By.id("label");
	public final By ELEMENT_INPUT_LABEL_1 = By.xpath("//*[contains(@id, 'Label')]");
	public final String ELEMENT_TEXTAREA_DESCRIPTION = "//textarea[@id='description']";

	public final By ELEMENT_GROUP_SEARCH_USER_ICON = By.xpath("//form[@id='UIGroupMembershipForm']//*[contains(@class, 'uiIconSearch')]");
	//"//form[@id='UIGroupMembershipForm']/div[2]/div/table/tbody/tr[1]/td[2]/a";
	public final String ELEMENT_GROUP_SEARCH_POPUP_ADD_ICON = "//form[@id='UIUserSelector']//div[@class='UIAction']//a[@class='ActionButton LightBlueStyle']";
	public final String ELEMENT_SELECT_MEMBERSHIP = "//select[@name='membership']";
	public final String ELEMENT_GROUP_USER_IN_TABLE = "//*[@class='UIUserInGroup']//*[text()='${username}']";

	//Membership Management
	public final By ELEMENT_TAB_MEMBERSHIP_MANAGEMENT = By.xpath("//*[contains(@class, 'uiIconMembership')]");
	public final String ELEMENT_MEMBERSHIP_EDIT_ICON = "//*[text()='${membership}']/../..//*[@data-original-title='Edit Membership']";
	public final String ELEMENT_MEMBERSHIP_DELETE_ICON = "//*[text()='${membership}']/../..//*[@data-original-title='Delete Membership']";
	public final String ELEMENT_NEXT_PAGE_ICON = "//a[@title='Next Page']";
	public final By ELEMENT_INPUT_NAME = By.id("name");
	/*
	 * Manage Account
	 * */
	public final By ELEMENT_SIGN_IN_BUTTON = By.xpath("//*[@class='loginButton']/*");
	public final String ELEMENT_SELECT_SEARCH_OPTION = "//select[@name='searchOption']";
	public final String ELEMENT_SEARCH_ICON_REGISTER = "//img[@class='SearchIcon']";
	public final String ELEMENT_ADD_NEW_PORTAL_LINK = "//a[text()='Add New Site']";	
	public final String ELEMENT_CHECKBOX_SHOW_INFO_BAR_BY_DEFAULT = "//input[@name='showInfobar']";
	public final String ELEMENT_PORTAL_IN_LIST = "//td[@class='Content']/div[@class='Label' and text()='${portalName}']";
	public final String ELEMENT_PORTAL_DELETE_ICON = "//*[text()='${portalName}']/../..//*[text()='Delete']";
	public final String ELEMENT_PORTAL_EDIT_ICON = "//td[@class='Content']/div[@class='Label' and text()='${portalName}']/../../td[3]/a[@class='EditNavIcon'][2]";
	public final String ELEMENT_EDIT_FIRST_PORTAL_CONFIG = "//div[@id='UISiteManagement']//a[@class='EditNavIcon'][2]";
	public final By ELEMENT_SWITCH_VIEW_MODE_PORTAL = By.linkText("Switch View Mode");

	/* Portal management */
	public final String ELEMENT_PORTAL = "//*[@class='siteName' and text()='${siteName}']";
	public final String ELEMENT_PORTAL_EDIT_LAYOUT = "//*[@class='siteName' and text()='${siteName}']/../..//*[text()='Edit Layout']";
	public final String ELEMENT_PORTAL_EDIT_CONFIGURATION = "//*[text()='${siteName}']/../..//*[text()='Edit Site Configuration']";

	//Portal Edit Layout screen
	public final By ELEMENT_EDIT_INLINE_COMPOSER = By.xpath("//*[@class='PopupTitle popupTitle' and text()='Edit Inline Composer']");
	public final By ELEMENT_SWITCH_PORTAL_CONFIG = By.linkText("Site's Config");

	//Portal Setting TAB
	public final By ELEMENT_PORTAL_NAME = By.id("name");
	public final By ELEMENT_PORTAL_LABEL = By.id("label");
	public final By ELEMENT_PORTAL_DESCRIPTION = By.id("description");
	public final By ELEMENT_SELECT_LOCALE = By.name("locale");
	public final By ELEMENT_SELECT_SKIN = By.name("skin");

	//Property TAB
	public final String ELEMENT_SELECT_SESSION_ALIVE= "//select[@name='sessionAlive']"; 
	public final By ELEMENT_PROPERTIES_TAB = By.linkText("Properties");

	//Permission Setting TAB
	public final By ELEMENT_PERMISSION_SETTING_TAB= By.linkText("Permission Settings");
	public final By ELEMENT_CHECKBOX_PUBLIC_MODE = By.id("publicMode");
	public final By ELEMENT_EDIT_PERMISSION_SETTING = By.linkText("Edit Permission Settings");
	public final String ELEMENT_SELECT_ACCESS_MEMBERSHIP_ITEM = "//a[text()='${membership}']";
	public final String ELEMENT_SELECTED_ACCESS_PERMISSION_GROUP = "//div[@id='PermissionGrid']/table/tbody//div[text()='/${groupId}']";
	public final String ELEMENT_SELECTED_ACCESS_PERMISSION_MEMBERSHIP = "//*[@id='PermissionGrid']//*[text()='${membership}']";
	public final By ELEMENT_ADD_PERMISSION_BUTTON = By.linkText("Add Permission");
	public final String ELEMENT_SELECT_EDIT_MEMBERSHIP_ITEM = "//div[@id='UIPermissionSelector']//a[text()='${membership}']";
	public final String ELEMENT_SELECTED_EDIT_PERMISSION_GROUP = "// div[@class='SelectedPermissionInfo']/div[2]/div[.='/${groupId}']";
	public final String ELEMENT_SELECTED_EDIT_PERMISSION_MEMBERSHIP = "//*[@id='UIPermissionSelector']//*[text()='${membership}']";
	public final String ELEMENT_SELECT_PERMISSION_BUTTON = "//a[text()='Select Permission']";
	public final String ELEMENT_SELECT_ACCESS_GROUP_ITEM = "//*[@id='ListPermissionSelector']//a[@title='${group}']";
	public final String ELEMENT_SELECT_EDIT_GROUP_ITEM = "//*[@id='UIPermissionSelector']//*[contains(text(), '${group}')]";

	////
	public final String ELEMENT_PAGINATOR_PAGE_LINK = "//a[contains(@class, 'Number') and text()='${number}']";
	public final String ELEMENT_PAGINATOR_PAGE_NAMESPACE_LINK = "//*[@id='UINamespaceList']/div[1]//a[text()='${number}']";
	public final String ELEMENT_PAGINATOR_TOTAL_NUMBER = "//*[@class='pagesTotalNumber']";
	public final By ELEMENT_PAGINATOR_NAMESPACE_TOTAL_NUMBER = By.xpath("//*[@id='UINamespaceList']/div[1]//*[@class='pagesTotalNumber']");
	public final String ELEMENT_PAGINATOR_NEXT_ICON = "//a[@class='Icon NextPageIcon']";
	public final By ELEMENT_PAGINATOR_NEXT_ICON_NAMESPACE = By.xpath("//*[@id='UINamespaceList']/div[1]//i[@class='uiIconNextArrow']");
	public final String ELEMENT_PAGINATOR_SELECTED_PAGE = "//a[@class='Number PageSelected' and text()='${number}']";
	public final String ELEMENT_PAGINATOR_NAMESPACE_SELECTED_PAGE = "//*[@id='UINamespaceList']/div[1]//li[@class='active']/*[text()='${number}']";
	//public final String ELEMENT_MESSAGE_TEXT = "//li[@class='MessageContainer']/span[contains(@class, 'PopupIcon')]";
	//public final String ELEMENT_MESSAGE_DIALOG_CLOSE_ICON_IE = ELEMENT_MESSAGE_TEXT + "/../../../../../..//a";
	//public final String ELEMENT_MESSAGE_DIALOG_CLOSE_ICON = " //div[contains(@class, 'UIPopupWindow') and contains(@style, 'visibility: visible')]//a[contains(@class, 'uiIconClose')]";
	//"//div[contains(@class, 'UIPopupWindow') and contains(@style, 'visibility: visible')]//span[text()='Messages']/..//a[@class='CloseButton']";

	/* Add New Page Form */
	public final String ELEMENT_CHECKBOX_MAX_WINDOWS = "//input[@id='showMaxWindow']";
	public final String ELEMENT_LINK_EDITOR = "//a[@class='EditorIcon TBIcon' and text() = 'Edit']";
	public final String ELEMENT_LINK_EDITOR_PAGE = "//a[text()='Page']";
	public final String ELEMENT_LINK_EDITOR_ADD_PAGE = "//a[text()='Add Page']";	
	public final String ELEMENT_INPUT_NODE_NAME = "//input[@id='pageName']";
	public final By ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE = By.id("switchmode");
	public final String ELEMENT_LINK_EDITOR_PAGE_LAYOUT="//a[@class='ItemIcon EditPageIcon' and text()='Layout']";

	public final By ELEMENT_UP_LEVEL=By.xpath("//a[@title='Up Level']");
	public final String ELEMENT_SELECT_LANGUAGE = "//select[@name='languages']";
	public final String ELEMENT_INPUT_PAGE_DISPLAY_NAME = "//input[@id='pageDisplayName']";
	public final String ELEMENT_PAGE_EDITOR_NEXT_STEP = "//button[text()='Next']";
	public final String ELEMENT_EDIT_PAGE_CATEGORY_MENU = "//a[@title='${categoryLabel}']";
	public final String ELEMENT_EDIT_PAGE_PAGE = "//div[@id='UIPage']";
	public final String ELEMENT_PORTLET_LABEL = "//div[@class='CPortletLayoutDecorator' and contains(text(), '${portletName}')]";	
	public final String ELEMENT_PAGE_FINISH_BUTTON = "//*[@data-original-title='Finish']";
	public final By ELEMENT_PAGE_CLOSE = By.xpath("//a[@title='Abort']");
	public final By ELEMENT_SWITCH_VIEW_MODE = By.linkText("Switch View mode");

	//PortalNavigation - http://localhost:8080/portal/g/:platform:administrators/portalnavigation
	public final String ELEMENT_NODE_LINK = "//*[@class='node']//*[@title='${nodeLabel}']";
	public final String ELEMENT_EDIT_NAVIGATION = "//*[text()='${navigation}']/../..//*[@class='uiIconNavigation uiIconLightGray']";
	public final By ELEMENT_TITLE_NAVIGATION_MANAGEMENT = By.xpath("//*[contains(@class, 'popupTitle') and text() = 'Navigation Management']");
	public final By ELEMENT_ADD_NODE_LINK = By.linkText("Add Node");
	public final By ELEMENT_PAGE_SELECTOR_TAB = By.linkText("Page Selector");
	public final By ELEMENT_INPUT_PAGE_NAME = By.name("pageName");
	public final By ELEMENT_INPUT_PAGE_TITLE = By.name("pageTitle");
	public final By ELEMENT_CREATE_PAGE_LINK = By.xpath("//*[contains(@class, 'uiIconAddPage')]");
	public final By ELEMENT_SEARCH_SELECTOR_PAGE_LINK = By.className("uiIconSelectPage");
	public final By ELEMENT_CLEAR_SELECTOR_PAGE = By.className("uiIconDelete");
	public final By ELEMENT_SELECT_SEARCHED_PAGE = By.xpath("//*[@data-original-title='Select Page']");
	public final By ELEMENT_PAGE_MANAGEMENT_SEARCH_BUTTON = By.xpath("//*[contains(@class, 'uiIconSearch')]");
	public final By ELEMENT_SELECT_PAGE_SEARCH = By.xpath("//a[@data-original-title='Quick Search']");
	public final By ELEMENT_SELECT_PAGE_BUTTON = By.xpath("//a[@data-original-title='Select Page']");

	public final String ELEMENT_LIST_NODE_LINK = ELEMENT_NODE_LINK.replace("${nodeLabel}", "${nodeLabel}") + "/..//li[${number}]//*[@title='${childNode}']";
	public final String ELEMENT_CHILD_NODE_LINK = ELEMENT_NODE_LINK.replace("${nodeLabel}", "${nodeLabel}") + "/../*[contains(@class, 'childrenContainer')]//*[@title='${childNode}']";
	public final String ELEMENT_SELECT_HOME_PAGE = "//div[@id='UIRepeater']//table//tbody/tr/td[5]/div[@class='ActionContainer']/img";
	public final String ELEMENT_NAVIGATION_HOME_NODE = "//div[@class='HomeNode']";				 
	public final String ELEMENT_NODE_ADD_NEW_TOP_NODE = "//div[@id='UINavigationNodeSelectorPopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon AddNode16x16Icon']";
	public final By ELEMENT_NODE_ADD_NEW = By.xpath("//*[@id='NavigationNodePopupMenu']/*[@class='uiContextMenuContainer']//*[@class='uiIconAddNode']");
	//public final String ELEMENT_NODE_EDIT = "//div[@id='NavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon EditSelectedNode16x16Icon']";
	//	public final String ELEMENT_NAVIGATION_NODE_AREA= "//div[@class='Node']"; 

	//Navigation management > Context menu
	public final By ELEMENT_NAVIGATION_DELETE_NODE = By.className("uiIconDeleteNode");
	public final By ELEMENT_EDIT_SELECTED_NODE = By.className("uiIconEditSelectedNode");
	public final By ELEMENT_NAVIGATION_COPY_NODE = By.className("uiIconCopyNode");
	public final By ELEMENT_NAVIGATION_CUT_NODE = By.className("uiIconCutNode");
	public final By ELEMENT_NAVIGATION_CLONE_NODE = By.className("uiIconCloneNode");
	public final By ELEMENT_NAVIGATION_PASTE_NODE = By.xpath("//*[@class='UINavigationNodeSelector']//*[@class='uiIconPasteNode']");
	public final By ELEMENT_NAVIGATION_MOVE_UP_NODE = By.className("uiIconMoveUp");
	public final By ELEMENT_NAVIGATION_MOVE_DOWN_NODE = By.className("uiIconMoveDown");
	public final By ELEMENT_NAVIGATION_EDIT_PAGE_NODE = By.className("uiIconEditPageNode");

	//Add new Page
	public final By ELEMENT_NEWPAGE_NAME_TEXTBOX = By.id("pageName");	
	//public final By ELEMENT_NEWPAGE_SAVE_BUTTON = By.xpath("//*[@data-original-title='Finish']");
	public final By ELEMENT_NEWPAGE_LAYOUT_OPTION = By.className("caret");
	//By.xpath("//div[@class='DropDownSelectLabel']") ;

	//Page Creation Wizard -> Page Configs
	public final By ELEMENT_NEWPAGE_LAYOUT_COLUMN_PAGE_OPTION = By.linkText("Column Page Configs") ;
	public final By ELEMENT_NEWPAGE_LAYOUT_ROW_PAGE_OPTION = By.linkText("Row Page Configs");
	public final By ELEMENT_NEWPAGE_LAYOUT_TAB_PAGE_OPTION = By.linkText("Tabs Page Config");
	public final By ELEMENT_NEWPAGE_LAYOUT_MIX_PAGE_OPTION = By.linkText("Mix Page Configs");
	public final By ELEMENT_NEWPAGE_LAYOUT_DEFAULT_OPTION = By.linkText("Page Configs");
	public final By ELEMENT_CONTENT_GROUP_PORTLET = By.xpath("//*[@title='Content']");

	public final By ELEMENT_APPLICATION_TAB = By.linkText("Applications");
	public final By ELEMENT_ADD_CONTENT_DETAIL_PORTLET = By.xpath("//div[contains(text(),'Content Detail')]");
	public final By ELEMENT_DROP_TARGET_NO_LAYOUT = By.xpath("//div[@id='UIPage']");
	public final By ELEMENT_DROP_TARGET_HAS_LAYOUT = By.xpath("//div[@class='UIRowContainer EmptyContainer']");
	public final By ELEMENT_ADD_CONTENT_LIST_PORTLET = By.xpath("//div[contains(text(),'Content List')]");
	public final By ELEMENT_CLV_PORTLET = By.className("UICLVPortlet");
	public final By ELEMENT_FRAME_CONTAIN_PORTLET = By.xpath("//div[contains(@id,'UIPortlet')]");
	public final By ELEMENT_CONTENT_DETAIL_IN_LAYOUT = By.xpath("//*[contains(@id, 'UIPortlet')]//div[contains(text(),'Content Detail')]");
	public final By ELEMENT_CONTENT_DETAIL_EDIT_ICON =  By.xpath("//*[text()='Content Detail']/..//a[@data-original-title='Edit Portlet']");
	public final By ELEMENT_CONTENT_DETAIL_DELETE_ICON =  By.xpath("//*[text()='Content Detail']/..//a[@data-original-title='Delete Portlet']");
	public final By ELEMENT_SELECT_CONTENT_PATH_LINK = By.xpath("//a[@data-original-title='Add Path']");
	public final By ELEMENT_SELECT_CONTENT_PATH_LINK_AUX = By.xpath("//*[contains(@class, 'uiIconAddPath')]");
	public final String ELEMENT_CONTENT_IN_CONTENT_DETAIL_PORTLET = "//*[contains(@id, 'UISingleContentViewerPortlet')]//*[@class='Title' and text()='${contentName}']";

	public final By ELEMENT_CONTENTS_BY_QUERY_PORTLET = By.xpath("//div[contains(text(),'Content By Query')]");
	public final By ELEMENT_CATEGORY_CONTENT = By.linkText("Content");
	public final By ELEMENT_CATEGORY_COLLABORATION = By.linkText("Collaboration");
	public final By ELEMENT_CATEGORY_ADMINISTRATION = By.linkText("Administration");
	public final By ELEMENT_ACCOUNT_PORTLET = By.id("Administration/AccountPortlet");
	public final By ELEMENT_PAGE_MANAGEMENT_PORTLET = By.id("Administration/PageManagementPortlet");
	public final By ELEMENT_ORGANIZATION_PORTLET = By.id("Administration/OrganizationPortlet");
	public final By ELEMENT_CONTENTS_LIST_VIEWER_PORTLET = By.id("Content/ContentListViewerPortlet");
	public final By ELEMENT_ANWSER_PORTLET = By.id("Collaboration/AnswersPortlet");
	public final By ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE = By.id("UIAnswersPortlet");
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

	//Container setting
	public final String ELEMENT_DRAG_CONTAINER = "//*[@title='Hold this area to drag this container']";
	public final String ELEMENT_CONTAINER_ROW = "//*[@class='UIRowContainer ']/div[${No}]//*[@class='UIRowContainer EmptyContainer']";
	public final String ELEMENT_CONTAINER_ROW_0 = "//*[@class='UIRowContainer']/div[1]//*[@class='UIRowContainer EmptyContainer']";
	public final By ELEMENT_CONTAINER_ROW_1 = By.xpath("//*[@class='UIRowContainer']/div[1]");
	public final By ELEMENT_CONTAINER_TAB = By.linkText("Containers");
	public final By ELEMENT_EDIT_CONTAINER_ICON = By.xpath("//*[@data-original-title='Edit Container']");
	public final By ELEMENT_DELETE_CONTAINER_ICON = By.xpath("//*[@data-original-title='Delete Container']");
	public final By ELEMENT_CONTAINER_TITLE = By.id("title");
	public final By ELEMENT_CONTAINER_DRAG_DROP_ICON = By.xpath(ELEMENT_DRAG_CONTAINER);
	public final By ELEMENT_CONTAINER_COLUMN_EDIT_ICON = By.xpath("//*[@data-original-title='Edit Table']");
	public final By ELEMENT_EDITING_CONTAINER = By.xpath("//div[@class='UIRowContainer EmptyContainer']/ancestor::div[contains(@class, 'EdittingContainer')]");
	public final By ELEMENT_WIDTH_TEXTBOX = By.id("width");
	public final By ELEMENT_HEIGHT_TEXTBOX = By.id("height");
	public final By ELEMENT_PORTLET_LAYOUT_DECORATOR = By.className("portletLayoutDecorator");
	public final String ELEMENT_COLUMN_CONTAINER = "//*[@class='UITableColumn']";
	public final String ELEMENT_NAME_CONTAINER = ELEMENT_DRAG_CONTAINER + "/../*[text()='${nameContainer}']";
	public final String ELEMENT_NAME_CURRENT_CONTAINER = "//*[text()='${nameContainer}']/ancestor::div[contains(@class, 'EdittingContainer')]";
	public final String ELEMENT_DRAG_CURRENT_CONTAINER = "//*[text()='${nameContainer}']/../*[@title='Hold this area to drag this container']";
	public final String ELEMENT_LIST_CONTAINER = "//*[@class='UIRowContainer']/div[${number}]//*[contains(text(), '${nameContainer}')]";

	/*
	 * END Page Management
	 * */

	/*Portlet in general*/
	public final By ELEMENT_EDIT_PORTLET_ICON = By.xpath("//*[@data-original-title='Edit Portlet']");
	public final By ELEMENT_DELETE_PORTLET_ICON = By.xpath("//*[@data-original-title='Delete Portlet']");
	//public final By ELEMENT_PORTLET_CONTAINER = By.className("PortletLayoutDecorator");
	public final By ELEMENT_ABORTEDIT_BUTTON = By.xpath("//*[@data-original-title='Abort']");
	public final String ELEMENT_PORTLET_DRAG_DROP_ICON = "//*[@title='Hold this area to drag this portlet']";
	public final String ELEMENT_NAME_PORTLET = "//*[@class='portletName' and contains(text(), '${portletName}')]";
	public final String ELEMENT_PORTLET_FRAGMENT = "//*[@id='${portletName}']/ancestor::div[contains(@class, 'UIApplication')]";
	public final String ELEMENT_LIST_PORTLET_LAYOUT_DECORATOR = "//*[@class='portletLayoutDecorator' and contains(text(), '${portletName}')]";
	public final String ELEMENT_DRAG_CURRENT_PORTLET = "//*[text()='${portletName}']/../*[@title='Hold this area to drag this portlet']";

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
	public final By ELEMENT_ADVANCED_CONFIGURATION_TAB = By.xpath("//*[text()='Advanced']");
	public final By ELEMENT_MANAGE_LOCK_TAB = By.xpath("//*[text() = 'Manage Lock']");
	//("//div[@class = 'MiddleTab' and text() = 'Manage Lock']");
	public final By ELEMENT_MANAGE_LOCKS = By.className("uiIconEcmsUnLockManager");
	public final By ELEMENT_REPOSITORY_TAB = By.xpath("//*[text()='Repository']");

	//Edit In-line a SCV
	public final By ELEMENT_PREFERENCE_TITLE=By.xpath("//span[contains(text(),'Content Detail Preferences')]");
	public final By ELEMENT_CONTAINER_CONTENT = By.xpath("//*[@class='NavigationContainer']");
	public final By ELEMENT_BUTTON_BACK = By.xpath("//*[@class='URLBackToButton']");

	//Others
	//Content template > upload
	public final By ELEMENT_UPLOAD_LINK_XPATH = By.xpath("//*[@class='actionIcon']//*[@class='uiIconEcmsUpload']");
	public final By ELEMENT_UPLOAD_FILE_NAME_ID = By.id("name");
	public final By ELEMENT_UPLOAD_IMG_FRAME_XPATH = By.xpath("//iframe[contains(@id,'iFrameUpload')]");
	public final By ELEMENT_UPLOAD_IMG_ID = By.name("file");
	public final By ELEMENT_UPLOAD_VERSION_ID = By.xpath("//div[@id='versionHistory']//input[@name='file']");

	//Space > Wiki link
	public final By ELEMENT_WIKI_LINK_IN_SPACE = By.xpath("//*[@id='spaceMenuTab']/li[3]/a/span[text()='Wiki']");

	//----------------------Gmail form ---------------------------------------------------
	public final String GMAIL_URL = "https://mail.google.com";
	public final String EMAIL_ADDRESS1 = "exomailtest01@gmail.com";
	public final String EMAIL_ADDRESS2 = "exoservice@gmail.com";
	public final String EMAIL_PASS = "exoadmin";
	public final By ELEMENT_DELETE_MAIL = By.xpath("//*[@id=':ro']/div[2]//*[@class='ar9 T-I-J3 J-J5-Ji']");
    public final By ELEMENT_DELETE_MAIL_2 = By.xpath("//*[@id=':5']//*[@class='iH']//*[@class='ar9 T-I-J3 J-J5-Ji']");
	public final By ELEMENT_GMAIL_INBOX = By.xpath("//a[contains(@title, 'Inbox')]");
	public final By ELEMENT_MAIL_CONTENT = By.xpath("//*[contains(@class, 'adP adO')]/div");
	public final By ELEMENT_GMAIL_USERNAME = By.id("Email");
	public final By ELEMENT_GMAIL_PASS = By.id("Passwd");
	public final By ELEMENT_GMAIL_SIGN_IN = By.id("signIn");
	public final String ELEMENT_GMAIL_TITLE = "//span/b[contains(text(),'{$title}')]";
	public final By ELEMENT_GMAIL_COMPOSE = By.xpath("//div[contains(text(),'COMPOSE')]");
	
	//get url
	public final String ELEMENT_GET_URL_IMAGE = "//img[@alt='${name}']";

	///////////////////
	//Set view permissions for portal
	public void setViewPermissions(String groupId, String membership) {
		String membershipToSelect = ELEMENT_SELECT_ACCESS_MEMBERSHIP_ITEM.replace("${membership}", membership);
		//String selectedGroup = ELEMENT_SELECTED_ACCESS_PERM_GROUP.replace("${groupId}", groupId.replace(" ", "-").toLowerCase());
		String selectedMembership = ELEMENT_SELECTED_ACCESS_PERMISSION_MEMBERSHIP.replace("${membership}", membership);

		info("--Setting view permission to " + groupId + ", " + membership + "--");
		String[] groups = groupId.split("/");
		Utils.pause(500);
		click(ELEMENT_ADD_PERMISSION_BUTTON);
		waitForTextPresent("Browse and select a group");
		for (String group : groups) {
			String groupToSelect = ELEMENT_SELECT_ACCESS_GROUP_ITEM.replace("${group}", group);
			click(groupToSelect);
		}
		Utils.pause(500);
		click(membershipToSelect);
		Utils.pause(500);
		//waitForTextNotPresent("Permission Selector");
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
		Utils.pause(500);
		waitForTextPresent("Permission Selector");
		for (String group : groups) {
			String groupToSelect = ELEMENT_SELECT_EDIT_GROUP_ITEM.replace("${group}", group);
			click(groupToSelect);
		}
		click(membershipToSelect);
		waitForTextNotPresent("Permission Selector");
		//waitForAndGetElement(selectedGroup);
		waitForAndGetElement(selectedMembership, DEFAULT_TIMEOUT, 1, 2);
	}

	//Define a type of page
	public enum PageType {
		PORTAL, GROUP;
	}

	//Link to Edit a navigation
	public void editNavigation(String currentNavigation) {
		String navigation = ELEMENT_EDIT_NAVIGATION.replace("${navigation}", currentNavigation);
		click(navigation);
		//waitForTextPresent("Navigation Management");
		waitForAndGetElement(ELEMENT_TITLE_NAVIGATION_MANAGEMENT);
	}

	//Copy value from Source and paste to Target
	public void copyPaste(Object Source, String value, Object Target){ 	
		Actions actions = new Actions(driver);
		WebElement element = waitForAndGetElement(Source);
		element.sendKeys(value);
		actions.doubleClick(element).perform();
		element.sendKeys(Keys.LEFT_CONTROL + "a");
		element.sendKeys(Keys.LEFT_CONTROL + "c");
		Utils.pause(3000);
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
			Utils.pause(500);
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
			Utils.pause(1000);
		}
		for (; isElementNotPresent(locator); seconds++) {
			if (seconds >= (DEFAULT_TIMEOUT/WAIT_INTERVAL) ) {
				Assert.fail("Timeout at goToPage");
			}
			Utils.pause(500);
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

	//Verify that element [locator] is displayed...
	public void usePaginator(Object locator, String exceptionMessage) {
		String page1 = ELEMENT_PAGINATOR_PAGE_LINK.replace("${number}", "1");
		String page1Namespace = ELEMENT_PAGINATOR_PAGE_NAMESPACE_LINK.replace("${number}", "1"); 

		if (waitForAndGetElement(page1, 5000, 0) != null){
			click(page1);
		}else if (waitForAndGetElement(page1Namespace, 3000, 0) != null){
			click(page1Namespace);
		}
		Utils.pause(500);
		int totalPages = 0;
		if (waitForAndGetElement(ELEMENT_PAGINATOR_TOTAL_NUMBER, 3000, 0) != null){
			totalPages = isElementPresent(ELEMENT_PAGINATOR_TOTAL_NUMBER) ? Integer.valueOf(getText(ELEMENT_PAGINATOR_TOTAL_NUMBER)) : 1;
		}else if (waitForAndGetElement(ELEMENT_PAGINATOR_NAMESPACE_TOTAL_NUMBER, 3000, 0) != null){
			totalPages = isElementPresent(ELEMENT_PAGINATOR_NAMESPACE_TOTAL_NUMBER) ? Integer.valueOf(getText(ELEMENT_PAGINATOR_NAMESPACE_TOTAL_NUMBER)) : 1;
		}
		info("-- The total pages is: " + totalPages);
		int i = 1;
		while (isElementNotPresent(locator)) {
			if (i == totalPages) {
				//Assert.fail(exceptionMessage);
				info(exceptionMessage);
				break;
			}
			if (waitForAndGetElement(ELEMENT_PAGINATOR_NEXT_ICON_NAMESPACE, 3000, 0) != null){
				click(ELEMENT_PAGINATOR_NEXT_ICON_NAMESPACE);
				waitForAndGetElement(ELEMENT_PAGINATOR_NAMESPACE_SELECTED_PAGE.replace("${number}", String.valueOf((++i))));
			}else if (waitForAndGetElement(ELEMENT_PAGINATOR_NEXT_ICON, 3000, 0) != null){
				click(ELEMENT_PAGINATOR_NEXT_ICON);
				waitForAndGetElement(ELEMENT_PAGINATOR_SELECTED_PAGE.replace("${number}", String.valueOf((++i))));
			}else {
				click(button.ELEMENT_NEXT_PAGE_BUTTON);
			}
			Utils.pause(500);
		}
	}

	/*public void usePaginator(By locator, String exceptionMessage) {
		String page1 = ELEMENT_PAGINATOR_PAGE_LINK.replace("${number}", "1");

		click(page1);
		Utils.pause(500);
		int totalPages = isElementPresent(ELEMENT_PAGINATOR_TOTAL_NUMBER) ? Integer.valueOf(getText(ELEMENT_PAGINATOR_TOTAL_NUMBER)) : 1;
		int i = 1;
		while (isElementNotPresent(locator)) {
			if (i == totalPages) {
				Assert.fail(exceptionMessage);
			}
			click(ELEMENT_PAGINATOR_NEXT_ICON);
			waitForAndGetElement(ELEMENT_PAGINATOR_SELECTED_PAGE.replace("${number}", String.valueOf((++i))));
			Utils.pause(500);
		}
	}*/

	// Check UnexpectedError
	public boolean checkUnexpectedError() {
		try {
			driver.findElement(ELEMENT_ERROR_ICON);
			return false;

		} catch (NoSuchElementException e) {
			return true;
		}
	}

	/**
	 * @author thuntn
	 * @return 
	 */
	public boolean checkUnhandleAlert() {
		try {
			alert.acceptAlert();
			return false;

		} catch (org.openqa.selenium.UnhandledAlertException e) {
			return true;
		}
	}

	public void cloneNode(Object locator)	{
		for (int i =0;; i++){
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL) {
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_CLONE_NODE,30000,0)!=null){
				click((ELEMENT_CLONE_NODE));
				return;
			}else if (waitForAndGetElement(ELEMENT_NAVIGATION_CLONE_NODE, 5000, 0) != null){
				click(ELEMENT_NAVIGATION_CLONE_NODE);
				return;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}

	public void cutNode(Object locator)	{
		for (int i =0;; i++){
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL) {
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_CUT_NODE, 5000, 0)!=null){
				debug("==Cut node " + locator + "==");
				click((ELEMENT_CUT_NODE));
				return;
			}else if (waitForAndGetElement(ELEMENT_NAVIGATION_CUT_NODE, 5000, 0) != null){
				click(ELEMENT_NAVIGATION_CUT_NODE);
				return;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}

	public void copyNode(Object locator){
		for (int i =0;; i++){
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL) {
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_COPY_NODE, 5000, 0) != null){
				click((ELEMENT_COPY_NODE));
				return;
			}else if (waitForAndGetElement(ELEMENT_NAVIGATION_COPY_NODE, 5000, 0) != null){
				click(ELEMENT_NAVIGATION_COPY_NODE);
				return;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}

	public void pasteNode(Object locator) {
		for (int i =0;; i++){
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL){
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_PASTE_NODE, 5000, 0) != null){
				click(ELEMENT_PASTE_NODE);
				return;
			}else if (waitForAndGetElement(ELEMENT_NAVIGATION_PASTE_NODE, 5000, 0) != null){
				click(ELEMENT_NAVIGATION_PASTE_NODE);
				return;
			}
			Utils.pause(WAIT_INTERVAL);
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
		button.save();
		button.close();
		if (useFrench){
			click(ELEMENT_PAGE_FINISH_BUTTON_INFRENCH);
			waitForTextNotPresent("Editeur de page");
		}else{
			click(ELEMENT_PAGE_FINISH_BUTTON);
			waitForTextNotPresent("Page Editor");
		}
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
	public void inputDataToFrame(By framelocator, String data, boolean...validate){
		try {
			WebElement inputsummary = null;

			for (int repeat = 0;; repeat++) {
				if (repeat >= DEFAULT_TIMEOUT/WAIT_INTERVAL) {
					Assert.fail("Fail to input data to frame " + framelocator);
				}
				driver.switchTo().frame(waitForAndGetElement(framelocator));
				inputsummary = driver.switchTo().activeElement();

				inputsummary.click();

				inputsummary.clear();

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
			Utils.pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			inputDataToFrame (framelocator, data);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			driver.switchTo().defaultContent();
			inputDataToFrame (framelocator,data);
		}catch (WebDriverException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
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
				Utils.pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
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
			info("Switch to new windown successfully");
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

	/**
	 * @author lientm
	 * @param cke_frame
	 * @param content
	 */
	public void typeMultiLineInCkeContent(By cke_frame, String content){
		WebElement inputsummary = null;
		String[] lines = content.split("/");

		if (lines.length > 0){
			driver.switchTo().frame(waitForAndGetElement(cke_frame));
			inputsummary = driver.switchTo().activeElement();
			inputsummary.click();
			for (int i = 0; i < lines.length; i++){
				inputsummary.sendKeys(lines[i]);
				inputsummary.sendKeys(Keys.ENTER);
				Utils.pause(500);
			}
		}
		switchToParentWindow();
		Utils.pause(1000);
	}

	//function open and go to mail
	public void goToMail(String email, String pass){	
		((JavascriptExecutor) driver).executeScript("window.open()");
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
		info("Go to gmail");
		driver.navigate().to(GMAIL_URL);
		driver.manage().window().maximize();
		waitForAndGetElement(ELEMENT_GMAIL_USERNAME);

		//login to mail
		type(ELEMENT_GMAIL_USERNAME, email, true);
		type(ELEMENT_GMAIL_PASS, pass, true);
		click(ELEMENT_GMAIL_SIGN_IN);
		click(ELEMENT_GMAIL_INBOX);
		Utils.pause(1000);
	}

	/**
	 * function: check content of mail then delete mail
	 * @param mail: element title of mail
	 * @param content: mail content
	 */
	public void checkAndDeleteMail(By mail, String content){
		waitForAndGetElement(mail,150000);
		click(mail);	
		waitForTextPresent(content);
		info("Found notify mail");

		info("delete mail");
		if (waitForAndGetElement(ELEMENT_DELETE_MAIL_2, 5000, 0) == null){
			click(ELEMENT_DELETE_MAIL);
		}else {
			click(ELEMENT_DELETE_MAIL_2);
		}
		Utils.pause(1000);
	}
}
