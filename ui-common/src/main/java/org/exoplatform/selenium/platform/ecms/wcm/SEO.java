package org.exoplatform.selenium.platform.ecms.wcm;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author vuna2
 *
 */
public class SEO extends EcmsBase{

	public SEO(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	Button button = new Button(driver);
	
	//-------SEO Management Form--------------------
	public By ELEMENT_SEO_FORM = By.id("UISEOPopupWindow");
	public By ELEMENT_DESCRIPTION_TEXTAREA = By.id("description");
	public By ELEMENT_KEYWORD_TEXTAREA = By.id("keywords");
	public By ELEMENT_ROBOT_INDEX_COMBOX = By.id("index");
	public By ELEMENT_ROBOT_FOLLOW_COMBOX = By.id("follow");
	public By ELEMENT_SITE_MAP_CHECKBOX = By.id("sitemap");
	public By ELEMENT_FREQUENCY_COMBOX = By.id("frequency");
	public By ELEMENT_PRIORITY = By.id("priority");

	//-----------SEO management------------------------------------
	/* Function to input data on SEO management Form
	 * @des: data input to description
	 * @key: data input to keyword
	 * @index: option of checkbox robot index
	 * @follow: option of checkbox robot follow
	 * @sitemap: = true: tick, = false: no tick
	 * @fre: option of checkbox frequency
	 * @pri: data input to priority
	 */
	public void inputDataSeo(String des, String key, String index, String follow, boolean sitemap, String fre, String pri){

		waitForElementPresent(ELEMENT_SEO_FORM);
		waitForAndGetElement(ELEMENT_DESCRIPTION_TEXTAREA).clear();
		waitForAndGetElement(ELEMENT_KEYWORD_TEXTAREA).clear();
		waitForAndGetElement(ELEMENT_PRIORITY).clear();
		type(ELEMENT_KEYWORD_TEXTAREA, key, true);
		info("Input data on SEO management");
		if (des != ""){
			type(ELEMENT_DESCRIPTION_TEXTAREA, des, true);
		}
		if (key != ""){
			type(ELEMENT_KEYWORD_TEXTAREA, key, true);
		}
		select(ELEMENT_ROBOT_INDEX_COMBOX, index);
		select(ELEMENT_ROBOT_FOLLOW_COMBOX, follow);
		if ((sitemap && waitForAndGetElement(ELEMENT_SITE_MAP_CHECKBOX).isSelected() == false) || (sitemap == false && waitForAndGetElement(ELEMENT_SITE_MAP_CHECKBOX).isSelected())){
			click(ELEMENT_SITE_MAP_CHECKBOX);
		}
		select(ELEMENT_FREQUENCY_COMBOX, fre);
		if (pri != ""){
			type(ELEMENT_PRIORITY, pri, true);
		}
		click(button.ELEMENT_SAVE_BUTTON);
		info("finish input data on SEO management");
	}
}
