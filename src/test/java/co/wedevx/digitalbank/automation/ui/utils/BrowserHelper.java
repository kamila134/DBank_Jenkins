package co.wedevx.digitalbank.automation.ui.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrowserHelper {

    public static WebElement fluentWaitUntilElementVisible(WebDriver driver, By locator, int timeToWaitInSec) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeToWaitInSec))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);

        WebElement visibleElement = null;
        try {
            visibleElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        } catch (TimeoutException e) {
            System.out.println("Element wasn't found in given " + timeToWaitInSec + " seconds");
        } catch (NoSuchElementException e) {
            System.out.println("No such element was found");
        }
        return visibleElement;
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView()", element);
    }

    public static WebElement clickElementWithText(WebDriver driver, String elementText) {
        String elementXpath = "//*[contains(text(),'" + elementText.trim() + "')]";
        List<WebElement> foundElementsList = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            foundElementsList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elementXpath)));
        } catch (NoSuchElementException e) {
            System.out.println("No elements with text \"" + elementText + "\" were found");
        }
        WebElement elementToClick = null;
        if (foundElementsList.size() == 1) {
            elementToClick = foundElementsList.get(0);
        } else if (foundElementsList.size() > 1){
            for (WebElement element: foundElementsList) {
                if (element.getText().equalsIgnoreCase(elementText.trim())) {
                    elementToClick = element;
                }
            }
        }
        elementToClick.click();
        return elementToClick;
    }

    public static void fillTextInput (WebDriver driver, WebElement element, String text) {
        scrollToElement(driver, element);
        if (element.isDisplayed() && element.isEnabled()){
            element.clear();
            element.sendKeys(text);
        }
    }

//    Scroll to the text input element to ensure it is in view.
//    Check if the text input element is interactable (visible and enabled).
//    If interactable, clear any existing value and fill the input with the provided value.


}


