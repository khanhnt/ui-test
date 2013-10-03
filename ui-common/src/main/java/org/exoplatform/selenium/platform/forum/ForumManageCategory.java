package org.exoplatform.selenium.platform.forum;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.List;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.PlatformPermission;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Migrate from plf3.5
 * @author lientm
 * @date 19 Aug 2013
 */
public class ForumManageCategory extends ForumBase {

	ForumPermission frumPer;

	public ForumManageCategory(WebDriver dr){
		driver = dr;
		frumPer = new ForumPermission(driver);
		but = new Button(driver);
		alert = new ManageAlert(driver);
		per = new PlatformPermission(driver);
	}

	//------------category home screen----------------------------------------------------------------------
	public final String ELEMENT_CATEGORY = "//*[@class='nameForum']//strong[text()='${categoryName}']";	
	public final By ELEMENT_HOME_CATEGORY = By.xpath("//div[@class='Selected' and text()='Home']");

	//------------Manage category menu---------------------------------------------------------------------
	public final By ELEMENT_MANAGE_CATEGORY = By.xpath("//*[@class='uiIconForumManageCategory uiIconForumLightGray']");
	public final By ELEMENT_EDIT_CATEGORY = By.xpath("//*[contains(@href, 'EditCategory')]");
	public final By ELEMENT_DELETE_CATEGORY = By.xpath("//a[@id='UICategoryConfirm0' and contains(text(),'Delete')]");
	public final By ELEMENT_EXPORT_FORUM_IN_CATEGORY = By.xpath("//*[contains(@href, 'ExportCategory')]");
	public final By ELEMENT_IMPORT_FORUM_IN_CATEGORY = By.xpath("//*[contains(@href, 'ImportForum')]");

	//------------add category form------------------------------------------------------------------------
	public final By ELEMENT_POPUP_ADD_CATEGORY = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Category']");
	public final By ELEMENT_CATEGORY_TAB = By.xpath("//div[@class='MiddleTab' and text()='Category']");
	public final By ELEMENT_CATEGORY_TITLE = By.id("CategoryTitle");
	public final By ELEMENT_CATEGORY_ORDER = By.id("CategoryOrder");
	public final By ELEMENT_RESTRICTED_AUDIENCE = By.id("UserPrivate");
	public final String ELEMENT_RESTRICTED = "UserPrivate";
	public final By ELEMENT_DESCRIPTION = By.id("Description");
	public final By ELEMENT_RESTRICTED_SELECT_USER = By.xpath("//*[@id='DetailTab']//i[@class='uiIconUser uiIconLightGray']");
	public final By ELEMENT_RESTRICTED_SELECT_GROUP = By.xpath("//*[@id='DetailTab']//i[@class='uiIconGroup uiIconLightGray']");
	public final By ELEMENT_RESTRICTED_SELECT_ROLE = By.xpath("//*[@id='DetailTab']//i[@class='uiIconMembership uiIconLightGray']");


	//---------------Import category form--------------------------------------
	public final By ELEMENT_IMPORT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Import Category']");
	public final By ELEMENT_IMPORT_FILE = By.name("file");
	public final String MSG_IMPORT_CATEGORY = "Import successful.";

	//-------------------Export category form-------------------------------------
	public final By ELEMENT_EXPORT_CATEGORY_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Export Categories']");
	public final String ELEMENT_EXPORT_CATEGORY_CHECKBOX = "//*[contains(text(), '${catName}')]/..//input[@type='checkbox']";
	public final String ELEMENT_EXPORT_CATEGORY_CHECKBOX_LIST = "//*[contains(@id, 'forumCategory')]";
	public final String ELEMENT_EXPORT_CATEGORY_NO = "//*[@id='UIExportForm']//tbody/tr[${No}]//input";
	public final By ELEMENT_EXPORT_CATEGORY = By.xpath("//*[@id='Administrations']//*[@class='uiIconExport']");	
	public final By ELEMENT_EXPORT_CATEGORY_FILE_NAME = By.id("FileName");
	public final By ELEMENT_EXPORT_CATEGORY_ALL = By.xpath("//*[@value='ExportAll']");
	public final By ELEMENT_EXPORT_CATEGORY_ONLY = By.xpath("//*[@value='ExportCategories']");

	//-------------------Export forums in category form----------------------------
	public final By ELEMENT_EXPORT_FORUMS_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Export Forums']");
	public final String ELEMENT_EXPORT_FORUMS_CHECKBOX_LIST = "//*[@id='UIExportForm']//*[contains(@id,'forumcc')]";
	public final String ELEMENT_EXPORT_FORUMS_NO = "//*[@id='UIExportForm']//tbody/tr[${No}]//input";
	public final String ELEMENT_EXPORT_FORUMS_CHECKBOX = "//*[contains(text(), '${forum}')]/..//input[@type='checkbox']";

	public By ELEMENT_OK_DELETE_CATEGORY = By.xpath("//*[@id='UIForumPopupConfirmation']//*[text()='OK']");

	//------------add category form------------------------------------------------------------------------
	public String[] SET_PERMISSION = {"moderators", "Topicable", "Postable", "Viewer"};
	public String ELEMENT_MODERATORS = "moderators";
	public String ELEMENT_TOPICABLE = "Topicable";
	public String ELEMENT_POSTABLE = "Postable";
	public String ELEMENT_VIEWER = "Viewer";

	//---------------Import category form--------------------------------------
	public By ELEMENT_IMPORT_FRAME = By.xpath("//iframe[contains(@id,'uploadFrame')]");

	//-------------------Export category form-------------------------------------
	public By ELEMENT_EXPORT_CATEGORY_BODY = By.xpath("//*[@id='UIExportForm']/*//fieldset/*//tbody");
	public String ELEMENT_EXPORT_CATEGORY_CHOOSE = "//label[text()='${category}']/../../*//input[@type='checkbox']";	

	/*------------------------------------common function---------------------------------*/

	/** Function go to Add Category Popup
	 * @author lientm
	 */
	public void goToAddCategory(){
		info("Go to add new category");
		click(ELEMENT_ADD_CATEGORY);
		waitForAndGetElement(ELEMENT_POPUP_ADD_CATEGORY);
	}

	public void selectRestricted(int chooseRestricted, String[] restricted){
		switch (chooseRestricted) {
		case 1:
			type(ELEMENT_RESTRICTED_AUDIENCE, restricted[0], false);
			break;
		case 2:
			click(ELEMENT_RESTRICTED_SELECT_USER);
			per.selectUserPermission(restricted[0]);
			break;
		case 3:
			click(ELEMENT_RESTRICTED_SELECT_GROUP);
			per.selectGroupPermission(restricted[0]);
			break;
		case 4:
			click(ELEMENT_RESTRICTED_SELECT_ROLE);
			per.selectGroupMembership(restricted[0], restricted[1]);
			break;
		default:
			break;
		}
	}
	
	/**
	 * function input data in category form
	 * @param catName
	 * @param order
	 * @param chooseRestricted
	 * @param restricted
	 * @param description
	 * @param setPermission
	 * @param userGroup
	 * @param permission
	 */
	public void inputDataCategoryInForum(String catName, String order, int chooseRestricted, String[] restricted, 
			String description, int setPermission, String[] userGroup, boolean... permission){

		if (catName != null){
			type(ELEMENT_CATEGORY_TITLE, catName, true);
		}
		if (order != null){
			type(ELEMENT_CATEGORY_ORDER, order, true);
		}
		selectRestricted(chooseRestricted, restricted);
		if (description != null){
			type(ELEMENT_DESCRIPTION, description, true);
		}
		//set permission
		if(setPermission != 0){
			info("Set permission for category");
			frumPer.configPermission4ForumCategory(setPermission, userGroup, permission);
		}

	}

	/**
	 * function add new category in Forum app
	 * @param catName
	 * @param order
	 * @param chooseRestricted
	 * @param restricted
	 * @param description
	 * @param setPermission
	 * @param userGroup
	 * @param permission
	 */
	public void addNewCategoryInForum(String catName, String order, int chooseRestricted, String[] restricted, 
			String description, int setPermission, String[] userGroup, boolean... permission){
		but = new Button(driver);
		goToAddCategory();
		inputDataCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, permission);
		but.save();
		boolean verify = permission.length > 5 ? permission[5]:true;
		if (verify){
			waitForAndGetElement(ELEMENT_CATEGORY.replace("${categoryName}", catName));
		}
	}

	/**function go to edit category
	 * @author lientm
	 */
	public void goToEditCategoryInForum(){
		info("Go to edit category");
		click(ELEMENT_MANAGE_CATEGORY);
		click(ELEMENT_EDIT_CATEGORY);
		waitForAndGetElement(ELEMENT_POPUP_ADD_CATEGORY);
	}

	/**
	 * function edit category in Forum app
	 * @param catName
	 * @param order
	 * @param chooseRestricted
	 * @param restricted
	 * @param description
	 * @param setPermission
	 * @param userGroup
	 * @param permission
	 */
	public void editCategoryInForum(String catName, String order, int chooseRestricted, String[] restricted, 
			String description, int setPermission, String[] userGroup, boolean... permission){
		but = new Button(driver);
		goToEditCategoryInForum();
		inputDataCategoryInForum(catName, order, chooseRestricted, restricted, description, setPermission, userGroup, permission);
		but.save();
		boolean verify = permission.length > 5 ? permission[5]:true;
		if (verify){
			waitForAndGetElement(ELEMENT_CATEGORY.replace("${categoryName}", catName));
		}
	}

	/**function: delete a category
	 * @author lientm
	 * @param title: title of category
	 */
	public void deleteCategoryInForum(String title, boolean...verify){
		Boolean check = verify.length > 0 ? verify[0] : true;

		click(ELEMENT_MANAGE_CATEGORY);
		info("Delete category");
		click(ELEMENT_DELETE_CATEGORY);
		click(ELEMENT_OK_DELETE_CATEGORY);
		if(check == true){
			//waitForTextNotPresent(title);
			waitForElementNotPresent(By.linkText(title));
		}
		info("Delete category successfully");
	}

	/** function: import a category
	 * @author lientm
	 * @param file: file import
	 */
	public void importCategoryInForum(String file){
		click(ELEMENT_ADMINISTRATION);
		click(ELEMENT_IMPORT);
		waitForAndGetElement(ELEMENT_IMPORT_POPUP);
		info("Import category");
		WebElement element = waitForAndGetElement(ELEMENT_IMPORT_FILE, DEFAULT_TIMEOUT, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", element);
		element.sendKeys(Utils.getAbsoluteFilePath("TestData/" + file));
		switchToParentWindow();
		waitForAndGetElement("//*[text()='" + file + "']", DEFAULT_TIMEOUT, 1, 2);
		but.save();
		waitForMessage(MSG_IMPORT_CATEGORY);
		info("Import file " + file + "successfully");
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}

	/** function: export a category
	 * @author lientm
	 * @param category: title of category that do not need to export
	 * @param fileName: file name export
	 * @param mode: full or only category
	 */
	public void exportCategoryInForum(String fileName, boolean mode, String...cat){

		but = new Button(driver);
		if(waitForAndGetElement(ELEMENT_ADMINISTRATION,10000,0) == null)
			click(ELEMENT_MORE_BUTTON);
		click(ELEMENT_ADMINISTRATION);
		click(ELEMENT_EXPORT_CATEGORY);
		waitForAndGetElement(ELEMENT_EXPORT_CATEGORY_POPUP);

		if (cat.length > 0){
			List <WebElement> checkbox = getElements(ELEMENT_EXPORT_CATEGORY_CHECKBOX_LIST);
			if (checkbox.size() != cat.length){
				info("Uncheck category list");
				for (int i = 0; i < checkbox.size(); i ++){
					uncheck(ELEMENT_EXPORT_CATEGORY_NO.replace("${No}", Integer.toString(i + 1)), 2);
				}
				info("Check categories that need to export");
				for (int i = 0; i < cat.length; i ++){
					check(ELEMENT_EXPORT_CATEGORY_CHECKBOX.replace("${catName}", cat[i]), 2);

				}
			}
		}		
		type(ELEMENT_EXPORT_CATEGORY_FILE_NAME, fileName, true);
		if (mode){
			check(ELEMENT_EXPORT_CATEGORY_ALL, 2);
		}else{
			check(ELEMENT_EXPORT_CATEGORY_ONLY, 2);
		}
		but.save();
		waitForElementNotPresent(ELEMENT_EXPORT_CATEGORY_POPUP, 50000);
	}

	/**
	 * function export forums in a category
	 * @param fileName
	 * @param forum
	 */
	public void exportForumsOfCategory(String fileName, String...forum){
		but = new Button(driver);
		click(ELEMENT_MANAGE_CATEGORY);
		click(ELEMENT_EXPORT_FORUM_IN_CATEGORY);
		waitForAndGetElement(ELEMENT_EXPORT_FORUMS_POPUP);

		if (forum.length > 0){
			info("Uncheck forum list");
			List <WebElement> checkbox = getElements(ELEMENT_EXPORT_FORUMS_CHECKBOX_LIST);
			if (checkbox.size() != forum.length){
				for (int i = 0; i < checkbox.size(); i ++){
					uncheck(ELEMENT_EXPORT_FORUMS_NO.replace("${No}", Integer.toString(i + 1)), 2);
				}

				for (int i = 0; i < forum.length; i ++){
					check(ELEMENT_EXPORT_FORUMS_CHECKBOX.replace("${catName}", forum[i]), 2);
				}

			}
		}		
		type(ELEMENT_EXPORT_CATEGORY_FILE_NAME, fileName, true);
		but.save();
		waitForElementNotPresent(ELEMENT_EXPORT_FORUMS_POPUP);
	}

	/**
	 * function import forums into a category
	 * @param file
	 */
	public void importForums2Category(String file){
		but = new Button(driver);
		click(ELEMENT_MANAGE_CATEGORY);
		click(ELEMENT_IMPORT_FORUM_IN_CATEGORY);
		info("Import category");
		WebElement element = waitForAndGetElement(ELEMENT_IMPORT_FILE, DEFAULT_TIMEOUT, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", element);
		element.sendKeys(Utils.getAbsoluteFilePath("TestData/" + file));
		switchToParentWindow();
		waitForAndGetElement("//*[text()='" + file + "']", DEFAULT_TIMEOUT, 1, 2);
		but.save();
		info("Import file " + file + "successfully");
		click(ELEMENT_OK_INFOR_POPUP);
		Utils.pause(1000);
	}
}
