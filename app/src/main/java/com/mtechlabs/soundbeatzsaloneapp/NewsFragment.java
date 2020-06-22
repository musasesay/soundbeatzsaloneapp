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

public class NewsFragment extends Fragment {
	ListView news_list;
	FragmentActivity activity;
	Data.News[] news;
	Functions f;
	TextView subtitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.newsfragment, container, false);

		news_list = (ListView) v.findViewById(R.id.news_list);
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
		final String json_news_string = getArguments().getString("json_news_string");
		
		news = f.getNews(json_news_string);
		//fill news
		News_List_Adapter adapter = new News_List_Adapter(activity, news);
		news_list.setAdapter(adapter);
		news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//go to news page
				Fragment newspage = Fragment.instantiate(activity, NewsPageFragment.class.getName());

				// Supply news data as an argument.
				Bundle args = new Bundle();
				args.putString("title", news[position].title);
				args.putString("news", news[position].news);
				newspage.setArguments(args);

				FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
				ft.add(R.id.realtabcontent, newspage);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

	}

}
