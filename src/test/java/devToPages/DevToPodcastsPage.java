package devToPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DevToPodcastsPage {
    WebDriverWait wait;
    WebDriver driver;
    String url = "https://dev.to/pod";

    @FindBy(tagName = "h3")
    List<WebElement> podcastList;

    public DevToPodcastsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver,this);
    }

    public DevToSinglePodcastPage selectFourthPodcast(){
        wait.until(ExpectedConditions.urlToBe(url));
        wait.until(ExpectedConditions.visibilityOfAllElements(podcastList));
        podcastList.get(3).click();
        return new DevToSinglePodcastPage(this.driver, this.wait);
    }
}
