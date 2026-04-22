package ru.sashil.subscribe.pages;

import org.openqa.selenium.WebDriver;
import ru.sashil.subscribe.utils.WaitHelper;

public abstract class BasePage {
    protected WebDriver driver;
    protected WaitHelper wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    public void open(String url) {
        driver.get(url);
    }
}
