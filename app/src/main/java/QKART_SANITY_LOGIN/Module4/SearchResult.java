package QKART_SANITY_LOGIN.Module4;

import java.util.List;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SearchResult {
    WebElement parentElement;

    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
    }

    /*
     * Return title of the parentElement denoting the card content section of a
     * search result
     */
    public String getTitleofResult() {
        String titleOfSearchResult = "";
        // Find the element containing the title (product name) of the search result and
        // assign the extract title text to titleOfSearchResult
        WebElement element = parentElement.findElement(By.xpath("//p[@class=\"MuiTypography-root MuiTypography-body1 css-yg30e6\"]"));
        titleOfSearchResult = element.getText();
        return titleOfSearchResult;
    }

    /*
     * Return Boolean denoting if the open size chart operation was successful
     */
    public Boolean openSizechart() {
        try {
            WebElement element = parentElement.findElement(By.xpath("//div[3]/div/div[2]/div[1]/div/div[1]/button"));
            element.click();

            Thread.sleep(3000);
            return true;
        } catch (Exception e) {
            System.out.println("Exception while opening Size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the close size chart operation was successful
     */
    public Boolean closeSizeChart(WebDriver driver) {
        try {
            synchronized (driver) {
                driver.wait(2000);
            }
            
            Actions action = new Actions(driver);

            action.sendKeys(Keys.ESCAPE);
            action.perform();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("MuiDialog-paperScrollPaper")));

            return true;
        } catch (Exception e) {
            System.out.println("Exception while closing the size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean based on if the size chart exists
     */
    public Boolean verifySizeChartExists() {
        Boolean status = false;
        try {
            /*
             * Check if the size chart element exists. If it exists, check if the text of
             * the element is "SIZE CHART". If the text "SIZE CHART" matches for the
             * element, set status = true , else set to false
             */
            WebElement element = parentElement.findElement(By.xpath("//button[text() = 'Size chart']"));
            status = element.getText().equals("SIZE CHART");

            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if the table headers and body of the size chart matches the
     * expected values
     */
    public Boolean validateSizeChartContents(List<String> expectedTableHeaders, List<List<String>> expectedTableBody,
            WebDriver driver) {
        Boolean status = true;
        try {
            /*
             * Locate the table element when the size chart modal is open
             * 
             * Validate that the contents of expectedTableHeaders is present as the table
             * header in the same order
             * 
             * Validate that the contents of expectedTableBody are present in the table body
             * in the same order
             */
            for (int i = 0; i < expectedTableHeaders.size(); i++) {
                String expectedTableHeader = expectedTableHeaders.get(i);
                int index = i + 1;

                WebElement actualTableHeaderElement = driver
                        .findElement(By.xpath("//table/thead/tr/th[" + index + "]"));

                String actualTableHeader = actualTableHeaderElement.getText();

                if (!expectedTableHeader.equals(actualTableHeader)) {
                    status = false;
                }

            }

            for(int i = 0; i< expectedTableHeaders.size(); i++){
                List<String> rowData = expectedTableBody.get(i);

                for(int j=0; j < rowData.size(); j++){
                    String expectedTableBodyText = rowData.get(j);

                    int row = i+1;
                    int column = j+1;

                    WebElement tableBodyElement = driver.findElement(By.xpath("//table/tbody/tr["+row+"]/td["+column+"]"));
                    String actualTableBodyText = tableBodyElement.getText();
                    if(!expectedTableBodyText.equals(actualTableBodyText)){
                        status = false;
                    }
                }
            }
            return status;

        } catch (Exception e) {
            System.out.println("Error while validating chart contents");
            return false;
        }
    }

    /*
     * Return Boolean based on if the Size drop down exists
     */
    public Boolean verifyExistenceofSizeDropdown(WebDriver driver) {
        Boolean status = false;
        try {
            WebElement element = driver.findElement(By.className("css-13sljp9"));
            status = element.isDisplayed();
            return status;
        } catch (Exception e) {
            return status;
        }
    }
}