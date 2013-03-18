package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.PageManagement;
import java.util.Map;
import org.openqa.selenium.By;

public class NavigationManagement extends  PageManagement{

	ManageAlert alt = new ManageAlert(driver);
	
	public  final By ELEMENT_INPUT_POPUP_SEARCH_TITLE = By.xpath("//div[@class='QuickSet']/input[@id='pageTitle']"); 
	public  final By ELEMENT_SELECT_PAGE = By.xpath("//div[@id='UIRepeater']//table//tbody/tr/td[5]/div[@class='ActionContainer']/img");

	// Add a node for portal at portal navigation
	public void addNodeForPortal(String currentNavigation, String currentNodeLabel, boolean useAddNodeLink, String nodeName, boolean extendedLabelMode,
			Map<String, String> languages, String nodeLabel, String pageName, String pageTitle, boolean verifyPage, boolean verifyNode){

		//String node = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeLabel);
		String currentNode = ELEMENT_NODE_LINK.replace("${nodeLabel}", currentNodeLabel);
		editNavigation(currentNavigation);

		info("--Add new node at navigation--");	
		if (useAddNodeLink){
			click(currentNode);
			click(ELEMENT_ADD_NODE_LINK);
		}else{

			click(currentNode);
			Utils.pause(500);
			rightClickOnElement(currentNode);
			if (currentNode.equals(ELEMENT_NAVIGATION_HOME_NODE)) {
				click(ELEMENT_NODE_ADD_NEW_TOP_NODE);
			} else {
				click(ELEMENT_NODE_ADD_NEW);
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

		click(ELEMENT_PAGE_SELECTOR_TAB);

		if (pageName != null) {
			info("-- Create new page --");
			type(ELEMENT_INPUT_PAGE_NAME, pageName, true);
			type(ELEMENT_INPUT_PAGE_TITLE, pageTitle, true);
			click(ELEMENT_CREATE_PAGE_LINK);
			if (verifyPage) {
				waitForElementNotPresent(ELEMENT_CREATE_PAGE_LINK);
			} else {
				return;
			}
		} else {
			info("-- Select Page --");
			Utils.pause(500);
			click(ELEMENT_SEARCH_SELECT_PAGE_LINK);
			type(ELEMENT_INPUT_POPUP_SEARCH_TITLE, pageTitle, true);
			click(ELEMENT_PAGE_MANAGEMENT_SEARCH_BUTTON);
			click(ELEMENT_SELECT_PAGE);
		}

		info("-- Save add node for portal --");
		Utils.pause(1000);
		button.save();
		if (verifyNode) {
			waitForTextNotPresent("Page Node Settings");
			waitForTextPresent(nodeName);
			button.save();
			waitForTextNotPresent("Navigation Management");
		}
	}

	// Edit a node 
	public void editNode(String currentNavigation, String nodeNameHome, String nodeName, boolean extendedLabelMode, Map<String, String> languages, 
			String nodeLabel, String pageName, String pageTitle, boolean firstLevel){

		String currentNodeHome = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeNameHome);
		String currentNodeName = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		editNavigation(currentNavigation);
		//currentNodeHome.equals(ELEMENT_NAVIGATION_NODE_AREA)
		if (firstLevel){
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_NODE_EDIT);	
		}else {
			click(currentNodeHome);
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_NODE_EDIT);	

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
		waitForTextNotPresent("Navigation Management");
	}

	//Delete a node from Portal navigation
	public void deleteNode(String currentNavigation, String nodeNameHome, String nodeName, boolean firstLevel){
		info("--Delete a node from navigation--");
		String currentNodeHome = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeNameHome);	
		String currentNodeName = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		editNavigation(currentNavigation);
		//currentNodeHome.equals(ELEMENT_NAVIGATION_NODE_AREA)
		if (firstLevel){
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_NODE_DELETE);
			alt.waitForConfirmation("Are you sure to delete this node?");
			waitForTextNotPresent(nodeName);
			button.save();		
		}else {
			click(currentNodeHome);
			click(currentNodeName);
			rightClickOnElement(currentNodeName);
			click(ELEMENT_NODE_DELETE);
			alt.waitForConfirmation("Are you sure to delete this node?");
			waitForTextNotPresent(nodeName);
			button.save();		
		}
		waitForTextNotPresent("Navigation Management");
	}

}
