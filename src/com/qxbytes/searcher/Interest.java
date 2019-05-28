package com.qxbytes.searcher;

public class Interest {
	public StringBuilder persistent = new StringBuilder();
	public String interest;
	public StringBuilder extraInfo = new StringBuilder();
	private int count;
	public Interest (String interest)
	{
		this.interest = interest;
	}
	public int getCount() 
	{
		return count;
	}
	public void addHit() 
	{
		count++;
	}
	public void reset() 
	{
		count = 0;
		extraInfo = new StringBuilder();
	}
	public void addInfo(String info)
	{
		extraInfo.append(info+"\n");
	}
	public String getInfo()
	{
		if (extraInfo.length() > 32000) 
		{
			return extraInfo.substring(0, 32000).toString();
		}
		return extraInfo.toString();
	}
	public void addPersistent(String info) 
	{
		persistent.append(info);
	}
	public String getPersistent()
	{
		return persistent.toString();
	}
}
