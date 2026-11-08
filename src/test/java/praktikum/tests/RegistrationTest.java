
package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import praktikum.client.UserApiClient;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationTest extends BaseTest {

    private final UserApiClient userApiClient = new UserApiClient();

    private String registeredEmail;
    private String registeredPassword;

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка регистрации с корректными данными и перехода на страницу авторизации")
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

        // Сохраняем данные для последующего удаления
        registeredEmail = email;
        registeredPassword = password;

        // Регистрация через UI
        registerPage.register(name, email, password);

        // Проверяем переход на страницу авторизации через Page Object
        assertTrue(
                "После регистрации должна открыться страница входа",
                loginPage.isLoginPageOpened()
        );
    }

    @Test
    @DisplayName("Ошибка регистрации с коротким паролем")
    @Description("Проверка сообщения об ошибке при регистрации с паролем менее шести символов")
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

        // Получаем токен зарегистрированного пользователя
        int statusCode = userApiClient.loginUser(
                registeredEmail,
                registeredPassword
        );

        assertEquals(
                "Зарегистрированный пользователь должен авторизоваться через API",
                200,
                statusCode
        );

        // Удаляем пользователя
        userApiClient.deleteUser();
    }
}



