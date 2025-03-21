package PlaywrightTest;

import Utility_Pack.testdata;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import Utility_Pack.PANGenerator;
import Utility_Pack.ReusableDetails;

public class ORG_Startup {
	Playwright playwright;
	Browser browser;
	BrowserContext context;
	Page page;
	Faker faker = new Faker();

	@BeforeTest
	public void setup() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		context = browser.newContext(); // Create new context before each test
		page = context.newPage();
		page.navigate(testdata.getProperty("baseurl"));
		page.locator("//button[@class='styles_registerBtn__SNvQW']//img").click();
		page.locator("//input[@id='firstName']").fill(faker.name().firstName());
		page.locator("//input[@id='lastName']").fill(faker.name().lastName());
		page.locator("//select[@id='nationality']").selectOption("Indian");
		String email = ReusableDetails.reusableEmail();
		System.out.println("Registered emailID - " + email);
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

		page.locator("//h3[normalize-space()='Organization']").click();
		page.locator("(//div[@class='styles_boxCard__jXGqM'])[1]").click();
		String PAN = PANGenerator.generatePAN();
		Locator PANLocator = page.locator("//input[@placeholder='Enter PAN Number']");
		PANLocator.waitFor();
		PANLocator.click();
		PANLocator.type(PAN);
		System.out.println(PAN);
		System.out.println("Entered PAN number" + PAN);
		Locator entityLocator = page.getByPlaceholder("Enter Name of Entity");
		entityLocator.click();
		entityLocator.type("En"+faker.name().fullName());
		System.out.println("Entered Entity name");
		Locator coutryDropLocator = page.getByPlaceholder("Select Country");
		coutryDropLocator.click();
		coutryDropLocator.selectOption("5f02e38c6f3de87babe20cd2");
		System.out.println("Selected country");
		Locator pincodeLocator = page.locator("//input[@placeholder='Enter Pincode']");
		pincodeLocator.click();
		pincodeLocator.fill("110025");
		System.out.println("Added pin code ");
		Locator generalemaiLocator = page.locator("//input[@placeholder='Enter Email Id']");
		generalemaiLocator.click();
		generalemaiLocator.fill("Generalemail@gmail.com");
		System.out.println("Added general email");
		page.locator("//button[normalize-space()='PREVIEW AND SUBMIT']").click();
		System.out.println("Clicked on preview and submit ");
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
		try {
			if (browser != null) {
				browser.close(); // Close the browser at the end of all tests
				System.out.println("Browser context closed.");
			}
			if (playwright != null) {
				playwright.close(); // Close Playwright at the end
				System.out.println("playwright context closed.");
			}
		} catch (Exception e) {
			System.err.println("Error closing context: " + e.getMessage());
		}

	}

//==============================================================================================

	@Test(priority = 1)
	public void BHASKAR_ORG_Startup_Complete_your_profile() {

		page.locator("//button[normalize-space()='Skip to your Dashboard']").click();
		System.out.println("Clicked on Skip to your Dashboard on success popup");

		Locator bHASKARidLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h4");
		bHASKARidLocator.waitFor();
		String BHASKARID = bHASKARidLocator.textContent();
		System.out.println("Generated BHASKAR ID: " + BHASKARID);


		Locator ProfileCompletedTAG = page.locator("//h3[normalize-space()='Profile Completed']");
		ProfileCompletedTAG.waitFor();
		System.out.println(ProfileCompletedTAG.isVisible() + " User Redirected to profile");
		
		Locator userRoleLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h6");
		String userRoleONprofileString = userRoleLocator.textContent();
		Assert.assertEquals(userRoleONprofileString, "Startup");
	}

	@Test(priority = 2)
	public void BHASKAR_ORG_Startup_Skip_to_your_DashboardCTA() {
		page.locator("//button[normalize-space()='Complete your profile']").click();
		System.out.println("Clicked on Complete your profile");

		page.waitForSelector("//button[normalize-space()='Skip to your Dashboard']");
		page.locator("//button[normalize-space()='Skip to your Dashboard']").click();
		System.out.println("Clicked on Skip to Dashboard");

		Locator bHASKARidLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h4");
		String BHASKARID = bHASKARidLocator.textContent();
		System.out.println("Generated BHASKAR ID: " + BHASKARID);

		Locator ProfileCompletedTAG = page.locator("//h3[normalize-space()='Profile Completed']");
		ProfileCompletedTAG.waitFor();
		System.out.println(ProfileCompletedTAG.isVisible() + " User Redirected to profile");
		
		Locator userRoleLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h6");
		String userRoleONprofileString = userRoleLocator.textContent();
		Assert.assertEquals(userRoleONprofileString, "Startup");
	}

	@Test(priority = 3)
	public void BHASKAR_ORG_Startup_Publish_Your_ProfileCTA() {
		page.locator("//button[normalize-space()='Complete your profile']").click();
		System.out.println("Clicked on Complete your profile");

		page.waitForSelector("//button[normalize-space()='Publish your Profile']");
		Locator submitCTALocator = page.locator("//button[normalize-space()='Publish your Profile']");
		submitCTALocator.dblclick();
		System.out.println("Double-clicked on Publish Your Profile");

		Locator bHASKARidLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h4");
		String BHASKARID = bHASKARidLocator.textContent().replace(" " , "");
		System.out.println("Generated BHASKAR ID: " + BHASKARID);

		Locator ProfileCompletedTAG = page.locator("//h3[normalize-space()='Profile Completed']");
		ProfileCompletedTAG.waitFor();
		System.out.println(ProfileCompletedTAG.isVisible() + " User Redirected to profile");
		
		Locator userRoleLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h6");
		String userRoleONprofileString = userRoleLocator.textContent();
		Assert.assertEquals(userRoleONprofileString, "Startup");



	}

	@Test(priority = 4)
	public  void Verify_Profile_Syncing_Into_searchPage()
	{
		page.locator("//button[normalize-space()='Complete your profile']").click();
		System.out.println("Clicked on Complete your profile");

		page.waitForSelector("//button[normalize-space()='Publish your Profile']");
		Locator submitCTALocator = page.locator("//button[normalize-space()='Publish your Profile']");
		submitCTALocator.dblclick();
		System.out.println("Double-clicked on Publish Your Profile");

		Locator bHASKARidLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h4");
		String BHASKARID = bHASKARidLocator.textContent().replace(" " , "");
		System.out.println("Generated BHASKAR ID: " + BHASKARID);

		Locator ProfileCompletedTAG = page.locator("//h3[normalize-space()='Profile Completed']");
		ProfileCompletedTAG.waitFor();
		System.out.println(ProfileCompletedTAG.isVisible() + " User Redirected to profile");

		Locator userRoleLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h6");
		String userRoleONprofileString = userRoleLocator.textContent();
		Assert.assertEquals(userRoleONprofileString, "Startup");

		page.getByRole(AriaRole.NAVIGATION).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Network")).click();
		Page page1 = page.waitForPopup(() -> {
			page.locator("//div[@class='search_companyinnerLogo__5sbfE']").nth(0).click();
			//	page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Magaret Reinger")).click();
		});

		Assert.assertTrue(page1.url().contains(BHASKARID));
	}
}
