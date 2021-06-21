package Test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ExcelExportFileandIO.ReadExcelFile;

public class HybridTest extends MyExtentReport {
	
	    
	    
	    
	    @Test(dataProvider="hybridData")
		public void testcases(String testcaseName, String keyword, String objectName, String objectType, String value,
				String Description) throws Exception {
	        // TODO Auto-generated method stub
	    	
	    	if(testcaseName!=null && testcaseName.length()!=0)
	    	{
	    		try
	    		{
	    		test=extent.createTest(testcaseName);
	    		}
	    		catch(Exception e)
		   		{
		   			 e.printStackTrace();
		   			 Assert.fail(testcaseName+" Failed");
		   	    }
	    	}
	    	
	    	operation.perform(allObjects, keyword, objectName, objectType, value);
	    
	    }
	    @DataProvider(name="hybridData")
	    
	    public Object[][] getDataFromDataprovider() throws IOException
	    {
	    
	    ReadExcelFile file = new ReadExcelFile();
	    
		String filepath = System.getProperty("user.dir");
	    
		Object[][] tabArray = file.readExcel(filepath, "Test_Data.xlsx", "wordpress");
	    
	    return tabArray;
   
	 }
	}
	    
	    
	    
	


