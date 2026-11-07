
package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriverWait wait;

    // Поле ввода email
    private final By emailField =
            By.xpath("//label[text()='Email']/following-sibling::input");

    // Поле ввода пароля
    private final By passwordField =
            By.xpath("//input[@type='password']");

    // Кнопка «Войти»
    private final By loginButton =
            By.xpath("//button[text()='Войти']");

    // Ссылка «Зарегистрироваться»
    private final By registerLink =
            By.xpath("//a[text()='Зарегистрироваться']");

    // Ссылка «Восстановить пароль»
    private final By forgotPasswordLink =
            By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Step("Ввести email пользователя")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField))
                .sendKeys(email);
    }

    @Step("Ввести пароль пользователя")
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField))
                .sendKeys(password);
    }

    @Step("Нажать кнопку «Войти»")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }

    @Step("Перейти на страницу регистрации")
    public void clickRegisterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink))
                .click();
    }

    @Step("Перейти на страницу восстановления пароля")
    public void clickForgotPasswordLink() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink))
                .click();
    }

    @Step("Авторизоваться под тестовым пользователем")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Проверить открытие страницы авторизации")
    public boolean isLoginPageOpened() {
        return wait.until(
                ExpectedConditions.urlContains("/login")
        );
    }
}

