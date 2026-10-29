
package praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAccountPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Ссылка «Конструктор»
    private final By constructorLink =
            By.xpath("//p[text()='Конструктор']");

    // Логотип Stellar Burgers
    private final By logo =
            By.xpath("//div[contains(@class, 'AppHeader_header__logo')]");

    // Кнопка «Выход»
    private final By logoutButton =
            By.xpath("//button[text()='Выход']");

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickConstructorLink() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorLink))
                .click();
    }

    public void clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(logo))
                .click();
    }

    public void clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton))
                .click();
    }
}

