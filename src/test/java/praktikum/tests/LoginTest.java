
package praktikum.tests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.client.UserApiClient;
import praktikum.pages.ForgotPasswordPage;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;

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

        assertSuccessfulLogin();
    }

    @Test
    public void loginFromPersonalAccountTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickPersonalAccountButton();
        loginPage.login(email, password);

        assertSuccessfulLogin();
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

        assertSuccessfulLogin();
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

        assertSuccessfulLogin();
    }

    private void assertSuccessfulLogin() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        assertTrue(
                wait.until(ExpectedConditions.urlToBe(
                        "https://stellarburgers.education-services.ru/"
                ))
        );
    }
}
