import java.io.FileNotFoundException;

public class parseKS {

	WriteJSON wjs;
	
	public parseKS()
	{
		wjs = new WriteJSON();
	}
	
	public static void main(String[] args) 
	{
		parseKS pks = new parseKS();
		pks.parseFiles(args[0], args[1]);
	}
	
	public void parseFiles(String originalPath, String destinationPath) 
	{
		try 
		{
			wjs.createKSFile(originalPath, destinationPath);
		}  
	    catch(FileNotFoundException e) {e.printStackTrace();}

	}

}
