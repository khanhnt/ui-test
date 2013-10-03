package org.exoplatform.selenium.platform.forum;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;

/**
 * @author lientm
 * @date 27 Aug 2013
 */
public class AnswerManageComment extends AnswerBase {

	public AnswerManageComment(WebDriver dr){
		driver = dr;
		but = new Button(driver);
		alert = new ManageAlert(driver);
	}
	
	AnswerManageQuestion magQuest = new AnswerManageQuestion(driver);
	AnswerManageAnwser magAns = new AnswerManageAnwser(driver);
	
	//Add comment
	public final By ELEMENT_COMMENT_LINK_IN_QUESTION = By.xpath("//*[@class='questionAction']//*[contains(text(),'Comment')]");
	public final By ELEMENT_COMMENT_CONTENT_FRAME_1 = By.id("CommentContent___Frame");
	public final By ELEMENT_COMMENT_CONTENT_FRAME_2 = By.xpath("//*[@id ='xEditingArea']/iframe");
	public final String ELEMENT_COMMENT_IN_QUESTION = "//*[contains(@id, 'Comment')]//*[text()='${comment}']";
	
	//More action
	public final String ELEMENT_COMMENT_MORE_ACTION_LINK = "//*[text()='${comment}']/../../../../..//*[contains(text(), 'More Actions')]";
	
	//message
	public final String MSG_DELETE_COMMENT = "Are you sure you want to delete this comment ?";
	
	/*----------------------------common functions---------------------------------*/
	
	/**
	 * function add comment for a question
	 * @param question
	 * @param comment
	 */
	public void addComment4Question(String question, String comment){
		if (waitForAndGetElement(ELEMENT_COMMENT_LINK_IN_QUESTION, 5000, 0) != null){
			click(ELEMENT_COMMENT_LINK_IN_QUESTION);
		}else {
			rightClickOnElement(By.linkText(question));
			click(magQuest.ELEMENT_COMMENT_LINK_IN_CONTEXT_MENU);
		}
		inputDataToFrameInFrame(ELEMENT_COMMENT_CONTENT_FRAME_1, ELEMENT_COMMENT_CONTENT_FRAME_2, comment, true);
		switchToParentWindow();
		but.save();
		waitForAndGetElement(ELEMENT_COMMENT_IN_QUESTION.replace("${comment}", comment));
	}
	
	/**
	 * function edit comment in question
	 * @param comment
	 * @param newComment
	 */
	public void editComment4Question(String comment, String newComment){
		click(ELEMENT_COMMENT_MORE_ACTION_LINK.replace("${comment}", comment));
		Utils.pause(2000);
		getElementFromTextByJquery("Edit Comment").click();
		inputDataToFrameInFrame(ELEMENT_COMMENT_CONTENT_FRAME_1, ELEMENT_COMMENT_CONTENT_FRAME_2, newComment, true);
		switchToParentWindow();
		but.save();
		waitForAndGetElement(ELEMENT_COMMENT_IN_QUESTION.replace("${comment}", newComment));
	}
	
	/**
	 * function delete comment in question
	 * @param comment
	 */
	public void deleteCommentInQuestion(String comment){
		click(ELEMENT_COMMENT_MORE_ACTION_LINK.replace("${comment}", comment));
		Utils.pause(2000);
		getElementFromTextByJquery("Delete Comment").click();
		waitForMessage(MSG_DELETE_COMMENT);
		click(ELEMENT_CONFIMATION_OK_POPUP);
		waitForElementNotPresent(ELEMENT_COMMENT_MORE_ACTION_LINK.replace("${comment}", comment));
	}
	
	/**
	 * function promote a comment as answer in question
	 * @param comment
	 */
	public void promoteAsAnswer(String comment){
		click(ELEMENT_COMMENT_MORE_ACTION_LINK.replace("${comment}", comment));
		Utils.pause(2000);
		((JavascriptExecutor) driver).executeScript("$(\"a:contains('Promote as Answer')\").get(0).click();");
		waitForElementNotPresent(ELEMENT_COMMENT_IN_QUESTION.replace("${comment}", comment));
		waitForAndGetElement(magAns.ELEMENT_ANSWER_IN_QUESTION.replace("${answer}", comment));
	}
}
