import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class BrokenLinks {
    WebDriver driver;

    @BeforeEach
    public void start(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
     //   driver.get("https://www.linkedin.com");
        driver.get("https://paperpaper.ru/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
       }

    @AfterEach
    public void finish(){
        driver.quit();
    }

    @Test
    public void findAllLinks() throws IOException {
        //Find and Get All links
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        System.out.println("# Links: " + allLinks.size());
        int i = 1;
        for (WebElement link : allLinks) {
            String url = link.getAttribute("href");
            if (url != null && !url.contains("javascript")){
                HttpURLConnection connection =
                        (HttpURLConnection) new URL(url).openConnection();
                connection.connect();
                //Establish A Connection to the URL
                //Get the response codes and response messages
               int responseCode = connection.getResponseCode();
               String responseMessage = connection.getResponseMessage();

                System.out.println(i + ". " + url +
                        "\n \t" + responseCode + "\n \t" + responseMessage);
                i++;
                connection.disconnect();

            }
        }
    }
}
