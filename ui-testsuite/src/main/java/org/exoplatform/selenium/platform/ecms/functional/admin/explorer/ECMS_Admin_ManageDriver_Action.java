package org.exoplatform.selenium.platform.ecms.functional.admin.explorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageDriver;
import org.exoplatform.selenium.platform.ecms.admin.Permission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/*
 * @author: Lientm
 * @date: 9/10/2012
 */
public class ECMS_Admin_ManageDriver_Action extends PlatformBase{

	//Platform
	NavigationToolbar nav;
	ManageAccount magAcc;
	UserGroupManagement userGrp;
	Button button;
	ManageAlert alt;

	//Ecms
	EcmsBase ecms;
	ECMainFunction ecMain;
	SitesExplorer sitesExp;
	Permission adminPer;
	ManageDriver magDrv;

	public final String DATA_USER = "john";
	public final String DATA_PASS = "gtngtn";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with "+ DATA_USER);
		nav = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		userGrp = new UserGroupManagement(driver);
		button = new Button(driver);
		alt = new ManageAlert(driver);
		ecms = new EcmsBase(driver); 
		ecMain = new ECMainFunction(driver); 
		sitesExp = new SitesExplorer(driver);
		adminPer = new Permission(driver);
		magDrv = new ManageDriver(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}


	/*case01+02+03: Add drive
	 * go to add drive
	 * add new drive
	 * edit drive
	 * delete drive
	 */
	@Test
	public void test01_02_03_AddEditDeleteDrive(){
		String DATA_DRIVE_NAME = "ECMS_Admin_ContentPresentation_ManageDriver_Action_01";
		By ELEMENT_DRIVE = By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", DATA_DRIVE_NAME));

		//go to add drive
		info("Go to add new drive");
		ecMain.goToManageDriver();
		magDrv.addNewDriver(DATA_DRIVE_NAME,"dms-system","exo:ecm", "Organization/Management/Executive Board","member", 
				"Non-document Nodes/Sidebar", "Admin/Icons/List");

		//edit drive
		info("Edit drive");
		click(magDrv.ELEMENT_DRIVE_EDIT.replace("${DATA_DRIVE_NAME}", DATA_DRIVE_NAME));
		waitForElementPresent(magDrv.ELEMENT_DRIVE_EDIT_POPUP);
		//--select workspace
		select(magDrv.ELEMENT_WORKSPACE, "system");
		click(magDrv.ELEMENT_ADD_PATH);
		waitForElementPresent(magDrv.ELEMENT_ADD_PATH_POPUP);
		click(magDrv.ELEMENT_JCR_SYSTEM);
		//--select permission
		click(magDrv.ELEMENT_ADD_PERMISSION);	
		click(By.linkText("manager"));
		assert getValue(magDrv.ELEMENT_PERMISSION_TEXTBOX).contains("manager:/organization/management/executive-board"):"Set permission is not true";
		select(magDrv.ELEMENT_ALLOW_CREATE_FOLDER, "Document Folder");
		//--add apply view
		click(magDrv.ELEMENT_APPLY_VIEW_TAB);
		ecms.selectCheckBoxList("Web");
		click(button.ELEMENT_SAVE_BUTTON);

		//delete drive
		info("Delete drive");
		click(magDrv.ELEMENT_DRIVE_DELETE.replace("${DATA_DRIVE_NAME}", DATA_DRIVE_NAME));
		alt.acceptAlert();
		waitForElementNotPresent(ELEMENT_DRIVE);
		info("Delete driver successfully");
	}
}
