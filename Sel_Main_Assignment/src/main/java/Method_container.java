import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;

@Listeners(Listener.class)
public class Method_container {
    static Logger log = LogManager.getLogger(Method_container.class);
    static WebDriver driver;
    static int new_client_id;
    static String FirstName;
    static String LastName;
    static String PinCode;
    static String deposit;
    static String withdraw;
    public static void read_data() throws Exception
    {
        String line = "";
        String splitBy = ",";
        ArrayList<String> lst = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader("/Users/sanyalsubhajit39/MYPC/codes/java_codes/eclipse_projects/IBM_Projects/Sel_Main_Assignment/src/main/resources/bank_manager_login_add_cus.csv"));
        String[] data = new String[0];
        while ((line = br.readLine()) != null) {
            data = line.split(splitBy);
            FirstName = data[0];
            LastName = data[1];
            PinCode = data[2];
            deposit=data[3];
            withdraw=data[4];
        }

    }
    public static void initialSetup() throws Exception
    {
        //Loading CSV
        System.out.println("Q:A :- Open Website");
        log.debug("Q:A :- Open Website");
        read_data();
        System.setProperty("webdriver.chrome.driver", "/Users/sanyalsubhajit39/Softwares/chromedriver2");
        driver = new ChromeDriver();
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        Thread.sleep(1000);
        driver.manage().window().maximize();

    }
    public static void add_new_cus() throws Exception{
        System.out.println("Q:B :- Add New Customer:- "+FirstName+" "+LastName);
        log.debug("Q:B :- Add New Customer:- "+FirstName+" "+LastName);
        //Clicked "bank manager login"
        WebElement btn = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button"));
        btn.click();
        Thread.sleep(2000);
        //Clicked "add customer"
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[1]")).click();
        Thread.sleep(2000);
        // Feeding values fetched from Excel
        WebElement txt = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input"));

        txt.sendKeys(FirstName);
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input")).sendKeys(LastName);


        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input")).sendKeys(PinCode);
        Thread.sleep(2000);
        //Submit
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();
        Thread.sleep(2000);
        //New account Created
        Alert alert = driver.switchTo().alert();
        String alertMessage = driver.switchTo().alert().getText();
        alert.accept();
        String[] al_t = alertMessage.split(" ");
        new_client_id = Integer.parseInt(al_t[al_t.length - 1].substring(1));
        log.debug("Successfully created new customer with ID"+ new_client_id);
        System.out.println("Successfully created new customer with ID"+ new_client_id);
    }
    public static void open_acc_new_cus() throws Exception{
        //Click "Open Account"
        System.out.println("Q:C :- Open Account of"+FirstName);
        log.debug("Q:C :- Open Account of"+FirstName);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[2]")).click();
        Thread.sleep(2000);
        // Select user just registered by ID
        WebElement drop = driver.findElement(By.xpath("//*[@id=\"userSelect\"]"));
        Select select = new Select(drop);
        Thread.sleep(2000);
        select.selectByIndex(new_client_id);

        WebElement drop1 = driver.findElement(By.xpath("//*[@id=\"currency\"]"));
        Select select1 = new Select(drop1);
        Thread.sleep(2000);
        select1.selectByValue("Rupee");
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
        log.debug("Account opening successfull" +FirstName);
        System.out.println("Account opening successfull" +FirstName);

    }
    public  static void login() throws Exception{
        // Click "Home
        System.out.println("Q:D :- Logging in as" +FirstName);
        log.debug("Q:D :- Logging in as" +FirstName);
        driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
        Thread.sleep(2000);

        //clicking "customer login section"
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[1]/button")).click();
        Thread.sleep(2000);

        //Searching for new user in the dropdown by ID
        WebElement drop3 = driver.findElement(By.xpath("//*[@id=\"userSelect\"]"));
        Select select2 = new Select(drop3);
        select2.selectByIndex(new_client_id);
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/button")).click();
        Thread.sleep(2000);
        //Loggedin as new User
        log.debug("Logged in as" +FirstName);
        System.out.println("Logged in as" +FirstName);

    }
    public  static void deposit_test() throws Exception{
        // Fetching Current Balance before deposit
        System.out.println("Q:E :- Depositting" +deposit);
        log.debug("Q:E :- Depositting" +deposit);
        int cur_bal= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        Thread.sleep(2000);
        System.out.println(cur_bal);
        // Click on "deposit"
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[2]")).click();
        Thread.sleep(2000);
        // Pass amt fetched from excel
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(deposit);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
        Thread.sleep(2000);
        int bal_after_deposit= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        // If deposit is Consistent then Successfully
        if (bal_after_deposit-cur_bal==Integer.parseInt(deposit)){
            //log.debug( +driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
            System.out.println("Concurrency maintained"+driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());}
        else{
            log.debug("Concurrency not-maintained");
            System.out.println("Concurrency not-maintained");
        }
    }
    public  static void withdrawl_test() throws Exception{
        System.out.println("Q:F :- Withdrawing" + withdraw);
        log.debug("Q:F :- Withdrawing" + withdraw);
        int cur_bal= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        Thread.sleep(2000);
        // Click withdraw
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[3]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(withdraw);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
        Thread.sleep(2000);
        int bal_after_withdraw= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        // If withdrawal is Consistent then Successfully
        if (cur_bal-bal_after_withdraw==Integer.parseInt(withdraw)){
            log.debug("Concurrency maintained" +driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
            System.out.println("Concurrency maintained" +driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());}
        else{
            log.debug("Concurrency not-maintained");
        }
    }
    public  static void withdraw_exception() throws Exception{

        //Trying to withdraw more than available balance
        int cur_bal= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        System.out.println("Q:F :- Withdrawing more than available" + cur_bal+10);
        log.debug("Q:G :- Withdrawing more than available" + cur_bal+10);
        Thread.sleep(2000);
        System.out.println(cur_bal);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[3]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(Integer.toString(cur_bal+10));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
        Thread.sleep(2000);
        log.debug(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
        System.out.println(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
    }




}
