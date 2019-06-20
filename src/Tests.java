import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.*;

public class Tests 
{
	
	public static void main(String[] args) 
	{
	   String fileName = "1462669319kspattern.json";
	   JSONObject jsonObject = ReadKSFile.parseObj(fileName);
	
	   LinkedHashMap<String, ArrayList<JSONObject>> mp = SplitCondition.fromCondition(jsonObject);
	   
	   /**for(Map.Entry<String, ArrayList<JSONObject>> entry : mp.entrySet())
	   {
		   System.out.println(entry.get("condition0").get(1).toString());
	   } **/
	  
	   System.out.println(mp.get("condition0").get(1).toString());
	   
	}

	
	  
	  
	   
	   
	   
}


