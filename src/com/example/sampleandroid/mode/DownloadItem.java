package com.example.sampleandroid.mode;


import java.io.Serializable;

public class DownloadItem implements Serializable{
	
	public String itemName;
	public int itemPercent;
	public String url;
	public String localFilePath;
	public String type;
	private static DownloadItem item;
	public static DownloadItem getInstence()
	{
		if(item==null)
			synchronized (DownloadItem.class) {
				if(item==null)
				item = new DownloadItem();
			}
		return item;
	}
}
