package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ArchivePage extends BasePage {

    private final By bodyLocator = By.xpath("//body");
    private final String ARCHIVE_URL = "https://subscribe.ru/archive/";

    public ArchivePage(WebDriver driver) {
        super(driver);
    }

    public void openArchive() {
        driver.get(ARCHIVE_URL);
    }

    public boolean isBodyPresent() {
        return driver.findElements(bodyLocator).size() > 0;
    }

    public String getPageSource() {
        return driver.getPageSource();
    }
}
