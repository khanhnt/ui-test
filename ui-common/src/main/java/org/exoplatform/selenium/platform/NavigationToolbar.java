package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class NavigationToolbar extends PlatformBase {

	public NavigationToolbar(WebDriver dr){
		driver = dr;
	} 


	//Go to portal sites
	public void goToPortalSites() {
		info("--Go to Portal Site Management--");
		waitForAndGetElement(By.xpath(ELEMENT_LINK_SETUP));
		mouseOver(ELEMENT_LINK_SETUP, false);
		Utils.pause(500);
		mouseOver(ELEMENT_LINK_PORTAL, false);
		Utils.pause(500);
		mouseOver(ELEMENT_LINK_SITES, false);
		click(ELEMENT_LINK_SITES);
		Utils.pause(500);
	}

	//Go to Portal Manage Pages	
	public void goToManagePages() {
		info("--Go to Portal Site Management--");
		//		waitForAndGetElement(By.xpath(ELEMENT_LINK_SETUP));
		for(;;){
			mouseOver(ELEMENT_LINK_SETUP, false);
			Utils.pause(500);
			if (waitForAndGetElement(ELEMENT_LINK_PORTAL,15000,0)!=null) {	
				mouseOver(ELEMENT_LINK_PORTAL, false);
				if (waitForAndGetElement(ELEMENT_LINK_PAGES,15000,0)!=null){
					click(ELEMENT_LINK_PAGES);
					break;
				}
			}
		}
	}

	//Go to Dashboard
	public void goToDashboard(){
		Actions actions = new Actions(driver);
		info("--Go to Dashboard page--");
		WebElement element = driver.findElement(By.id("UserNavigationTabsContainer"));
		actions.moveToElement(element).build().perform();
		driver.findElement(By.linkText("Dashboard")).click();	
	}

	//Go to User management page
	public void goToNewStaff() {
		info("Go to New Staff");
		//goToPage(ELEMENT_SEARCH_ICON_REGISTER, ELEMENT_LINK_SETUP, ELEMENT_LINK_USERS, ELEMENT_LINK_ADD_USERS);
		mouseOverAndClick(ELEMENT_LINK_SETUP);
		click(ELEMENT_LINK_USERS);
		Utils.pause(1000);
	}

	//Go to My Account
	public void goToMyAccount(){
		Actions actions = new Actions(driver);
		WebElement UI = driver.findElement(By.id("UserNavigationTabsContainer"));
		actions.moveToElement(UI).build().perform();
		driver.findElement(By.linkText("My Account")).click();	
		Utils.pause(500);
	}

	//Go to Portal/Group Sites
	public void goToGroupSites(){
		Actions actions = new Actions(driver);
		info("--Go to Portal Site Management--");
		waitForAndGetElement(By.xpath(ELEMENT_LINK_SETUP));
		mouseOver(ELEMENT_LINK_SETUP, false);
		Utils.pause(500);
		mouseOver(ELEMENT_LINK_PORTAL, false);
		Utils.pause(500);
		WebElement element;
		element = waitForAndGetElement(ELEMENT_LINK_GROUP);
		actions.moveToElement(element).click(element).build().perform();
		Utils.pause(500);
	}

	//Go to add page locator with Editor
	public void goToAddPageEditor(){
		Actions actions = new Actions(driver);
		waitForAndGetElement(By.xpath(ELEMENT_LINK_EDITOR));
		mouseOver(ELEMENT_LINK_EDITOR, true);
		Utils.pause(500);
		mouseOver(ELEMENT_LINK_EDITOR_PAGE, true);
		Utils.pause(500);
		WebElement element = waitForAndGetElement(ELEMENT_LINK_EDITOR_ADD_PAGE);
		actions.moveToElement(element).click(element).build().perform();
		Utils.pause(500);
	}

	public void goToUsersAndGroupsManagement() {
		info("--Go to Users and groups management--");
		//goToPage(ELEMENT_LINK_SETUP, ELEMENT_LINK_SETUP, ELEMENT_LINK_USERS, ELEMENT_GROUP_AND_ROLE_LINK);
		mouseOverAndClick(ELEMENT_LINK_SETUP);
		WebElement element = waitForAndGetElement(By.xpath(ELEMENT_GROUP_AND_ROLE_LINK), DEFAULT_TIMEOUT, 1, 2);		
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		Utils.pause(500);
	}

	//Go to Portal Application Registry
	public void goToApplicationRegistry() {
		info("--Go to Portal Application Registry--");
		mouseOver(ELEMENT_LINK_SETUP, false);
		Utils.pause(500);
		waitForElementPresent(ELEMENT_APPLICATIONS_LINK);
		click(ELEMENT_APPLICATIONS_LINK);
		Utils.pause(500);
	}

	public void goToEditPageEditor () {
		info("----Go to Edit page editor----");
		mouseOver(ELEMENT_MENU_EDIT_LINK,false);
		Utils.pause(500);
		mouseOver(ELEMENT_MENU_PAGE_LINK,false);
		Utils.pause(500);
		click(ELEMENT_MENU_EDIT_LAYOUT);
		Utils.pause(500);
	}

	//Go to change language for user interface
	public void goToChangeLanguageForUserInterface(){
		Actions actions = new Actions(driver);
		info("--Go to change language for user interface--");
		WebElement UI = driver.findElement(By.id("UserNavigationTabsContainer"));
		actions.moveToElement(UI).build().perform();
		driver.findElement(By.linkText("Change Language")).click();	
		Utils.pause(500);
	}

	//Go to register page in public mode
	public void goToRegisterPageInPublicMode(WebDriver driverTest){
		String registerPageLink = baseUrl.concat("/portal/intranet/Register");
		driverTest.get(registerPageLink);
		waitForTextPresent("Create a New Account");
	}

	// Go to content administration
	public void goToContentAdministration()
	{
		String url = DEFAULT_BASEURL + "/g/:platform:web-contributors/wcmAdmin";
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				driver.get(url);
				break;
			}
			mouseOverAndClick(ELEMENT_LINK_SETUP);
			if (waitForAndGetElement(ELEMENT_MENU_CONTENT_LINK, 5000, 0)!= null) {	
				mouseOver(ELEMENT_MENU_CONTENT_LINK, true);
				if (waitForAndGetElement(ELEMENT_LINK_CONTENT_ADMIN, 5000, 0)!= null){
					click(ELEMENT_LINK_CONTENT_ADMIN);
					break;
				}
			}
			info("Retry...[" + repeat + "]");
		}
		waitForTextPresent("Manage ECM Main Functions");
	}

	//Enter Sites Management Form 
	public void goToSiteExplorer(){
		Utils.pause(1000);
		mouseOverAndClick(ELEMENT_LINK_SETUP);
		mouseOverAndClick(ELEMENT_MENU_CONTENT_LINK);
		//click(ELEMENT_MENU_SITE_EXPLORER);
		Utils.pause(1000);
	}

	//Go to Page Creation Wizard
	public void goToPageCreationWinzard(){
		mouseOver(ELEMENT_MENU_EDIT_LINK,true);
		mouseOver(ELEMENT_MENU_PAGE_LINK,true);
		click(ELEMENT_MENU_ADD_PAGE_LINK);	
		Utils.pause(1000);
	}

	//Go To Content Administration / Advanced Configuration / Manage Lock Tab
	/*public void goToLockTabInContentAdmin(){
		goToContentAdministration();
		click(ELEMENT_ADVANCED_CONFIGURATION_TAB);
		click(ELEMENT_MANAGE_LOCKS);
		click(ELEMENT_MANAGE_LOCK_TAB);
		Utils.pause(1000);
	}*/

	//Function to go to SEO management
	public void goToSeoManagement(){
		info("Go to SEO management form");
		mouseOver(ELEMENT_MENU_EDIT_LINK, true);
		mouseOver(ELEMENT_MENU_PAGE_LINK, true);
		mouseOverAndClick(ELEMENT_MENU_SEO_LINK);	
		Utils.pause(1000);
	}
}
