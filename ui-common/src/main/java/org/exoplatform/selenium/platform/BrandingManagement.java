package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * 
 * @author phuongdt
 * @date 21/10/2013
 *
 */
public class BrandingManagement extends PlatformBase {
	public final By ELEMENT_PREVIEW_LOGO_ID = By.id("PreviewImg");
	public final By ELEMENT_UPLOAD_BUTTON = By.id("btUpload");
	public final By ELEMENT_NAVIGATION_STYLE = By.id("navigationStyle");
	public final By ELEMENT_TABLE_COLUMN_CONTAINER = By.className("UITableColumnContainer");
	public final String ELEMENT_NAVIGATION_STYLE_CURRENT = "//div[@class='btn dropdown-toggle']/span[contains(text(),'${optionStyle}')]";
	public final String ELEMENT_ACTIVITY_NAVIGATION_STYLE_OPTION = "//a[@class='OptionItem' and contains(text(),'${optionStyle}')]";
	public final By ELEMENT_ACTIVITY_DROPDOWN = By.xpath("//div[@class='btn dropdown-toggle']");
	public String ELEMENT_COLOR_CONTAINER_PREVIEW = "//div[@id='StylePreview']//div[@id='UIToolbarContainer' and contains(@class,'${color}')]";
	public String ELEMENT_COLOR_CONTAINER_TOOLBAR = "//div[contains(@class,'UIRowContainer')]/div[@id='PlatformAdminToolbarContainer']//div[@id='UIToolbarContainer' and contains(@class,'${color}')]";
	public final By ELEMENT_LOGO_CONTAINER_PREVIEW = By.xpath("//div[@id='StylePreview']//a[@class='HomeLink']/img[@alt='Home']");
	public final By ELEMENT_LOGO_CONTAINER_TOOLBAR = By.xpath("//div[contains(@class,'UIRowContainer')]/div[@id='PlatformAdminToolbarContainer']//a[@class='HomeLink']/img[@alt='Home']");
	
	public BrandingManagement(WebDriver dr) {
		driver = dr;
		but = new Button(driver);
	}
	
	/** 
	 * Upload a new logo
	 * @author phuongdt
	 * @param urlFile
	 */
	public void uploadLogo(String urlFile){
		info("-- Upload new logo--");
		WebElement preElement = waitForAndGetElement(ELEMENT_LOGO_CONTAINER_PREVIEW);
		WebElement element = waitForAndGetElement(ELEMENT_LOGO_CONTAINER_TOOLBAR); 
		String oldsrc = preElement.getAttribute("src"); 
		String oldsrc1 = element.getAttribute("src"); 
		WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_IMG_ID, DEFAULT_TIMEOUT, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; " +
				"arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", upload);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible'", upload);
		upload.sendKeys(Utils.getAbsoluteFilePath("TestData/"+urlFile));
		preElement = waitForAndGetElement(ELEMENT_LOGO_CONTAINER_PREVIEW);
		String newsrc = preElement.getAttribute("src"); 
		info("-- Verify preview logo --");
		assert (!oldsrc.contentEquals(newsrc));
		but.save();
		element = waitForAndGetElement(ELEMENT_LOGO_CONTAINER_TOOLBAR);
		String newsrc1 = element.getAttribute("src"); 
		info("-- Verify Navigation Bar logo --");
		assert (!oldsrc1.contentEquals(newsrc1));
	}
	
	/** 
	 * Select new stype navigation bar
	 * @author phuongdt
	 * @param optionStyle
	 */
	public void selectNavigationBarStyle(String optionStyle){
		info("-- Select Navigation Bar Style --");
		if(waitForAndGetElement(ELEMENT_NAVIGATION_STYLE_CURRENT.replace("${optionStyle}", optionStyle), DEFAULT_TIMEOUT, 0)==null){
			click(ELEMENT_ACTIVITY_DROPDOWN,2);
			click(ELEMENT_ACTIVITY_NAVIGATION_STYLE_OPTION.replace("${optionStyle}", optionStyle));
			waitForAndGetElement(ELEMENT_NAVIGATION_STYLE_CURRENT.replace("${optionStyle}", optionStyle));
			info("-- Verify Preview Navigation Bar Style --");
			waitForAndGetElement(ELEMENT_COLOR_CONTAINER_PREVIEW.replace("${color}", optionStyle));
		}
		but.save();
		info("-- Verify Navigation Bar --");
		waitForAndGetElement(ELEMENT_COLOR_CONTAINER_TOOLBAR.replace("${color}", optionStyle));
	}
}
