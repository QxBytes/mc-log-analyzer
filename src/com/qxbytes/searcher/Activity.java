package com.qxbytes.searcher;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.Format;
import jxl.format.Orientation;
import jxl.format.Pattern;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WriteException;

public class Activity implements CellFormat{
	public static WritableCellFormat HIGH = new WritableCellFormat();
	public static WritableCellFormat MEDIUM = new WritableCellFormat();
	public static WritableCellFormat LOW = new WritableCellFormat();
	static {
		try {
			Activity.HIGH.setBackground(Colour.RED);
			Activity.MEDIUM.setBackground(Colour.ORANGE);
			Activity.LOW.setBackground(Colour.YELLOW);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Alignment getAlignment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Colour getBackgroundColour() {
		// TODO Auto-generated method stub
		return Colour.RED;
	}

	@Override
	public BorderLineStyle getBorder(Border arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Colour getBorderColour(Border arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BorderLineStyle getBorderLine(Border arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Format getFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndentation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Orientation getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pattern getPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VerticalAlignment getVerticalAlignment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getWrap() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasBorders() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isShrinkToFit() {
		// TODO Auto-generated method stub
		return false;
	}

}
