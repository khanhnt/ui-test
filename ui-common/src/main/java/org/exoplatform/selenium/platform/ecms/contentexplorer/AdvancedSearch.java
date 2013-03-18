package org.exoplatform.selenium.platform.ecms.contentexplorer;

import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author vuna2
 *
 */
public class AdvancedSearch extends EcmsBase{

	public AdvancedSearch(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	//Advanced search form
	public final By ELEMENT_SAVED_SEARCH_ICON = By.className("uiIconEcmsSavedSearchesMini"); 
	public final By ELEMENT_ADVANCED_SEARCH_ICON = By.xpath("//*[@title='Advanced Search']");
	public final By ELEMENT_ADVANCED_SEARCH_TAB = By.xpath("//*[@id='UIECMSearch']//*[contains(text(),'Advanced Search')]");
	public final By ELEMENT_CONSTRAINT_FORM = By.xpath("//*[text()='Show/Hide Constraint Form']");
	
	//Advanced search form > Show/Hide constraint form
	public final By ELEMENT_ADD_CATEGORY_IMG = By.xpath("//*[@data-original-title = 'Add category']");
	public final By ELEMENT_ADD_CATEGORY_CHECKBOX = By.id("categoryPro");
	
	//Go to advanced search in content explorer
	public void goToAdvancedSearch(){
		click(ELEMENT_SAVED_SEARCH_ICON);
		if (isElementNotPresent(By.xpath("//*[@data-original-title = 'Advanced Search']"))){
			click(By.xpath("//*[@data-original-title = 'Advanced Search']"));
		}else if (isElementPresent(ELEMENT_ADVANCED_SEARCH_ICON)){
			click(ELEMENT_ADVANCED_SEARCH_ICON);
		}
		click(ELEMENT_ADVANCED_SEARCH_TAB);
		waitForElementPresent(ELEMENT_CONSTRAINT_FORM);
		//Utils.pause(500);
	}
}
