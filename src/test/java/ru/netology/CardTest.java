package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

class CardTest {

    @BeforeAll
    public static void setupAll() {
        open("http://localhost:7777");
    }

    @Test
    void shouldSubmitFormWithValidData() {
        $("#name").setValue("Иван Иванов");
        $("#phone").setValue("+79161234567");
        $("[type='checkbox']").click(); // Флажок согласия

        $(".button").click();

        $(".notification_status_ok").shouldBe(Condition.visible); // Проверяем успешность отправки заявки
    }

    @Test
    void shouldNotSubmitFormWithoutAgreement() {
        $("#name").setValue("Иван Иванов");
        $("#phone").setValue("+79161234567");

        $(".button").click();

        $(".error").shouldHave(Condition.text("Я согласен с условиями обработки персональных данных")); // Проверяем ошибку отсутствия галочки
    }

    @Test
    void shouldNotSubmitFormWithInvalidPhoneNumber() {
        $("#name").setValue("Иван Иванов");
        $("#phone").setValue("12345678901"); // Неверный телефон
        $("[type='checkbox']").click(); // Флажок согласия

        $(".button").click();

        $(".error").shouldHave(Condition.text("Номер телефона должен содержать 11 цифр и начинаться с '+'"));
    }

    @Test
    void shouldNotSubmitFormWithInvalidName() {
        $("#name").setValue("Ivan Ivanov"); // Имя на латинице
        $("#phone").setValue("+79161234567");
        $("[type='checkbox']").click(); // Флажок согласия

        $(".button").click();

        $(".error").shouldHave(Condition.text("Имя должно содержать только русские буквы, дефисы и пробелы"));
    }
}