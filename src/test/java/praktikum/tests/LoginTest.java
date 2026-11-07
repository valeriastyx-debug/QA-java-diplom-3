
package praktikum.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import praktikum.client.UserApiClient;
import praktikum.pages.ForgotPasswordPage;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginTest extends BaseTest {

    private final UserApiClient userApiClient = new UserApiClient();

    private final String email =
            "test-" + UUID.randomUUID() + "@yandex.ru";

    private final String password = "password123";
    private final String name = "Test User";

    @Before
    public void createTestUser() throws IOException {

        int statusCode = userApiClient.createUser(
                email, password, name
        );

        assertEquals(
                "Тестовый пользователь должен быть создан",
                200,
                statusCode
        );
    }

    @After
    public void deleteTestUser() throws IOException {
        userApiClient.deleteUser();
    }

    @Test
    public void loginFromMainPageTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickLoginButton();
        loginPage.login(email, password);

        assertTrue(
                "После авторизации пользователь должен попасть на главную страницу",
                mainPage.isMainPageOpened()
        );
    }

    @Test
    public void loginFromPersonalAccountTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickPersonalAccountButton();
        loginPage.login(email, password);

        assertTrue(
                "После авторизации пользователь должен попасть на главную страницу",
                mainPage.isMainPageOpened()
        );
    }

    @Test
    public void loginFromRegistrationPageTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registerPage.clickLoginLink();

        loginPage.login(email, password);

        assertTrue(
                "После авторизации пользователь должен попасть на главную страницу",
                mainPage.isMainPageOpened()
        );
    }

    @Test
    public void loginFromForgotPasswordPageTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ForgotPasswordPage forgotPasswordPage =
                new ForgotPasswordPage(driver);

        mainPage.clickLoginButton();
        loginPage.clickForgotPasswordLink();
        forgotPasswordPage.clickLoginLink();

        loginPage.login(email, password);

        assertTrue(
                "После авторизации пользователь должен попасть на главную страницу",
                mainPage.isMainPageOpened()
        );
    }
}

