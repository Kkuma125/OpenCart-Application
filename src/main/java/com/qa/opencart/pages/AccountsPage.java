package com.qa.opencart.pages;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountsPage {

    private final WebDriver driver;
    private final ElementUtil elementUtil;

    private final By HeaderLogo = By.cssSelector("div#logo a");
    private final By accountSectionHeaders = By.cssSelector("div#content h2");
    private final By searchtext = By.cssSelector("div#search input[name='search'");
    private final By searchButton = By.cssSelector("div#search button[type='button']");
    private final By searchItemResults = By.cssSelector("div.product-layout div.product-thumb");
    private final By resultsItems = By.cssSelector("div.product-thumb h4 a");

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }

    public String getAccountsPageTitle() {
        return elementUtil.waitForPageTitle(Constants.ACCOUNTS_PAGE_TITLE, 5);
    }

    public boolean getHeaderValue() {
        return elementUtil.checkElementIsDisplayed(HeaderLogo);
    }

    public int getAccountsSectionsCount() {
        return elementUtil.getElements(accountSectionHeaders).size();
    }

    public List<String> getAccountSectionList() {
        List<String> accountsList = new ArrayList<>();
        List<WebElement> accSectionList = elementUtil.getElements(accountSectionHeaders);

        for (WebElement e : accSectionList) {
            String Section = e.getText();
            System.out.println(Section);
            accountsList.add(Section);
        }
        return accountsList;
    }

    public boolean doSearch(String productName) {
        elementUtil.doSendKeys(searchtext, productName);
        elementUtil.doClick(searchButton);
        if (elementUtil.getElements(searchItemResults).size() > 0) {
            return true;
        }
        return false;

    }

    public ProductInfoPage GotoProductInfoPage(String productName) {
        doSearch(productName);
        return selectProductFromResults(productName);
    }

    public ProductInfoPage selectProductFromResults(String productName) {
        List<WebElement> resultItemList = elementUtil.getElements(resultsItems);
        System.out.println("Total Number Of Items Displayed :" + resultItemList.size());

        for (WebElement e : resultItemList) {
            if (e.getText().equals(productName)) {
                e.click();
                break;
            }
        }
        return new ProductInfoPage(driver);
    }

}
