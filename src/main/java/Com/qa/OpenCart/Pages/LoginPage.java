package Com.qa.OpenCart.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Com.qa.OpenCart.Constants.AppConstants;
import Com.qa.OpenCart.Utils.ElementUtil;
import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleutil;

	// 1. private By locator first create
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@type='submit']");
	private By ForgotPassLink = By.linkText("Forgotten Password");
	private By Logo = By.cssSelector("img.img-responsive");
	private By FooterLinks = By.cssSelector("div.container div.row li a");
	private By registerLink = By.linkText("Register");

	// 2. Page Constructor
	// whenever u create any pages every page is having its own driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	// 3. Page Actions/methods

	@Step("......getting Login page Title")
	public String getPageTitle() {
		// String title = driver.getTitle();
		String complteTitle = eleutil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("The Login Page Title" + complteTitle);
		return complteTitle;
	}

	@Step("......getting Login page URL")
	public String getLoginPageUrl() {
		String URL = eleutil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println("The URL is " + URL);
		return URL;
	}

	@Step("......getting Forgot password link")

	public boolean isForgotPassLinkExisted() {

		// return driver.findElement(ForgotPassLink).isDisplayed();
		return eleutil.waitForElementVisible(FooterLinks, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	@Step("login with the Username:{0} and password:{1}")

	public AccountPage doLogin(String Un, String Pass) {
		System.out.println("App Cred are " + Un + ":::" + Pass);
		eleutil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(Un);
		eleutil.doSendkeys(password, Pass);
		eleutil.doClick(loginBtn);
		return new AccountPage(driver);
	}

	@Step("......Checking the logo is visible on the login oage")

	public boolean isLogoVisible() {
		return eleutil.waitForElementVisible(Logo, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	@Step("......Navigate to the register page ")

	public RegisterPage navigateToRegisterPage() {
		eleutil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
