package week4.day2;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LeafGroundMouseHover {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://www.leafground.com/pages/mouseOver.html");
		System.out.println(driver.getTitle());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement hover = driver.findElement(By.className("btnMouse"));
		Actions builder = new Actions(driver);
		builder.moveToElement(hover).perform();
		List<WebElement> courseList = driver.findElements(By.className("listener"));
		System.out.println("List of courses available : ");
		for (WebElement course : courseList) {
			System.out.println(course.getText());
		}
		driver.findElement(By.className("listener")).click();
		driver.switchTo().alert().accept();
	}

}
