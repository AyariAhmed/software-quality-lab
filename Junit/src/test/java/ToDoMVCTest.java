import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class ToDoMVCTest {
    WebDriver driver;
    JavascriptExecutor js;
    WebDriverWait waitVar;

    @BeforeAll
    public static void initialize() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void prepareDriver(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        waitVar = new WebDriverWait(driver, Duration.ofSeconds(40)); // explicit wait
        js = (JavascriptExecutor) driver;
    }
    @AfterEach
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void todoTestCase() throws InterruptedException {
        driver.get("https://todomvc.com");
        setPlatform("Backbone.js");
        String[] todos = {"Plan user stories","Daily stand-up","Sprint planning","Team retro"};
        for (String todo : todos){
            addTodo(todo);
        }
        tickTodo(1);
        expectTodosLeftCount(3);
        tickTodo(2);
        tickTodo(2);
        expectFunctionalToggle();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Backbone.js",
            "React",
            "Dijon"
    })
    public void todoTestCase(String platform) throws InterruptedException {
        driver.get("https://todomvc.com");
        setPlatform(platform);
        String[] todos = {"Plan user stories","Daily stand-up","Sprint planning","Team retro","Bootcamp"};
        for (String todo : todos){
            addTodo(todo);
        }
        // Randomly ticking a todo
        tickTodo(2);
        expectTodosLeftCount(3);
        tickTodo(1);
        tickTodo(1);
        expectFunctionalToggle();
    }


    private void setPlatform(String platform) {
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(platform)));
        WebElement chosenPlatform = driver.findElement(By.linkText(platform));
        chosenPlatform.click();
    }

    private void addTodo(String todo) {
        waitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder,'What needs to be done?')]")));
        WebElement addTodoElement = driver.findElement(By.xpath("//input[contains(@placeholder,'What needs to be done?')]"));
        addTodoElement.sendKeys(todo);
        addTodoElement.sendKeys(Keys.RETURN);
    }

    private void tickTodo(int index) {
        WebElement pickedElement = driver.findElement(By.cssSelector("li:nth-child("+index+") .toggle"));
        pickedElement.click();
    }
    private void expectTodosLeftCount(int expectedCount) {
        WebElement todoCountElement = driver.findElement(By.className("todo-count"));
        ExpectedConditions.textToBePresentInElement(todoCountElement, Integer.toString(expectedCount));
    }
    private void expectFunctionalToggle() {
                int totalTodos = driver.findElements(By.xpath("//ul[contains(@class,'todo-list')]//li")).size();
        int completedTodos = driver.findElements(By.xpath("//ul[contains(@class,'todo-list')]//li[contains(@class,'completed')]")).size();
        WebElement todoCountElement = driver.findElement(By.className("todo-count"));
        ExpectedConditions.textToBePresentInElement(todoCountElement, Integer.toString(totalTodos-completedTodos));
    }

}
