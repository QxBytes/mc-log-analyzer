package com.qxbytes.analyzer;

import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qxbytes.searcher.ScanningGUI;

public class PlaytimeAnalyzer implements Analyzer{
	//perpetual variables
	long totalTime = 0;
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	//temporary per file
	//[17:58:50]
	Pattern p = Pattern.compile("\\d\\d:\\d\\d:\\d\\d");
	boolean startFound = false;
	String start = "";
	String end = "";
	private String locateTimeStamp(String line)
	{
		int startValue = -1;
		try 
		{
			Matcher m = p.matcher(line);
			m.find();
			startValue = m.start();
		}
		catch (IllegalStateException e)
		{
			startValue = -1;
		}
		
		if (startValue < 0) return null;
		return line.substring(startValue, startValue+8);
	}
	@Override
	public void start() {
		
	}

	@Override
	public void fileStart(Path file, int count) {
		startFound = false;
		start = "";
		end = "";
		
	}

	@Override
	public void analyzeLine(String data, int lineNumber) {
		String timestamp = locateTimeStamp(data);
		
		if (timestamp != null && !timestamp.equals(""))
		{
			if (startFound == false)
			{
				
				start = timestamp;
				startFound = true;
			}
			else if (timestamp.length() > 5)//because the clock is 00:00:00 should have 8 + 2 (bracket) chars minimum
			{
				end = timestamp;
			}
		}
		
	}

	@Override
	public void fileEnd(Path file, int count) {
		if (start != null && !start.equals("") && end != null && !end.equals(""))
		{
			try {
				long amount = getDateDiff(sdf.parse(start),sdf.parse(end), TimeUnit.MILLISECONDS);
				
				if (amount < 0)
				{
					ScanningGUI.addError("Time added is negative: " + amount + " for " + file.getFileName().toString() + "\nAttempting to Reverse...");
					amount = getDateDiff(sdf.parse(end),sdf.parse(start),TimeUnit.MILLISECONDS);
				}
				if (amount > 86400000 || amount < 0)
				{
					ScanningGUI.addError("Time added is more than 24 hours: " + amount + " for " + file.getFileName().toString());
				} else {
					totalTime += amount;
				}
			} catch (ParseException e) {
				ScanningGUI.addError("Error: Could not parse date for " + file.getFileName().toString());
			}
		} 
		else 
		{
			ScanningGUI.addError("Error: Could not find date for " + file.getFileName().toString());
		}
	}

	@Override
	public void end() {
		ScanningGUI.addLog("Total Time Spent: " + getDurationBreakdown(totalTime));
	}
	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	/**
     * Convert a millisecond duration to a string format
     * 
     * @param millis A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     */
    public static String getDurationBreakdown(long millis) {
        if(millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return(sb.toString());
    }
}
