package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateDigestUnauthorizedTest extends BaseTestNoLogin {

    @Test
    @DisplayName("UC-09: Создание рассылки (неавторизованный пользователь - форма логина)")
    void testCreateDigestUnauthorized() {
        driver.get(BASE_URL);
        createDigestPage.clickCreateDigest();
        wait.until(ExpectedConditions.urlContains("/member/list/new"));
        assertTrue(driver.getCurrentUrl().contains("/member/list/new"), "Должна открыться страница создания рассылки");
        assertTrue(createDigestPage.isLoginFormVisible(), "Неавторизованный пользователь должен видеть форму логина");
        assertTrue(createDigestPage.hasLoginForm(), "Поле пароля также должно быть видно");
    }
}
