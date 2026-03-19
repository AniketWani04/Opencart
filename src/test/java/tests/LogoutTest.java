package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class LogoutTest extends BaseTest {

    HomePage homePage;
    LoginPage loginPage;
    MyAccountPage myAccountPage;

    @Test(groups = {"Sanity", "Master", "Logout"})
    public void test_logout() throws InterruptedException {

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        myAccountPage = new MyAccountPage(driver);

        homePage.clickMyAccount();
        homePage.clickLogin();

        loginPage.doLogin("virat@gmail.com", "Virat@123");


        myAccountPage.doLogout();

        String actualTitle = myAccountPage.getTitle();
        Assert.assertEquals(actualTitle, "Account Logout", "Logout failed");

    }
}
