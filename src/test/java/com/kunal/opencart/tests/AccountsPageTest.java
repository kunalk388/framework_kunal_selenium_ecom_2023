package com.kunal.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.kunal.opencart.base.BaseTest;
import com.kunal.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accountsPageSetup() {

		// accountPage = loginPage.doLogin("kunalk388@gmail.com", "kunal@1234");
		accountPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());

	}

	@Test

	public void accountsPageTitleTest() {

		String actTitle = accountPage.getAccountsPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
	}

	@Test
	public void accountsPageUrlTest() {

		String actUrl = accountPage.getAccountsPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE));

	}

	@Test
	public void isLogoutLinkExistTest() {

		Assert.assertTrue(accountPage.isLogoutLinkExist());
	}

	@Test
	public void accountsPageHeaderCountTest() {
		List<String> actualAccountPageHeaderList = accountPage.getAccountsPageHeadersList();
		System.out.println("Account Page Header List " + actualAccountPageHeaderList);
		Assert.assertEquals(actualAccountPageHeaderList.size(), AppConstants.ACCOUNT_PAGE_HEADERS_COUNT);

	}

	@Test

	public void accountsPageHeaderValueTest() {
		List<String> actualAccountPageHeaderList = accountPage.getAccountsPageHeadersList();
		System.out.println("Actual Account Page Header List " + actualAccountPageHeaderList);
		System.out.println("Expected Account Page Header List " + AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
		Assert.assertEquals(actualAccountPageHeaderList, AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);

	}

	@DataProvider
	public Object[][] getProductData() {

		return new Object[][] {

				{ "Macbook" }, { "iMac" }, { "Apple" }, { "Samsung" } };
	}

	@Test(dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {

		searchPage = accountPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductCount() > 0);

	}

	@DataProvider
	public Object[][] getProductTestData() {

		return new Object[][] {

				{ "Macbook", "MacBook Air" }, { "Macbook", "MacBook Pro" }, { "iMac", "iMac" },
				{ "Apple", "Apple Cinema 30\"" }, { "Samsung", "Samsung SyncMaster 941BW" },
				{ "Samsung", "Samsung Galaxy Tab 10.1" } };
	}

	@Test(dataProvider="getProductTestData")
	public void selectProductTest(String searchKey , String productName) {

		searchPage = accountPage.performSearch(searchKey);

		if (searchPage.getSearchProductCount() > 0) {

			productInfoPage = searchPage.selectProduct(productName);
			String actualProductHeader = productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actualProductHeader, productName);

		}

	}

}
