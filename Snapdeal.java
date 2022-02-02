package week4.day2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Snapdeal {

	public static void main(String[] args) throws InterruptedException, IOException {

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		// 1. Launch https://www.snapdeal.com/
		driver.get("https://www.snapdeal.com/");
		System.out.println(driver.getTitle());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// 2. Go to Mens Fashion
		WebElement mens = driver.findElement(By.xpath("(//span[@class='catText'])[6]"));
		Actions builder = new Actions(driver);
		builder.moveToElement(mens).perform();

		// 3. Go to Sports Shoes
		driver.findElement(By.xpath("//span[text()='Sports Shoes']")).click();

		// 4. Get the count of the sports shoes
		String count = driver.findElement(By.xpath("//span[@class='category-count']")).getText();
		System.out.println("Count of the sports shoes : " + count);

		// 5. Click Training shoes
		driver.findElement(By.xpath("//div[text()='Training Shoes']")).click();

		// 6. Sort by Low to High
		driver.findElement(By.className("sort-label")).click();
		driver.findElement(By.xpath("//li[@data-sorttype='plth']")).click();

		// 7. Check if the items displayed are sorted correctly
		WebElement end = driver.findElement(By.xpath("//span[@class='btn-yes js-yesFeedback']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", end);
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0, -700)");
		Thread.sleep(5000);
		List<WebElement> prices = driver.findElements(By.xpath("//span[@class='lfloat product-price']"));
		// System.out.println(prices.size());
		boolean flag = true;
		for (int i = 0; i < (prices.size()) - 1; i++) {
			int first = Integer.parseInt(prices.get(i).getAttribute("display-price"));
			for (int j = i + 1; j < prices.size(); j++) {
				int second = Integer.parseInt(prices.get(j).getAttribute("display-price"));
				if (first > second) {
					// System.out.println(first + " " + second);
					flag = false;
					break;
				}
			}
			if (!flag) {
				System.out.println("Items displayed are not sorted");
				break;
			}
		}
		if (flag)
			System.out.println("Items displayed are sorted");

		// 8. Mouse Hover on puma Blue Training shoes
		WebElement blueShoe = driver.findElement(By.xpath("//img[@title='VSS Blue Training Shoes']"));
		builder.moveToElement(blueShoe).perform();

		// 9. click QuickView button
		driver.findElement(By.xpath("(//img[@title='VSS Blue Training Shoes']/following::div)[2]")).click();

		// 10. Print the cost and the discount percentage
		System.out
				.println("Cost of the shoe : " + driver.findElement(By.xpath("//span[@class='payBlkBig']")).getText());
		System.out.println("Discount Percantage of the shoe : "
				+ driver.findElement(By.xpath("//span[@class='percent-desc ']")).getText());

		// 11. Take the snapshot of the shoes.
		File source = driver.getScreenshotAs(OutputType.FILE);
		File dest = new File("./snaps/item4.png");
		FileUtils.copyFile(source, dest);

		// 12. Close the current window
		driver.findElement(By.xpath("//div[@class='close close1 marR10']/i")).click();

		// 13. Close the main window
		driver.quit();
	}
}
