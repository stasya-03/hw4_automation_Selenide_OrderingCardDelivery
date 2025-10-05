package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {

    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSubmitRequest() {
        Selenide.open("http://localhost:9999");
        String planDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Оренбург");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79223344556");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .should(Condition.appear, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + planDate));
    }
}
