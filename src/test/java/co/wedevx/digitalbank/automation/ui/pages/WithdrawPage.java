package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class WithdrawPage extends BaseViewAccountsPage{

    public WithdrawPage(WebDriver driver) {
        super(driver);
    }



    @FindBy(xpath = "//button[@class='btn btn-danger btn-sm']")
    private WebElement resetBtn;


    public void makeAWithdrawal(String accountName, String withdrawalAmount){
        withdrawMenuBtn.click();
        if (accountName != null) {
            Select selectAccount = new Select(accountDropdown);
            selectAccount.selectByIndex(1);
        }

        if (withdrawalAmount != null) {
            transactionAmountTxtBox.sendKeys(withdrawalAmount);
        }
        getAllErrorMessages();
        submitBtn.submit();
    }

    public void makeAWithdrawal(String withdrawAmount){
        withdrawMenuBtn.click();
        getAllErrorMessages();
        Select selectAccount = new Select(accountDropdown);
        if (selectAccount.getOptions().size() > 1) {
            selectAccount.selectByIndex(1);
        }
        transactionAmountTxtBox.sendKeys(withdrawAmount);
        submitBtn.submit();
    }

    public void makeAWithdrawAndReset(double withdrawAmount){
        withdrawMenuBtn.click();
        Select selectAccount = new Select(accountDropdown);
        selectAccount.selectByIndex(1);
        transactionAmountTxtBox.sendKeys(String.valueOf(withdrawAmount));
        getAllErrorMessages();
        resetBtn.click();
    }


}
