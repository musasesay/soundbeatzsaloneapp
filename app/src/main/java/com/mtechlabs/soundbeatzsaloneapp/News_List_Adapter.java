package com.mtechlabs.soundbeatzsaloneapp;

import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class News_List_Adapter extends ArrayAdapter<Object> {
	FragmentActivity activity;
	String json_categories_string;
	Data.News[] news;

	public News_List_Adapter(FragmentActivity activity, Data.News[] news) {
		super(activity, 0);
		this.activity = activity;
		this.news = news;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = activity.getLayoutInflater();
		convertView = inflator.inflate(R.layout.news_item, null);

		TextView news_title = (TextView) convertView.findViewById(R.id.news_title);
		TextView news_news = (TextView) convertView.findViewById(R.id.news_news);
		news_title.setText(Html.fromHtml(news[position].title));
		news_news.setText(Html.fromHtml((news[position].news.length()>100)?(news[position].news.substring(0, 100)+"..."):(news[position].news) ));

		Functions f = new Functions(activity);
		news_title.setTextSize(f.w(6));
		news_news.setTextSize(f.w(4));

		return convertView;
	}

	@Override
	public int getCount() {
		return news.length;
	}

	@Override
	public Data.News getItem(int position) {
		return news[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
