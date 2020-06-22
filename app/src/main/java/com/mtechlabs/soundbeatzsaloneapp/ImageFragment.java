package com.mtechlabs.soundbeatzsaloneapp;

import com.loopj.android.image.SmartImageView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImageFragment extends Fragment {
	SmartImageView gallery_image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.imagefragment, container, false);
		gallery_image = (SmartImageView) v.findViewById(R.id.gallery_image);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentActivity activity = this.getActivity();

		gallery_image.setImageUrl(getArguments().getString("imageurl"), activity.getResources().getIdentifier(activity.getResources().getString(R.string.default_image), "drawable", activity.getPackageName()));

	}

}
