package org.exoplatform.selenium.platform.ecms.admin;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author vuna2
 *
 */
public class Permission extends EcmsPermission{

	public Permission(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}
	
	ManageAlert magAlert = new ManageAlert(driver);

	//Permission management screen
	public final String MESSAGE_INFO_DELETE_PERMISSION_SYSTEM = "You cannot remove the owner or the system user.";
	public final String ELEMENT_PERMISSION_MANAGEMENT_ICON = "//*[@title='${categoryName}']/../..//*[@class='uiIconEcmsViewPermissions']";
	public final By ELEMENT_READ_CHEKBOX = By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@id='read']");
	public final By ELEMENT_ADDNODE_CHECKBOX = By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@id='add_node']");
	public final By ELEMENT_SET_PRO_CHEKBOX = By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@id='set_property']");
	public final By ELEMENT_REMOVE_CHEKBOX = By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@id='remove']");
	public final By ELEMENT_SELECT_USER_IN_PERMISSION_MANAGEMENT = By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@title='Select User']");
	public final By ELEMENT_SELECT_MEMBERSHIP_IN_PERMISSION_MANAGEMENT = By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@title='Select Membership']");
	public final By ELEMENT_SELECT_EVERYONE_IN_PERMISSION_MANAGEMENT = By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@title='Select Everyone']");
	public final By ELEMENT_SAVE_BUTTON_IN_PERMISSION_MANAGEMENT = By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[contains(text(),'Save')]");

	//public final String ELEMENT_EDIT_USER_PERMISSION = "//*[@title='${userOrGroupName}']/../..//*[@class='uiIconEdit']";
	//public final String ELEMENT_EDIT_USER_PERMISSION_AUX = "//div[@id='UITabContent' and @style='display: block;;']//*[@title='${userOrGroupName}']/../..//*[@class='uiIconEdit']";
	public String ELEMENT_DELETE_SELECTED_GROUP = "//*[contains(text(), '${selectedGroup}')]/../..//*[contains(@class, 'uiIconDelete')]";
	
	/*============== Explorer/Tags Permission Manager ================*/
	/**
	 * Remove permission 
	 * @param groupPath Group string separated by slash
	 * @param membership Membership 
	 */
	public void removeTagPermission(String groupPath,String membership) {
		String str = membership + ":/" + groupPath;
		//By locator = By.xpath("//*[contains(text(),'" + str + "')]/ancestor::tr//*[@class='uiIconDelete']");
		By permission = By.xpath("//*[contains(text(),'" + str + "')]");
		click(ELEMENT_DELETE_SELECTED_GROUP.replace("${selectedGroup}", str));
		//magAlert.waitForConfirmation("Are you sure you want to delete this permission?");
		magAlert.acceptAlert();
		waitForElementNotPresent(permission);
		Utils.pause(1000);
	}

	/*============== Advanced/Categories ============*/
	//Function to set permission for user on manage category screen
	public void setPermissionForUserOnManageCategory(boolean selectUser, String user, boolean selectMembership, String groupID, String membership,
			boolean read, boolean add, boolean set, boolean remove){
		info("Set permission for category with user " + user);
		if (!selectUser && !selectMembership){
			click(ELEMENT_SELECT_EVERYONE_IN_PERMISSION_MANAGEMENT);
		}
		else if (selectUser){
			click(ELEMENT_SELECT_USER_IN_PERMISSION_MANAGEMENT);
			if (isTextPresent("Add permissions to this node")){
				if (isElementPresent(By.xpath("//*[@title='" + user + "']/../..//*[@class='SelectPageIcon']"))){
					click(By.xpath("//*[@title='" + user + "']/../..//*[@class='SelectPageIcon']"));
				}else if (isElementPresent(By.xpath("//*[@data-original-title='" + user + "']/../..//*[contains(@class, 'uiIconPlus')]"))){
					click(By.xpath("//*[@data-original-title='" + user + "']/../..//*[contains(@class, 'uiIconPlus')]"));
				} 
			}
			else if (isTextPresent("Add permission to that node")){
				if (isElementPresent(By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@title='" + user + "']/../..//*[@class='SelectPageIcon']"))){
					click(By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@title='" + user + "']/../..//*[@class='SelectPageIcon']"));
				}else if (isElementPresent(By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@data-original-title='" + user + "']/../..//*[contains(@class, 'uiIconPlus')]"))){
					click(By.xpath("//div[@id='UITabContent' and @style='display: block;;']//*[@data-original-title='" + user + "']/../..//*[contains(@class, 'uiIconPlus')]"));
				}
			}	
		}
		else if (selectMembership){
			click(ELEMENT_SELECT_MEMBERSHIP_IN_PERMISSION_MANAGEMENT);
			userGroup.selectGroup(groupID);
			click(By.linkText(membership));
		}
		Utils.pause(500);
		info("Set read permission for user");
		if (read){
			click(ELEMENT_READ_CHEKBOX, 2);
			//((JavascriptExecutor) driver).executeScript("document.getElementById('read').click();");
		}
		if (add){
			click(ELEMENT_ADDNODE_CHECKBOX, 2);
			//((JavascriptExecutor) driver).executeScript("document.getElementById('add_node').click();");
		}
		/*if (set){
			click(ELEMENT_SET_PRO_CHEKBOX, 2);
		}*/
		if (remove){
			click(ELEMENT_REMOVE_CHEKBOX, 2);
		}
		if (read || add || set || remove){
			click(ELEMENT_SAVE_BUTTON_IN_PERMISSION_MANAGEMENT);
			//((JavascriptExecutor) driver).executeScript("document.getElementById('remove').click();");
		}else{
			info("Input permission is wrong");
		}
		Utils.pause(1000);
	}

	//Edit a permission for user or group
	public void editPermissionUserOrGroup(String userOrGroupName, boolean read, boolean add, boolean set, boolean remove){
		info("-- Edit a permission for user/group: "+ userOrGroupName +" --");

		waitForTextPresent("Permission Management");
		if (isTextPresent("Add permissions to this node")){
			click(ELEMENT_EDIT_USER_PERMISSION.replace("${userOrGroupName}", userOrGroupName));
		}else if (isTextPresent("Add permission to that node")){
			click(ELEMENT_EDIT_USER_PERMISSION_AUX.replace("${userOrGroupName}", userOrGroupName));
		}
		Utils.pause(500);
		if (read){
			click(ELEMENT_READ_CHEKBOX, 2);
		}
		if (add){
			click(ELEMENT_ADDNODE_CHECKBOX, 2);
		}
		/*if (set){
			click(ELEMENT_SET_PRO_CHEKBOX);
		}*/
		if (remove){
			click(ELEMENT_REMOVE_CHEKBOX, 2);
		}
		if (read || add || set || remove){
			click(ELEMENT_SAVE_BUTTON_IN_PERMISSION_MANAGEMENT);
		}
		Utils.pause(1000);
	}
}
