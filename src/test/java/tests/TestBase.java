package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentHelper.*;

public class TestBase {

    @BeforeAll
    static void setup() throws MalformedURLException {
        //System.out.println(System.getProperties());
        //System.out.println(System.getProperty("a"));
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
        //gradle clean test -Dremote.web.driver="https://user1:1234@selenoid.autotests.cloud/wd/hub";
        String remoteWebDriver = System.getProperty("remote.web.driver");
        if (remoteWebDriver != null)
            Configuration.remote = remoteWebDriver;
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
