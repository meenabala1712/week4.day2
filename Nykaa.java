package week4.day2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Nykaa {

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		// 1) Go to https://www.nykaa.com/
		driver.get("https://www.nykaa.com/");
		System.out.println(driver.getTitle());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// 2) Mouseover on Brands and Mouseover on Popular
		WebElement brands = driver.findElement(By.xpath("//a[text()='brands']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(brands).perform();
		WebElement popular = driver.findElement(By.xpath("//a[text()='Popular']"));
		builder.moveToElement(popular).perform();

		// 3) Click L'Oreal Paris
		driver.findElement(By.xpath("//img[@src='https://adn-static2.nykaa.com/media/wysiwyg/2019/lorealparis.png']"))
				.click();

		// 4) Go to the newly opened window and check the title contains L'Oreal Paris
		Set<String> windowHandlesSet = driver.getWindowHandles();
		List<String> windowHandlesList = new ArrayList<String>(windowHandlesSet);
		// System.out.println(windowHandlesList.size());
		WebDriver lorealDriver = driver.switchTo().window(windowHandlesList.get(1));
		if (lorealDriver.getTitle().contains("L'Oreal Paris"))
			System.out.println("Title contains L'Oreal Paris in it");
		else
			System.out.println("Title doesn't contain L'Oreal Paris in it");

		// 5) Click sort By and select customer top rated
		driver.findElement(By.xpath("//span[@class='pull-left']")).click();
		driver.findElement(By.xpath("//div[@for='3']/div")).click();

		// 6) Click Category and click Shampoo
		driver.findElement(By.xpath("//div[text()='Category']")).click();
		driver.findElement(By.xpath("//span[text()='Hair']")).click();
		driver.findElement(By.xpath("//span[text()='Hair Care']")).click();
		driver.findElement(By.xpath("//span[text()='Shampoo']")).click();

		// 7) check whether the Filter is applied with Shampoo
		String filter = driver.findElement(By.xpath("//ul[@class='pull-left applied-filter-lists']/li")).getText();
		if (filter.contains("Shampoo"))
			System.out.println("Filter is applied with Shampoo");
		else
			System.out.println("Filter is not applied with Shampoo");

		// 8) Click on L'Oreal Paris Colour Protect Shampoo
		List<WebElement> productList = driver.findElements(By.xpath("//div[@class='m-content__product-list__title']"));
		for (WebElement product : productList) {
			if (product.getText().contains("L'Oreal Paris Colour Protect Shampoo")) {
				product.click();
				break;
			}
		}

		// 9) GO to the new window and select size as 175ml
		Set<String> windowHandlesSet1 = driver.getWindowHandles();
		List<String> windowHandlesList1 = new ArrayList<String>(windowHandlesSet1);
		// System.out.println(windowHandlesList1.size());
		driver.switchTo().window(windowHandlesList1.get(2));
		driver.findElement(By.xpath("//span[text()='175ml']")).click();

		// 10) Print the MRP of the product
		String mrp = driver.findElement(By.xpath("//span[@class='post-card__content-price-offer']")).getText();
		System.out.println("MRP of the product is : " + mrp);

		// 11) Click on ADD to BAG
		driver.findElement(By.xpath("//div[@class='pull-left']//button")).click();
		Thread.sleep(2000);

		// 12) Go to Shopping Bag
		driver.findElement(By.className("AddBagIcon")).click();

		// 13) Print the Grand Total amount
		Set<String> windowHandlesSet2 = driver.getWindowHandles();
		List<String> windowHandlesList2 = new ArrayList<String>(windowHandlesSet2);
		// System.out.println(windowHandlesList2.size());
		driver.switchTo().window(windowHandlesList2.get(2));
		String grandTotal = driver.findElement(By.xpath("(//div[@class='value'])[4]")).getText();
		System.out.println("Grand Total is : " + grandTotal);

		// driver.switchTo().alert().dismiss();
		List<WebElement> frameElements = driver.findElements(By.tagName("iframe"));
		for (WebElement frameElement : frameElements) {
			if (frameElement.getAttribute("id").equals("__ta_notif_frame_2")) {
				driver.switchTo().frame(frameElement);
				driver.findElement(By.xpath("//div[@class='close']")).click();
				driver.switchTo().defaultContent();
			}
		}

		// driver.findElement(By.xpath("//button[text()='Close']")).click();

		// 14) Click Proceed
		driver.findElement(By.xpath("//button[@class='btn full fill no-radius proceed ']")).click();

		// 15) Click on Continue as Guest
		driver.findElement(By.xpath("//button[text()='CONTINUE AS GUEST']")).click();

		// 16) Check if this grand total is the same in step 13
		String grandTotalFinal = driver.findElement(By.xpath("(//div[@class='value'])[2]")).getText();
		if (grandTotal.equals(grandTotalFinal))
			System.out.println("This grand total is same as step 13");
		else
			System.out.println("This grand total is not the same as step 13");

		// 17) Close all windows
		driver.quit();

	}

}
