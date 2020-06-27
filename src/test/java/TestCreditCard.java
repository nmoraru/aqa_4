import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class TestCreditCard {
    String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    String dateUnderMin = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    String dateOverMin = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[name = name].input__control").setValue("Иван Иванов");
        $("[name = phone].input__control").setValue("+79005554433");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id = notification]").waitUntil(visible, 15000);
    }

    @Test
    void shouldDateAutocomplete() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[name = name].input__control").setValue("Иван Иванов");
        $("[name = phone].input__control").setValue("+79005554433");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id = notification]").waitUntil(visible, 15000);
    }

    @Test
    void shouldHyphenBetweenNameAndSurname() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id=name] input").setValue("Петров-Разумовский Василий");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]").waitUntil(visible, 15000);
    }

    @Test
    void shouldDateOverMin() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(dateOverMin);
        $("[name = name].input__control").setValue("Иван Иванов");
        $("[name = phone].input__control").setValue("+79005554433");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id = notification]").waitUntil(visible, 15000);
    }

    @Test
    void shouldDateUnderMin() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(dateUnderMin);
        $("[name = name].input__control").setValue("Иван Иванов");
        $("[name = phone].input__control").setValue("+79005554433");
        $(".checkbox").click();
        $(".button").click();
        $(".input_invalid").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldEmptyDate() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[name = name].input__control").setValue("Иван Иванов");
        $("[name = phone].input__control").setValue("+79005554433");
        $(".checkbox").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldEmptyName() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[name = phone].input__control").setValue("+79005554433");
        $(".checkbox").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEmptyTelNumber() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(dateOverMin);
        $("[name = name].input__control").setValue("Иван Иванов");
        $(".checkbox").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldEmptyAll() {
        open("http://localhost:9999");
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldUncheckedCheckbox() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[name = name].input__control").setValue("Иван Иванов");
        $("[name = phone].input__control").setValue("+79005554433");
        $(".button").click();
        $(".checkbox__text").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldInputPatronymic() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Петров Петр Петрович");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldEngName() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNumericInName() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("123");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSpecialCharacterInName() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Ив@нов В@силий");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSpacesBeforeTextName() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("   Иванов Василий");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSpacesAfterText() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Василий   ");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldMoreSpacesBetweenNameAndSurname() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов   Василий");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldHyphenInStartName() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("-Иван Иванов");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldHyphenInFinishName() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов-");
        $("[data-test-id=phone] input").setValue("+78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSpaceBetweenTelNumber() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+78005 554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр," +
                " например, +79012345678."));
    }

    @Test
    void shouldSpaceStartTelNumber() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue(" +78005554433");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр," +
                " например, +79012345678."));
    }

    @Test
    void shouldSpaceFinishTelNumber() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+78005554433 ");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр," +
                " например, +79012345678."));
    }

    @Test
    void shouldTelNumberMoreMax() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+780055544332");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр," +
                " например, +79012345678."));
    }

    @Test
    void shouldTelNumberLessMax() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("Москва");
        $("[data-test-id='date'] [type='tel']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id='date'] [type='tel']").setValue(date);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+7800555443");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр," +
                " например, +79012345678."));
    }
    
}
