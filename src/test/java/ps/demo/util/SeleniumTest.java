package ps.demo.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {

    public static void main(String[] args) {
        //WebDriver chromeDriver = new ChromeDriver();
        //WebDriver firefoxDriver = new FirefoxDriver();
        //WebDriver edgeDriver = new EdgeDriver();
        //WebDriver internetExplorerDriver = new InternetExplorerDriver();  // Internet Explorer浏览器
        //WebDriver driver = new HtmlUnitDriver();

        System.setProperty("webdriver.chrome.driver", "D:\\application\\chromedriver_win32\\chromedriver.exe");
        //设置无头模式
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("-headless");
        chromeOptions.addArguments("--window-size=1920,1048");
        ChromeDriverService driverService = ChromeDriverService.createDefaultService();
        ChromeDriver driver = new ChromeDriver(driverService, chromeOptions);
        //设置无头模式下允许下载文件
//        Map<String, Object> params = new HashMap<>();
//        params.put("behavior", "allow");
//        params.put("downloadPath", "C:\\Users\\" + System.getenv("USERNAME") + "\\Downloads");
//        driver.executeCdpCommand("Browser.setDownloadBehavior", params);

        try {
            driver.get("http://www.baidu.com");
            driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

//            WebElement queryBox = driver.findElement(By.id("kw"));
//            queryBox.sendKeys("headless firefox");
//            WebElement searchBtn = driver.findElement(By.id("su"));
//            searchBtn.click();
//            System.out.println(driver.getPageSource());

            File File = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            File imageFile = MyFileUtil.getFileDateDirInHomeDir(driver.getTitle()+".jpeg");
            FileUtils.copyFile(File, imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

}
