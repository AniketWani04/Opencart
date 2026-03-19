package tests;

import base.BaseTest;
import dataProviders.DataProviders;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import utils.ElementUtil;

public class LoginTestDDT extends BaseTest {

    HomePage homePage;
    LoginPage loginPage;

    @Test(groups = {"DataDriven", "Loginddt"}, dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void test_loginDDT(String email, String password, String expResult) {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);

        homePage.clickMyAccount();
        homePage.clickLogin();

        loginPage.doLogin(email, password);

        String actualTitle = loginPage.getTitle();
        if(actualTitle.equals("My Account") && expResult.equals("Pass")) {
            Assert.assertTrue(true);
        }
        else if (actualTitle.equals("Account Login") && expResult.equals("Pass")){
            Assert.assertTrue((false));
        } else if (actualTitle.equals("My Account") && expResult.equals("Fail")) {
            Assert.assertFalse(true);
        } else if (actualTitle.equals("Account Login") && expResult.equals("Fail")) {
            Assert.assertFalse(false);
        }
    }
}
