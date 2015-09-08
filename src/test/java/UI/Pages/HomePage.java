package UI.Pages;

import UI.AbstractPageObject.HeadPageBase;
import UI.AbstractPageObject.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

/**
 * Created by Nexis on 9/7/2015.
 */
public class HomePage extends HeadPageBase{

    @FindBy(className = "nav-main") private WebElement mainNav;
    @FindBy(className = "login-wrapper-container") private WebElement loginContainer;
    @FindBy(id = "login-username") private WebElement userID;
    @FindBy(id = "login-password") private WebElement userPass;
    @FindBy(className = "login-sign-in-btn") private WebElement signInBtn;
    @FindBy(id = "container-account") private WebElement accountContainer;

    /****************
     * Constructor
     * @param webDriver - WebDriver used to interact with the page
     * @param wait - The FluentWait on the webDriver with desired wait settings.
     */
    public HomePage(WebDriver webDriver, FluentWait wait) {
        super(webDriver, wait);
        PageFactory.initElements(getWebDriver(),this);
    }

    public HomePage(PageBase pb) {
        super(pb);
        PageFactory.initElements(getWebDriver(),this);
    }

    /**
     *  Resets the browser to the main page  via the url
     * @return new Homepage object with copy constuctor
     */
    public HomePage navigateToHome(){
        getWebDriver().navigate().to("https://www.walmart.com");
        Assert.assertTrue(isWebElementVisible(mainNav), " HomePage Header is not visible");
        return new HomePage(this);
    }

    public boolean navLoginPage(){
        accountLink.click();
        Assert.assertTrue(isWebElementVisible(loginContainer), "Login Page is not visible");
        //selectScreen(accountLink,accountSignIn);
        return isWebElementVisible(loginContainer);
    }

    public boolean logIn(String username, String passWord){

        Assert.assertTrue(isWebElementVisible(userID), "UserID input not visible");
        userID.sendKeys(username);
        userPass.sendKeys(passWord);
        signInBtn.click();
        return isWebElementVisible(accountContainer);
    }

}