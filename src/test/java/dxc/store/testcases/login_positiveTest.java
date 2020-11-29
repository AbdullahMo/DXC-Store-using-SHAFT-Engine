package dxc.store.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.validation.Assertions;

import dxcstore.base.TestBase;
import dxcstore.pages.HomePage;
import dxcstore.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;


public class login_positiveTest extends TestBase {

	HomePage homePageObject;
	LoginPage loginPageObject;
	
	@BeforeMethod
	public void beforeMethod() {

		openBrowser();
		// initialize page objects
		homePageObject = new HomePage(driver);
		loginPageObject = new LoginPage(driver);
	}

	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("Login Positive Scenario Test Case")
	public void testLoginPositive() {
		homePageObject.navigateToPageUsingAction("My Account", "Login");
		
		Boolean userIsLoggedIn = loginPageObject.userLogin(
				dxcstore.utilities.LoadProperties.userCredentials.getProperty("username"),
				dxcstore.utilities.LoadProperties.userCredentials.getProperty("password"));

		Assertions.assertTrue(userIsLoggedIn, pos_assertionType, "Checking that user is logged in..");
		
		String logOutLink = homePageObject.isUserLoggedIn();
		
		Assertions.assertEquals("Logout", logOutLink, equal_comparisonType, pos_assertionType, 
				"Checking that log out link is displayed..");
	}
}
