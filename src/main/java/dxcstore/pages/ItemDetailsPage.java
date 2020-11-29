package dxcstore.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

import dxcstore.base.PageBase;

public class ItemDetailsPage extends PageBase {
	private WebDriver driver;
	// private Properties properties;

	public ItemDetailsPage(WebDriver driver) {
		this.driver = driver;
	}

	private By calendar = By.cssSelector("i.fa.fa-calendar");
	private By addToCartBtn = By.xpath("//button[@id='button-cart']");
	private By date = By.xpath("//tbody/tr[6]/td[contains(text(),'5')]");

	public void chooseNewDateAndAddToCart() {
		try {
			if (ElementActions.isElementDisplayed(driver, calendar)) {
				ElementActions.click(driver, calendar);
				ElementActions.click(driver, date);
			}
			ElementActions.click(driver, addToCartBtn);

		} catch (ElementNotVisibleException e) {
			ReportManager.log("Element not visible exception");
		}

	}
}
