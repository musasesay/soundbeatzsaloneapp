 package com.mtechlabs.soundbeatzsaloneapp;

import com.loopj.android.image.SmartImageView;

import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Music_List_Adapter extends ArrayAdapter<Object> {
	FragmentActivity activity;
	String json_categories_string;
	Data.Song[] songs;

	public Music_List_Adapter(FragmentActivity activity, Data.Song[] songs) {
		super(activity, 0);
		this.activity = activity;
		this.songs = songs;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = activity.getLayoutInflater();
		convertView = inflator.inflate(R.layout.music_item, null);

		TextView music_title = (TextView) convertView.findViewById(R.id.music_title);
		TextView music_lirics = (TextView) convertView.findViewById(R.id.music_lirics);
		SmartImageView music_image = (SmartImageView) convertView.findViewById(R.id.music_image);
		music_title.setText(Html.fromHtml(songs[position].title));
		music_lirics.setText(Html.fromHtml((songs[position].lyrics.length()>100)?(songs[position].lyrics.substring(0, 100)+"..."):(songs[position].lyrics) ));
		music_image.setImageUrl(songs[position].imageurl, activity.getResources().getIdentifier(activity.getResources().getString(R.string.default_image), "drawable", activity.getPackageName()));

		Functions f = new Functions(activity);
		music_title.setTextSize(f.w(6));
		music_lirics.setTextSize(f.w(4));

		return convertView;
	}

	@Override
	public int getCount() {
		return songs.length;
	}

	@Override
	public Data.Song getItem(int position) {
		return songs[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
