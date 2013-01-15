package org.exoplatform.selenium.platform.gatein.functional.dashboard.managegadget;

import org.exoplatform.selenium.platform.DashBoard;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author NhungVT
 *@date: 28/09/2012
 */

public class GateIn_Dashboard_ManageGadget_EditGadget extends PlatformBase
{
	ManageAccount account;
	DashBoard dashboard;
	NavigationToolbar navigation;
	//Define data
	public String GADGET_NAME = "Calendar";
	public String GADGET_TITLE_DISPLAY = "Calendar";
	public By GADGET_DIRECTORY_LIST = By.xpath("//div[@class='UIPopupWindow UIDragObject NormalStyle']");
	public By BOOKMARKS_GADGET_ON_LIST = By.xpath("//div[@class='GadgetTitle' and @title='"+GADGET_NAME+"']");
	public By EDIT_ICON = By.xpath("//span[text()='"+GADGET_TITLE_DISPLAY+"']/preceding::span[@title='Edit Gadget']");
	public By MAXIMIZE_TO_DISPLAY_LIST = By.xpath("//select[contains(@id,m) and contains(@name, up_maxcount)]");
	public By OPTION_5 = By.xpath("//option[@value='5' and @selected='selected']");
	public By OPTION_10 = By.xpath("//option[@value='10' and @selected='selected']");
	public By SAVE_BUTTON = By.xpath("//input[@type='button' and @value='Save']");


	@BeforeMethod
	public void beforeTest()
	{
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		dashboard = new DashBoard(driver);
		navigation = new NavigationToolbar(driver);
		account.signIn("root", "gtn");
	}

	//Edit gadget preferences
	@Test(groups={"pending"})
	public void test01_EditGadgetPreference()
	{	Actions actions = new Actions(driver);
		//Goto DashBoard
		navigation.goToDashboard();
		waitForTextPresent(dashboard.MESSAGE_DRAG_GADGETS_HERE);

		//Click on Add Gadgets link
		waitForElementPresent(dashboard.ELEMENT_ADD_GADGETS_LINK);
		click(dashboard.ELEMENT_ADD_GADGETS_LINK);
		waitForElementPresent(GADGET_DIRECTORY_LIST);

		//Drag Bookmarks Gadget on list and Drop into Container
		actions.dragAndDropBy(waitForAndGetElement(BOOKMARKS_GADGET_ON_LIST), 2, 2).build().perform();
		waitForTextPresent(GADGET_NAME);
		waitForElementPresent(ELEMENT_CLOSE_WINDOW);
		click(ELEMENT_CLOSE_WINDOW);

		//Edit Bookmarks Gadget content
		waitForElementPresent(EDIT_ICON);
		click(EDIT_ICON);
		waitForElementPresent(OPTION_5);
		select(MAXIMIZE_TO_DISPLAY_LIST, "10");
		waitForElementPresent(SAVE_BUTTON);
		click(SAVE_BUTTON);
		pause(1000);
		//Verify edit gadget results and reset data
		waitForElementPresent(EDIT_ICON);
		click(EDIT_ICON);
		waitForElementPresent(OPTION_10);
		select(MAXIMIZE_TO_DISPLAY_LIST, "5");
		click(SAVE_BUTTON);

		//Delete data
		dashboard.deleteGadgetOnDashboard("Top voted rating topic");
	}

	@AfterMethod
	public void afterTest()
	{
		driver.quit();
	}
}
