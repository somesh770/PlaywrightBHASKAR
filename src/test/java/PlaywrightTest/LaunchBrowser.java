package PlaywrightTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class LaunchBrowser {

	Playwright playwright;
	Page page;
	Browser browser;

	@BeforeMethod
	public void setup() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
		page = browser.newPage();

	}

	@AfterMethod
	public void teardown() {
		page.close();
		browser.close();
		playwright.close();

	}

	@Test
	public void InputAndButtons() {

		page.navigate("https://ecommerce-playground.lambdatest.io/index.php");

		page.hover("//a[@role='button']//span[@class='title'][normalize-space()='My account']");
		page.click("//span[normalize-space()='Login']");

		assertThat(page.title()).isEqualTo("Account Login");

		page.fill("//input[@id='input-email']", "somesh@gmail.com");

		page.fill("//input[@id='input-password']", "Test@123");

		page.click("//input[@value='Login']");

		assertThat(page.title()).isEqualTo("My Account");

	}

	@Test
	public void HandellingFramesINPlaywright() {
		page.navigate("https://letcode.in/frame");
	    String  MainFramText = 	page.textContent("//h1[normalize-space()='Frame']");
	    System.out.println(MainFramText);
	  if(MainFramText == " Frame")
	  {
		  System.out.println("user is on main frame");
		 
	  }
	  else System.out.println("user is not on main frame");

	}

}
