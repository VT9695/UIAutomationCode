package Com.qa.OpenCart.Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Com.qa.OpenCart.Constants.AppConstants;
import Com.qa.OpenCart.Utils.ElementUtil;

public class AccountPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By logoutLink = By.linkText("Logout");
	private By AccHeaders = By.cssSelector("div#content h2");
	private By search = By.xpath("//input[@name='search']");
	private By searchIcon = By.cssSelector("#search button");

	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// IN LOGIN PAGE CLASS WE ARE RETURNING ACCOUNTPAGE OBJECT SO DRIVER SHOULD BE
	// DECLARED THATS THE REASON WE HAVE CRETED CONSTRUCTOR

	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
		System.out.println("The Title of the Page is ::" + title);
		return title;
	}

	public String getAccPageUrl() {
		String url = eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE);
		System.out.println("The URL of the ACC page is " + url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_SHORT_TIME_OUT).isDisplayed();
	}

	public boolean isSearchExist() {
		return eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_SHORT_TIME_OUT).isDisplayed();
	}

	public List<String> getAccountPageHeaderList() {

		List<WebElement> accHeadersList = eleUtil.waitForElementsVisible(AccHeaders,
				AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		// no need List<WebElement> accHeaderList = driver.findElements(AccHeaders);
		List<String> accHeaderValueList = new ArrayList<String>();

		for (WebElement e : accHeadersList) {
			String text = e.getText();
			accHeaderValueList.add(text);
		}
		return accHeaderValueList;
	}

	public SearchPage performSearch(String SearchKey) {
		if (isSearchExist()) {
			eleUtil.doSendkeys(search, SearchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);

		} else {
			System.out.println("THE SEARCH IS NOT PRESNT ON THE PAGE");
			return null;
		}

	}

}
