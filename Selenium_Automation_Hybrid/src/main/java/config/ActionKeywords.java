package config;

import static executionEngine.DriverScript.getFullPageScreenshot;
import static executionEngine.DriverScript.DecodePassword;
import  executionEngine.DriverScript;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.analysis.function.Sin;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;

import static executionEngine.DriverScript.PDFAutomationOR;
import static executionEngine.DriverScript.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.*;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import utility.ExcelUtils;
import utility.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.*;

import org.apache.commons.codec.binary.Base64;


public class ActionKeywords extends Constants{
	
	//private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	
	public static WebDriver driver;
	
		
@SuppressWarnings("deprecation")
@BeforeClass
public static void openBrowser(String object,String data,String sTestCaseName) throws Exception
	 {		
		
		//Log.info("Opening Browser");
		
		data=(Constants.CurrentBroweser).toString().trim();
		System.out.println("getCellData data openBrowser :"+data);
		
		try{				
			if(data.equals("Mozilla"))
			  {
				System.setProperty("webdriver.gecko.driver","C://ThaoLe//Selenium//Drivers//geckodriver.exe");
				//driver=new FirefoxDriver();
				Log.info("Mozilla browser started");				
				}
			else if(data.equals("IE")){
				WebDriverManager.iedriver().setup();
			     driver = new InternetExplorerDriver();
				Log.info("IE browser started");
				}
			else if(data.equals("Edge")){
				WebDriverManager.edgedriver().setup();
			     driver = new EdgeDriver();
				Log.info("IE browser started");
				}
			else if(data.equals("Chrome")){
				
				WebDriverManager.chromedriver().setup();
				
			     driver = new ChromeDriver();
				//Log.info("Chrome browser started");
				
			}
			
			 driver.manage().window().maximize();
			 driver.manage().deleteAllCookies();
			
			//getDriver().manage().window().maximize();
	        //getDriver().manage().deleteAllCookies();
	        //getDriver().manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
	        //getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			 
			 int implicitWaitTime=(10);
			 driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			 //driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			 DriverScript.bResult = true;
			 
		}catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
		
	 }
	
	public static void navigate(String object,String data,String sTestCaseName) throws Exception{	
		
			try{
				
				//Log.info("Navigating to URL");
				System.out.println("getCellData data openBrowser :"+Constants.URL.toString());
				 //System.out.println("Constants.URL :"+Constants.URL.toString());
				
				  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
			      driver.get(Constants.URL);
			     			      
				 //driver.get().get(Constants.URL);
				 //getDriver().get(data);
				 
				
				
								
			}catch(Exception e){
				Log.info("Not able to navigate --- " + e.getMessage());
				DriverScript.bResult = false;
				}
			}
		
		public static void closeBrowser(String object, String data,String sTestCaseName) throws Exception{
			try{
				Log.info("Closing the browser");
				
				driver.quit();
				
				//
				//driver.get().quit();
				//driver.get().close();
				//driver.remove();
				
				
			 }catch(Exception e){
				 Log.error("Not able to Close the Browser --- " + e.getMessage());
				 DriverScript.bResult = false;
	         	}
			}	
	
	
		
		
		
	}
	
	
	


