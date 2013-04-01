package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class UserGroupManagement extends PlatformBase {
	
	public UserGroupManagement(WebDriver dr){
		driver = dr;
	}
	
	ManageAlert alt;
	Dialog dialog;
	Button button;
	
	public  final String MESSAGE_DUPLICATE_USERS = "User \"${username}\" has already the same membership ";
	public  final String MESSAGE_DUPLICATE_GROUPS = "in the group \"${groupName}\", please select another one.";
	public  final String ELEMENT_USER_INGROUP_DELETE_ICON = "//div[@id='UIGridUser']//div[text()='${username}']/../..//img[@class='DeleteUserIcon']";

	//User Management -> Edit User form
	public  final String ELEMENT_USER_MEMBERSHIP_TAB = "//div[text()='User Membership' and @class='MiddleTab']";
	
	/*
	 *  Choose TAB actions
	 * */

	public void chooseUserTab(){
		info("-- Choose User tab--");
		click(ELEMENT_USER_MANAGEMENT);
		waitForTextPresent("User Name");
	}

	public void chooseGroupTab() {
		info("-- Choose Group Management tab--");
		click(ELEMENT_GROUP_MANAGEMENT_TAB);
		waitForTextPresent("Group Info");
	}

	public void chooseMembershipTab() {
		info("-- Choose Membership Management tab--");
		Utils.pause(500);
		click(ELEMENT_TAB_MEMBERSHIP_MANAGEMENT);
		waitForTextPresent("Add/Edit Membership");
	}

	/*
	 * User Management
	 * */

	public void deleteUser(String username) {
		dialog = new Dialog(driver);
		alt = new ManageAlert(driver);
		String userDeleteIcon = ELEMENT_USER_DELETE_ICON.replace("${username}", username);

		info("--Deleting user " + username + "--");
		if (isTextPresent("Total pages")) {
			usePaginator(userDeleteIcon, "User " + username + "not found in group");
		}
		Utils.pause(500);
		click(userDeleteIcon);
		alt.waitForConfirmation("Are you sure to delete user " + username + "?");
		Utils.pause(1000);
		type(ELEMENT_INPUT_SEARCH_USER_NAME, username, true);
		select(ELEMENT_SELECT_SEARCH_OPTION, "User Name");
		click(ELEMENT_SEARCH_ICON_USERS_MANAGEMENT);
		waitForMessage("No result found.");
		dialog.closeMessageDialog();
		waitForTextNotPresent(username);
	}

	public void searchUser(String user, String searchOption){
		info("--Search user " + user + "--");
		if (isTextPresent("Search")){
			type(ELEMENT_INPUT_SEARCH_USER_NAME, user, true);
			select(ELEMENT_SELECT_SEARCH_OPTION, searchOption);
		}	
		click(ELEMENT_SEARCH_ICON_USERS_MANAGEMENT);
		waitForTextPresent(user);
	}

	public void editUser(String username) {
		String userEditIcon = ELEMENT_USER_EDIT_ICON.replace("${username}", username);

		info("--Editing user " + username + "--");
		click(userEditIcon);
		Utils.pause(1000);
	}

	/*
	 *  Group Management 
	 * */

	public void addGroup(String groupName, String groupLabel, String groupDesc, boolean verify){
		button = new Button(driver);
		info("--Add a new group--");
		Utils.pause(500);
		click(ELEMENT_GROUP_ADD_NEW_ICON);
		type(ELEMENT_INPUT_GROUP_NAME, groupName, true);
		type(ELEMENT_INPUT_LABEL, groupLabel, true);
		type(ELEMENT_TEXTAREA_DESCRIPTION, groupDesc, true);
		button.save();
		if (verify) {
			waitForAndGetElement("//a[@title='" + (groupLabel.length() > 0 ? groupLabel : groupName) + "']");
		}
	}

	public void addUsersToGroup(String userNames, String memberShip, boolean select, boolean verify) {
		button = new Button(driver);
		info("--Adding users to group--");
		String[] users = userNames.split(",");
		if (select) {
			if (isElementPresent(ELEMENT_GROUP_SEARCH_USER_ICON)){
				click(ELEMENT_GROUP_SEARCH_USER_ICON);
			}else if (isElementPresent(By.xpath("//*[contains(@class, 'uiIconSelectUser')]"))){
				click(By.xpath("//*[contains(@class, 'uiIconSelectUser')]"));
			}
			waitForTextPresent("Select User");
			for (String user : users) {
				click("//input[@name='" + user + "']", 2);
			}
			//click(ELEMENT_GROUP_SEARCH_POPUP_ADD_ICON);
			click(button.ELEMENT_ADD_BUTTON);
			Utils.pause(500);
			Assert.assertEquals(getValue(ELEMENT_INPUT_USERNAME), userNames);
		} else {
			type(ELEMENT_INPUT_USERNAME, userNames, true);
		}
		select(ELEMENT_SELECT_MEMBERSHIP, memberShip);
		button.save();
		if (verify) {
			for (String user : users) {
				String addedUser = ELEMENT_GROUP_USER_IN_TABLE.replace("${username}", user);
				if (isTextPresent("Total pages")) {
					usePaginator(addedUser, "User " + user + "not found in group");
				} else {
					waitForAndGetElement(addedUser);
				}
			}
		}
	}

	//Add a duplicated user into group
	public void addDuplicatedUserToGroup(String groupName, String userName, String memberShip){
		button = new Button(driver);
		info("-- Add a duplicated user into group --");
		String MESSAGE_DUPLICATE_USER = MESSAGE_DUPLICATE_USERS.replace("${username}", userName);
		String MESSAGE_DUPLICATE_USER_WITH_SAME_ROLE = MESSAGE_DUPLICATE_USER + MESSAGE_DUPLICATE_GROUPS.replace("${groupName}", groupName);
		selectGroup(groupName);
		type(ELEMENT_INPUT_USERNAME, userName, true);
		select(ELEMENT_SELECT_MEMBERSHIP, memberShip);
		button.save();
		waitForMessage(MESSAGE_DUPLICATE_USER_WITH_SAME_ROLE);
		click(button.ELEMENT_OK_BUTTON);	
	}	

	//Delete a user in current group
	public void deleteUserInGroup(String groupName, String groupLabel, String username){
		String userDeleteIcon = ELEMENT_USER_INGROUP_DELETE_ICON.replace("${username}", username);
		//String MESSAGE_DELETE_CONFIRMATION = "Are you sure to delete user " + username + " from group " + groupName + "?";

		if (groupLabel != ""){
			selectGroup(groupLabel);
		}
		else {
			selectGroup(groupName);
		}

		info("--Deleting user " + username + "--");
		if (isTextPresent("Total pages")) {
			usePaginator(userDeleteIcon, "User " + username + "not found in group");
		}
		Utils.pause(500);
		click(userDeleteIcon);
		//waitForConfirmation(MESSAGE_DELETE_CONFIRMATION);
		Alert alert = driver.switchTo().alert();
		alert.accept();
		Utils.pause(500);
		waitForTextNotPresent(username);	
	}

	//Function to select group
	public void selectGroup(String groupPath, Object...params){
		String groupName =  "//*[text()='Select Group']/..//*[contains(text(), '${groupName}')]";
		String groupName_2 =  "//*[text()='selectGroup']/..//*[contains(text(), '${groupName}')]";
		String groupName_3 = "//*[contains(text(), 'select a group')]/..//*[contains(text(), '${groupName}')]";
		String[] temp;			 
		/* Delimiter */
		String delimiter = "/";
		Boolean isInPermissionTab = (Boolean) (params.length > 0? params[0]: false);

		temp = groupPath.split(delimiter);
		/* Go to group */
		for(int i =0; i < temp.length ; i++){
			info("Go to " + temp[i]);
			if (isInPermissionTab){
				if (isElementPresent(By.xpath(groupName.replace("${groupName}", temp[i])))){
					click(By.xpath(groupName.replace("${groupName}", temp[i])));
				}else if (isElementPresent(By.xpath(groupName_2.replace("${groupName}", temp[i])))){
					click(By.xpath(groupName_2.replace("${groupName}", temp[i])));
				}else if (isElementPresent(By.xpath(groupName_3.replace("${groupName}", temp[i])))){
					click(By.xpath(groupName_3.replace("${groupName}", temp[i])));
				}
			}else{
				click(By.linkText(temp[i]));
			}
			Utils.pause(500);
		}
	}

	public void editGroup(String groupName, boolean verify){
		info("-- Edit group: " + groupName + "--");
		click(ELEMENT_GROUP_EDIT_ICON);
		Utils.pause(1000);
	}

	public void deleteGroup(String groupName, boolean verify, int...wait) {
		info("-- Delete group: " + groupName + "--");
		int waitTime= wait.length > 0 ? wait[0]: DEFAULT_TIMEOUT;
		click(ELEMENT_GROUP_REMOVE_ICON);

		alt.waitForConfirmation("Are you sure to delete this group?");
		if (verify) {
			waitForElementNotPresent("//a[@title='"+ groupName +"']",waitTime);
		}
		Utils.pause(1000);
	}

	/*
	 * Membership Management
	 * 
	 * */

	public void addMembership(String membershipName, String membershipDesc, boolean verify){
		button = new Button(driver);
		boolean verifyMembership;
		info("--Creating new membership--");
		click(ELEMENT_TAB_MEMBERSHIP_MANAGEMENT);

		type(ELEMENT_INPUT_NAME, membershipName, true);
		type(ELEMENT_TEXTAREA_DESCRIPTION, membershipDesc, true);
		button.save();
		verifyMembership = isTextPresent(membershipName);
		if (verifyMembership){
			waitForTextPresent(membershipName);
		}
		else if (verify){
			click(ELEMENT_NEXT_PAGE_ICON);	
			waitForTextPresent(membershipName);
		}

	}

	public void editMembership(String membershipName, String newDesc){
		button = new Button(driver);
		info("-- Edit membership: " + membershipName + "--");

		boolean verifyMembership;
		verifyMembership = isTextPresent(membershipName);
		if (verifyMembership){
			waitForTextPresent(membershipName);
		}
		else {
			click(ELEMENT_NEXT_PAGE_ICON);
		}

		String editIcon = ELEMENT_MEMBERSHIP_EDIT_ICON.replace("${membership}", membershipName);
		String membershipInput = "//input[@value='" + membershipName + "']";
		click(editIcon);
		Utils.pause(1000);
		waitForAndGetElement(membershipInput);
		type(ELEMENT_TEXTAREA_DESCRIPTION, newDesc, true);
		button.save();
		if (verifyMembership){
			waitForTextPresent(membershipName);
		}
		else {
			click(ELEMENT_NEXT_PAGE_ICON);
		}
		waitForTextPresent(newDesc);
	}

	public void deleteMembership(String membershipName, boolean verify){

		boolean verifyMembership;
		verifyMembership = isTextPresent(membershipName);
		if (verifyMembership){
			waitForTextPresent(membershipName);
		}
		else {
			click(ELEMENT_NEXT_PAGE_ICON);
		}
		String deleteIcon = ELEMENT_MEMBERSHIP_DELETE_ICON.replace("${membership}", membershipName);
		info("--Deleting membership--");
		click(deleteIcon);
		alt.waitForConfirmation("Are you sure to delete this membership?");
		if (!verifyMembership){
			waitForTextNotPresent(membershipName);
		}
		else if (verify) {
			click(ELEMENT_NEXT_PAGE_ICON);
			waitForTextNotPresent(membershipName);
		}
	}

	//Function to select a group and membership on permission management popup
	//Go to siteExplorer - System tab - Permission - Select Membership
	public void selectGroupAndMembership(String groupPath, String membership){
		if (isElementPresent(By.xpath("//*[@data-original-title = 'Select Membership']"))){
			click(By.xpath("//*[@data-original-title = 'Select Membership']"));
		}else if (isElementPresent(By.xpath("//*[@title = 'Select Membership']"))){
			click(By.xpath("//*[@title = 'Select Membership']"));
		}else if (isElementPresent(By.xpath("//*[@title = 'SelectMember']"))){
			click(By.xpath("//*[@title = 'SelectMember']"));
		}else if (isElementPresent(By.xpath("//*[@data-original-title = 'SelectMember']"))){
			click(By.xpath("//*[@data-original-title = 'SelectMember']"));
		}
		selectGroup(groupPath);	
		click(By.linkText(membership));
		Utils.pause(1000);
	}
}
