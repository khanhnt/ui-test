package org.exoplatform.selenium.platform.forum;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Migrate from plf3.5
 * @author lientm
 * @date 19 Aug 2013
 */

public class ForumManageTopic extends ForumBase {
	
	ForumPermission per;
	ForumManageForum magFor;
	ForumManageCategory magCat;
	UserGroupManagement userGroup;
	ForumManagePost mngPost;

	public ForumManageTopic(WebDriver dr){
		driver = dr;

		userGroup = new UserGroupManagement(driver);
		per = new ForumPermission(driver);
		magCat = new ForumManageCategory(driver);
		magFor = new ForumManageForum(driver);
		but = new Button(driver);
		alert = new ManageAlert(driver);
	}

	//----------------topic home screen---------------------------------------------------

	public By ELEMENT_DELETE_TOPIC = By.id("UITopicDetailConfirm0");
	public By ELEMENT_EDIT_TOPIC = By.xpath("//*[contains(@href, 'EditTopic')]");
	public By ELEMENT_MOVE_TOPIC = By.xpath("//div[@class='dropdown uiDropdownWithIcon actionIcon open']//i[@class='uiIconMove']");
	public By ELEMENT_APPROVE_TOPIC = By.xpath("//a[@class='ItemIcon SetApproveIcon' and text()='Approve']");
	public By ELEMENT_CHECK_ALL = By.xpath("//*[@id='UITopicContent']//input[@title='Check All']");
	public String ELEMENT_BREADCRUMB_TOPIC = "//a[@data-original-title='${forum}']/../../li[text()='${topic}']"; 
	public String MSG_DELETE_TOPIC = "Are you sure you want to delete this topic ?";
	public By ELEMENT_OK_DELETE_TOPIC = By.xpath("//span[contains(text(),'Are you sure you want to delete this topic ?')]/../../..//button[@class='btn actionOK']");
	public By ELEMENT_LOCK_TOPIC = By.xpath("//li[@class='defaultStyle forumSeparatorLine']//a[contains(text(),'Lock')]"); 
	public By ELEMENT_UNLOCK_TOPIC = By.xpath("//li[@class='defaultStyle forumSeparatorLine']//a[contains(text(),'Unlock')]"); 
	public By ELEMENT_CLOSE_TOPIC = By.xpath("//i[@class='uiIconMinus']");
	public By ELEMENT_OPEN_TOPIC = By.xpath("//i[@class='uiIconOpen']");

	public String ELEMENT_CATEGORY_BREAD = "//a[@title='${category}']"; 
	
	//----------------start topic screen--------------------------------------------------

	public By ELEMENT_START_TOPIC_BUTTON = By.xpath("//*[@class='btn btn-primary pull-left']");
	public By ELEMENT_POPUP_START_TOPIC = By.xpath("//span[@class='PopupTitle popupTitle' and text()='New Topic']");
	public By ELEMENT_SUBMIT_BUTTON = By.xpath("//button[text()='Submit']");
	public By ElEMENT_CANCEL_ADD_TOPIC = By.xpath("//*[@id='UITopicForm']//button[text()='Cancel']");

	public By ELEMENT_TOPIC_CONTENT_TAB = By.linkText("Content");
	public By ELEMENT_TOPIC_TITLE = By.id("ThreadTitle");
	public By ELEMENT_TOPIC_MESSAGE_FRAME_1 = By.xpath("//iframe[@id='messageContent___Frame']");
	public By ELEMENT_TOPIC_MESSAGE_FRAME_2 = By.xpath("//td[@id='xEditingArea']/iframe");
	public String ELEMENT_REMOVE_FILE = "//a[contains(@title, '${file}')]/../*//img[@class='DustBin']";

	public By ELEMENT_TOPIC_ICON_TAB = By.linkText("Icon");
	public String ELEMENT_GROUP_ICON = "//div[@class='ItemTitle' and text()='${group}']";
	public String ELEMENT_ICON = "//div[@class='${icon}']";

	//Options tab
	public By ELEMENT_TOPIC_OPTIONS_TAB = By.linkText("Options");
	public By ELEMENT_TOPIC_ADD_TYPE = By.xpath("//img[@alt='Add Topic Type']");
	public By ELEMENT_TOPIC_SELECT_TYPE = By.id("TopicType");
	public By ELEMENT_TOPIC_STATE = By.id("TopicState");
	public By ELEMENT_TOPIC_STATUS = By.id("TopicStatus");
	public By ELEMENT_TOPIC_POSTS_MODER = By.id("ModeratePost");
	public By ELEMENT_TOPIC_POSTS_NOTIFY = By.id("NotifyWhenAddPost");
	public By ELEMENT_TOPIC_STICKY = By.id("Sticky");

	public By ELEMENT_TOPIC_PERMISSION_TAB = By.linkText("Permissions");
	public By ELEMENT_RATING_TOPIC = By.xpath("//div[contains(text(),'Rating')]");

	//------------------add topic type screen--------------------------------------------------
	public By ELEMENT_TOPIC_ADD_TYPE_POPUP = By.xpath("//span[@class='PopupTitle' and text()='Topic Type']");
	public By ELEMENT_TOPIC_TYPE_NAME = By.id("topicTypeName");
	public By ELEMENT_CANCEL_ADD_TYPE = By.xpath(".//*[@id='UIAddTopicTypeForm']/div[3]/a[text()='Cancel']");

	//------------------edit topic screen------------------------------------------------------
	public By ELEMENT_TOPIC_EDIT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Edit Topic']");
	public By ELEMENT_TOPIC_EDIT_REASON = By.id("editReason");

	//-------------------move topic screen----------------------------------------------------

	public By ELEMENT_POPUP_MOVE_TOPIC = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Move Topics']"); 
	public String ELEMENT_FORUM_SELECT = "//a[contains(text(),'${forum}')]"; 

	//-------------------Add tag--------------------------------------------------------------
	public By ELEMENT_TAG = By.xpath("//i[@class='uiIconTag uiIconLightGray']");
	public By ELEMENT_ADD_TAG = By.id("AddTag");
	public By ELEMENT_ADD_TAG_BUTTON = By.xpath("//a[contains(text(),'Add Tag')]");
	public String ELEMENT_TAG_NUMBER = "//*[@id='searchTagName']/div/font[text()='(${No})']";
	public By ELEMENT_MANAGE_TAG = By.linkText("Manage Tag");
	public By ELEMENT_UNTAG = By.xpath("//a[contains(text(), 'Untag')]");
	public String ELEMENT_CHECKBOX_UNTAG = "//input[@type='checkbox' and @title='${topic}']";
	public String MESSAGE_UNTAG = "Are you sure to remove this tag from the topic?";
	public String MESSAGE_ADD_TAG_BLANK_NAME = "The field must not be blank.";
	public String ELEMENT_UNTAG_ICON = "//a[text()='${tag}']/../span[@title='Untag this topic.']";
	public String ELEMENT_suggestion = "#searchTagName div:contains('${tag}') font:contains('(${No})')";

	//-------------------censor topic list form-----------------------------------------------
	public By ELEMENT_POPUP_CENSOR_TOPIC = By.xpath("//span[@class='PopupTitle' and text()='Censor Topics List']");
	public String ELEMENT_CENSOR_TOPIC_CHECKBOX = "//*[text()='${topic}']/../../../../td/input[@type='checkbox']";
	public By ELEMENT_CENSOR_APPROVE_BUTTON = By.linkText("Approve");

	//-------------------Go to topic types management screen----------------------------------------------------
	public By ELEMENT_TOPIC_TYPES=By.xpath("//span[text()='Topic Types']");
	public By ELEMENT_TOPIC_MANAGER_POPUP=By.xpath("//span[text()='Topic Type Manager']");
	public By ELEMENT_ADD_TOPIC_TYPE_BUTTON=By.xpath("//a[@class='ActionButton LightBlueStyle' and text()='Add Topic Type']");

	//-------------------Poll management screen-----------------------------------------------------------------

	public By ELEMENT_POLL_POPUP = By.xpath("//span[@class='PopupTitle' and text()='Poll']");

	//---------------------------------Vote topic--------------------------------
	public By ELEMENT_RATE_TOPIC = By.xpath("//a[@class='actionIcon' and contains(text(),'Rate')]");
	public By ELEMENT_RATE_TOPIC_TERRIBLE = By.xpath("//i[@data-original-title='Poor']");
	public By ELEMENT_RATE_TOPIC_BAD = By.xpath("//i[@data-original-title='Below average']");
	public By ELEMENT_RATE_TOPIC_AVERAGE = By.xpath("//i[@data-original-title='Average']");
	public By ELEMENT_RATE_TOPIC_GOOD = By.xpath("//i[@data-original-title='Above average']");
	public By ELEMENT_RATE_TOPIC_EXCELLENT = By.xpath("//i[@data-original-title='Good']");
	public String ELEMENT_TOPIC_VOTED = "//div[@class='avgRatingImages']/i[@class='voted'][${rate}]";
	public String ELEMENT_RATE_CLOSE_BUTTON = "//form[@id='UIRatingForm']//*[text()='Close']";

	//------------Watch/Unwatch Topic---------------
	public By ELEMENT_WATCH_TOPIC = By.linkText(" Watch");
	public String ELEMENT_CLASS_WATCH_TOPIC = "AddWatchingIcon StatusIcon";


	/*------------------------------Common function-----------------------------------*/

	/** function: go to start topic from action bar
	 * @author lientm
	 */
	public void goToStartTopic(int... wayStart){
		int way = wayStart.length > 0 ? wayStart[0]:2;
		if (way == 1){
			info("Go to start topic from more action");
			click(ELEMENT_MORE_ACTION);
			click(ELEMENT_START_TOPIC_BUTTON);
		}else {
			info("Go to start topic by click start topic but");
			click(ELEMENT_START_TOPIC_BUTTON);
		}
		waitForAndGetElement(ELEMENT_POPUP_START_TOPIC);
	}

	/** function: remove file in attach popup
	 * @author lientm
	 * @param fileName: remove a attached file in add/edit topic
	 */
	public void removeFileInTopic(String fileName){
		By ELEMENT_REMOVE = By.xpath(ELEMENT_REMOVE_FILE.replace("${file}", fileName));

		WebElement element = waitForAndGetElement(ELEMENT_REMOVE);
		if (element != null) {
			info("Remove file " + fileName);
			click(ELEMENT_REMOVE);
			waitForElementNotPresent(ELEMENT_REMOVE);
			info("Remove file " + fileName + "successfully");
		} else {
			info("Can not found file remove");
		}
	}

	/** function: input data in content tab in start topic popup
	 * @author lientm
	 * @param title: title of topic
	 * @param message: message
	 * @param file: file upload
	 */
	public void inputDataInContentTab_StartTopic(String title, String message, String... file){
		info("Input data in content tab");
		waitForAndGetElement(ELEMENT_TOPIC_TITLE);
		if (title != null){
			type(ELEMENT_TOPIC_TITLE, title, true);
		}

		if (message != "" && message != null){
			inputDataToFrameInFrame(ELEMENT_TOPIC_MESSAGE_FRAME_1, ELEMENT_TOPIC_MESSAGE_FRAME_2, message, true,false);
			switchToParentWindow();	
		}
		if(file.length > 0 && file[0] != "" && file[0] != null){
			click(ELEMENT_ATTACH_FILE);
			waitForAndGetElement(ELEMENT_POPUP_UPLOAD_FILE);
			attachFile(file[0]);
			waitForElementNotPresent(ELEMENT_POPUP_UPLOAD_FILE);
		}
	}

	/** function: input data in icon tab in start topic popup
	 * @author lientm
	 * @param groupName: group icon
	 * @param iconClass: icon
	 */
	public void chooseIcon(String groupName, String iconClass){
		By ELEMENT_GROUP = By.xpath(ELEMENT_GROUP_ICON.replace("${group}", groupName));
		By ICON = By.xpath(ELEMENT_ICON.replace("${icon}", iconClass));

		info("Choose icon");
		if (groupName != "" && groupName != null && iconClass != "" && iconClass != null){
			waitForAndGetElement(ELEMENT_GROUP);
			click(ELEMENT_GROUP);
			waitForAndGetElement(ICON);
			click(ICON);
		} else {
			info("There is not data input to choose icon");
		}
	}

	/** function: add a topic type in Options tab when start topic
	 * @author lientm
	 * @param typeName: name of topic type
	 * @param groupName: group icon
	 * @param iconClass: icon
	 */
	public void addTopicType(String typeName, String groupName, String iconClass){
		info("Add new topic type");
		waitForAndGetElement(ELEMENT_TOPIC_ADD_TYPE);
		click(ELEMENT_TOPIC_ADD_TYPE);
		modifyDataOfTopicType(typeName, groupName, iconClass);	
		waitForElementNotPresent(ELEMENT_TOPIC_ADD_TYPE_POPUP);	
	}


	public void inputDataInOptionsTab_StartTopic(boolean...options){
		if (options.length > 0){
			info("Input data in Options tab");
			click(ELEMENT_TOPIC_OPTIONS_TAB);
		}
		if (options.length > 0){
			if (options[0]){
				check(ELEMENT_TOPIC_STATE, 2);
			}else {
				uncheck(ELEMENT_TOPIC_STATE, 2);
			}
		}
		if (options.length > 1){
			if (options[1]){
				check(ELEMENT_TOPIC_STATUS, 2);
			}else {
				uncheck(ELEMENT_TOPIC_STATUS, 2);
			}
		}
		if (options.length > 2){
			if (options[2]){
				check(ELEMENT_TOPIC_STICKY, 2);
			}else {
				uncheck(ELEMENT_TOPIC_STICKY, 2);
			}
		}
		if (options.length > 3){
			if (options[3]){
				check(ELEMENT_TOPIC_POSTS_MODER, 2);
			}else {
				uncheck(ELEMENT_TOPIC_POSTS_MODER, 2);
			}
		}
		if (options.length > 4){
			if (options[4]){
				check(ELEMENT_TOPIC_POSTS_NOTIFY, 2);
			}else {
				uncheck(ELEMENT_TOPIC_POSTS_NOTIFY, 2);
			}
		}
	}

	public void inputDataStartTopic(String title, String message, String file, int type, String[] userGroup, boolean canview, boolean canpost, boolean... options){
		per = new ForumPermission(driver);
		inputDataInContentTab_StartTopic(title, message, file);
		inputDataInOptionsTab_StartTopic(options);
		if (type != 0){
			click(ELEMENT_TOPIC_PERMISSION_TAB);
			per.configPermission4Topic(type,userGroup, canview, canpost);
		}
	}

	/**
	 * 
	 * @param title
	 * @param message
	 * @param file
	 * @param type
	 * @param userGroup
	 * @param canview
	 * @param canpost
	 * @param options
	 */
	public void startTopic(String title, String message, String file, int type, String[] userGroup, boolean canview, boolean canpost, boolean... options){
		info("Start a topic");
		goToStartTopic();
		inputDataStartTopic(title, message, file, type, userGroup, canview, canpost, options);
		click(ELEMENT_SUBMIT_BUTTON);
	}	

	/**
	 * 
	 * @param title
	 * @param message
	 */
	public void quickStartTopic(String title, String message){
		info("Start a topic");
		goToStartTopic();
		inputDataInContentTab_StartTopic(title, message);
		click(ELEMENT_SUBMIT_BUTTON);
		waitForAndGetElement(By.linkText(title));
		info("Start topic successfully");
	} 

	/** function: delete a topic
	 * @author lientm
	 * @param title: title of topic that needs to delete
	 */

	public void deleteTopic(String title){
		click(ELEMENT_MORE_ACTION);
		info("Delete topic");
		click(ELEMENT_DELETE_TOPIC);

		alert.waitForMessage(MSG_DELETE_TOPIC);
		click(ELEMENT_OK_DELETE_TOPIC); 
		waitForTextNotPresent(title);
		info("Delete topic successfully");
	}

	/**
	 * function to go edit a topic
	 * @author lientm
	 */
	public void goToEditTopic(){
		info("Go to edit topic");
		click(ELEMENT_MORE_ACTION);
		click(ELEMENT_EDIT_TOPIC);

		waitForAndGetElement(ELEMENT_TOPIC_EDIT_POPUP);
	}

	/**
	 * 
	 * @param title
	 * @param message
	 * @param file
	 * @param type
	 * @param userGroup
	 * @param canview
	 * @param canpost
	 * @param options
	 */
	public void editTopic(String title, String message, String file, int type, String[] userGroup, boolean canview, boolean canpost, boolean... options){
		goToEditTopic();
		info("Edit topic");
		inputDataStartTopic(title, message, file, type, userGroup, canview, canpost, options);
		click(ELEMENT_SUBMIT_BUTTON);
		waitForElementNotPresent(ELEMENT_TOPIC_EDIT_POPUP);
		waitForTextPresent(title);
		info("Edit topic successfully");
	}

	/** function: move a topic to other forum
	 * @author lientm
	 * @param topic: title of topic
	 * @param destination: path go to forum
	 */
	public void moveTopic(String topic, String destination){

		info("Move topic to forum " + destination);
		waitForAndGetElement(ELEMENT_MORE_ACTION);
		click(ELEMENT_MORE_ACTION);
		waitForAndGetElement(ELEMENT_MOVE_TOPIC);
		click(ELEMENT_MOVE_TOPIC);
		waitForAndGetElement(ELEMENT_POPUP_MOVE_TOPIC);

		click(ELEMENT_FORUM_SELECT.replace("${forum}", destination)); 
		waitForElementNotPresent(ELEMENT_POPUP_MOVE_TOPIC);
		String links[] = destination.split("/");
		int length = links.length;
		waitForAndGetElement(ELEMENT_BREADCRUMB_TOPIC.replace("${forum}", links[length-1]).replace("${topic}", topic)); 
		info("Move topic successfully");
	}	

	/**function go to Add tag for topic
	 * @author lientm
	 */
	public void goToAddTagForTopic(){
		waitForAndGetElement(ELEMENT_TAG);
		click(ELEMENT_TAG);
		waitForAndGetElement(ELEMENT_ADD_TAG);
	}

	/**
	 * function add tag for topic
	 * @param tagName: name of tag
	 */
	public void addTagForTopic(String tagName){
		String[] tag = tagName.split(" ");

		goToAddTagForTopic();
		type(ELEMENT_ADD_TAG, tagName, true);
		click(ELEMENT_ADD_TAG_BUTTON);
		for(int i = 0; i < tag.length; i ++){
			waitForAndGetElement(By.linkText(tag[i]));
		}
		info("Add tag for topic successfully");
	}

	/**function untag for many topics
	 * @author lientm
	 * @param tagName: Name of tag
	 * @param topic: topics that need untag
	 */
	public void unTagForTopics(String tagName, String...topic){
		info("Go to manage tag");
		click(By.linkText(tagName));
		waitForAndGetElement(ELEMENT_MANAGE_TAG);		
		info("choose topic to untag");
		for (int i = 0; i < topic.length; i ++){
			By element_topic = By.xpath(ELEMENT_CHECKBOX_UNTAG.replace("${topic}", topic[i]));
			check(element_topic);
		}
		click(ELEMENT_MANAGE_TAG);
		for(int i = 0; i < 5; i ++){
			if (waitForAndGetElement(ELEMENT_UNTAG, 5000, 0) == null){
				click(ELEMENT_MANAGE_TAG);
				info("retry click manage tag" + i);
			}
			else break;
		}
		click(ELEMENT_UNTAG);
		alert.waitForConfirmation(MESSAGE_UNTAG);
		info("Untag for topics successfully");
	}

	/**function untag directly for topic
	 * @author lientm
	 * @param tagName
	 */
	public void unTagDirectly(String tagName){
		String[] tag = tagName.split(" ");

		for(int i = 0; i < tag.length; i ++){
			By element_untag = By.xpath(ELEMENT_UNTAG_ICON.replace("${tag}", tag[i]));	
			info("untag directly");
			click(element_untag);
			waitForTextNotPresent(tag[i]);
		}
		info("untag successfully");
	}

	/**function get element that is suggestion tag element when type into tag name
	 * @author lientm
	 * @param tag: name of suggestion tag
	 * @param number
	 * @return WebElement
	 */
	public WebElement getSuggestionElement(String tag, String number){	
		String jQuerySelector = ELEMENT_suggestion.replace("${tag}", tag).replace("${No}", number);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return $(\"" + jQuerySelector + "\").get(0);";
		WebElement exampleDiv = (WebElement) js.executeScript(script);
		return exampleDiv;
	}

	/**function check Occurence number when add tag
	 * @author lientm
	 * @param number
	 * @param tagName
	 */
	public void checkOccurenceOfTag(String number, String tagName, String... prefix){
		String valid = prefix.length > 0 ? prefix[0]: tagName;
		String[] tag = tagName.split(" ");
		String[] pre = valid.split(" ");
		By element_tag_no = By.xpath(ELEMENT_TAG_NUMBER.replace("${No}", number));

		if (pre.length == tag.length){
			goToAddTagForTopic();
			for (int i = 0; i < pre.length; i ++){
				type(ELEMENT_ADD_TAG, pre[i], true);
				info("Check suggesstion and occurence when add tag that is already exited");
				waitForAndGetElement(element_tag_no);				
				WebElement exampleDiv = getSuggestionElement(tag[i], number);				
				if (exampleDiv == null) assert false;
				info("Suggesstion and Occurence of tag is true");
			}
		}
	}

	/** function approve a topic
	 * @author lientm
	 */
	public void approveTopic(){
		waitForAndGetElement(ELEMENT_MORE_ACTION);
		click(ELEMENT_MORE_ACTION);
		info("Approve topic");
		waitForAndGetElement(ELEMENT_APPROVE_TOPIC);
		click(ELEMENT_APPROVE_TOPIC);
	}

	/** function censor for Topic list
	 * @author lientm
	 * @param topic: title of topic
	 */
	public void censorTopic(String topic){
		By element_topic = By.xpath(ELEMENT_CENSOR_TOPIC_CHECKBOX.replace("${topic}", topic));

		info("Approve a topic that is pending by censor");
		waitForAndGetElement(ELEMENT_MODERATION);
		click(ELEMENT_MODERATION);
		waitForAndGetElement(magFor.ELEMENT_CENSOR_TOPIC);
		click(magFor.ELEMENT_CENSOR_TOPIC);
		waitForAndGetElement(ELEMENT_POPUP_CENSOR_TOPIC);
		check(element_topic);
		click(ELEMENT_CENSOR_APPROVE_BUTTON);
		waitForElementNotPresent(ELEMENT_POPUP_CENSOR_TOPIC);
		info("Approve a topic successfully");
	}

	/**Function: Go to Topic Types
	 * @author hakt
	 * Date: 07/12/2012
	 */

	public void goToTopicTypes(){
		info("Go to topic types");
		click(ELEMENT_ADMINISTRATION);
		click(ELEMENT_TOPIC_TYPES);
		waitForAndGetElement(ELEMENT_TOPIC_MANAGER_POPUP);
	}

	/** function: modify data of a topic types 
	 * @author hakt
	 * @param typeName: name of topic type
	 * @param groupName: group icon
	 * @param iconClass: icon
	 */
	public void modifyDataOfTopicType(String typeName, String groupName, String iconClass){
		info("Modify topic type");
		waitForAndGetElement(ELEMENT_TOPIC_ADD_TYPE_POPUP);
		type(ELEMENT_TOPIC_TYPE_NAME, typeName, true);
		chooseIcon(groupName, iconClass);
		but.save();
	}

	/** function: create a topic types 
	 * @author hakt
	 * @param typeName: name of topic type
	 * @param groupName: group icon
	 * @param iconClass: icon
	 */
	public void createTopicType(String typeName, String groupName, String iconClass){
		info("Add new topic type");

		click(ELEMENT_ADD_TOPIC_TYPE_BUTTON);
		modifyDataOfTopicType(typeName, groupName, iconClass);
	}

	/**
	 * Function to edit a topic type
	 * @author hakt
	 * @param oldTopicTypeName
	 * @param newTopicTypeName
	 * @param groupName
	 * @param iconClass
	 */
	public void editTopicType(String oldTopicTypeName, String newTopicTypeName, String groupName, String iconClass){
		By TOPIC_TYPE_TO_EDIT=By.xpath("//td[text()='"+oldTopicTypeName+"']/../*//*[@class='Icon16x16 EditIcon']");

		info("Edit a topic type");
		click(TOPIC_TYPE_TO_EDIT);
		modifyDataOfTopicType(newTopicTypeName, groupName, iconClass);
	}

	/**
	 * function: delete a topic types 
	 * @author hakt
	 * @param topicTypeName
	 */
	public void deleteTopicType(String topicTypeName){
		By TOPIC_TYPE_TO_DELETE=By.xpath("//td[text()='"+topicTypeName+"']/../*//*[@class='Icon16x16 DeleteIcon']");
		By ELEMENT_TOPIC_TYPE=By.xpath("//td[text()='"+topicTypeName+"']");

		info("Delete topic type");
		click(TOPIC_TYPE_TO_DELETE);
		waitForElementNotPresent(ELEMENT_TOPIC_TYPE);
	}

	/** function: delete all topic
	 * @author HangNTT
	 * @param title: All topic that needs to delete
	 */

	public void deleteAllTopic(){
		waitForAndGetElement(ELEMENT_CHECK_ALL);
		click(ELEMENT_CHECK_ALL);
		info("Delete topic");
		waitForAndGetElement(ELEMENT_MODERATION);
		click(ELEMENT_MODERATION);
		waitForAndGetElement(ELEMENT_MODERTATION_DELETE_BUTTON);
		click(ELEMENT_MODERTATION_DELETE_BUTTON);
		alert.acceptAlert();
		waitForTextPresent("This forum is empty.");
		info("Delete topic successfully");
	}

	/**
	 * @author thuntn
	 * @param rate: 1: poor
	 * 				2: Below average
	 * 				3: average
	 * 				4: Above average
	 * 				5: good
	 * 				default: average
	 */
	public void voteTopic(int rate, boolean...verify){
		boolean check = verify.length > 0 ? verify[0]:false;
		info("--Vote a topic--");
		click(ELEMENT_RATE_TOPIC);
		switch (rate){
		case 1: click(ELEMENT_RATE_TOPIC_TERRIBLE); break;
		case 2: click(ELEMENT_RATE_TOPIC_BAD); break;
		case 3: click(ELEMENT_RATE_TOPIC_AVERAGE); break;
		case 4: click(ELEMENT_RATE_TOPIC_GOOD); break;
		case 5: click(ELEMENT_RATE_TOPIC_EXCELLENT); break;
		default: click(ELEMENT_RATE_TOPIC_AVERAGE); break;
		}
		waitForElementNotPresent(ELEMENT_RATE_CLOSE_BUTTON);

		if(check) {
			click(ELEMENT_RATE_TOPIC);
			for(int i = 1;i <= rate;i++){
				waitForAndGetElement(ELEMENT_TOPIC_VOTED.replace("${rate}", Integer.toString(i)));

			}
			click(ELEMENT_RATE_CLOSE_BUTTON);
			waitForElementNotPresent(ELEMENT_RATE_CLOSE_BUTTON);
		}

	}
	/** Do actions on a topic: lock, unlock, close, open
	 * @author thuntn
	 * @param action = 1: lock a topic
	 * 				 = 2: unlock a topic
	 * 				 = 3: close a topic
	 * 				 = 4: Reopen a topic
	 */
	public void actionOnTopic(int action){
		mngPost = new ForumManagePost(driver);
		click(ELEMENT_MORE_ACTION);
		switch (action) {
		case 1: 
			info("Lock a topic");
			click(ELEMENT_LOCK_TOPIC);
			waitForElementNotPresent(mngPost.ELEMENT_POST_REPLY_BUTTON);
			waitForAndGetElement(mngPost.ELEMENT_REPLY_LOCK_BUTTON);
			click(mngPost.ELEMENT_REPLY_LOCK_BUTTON);
			waitForElementNotPresent(mngPost.ELEMENT_POST_POPUP_NEW);
			break;

		case 2:
			info("Unlock a topic");
			click(ELEMENT_UNLOCK_TOPIC);

			waitForAndGetElement(mngPost.ELEMENT_POST_REPLY_BUTTON);
			waitForElementNotPresent(mngPost.ELEMENT_REPLY_LOCK_BUTTON);
			click(mngPost.ELEMENT_POST_REPLY_BUTTON);
			waitForAndGetElement(mngPost.ELEMENT_POST_POPUP_NEW);
			click(mngPost.ELEMENT_POST_CANCEL_BUTTON);
			break;

		case 3:
			info("Close a topic");
			click(ELEMENT_CLOSE_TOPIC);

			waitForAndGetElement(mngPost.ELEMENT_REPLY_LOCK_BUTTON);
			click(mngPost.ELEMENT_REPLY_LOCK_BUTTON);
			waitForElementNotPresent(mngPost.ELEMENT_POST_POPUP_NEW);
			break;

		case 4:
			info("Open a topic");
			click(ELEMENT_OPEN_TOPIC);

			waitForAndGetElement(mngPost.ELEMENT_POST_REPLY_BUTTON);
			waitForElementNotPresent(mngPost.ELEMENT_REPLY_LOCK_BUTTON);
			click(mngPost.ELEMENT_POST_REPLY_BUTTON);
			waitForAndGetElement(mngPost.ELEMENT_POST_POPUP_NEW);
			click(mngPost.ELEMENT_POST_CANCEL_BUTTON);
			break;

		default: break;
		}
	}

	/**
	 * Add category, forum, topic with simple data
	 * @param cate
	 * @param forum
	 * @param topic
	 * @param descTopic
	 */
	public void addCategoryForumTopic(String category, String forum, String topic, String descTopic){
		String[] permission = {};
		String[] addForum = {forum, "1",null,null,forum};
		magCat.addNewCategoryInForum(category, "1", 0,permission, category, 0,permission);
		magFor.addForum(category, addForum, true, "", "", false,0, permission);
		click(ELEMENT_START_TOPIC_BUTTON);
		startTopic(topic, descTopic, "", 0, permission,false, false, false);
	}
}