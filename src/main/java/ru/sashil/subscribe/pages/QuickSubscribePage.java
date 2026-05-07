package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class QuickSubscribePage extends BasePage {

    private final By bodyLocator = By.xpath("//body");
    private final String QUICK_SUBSCRIBE_URL = "https://subscribe.ru/member/quick?grp=digest.cookery";

    public QuickSubscribePage(WebDriver driver) {
        super(driver);
    }

    public void openQuickSubscribe() {
        driver.get(QUICK_SUBSCRIBE_URL);
    }

    public boolean isBodyPresent() {
        return driver.findElements(bodyLocator).size() > 0;
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public By getBodyLocator() {
        return bodyLocator;
    }
}
