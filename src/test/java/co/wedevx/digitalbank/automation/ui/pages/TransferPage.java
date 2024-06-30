package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.NoSuchElementException;

public class TransferPage extends BaseViewAccountsPage{

    private String fromAccountDropdownErrorMessage;
    private String toAccountDropdownErrorMessage;
    private String transferAmountTxtBoxErrorMessage;

    public TransferPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "fromAccount")
    private WebElement fromAccountDropdown;

    @FindBy(id = "toAccount")
    private WebElement toAccountDropdown;

    @FindBy(id = "amount")
    private WebElement transferAmountTxtBox;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm']")
    private WebElement submitTransferBtn;





    public String getFromAccountDropdownErrorMessage() {
        return fromAccountDropdownErrorMessage;
    }

    public String getToAccountDropdownErrorMessage() {
        return toAccountDropdownErrorMessage;
    }

    public String getTransferAmountTxtBoxErrorMessage() {
        return transferAmountTxtBoxErrorMessage;
    }

    public void transferBetweenAccounts(String fromAccount, String toAccount, String transferAmount) {
        transferMenuBtn.click();
        if (fromAccount != null) {
            Select selectFromAccount = new Select(fromAccountDropdown);
            selectAccount(selectFromAccount,fromAccount);
        }
        if (toAccount != null) {
            Select selectToAccount = new Select(toAccountDropdown);
            selectAccount(selectToAccount,toAccount);
        }
        if (transferAmount != null) {
            transferAmountTxtBox.sendKeys(transferAmount);
        }
        getFieldsErrorMessages();
        submitTransferBtn.submit();
    }

    public String transferBetweenNonExistentAccounts(String fromAccount, String toAccount) throws NoSuchElementException {
        transferMenuBtn.click();
        String nonExistentAccountException = "lol";
        if (fromAccount != null) {
            Select selectFromAccount = new Select(fromAccountDropdown);
            try {
                selectAccount(selectFromAccount,fromAccount);
            } catch (NoSuchElementException e) {
                nonExistentAccountException = e.toString() + e.getMessage();
            }
        }
        if (toAccount != null) {
            Select selectToAccount = new Select(toAccountDropdown);
            try {
                selectAccount(selectToAccount,toAccount);
            } catch (NoSuchElementException e) {
                nonExistentAccountException = e.toString();
            }
        }
        return nonExistentAccountException;
    }

    public void getFieldsErrorMessages(){
        fromAccountDropdownErrorMessage = fromAccountDropdown.getAttribute("validationMessage");
        toAccountDropdownErrorMessage = toAccountDropdown.getAttribute("validationMessage");
        transferAmountTxtBoxErrorMessage = transferAmountTxtBox.getAttribute("validationMessage");
    }

    public String getFullRedErrorMessage(){
        return redErrorMessage.getText() + " " + redErrorTextMessage.getText();
    }


}
