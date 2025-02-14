package BHASKAR_login;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.WaitForSelectorOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitForSelectorState;

public class IN_edit_StartupFounder {

	Playwright playwright;
	Browser browser;
	BrowserContext context;
	Page page;

	@BeforeMethod
	public void createContext() {

		playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		context = browser.newContext(); // Create new context before each test
		page = context.newPage();
		page.navigate("https://uat.startupindia.gov.in/bhaskar");
		page.locator("//button[normalize-space()='Login']").click();

		page.locator("//input[@name='username']").fill("In.founder01@gmail.com");
		page.locator("//input[@name='password']").fill("Test@123");
		page.locator("(//button[text()='Login'])[2]").click();

		Locator bHASKARidLocator = page.locator("//div[@class='pfofile_startupFounder__yK3Xm']/h4");
		bHASKARidLocator.waitFor();
		System.out.println("Generated BHASKAR ID: " + bHASKARidLocator.textContent());

		Locator ProfileCompletedTAG = page.locator("//h3[normalize-space()='Profile Completed']");
		ProfileCompletedTAG.waitFor();
		System.out.println("User Redirected to profile - " + ProfileCompletedTAG.isVisible());
	}

	@AfterMethod
	public void closeContext() {
		if (page != null) {
			page.close(); // Close the page after each test
		}
		if (context != null) {
			context.close(); // Close the context after each test
		}

		if (browser != null) {
			browser.close(); // Close the browser at the end of all tests
		}
		if (playwright != null) {
			playwright.close(); // Close Playwright at the end
		}
	}

//======================================================================================	

	@SuppressWarnings("deprecation")
	@Test
	public void StartupFounder_EditProfessioanlHeadline() {

		page.locator("(//div[@class='pfofile_functionalHeading__KCIqX']//button[@class='pfofile_editBtn__vd_Ij'])[1]")
				.click();
		System.out.println("Clicked on edit icon");
		Locator popupLocator = page.locator("//div[@class='modal-content pfofile_professionalMin__a_5El']");
		if (popupLocator.isVisible()) {
			System.out.println("Modal is visible!");
		} else {
			System.out.println("Modal is not visible!");
		}
		page.waitForSelector(".modal-content pfofile_professionalMin__a_5El",
				new WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		System.out.println("wait for popup");
		page.locator("div.hundredPos textarea").waitFor();
		System.out.println("wait for test area");
		page.locator("div.hundredPos textarea").clear();
		System.out.println("clear text");
		page.locator("div.hundredPos textarea").type("Test");
		System.out.println("Enter text");
		page.locator("//button[normalize-space()='Save']").click();

	}

}
