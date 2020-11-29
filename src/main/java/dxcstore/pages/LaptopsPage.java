package dxcstore.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.element.ElementActions;

import dxcstore.base.PageBase;

public class LaptopsPage extends PageBase{
	private WebDriver driver;
	// private Properties properties;

	public LaptopsPage(WebDriver driver) {
		this.driver = driver;
	}
	

	public void addItemToCart(String text) {
		if(ElementActions.isElementDisplayed(driver, By.xpath("//a[contains(text(),'"+ text +"')]"))) {
			ElementActions.click(driver, By.xpath("//a[contains(text(),'"+ text +"')]"));
		}
		
	}
}
