package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.debug;
import static org.exoplatform.selenium.TestLogger.info;
import java.util.Map;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 *@author HangNTT
 * @date: 09/11/2012
 */
public class SpaceNavigation extends SocialBase{

	// Go to My Spaces > Select a space > Settings
	// Navigations Tab
	public static final By UP_LEVEL = By.xpath("//a[@title='Up Level']");

	public static final By ADD_NODE_BUTTON = By.xpath("//a[text()='Add Node']");
	public static final By NODE_NAME = By.xpath("//input[@id='name']");
	public static final By NODE_LABEL = By.xpath("//input[@id='i18nizedLabel']");
	public static final By PAGE_SELECTOR = By.xpath("//div[text()='Page Selector']");
	public static final By PAGE_NAME = By.id("pageName");
	public static final By CREATE_PAGE_BUTTON = By.linkText("Create Page");
	public static final String NODE_PATH = "//a[@class='NodeIcon DefaultPageIcon' and @title='${nodeLabel}']";
	public static final By ELEMENT_INPUT_POPUP_SEARCH_TITLE = By.xpath("//div[@class='QuickSet']/input[@id='pageTitle']"); 
	public static final By ELEMENT_SELECT_PAGE = By.xpath("//div[@id='UIRepeater']//table//tbody/tr/td[5]/div[@class='ActionContainer']/img");
	public static final String WARNING_EXISTING_NODE = "This node name already exists.";

	public static final String ELEMENT_NODE_LINK_FORM = "//div[@class='UITrees ScrollArea']//a[@title='${nodeLabel}']";
	public static final String ELEMENT_RIGHT_CLICK_ADD_NODE_FORM_ = "//div[@class='TopLeftRightClickPopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon AddNode16x16Icon']";
	public static final String ELEMENT_ADD_NEW_NODE_RIGHT_CLICK = "//div[@id='SpaceNavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon AddNode16x16Icon']";
	public static final String ELEMENT_DELETE_NODE = "//div[@id='SpaceNavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon DeleteNode16x16Icon']";
	public static final String ELEMENT_EDIT_NODE = "//div[@id='SpaceNavigationNodePopupMenu']/div[@class='UIContextMenuContainer']//a[@class='ItemIcon EditSelectedNode16x16Icon']";
	public static final By ELEMENT_CUT_NODE_LINK = By.linkText("Cut Node");
//	public static final By ELEMENT_PASTE_NODE_LINK = By.linkText("Paste Node");
	public static final By ELEMENT_PASTE_NODE_LINK = By.xpath("//*[@id='SpaceNavigationNodePopupMenu']//a[@class='ItemIcon PasteNode16x16Icon']");
	public static final By ELEMENT_COPY_NODE_LINK = By.linkText("Copy Node");
	public static final By ELEMENT_MOVE_UP_LINK = By.linkText("Move Up");
	public static final By ELEMENT_MOVE_DOWN_LINK = By.linkText("Move Down");
	public static final By ELEMENT_EDIT_NODE_PAGE = By.linkText("Edit Node's Page");

	// Spacesetting -> Navigation -> Edit Page
	public static By ELEMENT_PORTLET_LAYOUT = By.className("PortletLayoutDecorator");
	public static By ELEMENT_SAVECLOSE_LINK = By.linkText("Save And Close");
	public static By ELEMENT_WINDOWN_SETTINGS_TAB = By.xpath("//div[text()='Window Settings']");
	public static By ELEMENT_DESCRIPTION_AREA = By.id("description");
	public static By ELEMENT_TAB_CONTAINERS = By.xpath("//div[contains(text(),'Containers') and @class='MiddleTab']");
	public static By ELEMENT_ROWS_LAYOUT = By.xpath("//a[@class='TabLabel and @title='row']");
	public static String ELEMENT_EDIT_PAGE_COMPONENT = "//div[@class='UIRowContainer']/div[${portletNumber}]/div";
	public static By ELEMENT_EDIT_ICON = By.xpath("//a[@class = 'EditIcon' and @title='Edit Container']");
	public static By ELEMENT_PERMISSION_TAB = By.xpath("//div[text()='Access Permission']");
	public static By ELEMENT_WIDTH_TEXTBOX = By.id("width");
	public static By ELEMENT_HEIGHT_TEXTBOX = By.id("height");
	
	
	// Add node don't select page in Manage page
	public static void addNodeDoNotSelectPage(String nodeNameInput, String nodeLabelInput, String pageNameInput)
	{
		//Click add node button
		click(ADD_NODE_BUTTON);
		pause(1000);
		//Input node name
		waitForElementPresent(NODE_NAME);
		type(NODE_NAME, nodeNameInput, true);
		pause(500);
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
		save();
		pause(1000);
		save();
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
	public static void addNodeWhenSelectpage(String currentNodeLabel, boolean useAddNodeLink, String nodeName, boolean extendedLabelMode,
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
			pause(500);
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
				pause(500);
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
		pause(1000);

		type(By.xpath("//div[@class='QuickSet']/input[@id='pageTitle']"), pageTitle, true);
		
		click(ELEMENT_SELECT_PAGE_SEARCH);
		
		click(ELEMENT_SELECT_PAGE_BUTTON);
	}

		info("-- Save add node for portal --");
		pause(1000);
		save();
		if (verifyNode) {
			waitForTextNotPresent("Page Node Settings");
			waitForTextPresent(nodeName);
			save();
			waitForTextPresent(nodeName);
		}
	}

	// Edit a node 
	public static void editNode(String currentSpace, String nodeNameHome, String nodeName, boolean extendedLabelMode, Map<String, String> languages, 
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
				pause(500);
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
		pause(1000);
		save();
		pause(1000);
		save();
		waitForTextNotPresent(nodeName);
	}

	//Delete a node from Social navigation
	public static void deleteNode(String nodeNameHome, String nodeName, boolean firstLevel){
		info("--Deleting node from navigation--");
		String currentNodeHome = ELEMENT_NODE_LINK_FORM.replace("${nodeLabel}", nodeNameHome);	
		String currentNodeName = ELEMENT_NODE_LINK_FORM.replace("${nodeLabel}", nodeName);

		//currentNodeHome.equals(ELEMENT_NAVIGATION_NODE_AREA)
		if (firstLevel){
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_DELETE_NODE);
			waitForConfirmation("Are you sure to delete this node?");
			waitForTextNotPresent(nodeName);
			save();		
		}else {
			click(currentNodeHome);
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_DELETE_NODE);
			waitForConfirmation("Are you sure to delete this node?");
			waitForTextNotPresent(nodeName);
			save();		
		}
		waitForTextNotPresent(nodeName);
	}
	/**
	 * Updated by ThaoPTH
	 * Add function cutSpaceNode(), copySpaceNode(), pasteSpaceNode()
	 * Date: 06/12/2012
	 */
	public static void cutSpaceNode(By locator)	{
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
			pause(WAIT_INTERVAL);
		}
	}

	public static void copySpaceNode(By locator)	{
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
			pause(WAIT_INTERVAL);
	}
	}
	
	public static void pasteSpaceNode(By locator)	{
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
			pause(WAIT_INTERVAL);
		}
	}

	/**
	 * Create functions: moveUpNode() and moveDownNode(), editNodePage()
	 * @author: ThaoPTH
	 * Date: 03/12/2012
	 */
	public static void moveUpNode(String nodeName) {
		
		waitForElementPresent(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
				
		rightClickOnElement(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
				
		waitForElementPresent(ELEMENT_MOVE_UP_LINK);
		
		click(ELEMENT_MOVE_UP_LINK);
				
	}
	
	public static void moveDownNode(String nodeName) {
		
		waitForElementPresent(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
		
		rightClickOnElement(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
		
		waitForElementPresent(ELEMENT_MOVE_DOWN_LINK);
		
		click(ELEMENT_MOVE_DOWN_LINK);
	}
	
	public static void editNodePage(String nodeName) {;
	waitForElementPresent(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
	
	rightClickOnElement(By.xpath("//a[@class='NodeIcon DefaultPageIcon' and text()='"+nodeName+"']"));
	
	waitForElementPresent(ELEMENT_EDIT_NODE_PAGE);
	
	click(ELEMENT_EDIT_NODE_PAGE);
	}
}

