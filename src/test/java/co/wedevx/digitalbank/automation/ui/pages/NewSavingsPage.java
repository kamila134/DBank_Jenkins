package co.wedevx.digitalbank.automation.ui.pages;

import co.wedevx.digitalbank.automation.ui.models.BankingType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

public class NewSavingsPage extends BaseMenuPage{

    public NewSavingsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="Savings")
    private WebElement savingsAccTypeRadioBtn;

    @FindBy(id="Money Market")
    private WebElement moneyMarketTypeRadioBtn;

    @FindBy(id="Individual")
    private WebElement individualOwnershipTypeRadioBtn;

    @FindBy(id="Joint")
    private WebElement jointOwnershipTypeRadioBtn;

    @FindBy(id="name")
    private WebElement accNameTxtBox;

    @FindBy(id="openingBalance")
    private WebElement depositTxtBox;

    @FindBy(id="newSavingsSubmit")
    private WebElement newSavingsSubmitBtn;

    @FindBy(id="newSavingsReset")
    private WebElement resetBtn;

    public void openSavingsAccount(List<BankingType> savingsAccountList){
        BankingType firstRow = savingsAccountList.get(0);
        savingsMenuBtn.click();
        newSavingsMenuBtn.click();
        if (firstRow.getAccountType().equalsIgnoreCase("Savings")){
            savingsAccTypeRadioBtn.click();
        } else if (firstRow.getAccountType().equalsIgnoreCase("Money Market")){
            moneyMarketTypeRadioBtn.click();
        } else {
            throw new NoSuchElementException("Wrong Account Type option provided. Only supports Savings and Money Market");
        }
        if(firstRow.getOwnershipType().equalsIgnoreCase("Individual")){
            individualOwnershipTypeRadioBtn.click();
        } else if (firstRow.getOwnershipType().equalsIgnoreCase("Joint")) {
            jointOwnershipTypeRadioBtn.click();
        } else {
            throw new NoSuchElementException("Wrong Ownership Type option provided. Only supports Individual and Joint");
        }
        accNameTxtBox.sendKeys(firstRow.getAccountName());
        depositTxtBox.sendKeys(String.valueOf(firstRow.getInitialDepositAmount()));

        newSavingsSubmitBtn.submit();
    }

    public void openSavingsAccount(BankingType bankingType){
        savingsMenuBtn.click();
        newSavingsMenuBtn.click();
        if (bankingType.getAccountType().equalsIgnoreCase("Savings")){
            savingsAccTypeRadioBtn.click();
        } else if (bankingType.getAccountType().equalsIgnoreCase("Money Market")){
            moneyMarketTypeRadioBtn.click();
        } else {
            throw new NoSuchElementException("Wrong Account Type option provided. Only supports Savings and Money Market");
        }
        if(bankingType.getOwnershipType().equalsIgnoreCase("Individual")){
            individualOwnershipTypeRadioBtn.click();
        } else if (bankingType.getOwnershipType().equalsIgnoreCase("Joint")) {
            jointOwnershipTypeRadioBtn.click();
        } else {
            throw new NoSuchElementException("Wrong Ownership Type option provided. Only supports Individual and Joint");
        }
        accNameTxtBox.sendKeys(bankingType.getAccountName());
        depositTxtBox.sendKeys(String.valueOf(bankingType.getInitialDepositAmount()));

        newSavingsSubmitBtn.submit();
    }



}
