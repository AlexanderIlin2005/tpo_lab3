package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DigestPage extends BasePage {

    private final By subscribeButton = By.xpath("//a[contains(@href,'/member/quick') or contains(text(),'Подписаться')]");

    public DigestPage(WebDriver driver) {
        super(driver);
    }

    public void subscribeFromIssue() {
        wait.waitForClickable(subscribeButton);
        driver.findElement(subscribeButton).click();
    }
}
