package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;

    // Кнопка «Войти в аккаунт» на главной странице
    private final By loginButton =
            By.xpath("//button[contains(normalize-space(.), 'Войти в аккаунт')]");

    // Кнопка «Личный Кабинет» в шапке сайта
    private final By personalAccountButton =
            By.xpath("//p[text()='Личный Кабинет']");

    // Разделы конструктора
    private final By bunsTab =
            By.xpath("//span[text()='Булки']");

    private final By saucesTab =
            By.xpath("//span[text()='Соусы']");

    private final By fillingsTab =
            By.xpath("//span[text()='Начинки']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    public void clickBunsTab() {
        driver.findElement(bunsTab).click();
    }

    public void clickSaucesTab() {
        driver.findElement(saucesTab).click();
    }

    public void clickFillingsTab() {
        driver.findElement(fillingsTab).click();
    }

    public boolean isBunsTabActive() {
        return driver.findElement(bunsTab)
                .findElement(By.xpath(".."))
                .getAttribute("class")
                .contains("tab_tab_type_current");
    }

    public boolean isSaucesTabActive() {
        return driver.findElement(saucesTab)
                .findElement(By.xpath(".."))
                .getAttribute("class")
                .contains("tab_tab_type_current");
    }

    public boolean isFillingsTabActive() {
        return driver.findElement(fillingsTab)
                .findElement(By.xpath(".."))
                .getAttribute("class")
                .contains("tab_tab_type_current");
    }
}

