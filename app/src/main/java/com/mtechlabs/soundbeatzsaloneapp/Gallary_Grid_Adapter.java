package com.mtechlabs.soundbeatzsaloneapp;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class Gallary_Grid_Adapter extends BaseAdapter {
	private Activity activity;
	Data.Image[] gallery;
	Functions f;

	// Constructor
	public Gallary_Grid_Adapter(Activity activity, String json_gallery_string) {
		this.activity = activity;
		f = new Functions(activity);
		gallery = f.getGallery(json_gallery_string);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		SmartImageView imageView = new SmartImageView(activity);
		imageView.setImageUrl(gallery[position].smallimageurl, activity.getResources().getIdentifier(activity.getResources().getString(R.string.default_image), "drawable", activity.getPackageName()));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setAdjustViewBounds(true);

		return imageView;
	}

	@Override
	public int getCount() {
		return gallery.length;
	}

	@Override
	public Object getItem(int position) {
		return gallery[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
