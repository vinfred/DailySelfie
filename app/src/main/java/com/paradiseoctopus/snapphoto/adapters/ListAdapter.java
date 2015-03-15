package com.paradiseoctopus.snapphoto.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paradiseoctopus.snapphoto.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String> {
	Context context;
    private final ArrayList<String> captions;
	private final ArrayList<String> pics;    

    public ListAdapter (Context context, ArrayList<String> captions, ArrayList<String> pics) {
    	super(context, R.layout.row, pics);
    	
        this.context = context;
        this.pics = pics;
        this.captions = captions;
    }


    static class ViewHolder {
        public TextView text;
        public ImageView pic;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	ViewHolder mViewHolder;
    	View rowView = convertView;
    	
    	if (rowView == null) {
    		mViewHolder = new ViewHolder();
    		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		    
    		rowView = inflater.inflate(R.layout.row, parent, false);

    		mViewHolder.pic = (ImageView) rowView.findViewById(R.id.photo_view);    		
    		mViewHolder.text = (TextView) rowView.findViewById(R.id.photo_caption);
    		rowView.setTag(mViewHolder);
    	} 
    	else {
    		mViewHolder = (ViewHolder) rowView.getTag();
    	}
    	
    	Bitmap bitPic = BitmapFactory.decodeFile(pics.get(position));    	   
    	mViewHolder.pic.setImageBitmap(bitPic);
		
    	mViewHolder.text.setText(captions.get(position));

		/*rowView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, PhotoActivity.class);
				i.putExtra(ARG_PHOTOS, pics);
				i.putExtra(ARG_INDEX, position);
				context.startActivity(i);			
			}
		});*/
		
		return rowView;
    }
}