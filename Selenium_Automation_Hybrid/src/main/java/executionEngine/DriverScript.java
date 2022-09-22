package executionEngine;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.util.IOUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.Status;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;

public class DriverScript {

	public static Properties PDFAutomationOR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
		
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public String sTestCaseName; 
	
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	
	public String sModuleName;
	public static String sModuleTestStepsSheet;
	public static String sSkipTestStep;
	
	//Extent Report
	public static ExtentReports report;
	public static ExtentTest test;
	public ExtentHtmlReporter htmlReporter;
	
	public static String reportFilePath;
	public static String reportTCLevelFilePath;
	
	//New
	//private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public static WebDriver driver;


public DriverScript() throws NoSuchMethodException, SecurityException{
	actionKeywords = new ActionKeywords();
	method = actionKeywords.getClass().getMethods();	
}

public static void main(String[] args) throws Exception {
	ExcelUtils.setExcelFile(Constants.Path_TestData);
	DOMConfigurator.configure("log4j.xml");
	String Path_OR = Constants.Path_OR;
	FileInputStream fs = new FileInputStream(Path_OR);
	PDFAutomationOR= new Properties(System.getProperties());
	PDFAutomationOR.load(fs);
	
	DriverScript startEngine = new DriverScript();
	startEngine.execute_TestCase();
	
}

@BeforeClass
//@Test
private void execute_TestCase() throws Exception 
{
	
	int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases)-1;
	
	System.out.println("Total TC Count :"+iTotalTestCases);
	
	for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++)
	  {
		bResult = true;
		sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases).toString(); 
		
		sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases).toString();
		
		sModuleName = ExcelUtils.getCellData(iTestcase, Constants.Col_ModuleName,Constants.Sheet_TestCases).toString();
		
			if (sRunMode.equals("Yes")||sRunMode.equals("YES"))
			{
				
				String dateFileNameExt=new SimpleDateFormat("ddMMMYYYY").format(new Date()).toString();
				
				reportFilePath=System.getProperty("user.dir")+"\\Reports\\"+"ExtentReport_"+sTestCaseID+"_"+dateFileNameExt+".html";
				System.out.println("ExtentReport File Path :"+reportFilePath);
				
				//Extent Report Creation and Configuration
				ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter(reportFilePath);
				report=new ExtentReports();
				report.attachReporter(htmlReporter);
				htmlReporter.config().setDocumentTitle("Selenium Automation");
				htmlReporter.config().setReportName("SeleniumAutomationDemo");
				
				
				//Create Extent Report Based on TestCaseID
				test=report.createTest(sTestCaseID);
				
				System.out.println("TestCAse ID :"+sTestCaseID);
				System.out.println("TC Run Mode Execute or Not :"+sRunMode);
				Log.startTestCase(sTestCaseID);
				
				if(sModuleName.equalsIgnoreCase("TestStepsSNC"))
				{
					sModuleTestStepsSheet="TestStepsSNC";
					
					
				}
				else if(sModuleName.equalsIgnoreCase("SanitySteps"))
				{
					sModuleTestStepsSheet="SanityTCSteps";
				}
				else
				{
					sModuleTestStepsSheet="Test";
				}
				
				System.out.println("Step Sheet Name :"+sModuleTestStepsSheet);
				
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID,sModuleTestStepsSheet);
				System.out.println("iTestStep Start At :"+iTestStep);
				iTestLastStep = ExcelUtils.getTestStepsCount(sModuleTestStepsSheet, sTestCaseID, iTestStep);
				System.out.println("iTestLastStep Ends At :"+iTestLastStep);
				
				bResult=true;
				
				// For Module Test Step Sheet iteration
				for (;iTestStep<iTestLastStep;iTestStep++)
				{
				
					 sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,sModuleTestStepsSheet);	
					 System.out.println("iTestStep sActionKeywords :"+sActionKeyword);
					 
					 sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
					 System.out.println("iTestStep sPageObject :"+sPageObject);
					 
					 sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, sModuleTestStepsSheet);
					 System.out.println("iTestStep sData :"+sData);
					 
					 sTestCaseID= ExcelUtils.getCellData(iTestStep, Constants.Col_TestCaseID, sModuleTestStepsSheet).toString();
					 System.out.println("iTestStep TestCase Name :"+sTestCaseID);
					 
					 //Get the TestStep Skip Flag
					 sSkipTestStep= ExcelUtils.getCellData(iTestStep, Constants.Col_SkipTestStep, sModuleTestStepsSheet).toString();
					 //System.out.println("iTestStep sSkipTestStep Flag Status :"+sSkipTestStep);
				 
					 if(sSkipTestStep.equalsIgnoreCase("No")||sSkipTestStep.equalsIgnoreCase("NO"))
					 {
						 System.out.println("sSkipTestStep Flag Status :"+sSkipTestStep);
						 //Start Execution Of Keywords from TestStep Sheets
						 execute_Actions();
						 
						 if(bResult==false)
						  {
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
							Log.endTestCase(sTestCaseID);
							break;
						 }
					 }
					 
					 else
					 {
						 System.out.println("sSkipTestStep Flag Status :"+sSkipTestStep);
						 System.out.println("Steps Skipped For TC :"+sTestCaseID);
					 }
					 
			     } //TestStep For Loop End
				
			   if(bResult==true)
			     {
				   ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
				   Log.endTestCase(sTestCaseID);	
				 }	
			   
			}//End if of sRunMode sRunMode.equals("Yes")
			
	  }// End Of TestCase Counter for loop		
				
 }//execute TestCase Method End

@Test
private static void execute_Actions() throws Exception 
	 {
		
		for(int i=0;i<method.length;i++)
		{
			if(method[i].getName().equals(sActionKeyword))
			 {
				 System.out.println("method.length:"+method.length);
				 System.out.println("execute_Actions Method Name:"+method[i].getName());
				 
				 if(sPageObject==""&&sData=="")
					{
						 System.out.println("Inside Keyword Without Parameter:");
						 method[i].invoke(actionKeywords,"", "",sTestCaseID);
					}
				 else
					{
						System.out.println("Inside Keyword With Parameter:");
						 //method[i].invoke(actionKeywords,"", sData,sTestCaseID);
						 method[i].invoke(actionKeywords,sPageObject,sData,sTestCaseID);
					}
				 
					System.out.println("execute_Actions() bResult Inside method:"+bResult);
					 
					if(bResult==true) //TestCase Step PASS Update Result in Sheet
					{
						ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, sModuleTestStepsSheet);	
						report.flush();
						break;
					}
					else //TestCase Step FAIL Update Result in Sheet
					{
						
						// Extent Report Log With Screenshot
						test.log(Status.FAIL, "Test Failed -"+sActionKeyword+", Data:"+sData,MediaEntityBuilder.createScreenCaptureFromBase64String(getFullPageScreenshot(driver,sTestCaseID)).build());
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult,sModuleTestStepsSheet);
						ActionKeywords.closeBrowser("","","");
						report.flush();
						break;
						
					} //end if (bResult==true
						
			 } //end if method[i].getName()
		
		} // end For loop method.length
		
  }	// End execute_Actions()	

//Take Screenshot only WebPage Without URL
public static String getScreenShot(WebDriver driver,String screenshotName) throws Exception
  {
	String dateName=new SimpleDateFormat("MMddYYYYhhmmss").format(new Date()).toString();
	
	//***Convert web driver object to TakeScreenshot
	//TakesScreenshot scrShot =((TakesScreenshot)driver);
	//***Call getScreenshotAs method to create image file
	//File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
	
	String scrBase64=((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	File source=OutputType.FILE.convertFromBase64Png(scrBase64);
	String destination=System.getProperty("user.dir")+"/FailedTestsScreenshots/"+screenshotName+"_"+dateName+".png";
	//Move image file to new destination
	File finalDestination=new File(destination);
	//Copy file at destination
	FileUtils.copyFile(source, finalDestination);
	return 	destination;
	
	}

//Take Screenshot Full Desktop WebPage With URL - Note- Webpage Sould Not be minimized.
public static String getFullPageScreenshot(WebDriver driver,String screenshotName) throws Exception
{
	String dateName=new SimpleDateFormat("MMddYYYYhhmmss").format(new Date()).toString();
	Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
	Rectangle screenRectangle=new Rectangle(screenSize);
	Robot robot=new Robot();
	BufferedImage image=robot.createScreenCapture(screenRectangle);
    String destination=System.getProperty("user.dir")+"/FailedTestsScreenshots/"+screenshotName+"_"+dateName+".png";
    ImageIO.write(image,"png", new File(destination));
    
    String base64="";
    InputStream iStreamReader=new FileInputStream(destination);
    byte[] imageBytes=IOUtils.toByteArray(iStreamReader);
    base64=Base64.getEncoder().encodeToString(imageBytes);
    
    System.out.println("base64:"+base64);
    return base64;
}

public static String DecodePassword(String encodedPassword) throws Exception
{
  byte[] decodeBytes=Base64.getDecoder().decode(encodedPassword);
  String decodedPassword=new String(decodeBytes);
  return decodedPassword;
}
	
} //DriverScript End
