package PlaywrightTest;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import Utility_Pack.ReusableDetails;

public class BaseClass {
	Playwright playwright;
	Browser browser;
	BrowserContext context;
	Page page;

	@BeforeTest
	public void setup() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

		context = browser.newContext(); // Create new context before each test
		page = context.newPage();
		page.navigate("https://uat.startupindia.gov.in/bhaskar/");
		page.locator("//button[@class='styles_registerBtn__SNvQW']//img").click();
		page.locator("//input[@id='firstName']").fill("Somesh");
		page.locator("//input[@id='lastName']").fill("Landge");
		page.locator("//select[@id='nationality']").selectOption("Indian");
		page.locator("//input[@id='emailId']").fill(ReusableDetails.reusableEmail());
		page.locator("//input[@id='mobileNumber']").fill(ReusableDetails.reusableMobile());
		page.locator("//input[@id='password']").fill("Test@123");
		page.locator("//input[@id='confirmPassword']").fill("Test@123");
		page.locator("//button[normalize-space()='Generate OTP for Email & Mobile']").click();
		page.locator("//input[@placeholder='Email OTP']").fill("AA4488");
		page.locator("//button[normalize-space()='Verfiy Email OTP']").click();
		page.locator("//input[@placeholder='Mobile OTP']").fill("AA4488");
		page.locator("//button[normalize-space()='Verify Mobile OTP']").click();
		page.locator("//button[normalize-space()='Get your BHASKAR ID']").click();
	}

	@AfterTest
	public void teardown() {
		if (browser != null) {
			browser.close(); // Close the browser at the end of all tests
		}
		if (playwright != null) {
			playwright.close(); // Close Playwright at the end
		}
	}

}
