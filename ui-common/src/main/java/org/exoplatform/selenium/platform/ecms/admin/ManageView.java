package org.exoplatform.selenium.platform.ecms.admin;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author vuna2
 *
 */
public class ManageView extends EcmsBase{

	public ManageView(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	NavigationToolbar navToolbar = new NavigationToolbar(driver);
	Button button = new Button(driver);
	Dialog dialog = new Dialog(driver);
	ManageAlert alt = new ManageAlert(driver);
	ECMainFunction ecMain = new ECMainFunction(driver);

	//Settings tab
	public final By ELEMENT_SETTING_TAB = By.xpath("//*[contains(@class, 'popup')]//*[text()='Setting']");
	
	//Action tab
	public final By ELEMENT_ACTION_TAB = By.xpath("//*[contains(@class, 'popup')]//*[text()='Action']");
	public final String ELEMENT_EDIT_ACTION_ICON = "//*[contains(@class, 'popup')]//*[text()='${tabName}']/../..//*[@title='Edit']";
	public final By ELEMENT_SAVE_BUTTON_EDIT_VIEW_TAB = By.xpath("//*[text()='Add/Edit Tab']/.. /..//*[text()='Save']");
	
	//Permission tab
	public final By ELEMENT_ADDVIEW_PERMISSION_TAB = By.xpath("//*[text()='Permission']");
	public final By ELEMENT_ADDVIEW_PERMISSION_BUTTON = By.xpath("//*[@id='UIViewPermissionForm']//*[text()='Add']");

	/*function add a view 
	 * @view: name of view need add sub view
	 * @tab: name of tab on action bar that need add view
	 * @viewadd: list of sub view need add
	 */
	public void addView(String view, String tab, String viewadd ){
		ecMain.goToManageViews();
		click(By.xpath("//div[@title='" + view + "']/../..//*[@class='EditInfoIcon']"));
		click(By.xpath("//a[contains(text(),'" + tab + "')]"));
		selectCheckBoxList(viewadd);
		button.save();
		button.save();
	}

	/**
	 * Delete a view
	 * @param viewName : View Name
	 * @param confirmMessage : Confirm Message
	 */
	public void deleteView(String viewName, String confirmMessage, boolean verify){
		waitForTextPresent(viewName);
		click(By.xpath(ELEMENT_DELETE_VIEW.replace("${viewName}", viewName)));     
		alt.waitForConfirmation(confirmMessage);
		if(verify == true) waitForElementNotPresent(By.xpath(ELEMENT_EDIT_VIEW.replace("${viewName}", viewName)));
		Utils.pause(1000);
	}
	
	/**
	 * Edit View 
	 * @param viewName : View Name
	 */
	public void editView(String viewName){
		waitForElementPresent(By.xpath("//div[@class='Text' and contains(text(),'" + viewName + "')]"));
		By locator = By.xpath(ELEMENT_EDIT_VIEW.replace("${viewName}", viewName));
		click(locator);
		waitForElementPresent(ELEMENT_EDIT_VIEW_FORM);
	}

	/**
	 * @param anchor: Button's label to open form
	 * @param formTitle: Form's title   
	 */
	public void openAddViewForm(String anchor, String formTitle){
		//click(By.linkText(anchor));     
		click(By.xpath("//*[text()='" + anchor + "']"));
		waitForElementPresent(By.xpath("//*[contains(@class, 'popupTitle') and text()='" + formTitle + "']"));
	}

	/**
	 * 
	 * @param name : Template name
	 * @param template : Template view
	 */
	public void fillAddNewViewForm(String name, String template, String tabName, String property, Object...params) {
		boolean enableVersion = (Boolean) (params.length > 0 ? params[0] : false);
		boolean selectGroup = (Boolean) (params.length > 1 ? params[1] : false);
		String group =  (String) (params.length > 2 ? params[2] : "");
		String membership =  (String) (params.length > 3 ? params[3] : "");
		boolean selectUser = (Boolean) (params.length > 4 ? params[4] : false);
		String user =  (String) (params.length > 5 ? params[5] : "");
		
		//addTab
		click(ELEMENT_ACTION_TAB);
		click(button.ELEMENT_ADD_BUTTON);
		type(ELEMENT_TAB_NAME, tabName, true);
		selectCheckBoxList(property);
		click(ELEMENT_SAVE_BUTTON_EDIT_VIEW_TAB);
		waitForTextNotPresent("Tab Name");
		
		//Set permission
		click(ELEMENT_ADDVIEW_PERMISSION_TAB);
		if (selectGroup){
			selectMembership(group, membership, "Select Member");
		}
		if(selectUser){
			selectUser(user);
		}
		click(ELEMENT_ADDVIEW_PERMISSION_BUTTON);
		
		//Settings
		click(ELEMENT_SETTING_TAB);
		type(ELEMENT_TEMPLATE_VIEW_NAME, name, true);
		select(ELEMENT_TEMPLATE_VIEW, template);
		if (enableVersion){
			click(ELEMENT_ENABLE_VERSION, 2);
		}
		button.save();
		waitForElementPresent(ELEMENT_EDIT_VIEW.replace("${viewName}", name));
		Utils.pause(1000);
	}

	/**
	 * Fill data to Add New EXPLORER Template Form
	 * @param templateContent : Template Content
	 * @param templateName : Template Name
	 * @param templateType : Template Type
	 */
	public void fillExplorerTemplateForm(String templateContent, String templateName, String templateType){
		type(ELEMENT_EXPLORER_TEMPLATE_CONTENT, templateContent, true);
		type(ELEMENT_EXPLORER_TEMPLATE_NAME, templateName, true);
		select(ELEMENT_EXPLORER_TEMPLATE_TYPE, templateType);
		button.save();      
		waitForElementPresent(By.xpath("//div[@class='Text' and contains(text(),'" + templateName + "')]"));
	}
	
	/**
	 * Add tab to view
	 * @param tabname : Tab name
	 * @param property : Tab Property
	 */
	public void addTab(String tabname, String property){
		ecMain.goToManageViews();
		click(ELEMENT_EDIT_VIEW.replace("${viewName}", "Web"));
		click(ELEMENT_ACTION_TAB);
		click(button.ELEMENT_ADD_BUTTON);
		type(ELEMENT_TAB_NAME, tabname, true);
		selectCheckBoxList(property);
		button.save();
		waitForTextNotPresent("Tab Name");
		button.save();
		Utils.pause(1000);
	}

	/**
	 * Add an action to WCM View in Sites Explorer/Action Bar 
	 * @param viewAction
	 */
	public void setup2ShowViewAction(String viewAction){
		ecMain.goToManageViews();
		click(ELEMENT_EDIT_VIEW.replace("${viewName}", "Web"));
		click(ELEMENT_ACTION_TAB);
		click(ELEMENT_EDIT_ACTION_ICON.replace("${tabName}", "Authoring"));
		selectCheckBoxList(viewAction);
		click(ELEMENT_SAVE_BUTTON_EDIT_VIEW_TAB);
		button.save();
		Utils.pause(1000);
	}
	
	/**
	 * Create version for a view
	 * @param viewName : View Name
	 * @param versionNumber : Number of version that you want to create. It require 2 version at least
	 */
	public void createVersion(String viewName, int versionNumber){
		if(versionNumber < 2){
			assert false : ("Number of versions is required 2 at least");
		}
		for(int i = 0; i < versionNumber ; i++) {
			editView(viewName);
			if(getElement(ELEMENT_ENABLE_VERSION).isEnabled()) check(ELEMENT_ENABLE_VERSION);          
			button.save();
			Utils.pause(500);
		}
	}
	
	/**
	 * Restore a view to a certain version
	 * @param viewName : View Name
	 * @param orderVersion : Version that want to restore
	 */
	public void restoreVersion(String viewName, int orderVersion){
		editView(viewName);
		String order = "" + orderVersion;
		select(ELEMENT_VERSION_OPTION, order);
		click(By.linkText("Restore"));
		waitForElementPresent(By.xpath("//div[@class='Text' and contains(text(),'" + viewName + "')]/ancestor::tr//div[@class='Text' and text()='" + orderVersion + "']"));
	}

}
