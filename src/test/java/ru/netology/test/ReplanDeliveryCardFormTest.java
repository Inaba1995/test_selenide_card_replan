package ru.netology.test;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class ReplanDeliveryCardFormTest {

    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    static void InputForm(String city, String date, String name, String phone) {
        open("http://localhost:9999");

        $("[data-test-id='city']").$("[class='input__control']").setValue(city);

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name']").$("[class='input__control']").setValue(name);
        $("[data-test-id='phone']").$("[class='input__control']").setValue(phone);
        $("[data-test-id='agreement']").click();
    }

    @Test
    void shouldPreventSendRequestMultipleTimes() {
        RegistrationByCardInfo regData = DataGenerator.Registration.generate();
        InputForm(regData.getCity(), regData.getDate(), regData.getName(), regData.getPhone());

        $(".button").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + regData.getDate()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(regData.getDateNext());
        $(".button").click();

        $(".button__text")
                .shouldHave(Condition.text("Запланировать"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

        $(".notification__content")


                .shouldHave(Condition.text("Встреча успешно запланирована на " + regData.getDateNext()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }
}
