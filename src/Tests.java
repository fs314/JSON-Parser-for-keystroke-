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
		//test3 -412481395 //TEST3 REVERSE
		
		
		Tests ts = new Tests();
		//ts.testfromCondition(); 
		//ts.testFlagDelimiter(); 
		ts.reverseTest();
		
	}
	

	public void testfromCondition() 
	{
		JSONObject jsonObject = rKSF.parseObj("-412481395kspattern.json");
		System.out.println(sp.getSearchString(jsonObject)); 
		
		LinkedHashMap<String, ArrayList<JSONObject>> conditions = sp.fromCondition(jsonObject);
		for(String entry : conditions.keySet()) {
			System.out.println("FLAG: " + entry);
			System.out.println(sp.flagDelimiter(sp.getSearchString(jsonObject), entry));
			System.out.println(" ");
		}
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		
		JSONObject jsonObject1 = rKSF.parseObj("s0C-static.json");
		System.out.println(sp.getSearchString(jsonObject1)); 
		
		LinkedHashMap<String, ArrayList<JSONObject>> conditions1 = sp.fromCondition(jsonObject1);
		for(String entry : conditions1.keySet()) {
			System.out.println("FLAG: " + entry);
			System.out.println(sp.flagDelimiter(sp.getSearchString(jsonObject1), entry));
			System.out.println(" ");
		}
	} 
	
	public void testFlagDelimiter() 
	{
		JSONObject jsonObject = rKSF.parseObj("-412481395kspattern.json");
		System.out.println(sp.getSearchString(jsonObject)); 
		System.out.println(" ");
		
		for(int i=0; i< sp.flagsArray().size(); i++) 
		{
			
		    System.out.println(sp.flagsArray().get(i));
		    //System.out.println(sp.flagMinIndex(sp.getSearchString(jsonObject), sp.flagsArray().get(i)));
		    //System.out.println(sp.flagMaxIndex(sp.getSearchString(jsonObject), sp.flagsArray().get(i)));
		    System.out.println(sp.flagDelimiter(sp.getSearchString(jsonObject), sp.flagsArray().get(i)));
		    System.out.println(" ");
		}
		
	    
	}
	
	public void reverseTest () 
	{
		JSONObject jsonObject = rKSF.parseObj("TESTnoLastFlag.json");
		System.out.println("NEW JSON FILE: " + sp.getSearchString(jsonObject)); 
		System.out.println(" _____________________________________________________________________________");
		for(int i=0; i< sp.flagsArray().size(); i++) 
		{
			
		    System.out.println(sp.flagsArray().get(i));
		    System.out.println(sp.flagDelimiter(sp.getSearchString(jsonObject), sp.flagsArray().get(i)));
		    System.out.println(" ");
		}
		
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		
		System.out.println("OLD JSON FILE: " + sp.getSearchString(rKSF.parseObj("-412481395kspattern.json")));
		System.out.println(" _____________________________________________________________________________");
		for(int i=0; i< sp.flagsArray().size(); i++) 
		{
			
		    System.out.println(sp.flagsArray().get(i));
		    System.out.println(sp.flagDelimiter(sp.getSearchString(rKSF.parseObj("-412481395kspattern.json")), sp.flagsArray().get(i)));
		    System.out.println(" ");
		}
		
		//System.out.println(sp.flagDelimiter(sp.getSearchString(jsonObject), "HTRGWVX"));
	}
	   
}


