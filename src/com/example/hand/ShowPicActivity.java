package com.example.hand;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ShowPicActivity extends Activity{
	
	private ImageView show_pic;
	private String urlStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_pic);
		initView();
		bindData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		show_pic = (ImageView) findViewById(R.id.show_pic);
	}

	private void bindData() {
		// TODO Auto-generated method stub
		urlStr = getIntent().getStringExtra("url");
		Log.e("ShowPicActivity", " -------------- ShowPicActivity  photo.getPath() urlStr: " + urlStr);
		
//		FileInputStream fis = new FileInputStream("/sdcard/test.png");
		Bitmap bitmap = null;
		FileInputStream fis;
		try {
			fis = new FileInputStream(urlStr);
			bitmap=BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
//		Bitmap bitmap = GetLocalOrNetBitmap(urlStr);
		if (bitmap != null) {

			show_pic.setImageBitmap(bitmap);
		}
		
	}
	/** 
	  * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如: 
	  * 
	  * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ; 
	  * 
	  * B.本地路径:url="file://mnt/sdcard/photo/image.png"; 
	  * 
	  * C.支持的图片格式 ,png, jpg,bmp,gif等等 
	  * 
	  * @param url 
	  * @return 
	  */
	 public static Bitmap GetLocalOrNetBitmap(String url) 
	 { 
	  Bitmap bitmap = null; 
	  InputStream in = null; 
	  BufferedOutputStream out = null; 
	  try
	  { 
	   in = new BufferedInputStream(new URL(url).openStream(), 10); 
	   final ByteArrayOutputStream dataStream = new ByteArrayOutputStream(); 
	   out = new BufferedOutputStream(dataStream, 10); 
//	   copy(in, out); 
	   out.flush(); 
	   byte[] data = dataStream.toByteArray(); 
	   bitmap = BitmapFactory.decodeByteArray(data, 0, data.length); 
	   data = null; 
	   return bitmap; 
	  } 
	  catch ( Exception e) 
	  { 
	   e.printStackTrace(); 
	   return null; 
	  } 
	 }

}
