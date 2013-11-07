package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.publication;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author: HaKT
 * @date: 10/09/2013
 */
public class ECMS_SE_Publication extends PlatformBase{

	ManageAccount magAc;
	NavigationToolbar navToolBar;
	ECMainFunction ecMain;
	SitesExplorer sitesEx;
	ActionBar actBar;
	ContentTemplate conTemp;
    ContextMenu conMenu;
    EcmsBase ecms;
    Button button;

    public final String MESSAGE_INVALID_DATE_TIME = "The date format is invalid. Please check again.";
    public final String ELEMENT_INVALID_FROM_INPUT = "10/10/2013";

	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		navToolBar = new NavigationToolbar(driver);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", "gtn"); 
		ecMain = new ECMainFunction(driver);
		sitesEx = new SitesExplorer(driver);
		actBar = new ActionBar(driver);
		conTemp =  new ContentTemplate(driver);
        ecms = new EcmsBase(driver);
        button = new Button(driver);
        conMenu = new ContextMenu(driver);
	}
	
	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseId: 66269 -> Publish content when only set time in From field
	 * Create new content on site explorer
	 * Click Publication on action bar
	 * Choose staged
	 * Move to scheduled tab
	 * Choose Only From field
	 * Save
	 */
	@Test
	public void test01_PublishContentWhenOnlySetTimeInFromField(){
		
		String doc_Name = "doc_test_66269";
		String doc_Content = "Content of File: Publish content when only set time in From field";
        By file_locator = By.linkText(doc_Name);

        // Go to Sites Explorer
		navToolBar.goToSiteExplorer();

        // Add a content
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

        // Show Publication Form and change status to Staged
        click(actBar.ELEMENT_PUBLICATION_ICON);
		waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
        click(actBar.ELEMENT_STAGED_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_STAGED_STATUS);

        // Set time in From Field and check status
        String oneMinuteAfterCurrentTime = addMinutesToCurrentDateTime(1);
        click(actBar.ELEMENT_SCHEDULE_TAB);
        actBar.managePublication("Staged",oneMinuteAfterCurrentTime,"");
        // Wait for one minute plus time so that changes take effect
        Utils.pause(180000);
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);

        // Remove test data
        conMenu.deleteDocument(file_locator);
	}

    /**CaseId: 66270 -> Publish content when only set time in To field
     * @author hzekri
     */
    @Test
    public void test02_PublishContentWhenOnlySetTimeInToField(){

        String doc_Name = "doc_test_66270";
        String doc_Content = "Content of File: Publish content when only set time in To field";
        By file_locator = By.linkText(doc_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add a content
        actBar.goToAddNewContent();
        conTemp.createNewFile(doc_Name, doc_Content, null);

        // Show Publication Form and change status to Staged
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
        click(actBar.ELEMENT_STAGED_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_STAGED_STATUS);

        // Set time in To Field and check status
        String oneMinuteAfterCurrentTime = addMinutesToCurrentDateTime(1);
        click(actBar.ELEMENT_SCHEDULE_TAB);
        actBar.managePublication("Staged","",oneMinuteAfterCurrentTime);
        Utils.pause(180000);
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForElementNotPresent(actBar.ELEMENT_CURRENT_STATUS);

        // Remove test data
        conMenu.deleteDocument(file_locator);
    }

    /**CaseId: 66271 -> Publish content when put invalid date format
     * @author hzekri
     */
    @Test
    public void test03_PublishContentWhenPutInvalidDateFormat(){

        String doc_Name = "doc_test_66271";
        String doc_Content = "Content of File: Publish content when put invalid date format";
        By file_locator = By.linkText(doc_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add a content
        actBar.goToAddNewContent();
        conTemp.createNewFile(doc_Name, doc_Content, null);

        // Show Publication Form and change status to Staged
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
        click(actBar.ELEMENT_STAGED_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_STAGED_STATUS);

        // Check show message when put invalid date format
        click(actBar.ELEMENT_SCHEDULE_TAB);
        type(actBar.ELEMENT_PUB_FROM_INPUT, ELEMENT_INVALID_FROM_INPUT, true);
        button.save();
        waitForMessage(MESSAGE_INVALID_DATE_TIME);
        button.ok();

        // Remove test data
        conMenu.deleteDocument(file_locator);
    }

    /**CaseId: 66272 -> Publish content with setting publication time in both Form and To field
     * @author hzekri
     */
    @Test
    public void test04_PublishContentWithTimeInBothFromAndToFields(){

        String doc_Name = "doc_test_66272";
        String doc_Content = "Content of File: Publish content with setting publication time in both Form and To fields";
        By file_locator = By.linkText(doc_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add a content
        actBar.goToAddNewContent();
        conTemp.createNewFile(doc_Name, doc_Content, null);

        // Show Publication Form and change status to Staged
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
        click(actBar.ELEMENT_STAGED_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_STAGED_STATUS);

        // Set time in From and To Fields and check status
        String oneMinuteAfterCurrentTime = addMinutesToCurrentDateTime(1);
        String fourMinuteAfterCurrentTime = addMinutesToCurrentDateTime(4);
        click(actBar.ELEMENT_SCHEDULE_TAB);
        actBar.managePublication("Staged",oneMinuteAfterCurrentTime,fourMinuteAfterCurrentTime);
        Utils.pause(180000);
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);
        Utils.pause(180000);
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForElementNotPresent(actBar.ELEMENT_CURRENT_STATUS);

        // Remove test data
        conMenu.deleteDocument(file_locator);
    }

    /**CaseId: 66273, 67233 -> Add Stage status from Draft status
     * @author hzekri
     */
    @Test
    public void test05_AddStageStatusFromDraftStatus(){

        String doc_Name = "doc_test_66273";
        String doc_Content = "Content of File: Add Stage status from Draft status";
        By file_locator = By.linkText(doc_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add a content
        actBar.goToAddNewContent();
        conTemp.createNewFile(doc_Name, doc_Content, null);

        // Show Publication Form and change status to Staged
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
        click(actBar.ELEMENT_STAGED_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_STAGED_STATUS);

        // Remove test data
        conMenu.deleteDocument(file_locator);

    }

    /**CaseId: 67105 -> Check showing Manage publication form
     * @author hzekri
     */
    @Test
    public void test06_CheckShowingManagePublicationForm(){

        String doc_Name = "doc_test_67105";
        String doc_Content = "Content of File: Check showing Manage publication form";
        By file_locator = By.linkText(doc_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add a content
        actBar.goToAddNewContent();
        conTemp.createNewFile(doc_Name, doc_Content, null);

        // Show Publication Form and change status
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
        click(actBar.ELEMENT_PUBLIC_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);
        waitForAndGetElement(actBar.ELEMENT_HISTORY_TAB);
        waitForAndGetElement(actBar.ELEMENT_REVISION_TAB);

        // Remove test data
        conMenu.deleteDocument(file_locator);
    }

    /**CaseId: 67190 -> Add Pending revision from Draft revision
     * @author hzekri
     */
    @Test
    public void test07_AddPendingRevisionFromDraftRevision(){

        String doc_Name = "doc_test_67190";
        String doc_Content = "Content of File: Add Pending revision from Draft revision";
        By file_locator = By.linkText(doc_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add a content
        actBar.goToAddNewContent();
        conTemp.createNewFile(doc_Name, doc_Content, null);

        // Show Publication Form and change status
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
        click(actBar.ELEMENT_PENDING_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_PENDING_STATUS);

        // Remove test data
        conMenu.deleteDocument(file_locator);

    }

    /**CaseId: 67191 -> Add Published status from Draft status
     * @author hzekri
     */
    @Test
    public void test08_AddPublishedStatusFromDraftStatus(){

        String doc_Name = "doc_test_67191";
        String doc_Content = "Content of File: Add Published status from Draft status";
        By file_locator = By.linkText(doc_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add a content
        actBar.goToAddNewContent();
        conTemp.createNewFile(doc_Name, doc_Content, null);

        // Show Publication Form and change status
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
        click(actBar.ELEMENT_PUBLIC_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);

        // Remove test data
        conMenu.deleteDocument(file_locator);
    }

    /**CaseId: 67519 -> Add Approve status from Draft status
     * @author hzekri
     */
    @Test
    public void test09_AddApproveStatusFromDraftStatus(){

        String doc_Name = "doc_test_67519";
        String doc_Content = "Content of File: Add Approve status from Draft status";
        By file_locator = By.linkText(doc_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add a content
        actBar.goToAddNewContent();
        conTemp.createNewFile(doc_Name, doc_Content, null);

        // Show Publication Form and change status
        click(actBar.ELEMENT_PUBLICATION_ICON);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
        click(actBar.ELEMENT_APPROVED_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_APPROVED_STATUS);

        // Remove test data
        conMenu.deleteDocument(file_locator);

    }

    /**CaseId: 94785 -> Publish child node which is nt:file when parent has been published
     * @author hzekri
     */
    @Test
    public void test10_PublishChildNodeNTFileWhenParentPublished(){

        String parent_Name = "parent_test_94785";
        String parent_Content = "Content of File: Publish child node which is nt:file when parent has been published";
        String child_Name = "child_test_94785";
        String child_Content = "Content of File: Publish child node which is nt:file when parent has been published";
        By file_locator = By.linkText(parent_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add parent content
        actBar.goToAddNewContent();
        conTemp.createNewWebContent(parent_Name, parent_Content, "", "", "", "");

        // Add child content
        actBar.goToAddNewContent();
        conTemp.createNewFile(child_Name, child_Content, null);

        // Publish parent content
        ecms.goToNode(file_locator);
        click(actBar.ELEMENT_PUBLICATION_ICON);
        click(actBar.ELEMENT_PUBLIC_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);

        // Check Publish icon for child content
        ecms.goToNode(parent_Name + "/" + child_Name);
        waitForElementNotPresent(actBar.ELEMENT_PUBLISH_ICON);

        // Remove test data
        conMenu.deleteDocument(file_locator);

    }

    /**CaseId: 94786 -> Publish child node which is not nt:file when parent has been published
     * @author hzekri
     */
    @Test
    public void test11_PublishChildNodeNotNTFileWhenParentPublished(){

        String parent_Name = "parent_test_94786";
        String parent_Content = "Content of File: Publish child node which is not nt:file when parent has been published";
        String child_Name = "child_test_94786";
        String child_Sum = "Content of File: Publish child node which is not nt:file when parent has been published";
        By file_locator = By.linkText(parent_Name);

        // Go to Sites Explorer
        navToolBar.goToSiteExplorer();

        // Add parent content
        actBar.goToAddNewContent();
        conTemp.createNewWebContent(parent_Name, parent_Content, "", "", "", "");

        // Add child content
        actBar.goToAddNewContent();
        conTemp.createNewAnnouncement(child_Name, child_Sum);

        // Publish parent content
        ecms.goToNode(file_locator);
        click(actBar.ELEMENT_PUBLICATION_ICON);
        click(actBar.ELEMENT_PUBLIC_STATUS);
        waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);

        // Check Publish icon for child content
        ecms.goToNode(parent_Name+"/"+child_Name);
        mouseOverAndClick(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
        mouseOverAndClick(actBar.ELEMENT_PUBLISH_ICON);

        // Remove test data
        conMenu.deleteDocument(file_locator);

    }

    //Add  minutes to current date time
    public String addMinutesToCurrentDateTime(int i){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, i);
        return (dateFormat.format(cal.getTime()));
    }
}
