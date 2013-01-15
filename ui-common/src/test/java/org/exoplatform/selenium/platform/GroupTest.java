package org.exoplatform.selenium.platform;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import  org.exoplatform.selenium.platform.ManageAccount;
import  org.exoplatform.selenium.platform.NavigationToolbar;
import  org.exoplatform.selenium.platform.UserGroupManagement;

/**
 * 
 * @author vuna2
 *
 */
public class GroupTest extends NavigationToolbar{
	
<<<<<<< HEAD
	ManageAccount magAcc = new ManageAccount(driver);
=======
	ManageAccount magAcc = new ManageAccount(driver, actions);
>>>>>>> FQA-499: PLF 4 - Migrate EXOGTN (common + test cases)
	UserGroupManagement userGroup = new UserGroupManagement(driver);
	
	String groupName = "testgroup"	  ; 
	String groupLabel= ""			  ; 
	String groupDesc = "group example"; 
	boolean verify   = true			  ;
	
	String userName  = "demo";
	String memberShip= "member"; 
	boolean select   =  true; 
	
	@BeforeTest(groups = {"platform"})
	public void beforeTest()  {
		driver = new FirefoxDriver();
    	baseUrl = "http://localhost:8080";
    	driver.get(baseUrl + "/portal/intranet/");
    	pause(3000);
		
	}

	@AfterTest(groups = {"platform"})
	public void afterTest()  {
		driver.quit();
	}
	
	@Test(groups={"platform", "add new group"})
	public void testAddNewGroup(){
		System.out.println("--Create a new group--");
		magAcc.signIn("root", "gtn");
		pause(1000);
		goToUsersAndGroupsManagement();
		pause(1000);
		userGroup.chooseGroupTab();
		pause(1000);
		userGroup.addGroup(groupName, groupLabel, groupDesc,verify);
		magAcc.signOut();
		pause(1000);
		
	}
	
	@Test(groups={"platform", "add new group"})
	public void testSelectAndEditGroup(){
		System.out.println("--Edit a new group--");
		magAcc.signIn("root", "gtn");
		pause(1000);
		goToUsersAndGroupsManagement();
		pause(1000);
		userGroup.chooseGroupTab();
		pause(1000);
		userGroup.selectGroup(groupName);
		userGroup.editGroup(groupName, true);
		type(ELEMENT_INPUT_LABEL, "", true);
		type(ELEMENT_TEXTAREA_DESCRIPTION, "edit a group description", true);
		save();
		pause(1000);
		magAcc.signOut();
		pause(1000);
	}
	
	@Test(groups={"platform", "add new group"})
	public void testAddUserIntoGroup(){
		System.out.println("--Add user to a group--");
		magAcc.signIn("root", "gtn");
		pause(1000);
		goToUsersAndGroupsManagement();
		pause(1000);
		userGroup.chooseGroupTab();
		pause(1000);
		userGroup.selectGroup(groupName);
		userGroup.addUsersToGroup(userName, memberShip, select, verify);
		pause(1000);
		magAcc.signOut();
		pause(1000);
		
	}
	
	
	@Test(groups={"platform", "add new group"})
	public void testDeleteGroup(){
		System.out.println("--Delete a new group--");
		magAcc.signIn("root", "gtn");
		pause(1000);
		goToUsersAndGroupsManagement();
		pause(1000);
		userGroup.chooseGroupTab();
		pause(1000);
		userGroup.selectGroup(groupName);
		userGroup.deleteGroup(groupName, true);
		pause(3000);
		magAcc.signOut();
		pause(1000);		
	}
}
