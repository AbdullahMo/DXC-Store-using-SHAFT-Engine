package dxcstore.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

import dxcstore.base.PageBase;
import io.qameta.allure.Step;

public class CheckOutPage extends PageBase {
	private WebDriver driver;

	private By newAddressRadioBtn = By.xpath("//div/label/input[@name='payment_address' and @value='new']");
	private By newAddressRadioBtn2 = By.xpath("//div/label/input[@name='shipping_address' and @value='new']");

	private By firstNameP = By.id("input-payment-firstname");
	private By lastNameP = By.id("input-payment-lastname");
	private By address1P = By.id("input-payment-address-1");
	private By cityP = By.id("input-payment-city");
	private By countrySelectP = By.id("input-payment-country");
	private By regionSelectP = By.id("input-payment-zone");

	private By firstNameS = By.id("input-shipping-firstname");
	private By lastNameS = By.id("input-shipping-lastname");
	private By address1S = By.id("input-shipping-address-1");
	private By cityS = By.id("input-shipping-city");
	private By countrySelectS = By.id("input-shipping-country");
	private By regionSelectS = By.id("input-shipping-zone");
	private By textArea = By.name("comment");

	public CheckOutPage(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Confirming billing details Step..")
	public void confirmBillingDetails(String fNameTxt, String lNameTxt, String addressTxt, String cityTxt,
			String countryNameTxt, String regionNameTxt) {

		if (ElementActions.isElementDisplayed(driver, newAddressRadioBtn)) {
			ElementActions.click(driver, newAddressRadioBtn);
		}

		if (ElementActions.isElementDisplayed(driver, firstNameP)) {
			setTxt(driver, firstNameP, fNameTxt);
			setTxt(driver, lastNameP, lNameTxt);
			setTxt(driver, address1P, addressTxt);
			setTxt(driver, cityP, cityTxt);
			findDropDownElement(driver, countrySelectP);
			selectFromDropDown(driver, countrySelectP, countryNameTxt);
			findDropDownElement(driver, regionSelectP);
			selectFromDropDown(driver, regionSelectP, regionNameTxt);
			try {
				By continuePaymentAddressBtn = By.id("button-payment-address");
				if (ElementActions.isElementDisplayed(driver, continuePaymentAddressBtn)) {
					ElementActions.click(driver, continuePaymentAddressBtn);
				}
			} catch (StaleElementReferenceException e) {
				ReportManager.log("Stale Element Reference Exception.. Element Not Attached to DOM");
			}
		}

	}

	@Step("Confirming delivery details Step..")
	public void confirmDeliveryDetails(String fNameTxt, String lNameTxt, String addressTxt, String cityTxt,
			String countryNameTxt, String regionNameTxt) {
		if (ElementActions.isElementDisplayed(driver, newAddressRadioBtn2)) {
			ElementActions.click(driver, newAddressRadioBtn2);
		}

		if (ElementActions.isElementDisplayed(driver, firstNameS)) {
			setTxt(driver, firstNameS, fNameTxt);
			setTxt(driver, lastNameS, lNameTxt);
			setTxt(driver, address1S, addressTxt);
			setTxt(driver, cityS, cityTxt);
			findDropDownElement(driver, countrySelectS);
			selectFromDropDown(driver, countrySelectS, countryNameTxt);
			findDropDownElement(driver, regionSelectS);
			selectFromDropDown(driver, regionSelectS, regionNameTxt);
			try {
				By continueShippingAddressBtn = By.id("button-shipping-address");
				if (ElementActions.isElementDisplayed(driver, continueShippingAddressBtn)) {
					ElementActions.click(driver, continueShippingAddressBtn);
				}
			} catch (StaleElementReferenceException e) {
				ReportManager.log("Error! Stale Element Exception..");
			}
		}

	}

	@Step("Adding comment for order Step..")
	public boolean addCommentforOrder(String comment) {
		if (ElementActions.isElementDisplayed(driver, textArea)) {
			setTxt(driver, textArea, comment);
		}

		try {
			By continueShippingMethodBtn = By.id("button-shipping-method");
			if (ElementActions.isElementDisplayed(driver, continueShippingMethodBtn)) {
				ElementActions.click(driver, continueShippingMethodBtn);
			}

			By termsAgreementCheckbox = By.name("agree");
			ElementActions.click(driver, termsAgreementCheckbox);

			By continuePaymentMethodBtn = By.id("button-payment-method");
			if (ElementActions.isElementDisplayed(driver, continuePaymentMethodBtn)) {
				ElementActions.click(driver, continuePaymentMethodBtn);
				return true;
			}

		} catch (StaleElementReferenceException e) {
			ReportManager.log("Error! Stale Element Exception..");
		}
		return false;
	}

	public Double readAndAddConvertedPrice(String price) {
		String priceStringValue = null;
		priceStringValue = price;
		priceStringValue = priceStringValue.substring(1);
		return Double.parseDouble(priceStringValue);
	}

	@Step("Confirming Price and that it contains Flat Shipping Rate Step..")
	public boolean checkItemPriceAndFlatShippingRate(String price, String shippingRate, String totalPrice) {
		wait = new WebDriverWait(driver, 10);
		if (ElementActions.isElementDisplayed(driver, By.xpath("//table/tbody/tr/td[5][contains(text(),'" + price + "')]"))) {
			ReportManager.log("Item price is equal to expected price in cart " + ElementActions.getText(driver, By.xpath("//table/tbody/tr/td[5][contains(text(),'" + price + "')]")));
			try {
				double calculatePrice = 0;
				calculatePrice += readAndAddConvertedPrice(driver.findElement(By.xpath("//table/tbody/tr/td[5][contains(text(),'" + price + "')]")).getText());
				
				calculatePrice += readAndAddConvertedPrice(driver.findElement(By.xpath("//table/tfoot/tr[2]/td[2][contains(text(),'"+ shippingRate +"')]")).getText());
				
				if(calculatePrice == Double.parseDouble(totalPrice)) {
					ReportManager.log("Total price: "+ ElementActions.getText(driver, By.xpath("//table/tfoot/tr[3]/td[2]")) + " includes the flat shipping rate: " +shippingRate);
					return true;
				}
			}catch(NumberFormatException e) {
				ReportManager.log("Error! Number Format Exception..");
			}
		}
		return false;
	}

	@Step("Navigating to Checkout Success Page Step..")
	public void navigateToCheckOutSuccessPage() {
		By confirmOrderBtn = By.id("button-confirm");

		if (ElementActions.isElementDisplayed(driver, confirmOrderBtn)) {
			ElementActions.click(driver, confirmOrderBtn);
		}
	}

}
