package UI.Pages;

import UI.AbstractPageObject.HeadPageBase;
import UI.AbstractPageObject.PageBase;
import org.openqa.selenium.By;
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
    @FindBy(className = "center") private WebElement center;

    @FindBy(className = "js-searchbar-input") private WebElement searchBar;
    @FindBy(className = "searchbar-submit") private WebElement searchSubmit;
   // @FindBy(className = "recent-orders-heading") private WebElement accountContainer;

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

    /**
     * Goes to the login page by clicking on the account link
     * @return
     */
    public boolean navLoginPage(){
        accountLink.click();
        Assert.assertTrue(isWebElementVisible(loginContainer), "Login Page is not visible");
        //selectScreen(accountLink,accountSignIn);
        return isWebElementVisible(loginContainer);
    }

    /**
     * Given the login page, the username and password are entered and sign-in button is clicked. Then makes sure the
     * account page is visible afterwards to confirm login.
     * @param username
     * @param passWord
     * @return
     */
    public boolean logIn(String username, String passWord){

        Assert.assertTrue(isWebElementVisible(userID), "UserID input not visible");
        userID.sendKeys(username);
        userPass.sendKeys(passWord);
        signInBtn.click();
        Assert.assertTrue(isWebElementInvisible(By.className("js-login-wrapper-container")), " Sign in Button did not disappear");
        isWebElementVisible(center);
        WebElement accountContainer = center.findElement(By.className("container-account"));
        return isWebElementVisible(accountContainer);
    }

    /**
     * Uses the search bar lookup the item. Returns the list page of items from the search
     * @param item - name of item to be searched
     * @return ItemListPage - List page containing object from the search
     */
    public ItemListPage serchForItem(String item){
        searchBar.sendKeys(item);
        searchSubmit.click();
        return new ItemListPage(this);
    }

}
