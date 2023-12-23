package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class AccountsPageTest extends BaseTest {

    @BeforeClass
    public void accountsPageSetup() {
        accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }


    @Test(priority = 1)
    public void accountsPageTitleTest() {
        String title = accountsPage.getAccountsPageTitle();
        System.out.println("AccountsPage Title is  : " + title);
        Assert.assertEquals(title, Constants.ACCOUNTS_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void verifyAccountsPageHeaderTest() {
        boolean logo = accountsPage.getHeaderValue();
        System.out.println(logo);
        Assert.assertTrue(logo, "Logo Not Displayed");
    }

    @Test(priority = 3)
    public void verifyAccountsSectionsCountTest() {
        int Count = accountsPage.getAccountsSectionsCount();
        Assert.assertEquals(Count, Constants.ACCOUNTS_PAGE_SECTIONS_COUNT);
    }

    @Test(priority = 4)
    public void verifyAccountSectionListTest() {
        List<String> sectionsList = accountsPage.getAccountSectionList();
        System.out.println(sectionsList);
        Assert.assertEquals(sectionsList, Constants.EXPECTED_ACC_PAGE_HEADERS_LIST);
    }

    @DataProvider
    public Object[][] SearchProductsData() {
        return new Object[][]{{"iMac"}, {"iPhone"}, {"Macbook Air"}};
    }

    @Test(priority = 5, dataProvider = "SearchProductsData")
    public void searchTest(String productName) {
        Assert.assertTrue(accountsPage.doSearch(productName));
    }

    @Test(priority = 6)
    public void verifyProductResultsTest() {
        accountsPage.doSearch("iMac");
        accountsPage.selectProductFromResults("iMac");
    }


}
