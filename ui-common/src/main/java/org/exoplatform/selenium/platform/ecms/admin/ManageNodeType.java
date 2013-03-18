package org.exoplatform.selenium.platform.ecms.admin;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author vuna2
 *
 */
public class ManageNodeType extends EcmsBase{

	public ManageNodeType(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	/*//Repository
	public final By ELEMENT_CONTENT_TYPES = By.xpath("//div[contains(text(),'Content Types')]");
	public final By ELEMENT_MANAGE_NODETYPE = By.linkText("Manage Node Type");*/
	public final By ELEMENT_NODETYPE_TEXT = By.id("NodeTypeText");
	public final By ELEMENT_NODETYPE_SEARCH = By.xpath("//*[@title = 'Search']");
	
	//Message
	public final String MESSAGE_FOR_NO_INPUT_KEYWORD = "The value of the field is empty.";
	public final String MESSAGE_FOR_SPECIAL_KEYWORD = "There are some invalid characters. Please enter another value.";
	public final String MESSAGE_FOR_NOT_MATCH_KEYWORD = "There isn't any node type with condition search.";
	
	//Do a search in Node Types Tab
	public void doNodeTypeSearch(String keyword){
		type(ELEMENT_NODETYPE_TEXT, keyword, true);
		if (isElementPresent(ELEMENT_NODETYPE_SEARCH)){
			click(ELEMENT_NODETYPE_SEARCH);
		}else{
			WebElement search = driver.findElement(By.xpath("//*[contains(@href,'SearchNodeType')]"));
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", search);
		}
		Utils.pause(1000);
	}
}
