package loginPage.POM;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPagePOM {
    private WebDriver driver;

    private final By userNameField = By.xpath("//*[@id='username']");
    private final By passwordField = By.xpath("//*[@id='password']");
    private final By checkBox = By.xpath("//*[@id='rg-checkbox-0']");
    private final By loginButton = By.xpath("//div[@class='form-row']//button[contains(@class, " +
            "'auth-button')]");
    private final By forgotPasswordLink = By.xpath("//div[not(contains(@class, " +
            "'login-page__padded-panel'))]/flexible-link/a/span[text()='Сбросить пароль'][1]");


    public LoginPagePOM(WebDriver driver) {
        this.driver = driver;
    }

    private void enterUserName(String userName) {
        driver.findElement(userNameField).sendKeys(userName);
    }

    private void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void setCheckBox() {
        if(!driver.findElement(checkBox).isEnabled()) {
            driver.findElement(checkBox).click();
        }
    }

    public void uncheckCheckBox() {
        if(driver.findElement(checkBox).isEnabled()) {
            driver.findElement(checkBox).click();
        }
    }

    private void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUserName(username);
        enterPassword(password);
        clickLoginButton();
    }

    public void forgotPassword() {
        driver.findElement(forgotPasswordLink).click();
    }
}
