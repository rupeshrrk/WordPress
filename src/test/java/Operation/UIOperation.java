package Operation;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
public class UIOperation {
    WebDriver driver;
 
    public UIOperation(WebDriver driver){
        this.driver = driver;
        
    }
    
    
    public WebElement getElement(By locator)
    {
    	return driver.findElement(locator);
    }
    
    
    
    public void selectDropdownValueBy(By locator, String type, String value)
    {
    	Select select = new Select(getElement(locator));
    	
    	switch(type){
    	
    	case "index":
    		select.selectByIndex(Integer.parseInt(value));
    		break;
    	case "value":
    		select.selectByValue(value);
    		break; 
    	case "visibletext":
    		select.selectByVisibleText(value);
    		break;
    		
    	default:
    		System.out.print("Please pass the correct selection criteria");
    		break;
    	}
    }
    
    public void scrollValue(Properties p, String objectname, String objectType, String value) throws NumberFormatException, Exception
    {
    	 Actions action= new Actions(driver);
		 Thread.sleep(3000);
		 action.dragAndDropBy(driver.findElement(this.getObject(p, objectname, objectType)),Integer.parseInt(value), 0).build().perform();
		 Thread.sleep(3000);
    	
    }
     
    
    public void perform(Properties p,String operation,String objectName,String objectType,String value) throws Exception{
        System.out.println("");
        switch (operation.toUpperCase()) {
        
        case "CLICK":
            //Perform click
            driver.findElement(this.getObject(p,objectName,objectType)).click();
            Thread.sleep(3000);
            break;
            
        case "ENTER_TEXT":
            //Set text on control
            driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(p.getProperty(value));
            Thread.sleep(3000);
            break;
            
        case "CLEAR_TEXT":
        	driver.findElement(this.getObject(p, objectName, objectType)).clear();
        	Thread.sleep(3000);
        	break;

		case "MAXIMISE":
			driver.manage().window().maximize();
			Thread.sleep(3000);
			break;

        case "GOTOURL":
            //Get url of application
            driver.get(p.getProperty(value));
            Thread.sleep(3000);
			break;

		case "ACTION_COMMAND":
			Actions action1 = new Actions(driver);
			Thread.sleep(1000);
			action1.moveToElement(driver.findElement(this.getObject(p, objectName, objectType))).build().perform();
			Thread.sleep(3000);
			break;

		case "ACTION_CLICK":
			Actions action2 = new Actions(driver);
			action2.moveToElement(driver.findElement(this.getObject(p, objectName, objectType))).click().build()
					.perform();
			Thread.sleep(3000);
			break;

		case "JEXECUTESCRIPT_SCROLL_TO_VIEW":
			JavascriptExecutor jsc = (JavascriptExecutor) driver;
			jsc.executeScript("arguments[0].scrollIntoView();",
					driver.findElement(this.getObject(p, objectName, objectType)));
			break;

		case "IMPLICIT_WAIT":
			// Perform click
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(p.getProperty(value)), TimeUnit.SECONDS);
            break;
            
		case "EXPLICIT_WAIT":
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement element = wait
					.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p, objectName, objectType)));
			break;

        case "SELECT_DROPDOWN_TEXT":
        	this.selectDropdownValueBy(this.getObject(p, objectName, objectType),"visibletext", p.getProperty(value));
        	break;
        	
        case "SELECT_DROPDOWN_VALUE":
        	this.selectDropdownValueBy(this.getObject(p, objectName, objectType),"value", p.getProperty(value));
			break;

		case "REFRESH":
			driver.navigate().refresh();
			Thread.sleep(3000);
        	break;
        	
		case "NAVIGATE_BACK":
			driver.navigate().back();
			Thread.sleep(3000);
			break;

		case "GETTEXT":
			// Get text of an element
			driver.findElement(this.getObject(p, objectName, objectType)).getText();
			Thread.sleep(3000);
			break;

        case "SELECT_DROPDOWN_INDEX":
        	this.selectDropdownValueBy(this.getObject(p, objectName, objectType),"index", p.getProperty(value));
        	break;

        case "MULTI_SELECT_DROPDOWN":
        	Select dropdown= new Select(driver.findElement(this.getObject(p, objectName, objectType)));
    		List<WebElement> options = dropdown.getOptions();
    		break;
    	
        case "GETFIRSTSELECTED_DROPDOWN":
        	new Select(driver.findElement(this.getObject(p, objectName, objectType))).getFirstSelectedOption();
    		break;
        	
        	
        case "JEXECUTESCRIPT_CLICK":
        	 JavascriptExecutor js = (JavascriptExecutor)driver;
        	 js.executeScript(p.getProperty("scriptpart1"), driver.findElement(this.getObject(p, objectName, objectType)));
        	 Thread.sleep(3000);
        	 break;
        	 
        case "JEXECUTESCRIPT_TEXT":
        	JavascriptExecutor jse = (JavascriptExecutor)driver;
        	jse.executeScript("arguments[0].value='"+ p.getProperty(value) +"';", driver.findElement(this.getObject(p, objectName, objectType)));
        	break;
        	
        case "ACTION_TEXT":
        		Actions action3=new Actions(driver);
        		action3
        		.moveToElement(driver.findElement(this.getObject(p, objectName, objectType)))
	    		.click()
	    		.sendKeys(p.getProperty(value))
	    		.build()
	    		.perform();
	    		Thread.sleep(3000);
	    		break;
	    		
        case "SCROLL_VALUE":
	        	this.scrollValue(p, objectName, objectType, p.getProperty("scrollByValue"));
	        	break;
	        	
        case "SWITCH_TO_IFRAME":
        		driver.switchTo().frame(p.getProperty("iframeval"));
        		Thread.sleep(3000);
        		break;
        		
        case "PRESS_ENTER":
        		driver.findElement(this.getObject(p, objectName, objectType)).sendKeys(Keys.ENTER);
        		Thread.sleep(2000);
        		break;
       
        case "ROBOT_KEYS":
        		Robot r = new Robot();
   			 	r.keyPress(KeyEvent.VK_ENTER);
   			 	r.keyRelease(KeyEvent.VK_ENTER);
   			 	Thread.sleep(3000);
   			 	break;
   			 	
   			 	
        case "ROBOT_KEY_ESC":
    			Robot r1 = new Robot();
//			 	r1.keyPress(Integer.parseInt(p.getProperty(value)));
//			 	r1.keyRelease(Integer.parseInt(p.getProperty(value)));
    			r1.keyPress(KeyEvent.VK_ESCAPE);
    			r1.keyRelease(KeyEvent.VK_ESCAPE);
			 	Thread.sleep(3000);
			 	break;
			 	
        case "ROBOT_KEYS_TAB":
			Robot r2 = new Robot();
		 	r2.keyPress(KeyEvent.VK_TAB);
		 	r2.keyRelease(KeyEvent.VK_TAB);
		 	Thread.sleep(3000);
		 	break;
		 	
		 	
        case "COPY_AND_PASTE_TEXT":
        	//String text = "Hello World";
        	StringSelection stringSelection = new StringSelection(p.getProperty(value));
        	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        	clipboard.setContents(stringSelection, stringSelection);

        	Robot robot = new Robot();
        	robot.keyPress(KeyEvent.VK_CONTROL);
        	robot.keyPress(KeyEvent.VK_V);
        	robot.keyRelease(KeyEvent.VK_V);
        	robot.keyRelease(KeyEvent.VK_CONTROL);
			 break;
	
			 	
        case "FILE_UPLOAD":
        		Runtime.getRuntime().exec(p.getProperty(value));
        		Thread.sleep(3000);
        		break;
        default:
            break;
        }
    }
    
    // 
    private By getObject(Properties p,String objectName,String objectType) throws Exception{
        //Find by xpath
        if(objectType.equalsIgnoreCase("XPATH")){
            
            return By.xpath(p.getProperty(objectName));
        }
        //find by class
        else if(objectType.equalsIgnoreCase("CLASSNAME")){
            
            return By.className(p.getProperty(objectName));
            
        }
        //find by name
        else if(objectType.equalsIgnoreCase("NAME")){
            
            return By.name(p.getProperty(objectName));
            
        }
        //Find by css
        else if(objectType.equalsIgnoreCase("CSS")){
            
            return By.cssSelector(p.getProperty(objectName));
            
        }
        //find by link
        else if(objectType.equalsIgnoreCase("LINK")){
            
            return By.linkText(p.getProperty(objectName));
            
        }
        //find by partial link
        else if(objectType.equalsIgnoreCase("PARTIALLINK")){
            
            return By.partialLinkText(p.getProperty(objectName));
            
        }
        else if(objectType.equalsIgnoreCase("ID")){
		           	
		            return By.id(p.getProperty(objectName));
        }
        else if(objectType.equalsIgnoreCase("TAGNAME")){
           	
            return By.tagName(p.getProperty(objectName));
        }
        
        
        
		        else
        {
            throw new Exception("Wrong object type");
        }
    }
}