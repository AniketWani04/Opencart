package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementUtil;
import utils.WaitUtil;

import java.time.Duration;

public class MyAccountPage {

    WebDriver driver;
    ElementUtil elementUtil;
    WaitUtil waitUtil;
    WebDriverWait wait;
    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
        waitUtil = new WaitUtil(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By myAccountOption = By.xpath("//span[text() = 'My Account']");
    private By logoutOption = By.xpath("//a[text() = 'Logout' and @class = 'dropdown-item']");
    //private By logoutOption = By.xpath("(//a[text() = 'Logout'])[2]");

    public void doLogout() throws InterruptedException {
        //elementUtil.safeClick(myAccountOption);

        Thread.sleep(2000);
        WebElement element = waitUtil.waitForElementToClickable(myAccountOption, 15);

        element.click();

        //elementUtil.safeClick(logoutOption);

        elementUtil.safeClick(logoutOption);


    }

    public String getTitle() {
        waitUtil.waitForTitle("Account Logout", 10);
        String title = driver.getTitle();
        return title;
    }

}
