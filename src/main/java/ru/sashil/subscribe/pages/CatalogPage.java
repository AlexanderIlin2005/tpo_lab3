package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CatalogPage extends BasePage {

    private final By bodyLocator = By.xpath("//body");
    private final String CATALOG_URL = "https://subscribe.ru/catalog/";

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public void openCatalog() {
        driver.get(CATALOG_URL);
    }

    public boolean isBodyPresent() {
        return driver.findElements(bodyLocator).size() > 0;
    }

    public String getPageSource() {
        return driver.getPageSource();
    }
}
