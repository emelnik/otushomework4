import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class testChromeBrowser {

    protected WebDriver driver;
    private Logger logger = LogManager.getLogger();


    @Before
    public void StartUp() {
        logger.info("Драйвер поднят");
    }

    @After
    public void End(){
        if(driver != null){
            driver.quit();
        }
        logger.info("Драйвер закрыт");
    }

    @Test
    public void serachOtusDuck(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions chOptions = new ChromeOptions();
        chOptions.addArguments("headless");
        driver = new ChromeDriver(chOptions);

        driver.manage().window().maximize();

        By firstElementDDSearchPage = By.xpath("//*[@id='r1-0']");
        driver.get("https://duckduckgo.com/");
        driver.findElement(By.xpath("//*[@id='search_form_input_homepage']")).sendKeys("ОТУС");
        driver.findElement(By.xpath("//*[@id='search_button_homepage']")).click();

        getElement(firstElementDDSearchPage);

        String actual = driver.findElement(By.xpath("//span[normalize-space()='https://otus.ru']")).getText();
        Assert.assertEquals("https://otus.ru", actual);
    }

    @Test
    public void testModalWindow(){
        By modalWindow = By.xpath("//div[@class='pp_content_container']");

        WebDriverManager.chromedriver().setup();
        ChromeOptions chOptions = new ChromeOptions();
        chOptions.addArguments("kiosk");
        driver = new ChromeDriver(chOptions);

        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        driver.findElement(By.xpath("//*[@class='portfolio-item2 content'][position()=2]")).click();

        getElement(modalWindow);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='pp_content_container']")).isDisplayed());
    }

    @Test
    public void testCookieOtus(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        By mm = By.xpath("//div[@data-mode='login']");
        String email = "misom21861@altpano.com";
        String password = "123Qwerty123!";

        driver.get("https://otus.ru");
        driver.findElement(By.xpath("//button[@data-modal-id='new-log-reg']")).click();
        getElement(mm);
        driver.findElement(By.xpath("//form[@action='/login/']//input[@name='email']"))
                .sendKeys(email);
        driver.findElement(By.xpath("//input[@type='password']"))
                .sendKeys(password);
        driver.findElement(By.xpath("//div[@class='new-input-line new-input-line_last new-input-line_relative']")).click();
        logger.info(driver.manage().getCookies());
    }



    private WebElement getElement(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}
