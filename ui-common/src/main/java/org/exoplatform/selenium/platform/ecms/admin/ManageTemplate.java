package org.exoplatform.selenium.platform.ecms.admin;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author vuna2
 *
 */
public class ManageTemplate extends EcmsBase{

	public ManageTemplate(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	NavigationToolbar navToolbar = new NavigationToolbar(driver);
	Button button = new Button(driver);
	Dialog dialog = new Dialog(driver);
	ManageAlert alt = new ManageAlert(driver);
	ManageView magView = new ManageView(driver);
	
	//Manage Template    
	public By ELEMENT_TEMPLATE_LABEL = By.name("label");
	public By ELEMENT_TEMPLATE_NAME = By.name("name");
	public By ELEMENT_IS_DOCUMENT_TEMPLATE = By.id("isDocumentTemplate");  
	public By ELEMENT_DIALOG_TAB = By.xpath("//*[text()='Dialog']");
	public By ELEMENT_VIEW_TAB = By.xpath("//*[text()='View']");
	public By ELEMENT_CSS_TAB = By.xpath("//*[text()='CSS']");
	public By ELEMENT_TEMPLATE_FORM = By.xpath("//span[contains(text(),'Template Form')]");

	public By ELEMENT_SELECT_MEMBERSHIP = By.xpath("//img[@alt='Select Membership']");
	public By ELEMENT_SELECT_USERGROUP = By.id("SelectUserOrGroup");
	public By ELEMENT_SELECT_MEMBERSHIP_TYPE = By.linkText("platform");
	//public final By ELEMENT_SELECT_MEMBERSHIP_WEB_CONTRIBUTORS = By.linkText("web-contributors");

	//Open Add New Template Form
	public void openAddNewTemplateForm() {
		click(By.xpath("//*[text()='Add Template']"));
		waitForElementPresent(By.xpath("//*[contains(@class, 'PopupTitle') and text()='Template Form']"));	    
	}
	
	/**
	 * Fill data to Add New Template Form
	 * @param label: Template's label
	 * @param groupId: Group string is separate by slash, for example platform/web-contributors
	 * @param membership: Membership
	 */

	public void fillAddNewTemplateForm(String templateName, String templateLabel, String groupPath, String membership, Object... params) {   
		boolean isDocumentTemplate = (Boolean) (params.length > 0 ? params[0]: false);
		
		select(ELEMENT_TEMPLATE_NAME, templateName);
		type(ELEMENT_TEMPLATE_LABEL, templateLabel, false);
		if (isDocumentTemplate){
			click(ELEMENT_IS_DOCUMENT_TEMPLATE, 2);
		}
		selectMembership(groupPath,membership,"AddPermission");      
		//Switch between tabs
		click(ELEMENT_DIALOG_TAB);      
		click(ELEMENT_VIEW_TAB);
		click(ELEMENT_CSS_TAB);
		button.save();
		waitForElementPresent(By.xpath("//div[@class='Text' and contains(text(),'" + templateName + "')]"));    
		Utils.pause(1000);
	}

	//Delete a template 
	public void deleleTemplate(String templateName, String confirmMessage){
		By locator = By.xpath("//*[contains(text(),'" + templateName + "')]/../..//*[@class='uiIconDelete']");
		//click(locator);
		WebElement delete = driver.findElement(locator);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", delete);
		alt.waitForConfirmation(confirmMessage);
		waitForElementNotPresent(By.xpath("//div[@class='Text' and contains(text(),'" + templateName + "')]"));
		Utils.pause(1000);
	}
}