package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MemberPage extends BasePage {

    private final By mySubscriptionsLink = By.xpath("//a[contains(text(), 'Рассылки, которые были подписаны ранее')]");
    private final By propertiesLink = By.xpath("//a[contains(text(), 'Свойства подписки')]");
    private final By quickError = By.xpath("//*[contains(text(), 'Не указан подписной адрес')]");

    public MemberPage(WebDriver driver) {
        super(driver);
    }

    public void goToMySubscriptions() {
        wait.waitForClickable(mySubscriptionsLink);
        driver.findElement(mySubscriptionsLink).click();
    }

    public int getSubscriptionCount() {
        try {
            By countEl = By.xpath("//*[contains(text(), 'Сейчас подписано')]");
            String text = driver.findElement(countEl).getText();
            return Integer.parseInt(text.replaceAll("\\D+", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isQuickSubscribeErrorPresent() {
        return !driver.findElements(quickError).isEmpty();
    }
}
