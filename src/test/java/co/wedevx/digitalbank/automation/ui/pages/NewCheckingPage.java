package co.wedevx.digitalbank.automation.ui.pages;

import co.wedevx.digitalbank.automation.ui.models.BankingType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

public class NewCheckingPage extends BaseMenuPage{

    public NewCheckingPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="Standard Checking")
    private WebElement standardAccTypeRadioBtn;

    @FindBy(id="Interest Checking")
    private WebElement interestAccTypeRadioBtn;

    @FindBy(id="Individual")
    private WebElement individualOwnershipTypeRadioBtn;

    @FindBy(id="Joint")
    private WebElement jointOwnershipTypeRadioBtn;

    @FindBy(id="name")
    private WebElement accNameTxtBox;

    @FindBy(id="openingBalance")
    private WebElement depositTxtBox;

    @FindBy(id="newCheckingSubmit")
    private WebElement newCheckingSubmitBtn;


    //action methods

    public void openCheckingAccount(List<BankingType> chqAccountList) {
        BankingType firstRow = chqAccountList.get(0);
        checkingMenuBtn.click();
        newCheckingMenuBtn.click();
        if (firstRow.getAccountType().equals("Standard Checking")) {
            standardAccTypeRadioBtn.click();
        } else if (firstRow.getAccountType().equals("Interest Checking")) {
            interestAccTypeRadioBtn.click();
        } else {
            throw new NoSuchElementException("Wrong Account Type option provided. Only supports Standard Checking and Interest Checking");
        }
        if (firstRow.getOwnershipType().equals("Individual")){
            individualOwnershipTypeRadioBtn.click();
        } else if(firstRow.getOwnershipType().equals("Joint")){
            jointOwnershipTypeRadioBtn.click();
        } else {
            throw new NoSuchElementException("Wrong Ownership Type option provided. Only supports Individual and Joint");
        }
        accNameTxtBox.sendKeys(firstRow.getAccountName());
        depositTxtBox.sendKeys(String.valueOf(firstRow.getInitialDepositAmount()));

        newCheckingSubmitBtn.submit();
    }

    public void openCheckingAccount(BankingType bankingType) {
        checkingMenuBtn.click();
        newCheckingMenuBtn.click();
        if (bankingType.getAccountType().equals("Standard Checking")) {
            standardAccTypeRadioBtn.click();
        } else if (bankingType.getAccountType().equals("Interest Checking")) {
            interestAccTypeRadioBtn.click();
        } else {
            throw new NoSuchElementException("Wrong Account Type option provided. Only supports Standard Checking and Interest Checking");
        }
        if (bankingType.getOwnershipType().equals("Individual")){
            individualOwnershipTypeRadioBtn.click();
        } else if(bankingType.getOwnershipType().equals("Joint")){
            jointOwnershipTypeRadioBtn.click();
        } else {
            throw new NoSuchElementException("Wrong Ownership Type option provided. Only supports Individual and Joint");
        }
        accNameTxtBox.sendKeys(bankingType.getAccountName());
        depositTxtBox.sendKeys(String.valueOf(bankingType.getInitialDepositAmount()));

        newCheckingSubmitBtn.submit();
    }


}
