package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class ReplanDeliveryCardFormTest {

    static void inputForm(String city, String date, String name, String phone) {
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
        inputForm(regData.getCity(), regData.getDate(), regData.getName(), regData.getPhone());

        $(".button").click();
        $("[data-test-id='success-notification']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + regData.getDate()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(regData.getDateNext());
        $(".button").click();

        $("[data-test-id='replan-notification']")
                .shouldHave(Condition.text("Перепланировать"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
        $("[data-test-id='replan-notification'] button").click();

        $("[data-test-id='success-notification']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + regData.getDateNext()), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
