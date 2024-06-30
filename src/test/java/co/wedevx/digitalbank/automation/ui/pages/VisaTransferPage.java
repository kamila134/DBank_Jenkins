package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class VisaTransferPage extends BaseMenuPage{

    public VisaTransferPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "extAccount")
    private WebElement visaAccNumberDropdown;

    @FindBy(id = "extAmount")
    private WebElement transferAmountTxtBox;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm']")
    private WebElement submitToVisaBtn;

    @FindBy(xpath = "//span[contains(text(),'Unavailable')]")
    private WebElement redErrorMessage;

    public void transferToVisa(String transferAmount){
        visaTransferBtn.click();
        Select selectVisaAccount = new Select(visaAccNumberDropdown);
        selectAccount(selectVisaAccount,"Account");

        transferAmountTxtBox.sendKeys(transferAmount);

        submitToVisaBtn.submit();
    }

    public String getRedErrorMessage(){
        return redErrorMessage.getText();
    }
}
