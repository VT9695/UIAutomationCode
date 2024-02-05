package Com.qa.OpenCart.Factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	FileInputStream ip;

	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();

	public static String highlight;

	/**
	 * THid method is initilizing the driver on the basis of given broswser name
	 * 
	 * @param browserName
	 * @return this return driver
	 */
	public WebDriver initilizeDriver(Properties prop) {
		optionsManager = new OptionsManager(prop);
		highlight = prop.getProperty("highlight").trim();
		String browserName = prop.getProperty("browser").toLowerCase().trim();

		System.out.println("The Browsername is " + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {

			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

		} else if (browserName.equalsIgnoreCase("firefox")) {

			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));

		} else if (browserName.equalsIgnoreCase("edge")) {
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tldriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		} else {
			System.out.println("please Pass the Right BrowserName" + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();

		getDriver().get(prop.getProperty("url"));

		return getDriver();
	}

	/**
	 * get the local thread copy of the driver
	 */

	public synchronized static WebDriver getDriver() {
		return tldriver.get();
	}

	/**
	 * this method is reading the Properties from .properties file
	 * 
	 * @return
	 */
	public Properties initProp() {

		prop = new Properties();
		FileInputStream ip = null;
		String envName = System.getProperty("env");
		System.out.println("Running test cases on the Env::::" + envName);
		try {
			if (envName == null) {
				System.out.println("NO env is passed :: Running test cases on the QA env....");
				 ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {

				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");

					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");

					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");

					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");

					break;

				default:
					break;
				}
			}
		} catch (FileNotFoundException e) {
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * Takes the screenshot TakeScrenshot is an interface in that we are calling the
	 * getScreenshotAs method and storing the screen as a type File then we have to
	 * locate it in the folder so set the path we use System.getProperty and pass
	 * the key which is "usr.dir" which is current directory then append with the
	 * +"screenshot" so it will create it and appnd withcurentTimemills and pass the
	 * file type where u have to stored the file type which is png, jpeg etc File
	 * destination = new File(path);this file pointing to above path the last step
	 * use FileHandler class with copy method and pass source and destination then
	 * we are returning the path bcuse extent report is waiting for it
	 */
	public static String getScreenshot() {

		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
