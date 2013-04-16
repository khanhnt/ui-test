package org.exoplatform.selenium.platform.wiki.functional.basicaction;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * 
 * @author HangNTT
 * @date: 7/12/2012
 */
public class Wiki_BasicAction_Delete extends BasicAction {
	ManageAccount magAcc;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		magAcc.signIn("john", "gtn");
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	//Delete a wiki page with without confirm and confirm message
	@Test
	public void test01_02_deletePageWithoutConfirmAndConfirmMessage() {
		
		By ELEMENT_PAGE1 = By.linkText("wiki1");
		
		goToWiki();

		addBlankWikiPage("wiki1", "wiki1", 0);
		
		click(ELEMENT_PAGE1);
		
		deleteCurrentWikiPage(true);
		
		waitForElementPresent(ELEMENT_PAGE1);
		
		deleteCurrentWikiPage();
		
		waitForElementNotPresent(ELEMENT_PAGE1);
	}
}
