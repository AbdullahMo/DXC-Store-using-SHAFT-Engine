package dxcstore.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

import dxcstore.base.PageBase;
import io.qameta.allure.Step;

public class TabletsPage extends PageBase {
	private WebDriver driver;
	// private Properties properties;
	private By addToCartBtn = By.xpath("//span[contains(text(),'Add to Cart')]");
	
	public TabletsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	@Step("Adding Item to Cart Step..")
	public void addItemToCart(String word) {
		if(ElementActions.isElementDisplayed(driver, By.xpath("//h4//a[contains(text(),'" + word + "')]"))) {
			ReportManager.log("Element: "+ word + " is displayed and adding to cart..");
			clickOnBtn(driver, addToCartBtn);
		}
		else {
			ReportManager.log("Requested element: "+ word + " wasn't displayed..");
		}
	}
	
	@Step("Checking if item is Added Step..")
	public boolean checkAddedItem(String word) {
		
		if (ElementActions.isElementDisplayed(driver, By.xpath("//div/a[contains(text(),'" + word + "')]"))) {
			String item = ElementActions.getText(driver, By.xpath("//div/a[contains(text(),'" + word + "')]"));
			
			ReportManager.log("Your item: "+ item + " has been added to the cart successfully");
			return true;
		}
		return false;
	}
	
}
