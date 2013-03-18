package org.exoplatform.selenium.platform.ecms.contentexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * 
 * @author vuna2
 *
 */
public class ContextMenu extends EcmsBase{

	public ContextMenu(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}
	
	Dialog dialog = new Dialog(driver);
	Button button = new Button(driver);

	public final By ELEMENT_MENU_LOCK = By.xpath("//*[@class='uiIconEcmsLock']");
	public final By ELEMENT_MENU_UNLOCK = By.xpath("//*[@class='uiIconEcmsUnlock']");
	public final By ELEMENT_MENU_CHECKIN = By.xpath("//*[@class='uiIconEcmsCheckIn']");
	public final By ELEMENT_MENU_CHECKOUT = By.xpath("//*[@class='uiIconEcmsCheckOut']");
	public final By ELEMENT_MENU_RENAME_NODE = By.linkText("Rename");
	public final By ELEMENT_MENU_PASTE = By.xpath("//a[contains(text(),'Paste')]");
	public final By ELEMENT_MENU_DELETE = By.className("uiIconEcmsDelete");
	public final By ELEMENT_MENU_EDIT_ITEM = By.xpath("//a[@class='ItemIcon DefaultAction16x16Icon EditDocument16x16Icon']");
	public final By ELEMENT_MENU_ADD_SYMLINK = By.className("uiIconEcmsAddSymLink");

	public final String ELEMENT_FILE_LOCKED_BY_ADMIN = "//*[@data-original-title = '${titleOfFile} (Locked by john)']";
	public final String ELEMENT_FILE_TITLE = "//*[@title = '${titleOfFile}']";
	public final String ELEMENT_FILE_TITLE_AUX = "//a[@title='${title1}']/following::a[@title='${title2} "+"']";
	public final By ELEMENT_DOCUMENT = By.linkText("documents");
	public final By ELEMENT_UNLOCK_ARTICLE = By.className("uiIconUnLockMini");
	
	public final String WARNING_MESSAGE_CANNOT_PASTE = "Cannot paste the copied node type on the current node.";
	public final String ELEMENT_VERIFY = "//*[text()='${destination}']/../../../../*//*[text()='${source}']";

	/*=========================================================*/

	/**
	 * Define a type of action (on NODE) 
	 * @author vuna2
	 *
	 */
	public enum actionType{
		COPY, CUT, DELETE, PASTE, LOCK, UNLOCK, CHECKIN, CHECKOUT, RENAME, SYMLINK;
	}

	/**
	 * 
	 * @param locator: of NODE
	 * @param action: COPY, CUT, PASTE, LOCK, UNLOCK, CHECK IN, CHECK OUT, RENAME NODE
	 * @author vuna2
	 */
	public void contextMenuAction(Object locator, actionType action, Object... params){
		By loc = locator instanceof By ? (By)locator : By.xpath((String)locator);
		String nodeName = (String) (params.length > 0 ? params[0] : "");
		Utils.pause(1000);
		//for(int repeat=0;; repeat ++)
		//{
		//	if (repeat >= ACTION_REPEAT) {
		//		Assert.fail("Cannot perform this action after " + ACTION_REPEAT + " tries");
		//	}
		rightClickOnElement(loc);
		switch (action) {
		case COPY:
			if (waitForAndGetElement(ELEMENT_COPY_NODE, 5000, 1) != null) {
				click(ELEMENT_COPY_NODE);
				break;
			}
		case CUT:
			if (waitForAndGetElement(ELEMENT_CUT_NODE, 5000, 1) != null) {
				click(ELEMENT_CUT_NODE);
				break;
			}
		case PASTE:
			if (waitForAndGetElement(ELEMENT_PASTE_NODE, 5000, 1) != null) {
				click(ELEMENT_PASTE_NODE);
				break;
			}	
		case LOCK:
			if (waitForAndGetElement(ELEMENT_MENU_LOCK, 5000, 1) != null) {
				click(ELEMENT_MENU_LOCK);
				break;
			}
		case UNLOCK:
			if (waitForAndGetElement(ELEMENT_MENU_UNLOCK, 5000, 1) != null) {
				click(ELEMENT_MENU_UNLOCK);
				break;
			}
		case CHECKIN:
			if (waitForAndGetElement(ELEMENT_MENU_CHECKIN, 5000, 1) != null){
				click(ELEMENT_MENU_CHECKIN);
				info("Node is checked in successfully");
				break;
			}
		case CHECKOUT:
			if (waitForAndGetElement(ELEMENT_MENU_CHECKOUT, 5000, 1) != null){
				click(ELEMENT_MENU_CHECKOUT);
				info("Node is checked out successfully");
				break;
			}
		case RENAME:
			if (waitForAndGetElement(ELEMENT_MENU_RENAME_NODE, 5000, 1) != null){
				click(ELEMENT_MENU_RENAME_NODE);
				type(ELEMENT_INPUT_RENAME_NODE, nodeName, true);
				button.rename();
				waitForTextPresent(nodeName);
				info("Node is renamed successfully");
				break;
			}
		case SYMLINK:
			if (waitForAndGetElement(ELEMENT_MENU_ADD_SYMLINK, 5000, 1) != null){
				click(ELEMENT_MENU_ADD_SYMLINK);
				info("Add Symlink is checked successfully");
				break;
			}
		default:
			break;
		}
		Utils.pause(WAIT_INTERVAL);
		//	info("Retry...[" + repeat + "]");
		//}
	}

	//Check node is being locked
	public boolean isLockedNode(Object locator){
		boolean locked=false;
		By by = locator instanceof By ? (By)locator : By.xpath((String)locator);
		//		actions.contextClick(unlock).build().perform();
		try{
			rightClickOnElement(by);
			Utils.pause(500);
			if (isElementPresent(ELEMENT_MENU_UNLOCK)) {
				locked = true;
			} else 
				locked =false;

			WebElement unlock = waitForAndGetElement(by);
			unlock.sendKeys(Keys.RETURN);
		} catch(StaleElementReferenceException e){
			checkCycling(e, 10);
			isLockedNode(locator);
		} finally{			
			loopCount=0;			
		}
		return locked;
	}

	/*//Rename a node
	public void renameNode(By nodePath, String newName, By nodePathNew){
		rightClickOnElement(nodePath);
		click(By.xpath("//a[contains(text(),'Rename')]"));
		type(By.id("titleField"),newName,true);
		type(By.id("nameField"),newName,true);
		click(ELEMENT_SAVE_BUTTON);
		//check rename successfully
		waitForElementPresent(nodePathNew);
		assert isElementPresent(nodePathNew):"Rename node unsuccessfully";
		info("Rename node successfully");
	}*/

	//Copy and paste a node
	public void copyAndPasteNode(Object source, Object target){
		//goToNode(source);
		copyNode(source);
		//goToNode(target);
		pasteNode(target);
		Utils.pause(1000);
	}

	//Cut and paste a node
	public void cutAndPasteNode(Object source, Object target){
		cutNode(source);
		pasteNode(target);
	}

	//Delete a node at level 1
	public void deleteDocument(Object locator, int...timeout){
		int iTimeout = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
		for(int repeat=1;; repeat ++)
		{
			if (repeat > ACTION_REPEAT) {
				Assert.fail("Cannot perform this action after " + ACTION_REPEAT + " tries");
			}
			rightClickOnElement(locator);
			info("Before displaying " + repeat);
			if (waitForAndGetElement(ELEMENT_MENU_DELETE, 10000, 0)!=null) 
			{	
				info("Element is displayed ");
				click(ELEMENT_MENU_DELETE);
				waitForTextPresent("Delete");
				//click(By.linkText("OK"));
				dialog.deleteInDialog();
				break;
			}
			info("Retry...[" + repeat + "]");

		}
		//waitForElementNotPresent(By.linkText("OK"));
		//waitForTextNotPresent("Delete");
		if (isElementPresent(locator)){
			click(button.ELEMENT_REFRESH_BUTTON);
			Utils.pause(1000);
		}
		waitForElementNotPresent(locator, iTimeout);
		info(locator.toString() + " is deleted successfully");		
		Utils.pause(1000);
	}

	//Delete data
	public void deleteData(By data){
		goToNode(data);
		deleteDocument(data);
		Utils.pause(1000);
		waitForElementNotPresent(data);
	}
}
