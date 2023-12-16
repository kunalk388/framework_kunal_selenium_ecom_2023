package com.kunal.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.kunal.opencart.base.BaseTest;
import com.kunal.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 1 : design login for OpenCart")

@Story("User Login")

public class LoginPageTest extends BaseTest {

	@Severity(SeverityLevel.TRIVIAL)
	@Description("Getting Title of the Page")
	@Test(priority = 1)
	public void loginPageTitleTest() {

		String actualTitle = loginPage.getLoginpageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}

	@Severity(SeverityLevel.NORMAL)
	@Description("Getting url of the Page")
	@Test(priority = 2)

	public void loginPageUrlTest() {

		String actualUrl = loginPage.getLoginpageURL();
		Assert.assertTrue(actualUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("checking forgot password link")
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());

	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("User able to login with correct username and password")
	@Test(priority = 4)
	public void logintest() {
		// accountPage = loginPage.doLogin("kunalk388@gmail.com", "kunal@1234");
		accountPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accountPage.isLogoutLinkExist());

	}
}
