package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtil;
public class ElementUtil {

    private JavascriptExecutor js;
    WaitUtil waitUtil;
    WebDriver driver;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        waitUtil = new WaitUtil(driver);
    }
    public void safeClick(By locator) {
        WebElement element = waitUtil.waitForElement(locator, 10);

        try {
            element.click();
        }
        catch (Exception e) {
            js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", element);
        }

    }
}
