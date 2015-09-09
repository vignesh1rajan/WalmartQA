package UI.Pages;

import UI.AbstractPageObject.HeadPageBase;
import UI.AbstractPageObject.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * Created by vrajan on 9/8/2015.
 */
public class ItemPage extends HeadPageBase {
    @FindBy(className = "js-product-heading") private WebElement itemHeading;
    @FindBy(id = "WMItemAddToCartBtn") private WebElement addToCartBtn;
    @FindBy(className = "js-pac-modal") private WebElement popUPcart;
    @FindBy(className = "PACViewCartBtn") private WebElement viewCartBtn;
    //@FindBy(id = "CartItemInfo") private WebElement

    /****************
     * Constructor
     * @param webDriver - WebDriver used to interact with the page
     * @param wait - The FluentWait on the webDriver with desired wait settings.
       */
    protected ItemPage(WebDriver webDriver, FluentWait wait) {
        super(webDriver, wait);
        PageFactory.initElements(getWebDriver(), this);
    }
    /****************
     * Constructor
     * @param pb - Generic PageBase for new page
     */
    protected ItemPage(PageBase pb) {
        super(pb);
        PageFactory.initElements(getWebDriver(), this);
    }

    public boolean validateItemPage(String itemName){
        return itemHeading.getText().equalsIgnoreCase(itemName);
    }

    public boolean addToCart(){
      isWebElementVisible(addToCartBtn);
      addToCartBtn.click();
      return isWebElementVisible(popUPcart);
    }

    public void viewCart(){
        isWebElementVisible(viewCartBtn);
        isWebElementInvisible(By.id("cart-spinner-backdrop"));



    }

  }
