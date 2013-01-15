package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;
import org.openqa.selenium.By;

/**
 * Migrate to PLF 4
 * @author vuna2
 *
 */
public class BasicAction extends Permission{

	// Wiki page
	/*===================== Add Page ====================*/	


	/** Add a wiki page from blank
	 * @author thuntn
	 * @param title
	 * @param content
	 * @param mode =1: edit a wiki page in richtext
	 * mode =0 : edit a wiki page in source editor
	 */
	public void addBlankWikiPage(String title, String content, int mode, Object... option){
		info("-- Adding a new wiki page... --");
		//boolean  verify = option.length > 0 ? option[0] : false;
		boolean ca = (Boolean) (option.length > 0 ? option[0] : false);
		String message = (String) (option.length > 1 ? option[1] : "");
		pause(2000);

		goToAddBlankPage();
		info("--Add a wiki page from blank--");

		if (mode == 1){ 
			addWikiPageRichText(title, content);
		}
		else{
			addWikiPageSourceEditor(title, content);
		}
		
		if (ca){
			//cancel();
			click(ELEMENT_CANCEL_BUTTON_ADD_PAGE);
			waitForWikiConfirmation("Are you sure to leave this page?");
			waitForElementNotPresent(ELEMENT_TITLE_WIKI_INPUT);
		}else{
			//save();
			click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
			//waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		}
		
		if (!message.isEmpty()){ 
			waitForMessage(message);
			click(ELEMENT_OK_BUTTON);
		}
		
		/*if (verify){
			waitForTextPresent(content);
		}*/
		pause(1000);
	}

	/**
	 * Modify data with source editor
	 * @author hakt
	 * updated: thuntn
	 * @param title
	 * @param content
	 */
	public void addWikiPageSourceEditor(String title, String content){
		info("Modify data with source editor");
		if(title != null)
			type(ELEMENT_TITLE_WIKI_INPUT, title, true);
		if(content != null)
			type(ELEMENT_CONTENT_WIKI_INPUT,content,true);
		pause(1000);
	}

	/**
	 * Modify data with rich editor
	 * @author hakt
	 * updated: thuntn
	 * @param title
	 * @param content
	 */
	public void addWikiPageRichText(String title, String content){
		if(title != null)
			type(ELEMENT_TITLE_WIKI_INPUT, title, true);
		click(ELEMENT_RICHTEXT_BUTTON);
		waitForElementPresent(ELEMENT_SOURCE_EDITOR_BUTTON);
		if (content != null){
			inputDataToFrame(ELEMENT_CONTENT_WIKI_FRAME, content,true);
			pause(1000);
			driver.switchTo().defaultContent();
		}
		pause(1000);

	}

	/**
	 * @author vuna2
	 * @param totalPages: number of created page (int)
	 * @param wikiParentPath: parent (path) of created page 
	 * @param titlePage: page name (String)
	 * @param contentPage: page content (String)  
	 * @param mode = 1 : edit a wiki page in richtext
	 *        mode = 0 : edit a wiki page in source editor
	 */
	public void addBlankWikiPageInAdvance(int totalPages, String[] wikiParentPath, String[] titlePage, String[] contentPage, int mode){
		goToWiki();	
		for (int i = 0; i < totalPages; i++){
			goToWikiPage(wikiParentPath[i]);
			addBlankWikiPage(titlePage[i], contentPage[i], mode);
		}
		pause(1000);
	}

	/** Edit a wiki page
	 * @author thuntn
	 * @param title
	 * @param content
	 * @param mode =0 : edit a wiki page in source editor
	 * mode =1: edit a wiki page in richtext
	 * If you don't want to edit any field, you can pass null value to the respective parameter
	 */
	public void editWikiPage(String title, String content, int mode)
	{
		info("--Edit a wiki page--");
		pause(3000);
		mouseOverAndClick(ELEMENT_EDIT_PAGE_LINK);
		if(mode == 0){
			addWikiPageSourceEditor(title, content);
		}else{
			addWikiPageRichText(title, content);
		}
		//In PLF4, there is no more Minor Edit Option
		/*if (minorEdit) {
			click(ELEMENT_MINOR_EDIT_BUTTON);
			waitForElementNotPresent(ELEMENT_MINOR_EDIT_BUTTON);
		}*/		
		//save();
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		pause(5000);
	}

	//===========Delete wiki page ===========//
	/** Delete a wiki page
	 * @author thuntn
	 */
	public void deleteCurrentWikiPage(boolean... cancel)
	{
		boolean ca = cancel.length > 0 ? cancel[0] : false;
		info("--Delete a wiki page--");
		goToDeletePage();
		if (ca){
			click(ELEMENT_CANCEL_BUTTON);
			waitForElementNotPresent(ELEMENT_CANCEL_BUTTON);
		}else{
			click(ELEMENT_OK_BUTTON);
			waitForElementNotPresent(ELEMENT_OK_BUTTON);
		}
		pause(1000);
	}

	/**
	 * @author vuna2
	 * @param wikiPath: an element path indicates how to access wiki page (eg, "Wiki home/WikiTest")
	 */
	public void deleteWikiPage(String[] wikiPath){
		String[] nodes = null;
		String pageName = "";
		pause(500);
		for(int i = 0; i < wikiPath.length; i++){
			nodes = wikiPath[i].split("/");
			pageName = nodes[nodes.length-1];
			goToWikiPage(wikiPath[i]);
			deleteCurrentWikiPage();
			waitForTextNotPresent(pageName);
		}
	}


	//=========== Preview wiki page  =========//
	/**
	 * Preview wiki page before saving
	 * @param title : Page title
	 * @param content : Page content
	 * @param mode : editor mode
	 *             - 1 for RichTextEditor
	 *             - 0 for textarea element
	 * @author dunghm
	 */
	public void previewWikiPage(String title, String content,int mode){
		mouseOverAndClick(ELEMENT_ADD_PAGE_LINK);
		mouseOverAndClick(ELEMENT_BLANK_PAGE_LINK);
		type(ELEMENT_TITLE_WIKI_INPUT, title, true);
		if ( mode == 1 ){
			click(ELEMENT_RICHTEXT_BUTTON);
			waitForElementPresent(ELEMENT_SOURCE_EDITOR_BUTTON);
			inputDataToFrame(ELEMENT_CONTENT_WIKI_FRAME, content,true);
			driver.switchTo().defaultContent();
		}
		else {
			type(ELEMENT_CONTENT_WIKI_INPUT,content,true);
		}
		click(ELEMENT_PREVIEW_BUTTON);
		waitForElementPresent(ELEMENT_PREVIEW_SCREEN);
	}

	/**
	 * Verify a specific confirm message in wiki 
	 * @param message : message for confirmation
	 * @param isCancel : OK will be click by default, but if isCancel is set true, Cancel button will be click
	 */
	public void waitForWikiConfirmation(String message, boolean...isCancel){
		//By btnOK = By.xpath("//input[@type='button' and @value='OK']");
		//By btnCancel = By.xpath("//input[@type='button' and @value='Cancel']");
		By messageLocator = By.xpath("//div[@class='confirmMessage' and text()='" + message + "']");

		waitForElementPresent(messageLocator);
		if(isCancel.length > 0 && (isCancel[0] == true)) 
			//click(btnCancel);
			cancel();
		else
			//click(btnOK);
			click(ELEMENT_OK_BUTTON);
		waitForElementNotPresent(messageLocator);
	}

	/*================ Related page ============*/
	/**
	 * @author vuna2
	 * @param wikiPath: an element path indicates how to access wiki page (eg, "Wiki home/WikiTest")
	 * @param pageName: name of related page (String)
	 */
	public void addRelatedPage(String wikiPath, String pageName){
		//goToWikiPage(wikiPath);
		goToPageInfo(null, wikiPath);

		click(ELEMENT_ADD_MORE_RELATION_BUTTON);
		click(By.xpath(ELEMENT_SELECTED_PAGE.replace("${relatedPage}", pageName)));
		pause(500);
		click(ELEMENT_SELECT_BUTTON);
		waitForElementPresent(ELEMENT_RELATED_PAGE.replace("${relatedPage}", pageName));
	}

	/**
	 * @author vuna2
	 * @param delete: boolean (true = delete a page / false = just click on Remove link)
	 * @param direct: boolean (user is currently stay in page info window)
	 * @param wikiPath: an element path indicates how to access wiki page (eg, "Wiki home/WikiTest")
	 * @param pageName: name of related page (String)
	 */
	public void removeRelatedPage(boolean delete, boolean direct, String wikiPath, String pageName){
		if (direct){
			click(By.xpath(ELEMENT_REMOVE_RELATED_PAGE_LINK.replace("${relatedPage}", pageName)));
		}else{
			goToWikiPage(wikiPath);
			goToPageInfo();
			click(By.xpath(ELEMENT_REMOVE_RELATED_PAGE_LINK.replace("${relatedPage}", pageName)));
		}
		if (delete){
			acceptAlert();
			waitForElementNotPresent(ELEMENT_RELATED_PAGE.replace("${relatedPage}", pageName));
		}else{
			cancelAlert();
			waitForElementPresent(ELEMENT_RELATED_PAGE.replace("${relatedPage}", pageName));
		}
		pause(1000);
	}

	//////////
	//==== Common of common functions ====//

	/**
	 * @author vuna2
	 * @param totalPages: number of created page (int)
	 * @param wikiParentPath: parent (path) of created page 
	 * @param pageInfo: (pageInfo[0] = page name / pageInfo[1] =  page content)
	 * @param mode = 1 : edit a wiki page in richtext
	 *        mode = 0 : edit a wiki page in source editor
	 * @param wikiPath : an element path indicates how to access wiki page (eg, "Wiki home/WikiTest")
	 * @param pageName : name of related page (String
	 */
	public void addBlankWikiPageAndRelatePage(int totalPages, String[] wikiParentPath, String[][] pageInfo, int mode, String wikiPath, String pageName){
		addBlankWikiPageInAdvance(totalPages, wikiParentPath, pageInfo[0], pageInfo[1], mode);
		addRelatedPage(wikiPath, pageName);		
	}

	/**
	 * @author vuna2
	 * @param totalPages: number of created page (int)
	 * @param wikiParentPath: parent (path) of created page 
	 * @param pageInfo: (pageInfo[0] = page name / pageInfo[1] =  page content)
	 * @param mode = 1 : edit a wiki page in richtext
	 *        mode = 0 : edit a wiki page in source editor	 * @param editInfo
	 * @param editInfo : an Array Of Booleans (viewPage/editPage/deletePermission)       
	 * @param user: users or groups that we want to change their permissions
	 */
	public void addBlankWikiPageAndEditPagePermissions(int totalPages, String[] wikiParentPath, String[][] pageInfo, int mode, 
			boolean[] editInfo, String user, Integer... type){
		int notDisplay = 0;
		if (type.length > 0){
			if (!(type[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)type[0];
		}
		addBlankWikiPageInAdvance(totalPages, wikiParentPath, pageInfo[0], pageInfo[1], mode);
		editPagePermission(user, editInfo[0], editInfo[1], editInfo[2], notDisplay);

	}

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param wikiPath: an element path indicates how to access wiki page (eg, "Wiki home/WikiTest")
	 */
	public void resetDataByDeleteWikiPage(userType user, String[] wikiPath){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			magAcc.signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		userSignIn(user);
		goToWiki();
		deleteWikiPage(wikiPath);
	}

	//////////
	/**
	 * @lienTM
	 * function check a user can view and edit wiki page 
	 * @param element_page
	 * @param content: old content of page
	 * @param new_content: New content of page
	 */
	public void checkViewEditPage(By element_page, String content, String new_content){
		goToWiki();
		click(element_page);
		waitForTextPresent(content);
		editWikiPage(null, new_content, 0);
		waitForTextPresent(new_content);
		info("User can view and edit page");
	}

	/**
	 * @lienTM
	 * function: check permission default when just add user for wiki page then add Edit page permission
	 * @param user: user/group
	 */
	public void checkAndEditPagePermission(String user, Integer... type){
		int notDisplay = 0;
		if (type.length > 0){
			if (!(type[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)type[0];
		}

		By EditPage = By.xpath(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", user)) ;
		//By.xpath(ELEMENT_EDIT_PAGE_CHECK.replace("{$user}", user));
		By ViewPage = By.xpath(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", user));
		//By.xpath(ELEMENT_VIEW_PAGE_CHECK.replace("{$user}", user));

		info("Check user permission default has view permission but does not have edit page permission");
		goToPagePermission();
		assert !waitForAndGetElement(EditPage, 5000, 0, notDisplay).isSelected();
		assert waitForAndGetElement(ViewPage, 5000, 0, notDisplay).isSelected();
		close();
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Add edit page permission for " + user);
		editPagePermission(user, true, true, false, notDisplay);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
	}


	//Wiki page
	/*================= Paragraph =================*/
	/**
	 * @author thaopth
	 * @param paragraphTitle: input paragraph title without space character
	 * @param paragraphContent: input paragraph content with heading followed help tips
	 */
	public void editParagraph (String paragraphTitle, String paragraphContent) {
		String ELEMENT_PARAGRAPH_ID = "H"+paragraphTitle;

		mouseOver(By.id(ELEMENT_PARAGRAPH_ID), true);

		click(By.xpath("//a[@title='Edit section: " + paragraphTitle + "']"));
		type(ELEMENT_CONTENT_WIKI_INPUT,paragraphContent,true);

		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
	}

}
