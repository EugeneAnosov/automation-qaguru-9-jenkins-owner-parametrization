package tests;

import org.junit.jupiter.api.Test;
import pageobjects.PracticeFormRegistrationPage;

public class PracticeFormRegistrationTest extends TestBase {

    @Test
    public void CheckDataRegistrationForm() {

        PracticeFormRegistrationPage registrationForm = new PracticeFormRegistrationPage();

        registrationForm.openPage()
                .fillForm()
                .submitForm()
                .assertData();
    }
}
