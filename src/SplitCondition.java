import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*; 
import org.json.simple.JSONObject;

public class SplitCondition {

	ReadKSFile rKSF;
	
	/**
	 * class constructor
	 * **/
	public SplitCondition() 
	{
		 rKSF = new ReadKSFile ();
	}
	
	
	/**
	 * Parses original input JSONObject and splits all the keystroke data according to the condition they are generated in. 
	 * @param  JSONObject original JSONObject containing all keystroke data
	 * @return LinkedHashMap<String, ArrayList<JSONObject>> where the String represents the condition name (or flag) 
	 * and the ArrayList<JSONObject> all the JSONObject of each keystroke associated to that condition
	 * **/
	public LinkedHashMap<String, ArrayList<JSONObject>> fromCondition(JSONObject jsonObject) 
	{
		LinkedHashMap<String, ArrayList<JSONObject>> conditions = new LinkedHashMap<String, ArrayList<JSONObject>>(8); 
		
		ArrayList<JSONObject> ksData =  rKSF.extractKsData(jsonObject); 
		String searchString = getSearchString(jsonObject);
	
		for (int i=0; i<flagsArray().size(); i++) 
		{
			ArrayList<JSONObject> ksByCondition = new ArrayList<JSONObject>();
			
			int minIndex = flagMinIndex(searchString, flagsArray().get(i));
			int maxIndex = flagMaxIndex(searchString, flagsArray().get(i));
			
			if (maxIndex != 0 && minIndex != 0 || maxIndex != 0 && minIndex == 0) 
			{
				for(int index = minIndex; index < maxIndex; index++) 
				{
					ksByCondition.add(ksData.get(index));
				}
				conditions.put(flagsArray().get(i), ksByCondition);
			}
		} 
	return conditions;
	} 
	
	/**
	 * extracts the anonymous code from the data collected. 
	 * @param JSONObject original JSONObject containing all keystroke data
	 * @return String substring of original input string as delimited by a given flag
	 * **/
	public String extractAnonCode(JSONObject jsonObject) 
	{
		String anonCode = "C"; 
		
		String searchString = getSearchString(jsonObject);
		int minIndex = flagMinIndex(searchString, "code");
		int maxIndex = flagMaxIndex(searchString, "code");
		
		if(minIndex != -1 && maxIndex != -1) 
		{ 
			for(int index = minIndex; index < maxIndex; index++) 
			{
				anonCode= anonCode + searchString.substring(minIndex, maxIndex);
			}
		}
		return anonCode;
	} 
	
	/**
	 * splits original input string into a substring starting and ending with the same input flag (inclusive).
	 * @param String for the string to be searched and String for the flag to look for
	 * @return String substring of original input string as delimited by a given flag
	 * **/
	public String flagDelimiter(String searchString, String currFlag)      
	{
		String conditionString = " ";
		int maxIndex = flagMaxIndex(searchString, currFlag);
		int minIndex = flagMinIndex(searchString, currFlag);
		
		if (maxIndex != 0 && minIndex != 0 || maxIndex != 0 && minIndex == 0) 
		{
			conditionString = searchString.substring(minIndex, maxIndex);
		}
		
		return conditionString;
	}
	
	/**
	 * finds the minimum index at which a given flag occurs within the searchString
	 * @param String for the string to be searched and String for the current flag to look for 
	 * @return int minimum index at which the flag is found within the given string
	 * **/
	public int flagMinIndex(String searchString, String currFlag) 
	{
		ArrayList<Integer> flagIndexes = new ArrayList<Integer>();
		int minIndex=0;
		
		Matcher m = Pattern.compile(currFlag, Pattern.CASE_INSENSITIVE).matcher(searchString);
			while (m.find()) 
			{
				flagIndexes.add(m.start());
		    }
			
			//loops over array of indexes for given flag to find minimum index of flag or start
			for (int i=0; i<flagIndexes.size(); i++)  
			{
				int currIndex = flagIndexes.get(i);
				if(i==0) 
				{
					minIndex = flagIndexes.get(i);
				} else if (currIndex < minIndex) {
					minIndex =currIndex;
				}
			}
			
	return minIndex;
	}
	
	/**
	 * finds the maximum index at which a given flag occurs within the searchString
	 * @param String for the string to be searched and String for the current flag to look for 
	 * @return int minimum index at which the flag is found within the given string
	 * **/
	public int flagMaxIndex(String searchString, String currFlag) 
	{
		ArrayList<Integer> flagIndexes = new ArrayList<Integer>();
		int maxIndex=0;
		
		Matcher m = Pattern.compile(currFlag, Pattern.CASE_INSENSITIVE).matcher(searchString);
		while (m.find()) 
        {
        	flagIndexes.add(m.end());
		}
		
		//loops over array of indexes for given flag to find maximum index of flag or end index of condition
		for (int i=0; i<flagIndexes.size(); i++)  
		{
			int currIndex = flagIndexes.get(i);
		    if(i==0) 
		    {
		    	maxIndex = flagIndexes.get(i);
		    } else if (currIndex > maxIndex) {
		    	   maxIndex =currIndex;
		    }
	     }

	return maxIndex;	
	}
	
	
	/**
	 * finds how many times a given flag occurs in a given search string
	 * @param String for the string to be searched and String for the flag to look for
	 * @return int for the number of times the flag was found in the search string
	 * **/
	public int flagsOccurence(String searchString, String currFlag) 
	{
		int occurences=0;
		
		Matcher m = Pattern.compile(currFlag, Pattern.CASE_INSENSITIVE).matcher(searchString);
		while (m.find()) 
		{
			occurences++;
		}
		return occurences;
	}
	
	
	/**
	 * extracts an array of primary codes (ascii) from JSON file and converts it into a string 
	 * @param JSONObject original JSONObject containing all keystroke data
	 * @return String searchString
	 * **/
	public String getSearchString(JSONObject jsonObject) 
	{
		ArrayList<JSONObject> ksDataList =  rKSF.extractKsData(jsonObject); 
		
		ArrayList<Long> letterCodes =  rKSF.getLetterCodes(ksDataList); 
		String searchString = intoString(pCodeConverter(letterCodes));
		return searchString;
	}
	
	
	/**
	 * converts array of letters into a string
	 * @param ArrayList<String> containing a series of letters
	 * @return String obtained from an array of letters
	 * **/
	public String intoString(ArrayList<String> fromAscii) 
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
	public ArrayList<String> pCodeConverter(ArrayList<Long> letterCodes)  
	{	
	ArrayList<String> fromAscii = new ArrayList<String>();
	for (int i=0; i<letterCodes.size(); i++) 
	{
		int asciiCode = letterCodes.get(i).intValue();
		char asciiChar = (char) asciiCode;
 		String translated = String.valueOf(asciiChar);
		fromAscii.add(translated);  
	}		 
	return fromAscii;
	}
	
	
	/**
	 * gets an ArrayList of the Flags of type ENUM
	 * @return ArrayList<String> containing the name of the flags
	 * **/
	public ArrayList<String> flagsArray() 
	{
		ArrayList<String> flags = new ArrayList<String>();
		
		for (Flags flag: Flags.values()) 
		{
			flags.add(flag.toString());
		}
		return flags;
	}


	
}
