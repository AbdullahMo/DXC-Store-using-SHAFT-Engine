package dxcstore.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.browser.BrowserFactory;
import com.shaft.gui.browser.BrowserFactory.BrowserType;
import com.shaft.tools.io.ReportManager;
import com.shaft.validation.Assertions.AssertionComparisonType;
import com.shaft.validation.Assertions.AssertionType;


public class TestBase {
	public static Properties properties;
	public static WebDriver driver;
	
	
	public static final String PROPERTY_WEBDRIVER_BROWSER = System.getProperty("browser.type");
    public static final String PROPERTY_WEB_URL = System.getProperty("web.url");
    
    public static final AssertionComparisonType equal_comparisonType = AssertionComparisonType.EQUALS;
	public static final AssertionType pos_assertionType = AssertionType.POSITIVE; 
	public static final AssertionType neg_assertionType = AssertionType.NEGATIVE;  
    
    final static Logger logger = Logger.getLogger(TestBase.class);
	
	static {
		     properties = ReadProperties("./src/test/resources/Properties/configuration.properties");
	       } 

	public static Properties ReadProperties(String filePath) {

		try {
			FileInputStream testProperties = new FileInputStream(filePath);
			properties = new Properties();
			properties.load(testProperties);
			return properties;
		} catch (FileNotFoundException e) {
			logger.error("Properties file not found",e);
		} catch (IOException e) {
			logger.error("Properties file error",e);
		}
		return properties;
	}
	
	public void openBrowser() {
		
		//Utils.runWindowsCommand("taskkill /f /im chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.setAcceptInsecureCerts(true);
		
		driver = BrowserFactory.getBrowser(BrowserType.GOOGLE_CHROME,options);
		navigateHome();
	}
	
	public static void navigateHome() {
		ReportManager.log("URL is : " + PROPERTY_WEB_URL);
		
		BrowserActions.navigateToURL(driver, PROPERTY_WEB_URL);
		
  	   }
	
	public static void validateTestResult(ITestResult result)
	{
		
		switch (result.getStatus()) {
		case ITestResult.FAILURE:
			ReportManager.log("Test Case Failed is " + result.getName());
			ReportManager.log("Test Case Failed is " + result.getThrowable());
			break;
		case ITestResult.SKIP:
			result.setStatus(ITestResult.FAILURE);
			ReportManager.log("Test Case Skipped is [" + result.getName()+ "] and the result set to be Failed" );
			break;
		case ITestResult.SUCCESS:
			ReportManager.log("Test Case is passed");
			break;
		default:
			break;
		}
	}
	
	public static void closeBrowser() {
		ReportManager.log("Close All Browser");
		BrowserFactory.closeAllDrivers();
		ReportManager.attachFullLog("");
	}
	
	 @AfterMethod
	  public void afterMethod(ITestResult result) {
		  ReportManager.log("AfterMethod is called");
			validateTestResult(result);
			closeBrowser();
	  }


}
