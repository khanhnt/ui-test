package org.exoplatform.selenium.platform.gatein.functional.basicportlets.administration;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;

public class GateIn_BasicPortlets_Administration_ApplicationRegistry_Gadget extends PlatformBase {
	
	ManageApplications app;
	ManageAccount account;
	NavigationToolbar navigation;
	public static By OK_BUTTON = By.linkText("OK");
	
	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		navigation = new NavigationToolbar(driver);
		account = new ManageAccount(driver);
		app = new ManageApplications(driver);
	}

	@AfterMethod
	public void afterTest(){
		account.signOut();
		driver.quit();
	}

	@Test
	public void test02_AddRemoteGadgetWithValidUrl () {
		String DATA_URL = "http://www.labpixies.com/gadgads/gumtree/gumtree.xml";
		By DATA_GADGET_LINK = By.xpath("//a[@title='Gumtree - Londons free classifieds']");
		String DATA_GADGET_NAME = "Gumtree - Londons free classifieds";

		account.signIn("root", "gtn");
		navigation.goToApplicationRegistry();
		click(app.ELEMENT_GADGET_LINK);
		app.addRemoteGadget(DATA_URL);
		debug("Verify new gadget is added");
		waitForElementPresent(DATA_GADGET_LINK);

		debug("Delete gadget");
		app.deleteGadget(DATA_GADGET_NAME);
	}

	@Test
	public void test05_AddRemoteGadgetWithExistingUrl () {
		String DATA_URL = "http://www.labpixies.com/campaigns/todo/todo.xml";
		By DATA_GADGET_LINK = By.xpath("//a[@title='To-Do List']");
		String DATA_GADGET_NAME = "To-Do List";
		String MESSAGE_URL_EXIST = "This url is existing, please select another one!";

		account.signIn("root", "gtn");
		navigation.goToApplicationRegistry();
		click(app.ELEMENT_GADGET_LINK);
		app.addRemoteGadget(DATA_URL);
		debug("Verify new gadget is added");
		waitForElementPresent(DATA_GADGET_LINK);

		debug("Add gadget with URL existing");
		app.addRemoteGadget(DATA_URL);
		waitForMessage(MESSAGE_URL_EXIST);
		click(OK_BUTTON);

		debug("Delete gadget");
		app.deleteGadget(DATA_GADGET_NAME);	
	}
}