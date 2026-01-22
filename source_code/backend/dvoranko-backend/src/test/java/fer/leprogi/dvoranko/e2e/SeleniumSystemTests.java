package fer.leprogi.dvoranko.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SeleniumSystemTests {

    @Test
    public void loginAndNavigate() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:8080/test/login/moderator");
        driver.get("http://localhost:5173/my-profile");

        driver.quit();
    }

    @Test
    public void testEditDvorana(){

        String newIme = "Testna dvorana (edit test)";
        int newPrice = 50;
        int newMaxCapacity = 200;
        String newOpis = "Opis nove testne dvorane, promijenjen u testu";


        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:8080/test/login/moderator");
        driver.get("http://localhost:5173/my-profile");

        System.out.println("Navigating to create dvorana page");

        WebElement editVenueButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("uredi-dvoranu-33"))
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
//                        By.xpath("//div[label[normalize-space()='Ponedjeljak']]//label[normalize-space()='Od:']/following-sibling::div")
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


        WebElement visitDvorana = wait.until(ExpectedConditions.elementToBeClickable(By.id("view-venue-33")));
        visitDvorana.click();
        System.out.println("Navigating to edited dvorana page");


        System.out.println("Verifying edited dvorana data");

        WebElement editedIme =  wait.until(ExpectedConditions.elementToBeClickable(By.id("ime-dvorana")));
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



    @Test
    public void testAdminDeleteDvorana(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        int dvoranaId = 31;

        driver.get("http://localhost:8080/test/login/admin");
        driver.get("http://localhost:5173/admin");

        System.out.println("Navigating to admin page");

        driver.findElement(By.xpath("//button[text()='Prikaži korisnike i dvorane']")).click();
        System.out.println("Clicking button to show users and venues");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-dvorana-btn-"+dvoranaId))).click();
        System.out.println("Clicking delete button for dvorana with id "+dvoranaId);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("delete-dvorana-btn-"+dvoranaId)));
        System.out.println("Verifying that delete button is no longer visible");

        driver.navigate().refresh();
        System.out.println("Refreshing the page to confirm deletion, (frontend fetches data again)");

        driver.findElement(By.xpath("//button[text()='Prikaži korisnike i dvorane']")).click();
        System.out.println("Clicking button to show users and venues again");

        List<WebElement> elements = driver.findElements(By.id("delete-dvorana-btn-"+dvoranaId));

        assert elements.isEmpty() : "Dvorana with id " + dvoranaId + " is not deleted and still visible on admin page";

        System.out.println("Admin delete dvorana test completed successfully");

        driver.quit();

    }

    @Test
    public void testCreateTerminZahtjev(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        int dvoranaId = 33;
        String opisText = "Na ovom terminu radimo testiranje sustava";
        int brojLjudiText = 10;
        String odVrijeme = "20:00";
        String doVrijme = "22:00";
        String datum = "31.01.2026.";

        driver.get("http://localhost:8080/test/login/moderator");
        driver.get("http://localhost:5173/");

        System.out.println("Navigating to home page");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dvorana-link-" + dvoranaId))).click();
        System.out.println("Clicking link to dvorana with id " + dvoranaId);

        System.out.println("Navigating to dvorana with id: " + dvoranaId + " page");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rezerviraj-dvoranu-btn"))).click();
        System.out.println("Clicking button to create termin zahtjev");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@aria-label='31. siječnja 2026.' and text()='31']"))).click();
        System.out.println("Clicking on date 31. siječnja 2026.");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("time-select-" + odVrijeme))).click();
        System.out.println("Selecting starting time: " + odVrijeme);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("time-select-" + doVrijme))).click();
        System.out.println("Selecting ending time: " + doVrijme);

        WebElement opis = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-opis-dogadanja")));
        opis.sendKeys(opisText);
        System.out.println("Inputing opis dogadanja");

        WebElement brojLjudi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-broj-ljudi")));
        brojLjudi.sendKeys(String.valueOf(brojLjudiText));
        System.out.println("Inputing broj ljudi");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-zahtjev-termin-btn"))).click();
        System.out.println("Submitting termin zahtjev");

//        driver.get("http://localhost:5173/my-profile/" + dvoranaId);
        System.out.println("Navigating to my profile page");


        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.flex-1.rounded-xl.p-5.flex.flex-col.gap-3")));

        List<WebElement> kartice = driver.findElements(By.cssSelector("div.flex-1.rounded-xl.p-5.flex.flex-col.gap-3"));

        String opisOut = null;
        String odOut = null;
        String doOut = null;
        String datumOut = null;


        System.out.println("Verifying termin zahtjev data");
        for (WebElement kartica : kartice) {
            // 2️⃣ Dohvati opis unutar kartice
            WebElement opisElement = kartica.findElement(
                    By.cssSelector("div.flex.flex-col.gap-1.items-start.w-full > p.text-gray-700")
            );

            String opisTekst = opisElement.getText().trim();
            if (opisTekst.equals(opisText)) {
//                System.out.println("Pronađena kartica s opisom: " + opisTekst);
                opisOut = opisTekst;

                // Dohvati "Datum", “Od” i “Do” unutar kartice
                WebElement datumElement = kartica.findElement(By.xpath(".//p[span[text()='Datum:']]"));
                WebElement odElement = kartica.findElement(By.xpath(".//p[span[text()='Od:']]"));
                WebElement doElement = kartica.findElement(By.xpath(".//p[span[text()='Do:']]"));

                odOut= odElement.getText().replace("Od: ", "").trim();
                doOut = doElement.getText().replace("Do: ", "").trim();
                datumOut = datumElement.getText().replace("Datum: ", "").trim();

//                System.out.println("Od: " + odOut);
//                System.out.println("Do: " + doOut);
//                System.out.println("Datum: " + datumOut);

                break;
            }
        }


        assert opisText.equals(opisOut) : "Opis dogadanja nije ispravan";
        assert odVrijeme.equals(odOut) : "Vrijeme pocetka nije ispravno";
        assert doVrijme.equals(doOut) : "Vrijeme zavrsetka nije ispravno";
        assert datum.equals(datumOut) : "Datum nije ispravan";

        System.out.println("Termin zahtjev test completed successfully");

        driver.quit();

    }


    @Test
    public void testFilterDvorana(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("http://localhost:8080/test/login/user");
        driver.get("http://localhost:5173/");

        String cijenaRange = "40-60";

        System.out.println("Navigating to home page");

        //cekaj da se ucita neka dvorana
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[id^='dvorana-link-']")));
        List<WebElement> kartice = driver.findElements(By.cssSelector("a[id^='dvorana-link-']"));

        boolean postojiDvorana = false;
        for (WebElement kartica : kartice) {
            WebElement cijenaElement = kartica.findElement(By.name("cijena-po-satu"));

            int cijena = Integer.parseInt(cijenaElement.getText().replace("cijena: ", "").replace("€/h", "").trim());

            if (Integer.parseInt(cijenaRange.split("-")[0]) <= cijena && cijena <= Integer.parseInt(cijenaRange.split("-")[1])) {
                System.out.println("Postoji dvorana sa cijenom iz raspona " + cijenaRange);
                postojiDvorana = true;
                break;
            }
        }
        assert postojiDvorana : "Nema dvorana sa cijenom iz raspona " + cijenaRange;

        driver.findElement(By.id("filter-btn")).click();
        System.out.println("Clicking filter button");

        WebElement cijenaOptions = driver.findElement(By.id("filter-cijena-options"));
        Select cijenaSelect = new Select(cijenaOptions);
        cijenaSelect.selectByValue(cijenaRange);
        System.out.println("Selecting new cijena range: " + cijenaRange);

        driver.findElement(By.id("filter-submit-btn")).click();
        System.out.println("Submitting filter form");

        System.out.println("Verifying filtered dvorana data");

        //cekaj da se ucita neka dvorana
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[id^='dvorana-link-']")));
        List<WebElement> karticeAfter = driver.findElements(By.cssSelector("a[id^='dvorana-link-']"));

        for (WebElement kartica: karticeAfter) {
            WebElement cijenaElement = kartica.findElement(By.name("cijena-po-satu"));

            int cijena = Integer.parseInt(cijenaElement.getText().replace("cijena: ", "").replace("€/h", "").trim());

            if (Integer.parseInt(cijenaRange.split("-")[0]) > cijena && cijena > Integer.parseInt(cijenaRange.split("-")[1])) {
                throw new AssertionError("Postoji dvorana sa cijenom iz raspona " + cijenaRange + " nakon filtriranja na raspon " + cijenaRange);
            }
        }

        System.out.println("Filter dvorana test completed successfully, no dvorana found outside the cijena range :" + cijenaRange);

        driver.quit();
    }


}
