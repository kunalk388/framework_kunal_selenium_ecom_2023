package com.kunal.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.kunal.opencart.constants.AppConstants;
import com.kunal.opencart.utils.ElementUtils;

public class RegisterPage {
	
	private WebDriver driver;
	private ElementUtils elementutils;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");

	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");

	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']/input[@type='radio']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']/input[@type='radio']");

	private By registerSuccessMesg = By.cssSelector("div#content h1");

	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		elementutils= new ElementUtils(driver); 
	}
	
	public boolean registerUser(String firstname, String lastname , String email, String telephone, String password , String subscribe) {
		
		elementutils.waitForElementVisible(this.firstName, AppConstants.DEFAULT_SHORT_TIME_OUT).sendKeys(firstname);
		
		elementutils.doSendKeys(this.lastName, lastname);
		
		elementutils.doSendKeys(this.email, email);
	
		elementutils.doSendKeys(this.telephone, telephone);
		
		elementutils.doSendKeys(this.password, password);
		
		elementutils.doSendKeys(this.confirmpassword, password);
		
		if(subscribe.equalsIgnoreCase("Yes")) {
			
			elementutils.doClick(subscribeYes);
		}
		else
		{
			elementutils.doClick(subscribeNo);
		}
		
		elementutils.doClick(agreeCheckBox);
		
		elementutils.doClick(continueButton);
		
		String successMsg=elementutils.waitForElementVisible(registerSuccessMesg, AppConstants.DEFAULT_MEDIUM_TIME_OUT).getText();
		System.out.println("User registration Success message :: " +successMsg);
		
		if(successMsg.contains(AppConstants.USER_REG_SUCCESS_MESSAGE)) {
			
			
			elementutils.doActionsCick(logoutLink);
			elementutils.doClick(registerLink);
			
			return true;
		}
		
		return false;
	}

}
