package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IssuePage extends BasePage {

    private final By bodyLocator = By.xpath("//body");
    private final By noSubscriptionText = By.xpath("//*[contains(text(), 'Не подписана') or contains(text(), 'не подписана')]");
    private final By subscriptionSpan = By.xpath("//*[@id=\"all\"]/section/div[2]/div/div/div[1]/div[4]/div/span/span");
    private final By unsubscribeBtn = By.xpath("//*[@id=\"all\"]/section/div[2]/div/div/div[1]/div[4]/div/span/a");

    public IssuePage(WebDriver driver) {
        super(driver);
    }

    public void openIssuePage() {
        driver.get("https://subscribe.ru/member/issue");
    }

    public By getBodyLocator() {
        return bodyLocator;
    }

    public boolean isBodyPresent() {
        return driver.findElements(bodyLocator).size() > 0;
    }

    public boolean hasNoSubscriptionText() {
        return driver.findElements(noSubscriptionText).size() > 0;
    }

    public boolean hasSubscription() {
        return driver.findElements(subscriptionSpan).size() > 0 ||
               driver.getPageSource().contains("подписчик") ||
               driver.getPageSource().contains("Отписаться");
    }

    public boolean hasUnsubscribeButton() {
        return driver.findElements(unsubscribeBtn).size() > 0;
    }

    public void clickUnsubscribe() {
        if (hasUnsubscribeButton()) {
            driver.findElement(unsubscribeBtn).click();
        }
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public void clickUnsubscribeViaJS() {
        if (hasUnsubscribeButton()) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(unsubscribeBtn));
        }
    }
}
