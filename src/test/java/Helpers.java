import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Helpers {
    static WebElement waiter(WebDriverWait wait, String By) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.xpath(By))
        );
    }

    static List<WebElement> multipleWaiter(WebDriverWait wait, String By) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                AppiumBy.xpath(By)
        ));
    }
}
