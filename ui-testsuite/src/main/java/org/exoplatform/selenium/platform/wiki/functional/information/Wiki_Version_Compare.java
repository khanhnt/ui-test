package org.exoplatform.selenium.platform.wiki.functional.information;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Version;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test cases: KS/Wiki/Version/Compare
 * @author vuna2
 * <li>Date: Dec 17, 2012</li>
 */
public class Wiki_Version_Compare extends Version{
	ManageAccount magAcc;
	
	public String admin = "john";
	public String pass = "gtn";

	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		magAcc.signIn(admin, pass);
	}

	@AfterMethod
	public void afterMethods(){
		info("-- user.Logout --");
		//signOut();
		driver.quit();
	}

	/**
	 * Case ID 01
	 * <li>Compare version when select 2 versions</li>
	 * <li> Step 1: Create new page </li>
	 * <li> Step 2: Add version for page </li>
	 * <li> Step 3: Add more version for page </li>
	 * <li> Step 4: Open form to view all version of page</li>
	 * <li> Step 5: Compare versions</li>
	 */
	@Test
	public void test01_CompareVersionWhenSelect2Versions(){
		String[] pageInfo = {"compare 01", "page content 01"};

		goToWiki();

		addBlankWikiPage(pageInfo[0], pageInfo[1], 0);

		editWikiPage("1st edit compare 01", "1st edit page content", 0);

		editWikiPage("2nd edit compare 01", "2nd edit page content", 0);

		goToRevisionsPage();

		waitForElementPresent(ELEMENT_CURRENT_VERSION.replace("${version}", "3"));

		compareVersion("1", "3");

		Utils.captureScreen("FNC_KS_WIKI_VERSION_COMPARE_01");

		goToWikiPage("Wiki Home/2nd edit compare 01");

		deleteCurrentWikiPage();
	}

	/**
	 * Case ID 02
	 * <li>Compare version when select only 1 version</li>
	 * <li>Step 1: Create new page</li>
	 * <li>Step 2: Add version for page</li>
	 * <li>Step 3: Add more version for page</li>
	 * <li>Step 4: Open form to view all version of page</li>
	 * <li>Step 5: Compare versions</li>
	 */
	@Test
	public void test02_CompareVersionWhenSelectOnly1Version(){
		String[] pageInfo = {"compare 02", "page content 02"};

		goToWiki();

		addBlankWikiPage(pageInfo[0], pageInfo[1], 0);

		editWikiPage("1st edit compare 02", "1st edit page content", 0);

		editWikiPage("2nd edit compare 02", "2nd edit page content", 0);

		goToRevisionsPage();

		waitForElementPresent(ELEMENT_CURRENT_VERSION.replace("${version}", "3"));

		click(ELEMENT_VERSION_CHECKBOX.replace("{$version}", "2"), 2);

		waitForElementPresent(ELEMENT_DISABLE_COMPARE_BUTTON);

		deleteCurrentWikiPage();
	}

	/**
	 * Case ID 03
	 * <li>Compare version when do not select any version</li>
	 * <li>Step 1: Create new page</li>
	 * <li>Step 2: Add version for page</li>
	 * <li>Step 3: Add more version for page</li>
	 * <li>Step 4: Open form to view all version of page</li>
	 * <li>Step 5: Compare versions</li>
	 */
	@Test
	public void test03_CompareVersionWhenDoNotSelectAnyVersion(){
		String[] pageInfo = {"compare_03", "page content 03"};

		goToWiki();

		addBlankWikiPage(pageInfo[0], pageInfo[1], 0);

		editWikiPage("1st edit compare 03", "1st edit page content", 0);

		editWikiPage("2nd edit comapre 03", "2nd edit page content", 0);

		goToRevisionsPage();

		waitForElementPresent(ELEMENT_CURRENT_VERSION.replace("${version}", "3"));

		waitForElementPresent(ELEMENT_DISABLE_COMPARE_BUTTON);

		deleteCurrentWikiPage();
	}	
}
