package pageObjects;

import com.aventstack.extentreports.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ElementUtil;
import utils.WaitUtil;

public class RegisterPage {

    WebDriver driver;
    WaitUtil waitUtil;
    JavascriptExecutor js;
    ElementUtil elementUtil;
    public RegisterPage(WebDriver driver) {

        this.driver = driver;
        elementUtil = new ElementUtil(driver);
        waitUtil = new WaitUtil(driver);
        js = (JavascriptExecutor) driver;
    }

    private By firstNameField = By.xpath("//input[@id = 'input-firstname']");
    private By lastNameField = By.xpath("//input[@id = 'input-lastname']");
    private By emailField = By.xpath("//input[@id = 'input-email']");
    private By passwordField = By.xpath("//input[@id = 'input-password']");
    private By subscribeOption = By.xpath("//input[@id = 'input-newsletter']");
    private By privacyPolicyOption = By.xpath("(//input[@type = 'checkbox'])[2]");
    private By continueButton = By.xpath("//button[@type = 'submit' and text() = 'Continue']");


    public void doRegister(String firstName, String lastName, String email, String password, Boolean subscribe, Boolean acceptPrivacyPolicy)  {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);

        if(subscribe) {
            elementUtil.safeClick(subscribeOption);
        }

       if(acceptPrivacyPolicy) {
           elementUtil.safeClick(privacyPolicyOption);
       }

            elementUtil.safeClick(continueButton);
    }

    public String getTitle()  {
        try {
            waitUtil.waitForTitle("Your Account Has Been Created!", 10);
            String title = driver.getTitle();
            return title;
        } catch (Exception e) {
            return driver.getTitle();
        }
    }
}
