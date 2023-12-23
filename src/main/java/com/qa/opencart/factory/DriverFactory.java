package com.qa.opencart.factory;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    WebDriver driver;
    Properties prop;
    public static String highlight;
    public OptionsManager optionsManager;

    public static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
    public static final Logger LOGGER = Logger.getLogger(String.valueOf(DriverFactory.class));

    public WebDriver init_Driver(String browserName) {
        //    String browserName = prop.getProperty("Browser");
        System.out.println("BrowserName is : " + browserName);


        highlight = prop.getProperty("highlight").trim();
        optionsManager = new OptionsManager(prop);

        if (browserName.equalsIgnoreCase("Chrome")) {
            tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
        } else if (browserName.equalsIgnoreCase("firefox")) {
            tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
        } else if (browserName.equalsIgnoreCase("Edge")) {
            tlDriver.set(new EdgeDriver());

        } else {
            System.out.println("Please Pass the Correct BrowserName : " + browserName);
        }

        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        return getDriver();

    }

    private void init_remoteDriver(String browser) {
        System.out.println("Running test on remote grid server :" + browser);

        if (browser.equals("Chrome")) {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("browserName", "Chrome");
            cap.setCapability("enableVNC", "true");
            cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());

            try {
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        } else if (browser.equals("Firefox")) {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setCapability("browserName", "Firefox");
            cap.setCapability("enableVNC", "true");
            cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getFirefoxOptions());

            try {
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public Properties init_Prop() {
        FileInputStream ip = null;
        prop = new Properties();
        String env = System.getProperty("env");
        LOGGER.info("Running On Environment...>" + env);
        System.out.println("Running on Environment....>:" + env);
        if (env == null) {
            try {
                ip = new FileInputStream("./src/test/resources/Config/ config.properties");
                prop.load(ip);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                switch (env) {
                    case "QA":
                        ip = new FileInputStream("./src/test/resources/Config/qa.config.properties");
                        break;
                    case "Stage":
                        ip = new FileInputStream("./src/test/resources/Config/stage.config.properties");
                        break;
                    case "Dev":
                        ip = new FileInputStream("./src/test/resources/Config/dev.config.properties");
                        break;
                    case "Prod":
                        ip = new FileInputStream("./src/test/resources/Config/prod.config.properties");
                        break;

                    default:
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                LOGGER.error("File Not Found at the Given Location...");
            }

        }

        return prop;
    }


    public String getScreenshot() {
        String src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
        File srcFile = new File(src);
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileUtils.copyFile(srcFile, destination);
        } catch (IOException e) {
            LOGGER.error("some exception is coming while creating the screenshot");
            e.printStackTrace();
        }
        return path;

    }


}

