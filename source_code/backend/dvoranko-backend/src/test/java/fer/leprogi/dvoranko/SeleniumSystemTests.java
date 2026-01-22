package fer.leprogi.dvoranko;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumSystemTests {

    @Test
    public void loginAndNavigate() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:8080/test/login");
        driver.get("http://localhost:5173/my-profile");
    }

    @Test
    public void testEditDvorana(){

        int newPrice = 50;
        int newMaxCapacity = 200;
        String newOpis = "Big boy dvorana";
        String newIme = "Matijina dvorana";


        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:8080/test/login");
        driver.get("http://localhost:5173/my-profile");

        System.out.println("Navigating to create dvorana page");
//        driver.findElement(By.id("add-venue-button")).click();

        WebElement editVenueButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("uredi-dvoranu-33")
                )
        );
        editVenueButton.click();
        System.out.println("Clicking edit dvorana button for dvorana with id 33");



        WebElement name = driver.findElement(By.id("naziv-input"));
        name.clear();
        name.sendKeys(newIme);
        System.out.println("Inputing dvorana name");

        WebElement maxCapacity = driver.findElement(By.id("kapacitet-input"));
        maxCapacity.clear();
        maxCapacity.sendKeys(String.valueOf(newMaxCapacity));
        System.out.println("Inputing max capacity");

        WebElement price = driver.findElement(By.id("cijena-input"));
        price.clear();
        price.sendKeys(String.valueOf(newPrice));
        System.out.println("Inputing price");

        WebElement categoryButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='flex flex-wrap gap-[5px] w-[90%]']/button[normalize-space()='Odbojka']")
                )
        );
        categoryButton.click();
        System.out.println("Clicking category");


        WebElement eopis = driver.findElement(By.id("opis-input"));
        eopis.clear();
        eopis.sendKeys(newOpis);
        System.out.println("Inputing opis");

//        WebElement mondayCheckbox = wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.xpath("//label[normalize-space()='Ponedjeljak']/following-sibling::input[@type='checkbox']")
//                )
//        );
//        mondayCheckbox.click();
//        System.out.println("Clicking monday checkbox");
//
//        WebElement odDropdown = wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.xpath("//label[normalize-space()='Od:']/following-sibling::div")
////                        By.xpath("//div[label[normalize-space()='Ponedjeljak']]//label[normalize-space()='Od:']/following-sibling::div")
//
//                )
//        );
//        odDropdown.click(); // otvori dropdown
//        System.out.println("Clicking od dropdown");
//
//        WebElement odOption = wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.id("time-option-08")
//                )
//        );
//
//        odOption.click();
//        System.out.println("Selecting 8h");
//
//        WebElement doDropdown = wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.xpath("//label[normalize-space()='Do:']/following-sibling::div")
//                )
//        );
//        doDropdown.click();
//        System.out.println("Clicking do dropdown");
//
//        WebElement doOption = wait.until(
//                ExpectedConditions.elementToBeClickable(
//                        By.id("time-option-22")
//                )
//        );
//        doOption.click();
//        System.out.println("Selecting 22h");

        WebElement submitButton = driver.findElement(By.id("submit-update-button"));
        submitButton.click();
        System.out.println("Submitting form");

        System.out.println("Navigating back to profile page");


        WebElement visitDvorana = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("view-venue-33"))
        );
        visitDvorana.click();
        System.out.println("Navigating to edited dvorana page");


        System.out.println("Verifying edited dvorana data");

        WebElement editedIme =  wait.until(
                ExpectedConditions.elementToBeClickable(By.id("ime-dvorana")));
//                driver.findElement(By.id("ime-dvorana"));
        String ime = editedIme.getText();

        WebElement editedKapacitet = driver.findElement(By.id("kapacitet"));
        int kapacitet = Integer.parseInt(editedKapacitet.getText().replace("Kapacitet: ", "").trim());

        WebElement editedCijena = driver.findElement(By.id("cijena"));
        int cijena = Integer.parseInt(editedCijena.getText().replace("Cijena po satu: ", "").replace("€/h", "").trim());

        WebElement editedOpis = driver.findElement(By.id("opis"));
        String opis = editedOpis.getText().replace("Opis prostora: ", "");

        assert ime.equals(newIme) : "Ime dvorane nije ispravno azurirano";
        assert kapacitet == newMaxCapacity : "Kapacitet dvorane nije ispravno azurirano";
        assert cijena == newPrice : "Cijena dvorane nije ispravno azurirano";
        assert opis.equals(newOpis) : "Opis dvorane nije ispravno azurirano";

        System.out.println("Edit dvorana test completed successfully");

        driver.quit();
    }



}
