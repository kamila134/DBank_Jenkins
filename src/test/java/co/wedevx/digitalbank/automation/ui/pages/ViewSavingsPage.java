package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.fail;

public class ViewSavingsPage extends BaseViewAccountsPage{

    public ViewSavingsPage(WebDriver driver) {
        super(driver);
    }


    public Map<String, String> getSvgAccountCardDetails(String accountName) {
        savingsMenuBtn.click();
        viewSavingsBtn.click();

        Map<String, String> resultMap = new HashMap<>();
        WebElement accountCard = getSvgAccountCard(accountName);
        WebElement accountBalance = accountCard.findElement(By.xpath(".//div[@class='h4 m-0'][2]"));
        resultMap.put("balance", accountBalance.getText().substring(accountBalance.getText().indexOf("$") + 1));
        return resultMap;
    }

    public String getSvgAccountCardBalance(String accountName) {
        savingsMenuBtn.click();
        viewSavingsBtn.click();

        WebElement accountCard = getSvgAccountCard(accountName);
        WebElement accountBalance = accountCard.findElement(By.xpath(".//div[@class='h4 m-0'][2]"));
        return accountBalance.getText().substring(accountBalance.getText().indexOf("$") + 1);
    }

    public Map<String,String> getSvgTableLatestTransactionDetails(String accountName){
        savingsMenuBtn.click();
        viewSavingsBtn.click();

        WebElement activeAccountCardSwitch = getSvgAccountCard(accountName).findElement(By.xpath(".//span[@class='switch-handle']"));
        activeAccountCardSwitch.click();

        Map<String,String> resultMap = new HashMap<>();
        if (!tableFirstRow.isDisplayed()) {
            resultMap.put("Error", "No transactions found");
            fail();
        } else {
            WebElement transactionDate = tableFirstRow.findElement(By.xpath("./td[1]"));
            WebElement transactionDescription = tableFirstRow.findElement(By.xpath("./td[3]"));
            WebElement transactionAmount = tableFirstRow.findElement(By.xpath("./td[4]"));
            WebElement transactionBalance = tableFirstRow.findElement(By.xpath("./td[5]"));
            String date = transactionDate.getText().substring(0, transactionDate.getText().indexOf(" "));
            String description = transactionDescription.getText().substring(transactionDescription.getText().indexOf("-")+2);
            String amount = transactionAmount.getText().substring(1);
            String balance = transactionBalance.getText().substring(1);
            resultMap.put("date",date);
            resultMap.put("description",description);
            resultMap.put("amount",amount);
            resultMap.put("balance",balance);
        }
        return resultMap;
    }

    private WebElement getSvgAccountCard(String accountName) {
        WebElement resultElement = null;
        int count = 0;
        if (accountCardsList.isEmpty()) {
            throw new NoSuchElementException("No existing savings accounts found");
        } for (WebElement accountCard: accountCardsList) {
            WebElement accountHeader = accountCard.findElement(By.xpath(".//div[@contenteditable='true']"));
            String cardAccountName = accountHeader.getText();
            if (cardAccountName.equalsIgnoreCase(accountName)) {
                resultElement = accountCard;
                count++;
            }
        } if (count == 0 )  {
            throw new NoSuchElementException(accountName + " wasn't found");
        } else if (count > 1) {
            throw new NoSuchContextException("Multiple accounts " + accountName + " were found");
        } else return resultElement;
    }

}
