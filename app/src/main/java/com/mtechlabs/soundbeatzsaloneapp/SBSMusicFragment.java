package com.mtechlabs.soundbeatzsaloneapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class SBSMusicFragment extends Fragment {
	ListView music_list;
	FragmentActivity activity;
	Data.Song[] songs;
	Functions f;
	TextView subtitle;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.musicfragment, container, false);

		music_list = (ListView) v.findViewById(R.id.music_list);
		subtitle = (TextView) v.findViewById(R.id.subtitle);


		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = this.getActivity();
		f = new Functions(activity);

		//set fonts
		subtitle.setTextSize(f.w(6));
		

		//get data
		final String json_songs_string = getArguments().getString("json_songs_string");

//		songs = f.getSongs(json_songs_string);
		//fill music
		Music_List_Adapter adapter = new Music_List_Adapter(activity, songs);
		music_list.setAdapter(adapter);
		music_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//go to music playing page
				Fragment playpage = Fragment.instantiate(activity, SongFragment.class.getName());

				// Supply song data as an argument.
				Bundle args = new Bundle();
				args.putString("title", songs[position].title);
				args.putString("lyrics", songs[position].lyrics);
				args.putString("imageurl", songs[position].imageurl);
				args.putString("soundurl", songs[position].soundurl);
				playpage.setArguments(args);

				FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
				ft.add(R.id.realtabcontent, playpage);
				ft.commit();
				
				
			}
		});

	}
	
	

}
