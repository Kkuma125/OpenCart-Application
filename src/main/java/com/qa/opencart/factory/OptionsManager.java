package com.qa.opencart.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Properties;

public class OptionsManager {

    private Properties prop;
    private ChromeOptions co;
    private FirefoxOptions fo;

    public OptionsManager(Properties prop) {
        this.prop = prop;
    }

    public ChromeOptions getChromeOptions() {
        co = new ChromeOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless").trim())) co.addArguments("__headless");
        if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) fo.addArguments("__incognito");
        return co;
    }

    public FirefoxOptions getFirefoxOptions() {
        fo = new FirefoxOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless").trim())) fo.addArguments("__headless");
        if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) fo.addArguments("__incognito");
        return fo;
    }
}
