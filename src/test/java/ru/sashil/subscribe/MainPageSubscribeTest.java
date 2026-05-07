package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainPageSubscribeTest extends BaseTest {

    @Test
    @DisplayName("UC-05: Подписка через главную страницу")
    void testSubscribeFromMainPage() {

        issuePage.openIssuePage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));
        js.executeScript("window.stop();");
        assertTrue(issuePage.hasNoSubscriptionText() || issuePage.getPageSource().contains("не подписана"),
                   "До подписки должна быть надпись об отсутствии подписок");


        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));

        wait.until(ExpectedConditions.presenceOfElementLocated(mainPage.getSubscribeButtonLocator()));
        mainPage.clickFirstSubscribeViaJS();

        wait.until(ExpectedConditions.presenceOfElementLocated(mainPage.getSubscribedButtonLocator()));


        issuePage.openIssuePage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));
        js.executeScript("window.stop();");

        assertTrue(issuePage.hasSubscription(), "Подписка должна появиться в списке");


        if (issuePage.hasUnsubscribeButton()) {
            issuePage.clickUnsubscribeViaJS();
        }


        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));

        issuePage.openIssuePage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));
        js.executeScript("window.stop();");


        assertTrue(issuePage.hasNoSubscriptionText(), "После отписки снова должна быть надпись об отсутствии подписок");
    }
}
