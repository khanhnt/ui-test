package org.exoplatform.selenium.platform;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageEditor extends PlatformBase {

	public PageEditor (WebDriver dr){
		driver = dr;
	}

	NavigationToolbar nav = new NavigationToolbar(driver);
	UserGroupManagement userGroup = new UserGroupManagement(driver);
	Dialog dialog = new Dialog(driver);
	Button button = new Button(driver);
	ManageAlert magAlert;

	/** 
		Page Creation Wizard: Select a Navigation Node and create the Page 
	 **/
	public final String PORTAL_MANAGEMENT_LINK = "//a[@title='Portal Administration']";
	public final String APPLICATION_MANAGER_LINK = "//a[@title='Application Manager']";
	public final String PAGE_MANAGER_LINK = "//a[@title='Page Manager']";
	public final String ADD_USERS_LINK = "//a[@title='Add Users']";
	public final String USERS_GROUP_MANAGER_LINK = "//a[@title='User and Group Manager']";
	public final String UP_LEVEL_ICON = "//a[@title='Up Level']";
	public final String DEFAULT_NODE = "//div[contains(text(),'/default')]";
	public final String NODE_NAME_INPUT = "//input[@id='pageName']";

	/* Page Editor - View Page Properties*/
	//View Page Properties form -> Page Setting tab
	public final By ELEMENT_VIEW_PAGE_PROPERTIES = By.linkText("View Page properties");
	public final String ELEMENT_OWNERTYPE_SELECTED = "//*[@id='PageSetting-tab']//select[@name='ownerType']/option[@selected = 'selected' and text()='${ownerType}']";

	//View Page Properties form (there are 2 tabs in this form)
	//Page Setting Tab
	public final By ELEMENT_VIEWPAGE_PAGETITLE = By.id("title");
	//Permisstion setting tab
	//View Page Properties form End

	/*-- Site Editor/Edit Page/Edit Mode 
	 *-- Select Content Path/Content Search Form Tab  
	 * --*/
	public final By ELEMENT_SEARCH_BUTTON = By.xpath("//a[text()='Search']");
	public final By ELEMENT_CLOSE_WINDOWS_BUTTON = By.xpath("//a[@class='CloseButton']"); 
	public final By ELEMENT_CONFIRM_YES_BUTTON = By.xpath("//a[contains(text(), 'Yes')]");
	public final By ELEMENT_RADIO_MODE_CONTENT = By.xpath("//*[@class='uiRadio']//*[text()='By Content']"); 
	//By.id("UICLVConfigDisplayModeFormRadioBoxInput_ManualViewerMode"); 
	public final By ELEMENT_RADIO_MODE_FOLDER = By.xpath("//*[@class='uiRadio']//*[text()='By Folder']");
	//By.id("UICLVConfigDisplayModeFormRadioBoxInput_AutoViewerMode");
	public final By ELEMENT_ADDWIZARD_TEXT2 = By.xpath("//*[@class='stepTitle'  and contains(text(),'Select a Page Layout Template')]");

	//Edit [content list] portlet 
	public final By ELEMENT_EDITPAGE_CONTENT_DELETE = By.xpath("//div[@class='DeleteIcon']");
	public final By ELEMENT_TAB_SEARCH_RESULT=By.xpath("//div[@class='SelectedTab']/div/div/div[contains(text(),'Search Result')]");
	public final By ELEMENT_CLOSE_POPUP_BUTTON=By.xpath("//a[@title='Close Window']");
	public final By ELEMENT_SEARCH_FORM_CONTENT = By.xpath("//input[@name='WcmRadio' and @id='content']");
	public final By ELEMENT_INPUT_NAME_SEARCH_FORM_EDIT_MODE = By.xpath("//input[@id='name' and @type='text']");
	public final By ELEMENT_CHECK_BOX_WORD_PHRASE_EDIT_MODE = By.xpath("//input[@id='content' and @type='radio']");
	public final By ELEMENT_INPUT_NAME_SEARCH_WORD_PHRASE_EDIT_MODE = By.xpath("//input[@id='content' and @type='text']");
	public final By ELEMENT_CONTENT_SEARCH_FORM_TAB = By.xpath("//div[@class='MiddleTab' and text() = 'Content Search Form']");

	//Add Path > Right Workspace 
	public final String ELEMENT_RIGHT_WORKSPACE_NODE = "//*[@class='rightWorkspace']//*[text()='${node}']";

	//public final By ELEMENT_ROW_EMPTY_CONTAINER = By.xpath("//*[contains(@class, 'UIRowContainer EmptyContainer')]");

	/*===================== Common Function =======================*/

	//Create page wizard without layout
	public void goToPageEditor_EmptyLayout(String pageName){
		nav = new NavigationToolbar(driver);
		nav.goToPageCreationWizard();
		type(ELEMENT_NEWPAGE_NAME_TEXTBOX, pageName, false);
		click(button.ELEMENT_NEXT_BUTTON);
		waitForAndGetElement(ELEMENT_ADDWIZARD_TEXT2);
		click(button.ELEMENT_NEXT_BUTTON);
	}
	
	public void createNewPageEmptyLayoutWithGadget(String pageName, String gadget){
		info("Create new page winzard empty layout and drags and drop gadget");
		goToPageEditor_EmptyLayout(pageName);
		dragAndDropToObject(By.xpath(ELEMENT_GADGET_APPLICATION_PAGE_EDITOR.replace("${gadget}", gadget)), ELEMENT_DROP_TARGET_NO_LAYOUT);
		finishEditLayout();
	}

	//Create new page without content 
	public void createNewPageEmptyLayout(String pageName){	
		info("Create new page winzard empty layout");
		goToPageEditor_EmptyLayout(pageName);
		finishEditLayout();
	}

	//create new page having layout - step 1,2
	public void gotoPageEditorAndSelectLayout(String pageName, int numberLayout){
		nav = new NavigationToolbar(driver);
		nav.goToPageCreationWizard();		
		type(ELEMENT_NEWPAGE_NAME_TEXTBOX, pageName, false);
		click(button.ELEMENT_NEXT_BUTTON);
		click(ELEMENT_NEWPAGE_LAYOUT_OPTION);
		Utils.pause(500);
		switch (numberLayout){
		case 1: click(ELEMENT_NEWPAGE_LAYOUT_COLUMN_PAGE_OPTION);
		break;
		case 2: click(ELEMENT_NEWPAGE_LAYOUT_ROW_PAGE_OPTION);
		break;
		case 3: click(ELEMENT_NEWPAGE_LAYOUT_TAB_PAGE_OPTION);
		break;
		case 4: click(ELEMENT_NEWPAGE_LAYOUT_MIX_PAGE_OPTION);
		break;		
		default: click(ELEMENT_NEWPAGE_LAYOUT_DEFAULT_OPTION);
		break;
		}
		click(button.ELEMENT_NEXT_BUTTON);
	}

	//Create new page having layout 
	public void createNewPageWithLayout(String pageName, int numberLayout){
		info("Create new page winzard having layout");
		gotoPageEditorAndSelectLayout(pageName, numberLayout);
		finishEditLayout();
	}


	//Create empty layout SCV (Single Content Viewer) with content
	//	public void createPage_EmptyLayout_ContentDetail_ContentPath(String pageName, String contentPath){
	//		goToPageEditor_EmptyLayout(pageName);
	//		Utils.pause(500);
	//		addContentDetailEmptyLayout();
	//		Utils.pause(500);
	//		selectContentPath(contentPath);
	//		Utils.pause(500);
	//		click(ELEMENT_NEWPAGE_SAVE_BUTTON);			
	//	}

	//Create new CLV with layout and content
	public void addCLVPageAndCLVpath(String pageName, String path, String clv, Object...params){
		String modeContent = (String) (params.length > 0 ? params[0]: "");

		gotoPageEditorAndSelectLayout(pageName, 1);
		Utils.pause(500);
		addContentList();
		Utils.pause(500);
		selectCLVPath(path, clv, modeContent);
		Utils.pause(500);
		//click(ELEMENT_NEWPAGE_SAVE_BUTTON);
		finishEditLayout();
	}

	//Add content detail to an empty layout page
	public void addContentDetailEmptyLayout(){
		click(ELEMENT_CONTENT_GROUP_PORTLET);
		dragAndDropToObject(ELEMENT_ADD_CONTENT_DETAIL_PORTLET, ELEMENT_DROP_TARGET_NO_LAYOUT);	
	}

	//Add "ContentDetail" to page with selected layout
	public void addContentDetail(){
		click(ELEMENT_CONTENT_GROUP_PORTLET);
		dragAndDropToObject(ELEMENT_ADD_CONTENT_DETAIL_PORTLET,ELEMENT_DROP_TARGET_HAS_LAYOUT);		
	}
	/** function to add content path for content detail portlet
	 * @author lientm
	 * @param path
	 */
	public void addContentPathForContentDetailPortlet(String path){
		mouseOver(ELEMENT_CONTENT_DETAIL_IN_LAYOUT, true);
		click(ELEMENT_CONTENT_DETAIL_EDIT_ICON);
		click(ELEMENT_SELECT_CONTENT_PATH_LINK);
		if (path != ""){
			String[] paths = path.split("/");
			for (int i = 0; i < paths.length; i ++){
				click("//a[@data-original-title='" + paths[i] + "']");
				Utils.pause(1000);
			}
		}
		click(button.ELEMENT_SAVE_BUTTON);
		click(button.ELEMENT_CLOSE_BUTTON);
		Utils.pause(1000);
	}

	//Add "ContentList" to EmptyLayout page
	public void addContentListEmptyLayout(){
		click(ELEMENT_CONTENT_GROUP_PORTLET);
		dragAndDropToObject(ELEMENT_ADD_CONTENT_LIST_PORTLET, ELEMENT_DROP_TARGET_NO_LAYOUT);
	}

	//Add "ContentList" to page with selected layout
	public void addContentList(){
		click(ELEMENT_CONTENT_GROUP_PORTLET);
		dragAndDropToObject(ELEMENT_ADD_CONTENT_LIST_PORTLET, ELEMENT_DROP_TARGET_HAS_LAYOUT);		
	}

	//Open [Add Content Path]
	public void openAddContentPathForm(){
		Utils.pause(500);
		mouseOver(ELEMENT_FRAME_CONTAIN_PORTLET,true);	
		click(ELEMENT_EDIT_PORTLET_ICON);
		click(ELEMENT_SELECT_CONTENT_PATH_LINK);
		Utils.pause(500);
	}

	//Select "ContentPath" in edit portlet
	public void selectContentPath(String pathContent){
		userGroup = new UserGroupManagement(driver);
		openAddContentPathForm();
		userGroup.selectGroup(pathContent);
		click(button.ELEMENT_SAVE_BUTTON);
		click(button.ELEMENT_CLOSE_BUTTON);
	}

	/*Select "CLVPath" in Edit Mode
	 * @mode:  content: if select mode "By content"
	 * 		   other value: if select mode "By folder"	
	 */
	public void selectCLVPath(String path, String clv, String...mode){
		userGroup = new UserGroupManagement(driver);
		By ELEMENT_SELECT_CLV_PATH = By.xpath("//td/a[text()='" + clv + "']");
		goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		if (mode.length >0){ 
			if (mode[0] == "content")
				click(ELEMENT_RADIO_MODE_CONTENT);
			else 
				click(ELEMENT_RADIO_MODE_FOLDER);
		}

		click(ELEMENT_SELECT_CONTENT_PATH_LINK);
		String[] node = path.split("/");
		for (int i = 0; i < node.length; i ++){
			click(By.xpath("//*[@data-original-title='" + node [i] + "']"));
		}
		click(ELEMENT_SELECT_CLV_PATH);
		if (mode.length >0){ 
			if (mode[0] == "content"){
				click(button.ELEMENT_SAVE_BUTTON);
				waitForElementNotPresent(ELEMENT_SELECT_CLV_PATH);
			}		
		}
		click(button.ELEMENT_SAVE_BUTTON);
		click(button.ELEMENT_CLOSE_BUTTON);
	}

	/*-- Add common functions for Single Content Viewer/Add SCV
	 *-- Page Editor 
	 *-- Add a new SCV page and select a content path 
	 *-- @author: VuNA
	 *-- @date: 23/10/2012
	 *--*/

	//Add a new SCV page and add a selected content path to this page
	//Use a default page with Empty layout 
	public void addSCVPageAndContentFolderPaths(String pageName, String contentPath, Object...params){
		boolean rightClickNode = (Boolean) (params.length > 0 ? params[0] : false);
		info("-- Add a content path to SCV page: "+ pageName +" --");
		goToPageEditor_EmptyLayout(pageName);
		//Drag and drop Content Detail portlet into this page
		addContentDetailEmptyLayout();
		//click(ELEMENT_NEWPAGE_SAVE_BUTTON);
		//waitForElementNotPresent(ELEMENT_NEWPAGE_SAVE_BUTTON);
		//nav.goToEditPageEditor();

		info("-- Select Content Path --");
		selectContentPathInEditMode(contentPath, rightClickNode);
		//click(ELEMENT_NEWPAGE_SAVE_BUTTON);
		finishEditLayout();
	}

	//Select a content path to add to SCV page
	//Use as default: boolean inEditModeWindows = false
	public void selectContentPathInEditMode(String contentPath, boolean inEditModeWindows, Object...params){
		button = new Button(driver);
		Boolean contentMode = (Boolean) (params.length > 0 ? params[0]: false) ;
		Boolean nClose = (Boolean) (params.length > 1 ? params[1]: true) ;

		info("-- Select the content path: "+ contentPath +"--");
		String ELEMENT_SELECT_CONTENT_FOLDER_PATHS = "//span[contains(text(),'${pathName}')]";
		String ELEMENT_SELECT_CONTENT_FOLDER_PATHS_A = "//a[@data-original-title='${pathName}']";
		String[] pathNames = contentPath.split("/");
		//if (isTextNotPresent("Folder Browser")){
		//click(ELEMENT_SELECT_CONTENT_PATH_LINK);
		//}
		if(inEditModeWindows){

			if(waitForAndGetElement(ELEMENT_FRAME_CONTAIN_PORTLET,10000,0) != null){
				mouseOver(ELEMENT_FRAME_CONTAIN_PORTLET,true);	
				//click(ELEMENT_EDIT_PORTLET_ICON);
				//click(ELEMENT_SELECT_CONTENT_PATH_LINK);
				click(ELEMENT_EDIT_PORTLET_ICON);  
				if (waitForAndGetElement(ELEMENT_SELECT_CONTENT_PATH_LINK_AUX, 3000, 0) != null){
					click(ELEMENT_SELECT_CONTENT_PATH_LINK_AUX);
				}else{
					click(ELEMENT_SELECT_CONTENT_PATH_LINK);
				}
			}
			info("-- Load frame 1 --");
			if(waitForAndGetElement(By.id("UIContentSelectorOne"),DEFAULT_TIMEOUT,0)==null){
				info("-- Load frame 2 --");
				if(waitForAndGetElement(By.id("CorrectContentSelectorPopupWindow"),DEFAULT_TIMEOUT,0)==null){
					info("-- Load frame 3 --");
					waitForAndGetElement(By.id("UIContentSelectorFolder"));
				}
			}
			for(int i = 0; i < pathNames.length - 1; i ++ ){
				String pathToSelect = ELEMENT_SELECT_CONTENT_FOLDER_PATHS.replace("${pathName}", pathNames[i])+"/../../../div[@class='expandIcon']";
				String pathToSelectA = ELEMENT_SELECT_CONTENT_FOLDER_PATHS_A.replace("${pathName}", pathNames[i])+"/../../div[@class='expandIcon']";
				if(waitForAndGetElement(pathToSelect,DEFAULT_TIMEOUT,0)!=null)
					click(pathToSelect);
				else if(waitForAndGetElement(pathToSelectA,DEFAULT_TIMEOUT,0)!=null)
					click(pathToSelectA);
				else
					waitForAndGetElement(ELEMENT_SELECT_CONTENT_FOLDER_PATHS.replace("${pathName}", pathNames[i])+"/../../../div[@class='collapseIcon']");
			}
			click(ELEMENT_RIGHT_WORKSPACE_NODE.replace("${node}", pathNames[pathNames.length - 1]));
			//wait 1s
			Utils.pause(1000);
			button.save();
			if (contentMode){
				button.save();
				waitForTextPresent(pathNames[pathNames.length - 1]);
			}
			if (nClose){
				button.close();
			}
		}
		else{
			selectContentPath(contentPath);
		}
	}

	//---Function to create new page with content by query portlet, 
	//---@author: Nhungvt
	public void createPage_ContentByQuery_EmptyLayout(String pageName)
	{
		goToPageEditor_EmptyLayout(pageName);
		click(ELEMENT_CONTENT_GROUP_PORTLET);
		dragAndDropToObject(ELEMENT_CONTENTS_BY_QUERY_PORTLET, ELEMENT_DROP_TARGET_NO_LAYOUT);
		Utils.pause(500);
	}


	//function select home path on content list reference form
	public void selectHomePathOnContentList(String groupPath, String node){
		By ELEMENT_NODE = By.xpath("//td/a[contains(text(),'" + node + "')]");

		click(ELEMENT_SELECT_CONTENT_PATH_LINK);
		waitForAndGetElement(ELEMENT_FOLDER_BROWSER);
		if (getElement(ELEMENT_HOMEPATH_ROOT) != null){
			click(ELEMENT_HOMEPATH_ROOT);
		}
		userGroup.selectGroup(groupPath);
		waitForAndGetElement(ELEMENT_NODE);
		if (getElement(ELEMENT_NODE) != null){
			click(ELEMENT_NODE);
		}else{
			info("Not found category");
		}
	}

	//function remove a portlet
	public void removePortlet(Object elementPortlet, Object iconDelete, Object...params){
		info("Delete a portlet..." + elementPortlet);
		magAlert = new ManageAlert(driver);
		Boolean verify = (Boolean) (params.length > 0 ? params[0] : true) ;

		if (waitForAndGetElement(elementPortlet, 5000, 0) != null){
			mouseOver(elementPortlet, true);
			click(iconDelete);
			magAlert.acceptAlert();
			Utils.pause(1000);
			if (verify){
				finishEditLayout();
			}
			info("portlet is removed... successful");
		}else{
			info("portlet has already been deleted");
			if (verify){
				click(ELEMENT_PAGE_CLOSE);
				waitForElementNotPresent(ELEMENT_PAGE_FINISH_BUTTON, 60000);
			}
		}
		waitForElementNotPresent(elementPortlet);
	}

	/**function go to edit a portlet
	 * @author lientm
	 * @param elementPortlet
	 */
	public void goToEditPortlet(Object elementPortlet){	
		info("Go to edit portlet ");
		mouseOver(elementPortlet, true);
		click(ELEMENT_EDIT_PORTLET_ICON);
		waitForAndGetElement(By.id("tab-UIPortletForm"));
	}

	/**function move a portlet to a new container
	 * @author lientm
	 * @param elementPortlet
	 * @param iconMove
	 * @param newContainer
	 */
	public void movePortletOnContainer(By elementPortlet, By iconMove, By newContainer){
		info("Move portlet");
		mouseOver(elementPortlet, true);
		dragAndDropToObject(iconMove, newContainer);
		Utils.pause(2000);
	}

	/**function select and drag drop new container when edit layout of page
	 * @author lientm
	 * @param group
	 * @param container
	 */
	public void addNewContainer(String group, String container){
		info("Add new container: " + container);
		click(ELEMENT_CONTAINER_TAB);
		click(By.linkText(group));
		dragAndDropToObject(By.id(container), By.className("UIRowContainer"));
		Utils.pause(2000);
	}

	/**function remove a container in page layout
	 * @author lientm
	 * @param container
	 * @param iconDelete
	 */
	public void removeContainer(Object container, Object iconDelete){
		info("Delete a container..." + container);
		magAlert = new ManageAlert(driver);
		mouseOver(container, true);
		click(iconDelete);
		magAlert.acceptAlert();
		waitForElementNotPresent(iconDelete);
	}

	/**function add new container and app to layout of page
	 * @author lientm
	 * @param containerType
	 * @param container
	 * @param category
	 * @param portletId
	 */
	public void addNewContainerAndPortlet(String containerType, String container, String category, String portletId, Object...params){		
		boolean finishEdit = (Boolean) (params.length > 0 ? params[0] : true);

		addNewContainer(containerType, container);
		click(ELEMENT_APPLICATION_TAB);
		click(By.linkText(category));
		dragAndDropToObject(By.id(portletId), ELEMENT_DROP_TARGET_HAS_LAYOUT);
		if (finishEdit){
			finishEditLayout();
		}
		Utils.pause(1000);
	}

	//Finish Editing
	public void finishEditLayout(){
		info("Finish Editing PageLayout");
		Utils.pause(1000);
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForElementNotPresent(ELEMENT_PAGE_FINISH_BUTTON, 60000);
	}
}
