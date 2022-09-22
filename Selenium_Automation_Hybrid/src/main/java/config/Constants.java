package config;

import org.openqa.selenium.WebDriver;

public class Constants {
	
	//System Variables
		public static final String URL = "https://www.makemytrip.com/";
		static String workingDir=System.getProperty("user.dir");
		
		//public static final String Path_TestData = "C://Users//baban//eclipse-workspace//Selenium_Automation_Hybrid//src//main//java//dataEngine//TestDataEngine.xlsx";
		//public static final String Path_OR = "C://Users//baban//eclipse-workspace//Selenium_Automation_Hybrid//src//main//java//config//PDFAutomationOR.properties";
		
		public static final String Path_TestData = "C://DataSheets//TestDataEngine.xlsx";
		public static final String Path_OR = "C://Users//baban//eclipse-workspace//Selenium_Automation_Hybrid//src//main//java//config//PDFAutomationOR.properties";
		
		public static final String File_TestData = "TestDataEngine.xlsx";
		//public static final String File_TestData = "DataEngine1.xlsx";
		public static final String KEYWORD_FAIL = "FAIL";
		public static final String KEYWORD_PASS = "PASS";
						
		//TestCase Suite Data Sheet Column Numbers
		public static final int Col_TestCaseID = 0;	
		public static final int Col_ModuleName = 1;
		public static final int Col_RunMode =2 ;
		public static final int Col_Result =3 ;
		
		//TestSteps Data Sheet Column Numbers
		public static final int Col_TestScenarioID =1 ;
		public static final int Col_SkipTestStep =3 ;
		public static final int Col_PageObject =4 ;
		public static final int Col_ActionKeyword =5 ;
				
		public static final int Col_DataSet =6 ;
		public static final int Col_TestStepResult =7 ;
			
		// Data Engine Excel sheets
		public static final String Sheet_TestSteps = "TestSteps";
		public static final String Sheet_TestCases = "TestCases";
		
		public static final String CurrentBroweser = "Chrome";
		public static final String CurrentEnv = "https://www.makemytrip.com/";
		
		
		
		
		
		

}
