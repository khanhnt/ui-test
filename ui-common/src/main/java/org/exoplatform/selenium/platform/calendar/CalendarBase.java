package org.exoplatform.selenium.platform.calendar;

import static org.exoplatform.selenium.TestLogger.info;

import java.awt.event.KeyEvent;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.PlatformPermission;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.internal.seleniumemulation.KeyEvent;

/**
 * 
 * @author thuntn
 * @date 10 Oct 2013
 */
public class CalendarBase extends PlatformBase {

	PlatformPermission per;
	//Button btn;

	//Go to the calendar's page
	public String ID_CALENDAR_PAGE = "";
	public By ELEMENT_GET_ID_PAGE = By.xpath("//*[@id='CalendarApplicationMinWidth']/../..");
	public By ELEMENT_CALENDAR_LINK = By.className("uiIconPLFCalendar");
	public By ELEMENT_CALENDAR_PANEL = By.xpath("//div[@class='uiBox uiCalendars']");
	public String ELEMENT_CALENDAR_MINI_DATE= "//td[@class='highLight' and contains(text(),'${date}')]";
	public String ELEMENT_CALENDAR_SETTING_ICON = "//a[text()='${calendar}']/ancestor::li[contains(@class, 'calendarItem')]/div[contains(@id,'UICalendars')]";
	public By ELEMENT_CALENDAR_ACTIONS_ICON = By.xpath("//*[@class='uiIconCalSimplePlus uiIconLightGray']");
	public By ELEMENT_CALENDAR_ADD_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'AddCalendar')]");
	public By ELEMENT_CALENDAR_SETTINGS = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'CalendarSetting')]");
	public By ELEMENT_CALENDAR_IMPORT_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'ImportCalendar')]");
	public String ELEMENT_CALENDAR_GET_BY_TAG_LI = "//a[@class='calendarName' and contains(text(), '${calendar}')]/../..";
	public By ELEMENT_CALENDAR_POPUP_WINDOW = By.xpath("//*[@id='UICalendarPopupWindow']/div[2]");
	public String ELEMENT_VERIFY_CALENDAR_FORM = "//*[@id='defaultCalendarTab'] //div[@class='myCalendar']/*[@class='calendarTitle']/..//li[contains(@class,'calendarItem' )]//*[text()='${UserName}']/../a[@class='${CheckboxColor}']";

	//-----------Calendar Settings ----------
	public By ELEMENT_SETTINGS_TAB = By.xpath("//a[@data-toggle='tab' and text()='Settings']");
	public By ELEMENT_DISPLAYED_CALENDAR = By.xpath("//a[@data-toggle='tab' and text()='Displayed Calendars']");
	public By ELEMENT_FEEDS = By.xpath("//a[@data-toggle='tab' and text()='Feeds']");
	public By ELEMENT_CALENDAR_TAB_DEFAULT = By.xpath("//*[@id='defaultCalendarTab']");
	public By ELEMENT_PERSONAL_CALENDAR = By.xpath("//*[@id='defaultCalendarTab']//div[@class='myCalendar']/*[@class='calendarTitle' and text()='Personal Calendars']");
	public By ELEMENT_GROUP_CALENDAR = By.xpath("//*[@id='defaultCalendarTab']//div[@class='myCalendar']/*[@class='calendarTitle' and text()='Group Calendars']");
	public String ELEMENT_VERIFY_CALENDAR = "//*[@id='defaultCalendarTab'] //div[@class='myCalendar']/*[@class='calendarTitle']/..//li[contains(@class,'calendarItem' )]//*[text()='${UserName}']/../a[@class='${CheckboxColor}']//span[@class='${checkicon}']";
	public By ELEMENT_UNCHECK_BOX = By.xpath("//span[@class='iconCheckBox checkbox']");
	public By ELEMENT_SETTINGS_FORM_SAVE_BUTTON = By.xpath("//*[@id='UICalendarSettingForm']//*[text()='Save']");

	public By ELEMENT_VIEW_TYPE = By.name("viewType");
	public By ELEMENT_SELECTED_VIEW_TYPE = By.xpath("//*[@name='viewType']//*[@selected='selected']");
	public By ELEMENT_DATE_FORMAT = By.name("dateFormat");
	public By ELEMENT_SELECTED_DATE_FORMAT = By.xpath("//*[@name='dateFormat']//*[@selected='selected']");
	public By ELEMENT_TIME_FORMAT = By.name("timeFormat");
	public By ELEMENT_SELECTED_TIME_FORMAT = By.xpath("//*[@name='timeFormat']//*[@selected='selected']");
	public By ELEMENT_TIME_ZONE = By.name("timeZone");
	public By ELEMENT_SELECTED_TIME_ZONE = By.xpath("//*[@name='timeZone']//*[@selected='selected']");
	public By ELEMENT_WEEK_START_ON = By.name("weekStartOn");
	public By ELEMENT_SELECTED_WEEK_START_ON = By.xpath("//*[@name='weekStartOn']//*[@selected='selected']");
	public String ELEMENT_SEND_EVENT_INVITATION = "//*[text()='${option}']/../input[@name='send']";
	public By ELEMENT_SHOW_WORKING_TIME_CHECKBOX = By.name("showWorkingTime");
	public By ELEMENT_BEGIN_TIME = By.name("beginTime");
	public By ELEMENT_END_TIME = By.name("endTime");
	public By ELEMENT_MONTH_TAB_ACTIVE = By.xpath("//*[text()='Month']/ancestor::li[contains(@class, 'active')]");
	public By ELEMENT_WEEK_TAB = By.xpath("//*[text()='Week']/ancestor::li[contains(@class, 'btn')]");


	//------------Add event category------------------
	public String ELEMENT_ADD_EVENT_CATEGORY_ICON = "//*[@id='tmpMenuElement']//i[@class='uiIconCalCreateEvent uiIconLightGray']";
	public By ELEMENT_ADD_EVENT_CATEGORY_INPUT = By.id("eventCategoryName");
	public String ELEMENT_ADD_EVENT_CATEGORY_BUTTON_ADD = "//*[@id='btnEventCategoryFormContainer']";
	public String ELEMENT_ADD_EVENT_CATEGORY_BUTTON_CLOSE = "//*[@id='UIEventCategoryForm']//button[contains(text(),'Close')]";
	public String ELEMENT_EDIT_EVENT_CATEGORY_BUTTON_UPDATE = "//*[@id='btnEventCategoryFormContainer']";
	public String ELEMENT_EVENT_CATEGORY_FILTER = "//div[@class='pull-right eventCategory']/span[@class='uiSelectbox']";
	public String ELEMENT_EVENT_CATEGORY_COMBOBOX = "//*[@name='eventCategories']";
	public String ELEMENT_EVENT_CATEGORY_COMBOBOX_OPTION = "//*[@name='eventCategories']/option[contains(text(),'${categoryName}')]";
	public String ELEMENT_LIST_EVENT_CATEGORY = "//*[@id='UIEventCategoryList']//span[contains(text(),'${categoryName}')]";
	public String ELEMENT_LIST_DELETE_EVENT_BUTTON = "//*[@id='UIEventCategoryList']//span[contains(text(),'${categoryName}')]/parent::td/parent::tr//a[@data-original-title='Delete']/i[@class='uiIconDelete uiIconLightGray']";
	public String ELEMENT_LIST_EDIT_EVENT_BUTTON = ".//*[@id='UIEventCategoryList']//span[contains(text(),'${categoryName}')]/parent::td/parent::tr//a[@data-original-title='Edit']/i[@class='uiIconEdit uiIconLightGray']";

	//-----------Menu of calendar------------
	public By ELEMENT_CAL_ADD_EVENT_MENU = By.id("AddEvent");
	public By ELEMENT_CAL_ADD_TASK_MENU = By.id("AddTask");
	public By ELEMENT_CAL_REMOVE_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'RemoveSharedCalendar')]");
	public By ELEMENT_CAL_IMPORT_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'ImportCalendar')]");
	public By ELEMENT_CAL_EXPORT_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'ExportCalendar')]");
	public By ELEMENT_CAL_REFRESH_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'RefreshRemoteCalendar')]");
	public By ELEMENT_CAL_SHARE_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'ShareCalendar')]");
	public By ELEMENT_CAL_EDIT_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'EditCalendar')]");
	public By ELEMENT_CAL_SETTING_MENU = By.xpath("//*[@id='UIActionBar']//i[@class='uiIconSetting uiIconLightGray']");
	public By ELEMENT_CAL_SETTING_TIMEZONE_COMBOBOX = By.name("timeZone");
	public String ELEMENT_CAL_SETTING_TIMEZONE_VALUE = "//*[@id='setting']//select[@name='timeZone']/option[@value='${timezoneOpt}']";

	//------------Export calendar---------------
	public By ELEMENT_CALENDAR_EXPORT = By.xpath("//div[@id='CalendarPopupMenu']//*[@class='uiIconCalExportCalendar uiIconLightGray']");
	public By ELEMENT_CALENDAR_EXPORT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Export Calendar']");
	public By ELEMENT_CAL_EXPORT_FILE_NAME = By.id("name");
	public By ELEMENT_CAL_EXPORT_SAVE_BUTTON = By.xpath("//*[@id='UIExportForm']//*[text()='Save']");

	//---------- Feeds -------------------------
	public By ELEMENT_NAME_FEEDS = By.xpath("//div[@id='UIEditFeed']//input[@id='name']");
	public By ELEMENT_URL_FEEDS = By.id("url");
	public By ELEMENT_RESET_URL = By.xpath("//div[@id='UIEditFeed']//div[@class='controls inputLarge']//a[@title='Reset URL']");
	public By ELEMENT_GENERATE_URL = By.xpath("//div[@id='UIEditFeed']//div[@class='controls inputLarge']//a[@title='Generate URL']");
	public By ELEMENT_ADD_MORE = By.xpath("//select[@name='addMore']");
	public By ELEMENT_ADD_FEEDS_BUTTON = By.xpath("//div[@id='feedTab-tab']//*[text()='Add']");
	public By ELEMENT_ADD_CALENDAR_BUTTON = By.xpath("//i[@class='uiIconPlus uiIconLightGray']");
	public By ELEMENT_FEEDS_SAVE_BUTTON = By.xpath("//*[@id='UIEditFeed']//*[contains(text(),'Save')]");
	public By ELEMENT_OK_POPUP_BUTTON = By.xpath("//div[@class='uiAction uiActionBorder']//*[text()='OK']");
	public String VERIFY_MESSAGE_URL = "The feed ${name} has been generated successfully.";
	public String ELEMENT_EDIT_FEEDS = "//tr/td/span[text()='${namefeeds}']/../..//a[@class='actionIcon']//i[@class='uiIconEdit uiIconLightGray']";
	public By ELEMENT_DELETE_FEEDS = By.xpath("//a[@class='actionIcon']//i[@class='uiIconDelete uiIconLightGray']");
	public String MSG_FEEDS_DELETE = "Are you sure you want to delete this feed from the list?";

	//---------- Add calendar-------------------
	public By ELEMENT_CAL_DISPLAY_NAME_INPUT = By.id("displayName");
	public By ELEMENT_CAL_DESC_INPUT = By.xpath("//*[@id='UICalendarForm']//*[@id='description']");
	public By ELEMENT_CAL_COLOR = By.xpath("//*[contains(@class,'displayValue')]");
	public String ELEMENT_CAL_COLOR_SELECT = "//form[@id='UICalendarForm']//a[contains(@class,'red colorCell')]";
	public By ELEMENT_CAL_ADD_SAVE_BUTTON = By.xpath("//*[@id='UICalendarForm']//*[text()='Save']");
	public By ELEMENT_CAL_ADD_POPUP= By.xpath("//span[@class='PopupTitle popupTitle' and text()='Calendar']");
	public By ELEMENT_CAL_GROUP_TAB = By.linkText("Show in Groups");
	public By ELEMENT_CAL_GROUP_INPUT = By.id("AddGroupInput");
	public By ELEMENT_CAL_SELECT_GROUP_ICON = By.xpath("//*[@class='uiIconGroup uiIconLightGray']");

	//-----------Event/Task -----------
	public String ELEMENT_EVENT_TASK_ALL_DAY = "//div[@class='eventAlldayContent asparagus' and contains(text(),'${event}')]";
	public String ELEMENT_EVENT_TASK_ONE_DAY = "//*[@id='UIWeekViewGrid']//div[contains(text(),'${taskName}')]/parent::div[@class='clearfix']/div[@class='eventContainerBar eventTitle pull-left']";
	public String ELEMENT_EVENT_TASK_WORKING_PANE = "//div[contains(@class,'eventContainer') and contains(text(),'${event}')]";
	public By ELEMENT_EVENT_TASK_DELETE_MENU = By.xpath("//div[@id='tmpMenuElement']//a[@class='eventAction' and contains(@href,'Delete')]");
	public String MSG_EVENT_TASK_DELETE = "Are you sure you want to delete this event/task?";

	public String MSG_CALENDAR_DELETE = "Are you sure you want to delete this calendar and all its events?";

	//--------------Import calendar -------------------------
	public By ELEMENT_CAL_IMPORT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Calendar']");
	public By ELEMENT_CAL_IMPORT_TYPE = By.name("type");
	public By ELEMENT_CAL_IMPORT_SELECT_FILE = By.name("file");
	public By ELEMENT_CAL_IMPORT_FRAME_UDLOAD = By.xpath("//div[@id='upload']//iframe");
	public By ELEMENT_CAL_IMPORT_DESC_INPUT = By.xpath("//form[@id='UIImportForm']//*[@id='description']");
	public By ELEMENT_CAL_IMPORT_SAVE_BUTTON = By.xpath("//form[@id='UIImportForm']//*[text()='Save']");
	public By ELEMENT_CAL_IMPORT_DELETE_ICON = By.xpath("//a[@data-original-title='Delete']/i[@class='uiIconDelete uiIconLightGray']");

	//--------------Share calendar --------------------------
	public By ELEMENT_CAL_SELECT_USER_ICON = By.xpath("//*[@class='uiIconUser uiIconLightGray']");
	public By ELEMENT_CAL_SHARE_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Share Calendar']");
	public By ELEMENT_CAL_SHARE_ADD_BUTTON = By.xpath("//form[@id='UISharedForm']//*[text()='Add']");
	public By ELEMENT_CAL_SHARE_SAVE_BUTTON = By.xpath("//form[@id='UISharedForm']//*[text()='Save']");
	public String ELEMENT_CAL_SHARE_EDIT_PERMISSION = "//div[@title='${user}']/../..//input[@class='checkbox']"; 
	public By ELEMENT_CAL_SHARE_INPUT = By.id("PermissionOwnerInput");
	public By ELEMENT_CAL_SELECT_MEMBERSHIP_ICON = By.xpath("//i[@class='uiIconMembership uiIconLightGray']");

	//----------------DateTime of Calendar---------------
	public String ELEMENT_CURRENT_DATE = getCurrentDate("EEE MMM dd yyyy HH"); 
	public String ELEMENT_TARGET_TIME = ELEMENT_CURRENT_DATE +":00:00";
	public By ELEMENT_TARGET_DATE = By.xpath("//*[contains(@startfull, '${targetDate}')]".replace("${targetDate}", ELEMENT_TARGET_TIME));
	//-----------------Calendar Search-----------------------------
	public String ELEMENT_INPUT_QUICK_SEARCH = "//div[@class='uiSearchForm uiSearchInput pull-right']//*[@id='value']";
	public String ELEMENT_QUICK_SEARCH_FORM = "//div[@class='uiSearchForm uiSearchInput pull-right']";
	public String ELEMENT_BUTTON_CLOSE_QUICK_SEARCH_RESULT = "//*[@id='UIListView']//button[contains(text(),'Close Search')]";
	public String ELEMENT_BUTTON_OPEN_ADVANCE_SEARCH_FORM = "//*[@id='UIListView']//button[contains(text(),'Advanced Search')]";
	public String ELEMENT_INPUT_TEXT_ADVANCE_SEARCH = "//*[@id='UIAdvancedSearchForm']//*[@id='text']";
	public String ELEMENT_BUTTON_SEARCH_ADVANCE_SEARCH = "//*[@id='UIAdvancedSearchForm']//button[contains(text(),'Search')]";

	//----------------------Calendar View------------------------------
	public String ELEMENT_BUTTON_DAY_VIEW = "//*[@id='UIActionBar']//a[text()='Day']";
	public String ELEMENT_BUTTON_WEEK_VIEW = "//*[@id='UIActionBar']//a[text()='Week']";
	public String ELEMENT_BUTTON_MONTH_VIEW = "//*[@id='UIActionBar']//a[text()='Month']";
	public String ELEMENT_BUTTON_LIST_VIEW = "//*[@id='UIActionBar']//a[text()='List']";
	public String ELEMENT_BUTTON_WORK_WEEK_VIEW = "//*[@id='UIActionBar']//a[text()='Work Week']";

	public String EVENT_WEEK_VIEW = "//*[@id='UIWeekViewGridAllDay']//div[contains(text(),'${eventTitle}')]";
	public String EVENT_DAY_VIEW = "//*[@id='UIDayView']//div[contains(text(),'${eventTitle}')]";
	public String EVENT_MONTH_VIEW = "//*[@id='UIMonthView']//span[contains(text(),'${eventTitle}')]";
	public String EVENT_LIST_VIEW = "//*[@id='UIListUsers']//span[contains(text(),'${eventTitle}')]";
	public String EVENT_WORK_WEEK_VIEW = "//*[@id='UIWeekViewGridAllDay']//div[contains(text(),'${eventTitle}')]";

	/*================== Common functions for Calendar =================*/

	/** Go to calendar
	 * @author thuntn
	 */
	public void goToCalendarPage(){	
		info("--Go to calendar--");
		click(ELEMENT_CALENDAR_LINK);
		waitForAndGetElement(ELEMENT_CALENDAR_PANEL);
		ID_CALENDAR_PAGE = waitForAndGetElement(ELEMENT_GET_ID_PAGE).getAttribute("id");
	}

	/** Get Property of a calendar
	 * @author thuntn
	 * @param calendar
	 * @param property = 1: ID
	 *                 = 2: type
	 *                 = 3: color
	 * @return: ID of calendar
	 */
	public String getPropertyOfCalendar(String calendar,int property){
		WebElement eCalendar = waitForAndGetElement(ELEMENT_CALENDAR_GET_BY_TAG_LI.replace("${calendar}", calendar));
		switch (property){
		case 1: 
			return eCalendar.getAttribute("id");
		case 2: 
			return eCalendar.getAttribute("caltype");
		case 3: 
			return eCalendar.getAttribute("calcolor");
		default: 
			return "";
		}
	}

	/**
	 * Execute action of calendar: Edit, Delete, Share, export....
	 * @author thuntn
	 * @param idCal
	 * @param action
	 * @param color
	 * @param type
	 */
	public void executeActionCalendar(String calendar, String action){
		String idCal = getPropertyOfCalendar(calendar,1);
		String color = getPropertyOfCalendar(calendar,3);
		String type = getPropertyOfCalendar(calendar,2);
		((JavascriptExecutor)driver).executeScript("javascript:eXo.webui.UIForm.submitEvent('"+ID_CALENDAR_PAGE+"#UICalendars','"+action+"','&objectId="+idCal+"&calType="+type+"&calColor="+color+"')");
	}

	/** Share a calendar
	 * @author thuntn
	 * @param calendar
	 * @param userGroup
	 * @param canEdit
	 * @param mode
	 */
	public void shareCalendar(String calendar, String[] userGroup, boolean[] canEdit, int...mode){

		executeActionCalendar(calendar,"ShareCalendar");

		for(int i = 0; i < userGroup.length; i++){
			int modeUser = mode.length > i ? mode[i] : 0;
			switch (modeUser){
			case 0: type(ELEMENT_CAL_SHARE_INPUT,userGroup[i],false);
			break;
			case 1:
				click(ELEMENT_CAL_SELECT_USER_ICON); 
				per.selectUserPermission(userGroup[i]); break;
			case 2: 
				String[] group = userGroup[i].split(":");
				click(ELEMENT_CAL_SELECT_GROUP_ICON);
				per.selectGroupPermission(group[0]); break;
			case 3: 
				String[] groupMem = userGroup[i].split(":");
				String[] membership = groupMem[1].split(".");
				click(ELEMENT_CAL_SELECT_MEMBERSHIP_ICON);
				per.selectGroupMembership(groupMem[0],membership[1]); break;
			}
		}
		click(ELEMENT_CAL_SHARE_ADD_BUTTON);
		for(int j=0; j < canEdit.length; j++){
			check(ELEMENT_CAL_SHARE_EDIT_PERMISSION.replace("${user}", userGroup[j]),2);
		}
		click(ELEMENT_CAL_SHARE_SAVE_BUTTON);
	}


	/** Open export calendar form
	 * @author thuntn
	 * @param calendar
	 */
	public void goToExportCalendar(String calendar){

		openMenuOfCalendar(calendar);
		click(ELEMENT_CAL_EXPORT_MENU);
		waitForAndGetElement(ELEMENT_CALENDAR_EXPORT_POPUP);
	}

	/**
	 * Export calendar
	 * @author thuntn
	 * @param calendar
	 * @param name
	 */
	public void exportCalendar(String calendar, String name){
		info("Export calendar");
		goToExportCalendar(calendar);
		type(ELEMENT_CAL_EXPORT_FILE_NAME,name,true);
		click(ELEMENT_CAL_EXPORT_SAVE_BUTTON);
		Utils.pause(3000);
		driver.navigate().refresh();
		Utils.pause(3000);
	}

	/** Open Add calendar form
	 * @author thuntn
	 */
	public void goToAddCalendar(){
		click(ELEMENT_CALENDAR_ACTIONS_ICON);
		click(ELEMENT_CALENDAR_ADD_MENU);
	}

	/** Open Calendar Settings form
	 * @author HangNTT
	 */
	public void goToCalendarSettings(){
		click(ELEMENT_CALENDAR_ACTIONS_ICON);
		click(ELEMENT_CALENDAR_SETTINGS);
	}

	/** Input into Add calendar form
	 * @author thuntn
	 * @param name
	 * @param desc
	 * @param color
	 * @param groups
	 */
	public void inputAddCalendarForm(String name, String desc, String color, String...groups){
		String type = groups.length > 2 ? (String) groups[1]: "0";
		per = new PlatformPermission(driver);
		but = new Button(driver);

		if(name != null)
			type(ELEMENT_CAL_DISPLAY_NAME_INPUT,name,true);
		if(desc != null)
			type(ELEMENT_CAL_DESC_INPUT, desc,true);
		if(color != null){
			click(ELEMENT_CAL_COLOR);
			click(ELEMENT_CAL_COLOR_SELECT.replace("${color}", color));
		}
		if(groups.length > 0){
			click(ELEMENT_CAL_GROUP_TAB);
			if(type.equals("0")){
				click(ELEMENT_CAL_SELECT_GROUP_ICON);
				click(ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", groups[0]));
			}else
				type(ELEMENT_CAL_GROUP_INPUT,groups[0],true);
			//button.add();
			click(but.ELEMENT_ADD_BUTTON);
		}
	}

	/*================== Calendar Feeds ====================*/
	/** 
	 * Input into feeds form
	 * @author hangntt
	 * @param name
	 * @param groups
	 */
	public void inputFeedsData(String name,String[] userGroup, int...url){
		per = new PlatformPermission(driver);
		but = new Button(driver);

		Utils.pause(1000);
		if(name != null)
			type(ELEMENT_NAME_FEEDS,name,true);
		for(int i = 0; i < userGroup.length; i++){
			select(ELEMENT_ADD_MORE,userGroup[i]);
			click(ELEMENT_ADD_CALENDAR_BUTTON);
		}
		int urlfeed = url.length > 0 ? url[0] : 0;
		switch (urlfeed){
		case 1:
			click(ELEMENT_RESET_URL); break;
		case 2: 
			click(ELEMENT_GENERATE_URL);break;
		default: break;
		}
		click(ELEMENT_FEEDS_SAVE_BUTTON);
		waitForAndGetElement("//*[contains(text(),'"+VERIFY_MESSAGE_URL.replace("${name}", name)+"')]");
		click(ELEMENT_OK_POPUP_BUTTON);
		waitForAndGetElement("//*[contains(text(),'"+name+"')]");
	}

	/** Add feeds
	 * @author hangntt
	 * @param name
	 * @param url
	 * @param add more: name of group which is the same as data inputed by manual, eg: /platform/users
	 */
	public void addFeeds(String name, String[] userGroup,int...url){
		goToCalendarSettings();
		info("--Verify feeds tab--");
		click(ELEMENT_FEEDS);
		waitForAndGetElement(ELEMENT_FEEDS);
		click(ELEMENT_ADD_FEEDS_BUTTON);
		inputFeedsData(name, userGroup, url);

	}

	/** Edit feeds
	 * @author hangntt
	 * @param name
	 * @param url
	 * @param add more: name of group which is the same as data inputed by manual, eg: /platform/users
	 */
	public void editFeeds(String oldName, String name, String[] userGroup, int...url){
		click(ELEMENT_EDIT_FEEDS.replace("${namefeeds}", oldName));
		waitForAndGetElement(ELEMENT_FEEDS);
		inputFeedsData(name, userGroup, url);
	}

	/** delete feeds
	 * @author hangntt
	 */
	public void deleteFeeds(String name){
		alert = new ManageAlert(driver);
		info("--Delete event--");
		click(ELEMENT_DELETE_FEEDS);
		alert.waitForConfirmation(MSG_FEEDS_DELETE);
		waitForElementNotPresent(By.linkText(name));
		click(ELEMENT_SETTINGS_FORM_SAVE_BUTTON);
	}

	/*============== End of Feeds ===============*/

	/** Upload calendar
	 * @author thuntn
	 * @param path
	 */
	public void uploadCalendar(String path){
		info("--Upload Calendar--");
		WebElement element = waitForAndGetElement(ELEMENT_CAL_IMPORT_SELECT_FILE, DEFAULT_TIMEOUT, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", element);
		element.sendKeys(Utils.getAbsoluteFilePath(path));
		String[] links = path.split("/");
		waitForAndGetElement("//*[text()='"+links[links.length-1]+"']");
		info("import " + Utils.getAbsoluteFilePath(path));
		//		driver.switchTo().defaultContent();
		switchToParentWindow();
		waitForAndGetElement(ELEMENT_CAL_IMPORT_DELETE_ICON);
	}

	/**
	 * Open form Import calendar
	 * @author thuntn
	 */
	public void goToImportCalendar(){
		click(ELEMENT_CALENDAR_ACTIONS_ICON);
		click(ELEMENT_CALENDAR_IMPORT_MENU);
		waitForAndGetElement(ELEMENT_CAL_IMPORT_POPUP);
	}

	/** Import calendar
	 * @author thuntn
	 * @param path
	 * @param name
	 * @param desc
	 * @param color
	 */
	public void importCalendar(String path, String name, String desc, String color){
		goToImportCalendar();
		uploadCalendar(path);
		if(name != null)
			type(ELEMENT_CAL_DISPLAY_NAME_INPUT,name,true);
		if(desc != null)
			type(ELEMENT_CAL_IMPORT_DESC_INPUT,desc,true);
		click(ELEMENT_CAL_COLOR);
		if (color != null)
			click(ELEMENT_CAL_COLOR_SELECT.replace("${color}", color));
		click(ELEMENT_CAL_IMPORT_SAVE_BUTTON);
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(name));
	}

	/** 
	 * Delete event/task
	 * @author thuntn, havtt edited
	 * @param String event
	 * @param boolean allDay
	 */
	/*public void deleteEventTask(String event, boolean allDay){
		alert = new ManageAlert(driver);
		if (allDay == true)
		{
			info("--Delete event--");
			rightClickOnElement(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event),2);
			click(ELEMENT_EVENT_TASK_DELETE_MENU);
			alert.waitForConfirmation(MSG_EVENT_TASK_DELETE);
			waitForElementNotPresent(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event));
			waitForElementNotPresent(ELEMENT_EVENT_TASK_WORKING_PANE.replace("${event}", event));
		}
		else
		{        
			info("--Delete event--");
			rightClickOnElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event),2);
			click(ELEMENT_EVENT_TASK_DELETE_MENU);
			info("--Confirm deleted event--");
			alert.waitForConfirmation(MSG_EVENT_TASK_DELETE);
			alert.acceptAlert();
			waitForElementNotPresent(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event));
		}
	}*/

	/**
	 * Select an option when creating an Event/Task 
	 * ONE DAY
	 * ALL DAY  
	 */
	public enum selectDayOption{
		ONEDAY, ALLDAY;
	}

	public void deleteEventTask(String event, selectDayOption... options){
		alert = new ManageAlert(driver);
		selectDayOption optDay = options.length > 0 ? options[0] : selectDayOption.ALLDAY;

		info("--Delete an Event/Task--");
		switch (optDay) {
		case ALLDAY:
			if(waitForAndGetElement(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event), 5000, 0) == null){
				rightClickOnElement(ELEMENT_EVENT_TASK_WORKING_PANE.replace("${event}", event),2);
			}        
			else{
				rightClickOnElement(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event),2);
			}
			break;
		case ONEDAY:
			rightClickOnElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event),2);
			break;			
		default:
			break;
		}
		click(ELEMENT_EVENT_TASK_DELETE_MENU);
		alert.waitForConfirmation(MSG_EVENT_TASK_DELETE);
		driver.navigate().refresh();
		Utils.pause(1000);
		if (optDay.equals(selectDayOption.ALLDAY)){
			waitForElementNotPresent(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event));
			waitForElementNotPresent(ELEMENT_EVENT_TASK_WORKING_PANE.replace("${event}", event));
		}else if (optDay.equals(selectDayOption.ONEDAY)){
			waitForElementNotPresent(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event));
		}	
	}

	/*============Add, Edit, Delete a Calendar ===========*/
	/** 
	 * Add calendar
	 * @author thuntn
	 * @param name
	 * @param desc
	 * @param color: name of color which is used in @class of the color element, eg: hot_pink
	 * @param groups: name of group which is the same as data inputed by manual, eg: /platform/users
	 */
	public void addCalendar(String name, String desc, String color, String...groups){
		info("Add calendar");
		goToAddCalendar();
		inputAddCalendarForm(name,desc,color,groups);
		click(ELEMENT_CAL_ADD_SAVE_BUTTON);
		waitForAndGetElement(By.linkText(name));
	}

	/** Delete calendar
	 * @author thuntn
	 * @param name
	 * @param verify
	 */
	public void deleteCalendar(String name, boolean...verify){
		alert = new ManageAlert(driver); 
		boolean check = verify.length > 0 ? verify[0] : true;

		info("--Delete a Calendar-");

		executeActionCalendar(name,"RemoveCalendar");

		if (check){
			waitForElementNotPresent(ELEMENT_CALENDAR_GET_BY_TAG_LI.replace("{$calendar}", name));
			info("Remove calendar successfully");
		}
	}

	/** Edit a calendar
	 * @author thuntn
	 * @param oldName
	 * @param name
	 * @param desc
	 * @param color
	 * @param groups
	 */
	public void editCalendar(String oldName,String name, String desc, String color, String...groups){

		executeActionCalendar(oldName,"EditCalendar");
		inputAddCalendarForm(name,desc,color,groups);
		click(ELEMENT_CAL_ADD_SAVE_BUTTON);
		waitForAndGetElement(By.linkText(name));
	}

	/**Delete shared calendar
	 * @author thuntn
	 * @param calendar
	 */
	public void deleteSharedCalendar(String calendar){

		executeActionCalendar(calendar,"RemoveSharedCalendar");
		waitForElementNotPresent(By.linkText(calendar));
	}


	/*========== End of Add a Calendar ==========*/

	/**
	 * @author thuntn
	 * @param calendar
	 */
	public void openMenuOfCalendar(String calendar){
		WebElement element = waitForAndGetElement(ELEMENT_CALENDAR_SETTING_ICON.replace("${calendar}", calendar), DEFAULT_TIMEOUT, 0, 2);
		mouseOver(By.linkText(calendar),true);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
	}

	/**
	 * @author vuna2
	 * @param beginTime
	 * @param endTime
	 */
	public void showWorkingTimes(String beginTime, String endTime){
		info("Show Working Times");
		WebElement element = waitForAndGetElement(ELEMENT_SHOW_WORKING_TIME_CHECKBOX, 5000, 1, 2);
		if (!element.isSelected()){
			check(ELEMENT_SHOW_WORKING_TIME_CHECKBOX, 2);
			select(ELEMENT_BEGIN_TIME, beginTime);
			select(ELEMENT_END_TIME, endTime);	
		}else{
			info("[Show working times is already checked]");
		}
		Utils.pause(500);
	}

	/** Setting timezone for Calendar
	 * @author havtt
	 * @date 18-Oct-2013
	 */
	public void setTimezoneCal(String timezoneOpt){
		click(ELEMENT_CAL_SETTING_MENU);
		Utils.pause(3000);
		info("-- Select filter option of Timezone --");
		click(ELEMENT_CAL_SETTING_TIMEZONE_COMBOBOX);
		click(ELEMENT_CAL_SETTING_TIMEZONE_VALUE.replace("${timezoneOpt}", timezoneOpt));
		waitForAndGetElement(ELEMENT_CAL_SETTING_TIMEZONE_VALUE.replace("${timezoneOpt}", timezoneOpt));		 

	}

	/** Quick search in Calendar
	 * @author havtt
	 * @date 22-Oct-2013
	 */
	public void quickSearchCalendar(String keyword){
		info("----Type in quick search box----");
		waitForAndGetElement(ELEMENT_INPUT_QUICK_SEARCH);
		type(By.xpath(ELEMENT_INPUT_QUICK_SEARCH), keyword, true);
		info("----Send search request----");
		Utils.pause(3000);
		Utils.javaSimulateKeyPress(KeyEvent.VK_ENTER);
		info("----Confirm search result page displayed----");
		Utils.pause(3000);
		waitForAndGetElement(ELEMENT_BUTTON_CLOSE_QUICK_SEARCH_RESULT);
	}

	/** Advance search in Calendar
	 * @author havtt
	 * @date 22-Oct-2013
	 */
	public void advanceSearchCalendar(String keyword){
		info("----Open Advance Search window----");
		waitForAndGetElement(ELEMENT_BUTTON_OPEN_ADVANCE_SEARCH_FORM);
		click(ELEMENT_BUTTON_OPEN_ADVANCE_SEARCH_FORM);
		info("----Input keyword----");
		waitForAndGetElement(ELEMENT_INPUT_TEXT_ADVANCE_SEARCH);
		type(ELEMENT_INPUT_TEXT_ADVANCE_SEARCH,keyword,true);
		click(ELEMENT_BUTTON_SEARCH_ADVANCE_SEARCH);
		info("----Confirm search result displayed----");
		Utils.pause(3000);
		waitForAndGetElement(ELEMENT_BUTTON_CLOSE_QUICK_SEARCH_RESULT);
	}

	/** Go to Calendar Actions> Add Event Category
	 * @author havtt
	 * @date 22-Oct-2013
	 */
	public void addEventCategory(String categoryName){
		info("----Add new event category----");
		type(ELEMENT_ADD_EVENT_CATEGORY_INPUT,categoryName,true);
		click(ELEMENT_ADD_EVENT_CATEGORY_BUTTON_ADD);
		info("----Verify if event category is added in Category List or not----");
		waitForAndGetElement(ELEMENT_LIST_EVENT_CATEGORY.replace("${categoryName}", categoryName));
		click(ELEMENT_ADD_EVENT_CATEGORY_BUTTON_CLOSE);	 
	}

	/** Choose event category in Category combo box
	 * @author havtt
	 * @date 22-Oct-2013
	 */
	public void chooseEventCategoryOpt(String categoryName){
		info("----Verify if new category is displayed in Category option or not----");
		waitForAndGetElement(ELEMENT_EVENT_CATEGORY_FILTER);
		click(ELEMENT_EVENT_CATEGORY_COMBOBOX);
		info("----Choose a category option----");
		click(ELEMENT_EVENT_CATEGORY_COMBOBOX_OPTION.replace("${categoryName}", categoryName));

	}

	/** Delete Event Category
	 * @author havtt
	 * @date 23-Oct-2013
	 */
	public void deleteEventCategory(String categoryName){
		alert = new ManageAlert(driver);
		waitForAndGetElement(ELEMENT_LIST_DELETE_EVENT_BUTTON.replace("${categoryName}",categoryName));
		click(ELEMENT_LIST_DELETE_EVENT_BUTTON.replace("${categoryName}",categoryName));
		Utils.pause(3000);
		alert.acceptAlert();
		waitForElementNotPresent(ELEMENT_LIST_DELETE_EVENT_BUTTON.replace("${categoryName}",categoryName));
		click(ELEMENT_ADD_EVENT_CATEGORY_BUTTON_CLOSE);	
	}

	/** Edit Event Category
	 * @author havtt
	 * @date 23-Oct-2013
	 */

	public void editEventCategory(String categoryName, String editedCategoryName){
		waitForAndGetElement(ELEMENT_LIST_EDIT_EVENT_BUTTON.replace("${categoryName}",categoryName));
		click(ELEMENT_LIST_EDIT_EVENT_BUTTON.replace("${categoryName}",categoryName));
		type(ELEMENT_ADD_EVENT_CATEGORY_INPUT,editedCategoryName,true);
		click(ELEMENT_EDIT_EVENT_CATEGORY_BUTTON_UPDATE);
		Utils.pause(3000);
		waitForAndGetElement(ELEMENT_LIST_EDIT_EVENT_BUTTON.replace("${categoryName}",editedCategoryName));
		click(ELEMENT_ADD_EVENT_CATEGORY_BUTTON_CLOSE);
	}

	/** Go to Add Event Category
	 * @author havtt
	 * @date 23-Oct-2013
	 */
	public void gotoAddEventCategory(){
		info("----Go to Calendar Action----");
		waitForAndGetElement(ELEMENT_CALENDAR_ACTIONS_ICON);
		click(ELEMENT_CALENDAR_ACTIONS_ICON);
		info("----GO to Add Event Category form----");
		waitForAndGetElement(ELEMENT_ADD_EVENT_CATEGORY_ICON);
		click(ELEMENT_ADD_EVENT_CATEGORY_ICON);
		Utils.pause(3000);
	}	 

}