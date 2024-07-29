import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import net.bytebuddy.build.Plugin;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class Clock {
    AndroidDriver driver;
    WebDriverWait wait;

    @BeforeTest
    @Parameters({"uid", "appPackage", "appActivity"})
    void setup(
            String uid,
            String appPackage,
            String appActivity
    ) throws MalformedURLException {
//      https://github.com/appium/java-client
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid(uid)
                .setAppPackage(appPackage)
                .setAppActivity(appActivity);
        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    }

    @AfterMethod
    void printSource() {
        System.out.println(driver.getPageSource());
    }

    @Test(priority = 1)
    @Parameters({"city"})
    void addClock(String city) throws Exception {

        Helpers.waiter(wait, "//android.widget.Button[@content-desc=\"Add city\"]").click();

        Helpers.waiter(wait, "//*[@text=\"Search for a city\"]").sendKeys(city);

        Helpers.waiter(wait, "//*[@resource-id=\"com.google.android.deskclock:id/search_results_view\"]/android.widget.LinearLayout[1]").click();

        String result = Helpers.waiter(wait, "//android.widget.TextView[@resource-id=\"com.google.android.deskclock:id/city_name\" and @text=\""+ city +"\"]").getText();

        Assert.assertEquals(city, result);
    }

    @Test(priority = 2)
    @Parameters({"city"})
    void removeClock(String city) throws Exception {
//      https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md

//        ((JavascriptExecutor) driver).executeScript("mobile: pinchOpenGesture", ImmutableMap.of(
//                "elementId", ((RemoteWebElement) Helpers.waiter(wait, "//android.widget.TextView[@resource-id=\"com.google.android.deskclock:id/city_name\" and @text=\""+ city +"\"]")).getId(),
//                "percent", 0.75
//        ));

//        Thread.sleep(1000);

        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) Helpers.waiter(wait, "//android.widget.TextView[@resource-id=\"com.google.android.deskclock:id/city_name\" and @text=\""+ city +"\"]")).getId(),
                "direction", "right",
                "percent", 0.75
        ));

        Thread.sleep(5000);

        List<WebElement> result = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"com.google.android.deskclock:id/city_name\" and @text=\""+ city +"\"]"));

        Assert.assertEquals(result.size(), 0);

    }

    @AfterTest
    void cleanup() {
//        driver.quit();
    }
}
