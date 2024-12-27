package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="Data")
	public String[][] getAllData() throws IOException
	{
		String path=System.getProperty("user.dir")+"//testData//UserData.xlsx";
		XLUtilities x1=new XLUtilities(path);
		
		int rownum=x1.getRowCount("Sheet1");
		int colcount=x1.getCellCount("Sheet1", 1);
		
		 if (rownum == 0 || colcount == 0) {
	            throw new IOException("Sheet1 is empty or has no valid data.");
	        }
		
		String apidata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum; i++)
		{
			for(int j=0; j<colcount; j++)
			{
				apidata[i-1][j]=x1.getCellData("Sheet1", i, j);
			}
		}
		
		return apidata;
		
	}
	
	
	@DataProvider(name="UserNames")
	public String[] getUserNames() throws IOException
	{
		String path=System.getProperty("user.dir")+"/testData/UserData.xlsx";
		XLUtilities x1=new XLUtilities(path);
		
		int rownum=x1.getRowCount("Sheet1");
		
		  if (rownum == 0) {
	            throw new IOException("Sheet1 is empty or has no valid data.");
	        }
		
		String apidata[]=new String[rownum];
		
		for(int i=1;i<=rownum; i++)
		{
			
				apidata[i-1]=x1.getCellData("Sheet1", i,1);
			
		}
		
		return apidata;
		
	}

}
