package Com.qa.OpenCart.Base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import Com.qa.OpenCart.Factory.DriverFactory;
import Com.qa.OpenCart.Pages.AccountPage;
import Com.qa.OpenCart.Pages.LoginPage;
import Com.qa.OpenCart.Pages.ProductInfoPage;
import Com.qa.OpenCart.Pages.RegisterPage;
import Com.qa.OpenCart.Pages.SearchPage;

public class BaseTest {

	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage loginpage;
	protected AccountPage accPage;
	protected SearchPage searchPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	protected SoftAssert softAssert;

	@BeforeTest
	public void setUp() {
		df = new DriverFactory();
		prop = df.initProp();
		driver = df.initilizeDriver(prop);
		loginpage = new LoginPage(driver);
		softAssert = new SoftAssert();

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
