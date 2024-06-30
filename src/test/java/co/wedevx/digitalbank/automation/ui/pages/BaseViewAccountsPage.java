package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

import static org.junit.jupiter.api.Assertions.fail;

public class BaseViewAccountsPage extends BaseMenuPage {

    public BaseViewAccountsPage(WebDriver driver) {
        super(driver);
    }
    private static String accountNameErrorMessage;
    private static String transactionAmountErrorMessage;

    public static String getAccountNameErrorMessage() {
        return accountNameErrorMessage;
    }

    public static String getTransactionAmountErrorMessage() {
        return transactionAmountErrorMessage;
    }


    @FindBy(xpath = "//span[text()='Error']/following-sibling::span")
    protected WebElement redErrorTextMessage;

    @FindBy(id = "selectedAccount")
    protected WebElement accountDropdown;

    @FindBy(id = "amount")
    protected WebElement transactionAmountTxtBox;

    @FindBy(xpath="//button[@class='btn btn-primary btn-sm']")
    protected WebElement submitBtn;

    @FindBy(xpath = "//div[@class='col-md-6 col-lg-3']")
    protected List<WebElement> accountCardsList;

    @FindBy(xpath = "//tbody/tr[1]")
    protected WebElement tableFirstRow;

    @FindBy(xpath = "//tbody/tr")
    protected List<WebElement> transactionTableRowsList;

    @FindBy(name = "transactionTable_length")
    protected WebElement showNumberDropdown;

    @FindBy(id = "transactionTable_previous")
    protected WebElement previousPageBtn;

    @FindBy(id = "transactionTable_next")
    protected WebElement nextPageBtn;

    @FindBy(xpath = "//ul[@class='pagination']/li")
    protected List<WebElement> paginationButtonsList;

    @FindBy(xpath = "//tbody//td")
    protected List<WebElement> transactionTableColumnsList;

    @FindBy(xpath = "//input[@type='search']")
    protected WebElement searchBarTxtBox;

    public void getAllErrorMessages(){
        accountNameErrorMessage = accountDropdown.getAttribute("validationMessage");
        transactionAmountErrorMessage = transactionAmountTxtBox.getAttribute("validationMessage");
    }

    public String getRedErrorMessage(){
        return redErrorMessage.getText();
    }

    public String getAccountNameDropdownValue(){
        Select selectAccount = new Select(accountDropdown);
        return selectAccount.getFirstSelectedOption().getText();
    }

    public String getTransactionAmountTxtBoxValue(){
        return transactionAmountTxtBox.getAttribute("value");
    }

    public Map<String,String> getAccountCardDetails(){
        Map<String,String> resultMap = new HashMap<>();
        WebElement accountBalance = getAccountCard().findElement(By.xpath(".//div[@class='h4 m-0'][2]"));
        resultMap.put("balance", accountBalance.getText().substring(accountBalance.getText().indexOf("$")+1));
        return resultMap;
    }

    public String getAccountCardBalance(){
        WebElement accountCard = getAccountCard();
        WebElement accountBalance = accountCard.findElement(By.xpath(".//div[@class='h4 m-0'][2]"));
        return accountBalance.getText().substring(accountBalance.getText().indexOf("$")+1);
    }

    public Map<String,String> getTableLatestTransactionDetails(){

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

    private WebElement getAccountCard() {
        if (accountCardsList.isEmpty()) {
            throw new NoSuchElementException("No existing checking accounts found");
        } else if (accountCardsList.size() > 1) {
            throw new NoSuchContextException("Multiple accounts were found. Specify Account Name");
        } return accountCardsList.get(0);
    }

    public void selectShowNumber(String showNumber) {
        Select selectShowNumberOfTransactions = new Select(showNumberDropdown);
        if (!showNumber.equals("10") && !showNumber.equals("25") && !showNumber.equals("50") && !showNumber.equals("100")) {
            throw new NoSuchElementException("Wrong selection of Show Number. Only available: 10, 25, 50, 100.");
        } selectShowNumberOfTransactions.selectByValue(showNumber);
    }

    public int getTransactionTableLength(){
        WebElement emptyFirstRow = tableFirstRow.findElement(By.xpath("./td"));
        if (emptyFirstRow.getText().contains("No matching records found")) {
            return 0;
        } return transactionTableRowsList.size();
    }

    public int getPageNumbers(){
        return paginationButtonsList.size()-2;
    }

    public void clickPreviousPageBtn(){
        if (previousPageBtn.isDisplayed() && previousPageBtn.isEnabled()) {
            previousPageBtn.click();
        }
    }

    public void clickNextPageBtn(){
        if (nextPageBtn.isDisplayed() && nextPageBtn.isEnabled()) {
            nextPageBtn.click();
        }
    }

    public void clickPageNumber(int pageNumber) {
        if (pageNumber > paginationButtonsList.size() - 2) {
            throw new NoSuchElementException("Wrong page number. There are only " + paginationButtonsList.size() + " pages available.");
        }
        for (int i = 0; i < paginationButtonsList.size(); i++) {
            WebElement pageBtn = paginationButtonsList.get(i);
            if (i == pageNumber && !pageBtn.getAttribute("class").contains("active")) {
                pageBtn.click();
            }
        }
    }

    public int getActivePageNum(){
        for (WebElement pageNum: paginationButtonsList) {
            if (pageNum.getAttribute("class").contains("active")) {
                return Integer.parseInt(pageNum.getText());
            }
        } return -1;
    }

    public void inputIntoSearchBar(String searchItem) {
        searchBarTxtBox.sendKeys(searchItem);
    }

    public void clearSearchBar(){
        searchBarTxtBox.clear();
    }

    public List<String> getAllTableCellValues() {
        List<String> resultList = new ArrayList<>();
        for (WebElement tableColumn: transactionTableColumnsList) {
            resultList.add(tableColumn.getText());
        }
        return resultList;
    }

}