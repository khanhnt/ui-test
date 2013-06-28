package org.exoplatform.selenium;

import static org.exoplatform.selenium.TestLogger.*;


import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ErrorHandler.UnknownServerException;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class TestBase {
	public static WebDriver driver;
	public static Actions actions ;
	public static String baseUrl;
	protected static int DEFAULT_TIMEOUT = 30000; //milliseconds = 30 seconds
	protected static int WAIT_INTERVAL = 1000; //milliseconds  
	public static int loopCount = 0;	
	protected static boolean ieFlag;	 
	protected static boolean chromeFlag;
	public static final int ACTION_REPEAT = 5;
	public static boolean agreementCheck = false;

	//public static final String AJAX_LOADING_MASK = "//div[@id='AjaxLoadingMask']";
	public static final String DEFAULT_BASEURL="http://localhost:8080";

	public static void initSeleniumTest(){
		String browser = System.getProperty("browser");
		if("chrome".equals(browser)){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver");
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
		termsAndConditions();
	}
	public static void termsAndConditions(){
		if (agreementCheck) return;
		actions = new Actions(driver);
		driver.get(baseUrl);
		WebElement element = waitForAndGetElement(By.id("agreement"), 5000, 0);
		if (element != null) {
			check(By.id("agreement"));
			click(By.linkText("Continue"));
			agreementCheck = true;
		}
	}
	
	public static WebElement getElement(Object locator) {
		By by = locator instanceof By ? (By)locator : By.xpath(locator.toString());
		WebElement elem = null;
		try {
			elem = driver.findElement(by);
		} catch (NoSuchElementException ex) {
      debug(ex.getMessage() );
		}
		return elem;
	}

	//return element only in case the element is displayed.
	public static WebElement getDisplayedElement(Object locator) {
		By by = locator instanceof By ? (By)locator : By.xpath(locator.toString());
		WebElement e = null;
		try {
			e = driver.findElement(by);
			if (e != null){
				if (e.isDisplayed()) return e;
			}
		} catch (NoSuchElementException ex) {
			debug(ex.getMessage() );
		}catch(StaleElementReferenceException ex)
		{
			checkCycling(ex, 10);
			pause(WAIT_INTERVAL);
			getDisplayedElement(locator);
		}
		finally{
			loopCount=0;
		}
		return null;
	}

	public static boolean isElementPresent(Object locator) {
		return getElement(locator) != null;
	}

	public static boolean isElementNotPresent(Object locator) {
		return !isElementPresent(locator);
	}
	/*
	 * @opPram[0]: timeout
	 * @opPram[1]: 0,1
	 * 				0: No Assert
	 * 				1: Assert
	 */
	public static WebElement waitForElementPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length>0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1]: 1;

		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
			//elem = getElement(locator);
			elem = getDisplayedElement(locator);
			if (null != elem) return elem;
			pause(WAIT_INTERVAL);
		}
		if (isAssert == 1)
			assert false: ("Timeout after " + timeout + "ms waiting for element present: " + locator);
		return null;
	}


	/*
	 * @opPram[0]: timeout
	 * @opPram[1]: 0,1
	 * 				0: No Assert
	 * 				1: Assert
	 */
	public static WebElement waitForElementNotPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1]: 1;

		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
			//			elem = getElement(locator);
			elem = getDisplayedElement(locator);
			if (null == elem) return null;
			pause(WAIT_INTERVAL);
		} 

		if (isAssert == 1)
			assert false: ("Timeout after " + timeout + "ms waiting for element not present: " + locator);
		return elem;
	}
	//
	//  public WebElement waitForAndGetElement(Object locator, int... timeInMillis) {
	//    WebElement elem = null;
	//    int timeout = timeInMillis.length>0 ? timeInMillis[0] : DEFAULT_TIMEOUT;
	//    for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
	//      elem = getElement(locator);
	//      if (null != elem) return elem;
	//      pause(WAIT_INTERVAL);
	//    }
	//    debug("Timeout after " + timeout + "ms waiting for element " + locator);
	//    return elem;
	//  }

	/*
	 * @opPram[0]: timeout
	 * @opPram[1]: 0,1
	 * 				0: No Assert
	 * 				1: Assert
	 */
	public static WebElement waitForAndGetElement(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1]: 1;

		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
			//elem = getElement(locator);
			elem = getDisplayedElement(locator);
			if (null != elem)
				return elem;
			pause(WAIT_INTERVAL);
		}
		if (isAssert == 1)		
			assert false: ("Timeout after " + timeout + "ms waiting for element present: " + locator);
		return null;
	}

	public static boolean isTextPresent(String text) {
		pause(500);
		String allVisibleTexts = getText(By.xpath("//body"));
		return allVisibleTexts.contains(text);
	}

	public static String getText(Object locator) {
		WebElement element = null;
		try {
			element = waitForAndGetElement(locator);
			return element.getText();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			return getText(locator);
		} finally {
			loopCount = 0;
		}
	}

	public static List<WebElement> getElements(String xpath) {
		try {
			return driver.findElements(By.xpath(xpath));
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			return getElements(xpath);
		} finally {
			loopCount = 0;
		}
	}

	public static boolean isTextNotPresent(String text) {
		return !isTextPresent(text);
	}

	public static String getTextFromAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		} catch (NoAlertPresentException e) {
			return "";
		}
	}


	public static void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			switchToParentWindow();
		} catch (NoAlertPresentException e) {
		}
	}
	
	public static void cancelAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			switchToParentWindow();
		} catch (NoAlertPresentException e) {
		}
	}

	public static void pause(long timeInMillis) {
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void dragAndDropToObject(Object sourceLocator, Object targetLocator) {
		info("--Drag and drop to object--");
		Actions action = new Actions(driver);
		try {
			WebElement source = waitForAndGetElement(sourceLocator);
			WebElement target = waitForAndGetElement(targetLocator);

			action.dragAndDrop(source, target).build().perform();

		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			dragAndDropToObject(sourceLocator, targetLocator);
		}catch (UnhandledAlertException e) {
			/*checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			dragAndDropToObject(sourceLocator, targetLocator);*/
			acceptAlert();
		}

		finally {
			loopCount = 0;
		} 
	}

	public static void click(Object locator) {
		try {
			WebElement element = waitForAndGetElement(locator);
			if(element.isEnabled())
				actions.click(element).perform();
			else {
				debug("Element is not enabled");
				click(locator);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			click(locator);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			click(locator);
		} finally {
			loopCount = 0;
		}
	}

	public static void clearCache(){
		Actions actionObject = new Actions(driver);
		try{

			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		} catch(WebDriverException e){	
			debug("Retrying clear cache...");
			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		}
	}

	//Use this function to verify if a check-box is checked (using when creating a portal/publicMode)
	public static void check(Object locator) {
		try {
			WebElement element = waitForAndGetElement(locator);

			if (!element.isSelected()) {
				click(locator);
			} else {
				info("Element " + locator + " is already checked.");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			check(locator);
		} finally {
			loopCount = 0;
		}
	}

	public static String getValue(Object locator) {
		try {
			return waitForAndGetElement(locator).getAttribute("value");
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			return getValue(locator);
		} finally {
			loopCount = 0;
		}
	}

	public static void mouseOver(Object locator, boolean safeToSERE) {
		WebElement element;
		try {
			if (safeToSERE) {
				for (int i = 1; i < ACTION_REPEAT; i++){
					element = waitForAndGetElement(locator, 5000, 0);
					if (element == null){
						pause(WAIT_INTERVAL);
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
			pause(WAIT_INTERVAL);
			mouseOver(locator, safeToSERE);
		} finally {
			loopCount = 0;
		}
	}

	public static void mouseOverAndClick(Object locator) {
		WebElement element;
		if (ieFlag) {
			element = getDisplayedElement(locator);
		} else {
			element = waitForAndGetElement(locator);
		}
		try {
			actions.moveToElement(element).click(element).build().perform();
		} catch (StaleElementReferenceException e) {
		} catch (ElementNotVisibleException e){		
		}
	}

	public static void waitForTextPresent(String text,int...wait) {
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		for (int second = 0;; second++) {
			if (second >= waitTime/WAIT_INTERVAL) {
				Assert.fail("Timeout at waitForTextPresent: " + text);
			}
			if (isTextPresent(text)) {
				break;
			}
			pause(WAIT_INTERVAL);
		}
	}

	public static void waitForTextNotPresent(String text,int...wait) {
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		for (int second = 0;; second++) {
			if (second >= waitTime/WAIT_INTERVAL) {
				Assert.fail("Timeout at waitForTextNotPresent: " + text);
			}
			if (isTextNotPresent(text)) {
				break;
			}
			pause(WAIT_INTERVAL);
		}
	}

	public static void waitForMessage(String message,int...wait) {
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		//info("--Verify message: " + message);
		pause(500);
		waitForTextPresent(message,waitTime);
	}

	public static void type(Object locator, String value, boolean validate) {
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
				pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			type(locator, value, validate);
		}  catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			type(locator, value, validate);
		} finally {
			loopCount = 0;
		}
	}

	// Select option from combo box
	public static void select(Object locator, String option) {
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
				pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			select(locator, option);
		} finally {
			loopCount = 0;
		}
	}

	//un-check a checked-box
	public static void uncheck(Object locator) {
		try {
			WebElement element = waitForAndGetElement(locator);
			if (element.isSelected()) {
				click(locator);
			} else {
				info("Element " + locator + " is already unchecked.");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			uncheck(locator);
		} finally {
			loopCount = 0;
		}
	}
	public static void rightClickOnElement(Object locator) {
		pause(500);
		try {
			WebElement element = waitForAndGetElement(locator);
			actions.contextClick(element).perform();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			rightClickOnElement(locator);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			click(locator);
		} finally {
			loopCount = 0;
		}
	}

	//doubleClickOnElement
	public static void doubleClickOnElement(Object locator) {
		try {
			WebElement element = waitForAndGetElement(locator);
			actions.doubleClick(element).perform();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			doubleClickOnElement(locator);
		} finally {
			loopCount = 0;
		}
	}

	public static void waitForConfirmation(String confirmationText) {
		String message = getTextFromAlert();

		//log("confirmation: " + message);

		if (message.isEmpty()) {
			if (loopCount > 5) {
				Assert.fail("Message is empty");
			}
			pause(500);
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
			pause(100);
		}
		Alert alert = driver.switchTo().alert();
		alert.accept();
		pause(500);
	}

	//This function returns a absolute path from a relative path
	public static String getAbsoluteFilePath(String relativeFilePath){
		String curDir = System.getProperty("user.dir");
		String absolutePath = curDir + "/src/main/resources/" + relativeFilePath;
		return absolutePath;
	}
	public static void checkCycling(Exception e, int loopCountAllowed) {
    loopCount++;
    debug("Exception:" + e.getClass().getName());
		if (loopCount > loopCountAllowed) {
			Assert.fail("Cycled: " + e.getMessage());
		} 
    debug("Repeat... " + loopCount + "time(s)");
	}

	/*---- Auxiliary functions ----*/
	public static void captureScreen(String fileName){
		String path;
		try {
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//			relativeFilePath = "target/screenshot/" + fileName;
			String curDir = System.getProperty("user.dir");
			path = curDir + "/target/screenshoot/" + fileName;
			FileUtils.copyFile(scrFile, new File(path));
		} catch (IOException e) {
			path = "Failed to capture screenshot: " + e.getMessage();
		}catch(UnknownServerException e)
		{
			error("Failed to capture screenshot");
		}
	}

	//function to switch to parent windows
	public static void switchToParentWindow (){
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
			pause(1000);
		}
		catch (WebDriverException e)
		{
			e.printStackTrace();
		}
	}

	public static boolean isDisplay(Object locator) { 
		boolean bool = false;
		WebElement e = getElement(locator);
		try{
			if (e!=null)
				bool = e.isDisplayed();
		}catch(StaleElementReferenceException ex) 
		{
			checkCycling(ex, 10);
			pause(WAIT_INTERVAL);
			isDisplay(locator);
		}
		finally{
			loopCount=0;
		}
		return bool;
	}
	

	///////
	//
	/**
	 * Capture the screen of the current graphics device 
	 * @author vuna2
	 * @param fileName: input an image name (String) 
	 */
	public static void javaCaptureScreen(String fileName){
		String path;
		BufferedImage screenCapture;
		pause(2000);
		try {
			Robot robot = new Robot();
			Rectangle screenSize = getScreenSize();
			screenCapture = robot.createScreenCapture(screenSize);
			// Save as PNG
			String curDir = System.getProperty("user.dir");
			path = curDir + "/target/screenshoot/" + fileName;
			ImageIO.write(screenCapture, "png", new File(path));

		}catch (AWTException e) {
			error("Failed to capture screenshot");
		}catch(IOException e)
		{
			path = "Failed to capture screenshot: " + e.getMessage();
		}
	}
	
	/**
	 * 
	 * @return the size of the default screen
	 */
	public static Rectangle getScreenSize() {
		GraphicsEnvironment graphE = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphD = graphE.getDefaultScreenDevice();
		DisplayMode displayM = graphD.getDisplayMode();
		return new Rectangle(displayM.getWidth(), displayM.getHeight());
	}

	///////
	//
	/**
	 * Simulating keyboard presses 
	 * @author vuna2
	 * @param firstKey: send the first key (type: KeyEvent)
	 * @param secondKey: send the second key (type: KeyEvent) 
	 */
	public static void javaSimulateKeyPress(int firstKey, int secondKey){
		try {
			Robot robot = new Robot();
			// Simulate a key press
			robot.keyPress(firstKey);
			robot.keyPress(secondKey);
			pause(500);
			robot.keyRelease(secondKey);
			robot.keyRelease(firstKey);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	//////
	/** function: set driver to auto save file to TestData/TestOutput
	 * @author lientm
	 */
	public static void getDriverAutoSave(){
		String pathFile = System.getProperty("user.dir") + "/src/main/resources/TestData/TestOutput";

		FirefoxProfile fp = new FirefoxProfile();		
		fp.setPreference("browser.download.folderList", 2);
		info("Save file to " + pathFile);
		fp.setPreference("browser.download.dir", pathFile);
		fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-xpinstall;application/x-zip" +
				";application/x-zip-compressed;application/octet-stream" +
				";application/zip;application/pdf;application/msword;text/plain;" +
				"application/octet;text/calendar;text/x-vcalendar;text/Calendar;" +
				"text/x-vCalendar;image/jpeg;image/jpg;image/jp_;application/jpg;" +
				"application/x-jpg;image/pjpeg;image/pipeg;image/vnd.swiftview-jpeg;image/x-xbitmap;image/png");
		driver = new FirefoxDriver(fp);
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
	}
	
	/**function set driver to auto open new window when click link
	 * @author lientm
	 */
	public static void getDriverAutoOpenWindow(){
		FirefoxProfile fp = new FirefoxProfile();		
		fp.setPreference("browser.link.open_newwindow.restriction", 2);
		driver = new FirefoxDriver(fp);
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
	}
	
	public static void getDriverAutoSaveAndOpenWindow(){
		String pathFile = System.getProperty("user.dir") + "/src/main/resources/TestData/TestOutput";

		FirefoxProfile fp = new FirefoxProfile();	
		fp.setPreference("browser.link.open_newwindow.restriction", 2);
		fp.setPreference("browser.download.folderList", 2);
		info("Save file to " + pathFile);
		fp.setPreference("browser.download.dir", pathFile);
		fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-xpinstall;application/x-zip" +
				";application/x-zip-compressed;application/octet-stream" +
				";application/zip;application/pdf;application/msword;text/plain;" +
				"application/octet;text/calendar;text/x-vcalendar;text/Calendar;" +
				"text/x-vCalendar;image/jpeg;image/jpg;image/jp_;application/jpg;" +
				"application/x-jpg;image/pjpeg;image/pipeg;image/vnd.swiftview-jpeg;image/x-xbitmap;image/png");
		driver = new FirefoxDriver(fp);
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
		termsAndConditions();
	}
	
	/**function get current Date of system follow a fomat
	 * @author lientm
	 * @param fomat
	 * @return
	 */
	public static String getCurrentDate(String fomat){
		DateFormat dateFormat = new SimpleDateFormat(fomat);
		Date date = new Date();
		return (dateFormat.format(date));
	}
	
	/**function get a date from add date to current date
	 * @author lientm
	 * @param fomat
	 * @param day
	 * @return
	 */
	public static String addDateFromCurrentDate(String fomat, int day){
		String dt = getCurrentDate(fomat);  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat(fomat);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
			c.add(Calendar.DATE, day);
			dt = sdf.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	public static boolean checkError(){
		try{
		
		}catch(org.openqa.selenium.UnhandledAlertException e){
			String message = getTextFromAlert();
			error("There is an error: " + message);
			return false;
		}
		return true;
	}
}
