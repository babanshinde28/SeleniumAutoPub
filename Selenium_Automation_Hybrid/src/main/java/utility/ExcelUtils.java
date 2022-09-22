package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.Constants;
import executionEngine.DriverScript;

public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static org.apache.poi.ss.usermodel.Cell Cell;
    private static XSSFRow Row;
    static DataFormatter fmt= new DataFormatter();
    	    
    
public static void setExcelFile(String Path) throws Exception {
	
	try {
		
		 System.out.println("Excel Path inside setExcelFile :"+Path);
         FileInputStream ExcelFile = new FileInputStream(Path);
         
         ExcelWBook = new XSSFWorkbook(ExcelFile);
      
        
	} catch (Exception e){
		Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
		DriverScript.bResult = false;
    	}
	}	
	
public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
    try{
    	
    	DataFormatter formatter=new DataFormatter();
    	
    	ExcelWSheet = ExcelWBook.getSheet(SheetName);
       	Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
       	
       // String CellData = Cell.getStringCellValue();
       // return CellData;
       	
       	if(Cell.getCellType()==CellType.STRING)
       	{
       		String strValue=formatter.formatCellValue(Cell);
       		System.out.println("getCellData :"+strValue);
       		return strValue;
       	}
       	else if(Cell.getCellType()==CellType.NUMERIC)
       	{
       		FormulaEvaluator evaluator=ExcelWBook.getCreationHelper().createFormulaEvaluator();
       		String strValue=formatter.formatCellValue(Cell,evaluator);
       		System.out.println("getCellData :"+strValue);
       		return strValue;
       	}
       	else if(Cell.getCellType()==CellType.FORMULA)
       	{
       		FormulaEvaluator evaluator=ExcelWBook.getCreationHelper().createFormulaEvaluator();
       		String strValue=formatter.formatCellValue(Cell,evaluator);
       		System.out.println("getCellData :"+strValue);
       		return strValue;
       	}
       	else if(Cell.getCellType()==CellType.BLANK)
       		
       	{
       		return "";
       	}
       	else
       	{
       		System.out.println("getCellData :"+String.valueOf(Cell.getBooleanCellValue()));
       		return String.valueOf(Cell.getBooleanCellValue());
       	}
        
     }catch (Exception e){
         Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
         DriverScript.bResult = false;
         return"";
         }
     }

public static int getTestCaseRowNumber(String SheetName,String sTestCaseName,int ColNum) throws Exception{
	
	int iRowNum=1;
	
    try{
    	int rowcount=ExcelUtils.getRowCount(SheetName);
    	
    	for(iRowNum=1;iRowNum<rowcount;iRowNum++)
    	{
    		if(ExcelUtils.getCellData(iRowNum, ColNum, SheetName).equals(sTestCaseName))
    		{
    			break;
    		}
    	}
    	
    
    }catch (Exception e){
        Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
        DriverScript.bResult = false;
       
        }
    return iRowNum;
    }
	
//Read Excel Data Based On Coloumn Name
public static String getCellDataByColumnName(String SheetName,String sTestCaseName,String colName) throws Exception{
	
	int col_Number=0;
	int iRowNum=0;
	int iExpectedColNum=0;
	int iExpectedTestCaseRowNum=0;
	int iColNum;
	
    try{
    	ExcelWSheet = ExcelWBook.getSheet(SheetName);
    	int iTotalRowCount=ExcelWSheet.getLastRowNum();
    	Row= ExcelWSheet.getRow(0);
    	int colCount=Row.getLastCellNum();
    	
    	for(iColNum=0;iColNum<colCount+1;iColNum++)
    	{
    		Cell cell=ExcelWSheet.getRow(0).getCell(iColNum);
    		String ColCellData=Cell.getStringCellValue().toString();
    		if(ColCellData.equalsIgnoreCase((colName).trim()))
    		{
    			iExpectedColNum=(int)iColNum;
    			System.out.println("Col Name Found at ColNumber :"+iExpectedColNum);
    			break;
    		}
    	}
    	
    	for(iRowNum=1;iRowNum<iTotalRowCount;iRowNum++)
    	{
    		Cell cell=ExcelWSheet.getRow(iRowNum).getCell(0);
    		String ColTestNameCellData=Cell.getStringCellValue().toString();
    		if(ColTestNameCellData.equalsIgnoreCase((sTestCaseName).trim()))
    		{
    			iExpectedTestCaseRowNum=(int)iRowNum;
    			System.out.println("TestCase Name Fount At Row Num :"+iExpectedTestCaseRowNum);
    			break;
    			
    		}
    	}
    	
    	//
    	String dataSearched=getCellData(iExpectedTestCaseRowNum,iExpectedColNum,SheetName);
    	System.out.println("Final Data :"+dataSearched);
    	return dataSearched;
    	
    }catch (Exception e){
        Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
        DriverScript.bResult = false;
        return "Row"+iRowNum+" or Column "+col_Number+"does Not Exist in Excel.";
        }
    }

public static int getRowCount(String SheetName){
	int iNumber=0;
	
	try {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		iNumber=ExcelWSheet.getLastRowNum()+1;
		
	} catch (Exception e){
		System.out.println("SheetName :"+e.toString());
		Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
		DriverScript.bResult = false;
		}
	return iNumber;
	}
	
public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception{
	int iRowNum=0;	
	try {
	    //ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int rowCount = ExcelUtils.getRowCount(SheetName);
		for (; iRowNum<rowCount; iRowNum++){
			if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName)){
				break;
			}
		}       			
	} catch (Exception e){
		Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
		DriverScript.bResult = false;
		}
	return iRowNum;
	}

public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception{
	try {
		for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++){
			if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))){
				int number = i;
				return number;      				
				}
			}
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int number=ExcelWSheet.getLastRowNum()+1;
		return number;
	} catch (Exception e){
		Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
		DriverScript.bResult = false;
		return 0;
    }
}

//@SuppressWarnings("static-access")
public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
       try{
    	   
    	   ExcelWSheet = ExcelWBook.getSheet(SheetName);
           Row  = ExcelWSheet.getRow(RowNum);
          // Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
           Cell = Row.getCell(ColNum,org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
           
           if (Cell == null) {
        	   Cell = Row.createCell(ColNum);
        	   Cell.setCellValue(Result);
            } else {
                Cell.setCellValue(Result);
            }
             FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
             ExcelWBook.write(fileOut);
             //fileOut.flush();
             fileOut.close();
             ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
         }catch(Exception e){
        	 DriverScript.bResult = false;
  
         }
    }
	
	
	
	
	
}
