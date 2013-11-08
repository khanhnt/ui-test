package org.exoplatform.selenium.platform;
import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author phuongdt
 * @date 25/10/2013
 *
 */
public class SettingSearchPage extends PlatformBase {
	NavigationToolbar navTool;
	PageEditor pEditor;
	ManageApplications app;

	//Search page
	public final By ELEMENT_SEARCH_TEXTBOX = By.id("txtQuery");
	public final By ELEMENT_SEARCH_BUTTON = By.id("btnSearch");
	
	//Filter sort
	public final By ELEMENT_SORT_DROPDOWN = By.xpath("//a[@class='btn dropdown-toggle']");
	public final String ELEMENT_SORT_ITEM_CURRENT = "//*[@class='btn dropdown-toggle']/span[contains(text(),'${sortItem}')]";
	public final String ELEMENT_SORT_ITEM_OPTION = "//*[@id='sortOptions']//a[contains(text(),'${sortItem}')]";

	//Edit dialog
	public final By ELEMENT_EDIT_MODE_TAB = By.xpath("//*[@data-toggle='tab' and text()='Edit Mode']");
	public final By ELEMENT_PORTLET_SETTING_TAB = By.xpath("//*[@data-toggle='tab' and text()='Portlet Setting']");
	public final By ELEMENT_SELECT_ICON_TAB = By.xpath("//*[@data-toggle='tab' and text()='Select Icon']");
	public final By ELEMENT_DECORATION_THEMES_TAB = By.xpath("//*[@data-toggle='tab' and text()='Decoration Themes']");
	public final By ELEMENT_ACCESS_PERMISSION_TAB = By.xpath("//*[@data-toggle='tab' and text()='Access Permission']");

	//Setting search tab
	public final By ELEMENT_RESULTS_PER_PAGE_DROPDOWN = By.id("resultsPerPage");
	public final String ELEMENT_RESULT_PER_PAGE_OPTION = "//*[@id='resultsPerPage']/option[text()='${resultsPerPage}']";
	public final By ELEMENT_SEARCH_CURRENT_SITE_ONLY_CHECKBOX = By.id("searchCurrentSiteOnly");
	public final By ELEMENT_HIDE_SEARCH_FORM_CHECKBOX = By.id("hideSearchForm");
	public final By ELEMENT_HIDE_FACETS_FILTER_CHECKBOX = By.id("hideFacetsFilter");
	public final By ELEMENT_SEARCH_ALL_CHECKBOX = By.xpath("//span[@class='uiCheckbox']/span[text()='All']/../input[@class='checkbox']");
	public final By ELEMENT_SEARCH_EVERYTHING_CHECKBOX = By.xpath("//span[@class='uiCheckbox']/span[text()='Everything']/../input[@class='checkbox']");
	public final By ELEMENT_SEARCH_FILES_CHECKBOX = By.xpath("//span[@class='uiCheckbox']/span[text()='Files']/../input[@class='checkbox']");
	public final By ELEMENT_SEARCH_WIKI_CHECKBOX = By.xpath("//span[@class='uiCheckbox']/span[text()='Wiki']/../input[@class='checkbox']");
	public final By ELEMENT_SAVE_SETTING = By.id("btnSave");

	//Portlet Setting
	public final By ELEMENT_PORTLET_TITLE_TEXTBOX = By.id("title");
	public final By ELEMENT_WIDTH_TEXTBOX = By.id("width");
	public final By ELEMENT_HEIGHT_TEXTBOX = By.id("height");
	public final By ELEMENT_SHOW_INFO_BAR_CHECKBOX = By.id("showInfoBar");
	public final By ELEMENT_SHOW_PORTLET_MODE_CHECKBOX = By.id("showPortletMode");
	public final By ELEMENT_SHOW_WINDOW_STATE_CHECKBOX = By.id("showPortletMode");
	public final By ELEMENT_DESCRIPTION_TEXTBOX = By.id("description");

	//Filter Search
	public final By ELEMENT_FILTER_SEARCH_ALLSITE_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='acme' and @name='site']");
	public final By ELEMENT_FILTER_SEARCH_ACME_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='acme'] and @name='site'");
	public final By ELEMENT_FILTER_SEARCH_INTRANET_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='intranet' and @name='site']");
	public final By ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='all' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_FILE_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='file' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_DOCUMENT_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='document' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_WIKI_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='wiki' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_PAGE_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='page' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_DISCUSSION_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='post' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_PEOPLE_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='people' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_SPACE_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='space' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_EVENT_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='event' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_TASK_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='task' and @name='contentType']");
	public final By ELEMENT_FILTER_SEARCH_ANSWER_CHECKBOX = By.xpath("//input[@class='checkbox' and @value='answer' and @name='contentType']");

	//Search result form
	public final By ELEMENT_RESULT_SEARCH_PAGE = By.id("resultPage");
	public final String ELEMENT_RESULT_ITEM = "//div[@id='resultPage']//a[text()='${item}']/*[contains(text(),'${keySearch}')]";
	public final String ELEMENT_RESULT_LOCATION_DATETIME = "//div[@id='resultPage']//a[text()='${item}']/*[contains(text(),'${keySearch}')]/../../../*[@class='detail']";
	public final String ELEMENT_RESULT_EXCERPT = "//div[@id='resultPage']//a[text()='${item}']/*[contains(text(),'${keySearch}')]/../../../*[@class='excerpt']";
	public final String ELEMENT_RESULT_ICON = "//div[@id='resultPage']//a[text()='${item}']/*[contains(text(),'${keySearch}')]/../../../../*[@class='avatar pull-left']";
	public final String ELEMENT_RESULT_INDEX = "//*[@id='result']/div[${index}][contains(@class,'${title}')]";
	public final By ELEMENT_RESULT_CONTENT_DETAIL = By.xpath("//*[@class='content']/*[@class='detail']");

	//Task page result
	public final String ELEMENT_RESULT_TASK_ICON = "//div[@id='resultPage']//a[text()='${item}']/*[contains(text(),'${keySearch}')]/../../../..//*[@class='uiIconStatus-64-needs-action']";

	//Event page result
	public final String ELEMENT_RESULT_EVENT_ICON = "//div[@id='resultPage']//a[text()='${item}']/*[contains(text(),'${keySearch}')]/../../../..//*[@class='calendarBox']";

	//People page result
	public final String ELEMENT_RESULT_PEOPLE_ICON = "//*[@class='avatar pull-left']";
	public final By ELEMENT_RESULT_TITLE = By.xpath("//*[@class='content']//a");

	//File page result
	public final String ELEMENT_RESULT_FILE_ICON = "//div[@id='resultPage']//a[text()='${item}']/*[contains(text(),'${keySearch}')]/../../../..//*[@class='avatar pull-left']";

	//Forum page result
	public final By ELEMENT_RESULT_FORUM_ICON = By.xpath("//*[@class='uiIconAppForumPortlet']");
	public final By ELEMENT_RESULT_FORUM_VOTE = By.xpath("//*[@class='avgRatingImages clearfix']");

	//Answer page result
	public final By ELEMENT_RESULT_ANSWER_ICON = By.xpath("//*[@class='uiIconAppAnswersPortlet']");

	public SettingSearchPage(WebDriver dr){
		driver = dr;
		navTool = new NavigationToolbar(driver);
		pEditor = new PageEditor(driver);
		button = new Button(driver);
		app = new ManageApplications(driver);
		alert = new ManageAlert(driver);
	}

	public void addQuickSearchPage(String pageName, String gadget){
		app.showImportApplication(true);
		app.importApplication();
		navTool.goToHomePage();
		Utils.pause(1000);
		info("Create new page winzard empty layout and drags and drop gadget");
		pEditor.goToPageEditor_EmptyLayout(pageName);
		if(waitForAndGetElement(ELEMENT_GADGET_SEARCH_APPLICATION_PAGE_EDITOR.replace("${gadget}", gadget), DEFAULT_TIMEOUT,0)==null)
			click(ELEMENT_SEARCH_APPLICATION);
		dragAndDropToObject(By.xpath(ELEMENT_GADGET_SEARCH_APPLICATION_PAGE_EDITOR.replace("${gadget}", gadget)), ELEMENT_DROP_TARGET_NO_LAYOUT);
		pEditor.finishEditLayout();
	}

	/** quick search a text
	 * @author phuongdt
	 * @param searchText
	 */
	public void quickSearch(String searchText){
		info("-- Go to quick search --");
		click(ELEMENT_QUICK_SEARCH_ICON);
		type(ELEMENT_QUICK_SEARCH_TEXTBOX,searchText,true);
		Utils.pause(3000);
		waitForAndGetElement(ELEMENT_QUICK_SEARCH_TEXTBOX).sendKeys(Keys.RETURN);
		Utils.pause(1000);
	}

	/** Go To Edit Search Portlet
	 * @author phuongdt
	 */
	public void goToEditSearchPortlet(){
		navTool.goToEditLayout();
		pEditor.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		waitForAndGetElement(ELEMENT_EDIT_MODE_TAB);
	}

	/** Edit tab edit mode (quick search page)
	 * @author phuongdt
	 * @param resultsPerPage
	 * @param searchCurrentSiteOnly
	 * @param hideSearch
	 * @param hideFacet
	 * @param everyThing
	 * @param files
	 * @param wiki
	 */
	public void editQuickSearchSettingEditMode(String resultsPerPage, boolean searchCurrentSiteOnly,boolean everyThing, boolean files, boolean wiki){
		info("-- Edit Search Setting tab--");
		waitForAndGetElement(ELEMENT_EDIT_MODE_TAB).click();
		if(resultsPerPage!=""){
			click(ELEMENT_RESULTS_PER_PAGE_DROPDOWN,2);
			click(ELEMENT_RESULT_PER_PAGE_OPTION.replace("${resultsPerPage}", resultsPerPage));
		}
		if(searchCurrentSiteOnly)
			check(ELEMENT_SEARCH_CURRENT_SITE_ONLY_CHECKBOX,2);
		else
			uncheck(ELEMENT_SEARCH_CURRENT_SITE_ONLY_CHECKBOX,2);
		if(everyThing)
			check(ELEMENT_SEARCH_ALL_CHECKBOX,2);
		else
			uncheck(ELEMENT_SEARCH_ALL_CHECKBOX,2);
		if(files)
			check(ELEMENT_SEARCH_FILES_CHECKBOX,2);
		else
			uncheck(ELEMENT_SEARCH_FILES_CHECKBOX,2);
		if(wiki)
			check(ELEMENT_SEARCH_WIKI_CHECKBOX,2);
		else
			uncheck(ELEMENT_SEARCH_WIKI_CHECKBOX);
		click(ELEMENT_SAVE_SETTING);
		alert.acceptAlert();
		button.close();
		pEditor.finishEditLayout();

	}

	/** Edit tab edit mode
	 * @author phuongdt
	 * @param resultsPerPage
	 * @param searchCurrentSiteOnly
	 * @param hideSearch
	 * @param hideFacet
	 * @param everyThing
	 * @param files
	 * @param wiki
	 */
	public void editSearchSettingEditMode(String resultsPerPage, boolean searchCurrentSiteOnly, boolean hideSearch, boolean hideFacet, boolean everyThing, boolean files, boolean wiki){
		info("-- Edit Search Setting tab--");
		click(ELEMENT_EDIT_MODE_TAB);
		if(resultsPerPage!=""){
			click(ELEMENT_RESULTS_PER_PAGE_DROPDOWN,2);
			click(ELEMENT_RESULT_PER_PAGE_OPTION.replace("${resultsPerPage}", resultsPerPage));
		}
		if(searchCurrentSiteOnly)
			check(ELEMENT_SEARCH_CURRENT_SITE_ONLY_CHECKBOX,2);
		else
			uncheck(ELEMENT_SEARCH_CURRENT_SITE_ONLY_CHECKBOX,2);
		if(hideSearch)
			check(ELEMENT_HIDE_SEARCH_FORM_CHECKBOX,2);
		else
			uncheck(ELEMENT_HIDE_SEARCH_FORM_CHECKBOX,2);
		if(hideFacet)
			check(ELEMENT_HIDE_FACETS_FILTER_CHECKBOX,2);
		else
			uncheck(ELEMENT_HIDE_FACETS_FILTER_CHECKBOX,2);
		if(everyThing)
			check(ELEMENT_SEARCH_EVERYTHING_CHECKBOX,2);
		else
			uncheck(ELEMENT_SEARCH_EVERYTHING_CHECKBOX,2);
		if(files)
			check(ELEMENT_SEARCH_FILES_CHECKBOX,2);
		else
			uncheck(ELEMENT_SEARCH_FILES_CHECKBOX,2);
		if(wiki)
			check(ELEMENT_SEARCH_WIKI_CHECKBOX,2);
		else
			uncheck(ELEMENT_SEARCH_WIKI_CHECKBOX);
		click(ELEMENT_SAVE_SETTING);
		alert.acceptAlert();
		button.close();
		pEditor.finishEditLayout();

	}

	/**Edit portlet setting
	 * @author phuongdt
	 * @param portletTitle
	 * @param width
	 * @param height
	 * @param showInfoBar
	 * @param showPortletMode
	 * @param showWindowState
	 * @param description
	 */
	public void editPortletSetting(String portletTitle, String width, String height, boolean showInfoBar, boolean showPortletMode, boolean showWindowState, String description){
		info("-- Edit portlet setting tab--");
		waitForAndGetElement(ELEMENT_PORTLET_SETTING_TAB).click();
		if(portletTitle!=null)
			type(ELEMENT_PORTLET_TITLE_TEXTBOX, portletTitle,true);
		if(width!=null)
			type(ELEMENT_WIDTH_TEXTBOX,width,true);
		if(height!=null)
			type(ELEMENT_HEIGHT_TEXTBOX,height,true); 
		if(showInfoBar)
			check(ELEMENT_SHOW_INFO_BAR_CHECKBOX,2);
		else
			uncheck(ELEMENT_SHOW_INFO_BAR_CHECKBOX,2);
		if(showPortletMode)
			check(ELEMENT_SHOW_PORTLET_MODE_CHECKBOX,2);
		else
			uncheck(ELEMENT_SHOW_PORTLET_MODE_CHECKBOX,2);
		if(showWindowState)
			check(ELEMENT_SHOW_WINDOW_STATE_CHECKBOX,2);
		else
			uncheck(ELEMENT_SHOW_WINDOW_STATE_CHECKBOX,2);
		if(description!=null)
			type(ELEMENT_DESCRIPTION_TEXTBOX,description,true);
		button.saveAndClose();
	}

	/**
	 * Search item in search page
	 * @author phuongdt
	 * @param keySearch
	 */
	public void searchInSearchPage(String keySearch){
		type(ELEMENT_SEARCH_TEXTBOX, keySearch,true);
		click(ELEMENT_SEARCH_BUTTON);
		Utils.pause(1000);
	}

	/**
	 * Sort item in result search page
	 * @author phuongdt
	 * @param item
	 */
	public void sortByItem(String sortItem){
		info("-- Select sort "+sortItem+" --");
		if(waitForAndGetElement(ELEMENT_SORT_ITEM_CURRENT.replace("${sortItem}", sortItem), DEFAULT_TIMEOUT, 0)==null){
			click(ELEMENT_SORT_DROPDOWN,2);
			click(ELEMENT_SORT_ITEM_OPTION.replace("${sortItem}", sortItem));
			waitForAndGetElement(ELEMENT_SORT_ITEM_CURRENT.replace("${sortItem}", sortItem));

		}
	}
}
