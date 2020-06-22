package com.mtechlabs.soundbeatzsaloneapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class Main extends FragmentActivity {

	public static FragmentTabHost mTabHost;
	TextView band_title;
	FragmentActivity activity;
	String json_songs_string;
	String json_gallery_string;
	String json_news_string;
	Functions f;
	static MediaPlayer mp = new MediaPlayer();
	static Boolean prepared = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		//force portrait and no title
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//set xml and uI
		setContentView(R.layout.main);
		f = new Functions(this);

		//load resources
		mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		band_title = (TextView) findViewById(R.id.band_title);

		//set font sizes
		band_title.setTextSize(f.w(8));

		//set tabs
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.getTabWidget().setStripEnabled(false);

		mTabHost.addTab(mTabHost.newTabSpec("Home").setIndicator("", this.getResources().getDrawable(R.drawable.home)), MainFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("Music").setIndicator("", this.getResources().getDrawable(R.drawable.music)), ConnectMusicFragment.class, null);
		mTabHost.getCurrentTabView().setBackgroundColor(Color.parseColor(getResources().getString(R.color.blue)));

//		ConnectivityManager connectivityManager
//				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		// return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//		if (activeNetworkInfo != null && activeNetworkInfo.isConnected()){
////			GetAllDetails getalldetails = new GetAllDetails();
////			getalldetails.execute();
////
//		}
//		else {
//			Alert alert = new Alert();
//			alert.set(getString(R.string.ServerNotFound), activity, false);
////            alert.show(getSupportFragmentManager(), getString(R.string.ServerNotFound));
//			// String error = getException().getMessage();
//			Toast.makeText(Main.this, " No Internet Avalable " , Toast.LENGTH_LONG).show();
//			//	loadPref();
//
//		}

		//load data
		GetAllDetails getalldetails = new GetAllDetails();
		getalldetails.execute();
	}

	//get details from server
	class GetAllDetails extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			//get menu data from server
			JSONObject json = null;

			Server server = new Server(activity);
			json = server.getAllDetails();
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			//check for server feedback
			try {
				if (json != null) {
					if (json.getString("success") != null) {
						String res = json.getString("success");
						if (Integer.parseInt(res) == 1) {
							// successfully obtained menu data
//							JSONArray json_songs = json.getJSONArray("songs");
							JSONArray json_gallery = json.getJSONArray("images");
							JSONArray json_news = json.getJSONArray("news");
//							json_songs_string = json_songs.toString();
							json_gallery_string = json_gallery.toString();
							json_news_string = json_news.toString();

							//save all data in shared preferences in case there's no internet connection next time.
							if (activity != null) {
								SharedPreferences pref = activity.getSharedPreferences(getString(R.string.UniquePreferenceName), 0);
								SharedPreferences.Editor pref_editor = pref.edit();
//								pref_editor.putString("json_songs_string", json_songs_string);
								pref_editor.putString("json_gallery_string", json_gallery_string);
								pref_editor.putString("json_news_string", json_news_string);
								pref_editor.commit();
								updateUI();
							}

						} else {
							//Server error
							Alert alert = new Alert();
							alert.set(getString(R.string.ServerError), activity, false);
							alert.show(getSupportFragmentManager(), getString(R.string.ServerError));
							loadPref();
						}
					} else {
						//Server error
						Alert alert = new Alert();
						alert.set(getString(R.string.ServerError), activity, false);
						alert.show(getSupportFragmentManager(), getString(R.string.ServerError));
						loadPref();
					}
				} else {
					//error connecting
					Alert alert = new Alert();
					alert.set(getString(R.string.ServerNotFound), activity, false);
					alert.show(getSupportFragmentManager(), getString(R.string.ServerNotFound));
					loadPref();
				}

			} catch (JSONException e) {
				//phrasing error
				Alert alert = new Alert();
				alert.set(getString(R.string.CorruptedData), activity, false);
				alert.show(getSupportFragmentManager(), getString(R.string.CorruptedData));
				loadPref();
			}

		}

	}

	public void loadPref() {
		SharedPreferences pref = activity.getSharedPreferences(getString(R.string.UniquePreferenceName), 0);
//		json_songs_string = pref.getString("json_songs_string", "null");
		json_gallery_string = pref.getString("json_gallery_string", "null");
		json_news_string = pref.getString("json_news_string", "null");
		updateUI();

	}

	//ui-------------------------------------------
	public void updateUI() {

		//prepare data bundle
		Bundle b = new Bundle();

		//create tabs and send bundles
//		b.putString("json_songs_string", json_songs_string);
//		mTabHost.addTab(mTabHost.newTabSpec("Music").setIndicator("", this.getResources().getDrawable(R.drawable.music)), MusicFragment.class, b);
		b.putString("json_gallery_string", json_gallery_string);
		mTabHost.addTab(mTabHost.newTabSpec("Gallery").setIndicator("", this.getResources().getDrawable(R.drawable.gallery)), GalleryFragment.class, b);
		b.putString("json_news_string", json_news_string);
		mTabHost.addTab(mTabHost.newTabSpec("News").setIndicator("", this.getResources().getDrawable(R.drawable.news)), NewsFragment.class, b);

		for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++) {
			//set padding
			mTabHost.getTabWidget().getChildAt(i).setPadding(mTabHost.getHeight() / 5, mTabHost.getHeight() / 5, mTabHost.getHeight() / 5, mTabHost.getHeight() / 5);
			//set all colors to white
			mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor(getResources().getString(R.color.white)));
		}

		mTabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTabHost.setCurrentTab(1);
				mTabHost.setCurrentTab(0);
			}
		});
		mTabHost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTabHost.setCurrentTab(0);
				mTabHost.setCurrentTab(1);

			}
		});
		mTabHost.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTabHost.setCurrentTab(0);
				mTabHost.setCurrentTab(2);

			}
		});
//		mTabHost.getTabWidget().getChildAt(3).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				mTabHost.setCurrentTab(0);
//				mTabHost.setCurrentTab(3);
//			}
//		});

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String str) {

				mTabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor(getResources().getString(R.color.white)));
				mTabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor(getResources().getString(R.color.white)));
				mTabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor(getResources().getString(R.color.white)));
//				mTabHost.getTabWidget().getChildAt(3).setBackgroundColor(Color.parseColor(getResources().getString(R.color.white)));
				mTabHost.getCurrentTabView().setBackgroundColor(Color.parseColor(getResources().getString(R.color.blue)));

				if (prepared == true) {
					prepared = false;
					if (Main.mp != null) {
						if (Main.mp.isPlaying()) {
							Main.mp.stop();
							Main.mp.release();
						}
					}
				}

			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (prepared == true) {
			prepared = false;
			if (Main.mp != null) {
				if (Main.mp.isPlaying()) {
					Main.mp.stop();
					Main.mp.release();
				}
			}
		}

	}
}
