package PlaywrightTest;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import Utility_Pack.ReusableDetails;

public class IN_Explorer {
	Playwright playwright;
	Browser browser;
	BrowserContext context;
	Page page;
	String email = ReusableDetails.reusableEmail();

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
		// String email= ReusableDetails.reusableEmail();
		System.out.println("Registreed Email id - " + email);
		page.locator("//input[@id='emailId']").fill(email);
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

	@BeforeMethod
	public void createContext() {

		page.locator("//h3[normalize-space()='Individual']").click();
		page.locator("(//div[@class='styles_boxCard__jXGqM'])[6]").click();
		page.locator("//input[@name='firstName']").fill("Somesh");
		page.locator("//input[@name='lastName']").fill("Landge");
		Locator DOB = page.locator("//input[@placeholder='Select year of birth']");
		DOB.click();
		page.locator("//div[@class='react-datepicker__year-text react-datepicker__year-2006']").click();
		page.locator("//select[@name='gender']").selectOption("Male");
		page.locator("//select[@placeholder='Please select your country of residence']")
				.selectOption("5f02e38c6f3de87babe20cd2");
		page.locator("//input[@placeholder='Please write the 6 digit pin code of your residence']").fill("110025");
		page.locator("//span[@class='slider round']").click();
		page.locator("//button[normalize-space()='PREVIEW AND SUBMIT']").click();
		page.locator("//label[normalize-space()='I Agree to Terms & Conditions']").click();
		page.locator("//button[normalize-space()='Generate BHASKAR ID']").click();
		System.out.println("Clicked on Generate BHASKAR ID CTA on terms and condition page");

		Locator popupLocator = page.locator("//div[@class='modal-content styles_bhaskerIdmodal__jvSd1']"); // .waitFor();
		popupLocator.waitFor();
		page.locator("//div[@class='modal-content styles_bhaskerIdmodal__jvSd1']").click();
		System.out.println("Success popup is displyed");
	}

	@AfterMethod
	public void closeContext() {
		if (page != null) {
			page.close(); // Close the page after each test
		}
		if (context != null) {
			context.close(); // Close the context after each test
		}
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

//====================================================================================
	@Test(priority = 1)
	public void BHASKAR_IN_Explorer_Complete_your_profile() {
		page.locator("//button[normalize-space()='Skip to your Dashboard']").click();
		System.out.println("Clicked on Skip to your Dashboard on success popup");

		Locator bHASKARidLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h4");
		bHASKARidLocator.waitFor();
		System.out.println("Generated BHASKAR ID: " + bHASKARidLocator.textContent());

		Locator ProfileCompletedTAG = page.locator("//h3[normalize-space()='Profile Completed']");
		ProfileCompletedTAG.waitFor();
		System.out.println(ProfileCompletedTAG.isVisible() + " User Redirected to profile");
		
		Locator userRoleLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h6");
		String userRoleONprofileString = userRoleLocator.textContent();
		Assert.assertEquals(userRoleONprofileString, "Explorer");
	}

	@Test(priority = 2)
	public void BHASKAR_IN_Explorer_Skip_to_your_DashboardCTA() {
		page.locator("//button[normalize-space()='Complete your profile']").click();
		System.out.println("Clicked on Complete your profile");

		page.waitForSelector("//button[normalize-space()='Skip to Dashboard']");
		page.locator("//button[normalize-space()='Skip to Dashboard']").click();
		System.out.println("Clicked on Skip to Dashboard");

		Locator bHASKARidLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h4");
		System.out.println("Generated BHASKAR ID: " + bHASKARidLocator.textContent());

		Locator ProfileCompletedTAG = page.locator("//h3[normalize-space()='Profile Completed']");
		ProfileCompletedTAG.waitFor();
		System.out.println(ProfileCompletedTAG.isVisible() + " User Redirected to profile");
		
		Locator userRoleLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h6");
		String userRoleONprofileString = userRoleLocator.textContent();
		Assert.assertEquals(userRoleONprofileString, "Explorer");
	}

	@Test(priority = 3)
	public void BHASKAR_IN_Explorer_Publish_Your_ProfileCTA() {
		page.locator("//button[normalize-space()='Complete your profile']").click();
		System.out.println("Clicked on Complete your profile");
		page.waitForSelector("//button[normalize-space()='Publish Your Profile']");
		page.locator("//button[normalize-space()='Publish Your Profile']").dblclick();
		System.out.println("Double-clicked on Publish Your Profile");

		Locator bHASKARidLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h4");
		System.out.println("Generated BHASKAR ID: " + bHASKARidLocator.textContent());

		Locator ProfileCompletedTAG = page.locator("//h3[normalize-space()='Profile Completed']");
		ProfileCompletedTAG.waitFor();
		System.out.println(ProfileCompletedTAG.isVisible() + " User Redirected to profile");
		
		Locator userRoleLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h6");
		String userRoleONprofileString = userRoleLocator.textContent();
		Assert.assertEquals(userRoleONprofileString, "Explorer");
	}

}
