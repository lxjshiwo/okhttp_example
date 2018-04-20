/**
 * 
 */
package com.imooc.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.RandomAccess;

import okhttp3.Response;

import com.imooc.download.file.FileStorageManager;
import com.imooc.download.http.DownloadCallback;
import com.imooc.download.http.HttpManager;

/**
 * @author Administrator
 *
 */
public class DowloadRunnable implements Runnable{
	
	private long mStart;
	
	private long mEnd;
	
	private String mUrl;
	
	private DownloadCallback mCallback;
	
	
	public DowloadRunnable(long mStart,long mEnd,String mUrl,DownloadCallback mCallBack)
	{
		this.mStart = mStart;
		this.mEnd = mEnd;
		this.mUrl = mUrl;
		this.mCallback = mCallBack;
	}
	

	@Override
	public void run() 
	{
		Response response = HttpManager.getInstance().syncRequestByRange(mUrl,mStart,mEnd);
		if(response == null && mCallback != null)
		{
			mCallback.failed(HttpManager.NETWORK_ERROR_CODE,"网络出错误");
			return;
		}
		

		File file = FileStorageManager.getInstance().getFileByName(mUrl);
		try {
			
			byte[] buffer = new byte[1024*500];
			int len;
			RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");
			InputStream inStream = response.body().byteStream();
			while((len = inStream.read(buffer,0,buffer.length))!= -1)
			{
				randomAccessFile.write(buffer,0,len);
			}
			mCallback.success(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
		
	}

}
