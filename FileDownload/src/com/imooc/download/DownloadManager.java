/**
 * 
 */
package com.imooc.download;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.imooc.download.http.DownloadCallback;
import com.imooc.download.http.HttpManager;

/**
 * @author Administrator
 *
 */
public class DownloadManager {
	
	private final static int MAX_THREAD = 2;//指定线程数
	
	private static final DownloadManager sManager = new DownloadManager();
	
	
	private static final ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 60,TimeUnit.MILLISECONDS,new SynchronousQueue<Runnable>(),new ThreadFactory() {
		
		private AtomicInteger mInteger = new AtomicInteger(1);
		
		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r,"download thread #"+mInteger.getAndIncrement());
			return thread;
		}
	});
	
	public static DownloadManager getInstance()
	{
		return sManager;
	}
	
	
	public void download(final String url,final DownloadCallback callback)
	{
		HttpManager.getInstance().asyncRequest(url,new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				if(arg1.isSuccessful() && callback !=null)
				{
					callback.failed(HttpManager.NETWORK_ERROR_CODE,"网络出问题了");
					return;
				}
				
				long length = arg1.body().contentLength();
				if(length == -1)
				{
					callback.failed(HttpManager.CONTENT_LENGTH_ERROR_CODE,"content length -1");
					return;
				}
				
				proccessDownload(url, length, callback);
					
				
			}
			
			private void proccessDownload(String url,long length,DownloadCallback callback) {
				// TODO Auto-generated method stub
				long threadDownloadSize = length / MAX_THREAD;
				
				for(int i =0;i<MAX_THREAD ;i++)
				{
					long startSize = i * threadDownloadSize;
					long endSize = (i+1)*threadDownloadSize - 1 ;
					
					sThreadPool.execute(new DowloadRunnable(startSize,endSize,url, callback));
				}
				
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	/**
	 * 
	 */
	public DownloadManager() {

	}
	
	
	

}
