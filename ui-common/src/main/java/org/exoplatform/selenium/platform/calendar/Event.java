package org.exoplatform.selenium.platform.calendar;

import static org.exoplatform.selenium.TestLogger.info;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;

/**
 * @Date 10 Oct 2013
 * @author thuntn
 *
 */
public class Event extends CalendarBase{

	public By ELEMENT_INPUT_EVENT_TITLE = By.id("eventName");
	public By ELEMENT_INPUT_EVENT_DESCRIPTION = By.id("description");
	public By ELEMENT_CHECKBOX_EVENT_ALLDAY = By.xpath("//form[@id='UIQuickAddEvent']//input[@id='allDay']");
	public By ELEMENT_INPUT_EVENT_FROM = By.name("from");
	public By ELEMENT_INPUT_EVENT_TO = By.name("to");
	public By ELEMENT_INPUT_EVENT_FROM_TIME = By.xpath("//*[@id='fromTime']/../input");
	public By ELEMENT_INPUT_EVENT_TO_TIME = By.xpath("//*[@id='toTime']/../input");
	public By ELEMENT_INPUT_EVENT_CALENDAR = By.name("calendar");
	public By ELEMENT_INPUT_EVENT_CATEGORY = By.name("category");
	public By ELEMENT_BUTTON_EVENT = By.id("UIActionBarQuickAddEvent");
	public String ELEMENT_EVENT_CATEGORY_OPT_COMBOBOX = "//*[@id='UIQuickAddEvent']//div[contains(text(),'Event Category:')]/parent::div[@class='control-group']//span[@class='uiSelectbox']/select[@name='category']/option[contains(text(),'${categoryName}')]";

	public Event(WebDriver dr){
		driver = dr;
		but = new Button(driver);
		alert = new ManageAlert(driver);
	}

	/** Input into Tab detail of Add event form
	 * @author thuntn
	 * @param name
	 * @param desc
	 * @param from
	 * @param to
	 * @param allDay
	 * @param opt
	 */
	public void inputQuickAddEventForm(String name, String desc, String from, String to, boolean allDay, String...opt){
		if (name != null){
			type(ELEMENT_INPUT_EVENT_TITLE, name, true);
		}
		if (desc != null){
			type(ELEMENT_INPUT_EVENT_DESCRIPTION, desc, true);
		}
		if (allDay){
			check(ELEMENT_CHECKBOX_EVENT_ALLDAY,2);
		} else {
			uncheck(ELEMENT_CHECKBOX_EVENT_ALLDAY,2);
		}
		if (from != null){
			if (from != ""){
				if (allDay){
					type(ELEMENT_INPUT_EVENT_FROM, from, true);
				}else {
					String[] dateTime = from.split(" ");
					type(ELEMENT_INPUT_EVENT_FROM, dateTime[0], true);
					type(ELEMENT_INPUT_EVENT_FROM_TIME, dateTime[1], true);
				}
			}
		}
		if (to != null){
			if (to != ""){
				if (allDay){
					type(ELEMENT_INPUT_EVENT_TO, to, true);
				}else{
					String[] dateTime = to.split(" ");
					type(ELEMENT_INPUT_EVENT_TO, dateTime[0], true);
					type(ELEMENT_INPUT_EVENT_TO_TIME, dateTime[1], true);

				}
			}
		}
		if (opt.length > 0 && opt[0] != null){
			select(ELEMENT_INPUT_EVENT_CALENDAR, opt[0]);
		}
		if (opt.length > 1 && opt[1] != null){
			select(ELEMENT_INPUT_EVENT_CATEGORY, opt[1]);
		}
	}

	/**
	 * Open form Add event
	 * @author thuntn
	 */
	public void goToAddEvent(){
		info("Go to Add Event page");
		click(ELEMENT_BUTTON_EVENT);
	}

	/** Quick add event
	 * @author thuntn
	 * @param name
	 * @param desc
	 * @param from
	 * @param to
	 * @param allDay
	 * @param opt
	 */
	public void addEvent(String name, String desc, String from, String to, boolean allDay, String...opt){
		info("--Add an event--");
		goToAddEvent();
		inputQuickAddEventForm(name,desc,from,to,allDay,opt);
		but.save();
	}


	/**Choose category opt for Add event
	 * @author havtt
	 * @date 23-Oct-2013
	 */

	public void chooseCategorywhenAddEvent(String categoryName){
		info("----Choose category opt----");
		waitForAndGetElement(ELEMENT_EVENT_CATEGORY_OPT_COMBOBOX.replace("${categoryName}", categoryName));
		click(ELEMENT_EVENT_CATEGORY_OPT_COMBOBOX.replace("${categoryName}", categoryName));
	}

}

