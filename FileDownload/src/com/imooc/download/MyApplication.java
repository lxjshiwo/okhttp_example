/**
 * 
 */
package com.imooc.download;

import com.imooc.download.file.FileStorageManager;

import android.app.Application;

/**
 * @author Administrator
 *
 */
public class MyApplication extends Application {

	/**
	 * 
	 */
	public MyApplication() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		FileStorageManager.getInstance().init(this);
	}

}
