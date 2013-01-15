package org.exoplatform.selenium.platform.gatein.functional.dashboard.managegadget;

import org.exoplatform.selenium.platform.DashBoard;
import org.exoplatform.selenium.platform.NavigationToolbar;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author NhungVT
 *@date: 28/09/2012
 */

public class GateIn_Dashboard_ManageGadget_DeleteGadget extends PlatformBase 
{
	ManageAccount account;
	NavigationToolbar navigation;
	DashBoard dashboard;
	//Define data
	public String GADGET_NAME = "Calendar";
	public String GADGET_TITLE_DISPLAY = "Calendar";
	public By GADGET_DIRECTORY_LIST = By.xpath("//div[@class='UIPopupWindow UIDragObject NormalStyle']");
	public By AGENDA_GADGET_ON_LIST = By.id("Gadgets/Calendar");
			//By.xpath("//div[@class='GadgetTitle' and @title='"+GADGET_NAME+"']");

	public void deleteGadgetOnDashboard(String gadgetTitleDisplay)
	{
		String action = "Delete Gadget";
		By deleteGadgetIcon = By.xpath("//span[text()='"+gadgetTitleDisplay+"']/preceding::span[@title='"+action+"']");
		waitForAndGetElement(deleteGadgetIcon);
		click(deleteGadgetIcon);
		waitForConfirmation("Are you sure to delete this gadget?");
		waitForTextNotPresent(gadgetTitleDisplay);
	}
	
	@BeforeMethod
	public void beforeTest()
	{
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		dashboard = new DashBoard(driver);
		account.signIn("root", "gtn");
	}
	
	//Delete gadget with  deleting confirmation
	@Test(groups={"pending"})
	public void test01_DeleteGadgetWithConfirmation()
	{		
		//Goto DashBoard
		navigation.goToDashboard();
		waitForTextPresent(dashboard.MESSAGE_DRAG_GADGETS_HERE);

		//Click on Add Gadgets link
		waitForElementPresent(dashboard.ELEMENT_ADD_GADGETS_LINK);
		click(dashboard.ELEMENT_ADD_GADGETS_LINK);
		waitForElementPresent(GADGET_DIRECTORY_LIST);

		By b = By.xpath("//div[@class='UIColumns ClearFix']");
		dragAndDropToObject(AGENDA_GADGET_ON_LIST, b);
		//actions.dragAndDropBy(e, 10, 10).build().perform();
		
		//click(ELEMENT_CLOSE_WINDOW);
		waitForTextPresent(GADGET_TITLE_DISPLAY);
		
		//Delete data
		deleteGadgetOnDashboard(GADGET_TITLE_DISPLAY);
	}
	
	@AfterMethod
	public void afterTest()
	{
		account.signOut();
		driver.quit();
	}
}