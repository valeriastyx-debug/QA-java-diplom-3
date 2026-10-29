
package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поле «Имя»
    private final By nameField =
            By.xpath("//label[text()='Имя']/following-sibling::input");

    // Поле «Email»
    private final By emailField =
            By.xpath("//label[text()='Email']/following-sibling::input");

    // Поле «Пароль»
    private final By passwordField =
            By.xpath("//input[@type='password']");

    // Кнопка «Зарегистрироваться»
    private final By registerButton =
            By.xpath("//button[text()='Зарегистрироваться']");

    // Ссылка «Войти» под формой регистрации
    private final By loginLink =
            By.xpath("//a[text()='Войти']");

    // Сообщение об ошибке короткого пароля
    private final By passwordError =
            By.xpath("//p[text()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField))
                .sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink))
                .click();
    }

    public boolean isPasswordErrorDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordError)
        ).isDisplayed();
    }

    public void register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }
}
