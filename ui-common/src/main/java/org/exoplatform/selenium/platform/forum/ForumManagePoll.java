package org.exoplatform.selenium.platform.forum;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Migrate from plf3.5
 * @author lientm
 * @date 19 Aug 2013
 */
public class ForumManagePoll extends ForumBase {
	
	ForumManageTopic magTopic;
	ManageApplications app;
	UserGroupManagement userGroup;
	ForumManagePost mngPost;
	
	public ForumManagePoll(WebDriver dr){
		driver = dr;
		magTopic = new ForumManageTopic(driver);
		app = new ManageApplications(driver);
		userGroup = new UserGroupManagement(driver);
		button = new Button(driver);
		alert = new ManageAlert(driver);
		mngPost = new ForumManagePost(driver);
	}

	//Poll Manage
	public By ELEMENT_POLL = By.xpath("//div[@class='UITopicPoll']");
	public By ELEMENT_ADD_POLL = By.linkText("Add Poll"); 
	public final By ELEMENT_POLL_MORE_ACTION = By.xpath("//form[@id='UITopicPoll']//i[@class='uiIconSettings uiIconLightGray']");
	public final By ELEMENT_POLL_EDIT_LINK = By.xpath("//form[@id='UITopicPoll']//i[@class='uiIconEdit uiIconLightGray']");
	public final By ELEMENT_POLL_DELETE_LINK = By.xpath("//form[@id='UITopicPoll']//i[@class='uiIconDelete uiIconLightGray']");
	public final By ELEMENT_POLL_CATEGORY = By.xpath("//a[@title='poll']");
	public final By ELEMENT_POLL_PORTLET = By.id("poll/local._poll.PollPortlet");
	public final By ELEMENT_ADDMORE_ITEMS = By.xpath("//img[@title='Add Item']");
	public final By ELEMENT_PUBLIC_DATA = By.id("PublicData");
	public final By ELEMENT_TIMEOUT = By.id("TimeOut");
	public final By ELEMENT_CHANGEVOTE = By.id("VoteAgain");
	public final By ELEMENT_MULTICHOICE = By.id("MultiVote");
	public final By ELEMENT_EDIT_PORTLET = By.xpath("//a[@title='Edit Portlet']");
	public final By ELEMENT_ADD_POLL_BUTTON = By.linkText("Add Poll");
	public final By ELEMENT_ADD_POLL_FORM = By.xpath("//span[text()='Add New Poll']");
	public final By ELEMENT_SUBMIT_POLL_BUTTON = By.linkText("Submit Poll");
	public final By ELEMENT_SELECT_POLL = By.id("selectPoll");
	public final By ELEMENT_POLL_QUESTION = By.id("Question");
	public final By ELEMENT_SELECT_GROUP = By.xpath("//img[@title='Select Group']");
	public final String ELEMENT_GROUP_PATH = "//a[@class='NodeIcon GroupAdminIcon' and @title='${groupTitle}']";
	public final By ELEMENT_VOTE_NOW_BUTTON = By.linkText("Vote Now");
	public final By ELEMENT_VOTE_AGAIN = By.linkText("Vote Again");
	public final By ELEMENT_HOME_LINK = By.linkText("Home");
	public final By ELEMENT_SELECT_THIS_GROUP_LINK = By.linkText("Select this Group");
	public final By ELEMENT_EDIT_POLL_FORM = By.xpath("//span[text()='Edit Poll']");
	public final String ELEMENT_POLL_OPTION = "//input[@id='Option${index}']";
	public By ELEMENT_POLL_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Poll']");
	
	public By ELEMENT_POLL_OPTION1 = By.id("Option1");
	public By ELEMENT_POLL_CLOSE = By.id("TimeOut");
	public By ELEMENT_POLL_VOTE_AGAIN = By.id("VoteAgain");
	public By ELEMENT_POLL_MULTI_VOTE = By.id("MultiVote");
	public By ELEMENT_POLL_SUBMIT_BUTTON = By.xpath("//button[text()='Submit Poll']");
	public String ELEMENT_POLL_DELETE_OK = "//span[contains(text(),'Are you sure you want to delete this poll ?')]/../../..//button[text()='OK']";
	
	public final String ELEMENT_POLL_QUESTION_LINK = "//div[text()='${pollQuestion} ?']";
	public final String ELEMENT_POLL_TITLE = "//div[@class='textTitlePoll pull-left' and contains(text(),'${poll}')]";
	public final String ELEMENT_OPTION = "//tbody[@class='contentVoting']//span[contains(text(),'${option}')]";
	public final String ELEMENT_OPTION_CLOSED = "//td[text()='${option}']/../td//div[@class='progress']";
	public final String WARNING_MESSAGE_NO_PERMISSION = "You have no permission to view this poll or the poll has been deleted.";
	public final String MSG_POLL_DELETE = "Are you sure you want to delete this poll ?";

	public By ELEMENT_POLL_CLOSE_LINK = By.xpath("//a[contains(@href,'ClosedPoll')]");
	public By ELEMENT_POLL_REOPEN_LINK = By.xpath("//div[@class='UITopicPoll uiBox uiTopicPoll uiCollapExpand']//i[@class='uiIconOpen']");

	/*------------------------------------Common function------------------------------*/
	/**
	 * 
	 * @param pageName
	 */
	public void addPageWithPollPortlet (String pageName) {
		app.showImportApplication(true);
		app.importApplication();
		Utils.pause(1000);
		navTool.goToHomePage();
		pageE.goToPageEditor_EmptyLayout(pageName);
		Utils.pause(500);
		click(ELEMENT_POLL_CATEGORY);
		dragAndDropToObject(ELEMENT_POLL_PORTLET, ELEMENT_DROP_TARGET_NO_LAYOUT);
		Utils.pause(1000);
		pageE.finishEditLayout();
	}
	/**
	 * 
	 * @param pollQuestion
	 * @param pollOptions: options of poll, seperated by /
	 * @param publicData
	 * @param groups
	 * @param closeDate
	 * @param changeVote
	 * @param multipleChoice
	 */
	public void addPollAndSelectPoll (String pollQuestion, String pollOptions, boolean publicData, String groups, String closeDate, boolean changeVote, boolean multipleChoice) {
		navTool.goToEditPageEditor();
		//mouseOver(pageE.ELEMENT_APPS_REG_PORTLET, true);
		click(ELEMENT_EDIT_PORTLET);
		click(ELEMENT_ADD_POLL_BUTTON);
		waitForAndGetElement(ELEMENT_ADD_POLL_FORM);
		type(ELEMENT_POLL_QUESTION, pollQuestion, false);
		String[] pollOption = pollOptions.split("/");
		for (int i = 0; i < pollOption.length; i++){
			if (i > 1){
				click(ELEMENT_ADDMORE_ITEMS);
			}
			type(By.id("Option" + i), pollOption[i], false);
		}
		if (!publicData) {
			click(ELEMENT_PUBLIC_DATA);
			waitForAndGetElement(ELEMENT_SELECT_GROUP);
			click(ELEMENT_SELECT_GROUP);
			userGroup.selectGroup(groups);
			waitForAndGetElement(ELEMENT_SELECT_THIS_GROUP_LINK);
			click(ELEMENT_SELECT_THIS_GROUP_LINK);
		}
		type(ELEMENT_TIMEOUT, closeDate, false);
		if (changeVote) click(ELEMENT_CHANGEVOTE);
		if (multipleChoice) click(ELEMENT_MULTICHOICE);
		click(ELEMENT_SUBMIT_POLL_BUTTON);
		waitForElementNotPresent(ELEMENT_ADD_POLL_FORM);
		waitForAndGetElement(By.xpath(ELEMENT_POLL_TITLE.replace("${pollQuestion}", pollQuestion)));
		select(ELEMENT_SELECT_POLL,pollQuestion);
		button.save();
		button.close();
		pageE.finishEditLayout();
		waitForAndGetElement(By.xpath(ELEMENT_POLL_QUESTION_LINK.replace("${pollQuestion}", pollQuestion)));

	}

	/**
	 * 
	 * @param changeVote
	 * @param mutipleChoice
	 * @param pollOptions
	 */
	public void votePollSingleChoice (boolean changeVote, String pollOption) {
		By ELEMENT_POLL_OPTION_PATH = By.id("option_"+pollOption+"");
		WebElement e = waitForAndGetElement(ELEMENT_POLL_OPTION_PATH);
		if (!e.isSelected())
			click(ELEMENT_POLL_OPTION_PATH);

		click(ELEMENT_VOTE_NOW_BUTTON);
		if (changeVote) {
			waitForAndGetElement(ELEMENT_VOTE_AGAIN);

		}
	}

	/**
	 * 
	 * @param changeVote
	 * @param index: the option you want to choose
	 */
	public void votePollMultiChoice (boolean changeVote, int[] index) {
		for (int i = 0; i < index.length; i++) {
			click(By.id("option" + (index[i] - 1)));
		}
		click(ELEMENT_VOTE_NOW_BUTTON);
		if (changeVote) {
			waitForAndGetElement(ELEMENT_VOTE_AGAIN);
		}
		else waitForElementNotPresent(ELEMENT_VOTE_AGAIN); 

	}
	
	/**
	 * 
	 * @param pollQuestion
	 */
	public void deletePoll (String pollQuestion) {
		By ELEMENT_DELETE_POLL_BUTTON = By.xpath("//div[@title='"+pollQuestion+"']/following::div/img[@title='Delete Poll']");
		navTool.goToEditPageEditor();
		//mouseOver(ELEMENT_APPS_REG_PORTLET, true);
		click(ELEMENT_EDIT_PORTLET);
		click(ELEMENT_DELETE_POLL_BUTTON);
		waitForElementNotPresent(By.xpath(ELEMENT_POLL_TITLE.replace("${pollQuestion}", pollQuestion)));
		button.save();
		button.close();
		pageE.finishEditLayout();

	}

	public void goToPollPortlet (String pageName) {
		mouseOver(ELEMENT_HOME_LINK,true);
		click(By.linkText(pageName));
	}
	
	/**
	 * 
	 * @param pollQuestion
	 * @param newPollQuestion
	 */
	public void editPollQuestion (String pollQuestion, String newPollQuestion) {
		By ELEMENT_EDIT_POLL_BUTTON = By.xpath("//div[@title='"+pollQuestion+"']/following::div/img[@title='Edit Poll']");		

		navTool.goToEditPageEditor();
		//mouseOver(ELEMENT_APPS_REG_PORTLET, true);
		click(ELEMENT_EDIT_PORTLET);
		waitForAndGetElement(ELEMENT_EDIT_POLL_BUTTON);
		click(ELEMENT_EDIT_POLL_BUTTON);
		waitForAndGetElement(ELEMENT_EDIT_POLL_FORM);
		type(ELEMENT_POLL_QUESTION, newPollQuestion, true);
		click(ELEMENT_SUBMIT_POLL_BUTTON);
		waitForElementNotPresent(ELEMENT_EDIT_POLL_FORM);
		select(ELEMENT_SELECT_POLL, newPollQuestion);		
		button.save();		
		button.close();		
		pageE.finishEditLayout();		
		waitForAndGetElement(By.xpath(ELEMENT_POLL_QUESTION_LINK.replace("${pollQuestion}", newPollQuestion)));
	}

	/**Function add a poll for topic
	 * @author thuntn
	 * @param pollQuestion: question of poll
	 * @param options: array of options of poll
	 * @param timeout
	 * @param changeVote
	 * @param verify
	 */
	public void inputFormPoll(String pollQuestion, String[] options, String timeout, boolean changeVote, boolean multiChoice){
		int numberOfOption = options.length;
		type(ELEMENT_POLL_QUESTION, pollQuestion, true);
		for(int i = 0; i < numberOfOption; i ++){
		type(ELEMENT_POLL_OPTION.replace("${index}", Integer.toString(i)), options[i], true);
		}
		if (timeout != null){
			type(ELEMENT_POLL_CLOSE, timeout, true);
		}
		if (changeVote ){
			check(ELEMENT_POLL_VOTE_AGAIN,2);
		}
		else 
			uncheck(ELEMENT_POLL_VOTE_AGAIN,2);
		if (multiChoice)
			check(ELEMENT_POLL_MULTI_VOTE,2);	
	}
	
	public void addPoll(String pollQuestion, String[] options, String timeout, boolean changeVote, boolean multi, boolean... verify){
		boolean check = verify.length > 0 ? verify[0]: true;

		info("Add a poll in a topic");
		goToAddPoll();
		inputFormPoll(pollQuestion, options, timeout, changeVote, multi);
		click(ELEMENT_POLL_SUBMIT_BUTTON);
		if (check){
			waitForElementNotPresent(ELEMENT_POLL_POPUP);
			info("Add poll successfully");
		}

		//Check after add poll
		//waitForTextPresent(pollQuestion);
		Utils.pause(500);
		for(int i = 0; i < options.length; i ++)
			waitForAndGetElement(ELEMENT_OPTION.replace("${option}", options[i]));
	} 
	/** function: go to add poll for topic from More action/Add poll
	 * @author lientm
	 */
	public void goToAddPoll(){
		info("Go to add poll for topic");
		Utils.pause(2000);
		waitForAndGetElement(mngPost.ELEMENT_POST_REPLY_BUTTON);
		click(ELEMENT_MORE_ACTION);
		//waitForAndGetElement(ELEMENT_ADD_POLL);
		click(ELEMENT_ADD_POLL);
		waitForAndGetElement(ELEMENT_POLL_POPUP);
	}

	/** Edit a poll in topic
	 * @author thuntn  
	 * @param pollQuestion
	 * @param options
	 * @param timeout
	 * @param changeVote
	 * @param verify
	 */
	public void editPoll(String pollQuestion, String[] options, String timeout, boolean changeVote, boolean... verify){
		boolean check = verify.length > 0 ? verify[0]: true;

		info("Edit a poll in a topic");
		click(ELEMENT_POLL_MORE_ACTION);
		click(ELEMENT_POLL_EDIT_LINK);
		inputFormPoll(pollQuestion, options, timeout, changeVote, false);
		click(ELEMENT_POLL_SUBMIT_BUTTON);
		if (check){
			waitForElementNotPresent(ELEMENT_POLL_POPUP);
			info("Edit poll successfully");
		}

		//Check after edit
		//waitForTextPresent(pollQuestion);
		for(int i = 0; i < options.length; i ++)
			waitForAndGetElement(ELEMENT_OPTION.replace("${option}", options[i]));
	} 

	/**Delete a poll in a topic
	 * @author thuntn
	 * @param poll
	 */
	public void deletePollInTopic(String poll){
		info("Delete a poll in a topic");

		click(ELEMENT_POLL_MORE_ACTION);
		click(ELEMENT_POLL_DELETE_LINK);
		waitForMessage(MSG_POLL_DELETE);
		click(ELEMENT_POLL_DELETE_OK);

		//waitForTextNotPresent(poll);
		waitForElementNotPresent(By.linkText(poll));
	} 

	/**Close or reopen a poll
	 * @author thuntn
	 * @param options: array of options of poll
	 * @param close: =true: close poll
	 * 				 = false: reopen poll
	 */
	public void closeReopenPoll(String[] options, boolean close){
		click(ELEMENT_MORE_ACTION);
		
		if (close){
			info("Close poll");
			
			click(ELEMENT_POLL_CLOSE_LINK);

			waitForElementNotPresent(ELEMENT_VOTE_NOW_BUTTON);
			for(int i = 0; i < options.length; i ++)
				waitForAndGetElement(ELEMENT_OPTION_CLOSED.replace("${option}", options[i]));
		}else{
			info("Reopen poll");

			click(ELEMENT_POLL_REOPEN_LINK);

			waitForAndGetElement(ELEMENT_VOTE_NOW_BUTTON);
			for(int i = 0; i < options.length; i ++)
				waitForAndGetElement(ELEMENT_OPTION.replace("${option}", options[i]));
		}
	}

}
