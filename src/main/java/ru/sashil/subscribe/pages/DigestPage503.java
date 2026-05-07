package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DigestPage503 extends BasePage {

    private final By bodyLocator = By.xpath("//body");
    private final String DIGEST_URL = "https://subscribe.ru/catalog/digest.inet";

    public DigestPage503(WebDriver driver) {
        super(driver);
    }

    public void openDigest() {
        driver.get(DIGEST_URL);
    }

    public boolean isBodyPresent() {
        return driver.findElements(bodyLocator).size() > 0;
    }

    public String getPageSource() {
        return driver.getPageSource();
    }
}
