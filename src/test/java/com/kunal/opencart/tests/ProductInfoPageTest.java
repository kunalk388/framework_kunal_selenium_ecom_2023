package com.kunal.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.kunal.opencart.base.BaseTest;

class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoPageSetup() {

		// accountPage = loginPage.doLogin("kunalk388@gmail.com", "kunal@1234");
		accountPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());

	}

	@DataProvider
	public Object[][] getProductImagesTestData() {

		return new Object[][] {

				{ "Macbook", "MacBook Pro" , 4 }, { "iMac", "iMac" , 3 }, { "Apple", "Apple Cinema 30\""  , 6},
				{ "Samsung", "Samsung SyncMaster 941BW" , 1 } };
	} 

	@Test(dataProvider= "getProductImagesTestData")
	public void productImagesCountTest(String serachKey , String productName , int imageCount ) {

		searchPage = accountPage.performSearch(serachKey);

		productInfoPage = searchPage.selectProduct(productName);

		int actualImagesCount = productInfoPage.getProductImagesCount();

		Assert.assertEquals(actualImagesCount, imageCount);
	}
	
	
	@Test
	public void productInfoTest() {
		
		searchPage = accountPage.performSearch("MacBook");
		
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		
		Map<String , String > actualProductInfoMap=productInfoPage.getProductInfo();
		
		//System.out.println(actualProductInfoMap);

		softAssert.assertEquals(actualProductInfoMap.get("Brand"), "Apple");
		
		softAssert.assertEquals(actualProductInfoMap.get("Product Code"), "Product 18");
		
		softAssert.assertEquals(actualProductInfoMap.get("productname"), "MacBook Pro");
		
		softAssert.assertEquals(actualProductInfoMap.get("productPrice"), "$2,000.00");
		
		softAssert.assertAll();
	}
	
	@Test
	public void addToCartTest() {
		
		searchPage = accountPage.performSearch("MacBook");
		
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		
		productInfoPage.enterQuantity(2);
		
		String actualCartMsg =productInfoPage.addProductToCart();
		
		softAssert.assertTrue(actualCartMsg.indexOf("Success")>=0);
		
		softAssert.assertTrue(actualCartMsg.indexOf("MacBook Pro")>=0);
		
		softAssert.assertEquals(actualCartMsg,"Success: You have added MacBook Pro to your shopping cart!");
		
		softAssert.assertAll();
		
	}

}
