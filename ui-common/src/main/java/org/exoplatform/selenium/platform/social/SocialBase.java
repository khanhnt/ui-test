package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.PlatformBase;
import org.openqa.selenium.By;

/**
 *@author HangNTT
 * @date: 07/11/2012
 */
public class SocialBase extends PlatformBase {
	
	//SpaceManagement spaceMag = new SpaceManagement();

	//public final By ELEMENT_JOIN_SPACE_LINK = By.xpath("//div[@class='uiSpaceNavigationPortlet']/div/a/i[contains(@class, 'uiIconPLFMan')]");
	public final By ELEMENT_JOIN_SPACE_LINK = By.xpath("//div[@class='uiSpaceNavigationPortlet']/..//div/a[contains(text(),'Join a space')]");
	
	// Go to My space link
	public final By ELEMENT_MY_SPACES_LINK = By.linkText("My Spaces");
	public final By ELEMENT_ALL_SPACE_LINK = By.linkText("All Spaces");
	public final By ELEMENT_INVATATION_RECEIVES_LINK = By.linkText("Invitations Received");
	public final By ELEMENT_REQUESTS_PENDING_LINK = By.linkText("Requests Pending");

	// Go to My Space > All Spaces Tab
    public final String ELEMENT_SEND_REQUEST_LINK = "//*[@id='UIManageAllSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Request to Join']";
    public final String ELEMENT_INVITATION_LEAVE_LINK_IN_ALL_SPACES = "//*[@id='UIManageAllSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Leave']";
    public final String ELEMENT_INVITATION_ACCEPT_LINK_IN_ALL_SPACES = "//*[@id='UIManageAllSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Accept']";
	public final String ELEMENT_INVITATION_IGNORE_LINK_IN_ALL_SPACES = "//*[@id='UIManageAllSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Ignore']";
    
    // Go to My Space > My Spaces Tab
 	public final String ELEMENT_INVITATION_LEAVE_LINK = "//*[@id='UIManageMySpaces']//*[text()='${spaceName}']/../../ul//*[text()='Leave']";

    // Go to My Space > Invitation Receives Tab
	public final String ELEMENT_INVITATION_ACCEPT_LINK = "//*[@id='UIManageInvitationSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Accept']";
	public final String ELEMENT_INVITATION_IGNORE_LINK = "//*[@id='UIManageInvitationSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Ignore']";
		
	// Go to My Space > Requests Pending Tab
	public final String ELEMENT_CANCEL_LINK = "//*[@id='UIManagePendingSpaces']//*[text()='${spaceName}']/../../ul//*[text()='Cancel']";
	
	// Go to My Space -> Search Button
	public final By ELEMENT_SEARCH_BUTTON = By.id("SearchButton");
	
	// Go to My Space -> Select a space > click setting icon
	public final By ELEMENT_SETTINGS = By.xpath("//a[@title='Settings']");

	// Go to My Space -> Select a space > click Navigation tab
	public final By ELEMENT_NAVIGATION_TAB = By.xpath("//div[text()='Navigations']");

	// Go to My Space -> Select a space > click Access & Edit tab
	public final By  ELEMENT_ACCESS_AND_EDIT_TAB = By.xpath("//div[text()='Access & Edit']");

	// Go to My Space -> Select a space > click Member tab
	public final By ELEMENT_MEMBERS_TAB = By.xpath("//div[@class='TabsContainer']//div[text()='Members']");
	public final By ELEMENT_MEMBERS_TAB_IN_SPACE_MENU = By.xpath("//*[@id='UISpaceMenu']//*[text()='Members']");
	
	// Go to My Space -> Select a space > click Application tab
	public final By ELEMENT_APPLICATIONS_TAB = By.xpath("//div[text()='Applications']");
	
	// Go to Account Name link	
//	public final By ELEMENT_MY_PROFILE_LINK = By.linkText("My Profile");
//	public final By ELEMENT_FIND_CONNECTIONS_LINK = By.linkText("Find Connections");
//	public final By ELEMENT_MY_CONNECTIONS_LINK = By.linkText("My Connections");

//	public final By ELEMENT_TOOLBAR_PROFILE_ICON = By.xpath("//*[@id='UserNavigationTabsContainer']/ul/li[2]/ul/li[1]/a[@class='ToolBarProfileIcon']");
//	public final By ELEMENT_TOOLBAR_NETWORKS_ICON = By.xpath("//*[@id='UserNavigationTabsContainer']/ul/li[2]/ul/li[1]/a[@class='ToolBarNetworkIcon']");
//	public final By ELEMENT_FIND_PEOPLE_ICON = By.xpath("//*[@id='UserNavigationTabsContainer']/ul/li[2]/ul/li[2]/a[@class='FindPeopleIcon']");
//	public final By ELEMENT_TOOLBAR_ACTIVITY_ICON = By.xpath("//*[@id='UserNavigationTabsContainer']/ul/li[2]/ul/li[2]/a[@class='ToolBarActivityIcon']");
	public final By ELEMENT_TITLEBAR_BOLD = By.xpath("//div[@class='TitleBar Bold']");

	///
	public final By ELEMENT_TOOLBAR_PROFILE_ICON = By.linkText("My Profile");
	public final By ELEMENT_TOOLBAR_NETWORKS_ICON = By.linkText("My Connections");
	public final By ELEMENT_FIND_PEOPLE_ICON = By.linkText("Find Connections");
	public final By ELEMENT_TOOLBAR_ACTIVITY_ICON = By.linkText("Activity Stream");

	// Go to Account Name link > My Profile 
	//public final By ELEMENT_ACTIVITY_STREAM_LINK = By.linkText("Activity Stream");
	public final By ELEMENT_PROFILE_LINK = By.linkText("Profile");
	public final By ELEMENT_CONNECTIONS_LINK = By.linkText("Connections");
	public final By ELEMENT_ALL_UPDATES_TAB = By.linkText("All Updates");
	public final By ELEMENT_MY_SPACES_TAB = By.xpath("//a[@class='ActionButton LightBlueStyle' and text()='My Spaces']");
	public final By ELEMENT_MY_STATUS_TAB = By.linkText("My Status");
	public final By ELEMENT_CHANGE_PICTURE = By.linkText("Change Picture");
	public String ELEMENT_PROFILE_NAME_LINK = "//a[contains(@class,'CommunityName') and text()='${name}']";
	public String ELEMENT_PROFILE_FULLNAME = "//div[@id='UIProfile']//h2[contains(text(),'${name}')]";
	public String ELEMENT_ACTIVITYSTREAM_TITLE = "//div[contains(@id,'ActivityContextBox')]/h5/a[@title='${name}']";

	// Activity Stream tab	
	public final By ELEMENT_ACTIVITY_STREAM_TAB = By.xpath("//div[@id='UIProfileNavigationPortlet']//a[text()='Activity Stream']");
	
    /*------------------- End of parameters ---------------------*/	
	
	/**
	 * Common functions for Social
	 */
	// Go to My Space Page
	/**
	 * Migrate to PLF 4
	 * <li> Update by @author vuna2 </li>
	 */
	public void goToMySpacePage(){
		info("--Go to My Space Page--");
		//waitForElementPresent(ELEMENT_MY_SPACES_LINK);
		//click(ELEMENT_MY_SPACES_LINK);
		Utils.pause(500);
		click(ELEMENT_JOIN_SPACE_LINK);
		waitForTextPresent("Add New Space");
	}

	// Go to All Spaces
	public void goToAllSpaces(){
		info("Go to All Spaces");
		goToMySpacePage();
		waitForElementPresent(ELEMENT_ALL_SPACE_LINK);
		click(ELEMENT_ALL_SPACE_LINK);
	}

	// Go to Invitation Receives
	public void goToInvitationReceives(){
		info("Go to Invitation Receives");
		goToMySpacePage();
		waitForElementPresent(ELEMENT_INVATATION_RECEIVES_LINK);
		click(ELEMENT_INVATATION_RECEIVES_LINK);	
	}
	// Go to Request Pending
	public void goToRequestsPeding(){
		info("Go to Requests Pending");
		goToMySpacePage();
		waitForElementPresent(ELEMENT_REQUESTS_PENDING_LINK);
		click(ELEMENT_REQUESTS_PENDING_LINK);	
	}

	// Go to Settings
	public void goToSettings(){
		click(ELEMENT_SETTINGS);
		waitForTextPresent("Settings");
	}
	
	// Go to Access and Edit tab
	public void goToAccessAndEdit(){
		click(ELEMENT_ACCESS_AND_EDIT_TAB);
		waitForTextPresent("Access & Edit");
	}
	
	/**
	 * Go to Access and Edit tab
	 * @author vuna2
	 * @param spaceName: name of space (String)
	 */
	public void goToAccessAndEdit(String spaceName){
		goToMySpacePage();
		doAction("Edit", spaceName);
		goToAccessAndEdit();
	}
	
	// Go to Member tab
	public void goToMembers(){
		click(ELEMENT_MEMBERS_TAB);
		waitForTextPresent("Members");
	}
	
	/**
	 * Go to Member tab of space
	 * @author vuna2
	 * @param spaceName: name of space (String)
	 */
	public void goToMembers(String spaceName){
		goToMySpacePage();
		doAction("Edit", spaceName);
		goToMembers();
	}
	
	// Go to Applications tab
	public void goToApplications(){
		click(ELEMENT_APPLICATIONS_TAB);
		waitForTextPresent("Applications");
	}
	
	/**
	 * Go to Applications tab
	 * @author vuna2
	 * @param spaceName: name of space (String)
	 */
	public void goToApplications(String spaceName){
		goToMySpacePage();
		doAction("Edit", spaceName);
		goToApplications();
	}
	
	// Go to Navigation tab
	public void goToNavigation(){
		click(ELEMENT_NAVIGATION_TAB);
		waitForTextPresent("Navigations");
	}
	
	/**
	 * Go to Navigation tab
	 * @author vuna2
	 * @param spaceName: name of space (String)
	 */
	public void goToNavigation(String spaceName){
		goToMySpacePage();
		doAction("Edit", spaceName);
		goToNavigation();
	}
	
	// Access a space
	public void accessSpace(String spaceName) {
		goToMySpacePage();
		click(By.xpath("//*[@id='UIManageMySpaces']//*[text()= '"+ spaceName +"']"));
		waitForTextNotPresent("Add New Space");
	}
	
	// Go to My Profile
	public void goToMyProfile(){
		info("--Go to My Profile--");
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		mouseOver(ELEMENT_TOOLBAR_PROFILE_ICON, true);
		click(ELEMENT_TOOLBAR_PROFILE_ICON);
		waitForTextPresent("Basic information");
	}	

	// Go to Find Connections
	public void goToFindConnections(){
		info("--Go to Find Connections--");
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		//mouseOver(ELEMENT_FIND_PEOPLE_ICON, true);
		mouseOver(ELEMENT_FIND_PEOPLE_ICON,true);
		click(ELEMENT_FIND_PEOPLE_ICON);
		waitForTextPresent("Contacts Directory");
	}	
	
	// Go to My Connections
	public void goToMyConnections(){
		info("--Go to My Connections--");
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		mouseOver(ELEMENT_TOOLBAR_NETWORKS_ICON, true);
		click(ELEMENT_TOOLBAR_NETWORKS_ICON);
		waitForElementPresent(ELEMENT_TITLEBAR_BOLD);
	}	
	
	// Go to Activity stream
	public void goToActivityStream(){
		info("--Go to Activity Stream--");
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		mouseOver(ELEMENT_TOOLBAR_ACTIVITY_ICON, true);
		click(ELEMENT_TOOLBAR_ACTIVITY_ICON);
		waitForTextPresent("All Updates");
	}	
	
	////////////
	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Do a action on space such as Edit, delete, ...
	 * 
	 * @param action : Action name 
	 * @param spaceName : Space name
	 */
	public void doAction(String action, String spaceName){
		By actionLink = By.xpath("//a[text()='" + spaceName + "']/../../../div/button[text()='" + action + "']");
				//("//a[text()='" + spaceName + "']/ancestor::div[contains(@class,'ContentBox')]//a[text()='" + action + "']");
		click(actionLink);
		Utils.pause(1000);
	}

}