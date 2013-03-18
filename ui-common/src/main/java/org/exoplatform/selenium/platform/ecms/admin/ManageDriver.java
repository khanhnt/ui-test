package org.exoplatform.selenium.platform.ecms.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author vuna2
 *
 */
public class ManageDriver extends EcmsBase{

	public ManageDriver(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	NavigationToolbar nav = new NavigationToolbar(driver);
	Button button = new Button(driver);
	ActionBar actBar = new ActionBar(driver);
	NavigationToolbar navToolbar = new NavigationToolbar(driver);
	ECMainFunction ecMain = new ECMainFunction(driver);
	ManageAlert alt = new ManageAlert(driver);

	/*Manage Drive Page */
	//Add a drive
	public final By ELEMENT_ADD_DRIVER_BUTTON = By.xpath("//*[text()='Add Drive']"); 

	//Edit a drive
	public final By ELEMENT_DRIVE_EDIT_POPUP = By.id("EditDriveManagerPopup");
	public final String ELEMENT_DRIVE_EDIT = "//*[@title='${DATA_DRIVE_NAME}']/../..//*[@title='Edit']";

	//Delete a drive
	public final String ELEMENT_DRIVE_DELETE = "//*[@title='${DATA_DRIVE_NAME}']/../..//*[@title='Delete']";

	//Add a Driver form 
	public final By ELEMENT_ADD_DRIVE_POPUP = By.id("AddDriveManagerPopup");
	public final By ELEMENT_APPLY_VIEW_TAB = By.xpath("//*[contains(text(),'Apply Views')]");
	public final By ELEMENT_DRIVE_TAB = By.xpath("//*[contains(text(),'Drive')]");
	public final By ELEMENT_WORKSPACE = By.name("workspace");
	public final By ELEMENT_ADD_PATH = By.xpath("//*[@title='Add Path']");
	public final By ELEMENT_ADD_PATH_POPUP = By.id("JCRBrowser");
	public final By ELEMENT_JCR_SYSTEM = By.xpath("//*[contains(text(),'jcr:system')]/../..//*[@title='select']");
	public final By ELEMENT_HOME_PATH = By.id("homePath");
	public final By ELEMENT_ADD_PERMISSION = By.xpath("//*[@title='Add Permission']");
	public final By ELEMENT_PERMISSION_TEXTBOX = By.id("permissions");
	public final By ELEMENT_ALLOW_CREATE_FOLDER = By.name("allowCreateFolders");
	public final By ELEMENT_DRIVE_NAME = By.id("name");

	public final By ELEMENT_MANAGE_DRIVE_LINK = By.linkText("Manage Drives");

	//------------Manage driver------------------//

	//Function to add new driver in manage driver form
	public void addNewDriver(String driverName, String workspace, String path, String group, 
			String member, String viewOption, String view){
		By ELEMENT_EXO_ECM = By.xpath("//*[contains(text(),'" + path + "')]/../..//*[@title='select']");
		By ELEMENT_DRIVER = By.xpath("//*[@title='" + driverName + "']");

		click(ELEMENT_ADD_DRIVER_BUTTON);

		//check add driver form
		info("Add new driver with name " + driverName);
		waitForElementPresent(ELEMENT_ADD_DRIVE_POPUP);
		assert isElementPresent(ELEMENT_DRIVE_TAB):"Add driver form is wrong";
		assert isElementPresent(ELEMENT_APPLY_VIEW_TAB):"Add driver form is wrong";

		//input name
		type(ELEMENT_DRIVE_NAME,driverName,false);

		// Select workspace for new drive
		select(ELEMENT_WORKSPACE, workspace);

		//select path for new drive
		if (path.equals("/") || path.equals("Root")){
			click(ELEMENT_ADD_PATH);
			waitForElementPresent(ELEMENT_ADD_PATH_POPUP);
			click(By.xpath("//*[@title='AddRootNode']"));
			Utils.pause(1000);
		}else{
			click(ELEMENT_ADD_PATH);
			waitForElementPresent(ELEMENT_ADD_PATH_POPUP);
			assert waitForAndGetElement(By.xpath("//*[@name='workspaceName']/option[@value='" + workspace + "']")).isSelected():"Homepath display wrong";
			if (waitForAndGetElement(ELEMENT_EXO_ECM) != null){
				click(ELEMENT_EXO_ECM);
			}else{
				info("list node of homepath display false");
			}
			assert getValue(ELEMENT_HOME_PATH).equals("/" + path + ""):"select homepath is false";
		}

		//Set permission on new drive
		info("Set Permission for driver");
		click(ELEMENT_ADD_PERMISSION);
		userGroup.selectGroup(group, true);
		Utils.pause(1000);
		click(By.linkText(member));
		Utils.pause(1000);
		assert getValue(ELEMENT_PERMISSION_TEXTBOX).contains(member):"Set permission is not true";

		setViewPreferenceForDriver(viewOption);

		//Select Allow Folder Creation
		select(ELEMENT_ALLOW_CREATE_FOLDER, "Content Folder");

		//Allowance node type on left tree
		type(By.id("allowNodeTypesOnTree"), "*", true);

		//Choose applied view
		click(ELEMENT_APPLY_VIEW_TAB);
		selectCheckBoxList(view);

		//Complete add new drive
		click(button.ELEMENT_SAVE_BUTTON);
		select(By.xpath("//select[contains(@id,'maxPageSize')]"),"20");
		waitForElementPresent(ELEMENT_DRIVER);
		assert isElementPresent(ELEMENT_DRIVER):"Add new driver is unsuccessful";
		info("Create new driver is successful");
	} 

	//Set a view preference for Drive
	public void setViewPreferenceForDriver(String viewOption){
		Utils.pause(1000);
		String[] temp = viewOption.split("/");
		if (temp.length != 0){
			for (int i=0; i < temp.length ; i++ ){
				if (temp[i].equals("Referenced Documents")){
					((JavascriptExecutor) driver).executeScript("document.getElementById('viewPreferences').click();");
				}
				if (temp[i].equals("Non-document Nodes")){
					((JavascriptExecutor) driver).executeScript("document.getElementById('viewNonDocument').click();");
				}
				if (temp[i].equals("Sidebar")){
					((JavascriptExecutor) driver).executeScript("document.getElementById('viewSideBar').click();");
				}
				if (temp[i].equals("Hidden Nodes")){
					((JavascriptExecutor) driver).executeScript("document.getElementById('showHiddenNode').click();");
				}
			}
		}
		Utils.pause(1000);
	}

	//function to delete drive on manage drive
	public void deleteDriver(String driver){
		By ELEMENT_DRIVER = By.xpath("//*[@title='" + driver + "']");
		ecMain.goToManageDriver();
		info("Delete driver");
		click(ELEMENT_DRIVE_DELETE.replace("${DATA_DRIVE_NAME}", driver));
		alt.acceptAlert();
		waitForElementNotPresent(ELEMENT_DRIVER);
		info("Delete driver successfully");
	}

}
