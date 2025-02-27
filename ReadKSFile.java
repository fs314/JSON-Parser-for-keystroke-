import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.util.*;

public class ReadKSFile {

	/**
	 * extracts primary codes(ascii) of each keystroke data and returns them into an ArrayList
	 * @param ArrayList<JSONObject> containing the JSONObject associated to each keystroke in the original JSON file
	 * @return ArrayList<Long> containing the primaryCode value of each keystroke in the original JSON file
	 * **/
   public ArrayList<Long>  getLetterCodes(ArrayList<JSONObject> ksDataList)
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
	public Long  extractPrimaryCode(JSONObject jo1)
    {
    	Long primaryCode = (Long) jo1.get("primaryCode");
    	return primaryCode;
	}


	 /**
	  * extracts KeyPressDelay value for one keystroke
	  * @param JSONObject corresponding to one keystroke
	  * @return double KeyPressDelay value
	  * **/
    public double extractKeyPressDelay(JSONObject jo1)
    {
    	double keyPressDelay = (double) jo1.get("KeyPressDelay");
    	return keyPressDelay;
    }


    /**
	 * extracts vectorCoord value for one keystroke
	 * @param JSONObject corresponding to one keystroke
	 * @return String vectorCoord value
	 * **/
	public String extractVectorCoord(JSONObject jo1)
	{
		String vectorCoord = (String) jo1.get("VectorCoord");
    	return vectorCoord;
	}

	/**
     * loops over the first layer of keystroke array extracting all JSONObject related to one keystroke
     * and organizing them all together into an ArrayList
     * @param JSONObject original JSONObject containing all keystroke data
     * @return ArrayList of JSONObjects related to each keystroke
     * **/
	@SuppressWarnings("unchecked")
	// otherwise line jo1.put("keyLabel", extractKsLabel(jsonObject).get(i));"
	//raises warning of type safety (References to generic type HashMap<K,V> should be parameterized).
   	public ArrayList<JSONObject> extractKsData(JSONObject jsonObject)
   	{
   		ArrayList<JSONObject> ksDataList = new ArrayList<JSONObject>();
   		ArrayList<JSONArray> ksArray = getKsArray(jsonObject);

   		for (int i=0; i<ksArray.size(); i++)
    	{
   			JSONArray ja = ksArray.get(i);

   			Iterator<?> itr1 = ja.iterator();
   			while (itr1.hasNext())
   			 {
   				JSONObject jo1 = (JSONObject) itr1.next();
   			    //put keyLabel value into each JSONObject as part of keystroke data
   				jo1.put("keyLabel", extractKsLabel(jsonObject).get(i));
   				ksDataList.add(jo1);
   			 }
   		}
   		return ksDataList;
   	}

   	/**
     * retrieves JSONArrays in the original JSONObject and organises them into an ArrayList
     * @param JSONObject original JSONObject containing all keystroke data
     * @return ArrayList of first layer of JSONArray in Keystroke data file
     * **/
    public ArrayList<JSONArray> getKsArray(JSONObject jsonObject)
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
     * @param JSONObject original JSONObject containing all keystroke data
     * @return ArrayList of keystroke labels
     * **/

  	public ArrayList<String> extractKsLabel(JSONObject jsonObject)
  	{
  		ArrayList<String> ksLabels = new ArrayList<String>();
  		Map<?,?> keys = (Map<?,?>) jsonObject;

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
	public JSONObject parseObj (String fileName)
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
