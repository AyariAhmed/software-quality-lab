import Entities.User;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Tunisianet {
    public static void main(String[] args) throws InterruptedException, IOException {
        // Environment setup
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        WebDriverWait waitVar = new WebDriverWait(driver, Duration.ofSeconds(10)); // explicit wait
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // navigate to the website
        driver.get("https://www.tunisianet.com.tn/");
        Thread.sleep(2000);
        // Click on the user icon
        WebElement userDropdown = driver.findElement(By.xpath("//*[@id='_desktop_user_info']//div[@class='nav-link']//*[@class]"));
        userDropdown.click();
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'user-down')]//span[contains(text(),'Connexion')]")));
        WebElement signInButton = driver.findElement(By.xpath("//ul[contains(@class, 'user-down')]//span[contains(text(),'Connexion')]"));
        signInButton.click();

        // signIn page
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.className("no-account")));
        WebElement createAccountButton = driver.findElement(By.className("no-account"));
        createAccountButton.click();
        Thread.sleep(1000);

        // signUp page
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2/span/following-sibling::hr")));

        // user generation
        User generatedUser = Helpers.generateUser();

        // Fill user info
        WebElement maleRadioInput = driver.findElement(By.xpath("//input[@name='id_gender' and @value='1']"));
        maleRadioInput.click();
        List<WebElement> formFields = driver.findElements(By.cssSelector("input.form-control"));
        formFields.get(1).sendKeys(generatedUser.firstName);
        formFields.get(2).sendKeys(generatedUser.lastName);
        formFields.get(3).sendKeys(generatedUser.email);
        formFields.get(4).sendKeys(generatedUser.password);
        formFields.get(5).sendKeys(Helpers.dateFormatter(generatedUser.dateOfBirth));
        // take a screenshot of the created user
        js.executeScript("window.scrollBy(0,10)", "");

        Helpers.takeScreenshot(driver, "created-user");

        Thread.sleep(1000);

        WebElement signupButton = driver.findElement(By.className("form-control-submit"));
        signupButton.click();

        Thread.sleep(2000);

        // sign out
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='_desktop_user_info']//div[@class='nav-link']//*[@class]")));
        userDropdown = driver.findElement(By.xpath("//*[@id='_desktop_user_info']//div[@class='nav-link']//*[@class]"));
        userDropdown.click();
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'user-down')]//a[@class='logout']")));
        WebElement signOutButton = driver.findElement(By.xpath("//ul[contains(@class, 'user-down')]//a[@class='logout']"));
        signOutButton.click();

        Thread.sleep(2000);

        // signIn with the generated user
        userDropdown = driver.findElement(By.xpath("//*[@id='_desktop_user_info']//div[@class='nav-link']//*[@class]"));
        userDropdown.click();
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'user-down')]//span[contains(text(),'Connexion')]")));
        signInButton = driver.findElement(By.xpath("//ul[contains(@class, 'user-down')]//span[contains(text(),'Connexion')]"));
        signInButton.click();
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'  Connectez-vous à votre compte')]")));

        // fill generated user info
        WebElement emailFiled = driver.findElement(By.xpath("//input[contains(@class,'form-control')][@name='email']"));
        WebElement passwordFiled = driver.findElement(By.xpath("//input[contains(@class,'form-control')][@name='password']"));
        WebElement submitButton = driver.findElement(By.id("submit-login"));

        emailFiled.sendKeys(generatedUser.email);
        passwordFiled.sendKeys(generatedUser.password);
        submitButton.click();

        Thread.sleep(2000);
        // search for the desired product
        WebElement searchBar = driver.findElement(By.className("search_query"));
        searchBar.sendKeys("PC portable MacBook M1 13.3");
        WebElement searchButton = driver.findElement(By.cssSelector("#sp-btn-search > button"));
        searchButton.click();

        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Résultats de la recherche')]")));
        js.executeScript("window.scrollBy(0,20)", "");
        Helpers.takeScreenshot(driver, "search-results");

        Thread.sleep(2000);

        List<WebElement> searchResults = driver.findElements(By.xpath("//h2[contains(@class,'product-title')]"));
        if (searchResults.size() == 0) {
            System.out.println("No Results found for the searched product");
        } else {
            searchResults.get(0).click();
            Thread.sleep(1500);
            waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@itemprop='name']")));
            WebElement addCartButton = driver.findElement(By.className("add-to-cart"));
            addCartButton.click();

            // validate order
            waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Produit ajouté au panier avec succès']")));
            Thread.sleep(1500);
            Helpers.takeScreenshot(driver, "order-validation");
            WebElement orderButton = driver.findElement(By.xpath("//a[text()='Commander']"));
            orderButton.click();
        }


        // quit the web driver
        driver.quit();
    }

}
