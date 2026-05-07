package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage extends BasePage {

    private final By subscribeButton = By.xpath("//a[contains(@class, 'subscriberu_subscribe') and contains(@class, 'subscriberu_notsubscribed')]");
    private final By subscribedButton = By.xpath("//a[contains(@class, 'subscriberu_subscribed')]");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getSubscribeButtons() {
        return driver.findElements(subscribeButton);
    }

    public WebElement getFirstSubscribeButton() {
        List<WebElement> buttons = getSubscribeButtons();
        if (buttons.size() > 0) {
            return buttons.get(0);
        }
        return null;
    }

    public boolean isSubscribeButtonPresent() {
        return getSubscribeButtons().size() > 0;
    }

    public boolean isSubscribedButtonPresent() {
        return driver.findElements(subscribedButton).size() > 0;
    }

    public void clickSubscribeByIndex(int index) {
        List<WebElement> buttons = getSubscribeButtons();
        if (buttons.size() > index) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttons.get(index));
        }
    }

    public void clickFirstSubscribeViaJS() {
        List<WebElement> buttons = getSubscribeButtons();
        if (buttons.size() > 0) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttons.get(0));
        }
    }
}
