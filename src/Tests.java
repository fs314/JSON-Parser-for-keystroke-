import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.*;

public class Tests 
{
	ReadKSFile rKSF;
	SplitCondition sp;
	
	/**
	 * class constructor
	 * **/
	public Tests () 
	{
		 rKSF = new ReadKSFile();
		 sp = new SplitCondition();
	}
	
	public static void main(String[] args) 
	{
		Tests ts = new Tests();
		ts.testFlagMinIndex();
	}
	
	public void testFlagMinIndex() 
    {
	   //test1 1789050386
	   //test2 1462669319
	   //test3 -412481395
	   String fileName = "1462669319kspattern.json";
	   JSONObject jsonObject = rKSF.parseObj(fileName);
	
	   String searchString = sp.intoString(sp.pCodeConverter(rKSF.getLetterCodes(rKSF.extractKsData(jsonObject))));
	
	   for(int i=0; i< sp.flagsArray().size(); i++)
	   {
		 System.out.println(sp.flagsArray().get(i) + " " + sp.flagMinIndex(searchString,  sp.flagsArray().get(i)));  

	   }  
    }   
	   
}


