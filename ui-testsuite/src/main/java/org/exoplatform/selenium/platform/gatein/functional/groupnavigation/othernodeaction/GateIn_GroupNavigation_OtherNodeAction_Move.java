package org.exoplatform.selenium.platform.gatein.functional.groupnavigation.othernodeaction;

/**
 *@author HangNTT
 * @date: 24/09/2012
 */

import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.exoplatform.selenium.TestLogger.*;

public class GateIn_GroupNavigation_OtherNodeAction_Move extends PlatformBase
{
	ManageAccount account;
	NavigationToolbar navigation;
	NavigationManagement naviManage;
	PageManagement page;
	GroupNavigation groupNavi;
	By ELEMENT_EDIT_NAV_GROUP = null;
	String MOVE_UP_LINK = "Move Up";
	String MOVE_DOWN_LINK = "Move Down";
	By PORTAL_ADMINISTRATION = By.xpath("//a[contains(text(),'Portal Administration')]");
	By GROUP_NAVIGATION = By.xpath("//a[contains(text(),'Group Navigation')]");
	By APP_REGISTRY_POSITION = By.xpath("//div[1]/div/a[text()='Application Registry']");
	By MANAGEMENT_PAGE_OLD_POSITION = By.xpath("//div[2]/div/a[text()='Page Management']");
	By SERVICES_MANAGE_OLD_POSITION = By.xpath("//div[3]/div/a");
	By SERVICES_MANAGE_NEW_POSITION = By.xpath("//div[4]/div/a[text()='Services Management']");
	By MANAGEMENT_PAGE_NEW_POSITION = By.xpath("//div[1]/div/a[text()='Page Management']");
	By SITE_EX_IMPORT_POSITION = By.xpath("//div[4]/div/a[text()='Site Export/Import']");

	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		driver.get(baseUrl);
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		naviManage = new NavigationManagement(driver);
		page = new PageManagement(driver);
		groupNavi = new GroupNavigation(driver);
		account.signIn("root", "gtn");
		driver.manage().window().maximize();
		ELEMENT_EDIT_NAV_GROUP= By.xpath(groupNavi.ELEMENT_GROUP_EDIT_NAVIGATION.replace("{$navigation}", "Administration"));
	}

	//Change order of node in case Move Up or Move Down
	@Test(groups={"later"})
	public void test01_ChangeNodeOrder()
	{
		info("-START test01_ChangeNodeOrder");

		//Goto Edit Navigation
		info("---Goto Edit Navigation of Group");
		navigation.goToGroupSites();

		//Click on Edit Navigation of group
		info("---Click on Edit Navigation of Group");
		click(ELEMENT_EDIT_NAV_GROUP);

		info("---Verify can't Move Up order of first node");

		//Verify position of node Application Registry
		info("-----Verify position of node Application Registry before Move Up");
		waitForElementPresent(APP_REGISTRY_POSITION);
		info("Get position of Application Registry failed");

		info("-----Click on Move Up item of node Management");
		//Right click on node PORTAL_ADMINISTRATION
		rightClickOnElement(APP_REGISTRY_POSITION);

		//Click on Move Up item
		click(By.linkText(MOVE_UP_LINK));

		//Verify nothing happens ~ position of node Application Registry is not changed
		info("-----Verify position of node Application Registry is not changed after Moving Up");
		waitForElementPresent(APP_REGISTRY_POSITION);

		info("---Verify can't Move Down order of last node");

		//Verify position of node  Export Import
		info("-----Verify position of node  Export Import before Moving Down");
		waitForElementPresent(SITE_EX_IMPORT_POSITION);

		info("-----Click on Move Down item of node  Export Import");
		//Right click on node  Export Import
		rightClickOnElement(SITE_EX_IMPORT_POSITION);

		//Click on Move Down item of node  Export Import
		click(By.linkText(MOVE_DOWN_LINK));

		//Verify position of GROUP_NAVIGATION is not changed
		info("-----Verify position of node Export Import is changed after Moving Down");
		waitForElementPresent(SITE_EX_IMPORT_POSITION);

		info("---Verify user can move down node which is not last node");

		//Verify position of node Services MANAGEMENT
		info("-----Verify position of node Services MANAGEMENT before Move Down");
		waitForElementPresent(SERVICES_MANAGE_OLD_POSITION);

		info("-----Click on Move Down of node Services MANAGEMENT");
		//Right click on node
		rightClickOnElement(SERVICES_MANAGE_OLD_POSITION);

		//Click on Move Down item
		click(By.linkText(MOVE_DOWN_LINK));

		//Verify nothing happens ~ position of node Services MANAGEMENT is not changed
		info("-----Verify position of node Services MANAGEMENT is not changed after Moveing Down");
		waitForElementPresent(SERVICES_MANAGE_NEW_POSITION);

		info("---Verify Moving Up node is not first node");

		//Verify position of node Page Management
		info("-----Verify position of node Page Management before Moving Up");
		waitForElementPresent(MANAGEMENT_PAGE_OLD_POSITION);

		info("-----Click on Move Up item of node Page Management");
		//Right click on node Page Management
		rightClickOnElement(MANAGEMENT_PAGE_OLD_POSITION);

		//Click on Move Up item of node Page Management
		click(By.linkText(MOVE_UP_LINK));

		//Verify position of Page Management is changed
		info("-----Verify position of node Page Management is changed after Move Up");
		waitForElementPresent(MANAGEMENT_PAGE_NEW_POSITION);


		info("-END test01_ChangeNodeOrder");
	}

	@AfterMethod()
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}