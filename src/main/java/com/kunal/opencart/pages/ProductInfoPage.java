package com.kunal.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.kunal.opencart.constants.AppConstants;
import com.kunal.opencart.utils.ElementUtils;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtils elementutils;

	private Map<String, String> productInfoMap;
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPricingData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn= By.id("button-cart");
	private By cartSuccessMsg= By.cssSelector("div.alert.alert-success ");

	public ProductInfoPage(WebDriver driver) {

		this.driver = driver;
		elementutils = new ElementUtils(driver);

	}

	public String getProductHeaderValue() {

		String productHeaderValue = elementutils.doElementGetText(productHeader);
		System.out.println("Product Header ====>>>" + productHeaderValue);
		return productHeaderValue;
	}

	public int getProductImagesCount() {

		int imageCount = elementutils.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT)
				.size();
		System.out.println("Product Image count ===>>>" + imageCount);

		return imageCount;

	}

	public Map<String, String> getProductInfo() {

		// productInfoMap = new HashMap<String, String>();
		// productInfoMap = new LinkedHashMap<String, String>();
		productInfoMap = new TreeMap<String, String>();
		// header

		productInfoMap.put("productname", getProductHeaderValue());
		getProductMetaData();
		getProductPriceData();
		System.out.println(productInfoMap);
		return productInfoMap;

	}

	
	public void enterQuantity(int qty) {
		
		System.out.println("Product Quantity : " + qty);
		elementutils.doSendKeys(quantity, String.valueOf(qty));
		
	}
	
	public String addProductToCart() {
		
		
		elementutils.doClick(addToCartBtn);
		String successMsg = elementutils.waitForElementVisible(cartSuccessMsg, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		
		StringBuilder sb = new StringBuilder(successMsg);
		String msg=sb.substring(0,successMsg.length()-1).replace("\n", "").toString();
		System.out.println("the Cart Success Message :: " + msg);
		return msg;
	}
	
	
	// fetching metadata
	private void getProductMetaData() {

		// metadata

		List<WebElement> metaList = elementutils.getElements(productMetaData);

		for (WebElement e : metaList) {
			String meta = e.getText();
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}

	}

	// fetching price data
	private void getProductPriceData() {
		// price

		List<WebElement> priceList = elementutils.getElements(productPricingData);
		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String exTaxVal = exTax.split(":")[1].trim();
		productInfoMap.put("productPrice", price);
		productInfoMap.put("exTax", exTaxVal);

	}

}
