package com.qa.opencart.pages;

import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoPage {

    private final WebDriver driver;
    private final ElementUtil elementUtil;

    private final By productnameheader = By.cssSelector("div#content h1");
    private final By productmetaData = By.cssSelector("#content .list-unstyled:nth-of-type(1) li");
    private final By Productmetaprice = By.cssSelector("#content .list-unstyled:nth-of-type(2) li");
    private final By productimages = By.cssSelector("ul.thumbnails img");
    private final By Qty = By.cssSelector("input#input-quantity");
    private final By AddToCart = By.cssSelector("button#button-cart");
    private final By SuccessMsg = By.xpath(" //div[@class='alert alert-success alert-dismissible']");

    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }


    public Map<String, String> getProductNameInformation() {
        Map<String, String> productinfoMap = new HashMap<String, String>();

        productinfoMap.put("name", elementUtil.doGetText(productnameheader).trim());

        List<WebElement> productMetaDataList = elementUtil.getElements(productmetaData);

        for (WebElement e : productMetaDataList) {
            String[] meta = e.getText().split(":");
            String metaName = meta[0].trim();
            String metaValue = meta[1].trim();
            productinfoMap.put(metaName, metaValue);

        }

        List<WebElement> productMetaPriceList = elementUtil.getElements(Productmetaprice);
        productinfoMap.put("Price", productMetaPriceList.get(0).getText().trim());
        productinfoMap.put("Ex Tax Price", productMetaPriceList.get(1).getText().split(":")[1].trim());

        return productinfoMap;

    }

    public boolean selectQty(String QtyValue) {
        elementUtil.doSendKeys(Qty, QtyValue);
        System.out.println("Quantity : " + QtyValue);
        return true;
    }

    public void AddToCart() {
        elementUtil.doClick(AddToCart);
    }

    public int verifyProductImages() {
        int ImageCount = elementUtil.getElements(productimages).size();
        System.out.println("Total Product Image Count is  : " + ImageCount);
        return ImageCount;
    }

    public String getProductInfoPageTitle(String productName) {
        String title = elementUtil.waitForPageTitlePresent(productName, 5);
        System.out.println("ProductPage Title is :" + title);
        return title;
    }

    public String ShoppingCartSuccessMessage() {
        elementUtil.waitForElementPresent(SuccessMsg, 5);
        elementUtil.doGetText(SuccessMsg).trim();
        String textMsg = elementUtil.doGetText(SuccessMsg);
        System.out.println(textMsg);
        return textMsg;
    }

}
