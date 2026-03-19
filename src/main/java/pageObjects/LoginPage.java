package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementUtil;
import utils.WaitUtil;

public class LoginPage {

    WebDriver driver;
    ElementUtil elementUtil;
    WaitUtil waitUtil;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
        waitUtil = new WaitUtil(driver);
    }

    private By emailField = By.xpath("//input[@id = 'input-email']");
    private By passwordField = By.xpath("//input[@id = 'input-password']");
    private By loginButton = By.xpath("//button[@type = 'submit' and text() = 'Login']");

    public void doLogin(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);

        elementUtil.safeClick(loginButton);


    }

    public String getTitle() {
        waitUtil.waitForTitle("My Account", 10);
        String title = driver.getTitle();
        return title;
    }

}
