
package praktikum.tests;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.client.UserApiClient;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationTest extends BaseTest {

    private final UserApiClient userApiClient = new UserApiClient();

    private String registeredEmail;
    private String registeredPassword;

    @Test
    public void successfulRegistrationTest() {

        String email = "test-" + UUID.randomUUID() + "@yandex.ru";
        String password = "password123";
        String name = "Test User";

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        // Переход на страницу регистрации
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();

        // Сохраняем данные для удаления пользователя
        registeredEmail = email;
        registeredPassword = password;

        // Регистрация через UI
        registerPage.register(name, email, password);

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

        // Проверяем переход на страницу авторизации
        assertTrue(
                "После регистрации должна открыться страница входа",
                wait.until(ExpectedConditions.urlContains("/login"))
        );
    }

    @Test
    public void registrationWithShortPasswordTest() {

        String email = "test-" + UUID.randomUUID() + "@yandex.ru";
        String password = "12345";
        String name = "Test User";

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        // Переход на страницу регистрации
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();

        // Попытка регистрации с коротким паролем
        registerPage.register(name, email, password);

        // Проверяем сообщение об ошибке
        assertTrue(
                "Должна отображаться ошибка короткого пароля",
                registerPage.isPasswordErrorDisplayed()
        );
    }

    @After
    public void deleteRegisteredUser() throws IOException {

        // Если пользователь не регистрировался, очистка не требуется
        if (registeredEmail == null || registeredPassword == null) {
            return;
        }

        // Получаем токен пользователя через API
        int statusCode = userApiClient.loginUser(
                registeredEmail,
                registeredPassword
        );

        assertEquals(
                "Зарегистрированный пользователь должен авторизоваться через API",
                200,
                statusCode
        );

        // Удаляем созданного пользователя
        userApiClient.deleteUser();
    }
}


