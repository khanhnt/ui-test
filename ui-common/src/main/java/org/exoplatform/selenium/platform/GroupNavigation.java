package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GroupNavigation extends PlatformBase {
	public  String ELEMENT_GROUP_EDIT_NAVIGATION = "//div[text()='{$navigation}']/../..//a[@class='EditNavIcon' and text() = 'Edit Navigation']";
//	public  String MSG_SAME_SOURCE = "The source and the destination must be different";
	public  By ELEMENT_MENU_GROUP_ADMIN = By.linkText("Administration");
	public  String ELEMENT_NODE_GROUP_LINK = "//div[@class='Node']//a[@title='{$navigation}']";
	//Add a node for group at group navigation

	public GroupNavigation(WebDriver dr){
		driver = dr;
	}
	public void addNodeForGroup(String currentNavigation, String currentNodeLabel, boolean useAddNodeLink, String nodeName, boolean extendedLabelMode, 
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
			pause(500);
			rightClickOnElement(currentNode);
			if (currentNode.equals(ELEMENT_NAVIGATION_HOME_NODE)) {
				click(ELEMENT_NODE_ADD_NEW_TOP_NODE);
			} else {
				click(ELEMENT_NODE_ADD_NEW);
			}		
		}
		waitForTextPresent("Page Node Setting");
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

		click(ELEMENT_PAGE_SELECTOR_TAB);

		if (pageName != null & pageTitle != null) {
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
			pause(500);
			click(ELEMENT_SEARCH_SELECT_PAGE_LINK);
			click(ELEMENT_SELECT_HOME_PAGE);
		}

		info("-- Save to add node for portal --");
		pause(1000);
		save();
		if (verifyNode) {
			waitForTextNotPresent("Page Node Setting");
			waitForTextPresent(nodeName);
			save();
			waitForTextNotPresent("Navigation Management");
		}
	}
}
