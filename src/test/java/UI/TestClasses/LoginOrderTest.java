package UI.TestClasses;

import UI.AbstractPageObject.HeadPageBase;
import UI.Pages.HomePage;
import UI.Pages.ItemListPage;
import UI.Pages.ItemPage;
import UI.TestBase;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
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
    ItemListPage itemListPage = null;
    ItemPage itemPage = null;

    String userName = "vignesh1rajan@gmail.com";
    String passWord = "mpt232";

    String searchItem = "Socks";
    String itemName = "Hanes Men's 12 Pack Crew Socks";

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

    @Test(description =  "01) Navigate to HomePage", groups = "OrderTest")
    public void mainPage(){
        homePage = (HomePage) pageBase;
        homePage.navigateToHome();
    }

    @Test(description = "02) Navigate to accounts Page",dependsOnMethods = "mainPage",groups = "OrderTest")
    public void navigateToLoginPage(){
        homePage.navLoginPage();
    }

    @Test(description = "03) Navigate to accounts Page", dependsOnMethods = "navigateToLoginPage",groups = "OrderTest")
    public void logIn(){
        Assert.assertTrue(homePage.logIn(userName, passWord), "Log in Test failed");
    }

    @Test(description = "04) Log in to account",dependsOnMethods = "logIn", groups = "OrderTest")
    public void searchForTest(){
        itemListPage =  homePage.serchForItem(searchItem);
    }

    @Test(description = "04) Search for Item",dependsOnMethods = "searchForTest", groups = "OrderTest")
    public void findItemInList(){
        Assert.assertTrue(itemListPage.findItem(itemName), "Item not found");

    }

    @Test(description = "05) Browse to Item Page, Add to cart",dependsOnMethods = "findItemInList", groups = "OrderTest")
    public void addItemToCart(){
        itemPage = itemListPage.getItemPage();
        Assert.assertTrue(itemPage.validateItemPage(itemName), "Item Page Name does not match item " + itemName);
        itemPage.addToCart();
        itemPage.viewCart(itemName);
        itemPage.clearCart();
    }


}
