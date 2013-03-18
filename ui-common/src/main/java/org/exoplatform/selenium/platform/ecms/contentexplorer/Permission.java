package org.exoplatform.selenium.platform.ecms.contentexplorer;


import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author vuna2
 *
 */
public class Permission extends EcmsBase{

	public Permission(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	NavigationToolbar navToolBar = new NavigationToolbar(driver);
	Dialog dialog = new Dialog(driver);
	Button button = new Button(driver);
	ManageAlert magAlert = new ManageAlert(driver);
		
}
