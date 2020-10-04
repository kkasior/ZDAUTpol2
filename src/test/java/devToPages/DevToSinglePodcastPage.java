package devToPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DevToSinglePodcastPage {
    WebDriverWait wait;
    WebDriver driver;

    @FindBy(className = "record-wrapper")
    public WebElement playArea;

    @FindBy(className = "status-message")
    public WebElement initializing;

    public DevToSinglePodcastPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(this.driver, this);
    }

    public void playPodcast(){
        wait.until(ExpectedConditions.urlContains("stackpodcast"));
        playArea.click();
    }
    public boolean isPodcastPlayed(){
        wait.until(ExpectedConditions.invisibilityOf(initializing));
        String playAreaClassAttribute = playArea.getAttribute("class");
        boolean isPlaying = playAreaClassAttribute.contains("playing");
        return isPlaying;
    }
}
