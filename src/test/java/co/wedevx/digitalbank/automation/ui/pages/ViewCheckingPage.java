package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.fail;

public class ViewCheckingPage extends BaseViewAccountsPage{

    public ViewCheckingPage(WebDriver driver) {
        super(driver);
    }

   //below methods are for cases with multiple accounts + multiple banking types

    public Map<String,String> getChqAccountCardDetails(String accountName){
        checkingMenuBtn.click();
        viewCheckingBtn.click();

        Map<String,String> resultMap = new HashMap<>();
        WebElement accountCard = getChqAccountCard(accountName);
        WebElement accountBalance = accountCard.findElement(By.xpath(".//div[@class='h4 m-0'][2]"));
        resultMap.put("balance", accountBalance.getText().substring(accountBalance.getText().indexOf("$")+1));
        return resultMap;
    }

    public String getChqAccountCardBalance(String accountName){
        checkingMenuBtn.click();
        viewCheckingBtn.click();

        WebElement accountCard = getChqAccountCard(accountName);
        WebElement accountBalance = accountCard.findElement(By.xpath(".//div[@class='h4 m-0'][2]"));
        return accountBalance.getText().substring(accountBalance.getText().indexOf("$")+1);
    }

    public Map<String,String> getChqTableLatestTransactionDetails(String accountName){
        checkingMenuBtn.click();
        viewCheckingBtn.click();

        WebElement activeAccountCardSwitch = getChqAccountCard(accountName).findElement(By.xpath(".//span[@class='switch-handle']"));
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

    private WebElement getChqAccountCard(String accountName) {
        WebElement resultElement = null;
        int count = 0;
        if (accountCardsList.isEmpty()) {
            throw new NoSuchElementException("No existing checking accounts found");
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
