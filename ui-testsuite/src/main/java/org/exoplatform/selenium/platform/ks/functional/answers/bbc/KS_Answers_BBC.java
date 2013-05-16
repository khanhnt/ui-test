package org.exoplatform.selenium.platform.ks.functional.answers.bbc;

import static org.exoplatform.selenium.TestLogger.info; 
import static org.exoplatform.selenium.platform.ManageAccount.*;
import static org.exoplatform.selenium.platform.ManageApplications.showImportApplication;
import static org.exoplatform.selenium.platform.PageManagement.*;
import java.awt.event.KeyEvent;

import org.exoplatform.selenium.platform.ks.Answer;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test cases: KS/Answers/BBC
 * @author vuna2
 * Date: November 26, 2012
 */
public class KS_Answers_BBC extends Answer{
	public String admin = "john";
	public String pass = "gtn";

	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		actions = new Actions(driver);
		signIn(admin, pass);
	}

	@AfterMethod
	public void afterMethods(){
		info("-- Finished: test case --");
		//signOut();
		driver.quit();
		actions = null;
	}

	/**
	 * Case ID 001
	 * Check Bold Tag Effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose a question with Bold tag </li>
	 */
	@Test
	public void test01_CheckBoldTagEffect(){
		//Data for this case
		String questionName = "CheckBoldTagEffect"; 

		String questionContent = "[b]Content of case 01: Check the bold tag effect[/b]"; 

		boolean upload = false; 

		String filePaths = "";

		createNewQuestionAtAnswersApplication("", questionName, questionContent, upload, filePaths);

		info("-- The question is shown with Bold effect --");

		waitForTextPresent("Content of case 01: Check the bold tag effect");

		captureScreen("FNC_KS_ANS_BBC_Case01");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);	
	}

	/**
	 * Case ID 002
	 * Check Italic Tag Effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose post with Italic tag </li>
	 */
	@Test
	public void test02_CheckItalicTagEffect(){
		String questionName = "Check Italic Tag Effect"; 

		String questionContent = "[i]Content of case 02: Check Italic Tag Effect[/i]"; 

		boolean upload = false; 

		String filePaths = "";

		createNewQuestionAtAnswersApplication("", questionName, questionContent, upload, filePaths);

		waitForTextPresent("Content of case 02: Check Italic Tag Effect");

		captureScreen("FNC_KS_ANS_BBC_Case02");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);	
	} 

	/**
	 * Case ID 003
	 * Check Underline tag effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Underline tag </li>
	 * 
	 */
	@Test
	public void test03_CheckUnderlineTagEffect(){
		String questionName = "Check Underline tag effect"; 

		String questionContent = "[u]Content of case 03: Check Underline tag effect[/u]"; 

		boolean upload = false; 

		String filePaths = "";

		createNewQuestionAtAnswersApplication("", questionName, questionContent, upload, filePaths);

		waitForTextPresent("Content of case 03: Check Underline tag effect");

		captureScreen("FNC_KS_ANS_BBC_Case03");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Case ID 004
	 * Check Size tag effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Size tag </li>
	 * 
	 */
	@Test
	public void test04_CheckSizeTagEffect(){
		String questionName = "Check Size tag effect"; 

		String questionContent = "[size=5]Content of case 04: Check Size tag effect[/size]"; 

		boolean upload = false; 

		String filePaths = "";

		createNewQuestionAtAnswersApplication("", questionName, questionContent, upload, filePaths);

		waitForTextPresent("Content of case 04: Check Size tag effect");

		captureScreen("FNC_KS_ANS_BBC_Case04");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Case ID 005
	 * Check Color tag effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Color tag </li>
	 * 
	 */
	@Test
	public void test05_CheckColorTagEffect(){
		String questionName = "Check Color tag effect"; 

		String questionContent = "[color=red]Content of case 05: Check Color tag effect[/color]"; 

		boolean upload = false; 

		String filePaths = "";

		createNewQuestionAtAnswersApplication("", questionName, questionContent, upload, filePaths);

		waitForTextPresent("Content of case 05: Check Color tag effect");

		captureScreen("FNC_KS_ANS_BBC_Case05");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Case ID 006
	 * Check combined formatting tags
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose post with combined tags </li>
	 * 
	 */
	@Test
	public void test06_CheckCombinedFormattingTags(){
		String questionName = "Check combined formatting tags"; 

		String questionContent = "[size=5][color=red][b]Content of case 06: Check combined formatting tags[/b][/color][/size]"; 

		boolean upload = false; 

		String filePaths = "";

		createNewQuestionAtAnswersApplication("", questionName, questionContent, upload, filePaths);

		waitForTextPresent("Content of case 06: Check combined formatting tags");

		captureScreen("FNC_KS_ANS_BBC_Case06");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Case ID 007
	 * Check Quote tag effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Quote tag </li>
	 * 
	 */
	@Test
	public void test07_CheckQuoteTagEffect(){
		String questionName = "Check Quote tag effect"; 

		String questionContent = "[quote]Content of case 07: Check Quote tag effect[/quote]"; 

		boolean upload = false; 

		String filePaths = "";

		createNewQuestionAtAnswersApplication("", questionName, questionContent, upload, filePaths);

		waitForTextPresent("Content of case 07: Check Quote tag effect");

		captureScreen("FNC_KS_ANS_BBC_Case07");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Case ID 008
	 * Check Code tag effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Code tag </li>
	 */
	@Test
	public void test08_CheckCodeTagEffect(){
		String questionName = "Check Code tag effect"; 

		String questionContent = "[code]Content of case 08: Check Code tag effect[/code]"; 

		createNewQuestionAtAnswersApplication("", questionName, questionContent, false, "");

		waitForTextPresent("Content of case 08: Check Code tag effect");

		captureScreen("FNC_KS_ANS_BBC_Case08");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Case ID 009
	 * Check Unordered list effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Unordered list tag </li>
	 */
	@Test
	public void test09_CheckUnOrderedListEffect(){
		String questionName = "Check Unordered list effect"; 

		String questionContent = "[list] [*]Check Unordered list effect 01 [*]Check Unordered list effect 02 [*]Check Unordered list effect 03 [/list]"; 

		createNewQuestionAtAnswersApplication("", questionName, questionContent, false, "");

		waitForTextPresent("Check Unordered list effect 01");

		captureScreen("FNC_KS_ANS_BBC_Case09");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Case ID 10
	 * Check Ordered list effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Ordered list tag </li>
	 */
	@Test
	public void test10_CheckOrderedListEffect(){
		String questionName = "Check Ordered list effect"; 

		String questionContent = "[list=1] [*]Check Ordered list effect 01 [*]Check Ordered list effect 02 [*]Check Ordered list effect 03 [/list]"; 

		createNewQuestionAtAnswersApplication("", questionName, questionContent, false, "");

		waitForTextPresent("Check Ordered list effect 01");

		captureScreen("FNC_KS_ANS_BBC_Case10");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Case ID 11
	 * Check Link effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Link tag </li>
	 */
	@Test
	public void test11_CheckLinkEffect(){
		String questionName = "Check Link effect"; 

		String questionContent = "[url=http://www.google.com]Link effect[/url]"; 

		createNewQuestionAtAnswersApplication("", questionName, questionContent, false, "");

		waitForTextPresent("Link effect");

		captureScreen("FNC_KS_ANS_BBC_Case11_01");
		String previousWindowHandle = driver.getWindowHandle();
		
		click(By.linkText("Link effect"));

		switchToNewWindow();
		waitForElementPresent(By.xpath("//*[@title='Google']"));
		captureScreen("FNC_KS_ANS_BBC_Case11_02");

		//backToPreviousBrowser(cookies, previousWindowHandle); 
		driver.switchTo().window(previousWindowHandle);

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Case ID 012
	 * Check Email effect
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Email tag </li>
	 */
	@Test
	public void test12_CheckEmailEffect(){
		String questionName = "Check Email effect"; 

		String questionContent = "[email]test@platform.com[/email]"; 

		createNewQuestionAtAnswersApplication("", questionName, questionContent, false, "");

		waitForTextPresent("test@platform.com");

		captureScreen("FNC_KS_ANS_BBC_Case12_01");

		click(By.linkText("test@platform.com"));

		javaCaptureScreen("FNC_KS_ANS_BBC_Case12_02");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);		

		signOut();

		info("-- Close the email application window --");

		javaSimulateKeyPress(KeyEvent.VK_CONTROL, KeyEvent.VK_W);
	}

	/**
	 * Case ID 13
	 * Check Adding image to a post
	 * <li> Step 1: Login and Open Answers application </li>
	 * <li> Step 2: Open Submit question form </li>
	 * <li> Step 3: Compose question with Image tag </li>
	 */
	@Test
	public void test13_CheckAddingImageToAPost(){
		String questionName = "Check Adding image to a post"; 

		String questionContent = "[img]http://www.google.com/intl/en_ALL/images/logo.gif[/img]"; 

		createNewQuestionAtAnswersApplication("", questionName, questionContent, false, "");

		waitForTextPresent("Check Adding image to a post");

		captureScreen("FNC_KS_ANS_BBC_Case13");

		info("-- Reset a data --");

		resetDataByDeletingQuestion(questionName);
	}

	/**
	 * Delete data after testing
	 */
	//@Test -> lientm: do not need delete answer page, run order may be not follow case order
	public void test20_DeleteData(){
		info("-- Deleting a data --");
		showImportApplication(false);
		deletePageAtManagePageAndPortalNavigation("Answer", true, "intranet", false, "");
	}

}
