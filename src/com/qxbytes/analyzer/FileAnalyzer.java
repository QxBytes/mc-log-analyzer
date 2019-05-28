package com.qxbytes.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.qxbytes.searcher.Interest;
import com.qxbytes.searcher.ScanningGUI;

public class FileAnalyzer implements Analyzer {
	private List<Interest> interests;
	public FileAnalyzer(List<Interest> interests)
	{
		this.interests = interests;	
	}
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fileStart(Path file, int count) {


	}

	@Override
	public void analyzeLine(String data, int lineNumber) {
		for (int i = 0 ; i < interests.size() ; i++)
		{
			if (data.contains(interests.get(i).interest))
			//if (Pattern.compile(Pattern.quote(interests.get(i).interest), Pattern.CASE_INSENSITIVE).matcher(data+"").find())
			{
				interests.get(i).addHit();
			}
		}
	}

	@Override
	public void fileEnd(Path file, int count) {
		for (int i = 0 ; i < interests.size() ; i++)
		{
			if (interests.get(i).getCount() > 0) 
			{
				try {
					ScanningGUI.addLog(file.getFileName().toString() + " : " + interests.get(i).interest + " : " + interests.get(i).getCount() + " : " + Files.size(file));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//important!
			interests.get(i).reset();
		}

	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

}
