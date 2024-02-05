package Com.qa.OpenCart.Pages.Test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Com.qa.OpenCart.Base.BaseTest;
import Com.qa.OpenCart.Constants.AppConstants;
import Com.qa.OpenCart.Utils.ExcelUtil;

public class ProductPageInfoTest extends BaseTest {

	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProducImagestTestData() {
		return new Object[][] { { "macBook", "MacBook Pro", 4 }, { "iMac", "iMac", 3 },
				{ "Apple", "Apple Cinema 30\"", 6 }, { "Samsung", "Samsung SyncMaster 941BW", 1 },

		};
		// in object array we can store any type of data
		// in case if in future if seller is sayin g add some images so we can update
		// the count in that dataprovider
	}

	@Test(dataProvider = "getProducImagestTestData")
	public void productImagesCountTest(String searchkey, String productName, int imgCount) {
		searchPage = accPage.performSearch(searchkey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImgCount();
		Assert.assertEquals(actImagesCount, imgCount);
	}

//	@DataProvider
//	public Object[][] getProducDescriptiontTestData() {
//		return new Object[][] { { "macBook", "MacBook Pro" }, { "iMac", "iMac" }, { "Apple", "Apple Cinema 30\"" },
//				{ "Samsung", "Samsung SyncMaster 941BW" },
//
//		};
//	}

	// OR
	@DataProvider
	public Object[][] getProductsearchandSelectTestData() {
		Object searchSelectData[][] = ExcelUtil.getTestData(AppConstants.PRODUCT_SEARCH_AND_SELECTION);
		return searchSelectData;
	}

	@Test(dataProvider = "getProductsearchandSelectTestData")
	public void productDescriptionvisiblityTest(String searchkey, String productName) {
		searchPage = accPage.performSearch(searchkey);
		productInfoPage = searchPage.selectProduct(productName);
		Assert.assertTrue(productInfoPage.isProductDescriptionVisible());
	}

	@Test
	public void productInfoTest() {
		searchPage = accPage.performSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> actproductInfoMap = productInfoPage.getProductInfo();
		softAssert.assertEquals(actproductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actproductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actproductInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(actproductInfoMap.get("productprice"), "$2,000.00");

		softAssert.assertAll();
		// for multiple things go with SoftAssert mandatory to right above

	}

//	@DataProvider
//	public Object[][] addTocartTestData() {
//		return new Object[][] { { "macBook", "MacBook Pro" }, { "iMac", "iMac" },
//				{ "Samsung", "Samsung SyncMaster 941BW" }
//
//		};
//	}

	@Test(dataProvider = "getProductsearchandSelectTestData")
	public void addToCartTest(String searchkey, String productName) {
		searchPage = accPage.performSearch(searchkey);
		productInfoPage = searchPage.selectProduct(productName);
		productInfoPage.enterQuantity(2);
		String actCartMesg = productInfoPage.addProductToCart();

		// Success: You have added MacBook Pro to your shopping cart!
		softAssert.assertTrue(actCartMesg.indexOf("Success") >= 0);
		softAssert.assertTrue(actCartMesg.indexOf(productName) >= 0);

		softAssert.assertEquals(actCartMesg, "Success: You have added " + productName + " to your shopping cart!");

		softAssert.assertAll();
	}
}
