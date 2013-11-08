package org.exoplatform.selenium.platform.forum;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static org.exoplatform.selenium.TestLogger.info;

/**
 * Migrate from plf3.5
 * @author lientm
 * @date 19 Aug 2013
 */
public class AnswerBase extends ForumBase {

	PageManagement page;
	UserGroupManagement userGroup;
	ManageAccount account;

	public final By ELEMENT_ANSWER_LINK = By.linkText("Answer");
	public final String ELEMENT_ANSWER_BREADCUMB = "//*[@id='UIBreadcumbs']//*[text()='${category}']";
	public final By ELEMENT_ANSWER_HOME_LINK = By.xpath("//*[@id='UIBreadcumbs']//*[text()='Home']");
	public final By ELEMENT_CONFIMATION_OK_POPUP = By.xpath("//*[@id='UIForumPopupConfirmation']//*[text()='OK']");
	public final By ELEMENT_UP_LEVEL = By.xpath("//i[@class='uiIconUpLevel uiIconLightGray']");
	public final By ELEMENT_PRINT_ICON = By.xpath("//i[@class='uiIconPrint uiIconLightGray']");
	public final By ELEMENT_HOME_ICON = By.xpath("//*[@class='uiIconHome uiIconLightGray']");

	//Add answer page
	public final String DATA_ANSWER_PAGE_NAME = "Answer";
	public final String CATEGORY_TITLE = "Collaboration";
	public final String MSG_SAVE_ANSWER_PORTLET_SETTING = "The settings have been saved.";

	//Search in answer
	public final By ELEMENT_SIMPLESEARCH_TEXTBOX_IN_ANSWER = By.id("inputValue");
	public final By ELEMENT_SEARCH_RESULT_POPUP = By.xpath("//*[@class='PopupTitle popupTitle' and text()='Search Results']");
	public final By ELEMENT_CLOSE_QUICK_SEARCH = By.xpath("//*[@id='UIResultQuickSearchs']//button[text()='Close']");
	public final By ELEMENT_ADVANCED_SEARCH_BUTTON = By.xpath("//button[text()='Advanced Search']");
	public final By ELEMENT_ADVANCED_SEARCH_TEXTBOX = By.id("Text");
	public final By ELEMENT_SEARCH_IN_ALL = By.xpath("//input[@value='categoryAndQuestion']");
	public final By ELEMENT_SEARCH_IN_CATEGORY = By.xpath("//input[@value='faqCategory']");
	public final By ELEMENT_SEARCH_IN_ENTRIES = By.xpath("//input[@value='faqQuestion']");
	public final By ELEMENT_SEARCH_FROM_DATE = By.name("FromDate");
	public final By ELEMENT_SEARCH_TO_DATE = By.name("ToDate");
	public final By ELEMENT_SEARCH_BUTTON = By.xpath("//*[@id='UIAdvancedSearchForm']//a[text()='Search']");
	public final By ELEMENT_CLOSE_ADVANCE_SEARCH = By.xpath("//*[@id='UIAdvancedSearchForm']//a[text()='Close']");
	public final By ELEMENT_SEARCH_CATEGORY_NAME = By.id("CategoryName");
	public final By ELEMENT_IS_MODERATE_TRUE = By.xpath("//input[@value='true']");
	public final By ELEMENT_IS_MODERATE_FALSE = By.xpath("//input[@value='false']");
	public final By ELEMENT_SEARCH_MODERATOR_TEXTBOX = By.id("CategoryModerator");
	public final By ELEMENT_SEARCH_AUTHOR_TEXTBOX = By.id("Author");
	public final By ELEMENT_SEARCH_AUTHOR_EMAIL_TEXTBOX = By.id("EmailAddress");
	public final By ELEMENT_SEARCH_SELECT_LANGUAGE = By.name("Language");
	public final By ELEMENT_SEARCH_QUESTION_TEXTBOX = By.id("Question");
	public final By ELEMENT_SEARCH_ANSWER_TEXTBOX = By.id("Response");
	public final By ELEMENT_SEARCH_COMMENT_TEXTBOX = By.id("Comment");

	//Setting answer portlet
	public final By ELEMENT_CATEGORY_SCOPING_TAB = By.xpath("//button[text()='Category Scoping']");
	public final String ELEMENT_CATEGORY_IN_SCOPE_TAB = "//*[contains(text(),'${catName}')]/..//input[@type='checkbox']";

	public final By ELEMENT_DISPLAY_MODE_TAB = By.xpath("//button[text()='Display Mode']");
	public final By ELEMENT_SELECT_DISPLAY_MODE = By.name("display-mode");
	public final By ELEMENT_SELECT_ORDER_BY_MODE = By.name("order-by");
	public final By ELEMENT_SELECT_DIRECTION_MODE = By.name("order-type");
	public final By ELEMENT_ENABLE_VOTE_COMMENT = By.id("enableVotComment");
	public final By ELEMENT_ENABLE_SUBMIT_QUESTION = By.id("enableAnonymousSubmitQuestion");
	public final By ELEMENT_ENABLE_RSS = By.id("enableRSS");
	public final By ELEMENT_VIEW_AVATAR = By.id("enableViewAvatar");
	public final By ELEMENT_POST_QUESTION_IN_ROOT = By.id("isPostQuestionInRootCategory");

	public final By ELEMENT_DISCUSSION_TAB = By.xpath("//button[text()='Discussion']");
	public final By ELEMENT_ENABLE_DISCUSSION_CHECKBOX = By.id("EnableDiscuss");
	public final By ELEMENT_ADD_FORUM = By.xpath("//*[@data-original-title='Select Forum']");

	public final By ELEMENT_MAIL_NOTIFICATION_TEMPLATE_TAB = By.xpath("//button[text()='Email Notifications']");
	public final By ELEMENT_MAIL_NEW_QUESTION_TAB = By.xpath("//button[text()='New Question']");
	public final By ELEMENT_MAIL_EDIT_ANSWER_TAB = By.xpath("//button[text()='Edit/Answer']");
	public final By ELEMENT_MAIL_MOVE_QUESTION_TAB = By.xpath("//button[text()='Move Question']");
	public final By ELEMENT_MAIL_CONTENT_FRAME1 = By.id("EmailMoveQuestion___Frame");
	public final By ELEMENT_MAIL_CONTENT_FRAME2 = By.xpath("//*[@id='xEditingArea']/iframe");
	public final By ELEMENT_CLOSE_SETTING_BUTTON = By.id("Close");

	/*---------------------------------Common functions-----------------------------------*/

	/** 
	 * Go to Answer
	 * @author hakt
	 */
	public void goToAnswer(){
		info("--Go to Answer--");
		WebElement answer = waitForAndGetElement(ELEMENT_ANSWER_LINK, 10000, 0);
		if (answer == null){
			createAnswerPageAtRootPath();                        
		} else {
			click(ELEMENT_ANSWER_LINK);
		} 
		waitForAndGetElement(ELEMENT_PRINT_ICON);
	}

	public void goToAnwserHome(){
		info("---Go to answer home---");
		click(ELEMENT_ANSWER_HOME_LINK);
		waitForElementNotPresent(ELEMENT_ANSWER_HOME_LINK);
	}

	/**
	 * Create answer page at root path
	 * @author hakt
	 */
	public void createAnswerPageAtRootPath() {
		navTool = new NavigationToolbar(driver);
		page = new PageManagement(driver);

		Map<String, String> ANSWER_PORTLET_ID = new HashMap<String, String>();
		ANSWER_PORTLET_ID.put("Collaboration/AnswersPortlet", "");

		info("--Go to intranet--");
		navTool.goToHomePage();

		info("Go to Add page editor");
		navTool.goToPageCreationWizard();

		info("Up level");
		click(ELEMENT_UP_LEVEL);

		info("Create Answer page");
		page.addNewPageEditor(DATA_ANSWER_PAGE_NAME, DATA_ANSWER_PAGE_NAME,"",CATEGORY_TITLE, 
				ANSWER_PORTLET_ID, false);	
	}

	/**function go to edit answer portlet in answer page
	 * @author lientm
	 */
	public void goToEditAnswerPortlet(){
		pageE = new PageEditor(driver);
		navTool = new NavigationToolbar(driver);

		goToAnswer();
		info("Go to edit answer portlet");
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
	}

	/**function seting display for categories in answer portlet
	 * @author lientm
	 * @param categoryScope
	 * @param display
	 */
	public void setDisplayCategoryScoping(String categoryScope, boolean display){
		button = new Button(driver);
		String[] cat = categoryScope.split("/");

		click(ELEMENT_CATEGORY_SCOPING_TAB);
		for (int i = 0; i < cat.length; i++){
			if (display){
				check(ELEMENT_CATEGORY_IN_SCOPE_TAB.replace("${catName}", cat[i]), 2);
			}else {
				uncheck(ELEMENT_CATEGORY_IN_SCOPE_TAB.replace("${catName}", cat[i]), 2);
			}
		}
		button.save();
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}

	/**function setting display mode in answer portlet
	 * @author lientm
	 * @param all
	 * @param date
	 * @param ascending
	 * @param opts
	 */
	public void settingDisplayMode(boolean all, boolean date, boolean ascending, boolean...opts){
		button = new Button(driver);
		click(ELEMENT_DISPLAY_MODE_TAB);
		if (all){
			select(ELEMENT_SELECT_DISPLAY_MODE, "All");
		}else {
			select(ELEMENT_SELECT_DISPLAY_MODE, "Approved");
		}
		if (date){
			select(ELEMENT_SELECT_ORDER_BY_MODE, "Created Date");
		}else {
			select(ELEMENT_SELECT_ORDER_BY_MODE, "Alphabet/Index");
		}
		if (ascending){
			select(ELEMENT_SELECT_DIRECTION_MODE, "Ascending order");
		}else {
			select(ELEMENT_SELECT_DIRECTION_MODE, "Descending order");
		}
		if (opts.length > 0){
			if (opts[0]){
				check(ELEMENT_ENABLE_VOTE_COMMENT, 2);
			}else{
				uncheck(ELEMENT_ENABLE_VOTE_COMMENT, 2);
			}			
		}
		if (opts.length > 1){
			if (opts[1]){
				check(ELEMENT_ENABLE_SUBMIT_QUESTION, 2);
			}else{
				uncheck(ELEMENT_ENABLE_SUBMIT_QUESTION, 2);
			}			
		}
		if (opts.length > 2){
			if (opts[2]){
				check(ELEMENT_ENABLE_RSS, 2);
			}else{
				uncheck(ELEMENT_ENABLE_RSS, 2);
			}			
		}
		if (opts.length > 3){
			if (opts[3]){
				check(ELEMENT_VIEW_AVATAR, 2);
			}else{
				uncheck(ELEMENT_VIEW_AVATAR, 2);
			}			
		}
		if (opts.length > 4){
			if (opts[4]){
				check(ELEMENT_POST_QUESTION_IN_ROOT, 2);
			}else{
				uncheck(ELEMENT_POST_QUESTION_IN_ROOT, 2);
			}			
		}
		button.save();
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}

	/**Function setting discussion in answert portlet
	 * @author lientm
	 * @param discuss
	 * @param forumPath
	 */
	public void settingDiscussion(boolean discuss, String forumPath){
		userGroup = new UserGroupManagement(driver);

		click(ELEMENT_DISCUSSION_TAB);
		if (discuss){
			check(ELEMENT_ENABLE_DISCUSSION_CHECKBOX, 2);
			click(ELEMENT_ADD_FORUM);
			userGroup.selectGroup(forumPath);
		}else {
			uncheck(ELEMENT_ENABLE_DISCUSSION_CHECKBOX, 2);
		}
		button.save();
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}

	/**function setting email template for answser portlet
	 * @author lientm
	 * @param tab
	 * @param content
	 */
	public void settingEmailTemplate(int tab, String content){
		click(ELEMENT_MAIL_NOTIFICATION_TEMPLATE_TAB);
		switch (tab) {
		case 1:
			click(ELEMENT_MAIL_NEW_QUESTION_TAB);
			break;
		case 2:
			click(ELEMENT_MAIL_EDIT_ANSWER_TAB);
			break;
		default:
			click(ELEMENT_MAIL_MOVE_QUESTION_TAB);
			break;
		}

		inputDataToFrameInFrame(ELEMENT_MAIL_CONTENT_FRAME1, ELEMENT_MAIL_CONTENT_FRAME2, content, true);
		switchToParentWindow();
		button.save();
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}

	/**
	 * @author lientm
	 * @param keyword
	 */
	public void quickSearchInAnswer(String keyword){
		info("Quick search with keyword " + keyword);
		type(ELEMENT_SIMPLESEARCH_TEXTBOX_IN_ANSWER, keyword, true);
		String id = waitForAndGetElement(By.xpath("//*[@id='UIPage']/div/div")).getAttribute("id");
		((JavascriptExecutor)driver).executeScript("javascript:eXo.webui.UIForm.submitForm('" + id + "#QuickSearchForm','Search',true);");	
		waitForAndGetElement(ELEMENT_SEARCH_RESULT_POPUP);
	}

	/**
	 * @author lientm
	 * @param term
	 * @param range
	 * @param from
	 * @param to
	 * @param catName
	 * @param moderate
	 * @param moderator
	 * @param author
	 * @param email
	 * @param language
	 * @param question
	 * @param answer
	 * @param comment
	 */
	public void advancedSearchInAnswer(String term, int range, String from, String to, String catName, boolean moderate, String moderator
			, String author, String email, String language, String question, String answer, String comment){
		quickSearchInAnswer("advanced search");
		click(ELEMENT_ADVANCED_SEARCH_BUTTON);
		if (term != null){
			type(ELEMENT_ADVANCED_SEARCH_TEXTBOX, term, true);
		}
		switch (range){
		case 1:
			info("Search range is all");
			click(ELEMENT_SEARCH_IN_ALL, 2);
			break;
		case 2:	
			info("Search in category");
			click(ELEMENT_SEARCH_IN_CATEGORY, 2);
			if (catName != null){
				type(ELEMENT_SEARCH_CATEGORY_NAME, catName, true);
			}
			if (moderate){
				click(ELEMENT_IS_MODERATE_TRUE);
			} else click(ELEMENT_IS_MODERATE_FALSE);
			if (moderator != null){
				type(ELEMENT_SEARCH_MODERATOR_TEXTBOX, moderator, true);
			}
			break;
		default:
			info("Search in entries");
			click(ELEMENT_SEARCH_IN_ENTRIES, 2);
			if (author != null){
				type(ELEMENT_SEARCH_AUTHOR_TEXTBOX, author, true);
			}
			if (email != null){
				type(ELEMENT_SEARCH_AUTHOR_EMAIL_TEXTBOX, email, true);
			}
			if (language != null){
				select(ELEMENT_SEARCH_SELECT_LANGUAGE, language);
			}
			if (question != null){
				type(ELEMENT_SEARCH_QUESTION_TEXTBOX, question, true);
			}
			if (answer != null){
				type(ELEMENT_SEARCH_ANSWER_TEXTBOX, answer, true);
			}
			if (comment != null){
				type(ELEMENT_SEARCH_COMMENT_TEXTBOX, comment, true);
			}
			break;
		}
		if (from != null){
			type(ELEMENT_SEARCH_FROM_DATE, from, true);
		}
		if (to != null){
			type(ELEMENT_SEARCH_TO_DATE, to, true);
		}
		click(ELEMENT_SEARCH_BUTTON);
		Utils.pause(1000);
	}

	
	//Set display Category
	public void setDisplayCategory(String categoryScope, boolean display){
		pageE = new PageEditor(driver);
		goToEditAnswerPortlet();
		setDisplayCategoryScoping(categoryScope, display);
		click(ELEMENT_CLOSE_SETTING_BUTTON);
		pageE.finishEditLayout();
		
	}
	
	//Set Display mode in tab Display mode
	public void setDisplayMode(boolean all, boolean date, boolean ascending, boolean...opts){
		pageE = new PageEditor(driver);
		goToEditAnswerPortlet();
		settingDisplayMode(all,date,ascending,opts);
		click(ELEMENT_CLOSE_SETTING_BUTTON);
		pageE.finishEditLayout();
	}
}