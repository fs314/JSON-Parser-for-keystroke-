import java.io.PrintWriter;
import java.io.*; 
import java.io.FileNotFoundException;

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
	
	/**
	 * creates a new directory for each participant's data and stores the original keystroke data file in it 
	 * as well as a parsed JSON file where keystroke data is divided by condition. 
	 * Original keystroke label is preserved as a JSONObject within new main JSON file. 
	 * **/
	public void createKSFile () throws FileNotFoundException
	{
		for (int i=0; i<filesForFolder().size(); i++) 
		{
			JSONObject jsonObject = rKSF.parseObj(filesForFolder().get(i));
			JSONObject newJo = new JSONObject();
			String ppsNumber = "s"+ i;
			
			LinkedHashMap<String, ArrayList<JSONObject>> mp = sp.fromCondition(jsonObject);
		    
			for(String key : mp.keySet()) 
			{
				//Create a JSONArray ks for every condition. Contains a JSONObject for each keystroke associated to that condition.
				JSONArray ks = new JSONArray(); 
				
				for(int a=0; a< mp.get(key).size(); a++) 
				{
					//iterate over array of JSONObjects associated to every condition (as split by SplitCondition.fromCondition)
					JSONObject jo1 = mp.get(key).get(a);  
					ks.add(jo1);                                            
			    }
				//Add JSONArray ks to JSONObject newJo
		    	newJo.put(key, ks); 
			}
			
			printFiles(makePpsFolder(ppsNumber) + "static", newJo); 
			printFiles(makePpsFolder(ppsNumber) + "original", jsonObject);
	    }
	}
	
	
	/**
	 * gets name of every file in a selected folder 
	 * @return ArrayList<String> containing the names of all the files within a selected folder
	 * **/
	public ArrayList<String> filesForFolder() 
	{
		ArrayList<String> filename = new ArrayList<String>();
		
		File folder = new File("X:\\home\\Eclipse - workspace\\OriginalKSFiles");
	    File [] listOfFiles = folder.listFiles();
	    
	    for (File file : listOfFiles) 
	    {
	    	if (file.isFile()) 
	    	{
	    		filename.add(file.getName().toString());
	    	}
	    }
	    return filename;
	} 
	
	/**
	 * creates a new JSON file and saves it in a specific folder
	 * @param String representing the name of the new file and JSONObject for the JSON file to be saved
	 * **/
	public void printFiles(String newFilename, JSONObject newJo) throws FileNotFoundException  
	{  
		  String newPath = (newFilename + ".json");	  
		  PrintWriter pw = new PrintWriter(newPath);  
		  pw.write(newJo.toJSONString()); 
	          
	        pw.flush(); 
	        pw.close();
	}
	
	/**
	 * creates a new folder to store the participant's data 
	 * @param String representing the participant's number
	 * @return String representing the new folder's absolute path
	 * **/
	public String makePpsFolder(String ppsNumber) 
	{ 
		File dir = new File("X:\\home\\Eclipse - workspace\\ParsedKSFiles\\" + ppsNumber);
		dir.mkdir();
		
		String newFilename = dir.getAbsolutePath() + "\\" + ppsNumber + "-"; 
		return newFilename;
	}

}
