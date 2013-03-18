package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;

/**
 * Migrate to PLF 4
 * @author vuna2
 *
 */
public class Template extends BasicAction{

	//Dialog dialog = new Dialog(driver);
	//Button button = new Button(driver);
	
	/** Add a wiki page from template
	 * @author thuntn
	 * @param title
	 * @param content
	 * @param mode: mode =1: edit a wiki page in richtext
	 * mode =0 : edit a wiki page in source editor
	 * @param template
	 */
	public void addWikiPageFromTemplate(String title, int mode, String template)
	{
		By eTemplate = By.xpath(ELEMENT_SELECT_TEMPLATE_LINK.replace("{$template}",template));
		goToAddTemplateWikiPage();	
		info("--Add a wiki page from template--");
		click(eTemplate, 2);
		click(button.ELEMENT_SELECT_BUTTON);
		if (mode == 1){ 

			addWikiPageRichText(title, null);
		}
		else
			addWikiPageSourceEditor(title, null);

		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		waitForElementNotPresent(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		//save();
		//waitForElementNotPresent(ELEMENT_SAVE_BUTTON);
	}

	/**Add a wiki page template
	 * @author thuntn
	 * @param title
	 * @param description
	 * @param content
	 */
	public void addTemplate(String title, String description, String content){
		dialog = new Dialog(driver);
		if (isElementNotPresent(ELEMENT_TEMPLATE_LINK)){
			goToTemplateManagement();
		}
		info("--Add a wiki page template--");
		click(ELEMENT_ADD_TEMPLATE_LINK);
		type(ELEMENT_TITLE_TEMPLATE_INPUT,title,true);
		Utils.pause(1000);
		type(ELEMENT_DESC_TEMPLATE_INPUT,description,true);
		Utils.pause(1000);
		type(ELEMENT_CONTENT_TEMPLATE_INPUT,content,true);
		click(ELEMENT_SAVE_TEMPLATE_INPUT);
		waitForMessage(MSG_CREATE_TEMPLATE);
		dialog.closeMessageDialog();
		waitForElementNotPresent(ELEMENT_SAVE_TEMPLATE_INPUT);
	}
	
	/**
	 *Edit a wiki page template
	 * @author thuntn
	 * @param oldTitle: old title of a template
	 * @param title: new title
	 * @param description: new description
	 * @param content: new content
	 * If you don't want to edit any field, you can pass null value to the respective parameter
	 */
	public void editTemplate(String oldTitle, String title, String description, String content){

		goToTemplateManagement();

		info("--Edit a wiki page template--");
		click(ELEMENT_EDIT_TEMPLATE_ICON.replace("{$template}", oldTitle));
		if (title != null)
			type(ELEMENT_TITLE_TEMPLATE_INPUT,title,true);
		if (description != null)
			type(ELEMENT_DESC_TEMPLATE_INPUT,description,true);
		if (content != null)
			type(ELEMENT_CONTENT_TEMPLATE_INPUT,content,true);
		click(ELEMENT_SAVE_TEMPLATE_INPUT);

		waitForElementNotPresent(ELEMENT_SAVE_TEMPLATE_INPUT);
	}
	
	/** Delete a wiki page template
	 * @author thuntn
	 * @param title
	 */
	public void deleteTemplate(String title){
		magAlert = new ManageAlert(driver);
		goToTemplateManagement();
		info("--Delete a wiki page template--");
		click(ELEMENT_DELETE_TEMPLATE_ICON.replace("{$template}", title));
		magAlert.waitForConfirmation(MSG_DELETE_TEMPLATE);
		waitForElementNotPresent(ELEMENT_DELETE_TEMPLATE_ICON.replace("{$template}", title));
		Utils.pause(500);
	}
	
	/** Search for templates
	 * @author thuntn
	 * @param keyword
	 */
	public void searchTemplate(String keyword){
		type(ELEMENT_SEARCH_TEMPLATE_INPUT, keyword, true);
		click(ELEMENT_SEARCH_BUTTON);
		Utils.pause(1000);
	}
}
