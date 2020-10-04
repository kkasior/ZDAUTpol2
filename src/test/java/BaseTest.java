import devToPages.DevToMainPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class BaseTest {

    WebDriver driver;
    WebDriverWait wait;

    public void highlightElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: green; border: 3px solid blue;');", element);
    }

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 20);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //poczekaj zanim wywalisz error,
        //że elementu nie ma 10 sekund, sprawdzaj co sekundę czy element jest
    }

    @Test
    public void selectFirstPostFromWeek(){
        DevToMainPage devToMainPage = new DevToMainPage(driver);
        devToMainPage.goToWeekPage();

        wait.until(ExpectedConditions.urlToBe("https://dev.to/top/week")); //zanim zaczniesz szukać elementu, poczekaj aż url będzie miał wartość https://dev.to/top/week
        WebElement firstPostOnWeek = driver.findElement(By.cssSelector("h2.crayons-story__title > a"));
        String firstPostOnWeekText = firstPostOnWeek.getText();
        String firstPostLink = firstPostOnWeek.getAttribute("href");
        firstPostOnWeek.click();
        wait.until(ExpectedConditions.urlToBe(firstPostLink));
        WebElement postTitle = driver.findElement(By.cssSelector("div.crayons-article__header__meta > h1"));

        String postUrl = driver.getCurrentUrl();
        String postTitleText = postTitle.getText();

        assertEquals("Urls aren't the same", postUrl, firstPostLink);
        assertEquals("Titles aren't the same",postTitleText, firstPostOnWeekText);
    }

    @Test
    public void searchBarTesting() {
        WebElement searchBox = driver.findElement(By.id("nav-search"));
        highlightElement(driver, searchBox);
        String searchText = "testing";
        searchBox.sendKeys(searchText);
        searchBox.sendKeys(Keys.ENTER);
        String searchUrl = "https://dev.to/search?q=";
        String searchingUrlWithText = searchUrl + searchText;
        wait.until(ExpectedConditions.urlToBe(searchingUrlWithText));

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("h2.crayons-story__title > a")));
        List<WebElement> postTilesList = driver.findElements(By.cssSelector("h2.crayons-story__title > a"));

        int i = 0;
        while (i<3){
            highlightElement(driver,postTilesList.get(i));
            i++;
        }

        WebElement element = postTilesList.get(0);
        String elementText = element.getText();
        assertTrue("there's no searching value in post tile", elementText.contains(searchText));
    }

    @Test
    public void findJavaInNavBar(){
        WebElement java = driver.findElement(By.cssSelector("div#default-sidebar-element-java > a"));
        highlightElement(driver,java);
        java.click();
    }

    @Test
    public void playFourthPodcast(){
        WebElement podcast = driver.findElement(By.xpath("//a[@href='/pod']"));
        podcast.click();
        wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("h3")));
        List<WebElement> podcasts = driver.findElements(By.tagName("h3"));
        podcasts.get(3).click();
        wait.until(ExpectedConditions.urlContains("stackpodcast"));
        WebElement playArea = driver.findElement(By.className("record-wrapper"));
        playArea.click();
        WebElement initializing = driver.findElement(By.className("status-message"));
        wait.until(ExpectedConditions.invisibilityOf(initializing));
        String playAreaClassAttribute = playArea.getAttribute("class");
        boolean isPlaying = playAreaClassAttribute.contains("playing");

        assertTrue("Podcast isn't playing",isPlaying);
    }




//        WebElement initializing = driver.findElement(By.className("status-message"));
//        wait.until(ExpectedConditions.invisibilityOf(initializing));
//        String classAttribute = playButton.getAttribute("class");
//        boolean isPlaying = classAttribute.contains("playing");
//        assertTrue(isPlaying);




}
