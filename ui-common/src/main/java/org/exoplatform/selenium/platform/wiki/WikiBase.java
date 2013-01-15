package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
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
public class WikiBase extends ManageMember {

	ManageAccount magAcc = new ManageAccount(driver);
	UserGroupManagement userGroup = new UserGroupManagement(driver);
	Button button = new Button(driver);

	/*-----------------Page control area-------------------*/

	//Intranet home page
	public final By ELEMENT_INTRANET_HOME_PAGE = By.xpath("//ul[@class='uiCompanyNavigations']//li/a[text()='Home']");
	
	//Company/Left panel > Wiki Link
	public final By ELEMENT_WIKI_LINK=By.xpath("//ul[@class='uiCompanyNavigations']//li/a[text()='Wiki']");

	//Add page menu
	public final By ELEMENT_ADD_PAGE_LINK = By.xpath("//*[@id='UIWikiPageControlArea_PageToolBar']//div[contains(text(),'Add Page')]");
	public final By ELEMENT_BLANK_PAGE_LINK = By.linkText("Blank Page");
	public final By ELEMENT_FROM_TEMPLATE_LINK = By.linkText("From Template...");

	//Edit menu
	public final By ELEMENT_EDIT_PAGE_LINK= By.xpath("//*[@id='UIWikiPageControlArea_PageToolBar']//li/div[contains(text(), 'Edit')]");
	public final By ELEMENT_MINOR_EDIT_BUTTON = By.xpath(".//*[@id='UISubmitToolBarUpper']/a[2]");

	//More menu
	public final By ELEMENT_MORE_LINK = By.xpath("//*[@id='UIWikiPageControlArea_PageToolBar']//div[contains(text(), 'More')]");
	public final By ELEMENT_DELETE_LINK = By.linkText("Delete Page");
	public final By ELEMENT_WATCH_LINK = By.linkText("Watch");
	public final By ELEMENT_UNWATCH_LINK = By.linkText("Stop Watching");
	public final By ELEMENT_PAGE_INFO_LINK = By.linkText("Page Info");
	public final By ELEMENT_PAGE_PERMISSION_LINK = By.linkText("Page Permissions");
	public final By ELEMENT_MOVE_PAGE_LINK = By.linkText("Move Page");

	//BreadCrumb
	public final By ELEMENT_WIKI_HOME = By.linkText("Wiki Home");

	//Browse menu
	public final By ELEMENT_BROWSE_LINK = By.xpath("//div[contains(text(),'Browse')]");
	public final By ELEMENT_WIKI_SETTING_LINK= By.linkText("Wiki Settings");
	//Search area
	public final By ELEMENT_QUICK_SEARCH = By.id("wikiSearchValue");

	/*------------------add wiki page---------------------*/
	//Source Editor mode
	public final By ELEMENT_TITLE_WIKI_INPUT = By.id("titleInput");
	public final By ELEMENT_CONTENT_WIKI_INPUT = By.id("Markup");
	public final By ELEMENT_RICHTEXT_BUTTON = By.xpath("//*[@id='UIEditorTabs']/button[1]");
	//("//a[@title='Rich Text']");
	public final By ELEMENT_PREVIEW_BUTTON = By.xpath("//*[@id='UIEditorTabs']/button[2]");
	//("//a[@title='Preview']");
	public final By ELEMENT_PREVIEW_SCREEN = By.xpath("//div[@class='popupTitle' and text()='Preview']");

	//Richtext mode
	public final By ELEMENT_SOURCE_EDITOR_BUTTON= By.xpath("//a[contains(text(),'Source Editor')]");
	public final By ELEMENT_CONTENT_WIKI_FRAME = By.xpath("//div[@class='xRichTextEditor']/iframe");

	//Upload file area
	public By ELEMENT_UPLOAD_FILE = By.xpath("//div[@class='uiUploadInput']/input"); 
	//By.id("WikiUploadFile");
	public final By ELEMENT_FRAME_UPLOAD=By.xpath("//div[@class='uiUploadInput']/iframe");
	//("//div[@title='Upload New File']/iframe");
	public final String ELEMENT_REMOVE_ATTACHMENT = "//a[text()='{$file}']/../../td/a/i[@class='uiIconDelete']";
	//"//a[contains(text(),'{$file}')]/../../td/img[@title='Remove Attachment']";

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

	/*-------------------------More/page permission page--------------------*/
	public final By ELEMENT_SELECT_USER = By.xpath("//a[contains(@onclick, 'OpenSelectUserForm')]");
	//("//img[@title='Select User']");
	public final String ELEMENT_USERNAME_CHECK = "//input[@id='${user}' and @type='checkbox']";
	public final By ELEMENT_SELECTOR_TEXT = By.xpath("//span[@class='PopupTitle' and contains(text(),'User Selector')]");
	public final By ELEMENT_SELECT_INPUT = By.id("PermissionOwner");
	public final By ELEMENT_SELECT_GROUP = By.className("uiIconGroup");
	//("//a[@title='Select Group']");
	public final By ELEMENT_SELECT_MEMBERSHIP = By.className("uiIconMembership");
	//("//a[@title='Select Membership']");
	
	// Go to Wiki page > More > Page Permissions
	public final String ELEMENT_EDIT_PAGE_PERMISSIONS = "//*[contains(text(), '${user}')]/../..//input[contains(@id, 'EDITPAGE')]";
	public final String ELEMENT_VIEW_PAGE_PERMISSIONS = "//*[contains(text(), '${user}')]/../..//input[contains(@id, 'VIEWPAGE')]";
	public final String ELEMENT_DELETE_PERMISSIONS = "//*[contains(text(), '${user}')]/../..//*[contains(@class, 'uiIconDelete')]";
	public final By ELEMENT_PAGE_PERMISSION_POPUP = By.xpath("//span[contains(@class, 'popupTitle') and text()='Page Permissions']");

	/*----------------------Browse/Space setting/ add, edit, delete template-----------------*/
	public final By ELEMENT_TEMPLATE_LINK = By.linkText("Template");
	public final By ELEMENT_ADD_TEMPLATE_LINK = By.xpath("//*[text()='Add More Templates']");
	public final String ELEMENT_EDIT_TEMPLATE_ICON = "//*[@title='{$template}']/../..//*[@class='uiIconEdit']";
	public final String ELEMENT_DELETE_TEMPLATE_ICON = "//*[text()='{$template}']/../..//*[@class='uiIconDelete']";
	public final By ELEMENT_SEARCH_TEMPLATE_INPUT=By.id("TemplateSeachBox");
	//Add template page
	public final By ELEMENT_TITLE_TEMPLATE_INPUT= By.id("titleInput");
	public final By ELEMENT_DESC_TEMPLATE_INPUT= By.id("Description");
	public final By ELEMENT_CONTENT_TEMPLATE_INPUT= By.id("Markup");
	public final By ELEMENT_SAVE_TEMPLATE_INPUT= By.id("UISubmitToolBarBottom_SaveTemplate_"); 
	//Message
	public final String MSG_CREATE_TEMPLATE="is created successfully.";
	public final String MSG_DELETE_TEMPLATE="Are you sure to delete this template?";
	//Close template list	
	public By ELEMENT_CLOSE_TEMPLATE_LIST=By.xpath("//*[text()='Select Template']/../a[@class='uiIconClose pull-right']");
	//Close preview window
	public By ELEMENT_CLOSE_PREVIEW_WINDOW=By.xpath("//div[text()='Preview']/..//*[@class='uiIconClose']");
	//	Preview template
	public final String ELEMENT_PREVIEW_NEW_TEMPLATE="//div[text()='${TEMPLATE_TITLE}']/../..//*[@class='uiIconPreview']";
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
	public final String ELEMENT_EMPTY_DATA="//*[@class='noticeEmpty' and text()='Empty data']";

	// Verify Page content
	public final String ELEMENT_VERIFY_PAGE_CONTENT="//*[@class='uiWikiContentDisplay']//p[text()='${TEMPLATE_CONTENT}']";
	
	/*------------------------Browser/Space setting/ Permission space------------------------*/
	public final By ELEMENT_PERMISSION_LINK=By.linkText("Permission");
	public final String MSG_PERMISSION_SAVE = "The permission setting has been saved successfully.";
	public final String ELEMENT_EDIT_PAGE_CHECK = "//*[contains(text(), '{$user}')]/../..//*[@title='Edit Pages']";
	public final String ELEMENT_VIEW_PAGE_CHECK = "//*[contains(text(), '{$user}')]/../..//*[@title='View Pages']";
	public final String ELEMENT_ADMIN_PAGE_CHECK = "//*[contains(text(), '{$user}')]/../..//input[contains(@id, 'ADMINPAGE')]";
	public final String ELEMENT_ADMIN_SPACE_CHECK = "//*[contains(text(), '{$user}')]/../..//input[contains(@id, 'ADMINSPACE')]"; 
	//public final String ELEMENT_VIEW_SPACE_CHECK = "//tr/td/div[@title='{$user}']/../../td/input[@title='View Pages']";
	public final String ELEMENT_DELETE_PERMISSION = "//tr/td/div[contains(text(),'{$user}')]/../../td/a/i[@class='uiIconDelete']";

	//set permission screen
	public String ELEMENT_USER_CHECKBOX = "//*[text()='${user}']/../..//*[@type='checkbox']"; 
	public By ELEMENT_SEARCH_USER_INPUT = By.id("Quick Search");
	public By ELEMENT_QUICK_SEARCH_BUTTON = By.xpath("//*[contains(@class, 'uiIconSearch')]");
			//("//a[@title='Quick Search']");
	public By ELEMENT_SELECT_SEARCH = By.name("filter");
	//By.id("filter");

	/*-------------------------Page information area---------------------------*/
	public final By ELEMENT_COMPARE_TEXT = By.xpath("//div[contains(text(),'Compared With')]");
	public final By ELEMENT_REVISION_LINK = By.xpath("//*[@id='UIWikiPageInfoArea']//a[contains(text(), 'V')]");
	public final String ELEMENT_VERSION_LINK= "//a[contains(text(),'v. {$version}')]";
	public final String ELEMENT_RESTORE_LINK = "//td/label/a[contains(text(), 'v. {$version}')]/../../..//*[@class='uiIconRestore']";
	public final String ELEMENT_VERSION_CHECKBOX="//input[@id='version_{$version}']";
	public final By ELEMENT_COMPARE_BUTTON = By.xpath("//*[text()='Compare Selected']");	
	public final By ELEMENT_VIEW_CHANGE=By.linkText("(View Change)");

	// Wiki page > View Change
	public final String ELEMENT_CHANGES_COMPARE_VERSION = "//*[text()='${1stNumber}']/../b[text()='${2ndNumber}']/../..//a[@class='changes']";

	public final By ELEMENT_ADD_MORE_RELATION_BUTTON = By.xpath("//button[text()='Add More Relations']");
	//By.linkText("Add More Relations");
	//public final By ELEMENT_SELECT_BUTTON = By.linkText("Select");
	//public final By ELEMENT_SELECT_BUTTON = By.xpath(".//*[@id='UIWikiSelectPageForm']/div[3]/a[text()='Select']");
	//public final By ELEMENT_REMOVE_RELATION_BUTTON = 

	// Go to Wiki page > More > Page info > Add more relations
	public final String ELEMENT_SELECTED_PAGE = "//div[contains(@class,'popupContent')]//*[@id='iconTreeExplorer' and contains(@onclick, 'event')]//a[contains(text(), '${relatedPage}')]"; 
	public final String ELEMENT_RELATED_PAGE = "//*[text()='Related Pages']/..//a[contains(text(),'${relatedPage}')]";
	public final String ELEMENT_REMOVE_RELATED_PAGE_LINK = "//li[contains(text(),'${relatedPage}')]/../../../../td/a/i[@class='uiIconDelete']";

	//Wiki page > Revisions page
	public final String ELEMENT_CURRENT_VERSION = "//a[@title='View Revision' and text()='CURRENT (v. ${version})']";
	public final By ELEMENT_DISABLE_COMPARE_BUTTON = By.xpath("//*[contains(@class, 'disableButton') and text()='Compare Selected']");

	/*------------------------My spaces/space----------------------------------*/
	public final String ELEMENT_SPACE_WIKI = "//a[text()='${spaceName}']/..//a[text()='Wiki']";
	public final By ELEMENT_TITLE_WIKI_HOME = By.xpath("//*[@id='titleInfo' and text()='Wiki Home']");
	public final By ELEMENT_WIKI_TAB = By.xpath("//a[@class='ApplicationAdd' and text()='Wiki']");

	/*-------------------------Go to wiki home---------------------------*/
	public final By ELEMENT_WIKI_HOME_LINK=By.xpath("//a[text()='Wiki Home']");
	public final By ELEMENT_WIKI_HOME_PAGE=By.xpath("//*[@id='titleInfo' and text()='Wiki Home']");

	public final By ELEMENT_SELECT_THIS_GROUP = By.linkText("Select this Group");
	public final By ELEMENT_SELECT_ROLE_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Select a role']");
	public final By ELEMENT_SELECT_GROUP_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Select a group']");

	//Wiki page > Add page
	public final By ELEMENT_SAVE_BUTTON_ADD_PAGE = By.id("UISubmitToolBarBottom_SavePage_");
	public final By ELEMENT_CANCEL_BUTTON_ADD_PAGE = By.id("UISubmitToolBarBottom_Cancel_");
	public final By ELEMENT_CONFIRM_BUTTON_ADD_PAGE = By.xpath("//button[text()='Confirm']");
	public final By ELEMENT_CANCEL_BUTTON_ADD_PAGE_NULL_TITLE = By.xpath("//button[text()='Cancel']");
	public final String MESSAGE_PAGE_ALREADY_EXISTS = "The page title already exists. Please select another one.";
	
	//Wiki page > Move page
	//public final By ELEMENT_MOVE_BUTTON_MOVE_PAGE = By.xpath("//*[@id='UIWikiMovePageForm']//button[contains(text(), 'Move')]");
	public final By ELEMENT_CANCEL_BUTTON_MOVE_PAGE = By.xpath("//*[contains(@class, 'uiWikiMovePageForm')]//button[contains(text(), 'Cancel')]");
	public final By ELEMENT_MOVE_PAGE_POPUP = By.xpath("//*[contains(@class, 'popupTitle') and text()='Move Page']");
	
	//Create page 
	public final String MESSAGE_CANCEL_CREATE_PAGE = "Are you sure to leave this page?";

	//Edit page
	public final By ELEMENT_OK_BUTTON_WIKI_PAGE = By.xpath("//div[contains(@class, 'uiAction')]/a[text()='OK']");

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
		waitForTextPresent("Wiki Home");	
	}

	/**
	 * Go to wiki home
	 * @author hakt
	 */
	public void goToWikiHome(){
		click(ELEMENT_WIKI_HOME_LINK);
		waitForElementPresent(ELEMENT_WIKI_HOME_PAGE);
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
	}

	/** Go to Delete Page
	 * @author HangNTT
	 */
	public void goToDeletePage()
	{
		//mouseOver(ELEMENT_MORE_LINK,true);
		click(ELEMENT_MORE_LINK);
		mouseOverAndClick(ELEMENT_DELETE_LINK);
	}

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param wikiPath: an element path indicates how to access wiki page (eg, "Wiki home/WikiTest")
	 */
	public void goToWikiPage(String wikiPath, userType... user){
		magAcc = new ManageAccount(driver);		
		userType usr = (userType) (user.length > 0 ? user[0] : null);
	
		if (usr != null){
			if (isElementNotPresent(ELEMENT_INPUT_USERNAME)){
				magAcc.signOut();
			}else{
				info("-- User.logIn: " + user);
			}
			userSignIn(usr);
			Utils.pause(1000);
		}
		if (isTextNotPresent("Wiki Home")){
			goToWiki();
		}		
		//goToWikiPage(wikiPath);
		String bExpandIcon = "//*[@class='node']//*[contains(text(), '{$node}')]"; 
		String[] nodes = wikiPath.split("/");
		int length = nodes.length -1;
		for (int index = 0;index < length;index++)
		{ 	
			String node = nodes[index];
			String nodeNext = nodes[index+1];

			if(waitForAndGetElement(bExpandIcon.replace("{$node}",nodeNext),5000,0) == null)
				click(bExpandIcon.replace("{$node}",node));
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
	public void movePage(String pageName1, String pageName2){
		button = new Button(driver);
		By ELEMENT_VERIFY_CURRENT_LOCATION = By.xpath("//label[text()='Current Location']/../..//*[contains(text(), '"+ pageName1 +"')]");
		By ELEMENT_VERIFY_NEW_LOCATION = By.xpath("//label[text()='New Location']/../..//*[contains(text(), '"+ pageName2 +"')]");
		By ELEMENT_VERIFY_AFTER_MOVE_PAGE = By.xpath("//*[contains(text(), '"+pageName2+"')]/ancestor::li[@class='node']//ul//*[contains(text(), '"+pageName1+"')]");
				//("//a[@data-original-title='"+ pageName2 +"']/ancestor::div[@id = 'UITreeExplorer']//*[@data-original-title = '"+pageName1+"']");
		By ELEMENT_NEW_LOCATION = By.xpath("//*[contains(@class, 'popupContent')]//*[contains(@onclick, 'event')]//a[contains(text(), '"+ pageName2 +"')]");
		//("//*[@id='iconTreeExplorer' and contains(@onclick, 'event')]//a[@data-original-title = '"+ pageName2 +"']");

		info("Move a page");
		goToMovePage();
		waitForElementPresent(ELEMENT_MOVE_PAGE_POPUP);

		info("CURRENT_LOCATION");
		waitForElementPresent(ELEMENT_VERIFY_CURRENT_LOCATION);
		click(ELEMENT_NEW_LOCATION);
		waitForElementPresent(ELEMENT_VERIFY_NEW_LOCATION);
		
		//click(CLICK_MOVE_ACTION);
		click(button.ELEMENT_MOVE_BUTTON);
		waitForElementPresent(ELEMENT_VERIFY_AFTER_MOVE_PAGE);
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
		if(isElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP)) {
			Utils.pause(500);
			mouseOverAndClick(ELEMENT_MORE_LINK);
			Utils.pause(500);
			mouseOverAndClick(ELEMENT_PAGE_PERMISSION_LINK);	
		}
		Utils.pause(1000);
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
		waitForElementPresent(ELEMENT_SELECT_USER);
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
		userType usr = (userType) (optional.length > 0 ? optional[0] : null);
		String path = (String) (optional.length > 1 ? optional[1] : "");
		if (usr != null){
			if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
				magAcc.signOut();
			}else{
				info("-- User.logIn: " + usr);
			}
			userSignIn(usr);
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
		waitForTextPresent("Summary");		
	}

	/** function go to Wiki page of a Space
	 * @author lientm
	 * @param spaceName
	 */
	public void goToWikiFromSpace(String spaceName){
		By element_space = By.linkText(spaceName);
		//By element_wiki = By.xpath(ELEMENT_SPACE_WIKI.replace("${spaceName}", spaceName));

		info("Go to wiki page of space " + spaceName);
		//mouseOver(ELEMENT_MY_SPACES_LINK, true);
		//mouseOver(element_space, true);
		//mouseOverAndClick(element_wiki);
		Utils.pause(1000);
		if (isElementNotPresent(ELEMENT_WIKI_LINK_IN_SPACE)){
			goToMySpacePage();
			click(element_space);
			Utils.pause(2000);
		}
		click(ELEMENT_WIKI_LINK_IN_SPACE);
		waitForElementPresent(ELEMENT_TITLE_WIKI_HOME);
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
		waitForElementPresent(ELEMENT_ADD_TEMPLATE_LINK);
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
			WebElement element = waitForAndGetElement(ELEMENT_UPLOAD_FILE, 5000, 0, notDisplay);
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
	}

	/** Delete an attachment
	 * @author thuntn
	 * @param fName: name of a file which will be deleted
	 */
	public void deleteFile(String fName){
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
		info("--Search quick--");
		type(ELEMENT_QUICK_SEARCH, keyword, true);
		((JavascriptExecutor) driver).executeScript("javascript:eXo.wiki.UIWikiSearchBox.doAdvanceSearch();");
		waitForElementPresent(ELEMENT_SEARCH_BUTTON);
		Utils.pause(1000);
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

	/////////	
//	/**function: select a user when set permission for a element
//	 * @author lientm
//	 * @param element: id of element need set permission
//	 * @param user: user that needs to set permission
//	 */
//	public void selectUserPermission(String user, Integer... type){
//		int notDisplay = 0;
//		if (type.length > 1){
//			if (!(type[1] instanceof Integer)) {
//				throw new IllegalArgumentException("-- Argument should be an Integer --");
//			}
//			notDisplay = (Integer)type[1];
//		}
//
//		String[] temp = user.split("/");
//		//Multi-user
//		if (temp.length > 0){
//			for (int i = 0; i < temp.length; i ++){
//				By ELEMENT_USER = By.xpath(ELEMENT_USER_CHECKBOX.replace("${user}", temp[i]));
//				click(ELEMENT_USER, notDisplay);
//			}
//		}else{
//			By ELEMENT_USER = By.xpath(ELEMENT_USER_CHECKBOX.replace("${user}", user));
//			type(ELEMENT_SEARCH_USER_INPUT, user, true);
//			switch (type[0]){
//			case 1:
//				select(ELEMENT_SELECT_SEARCH, "User Name");
//				break;
//			case 2:	
//				select(ELEMENT_SELECT_SEARCH, "First Name");
//				break;
//			case 3:	
//				select(ELEMENT_SELECT_SEARCH, "Last Name");
//				break;
//			case 4:	
//				select(ELEMENT_SELECT_SEARCH, "Email Name");
//				break;
//			default:
//				break;
//			}
//			click(ELEMENT_QUICK_SEARCH_BUTTON);
//			waitForElementPresent(ELEMENT_USER, 5000, 0, notDisplay);
//			if (waitForAndGetElement(ELEMENT_USER, 10000, 0, notDisplay).isSelected() == false){
//				click(ELEMENT_USER, notDisplay);
//			}
//		}	
//		click(ELEMENT_ADD_BUTTON);
//		Utils.pause(1000);
//		waitForElementNotPresent(ELEMENT_SEARCH_ICON);
//	}

	/**function: select a user when set permission for a element
	* @author lientm
	* @param user: user that needs to set permission
	* @param type: way search user in select user popup
	*            = 1: select follow user name
	*            = 2: select follow first name
	*            = 3: select follow last name
	*            = 4: select follow email
	*            = default: select follow user name
	*            = null: select checkbox
	*/
	public void selectUserPermission(String user, Integer...type){
		String[] temp = user.split("/");
		if (temp.length > 0){
			for (int i = 0; i < temp.length; i ++){
				By ELEMENT_USER = By.xpath(ELEMENT_USER_CHECKBOX.replace("${user}", temp[i]));
				By ELEMENT_FIRST = By.xpath("//*[@id='UIListUsers']/tbody/tr[1]/td/*[text()='" + temp[i] + "']");
				By ELEMENT_SECOND = By.xpath("//*[@id='UIListUsers']/tbody/tr[2]");
				if (type.length > i){
					type(ELEMENT_SEARCH_USER_INPUT, temp[i], true);
					switch (type[i]){
					case 1:
						select(ELEMENT_SELECT_SEARCH, "User Name");
						break;
					case 2:
						select(ELEMENT_SELECT_SEARCH, "First Name");
						break;
					case 3:
						select(ELEMENT_SELECT_SEARCH, "Last Name");
						break;
					case 4:
						select(ELEMENT_SELECT_SEARCH, "Email Name");
						break;
					default:
						break;
					}
					click(ELEMENT_QUICK_SEARCH_BUTTON);
					waitForElementPresent(ELEMENT_FIRST);
					waitForElementNotPresent(ELEMENT_SECOND);
				}
				WebElement checkbox = waitForAndGetElement(ELEMENT_USER, DEFAULT_TIMEOUT, 1, 2);
				if (checkbox.isSelected() == false){
					click(ELEMENT_USER, 2);
				}
			}
		}
		click(button.ELEMENT_ADD_BUTTON);
		waitForElementNotPresent(ELEMENT_SEARCH_ICON);
	}
	
	
	/**function: select a group when set permission
	 * @author lientm
	 * @param element: id of element need set permission
	 * @param grouppath: group path
	 */
	public void selectGroupPermission( String grouppath){
		userGroup = new UserGroupManagement(driver);
		/*//By ELEMENT_SELECT_GROUP = By.xpath(ELEMENT_SELECT_GROUP_ICON.replace("${element}", element));
		waitForElementPresent(ELEMENT_SELECT_GROUP);
		click(ELEMENT_SELECT_GROUP);*/
		waitForElementPresent(ELEMENT_SELECT_GROUP_POPUP);
		userGroup.selectGroup(grouppath, true);
		click(ELEMENT_SELECT_THIS_GROUP);
	}

	/**function: select group and membership when set permission
	 * @author lientm
	 * @param element: id of element need set permission
	 * @param groupPath: path to group
	 * @param membership
	 */
	public void selectGroupMembership(String groupPath, String membership){
		userGroup = new UserGroupManagement(driver);
		/*By ELEMENT_SELECT_ROLE = By.xpath(ELEMENT_SELECT_ROLE_ICON.replace("${element}", element));
		waitForElementPresent(ELEMENT_SELECT_ROLE);
		click(ELEMENT_SELECT_ROLE);*/
		waitForElementPresent(ELEMENT_SELECT_ROLE_POPUP);
		userGroup.selectGroup(groupPath, true);	
		click(By.linkText(membership));
		//waitForTextPresent(membership);
	}
}
