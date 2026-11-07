
package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private static final String BASE_URL =
            "https://stellarburgers.education-services.ru/";

    private final WebDriverWait wait;

    // Кнопка «Войти в аккаунт»
    private final By loginButton =
            By.xpath("//button[contains(normalize-space(.), 'Войти в аккаунт')]");

    // Кнопка «Личный Кабинет»
    private final By personalAccountButton =
            By.xpath("//p[text()='Личный Кабинет']");

    // Вкладки конструктора
    private final By bunsTab =
            By.xpath("//span[normalize-space()='Булки']");

    private final By saucesTab =
            By.xpath("//span[normalize-space()='Соусы']");

    private final By fillingsTab =
            By.xpath("//span[normalize-space()='Начинки']");

    // Контейнеры вкладок для проверки активного состояния
    private final By activeBunsTab =
            By.xpath("//span[normalize-space()='Булки']/..");

    private final By activeSaucesTab =
            By.xpath("//span[normalize-space()='Соусы']/..");

    private final By activeFillingsTab =
            By.xpath("//span[normalize-space()='Начинки']/..");

    public MainPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Step("Нажать кнопку «Войти в аккаунт»")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }

    @Step("Нажать кнопку «Личный кабинет»")
    public void clickPersonalAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton))
                .click();
    }

    @Step("Переключиться на вкладку «Булки»")
    public void clickBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab))
                .click();
    }

    @Step("Переключиться на вкладку «Соусы»")
    public void clickSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab))
                .click();
    }

    @Step("Переключиться на вкладку «Начинки»")
    public void clickFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab))
                .click();
    }

    @Step("Проверить активность вкладки «Булки»")
    public boolean isBunsTabActive() {
        return wait.until(ExpectedConditions.attributeContains(
                activeBunsTab,
                "class",
                "tab_tab_type_current"
        ));
    }

    @Step("Проверить активность вкладки «Соусы»")
    public boolean isSaucesTabActive() {
        return wait.until(ExpectedConditions.attributeContains(
                activeSaucesTab,
                "class",
                "tab_tab_type_current"
        ));
    }

    @Step("Проверить активность вкладки «Начинки»")
    public boolean isFillingsTabActive() {
        return wait.until(ExpectedConditions.attributeContains(
                activeFillingsTab,
                "class",
                "tab_tab_type_current"
        ));
    }

    @Step("Проверить открытие главной страницы")
    public boolean isMainPageOpened() {
        return wait.until(
                ExpectedConditions.urlToBe(BASE_URL)
        );
    }
}




