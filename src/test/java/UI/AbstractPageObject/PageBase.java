package UI.AbstractPageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by vrajan on 8/31/2015.
 */
public abstract class PageBase {

    /**
     * PageBase class contains the webdriver and wait object. It also contains utility functions common to all pages
     */

    protected final WebDriver webDriver;
    protected FluentWait<WebDriver> wait = null;

    protected PageBase(WebDriver webDriver) {
        this(webDriver, new FluentWait<WebDriver>(webDriver).withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class));
    }

    /**
     * Shared UI.AbstractPageObject.PageBase constructor with wait override
     *
     * @param webDriver
     * @param wait
     */
    protected PageBase(WebDriver webDriver, FluentWait<WebDriver> wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    /**
     * UI.AbstractPageObject.PageBase Copy Constructor. Uses the same webDriver and wait objects as the original.
     *
     * @param pb
     */
    protected PageBase(PageBase pb) {
        this.webDriver = pb.webDriver;
        this.wait = pb.wait;
    }

    /*******************
     * Utility funtions
     *******************/


    /**
     * Uses the standard wait object to look for the visibility of the given WebElement. Turns two exceptions that could
     * be thrown (NoSuchElementException and TimeoutException) into mere false return values.
     *
     * @param element
     * @return true if the given element is visible. False if the element does not exist after a certain amount of time,
     *         or is merely not visible.
     */
    protected boolean isWebElementVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch(TimeoutException ex) {
            return false;
        } catch(NoSuchElementException ex) {
            return false;
        }
    }

    /**
     * Uses the standard wait object to look for the absence of the given BY locator.
     *
     * @param by
     * @return true if the given element does not exist or is merely not visible. False if the element is still visible
     *         after a certain amount of time.
     */
    protected boolean isWebElementInvisible(By by) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch(TimeoutException ex) {
            return false;
        }
    }
    /**
     * Returns web driver instance used by this page.
     *
     * @return webDriver
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }
}
