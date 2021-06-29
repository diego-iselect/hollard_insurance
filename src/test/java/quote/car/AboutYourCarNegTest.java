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
     * Rigorous Test :-)
     */
    WebDriver driver;
    ChromeOptions options;
    WebDriverWait wait;
    String URL = "https://car.iselect.com.au/car/compare-car-insurance/gatewayStore";
    JavascriptExecutor je;

    // Comment to debug on vscode due to https://github.com/microsoft/vscode-java-test/issues/596
    @Parameters({"suite-chrome-version"})
    @BeforeTest(description = "Setup for chromedriver, open browser and access the url")
    // Comment to debug
    public void setUp(String chromeVersion) {
    //public void setUp() {

        // Setup chromedriver
        // Comment to debug on vscode due to https://github.com/microsoft/vscode-java-test/issues/596
        WebDriverManager.chromedriver().browserVersion(chromeVersion).setup();
        //WebDriverManager.chromedriver().browserVersion("91.0.4472.114").setup();

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

    @Test(priority = 0, description = "Fill 'Make' field of the form")
    public void findYourCar(){
        fill("//div[text()='Make']/following-sibling::div/input", "Holden", 20);
    }

    @Test(priority = 1, description = "Fill 'Model' field of the form")
    public void fillModel(){
        fill("//div[text()='Model']/following-sibling::div/input", "Barina", 10);
    }

    @Test(priority = 2, description = "Fill 'Year' field of the form")
    public void fillYear(){
        fill("//div[text()='Year']/following-sibling::div/input", "2014", 10);
    }

    @Test(priority = 3, description = "Fill 'Car type of series' field of the form")
    public void fillCarTypeofSeries(){
        fill("//div[text()='Car type or series']/following-sibling::div/input", "TM MY14 CD Sedan 4dr Auto 6sp 1.6i", 10);
    }
        
    @Test(priority = 4, description = "Fill 'Colour' field of the form")
    public void fillColour(){
        fill("//div[text()='Colour']/following-sibling::div/input", "Black", 10);
    }

    @Test(priority = 5, description = "Fill 'Level of cover' field of the form")
    public void fillLevelofCover(){
        click("//span[text()='Comprehensive']/parent::div/parent::button", 10);
    }

    @Test(priority = 6, description = "Fill 'Reason for shopping' field of the form")
    public void fillReasonforShopping(){
        click("//div[text()='Please select']", 10);
        click("//div[text()='My cover is about to expire']/parent::li[@role='option']", 10);
    }

    @Test(priority = 7, description = "Fill 'Information about anti-theft devices' field of the form")
    public void fillInAntitheftDevicesInfo(){
        click("//div[text()='Please select']", 10);
        click("//div[text()='Alarm']/parent::li[@role='option']", 10);
    }

    @Test(priority = 8, description = "Fill 'Information about factory fitted options' field of the form")
    public void fillFactoryFittedOptionsInfo(){
        click("//div[text()='Does your car have any factory fitted options?']/..//span[text()='Yes']", 10);
    }

    @Test(priority = 9, description = "Fill 'List of factory fitted options' field of the form")
    public void fillListofFactoryFittedOptions(){
        click("//div[text()='Select factory options, if any']", 10);
        click("//div[text()='Paint - Prestige']/parent::li[@role='option']", 10);

        // Click OK if button appears
        if (findElementByXpath("//button[text()='Ok']", false, 5) != null) {
            click("//button[text()='Ok']", 5);
        }
    }

    @Test(priority = 10, description = "Fill 'Information about non-standard accessories' field of the form")
    public void fillNonStandardAccessoriesInfo(){
        click("//div[text()='Has your car been fitted with any non-standard accessories?']/../../following-sibling::div//span[text()='No']",
                10);
    }

    @Test(priority = 11, description = "Fill 'Information about hail damage' field of the form")
    public void fillHailDamageInfo(){
        click("//div[text()='Does your car have unrepaired accident or hail damage?']/../../following-sibling::div//span[text()='No']",
                10);
    }

    @Test(priority = 12, description = "Fill 'Information about current insurance' field of the form")
    public void fillCurrentInsuranceInfo(){
        click("//div[text()='Please select']", 10);
        click("//div[text()='Yes']/parent::li[@role='option']", 10);
    }


    @Test(priority = 13, description = "Fill 'Information about current insurer' field of the form")
    public void fillCurrentInsurerInfo(){
        click("//div[text()='Insurer']", 10);
        click("//div[text()='Allianz']/parent::li[@role='option']", 10);
    }

    @Test(priority = 14, description = "Fill 'Information about current cover' field of the form")
    public void fillCurrentCoverInfo(){
        click("//div[text()='Type of cover']", 10);
        click("//div[text()='Comprehensive']/parent::li[@role='option']", 10);
    }

    @AfterTest(description = "Check if submitting 'About Your Car' form directs to the next page")
    public void submitForm() {
        if (findElementByXpath("//button[text()='Continue']", false, 5) == null) {
            click("//input[@type='checkbox']/parent::label", 10);
        }
        click("//button[text()='Continue']", 10);
        findElementByXpath("//*[text()='Usage & Driver']", true, 10);
        driver.close();
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

    @BeforeMethod(description = "Attempt to submit form and validate")
    public void validateFormSubmission() {
        // Acknowledge terms and conditions and Continue
        WebElement checkbox = findElementByXpath("//input[@type='checkbox']/parent::label", false, 5);
        if (checkbox != null) {
            je.executeScript("arguments[0].scrollIntoView(true);", checkbox);
            if (findElementByXpath("//button[text()='Continue']", false, 0) == null) {
                    click("//input[@type='checkbox']/parent::label", 0);
            }
            click("//button[text()='Continue']", 5);
            Assert.assertNotNull(findElementByXpath("//span[text()='Please select an option']", true, 5));
        }
    }

}