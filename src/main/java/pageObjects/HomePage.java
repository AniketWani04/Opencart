package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private By myAccountOption = By.xpath("//span[text() = 'My Account']");
    private By registerOption = By.xpath("//a[text() = 'Register']");
    private By loginOption = By.xpath("//a[text() = 'Login']");

    public void clickMyAccount() {
        driver.findElement(myAccountOption).click();
    }

    public void clickRegister() {
        driver.findElement(registerOption).click();
    }

    public void clickLogin() {
        driver.findElement(loginOption).click();
    }
}
