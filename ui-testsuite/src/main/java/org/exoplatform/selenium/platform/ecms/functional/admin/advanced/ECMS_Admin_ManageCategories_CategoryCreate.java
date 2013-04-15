package org.exoplatform.selenium.platform.ecms.functional.admin.advanced;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageCategory;
import org.exoplatform.selenium.platform.ecms.admin.Permission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author VuNA
 *@date: 15/10/2012
 */
public class ECMS_Admin_ManageCategories_CategoryCreate extends PlatformBase{

	//Platform
	NavigationToolbar nav;
	ManageAccount magAcc;
	Dialog dialog;
	Button button;

	//Ecms
	EcmsBase ecms; 
	ActionBar actBar;
	ContextMenu cMenu;
	ManageCategory magCa;
	ContentTemplate cTemplate;
	EcmsPermission ecmsPer;
	Permission adminPer;
	ECMainFunction ecMain;

	//Data for these test cases
	public String DATA_USER = "john";
	public String DATA_PASS = "gtngtn";

	String categoryName = "category1";
	String categoryWorkspace = "collaboration";
	String nodeHomePath = "sites/intranet";
	//String username = "john";
	String groupID = "Platform/Administration";
	String actionName = "test";
	String optionLifeCycle = "User Action/Content Addition"; 
	String nodeTargetPath = "jcr:system/exo:namespaces";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER);
		nav = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		button = new Button(driver);
		dialog = new Dialog(driver);
		ecms = new EcmsBase(driver);
		magCa = new ManageCategory(driver);
		cMenu = new ContextMenu(driver);
		ecmsPer = new EcmsPermission(driver);
		adminPer = new Permission(driver);
		ecMain = new ECMainFunction(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.quit();
		//actions = null;
	}

	/*-- Case No 020 / ID 001
	 *-- Add category in Category tree when put valid data into name field
	 * --*/
	@Test
	public void test01_AddCategoryInCategoryTreeWhenPutValidDataIntoNameField(){
		info("-- Step 1: Create new Category tree --");

		String categoryTreeName = "testCase01";

		nav.goToContentAdministration();
		//Add category tree
		String[] form1 = {"testCase01", categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2 & 3: Open form to add category and Add Category--");
		magCa.addChildCategory(categoryTreeName, categoryName);

		info("-- Restore original data --");
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//signOut();
	}

	/*-- Case No 025 / ID 006
	 *-- Add permission for Category to user
	 *--*/
	@Test
	public void test06_AddPermissionForCategoryToUser(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "testCase06";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {"testCase06", categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "manager"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false, false};
		magCa.addNewCategoryTree(form1, false, false, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Open form to add permission --");
		magCa.addChildCategory(categoryTreeName, categoryName);

		info("-- Step 3: Add permission for user --");
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));

		info("-- Step 4 & 5: Select user to add permission and Select permission for user --");	
		adminPer.setPermissionForUserOnManageCategory(true, "mary", false, groupID, "member", true, true, false, false);
		waitForElementPresent(adminPer.ELEMENT_EDIT_USER_PERMISSION.replace("${userOrGroupName}", "mary"));

		info("-- Restore original data --");
		button.close();
		button.close();

		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//signOut();		
	}

	/*-- Case No 026 / ID 007
	 *-- Add permission for Category to group (1) 
	 *--*/
	@Test
	public void test07_AddPermissionForCategoryToGroup1(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "testCase07";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {"testCase07", categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "manager"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false, false};
		magCa.addNewCategoryTree(form1, true, false, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Open form to add permission --");
		magCa.addChildCategory(categoryTreeName, categoryName);

		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));
		info("-- Step 3 & 4 & 5: Add permission for group / Select group in form to add permission / Select permission for group --");

		adminPer.setPermissionForUserOnManageCategory(false, "mary", true, "Platform/Content Management", "manager", true, false, false, true);
		//waitForElementPresent(adminPer.ELEMENT_EDIT_USER_PERMISSION_AUX.replace("${userOrGroupName}", "manager:/platform/web-contributors"));

		button.close();
		button.close();

		info("-- Verify that all the users with added membership can do these actions--");

		magAcc.signOut();

		magAcc.signIn("mary", "gtngtn");

		nav.goToSiteExplorer();

		ecms.goToNode("intranet/"+ categoryTreeName +"");

		waitForTextPresent(categoryName);

		cMenu.deleteDocument(By.linkText(categoryName));

		magAcc.signOut();

		magAcc.signIn(DATA_USER, DATA_PASS);

		ecMain.goToCategoriesTabInContentAdmin();

		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");

		//magAcc.signOut();
	}

	/*-- Case No 027 / ID 008
	 *-- Add permission for Category to group (2) 
	 *--*/
	@Test
	public void test08_AddPermissionForCategoryToGroup2(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "testCase08";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {"testCase08", categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "manager"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false, false};
		magCa.addNewCategoryTree(form1, true, false, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Open form to add permission --");
		magCa.addChildCategory(categoryTreeName, categoryName);

		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));

		info("-- Step 3 & 4 & 5: Add permission for group / Select group in form to add permission / Select permission for group --");

		ecmsPer.deletePermission("*:/platform/web-contributors", true);

		adminPer.setPermissionForUserOnManageCategory(false, "", true, "platform/web-contributors", "*", true, false, false, true);
		waitForElementPresent(adminPer.ELEMENT_EDIT_USER_PERMISSION_AUX.replace("${userOrGroupName}", "*:/platform/web-contributors"));

		button.close();

		button.close();

		info("-- Verify that all the users with added membership can do these actions--");

		magAcc.signOut();

		magAcc.signIn("james", "gtngtn");

		nav.goToSiteExplorer();

		ecms.goToNode("intranet/"+ categoryTreeName +"");

		waitForTextPresent(categoryName);

		cMenu.deleteDocument(By.linkText(categoryName));

		magAcc.signOut();

		magAcc.signIn(DATA_USER, DATA_PASS);

		ecMain.goToCategoriesTabInContentAdmin();

		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");

		//magAcc.signOut();
	}

	/*-- Case No 028 / ID 009
	 *-- Add permission for Category to everybody 
	 *--*/
	@Test
	public void test09_AddPermissionForCategoryToEverybody(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "testCase09";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {"testCase09", categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "manager"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false, false};
		magCa.addNewCategoryTree(form1, true, false, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Open form to add permission --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));

		info("-- Step 3 & 4 & 5: Add permission for group / Select group in form to add permission / Select permission for group --");
		adminPer.setPermissionForUserOnManageCategory(false, "", false, "", "", true, true, false, true);
		button.close();
		button.close();

		info("-- Verify that all the users with added membership can do these actions--");
		magAcc.signOut();
		magAcc.signIn("james", "gtngtn");
		nav.goToSiteExplorer();
		ecms.goToNode("intranet/"+ categoryTreeName +"");
		waitForTextPresent(categoryName);
		magAcc.signOut();

		magAcc.signIn(DATA_USER, DATA_PASS);
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(categoryTreeName);
		info("-- Sign Out --");
		//magAcc.signOut();
	}

	/*-- Case No 029 / ID 010
	 *-- Edit permission of user/group 
	 *--*/
	@Test
	public void test10_EditPermissionOfUserOrGroup(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "testCase10";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {"testCase10", categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "manager"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false, false};
		magCa.addNewCategoryTree(form1, true, false, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Open form to add permission --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));
		adminPer.setPermissionForUserOnManageCategory(true, "mary", false, "", "", true, false, false, false);

		info("-- Step 3: Edit permission of Category --");
		button.close();
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));
		adminPer.editPermissionUserOrGroup("mary", true, false, false, true);

		//Verify that Permission of Category to user/group is edited
		By verifyEditUser = By.xpath("//*[text()='Add permission to that node']/../.." +
				"//*[text()='Remove']/../../..//*[@title='mary']/../../td[4]/*[@title='true']");
		waitForElementPresent(verifyEditUser);
		info("-- Restore original data --");
		button.close();
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//magAcc.signOut();	
	}

	/*-- Case No 030 / ID 011
	 *-- Edit permission of system 
	 *--*/
	@Test
	public void test11_EditPermissionOfSystem(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "testCase11";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {"testCase11", categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "manager"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false, false};
		magCa.addNewCategoryTree(form1, true, false, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Open form to add permission --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));
		adminPer.setPermissionForUserOnManageCategory(true, "mary", false, "", "", true, false, false, true);

		info("-- Step 3: Edit permission of Category --");
		button.close();
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));
		waitForTextPresent("Permission Management");
		adminPer.editPermissionUserOrGroup("__system", false, false, false, false);
		waitForElementNotPresent(adminPer.ELEMENT_SELECT_USER_IN_PERMISSION_MANAGEMENT);
		info("-- Restore original data --");
		button.close();
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//magAcc.signOut();
	}

	/*-- Case No 031 / ID 012
	 *-- Delete permission of user/group
	 *--*/
	@Test
	public void test12_DeletePermissionOfUserOrGroup(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "testCase12";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {"testCase12", categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "manager"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false, false};
		magCa.addNewCategoryTree(form1, true, false, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Open form to add permission --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));
		adminPer.setPermissionForUserOnManageCategory(true, "demo", false, "", "", true, true, false, false);

		info("-- Step 3: Delete permission of Category --");
		button.close();
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));
		ecmsPer.deletePermission("demo", true);
		info("-- Restore original data --");
		button.close();
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//	magAcc.signOut();	
	}

	/*-- Case No 032 / ID 013
	 *-- Delete permission of system
	 *--*/
	@Test
	public void test13_DeletePermissionOfSystem(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "testCase13";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {"testCase13", categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "manager"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false, false};
		magCa.addNewCategoryTree(form1, true, false, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Open form to add permission --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));
		adminPer.setPermissionForUserOnManageCategory(false, "", true, "developers", "*", true, true, false, false);

		info("-- Step 3: Delete permission of system --");
		button.close();
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));
		ecmsPer.deletePermission("__system", false);
		waitForMessage(adminPer.MESSAGE_INFO_DELETE_PERMISSION_SYSTEM);
		//dialog.closeMessageDialog();
		click(button.ELEMENT_OK_BUTTON);

		info("-- Restore original data --");
		button.close();
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//magAcc.signOut();	
	}

	/*@LienTM
	 * case01+11+12: Add new Category Tree when input valid data in all fields
	 * create new category tree with 4 steps
	 * edit category
	 * delete category
	 */
	@Test
	public void test01_11_12_AddEditDeleteCategoryTree(){
		String DATA_CATEGORY_TREE_NAME = "ECMS_Admin_ManageCategories_Create_tree_01";
		String DATA_ACTION_NAME = "ECMS_Admin_ManageCategories_Create_action_01";
		String DATA_CATEGORY_TREE_CHILD_01 = "ECMS_Admin_ManageCategories_Create_tree_child_01";
		String DATA_CATEGORY_TREE_CHILD_02 = "ECMS_Admin_ManageCategories_Create_tree_child_02";

		String[] DATA1 = {DATA_CATEGORY_TREE_NAME, "collaboration","sites/intranet"};
		String[] DATA2 = {"",""};
		String[] DATA3 = {DATA_ACTION_NAME,"User Action/Content Addition","jcr:system/exo:namespaces"};
		boolean[] setPermission = {true, false, false, false};
		
		//go to add new category tree
		nav.goToContentAdministration();

		//add new category tree
		magCa.addNewCategoryTree(DATA1, true, false, DATA2, "mary", setPermission, DATA3);

		//edit category tree at step 4
		info("Edit category tree");
		click(magCa.ELEMENT_EDIT_CATEGORY_TREE.replace("${categoryTreeName}", DATA_CATEGORY_TREE_NAME));
		magCa.addNewCategoryTree_Step4(DATA_CATEGORY_TREE_NAME, DATA_CATEGORY_TREE_CHILD_01, DATA_CATEGORY_TREE_CHILD_02, "mary", true, true,false,false);

		//delete data
		magCa.deleteCategory(DATA_CATEGORY_TREE_NAME);
		//waitForElementNotPresent(magCa.ELEMENT_ALERT_VISIBLE);
		info("Delete category is successful");
	}

	/*-- Case No 033 / ID 014
	 *-- Delete permission of Category in case there is only one permission of this Category (except system)
	 *-- Status: N/A (test case does not correspond to PLF 3.5.x)
	 *--*/
	/*@Test
	public void test14_DeletePermissionOfCategory(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "testCase14";

		nav.goToContentAdministration();

		click(ELEMENT_ADD_CATEGORY_TREE_BUTTON);
		magCa.addNewCategoryTree_Step1(categoryTreeName, categoryWorkspace, nodeHomePath);

		next();

		magCa.addNewCategoryTree_Step2(true, false, groupID, "manager", username, true, false, true, true);

		next();

		magCa.addNewCategoryTree_Step3(actionName, optionLifeCycle, nodeTargetPath);

		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Open form to add permission --");

		magCa.addChildCategory(categoryTreeName, categoryName);

		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));

		adminPer.setPermissionForUserOnManageCategory(false, "", true, "platform/users", "*", true, false, false, true);

		info("-- Step 3: Delete permission of Category --");

		button.close();

		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", categoryName));

		String[] userGroupToDeletes = {"*:/platform/administrators", "*:/platform/web-contributors", "any", "john"};

		for (String userGroupToDelete: userGroupToDeletes){
			ecmsPer.deletePermission(userGroupToDelete, false);
		}

		button.close();

		button.close();

		info("-- Verify that user at step 2 (in the added group) cannot delete permission of this user/group --");

		//magAcc.signOut();

		//driver.get(baseUrl);

		//ecms.loginEcms("james", "gtn");

		nav.goToSiteExplorer();

		ecms.goToNode("intranet/"+ categoryTreeName +"/"+ categoryName +"");

		click(By.linkText("System"));

		click(By.linkText("Permissions"));

		waitForTextPresent("Permission Management");

		By verifyRemoveIconNotPresent = By.xpath("//img[@class = 'DeleteIcon' and @title = 'Remove Permission']");

		waitForElementNotPresent(verifyRemoveIconNotPresent);

		//magAcc.signOut();

		info("-- Restore original data --");

		//driver.get(baseUrl);

		//ecms.loginEcms(DATA_USER, DATA_PASS);

		nav.goToContentAdministration();

		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");

		//magAcc.signOut();	
	}*/	
}