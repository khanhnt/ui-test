package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class UserGroupManagement extends PlatformBase {
	public  final String MESSAGE_DUPLICATE_USERS = "User \"${username}\" has already the same membership ";
	public  final String MESSAGE_DUPLICATE_GROUPS = "in the group \"${groupName}\", please select an other membership.";
	public  final String ELEMENT_USER_INGROUP_DELETE_ICON = "//div[@id='UIGridUser']//div[text()='${username}']/../..//img[@class='DeleteUserIcon']";
	public  final String GROUP_PLATFORM_ADMIN = "Platform/Administration";
	public  final String MSG_DELETE_GROUP = "Are you sure you want to delete this group?";
	public  final String ELEMENT_SELECT_USER_CHECK = "//div[@title='{$user}']/../..//input";
	public  final String MSG_DELETE_MEMBERSHIP = "Are you sure to delete this membership?";
	public  final String MSG_DELETE_MANDATORY_MEMBERSHIP = "You can not delete this membership because it is mandatory";
	public  final By ELEMENT_EDIT_MEMBER= By.xpath("//i[@class='uiIconEdit']");
	public  final By ELEMENT_SELECT_MEMBERSHIP = By.xpath("//select[@name='membership']");
	public  final String ELEMENT_MEMBERSHIP_TEXT = "//td/div[@title='{$user}']/following::td/div[@title='{$membership}']";
	
	public UserGroupManagement(WebDriver dr){
		driver = dr;
	}
	

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
		pause(500);
		click(ELEMENT_TAB_MEMBERSHIP_MANAGEMENT);
		waitForTextPresent("Add/Edit Membership");
	}

	/*
	 * User Management
	 * */

	public void deleteUser(String username) {
		String userDeleteIcon = ELEMENT_USER_DELETE_ICON.replace("${username}", username);

		info("--Deleting user " + username + "--");
		if (isTextPresent("Total pages")) {
			usePaginator(userDeleteIcon, "User " + username + "not found in group");
		}
		pause(500);
		click(userDeleteIcon);
		waitForConfirmation("Are you sure to delete user " + username + "?");
		pause(1000);
		type(ELEMENT_INPUT_SEARCH_USER_NAME, username, true);
		select(ELEMENT_SELECT_SEARCH_OPTION, "User Name");
		click(ELEMENT_SEARCH_ICON_USERS_MANAGEMENT);
		waitForMessage("No result found.");
		closeMessageDialog();
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
		pause(1000);
	}

	/*
	 *  Group Management 
	 * */

	public void addGroup(String groupName, String groupLabel, String groupDesc, boolean verify){
		info("--Add a new group--");
		pause(500);
		click(ELEMENT_GROUP_ADD_NEW_ICON);
		type(ELEMENT_INPUT_GROUP_NAME, groupName, true);
		type(ELEMENT_INPUT_LABEL, groupLabel, true);
		type(ELEMENT_TEXTAREA_DESCRIPTION, groupDesc, true);
		save();
		if (verify) {
			waitForAndGetElement("//a[@title='" + (groupLabel.length() > 0 ? groupLabel : groupName) + "']");
		}
	}

	public void addUsersToGroup(String userNames, String memberShip, boolean select, boolean verify) {
		info("--Adding users to group--");
		String[] users = userNames.split(",");
		if (select) {
			click(ELEMENT_GROUP_SEARCH_USER_ICON);
			waitForTextPresent("Select User");
			for (String user : users) {
				check(By.id(user));
			}
			click(ELEMENT_GROUP_SEARCH_POPUP_ADD_ICON);
			pause(500);
			Assert.assertEquals(getValue(ELEMENT_INPUT_USERNAME), userNames);
		} else {
			type(ELEMENT_INPUT_USERNAME, userNames, true);
		}
		select(ELEMENT_SELECT_MEMBERSHIP, memberShip);
		save();
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
		info("-- Add a duplicated user into group --");
		String MESSAGE_DUPLICATE_USER = MESSAGE_DUPLICATE_USERS.replace("${username}", userName);
		String MESSAGE_DUPLICATE_USER_WITH_SAME_ROLE = MESSAGE_DUPLICATE_USER + MESSAGE_DUPLICATE_GROUPS.replace("${groupName}", groupName);
		selectGroup(groupName);
		type(ELEMENT_INPUT_USERNAME, userName, true);
		select(ELEMENT_SELECT_MEMBERSHIP, memberShip);
		save();
		waitForMessage(MESSAGE_DUPLICATE_USER_WITH_SAME_ROLE);
		click(ELEMENT_OK_BUTTON);	
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
		pause(500);
		click(userDeleteIcon);
		//waitForConfirmation(MESSAGE_DELETE_CONFIRMATION);
		Alert alert = driver.switchTo().alert();
		alert.accept();
		pause(500);
		waitForTextNotPresent(username);	
	}

	//Function to select group
	public void selectGroup(String groupPath){
		String[] temp;			 
		/* Delimiter */
		String delimiter = "/";

		temp = groupPath.split(delimiter);
		/* Go to group */
		for(int i =0; i < temp.length ; i++){
			info("Go to " + temp[i]);
			click(By.linkText(temp[i]));
			pause(500);
		}
	}

	public void editGroup(String groupName, boolean verify){
		info("-- Edit group: " + groupName + "--");
		click(ELEMENT_GROUP_EDIT_ICON);
		pause(1000);
	}

	public void deleteGroup(String groupName, boolean verify, int...wait) {
		info("-- Delete group: " + groupName + "--");
		int waitTime= wait.length > 0 ? wait[0]: DEFAULT_TIMEOUT;
		click(ELEMENT_GROUP_REMOVE_ICON);

		waitForConfirmation(MSG_DELETE_GROUP);
		if (verify) {
			waitForElementNotPresent("//a[@title='"+ groupName +"']",waitTime);
		}
		pause(1000);
	}

	/*
	 * Membership Management
	 * 
	 * */

	public void addMembership(String membershipName, String membershipDesc, boolean verify){
		boolean verifyMembership;
		info("--Creating new membership--");
		click(ELEMENT_TAB_MEMBERSHIP_MANAGEMENT);

		type(ELEMENT_INPUT_NAME, membershipName, true);
		type(ELEMENT_TEXTAREA_DESCRIPTION, membershipDesc, true);
		save();
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
		pause(1000);
		waitForAndGetElement(membershipInput);
		type(ELEMENT_TEXTAREA_DESCRIPTION, newDesc, true);
		save();
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
		waitForConfirmation(MSG_DELETE_MEMBERSHIP);
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
		click(By.xpath("//img[@title='Select Membership']"));
		selectGroup(groupPath);	
		click(By.linkText(membership));
	}
	public  void check(Object locator) {
		Actions actions = new Actions(driver);
		try {
			WebElement element = waitForAndGetHidenElement(locator);

			if (!element.isSelected()) {
				actions.click(element).perform();
			} else {
				Assert.fail("Element " + locator + " is already checked.");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			check(locator);
		} finally {
			loopCount = 0;
		}
	}
}
