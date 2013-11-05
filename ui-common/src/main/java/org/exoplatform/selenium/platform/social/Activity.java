package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;

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

    
	Dialog dialog = new Dialog(driver);
	ManageAlert magAlert;
	Button button = new Button(driver);
	HomePageActivity hpActivity = new HomePageActivity(driver);

	//=====Element on space home page=======stash@{1}
	// Go to My Spaces > Select a space
	//Or Go to My Activity Stream
	public final String ELEMENT_COMMENT_BOX_SPACE_ACTIVITY = "//div[@class='author']/a[contains(text(),'${activityText}')]//ancestor::div[contains(@id,'ContextBox')]/div[contains(@id,'CommentBlockBound')]//p[@class='contentComment']";
	public final String ELEMENT_AVATAR_SPACE_ACTIVITY = "//div[@class='author']/a[contains(text(),'${activityText}')]//ancestor::div[contains(@id,'ContextBox')]//*[@class='avatarXMedium']";
	public final String ELEMENT_DESCRIPTION_SPACE_ACTIVITY = "//div[@class='author']/a[contains(text(),'${activityText}')]//ancestor::div[contains(@id,'ContextBox')]//p[@class='spaceDescription' and contains(text(),'${description}')]"; 
	public final String ELEMENT_NUMBER_MEMBER_SPACE_ACTIVITY = "//div[@class='author']/a[contains(text(),'${activityText}')]//ancestor::div[contains(@id,'ContextBox')]//div[@class='spaceMembers' and contains(text(),'${numbermember}')]";
	public final String ELEMENT_ACTIVITY_AUTHOR_SPACENAME = "//div[@class='author']/a[contains(text(),'${activityText}')]";
	public final String ELEMENT_ACTIVITY_AUTHOR_ACTIVITY = "//*[contains(text(), '${activityText}')]/../../../../..//*[@class='author']";
	public final By ELEMENT_ACTIVITY_DROPDOWN = By.xpath("//div[@class='btn dropdown-toggle']");
	public final String ELEMENT_ACTIVITY_FILTER_OPTION = "//a[@class='OptionItem' and contains(text(),'${filterOption}')]";
	public final String ELEMENT_ACTIVITY_FILTER_CURRENT = "//div[@class='btn dropdown-toggle']/span[contains(text(),'${filterOption}')]";
	public final By ELEMENT_ACTIVITY_TEXTAREA = By.id("composerInput");
	public final By ELEMENT_ACTIVITY_MENTION_USER_MENU = By.xpath("//div[@id='DisplaycomposerInput']/../div[@class='autocomplete-menu']");
	public final String ELEMENT_ACTIVITY_MENTION_USER = ELEMENT_ACTIVITY_MENTION_USER_MENU+"//*[contains(text(),'${name}')]/../div[@class='avatarSmall']";
	public final By ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL = By.xpath("//div[@id='DisplaycomposerInput']/../div[@class='placeholder']");
	public final By ELEMENT_LINK = By.xpath("//i[@class='uiIconSocUILinkActivityComposer uiIconSocLightGray']");
	public final By ELEMENT_INPUT_LINK_BOX = By.id("InputLink");
	public final By ELEMENT_SELECT_FILE_BUTTON = By.linkText("Select File"); 
	public final By ELEMENT_SELECT_BUTTON = By.xpath("//button[text()='Select']");
	public final By ELEMENT_UPLOAD_BUTTON = By.xpath("//*[@data-original-title='Upload file.']");
	public final By ELEMENT_ATTACH_BUTTON = By.id("AttachButton");
	public final By ELEMENT_SHARE_BUTTON = By.id("ShareButton");

//	public final String ELEMENT_COMMENT_LINK = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//*[starts-with(@id, 'CommentLink')]";
	public final String ELEMENT_COMMENT = "//div[@class='ContentBox']//*[contains(text(), '${activityText}')]";
	public final String ELEMENT_SHOW_HIDE_COMMENTS = "/following::*[@class='CommentListInfo']/a[contains(text(), '${inforComment}')]";
	public final String ELEMENT_COMMENT_ICON = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//*[@class= 'uiIconComment uiIconLightGray']";
	public final String ELEMENT_COMMENT_BUTTON = "//*[contains(text(), '${activityText}')]/..//button[contains(@id,'CommentButton')]";
	public final String ELEMENT_INPUT_COMMENT_TEXT_AREA = "//*[contains(text(), '${activityText}')]/..//*[contains(@id,'DisplayCommentTextarea')]";
	public final String ELEMENT_ACTIVITY_ADD_YOUR_COMMENTLABEL = "//*[contains(text(), '${activityText}')]/..//*[contains(@id,'DisplayCommentTextarea')]/../div[@class='placeholder']";
	public final String ELEMENT_MENTION_USER_AVATAR = "//*[@data-display='${userName}']/*[@class='avatarSmall']";

//	public final String ELEMENT_LIKE_ICON = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//*[starts-with(@id, 'LikeLink')]";
//	public final String ELEMENT_UNLIKE_ICON = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//*[starts-with(@id, 'UnLikeLink')]";
	public final String ELEMENT_ACTIVITY_NAME_CONSECUTIVE = "//*[contains(@id,'UIActivitiesContainer')]/div[1]//*[@class='description' and text()='${activityText1}']/../../../../../../div[2]//*[@class='description' and text()='${activityText2}']";
	public final String ELEMENT_COMMENT_LINK_MENTION = "//*[@class='contentComment']/a[contains(text(),'${userName}')]";
	public final String ELEMENT_USER_NAME_LINK_ACTIVITY = "//div[@class='description']/a[contains(text(),'${userName}')]";
	public final String ELEMENT_USER_NAME_LINK_COMMENT = "//div[@class='description']/../*[contains(text(), '${activityText}')]/../../..//p[@class='contentComment']/a[contains(text(),'${userName}')]";
	public final By ELEMENT_MENTION_USER_BUTTON = By.id("mentionButton");
	public final String ELEMENT_DELETE_COMMENT_BUTTON = "//*[contains(text(), '${activityText}')]/..//div[@class='commentList']/div[contains(@id,'commentContainer')]//p[@class='contentComment'  and contains(text(),'${commentText}')]/../../a[contains(@id,'DeleteCommentButton')]";
	public final String ELEMENT_DELETE_COMMENT_BUTTON_INDEX = "//*[contains(text(), '${activityText}')]/..//div[@class='commentList']/div[contains(@id,'commentContainer')][${index}]//p[@class='contentComment'  and contains(text(),'${commentText}')]/../../a[contains(@id,'DeleteCommentButton')]";
	public final By ELEMENT_MESSAGE_CONFIRM_DELETE_COMMENT = By.xpath("//*[text()='Are you sure you want to delete this comment?']");
	public final String ELEMENT_SHOW_ALL_COMMENTS = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/../*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]/div[contains(@id,'CommentBlockBound')]//a[contains(text(),'View all "+"${inforComment}"+"')]";
	public final String ELEMENT_HIDE_ALL_COMMENTS = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/../*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]/div[contains(@id,'CommentBlockBound')]//a[contains(text(),'Hide all "+"${inforComment}"+"')]";
	public final String ELEMENT_AVATAR_LIST_LIKER_INDEX = "//div[@class='text' or @class = 'description'or @class='linkSource' or contains(@id, 'ContextBox')]/../*[contains(text(), '${activityText}')]//ancestor::div[contains(@id,'ActivityContextBox')]//div[@class='listPeopleContent']/div[@class='listLiked']/a[@class='avatarXSmall'][${index}]/img";
	public final String ELEMENT_USER_PROFILE_POPUP = "//table[@id='tipName']//a[contains(text(),'${userName}')]";

	/**
	 * Select filter activity
	 * @author phuongdt
	 * @date 03/10/2013
	 * @param filterOption: All Activities, My Activities, My Spaces, Connections
	 */
	public void selectFileter(String filterOption){
		info("-- Select filter activity --");
		if(waitForAndGetElement(ELEMENT_ACTIVITY_FILTER_CURRENT.replace("${filterOption}", filterOption), DEFAULT_TIMEOUT, 0)==null){
			click(ELEMENT_ACTIVITY_DROPDOWN,2);
			click(ELEMENT_ACTIVITY_FILTER_OPTION.replace("${filterOption}", filterOption));
			waitForAndGetElement(ELEMENT_ACTIVITY_FILTER_CURRENT.replace("${filterOption}", filterOption));

		}
	}

	/**
	 * Select a file to post on activity
	 * @param driveName
	 * @param upload
	 * @param folderPath
	 * @param selectFileName
	 * @param uploadFileLink
	 * @param uploadFileName
	 * @param option: newFolder
	 */
	public void selectFile(String driveName, boolean upload, String folderPath, String selectFileName, String uploadFileName, Object...params) {
		String newFolder = (String) (params.length > 0 ? params[0] : "");
		magAlert = new ManageAlert(driver);
		info("-- Selecting a file to post on activity --");
		waitForAndGetElement(ELEMENT_FILE_LINK);
		click(ELEMENT_FILE_LINK);
		waitForAndGetElement(ELEMENT_SELECT_FILE_POPUP);	
		info("----Select drive----");
		if(waitForAndGetElement(ELEMENT_DRIVER_CURRENT.replace("${driveName}", driveName), DEFAULT_TIMEOUT, 0)==null){
			click(ELEMENT_DRIVER_BOX,2);
			click(ELEMENT_DRIVER_OPTION.replace("${driveName}", driveName));
		}
		info("---Select folder path----");
		String [] paths = folderPath.split("/");
		for (String path : paths)
			click(By.linkText(path));
		if(newFolder!=""){
			click(ELEMENT_CREATE_FOLDER_BUTTON);
			magAlert.inputAlertText(newFolder);
			click(By.linkText(newFolder));
		}
		if (upload)
		{
			info("-- Upload file --");
			WebElement frame = waitForAndGetElement(ELEMENT_UPLOAD_FILE_FRAME_XPATH);
			driver.switchTo().frame(frame);
			WebElement upload2 = waitForAndGetElement(ELEMENT_UPLOAD_IMG_ID, DEFAULT_TIMEOUT,1,2);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload2);
			upload2.sendKeys(Utils.getAbsoluteFilePath("TestData/" +uploadFileName));	
			info("Upload file " + Utils.getAbsoluteFilePath("TestData/" +uploadFileName));
			switchToParentWindow();
			waitForAndGetElement(By.linkText(uploadFileName));
			click(By.linkText(uploadFileName));
		}
		else 
		{
			click(By.linkText(selectFileName));
			Utils.pause(500);
		}
		click(ELEMENT_SELECT_BUTTON);
		click(ELEMENT_SHARE_BUTTON);
		waitForAndGetElement(By.linkText(uploadFileName));
	}

	/**
	 * Add new activity for space 
	 * @param addText: boolean
	 * @param text: input a text (String)
	 * @param addLink: boolean
	 * @param link: input a link (String)
	 */
	public void addActivity (boolean addText, String text, boolean addLink, String link) {
		hpActivity = new HomePageActivity(driver);
		info("-- Adding an activity to space --");
		//waitForAndGetElement(ELEMENT_ACTIVITY_TEXTBOX, DEFAULT_TIMEOUT,1, 2);
		Utils.pause(3000);
		if (addText) 
		{
			info("----Add text into activity text box-----");
			WebElement inputText = waitForAndGetElement(hpActivity.ELEMENT_ACTIVITY_TEXTBOX);
			WebElement shareButton = waitForAndGetElement(ELEMENT_SHARE_BUTTON);
			WebElement workingLabel = waitForAndGetElement(ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL);
			((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '';", workingLabel);
			((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '"+text+"';", inputText);
			((JavascriptExecutor)driver).executeScript("arguments[0].disabled = false;", shareButton);
			((JavascriptExecutor)driver).executeScript("arguments[0].className = 'pull-right btn btn-primary';", shareButton);
		}
		if (addLink)
		{
			info("----Click on Link----");
			waitForAndGetElement(ELEMENT_LINK).click();
			info("----Input link into link box-----");
			waitForAndGetElement(ELEMENT_INPUT_LINK_BOX);
			type(ELEMENT_INPUT_LINK_BOX, link, true);
			waitForAndGetElement(ELEMENT_ATTACH_BUTTON);
			info("----Click attach button-----");
			click(ELEMENT_ATTACH_BUTTON);
			waitForAndGetElement(By.id("LinkTitle"));
		}
		waitForAndGetElement(ELEMENT_SHARE_BUTTON);
		info("----Click share button----");
		click(ELEMENT_SHARE_BUTTON);
		Utils.pause(1000);
		info("-- Verify that an activity has been added --");
		if (addText) {
			waitForAndGetElement(By.xpath(ELEMENT_ACTIVITY_AUTHOR_ACTIVITY.replace("${activityText}", text)));
		}
		if (addLink){
			waitForAndGetElement(By.linkText(link));
		}
	}

	//	/**
	//	 * Delete activity 
	//	 * @param activityText: input a String 
	//	 */
	//	public void deleteActivity (String activityText) {
	//		info("-- Deleting an activity --");
	//
	//		By ELEMENT_DELETE_ACTIVITY = By.xpath("//div[@class='ContentBox']/div/h5/span/../../div[contains(text(), '"+activityText+"')]/../h5/span[contains(@id, 'DeleteActivityButton')]");
	//
	//		WebElement elementDeleteActivityID = waitForAndGetElement(ELEMENT_DELETE_ACTIVITY);
	//
	//		String deleteActivityIconID = elementDeleteActivityID.getAttribute("id");		
	//
	//		info("---Click delete activity icon---");
	//
	//		click(By.id(deleteActivityIconID));
	//
	//		magAlert.waitForConfirmation("Are you sure to delete this activity?");
	//
	//		waitForElementNotPresent(ELEMENT_DELETE_ACTIVITY);
	//
	//		Utils.pause(1000);
	//	}


	/**
	 * Add a new comment on activity stream
	 * @param activityText: input a text (String) 
	 * @param contentOfComment: input a comment (String)
	 */
	public void addComment(String activityText, String contentOfComment){
		//Add a new comment following an activity 
		click(ELEMENT_COMMENT_ICON.replace("${activityText}", activityText));
		WebElement commentText = waitForAndGetElement(ELEMENT_INPUT_COMMENT_TEXT_AREA.replace("${activityText}", activityText));
		WebElement commentButton = waitForAndGetElement(ELEMENT_COMMENT_BUTTON.replace("${activityText}", activityText));
		WebElement workingLabel = waitForAndGetElement(ELEMENT_ACTIVITY_ADD_YOUR_COMMENTLABEL.replace("${activityText}", activityText));
		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '';", workingLabel);
		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '"+contentOfComment+"';", commentText);
		((JavascriptExecutor)driver).executeScript("arguments[0].disabled = false;", commentButton);
		((JavascriptExecutor)driver).executeScript("arguments[0].className = 'btn pull-right btn-primary';", commentButton);
		click(ELEMENT_COMMENT_BUTTON.replace("${activityText}", activityText));
		waitForAndGetElement(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activityText).replace("${commentText}", contentOfComment), DEFAULT_TIMEOUT,1,2);
	}

	/**
	 * Delete a comment
	 * @param contentOfComment: input a comment (String)
	 */
	public void deleteComment(String activityText, String commentText){
		info("-- Deleting a comment --");
		button = new Button(driver);
		WebElement deleteButton = waitForAndGetElement(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activityText).replace("${commentText}", commentText), DEFAULT_TIMEOUT,1,2);
		String deleteCommentIconID = deleteButton.getAttribute("id");
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("document.getElementById('"+deleteCommentIconID+"').click();");
		waitForAndGetElement(ELEMENT_MESSAGE_CONFIRM_DELETE_COMMENT);
		button.ok();
		waitForElementNotPresent(ELEMENT_DELETE_COMMENT_BUTTON.replace("${activityText}", activityText).replace("${commentText}", commentText));
		Utils.pause(1000);
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
		hpActivity = new HomePageActivity(driver);
		info("-- Editing a shared link --");

		By ELEMENT_INPUT_EDIT_TITLE_LINK = By.xpath("//*[@id='UIActivityComposerContainer_LINK_ACTIVITY_']/div/div/input[@class='InputTitle']");

		By ELEMENT_INPUT_EDIT_DESCRIPTION_LINK = By.xpath("//*[@id='UIActivityComposerContainer_LINK_ACTIVITY_']/div/div/textarea[@class='InputDescription']");

		type(hpActivity.ELEMENT_ACTIVITY_TEXTBOX, inputText, true);

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
		Utils.pause(1000);
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
		if (showComment){
			WebElement elem = waitForAndGetElement(ELEMENT_SHOW_ALL_COMMENTS.replace("${activityText}", activityText).replace("${inforComment}", inforComment));
			elem.click();
			waitForAndGetElement(ELEMENT_HIDE_ALL_COMMENTS.replace("${activityText}", activityText).replace("${inforComment}", inforComment));
		}else if(hideComment){
			WebElement elem = waitForAndGetElement(ELEMENT_HIDE_ALL_COMMENTS.replace("${activityText}", activityText).replace("${inforComment}", inforComment));
			elem.click();
			waitForAndGetElement(ELEMENT_SHOW_ALL_COMMENTS.replace("${activityText}", activityText).replace("${inforComment}", inforComment));
		}
		Utils.pause(1000);
	}

	/**
	 * @author phuongdt
	 * check visibility of activity on page using scroll bar
	 */
	public void waitForActivityPresent(String activityText, boolean isCurrentPosition){
		info("-- Verify Activity "+activityText+"--");
		if(!isCurrentPosition){
			Locatable hoverItem = (Locatable) driver.findElement(By.xpath("//div[@class='TRContainer ClearFix']"));
			int y = hoverItem.getCoordinates().onPage().getY();
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+y+");");
		}
		for (int second = 0;; second++) {
			if(second >8){
				waitForAndGetElement(By.xpath("//*[contains(text(),'"+activityText+"')]"));
				break;
			}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("window.scrollBy(0,1000)", "");
			if(waitForAndGetElement(By.xpath("//*[contains(text(),'"+activityText+"')]"), DEFAULT_TIMEOUT,0)!=null)
				break;
		}
	}

	/**
	 * @author phuongdt
	 * check invisibility of activity on page using scroll bar
	 */
	public void waitForActivityNotPresent(String activityText, boolean isCurrentPosition){
		info("-- Verify Activity "+activityText+"--");
		if(!isCurrentPosition){
			Locatable hoverItem = (Locatable) driver.findElement(By.xpath("//div[@class='TRContainer ClearFix']"));
			int y = hoverItem.getCoordinates().onPage().getY();
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+y+");");
		}
		for (int second = 0;; second++) {
			if(second >=8){
				break;
			}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("window.scrollBy(0,1000)", "");
			waitForElementNotPresent(By.xpath("//*[contains(text(),'"+activityText+"')]"));
		}
	}

	/**
	 * @author phuongdt
	 * mention a user in activity or comment of a activity
	 */
	public void mentionActivity(boolean isActivity, String activityText, String userName){
		hpActivity = new HomePageActivity(driver);
		if(isActivity){
			info ("-- Adding a mention activity --");			
			WebElement inputText = waitForAndGetElement(hpActivity.ELEMENT_ACTIVITY_TEXTBOX, DEFAULT_TIMEOUT, 1, 2);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible'", inputText);
			click(ELEMENT_MENTION_USER_BUTTON);
			inputText.sendKeys(userName);
			Utils.pause(1000);
			click(hpActivity.ELEMENT_ACTIVITY_TEXTBOX);
			Utils.pause(1000);
			click(ELEMENT_MENTION_USER_AVATAR.replace("${userName}", userName));
			Utils.pause(1000);
			click(ELEMENT_SHARE_BUTTON);
			waitForAndGetElement(By.xpath(ELEMENT_USER_NAME_LINK_ACTIVITY.replace("${userName}", userName)));
		}
		else{
			info("-- Adding a mention comment --");
			//Add a new comment following an activity 
			click(ELEMENT_COMMENT_ICON.replace("${activityText}", activityText));
			type(ELEMENT_INPUT_COMMENT_TEXT_AREA.replace("${activityText}", activityText), "@"+userName, false);
			Utils.pause(1000);
			click(ELEMENT_INPUT_COMMENT_TEXT_AREA.replace("${activityText}", activityText));
			Utils.pause(1000);
			//click("//*[@class='avatarSmall' and text()='"+userName+"']");
			click(ELEMENT_MENTION_USER_AVATAR.replace("${userName}", userName));
			Utils.pause(1000);
			//Click on Comment button
			WebElement commentButton = waitForAndGetElement(ELEMENT_COMMENT_BUTTON.replace("${activityText}", activityText));
			((JavascriptExecutor)driver).executeScript("arguments[0].disabled = false;", commentButton);
			((JavascriptExecutor)driver).executeScript("arguments[0].className = 'pull-right btn btn-primary';", commentButton);
			commentButton.click();
			waitForAndGetElement(ELEMENT_USER_NAME_LINK_COMMENT.replace("${activityText}", activityText).replace("${userName}", userName));
		}
	}
}
