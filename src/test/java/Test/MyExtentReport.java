package Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Operation.ReadObject;
import Operation.UIOperation;

public class MyExtentReport {
	public ExtentHtmlReporter htmlreporter = null;
	public ExtentReports extent = null;
	public ExtentTest test = null;

	WebDriver webdriver;
	Properties allObjects;
	UIOperation operation;

	public void initiateDriver(String Browser) throws Exception {
		switch (Browser.toUpperCase()) {
		case "CHROME":
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\java\\Drivers\\chromedriver.exe");
			ChromeOptions options1 = new ChromeOptions();

			Map<String, Object> prefs = new HashMap<String, Object>();
			Map<String, Object> langs = new HashMap<String, Object>();
			langs.put("ru", "en");
			prefs.put("translate", "{'enabled' : true}");
			prefs.put("translate_whitelists", langs);
			options1.setExperimentalOption("prefs", prefs);

			webdriver = new ChromeDriver(options1);
			break;

		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\test\\java\\Drivers\\geckodriver.exe");
			FirefoxOptions Options11 = new FirefoxOptions();
			Options11.addPreference("media.navigator.permission.disabled", true);
			webdriver = new FirefoxDriver(Options11);
			break;
		case "OPERA":
			System.setProperty("webdriver.opera.driver",
					System.getProperty("user.dir") + "\\src\\test\\java\\Drivers\\operadriver.exe");
			OperaOptions oo1 = new OperaOptions();
			oo1.addArguments("use-fake-ui-for-media-stream");
			webdriver = new OperaDriver(oo1);
			break;
		}
	}

	@BeforeTest()
	public void setReport() throws Exception {
		htmlreporter = new ExtentHtmlReporter("WordPress_Test_Results.html");

		htmlreporter.config().setEncoding("utf-8");
		htmlreporter.config().setDocumentTitle("OrangeHRM Automation");
		htmlreporter.config().setReportName("Automation Results");
		htmlreporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(htmlreporter);

		// Chrome Driver
		ReadObject object = new ReadObject();
		allObjects = object.getObjectRepository();

		this.initiateDriver(allObjects.getProperty("Browser"));

		operation = new UIOperation(webdriver);

	}

	@AfterTest
	public void close() throws InterruptedException {
		extent.flush();
		Thread.sleep(3000);
		webdriver.close();

	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		String logText;

		if (result.getStatus() == ITestResult.FAILURE) {
			Object[] param = result.getParameters();
			String describe = param[5].toString();
			if (!describe.isEmpty()) {
				logText = "<b>" + describe + "</b>";

				Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
				test.fail(m);
			}
		} else if (result.getStatus() == ITestResult.SUCCESS) {

			Object[] param = result.getParameters();
			String describe = param[5].toString();
			if (!describe.isEmpty()) {
				logText = "<b>" + describe + "</b>";

				Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
				test.pass(m);
			}
		} else if (result.getStatus() == ITestResult.SKIP) {
			Object[] param = result.getParameters();
			String describe = param[5].toString();
			if (!describe.isEmpty()) {
				logText = "<b>" + describe + "</b>";

				Markup m = MarkupHelper.createLabel(logText, ExtentColor.BLUE);
				test.skip(m);
			}
		}
	}

}
