package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.debug;
import static org.exoplatform.selenium.TestLogger.info;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 *@author HangNTT
 * @date: 09/11/2012
 */
public class SpaceNavigation extends SocialBase{

	Button button = new Button(driver);
	Dialog dialog = new Dialog(driver);
	ManageAlert magAlert = new ManageAlert(driver);
	
	// Go to My Spaces > Select a space > Settings
	// Navigations Tab
	public final By UP_LEVEL = By.xpath("//a[@title='Up Level']");

	public final By ADD_NODE_BUTTON = By.xpath("//a[text()='Add Node']");
	public final By NODE_NAME = By.xpath("//input[@id='name']");
	public final By NODE_LABEL = By.xpath("//input[@id='label']");
	public final By PAGE_SELECTOR = By.xpath("//div[text()='Page Selector']");
	public final By PAGE_NAME = By.id("pageName");
	public final By CREATE_PAGE_BUTTON = By.linkText("Create Page");
	public final String NODE_PATH = "//a[@class='NodeIcon DefaultPageIcon' and @title='${nodeLabel}']";
	public final By ELEMENT_INPUT_POPUP_SEARCH_TITLE = By.xpath("//div[@class='QuickSet']/input[@id='pageTitle']"); 
	public final By ELEMENT_SELECT_PAGE = By.xpath("//div[@id='UIRepeater']//table//tbody/tr/td[5]/div[@class='ActionContainer']/img");
	public final String WARNING_EXISTING_NODE = "This node name already exists.";

	public final String ELEMENT_NODE_LINK_FORM = "//div[@class='UITrees ScrollArea']//a[@title='${nodeLabel}']";
	public final String ELEMENT_RIGHT_CLICK_ADD_NODE_FORM_ = "//div[@class='TopLeftRightClickPopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon AddNode16x16Icon']";
	public final String ELEMENT_ADD_NEW_NODE_RIGHT_CLICK = "//div[@id='SpaceNavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon AddNode16x16Icon']";
	public final String ELEMENT_DELETE_NODE = "//div[@id='SpaceNavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon DeleteNode16x16Icon']";
	public final String ELEMENT_EDIT_NODE = "//div[@id='SpaceNavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon EditSelectedNode16x16Icon']";
	public final By ELEMENT_CUT_NODE_LINK = By.linkText("Cut Node");
//	public final By ELEMENT_PASTE_NODE_LINK = By.linkText("Paste Node");
	public final By ELEMENT_PASTE_NODE_LINK = By.xpath("//*[@id='SpaceNavigationNodePopupMenu']//a[@class='ItemIcon PasteNode16x16Icon']");
	public final By ELEMENT_COPY_NODE_LINK = By.linkText("Copy Node");
	public final By ELEMENT_MOVE_UP_LINK = By.linkText("Move Up");
	public final By ELEMENT_MOVE_DOWN_LINK = By.linkText("Move Down");
	public final By ELEMENT_EDIT_NODE_PAGE = By.linkText("Edit Node's Page");

	// Spacesetting -> Navigation -> Edit Page
	public By ELEMENT_PORTLET_LAYOUT = By.className("PortletLayoutDecorator");
	public By ELEMENT_SAVECLOSE_LINK = By.linkText("Save And Close");
	public By ELEMENT_WINDOWN_SETTINGS_TAB = By.xpath("//div[text()='Window Settings']");
	public By ELEMENT_DESCRIPTION_AREA = By.id("description");
	public By ELEMENT_TAB_CONTAINERS = By.xpath("//div[contains(text(),'Containers') and @class='MiddleTab']");
	public By ELEMENT_ROWS_LAYOUT = By.xpath("//a[@class='TabLabel and @title='row']");
	public String ELEMENT_EDIT_PAGE_COMPONENT = "//div[@class='UIRowContainer']/div[${portletNumber}]/div";
	public By ELEMENT_EDIT_ICON = By.xpath("//a[@class = 'EditIcon' and @title='Edit Container']");
	public By ELEMENT_PERMISSION_TAB = By.xpath("//div[text()='Access Permission']");
	public By ELEMENT_WIDTH_TEXTBOX = By.id("width");
	public By ELEMENT_HEIGHT_TEXTBOX = By.id("height");
	
	
	// Add node don't select page in Manage page
	public void addNodeDoNotSelectPage(String nodeNameInput, String nodeLabelInput, String pageNameInput)
	{
		//Click add node button
		click(ADD_NODE_BUTTON);
		Utils.pause(1000);
		//Input node name
		waitForElementPresent(NODE_NAME);
		type(NODE_NAME, nodeNameInput, true);
		Utils.pause(500);
		//Input node label
		waitForElementPresent(NODE_LABEL);
		type(NODE_LABEL, nodeLabelInput, true);
		//Click Page selector tab
		waitForElementPresent(PAGE_SELECTOR);
		click(PAGE_SELECTOR);
		//Input page name
		waitForElementPresent(PAGE_NAME);
		type(PAGE_NAME,pageNameInput,true);
		//Click create page
		waitForElementPresent(CREATE_PAGE_BUTTON);
		click(CREATE_PAGE_BUTTON);
		button.save();
		Utils.pause(1000);
		button.save();
	}
	/**
	 * Updated by thaopth
	 * Edit function to do action search and select an existing page
	 * Date: 04/12/2012
	 * @param currentNodeLabel
	 * @param useAddNodeLink
	 * @param nodeName
	 * @param extendedLabelMode
	 * @param languages
	 * @param nodeLabel
	 * @param pageName
	 * @param pageTitle
	 * @param verifyPage
	 * @param verifyNode
	 * @param select
	 */
	// Add node when select a page in Manage page
	public void addNodeWhenSelectpage(String currentNodeLabel, boolean useAddNodeLink, String nodeName, boolean extendedLabelMode,
			Map<String, String> languages, String nodeLabel, String pageName, String pageTitle, boolean verifyPage, boolean verifyNode, boolean select){
		String ELEMENT_SELECT_PAGE_SEARCH="//a[@title='Quick Search']";
		String ELEMENT_SELECT_PAGE_BUTTON= "//img[@title='Select Page']";
		String ELEMENT_SEARCH_SELECT_PAGE_BUTTON= "//a[text()='Search and Select Page']";
		String currentNode = ELEMENT_NODE_LINK_FORM.replace("${nodeLabel}", currentNodeLabel);

		info("--Click add node button--");	
		if (useAddNodeLink){
			click(currentNode);
	click(ADD_NODE_BUTTON);
		} else{

			click(currentNode);
			Utils.pause(500);
			info("--Click riggt click--");
			rightClickOnElement(currentNode);
			if (currentNode.equals(ELEMENT_NAVIGATION_HOME_NODE)) {
				click(ELEMENT_RIGHT_CLICK_ADD_NODE_FORM_);
			} else {
				click(ELEMENT_ADD_NEW_NODE_RIGHT_CLICK);
			}	
		}
		waitForTextPresent("Page Node Settings");
		type(ELEMENT_INPUT_NAME, nodeName, true);

		if (extendedLabelMode) {
			for (String language : languages.keySet()) {
				select(ELEMENT_SELECT_LANGUAGE, language);
				Utils.pause(500);
			}
		} else {
			uncheck(ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE);
			type(ELEMENT_INPUT_LABEL, nodeLabel, true);
		}
		info("-- Select page selectot tab --");
		click(ELEMENT_PAGE_SELECTOR_TAB);
	
	if (!select) {
		info("--Create new page");
		type(ELEMENT_INPUT_PAGE_NAME, pageName, true);
		type(ELEMENT_INPUT_PAGE_TITLE, pageTitle, true);
		click(ELEMENT_CREATE_PAGE_LINK);
		if (verifyPage) {
			waitForElementNotPresent(ELEMENT_CREATE_PAGE_LINK);
		} else {
			return;
		}
	} else {
		//info("-- Select Page --");
		
		click(ELEMENT_SEARCH_SELECT_PAGE_BUTTON);
		Utils.pause(1000);

		type(By.xpath("//div[@class='QuickSet']/input[@id='pageTitle']"), pageTitle, true);
		
		click(ELEMENT_SELECT_PAGE_SEARCH);
		
		click(ELEMENT_SELECT_PAGE_BUTTON);
	}

		info("-- Save add node for portal --");
		Utils.pause(1000);
		button.save();
		if (verifyNode) {
			waitForTextNotPresent("Page Node Settings");
			waitForTextPresent(nodeName);
			button.save();
			waitForTextPresent(nodeName);
		}
	}

	// Edit a node 
	public void editNode(String currentSpace, String nodeNameHome, String nodeName, boolean extendedLabelMode, Map<String, String> languages, 
			String nodeLabel, String pageName, String pageTitle, boolean firstLevel){

		String currentNodeHome = ELEMENT_NODE_LINK_FORM.replace("${nodeLabel}", nodeNameHome);
		String currentNodeName = ELEMENT_NODE_LINK_FORM.replace("${nodeLabel}", nodeName);


		if (firstLevel){
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_EDIT_NODE);	
		}else {
			click(currentNodeHome);
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_EDIT_NODE);	

		}
		waitForTextPresent("Page Node Settings");
		if (extendedLabelMode) {
			for (String language : languages.keySet()) {
				select(ELEMENT_SELECT_LANGUAGE, language);
				Utils.pause(500);
			}
		} else {
			uncheck(ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE);
			type(ELEMENT_INPUT_LABEL, nodeLabel, true);
		}

		click(ELEMENT_PAGE_SELECTOR_TAB);
		click(ELEMENT_CLEAR_PAGE_LINK);
		type(ELEMENT_INPUT_PAGE_NAME, pageName, true);
		type(ELEMENT_INPUT_PAGE_TITLE, pageTitle, true);
		click(ELEMENT_CREATE_PAGE_LINK);
		Utils.pause(1000);
		button.save();
		Utils.pause(1000);
		button.save();
		waitForTextNotPresent(nodeName);
	}

	//Delete a node from Social navigation
	public void deleteNode(String nodeNameHome, String nodeName, boolean firstLevel){
		info("--Deleting node from navigation--");
		String currentNodeHome = ELEMENT_NODE_LINK_FORM.replace("${nodeLabel}", nodeNameHome);	
		String currentNodeName = ELEMENT_NODE_LINK_FORM.replace("${nodeLabel}", nodeName);

		//currentNodeHome.equals(ELEMENT_NAVIGATION_NODE_AREA)
		if (firstLevel){
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_DELETE_NODE);
			magAlert.waitForConfirmation("Are you sure to delete this node?");
			waitForTextNotPresent(nodeName);
			button.save();		
		}else {
			click(currentNodeHome);
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_DELETE_NODE);
			magAlert.waitForConfirmation("Are you sure to delete this node?");
			waitForTextNotPresent(nodeName);
			button.save();		
		}
		waitForTextNotPresent(nodeName);
	}
	/**
	 * Updated by ThaoPTH
	 * Add function cutSpaceNode(), copySpaceNode(), pasteSpaceNode()
	 * Date: 06/12/2012
	 */
	public void cutSpaceNode(By locator)	{
		for (int i =0;; i++){
			if (i > DEFAULT_TIMEOUT/WAIT_INTERVAL) {
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_CUT_NODE_LINK,30000,0)!=null){
				debug("==Cut node " + locator + "==");
				click((ELEMENT_CUT_NODE_LINK));
				return;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}

	public void copySpaceNode(By locator)	{
		for (int i =0;; i++){
			if (i > ACTION_REPEAT) {
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_COPY_NODE_LINK,5000,0)!=null){
				debug("==Cut node " + locator + "==");
				click((ELEMENT_COPY_NODE_LINK));
				return;
			}
			Utils.pause(WAIT_INTERVAL);
	}
	}
	
	public void pasteSpaceNode(By locator)	{
		for (int i =0;; i++){
			if (i > ACTION_REPEAT) {
				Assert.fail("Timeout");
			}
			rightClickOnElement(locator);
			if (waitForAndGetElement(ELEMENT_PASTE_NODE_LINK,5000,0)!=null){
				debug("==Cut node " + locator + "==");
				click((ELEMENT_PASTE_NODE_LINK));
				return;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}

	/**
	 * Create functions: moveUpNode() and moveDownNode(), editNodePage()
	 * @author: ThaoPTH
	 * Date: 03/12/2012
	 */
	public void moveUpNode(String nodeName) {
		
		waitForElementPresent(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
				
		rightClickOnElement(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
				
		waitForElementPresent(ELEMENT_MOVE_UP_LINK);
		
		click(ELEMENT_MOVE_UP_LINK);
				
	}
	
	public void moveDownNode(String nodeName) {
		
		waitForElementPresent(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
		
		rightClickOnElement(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
		
		waitForElementPresent(ELEMENT_MOVE_DOWN_LINK);
		
		click(ELEMENT_MOVE_DOWN_LINK);
	}
	
	public void editNodePage(String nodeName) {;
	waitForElementPresent(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
	
	rightClickOnElement(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
	
	waitForElementPresent(ELEMENT_EDIT_NODE_PAGE);
	
	click(ELEMENT_EDIT_NODE_PAGE);
	}
}

