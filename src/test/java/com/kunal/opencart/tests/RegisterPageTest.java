package com.kunal.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.kunal.opencart.base.BaseTest;
import com.kunal.opencart.constants.AppConstants;
import com.kunal.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass

	public void regPageSetup() {

		registerPage = loginPage.nevigateToRegister();
	}
	
	public String getRandomEmail() {
		
		Random random= new Random();
		
		//String email ="automation@" + random.nextInt(1000) +"gmail.com";
		
		String email ="automation@" + System.currentTimeMillis() +"gmail.com";
		
		return email ;
		
	}

	@DataProvider

	public Object[][] getRegTestData() {

		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}

	@Test(dataProvider = "getRegTestData")
	public void userRegTest(String firstname, String lastname, String telephone, String password,
			String subscribe) {

		// registerPage.registerUser("eygv", "fmjb", "tkjad@gmail.com", "9898989898",
		// "nhy@123", "yes");
		
		Assert.assertTrue(registerPage.registerUser(firstname, lastname, getRandomEmail(), telephone, password, subscribe));
	}

}
