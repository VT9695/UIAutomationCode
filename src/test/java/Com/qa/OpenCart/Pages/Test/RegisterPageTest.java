package Com.qa.OpenCart.Pages.Test;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Com.qa.OpenCart.Base.BaseTest;
import Com.qa.OpenCart.Constants.AppConstants;
import Com.qa.OpenCart.Utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regPageSetup() {
		registerPage = loginpage.navigateToRegisterPage();
	}

	public String getRandomEmail() {
		Random random = new Random();
		// String email="Automation"+random.nextInt(1000)+"@gmail.com";
		String email = "Automation" + System.currentTimeMillis() + "@gmail.com";
		
		//this is easy for us to know the user which is created by the Automation engineer
		//we can write sql query >> delete email like %automation
		//delete from table where email like 'automation%'
		
		return email;
	}

	@DataProvider
	public Object[][] getRegisterTestData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}

	@Test(dataProvider = "getRegisterTestData")
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		Assert.assertTrue(
				registerPage.registerUser(firstName, lastName, getRandomEmail(), telephone, password, subscribe));
	}
}
