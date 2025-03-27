package ru.netology.Selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardPositiveTest {
    private String getDate(int countDay, String format) {
        return LocalDate.now().plusDays(countDay).format(DateTimeFormatter.ofPattern(format));
    }

    @ParameterizedTest
    @CsvSource({
            "Саратов, 3, Татьяна, +79172518555",
            "Москва, 4, Татьяна Казанцева, +12345678910",
            "Санкт-Петербург, 30, Татьяна-Казанцева, +00000000000",
            "Ростов-на-Дону, 365, Татьяна-Казанцева Юрьевна, +99999999999"
    })
    void shouldRegisterAccount(String city, int correctDate, String name, String phone) {
        String currentDay = getDate(correctDate, "dd.MM.yyyy");
//        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] [type=tel]").sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] [type=tel]").setValue(currentDay);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $(".checkbox__box").click();
        $(".button__text").click();
        /*
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $x("//div[contains(text(), 'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").find(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        */
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__title").shouldHave(text("Успешно!"));
        $("[data-test-id=notification] .notification__content").shouldHave(text("Встреча успешно забронирована на " + currentDay));
    }
}
