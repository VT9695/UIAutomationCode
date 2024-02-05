package Com.qa.OpenCart.Utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Com.qa.OpenCart.Factory.DriverFactory;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) { // use constructor conecept
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doSendkeys(By locator, String value) {

		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public boolean doElementDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String getAttribute(By locator, String attValue) {
		return getElement(locator).getAttribute(attValue);
	}

	public void getElementAttribute(By locator, String attName) {
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attName);
			System.out.println(attrValue);
		}
	}

	public int getTotalElementsCount(By locator) {
		int eleCount = getElements(locator).size();
		System.out.println("total elements for :" + locator + ">>:>>" + eleCount);
		return eleCount;
	}

	public List<String> getElementsTextList(By locator) {
		List<String> eletextList = new ArrayList<String>();
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			eletextList.add(text);
		}
		return eletextList;
	}

	// *************************SelectBaseDropDownUtils***************//

	public void doSelectDropdownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropdownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectDropdownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public List<WebElement> getDropDownOptionsList(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions();

	}

	public List<String> getDropDownOptionsTextList(By locator) {
		List<WebElement> OptiosList = getDropDownOptionsList(locator);
		List<String> optionsTextList = new ArrayList<String>();
		for (WebElement e : OptiosList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	public void selectDropDownvalue(By locator, String expValue) {
		List<WebElement> optiosList = getDropDownOptionsList(locator);
		for (WebElement e : optiosList) {
			String text = e.getText();
			if (text.equals(expValue)) {
				e.click();
				break;
			}
		}
	}

	public int getTotalDropDownOptions(By locator) {
		int optionCount = getDropDownOptionsList(locator).size();
		System.out.println("Total Options===>>>:" + optionCount);
		return optionCount;
	}

	/******************************
	 * WAIT UTILS /* An expectation for checking that an element is present on the
	 * DOM of a page. This does notnecessarily mean that the element is visible DOM
	 * will updated first then page will loaded
	 */

	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * 
	 * 
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible.Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public List<WebElement> waitForElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * Alert Wait
	 */
	public Alert waitForAlertPresence(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public String getAlertText(int timeout) {
		return waitForAlertPresence(timeout).getText();
	}

	public void acceptAlert(int timeout) {
		waitForAlertPresence(timeout).accept();
	}

	public void dismissAlert(int timeout) {
		waitForAlertPresence(timeout).dismiss();
	}

	public void alertSendkeys(int timeout, String value) {
		waitForAlertPresence(timeout).sendKeys(value);
	}

	// this is for the huge title and putting partial value inside it
	public String waitForTitleContainsAndFetch(int timeout, String titleFractionValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.titleContains(titleFractionValue));
		return driver.getTitle();
	}

	// this is for the huge title
	public String waitForTitleIsAndFetch(int timeout, String titleValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.titleIs(titleValue));
		return driver.getTitle();
	}

	public String waitForURLContainsAndFetch(int timeout, String urlFractionvalue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.urlContains(urlFractionvalue));
		return driver.getCurrentUrl();
	}

	public boolean waitForURLContains(int timeout, String urlFractionvalue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.urlContains(urlFractionvalue));
	}

	/**
	 * FRAME Utilities
	 * 
	 * @param timeout
	 * @param idOrName
	 */

	public void waitForFrameAndSwitchToItByIdOrName(int timeout, String idOrName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	public void waitForFrameAndSwitchToItByIndex(int timeout, int FrameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FrameIndex));
	}

	public void waitForFrameAndSwitchToItByFrameElement(int timeout, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public void waitForFrameAndSwitchToItByFrameLocator(int timeout, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param timeout
	 * @param locator
	 */
	public void clickWhenReady(int timeout, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public WebElement waitForElementToBeClickable(int timeout, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void doClickWithActionsAndWait(int timeout, By locator) {
		WebElement ele = waitForElementToBeClickable(timeout, locator);
		Actions action = new Actions(driver);
		action.click(ele).build().perform();
	}

}
