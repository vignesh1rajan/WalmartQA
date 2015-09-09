
package UI.AbstractPageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by vrajan on 8/29/2015.
 */
public abstract class HeadPageBase extends PageBase {

    /***
     HeadPageBase contains all functions and objects related to top navigation PageObjects that remain in all the pages
     ***/

    @FindBy(id = "department-navigation") protected WebElement deptNavigation;
    @FindBy(linkText = "My Account") protected WebElement accountLink;
    @FindBy(linkText = "Sign In") protected WebElement accountSignIn;
    @FindBy(className = "cart-spinner-backdrop") WebElement loadingIcon;



    public HeadPageBase(WebDriver webDriver, FluentWait wait){
        super(webDriver,wait);
    }

    public HeadPageBase(PageBase pb){
        super(pb);
    }

    private void pointAt(WebElement element) {
        Actions actions = new Actions(getWebDriver());
        actions.moveToElement(element);
        actions.build().perform();
    }

    protected void selectScreen(WebElement headElement, WebElement element) {
        //Try to display the head element's list item up to 3 times.
        int i = 0;
        do {
            webDriver.switchTo().window(webDriver.getWindowHandle());
            pointAt(headElement);
            pointAt(element);
            i++;
        } while(!isWebElementVisible(element) && i <= 5);
        getWebDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        element.click();
    }
}
