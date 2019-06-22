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
	    //test1 1789050386
		//test2 1462669319
		//test3 -412481395
		Tests ts = new Tests();
		ts.testfromCondition(); 
	}
	

	public void testfromCondition() 
	{
		JSONObject jsonObject = rKSF.parseObj("-412481395kspattern.json");
		LinkedHashMap<String, ArrayList<JSONObject>> conditions = sp.fromCondition(jsonObject);
		
		for(Map.Entry<String, ArrayList<JSONObject>> entry : conditions.entrySet()) {
			System.out.println(entry.getKey());
		}
	} 
	   
}


