import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class Test {

    private WebDriver driver;
    private String url = "https://vue-demo.daniel-avellaneda.com";


    @BeforeClass
    public void createDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\bootcamp\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void openWebsite() {
        driver.get(url);
    }

    @AfterClass
    public void afterClass() {
        driver.close();
    }

    @org.testng.annotations.Test
    public void verifyRoute() throws InterruptedException {
        Thread.sleep(2000);
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]"));
        loginButton.click();
        //Thread.sleep(2000);

        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("password"));


        if ((url + "/login").equals(driver.getCurrentUrl())) {
            System.out.println("url ima /login u sebi");
        } else {
            System.out.println("url nema /login u sebi");
        }


        if (email.getAttribute("type").equals("email")) {
            System.out.println("Atribut ima vrednost email.");
        } else {
            System.out.println("Atribut nema vrednost email.");
        }

        if (password.getAttribute("type").equals("password")) {
            System.out.println("Atribut ima vrednost password");
        } else {
            System.out.println("Atribut nema vrednost paswword");
        }
    }

    @org.testng.annotations.Test
    public void verifyMessage() throws InterruptedException {
        Thread.sleep(2000);
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]"));
        loginButton.click();
        Thread.sleep(2000);

        Faker faker = new Faker();

        WebElement email = driver.findElement(By.id("email"));
        String emailField = faker.internet().emailAddress();
        email.sendKeys(emailField);

        WebElement password = driver.findElement(By.id("password"));
        String passwordField = faker.internet().password();
        password.sendKeys(passwordField);

        WebElement loginEmailPassword = driver.findElement(By.xpath("//*[@id=\"app\"]/div/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button"));
        loginEmailPassword.click();

        Thread.sleep(2000);
        String expectedMessage = "User does not exists";
        WebElement message = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        String actualMessage = message.getText();

        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @org.testng.annotations.Test
    public void wrongPassword() throws InterruptedException {
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]"));
        loginButton.click();
        Thread.sleep(2000);

        WebElement emailAdmin = driver.findElement(By.id("email"));
        emailAdmin.sendKeys("admin@admin.com");

        Faker faker = new Faker();
        WebElement password12345 = driver.findElement(By.id("password"));
        String fakePassword = faker.internet().password();
        password12345.sendKeys(fakePassword);

        WebElement loginAdmin = driver.findElement(By.xpath("//*[@id=\"app\"]/div/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button"));
        loginAdmin.click();

        Thread.sleep(5000);//stavljam da bih uspela da vidim poruku, nakon cega se program zatvara (driver.close();)

        String expectedResult = "Wrong password";
        WebElement messageWrongPassword = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        String actualResult = messageWrongPassword.getText();

        Assert.assertEquals(actualResult, expectedResult);
    }
}


/*Testirati na stranici https://vue-demo.daniel-avellaneda.com login stranicu.

Test 1: Verifikovati da se u url-u stranice javlja ruta "/login".
Verifikovati da atribut type u polju za unos email ima vrednost "email" i za password da ima atribut type "password.

Test 2: Koristeci Faker uneti nasumicno generisan email i password i verifikovati da se pojavljuje poruka "User does not exist".

Test 3: Verifikovati da kad se unese admin@admin.com (sto je dobar email) i pogresan password (generisan faker-om),
da se pojavljuje poruka "Wrong password"

Koristiti TestNG i dodajte before i after class metode.

Domaci se kači na github.
*/