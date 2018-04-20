/**
 * 
 */
package com.imooc.download.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.InputSource;

import com.imooc.download.file.FileStorageManager;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.content.Context;
import android.content.pm.LauncherApps.Callback;

/**
 * @author Administrator
 *
 */
public class HttpManager {
	
	private static final HttpManager sManager = new HttpManager();

	public static final int NETWORK_ERROR_CODE = 1;
	
	public static final int CONTENT_LENGTH_ERROR_CODE = 1;
	
	private Context mContext;
	
	private OkHttpClient mClient;

	public static HttpManager getInstance()
	{
		return sManager;
	}

	public HttpManager() {
		mClient = new OkHttpClient();
	}
	
	public void init(Context context)
	{
		this.mContext = context;
	}
	
	
	//同步请求不需要使用相应的回调函数
	/**
	 *同步请求
	 */
	public Response syncRequest(String url)
	{
		Request request = new Request.Builder().url(url).build();

		try {
			return mClient.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public Response syncRequestByRange(String url,long start,long end)
	{
		Request request = new Request.Builder().url(url)
				.addHeader("Range", "bytes="+ start + "-" + end)
				.build();

		try {
			return mClient.newCall(request).execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 *异步请求
	 */
	
	public void asyncRequest(final String url,okhttp3.Callback callback)
	{
		Request request =new Request.Builder().url(url).build();
		mClient.newCall(request).enqueue(callback);
	}
	
	
	public void asyncRequest(final String url ,final DownloadCallback callback)
	{
		Request request = new Request.Builder().url(url).build();
		mClient.newCall(request).enqueue(new okhttp3.Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				if(!arg1.isSuccessful() && callback != null)
				{
					callback.failed(NETWORK_ERROR_CODE,"网络请求失败");
					
				}
				
				File file = FileStorageManager.getInstance().getFileByName(url); 
				
				byte[] buffer = new byte[1024 * 500];
				int len;
				FileOutputStream fileOut = new FileOutputStream(file);
				InputStream inStream = arg1.body().byteStream();
				while((len = inStream.read(buffer,0,buffer.length))!=-1)
				{
					fileOut.write(buffer,0,len);
					fileOut.flush();
				}
				
				
				callback.success(file);
				
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				
			}
		});
	}

}
