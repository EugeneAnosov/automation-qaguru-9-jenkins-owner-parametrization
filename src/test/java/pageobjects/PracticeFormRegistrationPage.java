package pageobjects;

import io.qameta.allure.Step;
import tests.TestBase;
import tests.TestData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PracticeFormRegistrationPage extends TestBase {

    TestData testData = new TestData();

    @Step("Open Student Registration Form")
    public PracticeFormRegistrationPage openPage() {
        open(baseUrl);
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        return this;
    }

    @Step("Fill the form using data from Faker")
    public PracticeFormRegistrationPage fillForm() {
        setCommonData(testData);
        setBirthdayDate(testData.birthdayMonth, testData.birthdayYear);
        setHobbySubjectData(testData);
        uploadImage(testData);
        setCityState(testData);

        return this;
    }

    @Step("Fill First/Last Name")
    private void setCommonData(TestData testData) {
        $("#firstName").setValue(testData.firstName);
        $("#lastName").setValue(testData.lastName);
        $("#userEmail").setValue(testData.userEmail);
        $(byText(testData.gender)).click();
        $("#userNumber").setValue(testData.userNumber);
    }

    @Step("Set Subject and Hobby")
    private void setHobbySubjectData(TestData testData) {
        $("#subjectsInput").setValue(testData.subject).pressTab();
        $(byText(testData.hobbies)).click();
        $("#uploadPicture").uploadFromClasspath("img/" + testData.file);
    }

    @Step("Upload image")
    private void uploadImage(TestData testData) {
        $("#uploadPicture").uploadFromClasspath("img/" + testData.file);
    }

    @Step("Fill Current Address, City and State")
    private void setCityState(TestData testData) {
        $("#currentAddress").setValue(testData.currentAddress);
        $("#react-select-3-input").setValue(testData.state).pressEnter();
        $("#react-select-4-input").setValue(testData.city).pressEnter();
    }

    @Step("Choose Birthday Date on the datepicker")
    private void setBirthdayDate(String birthdayMonth, String birthdayYear) {
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOptionContainingText(birthdayMonth);
        $(".react-datepicker__year-select").selectOptionByValue(birthdayYear);
        $(".react-datepicker__day--001").click();
    }

    @Step("Submit Form")
    public PracticeFormRegistrationPage submitForm() {
        $("#submit").click();
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

        return this;
    }

    @Step("Check correct filled data")
    public PracticeFormRegistrationPage assertData() {
        $(".table-responsive").shouldHave(
                text(testData.firstName + " " + testData.lastName),
                text(testData.userEmail),
                text(testData.gender),
                text(testData.birthdayMonth),
                text(testData.birthdayYear),
                text(testData.subject),
                text(testData.hobbies),
                text(testData.file),
                text(testData.currentAddress),
                text(testData.state + " " + testData.city));

        return this;
    }
}
