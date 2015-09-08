package UI.Pages;

import UI.AbstractPageObject.HeadPageBase;
import UI.AbstractPageObject.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;

/**
 * Created by Nexis on 9/7/2015.
 */
public class ItemListPage extends HeadPageBase {
    @FindBy(className = "tile-container") private WebElement items;

    @FindBy(className = "js-product-title") private WebElement itemContainer;

    /****************
     * Constructor
     * @param webDriver - WebDriver used to interact with the page
     * @param wait - The FluentWait on the webDriver with desired wait settings.
     */
    public ItemListPage(WebDriver webDriver, FluentWait wait) {
        super(webDriver, wait);
        PageFactory.initElements(getWebDriver(),this);
    }
    /****************
     * Constructor
     * @param pb - Generic PageBase for new page
     */
    public ItemListPage(PageBase pb) {
        super(pb);
        PageFactory.initElements(getWebDriver(),this);
    }

    public boolean findItem(String itemName){
        boolean found = false;
        List<WebElement>  itemList = items.findElements(By.className("js-product-title"));
        for(WebElement item: itemList){
            if(item.getText().equalsIgnoreCase(itemName))
                item.click();
                found = true;

        }
        return found;
    }

    public boolean getItemPage(){
        isWebElementInvisible(By.className("js-product-title"));
        return isWebElementVisible(itemContainer);


    }
}
