import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class TestChromeBrowser {

    private WebDriver driver;
    private Logger logger = LogManager.getLogger();

    @BeforeClass
            public static void startDriver(){
                WebDriverManager.chromedriver().setup();
    }


    @After
    public void end(){
        if(driver != null){
            driver.quit();
        }
        logger.info("Драйвер закрыт");
    }

    @Test
    public void serachOtusDuckTest(){
        startDriver();
        ChromeOptions chOptions = new ChromeOptions();
        chOptions.addArguments("headless");
        driver = new ChromeDriver(chOptions);

        driver.manage().window().maximize();

        By firstElementDDSearchPage = By.xpath("//*[@id='r1-0']");
        driver.get("https://duckduckgo.com/");
        driver.findElement(By.xpath("//*[@id='search_form_input_homepage']")).sendKeys("ОТУС");
        driver.findElement(By.xpath("//*[@id='search_button_homepage']")).click();

        getElement(firstElementDDSearchPage);

        String actual = driver.findElements(By.xpath("(//article[@id='r1-0'])[1]"))
                .get(0)
                .getText();

        Assert.assertEquals("https://otus.ru", actual.substring(0,15));
    }

    @Test
    public void modalWindowTest(){
        By modalWindow = By.xpath("//div[@class='pp_content_container']");

        startDriver();
        ChromeOptions chOptions = new ChromeOptions();
        chOptions.addArguments("kiosk");
        driver = new ChromeDriver(chOptions);

        driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
        driver.findElement(By.xpath("//li[@data-id='id-2']")).click();

        getElement(modalWindow);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='pp_content_container']")).isDisplayed());
    }

    @Test
    public void cookieOtusTest(){
        Set<Cookie> cookeList = new HashSet<>();

        startDriver();
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
        driver.findElement(By.xpath("//form[@action='/login/']//button[@type='submit']")).click();
        cookeList = driver.manage().getCookies();

        for (Cookie c:cookeList) {
            System.out.println(c.getName() + " -> " + c.getValue());
        }
    }

    private WebElement getElement(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}
