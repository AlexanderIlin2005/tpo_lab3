package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainPageSubscribeTest extends BaseTest {

    @Test
    @DisplayName("UC-05: Подписка через главную страницу")
    void testSubscribeFromMainPage() {
        // ШАГ 1: Проверяем, что нет подписок
        driver.get(BASE_URL + "member/issue");
        memberIssuePage.waitForBody();
        js.executeScript("window.stop();");
        assertTrue(memberIssuePage.hasNoSubscription(), "До подписки должна быть надпись об отсутствии подписок");

        // ШАГ 2: На главной странице подписываемся на первую рассылку
        driver.get(BASE_URL);
        memberIssuePage.waitForBody();

        assertTrue(mainPage.isSubscribeButtonPresent(), "Кнопка подписки должна быть на странице");
        WebElement firstSubscribe = mainPage.getFirstSubscribeButton();
        assertNotNull(firstSubscribe, "Первая кнопка подписки не найдена");
        mainPage.clickSubscribeButton(firstSubscribe);

        assertTrue(mainPage.isSubscribedButtonPresent(), "Кнопка должна изменить состояние на 'подписано'");

        // ШАГ 3: Проверяем, что подписка появилась
        driver.get(BASE_URL + "member/issue");
        memberIssuePage.waitForBody();
        js.executeScript("window.stop();");
        assertTrue(memberIssuePage.hasSubscription(), "Подписка должна появиться в списке");

        // ШАГ 4: Отписываемся
        if (memberIssuePage.isUnsubscribeButtonPresent()) {
            memberIssuePage.clickUnsubscribe();
        }

        // ШАГ 5: Выходим на главную и обратно для обновления состояния
        driver.get(BASE_URL);
        memberIssuePage.waitForBody();
        driver.get(BASE_URL + "member/issue");
        memberIssuePage.waitForBody();
        js.executeScript("window.stop();");

        assertTrue(memberIssuePage.hasNoSubscription(), "После отписки снова должна быть надпись об отсутствии подписок");
    }
}
