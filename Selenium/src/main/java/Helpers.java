import Entities.User;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Helpers {

    public static String dateFormatter(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat( "dd/MM/yyyy");
        return dateFormatter.format(date);
    }

    public static User generateUser(){
        String random = RandomStringUtils.random(10, true, false);
        return new User(
                "A" + random ,
                "H" + random ,
                "M" + random + "@hotmail.fr",
                "E" + random ,
                new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(1) * 365 * 30)
        );
    }

    public static void takeScreenshot(WebDriver driver, String screenshotName) throws IOException {
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File("./src/main/resources/Screenshots/"+screenshotName+".png"));
    }
}
