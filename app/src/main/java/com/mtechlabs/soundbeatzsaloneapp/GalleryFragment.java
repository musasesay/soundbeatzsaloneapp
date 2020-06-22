package com.mtechlabs.soundbeatzsaloneapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class GalleryFragment extends Fragment {

	GridView gridView;
	Context context;
	TextView subtitle;
	Functions f;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.galleryfragment, container, false);
		context = v.getContext();

		//load resources
		gridView = (GridView) v.findViewById(R.id.gallery_grid);
		subtitle = (TextView) v.findViewById(R.id.subtitle);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final FragmentActivity activity = this.getActivity();
		f = new Functions(this.getActivity());
		//get data
		final String json_gallery_string = getArguments().getString("json_gallery_string");

		//fill categories
		Gallary_Grid_Adapter adapter = new Gallary_Grid_Adapter(activity, json_gallery_string);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int positon, long id) {
				//open image
				Fragment productpage = Fragment.instantiate(context, ImageFragment.class.getName());

				Data.Image[] gallery = f.getGallery(json_gallery_string);
				Bundle args = new Bundle();
				args.putString("imageurl", gallery[positon].imageurl);
				productpage.setArguments(args);

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.add(R.id.realtabcontent, productpage);
				ft.addToBackStack(null);
				ft.commit();

			}
		});

		subtitle.setTextSize(f.w(6));
	}

}
