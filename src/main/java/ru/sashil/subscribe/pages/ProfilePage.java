package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {

    private final By editProfileLink = By.xpath("//a[contains(@href, '/member/edit') or contains(text(), 'Редактирование профиля')]");
    private final By questionnaireLink = By.xpath("//a[contains(@href, 'anketa') or contains(text(), 'Анкета')]");
    private final By firstNameField = By.xpath("//input[@name='first_name' or contains(@id, 'first_name')]");
    private final By lastNameField = By.xpath("//input[@name='last_name' or contains(@id, 'last_name')]");
    private final By locationField = By.xpath("//input[@name='location' or contains(@placeholder, 'живу')]");
    private final By educationField = By.xpath("//select[@name='education'] | //input[@name='education']");
    private final By jobField = By.xpath("//input[@name='job' or contains(@placeholder, 'работаю')]");
    private final By saveButton = By.xpath("//button[contains(text(), 'Сохранить')] | //input[@value='Сохранить']");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error')] | //span[contains(text(), 'обязательное')]");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public void goToProfile() {
        wait.waitForClickable(editProfileLink);
        driver.findElement(editProfileLink).click();
        wait.waitForClickable(questionnaireLink);
        driver.findElement(questionnaireLink).click();
    }

    public void fillQuestionnaire(String firstName, String lastName, String location, String education, String job) {
        wait.waitForVisible(firstNameField);
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);

        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);

        driver.findElement(locationField).clear();
        driver.findElement(locationField).sendKeys(location);

        driver.findElement(educationField).clear();
        driver.findElement(educationField).sendKeys(education);

        driver.findElement(jobField).clear();
        driver.findElement(jobField).sendKeys(job);
    }

    public void saveProfile() {
        driver.findElement(saveButton).click();
    }

    public boolean isErrorPresent() {
        return !driver.findElements(errorMessage).isEmpty();
    }

    public boolean isSuccessMessagePresent() {
        By successMsg = By.xpath("//div[contains(@class, 'success')] | //div[contains(text(), 'сохранен')]");
        return !driver.findElements(successMsg).isEmpty();
    }
}
