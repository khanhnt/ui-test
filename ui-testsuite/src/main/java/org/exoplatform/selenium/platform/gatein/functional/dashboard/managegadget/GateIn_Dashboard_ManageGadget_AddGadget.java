package org.exoplatform.selenium.platform.gatein.functional.dashboard.managegadget;

import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.exoplatform.selenium.platform.DashBoard;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;

/**
 *@author NhungVT
 *@date: 28/09/2012
 */

public class GateIn_Dashboard_ManageGadget_AddGadget extends PlatformBase
{
	ManageAccount account;
	DashBoard dashboard;
	NavigationToolbar navigation;
	//Define data
	public String GADGET_NAME = "Hangman";
	public By MAXIMIZE_ICON = By.xpath("//span[text()='"+ GADGET_NAME+"']/preceding::span[@title='Maximize']");
	public String GADGET_URI = "http://www.labpixies.com/campaigns/hangman/hangman.xml";
	
	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		dashboard = new DashBoard(driver);
		navigation = new NavigationToolbar(driver);
		account.signIn("john", "gtn");
	}
	
	
	//Add new gadget into dashboard with valid value
	@Test()
	public void test01_AddValidGadgetIntoDashboard()
	{
		//Goto DashBoard
		navigation.goToDashboard();
		waitForTextPresent(dashboard.MESSAGE_DRAG_GADGETS_HERE);
		
		//Click on Add Gadgets link
		waitForElementPresent(dashboard.ELEMENT_ADD_GADGETS_LINK);
		click(dashboard.ELEMENT_ADD_GADGETS_LINK);
		
		//Add "http://www.labpixies.com/campaigns/hangman/hangman.xml" into Gadget list
		type(dashboard.ELEMENT_GADGET_URL_INPUT, GADGET_URI, true);
		click(dashboard.ELEMENT_ADD_GADGET_BUTTON);
		
		waitForElementPresent(dashboard.ELEMENT_CLOSE_DASHBOARD);
		click(dashboard.ELEMENT_CLOSE_DASHBOARD);
		
		//Verify Gadget is added into Dashboard
		waitForTextPresent(GADGET_NAME);
		waitForElementPresent(MAXIMIZE_ICON);
		
		//Open Hangman Gadget
		click(MAXIMIZE_ICON);
		waitForElementNotPresent(MAXIMIZE_ICON);
		/*--- After adding a gadget on Dashboard, not display icon edit, minimize, delete gadget
		 *--- Must refresh browser or open this Gadget --> These icon are displayed */ 
		
		//Delete Gadget
		dashboard.deleteGadgetOnDashboard(GADGET_NAME);
	}
	
	@AfterMethod()
	public void afterTest()
	{
		driver.quit();
	}
}
