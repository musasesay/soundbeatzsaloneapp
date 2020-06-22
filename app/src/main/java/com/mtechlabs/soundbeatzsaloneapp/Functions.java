package com.mtechlabs.soundbeatzsaloneapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.util.DisplayMetrics;

public class Functions {
	Activity activity;
	Data data = new Data();

	Functions(Activity activity) {
		this.activity = activity;
	}

	//used to make sure text is good on all devices..percentage from screen width
	public int w(double d) {
		//get resolution
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int px = (int) ((float) dm.widthPixels * ((float) d / 100));
		float dp = px / dm.density;
		return (int) dp;
	}

	//percentage from screen height
	public int h(double d) {
		//get resolution
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int px = (int) ((float) dm.heightPixels * ((float) d / 100));
		float dp = px / dm.density;
		return (int) dp;
	}
	public int h_px(double d) {
		//get resolution
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int px = (int) ((float) dm.heightPixels * ((float) d / 100));
		return (int) px;
	}



	//decode songs data
	public Data.Song[] getSongs(String json_songs_string) {
		JSONArray json_songs;
		JSONObject json_song;

		try {
			json_songs = new JSONArray(json_songs_string);
		} catch (JSONException e) {
			return null;

		}
		Data.Song[] songs = new Data.Song[json_songs.length()];

		//loop through all songs in json and put them in array
		for (int i = 0; i < json_songs.length(); i++) {
			try {
				json_song = (JSONObject) json_songs.get(i);
				songs[i] = data.new Song();
				songs[i].title = json_song.getString("title");
				songs[i].lyrics = json_song.getString("lirics");
				songs[i].imageurl = json_song.getString("imageurl");
				songs[i].soundurl = json_song.getString("soundurl");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return songs;
	}

	//decode gallery data
	public Data.Image[] getGallery(String json_gallery_string) {
		JSONArray json_gallery;
		JSONObject json_image;

		try {
			json_gallery = new JSONArray(json_gallery_string);
		} catch (JSONException e) {
			return null;

		}
		Data.Image[] gallery = new Data.Image[json_gallery.length()];

		//loop through all songs in json and put them in array
		for (int i = 0; i < json_gallery.length(); i++) {
			try {
				json_image = (JSONObject) json_gallery.get(i);
				gallery[i] = data.new Image();
				gallery[i].imageurl = json_image.getString("imageurl");
				gallery[i].smallimageurl = json_image.getString("smallimageurl");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return gallery;
	}

	//decode news data
	public Data.News[] getNews(String json_news_string) {
		JSONArray json_news;
		JSONObject json_new;

		try {
			json_news = new JSONArray(json_news_string);
		} catch (JSONException e) {
			return null;

		}
		Data.News[] news = new Data.News[json_news.length()];

		//loop through all songs in json and put them in array
		for (int i = 0; i < json_news.length(); i++) {
			try {
				json_new = (JSONObject) json_news.get(i);
				news[i] = data.new News();
				news[i].title = json_new.getString("title");
				news[i].news = json_new.getString("news");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return news;
	}

}
