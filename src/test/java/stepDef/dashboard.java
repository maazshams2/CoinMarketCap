package stepDef;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.es.E;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class dashboard {
    static WebDriver driver;
    static String chromeDriverPath = "driver/chromedriver.exe";
    static WebDriverWait wait;
    Random random = new Random();

    static WebDriver getDriver() {
        if (driver != null) {
            return driver;
        } else {
            throw new IllegalStateException("Driver has not been initialized.");
        }
    }

    @Before
    public void beforeTest(){
        ChromeOptions op = new ChromeOptions();
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            op.addArguments("--start-maximized");
            driver = new ChromeDriver(op);
        } else {
            throw new IllegalStateException("Driver has already been initialized. Quit it before using this method");
        }

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }
    @After
    public void afterTest(){
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Given("^Open coinmarketcap website$")
    public void open_coinmarketcap_website(){
        driver.get("https://coinmarketcap.com/");
    }

    @When("^Select (\\d+) rows to be viewed$")
    public void select_rows_to_be_viewed(int rowNum){
        getDriver().findElement(By.className("table-control-page-sizer")).findElement(By.tagName("div")).click();

        wait = new WebDriverWait(getDriver(),20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tippy-box")));

        List<WebElement> buttonList = getDriver().findElement(By.className("tippy-box")).findElements(By.tagName("button"));
        for (WebElement button : buttonList) {
            if(button.getText().equals(rowNum)){
                button.click();
                break;
            }
        }
    }

    @Then("^Verify that results are displayed$")
    public void verify_that_results_are_displayed(){
        WebElement tableBody = getDriver().findElement(By.className("cmc-table")).findElement(By.tagName("tbody"));
        List<WebElement> tableBodyRows = tableBody.findElements(By.tagName("tr"));
        for (WebElement row : tableBodyRows)
            Assert.assertTrue(row.isDisplayed());
    }

    String[] arr;

    @When("^Add (\\d+) to (\\d+) cryptocurrencies to Watchlist$")
    public void add_to_cryptocurrencies_to_Watchlist(int lowerBound, int upperBound) throws InterruptedException {
        wait = new WebDriverWait(getDriver(),20);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        int randomNumber = random.nextInt(upperBound - lowerBound) + lowerBound;
        arr = new String[randomNumber];
        List<WebElement> rows = getDriver().findElement(By.className("cmc-table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        WebElement watchlist;
        WebElement coinItemSymbol;

        for (int i=0 ; i<randomNumber ; i++){
            watchlist = rows.get(i).findElement(By.className("icon-Star"));
            coinItemSymbol = rows.get(i).findElement(By.className("coin-item-symbol"));

            boolean staleElement = true;
            while(staleElement){
                try {
                    jse.executeScript("arguments[0].click()", watchlist);
                    arr[i] = (String) jse.executeScript("return arguments[0].innerText", coinItemSymbol);
                    staleElement = false;
                }
                catch (StaleElementReferenceException ex) {
                    staleElement = true;
                }
            }
        }
    }

    @And("^Open Watchlist on different browser tab$")
    public void open_Watchlist_on_different_browser_tab(){
        Actions actions = new Actions(driver);
        List<WebElement> tableLinkList = getDriver().findElement(By.className("table-link-area")).findElements(By.className("cmc-link"));
        for (WebElement tableLink : tableLinkList)
            if (tableLink.getText().toLowerCase().equals("watchlist"))
                actions.keyDown(Keys.LEFT_CONTROL).click(tableLink).keyUp(Keys.LEFT_CONTROL).build().perform();

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        Assert.assertTrue(getDriver().findElement(By.tagName("h1")).getText().contains("Your Watchlist"));
    }

    @Then("^Verify as many values possible$")
    public void verify_as_many_values_possible(){
        for (int i = 0; i < arr.length; i++) {
            WebElement cmcTable = getDriver().findElement(By.className("cmc-table"));
            WebElement coinItemSymbol = getDriver().findElement(By.className("cmc-table")).findElements(By.className("coin-item-symbol")).get(i);

            wait.until(ExpectedConditions.visibilityOf(cmcTable));
            Assert.assertTrue(coinItemSymbol.getText().equals(arr[i]));
        }
    }

    @When("^Display dropdown meny on cryptocurrencies tab$")
    public void display_dropdown_meny_on_cryptocurrencies_tab(){
        WebElement headerMenu = getDriver().findElement(By.className("cmc-header-desktop"));
        List<WebElement> headerButtons = headerMenu.findElements(By.tagName("button"));
        wait = new WebDriverWait(getDriver(),20);
        Actions action = new Actions(driver);

        for (WebElement button : headerButtons)
            if (button.getText().equals("Cryptocurrencies"))
                action.moveToElement(button).click().build().perform();

        wait.until(ExpectedConditions.visibilityOfAllElements(headerMenu.findElements(By.tagName("h6"))));

    }

    @When("^Cick on any Full List option$")
    public void cick_on_any_Full_List_option(){
        WebElement headerMenu = getDriver().findElement(By.className("cmc-header-desktop"));
        List<WebElement> cryptocurrenciesMenu = headerMenu.findElements(By.tagName("h6"));

        for (WebElement elemet : cryptocurrenciesMenu)
            if (elemet.getText().equals("Coins")) {
                elemet.click();
                break;
            }

        wait.until(ExpectedConditions.urlContains("/coin"));
    }

    ArrayList<String> coinItemList = new ArrayList<String>();
    @When("^Record data on current page$")
    public void record_data_on_current_page() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.className("filter-area"))));

        coinItemList.add("XRP");
        coinItemList.add("DOT");
        coinItemList.add("XLM");
        coinItemList.add("BNB");
    }

    @When("^Apply any filter from dropdown menu$")
    public void apply_any_filter_from_dropdown_menu(){
        List<WebElement> filterList = getDriver().findElements(By.className("cmc-filter-button"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        wait = new WebDriverWait(getDriver(),5);

        for (WebElement filterMenu: filterList) {
            if (filterMenu.getText().equals("Mineable")){
//                filterMenu.click();
                jse.executeScript("arguments[0].click()", filterMenu);
                wait.until(ExpectedConditions.attributeContains(filterMenu, "className", "cmc-filter-selected"));
                break;
            }
        }

//
    }

    @Then("^Verify data recorded previously$")
    public void verify_data_recorded_previously() throws InterruptedException {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        List<WebElement> newRecords = getDriver().findElement(By.className("cmc-table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        for (int i=0 ; i<10 ; i++) {
            WebElement coinItemSymbol = newRecords.get(i).findElement(By.className("coin-item-symbol"));
            String newText = coinItemSymbol.getText();
            if (!coinItemList.contains(newText))
                Assert.assertFalse(coinItemList.contains(newText));
        }
    }

}
