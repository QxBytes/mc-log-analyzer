package com.qxbytes.searcher;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import com.qxbytes.analyzer.Analyzer;
import com.qxbytes.analyzer.ExcelAnalyzer;
import com.qxbytes.analyzer.PlaytimeAnalyzer;
import com.qxbytes.filetreatment.FileTreatment;
import com.qxbytes.filetreatment.MinecraftFileTreatment;

public class AwesomeFileVisitor extends SimpleFileVisitor<Path>{
	public static Charset CHARSET = Charset.forName("ISO-8859-1");
	
	public int count = 1;
	private int validcount = 0;
	private List<Analyzer> analyzers = new ArrayList<Analyzer>();
	private FileTreatment filetreatment = new MinecraftFileTreatment();
	public AwesomeFileVisitor()
	{
		super();
		/*
		 * Adjust components here
		 */
		analyzers.add(new ExcelAnalyzer(Searcher.interests, Searcher.excludes));
		//analyzers.add(new FileAnalyzer(Main.interests));
		analyzers.add(new PlaytimeAnalyzer());
		for (Analyzer x : analyzers)
		{
			x.start();
		}
		
	}
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
	{
		
		try {
			//start
			if (filetreatment.isFileCompatible(file) == false)
			{
				throw new Exception("File is not compatible");
			}
			for (Analyzer x : analyzers)
			{
				x.fileStart(file, validcount);
			}
			//middle
			List<String> data = filetreatment.getDataFromFile(file);
			if (data == null)
			{
				throw new Exception("Error getting data from file");
			}
			for (int i = 0 ; i < data.size() ; i++)
			{
				for (Analyzer x : analyzers) 
				{
					x.analyzeLine(data.get(i),i);
				}
			}
			
			//end
			for (Analyzer x : analyzers)
			{
				x.fileEnd(file, validcount);
			}
			
			validcount++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			
		}
		count++;
		return FileVisitResult.CONTINUE;
	}
	public void close()
	{
		for (Analyzer x : analyzers)
		{
			x.end();
		}
	}
}
