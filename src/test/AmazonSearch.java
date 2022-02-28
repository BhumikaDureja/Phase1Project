package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class AmazonSearch {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	// Properties set for both Chrome & FF Browser	
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
		
		WebDriver driver = new ChromeDriver();
		//WebDriver driver = new FirefoxDriver();
		
		
		// Launching amazon.in
		driver.get("https://www.amazon.in/");    
		
		//Maximize window
		driver.manage().window().maximize();
		
		//wait for browser to load - implicit
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

		// The below code retrieves search results for iphone12 using xpath for product name iphone12 & their price 
		WebElement SearchBox = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		SearchBox.sendKeys("iphone 12");
		
		WebElement SearchIcon = driver.findElement(By.xpath("//input[@id='nav-search-submit-button']"));
		SearchIcon.click();
		
		List<WebElement> iPhone_Model = driver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
		
		List<WebElement> iPhone_Price = driver.findElements(By.xpath("//span[@class='a-price-whole']"));
		
		System.out.println("Total number of iPhone 12 model listed on screen: " +iPhone_Model.size());
		
		for (int count=0; count<iPhone_Model.size(); count++) {
			
			if(iPhone_Model.get(count).getText().toLowerCase().contains("iphone 12")) {
				
				System.out.println("Product Name: " +iPhone_Model.get(count).getText() +" Price: " +iPhone_Price.get(count).getText());
			}
			}
	
	
	// Part 2 of the project - setting up DB connection and retrieving results from database. 
	//External jar file for setting up the JDBC has been added	
		
		Class.forName("com.mysql.cj.jdbc.Driver");  
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "root");
		
		Statement stm = con.createStatement();
		
		ResultSet result = stm.executeQuery("select * from eproduct");
		
		ArrayList<String> names = new ArrayList<String>();
		
	// retrieving results from DB and storing in ArrayList	
		while(result.next()) {
			
			System.out.println(result.getInt("ID"));
			System.out.println(result.getString("name"));
			
			names.add(result.getString("name"));
			
		}
		
		System.out.println("names in array list : "+names);
		
		// Iterating through array list
		
		for(int index=0; index<names.size();index++)
		{
			System.out.println("Name at " +index +" : " +names.get(index));
			
			//Storing the name value in a new String variable
			
			String input = names.get(index);
			System.out.println("Input value in search field is : " +input);
			
			// Based on the results from DB, the product information for Laptop & Tablet are retrieved
			
			if(input.equalsIgnoreCase("Laptop")) {
				
				WebElement SearchBox1 = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
				SearchBox1.clear();
				SearchBox1.sendKeys(input);
				
				WebElement SearchIcon1 = driver.findElement(By.xpath("//input[@id='nav-search-submit-button']"));
				SearchIcon1.click();
				
				List<WebElement> Laptop_Model = driver.findElements(By.xpath("//div[@class='a-section a-spacing-none s-padding-right-small s-title-instructions-style']"));
				
				List<WebElement> Laptop_Price = driver.findElements(By.xpath("//span[@class='a-price-whole']"));
				
				System.out.println("Total number of Laptop model listed on screen: " +Laptop_Model.size());
				
				for (int count1=0; count1<Laptop_Model.size(); count1++) {
					
					if(Laptop_Model.get(count1).getText().contains("Laptop")) {
						
						System.out.println("Product Name: " +Laptop_Model.get(count1).getText() +" Price: " +Laptop_Price.get(count1).getText());
					}
					
		
					}
			}
			
			else if (input.equalsIgnoreCase("Tablet"))
			{
			
				WebElement SearchBox2 = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
				SearchBox2.clear();
				SearchBox2.sendKeys(input);
				
				WebElement SearchIcon2 = driver.findElement(By.xpath("//input[@id='nav-search-submit-button']"));
				SearchIcon2.click();
				
				List<WebElement> Tablet_Model = driver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
				
				List<WebElement> Tablet_Price = driver.findElements(By.xpath("//span[@class='a-price-whole']"));
				
				System.out.println("Total number of Laptop model listed on screen: " +Tablet_Model.size());
				
				for (int count2=0; count2<Tablet_Model.size(); count2++) {
					
					if(Tablet_Model.get(count2).getText().contains("Tab")) {
						
						System.out.println("Product Name: " +Tablet_Model.get(count2).getText() +" Price: " +Tablet_Price.get(count2).getText());
					}
					
		
					}
			
		}
		
		con.close();
		
		
	}
		driver.close();
	}

}
