package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class BaseTest {

    DriverFactory df;
    public Properties prop;
    WebDriver driver;
    public LoginPage loginPage;
    public AccountsPage accountsPage;
    public ProductInfoPage productInfoPage;

    @Parameters({"Browser"})
    @BeforeTest
    public void setUp(String browserName) {
        df = new DriverFactory();
        prop = df.init_Prop();
        String Browser = prop.getProperty("Browser");

        if (browserName != null) {
            Browser = browserName;
        }


        driver = df.init_Driver(Browser);
        driver.get(prop.getProperty("url"));
        loginPage = new LoginPage(driver);


    }


    @AfterTest
    public void teadrDown() {
        driver.quit();
    }

}
