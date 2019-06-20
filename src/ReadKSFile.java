import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.util.*; 

public class ReadKSFile {

	public static void main(String[] args) {}
	
	
	//SEEMS NOT NEEDED
	/**
	 * extracts data of type Long ("PressureOnPress", "primaryCode", "XOnPress", "YOnPress", "PressureOnRelease", "XOnRelease", "YOnRelease") 
	 * of each keystroke data and organises them into an ArrayList of HashMap<String, Long>, where String represents the kind of data extracted and 
	 * Long represents its value. 
	 * @param ArrayList<JSONObject> containing the JSONObject associated to each keystroke in the original JSON file
	 * @return ArrayList<HashMap<String, Long>> 
	 * **/
	/**
	public static ArrayList<HashMap<String, Long>> extractLongData(ArrayList<JSONObject> ksDataList) 
	{
		ArrayList<HashMap<String, Long>> data = new ArrayList<HashMap<String, Long>>();
		ArrayList<String> toExtract = new ArrayList<String>();
		toExtract.add("PressureOnPress");                       //Maybe put into ENUMS too
		toExtract.add("primaryCode");
		toExtract.add("XOnPress");
		toExtract.add("YOnPress");
		toExtract.add("PressureOnRelease");
		toExtract.add("XOnRelease");
		toExtract.add("YOnRelease");

		for (int i=0; i<ksDataList.size(); i++) 
    	{
			HashMap<String, Long> values = new HashMap<String, Long>();  
			JSONObject jo1 = ksDataList.get(i);
			for(int a=0; a<toExtract.size(); a++) 
		    {
		    	Long value = (Long) jo1.get(toExtract.get(a)); 
		    	values.put(toExtract.get(a), value);	
		    	data.add(values);
		    }
    	}	
	return data; 
	} **/
	
	
	/**
	 * extracts primary codes(ascii) of each keystroke data and returns them into an ArrayList
	 * @param ArrayList<JSONObject> containing the JSONObject associated to each keystroke in the original JSON file
	 * @return ArrayList<Long> containing the primaryCode value of each keystroke in the original JSON file
	 * **/
   public static ArrayList<Long>  getLetterCodes(ArrayList<JSONObject> ksDataList)                      
    {
    	ArrayList<Long> letterCodes = new ArrayList<Long>();	
    	
    	for (int i=0; i<ksDataList.size(); i++) 
    	{
    		letterCodes.add(extractPrimaryCode(ksDataList.get(i)));
    	}
		return letterCodes;	
	} 
	
   /**
    * extracts primaryCode value for one keystroke
    * @param JSONObject corresponding to one keystroke
    * @return Long primaryCode value
    * **/
	public static Long  extractPrimaryCode(JSONObject jo1)                      
    {  
    	Long primaryCode = (Long) jo1.get("primaryCode"); 
    	return primaryCode;	
	}
	
	
	 /**
	  * extracts KeyPressDelay value for one keystroke
	  * @param JSONObject corresponding to one keystroke
	  * @return double KeyPressDelay value
	  * **/
    public static double extractKeyPressDelay(JSONObject jo1) 
    {
    	double keyPressDelay = (double) jo1.get("KeyPressDelay");
    	return keyPressDelay;
    } 
	
    
    /**
	 * extracts vectorCoord value for one keystroke
	 * @param JSONObject corresponding to one keystroke
	 * @return String vectorCoord value
	 * **/
	public static String extractVectorCoord(JSONObject jo1) 
	{
		String vectorCoord = (String) jo1.get("VectorCoord");
    	return vectorCoord;
	} 
	
	/**
     * loops over the first layer of keystroke array extracting all JSONObject related to one keystroke 
     * and organizing them all together into an ArrayList
     * @param the list of initial JSONArray found in the JSON file to parse
     * @return ArrayList of JSONObjects related to each keystroke
     * **/
   	public static ArrayList<JSONObject> extractKsData(ArrayList<JSONArray> ksArray)  
   	{
   		ArrayList<JSONObject> ksDataList = new ArrayList<JSONObject>();

   		for (int i=0; i<ksArray.size(); i++) 
    	{
    		JSONArray ja = ksArray.get(i);
   		
   			Iterator itr1 = ja.iterator(); 	
   			while (itr1.hasNext())
   			 {
   				JSONObject jo1 = (JSONObject) itr1.next();
   				ksDataList.add(jo1);
   			 }
   		}
   		return ksDataList;
   	}
    
   	/**
     * loops over first layer of arrays in the JSON file and extracts their label. 
     * @param the JSON file from which to extract the Keystroke data
     * @return ArrayList of first layer of JSONArray in Keystroke data file
     * **/
    public static ArrayList<JSONArray> getKsArray(JSONObject jsonObject) 
	{
		ArrayList<JSONArray> ksArray = new ArrayList<JSONArray>();
		
		ArrayList<String> ksLabels = extractKsLabel(jsonObject);
		for (int i=0; i<ksLabels.size(); i++) 
		{
			ksArray.add(((JSONArray) jsonObject.get(ksLabels.get(i))));  
		}
		return ksArray;
	}
    
    /**
     * loops over first layer of arrays in the JSON file and extracts their label. 
     * @param the initial JSON file 
     * @return ArrayList of labels 
     * **/
  	public static ArrayList<String> extractKsLabel(JSONObject jsonObject)                          
  	{
  		ArrayList<String> ksLabels = new ArrayList<String>();
  		Map keys = (Map) jsonObject;
  		
  		for  (Object key: keys.keySet())     
  		{
  			ksLabels.add(key.toString());
  		}
  		return ksLabels;
  	}
    
  	/**
     * Parses the file to read and works it as a JSONObject
     * @param String representing the name of the JSON file to parse 
     * @return Parsed file as JSONObject
     * **/
	public static JSONObject parseObj (String fileName) 
	{
		JSONParser parser = new JSONParser();
	    JSONObject jsonObject = new JSONObject();
		try 
		{
			Object obj = parser.parse(new FileReader(fileName));   
			jsonObject = (JSONObject) obj;                         
		} 
		catch(FileNotFoundException e) {e.printStackTrace();}
		catch(IOException e) {e.printStackTrace();}
		catch(ParseException e) {e.printStackTrace();}
		catch(Exception e) {e.printStackTrace();}
		    
		return jsonObject;
	}
		
	
		
}
