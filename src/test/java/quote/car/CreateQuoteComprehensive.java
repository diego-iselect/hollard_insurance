package quote.car;

import org.openqa.selenium.TimeoutException;

import javax.lang.model.element.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.NoSuchElementException;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Unit test for simple App.
 */
public class CreateQuoteComprehensive {
    /**
     * Rigorous Test :-)A
     */
    WebDriver driver;
    ChromeOptions options;
    WebDriverWait wait;
    String URL = "https://car.iselect.com.au/car/compare-car-insurance/gatewayStore";

    @BeforeTest
    public void setUp(){

        // Setup chromedriver
        WebDriverManager.chromedriver().browserVersion("91.0.4472.114").setup();

        options = new ChromeOptions();
        // List of arguments : https://peter.sh/experiments/chromium-command-line-switches/
        options.addArguments("start-maximized"); 
        //options.addArguments("enable-automation"); 
        options.addArguments("--no-sandbox"); 
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation"); 
        options.addArguments("--disable-gpu"); 

        // Create ChromeDriver object
        driver= new ChromeDriver(options);

        // Set default wait time (10s)
         wait = new WebDriverWait(driver, 10);

        // Open url
        driver.get(URL);

    }

    //@AfterTest
    public void tearDown(){
        driver.close();
    }

    // Find a car from the dropdowns
    // TODO: use annotation to set test description
    @Test
    public void findYourCar() {
        
        fill("//div[text()='Make']/following-sibling::div/input", "Holden");
        fill("//div[text()='Model']/following-sibling::div/input", "Barina");
        fill("//div[text()='Year']/following-sibling::div/input", "2014");
        fill("//div[text()='Car type or series']/following-sibling::div/input", "TM MY14 CD Sedan 4dr Auto 6sp 1.6i");
        fill("//div[text()='Colour']/following-sibling::div/input", "Black");
    }

    @Test
    public void selectCoverType() {
        click("//span[contains(text(),'Comprehensive')]");

        // Selection option for the question "Why are you looking for cover?"
        click("//div[text()='Please select']");
        click("//div[@role='listbox']//div[text()='My cover is about to expire']");
    }

    @Test
    public void selectCarAccessAndOptions() {

        // Select option for the question "Does your car have any anti-theft devices?"
        click("//div[text()='Please select']");
        click("//div[@role='listbox']//div[text()='Alarm']");

        // Select answer and option for the question "Does your car have any factory fitted options?'"
        click("//div[text()='Does your car have any factory fitted options?']/..//span[text()='Yes']");
        click("//div[text()='Select factory options, if any']");
        click("//div[@role='listbox']//div[text()='Paint - Prestige']");

        // Select option for the question "Has your car been fitted with any non-standard accessories?"
        click("//div[text()='Has your car been fitted with any non-standard accessories?']/../../following-sibling::div//span[text()='No']");

        // Select option for the question "Does your car have unrepaired accident or hail damage?"
        click("//div[text()='Does your car have unrepaired accident or hail damage?']/../../following-sibling::div//span[text()='Yes']");

        // Select option for the question "Is your vehicle currently insured?"
        click("//div[text()='Please select']");
        click("//div[@role='listbox']//div[text()='Alarm']");
    }


    /***
     * Finds an element by xpath and sends a char sequence
     * @param xpath
     * @param value
     * @throws TimeoutException
     */
    private void fill (String xpath, String value) {
        WebElement element = findElementByXpath(xpath);
        try {
            element.sendKeys(value);
            element.sendKeys(Keys.ENTER);
        }catch (TimeoutException e){
            Assert.fail("Unable to fill " + xpath);
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unable to set value '" + value + "' to an element " + xpath);
        }
    }

    /**
     * Finds an element by xpath and clicks
     * @param xpath
     * @throws TimeoutException
     */
    private void click(String xpath){
        WebElement element = findElementByXpath(xpath);
        try {
            element.click();
        }catch (TimeoutException e){
            Assert.fail("Unable to click " + xpath);
        }catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unable to resolve click " + xpath);
        }
    }

    /**
     * Finds an element by xpath and returns as WebElement object
     * @param xpath
     * @return a WebElement object
     */
    private WebElement findElementByXpath(String xpath){
        By byXpath = By.xpath(xpath);
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.elementToBeClickable(byXpath));
        }catch (TimeoutException e){
            Assert.fail("Unable to find element " + byXpath.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

    

}
