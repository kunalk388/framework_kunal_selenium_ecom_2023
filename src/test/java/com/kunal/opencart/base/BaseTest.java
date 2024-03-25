package com.kunal.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.kunal.opencart.factory.DriverFactory;
import com.kunal.opencart.pages.AccountsPage;
import com.kunal.opencart.pages.Loginpage;
import com.kunal.opencart.pages.ProductInfoPage;
import com.kunal.opencart.pages.RegisterPage;
import com.kunal.opencart.pages.SearchPage;

public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected Loginpage loginPage; //protected means child class of same or different package can use this.(can used public also and anyone can use this 
	protected AccountsPage accountPage;
	protected SearchPage searchPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	
	protected SoftAssert softAssert ;
	
	@Parameters({"browser","browserversion","testcasename"})
	@BeforeTest
	public void setup(String browserName , String browserVersion,String testCaseName) {
		df=new DriverFactory();
		prop=df.initProp();
		if(browserName!=null) {
			prop.setProperty("browser", browserName);
			prop.setProperty("browserversion", browserVersion);
			prop.setProperty("testcasename", testCaseName);
		}
		driver=df.initDriver(prop);
		//driver=df.initDriver("Chrome");
		loginPage=new Loginpage(driver);
		
		softAssert = new SoftAssert();
		
	}
	
	@AfterTest
	public void tearDown() {
		
		driver.quit();
	}

}
