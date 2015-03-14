package com.paradiseoctopus.snapphoto.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.paradiseoctopus.snapphoto.R;
import com.paradiseoctopus.snapphoto.adapters.ListAdapter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends ActionBarActivity {
	static final int REQUEST_TAKE_PHOTO = 1;

	private String dirName = "Photo";
	private File storageDir;
	private ListView listview;
	private ArrayList<String> caps;
	private ArrayList<String> pics;
	private File[] listFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

		storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), dirName);
		storageDir.mkdirs();

		listview = (ListView) findViewById(R.id.photo_list);
		//fab.attachToListView(listview);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				takePicture();
			}
		});

		caps = new ArrayList<>();
		pics = new ArrayList<>();
		showFromSdCard();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_camera) {
			takePicture();
		}
		return super.onOptionsItemSelected(item);
	}

	private void takePicture() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {

			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
			showFromSdCard();
		}
	}

	public void showFromSdCard() {
		caps.clear();
		pics.clear();

		if (storageDir.isDirectory()) {
			listFile = storageDir.listFiles();
			for (int i = 0; i < listFile.length; i++) {
				Date fileData = new Date(listFile[i].lastModified());

				DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
				DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this);

				String s1 = dateFormat.format(fileData);
				String s2 = timeFormat.format(fileData);

				caps.add(s2 + " " + s1);
				pics.add(listFile[i].getAbsolutePath());
			}
		}
		Collections.reverse(pics);
		listview.setAdapter(new ListAdapter(this, caps, pics));
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";

		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);

		return image;
	}
}
