package com.example.imagedownloader;


import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ImagedownloaderActivity extends Activity {
	
	private String imageUrls[] = {"http://www.abc.com/image1.jpg",
								"http://www.abc.com/image2.jpg",
								"http://www.abc.com/image3.jpg"
								};
	
	private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
		  ImageView bmImage;

		  public ImageDownloader(ImageView bmImage) {
		      this.bmImage = bmImage;
		  }

		  protected Bitmap doInBackground(String... urls) {
		      String url = urls[0];
		      Bitmap bitmap = null;
		      try {
		        InputStream in = new java.net.URL(url).openStream();
		        bitmap = BitmapFactory.decodeStream(in);
		      } catch (Exception e) {
		          Log.e("MyApp", e.getMessage());
		      }
		      return bitmap;
		  }

		  protected void onPostExecute(Bitmap result) {
		      bmImage.setImageBitmap(result);
		  }
		}
	private int currImage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagedownloader);
		
		setInitialImage();
		setImageRotateListener();
		
	}

	private void setImageRotateListener() {
		final Button rotatebutton = (Button) findViewById(R.id.btnRotateImage);
		rotatebutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				currImage++;
				if(currImage == 3) {
					currImage = 0;
				}
				setCurrentImage();
			}
		});
	}

	private void setInitialImage() {
		setCurrentImage();
	}
	
	private void setCurrentImage() {
		final ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
		ImageDownloader imageDownLoader = new ImageDownloader(imageView);
		imageDownLoader.execute(imageUrls[currImage]);
	}
	
}