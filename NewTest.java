import static org.testng.Assert.assertTrue;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
//--
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
//--
import org.openqa.selenium.WebDriver;
//--
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Wait;
//--
//--
import org.testng.Assert;
//--
import org.testng.annotations.AfterClass;
//--
import org.testng.annotations.BeforeClass;
//--
import org.testng.annotations.Test;
import java.util.NoSuchElementException;

public class NewTest {
 
    private WebDriver driver;
 
    @BeforeClass
    public void beforeClass() {
    	System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
        driver = new ChromeDriver();
    }
 
    @AfterClass
    public void afterClass() {
        driver.quit();
    }
    
    @Test
    public void FirstTask() throws InterruptedException {
 
        driver.get("https://www.bayut.com/");
        driver.manage().window().maximize();
       
        WebElement location = driver.findElement(By.xpath("//input[@class='a41c4dcc _6a3a3de9']"));
        location.sendKeys("Dubai Marina");
        Thread.sleep(2000);
        location.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        
        WebElement rent = driver.findElement(By.xpath("(//span[@class='fontCompensation'])[1]"));
        rent.click();
        Thread.sleep(2000);
        
        WebElement buy = driver.findElement(By.xpath("//button[@aria-label='Buy']"));
        buy.click();
        Thread.sleep(2000);
        
        WebElement find = driver.findElement(By.xpath("//a[@aria-label='Find button']"));
        find.click();
        Thread.sleep(2000);
        
        
        
        WebElement next = driver.findElement(By.xpath("//div[@title='Next']"));
        
        // This is a list of buttons that are at the bottom of the page(previous page, next page etc. )
        List<WebElement> lista = driver.findElements(By.xpath("//a[@class='b7880daf']"));
        
        //Checking in all the pages that have properties for sale and are in Dubai Marina area.
        while (next.isDisplayed()){   
        	// Checking the properties in the current page.
        	List<WebElement> texte = driver.findElements(By.xpath("//div[@class='_7afabd84']"));
        	for(WebElement text: texte) {
        		String actualText = text.getText();
        		assertTrue(actualText.contains("Dubai Marina"));
        		//System.out.println(text.getText());
        	}
        	// Move to the next page.
        	next.click();
        	String strUrl = driver.getCurrentUrl();
        	driver.get(strUrl);
        	lista = driver.findElements(By.xpath("//a[@class='b7880daf']"));
        	
        	// This is for the last page. If it has 6 buttons at the bottom(the button for 
			//current page is not included),then it is the last page.  	
        	if(lista.size() == 6)
        	{
        		List<WebElement> texts = driver.findElements(By.xpath("//div[@class='_7afabd84']"));
            	for(WebElement text: texts) {
            		String actualText = text.getText();
            		assertTrue(actualText.contains("Dubai Marina"));
            		//System.out.println(text.getText());
            	}
            	break;
        	}
        	next = driver.findElement(By.xpath("//div[@title='Next']"));
        }
    }
    
    @Test
    public void SecondTask() throws InterruptedException {

        driver.get("https://www.bayut.com/");
        driver.manage().window().maximize();
        Thread.sleep(2000);
        
        // Scroll Down to "Popular searches in the UAE" Section.
        WebElement l=driver.findElement(By.xpath("//div[@class='fa2044b7']"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", l);
        Thread.sleep(2000);
        
        WebElement toRent = driver.findElement(By.xpath("//div[@class='d8530318']"));
        toRent.click();
        Thread.sleep(2000);
        
        WebElement viewAll = driver.findElement(By.xpath("(//div[@class='_2f838ff4 _5b112776 _29dd7f18'])[3]"));
        viewAll.click();
        Thread.sleep(2000);
          
        WebElement property;
        WebElement linkName;
        String href;
        String linkNameText;
        String clicklnk = Keys.chord(Keys.CONTROL,Keys.ENTER);
        
        // Checking the areas under Dubai apartments that are for rent.
        for(int i = 62; i <= 74; i++) {
        	property =  driver.findElement(By.xpath("(//a[@class='_78d325fa '])["+i+"]"));
            property.sendKeys(clicklnk);
            href = property.getAttribute("href");
            Thread.sleep(4000);
            
            ArrayList<String> w = new ArrayList<String>(driver.getWindowHandles());
            
            //switch to open tab
            // Validating the current URL with the link(href) that was on the previous button.
            driver.switchTo().window(w.get(1));
            String strUrl = driver.getCurrentUrl();
            assertTrue(strUrl.equals(href));
            //System.out.println(href);
            
            //Validating that text "Apartments for Rent in Dubai" is on the page.
            linkName = driver.findElement(By.xpath("(//span[@class='_327a3afc'])[2]"));
            linkNameText = linkName.getText();
            assertTrue(linkNameText.equals("Apartments for Rent in Dubai"));
            //System.out.println(linkNameText);
            
            Thread.sleep(4000);
            driver.close();
            
            //switch to first tab
            driver.switchTo().window(w.get(0));
            Thread.sleep(4000);
        }
    }
}