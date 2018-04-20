package com.imooc.download;

import java.io.File;

import com.imooc.download.file.FileStorageManager;
import com.imooc.download.http.DownloadCallback;
import com.imooc.download.http.HttpManager;
import com.imooc.download.utils.Logger;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

	private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imageView = (ImageView) findViewById(R.id.imageView);
        
        File file = FileStorageManager.getInstance().getFileByName("http://www.imooc.com");
        Logger.debug("nate","file path="+file.getAbsoluteFile());
//        HttpManager.getInstance().asyncRequest("https://img4.mukewang.com/szimg/59b8a486000107fb05400300.jpg", new DownloadCallback() {
//			
//			@Override
//			public void success(File file) {
//				
//				final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//				runOnUiThread(new Runnable() {
//					
//					@Override
//					public void run() {
//						imageView.setImageBitmap(bitmap);
//					}
//				});
//				
//				Logger.debug("nate","Success"+file.getAbsolutePath());
//				
//			}
//			
//			@Override
//			public void failed(int errorcode, String errorMessage) {
//				Logger.debug("nate","fail"+errorcode+" "+errorMessage);
//				
//			}
//			
//			@Override
//			public void Progress(int progress) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
        
        
        DownloadManager.getInstance().download("", new DownloadCallback() {
			
			@Override
			public void success(File file) {
				
				
				final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
				runOnUiThread(new Runnable() {
					public void run() {
						imageView.setImageBitmap(bitmap);
						
					}
				});
				
				Logger.debug("nate","success"+file.getAbsolutePath());
			}
			
			@Override
			public void failed(int errorcode, String errorMessage) {

				Logger.debug("nate","failed"+ errorcode + " "+errorMessage);
				
			}
			
			@Override
			public void Progress(int progress) {
				
			}
		});
    }


}
