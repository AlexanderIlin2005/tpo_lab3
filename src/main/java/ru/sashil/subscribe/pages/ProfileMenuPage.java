package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfileMenuPage extends BasePage {

    private final By userIcon = By.xpath("//*[@id=\"all\"]/header/ul/li[1]/a");
    private final By subscriptionsMenuItem = By.xpath("//*[@id=\"logged_list\"]/li[2]/a/span[2]");
    private final By profileMenuItem = By.xpath("//*[@id=\"logged_list\"]/li[1]/a/span[2]");
    private final By logoutMenuItem = By.xpath("//*[@id='logged_list']/li[9]/a");

    public ProfileMenuPage(WebDriver driver) {
        super(driver);
    }

    public void clickUserIcon() {
        wait.waitForClickable(userIcon);
        driver.findElement(userIcon).click();
    }

    public void goToSubscriptions() {
        wait.waitForClickable(subscriptionsMenuItem);
        driver.findElement(subscriptionsMenuItem).click();
    }

    public void goToProfile() {
        wait.waitForClickable(profileMenuItem);
        driver.findElement(profileMenuItem).click();
    }

    public void logout() {
        wait.waitForClickable(logoutMenuItem);
        driver.findElement(logoutMenuItem).click();
    }

    public boolean isUserIconVisible() {
        return driver.findElements(userIcon).size() > 0;
    }

    public By userIconLocator() {
        return userIcon;
    }

}

