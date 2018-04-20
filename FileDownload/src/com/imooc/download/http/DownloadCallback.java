/**
 * 
 */
package com.imooc.download.http;

import java.io.File;

/**
 * @author Administrator
 *
 */
public interface DownloadCallback {

	/*
	 * 
	 */
	
	void success(File file);

	void failed(int errorcode,String errorMessage);
	
	void Progress(int progress);

}
