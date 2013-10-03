package org.exoplatform.selenium.platform.forum;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Migrate from plf3.5
 * @author lientm
 * @date 19 Aug 2013
 */
public class FAQ extends AnswerBase {
	
	PageManagement page;
	ManageApplications app;
	AnswerManageAnwser answer;
	AnswerManageCategory cat;
	AnswerManageQuestion question;
	
	public final String DATA_ANSWER_FAQ_PAGE_NAME="AnswerFAQ";
	public final By ELEMENT_FAQ_VIEWER = By.id("UIFAQPortlet");
	public final By ELEMENT_FQA_CONTAINER = By.xpath("//a[@title='faq']");
	public final By ELEMENT_FAQ_PORLET = By.xpath("//*[@id='Collaboration/FAQPortlet']//img");
	public final By ELEMENT_ANSWER_PAGE = By.id("UIPage");
	public final String ELEMENT_FAQ_CATEGORY = "//*[@id='UIFAQPortlet']//h5/*[text()='${cat}']";
	public final String ELEMENT_FAQ_QUESTION = "//*[@id='UIFAQPortlet']//a[text()='${quest}']";
	public final String ELEMENT_FAQ_ANSWER = "//*[@id='UIFAQPortlet']//*[text()='${ans}']";
	public final By ELEMENT_FAQ_HOME = By.linkText("FAQ home");
	public final By ELEMENT_FAQ_PORTLET_IN_PAGE = By.xpath("//*[@class='portletLayoutDecorator' and contains(text(), 'FAQPortlet')]");
	public final By ELEMENT_FAQ_PORTLET_DELETE = By.xpath("//*[text()='FAQPortlet']/../*[@data-original-title='Delete Portlet']");
	public final By ELEMENT_FAQ_PORTLET_EDIT = By.xpath("//*[text()='FAQPortlet']/../*[@data-original-title='Edit Portlet']");
	public final By ELEMENT_FAQ_PORTLET_H2 = By.xpath("//a[@class='TitleActionLink']");
	public final By ELEMENT_FAQ_EDIT_TEMPLATE_TAB = By.xpath("//button[contains(text(), 'Edit Template')]");
	public final By ELEMENT_FAQ_EDIT_TEMP_INPUT = By.id("ContentTemplate");
	public final By ELEMENT_FAQ_DISPLAY_CATEGORY_TAB = By.xpath("//button[contains(text(), 'Display Category')]");
	public final By ELEMENT_FAQ_PORLET_ICON = By.id("faq/local._faq.FAQPortlet");
	
	/*-----------------------Common function--------------------------------*/
	
	/**
	 * Create Answer and FAQ page at root path
	 * @author hakt
	 */
	public void createAnswerAndFAQPage()	{
		navTool = new NavigationToolbar(driver);
		page = new PageManagement(driver);
		
		Map<String, String> ANSWER_FQA_PORTLET_ID = new HashMap<String, String>();
		ANSWER_FQA_PORTLET_ID.put("faq/local._faq.AnswersPortlet", "faq/local._faq.FAQPortlet");

		info("--Go to intranet--");
		navTool.goToHomePage();
		navTool.goToPageCreationWizard();
		click(ELEMENT_UP_LEVEL);

		info("Create Answer&FAQ page");
		page.addNewPageEditor(DATA_ANSWER_FAQ_PAGE_NAME, DATA_ANSWER_FAQ_PAGE_NAME,"",CATEGORY_TITLE, 
				ANSWER_FQA_PORTLET_ID, false);	
	}

	public void goToFQAHome(){
		click(ELEMENT_FAQ_HOME);
		waitForElementNotPresent(ELEMENT_FAQ_HOME);
	}
	
	/** function add new Faq portlet in page if it is has not added
	 * @author lientm
	 */
	public void goToFaq(){
		navTool = new NavigationToolbar(driver);
		pageE = new PageEditor(driver);
		
		WebElement faq = waitForAndGetElement(ELEMENT_FAQ_VIEWER, 5000, 0);
		if (faq == null){
			navTool.goToEditPageEditor();
			click(By.linkText("Collaboration"));
			dragAndDropToObject(ELEMENT_FAQ_PORLET, ELEMENT_ANSWER_PAGE);
			waitForAndGetElement("//*[@class='portletLayoutDecorator' and contains(text(), 'FAQPortlet')]");
			info("drag and drop FAQ");
			pageE.finishEditLayout();
		}
	}
	
	/**function: check elements that existed in Faq
	 * @author lientm
	 * @param category
	 * @param question
	 * @param answer
	 */
	public void checkElementPresentInContentOfFaq(String category, String question, String answer){
		By element_category = By.xpath(ELEMENT_FAQ_CATEGORY.replace("${cat}", category));
		By element_question = By.xpath(ELEMENT_FAQ_QUESTION.replace("${quest}", question));
		By element_answer = By.xpath(ELEMENT_FAQ_ANSWER.replace("${ans}", answer));
		
		waitForAndGetElement(element_category);
		waitForAndGetElement(element_question);
		waitForAndGetElement(element_answer);
	}
	
	/**function: check elements that did not exist in Faq
	 * @author lientm
	 * @param category
	 * @param question
	 * @param answer
	 */
	public void checkElementNotPresentInContentOfFaq(String category, String question, String answer){
		By element_category = By.xpath(ELEMENT_FAQ_CATEGORY.replace("${cat}", category));
		By element_question = By.xpath(ELEMENT_FAQ_QUESTION.replace("${quest}", question));
		By element_answer = By.xpath(ELEMENT_FAQ_ANSWER.replace("${ans}", answer));
		
		waitForElementNotPresent(element_category);
		waitForElementNotPresent(element_question);
		waitForElementNotPresent(element_answer);
	}
	
	/**function delete faq portlet for page
	 * @author lientm
	 */
	public void deleteFaqPortlet(){
		pageE = new PageEditor(driver);
		navTool = new NavigationToolbar(driver);
		
		navTool.goToEditPageEditor();
		pageE.removePortlet(ELEMENT_FAQ_PORTLET_IN_PAGE, ELEMENT_FAQ_PORTLET_DELETE);
	}
	
	/**function go to edit faq portlet
	 * @author lientm
	 */
	public void goToEditFaqPortlet(){
		navTool = new NavigationToolbar(driver);
		pageE = new PageEditor(driver);
		
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FAQ_PORTLET_IN_PAGE);
		waitForAndGetElement(ELEMENT_FAQ_EDIT_TEMPLATE_TAB);
	}
	
	/**
	 * @author lientm
	 * @param category
	 * @param display
	 */
	public void settingDisplayCategoryScope(String category, boolean display){
		but = new Button(driver);
		click(ELEMENT_FAQ_DISPLAY_CATEGORY_TAB);
		if (display){
			check(ELEMENT_SELECT_DISPLAY_CHECKBOX.replace("${name}", category), 2);
		}else {
			uncheck(ELEMENT_SELECT_DISPLAY_CHECKBOX.replace("${name}", category), 2);
		}
		but.save();
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}
	
	/**
	 * @author lientm
	 * @param file
	 */
	public void settingFAQTemplate(String data){
		but = new Button(driver);
		click(ELEMENT_FAQ_EDIT_TEMPLATE_TAB);
		type(ELEMENT_FAQ_EDIT_TEMP_INPUT, data, false);
		but.save();
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}
}
