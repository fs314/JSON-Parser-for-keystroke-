import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;


public class WriteJSON {
	
	
	
	public static void main(String[] args) throws FileNotFoundException  
    { 
		String fileName = "1462669319kspattern.json";
		JSONObject jsonObject = ReadKSFile.parseObj(fileName);
		
	    JSONObject newJo = new JSONObject();            //JSONObject containing all the participant's data 
	    
	    LinkedHashMap<String, ArrayList<JSONObject>> mp = SplitCondition.fromCondition(jsonObject);
	    ArrayList<String> conditionsFound = new ArrayList<String>();
	    
	    for(Map.Entry<String, ArrayList<JSONObject>> entry : mp.entrySet()) 
	    {
	    	conditionsFound.add(entry.getKey());
	    }
	    
	    for(int i=0; i<conditionsFound.size(); i++) 
	    {
	    	JSONArray ks = new JSONArray();             //Create a JSONArray ks for every condition. Contains a JSONObject for each keystroke associated to that condition.
	    	
	    	for(int a=0; a< mp.get(conditionsFound.get(1)).size(); a++)  //TEMP CHANGE FROM get(i) TO get(1)
	    	{
	    		JSONObject jo1 = mp.get(conditionsFound.get(1)).get(a);  //iterate over array of JSONObjects associated to every condition (as split by SplitCondition.fromCondition)
	    		ks.add(jo1);                                            
	    	}
	    	newJo.put(conditionsFound.get(i), ks);                       //Add JSONArray ks to JSONObject newJo
	    }
	    
        PrintWriter pw = new PrintWriter("newJSON/JSONExample.json");   	 // writing JSON to file:"JSONExample.json" in cwd 
        pw.write(newJo.toJSONString()); 
          
        pw.flush(); 
        pw.close(); 
   
    
    }

}
