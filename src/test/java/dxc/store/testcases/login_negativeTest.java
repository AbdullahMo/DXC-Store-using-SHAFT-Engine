package dxc.store.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.validation.Assertions;
import com.shaft.validation.Verifications;

import dxcstore.base.TestBase;
import dxcstore.pages.HomePage;
import dxcstore.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class login_negativeTest extends TestBase {
	HomePage homePageObject;
	LoginPage loginPageObject;

	@BeforeMethod
	public void beforeMethod() {
		openBrowser();
		homePageObject = new HomePage(driver);
		loginPageObject = new LoginPage(driver);
	}

	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("Login Positive Scenario Test Case")
	public void testLoginNegative() {
		homePageObject.navigateToPageUsingAction("My Account", "Login");

		Boolean userIsLoggedIn = loginPageObject.userLogin("dummyt", "data");

		// Assertions.assertTrue(userIsLoggedIn, neg_assertionType, "Checking that user
		// is logged in..");

		Verifications.verifyTrue(userIsLoggedIn, "Checking that user is logged in..");
		String errorMessage = loginPageObject.getEmailAndPasswordErrorMessageDisplayed();

		Assertions.assertEquals("Warning: No match for E-Mail Address and/or Password.", errorMessage,
				equal_comparisonType, pos_assertionType,
				"Checking that invalid Email/Password error message is displayed..");
	}

}
