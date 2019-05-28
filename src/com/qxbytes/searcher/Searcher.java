package com.qxbytes.searcher;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Searcher {
	
	public static final int threshold = 3;
	public static List<Interest> interests;
	public static List<String> excludes;
	public static void search(Path file, String strings[], String excludes[]) 
	{
		
		interests = new ArrayList<Interest>();
		
		for (String x : strings)
		{
			ScanningGUI.addLog("Scanning for: " + x);
			if (x.equals("") || x == null) continue;
			interests.add(new Interest(x));
		}
		
		Searcher.excludes = Arrays.asList(excludes);
		ScanningGUI.addLog("Excluding " + Searcher.excludes);
		try 
		{
		    ReportGenerator.search(file);
		}
		catch (Exception e)
		{
			
		}
		ScanningGUI.addLog("=====FINISHED!=====");
	}
}
