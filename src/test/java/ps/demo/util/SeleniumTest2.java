package ps.demo.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import ru.yandex.qatools.ashot.AShot;
//import ru.yandex.qatools.ashot.Screenshot;
//import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumTest2 {

    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\application\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--disable-extensions");

        //headless
        //chromeOptions.addArguments("-headless");
        //chromeOptions.addArguments("--window-size=1920,1048");
        ChromeDriverService driverService = ChromeDriverService.createDefaultService();
        ChromeDriver driver = new ChromeDriver(driverService, chromeOptions);

        try {
            //driver.get("https://stackoverflow.com/questions/26566799/wait-until-page-is-loaded-with-selenium-webdriver-for-python");
            driver.get("https://selenium-python.readthedocs.io/waits.html#explicit-waits");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            //new WebDriverWait(driver, 20).until(ExpectedConditions.titleContains("jQuery"));
            //MyWebDriverUtil.waitUntil(driver, ExpectedConditions.titleContains(""));

//            WebElement queryBox = driver.findElement(By.id("kw"));
//            queryBox.sendKeys("headless firefox");
//            WebElement searchBtn = driver.findElement(By.id("su"));
//            searchBtn.click();
//            System.out.println(driver.getPageSource());
            MyWebDriverUtil.takeFullPageScreenShot(driver);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }



//    public static void takeFullPageScreenShotByAshot(ChromeDriver driver) throws IOException {
//        Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
//        ImageIO.write(s.getImage(), "PNG", MyFileUtil.getFileDateDirInHomeDir(driver.getTitle() + ".png"));
//    }

}
