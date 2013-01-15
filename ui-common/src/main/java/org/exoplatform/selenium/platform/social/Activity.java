package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


/**
 * 
 * @author thaopth
 * Date: 09/11/2012
 */

/**
 * Update Common functions for part: Activity (Space and People)
 * -- Delete an activity
 * -- Add/Delete a comment
 * -- Like/unlike an activity
 * -- Edit a title/description of a shared link
 * @author vuna2
 * Date: 12/11/2012
 */

public class Activity extends SocialBase {

	//=====Element on space home page=======stash@{1}
	// Go to My Spaces > Select a space
	//Or Go to My Activity Stream
	public final By ELEMENT_ATIVITY_TEXTBOX = By.id("composerInput");
	public final By ELEMENT_LINK = By.linkText("Link");
	public final By ELEMENT_FILE_LINK = By.linkText("File");
	public final By ELEMENT_INPUT_LINK_BOX = By.id("InputLink");
	public final By ELEMENT_SELECT_FILE_BUTTON = By.linkText("Select File"); 
	public final By ELEMENT_SELECT_FILE_POPUP = By.xpath("//span[text()='Select File']");
	public final By ELEMENT_SELECT_BUTTON = By.xpath("input[@value='Select']");
	public final By ELEMENT_UPLOAD_BUTTON = By.id("file");
	public final By ELEMENT_ATTACH_BUTTON = By.id("AttachButton");
	public final By ELEMENT_SHARE_BUTTON = By.id("ShareButton");

	public final String ELEMENT_COMMENT = "//div[@class='ContentBox']//*[contains(text(), '${activityText}')]";
	public final String ELEMENT_SHOW_HIDE_COMMENTS = "/following::*[@class='CommentListInfo']/a[contains(text(), '${inforComment}')]";


	/**
	 * Select a file to post on activity
	 * @param driveName
	 * @param upload
	 * @param folderPath
	 * @param selectFileName
	 * @param uploadFileLink
	 * @param uploadFileName
	 */
	public void selectFile(String driveName, boolean upload, String folderPath, String selectFileName, String uploadFileLink, String uploadFileName) {
		info("-- Selecting a file to post on activity --");

		By ELEMENT_DRIVE_BOX = By.xpath("//select[@onchange='eXo.commons.DocumentSelector.changeDrive(this);']");
		String [] paths = folderPath.split("/");

		waitForElementPresent(ELEMENT_FILE_LINK);

		click(ELEMENT_FILE_LINK);

		waitForElementPresent(ELEMENT_SELECT_FILE_BUTTON);

		info("----Click Select file button----");

		click(ELEMENT_SELECT_FILE_BUTTON);

		waitForElementPresent(ELEMENT_SELECT_FILE_POPUP);	

		info("----Select drive----");

		select(ELEMENT_DRIVE_BOX, driveName);

		info("---Select folder path----");
		for (String path : paths)

			//info("---Select folder path----");

			click(By.xpath("//a[text()='"+ path +"']"));

		if (upload)
		{
			WebElement frame = waitForAndGetElement(ELEMENT_UPLOAD_IMG_FRAME_XPATH);

			driver.switchTo().frame(frame);

			//System.out.println("DEBUG:" + getAbsoluteFilePath(uploadFileLink));

			type(ELEMENT_UPLOAD_IMG_ID, Utils.getAbsoluteFilePath(uploadFileLink), false);

			pause(500);

			switchToParentWindow();	

			waitForElementPresent(By.xpath("//a[text()='"+uploadFileName+"']"));

			click(By.xpath("//a[text()='"+uploadFileName+"']"));

			pause(500);

			click(ELEMENT_SELECT_BUTTON);

			waitForElementPresent(By.xpath("//span[@class='BrowsedDocument' and text()='"+uploadFileName+"']"));
		}
		else 
		{
			click(By.xpath("//a[text()='"+selectFileName+"']"));

			pause(500);

			click(ELEMENT_SELECT_BUTTON);

			waitForElementPresent(By.xpath("//span[@class='BrowsedDocument' and text()='"+selectFileName+"']"));
		}
	}

	/**
	 * Add new activity for space 
	 * @param addText: boolean
	 * @param text: input a text (String)
	 * @param addLink: boolean
	 * @param link: input a link (String)
	 */
	public void addActivity (boolean addText, String text, boolean addLink, String link) {
		info("-- Adding an activity to space --");

		waitForElementPresent(ELEMENT_ATIVITY_TEXTBOX);

		if (addText) 
		{
			info("----Add text into activity text box-----");

			type(ELEMENT_ATIVITY_TEXTBOX, text, true);
		}
		if (addLink)
		{
			waitForElementPresent(ELEMENT_LINK);

			info("----Click on Link----");

			click(ELEMENT_LINK);

			info("----Input link into link box-----");

			type(ELEMENT_INPUT_LINK_BOX, link, true);

			waitForElementPresent(ELEMENT_ATTACH_BUTTON);

			info("----Click attach button-----");

			click(ELEMENT_ATTACH_BUTTON);

			waitForElementPresent(By.id("LinkTitle"));
		}

		waitForElementPresent(ELEMENT_SHARE_BUTTON);

		info("----Click share button----");

		click(ELEMENT_SHARE_BUTTON);

		info("-- Verify that an activity has been added --");

		if (addText) {
			waitForTextPresent(text);
		}
		if (addLink){
			waitForTextPresent(link);
		}
	}

	/**
	 * Delete activity 
	 * @param activityText: input a String 
	 */
	public void deleteActivity (String activityText) {
		info("-- Deleting an activity --");

		By ELEMENT_DELETE_ACTIVITY = By.xpath("//div[@class='ContentBox']/div/h5/span/../../div[contains(text(), '"+activityText+"')]/../h5/span[contains(@id, 'DeleteActivityButton')]");

		WebElement elementDeleteActivityID = waitForAndGetElement(ELEMENT_DELETE_ACTIVITY);

		String deleteActivityIconID = elementDeleteActivityID.getAttribute("id");		

		info("---Click delete activity icon---");

		click(By.id(deleteActivityIconID));

		waitForConfirmation("Are you sure to delete this activity?");

		waitForElementNotPresent(ELEMENT_DELETE_ACTIVITY);

		pause(1000);
	}


	/**
	 * Add a new comment on activity stream
	 * @param activityText: input a text (String) 
	 * @param contentOfComment: input a comment (String)
	 */
	public void addComment(String activityText, String contentOfComment){
		info("-- Adding a new comment --");

		By ELEMENT_COMMENT_OF_ACTIVITY = By.xpath(ELEMENT_COMMENT.replace("${activityText}", activityText) + "/../div[@class='LinkShareActivityIcon']/a[1]");

		By ELEMENT_COMMENT_OF_DEFAULT_ACTIVITY = By.xpath(ELEMENT_COMMENT.replace("${activityText}", activityText) + "/../div[@class='DefaultActivityIcon']/a[1]");

		By ELEMENT_INPUT_COMMENT_TEXT_AREA = By.xpath(ELEMENT_COMMENT.replace("${activityText}", activityText) + "/../../div[2]/div/div/textarea"); 		

		//Add a new comment following an activity 
		if (waitForAndGetElement(ELEMENT_COMMENT_OF_DEFAULT_ACTIVITY, 3000, 0) != null){

			click(ELEMENT_COMMENT_OF_DEFAULT_ACTIVITY);

		}else if (waitForAndGetElement(ELEMENT_COMMENT_OF_ACTIVITY, 3000, 0) != null){

			click(ELEMENT_COMMENT_OF_ACTIVITY);
		}

		type(ELEMENT_INPUT_COMMENT_TEXT_AREA, contentOfComment, true);

		//Click on Comment button
		By ELEMENT_COMMENT_ID = By.xpath(ELEMENT_COMMENT.replace("${activityText}", activityText) + "/../../div[2]/div/div/textarea[contains(@id, 'CommentTextarea')]");

		WebElement elementCommentButtonID = waitForAndGetElement(ELEMENT_COMMENT_ID);

		String elementCommentID = elementCommentButtonID.getAttribute("id");

		String commentButtonID = elementCommentID.replace("CommentTextarea", "CommentButton"); 

		click(By.id(commentButtonID));

		waitForTextPresent(contentOfComment);

		pause(1000);
	}

	/**
	 * Delete a comment
	 * @param contentOfComment: input a comment (String)
	 */
	public void deleteComment(String contentOfComment){
		info("-- Deleting a comment --");

		String ELEMENT_COMMENT_TEXT = "//div[@class='ContentBox']/div[2]/div/div/p[contains(text(), '"+ contentOfComment +"')]";

		By ELEMENT_DELETE_COMMENT_BUTTON = By.xpath( ELEMENT_COMMENT_TEXT + "/../../span[@class='CloseContentBoxNormal']");

		click(ELEMENT_DELETE_COMMENT_BUTTON);

		waitForConfirmation("Are you sure to delete this comment?");

		waitForElementNotPresent(ELEMENT_DELETE_COMMENT_BUTTON);

		pause(1000);
	}

	/**
	 * Like/Unlike an activity
	 * @param activityText: input a text (String) 
	 */
	public void likeOrUnlikeActivity(String activityText){

		By ELEMENT_LIKE_ACTIVITY = By.xpath(ELEMENT_COMMENT.replace("${activityText}", activityText) + "/../div[@class='LinkShareActivityIcon']/a[2]");

		By ELEMENT_LIKE_DEFAULT_ACTIVITY = By.xpath(ELEMENT_COMMENT.replace("${activityText}", activityText) + "/../div[@class='DefaultActivityIcon']/a[2]");

		info("-- Action: Like or Unlike an activity --");

		if (waitForAndGetElement(ELEMENT_LIKE_DEFAULT_ACTIVITY, 3000, 0) != null){

			click(ELEMENT_LIKE_DEFAULT_ACTIVITY);

		}else if (waitForAndGetElement(ELEMENT_LIKE_ACTIVITY, 3000, 0) != null){

			click(ELEMENT_LIKE_ACTIVITY);
		}
	}

	/**
	 * Edit a title/description of a shared link
	 * Characters < 50 
	 * 
	 * @param inputLink: String
	 * @param editTitle: boolean
	 * @param titleOfSharedLink: String
	 * @param editDescription: boolean
	 * @param descriptionOfSharedLink: String
	 */
	public void editSharedLink(String inputText, String inputLink, boolean editTitle, String titleOfSharedLink, boolean editDescription, String descriptionOfSharedLink){
		Actions actions = new Actions(driver);

		info("-- Editing a shared link --");

		By ELEMENT_INPUT_EDIT_TITLE_LINK = By.xpath("//*[@id='UIActivityComposerContainer_LINK_ACTIVITY_']/div/div/input[@class='InputTitle']");

		By ELEMENT_INPUT_EDIT_DESCRIPTION_LINK = By.xpath("//*[@id='UIActivityComposerContainer_LINK_ACTIVITY_']/div/div/textarea[@class='InputDescription']");

		type(ELEMENT_ATIVITY_TEXTBOX, inputText, true);

		click(ELEMENT_LINK);

		type(ELEMENT_INPUT_LINK_BOX, inputLink, true);

		click(ELEMENT_ATTACH_BUTTON);

		if (editTitle){			
			click(By.id("LinkTitle"));	

			type(ELEMENT_INPUT_EDIT_TITLE_LINK, titleOfSharedLink, true);

			click(By.id("UIComposer"));

			actions.sendKeys(Keys.ENTER);

			waitForElementNotPresent(ELEMENT_INPUT_EDIT_TITLE_LINK);
		}
		if (editDescription){
			doubleClickOnElement(By.id("LinkDescription"));

			type(ELEMENT_INPUT_EDIT_DESCRIPTION_LINK, descriptionOfSharedLink, true);

			click(By.id("UIComposer"));

			actions.sendKeys(Keys.ENTER);	

			waitForElementNotPresent(ELEMENT_INPUT_EDIT_DESCRIPTION_LINK);
		}
		//Verify that content has been changed
		if (editTitle && editDescription){
			click(ELEMENT_SHARE_BUTTON);

			waitForTextPresent(titleOfSharedLink);

			waitForTextPresent(descriptionOfSharedLink);
		}else if (editTitle && !editDescription){
			click(ELEMENT_SHARE_BUTTON);

			waitForTextPresent(titleOfSharedLink);
		}else if (editDescription && !editTitle){
			click(ELEMENT_SHARE_BUTTON);

			waitForTextPresent(descriptionOfSharedLink);
		}
	}
	///////
	/**
	 * Add plural comments to an activity
	 * @param activityText
	 * @param contentOfCommentList
	 */
	public void addSomeComments(String activityText, String[] contentOfCommentList){
		for(String content : contentOfCommentList){
			addComment(activityText, content);
			click(ELEMENT_COMMENT.replace("${activityText}", activityText) + "/../div[@class='DefaultActivityIcon']/a[1]");
		}
		pause(1000);
	}

	/**
	 * Show/Hide all comments
	 * @param activityText: String
	 * @param showComment: boolean
	 * @param hideComment: boolean
	 * @param inforComment: String
	 */
	public void showHideComments(String activityText, boolean showComment, boolean hideComment, String inforComment){
		info("-- Show/Hide a comment --");

		By ELEMENT_SHOW_HIDE_ALL_COMMENTS = By.xpath(ELEMENT_COMMENT.replace("${activityText}", activityText) + ELEMENT_SHOW_HIDE_COMMENTS.replace("${inforComment}", inforComment));

		if (showComment){
			click(ELEMENT_SHOW_HIDE_ALL_COMMENTS);
			waitForElementNotPresent(ELEMENT_SHOW_HIDE_ALL_COMMENTS);
		}else if(hideComment){
			click(ELEMENT_SHOW_HIDE_ALL_COMMENTS);
			waitForElementNotPresent(ELEMENT_SHOW_HIDE_ALL_COMMENTS);
		}
		pause(1000);
	}

}
