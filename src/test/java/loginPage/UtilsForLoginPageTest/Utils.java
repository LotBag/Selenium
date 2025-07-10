package loginPage.UtilsForLoginPageTest;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Utils {
    public static WebDriver setUpRemoteWebDriver(String url, String browser) throws MalformedURLException {
        URL hubURl = new URL(url);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser);

        return new RemoteWebDriver(hubURl, caps);
    }

    public static void takeScreenShot(String fileName, WebDriver driver) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(srcFile, new File("C:\\Users\\LotBag\\Desktop\\Hexlet\\Selenium\\src" +
                    "\\test\\resources\\Screenshots" + fileName + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties loadProperties() throws IOException {
        File file = new File("C:\\Users\\LotBag\\Desktop\\Hexlet\\Selenium\\src\\test\\resources" +
                "\\loginPage.properties");

        Properties prop = new Properties();
        prop.load(new FileReader(file));

        return prop;
    }
}
