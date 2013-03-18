package org.exoplatform.selenium.platform.wiki.functional.search;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.BasicAction;

/**
 *
 * @author HangNTT
 * @date: 13/12/2012
 */
public class Wiki_Advanced_Search extends BasicAction {

	ManageAccount magAc;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", "gtngtn");
	}
	
	//Search when the keyword is matched
	@Test
	public void test01_SearchWhenKeyWordIsMatched() {
		
		String PAGE_NAME1 = "Wiki_Advanced_Search_01";

		By ELEMENT_PAGE1 = By.xpath(ELEMENT_RESULT_SEARCH.replace("${pageName}", PAGE_NAME1));

		goToWiki();

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		advancedSearch(PAGE_NAME1, "intranet");

		click(ELEMENT_PAGE1);

		deleteCurrentWikiPage();
	}
	
	//Search when the keyword is not matched
	@Test
	public void test02_SearchWhenKeyWordIsNotMatched() {

		String PAGE_NAME1 = "Wiki_Advanced_Search_02";

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		goToWiki();

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		advancedSearch("bbb", "intranet");

		//waitForTextPresent(ELEMENT_VERIFY_MESSAGE);
		waitForElementPresent(ELEMENT_VERIFY_RESULT_SEARCH.replace("${pageName}", "bbb"), DEFAULT_TIMEOUT, 1, 2);
			
		click(ELEMENT_PAGE1);

		deleteCurrentWikiPage();
	}
	
	//View content of search result when user does not have permission to view page
	@Test
	public void test03_ViewContentOfSearchResultWhenUserDoesNotHavePermissionToViewPage() {

		String[] user1= {"james"};

		String PAGE_NAME1 = "Wiki_Advanced_Search_03";

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		goToWiki();

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		deletePagePermission("any");

		addPagePermission(0,user1);

		editPagePermission("james", false,false, false, 2);

		magAc.signOut();

		magAc.signIn("james", "gtngtn");

		goToWiki();

		advancedSearch(PAGE_NAME1, "intranet");

		//waitForTextPresent(ELEMENT_VERIFY_MESSAGE);
		waitForElementPresent(ELEMENT_VERIFY_RESULT_SEARCH.replace("${pageName}", PAGE_NAME1), DEFAULT_TIMEOUT, 1, 2);

		magAc.signOut();

		magAc.signIn("john", "gtngtn");

		goToWiki();

		click(ELEMENT_PAGE1);

		deleteCurrentWikiPage();
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
