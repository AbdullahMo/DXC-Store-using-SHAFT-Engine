package dxcstore.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

import dxcstore.base.PageBase;
import io.qameta.allure.Step;

public class MP3PlayersPage extends PageBase {
	
	private WebDriver driver;
	
	public MP3PlayersPage(WebDriver driver) {
		this.driver = driver;
	}
	
	private By iPodShuffleAddToCartBtn = By.cssSelector("button[onclick=\"cart.add('34', '1');\"]");
	
	@Step("Adding item to cart Step..")
	public void addItemToCart() {
		if (ElementActions.isElementDisplayed(driver, iPodShuffleAddToCartBtn)) {
			ElementActions.click(driver, iPodShuffleAddToCartBtn);
		}
	}

	@Step("Checking if item is Added Step..")
	public boolean checkAddedItem(String word) {
		if (ElementActions.isElementDisplayed(driver, By.xpath("//div/a[contains(text(),'" + word + "')]"))) {
			ReportManager.log("Your Item: " + ElementActions.getText(driver, By.xpath("//div/a[contains(text(),'" + word + "')]"))+
					" has been added to the cart successfully");
			return true;
		}
		return false;
	}

	@Step("Checking mp3 item has been added successfully in cart menu step..")
	public boolean checkMP3NameInCartsMenu(String word) {
		try {
			By cartItemsMenu2 = By.xpath("//div[@id='cart']");
			if(ElementActions.isElementDisplayed(driver, cartItemsMenu2)){
				ElementActions.click(driver, cartItemsMenu2);
			}
			
			By mp3ItemInMenu2 = By.xpath("//ul/li/table/tbody/tr/td/a[contains(text(),'" + word + "')]");
			if (ElementActions.isElementDisplayed(driver, mp3ItemInMenu2)) {
				ReportManager.log("Your item has been found successfully: "+ ElementActions.getText(driver, mp3ItemInMenu2));
				return true;

			}
		} catch (StaleElementReferenceException e) {
			ReportManager.log("Stale Element Reference Exception" + e.getStackTrace());
		}
		return false;
	}

	@Step("Navigating to Check out page Step..")
	public void navigateToShoppingCartPage() {
		try {
		By viewCartLink = By.xpath("//a/strong[contains(text(),'View Cart')]");
		if(ElementActions.isElementDisplayed(driver, viewCartLink)) {
			ElementActions.click(driver, viewCartLink);
		}
		} catch(ElementNotVisibleException e) {
			ReportManager.log("Element Not Visible Exception" + e.getStackTrace());
		}
	}
}
