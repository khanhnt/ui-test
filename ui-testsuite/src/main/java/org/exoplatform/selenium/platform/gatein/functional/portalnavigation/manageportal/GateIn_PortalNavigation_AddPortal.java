package org.exoplatform.selenium.platform.gatein.functional.portalnavigation.manageportal;

import java.util.Map;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;
import org.exoplatform.selenium.platform.PortalManagement;

public class GateIn_PortalNavigation_AddPortal extends PlatformBase{
	
	ManageAccount account;
	NavigationToolbar navigation;
	PageEditor pageEditor;
	PageManagement page;
	NavigationManagement naviManage;
	PortalManagement portal;
	String MESSAGE_PORTAL_NAME_REQUIRED = "The field \"Portal Name\" is required." ;
	String MESSAGE_PORTAL_NAME_SPECIAL_CHARACTERS = "The \"Portal Name\" field must start with a letter and must not contain special characters.";
	String MESSAGE_PORTAL_NAME_EXISTS = "This portal name already exists.";
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		pageEditor = new PageEditor(driver);
		page = new PageManagement(driver);
		naviManage = new NavigationManagement(driver);
		portal = new PortalManagement(driver);
		driver.manage().window().maximize();
	}
	
	@AfterMethod
	public void afterTest(){
		driver.quit();
		
	}
	
	/*--Case 004 Portal\Portal Navigation\Add
	 	Create new portal with valid value 
	 --*/
	
	@Test
	public void test02_AddNewPortalWithValidValue(){
		String portalName   = "demoPortal"; 
		String portalLocale = "English";
		String portalSkin   = "Default"; 
		String portalSession = "On Demand";
	    boolean publicMode = true;
	    Map<String, String> permissions = null;   
	    String groupId    = "Platform/Administration"; 
	    String membership = "manager" ;
		
		account.signIn("root", "gtn");
		navigation.goToPortalSites();
		
		portal.addNewPortal(portalName, portalLocale, portalSkin, portalSession, 
				publicMode, permissions, groupId, membership);
		
		waitForTextPresent(portalName);
		
		portal.deletePortal(portalName);
		
		account.signOut();			
	}
	
	/*--Case 005 Portal\Portal Navigation\Add
	    Create new portal with blank name
	 --*/
	@Test
	public void test03_AddNewPortalWithBlankName(){
		
		String portalName   = ""; 
		String portalLocale = "English";
		String portalSkin   = "Default"; 
		String portalSession = "On Demand";
	    boolean publicMode = true;
	    Map<String, String> permissions = null;   
	    String groupId    = "Platform/Administration"; 
	    String membership = "manager" ;
		
		account.signIn("root", "gtn");
		navigation.goToPortalSites();
		
		portal.addNewPortal(portalName, portalLocale, portalSkin, portalSession, 
				publicMode, permissions, groupId, membership);
		
		waitForMessage(MESSAGE_PORTAL_NAME_REQUIRED);
		closeMessageDialog();
		cancel() ;
		account.signOut();		
	}
	
	/*--Case 006 Portal\Portal Navigation\Add
	 * Create new portal with name starts with number
	 * --*/
	@Test
	public void test04_AddNewPortalWithNameStartWithNumber(){
		
		String portalName   = "123exo"; 
		String portalLocale = "English";
		String portalSkin   = "Default"; 
		String portalSession = "On Demand";
	    boolean publicMode = true;
	    Map<String, String> permissions = null;   
	    String groupId    = "Platform/Administration";  
	    String membership = "manager" ;
		
		account.signIn("root", "gtn");
		navigation.goToPortalSites();
		
		portal.addNewPortal(portalName, portalLocale, portalSkin, portalSession, 
				publicMode, permissions, groupId, membership);
		
		waitForMessage(MESSAGE_PORTAL_NAME_SPECIAL_CHARACTERS);
		closeMessageDialog();
		cancel() ;
		account.signOut();			
	}
	
	/*--Case 010 Portal\Portal Navigation\Add
	 * Create new portal with name is the same with existing one
	 * --*/
	@Test
	public void test08_AddNewPortalWithNameIsTheSameWithExistingOne(){
		String portalName   = "demoPortal"; 
		String portalLocale = "English";
		String portalSkin   = "Default"; 
		String portalSession = "On Demand";
	    boolean publicMode = true;
	    Map<String, String> permissions = null;   
	    String groupId    = "Platform/Administration"; 
	    String membership = "manager" ;
		
		account.signIn("root", "gtn");
		navigation.goToPortalSites();
		
		portal.addNewPortal(portalName, portalLocale, portalSkin, portalSession, 
				publicMode, permissions, groupId, membership);
		
		waitForTextPresent(portalName);
		
		portal.addNewPortal(portalName, portalLocale, portalSkin, portalSession, 
				publicMode, permissions, groupId, membership);
		
		waitForMessage(MESSAGE_PORTAL_NAME_EXISTS);
		closeMessageDialog();
		cancel(); 
		portal.deletePortal(portalName);
		
		account.signOut();	
	}
	
	/*--Case 011 Portal\Portal Navigation\Add
	 * Create new portal with portal name the same with 
	 * existing but different by lower/upper case
	 * --*/
	@Test
	public void test09_AddNewPortalWithTheSameNameWithExistingButDiffByLowerUpperCase(){
		String portalName   = "test011_demo_portal"; 
		String portalLocale = "English";
		String portalSkin   = "Default"; 
		String portalSession = "On Demand";
	    boolean publicMode = true;
	    Map<String, String> permissions = null;   
	    String groupId    = "Platform/Administration"; 
	    String membership = "manager" ;
		
		account.signIn("root", "gtn");
		navigation.goToPortalSites();
		
		portal.addNewPortal(portalName, portalLocale, portalSkin, portalSession, 
				publicMode, permissions, groupId, membership);
		
		waitForTextPresent(portalName);
		
		info("-- Add new portal with name is the same with existing one but different with existing by upper case --");
		portal.addNewPortal("TEST011_DEMO_PORTAL", portalLocale, portalSkin, portalSession, 
				publicMode, permissions, groupId, membership);
		
		waitForTextPresent("TEST011_DEMO_PORTAL");
		
		info("--Delete portal with name is lower case--");
		portal.deletePortal(portalName);
		
		info("--Delete portal with name is upper case--");
		portal.deletePortal("TEST011_DEMO_PORTAL");
		
		account.signOut();	
	}
}