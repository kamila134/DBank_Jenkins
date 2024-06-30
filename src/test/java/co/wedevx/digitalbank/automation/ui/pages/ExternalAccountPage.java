package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ExternalAccountPage extends BaseMenuPage{

    public ExternalAccountPage(WebDriver driver) {super(driver);}
    private String dropdownValidationText;

    @FindBy(id = "bankId")
    private WebElement bankingProvidersDropdown;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm']")
    private WebElement submitBtn;

    public void linkExternalAccount(){
        externalAccountMenuBtn.click();
        linkExternalAccountBtn.click();
        dropdownValidationText = bankingProvidersDropdown.getAttribute("validationMessage");
    }

    public String getFullRedErrorMessage(){
        return fullRedErrorMessage.getText();
    }

    public String getDropdownErrorMessage(){
        return dropdownValidationText;
    }

    public List<WebElement> getDropdownOptionsList(){
        Select selectBankingProvider = new Select(bankingProvidersDropdown);
        return selectBankingProvider.getOptions();
    }

    public void clickSubmit(){
        submitBtn.submit();
    }

}
