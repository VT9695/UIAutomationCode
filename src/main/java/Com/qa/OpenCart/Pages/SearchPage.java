package Com.qa.OpenCart.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Com.qa.OpenCart.Constants.AppConstants;
import Com.qa.OpenCart.Utils.ElementUtil;

public class SearchPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By searchProductResults = By.cssSelector("div#content div.product-layout");

	public SearchPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public int getSearchProductsCount() {
		int productCount = eleUtil.waitForElementsVisible(searchProductResults, AppConstants.DEFAULT_MEDIUM_TIME_OUT)
				.size();
		System.out.println("product count ::::" + productCount);
		return productCount;
	}

	public ProductInfoPage selectProduct(String productName) {
		// create dynamic locator
		By productLocator = By.linkText(productName);
		eleUtil.waitForElementVisible(productLocator, AppConstants.DEFAULT_MEDIUM_TIME_OUT).click();
		return new ProductInfoPage(driver);

	}

}
