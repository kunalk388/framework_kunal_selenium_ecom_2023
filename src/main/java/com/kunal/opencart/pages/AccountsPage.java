package com.kunal.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.kunal.opencart.constants.AppConstants;
import com.kunal.opencart.utils.ElementUtils;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtils elementutils;

	private By logoutLink = By.linkText("Logout");
	private By accountsHeader = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon= By.cssSelector("#search button");

	public AccountsPage(WebDriver driver) {

		this.driver = driver;
		elementutils = new ElementUtils(driver);
	}

	public String getAccountsPageTitle() {

		String title = elementutils.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
		System.out.println("Accounts page title is ----:" + title);
		return title;

	}

	public String getAccountsPageUrl() {

		String url = elementutils.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE);
		System.out.println("Accounts page title is :" + url);
		return url;

	}

	public boolean isLogoutLinkExist() {
		// return driver.findElement(logoutLink).isDisplayed();

		return elementutils.waitForElementVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	public boolean isSerachExist() {
		// return driver.findElement(search).isDisplayed();
		return elementutils.waitForElementVisible(search, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();

	}

	
	// /Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home
	// 2648d858196147e08d56d0cddcc93d56
	public List<String> getAccountsPageHeadersList() {

		List<WebElement> accountHeadersList = elementutils.waitForElementsVisible(accountsHeader,
				AppConstants.DEFAULT_MEDIUM_TIME_OUT);

		// List<WebElement> accountHeadersList = driver.findElements(accountsHeader);
		List<String> accountHeadersValList = new ArrayList<String>();

		for (WebElement e : accountHeadersList) {

			String text = e.getText();
			accountHeadersValList.add(text);
		}

		return accountHeadersValList;
	}
	
	public SearchPage performSearch(String searchKey) {
		
		if(isSerachExist()) {
			
			elementutils.doSendKeys(search, searchKey);
			elementutils.doClick(searchIcon);
			return new SearchPage(driver);
		}
		else {
			
			System.out.println("Search Field is not present on page ---->");
			return null;
		}
	}
}
