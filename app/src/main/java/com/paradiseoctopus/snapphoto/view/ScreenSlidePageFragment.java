package com.paradiseoctopus.snapphoto.view;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paradiseoctopus.snapphoto.R;


public class ScreenSlidePageFragment extends Fragment {
	private ImageView photoView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_screen_slide_page, container, false);

		photoView = (ImageView) rootView.findViewById(R.id.full_photo_view);
		String photoPath = getArguments().getString(PhotoActivity.ARG_CURPHOTO);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		photoView.setImageBitmap(BitmapFactory.decodeFile(photoPath, options));

		return rootView;
	}
}
