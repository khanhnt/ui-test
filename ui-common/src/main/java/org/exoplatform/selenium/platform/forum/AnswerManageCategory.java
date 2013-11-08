package org.exoplatform.selenium.platform.forum;

import  static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Migrate from plf3.5
 * @author lientm
 * @date 19 Aug 2013
 */
public class AnswerManageCategory extends AnswerBase {

	ForumPermission forumPer;
	public AnswerManageCategory(WebDriver dr){
		driver = dr;
		button = new Button(driver);
		alert = new ManageAlert(driver);
		forumPer = new ForumPermission(driver);
	}
	
	
	//Manage Category
	public final By ELEMENT_CATEGORY_BUTTON = By.className("uiIconAnsManageCategory");
	public final By ELEMENT_ADD_CATEGORY_LINK = By.linkText("Add Category");
	public final By ELEMENT_CATEGORY_NAME = By.id("eventCategoryName");
	public final By ELEMENT_ORDER = By.id("index");
	public final By ELEMENT_RETRICTED_AUDIENCE = By.id("userPrivate");
	public final By ELEMENT_DESCRIPTION = By.id("description");
	public final By ELEMENT_MODERATE_NEW_QUESTION = By.id("moderatequestions");
	public final By ELEMENT_VIEW_QUESTION_AUTHOR = By.id("ViewAuthorInfor");
	public final By ELEMENT_MODERATE_ANSWER = By.id("moderateAnswers");
	public final By ELEMENT_MODERATOR = By.id("moderator");
	public final By ELEMENT_EDIT_CATEGORY_MENU = By.xpath("//*[@class='uiIconEditCategory']");
	public final By ELEMENT_EDIT_CATEGORY_RIGHT_CLICK = By.linkText(" Edit");
	
	
	//Delete category
	public final By ELEMENT_DELETE_CATEGORY_ON_MENU = By.linkText("Delete");
	public final String MSG_DELETE_CATEGORY = "Are you sure you want to delete this category ?";
	public final By ELEMENT_OK_DELETE_BUTTON = By.xpath("//*[@id='UIForumPopupConfirmation']//button[text()='OK']");
	
	//Export category
	public final By ELEMENT_EXPORT_CATEGORY_LINK = By.linkText("Export");
	public final By ELEMENT_FILE_NAME_EXPORT = By.id("FileName");
	
	//Import category
	public final By ELEMENT_IMPORT_CATEGORY_LINK = By.linkText("Import");
	public final By ELEMENT_IMPORT_CATEGORY_INPUT = By.name("file");
	public final String ELEMENT_IMPORT_SUCCESS_MESSAGE = "The file has been imported.";
	
	//context menu
	public final By ELEMENT_MOVE_CATEGORY_LINK = By.linkText(" Move");
	public final By ELEMENT_DELETE_CATEGORY_LINK = By.linkText(" Delete");
	public final By ELEMENT_WATCH_CATEGORY_LINK = By.linkText(" Watch");
	public final By ELEMENT_UNWATCH_CATEGORY_LINK = By.linkText(" Unwatch");
	
	//watch/unwatch category
	public final String MESSAGE_WATCH_CATEGORY = "You are watching this category. You will be notified about all changes.";
	public final By WATCH_CATEGORY_ICON = By.xpath("//*[@class='uiIconWatch uiIconLightGray']");
	
	//move page
	public final String ELEMENT_CATEGORY_IN_MOVE_FORM = "//*[@id='UIMoveCategoryForm']//*[text()='${category}']";
	
	/*----------------------------------common function----------------------------------*/

	/**
	 * Open a category using jquery
	 * @param categoryName
	 */
	public void openCategoryInAnswer(String categoryName){
		info("Open category " + categoryName);
		for (int i = 0; i < ACTION_REPEAT; i ++){
			if (getElementFromTextByJquery(categoryName) != null) break;
			else Utils.pause(1000);
		}
		getElementFromTextByJquery(categoryName).click();
		Utils.pause(1000);
	}

	/**
	 * @author lientm
	 * @param name_edit
	 * @param order
	 * @param setAudience
	 * @param audience
	 * @param description
	 * @param setModerator
	 * @param moderator
	 * @param opt: option to check
	 */
	public void modifyDataInCategory(String name_edit, String order, String description, boolean... opt){
		
		if (name_edit != null){
			type(ELEMENT_CATEGORY_NAME, name_edit ,true);
		}
		if (order != null){
			type(ELEMENT_ORDER, order, true);
		}
		if (description != null){
			type(ELEMENT_DESCRIPTION, description, true);
		}
		if (opt.length > 0){
			if (opt[0]){
				check(ELEMENT_MODERATE_NEW_QUESTION, 2);
			} else {
				uncheck(ELEMENT_MODERATE_NEW_QUESTION, 2);
			}
		}
		if (opt.length > 1){
			if (opt[1]){
				check(ELEMENT_VIEW_QUESTION_AUTHOR, 2);
			} else {
				uncheck(ELEMENT_VIEW_QUESTION_AUTHOR, 2);
			}
		}
		if (opt.length > 2){
			if (opt[2]){
				check(ELEMENT_MODERATE_ANSWER, 2);
			} else {
				uncheck(ELEMENT_MODERATE_ANSWER, 2);
			}
		}
		
	}

	/**
	 * @author lientm
	 * Create Category in Answer by typing user/ group directly
	 * @param categoryName: String
	 * @param order: string
	 * @param setAudience: way to set audience
	 * @param audience: user/group audience
	 * @param description: string
	 * @param setModerator: way to set moderator
	 * @param moderator: user/group moderator
	 * @param opt: group checkbox
	 */
	public void addNewCategoryInAnswer(String categoryName, String order, String description, int permission, String[] userGroup,
			boolean restricted, boolean moderator, boolean... opt){
		info("Create new category " + categoryName);
		click(ELEMENT_CATEGORY_BUTTON);
		click(ELEMENT_ADD_CATEGORY_LINK);
		modifyDataInCategory(categoryName, order, description, opt);
		if (permission != 0){
			forumPer.configPermission4AnswerCategory(permission, userGroup, restricted, moderator);
		}
		button.save();
		Utils.pause(2000);
	}
	
	/**
	 * function edit category in answer
	 * @param wayEdit
	 * @param categoryName
	 * @param name_edit
	 * @param order
	 * @param description
	 * @param permission
	 * @param userGroup
	 * @param restricted
	 * @param moderator
	 * @param opt
	 */
	public void editCategoryInAnswer(String categoryName, String name_edit, String order, String description, int permission, String[] userGroup,
			boolean restricted, boolean moderator, boolean... opt){
		action = new Actions(driver);
		
		if (getElementFromTextByJquery(categoryName) != null){
			info("Edit category by right click");
			action.contextClick(getElementFromTextByJquery(categoryName)).perform();
			click(ELEMENT_EDIT_CATEGORY_RIGHT_CLICK);
		} else {
			info("Edit category while opening category");
			click(ELEMENT_CATEGORY_BUTTON);
			click(ELEMENT_EDIT_CATEGORY_MENU);
		}
		modifyDataInCategory(name_edit, order, description, opt);
		
		if (permission != 0){
			forumPer.configPermission4AnswerCategory(permission, userGroup, restricted, moderator);
		}
		button.save();
		Utils.pause(2000);
	}

	/**
	 * Function delete category in answer
	 * @param wayDelete
	 * @param categoryName
	 */
	public void deleteCategoryInAnswer(String categoryName, boolean...verify){
		boolean check = verify.length > 0 ? verify[0]:true;
		action = new Actions(driver);
		if (getElementFromTextByJquery(categoryName) != null){
			info("Delete category by right click");
			action.contextClick(getElementFromTextByJquery(categoryName)).perform();
			click(ELEMENT_DELETE_CATEGORY_LINK);
		}else {
			info("Delete category while opening category");
			click(ELEMENT_CATEGORY_BUTTON);
			click(ELEMENT_DELETE_CATEGORY_ON_MENU);
		}
		//waitForMessage(MSG_DELETE_CATEGORY);
		click(ELEMENT_OK_DELETE_BUTTON);
		if (check){
			//waitForTextNotPresent(categoryName);
			waitForElementNotPresent(By.linkText(categoryName));
		}
		Utils.pause(1000);
	}
	
	/**
	 * function move category source to category destination
	 * @param source
	 * @param destination
	 */
	public void moveCategory(String source, String destination){
		action = new Actions(driver);
		info("Move category " + source + "to category " + destination);
		action.contextClick(getElementFromTextByJquery(source)).perform();
		click(ELEMENT_MOVE_CATEGORY_LINK);
		doubleClickOnElement(ELEMENT_CATEGORY_IN_MOVE_FORM.replace("${category}", destination));
		waitForElementNotPresent(ELEMENT_CATEGORY_IN_MOVE_FORM.replace("${category}", destination));
	}
	
	/**
	 * function export category in answer
	 * @param fileName
	 */
	public void exportAnswerCategory(String fileName){
		info("Export category to file " + fileName);
		click(ELEMENT_CATEGORY_BUTTON);
		click(ELEMENT_EXPORT_CATEGORY_LINK);
		type(ELEMENT_FILE_NAME_EXPORT, fileName, true);
		button.save();
		Utils.pause(1000);		
	}
	
	/**
	 * function import category in answer
	 * @param fileName
	 */
	public void importAnswerCategory(String fileName){
		info("Import category from file " + fileName);
		
		String[] links = fileName.split("/");
		click(ELEMENT_CATEGORY_BUTTON);
		click(ELEMENT_IMPORT_CATEGORY_LINK);
		
		WebElement element = waitForAndGetElement(ELEMENT_IMPORT_CATEGORY_INPUT, DEFAULT_TIMEOUT, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", element);
		element.sendKeys(Utils.getAbsoluteFilePath("TestData/" + fileName));
		switchToParentWindow();
		waitForAndGetElement(ELEMENT_CONTAINS_TEXT.replace("${text}", links[links.length-1]), DEFAULT_TIMEOUT, 1, 2);
		button.save();
		waitForMessage(ELEMENT_IMPORT_SUCCESS_MESSAGE);
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(2000);
	}
	
	/**
	 * function watch/unwatch a category in answer
	 * @param  watch
	 * @param categoryName
	 */
	public void watchAnswerCategory(String categoryName, boolean watch){
		action = new Actions(driver);
		action.contextClick(getElementFromTextByJquery(categoryName)).perform();
		if (watch){
			if (waitForAndGetElement(ELEMENT_WATCH_CATEGORY_LINK, 5000, 0) != null){
				click(ELEMENT_WATCH_CATEGORY_LINK);
				waitForMessage(MESSAGE_WATCH_CATEGORY);
				click(ELEMENT_OK_INFOR_POPUP);	
				waitForAndGetElement(WATCH_CATEGORY_ICON);
			}else {
				info("Category has already watched");
			}
		}else {
			if (waitForAndGetElement(ELEMENT_UNWATCH_CATEGORY_LINK, 5000, 0) != null) {
				click(ELEMENT_UNWATCH_CATEGORY_LINK);
				waitForElementNotPresent(WATCH_CATEGORY_ICON);
			}else {
				info("Category has not watched to unwatch");
			}
		}
	}
	
	/**
	 * function drag drop category in answer
	 * @param source
	 * @param target
	 */
	public void dragDropAnswerCategory(String source, String target){
		action = new Actions(driver);
		info("Drag category " + source + " to category " + target);
		action.dragAndDrop(getElementFromTextByJquery(source), getElementFromTextByJquery(target)).build().perform();
		Utils.pause(2000);
	}
	
}
