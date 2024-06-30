package co.wedevx.digitalbank.automation.ui.utils;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Driver {

    private static WebDriver driver;

    private Driver(){

    }

    public static WebDriver getDriver(){
        if (driver == null) {
            String browser = ConfigReader.getPropertiesValue("digitalbank.ui.browser");
            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                case "headless":
                    ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("disable-extensions");
                    options.setExperimentalOption("useAutomationExtension", false);
                    options.addArguments("--proxy-server='direct://'");
                    options.addArguments("--proxy-bypass-list=*");
                    options.addArguments("--start-maximized");
                    options.addArguments("--headless");
                    driver=new ChromeDriver(options);
                    break;
                case "saucelabs":
                    String platform = ConfigReader.getPropertiesValue("dbank.saucelabs.platfrom");
                    String browserType = ConfigReader.getPropertiesValue("dbank.saucelabs.browser");
                    String browserVersion = ConfigReader.getPropertiesValue("dbank.saucelabs.browser.version");
                    driver = loadSauceLabs(platform,browserType,browserVersion);
                    break;
                case "chrome":
                default:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
            }
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }


    private static WebDriver loadSauceLabs(String platform, String browserType, String browserVersion) {

        String USERNAME = ConfigReader.getPropertiesValue("dbank.saucelabs.username");
        String ACCESS_KEY = ConfigReader.getPropertiesValue("dbank.saucelabs.access_key");

        //setup url to the hub which is running on saucelabs VMs:

        String HOST = ConfigReader.getPropertiesValue("dbank.saucelabs.host");

        //get "Driver creation" from sauce labs my account:
        String url = "https://" + USERNAME + ":" + ACCESS_KEY + "@" + HOST;


        //selenium class:
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //set capabilities to choose OS + browser + their versions to run the tests
        //only these variables will be changed as you need to test on diff OS & browser
        //these variables are passed as arguments in this method (see method signature)
        capabilities.setCapability("platformName",platform);
        capabilities.setCapability("browserName",browserType);
        capabilities.setCapability("browserVersion",browserVersion);

        //setup remote driver:
        //throws MalformedURLException => add to method signature
        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(url), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;
    }

    public static void takeScreenshot(Scenario scenario){
        if(scenario.isFailed()){
            //taking screenshot
            final byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            //adding screenshots to report
            scenario.attach(screenshot,"image/png", "screenshot");
            saveScreenshotToFile(screenshot);
        }
    }

    private static void saveScreenshotToFile(byte[] screenshot) {
        String pattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        // Use SimpleDateFormat class, create a variable dateFormat with date format needed instead of this comment
        // Below code line will use the variable created above to convert the date into String
        String timestamp = dateFormat.format(new Date());

        // Replace SCREENSHOT_DIRECTORY with desired folder to keep screenshots in
        String Screenshot_Directory = "src/test/resources/Screenshot_Directory";;
        String fileName = Screenshot_Directory + File.separator + "screenshot_" + timestamp + ".png";
        Path filePath = Paths.get(fileName);

        // Below code saves the screenshot as file if the format is correct
        try {
            Files.write(filePath, screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeDriver(){
        if (driver != null) {
            driver.quit();
            driver = null;
        }

    }

}
