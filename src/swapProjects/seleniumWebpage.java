package swapProjects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class seleniumWebpage {

	public static WebDriver driver;

	@BeforeTest 
	public static void main(String[] args) throws InterruptedException, IOException 

	{
		verifyURL();
		//verifySignin();
		shopProducts();
		singoutPage();
	}

	@BeforeTest
	public static void verifyURL() throws InterruptedException {

		//Open URL
		System.setProperty("webdriver.chrome.driver","C:\\selenium\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.amazon.in");
		driver.manage().deleteAllCookies();
		//driver.manage().window().maximize();
		String strTitle = driver.getTitle();
		System.out.println(strTitle);
		Thread.sleep(4000);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	/*	
		//Mouse Hover
		Actions actions = new Actions(driver);
		WebElement signinOption = driver.findElement(By.id("nav-link-accountList-nav-line-1"));
		actions.moveToElement(signinOption).perform();

	}

	 public static void verifySignin() throws IOException {

		//Sign in 
		driver.findElement(By.linkText("Sign in")).click();
		System.out.println("Done mouse over on Sign in");
		WebElement email = driver.findElement(By.name("email"));

		try {
			email.sendKeys("swapnilsalukhe@yahoo.co.in");
			driver.findElement(By.id("continue")).click();
			WebElement password = driver.findElement(By.name("password"));
			password.sendKeys("92827262");
			driver.findElement(By.className("a-button-input")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}	

	 }
	 */

	@Test
	public static void shopProducts() throws InterruptedException, IOException {

		//Search for products
		FileInputStream fs = new FileInputStream("C:\\Users\\swapn\\eclipse-workspace\\SeleniumFirstProject\\excel\\data.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sheet1 =  wb.getSheet("Sheet1");
		int lastrow = sheet1.getLastRowNum()- sheet1.getFirstRowNum();

		for (int i=0; i<lastrow +1 ; i++)

		{
			Row row = sheet1.getRow(i);
			String val1 = row.getCell(i).getStringCellValue();
			System.out.println("Value of i is :"+ val1);

			for (int j =1; j<row.getLastCellNum(); j++)
			{
				String val2 = row.getCell(j).getStringCellValue(); 
				System.out.println("Value of j is :"+ val2);
				WebElement nikeSrch = driver.findElement(By.id("twotabsearchtextbox"));
				nikeSrch.clear();
				nikeSrch.sendKeys("Nike T-shirts");
				driver.findElement(By.id("nav-search-submit-button")).click();
				driver.findElement(By.xpath("//*[@id=\"p_89/Nike\"]/span/a/div/label/i")).click();
				driver.findElement(By.id("low-price")).sendKeys(val1);
				driver.findElement(By.id("high-price")).sendKeys (val2);
				Thread.sleep(5000);
				driver.findElement(By.className("a-button-input")).click();
				System.out.println("Displayed Nike T-Shirts");
				Select sortdd = new Select(driver.findElement(By.id("s-result-sort-select")));
				sortdd.selectByVisibleText("Newest Arrivals");
				driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[3]/div[2]/div[2]/div/span/div/div/div[1]/div/span/a/div/img")).click();
				System.out.println("Selected first new arrivals");
				Thread.sleep(4000);

				String currentTab = driver.getWindowHandle();
				for (String tab : driver.getWindowHandles()) 
				{
					if (!tab.equals(currentTab))
					{
						driver.switchTo().window(tab); 
						try {
							Select sizedd = new Select(driver.findElement(By.id("native_dropdown_selected_size_name")));
																		
							String psize = driver.findElement(By.id("native_dropdown_selected_size_name")).getText();
						
							System.out.println("print options" +psize);
							
							sizedd.selectByVisibleText("S");
							Thread.sleep(4000);
							
						} catch (NoSuchElementException e1) {
							// TODO Auto-generated catch block
							System.out.println(e1.getMessage());
						}
						
						finally {
							driver.findElement(By.xpath("//*[@id=\"add-to-cart-button\"]")).click();
							System.out.println("Added cart");
							Thread.sleep(4000);
							driver.findElement(By.id("hlb-view-cart-announce")).click();
							System.out.println("Go to cart");
							driver.findElement(By.className("a-color-link")).click();
							System.out.println("Empty the cart");
							Thread.sleep(4000); 
							//driver.close();
							System.out.println("this is it");
							driver.switchTo().window(currentTab);
						
						}

					}       
				}     
			}
		}
	}

	@Test

	public static void singoutPage() throws InterruptedException {

		//Sign-out

		WebElement signonDd = driver.findElement(By.id("nav-link-accountList-nav-line-1"));
		Actions action2 = new Actions(driver);
		action2.moveToElement(signonDd).perform();
		//driver.findElement(By.xpath("//*[@id=\"nav-item-signout\"]/span")).click();
		//System.out.println("Successfully signed out");
		//Thread.sleep(4000);
		driver.close();

	}
}
