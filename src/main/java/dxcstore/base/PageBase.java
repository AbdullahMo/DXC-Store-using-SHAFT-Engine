package dxcstore.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

public class PageBase {

    protected WebDriverWait wait;
    protected Actions a;
   
		
	public void setTxt(WebDriver driver, By element, String text){
        ElementActions.type(driver, element, text);
    }
	
	public void setTxtforElement(WebElement element, String text){
        element.clear();
        element.sendKeys(text);
    }
	public boolean isElementVisible(WebDriver driver, By elementLocator) {
		boolean flag = false;
		try {
			(new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			ReportManager.log("Element matching this locator [" + elementLocator + "] is visible.");
			flag = true;
		} catch (TimeoutException e) {
			ReportManager.log(e);
			ReportManager.log("Element matching this locator [" + elementLocator + "] is not visible.");
			flag = false;
		}

		return flag;
	}
	
    public void clickOnBtn(WebDriver driver,By element){
        ElementActions.click(driver, element);
    }

    protected Select findDropDownElement(WebDriver driver, By element){
        return new Select(driver.findElement(element));
    }
    
    public void selectFromDropDownElement(WebDriver driver, By element, String option) {
    	ElementActions.select(driver, element, option);
    }

    protected void selectFromDropDown(WebDriver driver, By element, String option){
        findDropDownElement(driver,element).selectByVisibleText(option);
    }
    
    protected void selectFromDropDownByIndex(WebDriver driver, By element, int index) {
    	findDropDownElement(driver, element).selectByIndex(index);
    }
    
	public void chooseSecondLevelItem(WebDriver driver, By firstLevelElement, By secondLevelElement) {
		a = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		a.moveToElement(driver.findElement(firstLevelElement)).perform();
		a.moveToElement(driver.findElement(secondLevelElement)).click().build().perform();
	}
	
	public void navigateToSpecifiedPage(WebDriver driver, By element) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if(driver.findElement(element).isDisplayed()){
				driver.findElement(element).click();
		}
	}
}
