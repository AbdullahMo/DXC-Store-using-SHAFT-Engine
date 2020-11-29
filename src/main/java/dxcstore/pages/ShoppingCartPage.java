package dxcstore.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

import dxcstore.base.PageBase;

public class ShoppingCartPage extends PageBase {
	private WebDriver driver;
	// private Properties properties;
	private By myAccountBtn = By.cssSelector("a[title='My Account']");
	private By logOutBtn = By.xpath("//li/a[contains(text(),'Logout')]");

	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean checkItemNameIsDisplayed(String itemName) {
		List<String> myItemsList = new ArrayList<String>();
		myItemsList.add(itemName);

		for (int i = 0; i < myItemsList.size(); i++) {
			By itemLabelCurrent = By.xpath("//form/div/table/tbody/tr[" + (i + 1) + "]/td[2]/a");
			if (ElementActions.isElementDisplayed(driver, itemLabelCurrent)) {
				ReportManager.log("Found item in cart: " + itemLabelCurrent);
				return true;

			}
		}
		return false;
	}

	public boolean checkItemPrice(String uPrice, String tPrice) {
		List<WebElement> itemTotalPrices = driver
				.findElements(By.xpath("//form/div/table/tbody/tr/td[@class='text-right']"));
		for (int i = 0; i < (itemTotalPrices.size()) / 2; i++) {
			setQuantity();
			By itemUnitPrice = By.xpath("//form/div/table/tbody/tr[" + (i + 1) + "]/td[5]");
			By itemTotalPrice = By.xpath("//form/div/table/tbody/tr[" + (i + 1) + "]/td[6]");
			if (ElementActions.isElementDisplayed(driver, itemUnitPrice) && ElementActions.getText(driver, itemUnitPrice).equals(uPrice)) {
				ReportManager.log("Found Item Unit Price as Expected: " + itemUnitPrice);
				if (ElementActions.isElementDisplayed(driver, itemTotalPrice) && ElementActions.getText(driver, itemTotalPrice).equals(tPrice)) {
					ReportManager.log("Found Item Total Price as Expected: " + itemTotalPrice);
					return true;
				}
			}

		}
		return false;

	}

	public void setQuantity() {
		List<WebElement> quantities = driver
				.findElements(By.xpath("//input[@type='text' and @class='form-control' and @size='1']"));

		if (quantities.size() == 1) {
			setTxt(driver, By.xpath("//input[@type='text' and @class='form-control' and @size='1']"), "1");
		} else {
			for (WebElement quantity : quantities) {
				setTxtforElement(quantity, "1");
			}
		}
		clickOnBtn(driver, By.xpath("//button[@data-toggle='tooltip' and @class='btn btn-primary']"));

	}

	public boolean checkItemDate(String date) {
		By dateElement = By.xpath("//small[contains(text(),'Delivery Date:')]");
		if (ElementActions.getText(driver, dateElement).contains(date)) {
			ReportManager.log("Success! Found date as expected: " + ElementActions.getText(driver, dateElement));
			return true;
		} else {
			ReportManager.log("Error! Found different date: " + ElementActions.getText(driver, dateElement));
		}
		return false;
	}

	public boolean checkItemTotalPrice(double total) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
		try {
			List<WebElement> itemTotalPrices = driver
					.findElements(By.xpath("//form/div/table/tbody/tr/td[@class='text-right']"));

			String totalStringValue = null;
			double totalCount = 0;

			for (int i = 0; i < (itemTotalPrices.size()) / 2; i++) {
				WebElement itemTPrice = driver
						.findElement(By.xpath("//form/div/table/tbody/tr[" + (i + 1) + "]/td[6]"));

				if (wait.until(ExpectedConditions.visibilityOf(itemTPrice)).isDisplayed()) {
					totalStringValue = itemTPrice.getText();
					ReportManager.log("Item Total Price is: " + totalStringValue);
					totalStringValue = totalStringValue.substring(1);
					totalCount += Double.parseDouble(totalStringValue);
					ReportManager.log("Item Total Price so far is: " + totalCount);
					totalStringValue = null;

				}

			}
			if (totalCount == total) {
				ReportManager.log("Total price is matching the expected value: " + totalCount);

				return true;
			} else {
				ReportManager.log("Found different price!: " + totalCount);
				return false;
			}
		} catch (ElementNotVisibleException e) {
			ReportManager.log("Error! Element Not Visible Exception");
		} catch (StaleElementReferenceException e) {
			ReportManager.log("Error! Stale Element Exception");
		}

		return false;
	}

	public void navigateToCheckOutPage() {
		By checkOutBtn = By.cssSelector("a[class='btn btn-primary']");
		if (ElementActions.isElementDisplayed(driver, checkOutBtn)) {
			ElementActions.click(driver, checkOutBtn);
		}
	}

	public void userLogout() {
		if (ElementActions.isElementDisplayed(driver, myAccountBtn)) {
			clickOnBtn(driver, myAccountBtn);
			clickOnBtn(driver, logOutBtn);
		}

	}
}
