package org.exoplatform.selenium.platform.wiki;

import static org.exoplatform.selenium.TestLogger.*;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.openqa.selenium.By;

/**
 * Migrate to PLF 4
 * @author vuna2
 *
 */
public class Permission extends WikiBase{

	ManageAccount magAc;
	ManageApplications app;

	public final String ELEMENT_PERMISSION = "//*[@id='UIPermissionGrid']//*[contains(text(),'${user}')]";

	/////
	/*=================== Page permission ===================*/
	/** Add page permission for an user
	 * @author: Thuntn
	 * 
	 * @param option: if the value of option determines  meaning of the userGroups parameter
	 * option = 0: userGroup[0]= user
	 * option = 1: userGroup[0]= user
	 * option = 2: userGroup[0] = path of a group
	 * option !=0,1,2: userGroup[0] = path of a group, userGroup[1] = membership
	 * @param userGroup: array of string
	 */
	public void addPagePermission(int option, String[] userGroup, Integer... type){
		int notDisplay = 0;
		if (type.length > 0){
			if (!(type[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)type[0];
		}
		info("--Add page permission for an user--");

		goToPagePermission();

		if(userGroup[0]!=null)
		{
			switch(option){

			case 0: type(ELEMENT_SELECT_INPUT,userGroup[0],true); break;
			case 1: click(ELEMENT_SELECT_USER);
			selectUserPermission(userGroup[0], 1, notDisplay); break;
			case 2: click(ELEMENT_SELECT_GROUP);
			selectGroupPermission(userGroup[0]); break;
			default: if (userGroup[1] !=null) {
				click(ELEMENT_SELECT_MEMBERSHIP);
				selectGroupMembership( userGroup[0], userGroup[1]);
			}
			break;
			}		
			pause(1000);
			click(app.ELEMENT_ADD_BUTTON);
			save();
		}
	}

	/** Edit permission for wiki's page
	 * @author HangNTT
	 * @param user: username 
	 * @param edit: true , then this user/group have edit permission and vice versa
	 */
	public void editPagePermission(String user, boolean viewPage, boolean edit, Object... option){
		boolean deletePermission = (Boolean) (option.length > 0 ? option[0] : false);
		int notDisplay = (Integer) (option.length > 1 ? option[1] : 0);
		
		By EDIT_PAGE = By.xpath(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", user));

		By VIEW_PAGE = By.xpath(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", user));
		
		By DELETE_PERMISSION = By.xpath(ELEMENT_DELETE_PERMISSIONS.replace("${user}", user));

		goToPagePermission();

		info("--Add space permission--");
		if (edit){
			if(!waitForAndGetElement(EDIT_PAGE, 5000, 0, notDisplay).isSelected()){
				click(EDIT_PAGE, notDisplay);
			}
		}
		else{
			if(waitForAndGetElement(EDIT_PAGE, 5000, 0, notDisplay).isSelected())
				click(EDIT_PAGE, notDisplay);
		}
		if (viewPage){
			if(!waitForAndGetElement(VIEW_PAGE, 5000, 0, notDisplay).isSelected()){
				click(VIEW_PAGE, notDisplay);
			}
		}
		else{
			if(waitForAndGetElement(VIEW_PAGE, 5000, 0, notDisplay).isSelected())
				click(VIEW_PAGE, notDisplay);
		}
		
		if (deletePermission){
			click(DELETE_PERMISSION);
			waitForElementNotPresent(DELETE_PERMISSION);
		}
		save();
		pause(1000);
	}
	
	/** Delete permission for an user
	 * @author: HangNTT
	 * @param user: username or group, or membership. eg: "*:/platform/users"
	 * 
	 */
	public void deletePagePermission(String user){
		By Delete = By.xpath(ELEMENT_DELETE_PERMISSION.replace("{$user}", user));

		goToPagePermission();

		info("--Delete permission--");
		click(Delete);

		waitForElementNotPresent(Delete);
		save();

		pause(2000);
	}

	/**
	 * 
	 * @param userType: login with User (Admin, Developer, etc...)
	 * @param user: user that we want to set a permission
	 * @param elementPage: Link to a Wiki page
	 */
	public void addEditPagePermission(ManageMember.userType userType, String user, By elementPage){
		magAc = new ManageAccount(driver);
		info("Add edit page permission for " + user);
		//magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);
		userSignIn(userType);
		goToWiki();
		click(elementPage);
		editPagePermission(user, true, true, false, 2);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();	
	}

	/**
	 * 
	 * @param userType: login with User (Admin, Developer, etc...)
	 * @param elementPage: Link to a Wiki page
	 * @param user: user that we want to set a permission
	 */
	public void removePagePermission(ManageMember.userType userType, By elementPage, String user){
		magAc = new ManageAccount(driver);
		info("remove view/edit page permission");
		//magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);
		userSignIn(userType);
		goToWiki();
		click(elementPage);
		editPagePermission(user, false, false, false, 2);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();
	}

	/*================== Space Permissions ===================*/
	/**
	 * 
	 * @param option: if the value of option determines  meaning of the userGroups parameter
	 * option = 0: userGroup[0]= user
	 * option = 1: userGroup[0]= user
	 * option = 2: userGroup[0] = path of a group
	 * option !=0,1,2: userGroup[0] = path of a group, userGroup[1] = membership
	 * @param groupUser: array of string
	 */
	public void addSpacePermission(int option, String[] groupUser, Integer...type){
		int notDisplay = 0;
		if (type.length > 0){
			if (!(type[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)type[0];
		}
		
		goToSpacePermission();

		info("--Add space permission--");
		switch(option) {
		case 0: type(ELEMENT_SELECT_INPUT,groupUser[0],true);
		break;
		case 1:	click(ELEMENT_SELECT_USER);
		selectUserPermission(groupUser[0], 0, notDisplay);
		break;

		case 2: click(ELEMENT_SELECT_GROUP);
		selectGroupPermission(groupUser[0]);
		break;

		default: click(ELEMENT_SELECT_MEMBERSHIP);
		selectGroupMembership(groupUser[0], groupUser[1]);
		break;
		}
		click(app.ELEMENT_ADD_BUTTON);
		save();
		waitForMessage(MSG_PERMISSION_SAVE);
		closeMessageDialog();
		pause(1000);
	}

	/** Edit a space permission for an user, or a group
	 * @author thuntn
	 * @param userGroup: username or group, or membership. eg: "*:/platform/users"
	 * @param view: true , then this user/group have view permission and vice versa
	 * @param edit: true , then this user/group have edit permission and vice versa
	 * @param adminPage: true , then this user/group have admin page permission and vice versa
	 * @param adminSpace: true , then this user/group have admin space permission and vice versa
	 */
	public void editSpacePermission(String userGroup,boolean view,boolean edit, boolean adminPage, 
			boolean adminSpace, Integer...type ){
		//By bEditPage = By.xpath(ELEMENT_EDIT_PAGE_CHECK.replace("{$user}", userGroup));
		By bEditPage = By.xpath(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", userGroup));
		By bAdminPage = By.xpath(ELEMENT_ADMIN_PAGE_CHECK.replace("{$user}", userGroup));		
		By bAdminSpace = By.xpath(ELEMENT_ADMIN_SPACE_CHECK.replace("{$user}", userGroup));
		//By bViewSpace = By.xpath(ELEMENT_VIEW_SPACE_CHECK.replace("{$user}", userGroup));
		By bViewSpace = By.xpath(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", userGroup));

		int notDisplay = 0;
		if (type.length > 0){
			if (!(type[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)type[0];
		}	

		goToSpacePermission();

		info("--Add space permission--");

		if (view){
			if(!waitForAndGetElement(bViewSpace, 3000, 0, notDisplay).isSelected()){
				click(bViewSpace, notDisplay);
			}
		}else{
			if(waitForAndGetElement(bViewSpace, 3000, 0, notDisplay).isSelected())
				click(bViewSpace, notDisplay);
		}
		if (edit){
			if(!waitForAndGetElement(bEditPage, 3000, 0, notDisplay).isSelected()){
				click(bEditPage, notDisplay);
			}
		}else{
			if(waitForAndGetElement(bEditPage, 3000, 0, notDisplay).isSelected())
				click(bEditPage, notDisplay);
		}
		if (adminPage){
			if(!waitForAndGetElement(bAdminPage, 3000, 0, notDisplay).isSelected()){
				click(bAdminPage, notDisplay);
			}
		}else{
			if(waitForAndGetElement(bAdminPage, 3000, 0, notDisplay).isSelected())
				click(bAdminPage, notDisplay);
		}

		if (adminSpace){
			if(!waitForAndGetElement(bAdminSpace, 3000, 0, notDisplay).isSelected()){
				click(bAdminSpace, notDisplay);
			}
		}else{
			if(waitForAndGetElement(bAdminSpace, 3000, 0, notDisplay).isSelected())
				click(bAdminSpace, notDisplay);
		}

		save();
		waitForMessage(MSG_PERMISSION_SAVE);
		closeMessageDialog();
		waitForTextNotPresent(MSG_PERMISSION_SAVE);
	}

	/** Delete a space permission for an user, or a group
	 * @author thuntn
	 * @param userGroup: username or group, or membership. eg: "*:/platform/users"
	 * 
	 */
	public void deleteSpacePermission(String userGroup){
		By bDelete = By.xpath(ELEMENT_DELETE_PERMISSION.replace("{$user}", userGroup));

		goToSpacePermission();

		info("--Add space permission--");
		click(bDelete);

		save();
		waitForMessage(MSG_PERMISSION_SAVE);
		closeMessageDialog();
		waitForElementNotPresent(bDelete);
		pause(1000);
	}

	//////////
	/**
	 * @author HangNTT
	 * @param pageOrSpace: boolean type[0]: gotoPage, type[1]: gotoSpace
	 * @param type: boolean type[0]: viewPage, type[1]: editPage, type[2]: adminPage, type[3]: adminSpace
	 * @param username
	 */
	public void verifyPermissions(boolean[] pageOrSpace, boolean[] type, String username, Integer... optional){	
		int notDisplay = 0;
		if (optional.length > 0){
			if (!(optional[0] instanceof Integer)) {
				throw new IllegalArgumentException("-- Argument should be an Integer --");
			}
			notDisplay = (Integer)optional[0];
		}
		
		if (pageOrSpace[0]){
			goToPagePermission();
		}else if (pageOrSpace[1]){
			goToSpacePermission();
		}

		if ( type[0]) {
			info("Message: check view page permission is checked");
			waitForAndGetElement(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}else if (!type[0]){
			info("Message: check view page permission is un-checked");
			waitForAndGetElement(ELEMENT_VIEW_PAGE_PERMISSIONS.replace("${user}", username), 3000, 0, notDisplay).isSelected();	
		}

		if ( type[1]) {
			info("Message: Check edit permission is checked");
			waitForAndGetElement(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}else if (!type[1]){
			info("Message: Check edit permission is un-checked");
			waitForAndGetElement(ELEMENT_EDIT_PAGE_PERMISSIONS.replace("${user}", username), 3000, 0, notDisplay).isSelected();	
		}

		if ( type.length > 2 && type[2]){
			info("Messgage: Check admin page is checked");
			waitForAndGetElement(ELEMENT_ADMIN_PAGE_CHECK.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}else if (type.length > 2 && !type[2]){
			info("Message: Check admin page is un-checked");
			waitForAndGetElement(ELEMENT_ADMIN_PAGE_CHECK.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}

		if ( type.length > 2 && type[3]){
			info("Messgage: Check admin space is checked");
			waitForAndGetElement(ELEMENT_ADMIN_SPACE_CHECK.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}else if (type.length > 2 && !type[3]){
			info("Message: Check admin space is un-checked");
			waitForAndGetElement(ELEMENT_ADMIN_SPACE_CHECK.replace("${user}", username), 3000, 0, notDisplay).isSelected();
		}
		save();
		pause(1000);
		if (isTextPresent(MSG_PERMISSION_SAVE)){
			//waitForMessage(MSG_PERMISSION_SAVE);
			closeMessageDialog();
			waitForTextNotPresent(MSG_PERMISSION_SAVE);
		}
	}

	//////////
	/**
	 * @lienTM
	 * @param user
	 * @param element_page
	 */
	public void deletePermissionWithUserAdmin(String user, By element_page){
		magAcc = new ManageAccount(driver);
		info("Delete page permission of user/group " + user);
		//magAcc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);
		userSignIn(userType.ADMIN);
		goToWiki();
		click(element_page);
		deletePagePermission(user);
		magAcc.signOut();
	}

	/**
	 * @lientTM
	 * @param user
	 * @param element_page
	 */
	public void checkViewPage(userType user, By element_page){
		magAcc = new ManageAccount(driver);
		info("Check user/group " + user + " does not have view/edit page permission");
		//magAcc.signIn(user, DATA_PASS_ADMIN);
		userSignIn(user);
		goToWiki();
		waitForElementNotPresent(element_page);
		info("User/group can not view page");
		magAcc.signOut();
	}
	
	/**
	 * 
	 * @param userType: login with User (Admin, Developer, etc...)
	 * @param elementPage: Link to a Wiki page
	 * @param content
	 */
	public void checkEditPage(userType user, By elementPage, String content){
		magAc = new ManageAccount(driver);
		info("Check user can view page but does not have edit page");
		//magAc.signIn(user, DATA_PASS_ADMIN);
		userSignIn(user);
		goToWiki();
		click(elementPage);
		waitForTextPresent(content);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);
		magAc.signOut();
	}
	
	/**
	 * Check user/group permission is not listed in page permission list
	 * @param user
	 */
	public void checkUserNotInPagePermission(String user){
		By element_permission = By.xpath(ELEMENT_PERMISSION.replace("${user}", user));

		info("Check user/group permission is not listed in page permission list");
		goToPagePermission();
		waitForElementNotPresent(element_permission);
		close();
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
	}
}
