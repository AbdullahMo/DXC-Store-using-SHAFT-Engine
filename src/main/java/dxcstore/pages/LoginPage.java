package dxcstore.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

import dxcstore.base.PageBase;
import io.qameta.allure.Step;

public class LoginPage extends PageBase {
	private By emailField = By.name("email");
	private By passwordField = By.name("password");
	private By submitBtn = By.cssSelector("input[type='submit']");
	private By emailPasswordValidation = By.cssSelector("div.alert.alert-danger");
	private By logOutLink = By.xpath("//li/a[contains(text(),'Logout')]");
	private By myAccountBtn = By.cssSelector("a[title='My Account']");

	private WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	@Step("User logging in step..")
	public boolean userLogin(String email, String password) {
		setTxt(driver, emailField, email);
		setTxt(driver, passwordField, password);
		clickOnBtn(driver, submitBtn);
		return true;
		
	}

	public boolean isUserLoggedIn() {
		ElementActions.click(driver, myAccountBtn);
		if (ElementActions.isElementDisplayed(driver, logOutLink)) {
			ElementActions.click(driver, myAccountBtn);
			return true;
		} else {
			ElementActions.click(driver, myAccountBtn);
			return false;
		}
	}

	@Step("Checking validations are triggered or not for Email and Password step..")
	public String getEmailAndPasswordErrorMessageDisplayed() {
		String validationMessage = null;
		if (ElementActions.isElementDisplayed(driver, emailPasswordValidation)) {
			validationMessage = ElementActions.getText(driver, emailPasswordValidation);
			ReportManager.log("Error message is displayed successfully: " + validationMessage);
			return validationMessage;
		} else {
			return null;
		}
	}
}
