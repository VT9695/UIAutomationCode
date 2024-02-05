package Com.qa.OpenCart.Pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Com.qa.OpenCart.Constants.AppConstants;
import Com.qa.OpenCart.Utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String, String> productInfoMap;

	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productDescription = By.cssSelector("div.tab-content");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPricingData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.cssSelector("input#input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMesg = By.cssSelector("div.alert-success");

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeadervalue() {
		String productHeaderValue = eleUtil.doElementGetText(productHeader);
		System.out.println("Product header ::" + productHeaderValue);
		return productHeaderValue;

	}

	public int getProductImgCount() {
		int imgCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_SHORT_TIME_OUT).size();
		System.out.println("The product Images count" + imgCount);
		return imgCount;
	}

	public boolean isProductDescriptionVisible() {
		return eleUtil.waitForElementVisible(productDescription, AppConstants.DEFAULT_SHORT_TIME_OUT).isDisplayed();

	}

	public void enterQuantity(int qty) {
		System.out.println("product Quantity" + qty);
		eleUtil.doSendkeys(quantity, String.valueOf(qty)); // we are using parser which is String.valueOf() bcuse
															// default
															// dosendkeys return string but we aee passing the int
	}

	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMesg = eleUtil.waitForElementVisible(cartSuccessMesg, AppConstants.DEFAULT_SHORT_TIME_OUT)
				.getText();
		StringBuilder sb = new StringBuilder(successMesg);
		String mesg = sb.substring(0, successMesg.length() - 1).replace("\n", "").toString();

		System.out.println("Cart Success mesg is  " + mesg);
		return mesg;
	}

	public Map<String, String> getProductInfo() {

//		Brand: Apple
//		Product Code: Product 18
//		Reward Points: 800
//		Availability: In Stock
		// productInfoMap = new HashMap<String, String>();// Does not maintain the order
		// the way u r adding the value
		productInfoMap = new LinkedHashMap<String, String>();// maintain order the way u r adding the value
		// productInfoMap = new TreeMap<String, String>(); // it maintain the alphabetic
		// order 1st capital , then small then number
		// also says that sorting manner

		// if u really want to be in same order as u have added or shown in app use
		// LinkedHashMap
		// dont bother about the order go with hashmap

		// header
		productInfoMap.put("productname", getProductHeadervalue());
		getProductMetaData();
		getproductPriceData();
		System.out.println(productInfoMap);
		return productInfoMap;
	}

	// fetching metadata
	private void getProductMetaData() {
		// metadata
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaList) {
			String meta = e.getText();
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}

	}

	private void getproductPriceData() {
		// priceData
//		$2,000.00   //0th elemnt
//		Ex Tax: $2,000.00    //1st element
		List<WebElement> priceList = eleUtil.getElements(productPricingData);
		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String extaxValue = exTax.split(":")[1].trim();

		productInfoMap.put("productprice", price); // if the key is not avaible so create the custom keys "productprice"
													// ,"exTaxWithPrice"
		productInfoMap.put("exTaxWithPrice", extaxValue);

	}

}
