package tests;

import base.BaseTest;
import dataProviders.DataProviders;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.RegisterPage;

public class RegisterTest extends BaseTest {

    HomePage homePage;
    RegisterPage registerPage;

    @Test(groups = {"Regression", "Register", "Master"}, dataProvider = "registerData", dataProviderClass = DataProviders.class)
    public void test_Register(String firstName, String lastName, String email, String password, Boolean subscribe, Boolean privacyPolicy) {
        try {
            logger.info("....Starting RegisterTest....");
            homePage = new HomePage(driver);
            registerPage = new RegisterPage(driver);

            homePage.clickMyAccount();
            logger.info("...Clicked on My Account option...");

            homePage.clickRegister();
            logger.info("...Clicked on Register option...");

            logger.info("...Providing customer details...");
            registerPage.doRegister(firstName, lastName, email, password, subscribe, privacyPolicy);
            String actualTitle = registerPage.getTitle();

            logger.info("...Validating the title of page...");
            Assert.assertEquals(actualTitle, "Your Account Has Been Created!", "Register Failed");
        }
        catch (Exception e){
            logger.error("Test Failed");
            logger.debug("Debug logs..");
            System.out.println(e.getMessage());
            Assert.fail();
        }

    }
}
