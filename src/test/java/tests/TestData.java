package tests;

import com.github.javafaker.Faker;

public class TestData {

    Faker faker = new Faker();

    public String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            userEmail = faker.internet().emailAddress(),
            gender = faker.demographic().sex(),
            userNumber = faker.phoneNumber().subscriberNumber(10),
            subject = "Maths",
            birthdayMonth = "May",
            birthdayYear = "1990",
            hobbies = "Music",
            file = "maxresdefault.jpg",
            currentAddress = faker.address().streetAddress(),
            city = "Delhi",
            state = "NCR";
}
