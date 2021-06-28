package quote.car;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Unit test for simple App.
 */
public class AboutYourCarNegTest {
    /**
     * Rigorous Test :-)A
     */
    WebDriver driver;
    ChromeOptions options;
    WebDriverWait wait;
    String URL = "https://car.iselect.com.au/car/compare-car-insurance/gatewayStore";
    JavascriptExecutor je;

    @Parameters({"suite-chrome-version"})
    @BeforeTest
    public void setUp(String chromeVersion) {

        // Setup chromedriver
        WebDriverManager.chromedriver().browserVersion(chromeVersion).setup();

        options = new ChromeOptions();
        // List of arguments :
        // https://peter.sh/experiments/chromium-command-line-switches/
        options.setHeadless(false);
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        options.addArguments("--desktop-window-1080p");
        options.addArguments("--disk-cache-size=0");

        // Create ChromeDriver object
        driver = new ChromeDriver(options);

        je = (JavascriptExecutor) driver;

        // Open url
        driver.get(URL);

    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

    @Test(priority = 0, description = "Check if car can be found by specifying details")
    public void findYourCar() {

        testSelectAnOptionErrorMsg("Make");
        fill("//div[text()='Make']/following-sibling::div/input", "Holden", 20);
        testSelectAnOptionErrorMsg("Model");
        fill("//div[text()='Model']/following-sibling::div/input", "Barina", 10);
        testSelectAnOptionErrorMsg("Year");
        fill("//div[text()='Year']/following-sibling::div/input", "2014", 10);
        testSelectAnOptionErrorMsg("Car type of series");
        fill("//div[text()='Car type or series']/following-sibling::div/input", "TM MY14 CD Sedan 4dr Auto 6sp 1.6i",
                10);
        testSelectAnOptionErrorMsg("Colour");
        fill("//div[text()='Colour']/following-sibling::div/input", "Black", 10);
    }

    @Test(priority = 1, description = "Check if details about required cover can be provided")
    public void selectCoverType() {

        testSelectAnOptionErrorMsg("Level of cover");
        click("//span[text()='Comprehensive']/parent::div/parent::button", 10);
        testSelectAnOptionErrorMsg("Reason for shopping");
        // Selection option for the question "Why are you looking for cover?"
        click("//div[text()='Please select']", 10);
        click("//div[text()='My cover is about to expire']/parent::li[@role='option']", 10);
    }

    @Test(priority = 2, description = "Check if details about fitted car accessories and options can be provided")
    public void selectCarAccessAndOptions() {

        testSelectAnOptionErrorMsg("Information about anti-theft devices");
        // Select option for the question "Does your car have any anti-theft devices?"
        click("//div[text()='Please select']", 10);
        click("//div[text()='Alarm']/parent::li[@role='option']", 10);

        testSelectAnOptionErrorMsg("Information about factory fitted options");
        // Select answer and option for the question "Does your car have any factory
        // fitted options?'"
        click("//div[text()='Does your car have any factory fitted options?']/..//span[text()='Yes']", 10);
        testSelectAnOptionErrorMsg("List of factory fitted options");
        click("//div[text()='Select factory options, if any']", 10);
        click("//div[text()='Paint - Prestige']/parent::li[@role='option']", 10);

        if (findElementByXpath("//button[text()='Ok']", false, 5) != null) {
            click("//button[text()='Ok']", 5);
        }

        testSelectAnOptionErrorMsg("Information about non-standard accessories");
        // Select option for the question "Has your car been fitted with any
        // non-standard accessories?"
        click("//div[text()='Has your car been fitted with any non-standard accessories?']/../../following-sibling::div//span[text()='No']",
                10);

        testSelectAnOptionErrorMsg("Information about hail damage");
        // Select option for the question "Does your car have unrepaired accident or
        // hail damage?"
        click("//div[text()='Does your car have unrepaired accident or hail damage?']/../../following-sibling::div//span[text()='No']",
                10);

        testSelectAnOptionErrorMsg("Information about current insurance");
        // Select option for the question "Is your vehicle currently insured?"
        click("//div[text()='Please select']", 10);
        click("//div[text()='Yes']/parent::li[@role='option']", 10);

        testSelectAnOptionErrorMsg("Information about current insurer");
        click("//div[text()='Insurer']", 10);
        click("//div[text()='Allianz']/parent::li[@role='option']", 10);

        testSelectAnOptionErrorMsg("Information about current cover");
        click("//div[text()='Type of cover']", 10);
        click("//div[text()='Comprehensive']/parent::li[@role='option']", 10);

        // Acknowledge terms and conditions and Continue
        if (findElementByXpath("//button[text()='Continue']", false, 5) == null) {
            click("//input[@type='checkbox']/parent::label", 10);
        }
        click("//button[text()='Continue']", 10);
    }

    @Test(priority = 3, description = "Check if submitting 'About Your Car' form directs to the next page")
    public void validateOutcome() {
        findElementByXpath("//*[text()='Usage & Driver']", true, 10);
    }

    /***
     * Finds an element by xpath and sends a char sequence
     * 
     * @param xpath
     * @param value
     * @param timeout
     * @throws TimeoutException
     */
    private void fill(String xpath, String value, int timeout) {
        WebElement element = findElementByXpath(xpath, true, timeout);
        try {
            element.sendKeys(value);
            element.sendKeys(Keys.ENTER);
            //click("//li[@aria-selected='true']", 5);
        } catch (TimeoutException e) {
            Assert.fail("Unable to fill " + xpath);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unable to set value '" + value + "' to an element " + xpath);
        }
    }

    /**
     * Finds an element by xpath and clicks
     * 
     * @param xpath
     * @param timeout
     * @throws TimeoutException
     */
    private void click(String xpath, int timeout) {
        WebElement element = findElementByXpath(xpath, true, timeout);
        try {
            element.click();
        } catch (TimeoutException e) {
            Assert.fail("Unable to click " + xpath);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unable to resolve click " + xpath);
        }
    }

    /**
     * Finds an element by xpath and returns as WebElement object
     * 
     * @param xpath
     * @param timeout
     * @return a WebElement object
     */
    private WebElement findElementByXpath(String xpath, Boolean report, int timeout) {
        By byXpath = By.xpath(xpath);
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
            element = wait.until(ExpectedConditions.elementToBeClickable(byXpath));
        } catch (TimeoutException e) {
            if (report) {
                Assert.fail("Unable to find element " + byXpath.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

    private void testSelectAnOptionErrorMsg(String field) {
        // Acknowledge terms and conditions and Continue
        if (findElementByXpath("//input[@type='checkbox']/parent::label", false, 5) != null) {
            if (findElementByXpath("//button[text()='Continue']", false, 0) == null) {
                    click("//input[@type='checkbox']/parent::label", 0);
            }
            je.executeScript("window.scrollBy(0,400)");
            click("//button[text()='Continue']", 5);
            Assert.assertNotNull(findElementByXpath("//span[text()='Please select an option']", true, 5),
            field + "is not mandatory");
        }
    }

}
