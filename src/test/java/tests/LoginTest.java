package tests;

import base.BaseTest;
import dataProviders.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;


public class LoginTest extends BaseTest {

    LoginPage loginPage;
    HomePage homePage;

    @Test(groups = {"Sanity", "Login", "Master"})
    public void test_Login() {

        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);

        homePage.clickMyAccount();
        homePage.clickLogin();

        loginPage.doLogin("virat@gmail.com", "Virat@123");

        String actualString = loginPage.getTitle();
        Assert.assertEquals(actualString, "My Account", "Login Failed");

    }
}
