package org.exoplatform.selenium;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * <li> This class provides simple way to manage all type of Alert in PLF4 </li>
 * @author vuna2
 * @Date January, 30th, 2013
 *
 */
public class ManageAlert extends TestBase{

	public ManageAlert(WebDriver dr) {
		driver = dr;
	}
	
	Button button = new Button(driver);

	//ECMS > Symlink
	public final By ELEMENT_ALERT = By.xpath("//*[contains(@class, 'popupTitle') and contains(text(), 'Warning')]");
	public final By ELEMENT_MESSAGE = By.xpath("//*[contains(@class, 'warningIcon')]");
	public final By ELEMENT_INFO = By.xpath("//*[contains(@class, 'infoIcon')]");

	//Accept an alert
	public void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			switchToParentWindow();
		} catch (NoAlertPresentException e) {
		}
	}

	//Cancel an Alert
	public void cancelAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			switchToParentWindow();
		} catch (NoAlertPresentException e) {
		}
	}

	//Get Text
	public String getTextFromAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		} catch (NoAlertPresentException e) {
			return "";
		}
	}

	//Accept a confirmation
	public void waitForConfirmation(String confirmationText) {
		String message = getTextFromAlert();

		//log("confirmation: " + message);

		if (message.isEmpty()) {
			if (loopCount > 5) {
				Assert.fail("Message is empty");
			}
			Utils.pause(500);
			loopCount++;
			waitForConfirmation(confirmationText);
			return;
		}

		for (int second = 0;; second++) {
			if (second >= DEFAULT_TIMEOUT) {
				Assert.fail("Timeout at waitForConfirmation: " + confirmationText);
			}
			if (message.equals(confirmationText)) {
				break;
			}
			Utils.pause(100);
		}
		Alert alert = driver.switchTo().alert();
		alert.accept();
		Utils.pause(500);
	}

	//Verify a message
	public void verifyAlertMessage(String message){
		//waitForElementPresent(ELEMENT_ALERT);
		//assert isElementPresent(ELEMENT_ALERT):"Not found alert";
		if (isElementPresent(ELEMENT_MESSAGE)){
			assert getText(ELEMENT_MESSAGE).contains(message):"Message is wrong";
		}else if (isElementPresent(ELEMENT_INFO)){
			assert getText(ELEMENT_INFO).contains(message):"Message is wrong";	
		}
		click(button.ELEMENT_OK_BUTTON);
		Utils.pause(1000);
	}
}
