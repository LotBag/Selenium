package loginPage;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginPageTest {

    private String[] testResult = new String[5];

    private final String correctPassword = "TestPassword";
    private final String correctLogin = "QA2";
    private WebDriver driver;
    private WebDriverWait wait;
    private final By wrongDataMessage = By.xpath("/html/body/div[2]/div[1]/div/div[1]/div[2]/div[1]");

    private static WebDriver setUpRemoteWebDriver() throws MalformedURLException {
        URL hubURl = new URL("http://localhost:4444/");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName("chrome");

        return new RemoteWebDriver(hubURl, caps);
    }

    private void takeScreenShot(String fileName) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(srcFile, new File("C:\\Users\\LotBag\\Desktop\\Hexlet\\Selenium\\src" +
                    "\\test\\resources" + fileName + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getTestResult() {
        return testResult;
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        driver = setUpRemoteWebDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("http://193.233.193.42:9091/");
   }
   
    @DisplayName("Test with correct login and password")
    @Test
    public void CorrectDataLoginTest() throws MalformedURLException {
        String testName = "CorrectLoginTest";
        LoginPagePOM loginPage = new LoginPagePOM(driver);
        loginPage.login(correctLogin, correctPassword);

        wait.until(ExpectedConditions.urlToBe("http://193.233.193.42:9091/dashboard?id=158-39"));

        Assertions.assertEquals(driver.getCurrentUrl(), "http://193.233.193.42:9091/dashboard?id=158-39");

        if (driver.getCurrentUrl().equals("http://193.233.193.42:9091/dashboard?id=158-39")) {
            testResult[0] = "Test " + testName + " passed";
            takeScreenShot(testName);
        } else  {
            testResult[0] = "Test " + testName + " failed";
            takeScreenShot(testName);
        }


    }

    @DisplayName("Remember me test")
    @Test
    public void RememberMeTest() throws MalformedURLException {
        String testName = "RememberMeTest";
        LoginPagePOM loginPage = new LoginPagePOM(driver);
        loginPage.setCheckBox();
        loginPage.login(correctLogin, correctPassword);

        wait.until(ExpectedConditions.urlToBe("http://193.233.193.42:9091/dashboard?id=158-39"));

        driver.get("http://193.233.193.42:9091");

        wait.until(ExpectedConditions.urlToBe("http://193.233.193.42:9091/dashboard?id=158-39"));

        Assertions.assertEquals(driver.getCurrentUrl(), "http://193.233.193.42:9091/dashboard?id=158-39");

        if (driver.getCurrentUrl().equals("http://193.233.193.42:9091/dashboard?id=158-39")) {
            testResult[0] = "Test " + testName + " passed";
        } else  {
            testResult[0] = "Test " + testName + " failed";
            takeScreenShot(testName);
        }
    }

    @DisplayName("Test with correct login and incorrect password")
    @Test
    public void IncorrectPasswordTest() throws MalformedURLException {
        String testName = "IncorrectPasswordTest";
        LoginPagePOM loginPage = new LoginPagePOM(driver);
        String wrongPassword = "wrongPassword";
        loginPage.login(correctLogin, wrongPassword);

        wait.until(ExpectedConditions.textToBe(wrongDataMessage, "Некорректное имя пользователя или пароль."));

        Assertions.assertEquals("Некорректное имя пользователя или пароль.",
                driver.findElement(wrongDataMessage).getText());

        if ("Некорректное имя пользователя или пароль.".equals(driver.findElement(wrongDataMessage).getText())) {
            testResult[0] = "Test " + testName + " passed";
        } else  {
            testResult[0] = "Test " + testName + " failed";
            takeScreenShot(testName);
        }
    }

    @DisplayName("Test with correct password and incorrect login")
    @Test
    public void IncorrectLoginTest() throws MalformedURLException {
        String testName = "IncorrectLoginTest";
        LoginPagePOM loginPage = new LoginPagePOM(driver);
        String wrongLogin = "wrongLogin";
        loginPage.login(wrongLogin, correctPassword);

        wait.until(ExpectedConditions.textToBe(wrongDataMessage, "Некорректное имя пользователя или пароль."));

        Assertions.assertEquals("Некорректное имя пользователя или пароль.",
                driver.findElement(wrongDataMessage).getText());

        if ("Некорректное имя пользователя или пароль.".equals(driver.findElement(wrongDataMessage).getText())) {
            testResult[0] = "Test " + testName + " passed";
        } else  {
            testResult[0] = "Test " + testName + " failed";
            takeScreenShot(testName);
        }
    }

    @DisplayName("Forgot password, incorrect Email test")
    @Test
    public void ForgotPasswordWithIncorrectEmailTest() throws MalformedURLException {
        String testName = "ForgotPasswordWithIncorrectEmailTest";
        LoginPagePOM loginPage = new LoginPagePOM(driver);
        loginPage.forgotPassword();

        wait.until(ExpectedConditions.urlContains("/hub/auth/restore"));

        String incorrectEmail = "incorrectEmail";
        driver.findElement(By.name("email")).sendKeys(incorrectEmail);

        By incorrectEmailMessage = By.xpath("/html/body/div[2]/div[1]/div/form/div[1]/div/div/div");
        wait.until(ExpectedConditions.textToBe(incorrectEmailMessage,
                "Требуется корректный адрес электронной почты"));

        Assertions.assertEquals("Требуется корректный адрес электронной почты",
                driver.findElement(incorrectEmailMessage).getText());

        if ("Требуется корректный адрес электронной почты".equals(driver.findElement(incorrectEmailMessage).getText())) {
            testResult[0] = "Test " + testName + " passed";
        } else  {
            testResult[0] = "Test " + testName + " failed";
            takeScreenShot(testName);
        }
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
