package UI;

import UI.AbstractPageObject.HeadPageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * Created by vrajan on 8/29/2015.
 */
public class TestBase {


    protected WebDriver webDriver = null;
    protected FluentWait wait = null;
    protected HeadPageBase pageBase = null;



    @BeforeClass
    public void beforeClass(){
        try{
            //webDriver = new ChromeDriver();
            FirefoxProfile profile = new FirefoxProfile();
            profile.setAcceptUntrustedCertificates(true);
            webDriver = new FirefoxDriver();
        }catch(Exception ex){
            System.out.println(" Error creating webdriver :" + ex.getMessage());
        }
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        wait = new FluentWait<WebDriver>(webDriver)
                .withTimeout(10,TimeUnit.SECONDS)
                .pollingEvery(1,TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        pageBase =  instantiateHeadObject(webDriver,wait);
    }

    @AfterClass(groups =
            {"SmokeTest"})
    public void afterClass(){
        webDriver.close();
        webDriver.quit();
        webDriver = null;
    }
    protected HeadPageBase instantiateHeadObject(WebDriver webDriver, FluentWait<WebDriver> wait){
        throw new UnsupportedOperationException(" The function must be overridden. Given the webDriver and wait must" +
                " create and return  a new instance of the page object");
    }

}
