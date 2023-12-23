package com.qa.opencart.pages;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;
    private final ElementUtil elementUtil;
    private final By username = By.id("input-email");
    private final By password = By.id("input-password");
    private final By ForgotPwdLink = By.xpath("(//a[text()='Forgotten Password'])[1]");

    private final By loginButton = By.cssSelector("input[type='submit']");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }

    public String getLoginPageTitle() {
        return elementUtil.waitForPageTitle(Constants.LOGIN_PAGE_TITLE, 5);
    }

    public boolean isforgotPwdLinkExist() {
        elementUtil.waitForElementPresent(ForgotPwdLink, 5);
        return elementUtil.checkElementIsDisplayed(ForgotPwdLink);
    }

    public AccountsPage doLogin(String userName, String Password) {
        System.out.println("Login with : " + userName + " " + Password);
        elementUtil.doSendKeys(username, userName);
        elementUtil.doSendKeys(password, Password);
        elementUtil.doClick(loginButton);
        System.out.println("Navigating to AccoutnsPage");
        return new AccountsPage(driver);

    }


}
