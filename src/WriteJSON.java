import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;


public class WriteJSON {
	
	ReadKSFile rKSF;
	SplitCondition sp;
	
	/**
	 * class constructor
	 * **/
	public WriteJSON() 
	{
		 rKSF = new ReadKSFile();
		 sp = new SplitCondition();
	}
	
	public static void main(String[] args) throws FileNotFoundException  
    { 
		WriteJSON wJS = new WriteJSON ();
		wJS.createKSFile();
    }
	
	public void createKSFile () throws FileNotFoundException
	{
		String fileName = "1462669319kspattern.json";         //Want program to iterate over files in directory and parse them one by one. 
		JSONObject jsonObject = rKSF.parseObj(fileName);
		
	    JSONObject newJo = new JSONObject();            //JSONObject containing all the participant's data 
	    
	    LinkedHashMap<String, ArrayList<JSONObject>> mp = sp.fromCondition(jsonObject);
	    ArrayList<String> conditionsFound = new ArrayList<String>();
	    
	    for(Map.Entry<String, ArrayList<JSONObject>> entry : mp.entrySet()) 
	    {
	    	conditionsFound.add(entry.getKey());
	    }
	    
	    for(int i=0; i<conditionsFound.size(); i++) 
	    {
	    	JSONArray ks = new JSONArray();             //Create a JSONArray ks for every condition. Contains a JSONObject for each keystroke associated to that condition.
	    	
	    	for(int a=0; a< mp.get(conditionsFound.get(i)).size(); a++) 
	    	{
	    		JSONObject jo1 = mp.get(conditionsFound.get(i)).get(a);  //iterate over array of JSONObjects associated to every condition (as split by SplitCondition.fromCondition)
	    		ks.add(jo1);                                            
	    	}
	    	newJo.put(conditionsFound.get(i), ks);                       //Add JSONArray ks to JSONObject newJo
	    }
	    
        PrintWriter pw = new PrintWriter("newJSON/JSONExNewFlags.json");   	 // writing JSON to file:"JSONExample.json" in another folder in cwd 
        pw.write(newJo.toJSONString()); 
          
        pw.flush(); 
        pw.close(); 
	}

}
