package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MemberIssuePage extends BasePage {

    private final By noSubscriptionText = By.xpath("//*[contains(text(), 'Не подписана') or contains(text(), 'не подписана')]");
    private final By subscriptionSpan = By.xpath("//*[@id=\"all\"]/section/div[2]/div/div/div[1]/div[4]/div/span/span");
    private final By unsubscribeButton = By.xpath("//*[@id=\"all\"]/section/div[2]/div/div/div[1]/div[4]/div/span/a");
    private final By body = By.xpath("//body");

    public MemberIssuePage(WebDriver driver) {
        super(driver);
    }

    public boolean hasNoSubscription() {
        return driver.findElements(noSubscriptionText).size() > 0;
    }

    public boolean hasSubscription() {
        return driver.findElements(subscriptionSpan).size() > 0 || 
               driver.getPageSource().contains("подписчик") || 
               driver.getPageSource().contains("Отписаться");
    }

    public boolean isUnsubscribeButtonPresent() {
        return driver.findElements(unsubscribeButton).size() > 0;
    }

    public void clickUnsubscribe() {
        if (isUnsubscribeButtonPresent()) {
            driver.findElement(unsubscribeButton).click();
        }
    }

    public void waitForBody() {
        wait.waitForVisible(body);
    }
}
