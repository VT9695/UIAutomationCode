package Com.qa.OpenCart.Pages.Test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Com.qa.OpenCart.Base.BaseTest;
import Com.qa.OpenCart.Constants.AppConstants;
import Com.qa.OpenCart.Pages.ProductInfoPage;

public class AccountPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password"));
	}

	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
	}

	@Test
	public void accPageURLTest() {
		String actUrl = accPage.getAccPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE));
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void accPageHeaderCountTest() {
		List<String> actualAccpageHeadersList = accPage.getAccountPageHeaderList();
		System.out.println("Account page header List" + actualAccpageHeadersList);
		Assert.assertEquals(actualAccpageHeadersList.size(), AppConstants.ACCOUNT_PAGE_HEADERS_COUNT);
	}

	@Test
	public void accPageHeadersValueTest() {
		List<String> actualAccpageHeadersList = accPage.getAccountPageHeaderList();
		System.out.println("Actual Account page header List" + actualAccpageHeadersList);
		System.out.println("Expected Account page header List" + AppConstants.EXPECTED_ACCOUNT_HEADERS_LIST);
		Assert.assertEquals(actualAccpageHeadersList, AppConstants.EXPECTED_ACCOUNT_HEADERS_LIST);
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] { { "macBook" }, { "iMac" }, { "Apple" }, { "Samsung" },{"vaibhav"} };
	}

	@Test(dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {
		searchPage = accPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductsCount() > 0);
	}

	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] { { "macBook", "MacBook Pro" }, { "macBook", "MacBook Air" }, { "iMac", "iMac" },
				{ "Apple", "Apple Cinema 30\"" }, { "Samsung", "Samsung SyncMaster 941BW" },
				{ "Samsung", "Samsung Galaxy Tab 10.1" },

		};
		
		
	}

	@Test(dataProvider = "getProductTestData")
	public void searchProductTest(String searchKey, String productName) {
		searchPage = accPage.performSearch(searchKey);

		if (searchPage.getSearchProductsCount() > 0) {
			productInfoPage = searchPage.selectProduct(productName);
			String actProductHeader = productInfoPage.getProductHeadervalue();
			Assert.assertEquals(actProductHeader, productName);

		}

	}
}
