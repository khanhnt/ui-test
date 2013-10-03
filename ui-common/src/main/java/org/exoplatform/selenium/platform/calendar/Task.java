package org.exoplatform.selenium.platform.calendar;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @Date 10 Oct 2013
 * @author thuntn
 *
 */
public class Task extends CalendarBase{


	//------------------------Task Menu-------------------------------------------------------
	public String ELEMENT_CURRENT_TASK = "//*[@id='UIWeekViewGrid']//div[contains(@deschtml, '${taskName}')]";
	public String ELEMENT_ALL_DAY_TASK = "//*[@id='UIWeekViewGridAllDay']/div[@class='eventAlldayBoard']//div[contains(text(),'${taskTitle}')]";
	public String ELEMENT_TASK_MENU_DELETE = "//*[@id='tmpMenuElement']//i[@class='uiIconDelete uiIconLightGray']";
	public String ELEMENT_TASK_MENU_EDIT = "//*[@id='tmpMenuElement']//i[@class='uiIconEdit uiIconLightGray']";
	public By ELEMENT_INPUT_TASK_TITLE_EDIT = By.xpath("//*[@id='eventDetail']//*[@id='eventName']");
	public By ELEMENT_INPUT_TASK_NOTE_EDIT = By.xpath("//*[@id='eventDetail']//*[@id='description']");
	public By ELEMENT_BUTTON_TASK_SAVE_EDIT = By.xpath("//*[@id='UITaskForm']//*[text()='Save']");

	public By ELEMENT_INPUT_TASK_TITLE = By.xpath("//*[@id='UIQuickAddTask']//*[@name='eventName']");
	public By ELEMENT_INPUT_TASK_NOTE = By.xpath("//*[@id='UIQuickAddTask']//*[@id='description']");
	public By ELEMENT_CHECKBOX_TASK_ALLDAY = By.xpath("//*[@id='UIQuickAddTask']//*[@name='allDay']");
	public By ELEMENT_INPUT_TASK_FROM = By.xpath("//*[@id='UIQuickAddTask']//*[@name='from']");
	public By ELEMENT_INPUT_TASK_TO = By.xpath("//*[@id='UIQuickAddTask']//*[@name='to']");
	public By ELEMENT_INPUT_TASK_FROM_TIME = By.xpath("//input[@id='fromTime' and @style='visibility: visible;']");
	public By ELEMENT_INPUT_TASK_TO_TIME = By.xpath("//input[@id='fromTime' and @style='visibility: visible;']");
	public By ELEMENT_INPUT_TASK_CALENDAR = By.xpath("//*[@id='UIQuickAddTask']//*[@name='calendar']");
	public By ELEMENT_INPUT_TASK_CATEGORY = By.xpath("//*[@id='UIQuickAddTask']//*[@name='category']");
	public By ELEMENT_BUTTON_TASK = By.id("UIActionBarQuickAddTask");
	public By ELEMENT_BUTTON_TASK_SAVE = By.xpath("//*[@id='UIQuickAddTaskPopupWindow']//*[text()='Save']");
	public By ELEMENT_BUTTON_TASK_MORE_DETAILS = By.xpath("//*[@id='QuickAddEventContainer']//*[text()='More Details']");


	//----------------------- Task Reminder ---------------------------------------------
	public By ELEMENT_TAB_REMINDER = By.xpath("//*[@id='UIPopupAddTaskContainer']//a[text()='Reminders']");
	public By ELEMENT_CHECKBOX_POPUP_REMINDER = By.id("popupReminder");
	public By ELEMENT_CHECKBOX_EMAIL_REMINDER = By.id("mailReminder");
	public By ELEMENT_CHECKBOX_EMAIL_REMINDER_REPEAT = By.id("emailIsRepeat");
	public By ELEMENT_CHECKBOX_POPUP_REMINDER_REPEAT = By.id("popupIsRepeat");
	public By ELEMENT_BUTTON_REMINDER_ADD_MORE_EMAIL = By.xpath("//*[@id='eventReminder']//i[@class='uiIconPlus uiIconLightGray']");

	//----------------------------------DateTime----------------------------------------
	public int HAFL_HOUR = 30; //minutes
	public int FULL_HOUR = 60; //minutes

	public Task(WebDriver dr){
		driver = dr;
		but = new Button(driver);
	}

	/** Input into tab Detail of Add task form
	 * @author thuntn
	 * @param name
	 * @param note
	 * @param from
	 * @param to
	 * @param allDay
	 * @param opt
	 */
	public void inputDataQuickTask(String name, String note, String from, String to, boolean allDay,  String...opt){
		if (name != null){
			type(ELEMENT_INPUT_TASK_TITLE, name, true);
		}
		if (note != null){
			type(ELEMENT_INPUT_TASK_NOTE, note, true);
		}
		if (allDay){
			check(ELEMENT_CHECKBOX_TASK_ALLDAY,2);
		} else if (!allDay){
			uncheck(ELEMENT_CHECKBOX_TASK_ALLDAY,2);
		}
		if (from != null){
			if (from != ""){
				if (allDay){
					type(ELEMENT_INPUT_TASK_FROM, from, true);
				}else {
					String[] dateTime = from.split(" ");
					if(dateTime.length > 0)
						type(ELEMENT_INPUT_TASK_FROM, dateTime[0], true);
					if(dateTime.length > 1)
						type(ELEMENT_INPUT_TASK_FROM_TIME, dateTime[1], false);
				}
			}
		}
		if (to != null){
			if (to != ""){
				if (allDay){
					type(ELEMENT_INPUT_TASK_TO, to, true);
				}else{
					String[] dateTime = to.split(" ");
					type(ELEMENT_INPUT_TASK_TO, dateTime[0], true);
					if(dateTime.length > 1)
						type(ELEMENT_INPUT_TASK_TO_TIME, dateTime[1], false);
				}
			}
		}
		if (opt.length > 0 && opt[0] != null){
			select(ELEMENT_INPUT_TASK_CALENDAR, opt[0]);
		}
		if (opt.length > 1 && opt[1] != null){
			select(ELEMENT_INPUT_TASK_CATEGORY, opt[1]);
		}
	}

	/** Open form Add a task
	 * @author thuntn
	 */
	public void goToAddTask(){
		info("Go to Add Task page"); 
		click(ELEMENT_BUTTON_TASK);
	}

	/** Quick add task
	 * @author thuntn
	 * @param name
	 * @param note
	 * @param from
	 * @param to
	 * @param allDay
	 * @param opt
	 */
	public void addTask(String name, String note, String from, String to, boolean allDay, String...opt){
		goToAddTask();
		inputDataQuickTask(name,note,from,to,allDay,opt);
		click(ELEMENT_BUTTON_TASK_SAVE);
	}

	/**Setting Pop-up reminder for task on Quick Add Task form
	 * @author havtt
	 */
	public void gotoSetPopupReminder() {

		click(ELEMENT_BUTTON_TASK_MORE_DETAILS);
		Utils.pause(3000);
		click(ELEMENT_TAB_REMINDER);
		Utils.pause(3000);
		click(ELEMENT_CHECKBOX_POPUP_REMINDER);
	}

	/**Setting E-mail reminder for task on Quick Add Task form
	 * @author havtt
	 */
	public void gotoSetEmailReminder() {
		click(ELEMENT_BUTTON_TASK_MORE_DETAILS);
		Utils.pause(5000);
		click(ELEMENT_TAB_REMINDER);
	}

	/**Edit a task by right click
	 * 
	 * @author havtt
	 * 
	 * @param taskName    the title of task before edit
	 * @param title       the title of task after edit
	 * @param description the description of task after edit
	 * 
	 */
	public void editTask(String taskName, String title, String description) {
		rightClickOnElement(By.xpath(ELEMENT_ALL_DAY_TASK.replace("${taskTitle}",taskName)), DEFAULT_TIMEOUT, 1);
		Utils.pause(3000);
		click(ELEMENT_TASK_MENU_EDIT);
		Utils.pause(3000);
		type(ELEMENT_INPUT_TASK_TITLE_EDIT, title, true);
		type(ELEMENT_INPUT_TASK_NOTE_EDIT, description, true);
		click(ELEMENT_BUTTON_TASK_SAVE_EDIT);
		waitForAndGetElement(By.xpath(ELEMENT_ALL_DAY_TASK.replace("${taskTitle}",title)));
	}

	/**Set time for Pop-up reminder on current date
	 * 
	 * @author havtt
	 * 
	 * @param fromTime Start time of a task
	 */
	public void setTimePopupReminder() {
		int CURRENT_MIN = getMinute();
		if (CURRENT_MIN == HAFL_HOUR) {
			System.out.print("Set time for Pop-up reminder");	
			//TO-DO: need to fix the issue FQA-1352
		}		
	}

}