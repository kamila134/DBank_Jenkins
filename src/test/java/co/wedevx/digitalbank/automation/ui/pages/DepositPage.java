package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class DepositPage extends BaseViewAccountsPage{

    public DepositPage(WebDriver driver) {
        super(driver);
    }
    

    @FindBy(xpath = "//button[@class='btn btn-danger btn-sm']")
    private WebElement resetBtn;

    public void makeADeposit(String accountName, String depositAmount){
        depositMenuBtn.click();
        if (accountName != null) {
            Select selectAccount = new Select(accountDropdown);
            selectAccount.selectByIndex(1);
        } if (depositAmount != null) {
            transactionAmountTxtBox.sendKeys(depositAmount);

        }
        getAllErrorMessages();
        submitBtn.submit();
    }

    public void makeADeposit(String depositAmount){
        depositMenuBtn.click();
        Select selectAccount = new Select(accountDropdown);
        if (selectAccount.getOptions().size() > 1) {
            selectAccount.selectByIndex(1);
        }
        transactionAmountTxtBox.sendKeys(depositAmount);
        getAllErrorMessages();
        submitBtn.submit();
    }

    public void makeADepositAndReset(double depositAmount){
        depositMenuBtn.click();
        getAllErrorMessages();
        Select selectAccount = new Select(accountDropdown);
        selectAccount.selectByIndex(1);
        transactionAmountTxtBox.sendKeys(String.valueOf(depositAmount));
        resetBtn.click();
    }


}
