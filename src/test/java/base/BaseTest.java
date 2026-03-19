package base;

import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class BaseTest {

    protected WebDriver driver;
    public Logger logger;
    public Properties prop;
    public ChromeOptions chromeOptions;
    public EdgeOptions edgeOptions;
    public DesiredCapabilities capibilities;
    InputStream input;
    @Parameters({"browser", "os"})
    @BeforeMethod(groups = {"Sanity", "Master", "Regression", "DataDriven"})
    public void setup(String browser, String os) throws IOException {

        prop = new Properties();
        input = getClass().getClassLoader().getResourceAsStream("config.properties");
        prop.load(input);

        logger = LogManager.getLogger(this.getClass());

         if (prop.getProperty("execution_env").equalsIgnoreCase("local")){

            driver = DriverFactory.initDriver(browser);
            driver.manage().deleteAllCookies();
            driver.get("http://localhost/opencart/upload/");
        }
        else {
            //capibilities = new DesiredCapabilities();
            //capibilities.setPlatform(Platform.WIN11);
            //capibilities.setBrowserName("chrome");

            /*if (os.equalsIgnoreCase("windows")) {
                capibilities.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("mac")) {
                capibilities.setPlatform(Platform.MAC);
            } else {
                System.out.println("No matching os");
                return;
            }*/

            if (browser.equalsIgnoreCase("chrome")) {
                //capibilities.setBrowserName("chrome");
                chromeOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);

                chromeOptions.setExperimentalOption("prefs", prefs);

                // disable automation popups
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-save-password-bubble");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-extensions");
                //chromeOptions.setPlatformName(os);
                // VERY IMPORTANT → start with fresh profile
                chromeOptions.addArguments("--guest");

                driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);
                driver.get("http://localhost/opencart/upload/");
                //driver.get("http://host.docker.internal/opencart/upload/");
                driver.manage().window().maximize();
            } else {
                //capibilities.setBrowserName("edge");

                edgeOptions = new EdgeOptions();

                // Disable password manager & credentials
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);

                edgeOptions.setExperimentalOption("prefs", prefs);

                // Disable automation popups / notifications
                edgeOptions.addArguments("--disable-notifications");
                edgeOptions.addArguments("--disable-save-password-bubble");
                edgeOptions.addArguments("--disable-infobars");
                edgeOptions.addArguments("--disable-extensions");
                //edgeOptions.setPlatformName(os);
                // Start with fresh profile
                edgeOptions.addArguments("--guest");


                driver = new RemoteWebDriver(new URL("http://localhost:4444"), edgeOptions);
                driver.get("http://localhost/opencart/upload/");
                driver.manage().window().maximize();
            }

        }
    }

    @AfterMethod(groups = {"Sanity", "Master", "Regression", "DataDriven"}, alwaysRun = true)
    public void tearDown() throws IOException {
        //DriverFactory.quitDriver();
        input.close();
        if (driver != null) {
            if (prop.getProperty("execution_env").equalsIgnoreCase("remote")){
                driver.quit();
                driver = null;
            }
            else {
                DriverFactory.quitDriver();
                driver = null;
            }
        }
    }

    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + " " + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

}
