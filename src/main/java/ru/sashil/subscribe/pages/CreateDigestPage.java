package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateDigestPage extends BasePage {

    private final By createDigestButton = By.xpath("//*[@id='all']/section/div[1]/div/div/a[1]");
    private final By emailField = By.xpath("//*[@id=\"credential_0\"]");
    private final By passwordField = By.xpath("//*[@id=\"credential_1\"]");

    public CreateDigestPage(WebDriver driver) {
        super(driver);
    }

    public void clickCreateDigest() {
        wait.waitForClickable(createDigestButton);
        driver.findElement(createDigestButton).click();
    }

    public boolean hasLoginForm() {
        return driver.findElements(emailField).size() > 0 && 
               driver.findElements(passwordField).size() > 0;
    }

    public boolean isLoginFormVisible() {
        return driver.findElements(emailField).size() > 0;
    }
}
