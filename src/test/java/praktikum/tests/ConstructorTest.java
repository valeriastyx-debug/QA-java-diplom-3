
package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import praktikum.pages.MainPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Переход на вкладку «Соусы»")
    @Description("Проверка переключения конструктора на раздел «Соусы»")
    public void switchToSaucesTabTest() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickSaucesTab();

        assertTrue(
                "После нажатия должна быть активна вкладка «Соусы»",
                mainPage.isSaucesTabActive()
        );
    }

    @Test
    @DisplayName("Переход на вкладку «Начинки»")
    @Description("Проверка переключения конструктора на раздел «Начинки»")
    public void switchToFillingsTabTest() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickFillingsTab();

        assertTrue(
                "После нажатия должна быть активна вкладка «Начинки»",
                mainPage.isFillingsTabActive()
        );
    }

    @Test
    @DisplayName("Переход на вкладку «Булки»")
    @Description("Проверка возвращения на вкладку «Булки» после перехода на «Соусы»")
    public void switchToBunsTabTest() {
        MainPage mainPage = new MainPage(driver);

        // Сначала переключаемся на вкладку «Соусы»
        mainPage.clickSaucesTab();

        assertTrue(
                "Перед возвратом к булкам должна быть активна вкладка «Соусы»",
                mainPage.isSaucesTabActive()
        );

        // Затем возвращаемся на вкладку «Булки»
        mainPage.clickBunsTab();

        assertTrue(
                "После нажатия должна быть активна вкладка «Булки»",
                mainPage.isBunsTabActive()
        );
    }
}


