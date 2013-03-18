package org.exoplatform.selenium.platform.ecms.wcm;

import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Viewer extends EcmsBase{

	public Viewer(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param anchor: class of element inside a CLV
	 * @param icon : Action icon of CLV, for example 
	 */
	public void doCLVEditingToolAction(String anchor, String icon) {

		mouseOver(By.xpath("//a[@class='" + anchor + "']"), true);
		By ELEMENT_ID_OF_SCV =
				By.xpath("//*[@id='UIPage']/div/div/div/div/div/table/tbody/tr/td[2]/div/div/div/div/div/div/div/div/div[contains(@id, 'UICLVFolderMode')]");
		String ELEMENT_ID_OF_SCV_TEST =
				"//*[@id='UIPage']/div/div/div/div/div/table/tbody/tr/td[2]/div/div/div/div/div/div/div/div/div[@id='${idSCV}']/div[1]/div/div/div/div//a[@class='" + icon + "']";
		WebElement elementIDSCV = waitForAndGetElement(ELEMENT_ID_OF_SCV);

		String idSCV = elementIDSCV.getAttribute("id");

		mouseOverAndClick(ELEMENT_ID_OF_SCV_TEST.replace("${idSCV}", idSCV));
	}

	public void editTitleInline(String title, String newTitle){
		String currentTitle = "//div[contains(text(),'" + title + "') and @title='Double-click to edit']";
		By inputTitle = By.xpath(currentTitle + "/following::div[@class='EditGroovyCLVTitleInput']/input[contains(@id,'newCLVTitle')]");
		By acceptButton = By.xpath(currentTitle + "/following::a[@class='AcceptButton']");
		doubleClickOnElement(currentTitle);
		type(inputTitle,newTitle, true);
		click(acceptButton);
		waitForElementPresent(By.xpath("//div[contains(text(),'" + newTitle + "') and @title='Double-click to edit']"));
	}

}
