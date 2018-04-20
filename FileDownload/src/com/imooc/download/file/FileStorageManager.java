/**
 * 
 */
package com.imooc.download.file;

import java.io.File;
import java.io.IOException;

import com.imooc.download.utils.Md5Utils;

import android.content.Context;
import android.os.Environment;

/**
 * @author Administrator
 *单例模式
 */
public class FileStorageManager {
	
	private static final FileStorageManager sManager = new FileStorageManager();
	
	private Context mContext;
	
	public static FileStorageManager getInstance()
	{
		return sManager;
		
	}
	
	private FileStorageManager()
	{
		
	}
	
	public void init(Context context)
	{
		this.mContext = context;
	}
	
	public  File getFileByName(String url)
	{
		File parent;
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			parent = mContext.getExternalCacheDir();
		}else{
			parent = mContext.getCacheDir();
			
		}
		
		String fileName = Md5Utils.generateCode(url);
		
		File file = new  File(parent,fileName);
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return file;
	}


}
