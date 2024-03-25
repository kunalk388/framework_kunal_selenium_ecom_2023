package com.kunal.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.kunal.opencart.exception.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionManager optionManger;

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * this method is initialising the driver on the basis of browser name
	 * 
	 * @param browserName
	 * @return return driver
	 */

	public WebDriver initDriver(Properties prop) {

		
		optionManger = new OptionManager(prop);

		highlight = prop.getProperty("highlight").trim();
		String browserName = prop.getProperty("browser").toLowerCase().trim();

		System.out.println("Browser name is: " + browserName);

		if (browserName.equalsIgnoreCase("Chrome")) {

			// Chrome Browser 
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {

				// run on grid/remote

				init_remoteDriver("Chrome");

			} else {
			// Local Execution
				   // driver = new ChromeDriver(optionManger.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionManger.getChromeOptions()));
			}

		}

		// Firefox browser

		else if (browserName.equalsIgnoreCase("firefox")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {

				// run on grid/remote

				init_remoteDriver("firefox");

			} else {
				// driver = new FirefoxDriver(optionManger.getFirefoxOptions());
				tlDriver.set(new FirefoxDriver(optionManger.getFirefoxOptions()));
			}
		}

		else if (browserName.equalsIgnoreCase("safari")) {
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		}

		// Edge browser

		else if (browserName.equalsIgnoreCase("edge")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run on grid/remote
				init_remoteDriver("edge");
			}
			else {
				// driver = new EdgeDriver(optionManger.getEdgeOptions());
				tlDriver.set(new EdgeDriver(optionManger.getEdgeOptions()));
			}
		}

		else {
			System.out.println("please pass right browser name" + browserName);
		}

//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
//		// driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
//		driver.get(prop.getProperty("url"));
//		return driver;
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
		return getDriver();
	}

	/**
	 * this method is called internally to initialise the driver with remote
	 * webDriver
	 * 
	 * @param browser
	 */
	private void init_remoteDriver(String browser) {
		
		System.out.println("Running test on grid server :::::" +browser);
		try {
			switch (browser.toLowerCase()) {

			case "chrome":

				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManger.getChromeOptions()));

				break;
			case "firefox":

				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManger.getFirefoxOptions()));
				break;

			case "edge":

				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManger.getEdgeOptions()));

				break;

			default:
				System.out.println("Please pass right browser name for remote execution " + browser);
				throw new FrameworkException("NOREMOTEBROWSEREXCEPTION");

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the local thread copy of the driver
	 * 
	 * @return
	 */
	// synchronized - every driver get its own respective copy.
	public synchronized static WebDriver getDriver() {

		return tlDriver.get();
	}

	/**
	 * this method is reading the properties from .properties file
	 * 
	 * @return
	 */
	public Properties initProp() {

		prop = new Properties();
		FileInputStream fip = null;
		// mvn clean install -Denv="dev"

		String envName = System.getProperty("env");
		System.out.println("Running test Cases in  ::" + envName);

		try {
			if (envName == null) {

				System.out.println("No Environment is passed----->> Runnning test in QA env.");
				fip = new FileInputStream("./src/main/resources/config/config.properties");
			}
//		prop = new Properties();
//		try {
//			FileInputStream fip = new FileInputStream("./src/main/resources/config/config.properties");
//			prop.load(fip);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

			else {

				switch (envName.toLowerCase().trim()) {

				case "qa":
					fip = new FileInputStream("./src/main/resources/config/config.properties");
					break;
				case "dev":
					fip = new FileInputStream("./src/main/resources/config/dev.config.properties");
					break;

				default:
					System.out.println("Wrong environment is passed . no need to run test cases");
					throw new FrameworkException("Wrong Env Passed .>>>");
				// break;

				}
			}
		} catch (FileNotFoundException e) {

		}
		try {
			prop.load(fip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;

	}

	/**
	 * take screenshot
	 */

	public static String getScreenshot() {

		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";

		File destination = new File(path);

		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return path;
	}

}
