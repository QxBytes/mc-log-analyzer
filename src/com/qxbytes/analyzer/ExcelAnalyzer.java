package com.qxbytes.analyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.qxbytes.searcher.Activity;
import com.qxbytes.searcher.ExcelHandler;
import com.qxbytes.searcher.Interest;
import com.qxbytes.searcher.ScanningGUI;

import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelAnalyzer implements Analyzer{
	private List<Interest> interests;
	private List<String> excludes;
	private WritableWorkbook wworkbook;
	private WritableSheet wsheet;
	private FileWriter fw;
	private BufferedWriter bw;
	public ExcelAnalyzer(List<Interest> interests, List<String> excludes)
	{
		
		this.interests = interests;
		this.excludes = excludes;
		try 
		{
			
		    wworkbook = Workbook.createWorkbook(new File("DATA" + System.currentTimeMillis() + ".xls"));
		    this.wsheet = wworkbook.createSheet("export", 0);
		    fw = new FileWriter(new File("ONE_FILE" + System.currentTimeMillis() + ".txt"));
		    bw = new BufferedWriter(fw);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void start() {
		ExcelHandler.writeHeaders(wsheet, interests);

	}

	@Override
	public void analyzeLine(String data,int lineNumber) {
		try {

			for (int i = 0 ; i < interests.size() ; i++)
			{
				boolean flag1 = false;
				flag1 = data.contains(interests.get(i).interest);
				
				for (String exclude : excludes)
				{
					if (exclude.equals("") || exclude == null) continue;
					if (data.contains(exclude))
					{
						flag1 = false;
					}
				}
				
//				if (data.contains("Playing")||data.contains("Summoned")||data.contains("Changed") || data.contains("Teleported") || data.contains("Given") || data.contains("given") || data.contains("@") || data.contains("joined") || data.contains("left") || data.contains("killed") || data.contains("slain") || data.contains("Death") || data.contains("party") || data.contains("Request") || data.contains("request") || data.contains("Followers Online") || data.contains("Killed") || data.contains("Object") || data.contains("offline") || data.contains("Bomb"))
//				{
//					flag1 = false;
//				}
				if (flag1)
				//if (Pattern.compile(Pattern.quote(interests.get(i).interest), Pattern.CASE_INSENSITIVE).matcher(data+"").find())
				{
					interests.get(i).addHit();
					interests.get(i).addInfo(lineNumber + ":" + data);
					bw.append(data + "\n");
					bw.newLine();
					
					//Debug only
					//System.out.println(data);
				}
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void end() {
	    try {
	    	wworkbook.write();
			wworkbook.close();
			bw.flush();
			fw.flush();
			bw.close();
			fw.close();
			ScanningGUI.addLog("Spreadsheet created with log data created in the folder with this .jar file - timestamped");
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void fileStart(Path file, int count) {
		ScanningGUI.addLog("#: " + count + " | File: " + file.getFileName());
		
		try {
			wsheet.setRowView(count, 20);
			wsheet.addCell(new Label(0,count,file.getFileName().toString()));
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void fileEnd(Path file, int count) {
		for (int i = 0 ; i < interests.size() ; i++)
		{
			if (interests.get(i).getCount() > 0) 
			{
				CellFormat select = Activity.LOW;
				if (interests.get(i).getCount() > 5 )
				{
					select = Activity.MEDIUM;
				}
				if (interests.get(i).getCount() > 30)
				{
					select = Activity.HIGH;
				}
				
				try {
					wsheet.addCell(new Label(i+1,count,interests.get(i).getCount() + "\n" + interests.get(i).getInfo(),select));
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//important!
			interests.get(i).reset();
		}
		
		
	}
	

}
