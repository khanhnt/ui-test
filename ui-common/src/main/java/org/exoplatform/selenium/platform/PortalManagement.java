package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;
import java.util.Map;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PortalManagement extends PlatformBase {
	public By ELEMENT_SITE_CONFIG_LINK = By.linkText("Site's Config");
	public String MSG_EDIT_EMPTY_PERMISSION_SETTING= "The \"Edit Permission Setting\" list can not be empty.";
	public String MSG_DELETE_PORTAL = "Are you sure you want to delete this portal?";
	NavigationToolbar nav = new NavigationToolbar(driver);
	public PortalManagement(WebDriver dr){
		driver = dr;
	}
	
	//Add new portal
	public void addNewPortal(String portalName, String portalLocale, String portalSkin, String portalSession, 
			boolean publicMode, Map<String, String> permissions, String editGroupId, String editMembership){
		info("--Create new portal--");

		click(ELEMENT_ADD_NEW_PORTAL_LINK);
		waitForTextPresent("Portal Setting");
		type(ELEMENT_INPUT_NAME, portalName, true);
		select(ELEMENT_SELECT_LOCALE, portalLocale);
		select(ELEMENT_SELECT_SKIN, portalSkin);
		click(ELEMENT_PROPERTIES_TAB);
		select(ELEMENT_SELECT_SESSION_ALIVE, portalSession);
		click(ELEMENT_PERMISSION_SETTING_TAB);

		if (publicMode) {
			waitForAndGetElement(ELEMENT_ADD_PERMISSION_BUTTON);
			check(ELEMENT_CHECKBOX_PUBLIC_MODE);
			waitForElementNotPresent(ELEMENT_ADD_PERMISSION_BUTTON);
		} else {
			for (String key : permissions.keySet()) {
				setViewPermissions(key, permissions.get(key));
			}
		}
		click(ELEMENT_EDIT_PERMISSION_SETTING);
		setEditPermissions(editGroupId, editMembership);
		save();
	}


	/*//Edit a portal
>>>>>>> FQA-499: PLF 4 - Migrate EXOGTN (common + test cases)
	public void editPortal(String portalName, String portalLocale, String portalSkin, String portalSession, 
			boolean publicMode, Map<String, String> permissions, String editGroupId, String editMembership){
		info("--Create new portal--");

		String editIcon = ELEMENT_SELECT_EDIT_PORTAL_CONFIG.replace("${portalName}", portalName);		
		click(editIcon);

		waitForTextPresent("Portal Setting");

		select(ELEMENT_SELECT_LOCALE, portalLocale);
		select(ELEMENT_SELECT_SKIN, portalSkin);
		click(ELEMENT_PROPERTIES_TAB);
		select(ELEMENT_SELECT_SESSION_ALIVE, portalSession);
		click(ELEMENT_PERMISSION_SETTING_TAB);

		click (ELEMENT_CHECKBOX_PUBLIC_MODE);

		if (publicMode) {
			waitForAndGetElement(ELEMENT_ADD_PERMISSION_BUTTON);
			check(ELEMENT_CHECKBOX_PUBLIC_MODE);
			waitForElementNotPresent(ELEMENT_ADD_PERMISSION_BUTTON);
		} else {
			for (String key : permissions.keySet()) {
				setViewPermissions(key, permissions.get(key));
			}
		}
		click(ELEMENT_EDIT_PERMISSION_SETTING);
		setEditPermissions(editGroupId, editMembership);
		save();
	}*/

	//Delete a portal	
	public void deletePortal(String portalName){
		String portalDeleteIcon = ELEMENT_PORTAL_DELETE_ICON.replace("${portalName}", portalName);
		info("--Delete portal (" + portalName + ")--");		
		click(portalDeleteIcon);
		waitForConfirmation(MSG_DELETE_PORTAL);
		//info("--Verify portal is deleted--");
		//		pause(30000);
		waitForTextNotPresent(portalName, 180000);
	}

	//Verify the existence of portal
	public void verifyPortalExists(String portalName) {
		String portal = ELEMENT_PORTAL_IN_LIST.replace("${portalName}", portalName);

		info("--Verify portal (" + portalName + ") exists--");
		nav.goToPortalSites();
		waitForAndGetElement(portal);
	}

}
