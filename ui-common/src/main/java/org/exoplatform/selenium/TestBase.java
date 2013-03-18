package org.exoplatform.selenium;

import static org.exoplatform.selenium.TestLogger.*;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class TestBase {
	protected WebDriver driver;
	protected String baseUrl;
	protected int DEFAULT_TIMEOUT = 30000; //milliseconds = 30 seconds
	protected int WAIT_INTERVAL = 1000; //milliseconds  
	public int loopCount = 0;	
	protected boolean ieFlag;	 
	protected boolean chromeFlag;
	public final int ACTION_REPEAT = 5;
	//public static boolean agreementCheck = false;

	//public final String AJAX_LOADING_MASK = "//div[@id='AjaxLoadingMask']";
	public final String DEFAULT_BASEURL="http://localhost:8080/portal";

	public void initSeleniumTest(){
		String browser = System.getProperty("browser");
		if("chrome".equals(browser)){
			driver = new ChromeDriver();
			chromeFlag = true;
		} else if ("iexplorer".equals(browser)){
			driver = new InternetExplorerDriver();
			ieFlag = true;
		} else {
			driver = new FirefoxDriver();
		}
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
		//		termsAndConditions();
	}

	//	public void termsAndConditions(){
	//		if (agreementCheck) return;
	//		driver.get(baseUrl);
	//		WebElement element = waitForAndGetElement(By.id("agreement"), 5000, 0);
	//		if (element != null) {
	//			check(By.id("agreement"));
	//			click(By.linkText("Continue"));
	//			agreementCheck = true;
	//		}
	//	}

	public WebElement getElement(Object locator) {
		By by = locator instanceof By ? (By)locator : By.xpath(locator.toString());
		WebElement elem = null;
		try {
			elem = driver.findElement(by);
		} catch (NoSuchElementException e) {

		}
		return elem;
	}

	//return element only in case the element is displayed.
	public WebElement getDisplayedElement(Object locator, Object... opParams) {
		By by = locator instanceof By ? (By)locator : By.xpath(locator.toString());
		WebElement e = null;
		try {
			e = driver.findElement(by);
			if (e != null){
				if (e.isDisplayed()) return e;
			}
		} catch (NoSuchElementException ex) {
			info("NoSuchElementException");
		}catch(StaleElementReferenceException ex)
		{
			checkCycling(ex, 10);
			Utils.pause(WAIT_INTERVAL);
			getDisplayedElement(locator);
		}
		finally{
			loopCount=0;
		}
		return null;
	}

	public boolean isElementPresent(Object locator) {
		return getElement(locator) != null;
	}

	public boolean isElementNotPresent(Object locator) {
		return !isElementPresent(locator);
	}
	/*
	 * @opPram[0]: timeout
	 * @opPram[1]: 0,1
	 * 0: No Assert
	 * 1: Assert
	 */
	public WebElement waitForElementPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length>0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1]: 1;
		int notDisplayE = opParams.length > 2 ? opParams[2]: 0;

		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
			if (notDisplayE == 2){
				elem = getElement(locator);
				//elem = getDisplayedElement(locator);
			}else{
				elem = getDisplayedElement(locator);
			}
			if (null != elem) return elem;
			Utils.pause(WAIT_INTERVAL);
		}
		if (isAssert == 1)
			assert false: ("Timeout after " + timeout + "ms waiting for element present: " + locator);
		return null;
	}


	/*
	 * @opPram[0]: timeout
	 * @opPram[1]: 0,1
	 * 0: No Assert
	 * 1: Assert
	 */
	public WebElement waitForElementNotPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1]: 1;
		int notDisplayE = opParams.length > 2 ? opParams[2]: 0;

		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
			if (notDisplayE == 2){
				elem = getElement(locator);
				//elem = getDisplayedElement(locator);
			}else{
				elem = getDisplayedElement(locator);
			}
			if (null == elem) return null;
			Utils.pause(WAIT_INTERVAL);
		}

		if (isAssert == 1)
			assert false: ("Timeout after " + timeout + "ms waiting for element not present: " + locator);
		return elem;
	}
	//
	// public WebElement waitForAndGetElement(Object locator, int... timeInMillis) {
	// WebElement elem = null;
	// int timeout = timeInMillis.length>0 ? timeInMillis[0] : DEFAULT_TIMEOUT;
	// for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
	// elem = getElement(locator);
	// if (null != elem) return elem;
	// Utils.pause(WAIT_INTERVAL);
	// }
	// debug("Timeout after " + timeout + "ms waiting for element " + locator);
	// return elem;
	// }

	/*
	 * @opPram[0]: timeout
	 * @opPram[1]: 0,1
	 * 0: No Assert
	 * 1: Assert
	 */
	public WebElement waitForAndGetElement(Object locator, Object... opParams) {
		WebElement elem = null;
		int timeout = (Integer) (opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT);
		int isAssert = (Integer) (opParams.length > 1 ? opParams[1]: 1);
		int notDisplayE = (Integer) (opParams.length > 2 ? opParams[2]: 0);

		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
			if (notDisplayE == 2){
				elem = getElement(locator);
			}else{
				elem = getDisplayedElement(locator);
			}
			if (null != elem)
				return elem;
			Utils.pause(WAIT_INTERVAL);
		}
		if (isAssert == 1)	
			assert false: ("Timeout after " + timeout + "ms waiting for element present: " + locator);
		return null;
	}

	public boolean isTextPresent(String text) {
		Utils.pause(500);
		String allVisibleTexts = getText(By.xpath("//body"));
		return allVisibleTexts.contains(text);
	}

	public String getText(Object locator) {
		WebElement element = null;
		try {
			element = waitForAndGetElement(locator);
			return element.getText();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			return getText(locator);
		} finally {
			loopCount = 0;
		}
	}

	public List<WebElement> getElements(String xpath) {
		try {
			return driver.findElements(By.xpath(xpath));
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			Utils.pause(1000);
			return getElements(xpath);
		} finally {
			loopCount = 0;
		}
	}

	public boolean isTextNotPresent(String text) {
		return !isTextPresent(text);
	}

	public void dragAndDropToObject(Object sourceLocator, Object targetLocator) {
		info("--Drag and drop to object--");
		Actions action = new Actions(driver);
		try {
			WebElement source = waitForAndGetElement(sourceLocator);
			WebElement target = waitForAndGetElement(targetLocator);

			action.dragAndDrop(source, target).build().perform();

		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			dragAndDropToObject(sourceLocator, targetLocator);
		}catch (UnhandledAlertException e) {
			/*checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			dragAndDropToObject(sourceLocator, targetLocator);*/
			//acceptAlert();
			try {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				switchToParentWindow();
			} catch (NoAlertPresentException eNoAlert) {
			}
		}

		finally {
			loopCount = 0;
		}
	}

	public void click(Object locator, Object... opParams) {
		//Actions actions = new Actions(driver);
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0]: 0);		
		Actions actions = new Actions(driver);
		try {
			WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, notDisplay);
			if(element.isEnabled())
				actions.click(element).perform();
			else {
				debug("Element is not enabled");
				click(locator, notDisplay);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} finally {
			loopCount = 0;
		}
	}

	public void clearCache(){
		Actions actionObject = new Actions(driver);
		try{
			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		} catch(WebDriverException e){	
			debug("Retrying clear cache...");
			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		}
	}

	//Use this function to verify if a check-box is checked (using when creating a portal/publicMode)
	public void check(Object locator) {
		Actions actions = new Actions(driver);
		try {
			WebElement element = waitForAndGetElement(locator);

			if (!element.isSelected()) {
				actions.click(element).perform();
			} else {
				Assert.fail("Element " + locator + " is already checked.");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			check(locator);
		} finally {
			loopCount = 0;
		}
	}

	public String getValue(Object locator) {
		try {
			return waitForAndGetElement(locator).getAttribute("value");
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			return getValue(locator);
		} finally {
			loopCount = 0;
		}
	}

	public void mouseOver(Object locator, boolean safeToSERE) {
		WebElement element;
		Actions actions = new Actions(driver);
		try {
			if (safeToSERE) {
				for (int i = 1; i < ACTION_REPEAT; i++){
					element = waitForAndGetElement(locator, 5000, 0);
					if (element == null){
						Utils.pause(WAIT_INTERVAL);
					} else {
						actions.moveToElement(element).perform();
						break;
					}
				}
			} else {
				element = waitForAndGetElement(locator);
				actions.moveToElement(element).perform();
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			mouseOver(locator, safeToSERE);
		} finally {
			loopCount = 0;
		}
	}

	public void mouseOverAndClick(Object locator) {
		WebElement element;
		Actions actions = new Actions(driver);
		if (ieFlag) {
			element = getDisplayedElement(locator);
		} else {
			element = waitForAndGetElement(locator);
		}
		actions.moveToElement(element).click(element).build().perform();
	}

	public void waitForTextPresent(String text, int...wait) {
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		for (int second = 0;; second++) {
			if (second >= waitTime/WAIT_INTERVAL) {
				Assert.fail("Timeout at waitForTextPresent: " + text);
			}
			if (isTextPresent(text)) {
				break;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}

	public void waitForTextNotPresent(String text,int...wait) {
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		for (int second = 0;; second++) {
			if (second >= waitTime/WAIT_INTERVAL) {
				Assert.fail("Timeout at waitForTextNotPresent: " + text);
			}
			if (isTextNotPresent(text)) {
				break;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}

	public void waitForMessage(String message,int...wait) {
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		//info("--Verify message: " + message);
		Utils.pause(500);
		waitForTextPresent(message, waitTime);
	}

	public void type(Object locator, String value, boolean validate) {
		try {
			for (int loop = 1;; loop++) {
				if (loop >= ACTION_REPEAT) {
					Assert.fail("Timeout at type: " + value + " into " + locator);
				}
				WebElement element = waitForAndGetElement(locator, 5000, 0);
				if (element != null){
					if (validate) element.clear();
					element.click();
					element.sendKeys(value);
					if (!validate || value.equals(getValue(locator))) {
						break;
					}
				}
				info("Repeat action..." + loop + "time(s)");
				Utils.pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			type(locator, value, validate);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			type(locator, value, validate);
		} finally {
			loopCount = 0;
		}
	}

	// Select option from combo box
	public void select(Object locator, String option) {
		try {
			for (int second = 0;; second++) {
				if (second >= DEFAULT_TIMEOUT/WAIT_INTERVAL) {
					Assert.fail("Timeout at select: " + option + " into " + locator);
				}
				Select select = new Select(waitForAndGetElement(locator));
				select.selectByVisibleText(option);
				if (option.equals(select.getFirstSelectedOption().getText())) {
					break;
				}
				Utils.pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			select(locator, option);
		} finally {
			loopCount = 0;
		}
	}

	//un-check a checked-box
	public void uncheck(Object locator) {
		Actions actions = new Actions(driver);
		try {
			WebElement element = waitForAndGetElement(locator);

			if (element.isSelected()) {
				actions.click(element).perform();
			} else {
				Assert.fail("Element " + locator + " is already unchecked.");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			Utils.pause(1000);
			uncheck(locator);
		} finally {
			loopCount = 0;
		}
	}
	public void rightClickOnElement(Object locator) {
		Actions actions = new Actions(driver);
		Utils.pause(500);
		try {
			WebElement element = waitForAndGetElement(locator);
			actions.contextClick(element).perform();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			rightClickOnElement(locator);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			click(locator);
		} finally {
			loopCount = 0;
		}
	}

	//doubleClickOnElement
	public void doubleClickOnElement(Object locator) {
		Actions actions = new Actions(driver);
		try {
			WebElement element = waitForAndGetElement(locator);
			actions.doubleClick(element).perform();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			Utils.pause(1000);
			doubleClickOnElement(locator);
		} finally {
			loopCount = 0;
		}
	}

	public void checkCycling(Exception e, int loopCountAllowed) {
		info("Exception:" + e.getClass().getName());
		if (loopCount > loopCountAllowed) {
			Assert.fail("Cycled: " + e.getMessage());
		}
		info("Repeat... " + loopCount + "time(s)");
		loopCount++;
	}

	//function to switch to parent windows
	public void switchToParentWindow (){
		try
		{
			Set<String> availableWindows = driver.getWindowHandles();
			String WindowIdParent= null;
			int counter = 1;
			for (String windowId : availableWindows) {
				if (counter == 1){
					WindowIdParent = windowId;
				}
				counter++;
			}
			driver.switchTo().window(WindowIdParent);
			Utils.pause(1000);
		}
		catch (WebDriverException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isDisplay(Object locator) {
		boolean bool = false;
		WebElement e = getElement(locator);
		try{
			if (e!=null)
				bool = e.isDisplayed();
		}catch(StaleElementReferenceException ex)
		{
			checkCycling(ex, 10);
			Utils.pause(WAIT_INTERVAL);
			isDisplay(locator);
		}
		finally{
			loopCount=0;
		}
		return bool;
	}

	//////
	/** function: set driver to auto save file to TestData/TestOutput
	 * @author lientm
	 */
	public void getDriverAutoSave(){
		String pathFile = System.getProperty("user.dir") + "/src/main/resources/TestData/TestOutput";

		FirefoxProfile fp = new FirefoxProfile();	
		fp.setPreference("browser.download.folderList", 2);
		info("Save file to " + pathFile);
		fp.setPreference("browser.download.dir", pathFile);
		fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-xpinstall;application/x-zip;application/x-zip-compressed;application/octet-stream;application/zip;application/pdf;application/msword;text/plain;application/octet");
		driver = new FirefoxDriver(fp);
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
	}

	/**function set driver to auto open new window when click link
	 * @author lientm
	 */
	public void getDriverAutoOpenWindow(){
		FirefoxProfile fp = new FirefoxProfile();		
		fp.setPreference("browser.link.open_newwindow.restriction", 2);
		driver = new FirefoxDriver(fp);
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
	}
}
