package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class ProductInfoPageTest extends BaseTest {

    @BeforeClass
    public void productsInfoPageSetup() {
        accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Test(priority = 1)
    public void productInfoPageTitleTest_iMac() {
        accountsPage.doSearch("iMac");
        productInfoPage = accountsPage.selectProductFromResults("iMac");
        Assert.assertEquals(productInfoPage.getProductInfoPageTitle("iMac"), "iMac");
    }

    @Test(priority = 2)
    public void productInfoPageTitleTest_MacBookAir() {
        accountsPage.doSearch("MacBook Air");
        productInfoPage = accountsPage.selectProductFromResults("MacBook Air");
        Assert.assertEquals(productInfoPage.getProductInfoPageTitle("MacBook Air"), "MacBook Air");
    }

    @Test(priority = 3)
    public void verifyProductImageTest() {
        Assert.assertTrue(productInfoPage.verifyProductImages() == 4);
    }

    @Test(priority = 4)
    public void verifyProductInfoTest() {
        String productName = "iMac";
        productInfoPage = accountsPage.GotoProductInfoPage(productName);
        Map<String, String> productinfoMap = productInfoPage.getProductNameInformation();

        System.out.println(productinfoMap);
        productinfoMap.forEach((k, v) -> System.out.println(k + " : " + v));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(productinfoMap.get("name"), productName);
        softAssert.assertEquals(productinfoMap.get("Brand"), "Apple");
        softAssert.assertEquals(productinfoMap.get("Price"), "$122.00");
        softAssert.assertEquals(productinfoMap.get("Product Code"), "Product 14");
        softAssert.assertAll();

    }


    @Test(priority = 5)
    public void selectQtyTest() {
        Assert.assertTrue(productInfoPage.selectQty("2"));
    }

    @Test(priority = 6)
    public void AddToCartTest() {
        productInfoPage.AddToCart();
    }

    @Test(priority = 7)
    public void ShoppingCartSuccessMessageTest() {
        Assert.assertEquals(productInfoPage.ShoppingCartSuccessMessage(), Constants.SHOPPING_CART_SUCCESS_MSG);
    }


}
