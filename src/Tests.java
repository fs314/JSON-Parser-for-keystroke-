import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.*;

public class Tests 
{
	ReadKSFile rKSF;
	SplitCondition sp;
	WriteJSON wjs;
	
	/**
	 * class constructor
	 * **/
	public Tests () 
	{
		 rKSF = new ReadKSFile();
		 sp = new SplitCondition();
		 wjs = new WriteJSON();
	}
	
	public static void main(String[] args) 
	{
	    //test1 1789050386
		//test2 1462669319
		//test3 -412481395
		Tests ts = new Tests();
		//ts.testfromCondition(); 
		//ts.testfileForFolder();
		ts.testFileLoader();
	}
	

	public void testfromCondition() 
	{
		JSONObject jsonObject = rKSF.parseObj("-412481395kspattern.json");
		LinkedHashMap<String, ArrayList<JSONObject>> conditions = sp.fromCondition(jsonObject);
		
		for(Map.Entry<String, ArrayList<JSONObject>> entry : conditions.entrySet()) {
			System.out.println(entry.getKey());
		}
	} 
	
	public void testfileForFolder() 
	{
		wjs.filesForFolder(); 
	}
	
	public void testFileLoader ()  //WORKS
	{
		wjs.filesForFolder();
		for (int i=0; i<wjs.filesForFolder().size(); i++) 
		{
			JSONObject jsonObject = rKSF.parseObj(wjs.filesForFolder().get(i));
			
			
			String searchstring = sp.getSearchString(jsonObject);
		
			for(int a=0; i<sp.flagsArray().size(); a++) 
			{
				System.out.println(" ");
			    System.out.println(sp.flagDelimiter(searchstring, sp.flagsArray().get(a)));
			}
			
			
		}
	}
	   
}


