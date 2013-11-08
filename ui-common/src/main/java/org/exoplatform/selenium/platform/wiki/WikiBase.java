package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

/**
 * Migrate to PLF 4
 * <li>Update by @author vuna2</li>
 */
/* @author: Thuntn
 * @date: 14/11/2012
 */
public class WikiBase extends PlatformBase{

	ManageAccount magAcc = new ManageAccount(driver);
	UserGroupManagement userGroup = new UserGroupManagement(driver);
	Button button = new Button(driver);
	ManageMember magMember = new ManageMember(driver);

	/*-----------------Page control area-------------------*/

	//Intranet home page
	public final By ELEMENT_INTRANET_HOME_PAGE = By.xpath("//ul[@class='uiCompanyNavigations']//li/a[text()='Home']");
	public final By ELEMENT_PAGE_TITLE_INFO = By.id("titleInfo");
	public final By ELEMENT_PAGE_TITLE_EDIT_TEXTBOX = By.id("EdiableInput");
	public final String ELEMENT_DISPLAY_MODE = "//*[@id='DisplayModesDropDown']//span[text()='${space}']";

	//Company/Left panel > Wiki Link
	public final By ELEMENT_WIKI_LINK=By.xpath("//ul[@class='uiCompanyNavigations']//li/a[text()='Wiki']");

	//Add page menu
	public final By ELEMENT_ADD_PAGE_LINK = By.xpath("//*[@id='UIWikiPageControlArea_PageToolBar']//div[contains(text(),'Add Page')]");
	public final By ELEMENT_BLANK_PAGE_LINK = By.linkText("Blank Page");
	public final By ELEMENT_FROM_TEMPLATE_LINK = By.linkText("From Template...");

	//Edit menu
	public final By ELEMENT_EDIT_PAGE_LINK= By.xpath("//*[@id='UIWikiPageControlArea_PageToolBar']//li/div[contains(text(), 'Edit')]");;

	//More menu
	public final By ELEMENT_MORE_LINK = By.xpath("//*[@id='UIWikiPageControlArea_PageToolBar']//div[contains(text(), 'More')]");
	public final By ELEMENT_DELETE_LINK = By.linkText("Delete Page");
	public final By ELEMENT_DELETE_LINK_2 = By.className("uiIconDeletePage");
	public final By ELEMENT_WATCH_LINK = By.linkText("Watch");
	public final By ELEMENT_UNWATCH_LINK = By.linkText("Stop Watching");
	public final By ELEMENT_PAGE_INFO_LINK = By.linkText("Page Info");
	public final By ELEMENT_PAGE_PERMISSION_LINK = By.linkText("Page Permissions");
	public final By ELEMENT_MOVE_PAGE_LINK = By.linkText("Move Page");
	public final By ELEMENT_EXPORT_AS_PDF_LINK = By.linkText("Export as PDF");
	public final By ELEMENT_PERMALINK_LINK = By.linkText("Permalink");

	//BreadCrumb
	public final By ELEMENT_WIKI_HOME = By.linkText("Wiki Home");

	//Browse menu
	public final By ELEMENT_BROWSE_LINK = By.xpath("//div[contains(text(),'Browse')]");
	public final By ELEMENT_WIKI_SETTING_LINK= By.linkText("Wiki Settings");
	public final By ELEMENT_MY_DRAFT = By.linkText("My Drafts");

	//Search area
	public final By ELEMENT_QUICK_SEARCH = By.id("wikiSearchValue");
	public final By ELEMENT_SEARCH_RESULT = By.className("resultNumber");
	public final String ELEMENT_PAGE_RESULT = "//*[@id='UIWikiAdvanceSearchResult']//*[contains(text(), '${title}')]";

	//Wiki Home
	public final By ELEMENT_WIKI_HOME_LINK=By.xpath("//a[text()='Wiki Home']");
	public final By ELEMENT_WIKI_HOME_PAGE=By.xpath("//*[@id='titleInfo' and text()='Wiki Home']");
	public final By ELEMENT_TITLE_WIKI_HOME_LINK = By.xpath("//*[@class='titleWikiBox']/*[contains(text(), 'Wiki Home')]");
	public final String ELEMENT_NODE_WIKI_PAGE = "//*[@class='node']//*[contains(text(), '{$node}')]";
	
	//Space Switcher
	public final By ELEMENT_SPACE_SWITCHER_BREADCRUMB = By.id("DisplayModesDropDown");
	public final By ELEMENT_SPACE_SWITCHER_INPUT = By.xpath("//*[@id='uiSpaceSwitcher_BreadCrumb']//input[@class='spaceSearchText lostFocus']") ;
	public final By ELEMENT_SPACE_SWITCHER_PLACEHOLDER = By.xpath("//*[contains(text(),'Filter Spaces')]");

	/*------------------add/edit wiki page---------------------*/
	//Source Editor mode
	public final By ELEMENT_TITLE_WIKI_INPUT = By.id("titleInput");
	public final By ELEMENT_CONTENT_WIKI_INPUT = By.id("Markup");
	public final By ELEMENT_RICHTEXT_BUTTON = By.xpath("//*[@id='UIEditorTabs']/button[1]");
	public final By ELEMENT_PREVIEW_BUTTON = By.xpath("//*[@id='UIEditorTabs']/button[2]");
	public final By ELEMENT_PREVIEW_SCREEN = By.xpath("//div[@class='popupTitle' and text()='Preview']");
	public final By ELEMENT_PUBLISH_ACTIVITY_CHECKBOX = By.id("PublishActivityUpper");
	public final By ELEMENT_COMMENT_TEXTBOX = By.id("Comment");
	public final By ELEMENT_SAVE_BUTTON_ADD_PAGE = By.id("UISubmitToolBarUpper_SavePage_");
	public final By ELEMENT_CANCEL_BUTTON_ADD_PAGE = By.id("UISubmitToolBarBottom_Cancel_");
	public final By ELEMENT_CONFIRM_BUTTON_ADD_PAGE = By.xpath("//button[text()='Confirm']");
	public final By ELEMENT_CANCEL_BUTTON_ADD_PAGE_NULL_TITLE = By.xpath("//button[text()='Cancel']");
	public final String MESSAGE_PAGE_ALREADY_EXISTS = "The page title already exists. Please select another one.";
	public final String MESSAGE_CANCEL_CREATE_PAGE = "to leave this page?";
	//"Are you sure to leave this page?";
	public final By ELEMENT_OK_BUTTON_WIKI_PAGE = By.xpath("//div[contains(@class, 'uiAction')]/a[text()='OK']");

	//Richtext mode
	public final By ELEMENT_SOURCE_EDITOR_BUTTON= By.xpath("//*[contains(text(),'Source Editor')]");
	public final By ELEMENT_CONTENT_WIKI_FRAME = By.xpath("//div[@class='xRichTextEditor']/iframe");

	//Upload file area
	public By ELEMENT_UPLOAD_FILE = By.xpath("//div[@class='uiUploadInput']//*[@type='file']"); 
	public final By ELEMENT_FRAME_UPLOAD=By.xpath("//div[@class='uiUploadInput']/iframe");
	public final String ELEMENT_REMOVE_ATTACHMENT = "//a[text()='{$file}']/../../td/a/i[@class='uiIconDelete']";

	//Add page from template
	public final String ELEMENT_SELECT_TEMPLATE_LINK = "//input[@value = '{$template}']";

	/*--------------------------Search page----------------------------*/
	public final By ELEMENT_SEARCH_BUTTON = By.xpath("//button[text()='Search']");
	//By.linkText("Search");
	public final By ELEMENT_SEARCH_FOR = By.xpath("//a[@class='ItemIcon MenuIcon' and contains(@title,'Search for')]"); 
	public final By ELEMENT_SEARCH_ICON = By.xpath("//div[@class='SearchIcon']");
	public final By ELEMENT_SEARCH_ADVANCE=By.id("text");
	public final String ELEMENT_RESULT_SEARCH = "//*[@id='UIWikiAdvanceSearchResult']//*[contains(@href, '${pageName}') and contains(text(),'${pageName}')]";
	public final String ELEMENT_VERIFY_RESULT_SEARCH = "//*[@id='UIWikiAdvanceSearchResult']//span[text()='0']/../strong[text()='${pageName}']";
	public final String ELEMENT_VERIFY_MESSAGE = "No matching search result.";

	/*-------------------------Move page--------------------*/
	public final By CLICK_MOVE_ACTION = By.xpath("//*[@id='UIWikiMovePageForm']//*[text()='Move']");
	public final By ELEMENT_CANCEL_BUTTON_MOVE_PAGE = By.xpath("//*[contains(@class, 'uiWikiMovePageForm')]//button[contains(text(), 'Cancel')]");
	public final By ELEMENT_MOVE_PAGE_POPUP = By.xpath("//*[contains(@class, 'popupTitle') and text()='Move Page']");
	public final By ELEMENT_SELECT_SPACE_DESTINATION = By.xpath("//*[contains(text(), 'Select the destination:')]/..//*[@class='btn dropdown-toggle']");
	public final String ELEMENT_SPACE_NAME_SELECTED = "//*[@id='UISpaceSwitcher_/spaces/${space}']/a";
	public final By ELEMENT_PORTAL_NAME_SELECTED = By.id("UISpaceSwitcher_/portal/intranet");
	public final String MESSAGE_MOVE_PAGE_DUPLICATE_TITLE = "Another page with the same title already exists in the selected space.";
	public final By ELEMENT_RENAME_LINK_WHEN_MOVE_PAGE = By.linkText("Rename");
	public final String MESSAGE_NO_PERMISSION_MOVE_PAGE = "You have no edit permission at the destination page";

	/*-------------------------Permission page--------------------*/
	public final By ELEMENT_SELECT_USER = By.xpath("//a[contains(@onclick, 'OpenSelectUserForm')]");
	public final String ELEMENT_USERNAME_CHECK = "//input[@id='${user}' and @type='checkbox']";
	public final By ELEMENT_SELECTOR_TEXT = By.xpath("//span[@class='PopupTitle' and contains(text(),'User Selector')]");
	public final By ELEMENT_SELECT_INPUT = By.id("PermissionOwner");
	public final By ELEMENT_SELECT_GROUP = By.className("uiIconGroup");
	public final By ELEMENT_SELECT_MEMBERSHIP = By.className("uiIconMembership");
	public final String ELEMENT_EDIT_PAGE_PERMISSIONS = "//*[contains(text(), '${user}')]/../..//input[contains(@id, 'EDITPAGE')]";
	public final String ELEMENT_VIEW_PAGE_PERMISSIONS = "//*[contains(text(), '${user}')]/../..//input[contains(@id, 'VIEWPAGE')]";
	public final String ELEMENT_DELETE_PERMISSIONS = "//*[contains(text(), '${user}')]/../..//*[contains(@class, 'uiIconDelete')]";
	public final By ELEMENT_PAGE_PERMISSION_POPUP = By.xpath("//span[contains(@class, 'popupTitle') and text()='Page Permissions']");
	public final By ELEMENT_SELECT_THIS_GROUP = By.linkText("Select this Group");
	public final By ELEMENT_SELECT_ROLE_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Select a role']");
	public final By ELEMENT_SELECT_GROUP_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Select a group']");

	/*----------------------Manage Template----------------------*/
	public final By ELEMENT_TEMPLATE_LINK = By.linkText("Template");
	public final By ELEMENT_ADD_TEMPLATE_LINK = By.xpath("//*[text()='Add More Templates']");
	public final String ELEMENT_EDIT_TEMPLATE_ICON = "//*[text()='{$template}']/../..//*[contains(@class, 'uiIconEdit')]";
	public final String ELEMENT_DELETE_TEMPLATE_ICON = "//*[text()='{$template}']/../..//*[contains(@class, 'uiIconDelete')]";
	public final By ELEMENT_SEARCH_TEMPLATE_INPUT=By.id("TemplateSeachBox");
	//Add template page
	public final By ELEMENT_TITLE_TEMPLATE_INPUT= By.id("titleInput");
	public final By ELEMENT_DESC_TEMPLATE_INPUT= By.id("Description");
	public final By ELEMENT_CONTENT_TEMPLATE_INPUT= By.id("Markup");
	public final By ELEMENT_SAVE_TEMPLATE_INPUT= By.id("UISubmitToolBarBottom_SaveTemplate_"); 
	public final By ELEMENT_PREVIEW_TEMPLATE = By.xpath("//*[@id='UIEditorTabs']//*[text()='Preview']");
	public final By ELEMENT_CANCEL_ADD_TEMPLATE = By.id("UISubmitToolBarUpper_Cancel_");

	//Message
	public final String MSG_CREATE_TEMPLATE = "Template \"${title}\" has been created successfully.";
	public final String MSG_DELETE_TEMPLATE = "Are you sure you want to delete this template?";
	//Close template list	
	public By ELEMENT_CLOSE_TEMPLATE_LIST=By.xpath("//*[text()='Select Template']/../a[@class='uiIconClose pull-right']");
	//Close preview window
	public By ELEMENT_CLOSE_PREVIEW_WINDOW=By.xpath("//div[text()='Preview']/..//*[@class='uiIconClose']");
	//	Preview template
	public final String ELEMENT_PREVIEW_NEW_TEMPLATE="//div[text()='${TEMPLATE_TITLE}']/../..//*[contains(@class, 'uiIconPreview')]";
	// Verify effect
	public final String VERIFY_BOLD_EFFECT="//strong[text()='${TEMPLATE_CONTENT}']";	
	public final String VERIFY_ITALIC_EFFECT="//em[text()='${TEMPLATE_CONTENT}']";
	public final String VERIFY_STRIKE_EFFECT="//del[text()='${TEMPLATE_CONTENT}']";
	public final String VERIFY_UNDERLINE_EFFECT="//ins[text()='${TEMPLATE_CONTENT}']";
	public final String VERIFY_HEADING_EFFECT="//h1//span[text()='${TEMPLATE_CONTENT}']";
	public final String ELEMENT_VERIFY_BULLET_EFFECT = "//ul//li[text()='${temp0}']/..//li[text()='${temp1}']";
	public final String ELEMENT_VERIFY_NUMBER_EFFECT ="//ol//li//ol//li[text()='${temp1}']";
	public final String ELEMENT_VERIFY_TABLE_EFFECT = "//table//*//th[text()='${temp0}']/../..//*//td[text()='${temp3}']";
	public final String ELEMENT_VERIFY_LINK_EFFECT = "//a[@href='${TEMPLATE_CONTENT}']//span[text()='${TEMPLATE_CONTENT}']";
	//	Template link
	public final String ELEMENT_NEW_TEMPLATE_LINK="//div[@class='Text' and text()='${TEMPLATE_TITLE}']";
	public final String ELEMENT_OLD_TEMPLATE_LINK="//div[@class='Text' and text()='${OLD_TEMPLATE_TITLE}']";

	// Verify Search with no result
	public final String ELEMENT_EMPTY_DATA = "//*[@class='center empty' and contains(text(), 'Empty data')]"; 
	//"//*[@class='noticeEmpty' and text()='Empty data']";

	// Verify Page content
	public final String ELEMENT_VERIFY_PAGE_CONTENT="//*[@class='uiWikiContentDisplay']//p[text()='${TEMPLATE_CONTENT}']";

	/*------------------------Permission space--------------------------------*/
	public final By ELEMENT_PERMISSION_LINK=By.linkText("Permission");
	public final String MSG_PERMISSION_SAVE = "The permission setting has been saved successfully.";
	public final String ELEMENT_EDIT_PAGE_CHECK = "//*[contains(text(), '{$user}')]/../..//*[@title='Edit Pages']";
	public final String ELEMENT_VIEW_PAGE_CHECK = "//*[contains(text(), '{$user}')]/../..//*[@title='View Pages']";
	public final String ELEMENT_ADMIN_PAGE_CHECK = "//*[contains(text(), '{$user}')]/../..//input[contains(@id, 'ADMINPAGE')]";
	public final String ELEMENT_ADMIN_SPACE_CHECK = "//*[contains(text(), '{$user}')]/../..//input[contains(@id, 'ADMINSPACE')]"; 
	public final String ELEMENT_DELETE_PERMISSION = "//tr/td/div[contains(text(),'{$user}')]/../../td/a/i[@class='uiIconDelete']";

	//set permission screen
	public String ELEMENT_USER_CHECKBOX = "//*[text()='${user}']/../..//*[@type='checkbox']"; 
	public By ELEMENT_SEARCH_USER_INPUT = By.id("Quick Search");
	public By ELEMENT_QUICK_SEARCH_BUTTON = By.xpath("//*[contains(@class, 'uiIconSearch')]");
	public By ELEMENT_SELECT_SEARCH = By.name("filter");

	/*-------------------------Page information management------------------------------*/
	public final By ELEMENT_WIKI_PAGE_INFO_FEED = By.xpath("//*[@id='UIWikiPageInfoArea']/div[@class='txtFeed']");
	public final By ELEMENT_COMPARE_TEXT = By.xpath("//div[contains(text(),'Compared With')]");
	public final By ELEMENT_REVISION_LINK = By.xpath("//*[@id='UIWikiPageInfoArea']//a[contains(text(), 'V')]");
	public final String ELEMENT_VERSION_LINK= "//a[contains(text(),'v.{$version}')]";
	public final String ELEMENT_VERSION_LINK_AUX= "//a[contains(text(),'v. {$version}')]";
	public final String ELEMENT_RESTORE_LINK = "//td/label/a[contains(text(), 'v. {$version}')]/../../..//*[@class='uiIconRestore']";
	public final By ELEMENT_COMPARE_BUTTON = By.xpath("//*[text()='Compare the selected versions']");	
	public final By ELEMENT_VIEW_CHANGE = By.linkText("(View Change)");
	public String ELEMENT_ATTACHMENT_NUMBER = "//*[@id='UIWikiPageInfoArea']//a[contains(text(),'${No}')]/*[@class='uiIconAttach']";
	public By ELEMENT_ATTACHMENT_ICON = By.xpath("//*[@id='UIWikiPageInfoArea']//*[@class='uiIconAttach']");
	public String ELEMENT_CREATOR_PAGE_INFO = "//*[@id='UIWikiPageInfoArea']//a[1][text()='${fullName}']";
	public String ELEMENT_UPDATER_PAGE_INFO = "//*[@id='UIWikiPageInfoArea']//a[2][text()='${fullName}']";
	public final By ELEMENT_ADD_MORE_RELATION_BUTTON = By.xpath("//button[text()='Add More Relations']");

	// Wiki page > View Change
	public final String ELEMENT_CHANGES_COMPARE_VERSION = "//*[text()='${1stNumber}']/../b[text()='${2ndNumber}']/../..//a[@class='changes']";
	public final String ELEMENT_VERSION_CHECKBOX="//input[@id='version_{$version}']";
	public String ELEMENT_LINE_REMOVE = "//*[@class='diffremoveword' and text()='${lineRemove}']";
	public String ELEMENT_LINE_ADD = "//*[@class='diffaddword' and text()='${lineAdd}']";

	// Go to Wiki page > More > Page info > Add more relations
	public final String ELEMENT_SELECTED_PAGE = "//div[contains(@class,'popupContent')]//*[@id='iconTreeExplorer' and contains(@onclick, 'event')]//a[contains(text(), '${relatedPage}')]"; 
	public final String ELEMENT_RELATED_PAGE = "//*[text()='Related Pages']/..//a[contains(text(),'${relatedPage}')]";
	public By ELEMENT_SELECT_SPACE = By.xpath("//*[contains(text(), 'Select the Wiki:')]/..//*[@class='btn dropdown-toggle']");
	public final String ELEMENT_REMOVE_RELATED_PAGE_LINK = "//*[contains(text(),'${relatedPage}')]/ancestor::table//*[@class='uiIconDelete']";
	public By ELEMENT_NO_SPACE_OPTION = By.id("UISpaceSwitcher_nospace");

	//Wiki page > Revisions page
	public final String ELEMENT_CURRENT_VERSION = "//*[@id='UIWikiPageInfo']//a[text()='Current Version (v.${version})']";
	public final By ELEMENT_DISABLE_COMPARE_BUTTON = By.xpath("//*[contains(@class, 'disableButton') and text()='Compare Selected']");
	public final By ELEMENT_DISABLE_COMPARE_BUTTON_AUX = By.xpath("//*[contains(@class, 'disableButton') and text()='Compare the selected versions']");

	/*------------------------My spaces/space----------------------------------*/
	public final String ELEMENT_SPACE_WIKI = "//a[text()='${spaceName}']/..//a[text()='Wiki']";
	public final By ELEMENT_TITLE_WIKI_HOME = By.xpath("//*[@id='titleInfo' and text()='Wiki Home']");
	public final By ELEMENT_WIKI_TAB = By.xpath("//a[@class='ApplicationAdd' and text()='Wiki']");

	/*-----------------------Watch/UnWatch page-------------------------------*/
	public final String MESSAGE_WATCH_PAGE = "You have started watching this page now.";
	public final String MESSAGE_UNWATCH_PAGE = "You have stopped watching this page now.";


	//================== PLF4/Common function for Wiki ==================//
	//////
	/**
	 * @author vuna2
	 */
	public void goToIntranetHomePage(){
		Utils.pause(500);
		click(ELEMENT_INTRANET_HOME_PAGE);
		waitForTextPresent("Join a space");
	}

	/**
	 * Go to Wiki
	 * @author hakt
	 */
	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li> 
	 */
	public void goToWiki(){
		info("--Go to Wiki--");
		Utils.pause(1000);
		click(ELEMENT_WIKI_LINK);
		waitForAndGetElement(ELEMENT_TITLE_WIKI_HOME_LINK);	
	}

	/**
	 * Go to wiki home
	 * @author hakt
	 */
	public void goToWikiHome(){
		info("-- Go to Wiki Home page --");
		if (waitForAndGetElement(ELEMENT_WIKI_HOME_LINK, 3000, 0, 2) != null){
			click(ELEMENT_WIKI_HOME_LINK);
			waitForAndGetElement(ELEMENT_WIKI_HOME_PAGE);
		}else{
			click(ELEMENT_TITLE_WIKI_HOME_LINK);
		}
		Utils.pause(200);
	}

	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li> 
	 * Go to add blank wiki page
	 * @author hakt
	 */
	public void goToAddBlankPage(){
		info("--Go to add blank wiki page--");
		Utils.pause(500);
		//mouseOver(ELEMENT_ADD_PAGE_LINK, true);
		mouseOverAndClick(ELEMENT_ADD_PAGE_LINK);
		mouseOverAndClick(ELEMENT_BLANK_PAGE_LINK);
		Utils.pause(1000);
	}

	/** Go to Delete Page
	 * @author HangNTT
	 */
	public void goToDeletePage()
	{
		info("Deleting a wiki page...");
		//mouseOver(ELEMENT_MORE_LINK,true);
		mouseOverAndClick(ELEMENT_MORE_LINK);
		if (waitForAndGetElement(ELEMENT_DELETE_LINK_2, 5000, 0) == null){
			mouseOverAndClick(ELEMENT_DELETE_LINK);
		}else {
			click(ELEMENT_DELETE_LINK_2);
		}
	}

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param wikiPath: an element path indicates how to access wiki page (eg, "Wiki home/WikiTest")
	 */
	public void goToWikiPage(String wikiPath, ManageAccount.userType... user){
		magAcc = new ManageAccount(driver);		
		ManageAccount.userType usr = (ManageAccount.userType) (user.length > 0 ? user[0] : null);

		if (usr != null){
			if (isElementNotPresent(ELEMENT_INPUT_USERNAME)){
				magAcc.signOut();
			}else{
				info("-- User.logIn: " + user);
			}
			magAcc.userSignIn(usr);
			Utils.pause(1000);
		}
		if (waitForAndGetElement(ELEMENT_ADD_PAGE_LINK, 5000, 0) == null){
			goToWiki();
		}		
		//goToWikiPage(wikiPath);
		driver.navigate().refresh();
		Utils.pause(2000);
		//String bExpandIcon = "//*[@class='node']//*[contains(text(), '{$node}')]"; 
		String[] nodes = wikiPath.split("/");
		int length = nodes.length -1;
		for (int index = 0;index < length;index++)
		{ 	
			String node = nodes[index];
			String nodeNext = nodes[index+1];

			//if(waitForAndGetElement(bExpandIcon.replace("{$node}",nodeNext),5000,0) == null){
			//	click(bExpandIcon.replace("{$node}",node));
			//}
			if(waitForAndGetElement(ELEMENT_NODE_WIKI_PAGE.replace("{$node}",nodeNext),5000,0) == null){
				click(ELEMENT_NODE_WIKI_PAGE.replace("{$node}",node));
			}  
			Utils.pause(100);
		}
		String nodeLast = nodes[length];
		click(By.linkText(nodeLast));
		Utils.pause(2000);
	}

	//Wiki page > Template page
	/**
	 * Go to add template wiki page
	 * @author hakt
	 */
	public void goToAddTemplateWikiPage(){
		info("--Go to add template wiki page--");
		Utils.pause(500);
		mouseOverAndClick(ELEMENT_ADD_PAGE_LINK);
		mouseOverAndClick(ELEMENT_FROM_TEMPLATE_LINK);
		waitForTextPresent("Template Title");
	}	

	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li> 
	 *  Go to Move Page
	 * @author HangNTT
	 */
	public void goToMovePage(){
		Utils.pause(500);
		//mouseOver(ELEMENT_MORE_LINK,true);
		mouseOverAndClick(ELEMENT_MORE_LINK);
		mouseOverAndClick(ELEMENT_MOVE_PAGE_LINK);
	}

	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Move Page
	 * @author: HangNTT
	 * @param pageName1
	 * @param pageName2
	 */
	public void movePage(String pageName1, String pageName2, Object...opts){
		String space = (String) (opts.length > 0 ? opts[0] : "");
		Boolean verify = (Boolean) (opts.length > 1 ? opts[1] : true);

		button = new Button(driver);
		//By ELEMENT_VERIFY_CURRENT_LOCATION = By.xpath("//label[text()='Current Location :']/../..//*[contains(text(), '"+ pageName1 +"')]");
		//By ELEMENT_VERIFY_NEW_LOCATION = By.xpath("//label[text()='New Location :']/../..//*[contains(text(), '"+ pageName2 +"')]");
		By ELEMENT_VERIFY_CURRENT_LOCATION = By.xpath("//label[contains(text(), 'Current Location')]/../..//*[contains(text(), '"+ pageName1 +"')]");
		By ELEMENT_VERIFY_NEW_LOCATION = By.xpath("//label[contains(text(), 'New Location')]/../..//*[contains(text(), '"+ pageName2 +"')]");
		By ELEMENT_VERIFY_AFTER_MOVE_PAGE = By.xpath("//*[contains(text(), '"+pageName2+"')]/ancestor::li[@class='node']//ul//*[contains(text(), '"+pageName1+"')]");
		//("//a[@data-original-title='"+ pageName2 +"']/ancestor::div[@id = 'UITreeExplorer']//*[@data-original-title = '"+pageName1+"']");
		By ELEMENT_NEW_LOCATION = By.xpath("//*[contains(@class, 'popupContent')]//*[contains(@onclick, 'event')]//a[contains(text(), '"+ pageName2 +"')]");
		//("//*[@id='iconTreeExplorer' and contains(@onclick, 'event')]//a[@data-original-title = '"+ pageName2 +"']");

		info("Move a page");
		goToMovePage();
		waitForAndGetElement(ELEMENT_MOVE_PAGE_POPUP);

		if (space != ""){
			click(ELEMENT_SELECT_SPACE_DESTINATION);
			if (space == "Intranet"){
				click(ELEMENT_PORTAL_NAME_SELECTED);
			}else {
				click(ELEMENT_SPACE_NAME_SELECTED.replace("${space}", space.toLowerCase()));
			}
		}
		info("CURRENT_LOCATION");
		waitForAndGetElement(ELEMENT_VERIFY_CURRENT_LOCATION);
		click(ELEMENT_NEW_LOCATION);
		waitForAndGetElement(ELEMENT_VERIFY_NEW_LOCATION);

		//click(CLICK_MOVE_ACTION);
		click(button.ELEMENT_MOVE_BUTTON);
		if (verify){
			waitForAndGetElement(ELEMENT_VERIFY_AFTER_MOVE_PAGE);
		}
		Utils.pause(1000);
	}

	/** 
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Go to Page Permission
	 * @author: HangNTT
	 */
	public void goToPagePermission(){
		//Utils.pause(2000);
		if(waitForAndGetElement(ELEMENT_PAGE_PERMISSION_POPUP, 5000, 0) == null) {
			mouseOverAndClick(ELEMENT_MORE_LINK);
			mouseOverAndClick(ELEMENT_PAGE_PERMISSION_LINK);	
		}
		Utils.pause(1000);
		info("-- Go to Page Permissions...successful");
		/*mouseOverAndClick(ELEMENT_MORE_LINK);
		mouseOverAndClick(ELEMENT_PAGE_PERMISSION_LINK);
		waitForElementPresent(ELEMENT_PAGE_PERMISSION_POPUP);*/
	}

	/** Go to space setting
	 * @author thuntn
	 */
	public void goToSpacePermission(){
		if (isElementNotPresent(ELEMENT_PERMISSION_LINK)){
			mouseOverAndClick(ELEMENT_BROWSE_LINK);
			mouseOverAndClick(ELEMENT_WIKI_SETTING_LINK);
			click(ELEMENT_PERMISSION_LINK);
		}
		Utils.pause(1000);
		waitForAndGetElement(ELEMENT_SELECT_USER);
	}

	/**
	 * Migrate to PLF 4
	 */
	 /**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param wikiPath: an element path indicates how to access wiki page (eg, "Wiki home/WikiTest")
	 */
	public void goToPageInfo(Object... optional){
		magAcc = new ManageAccount(driver);
		ManageAccount.userType usr = (ManageAccount.userType) (optional.length > 0 ? optional[0] : null);
		String path = (String) (optional.length > 1 ? optional[1] : "");
		if (usr != null){
			if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
				magAcc.signOut();
			}else{
				info("-- User.logIn: " + usr);
			}
			magAcc.userSignIn(usr);
		}	

		if (isTextNotPresent("Wiki Home")){
			goToWiki();
		}

		goToWikiPage(path);
		//goToPageInfo();
		Utils.pause(1000);
		//mouseOver(ELEMENT_MORE_LINK,true);
		mouseOverAndClick(ELEMENT_MORE_LINK);
		mouseOverAndClick(ELEMENT_PAGE_INFO_LINK);
		//waitForTextPresent("Summary");
		Utils.pause(2000);
	}

	/**
	 * @author lientm
	 */
	public void goToPageInfoFromCurrentPage(){
		mouseOverAndClick(ELEMENT_MORE_LINK);
		mouseOverAndClick(ELEMENT_PAGE_INFO_LINK);
		waitForTextPresent("Summary");
	}

	/** function go to Wiki page of a Space
	 * @author lientm
	 * @param spaceName
	 */
	public void goToWikiFromSpace(String spaceName){
		magMember = new ManageMember(driver);
		By element_space = By.linkText(spaceName);
		//By element_wiki = By.xpath(ELEMENT_SPACE_WIKI.replace("${spaceName}", spaceName));

		info("Go to wiki page of space " + spaceName);
		//mouseOver(ELEMENT_MY_SPACES_LINK, true);
		//mouseOver(element_space, true);
		//mouseOverAndClick(element_wiki);
		Utils.pause(1000);
		if (isElementNotPresent(ELEMENT_WIKI_LINK_IN_SPACE)){
			magMember.goToMySpacePage();
			click(element_space);
			Utils.pause(2000);
		}
		click(ELEMENT_WIKI_LINK_IN_SPACE);
		waitForAndGetElement(ELEMENT_TITLE_WIKI_HOME);
	}

	/** Go to the template management page
	 * @author thuntn
	 */
	public void goToTemplateManagement(){
		info("--Go to the template management page--");
		if (isElementNotPresent(ELEMENT_TEMPLATE_LINK)){
			mouseOverAndClick(ELEMENT_BROWSE_LINK);
			mouseOverAndClick(ELEMENT_WIKI_SETTING_LINK);
			click(ELEMENT_TEMPLATE_LINK);
		}
		waitForAndGetElement(ELEMENT_ADD_TEMPLATE_LINK);
	}

	/** Attach a file
	 * @author thuntn
	 * @param link
	 */
	public void attachFileInWiki(String link, Integer...type ){
		int notDisplay = 0;
		if (type.length > 0){
			if (!(type[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)type[0];
		}

		String path = Utils.getAbsoluteFilePath(link);

		info("Attach a file: " + path);
		//driver.switchTo().frame(waitForAndGetElement(ELEMENT_FRAME_UPLOAD));
		//ELEMENT_UPLOAD_FILE = By.xpath("//input[@id='WikiUploadFile']");

		try{
			//WebElement element = waitForAndGetElementNotDisplay(ELEMENT_UPLOAD_FILE);
			WebElement element = waitForAndGetElement(ELEMENT_UPLOAD_FILE, 10000, 1, notDisplay);
			element.sendKeys(path);

		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			attachFileInWiki(link);

		}  catch (ElementNotVisibleException e) {

			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			attachFileInWiki(link);

		} finally {
			loopCount = 0;
		}

		switchToParentWindow();
		waitForAndGetElement(By.linkText(link.replace("TestData/", "")));
	}

	/** Delete an attachment
	 * @author thuntn
	 * @param fName: name of a file which will be deleted
	 */
	public void deleteAnAttachment(String fName){
		info("--Delete an attachment--");
		String removeIcon= ELEMENT_REMOVE_ATTACHMENT.replace("{$file}", fName);

		//click(ELEMENT_EDIT_PAGE_LINK);
		click(removeIcon);
		waitForElementNotPresent(removeIcon);
	}

	//Wiki page > Search
	/** Quick search
	 * @author thuntn
	 * @param keyword
	 */
	public void quickSearch(String keyword){
		info("--Using quick search option ...--");
		type(ELEMENT_QUICK_SEARCH, keyword, true);
		((JavascriptExecutor) driver).executeScript("javascript:eXo.wiki.UIWikiSearchBox.doAdvanceSearch();");
		//waitForTextPresent("Search Results");
		info("Return " + getText(ELEMENT_SEARCH_RESULT) + " results");
		Utils.pause(2000);
	}

	/**Advance search
	 * @author thuntn
	 * @param keyword:
	 * @param space: space name which is searched
	 */
	public void advancedSearch(String keyword, String...space){
		info("--Advanced Search--");

		type(ELEMENT_QUICK_SEARCH, "advance search", true);
		//type(ELEMENT_QUICK_SEARCH,keyword,true);
		((JavascriptExecutor) driver).executeScript("javascript:eXo.wiki.UIWikiSearchBox.doAdvanceSearch();");
		Utils.pause(1000);
		type(ELEMENT_SEARCH_ADVANCE,keyword,true);
		if(space.length > 0)
			select(By.name("wikis"), space[0]);
		click(ELEMENT_SEARCH_BUTTON);	
		Utils.pause(1000);
	}

	public void goToAddRelation(){
		goToPageInfoFromCurrentPage();
		click(ELEMENT_ADD_MORE_RELATION_BUTTON);
		waitForAndGetElement(ELEMENT_SELECT_SPACE);
	}

	public void goToExportPageAsPDF(){
		info("Export wiki page as pdf");
		mouseOverAndClick(ELEMENT_MORE_LINK);
		mouseOverAndClick(ELEMENT_EXPORT_AS_PDF_LINK);
		Utils.pause(1000);
	}

	public void goToPermalink(){
		info("Go to permalink");
		mouseOverAndClick(ELEMENT_MORE_LINK);
		mouseOverAndClick(ELEMENT_PERMALINK_LINK);
		Utils.pause(1000);
	}

	public void watchWikiPage(){
		button = new Button(driver);

		info("Watch this wiki page");
		mouseOverAndClick(ELEMENT_MORE_LINK);
		mouseOverAndClick(ELEMENT_WATCH_LINK);
		waitForTextPresent(MESSAGE_WATCH_PAGE);
		button.ok();
		mouseOverAndClick(ELEMENT_MORE_LINK);
		waitForAndGetElement(ELEMENT_UNWATCH_LINK);
	}

	public void unwatchWikiPage(){
		button = new Button(driver);

		info("Watch this wiki page");
		mouseOverAndClick(ELEMENT_MORE_LINK);
		mouseOverAndClick(ELEMENT_UNWATCH_LINK);
		waitForTextPresent(MESSAGE_UNWATCH_PAGE);
		button.ok();
		mouseOverAndClick(ELEMENT_MORE_LINK);
		waitForAndGetElement(ELEMENT_WATCH_LINK);
		mouseOverAndClick(ELEMENT_MORE_LINK);
	}
}
