package org.exoplatform.selenium.platform.gatein.functional.portalnavigation.edit.editnavigation.othernodeaction;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.exoplatform.selenium.TestLogger.*;
import org.exoplatform.selenium.platform.NavigationManagement;


public class GateIn_PortalNavigation_Edit_EditNavigation_OtherNodeActions_CopyPaste extends PlatformBase 
{
	//Define Data
	ManageAccount account;
	NavigationToolbar navigation;
	PageEditor pageEditor;
	PageManagement page;
	NavigationManagement naviManage;
	public String UP_LEVEL = "//a[contains(@title,'Up Level')]";
	public String ADD_NODE_BUTTON = "//a[contains(text(),'Add Node')]";
	public String NODE_NAME_TEXTBOX = "name";
	public String CLOSE_NAVIGATION = "//a[contains(@title,'Close Window')]";
	public String CHILD_NODE = "(//a[contains(@title,'Node1')])[2]";
	public String EDIT_INTRANET_NAVIGATION = ELEMENT_EDIT_NAVIGATION.replace("${navigation}", "intranet");
	public String EDIT_ACME_NAVIGATION = ELEMENT_EDIT_NAVIGATION.replace("${navigation}", "acme");
	//Global variables
	public String FIRST_NODE = "";
	public String SECOND_NODE = "";
//	public WebElement ELEMENT = null;

	//Messages
	
	public String SAME_PLACE_MESSAGE = "This node name already exists.";

	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseUrl);
		account = new ManageAccount(driver);
		navigation = new NavigationToolbar(driver);
		pageEditor = new PageEditor(driver);
		page = new PageManagement(driver);
		naviManage = new NavigationManagement(driver);
		account.signIn("root", "gtn");
	}

	//Define using methods
	public void addNode(String addNodeButton, String nodeNameTextbox, String nodeNameInput)
	{
		//Click on Add Node Button
		click(addNodeButton);
		type(By.name(nodeNameTextbox), nodeNameInput, false);
		
		//Click button Save on Add Node screen
		save();
		waitForTextNotPresent("Page Node Setting");
		//Click button Save on Navigation Management screen
		save();
		pause(1000);
	}

	//Copy/Paste a node into another node in the same navigation
	@Test()
	public void test01_CopyPasteNodeInSameNavigation()
	{
		info("-START test01_CopyPasteNodeInSameNavigation");
		FIRST_NODE = "Node1";
		SECOND_NODE = "Node2";
		//Goto Edit Navigation 
		info("---Goto Edit Navigation");
		navigation.goToPortalSites();

		//Click on Edit Navigation of intranet
		info("---Click on Edit Navigation of Intranet");
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", "intranet"));

		info("---Add Test Data");

		//Add Node1
		info("-----Add Node1");
		waitForElementPresent(By.xpath(UP_LEVEL));
		click(UP_LEVEL);
		
		addNode(ADD_NODE_BUTTON, NODE_NAME_TEXTBOX, FIRST_NODE);
		pause(1000);

		//Click on Edit Navigation of intranet
		info("---Click on Edit Navigation of intranet");
		click(EDIT_INTRANET_NAVIGATION);

		//Add Node2
		info("-----Add Node2");
		waitForElementPresent(By.xpath(UP_LEVEL));
		click(UP_LEVEL);
		
		addNode(ADD_NODE_BUTTON, NODE_NAME_TEXTBOX, SECOND_NODE);
		pause(1000);

		//Click on Edit Navigation of intranet
		info("-----Click on Edit Navigation of intranet");
		click(EDIT_INTRANET_NAVIGATION);

		//Verify Node2 presents
		info("-----Verify Node2 has been added");
		waitForTextPresent(SECOND_NODE);

		//Click on Edit Navigation of intranet
		info("---Click on Edit Navigation of intranet");
		click(EDIT_INTRANET_NAVIGATION);
		pause(500);

		info("---Copy Node1 Paste to Node2");
		waitForTextPresent(FIRST_NODE);
		copyNode(By.xpath("//a[@title='" + FIRST_NODE + "']"));
		pasteNode(By.xpath("//a[@title='"+ SECOND_NODE +"']"));
		pause(1000);

		save();
		pause(1000);

		//Click on Edit Navigation of intranet
		info("---Click on Edit Navigation of intranet");
		click(EDIT_INTRANET_NAVIGATION);

		//Verify Node2 has a child node named Node1
		click("//a[@title='" + SECOND_NODE + "']");
		info("---Verify Node2 has a child node named Node1");
		waitForElementPresent(CHILD_NODE);
		assert isElementPresent(CHILD_NODE):"Can not found Node1";

		info("---Delete test Data");

		//Delete Node1
		info("-----Delete Node1");
		naviManage.deleteNode("intranet", FIRST_NODE, FIRST_NODE, true);

		//Delete Node2
		info("-----Delete Node2");
		naviManage.deleteNode("intranet", SECOND_NODE, SECOND_NODE, true);

		info("-END test01_CopyPasteNodeInSameNavigation");
	}

	//Copy/Paste a node into another node in different navigation
	@Test()
	public void test02_CopyPasteNodeInDiffirentNavigation()
	{
		info("-START test02_CopyPasteNodeInDiffirentNavigation");

		//Goto Edit Navigation 
		info("---Goto Edit Navigation");
		navigation.goToPortalSites();
		FIRST_NODE = "Node3";
		SECOND_NODE = "Node4";
		//Click on Edit Navigation of intranet
		info("---Click on Edit Navigation of ");
		click(EDIT_INTRANET_NAVIGATION);

		info("---Add Test Data");

		//Add Node3
		info("-----Add Node3");
		waitForElementPresent(By.xpath(UP_LEVEL));
		click(UP_LEVEL);
		FIRST_NODE = "Node3";
		addNode(ADD_NODE_BUTTON, NODE_NAME_TEXTBOX, FIRST_NODE);
		pause(1000);

		//Click on Edit Navigation of intranet
		info("-----Click on Edit Navigation of intranet");
		click(EDIT_INTRANET_NAVIGATION);

		//Verify Node3 presents
		waitForTextPresent(FIRST_NODE);

		//Close Navigation Management screen
		click(CLOSE_NAVIGATION);

		//Click on Edit Navigation of Intranet
		info("----- Click on Edit Navigation of INTRANET");
		click(EDIT_ACME_NAVIGATION);

		//Add Node4
		info("-----Add Node4");
		waitForElementPresent(By.xpath(UP_LEVEL));
		click(UP_LEVEL);
		SECOND_NODE = "Node4";
		addNode(ADD_NODE_BUTTON, NODE_NAME_TEXTBOX, SECOND_NODE);
		pause(1000);

		//Click on Edit Navigation of Intranet
		info("-----Click on Edit Navigation of INTRANET");
		click(EDIT_ACME_NAVIGATION);
		pause(1000);

		//Verify Node4 presents
		waitForTextPresent(SECOND_NODE);

		//Close Navigation Management screen
		click(CLOSE_NAVIGATION);

		//Click on Edit Navigation of intranet
		info("-----Click on Edit Navigation of intranet");
		click(EDIT_INTRANET_NAVIGATION);

		info("---Can't Copy Node3 to Node4");

		copyNode(By.xpath("//a[@title='" + FIRST_NODE +"']"));
		click(CLOSE_NAVIGATION);

		//Click on Edit Navigation of acme
		info("-----Click on Edit Navigation of acme");
		click(EDIT_ACME_NAVIGATION);
		pause(1000);

		//Right click on Node4
		rightClickOnElement(By.linkText(SECOND_NODE));

		//Confirm Paste Node item not existed
		info("-----Paste Node item not existed");
		waitForElementNotPresent(By.linkText("Paste Node"));
		pause(1000);

		info("---Delete test Data");

		//Delete Node4
		info("-----Delete Node4");
		naviManage.deleteNode("acme", SECOND_NODE, SECOND_NODE, true);

		//Click on Edit Navigation of intranet
		info("-----Click on Edit Navigation of intranet");
		click(EDIT_INTRANET_NAVIGATION);

		//Delete Node3
		info("---Delete Node3");
		naviManage.deleteNode("intranet", FIRST_NODE, FIRST_NODE, true);
		info("-END test02_CopyPasteNodeInDiffirentNavigation");
	}

	//Copy/Paste a node into the same place
	@Test()
	public void test03_CopyPasteNodesInSamePlace()
	{
		info("-START test03_CopyPasteNodesInSamePlace");

		//Goto Edit Navigation 
		info("---Goto Edit Navigation");
		navigation.goToPortalSites();

		//Click on Edit Navigation of intranet
		info("-----Click on Edit Navigation of intranet");
		click(EDIT_INTRANET_NAVIGATION);

		info("---Add Test Data");

		//Add Node5
		info("-----Add Node5");
		waitForElementPresent(By.xpath(UP_LEVEL));
		click(UP_LEVEL);
		FIRST_NODE = "Node5";
		addNode(ADD_NODE_BUTTON, NODE_NAME_TEXTBOX, FIRST_NODE);
		pause(1000);

		//Click on Edit Navigation of intranet
		click(EDIT_INTRANET_NAVIGATION);

		//Verify Node5 presents
		info("-----Verify Node5 present");
		waitForTextPresent(FIRST_NODE);

		//Add Node6
		waitForElementPresent(By.xpath(UP_LEVEL));
		click(UP_LEVEL);
		SECOND_NODE = "Node6";
		addNode(ADD_NODE_BUTTON, NODE_NAME_TEXTBOX, SECOND_NODE);

		//Click on Edit Navigation of intranet
		click(EDIT_INTRANET_NAVIGATION);

		//Verify Node6 presents
		waitForTextPresent(SECOND_NODE);

		info("---Copy Node5 to Node6");

		copyNode(By.xpath("//a[@title='" + FIRST_NODE + "']"));
		pasteNode(By.xpath("//a[@title='"+ SECOND_NODE +"']"));

		//Save
		save();
		pause(500);

		//Click on Edit Navigation of intranet
		click(EDIT_INTRANET_NAVIGATION);

		copyNode(By.xpath("//a[@title='" + FIRST_NODE + "']"));
		pasteNode(By.xpath("//a[@title='"+ SECOND_NODE +"']"));

		//Verify display message to notice that Node5 already existed
		waitForTextPresent(SAME_PLACE_MESSAGE);
		click(ELEMENT_OK_BUTTON);

		info("---Delete Test Data");

		//Delete Node5
		naviManage.deleteNode("intranet", FIRST_NODE, FIRST_NODE, true);
		pause(1000);

		//Click on Edit Navigation of intranet
		click(EDIT_INTRANET_NAVIGATION);

		//Delete Node6
		naviManage.deleteNode("intranet", SECOND_NODE, SECOND_NODE, true);
		pause(1000);

		info("-END test03_CopyPasteNodesInSamePlace");
	}

	//Copy/Paste a node into the same this source
	@Test()
	public void test04_CopyPasteNodeInSameSource()
	{
		info("-START test04_CopyPasteNodeInSameSource");

		//Goto Edit Navigation 
		info("---Goto Edit Navigation");
		navigation.goToPortalSites();

		//Click on Edit Navigation of intranet
		click(EDIT_INTRANET_NAVIGATION);

		//Add Node7
		waitForElementPresent(By.xpath(UP_LEVEL));
		click(UP_LEVEL);
		FIRST_NODE = "Node7";
		addNode(ADD_NODE_BUTTON, NODE_NAME_TEXTBOX, FIRST_NODE);

		//Click on Edit Navigation of intranet
		click(EDIT_INTRANET_NAVIGATION);

		//Verify Node7 presents
		waitForTextPresent(FIRST_NODE);

		copyNode(By.xpath("//a[@title='" + FIRST_NODE + "']"));
		pasteNode(By.xpath("//a[@title='"+ FIRST_NODE +"']"));
		pause(1000);

		//Verify display message to notice that The source and the destination must be different
		waitForTextPresent(naviManage.MSG_SAME_SOURCE);
		click(ELEMENT_OK_BUTTON);

		//Delete Node7
		naviManage.deleteNode("intranet", FIRST_NODE, FIRST_NODE, true);
		pause(1000);

		info("-END test04_CopyPasteNodeInSameSource");
	}

	@AfterMethod()
	public void afterTest() 
	{
		account.signOut();
		driver.quit();
	}
}
