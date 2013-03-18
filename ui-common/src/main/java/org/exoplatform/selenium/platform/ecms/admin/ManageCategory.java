package org.exoplatform.selenium.platform.ecms.admin;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 * @author vuna2
 *
 */
public class ManageCategory extends EcmsPermission{

	public ManageCategory(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	ECMainFunction ecMain = new ECMainFunction(driver);
	Permission permission = new Permission(driver);
	NavigationToolbar navToolbar = new NavigationToolbar(driver);
	Dialog dialog = new Dialog(driver);
	Button button = new Button(driver);
	ManageAlert magAlert = new ManageAlert(driver);

	//Add Category tree Form - Screen1
	public final By ELEMENT_ADD_CATEGORY_TREE_BUTTON = By.xpath("//*[text()='Add Category Tree']");
	//By.linkText("Add Category Tree");
	public final By ELEMENT_CATEGORIES_TREE_NAME = By.id("TaxoTreeName");
	public final By ELEMENT_CATEGORIES_WORKSPACE = By.name("TaxoTreeWorkspace");
	public final By ELEMENT_ADD_PATH_LINK = By.xpath("//*[@title='Add Path']");
	public final By ELEMENT_POPUP_HOME_PATH = By.id("PopupTaxonomyJCRBrowser");
	public final String ELEMENT_EDIT_CATEGORY_TREE = "//*[text()='${categoryTreeName}']/../..//*[@class='uiIconEditInfo']";

	// Add Category tree form -screen2
	//public final By ELEMENT_PREVIOUS_BUTTON = By.linkText("Previous");

	// Add Category tree form -screen3
	public final String MSG_ADD_CATEGORY_STEP3="Edit the taxonomy tree by adding, copying, cutting and selecting permissions.";
	public final By ELEMENT_ACTION_TYPE = By.id("actionType");
	public final By ELEMENT_ACTION_NAME = By.id("actionName");
	public final By ELEMENT_LIFE_CYCLE = By.name("lifecycle");
	public final By ELEMENT_SELECT_PATH_ICON = By.xpath("//input[@id='targetPath']/../../td/a/img[@class='ActionIcon SelectPath24x24Icon']");
	public final By ELEMENT_UP_LEVEL = By.xpath("//*[@id='UITaxonomyTreeCreateChild']//..//a[@title='Up Level']");
	public final By ELEMENT_ALERT_VISIBLE = By.xpath("//div[@class='UIPopupWindow UIDragObject ExoMessageDecorator' and contains(@style,'visible')]");	

	public final String ELEMENT_COPY_CATEGORY_ICON = "//*[@title='${categoryName}']/../..//*[@class='uiIconEcmsCopy']";
	public final String ELEMENT_PASTE_TO_CATEGORY_ICON = "//*[@title='${categoryName}']/../..//*[@class='uiIconEcmsPatse']";
	public final String ELEMENT_CUT_CATEGORY_ICON = "//*[@title='${categoryName}']/../..//*[@class='uiIconEcmsCut']";
	public final String MESSAGE_INFO_CUT_TO_CATEGORY =  "You cannot paste here. The category node '${pathCategory}' might be being cut.";
	public final String MESSAGE_INFO_PASTE_TO_CATEGORY = "Cannot read from the source file, or the destination category is a sub-category.";

	/*==========================================================*/

	/*-- Update common functions for Manage ECM Main Functions
	 *-- Manage categories 
	 *-- The category management includes adding, editing and deleting a category tree
	 *-- @author: VuNA
	 *-- @date: 15/10/2012
	 *--*/	

	//Add new category tree: step 1 - Specify the category name, workspace and home path.
	public void addNewCategoryTree_Step1(String categoryTreeName, String workspace, String homePath){
		info("-- Adding a workspace/homepath to: " + categoryTreeName + " --");
		type(ELEMENT_CATEGORIES_TREE_NAME, categoryTreeName, false);
		select(ELEMENT_CATEGORIES_WORKSPACE, workspace);
		click(ELEMENT_ADD_PATH_LINK);
		waitForElementPresent(ELEMENT_POPUP_HOME_PATH);
		selectHomePathForCategoryTree(homePath);
		waitForTextNotPresent("Select Home Path");
	}

	//Add new category tree: step 2 - Set permission for the category tree.
	/**
	 * 
	 * @param selectUserOrGroup
	 * @param selectMembership
	 * @param groupID
	 * @param membership
	 * @param user_Per
	 * @param read
	 * @param add
	 * @param set
	 * @param remove
	 */
	public void addNewCategoryTree_Step2(boolean selectUserOrGroup, boolean selectMembership, String groupID, String membership, 
			String user_Per, boolean read, boolean add, boolean set, boolean remove){
		info("-- Add permissions to the category tree --");
		if (!selectUserOrGroup && !selectMembership){
			click(By.xpath("//*[@title='Select Everyone']"));
		}
		else{
			if(selectUserOrGroup){
				selectUser(user_Per);
			}
			if(selectMembership){
				click(By.xpath("//*[@title='Select Membership']"));
				userGroup.selectGroup(groupID, true);
				if (isElementPresent(By.linkText(membership))){
					click(By.linkText(membership));
				}else if (isElementPresent(By.linkText("Select this Group"))){
					click(By.linkText("Select this Group"));
				}
			}
		}
		Utils.pause(500);
		setPermissionForNode(read,add,remove);
		click(button.ELEMENT_SAVE_BUTTON);
		if (!selectUserOrGroup && !selectMembership){
			waitForElementPresent(By.xpath("//*[@data-original-title = 'any']"));
		}
		else if (selectUserOrGroup){
			waitForElementPresent(By.xpath("//*[@data-original-title = '"+ user_Per +"']"));
		}
		else if (selectMembership){
		    if (groupID.contains("Administration")){
		    	waitForElementPresent(By.xpath("//*[@data-original-title = '"+ membership +":/platform/administrators']"));
		    }else if (groupID.contains("Guests")){
		    	waitForElementPresent(By.xpath("//*[@data-original-title = '"+ membership +":/platform/guests']"));
		    }else if (groupID.contains("Users")){
		    	waitForElementPresent(By.xpath("//*[@data-original-title = '"+ membership +":/platform/users']"));
		    }else if (groupID.contains("Content Management")){
		    	waitForElementPresent(By.xpath("//*[@data-original-title = '"+ membership +":/platform/web-contributors']"));
		    }
		    /*else{	
		    	waitForElementPresent(By.xpath("//*[@data-original-title = '"+ membership +":/"+ groupID +"']"));
		    }*/
		}
		info("Add user permission for category is successfull");
	}

	//Add new category tree: step 3 - Add an action to the category tree to complete the creation or update
	public void addNewCategoryTree_Step3(String actionName, String optionsLifeCycle, String nodeTargetPath){
		Actions actions = new Actions(driver);
		info("-- Add an action to the category tree --");
		String ELEMENT_LIFE_CYCLE_LIST_OPTIONS = ".//*[@name='lifecycle']/option[contains(text(), '${option}')]";  
		type(ELEMENT_ACTION_NAME,actionName,false);		
		String[] optionsLC = optionsLifeCycle.split("/");
		select(ELEMENT_LIFE_CYCLE, optionsLC[0]);
		actions.keyDown(Keys.CONTROL);
		for (int i = 1; i < optionsLC.length; i++) {
			click(ELEMENT_LIFE_CYCLE_LIST_OPTIONS.replace("${option}", optionsLC[i]));
		}
		Utils.pause(500);	
		click(ELEMENT_SELECT_PATH_ICON);

		selectHomePathForCategoryTree(nodeTargetPath);

		//click(button.ELEMENT_SAVE_BUTTON);
		click("//*[text()='Add an action to the category tree']/..//*[text()='Save']");
		Utils.pause(500);
	}

	//Add new category tree: step 4 - Edit the taxonomy tree by adding, copying, cutting and selecting permissions.
	public void addNewCategoryTree_Step4(String name, String childname1, String childname2, String user_Per, boolean read, boolean add, boolean set, boolean remove ){
		//--Add child category
		addChildCategory(name, childname1);
		click(ELEMENT_UP_LEVEL);
		addChildCategory(name, childname2);

		//--Copy and paste
		copyAndPasteCategory(childname1, childname2);
		deleteCategory(childname1);
		click(ELEMENT_UP_LEVEL);

		//--Cut and paste
		cutAndPasteCategory(childname1, childname2);

		//--Set permission
		click(By.xpath("//*[@title='" + childname2 + "']/../..//*[@title='Permission Management']"));
		permission.setPermissionForUserOnManageCategory(true, user_Per, false, "", "", read,add,set,remove);
		click(button.ELEMENT_CLOSE_BUTTON);
		deleteCategory(childname2);
	}

	//Function to add new category tree
	//boolean[] optionPermission: read / add / set property / remove
	public void addNewCategoryTree(String[] nameWorkspaceHomePath, boolean selectUserOrGroup, boolean selectMembership, String[] groupIDAndMembership, 
			String user_Per, boolean[] optionPermission, String[] actions){
		By ELEMENT_CATEGORY_TREE = By.xpath("//div[@title='" + nameWorkspaceHomePath[0] + "']");

		info("Add new category tree with name: " + nameWorkspaceHomePath[0]);
		click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
		click(ecMain.ELEMENT_MANAGE_CATEGORIES_LINK);
		click(ELEMENT_ADD_CATEGORY_TREE_BUTTON);

		//Step 1: Specify the category name, workspace and home path. 
		info("Add new category tree - step 1: Specify the category name, workspace and home path.");
		addNewCategoryTree_Step1(nameWorkspaceHomePath[0], nameWorkspaceHomePath[1], nameWorkspaceHomePath[2]);
		click(button.ELEMENT_NEXT_BUTTON);

		//Step 2: Set permission for the category tree.
		info("Add new category tree - step 2: Set permission for the category tree.");
		addNewCategoryTree_Step2(selectUserOrGroup, selectMembership, groupIDAndMembership[0], groupIDAndMembership[1], 
				user_Per, optionPermission[0], optionPermission[1], optionPermission[2], optionPermission[3]);
		//click(button.ELEMENT_NEXT_BUTTON);
		click("//*[text()='Add permissions to this node']/..//*[text()='Next']");

		//step 3: Add an action to the category tree to complete the creation or update
		info("Add new category tree - step 3: Add an action to the category tree to complete the creation or update");
		//actions[0]: actionName, actions[1]: optionsLifeCycle, actions[2]: nodeTargetPath
		addNewCategoryTree_Step3(actions[0], actions[1], actions[2]);

		//step 4: Edit the taxonomy tree by adding, copying, cutting and selecting permissions.
		//info("Add new category tree - step 4: Edit the taxonomy tree by adding, copying, cutting and selecting permissions");
		//addNewCategoryTree_Step4(name, childname1, childname2);
		waitForTextPresent(MSG_ADD_CATEGORY_STEP3);
		click(button.ELEMENT_CLOSE_BUTTON);

		//check add new category tree successfully
		waitForElementPresent(ELEMENT_CATEGORY_TREE);
		assert isElementPresent(ELEMENT_CATEGORY_TREE):"Add new category tree is not successful";
		info("Add new category is successful");
	}

	// Function to expand node on category tree
	public void expandNode(String node, boolean ex){
		//By ELEMENT_EXPAND = By.xpath("//div[@title='" + node + "']/../..//*[@class='ExpandIcon exo_taxonomy16x16Icon default16x16Icon']");
		By ELEMENT_COLLAPSE = By.xpath("//*[@title='" + node + "']/../..//*[contains(@class, 'collapseIcon uiIconEcmsexo')]");		  
		//goToNode(By.linkText(node));
		/*if (ex && (getElement(ELEMENT_EXPAND) != null)){
				click(ELEMENT_EXPAND);
			}*/
		if (ex && (getElement(ELEMENT_COLLAPSE) != null)){
			click(ELEMENT_COLLAPSE);
		}
		if (!ex && (getElement(ELEMENT_COLLAPSE) != null)){
			click(ELEMENT_COLLAPSE);
		}
	}

	//Function to add child category for a category
	public void addChildCategory(String cat_name, String child_name, boolean...grandChild){
		//Click edit category
		boolean isGrandChild = grandChild.length>0 ? grandChild[0] : false;
		if (!isGrandChild) click(ELEMENT_EDIT_CATEGORY_TREE.replace("${categoryTreeName}", cat_name));
		//click(By.xpath("//*[text() = '" + cat_name + "']/../..//*[@class='uiIconEditInfo']"));
		info("Add child category " + child_name + " for category " + cat_name);
		click(By.xpath("//div[@title='" + cat_name + "']/../..//*[@title='Add']"));
		type(By.id("taxonomyName"), child_name, false);
		//click(button.ELEMENT_SAVE_BUTTON);
		click("//*[@id='taxonomyName']/../../../..//*[text()='Save']");
		expandNode(cat_name, true);
		waitForElementPresent(By.linkText(child_name));
		info("Add child category successfully");
	}

	//Function to copy and paste a category
	public void copyAndPasteCategory(String child1, String child2){
		By ELEMENT_CHILD_NEW = By.xpath("//*[@title='"+child2+"']/../..//*[@title='"+child1+"']");

		click(By.xpath("//div[@title='"+child1+"']/../..//*[@class='uiIconEcmsCopy']"));
		click(By.xpath("//div[@title='"+child2+"']/../..//*[@class='uiIconEcmsPatse']"));
		expandNode(child2,true);
		waitForElementPresent(ELEMENT_CHILD_NEW);
		info("Copy category " + child1 + "to category " + child2 + "is successful");
	}

	//Function to cut and paste a category
	public void cutAndPasteCategory(String child1, String child2){
		By ELEMENT_CHILD_NEW = By.xpath("//*[@title='" + child2 + "']/../..//*[@title='"+child1+"']");

		click(By.xpath("//div[@title='" + child1 + "']/../..//*[@class='uiIconEcmsCut']"));
		click(By.xpath("//div[@title='" + child2 + "']/../..//*[@class='uiIconEcmsPatse']"));
		expandNode(child2,true);
		waitForElementPresent(ELEMENT_CHILD_NEW);
		click(ELEMENT_UP_LEVEL);
		waitForElementNotPresent(By.linkText("//*[@id='UITaxonomyTreeCreateChild']//..//*[@title='" + child1 + "']"));
		info("Cut category " + child1 + "to category " + child2 + "is successful");
	}

	//Delete a category
	public void deleteCategory(String categoryName){
		By ELEMENT_DELETE = By.xpath("//div[@title='" + categoryName + "']/../..//*[@class='uiIconDelete']");

		if (getElement(ELEMENT_DELETE) != null){
			click(ELEMENT_DELETE); 
			magAlert.acceptAlert();
			waitForElementNotPresent(ELEMENT_DELETE);
			info("Delete category " + categoryName + " is successful");
		}else{
			info("Do not find element to delete");
		}
		Utils.pause(1000);
	}
	/// End of Manage categories
}
