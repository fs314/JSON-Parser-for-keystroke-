import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;

import org.json.simple.JSONObject;

public class SplitCondition {

	public static void main(String[] args) {}
	
	
	
	/**
	 * Parses original input JSONObject and splits all the keystroke data according to the condition they are generated in. 
	 * @param  JSONObject original JSONObject containing all keystroke data
	 * @return LinkedHashMap<String, ArrayList<JSONObject>> where the String represents the condition name 
	 * and the ArrayList<JSONObject> all the JSONObject of each keystroke associated to that condition
	 * **/
	
	public static LinkedHashMap<String, ArrayList<JSONObject>> fromCondition(JSONObject jsonObject) 
	{
		LinkedHashMap<String, ArrayList<JSONObject>> conditions = new LinkedHashMap<String, ArrayList<JSONObject>>(8); 
		
		ArrayList<JSONObject> ksData = ReadKSFile.extractKsData(jsonObject); 
		
		for (int i=0; i<conditionDelimiter(jsonObject).get("startIndex").size(); i++)                
		{
			ArrayList<JSONObject> conditionedKsData = new ArrayList<JSONObject>();
			
			String conditionName = "condition" + i;
			for (int index= conditionDelimiter(jsonObject).get("startIndex").get(i); index<conditionDelimiter(jsonObject).get("endIndex").get(i); index++)
			{
				//value of conditionedKsData represents all the elements in ksData that go from startIndex(i) at endIndex(i), via .get(i). 
				conditionedKsData.add(ksData.get(index));
			}
		    conditions.put(conditionName, conditionedKsData); 
		}
	return conditions;
	} 
	
	
	/**
	 * finds start index and end index of each condition string and returns them into one ArrayList<Integer> each
	 * @param  JSONObject original JSONObject containing all keystroke data
	 * @return HashMap<String, ArrayList<Integer>> containing two separate lists of indexes, 
	 * one for the start index of each condition string and one for the end index of each condition string
	 * **/
	public static LinkedHashMap<String, ArrayList<Integer>> conditionDelimiter (JSONObject jsonObject) 
	{
		LinkedHashMap<String, ArrayList<Integer>> startToEnd = new LinkedHashMap<String, ArrayList<Integer>>();
		ArrayList<Integer> startIndex = new ArrayList<Integer>();
		ArrayList<Integer> endIndex = new ArrayList<Integer>();
		
		for(int i=0; i<indexByCondition(jsonObject).size(); i++)  
		{
			startIndex.add(indexByCondition(jsonObject).get(i));
			
			int b= i+1; 
			if(b<indexByCondition(jsonObject).size()) 
			{
				endIndex.add(indexByCondition(jsonObject).get(b));
			} else {
				endIndex.add(getSearchString(jsonObject).length() - 1);
			}
		}
		
		startToEnd.put("startIndex", startIndex);
		startToEnd.put("endIndex", endIndex);
		return startToEnd;
	}
	
	
	/**
	 * finds minimum index at which each condition string occurs within a given search string and  
	 * stores the index within a ArrayList<Integer>
	 * @param  JSONObject original JSONObject containing all keystroke data
	 * @return ArrayList<Integer> containing the minimum index of occurrence of each condition string
	 * **/
	public static ArrayList<Integer> indexByCondition(JSONObject jsonObject) 
	{
		ArrayList<Integer> splitAt= new ArrayList<Integer>();
		
		String data = getSearchString(jsonObject);
		for(int i=0; i < minIndexDelimiter(data).size(); i++) 
		{
			splitAt.add(flagMinIndex(data, minIndexDelimiter(data).get(i)));
		}
		return splitAt;
	}
	
	
	/**
	 * Splits a given string into substrings going from the minimum index 
	 * at which one flag has been found to the minimum index 
	 * at which the following flag has been found, thus delimiting data associated to each condition. 
	 * @param String for the string to be searched 
	 * @return ArrayList<String> containing all the newly obtained substrings
	 * **/
	public static ArrayList<String> minIndexDelimiter (String searchString) 
	{
	  ArrayList<String> flags = flagsArray();
	  ArrayList<String> conditionString = new ArrayList<String>();
	  int endIndex = 0;
	  int startIndex = 0;
	  
	  for (int i=0; i<flags.size(); i++) 
	  {
		startIndex = flagMinIndex(searchString, flags.get(i)); 
		int b = i+1; 
		if (b < flags.size()) {
			endIndex = flagMinIndex(searchString, flags.get(b));  
		} else {
			endIndex = searchString.length() - 1;
		}
		
		conditionString.add(searchString.substring(startIndex, endIndex));
	  }
	  
	  return conditionString;
	}
	
	
	/**
	 * finds the minimum index at which a given flag occurs within the searchString
	 * @param String for the string to be searched and String for the current flag to look for 
	 * @return int minimum index at which the flag is found within the given string
	 * **/
	public static int flagMinIndex(String searchString, String currFlag) 
	{
	ArrayList<Integer> flagIndexes = new ArrayList<Integer>();

	Matcher m = Pattern.compile(currFlag.toString(), Pattern.CASE_INSENSITIVE).matcher(searchString);
	while (m.find()) 
	{
		  flagIndexes.add(m.start());
	}

	int minIndex = flagIndexes.get(flagIndexes.indexOf(Collections.min(flagIndexes)));
	return minIndex;
	}
	
	/**
	 * splits original input string into a substring starting and ending with the same input flag (inclusive).
	 * @param String for the string to be searched and String for the flag to look for
	 * @return String substring of original input string as delimited by a given flag
	 * **/
	public static String flagDelimiter(String searchString, String currFlag)       //NOT CURRENTLY USED
	{
	  ArrayList<Integer> flagIndexes = new ArrayList<Integer>();
	  
	  Matcher m = Pattern.compile(currFlag, Pattern.CASE_INSENSITIVE).matcher(searchString);
	  while (m.find()) 
	    {
		  flagIndexes.add(m.start());
		  flagIndexes.add(m.end());
	    }
	  
	  int minIndex = flagIndexes.get(flagIndexes.indexOf(Collections.min(flagIndexes)));
	  int maxIndex = flagIndexes.get(flagIndexes.indexOf(Collections.max(flagIndexes)));
	  String conditionString = searchString.substring(minIndex, maxIndex);
	  
	  return conditionString;
	}
	
	
	/**
	 * extracts an array of primary codes (ascii) from JSON file and converts it into a string 
	 * @param original JSON file from which to extract the searchString
	 * @return String searchString
	 * **/
	public static String getSearchString(JSONObject jsonObject) 
	{
		ArrayList<JSONObject> ksDataList = ReadKSFile.extractKsData(jsonObject); //ReadKSFile.extractKsData(ReadKSFile.getKsArray(jsonObject));
		
		ArrayList<Long> letterCodes = ReadKSFile.getLetterCodes(ksDataList); 
		String searchString = intoString(pCodeConverter(letterCodes));
		
		return searchString;
	}
	
	
	/**
	 * converts array of letters into a string
	 * @param ArrayList<String> containing a series of letters
	 * @return String obtained from an array of letters
	 * **/
	public static String intoString(ArrayList<String> fromAscii) 
	{
		StringBuilder finalStr = new StringBuilder();
		for (int i=0; i< fromAscii.size(); i++) 
		{
			finalStr.append(fromAscii.get(i));
		}
		return finalStr.toString();
	}

	
	/**
	 * converts array of ascii codes into array of the correspondent letters 
	 * @param ArrayList<long> containing all the primary codes (ascii) of each keystroke 
	 * @return ArrayList<String> containing all the letters translated from ascii 
	 * **/
	public static ArrayList<String> pCodeConverter(ArrayList<Long> letterCodes)  //Converts arrayList of primaryCodes into string and prints it one by one
	{	
	ArrayList<String> fromAscii = new ArrayList<String>();
	for (int i=0; i<letterCodes.size(); i++) 
	{
		int asciiCode = letterCodes.get(i).intValue();
		String translated = new Character((char)asciiCode).toString();
		fromAscii.add(translated);  
	}		 
	return fromAscii;
	}
	
	
	/**
	 * gets an ArrayList of the Flags of type ENUM
	 * @return ArrayList<String> containing the name of the flags
	 * **/
	public static ArrayList<String> flagsArray() 
	{
		ArrayList<String> flags = new ArrayList<String>();
		
		for (Flags flag: Flags.values()) 
		{
			flags.add(flag.toString());
		}
		return flags;
	}


	
}
