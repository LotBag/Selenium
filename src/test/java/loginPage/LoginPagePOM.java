package loginPage;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPagePOM {
    private WebDriver driver;

    private final By userNameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By checkBox = By.id("rg-checkbox-0");
    private final By loginButton = By.xpath("/html/body/div[2]/div[1]/div/div[2]/form/div[2]/div[2]/button");
    private final By forgotPasswordLink = By.linkText("Сбросить пароль");


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
