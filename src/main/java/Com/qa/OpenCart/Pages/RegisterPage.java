package Com.qa.OpenCart.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Com.qa.OpenCart.Constants.AppConstants;
import Com.qa.OpenCart.Utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;

	private ElementUtil eleUtil;

	// loctors

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");

	private By subscribeYes = By.xpath("//label[@class='radio-inline'][1]");
	private By subscribeNo = By.xpath("//label[@class='radio-inline'][2]");

	private By agreeCheckBox = By.xpath("//input[@name='agree']");
	private By continueBtn = By.xpath("//input[@value='Continue']");

	private By registerSuccessMesg = By.cssSelector("div#content h1");

	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public boolean registerUser(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		eleUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_SHORT_TIME_OUT).sendKeys(firstName);
		eleUtil.doSendkeys(this.lastName, lastName);
		eleUtil.doSendkeys(this.email, email);
		eleUtil.doSendkeys(this.telephone, telephone);
		eleUtil.doSendkeys(this.password, password);
		eleUtil.doSendkeys(this.confirmpassword, password);

		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doActionsClick(subscribeYes);
		} else {
			eleUtil.doActionsClick(subscribeNo);
		}

		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);

		String successMesg = eleUtil.waitForElementVisible(registerSuccessMesg, AppConstants.DEFAULT_MEDIUM_TIME_OUT)
				.getText();
		System.out.println("The User Registration success mesg :::" + successMesg);

		if (successMesg.contains(AppConstants.USER_REGISTER_SUCCESS_MESG)) {

			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		return false;

	}

}
