package ru.netology.test;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {}

    public static class Registration {
        private Registration() {}

        public static RegistrationByCardInfo generate() {
            Faker faker = new Faker(new Locale("ru"));
            Random random = new Random();

            LocalDate date = LocalDate.now().plusDays(random.nextInt(17) + 3);
            LocalDate dateNext = date.plusDays(random.nextInt(3) + 1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            String textDate = date.format(formatter);
            String textDateNext = dateNext.format(formatter);
            return new RegistrationByCardInfo(
                    faker.name().firstName() + " " + faker.name().lastName(),
                    faker.numerify("+79#########"),
                    faker.address().city(),
                    textDate,
                    textDateNext
            );
        }
    }
}
