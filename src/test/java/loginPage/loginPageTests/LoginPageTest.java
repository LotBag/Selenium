package loginPage.loginPageTests;

import loginPage.POM.LoginPagePOM;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static loginPage.UtilsForLoginPageTest.Utils.*;

public class LoginPageTest {

    private String[] testResult = new String[5];
    private static Properties properties;

    private static String correctPassword;
    private static String correctLogin;
    private static String seleniumHubUrl;
    private static String loginPageUrl;
    private static String testUserDashBoardUrl;

    private WebDriver driver;
    private WebDriverWait wait;
    private final By wrongDataMessage = By.xpath("/html/body/div[2]/div[1]/div/div[1]/div[2]/div[1]");

    public String[] getTestResult() {
        return testResult;
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        properties = loadProperties();
        correctPassword = properties.getProperty("correctPassword");
        correctLogin = properties.getProperty("correctLogin");
        seleniumHubUrl = properties.getProperty("seleniumHubUrl");
        loginPageUrl = properties.getProperty("loginPageUrl");
        testUserDashBoardUrl = properties.getProperty("testUserDashBoardUrl");
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        driver = setUpRemoteWebDriver(seleniumHubUrl, "chrome");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(loginPageUrl);
   }
   
    @DisplayName("Test with correct login and password")
    @Test
    public void CorrectDataLoginTest() throws MalformedURLException {
        String testName = "CorrectLoginTest";
        LoginPagePOM loginPage = new LoginPagePOM(driver);
        loginPage.login(correctLogin, correctPassword);

        wait.until(ExpectedConditions.urlToBe(testUserDashBoardUrl));

        Assertions.assertEquals(driver.getCurrentUrl(), testUserDashBoardUrl);

        if (driver.getCurrentUrl().equals(testUserDashBoardUrl)) {
            testResult[0] = "Test " + testName + " passed";
            takeScreenShot(testName, driver);
        } else  {
            testResult[0] = "Test " + testName + " failed";
            takeScreenShot(testName, driver);
        }


    }

    @DisplayName("Remember me test")
    @Test
    public void RememberMeTest() throws MalformedURLException {
        String testName = "RememberMeTest";
        LoginPagePOM loginPage = new LoginPagePOM(driver);
        loginPage.setCheckBox();
        loginPage.login(correctLogin, correctPassword);

        wait.until(ExpectedConditions.urlToBe(testUserDashBoardUrl));

        driver.get(loginPageUrl);

        wait.until(ExpectedConditions.urlToBe(testUserDashBoardUrl));

        Assertions.assertEquals(driver.getCurrentUrl(), testUserDashBoardUrl);

        if (driver.getCurrentUrl().equals(testUserDashBoardUrl)) {
            testResult[0] = "Test " + testName + " passed";
        } else  {
            testResult[0] = "Test " + testName + " failed";
            takeScreenShot(testName, driver);
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
            takeScreenShot(testName, driver);
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
            takeScreenShot(testName, driver);
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
            takeScreenShot(testName, driver);
        }
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
