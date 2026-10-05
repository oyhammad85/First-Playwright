package com.example;

import com.google.gson.annotations.SerializedName;
import net.datafaker.Faker;

public class APITests {

    public record Address(
        String street,
        String city,
        String state,
        String country,
        @SerializedName("postal_code") String postalCode
    ) {}

    public record User(
        @SerializedName("first_name") String firstName,
        @SerializedName("last_name") String lastName,
        Address address,
        String phone,
        String dob,
        String password,
        String email
    ) {
        public static User randomeUser() {
            Faker faker = new Faker();
            return new User(
                faker.name().firstName(),
                faker.name().lastName(),
                new Address(
                    faker.address().streetAddress(),
                    faker.address().city(),
                    faker.address().state(),
                    faker.address().countryCode(),
                    faker.address().zipCode()
                ),
                faker.phoneNumber().cellPhone(),
                "1990-01-01",
                "Az123!&xyZ",
                faker.internet().emailAddress()
            );
        }
    }
}
