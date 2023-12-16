package com.kunal.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.kunal.opencart.constants.AppConstants;
import com.kunal.opencart.utils.ElementUtils;

public class SearchPage {

	private WebDriver driver;
	private ElementUtils elementutils;

	private By searchProductResults = By.cssSelector("div#content div.product-layout");

	public SearchPage(WebDriver driver) {

		this.driver = driver;
		elementutils = new ElementUtils(driver);
	}

	public int getSearchProductCount() {
		
		int productCount = elementutils.waitForElementsVisible(searchProductResults, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Product Count ====>> " + productCount);
		return productCount;
	}

	public ProductInfoPage selectProduct(String productName) {

		By productLocator = By.linkText(productName);

		elementutils.waitForElementVisible(productLocator, AppConstants.DEFAULT_MEDIUM_TIME_OUT).click();

		return new ProductInfoPage(driver);
	}

}
