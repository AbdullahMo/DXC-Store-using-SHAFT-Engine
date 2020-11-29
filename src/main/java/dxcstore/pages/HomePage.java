package dxcstore.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

import dxcstore.base.PageBase;
import io.qameta.allure.Step;

public class HomePage extends PageBase {

	private WebDriver driver;
	// private Properties properties;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	private By registerLink = By.linkText("Register");
	private By loginLink = By.linkText("Login");
	private By logoutLink = By.xpath("//li/a[contains(text(),'Logout')]");
	private By phonesPDAsLink = By.linkText("Phones & PDAs");

	private By searchBar = By.cssSelector("input.form-control.input-lg");
	private By searchButton = By.cssSelector("button.btn.btn-default.btn-lg");
	private By myAccountBtn = By.cssSelector("a[title='My Account']");

	private By desktopsLink = By.xpath("//a[@class='dropdown-toggle'][contains(text(),'Desktops')]");
	private By showAllDesktops = By.xpath("//a[contains(text(),'Show All Desktops')]");
	private By tabletsLink = By.xpath("//a[contains(text(),'Tablets')]");
	private By mP3Link = By.xpath("//a[@class='dropdown-toggle'][contains(text(),'MP3 Players')]");
	private By showAllMP3 = By.xpath("//a[contains(text(),'Show All MP3 Players')]");

	@Step("Navigating to registeration page step..")
	public void navigateToRegisterationPage() {
		clickOnBtn(driver, myAccountBtn);
		clickOnBtn(driver, registerLink);
	}

	@Step("Navigating to login page step..")
	public void navigateToLoginPage() {
		chooseSecondLevelItem(driver, myAccountBtn, loginLink);

		// clickOnBtn(myAccountBtn);
		// clickOnBtn(loginLink);
	}

	// For login and Register more generic
	public void navigateToPageUsingAction(String firstElement, String secondElement) {
		a = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		a.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'" + firstElement + "')]"))).click()
				.moveToElement(driver.findElement(By.xpath("//a[contains(text(),'" + secondElement + "')]"))).click()
				.build().perform();
	}

	// For items that has no Dropdown
	public void navigateToMenuItemFirstLevel(String menuItem) {
		clickOnBtn(driver, By.xpath("//ul/li/a[contains(text(),'" + menuItem + "')]"));
	}

	public void navigateToTopMenuFirstLevel(String menuItem) {
		clickOnBtn(driver, By.xpath("//span[contains(text(),'" + menuItem + "')]"));
	}

	// For items that has a dropdown
	public void navigateToMenuItemSecondLevel(String menuItem, String secondLvlMenuItem) {
		ElementActions.click(driver, By.xpath("//ul/li/a[contains(text(),'" + menuItem + "')]"));
		ElementActions.click(driver, By.xpath("//a[contains(text(),'" + secondLvlMenuItem + "')]"));
	}

	@Step("Checking that user is logged in step..")
	public String isUserLoggedIn() {
		clickOnBtn(driver, myAccountBtn);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (ElementActions.isElementDisplayed(driver, logoutLink)) {
			ReportManager.log("User is logged in and Logout link is displayed..");
			String logOutLabel = ElementActions.getText(driver, logoutLink);
			ReportManager.log("Logging out..");
			clickOnBtn(driver, logoutLink);
			return logOutLabel;
		}

		return null;
	}

	@Step("Navigating to show all desktops page step..")
	public void navigateToShowAllDesktops() {
		chooseSecondLevelItem(driver, desktopsLink, showAllDesktops);
	}

	@Step("Navigating to Tablets page step..")
	public void navigateToTabletsPage() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(tabletsLink))).click();
	}

	@Step("Entering search word and pressing on Search button step..")
	public void searchForSpecificWord(String word) {
		setTxt(driver, searchBar, word);
		clickOnBtn(driver, searchButton);
	}

	@Step("Clicking on Search Button Step..")
	public void clickOnSearchButton() {
		clickOnBtn(driver, searchButton);
	}

	@Step("Navigating to Phones & PDAs Link")
	public void navigateToPhonesPDAsPage() {
		clickOnBtn(driver, phonesPDAsLink);
	}

	@Step("Navigating to show all desktops page step..")
	public void navigateToShowAllMP3Page() {
		chooseSecondLevelItem(driver, mP3Link, showAllMP3);
	}

}
