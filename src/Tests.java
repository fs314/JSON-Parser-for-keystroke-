import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.*;

public class Tests 
{
	
	public static void main(String[] args) 
	{
       //test1 1789050386
	   //test2 1462669319
	   //test3 -412481395
	   String fileName = "1462669319kspattern.json";
	   JSONObject jsonObject = ReadKSFile.parseObj(fileName);
	
	   String searchString = SplitCondition.intoString(SplitCondition.pCodeConverter(ReadKSFile.getLetterCodes(ReadKSFile.extractKsData(jsonObject))));
	
	   for(int i=0; i< SplitCondition.flagsArray().size(); i++)
	   {
		 System.out.println(SplitCondition.flagsArray().get(i) + " " + SplitCondition.flagMinIndex(searchString,  SplitCondition.flagsArray().get(i)));  

	   }
	  
	   
	}

	
	  
	  
	   
	   
	   
}


