package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {

    private WebDriverWait wait;
    WebDriver driver;

    public WaitUtil(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElement(By locator, int timeInSeconds) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));

            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element;
    }

    public void waitForTitle(String stringForCheck, int timeInSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
        try {
            wait.until(ExpectedConditions.titleContains(stringForCheck));
        }
        catch(Exception e) {
            System.out.println("Login Failed");

        }

    }

    public WebElement waitForElementToClickable(By locator, int timeInSeconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        return element;
    }
}
