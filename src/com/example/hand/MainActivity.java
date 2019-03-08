package com.example.hand;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.hand.views.SignatureView;

public class MainActivity extends Activity {
	private SignatureView mSignaturePad;
	private Button mClearButton;
	private Button mSaveButton;
	private String urlStr;
	private Button showpic_button; //下一页
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = this;

		mSignaturePad = (SignatureView) findViewById(R.id.signature_pad);
		mClearButton = (Button) findViewById(R.id.clear_button);
		mSaveButton = (Button) findViewById(R.id.save_button);
		showpic_button = (Button) findViewById(R.id.showpic_button);
		
		showpic_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, ShowPicActivity.class);
				intent.putExtra("url", urlStr);
                startActivity(intent);
			}
		});
		
		mSignaturePad.setOnSignedListener(new SignatureView.OnSignedListener() {
			@Override
			public void onSigned() {
				mSaveButton.setEnabled(true);
				mClearButton.setEnabled(true);
				
				showpic_button.setEnabled(true);
			}

			@Override
			public void onClear() {
				mSaveButton.setEnabled(false);
				mClearButton.setEnabled(false);
				
				showpic_button.setEnabled(false);
			}
		});

		mClearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSignaturePad.clear();
			}
		});

		mSaveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
				if (addSignatureToGallery(signatureBitmap)) {
					Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public File getAlbumStorageDir(String albumName) {
		// Get the directory for the user's public pictures directory.
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
		if (!file.mkdirs()) {
			Log.e("SignaturePad", "Directory not created");
		}
		return file;
	}

	public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
		Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newBitmap);
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(bitmap, 0, 0, null);
		OutputStream stream = new FileOutputStream(photo);
		newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
		stream.close();
	}

	public boolean addSignatureToGallery(Bitmap signature) {
		boolean result = false;
		try {
			File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg",
					System.currentTimeMillis()));
			saveBitmapToJPG(signature, photo);
			Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri contentUri = Uri.fromFile(photo);
			mediaScanIntent.setData(contentUri);
			MainActivity.this.sendBroadcast(mediaScanIntent);
			result = true;
			
			
//			urlStr = photo.getPath();
			urlStr = photo.getAbsolutePath();
			Log.e("urlStr", " --------------  photo.getPath() urlStr: " + photo.getPath());
			Log.e("urlStr", " --------------  photo.getAbsolutePath() urlStr: " + photo.getAbsolutePath() );
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
