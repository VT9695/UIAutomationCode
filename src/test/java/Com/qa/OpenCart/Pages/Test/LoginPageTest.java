package Com.qa.OpenCart.Pages.Test;

import org.testng.Assert;
import org.testng.annotations.Test;

import Com.qa.OpenCart.Base.BaseTest;
import Com.qa.OpenCart.Constants.AppConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;




@Epic("EPIC-100: Design login from the open cart app")
@Story("US-LOGIN : design login page feature for the open cart app")

public class LoginPageTest extends BaseTest {

	@Severity(SeverityLevel.TRIVIAL)
	@Description(".....Getting the title of the page... Author:tester : vaibhav")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String title = loginpage.getPageTitle();
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}

	@Severity(SeverityLevel.MINOR)
	@Description(".....Checking the Url of the login  page... Author:tester : vaibhav")
	@Test(priority = 2)
	public void loginPageUrlTest() {
		String actUrl = loginpage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description(".....Checking forgot password link exist... Author:tester : vaibhav")
	@Test(priority = 3)
	public void iForgotPassLinkExistedTest() {
		Assert.assertTrue(loginpage.isForgotPassLinkExisted());
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description(".....The user is login to the app with corect username and password.. Author:tester : vaibhav")
	@Test(priority = 4)
	public void loginTest() {
		accPage = loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());

	}

	@Test(priority = 5)
	public void LogoVisiblity() {
		Assert.assertTrue(loginpage.isLogoVisible());
	}

}
