package org.exoplatform.selenium;

import static org.exoplatform.selenium.TestLogger.info;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * <li> This class provides simple way to manage basic modal dialogs 
 * by specifying a message, a title, an icon, and a message type 
 * or a type of option </li>
 * @author vuna2
 * @Date January, 30th, 2013
 *
 */
public class Dialog extends TestBase{

	public Dialog(WebDriver dr) {
		driver = dr;
	}

	public ManageAlert alt = new ManageAlert(driver);
	
	//Close Message
	public final String ELEMENT_MESSAGE_TEXT = "//li[@class='MessageContainer']/span[contains(@class, 'PopupIcon')]";
	public final String ELEMENT_MESSAGE_DIALOG_CLOSE_ICON_IE = ELEMENT_MESSAGE_TEXT + "/../../../../../..//a";
	public final String ELEMENT_MESSAGE_DIALOG_CLOSE_ICON = " //div[contains(@class, 'UIPopupWindow') and contains(@style, 'visibility: visible')]//a[contains(@class, 'uiIconClose')]";

	//ECMS > Admin > Repository > Manage Lock Tabs
	public final String MESSAGE_UNLOCK_WITHOUT_PERMISSION = "You do not have permission to unlock this node. Please contact the administrator to get the correct right.";
	public final String MESSAGE_CANNOT_DELETE_ADMIN_GROUP = "Cannot delete the group *:/platform/administrators.";

	//ECMS > Delete a document
	public final By ELEMENT_DELETE_IN_DIALOG = By.xpath("//*[contains(@class, 'uiAction')]//*[text()='Delete']");
	public final By ELEMENT_CANCEL_IN_DIALOG = By.xpath("//*[contains(@class, 'uiAction')]//*[text()='Cancel']");

	//Close message pop-up
	public void closeMessageDialog() {
		info("--Closing message dialog--");
		if (ieFlag) {
			click(ELEMENT_MESSAGE_DIALOG_CLOSE_ICON_IE);
		} else {
			click(ELEMENT_MESSAGE_DIALOG_CLOSE_ICON);
		}
		Utils.pause(1000);
	}

	//Delete Button in Dialog
	public void deleteInDialog(){
		waitForAndGetElement(ELEMENT_DELETE_IN_DIALOG);
		click(ELEMENT_DELETE_IN_DIALOG);
	}
}
