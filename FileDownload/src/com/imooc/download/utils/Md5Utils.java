/**
 * 
 */
package com.imooc.download.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.text.TextUtils;

/**
 * @author Administrator
 *
 */
public class Md5Utils {

	public static String generateCode(String url)
	{
		if(TextUtils.isEmpty(url))
		{
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			digest.update(url.getBytes());
			
			byte[] cipher = digest.digest();
			for(byte b: cipher)
			{
				String hexStr = Integer.toHexString(b&0xff);
				buffer.append(hexStr.length() == 1?"0"+hexStr:hexStr);
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}
	/**
	 * 
	 */
	public Md5Utils() {
		// TODO Auto-generated constructor stub
	}

}
