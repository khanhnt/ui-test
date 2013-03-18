package org.exoplatform.selenium.platform.ecms.contentexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * 
 * @author vuna2
 *
 */
public class BrowserPreferences extends EcmsBase{

	public BrowserPreferences(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}
	
	Button button = new Button(driver);
	
	//Enable preferences option
	public void setUpPreferenceOption(String optionId){
		By option = By.id(optionId);
		By advanced = By.className("advancePreferences");

		for (int repeat = 0;; repeat ++){
			if (repeat >= ACTION_REPEAT) {
				Assert.fail("Cannot enable reference option after " + ACTION_REPEAT + " tries");
			}
			waitForElementPresent(ELEMENT_PREFERENCE_LINK, 5000, 1);
			click(ELEMENT_PREFERENCE_LINK);
			waitForElementPresent(advanced, 5000, 1);
			if (getElement(advanced) != null){
				click(advanced);
				WebElement check = waitForAndGetElement(option, DEFAULT_TIMEOUT, 1, 2);
				if (check.isSelected()!= true){
					click(option, 2);
				}
				button.save();
				if (isTextPresent("Advanced")){
					button.save();
				} 
				//waitForElementNotPresent(advanced);
				break;
			}
			info("Retry...[" + repeat + "]");
		}
	}
}
