package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    void shouldSubmitRequest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Виктор Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79206161994");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldHyphenInNameField() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Николаев Иван-Чай");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79035001010");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Граев Пётр");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79111111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldCheckboxNotMarked() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Виктор Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79206161994");
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных" +
                " и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    @Test
    public void shouldEmptyPhoneField() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров Олег");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldPhoneWithoutPlusSymbol() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Нечаев Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89111111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldMoreElevenCharactersInPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+791111111119");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldLessElevenCharactersInPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7911111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldEmptyNameField() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7911111111");
        ;
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldEmptyNameField2() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Sergeev Asan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7911111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldEmptyNameField3() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов 23");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7911111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
}