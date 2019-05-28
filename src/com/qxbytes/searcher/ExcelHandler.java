package com.qxbytes.searcher;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException; 

public class ExcelHandler {
	/**
	 * Assume Elements is already sorted and compressed
	 * @param elements
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public static void writeHeaders(WritableSheet wsheet, List<Interest> interests) {
		
		for (int i = 0 ; i < interests.size() ; i++) 
		{
			try {
				wsheet.addCell(new Label(i + 1,0,interests.get(i).interest));
				
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
