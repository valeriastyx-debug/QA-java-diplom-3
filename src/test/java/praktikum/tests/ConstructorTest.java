
package praktikum.tests;

import org.junit.Test;
import praktikum.pages.MainPage;

import static org.junit.Assert.assertTrue;

public class ConstructorTest extends BaseTest {

    @Test
    public void switchToSaucesTabTest() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickSaucesTab();

        assertTrue(
                "После нажатия должна быть активна вкладка «Соусы»",
                mainPage.isSaucesTabActive()
        );
    }

    @Test
    public void switchToFillingsTabTest() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickFillingsTab();

        assertTrue(
                "После нажатия должна быть активна вкладка «Начинки»",
                mainPage.isFillingsTabActive()
        );
    }

    @Test
    public void switchToBunsTabTest() {
        MainPage mainPage = new MainPage(driver);

        // Сначала переключаемся на другую вкладку
        mainPage.clickSaucesTab();

        // Затем возвращаемся к булкам
        mainPage.clickBunsTab();

        assertTrue(
                "После нажатия должна быть активна вкладка «Булки»",
                mainPage.isBunsTabActive()
        );
    }
}

