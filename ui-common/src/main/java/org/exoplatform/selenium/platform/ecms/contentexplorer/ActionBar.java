package org.exoplatform.selenium.platform.ecms.contentexplorer;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ManageView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * 
 * @author vuna2
 *
 */
public class ActionBar extends EcmsBase{

	public ActionBar(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	Button button = new Button(driver);
	Dialog dialog = new Dialog(driver);
	NavigationToolbar navToolBar = new NavigationToolbar(driver);
	ManageAccount magAcc = new ManageAccount(driver);
	ManageView magView = new ManageView(driver);
	ContextMenu cMenu = new ContextMenu(driver);

	//System TAB
	//Export Form
	public By ELEMENT_DOC_VIEW = By.id("format_docview");
	public By ELEMENT_ZIP = By.id("zip");
	public By ELEMENT_EXPORT_VERSION = By.linkText("Export Version History");
	public By ELEMENT_EXPORT = By.xpath("//a[@class='ActionButton LightBlueStyle' and text()='Export']");

	//Import Form
	public By ELEMENT_UPLOAD_FILE_FRAME = By.xpath("//label[contains(text(),'Upload File:')]/following::div/iframe[contains(@id,'uploadFrame')]");
	public By ELEMENT_UPLOAD_VERSION_FRAME = By.xpath("//label[contains(text(),'Version History:')]/following::div/iframe[contains(@id,'uploadFrame')]");
	public By ELEMENT_BEHAVIOR = By.name("behavior");
	public By ELEMENT_IMPORT = By.xpath("//a[@class='ActionButton LightBlueStyle' and text()='Import']");

	//Publication > Add Category Form
	public By ELEMENT_CATEGORIES_LINK = By.xpath("//*[@class='actionIcon']//*[@class='uiIconEcmsManageCategories']"); 
	public By ELEMENT_CATEGORIES_MORE_LINK = By.xpath("//*[text()='More']/..//a[text()='Categories']");
	public By ELEMENT_SELECT_CATEGORY_TAB = By.xpath("//*[text()='Select Category']");
	public By ELEMENT_CATEGORY_TREE_BOX = By.name("taxonomyTree");
	public By ELEMENT_ADD_ROOT_BUTTON = By.xpath("//label[text()='Root Tree']/following::img[@title='Add Root Node']");

	//Version Info form
	public By ELEMENT_ICON_VERSION_ADD=By.xpath("//*[@title='Add Label']");
	public By ELEMENT_TEXTBOX_VERSION=By.id("label");

	//publication form
	public final By ELEMENT_PUBLIC_STATUS = By.xpath("//a[contains(text(), 'Published')]");
	public final By ELEMENT_CURRENT_STATUS = By.xpath("//a[@class='CurrentStatus']");
	public final By ELEMENT_CURRENT_PUBLIC_STATUS = By.xpath("//a[@class='CurrentStatus' and contains(text(), 'Published')]");

	//Go to new content
	public void goToAddNewContent(){
		waitForElementPresent(ELEMENT_NEW_CONTENT_LINK, DEFAULT_TIMEOUT, 0, 2);
		for (int repeat = 1;; repeat++)	{	
			if (repeat >= ACTION_REPEAT) {
				Assert.fail("Cannot perform the action after " + ACTION_REPEAT + "tries");
			}
			mouseOverAndClick(ELEMENT_NEW_CONTENT_LINK);
			//click(ELEMENT_NEW_CONTENT_LINK, 2);

			if (waitForElementNotPresent(ELEMENT_NEW_CONTENT_LINK, 30000,0, 2) == null) return;
			Utils.pause(WAIT_INTERVAL);
			info("retry...[" + repeat + "]");
		}
	}

	//Collaboration Tab
	public void goToCollaboration(){
		for (int repeat = 0;; repeat++)	{	
			if (repeat >= ACTION_REPEAT) {
				Assert.fail("Cannot perform the action after " + ACTION_REPEAT + "tries");
			}
			mouseOver(ELEMENT_COLLABORATION_TAB, true);
			click(ELEMENT_COLLABORATION_TAB);

			if (waitForElementPresent(ELEMENT_TAG, 30000, 0) != null) return;
			Utils.pause(WAIT_INTERVAL);
			info("retry...[" + repeat + "]");
		}
	}

	//Edit a document
	public void goToEditDocument(String title)
	{	
		goToNode(title);
		for(int loop = 1;;loop ++)
		{
			if (loop >= ACTION_REPEAT) {
				Assert.fail("Cannot go to the edit page: " + title );
			}
			click(ELEMENT_EDIT_LINK);
			if (waitForAndGetElement(button.ELEMENT_SAVE_CLOSE_BUTTON, 50000).isDisplayed()) 
				break;
		}
	}

	// Add a category in DMS Administration - Simple View
	public void addCategoryInSimpleView(String name)
	{
		click(ELEMENT_BUTTON_ADD_CATEGORY);
		waitForElementPresent(ELEMENT_ADD_CATEGORY_FORM);
		type(ELEMENT_INPUT_CATEGORY_NAME, name, false);
		click(button.ELEMENT_SAVE_BUTTON);
		waitForElementPresent(By.xpath("//a[@title='"+ name + " ']"));
	}

	//Export node
	public void exportNode(boolean systemView, boolean zip, boolean exportVersionHistory) {
		waitForElementPresent(ELEMENT_SYSTEM_TAB);
		click(ELEMENT_SYSTEM_TAB);
		Utils.pause(500);
		waitForElementPresent(ELEMENT_EXPORT_LINK);
		click(ELEMENT_EXPORT_LINK);
		if (!systemView)
		{
			click(ELEMENT_DOC_VIEW);
		}

		if (zip)
		{
			check(ELEMENT_ZIP);
		}

		if (exportVersionHistory)
		{
			click(ELEMENT_EXPORT_VERSION);
		}
		else
		{
			click(ELEMENT_EXPORT);
		}
		Utils.pause(10000);
		waitForElementNotPresent(ELEMENT_EXPORT);
	}

	//Import node
	public void importNode(String linkFile, String linkVersion, String behavior, boolean version) {
		//Click system tab
		waitForElementPresent(ELEMENT_SYSTEM_TAB);
		click(ELEMENT_SYSTEM_TAB);
		//Click import button
		waitForElementPresent(ELEMENT_IMPORT_LINK);
		click(ELEMENT_IMPORT_LINK);
		//Switch to frame upload file
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_UPLOAD_FILE_FRAME));
		waitForElementPresent(ELEMENT_UPLOAD_IMG_ID);
		type(ELEMENT_UPLOAD_IMG_ID, Utils.getAbsoluteFilePath(linkFile), false);
		Utils.pause(500);
		switchToParentWindow();

		select(ELEMENT_BEHAVIOR, behavior);
		if (version)
		{		
			driver.switchTo().frame(waitForAndGetElement(ELEMENT_UPLOAD_VERSION_FRAME));
			waitForElementPresent(ELEMENT_UPLOAD_IMG_ID);
			type(ELEMENT_UPLOAD_IMG_ID, Utils.getAbsoluteFilePath(linkVersion), false);
			Utils.pause(500);
			switchToParentWindow();
			click(ELEMENT_IMPORT);
			Utils.pause(500);
			waitForMessage("Import successfully.");
			click(button.ELEMENT_OK_BUTTON);
		}
		else 
		{
			click(ELEMENT_IMPORT);
			Utils.pause(500);
			waitForMessage("Import successfully.");
			click(button.ELEMENT_OK_BUTTON);
		}
	}

	//Add category for node
	public void addCategoryForNode(String categoryTree, boolean rootTree, String categoryPath, String categoryName) {
		By ELEMENT_ADD_CATEGORY_SPECIFIC = By.xpath("//div[contains(text(),'"+categoryName+"')]/following::a[@title='select']");
		//			By ELEMENT_CATEGORY_LIST = By.xpath("//th[text()='Category']");

		waitForElementPresent(ELEMENT_CATEGORIES_LINK);
		click(ELEMENT_CATEGORIES_LINK);
		waitForElementPresent(ELEMENT_SELECT_CATEGORY_TAB);
		click(ELEMENT_SELECT_CATEGORY_TAB);
		Utils.pause(500);
		select(ELEMENT_CATEGORY_TREE_BOX, categoryTree);
		if (rootTree) {
			click(ELEMENT_ADD_ROOT_BUTTON);
			waitForTextPresent(categoryTree);
			checkUnexpectedError();			
		}
		else {
			String paths [] = categoryPath.split("/");
			for (String path : paths)
				click(By.xpath("//div[@title='"+path+"']"));
			waitForElementPresent(ELEMENT_ADD_CATEGORY_SPECIFIC);
			click(ELEMENT_ADD_CATEGORY_SPECIFIC);
			Utils.pause(500);
			checkUnexpectedError();
			waitForTextPresent(categoryPath);

		}

		waitForElementPresent(button.ELEMENT_CLOSE_BUTTON);
		click(button.ELEMENT_CLOSE_BUTTON);
		info ("------Category "+categoryName+" is added succesfully");
	}

	/*
	 * Add version for a node
	 * + locator: locator of node
		   + version: version name
	 */
	public void addVersionForNode(By locator, String vesion){
		info("-- Add a version for a document... --");
		goToNode(locator);
		//click(ELEMENT_PUBLICATION_TAB);
		clearCache();
		if (isElementPresent(ELEMENT_VERSIONS_LINK)){
			info("-- Versions tab is already displayed --");
		}else if (isElementPresent(ELEMENT_MORE_LINK_WITHOUT_BLOCK)){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (isElementPresent(ELEMENT_VERSIONS_LINK)){
				info("-- Versions tab is already displayed --");
			}
		}
		click(ELEMENT_VERSIONS_LINK);
		click(ELEMENT_ICON_VERSION_ADD);
		type(ELEMENT_TEXTBOX_VERSION,vesion,true);
		click(button.ELEMENT_SAVE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		click(button.ELEMENT_CLOSE_BUTTON);
	}

	//Choose a drive
	public void chooseDrive(By locator)
	{
		click(ELEMENT_SHOW_DRIVES);
		Utils.pause(1000);
		//button = new Button(driver);
		//button.refresh();
		click(locator);
		Utils.pause(1000);
	}

	//function public a document
	public void publishDocument(){
		button = new Button(driver);
		info("Public this document");
		if ((waitForAndGetElement(ELEMENT_PUBLICATION,30000,0) == null ))
			click(ELEMENT_MORE_LINK);
		click(ELEMENT_PUBLICATION);
		WebElement current = waitForAndGetElement(ELEMENT_CURRENT_STATUS);
		if (current.getText().contains("Published") == false){
			click(ELEMENT_PUBLIC_STATUS);
		}
		waitForElementPresent(ELEMENT_CURRENT_PUBLIC_STATUS);
		button.save();
		info("Public document is successful");
	}

	// Node Permission
	public void goToNodePermissionManagement(){
		if (isTextPresent("Permissions")){
			info("-- Permission tab is already displayed --");
			click(ELEMENT_PERMISSION_LINK);
		}else{
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			click(ELEMENT_PERMISSION_LINK);
		}
		Utils.pause(1000);
	}

	//Add an action to Action Bar: View Permissions
	public void addViewPermissionToActionBar(){
		if (isElementPresent(ELEMENT_PERMISSION_LINK)){
			info("-- Permission tab is already displayed --");
		}else if (isElementPresent(ELEMENT_MORE_LINK)){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (isElementPresent(ELEMENT_PERMISSION_LINK)){
				info("-- Permission tab is already displayed --");
			}
		}else{
			magView.setup2ShowViewAction("viewPermissions");
			magAcc.signOut();
			magAcc.signIn("john", "gtn");
			navToolBar.goToSiteExplorer();
			if (isElementPresent(ELEMENT_PERMISSION_LINK)){
				info("-- Permission tab is already displayed --");
			}else if (isElementPresent(ELEMENT_MORE_LINK)){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
				if (isElementPresent(ELEMENT_PERMISSION_LINK)){
					info("-- Permission tab is already displayed --");
				}
			}else{
				info("-- Permission tab is not displayed... --");
			}
		}
		Utils.pause(1000);
	}


	//Go To Add Symlink 
	public void goToAddSymlinkTab(){
		if (isTextPresent("Add Symlink")){
			info("-- Add Symlink tab is already displayed --");
			click(ELEMENT_ADD_SYMLINK);
		}else{
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			click(ELEMENT_ADD_SYMLINK);
		}
		Utils.pause(1000);
	}

	//Go to the target node: ACME / when add symlink
	public void goToAcmeNodeInAddSymlink(){
		goToAddSymlinkTab();
		waitForElementPresent(ELEMENT_ADD_SYMLINK_POPUP);
		click(ELEMENT_ADD_ITEM);	
		//Go to sites > acme
		click(By.linkText("sites"));
		click(By.xpath("//*[@id='UIPopupSymLink']//*[@title='acme']"));
		Utils.pause(1000);
	}

	//Add symlink for node with target node is documents
	public void addSymlink(String workspace, String path, String name){
		goToAddSymlinkTab();
		waitForElementPresent(ELEMENT_ADD_SYMLINK_POPUP);
		click(ELEMENT_ADD_ITEM);
		//usePaginator(path, "Can not choose target node");
		select(ELEMENT_SYMLINK_WORKSPACE, workspace);
		selectHomePathForCategoryTree(path);
		/*if (path!=null){
			click(path);
		}*/
		String[] temp;
		String delimiter = "/";
		temp = name.split(delimiter);
		if (name != temp[temp.length - 1]+".lnk" ){
			type(ELEMENT_SYMLINK_NAME, name, true);
		}
		assert getValue(ELEMENT_SYMLINK_NAME).equalsIgnoreCase(name);
		click(button.ELEMENT_SAVE_BUTTON); 
	}

	//Add an action to Action Bar: View Symlink
	public void addViewSymlinkToActionBar(){
		if (isElementPresent(ELEMENT_ADD_SYMLINK)){
			info("-- Add Symlink tab is already displayed --");
		}else if (isElementPresent(ELEMENT_MORE_LINK)){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (isElementPresent(ELEMENT_ADD_SYMLINK)){
				info("-- Add Symlink tab is already displayed --");
			}
		}else{
			magView.setup2ShowViewAction("addSymLink");
			magAcc.signOut();
			magAcc.signIn("john", "gtn");
			navToolBar.goToSiteExplorer();
			if (isElementPresent(ELEMENT_ADD_SYMLINK)){
				info("-- Add Symlink tab is already displayed --");
			}else if (isElementPresent(ELEMENT_MORE_LINK)){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
				if (isElementPresent(ELEMENT_ADD_SYMLINK)){
					info("-- Add Symlink tab is already displayed --");
				}
			}else{
				info("-- Add Symlink tab is not displayed... --");
			}
		}
		Utils.pause(1000);
	}

	//A Function to copy/cut/paste/delete an Element (Document/Folder) in Sites Explorer
	//Check the box on the right side of Element
	//Select Action "Delete" on Action Bar
	public void actionsOnElement(String elementName, ContextMenu.actionType action){
		info("-- Action: "+ action + " on the element: " + elementName);
		waitForTextPresent(elementName);
		if (isElementPresent(ELEMENT_UI_CHECKBOX.replace("${element}", elementName))){
			click(ELEMENT_UI_CHECKBOX.replace("${element}", elementName), 2);
		}else if (isElementPresent(By.xpath("//*[contains(text(), '"+ elementName +"')]/../..//*[@name = 'checkbox']"))){
			click(By.xpath("//*[contains(text(), '"+ elementName +"')]/../..//*[@name = 'checkbox']"), 2);
		}
		//click(ELEMENT_UI_CHECKBOX.replace("${element}", elementName), 2);
		switch (action) {
		case COPY:

			break;
		case CUT:

			break;
		case DELETE:
			click(cMenu.ELEMENT_MENU_DELETE);
			waitForTextPresent("Delete");
			dialog.deleteInDialog();
			waitForElementNotPresent(ELEMENT_UI_CHECKBOX.replace("${element}", elementName));
			break;
		case PASTE:

			break;

		default:
			break;
		}
		Utils.pause(1000);
	}	
}