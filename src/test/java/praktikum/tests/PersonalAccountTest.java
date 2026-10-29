

package praktikum.tests;

import praktikum.pages.PersonalAccountPage;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.client.UserApiClient;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;

import java.io.IOException;
import java.time.Duration;
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
    public void openLoginPageFromPersonalAccountButtonTest() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickPersonalAccountButton();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        assertTrue(
                "Должна открыться страница авторизации",
                wait.until(ExpectedConditions.urlContains("/login"))
        );
    }

    @Test
    public void openPersonalAccountAfterLoginTest() throws IOException {

        // Создаём тестового пользователя через API
        int statusCode = userApiClient.createUser(
                email, password, name
        );

        assertEquals(
                "Тестовый пользователь должен быть создан",
                200,
                statusCode
        );

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Авторизуемся
        mainPage.clickLoginButton();
        loginPage.login(email, password);

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        // Ожидаем возвращения на главную страницу
        assertTrue(
                "После входа должна открыться главная страница",
                wait.until(ExpectedConditions.urlToBe(
                        "https://stellarburgers.education-services.ru/"
                ))
        );

        // Переходим в личный кабинет
        mainPage.clickPersonalAccountButton();

        // Проверяем адрес страницы личного кабинета
        assertTrue(
                "Должен открыться личный кабинет",
                wait.until(ExpectedConditions.urlContains("/account"))
        );
    }
    @Test
    public void goToConstructorFromPersonalAccountTest() throws IOException {
        // Создаём тестового пользователя через API
        int statusCode = userApiClient.createUser(email, password, name);

        assertEquals(
                "Тестовый пользователь должен быть создан",
                200,
                statusCode
        );

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage =
                new PersonalAccountPage(driver);

        // Авторизуемся
        mainPage.clickLoginButton();
        loginPage.login(email, password);

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.urlToBe(
                "https://stellarburgers.education-services.ru/"
        ));

        // Открываем личный кабинет
        mainPage.clickPersonalAccountButton();

        wait.until(ExpectedConditions.urlContains("/account"));

        // Нажимаем «Конструктор»
        personalAccountPage.clickConstructorLink();

        // Проверяем возвращение на главную страницу
        assertTrue(
                "После нажатия «Конструктор» должна открыться главная страница",
                wait.until(ExpectedConditions.urlToBe(
                        "https://stellarburgers.education-services.ru/"
                ))
        );
    }
    @Test
    public void goToConstructorByLogoTest() throws IOException {
        // Создаём тестового пользователя
        int statusCode = userApiClient.createUser(email, password, name);

        assertEquals(
                "Тестовый пользователь должен быть создан",
                200,
                statusCode
        );

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage =
                new PersonalAccountPage(driver);

        // Авторизуемся
        mainPage.clickLoginButton();
        loginPage.login(email, password);

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.urlToBe(
                "https://stellarburgers.education-services.ru/"
        ));

        // Переходим в личный кабинет
        mainPage.clickPersonalAccountButton();

        wait.until(ExpectedConditions.urlContains("/account"));

        // Нажимаем на логотип Stellar Burgers
        personalAccountPage.clickLogo();

        // Проверяем возвращение на главную страницу
        assertTrue(
                "После нажатия на логотип должна открыться главная страница",
                wait.until(ExpectedConditions.urlToBe(
                        "https://stellarburgers.education-services.ru/"
                ))
        );
    }
    @Test
    public void logoutFromPersonalAccountTest() throws IOException {
        // Создаём тестового пользователя
        int statusCode = userApiClient.createUser(email, password, name);

        assertEquals(
                "Тестовый пользователь должен быть создан",
                200,
                statusCode
        );

        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PersonalAccountPage personalAccountPage =
                new PersonalAccountPage(driver);

        // Авторизуемся
        mainPage.clickLoginButton();
        loginPage.login(email, password);

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.urlToBe(
                "https://stellarburgers.education-services.ru/"
        ));

        // Переходим в личный кабинет
        mainPage.clickPersonalAccountButton();

        wait.until(ExpectedConditions.urlContains("/account"));

        // Нажимаем кнопку «Выход»
        personalAccountPage.clickLogoutButton();

        // Проверяем переход на страницу авторизации
        assertTrue(
                "После выхода должна открыться страница авторизации",
                wait.until(ExpectedConditions.urlContains("/login"))
        );
    }
}


