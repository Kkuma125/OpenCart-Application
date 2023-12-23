package com.qa.opencart.tests;


import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test(priority = 1)
    public void LoginPageTitleTest() {
        String title = loginPage.getLoginPageTitle();
        System.out.println("LoginPage Title is : " + title);
        Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void forgotPwdLinkTest() {
        Assert.assertTrue(loginPage.isforgotPwdLinkExist());
    }

    @Test(priority = 3)
    public void LoginTest() {
        accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertEquals(accountsPage.getAccountsPageTitle(), Constants.ACCOUNTS_PAGE_TITLE);
    }


}



