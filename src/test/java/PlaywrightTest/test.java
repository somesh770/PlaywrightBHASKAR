package PlaywrightTest;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import Utility_Pack.ReusableDetails;

public class test {

	Playwright playwright;
	Browser browser;
	Page page;

	@BeforeTest
	public void setup() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		page = browser.newPage();
		page.navigate("https://uat.startupindia.gov.in/bhaskar/");

	}

	@AfterTest
	public void teardown() {
		// browser.close();
		playwright.close();
	}

	@Test
	public void testone() {

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

		page.locator("//h3[normalize-space()='Individual']").click();
		page.locator("(//div[@class='styles_boxCard__jXGqM'])[1]").click();

		System.out.println("Page Url :" + page.url());

		page.locator("//input[@name='firstName']").fill("Somesh");
		page.locator("//input[@name='lastName']").fill("Landge");
		page.locator("//input[@placeholder='Select year of birth']").fill("2006");
		page.locator("//select[@name='gender']").selectOption("Male");
		page.locator("//select[@placeholder='Please select your country of residence']")
				.selectOption("5f02e38c6f3de87babe20cd2");
		page.locator("//input[@placeholder='Please write the 6 digit pin code of your residence']").fill("110025");
		page.locator("//span[@class='slider round']").click();
		page.locator("//button[normalize-space()='PREVIEW AND SUBMIT']").click();
		page.locator("//label[normalize-space()='I Agree to Terms & Conditions']").click();
		page.locator("//button[normalize-space()='Generate BHASKAR ID']").click();
		
		page.locator("//div[@class='modal-content styles_bhaskerIdmodal__jvSd1']").waitFor();
		page.locator("//div[@class='modal-content styles_bhaskerIdmodal__jvSd1']").click();

		// Now click on the modal content (assuming it's a clickable element, like a
		// close button or any interaction)
		page.locator("//button[normalize-space()='Skip to your Dashboard']").click();

		// Verify if the next step (such as checking if the user is redirected) works
		boolean isVisible = page.locator("//h6[normalize-space()='Startup Founder']").isVisible();
		System.out.println(isVisible);
		// Page popup = context.pages().get(1);
		// popup.locator("//button[normalize-space()='Skip to your
		// Dashboard']").waitFor();
		// boolean isvisble = page.locator("//h6[normalize-space()='Startup
		// Founder']").isVisible();

		// String GeneratedBHASKARID = page.locator("//span[@xpath='1']").textContent();

		// System.out.println(isvisble);

	}

}
