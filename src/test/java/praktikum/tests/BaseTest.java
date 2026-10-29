
package praktikum.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;

public class BaseTest {

    protected WebDriver driver;

    private static final String BASE_URL =
            "https://stellarburgers.education-services.ru/";

    private static final String YANDEX_BROWSER_PATH =
            "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe";

    private static final String YANDEX_DRIVER_PATH =
            System.getProperty("user.home")
                    + "\\chromedriver152\\chromedriver-win64\\chromedriver.exe";

    @Before
    public void setUp() {

        String browser = System.getProperty("browser", "chrome");

        ChromeOptions options = new ChromeOptions();

        if ("yandex".equalsIgnoreCase(browser)) {

            options.setBinary(YANDEX_BROWSER_PATH);

            ChromeDriverService service =
                    new ChromeDriverService.Builder()
                            .usingDriverExecutable(new File(YANDEX_DRIVER_PATH))
                            .build();

            driver = new ChromeDriver(service, options);

        } else if ("chrome".equalsIgnoreCase(browser)) {

            driver = new ChromeDriver(options);

        } else {

            throw new IllegalArgumentException(
                    "Неизвестный браузер: " + browser
            );
        }

        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @After
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}



