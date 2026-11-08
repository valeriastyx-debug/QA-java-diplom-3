

package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import praktikum.client.UserApiClient;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.PersonalAccountPage;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonalAccountTest extends BaseTest {

    private final UserApiClient userApiClient = new UserApiClient();

    private final String email =
            "test-" + UUID.randomUUID() + "@yandex.ru";

    private final String password = "password123";
    private final String name = "Test User";

    @After
    public void deleteTestUser() throws IOException {
        userApiClient.deleteUser();
    }

    @Test
    @DisplayName("Переход на страницу авторизации через «Личный кабинет»")
    @Description("Проверка открытия страницы авторизации при нажатии на кнопку «Личный кабинет»")
    public void openLoginPageFromPersonalAccountButtonTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickPersonalAccountButton();

        assertTrue(
                "Должна открыться страница авторизации",
                loginPage.isLoginPageOpened()
        );
    }

    @Test
    @DisplayName("Открытие личного кабинета авторизованного пользователя")
    @Description("Проверка открытия личного кабинета после успешной авторизации")
    public void openPersonalAccountAfterLoginTest() throws IOException {
        createTestUser();

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage =
                new PersonalAccountPage(driver);

        mainPage.clickLoginButton();
        loginPage.login(email, password);

        assertTrue(
                "После входа должна открыться главная страница",
                mainPage.isMainPageOpened()
        );

        mainPage.clickPersonalAccountButton();

        assertTrue(
                "Должен открыться личный кабинет",
                personalAccountPage.isPersonalAccountOpened()
        );
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    @Description("Проверка перехода на главную страницу через кнопку «Конструктор»")
    public void goToConstructorFromPersonalAccountTest() throws IOException {
        createTestUser();

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage =
                new PersonalAccountPage(driver);

        mainPage.clickLoginButton();
        loginPage.login(email, password);

        assertTrue(
                "После входа должна открыться главная страница",
                mainPage.isMainPageOpened()
        );

        mainPage.clickPersonalAccountButton();

        assertTrue(
                "Должен открыться личный кабинет",
                personalAccountPage.isPersonalAccountOpened()
        );

        personalAccountPage.clickConstructorLink();

        assertTrue(
                "После нажатия «Конструктор» должна открыться главная страница",
                mainPage.isMainPageOpened()
        );
    }

    @Test
    @DisplayName("Переход из личного кабинета по логотипу Stellar Burgers")
    @Description("Проверка перехода на главную страницу при нажатии на логотип Stellar Burgers")
    public void goToConstructorByLogoTest() throws IOException {
        createTestUser();

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage =
                new PersonalAccountPage(driver);

        mainPage.clickLoginButton();
        loginPage.login(email, password);

        assertTrue(
                "После входа должна открыться главная страница",
                mainPage.isMainPageOpened()
        );

        mainPage.clickPersonalAccountButton();

        assertTrue(
                "Должен открыться личный кабинет",
                personalAccountPage.isPersonalAccountOpened()
        );

        personalAccountPage.clickLogo();

        assertTrue(
                "После нажатия на логотип должна открыться главная страница",
                mainPage.isMainPageOpened()
        );
    }

    @Test
    @DisplayName("Выход из личного кабинета")
    @Description("Проверка выхода из аккаунта и перехода на страницу авторизации")
    public void logoutFromPersonalAccountTest() throws IOException {
        createTestUser();

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage =
                new PersonalAccountPage(driver);

        mainPage.clickLoginButton();
        loginPage.login(email, password);

        assertTrue(
                "После входа должна открыться главная страница",
                mainPage.isMainPageOpened()
        );

        mainPage.clickPersonalAccountButton();

        assertTrue(
                "Должен открыться личный кабинет",
                personalAccountPage.isPersonalAccountOpened()
        );

        personalAccountPage.clickLogoutButton();

        assertTrue(
                "После выхода должна открыться страница авторизации",
                loginPage.isLoginPageOpened()
        );
    }

    private void createTestUser() throws IOException {
        int statusCode = userApiClient.createUser(
                email, password, name
        );

        assertEquals(
                "Тестовый пользователь должен быть создан",
                200,
                statusCode
        );
    }
}





