package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.HashMap;
import java.util.Map;

/*public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver initDriver(String browser) {
        if(driver == null) {
            if(browser.equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            else {
                driver = new EdgeDriver();
            }
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static void  quitDriver() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }
}*/



public class DriverFactory {

    // Thread-safe driver for parallel execution
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver initDriver(String browser) {
        if(driverThread.get() == null) {
            if(browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);

                options.setExperimentalOption("prefs", prefs);

                // disable automation popups
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-save-password-bubble");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-extensions");

                // VERY IMPORTANT → start with fresh profile
                options.addArguments("--guest");


                driverThread.set(new ChromeDriver(options));
            }
            else if(browser.equalsIgnoreCase("edge")) {
                //WebDriverManager.edgedriver().setup();
                driverThread.set(new EdgeDriver());
            }
            else {
                throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            driverThread.get().manage().window().maximize();
        }

        return driverThread.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();

        if(driver != null) {
            try {
                driver.quit();
            }
            catch(Exception e) {
                System.out.println("driver.quit() failed: " + e.getMessage());
            }
            finally {
                driverThread.remove(); // remove driver from ThreadLocal
            }
        }
    }
}
