package tests;

import com.codeborne.selenide.Configuration;
import config.DriverConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentHelper.*;

public class TestBase {

    static DriverConfig driverConfig = ConfigFactory.create(DriverConfig.class);

    @BeforeAll
    static void setup() {

        addListener("AllureSelenide", new AllureSelenide());
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://demoqa.com/automation-practice-form";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        //gradle clean test -Dweb.browser="opera"
        Configuration.browser = System.getProperty("web.browser", "chrome");

        //gradle clean test
        //gradle clean test -Dremote.web.driver="https://user1:1234@selenoid.autotests.cloud/wd/hub/"
        //gradle clean test -Dremote.web.driver="https://%s:%s@selenoid.autotests.cloud/wd/hub/"

        /*
        "https://%s:%s@selenoid.autotests.cloud/wd/hub/"
        String.format("https://%s:%s@selenoid.autotests.cloud/wd/hub/", "hello", "world")
            ->
            "https://hello:world@selenoid.autotests.cloud/wd/hub/"
         */

        String remoteWebDriver = System.getProperty("remote.web.driver");

        if (remoteWebDriver != null) {
            String user = driverConfig.remoteWebUser();
            String password = driverConfig.remoteWebPassword();
            Configuration.remote = String.format(remoteWebDriver, user, password);
            Configuration.browserVersion = System.getProperty("web.browser.version");
        }
    }

    @AfterEach
    void afterEach() {
        attachScreenshot("Last Screenshot");
        attachPageSource();
        attachAsText("Browser Consol Logs", getConsoleLogs());

        //gradle clean test -Dremote.web.driver="https://user1:1234@selenoid.autotests.cloud/wd/hub"
        // -Dvideo.storage="https://selenoid.autotests.cloud/video/";
        if (System.getProperty("video.storage") != null)
            attachVideo();
        closeWebDriver();
    }
}
