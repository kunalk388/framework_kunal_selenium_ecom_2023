package com.kunal.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.kunal.opencart.constants.AppConstants;
import com.kunal.opencart.utils.ElementUtils;

import io.qameta.allure.Step;

public class Loginpage {

	private WebDriver driver;
	private ElementUtils elementutils;

	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdlink = By.linkText("Forgotten Password");
	private By registerLink= By.linkText("Register");

	// page constructor
	public Loginpage(WebDriver driver) {
		this.driver = driver;
		elementutils= new ElementUtils(driver); 
	}

	// page action/method

	@Step("Getting the login page title")
	public String getLoginpageTitle() {

		//String title = driver.getTitle();
		String title=elementutils.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("Login Page Title : " + title);
		return title;

	}
	@Step("Getting the login page url")
	public String getLoginpageURL() {

		//String url = driver.getCurrentUrl();
		String url=elementutils.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println("Login Page Title : " + url);
		return url;

	}
	
	@Step("Getting the forgot password link")
	public boolean isForgotPwdLinkExist() {
		//return driver.findElement(forgotPwdlink).isDisplayed();
		
		return elementutils.waitForElementVisible(forgotPwdlink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}
	@Step("login with the username :: {0} and password :: {1}")
	public AccountsPage doLogin(String un , String pwd) {
		
//		driver.findElement(emailId).sendKeys(un);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
		System.out.println("App Credential are " + un + ": "  + pwd);
		elementutils.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);;
		elementutils.doSendKeys(password, pwd);
		elementutils.doClick(loginBtn);
		return new AccountsPage(driver);
	}

	@Step("nevigate to register page")
	public RegisterPage nevigateToRegister() {
		
		elementutils.doClick(registerLink);
		return new RegisterPage(driver);
	}
}
