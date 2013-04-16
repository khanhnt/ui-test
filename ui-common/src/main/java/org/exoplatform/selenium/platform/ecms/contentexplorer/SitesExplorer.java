package org.exoplatform.selenium.platform.ecms.contentexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author vuna2
 *
 */
public class SitesExplorer extends EcmsBase{

	public SitesExplorer(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	Dialog dialog = new Dialog(driver);
	Button button = new Button(driver);
	NavigationToolbar navToolBar = new NavigationToolbar(driver);
	ActionBar actBar = new ActionBar(driver);
	ManageAlert magAlert = new ManageAlert(driver);
	ECMainFunction ecMain = new ECMainFunction(driver);
	ManageDriver magDrv = new ManageDriver(driver);
	EcmsBase ecms = new EcmsBase(driver);

	//Button on the top-bar menu
	public final By ELEMENT_BUTTON_BACK_PREVIOUS_NODE = By.className("uiIconEcmsGoBack");
	public final By ELEMENT_BUTTON_REFRESH_TOPBAR_MENU = By.className("uiIconRefresh");

	//Simple Search
	public final By ELEMENT_SIMPLESEARCH_TEXTBOX = By.id("simpleSearch");
	public final By ELEMENT_SIMPLESEARCH_SUBMIT = By.id("SimpleSearch");

	/* sidebar */
	public final By ELEMENT_SIDEBAR_SITES_MANAGEMENT = By.xpath("//*[@data-original-title = 'Sites Management']");
	//File Explorer - relation -clipboard - tag clould - saved search
	public final By ELEMENT_TAG_CLOUD = By.xpath("//*[@data-original-title = 'Tag Cloud']");
	public final By ELEMENT_SIDEBAR_FILE_EXPLORER = By.xpath("//*[@data-original-title = 'File Explorer']");
	//public final By ELEMENT_FILE_EXPLORER_MINI_ICON = By.className("uiIconEcmsExplorerMini");

	public final By ELEMENT_CLIPBOARD_ICON = By.xpath("//*[@data-original-title = 'Clipboard']");
	public final By ELEMENT_CLEAR_ALL_ICON = By.xpath("//*[contains(text(),'Clear All')]");
	public final By MESSAGE_CLEAR_ALL = By.xpath("//*[contains(text(),'There are no items in the clipboard.')]");
	public final String ELEMENT_VERIFY_ACTION = "//*[contains(@data-original-title, '${titleOfFile}')]/../..//*[contains(@class, 'uiIconEcmsPaste')]";
	public final String ELEMENT_TITLE_LEFT_PANEL = "//div[@id='UITreeExplorer']//div[contains(@onmousedown,'collaboration:/sites/${title}')]";


	/*================***==================*/

	//Verify if Driver is present
	public boolean isDriverPresent(String driverName){
		return getElement(By.xpath("//*[@class = 'driveLabel' and @title='" + driverName + "']")) != null;	
	}

	public boolean isDriverNotPresent(String driverName) {
		return !isDriverPresent(driverName);
	}

	//Create a driver in Sites Explorer
	public void createDriverInSitesExplorer(String driverName, String driverWorkspace, String driverHomePath, String groupPermission, 
			String member, String viewOption, String view){
		if (isTextNotPresent(driverName)){
			Utils.pause(1000);
			ecMain.goToManageDriver();
			magDrv.addNewDrive(driverName, driverWorkspace, driverHomePath, groupPermission, member, viewOption, view);	
			navToolBar.goToSiteExplorer();
			Utils.pause(1000);
			if (isTextNotPresent(driverName)){
				click(ecms.ELEMENT_SHOW_DRIVES);
			}
		}else{
			info("Driver " + driverName + " already exists");
		}	
		Utils.pause(1000);
	}

	//Delete a tag
	public void deleteTag(String[] tagName){
		for(int i = 0; i < tagName.length ; i++){
			info("-- Deleting the tag: " + tagName[i]);
			By ELEMENT_REMOVE_TAG = By.xpath(REMOVE_TAG.replace("${TagsName}", tagName[i]));
			if (isElementPresent(ELEMENT_REMOVE_TAG)){
				click(ELEMENT_REMOVE_TAG);
			}else if (isElementPresent(By.xpath("//*[text()='Edit Tag']/../..//*[text()='"+ tagName[i] +"']/../..//*[@data-original-title='Remove Tag']"))){
				click(By.xpath("//*[text()='Edit Tag']/../..//*[text()='"+ tagName[i] +"']/../..//*[@data-original-title='Remove Tag']"));
			}
			//magAlert.waitForConfirmation(MESSAGE_WARNING_AFTER_DELETE_TAG);
			magAlert.acceptAlert();
		}
		click(button.ELEMENT_CLOSE_WINDOW);
		waitForTextNotPresent(tagName[0]);
	}

	//Edit a public tag
	public void goToEditTag(){
		info("-- Go to Edit Tags Form --");
		if (isElementNotPresent(ELEMENT_SIDEBAR_FILE_EXPLORER)){
			navToolBar.goToSiteExplorer();     
		}
		waitForElementPresent(ELEMENT_TAG_CLOUD);
		click(ELEMENT_TAG_CLOUD);
		if (isElementPresent(ELEMENT_EDIT_TAGS)){
			//click(ELEMENT_EDIT_TAGS);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", ELEMENT_EDIT_TAGS);
		}else if (isElementPresent(By.xpath("//*[@data-original-title = 'Edit Tags']"))){
			//click(By.xpath("//*[@data-original-title = 'Edit Tags']"));
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", By.xpath("//*[@data-original-title = 'Edit Tags']"));
		}
		click(By.xpath("//*[@id='UITagExplorer']//*[contains(@class, 'uiIconEdit')]"));
		waitForElementPresent(ELEMENT_EDIT_TAGS_FORM);
	}

	//Add tag for a node
	public void addTagForNode(String[] tagName) {
		info("-- Open a Tag Form --");
		if (isElementPresent(By.className("uiIconEcmsTaggingDocument"))){
			click(By.className("uiIconEcmsTaggingDocument"));
		}else {
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			click(By.className("uiIconEcmsTaggingDocument"));
		}

		for(int i = 0; i < tagName.length ; i++){
			// Input information
			type(ELEMENT_TAG_NAME, tagName[i], true);
			click(button.ELEMENT_ADD_BUTTON);
			//Verify new tag
			waitForElementPresent(By.xpath("//*[text()='Linked Tags:']/..//*[contains(text(), '"+ tagName[i] +"')]"));
		}
		
		//Close
		click(button.ELEMENT_CLOSE_BUTTON);

		//Verify added new tag
		click(ELEMENT_TAG_CLOUD);
		waitForTextPresent(tagName[0]);
	}

	//Simple search
	public boolean simpleSearch(String keyword){
		waitForElementPresent(ELEMENT_SIMPLESEARCH_TEXTBOX);
		type(ELEMENT_SIMPLESEARCH_TEXTBOX, keyword, true);
		Utils.pause(1000);
		click(ELEMENT_SIMPLESEARCH_SUBMIT);
		if (waitForAndGetElement(By.xpath("//*[contains(text(),'"+ keyword +"')]"), 30000, 1) != null){
			return true;
		}
		return false;
	}
}
