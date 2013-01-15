package org.exoplatform.selenium.platform.gatein.functional.basicportlets.administration;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author NhungVT
 *@date: 25/09/2012
 */

public class GateIn_BasicPortlets_Administration_ApplicationRegistry_Display extends PlatformBase
{
	
	ManageAccount account;
	NavigationToolbar navigation;
	ManageApplications app;
	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		account.signIn("root", "gtn");
		app = new ManageApplications(driver);
	}
	
	//Show Application portlet when check or un-check Change show import
	@Test()
	public void test01_ShowImportApplication()
	{
		//goto Application
		navigation.goToApplicationRegistry();
		
		//Verify Categories display as default
		waitForElementPresent(app.ELEMENT_CATEGORIES_AREA_TITLE);
		
		//Import Application is shown as default
		waitForElementPresent(app.ELEMENT_IMPORT_APPLICATION);
		
		//goto Edit Portlet
		navigation.goToEditPageEditor();
		
		//Click on Edit Portlet icon
		mouseOver(app.ELEMENT_APPS_REG_PORTLET, false);
		click(ELEMENT_EDIT_PORTLET_ICON);
		
		//Verify Change Show Import checkbox is checked
		waitForHidenElementPresent(app.SHOW_IMPORT_CHECKED);
		
		//Select checkbox
		uncheck(app.ELEMENT_SHOW_IMPORT_CHECKBOX,false);
		save();
		close();
		
		//Verify Change Show Import checkbox is checked
		mouseOver(app.ELEMENT_APPS_REG_PORTLET, false);
		click(ELEMENT_EDIT_PORTLET_ICON);
		waitForHidenElementNotPresent(app.SHOW_IMPORT_CHECKED);
		close();
		
		//Click Finish
		click(ELEMENT_FINISH_ICON);
		
		//Verify Import Applications is shown
		waitForElementNotPresent(app.ELEMENT_IMPORT_APPLICATION);
		
		//Reset data
		navigation.goToEditPageEditor();
		mouseOver(app.ELEMENT_APPS_REG_PORTLET, false);
		click(ELEMENT_EDIT_PORTLET_ICON);
		waitForHidenElementNotPresent(app.SHOW_IMPORT_CHECKED);
		check(app.ELEMENT_SHOW_IMPORT_CHECKBOX,false);
		save();
		close();
		click(ELEMENT_FINISH_ICON);
		waitForElementPresent(app.ELEMENT_IMPORT_APPLICATION);
	}
	
	@AfterMethod()
	public void afterTest()
	{
		driver.quit();
	}
}