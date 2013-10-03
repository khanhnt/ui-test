package org.exoplatform.selenium.platform;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * 
 * @author lientm
 * @date 28 Aug 2013
 */
public class PlatformPermission extends PlatformBase {

	public Map<String, String> group ;
	UserGroupManagement userGroup;

	public PlatformPermission(WebDriver dr){
		driver = dr;
		userGroup = new UserGroupManagement(driver);
		group = new HashMap<String, String>();
		group.put("Development", "developers");
		group.put("Content Management", "web-contributors");
		group.put("Administration", "administrators");
	}

	//set permission screen
	public final String ELEMENT_USER_CHECKBOX = "//*[text()='${user}']/../..//*[@type='checkbox']"; 
	public final By ELEMENT_SELECT_THIS_GROUP = By.linkText("Select this Group");
	public final By ELEMENT_SEARCH_ICON = By.xpath("//a[@title='Quick Search']");
	public final By ELEMENT_SEARCH_USER_INPUT = By.id("Quick Search");
	public final By ELEMENT_QUICK_SEARCH_BUTTON = By.xpath("//a[@data-original-title='Quick Search']");
	public final By ELEMENT_SELECT_SEARCH = By.name("filter");
	public final By ELEMENT_PERMISSION_TAB = By.linkText("Permissions");
	public final By ELEMENT_PERMISSION_INPUT = By.id("UIPermissionInput");
	public final By ELEMENT_SELECT_USER_ICON = By.xpath("//*[@id='PermissionTab']//*[@class='uiIconUser uiIconLightGray']");
	public final By ELEMENT_SELECT_MEMBERSHIP_ICON = By.xpath("//*[@id='PermissionTab']//*[@class='uiIconMembership uiIconLightGray']");
	public final By ELEMENT_SELECT_GROUP_ICON = By.xpath("//*[@id='PermissionTab']//*[@class='uiIconGroup uiIconLightGray']");
	public final By ELEMENT_ADD_USERS_BUTTON = By.xpath("//*[@id='UIUserSelector']//*[text()='Add']");
	public final By ELEMENT_SELECT_USER_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Select User']");
	public final By ELEMENT_SELECT_GROUP_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Select Group']");
	public final By ELEMENT_SELECT_ROLE_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Select Role']");

	/*-----------------Set permission for wiki, forum...------------------*/
	/**function: select a user when set permission for a element
	 * @author lientm
	 * @param element: id of element need set permission
	 * @param user: user that needs to set permission
	 */
	public void selectUserPermission(String user, int...type){	
		String[] temp = user.split("/");
		if (temp.length > 0){
			for (int i = 0; i < temp.length; i ++){
				By ELEMENT_USER = By.xpath(ELEMENT_USER_CHECKBOX.replace("${user}", temp[i]));
				By ELEMENT_FIRST = By.xpath("//*[@id='UIListUsers']/tbody/tr[1]//*[text()='" + temp[i] + "']");
				By ELEMENT_SECOND = By.xpath("//*[@id='UIListUsers']/tbody/tr[2]");
				if (type.length > i){
					type(ELEMENT_SEARCH_USER_INPUT, temp[i], true);
					switch (type[i]){
					case 1:
						select(ELEMENT_SELECT_SEARCH, "User Name");
						break;
					case 2:	
						select(ELEMENT_SELECT_SEARCH, "First Name");
						break;
					case 3:	
						select(ELEMENT_SELECT_SEARCH, "Last Name");
						break;
					case 4:	
						select(ELEMENT_SELECT_SEARCH, "Email Name");
						break;
					default:
						break;
					}
					click(ELEMENT_QUICK_SEARCH_BUTTON);
					waitForAndGetElement(ELEMENT_FIRST);
					waitForElementNotPresent(ELEMENT_SECOND);
				}
				check(ELEMENT_USER, 2);
			}
		}
		click(ELEMENT_ADD_USERS_BUTTON);
	}

	/**function: select a group when set permission
	 * @author lientm
	 * @param element: id of element need set permission
	 * @param grouppath: group path
	 */
	public void selectGroupPermission(String grouppath){
		userGroup = new UserGroupManagement(driver);
		userGroup.selectGroup(grouppath);
		if(waitForAndGetElement(ELEMENT_SELECT_THIS_GROUP, DEFAULT_TIMEOUT,0) != null)
			click(ELEMENT_SELECT_THIS_GROUP);
	}

	/**function: select group and membership when set permission
	 * @author lientm
	 * @param element: id of element need set permission
	 * @param groupPath: path to group
	 * @param membership
	 */
	public void selectGroupMembership(String groupPath, String membership){
		userGroup = new UserGroupManagement(driver);
		userGroup.selectGroup(groupPath);	
		WebElement elementMembership = waitForAndGetElement("//*[contains(text(), '" + membership + "')]", 5000, 0);
		WebElement elementMembership_1 = waitForAndGetElement(By.linkText(membership), 5000, 0);
		//WebElement elementMembership_2 = waitForAndGetElement("//*[@title='" + membership + "']", 5000, 0);
		if (elementMembership_1 != null){
			//click(elementMembership_1);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", elementMembership_1);
		}else {
			click(elementMembership);
		}
		Utils.pause(1000);
	}
}