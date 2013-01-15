/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.UserGroupManagement;

import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by The eXo Platform SAS Author : Hoang Manh Dung
 * dunghm@exoplatform.com Nov 7, 2012
 */
public class SpaceManagement extends SocialBase {
	
	UserGroupManagement userGroup = new UserGroupManagement(driver);
	
	//Go to My Spaces	> 
	//Add space Form
	protected  int DEFAULT_TIMEOUT = 60000;
	public final By     ELEMENT_ADDNEWSPACE_BUTTON      = By.xpath("//button[@data-original-title='Add New Space']");
			//("//a[@class='AddSpaceIcon']");

	public final By     ELEMENT_ADDNEWSPACE_FORM        = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Add New Space']");
			//("//span[@class='PopupTitle' and text()='Add New Space']");

	public final By     ELEMENT_SPACE_NAME_INPUT        = By.xpath("//input[contains(@name,'displayName')]");

	public final By     ELEMENT_SPACE_DESCRIPTION_INPUT = By.xpath("//textarea[contains(@name,'description')]");

	public final By     ELEMENT_ACCESS_TAB              = By.xpath("//*[text()='Access & Edit']");
			//("//div[contains(@class,'MiddleTab') and text()='Access & Edit']");

	public final By     ELEMENT_USER_GROUP_TAB          = By.xpath("//*[text()='Invite users from group']");
			//("//div[contains(@class,'MiddleTab') and text()='Invite users from group']");


	public final By     ELEMENT_USER_GROUP_CHECKBOX     = By.xpath("//*[@id='useExistingGroup']");

	//Edit space
	public final By     ELEMENT_SPACE_SETTING_TAB       = By.xpath("//div[contains(@class,'MiddleTab') and text()='Settings']");

	public final By     ELEMENT_CHANGE_AVATAR_ICON      = By.xpath("//a[contains(@class,'ChangeAva')]");  

	public final String MESSAGE_DELETE_SPACE            = "Cannot undo one deleted space with all its page navigations and group. Are you sure to delete this space?";
			//"Are you sure to delete this space? This can not be undone. All page navigations and this group will be deleted, too.";

	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Click on a button with a specific label
	 * 
	 * @param label : Button label
	 */
	public void clickButton(String label) {
		By button = By.xpath("//div[@class='uiAction']/a[text()='" + label + "']");
				//("//*[contains(@class,'ActionButton') and text()='" + label + "']");
		waitForElementPresent(button);
		click(button);
	}

	/**
	 * Switch to a tab
	 * 
	 * @param label : Tab label
	 */
	public void switchTabs(By tab) {
		waitForElementPresent(tab);
		click(tab);
	}
	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Do a action on space such as Edit, delete, ...
	 * 
	 * @param action : Action name 
	 * @param spaceName : Space name
	 */
	/*public void doAction(String action, String spaceName){
		By actionLink = By.xpath("//a[text()='" + spaceName + "']/../../../div/button[text()='" + action + "']");
				//("//a[text()='" + spaceName + "']/ancestor::div[contains(@class,'ContentBox')]//a[text()='" + action + "']");
	public void doAction(String action, String spaceName){
		By actionLink = By.xpath("//a[text()='" + spaceName + "']/ancestor::div[contains(@class,'ContentBox')]//a[text()='" + action + "']");
	/*public void doAction(String action, String spaceName){
		By actionLink = By.xpath("//a[text()='" + spaceName + "']/../../../div/button[text()='" + action + "']");
				//("//a[text()='" + spaceName + "']/ancestor::div[contains(@class,'ContentBox')]//a[text()='" + action + "']");
		click(actionLink);
	}*/

	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Create quickly a new space
	 * 
	 * @param name : Space name
	 * @param desc : Space description
	 * 
	 */
	public void addNewSpace(String name, String desc, int... params) {
		int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT; 

		click(ELEMENT_ADDNEWSPACE_BUTTON);
		waitForElementPresent(ELEMENT_ADDNEWSPACE_FORM);
		type(ELEMENT_SPACE_NAME_INPUT, name, true);
		type(ELEMENT_SPACE_DESCRIPTION_INPUT, desc, true);
		clickButton("Create");
		waitForTextPresent(name, iTimeout);
		//waitForElementPresent(By.xpath("//div[contains(@class,'UISpaceName')]/a[@title='" + name + "']"),iTimeout);
		pause(1000);
	}

	/**
	 * Update by @author vuna2
	 * -- @param visibility
	 * -- @param registration
	 */
	/**
	 * Create a new space
	 * 
	 * @param name : Space name
	 * @param desc : Space description
	 * @param visibility : Space visibility
	 * @param registration : Space registration
	 * @param groupPath : User Group
	 * @param childGroupName : The child group to invite users
	 */
	public void addNewSpace(String name, String desc, String visibility, String registration, String groupPath, String childGroupName, int... params){
		Actions actions = new Actions(driver);
		info("-- Adding a new space --");
		int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;
		//String v = visibility.isEmpty() ? "private" : visibility;
		String value1 = "private";
		String value2 = "hidden";
		//String r = registration.isEmpty() ? "validation" : registration;
		String[] regis = {"open", "validation", "close"};

		click(ELEMENT_ADDNEWSPACE_BUTTON);
		waitForElementPresent(ELEMENT_ADDNEWSPACE_FORM);
		type(ELEMENT_SPACE_NAME_INPUT, name, true);
		type(ELEMENT_SPACE_DESCRIPTION_INPUT, desc, true);
		switchTabs(ELEMENT_ACCESS_TAB);
		
		if (visibility != "") {
			if (visibility.equals("Visible")){
				WebElement rdoVisibility = waitForAndGetElement(By.xpath("//*[@name='UIVisibility' and @value='"+ value1 +"']"), 3000, 0, 2);
						//(By.id("UIVisibility_" + value1));
				if (!rdoVisibility.isSelected())
					actions.click(rdoVisibility).perform();
			}else if (visibility.equals("Hidden")){
				WebElement rdoVisibility = waitForAndGetElement(By.xpath("//*[@name='UIVisibility' and @value='"+ value2 +"']"), 3000, 0, 2);
						//(By.id("UIVisibility_" + value2));
				if (!rdoVisibility.isSelected())
					actions.click(rdoVisibility).perform();
			}	
		}
		
		if (registration != "") {
			if (registration.equals("Open")){
				WebElement rdoRegistration = waitForAndGetElement(By.xpath("//*[@name='UIRegistration' and @value='"+ regis[0] +"']"), 3000, 0, 2);
						//(By.id("UIRegistration_" + regis[0]));
				if (!rdoRegistration.isSelected())
					actions.click(rdoRegistration).perform();
			}else if (registration.equals("Validation")){
				WebElement rdoRegistration = waitForAndGetElement(By.xpath("//*[@name='UIRegistration' and @value='"+ regis[1] +"']"), 3000, 0, 2);
						//(By.id("UIRegistration_" + regis[1]));
				if (!rdoRegistration.isSelected())
					actions.click(rdoRegistration).perform();		
			}else if (registration.equals("Close")){
				WebElement rdoRegistration = waitForAndGetElement(By.xpath("//*[@name='UIRegistration' and @value='"+ regis[2] +"']"), 3000, 0, 2);
						//(By.id("UIRegistration_" + regis[2]));
				if (!rdoRegistration.isSelected())
					actions.click(rdoRegistration).perform();		
			}
		}

		switchTabs(ELEMENT_USER_GROUP_TAB);

		if (groupPath != "" && childGroupName != "") {
			addUserGroupToInvite(groupPath, childGroupName);
		}

		clickButton("Create");
		waitForTextPresent(name, iTimeout);
		pause(1000);
		//waitForElementPresent(By.xpath("//div[contains(@class,'UISpaceName')]/a[@title='" + name + "']"), iTimeout);
	}

	/**
	 * Add target group for a space when create new space
	 * 
	 * @param groupPath : Group path separate by a slash
	 * @param childGroupName : The child group to invite users
	 */
	public void addUserGroupToInvite(String groupPath, String childGroupName) {
		click(ELEMENT_USER_GROUP_CHECKBOX);
		userGroup.selectGroup(groupPath);
		click(By.linkText(childGroupName));
	}

	/**
	 * Delete a space
	 * 
	 * @param name : Space name
	 */
	public void deleteSpace(String name, int... params){
		int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;    
		doAction("Delete", name);    
		waitForConfirmation(MESSAGE_DELETE_SPACE);
		waitForElementNotPresent(By.xpath("//div[@class='contentBox']/h4[@class='spaceTitle']//a[text()='" + name + "']"), iTimeout);
		//(By.xpath("//a[text()='" + name + "']/ancestor::div[contains(@class,'ContentBox')]"),iTimeout);
		info(name + " was deleted successfully");
	}

	/**
	 * Update by @author vuna2
	 * add a @param uploadAvatar: boolean
	 */
	/**
	 * Edit a space
	 * @param name : Space name
	 * @param newName : New space name
	 * @param newDescription : New space description
	 * @param avatar : avatar file path
	 */
	public void editSpace(String name,String newName, String newDescription, boolean uploadAvatar, String avatar){
		gotoEditSpace(name);
		type(ELEMENT_SPACE_NAME_INPUT, newName, true);
		type(ELEMENT_SPACE_DESCRIPTION_INPUT, newDescription, true);
		if (uploadAvatar){
			changeAvatar(avatar);
		}else{
			info("-- Edit a space without changing the user's avatar --");
		}
		save();
		if(name == newName){
			waitForTextPresent("Update info of space successful.");
			closeMessageDialog();
		}else{
			waitForElementPresent(By.xpath("//div[contains(@class,'UISpaceName')]/a[@title='" + newName + "']"));
		}
	}
	/**
	 * Go to edit form of a space
	 * @param name : Space name
	 */
	public void gotoEditSpace(String name){    
		doAction("Edit", name);
		waitForElementPresent(ELEMENT_SPACE_SETTING_TAB);    
	}
	/**
	 * Change avatar of a space
	 * @param file : File path of new avatar
	 */
	public void changeAvatar(String file){
		if(!file.contains("ui-testsuite")) file = Utils.getAbsoluteFilePath(file); 
		click(ELEMENT_CHANGE_AVATAR_ICON);
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_UPLOAD_IMG_FRAME_XPATH));
		waitForElementPresent(ELEMENT_UPLOAD_IMG_ID);
		type(ELEMENT_UPLOAD_IMG_ID, file, false);
		pause(500);
		switchToParentWindow();
		clickButton("Confirm");    
		click(By.xpath("//*[@id='UIAvatarUploadContent']//a[contains(text(),'Save')]"));
		waitForTextNotPresent("Upload an Image");
	}
	
	/**
	 * Restore original data by deleting a space
	 * @author vuna2
	 * @param spaceName
	 * @param params: time for deleting a space (int)
	 */
	public void restoreData(String spaceName, int... params){
		info("-- Restore Original Data --");
		int timeToDeleteSpace = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;;
		goToMySpacePage();
		deleteSpace(spaceName, timeToDeleteSpace);
		pause(500);
	}

}
