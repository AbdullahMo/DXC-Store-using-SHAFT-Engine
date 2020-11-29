package dxc.store.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.validation.Assertions;

import dxcstore.base.TestBase;
import dxcstore.pages.CheckOutPage;
import dxcstore.pages.CheckOutSuccessPage;
import dxcstore.pages.HomePage;
import dxcstore.pages.LoginPage;
import dxcstore.pages.MP3PlayersPage;
import dxcstore.pages.ShoppingCartPage;

public class ShoppingCart_ConfirmOrder extends TestBase {

	HomePage homePageObject;
	LoginPage loginPageObject;
	MP3PlayersPage mp3PlayersPageObject;
	ShoppingCartPage shoppingCartPageObject;
	CheckOutPage checkOutPageObject;
	CheckOutSuccessPage checkOutSuccessPageObject;

	@BeforeMethod
	public void beforeMethod() {
		openBrowser();
		homePageObject = new HomePage(driver);
		loginPageObject = new LoginPage(driver);
		mp3PlayersPageObject = new MP3PlayersPage(driver);
		shoppingCartPageObject = new ShoppingCartPage(driver);
		checkOutPageObject = new CheckOutPage(driver);
		checkOutSuccessPageObject = new CheckOutSuccessPage(driver);

	}

	@Test
	public void UserCanAddItemsToShoppingCartAndConfirmOrder() {
		homePageObject.navigateToPageUsingAction("My Account", "Login");
		loginPageObject.userLogin("testdumpemail66112333@hotmail.com", "SuperSecretPassword!");

		// homePageObject.navigateToShowAllMP3Page();
		homePageObject.navigateToMenuItemSecondLevel("MP3 Players", "Show All MP3 Players");
		mp3PlayersPageObject.addItemToCart();

		boolean itemNameDisplayed = mp3PlayersPageObject.checkAddedItem("iPod Shuffle");
		Assertions.assertTrue(itemNameDisplayed, pos_assertionType, "Checking that item name is displayed..");

		boolean itemNameDisplayedInCartsMenu = mp3PlayersPageObject.checkMP3NameInCartsMenu("iPod Shuffle");
		Assertions.assertTrue(itemNameDisplayedInCartsMenu, pos_assertionType,
				"Checking that item is displayed in carts menu..");

		mp3PlayersPageObject.navigateToShoppingCartPage();

		boolean itemNameDisplayedd = shoppingCartPageObject.checkItemNameIsDisplayed("iPod Shuffle");
		Assertions.assertTrue(itemNameDisplayedd, pos_assertionType,
				"Checking that item is displayed in shopping cart page..");

		boolean itemPrice = shoppingCartPageObject.checkItemPrice("$100.00", "$100.00");
		Assertions.assertTrue(itemPrice, pos_assertionType, "Checking that item price is as expected..");

		shoppingCartPageObject.navigateToCheckOutPage();

		checkOutPageObject.confirmBillingDetails("Abdullah", "Mohamed", "911 Groove Street", "Detroit", "Egypt",
				"Al Jizah");
		checkOutPageObject.confirmDeliveryDetails("Abdullah", "Mohamed", "911 Groove Street", "Detroit", "Egypt",
				"Al Jizah");

		boolean comment = checkOutPageObject.addCommentforOrder("This is an automated test comment");
		Assertions.assertTrue(comment, pos_assertionType, "Checking that comment is added..");

		boolean itemPriceAndFlatShippingRate = checkOutPageObject.checkItemPriceAndFlatShippingRate("100", "5.00",
				"105.00");
		Assertions.assertTrue(itemPriceAndFlatShippingRate, pos_assertionType,
				"Checking item price and flat shipping rate equal expected..");

		checkOutPageObject.navigateToCheckOutSuccessPage();

		boolean checkOutSuccessMessage = checkOutSuccessPageObject.checkSuccessMessageAndShoppingCart();
		Assertions.assertTrue(checkOutSuccessMessage, pos_assertionType,
				"Checking that success message is displayed successfully..");

		homePageObject.navigateToPageUsingAction("My Account", "Logout");
	}
}
