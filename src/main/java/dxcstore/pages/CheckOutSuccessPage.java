package dxcstore.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

import dxcstore.base.PageBase;

public class CheckOutSuccessPage extends PageBase {

	private WebDriver driver;
	private By successMessage = By.xpath("//h1[contains(text(),'Your order has been placed!')]");
	private By shoppingCartButton = By.id("cart-total");

	public CheckOutSuccessPage(WebDriver driver) {
		this.driver = driver;
	}
	
	
	public boolean checkSuccessMessageAndShoppingCart() {
		if (ElementActions.isElementDisplayed(driver, successMessage)) {
			ReportManager.log("Item order has been placed successfully and message displayed is: "
					+ ElementActions.getText(driver, successMessage));
		}
		if (ElementActions.isElementDisplayed(driver, shoppingCartButton) 
				&& ElementActions.getText(driver, shoppingCartButton).equals("0 item(s) - $0.00"))
			{
				ReportManager.log("Success! No items found in shopping cart: " + ElementActions.getText(driver, shoppingCartButton));
				return true;
			}
		
		return false;
	}

}
