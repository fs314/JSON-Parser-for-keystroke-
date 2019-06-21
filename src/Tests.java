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
		
		/**
		ts.testminIndexDelimiter();
		System.out.println("___________________________________" );
		ts.testflagDelimiter();
		**/
		
		ts.testflagOccurence(); 
	}
	
	public void testFlagMinIndex() 
    {
	   //test1 1789050386
	   //test2 1462669319
	   //test3 -412481395
	   String fileName = "-412481395kspattern.json";
	   JSONObject jsonObject = rKSF.parseObj(fileName);
	
	   String searchString = sp.intoString(sp.pCodeConverter(rKSF.getLetterCodes(rKSF.extractKsData(jsonObject))));
	
	   for(int i=0; i< sp.flagsArray().size(); i++)
	   {
		 System.out.println(sp.flagsArray().get(i) + " " + sp.flagMinIndex(searchString,  sp.flagsArray().get(i)));  

	   }  
    }
	
	public void testgetSearchString() 
	{
		String fileName = "-412481395kspattern.json";
		JSONObject jsonObject = rKSF.parseObj(fileName);
		System.out.println(sp.getSearchString(jsonObject));
	}
	
	public void testminIndexDelimiter() 
	{
		String fileName = "-412481395kspattern.json";
		JSONObject jsonObject = rKSF.parseObj(fileName);
		
		for (int i=0; i<sp.minIndexDelimiter(sp.getSearchString(jsonObject)).size(); i++) 
		{
			System.out.println(sp.minIndexDelimiter(sp.getSearchString(jsonObject)).get(i));
			System.out.println(" ");
		}
	}
	
	public void testflagDelimiter() 
	{
		String fileName = "-412481395kspattern.json";
		JSONObject jsonObject = rKSF.parseObj(fileName);
		
		for (int i=0; i<sp.flagDelimiter(sp.getSearchString(jsonObject)).size(); i++) 
		{
			System.out.println("CONDITION" + i);
			System.out.println(" ");
			System.out.println(sp.flagDelimiter(sp.getSearchString(jsonObject)).get(i));
		}
	}
	
	public void testflagOccurence() 
	{
		JSONObject jsonObject = rKSF.parseObj("-412481395kspattern.json");
		
		for (int i=0; i< sp. flagsArray().size(); i++) 
		{
			System.out.println(sp.flagsArray().get(i));	
			System.out.println(sp.flagsOccurence(sp.getSearchString(jsonObject), sp.flagsArray().get(i)));
			System.out.println(" ");	
		}
	
	}
	   
}


