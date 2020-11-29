package dxc.store.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.validation.Assertions;

import dxcstore.base.TestBase;
import dxcstore.pages.HomePage;
import dxcstore.pages.ItemDetailsPage;
import dxcstore.pages.ProductDetailsPage;
import dxcstore.pages.LoginPage;
import dxcstore.pages.ShoppingCartPage;
import dxcstore.pages.TabletsPage;

public class ShoppingCart_ComparisonTest extends TestBase{
	
	HomePage homePageObject;
	LoginPage loginPageObject;
	TabletsPage tabletsPageObject;
	ShoppingCartPage shoppingCartPageObject;
	ProductDetailsPage productDetailsPageObject;
	ItemDetailsPage itemDetailsPageObject;
	
	@BeforeMethod
	public void beforeMethod() {
		openBrowser();
		homePageObject = new HomePage(driver);
		loginPageObject = new LoginPage(driver);
		tabletsPageObject = new TabletsPage(driver);
		shoppingCartPageObject = new ShoppingCartPage(driver);
		productDetailsPageObject = new ProductDetailsPage(driver);
		itemDetailsPageObject = new ItemDetailsPage(driver);
		
	}

	@Test
	public void UserCanAddItemsToShoppingCartAndCompareThem() {
		homePageObject.navigateToPageUsingAction("My Account", "Login");
		loginPageObject.userLogin("anyemailtestasd12377@hotmail.com", "SuperSecretPassword!");

		homePageObject.navigateToMenuItemFirstLevel("Tablets");
		
		tabletsPageObject.addItemToCart("Samsung");
		
		boolean itemIsAdded = tabletsPageObject.checkAddedItem("Samsung");
		Assertions.assertTrue(itemIsAdded, pos_assertionType, "Checking that item is added..");

		homePageObject.navigateToTopMenuFirstLevel("Shopping Cart");
		
		boolean itemNameIsDisplayed = shoppingCartPageObject.checkItemNameIsDisplayed("Samsung");
		Assertions.assertTrue(itemNameIsDisplayed, pos_assertionType, "Check item name is displayed..");
		
		boolean itemPriceIsDisplayed = shoppingCartPageObject.checkItemPrice("199.99","199.99");
		Assertions.assertTrue(itemPriceIsDisplayed, pos_assertionType, "Check item price is displayed as expected..");
		
		homePageObject.navigateToMenuItemSecondLevel("Laptops & Notebooks", "Show All Laptops & Notebooks");
		
		productDetailsPageObject.addItemToCart("HP LP3065");

		itemDetailsPageObject.chooseNewDateAndAddToCart();
		homePageObject.navigateToTopMenuFirstLevel("Shopping Cart");
		
		
		boolean itemDateIsDisplayed = shoppingCartPageObject.checkItemDate("2011-05-05");
		Assertions.assertTrue(itemDateIsDisplayed, pos_assertionType, "Checking item date is displayed..");
		
		boolean itemTotalPrice = shoppingCartPageObject.checkItemTotalPrice(299.99);
		Assertions.assertTrue(itemTotalPrice, pos_assertionType, "Checking total price equals to expected..");
		
		homePageObject.navigateToPageUsingAction("My Account", "Logout");
	}
}
