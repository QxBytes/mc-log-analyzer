package com.qxbytes.searcher;

import java.nio.file.Files;
import java.nio.file.Path;

public class ReportGenerator {
	public static void search(Path path) 
	{
		//Paths.get("C:\\Users\\ace\\AppData\\Roaming\\.minecraft\\logs")
		try 
		{
			ScanningGUI.addLog("Task Started");
			AwesomeFileVisitor afv = new AwesomeFileVisitor();
			Files.walkFileTree(path, afv);
			afv.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
