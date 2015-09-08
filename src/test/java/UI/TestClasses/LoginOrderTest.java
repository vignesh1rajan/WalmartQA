package UI.TestClasses;

import UI.AbstractPageObject.HeadPageBase;
import UI.Pages.HomePage;
import UI.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.AbstractCollection;

/**
 * Created by Nexis on 9/7/2015.
 */
public class LoginOrderTest extends TestBase {
    HomePage homePage = null;

    String userName = "vignesh1rajan@gmail.com";
    String passWord = "mpt232";

    /********************
     * Overridden methods
     *******************/

    @Override
    public HeadPageBase instantiateHeadObject(WebDriver webDriver,FluentWait<WebDriver> wait) {
        return new HomePage(webDriver, wait);
    }

    /***************
     * Test Methods
     **************/

    @Test(description =  "01) Navigate To HomePage", groups = "OrderTest")
    public void mainPage(){
        homePage = (HomePage) pageBase;
        homePage.navigateToHome();
    }

    @Test(description = "02) Navigate To accounts Page",dependsOnMethods = "mainPage",groups = "OrderTest")
    public void navigateToLoginPage(){
        homePage.navLoginPage();
    }

    @Test(description = "03) Navigate To accounts Page", dependsOnMethods = "navigateToLoginPage",groups = "OrderTest")
    public void logIn(){
        Assert.assertTrue(homePage.logIn(userName, passWord), "Log in Test failed");

    }
}
