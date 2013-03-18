package org.exoplatform.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * <li> This class provides simple way to manage all type of buttons in PLF4 </li>
 * @author vuna2
 * @Date January, 30th, 2013
 *
 */
public class Button extends TestBase{

	public Button(WebDriver dr) {
		driver = dr;
	}

	public final By ELEMENT_SAVE_CLOSE_BUTTON = By.xpath("//*[text()='Save & Close']");
	//By.linkText("Save & Close");
	public final By ELEMENT_OK_BUTTON = By.xpath("//*[text()='OK']");
	public final By ELEMENT_APPLY_BUTTON = By.linkText("Apply");
	public final By ELEMENT_SAVE_BUTTON = By.xpath("//*[text()='Save']"); 
	public final By ELEMENT_SAVE_BUTTON_POPUP_WINDOWS = By.xpath("//*[contains(@class, 'popup')]//*[text()='Save']");
	public final By ELEMENT_CANCEL_BUTTON = By.xpath("//*[text()='Cancel']"); 
	public final By ELEMENT_CLOSE_BUTTON = By.xpath("//*[text()='Close']");
	public final By ELEMENT_ADD_BUTTON = By.xpath("//*[text()='Add']");
	public final By ELEMENT_SELECT_BUTTON = By.xpath("//*[text()='Select']");
	public final By ELEMENT_CLOSE_WINDOW = By.xpath("//*[contains(@class, 'uiIconClose') and @title = 'Close Window']");
			//By.className("uiIconClose"); 
	public final By ELEMENT_MOVE_BUTTON = By.xpath("//button[contains(text(), 'Move')]");
	public final By ELEMENT_FINISH_ICON = By.xpath("//a[@title='Finish']"); //Finish editing portlet icon
	public final By ELEMENT_NEXT_BUTTON = By.xpath("//*[text()='Next']");	
	public final By ELEMENT_ABORT_BUTTON = By.xpath("//*[text()='Abort']");
	public final By ELEMENT_CONTINUE_BUTTON = By.xpath("//button[text()='Continue']");
	public final By ELEMENT_START_BUTTON = By.xpath("//button[text()='Start']");
	public final By ELEMENT_SUBMIT_BUTTON = By.xpath("//*[text()='Submit']");
	
	public final By ELEMENT_PREVIOUS_BUTTON = By.xpath("//*[text()='Previous']");
	//ECMS Admin > Category > Add Category
	public final By ELEMENT_PREVIOUS_BUTTON_ADMIN_4 = By.xpath("//*[@class='UITaxonomyTreeCreateChild']//*[text()='Previous']");
	public final By ELEMENT_PREVIOUS_BUTTON_ADMIN_3 = By.xpath("//*[@class='UIAction']//*[text()='Previous']");

	//Content explorer 
	public final By ELEMENT_REFRESH_BUTTON = By.xpath("//*[text()='refresh']");
	public final By ELEMENT_RENAME_BUTTON = By.xpath("//*[text()='Rename']");

	/*
	 * General
	 * 
	 */
	public void save() {
		waitForAndGetElement(ELEMENT_SAVE_BUTTON);
		click(ELEMENT_SAVE_BUTTON);
		Utils.pause(500);
	}

	public void close(){
		waitForAndGetElement(ELEMENT_CLOSE_BUTTON);
		click(ELEMENT_CLOSE_BUTTON);
		Utils.pause(500);
	}

	public void cancel(){
		waitForAndGetElement(ELEMENT_CANCEL_BUTTON);
		click(ELEMENT_CANCEL_BUTTON);
	}

	public void saveAndClose(){
		waitForAndGetElement(ELEMENT_SAVE_CLOSE_BUTTON);
		click(ELEMENT_SAVE_CLOSE_BUTTON);
		Utils.pause(500);
	}

	public void apply(){
		waitForAndGetElement(ELEMENT_APPLY_BUTTON);
		click(ELEMENT_APPLY_BUTTON);
	}

	public void next(){
		waitForAndGetElement(ELEMENT_NEXT_BUTTON);
		click(ELEMENT_NEXT_BUTTON);	
		Utils.pause(500);
	}

	public void refresh(){	
		waitForAndGetElement(ELEMENT_REFRESH_BUTTON);
		click(ELEMENT_REFRESH_BUTTON);
		Utils.pause(500);
	}

	public void rename(){
		waitForAndGetElement(ELEMENT_RENAME_BUTTON);
		click(ELEMENT_RENAME_BUTTON);
		Utils.pause(500);
	}

	public void previous(){
		waitForAndGetElement(ELEMENT_PREVIOUS_BUTTON);
		click(ELEMENT_PREVIOUS_BUTTON);	
		Utils.pause(500);
	}
	
	public void closeWindow(){
		if (isElementPresent(ELEMENT_CLOSE_WINDOW)){
			click(ELEMENT_CLOSE_WINDOW);
		}else if (isElementPresent(By.xpath("//*[contains(@class, 'wikiPreviewHeader')]//*[contains(@class, 'uiIconClose')]"))){
			click(By.xpath("//*[contains(@class, 'wikiPreviewHeader')]//*[contains(@class, 'uiIconClose')]"));
		}
		Utils.pause(500);
	}

}
