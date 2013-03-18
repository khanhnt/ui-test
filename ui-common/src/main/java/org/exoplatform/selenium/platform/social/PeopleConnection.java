package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.*;

import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;

/**
 * 
 * @author thaopth
 * Date: 09/10/2012
 */
/**
 * Update By vuna2
 * -- Add removeConnection 
 */
public class PeopleConnection extends SocialBase {

	// Go to Account Name link	
	// My Connections
	public final By ELEMENT_REQUESTS_RECEIVED_TAB = By.linkText("Requests Received");
	public final By ELEMENT_MY_CONNECTIONS_TAB = By.linkText("My Connections");
	public final By ELEMENT_EVERYONE_TAB = By.linkText("Everyone");
	public final By ELEMENT_REQUEST_SENT_TAB = By.linkText("Requests Sent");
	public final String ELEMENT_CONNECTION = "//*[@id='UIDisplayProfileList']/div[2]/div/a/img[@title='${peopleName}']";
	public final String ELEMENT_CANCEL_REQUEST_BUTTON = "//div/a[text()='${peopleName}']/following::ul/li/a[@title='Cancel Request']";
	
	/**
	 * Connect to people
	 * @param peopleName: name of selected people (String)
	 */
	public void connectPeople (String peopleName) {
		info("-- Connect to: " + peopleName);
		
		//By ELEMENT_CONNECT_BUTTON = By.xpath("//div/a[text()='"+peopleName+"']/following::ul/li/a[@title='Connect']");
		By ELEMENT_CONNECT_BUTTON = By.xpath(ELEMENT_CONNECTION.replace("${peopleName}", peopleName) + "/../../ul/li/a[@title='Connect']");
		//By ELEMENT_CANCEL_REQUEST_BUTTON = By.xpath("//div/a[text()='"+peopleName+"']/following::ul/li/a[@title='Cancel Request']");
		//By ELEMENT_CANCEL_REQUEST_BUTTON = By.xpath(ELEMENT_CONNECTION.replace("${peopleName}", peopleName) + "/../../ul/li[2]/a[@title='Cancel Request']");
		
		info("-----Go to find connections page-----");

		goToFindConnections();

		info("-----Click connect to people-----");

		waitForElementPresent(ELEMENT_CONNECT_BUTTON);

		click(ELEMENT_CONNECT_BUTTON);

		info("---Verify Connect button is disappeared----");

		waitForElementNotPresent(ELEMENT_CONNECT_BUTTON);

		info("-----Verify Cancel request button is displayed-----");

		waitForElementPresent(ELEMENT_CANCEL_REQUEST_BUTTON.replace("${peopleName}", peopleName));
	}

	/**
	 * Accept the invitation
	 * @param peopleName: name of selected people (String)
	 */
	public void acceptInvitation (String peopleName) {
		info("-- Accept the invitation: " + peopleName);
		
		By ELEMENT_CONFIRM_BUTTON = By.xpath("//div/a[text()='"+peopleName+"']/following::ul/li/a[@title='Confirm']");	 
		By ELEMENT_REMOVE_CONNECTION_BUTTON = By.xpath("//div/a[text()='"+peopleName+"']/following::ul/li/a[@title='Remove Connection']");

		info("----Go to My connections----");

		goToMyConnections();

		waitForElementPresent(ELEMENT_REQUESTS_RECEIVED_TAB);

		info("---Click Requests Received tab-----");

		click(ELEMENT_REQUESTS_RECEIVED_TAB);

		waitForElementPresent(ELEMENT_CONFIRM_BUTTON);

		info("----Confirm the invitation from user '"+peopleName+"' ");

		click(ELEMENT_CONFIRM_BUTTON);

		waitForElementNotPresent(ELEMENT_CONFIRM_BUTTON);

		info("----Go to My connections tab----");

		click(ELEMENT_MY_CONNECTIONS_TAB);

		info("---Verify remove connection button----");

		waitForElementPresent(ELEMENT_REMOVE_CONNECTION_BUTTON);	

	}

	/**
	 * Ignore the invitation
	 * @param peopleName: name of selected people (String)
	 */
	public void ignoreInvitation (String peopleName) {
		info("-- Ignore the invitation from: " + peopleName);
		
		By ELEMENT_IGNORE_BUTTON = By.xpath("//div/a[text()='"+peopleName+"']/following::ul/li/a[@title='Ignore']");
		By ELEMENT_CONNECT_BUTTON = By.xpath("//div/a[text()='"+peopleName+"']/following::ul/li/a[@title='Connect']");

		info("----Go to My connections----");

		goToMyConnections();

		waitForElementPresent(ELEMENT_REQUESTS_RECEIVED_TAB);

		info("---Click Requests Received tab-----");

		click(ELEMENT_REQUESTS_RECEIVED_TAB);

		waitForElementPresent(ELEMENT_IGNORE_BUTTON);
		
		info("---Ignore the invitation from user '"+peopleName+"'-----");
		
		click(ELEMENT_IGNORE_BUTTON);
		
		waitForElementNotPresent(ELEMENT_IGNORE_BUTTON);
		
		info("---Go to Everyone tab----");
		
		click(ELEMENT_EVERYONE_TAB);
		
		waitForElementPresent(ELEMENT_CONNECT_BUTTON);			
	}
	
	/**
	 * Remove connection 
	 * @param peopleName: name of selected people (String)
	 */
	public void removeConnection(String peopleName){
		info("-- Remove connection with: " + peopleName);
		
		By ELEMENT_REMOVE_CONNECTION_BUTTON = By.xpath(ELEMENT_CONNECTION.replace("${peopleName}", peopleName) + "/../../ul/li[2]/a[@title='Remove Connection']");
	
		goToFindConnections();
		
		click(ELEMENT_REMOVE_CONNECTION_BUTTON);
		
		waitForElementNotPresent(ELEMENT_REMOVE_CONNECTION_BUTTON);
		
		Utils.pause(1000);
	}
	
	/**
	 * @author dunghm
	 * Cancel a connection request
	 * @param name : User's name that sent connection request
	 */
	/**
	 * Update by vuna2
	 * @param name : User's name that sent connection request 
	 */
  public void cancelRequest(String name){
    String userContainer = "//a[contains(@class,'CommunityName') and text()='" + name + "']/ancestor::div[contains(@class,'ContentSpace')]";
    By cancelRequest = By.xpath(userContainer + "//a[@title='Cancel Request']");
    By cancelRequest_Aux = By.xpath("//*[@id='UISpaceMember']//td[contains(text(), '" + name + "')]/following::span[text()='Cancel Request']");
    if (waitForAndGetElement(cancelRequest, 5000, 0) != null){
    	click(cancelRequest);
    	waitForElementNotPresent(cancelRequest);
    }else if (waitForAndGetElement(cancelRequest_Aux, 5000, 0) != null){
    	click(cancelRequest_Aux);
    	waitForElementNotPresent(cancelRequest_Aux);
    } 
  }
  
  public void quickRemoveConnection(String peopleName){    
    
    By ELEMENT_REMOVE_CONNECTION_BTN = By.xpath("//a[contains(@class,'InviteTitle') and text()='" + peopleName + "']/ancestor::div[contains(@class,'ContentSpace')]//a[text()='Remove Connection']");    
    
    click(ELEMENT_REMOVE_CONNECTION_BTN);
    waitForElementNotPresent(ELEMENT_REMOVE_CONNECTION_BTN);
  }

}
