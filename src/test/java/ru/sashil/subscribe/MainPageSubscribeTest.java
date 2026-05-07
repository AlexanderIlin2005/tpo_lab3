package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainPageSubscribeTest extends BaseTest {

    @Test
    @DisplayName("UC-05: Подписка через главную страницу")
    void testSubscribeFromMainPage() {
        // ШАГ 1: Проверяем, что нет подписок
        issuePage.openIssuePage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));
        js.executeScript("window.stop();");
        assertTrue(issuePage.hasNoSubscriptionText() || issuePage.getPageSource().contains("не подписана"),
                   "До подписки должна быть надпись об отсутствии подписок");

        // ШАГ 2: На главной странице подписываемся на первую рассылку (только JS)
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));

        wait.until(ExpectedConditions.presenceOfElementLocated(mainPage.getSubscribeButtonLocator()));
        mainPage.clickFirstSubscribeViaJS();

        wait.until(ExpectedConditions.presenceOfElementLocated(mainPage.getSubscribedButtonLocator()));

        // ШАГ 3: Проверяем, что подписка появилась
        issuePage.openIssuePage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));
        js.executeScript("window.stop();");

        assertTrue(issuePage.hasSubscription(), "Подписка должна появиться в списке");

        // ШАГ 4: Отписываемся через JS
        if (issuePage.hasUnsubscribeButton()) {
            issuePage.clickUnsubscribeViaJS();
        }

        // ШАГ 5: Выходим на главную, потом снова в подписки (только так обновляется состояние)
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));

        issuePage.openIssuePage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(issuePage.getBodyLocator()));
        js.executeScript("window.stop();");

        // ШАГ 6: Проверяем, что подписок снова нет
        assertTrue(issuePage.hasNoSubscriptionText(), "После отписки снова должна быть надпись об отсутствии подписок");
    }
}
