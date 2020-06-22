package com.mtechlabs.soundbeatzsaloneapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsPageFragment extends Fragment {
	TextView news, title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.newspagefragment, container, false);
		title = (TextView) v.findViewById(R.id.title);
		news = (TextView) v.findViewById(R.id.news);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentActivity activity = this.getActivity();
		Functions f = new Functions(activity);

		title.setText(Html.fromHtml(getArguments().getString("title")));
		title.setMovementMethod(LinkMovementMethod.getInstance());
		news.setText(Html.fromHtml(getArguments().getString("news")));
		news.setMovementMethod(LinkMovementMethod.getInstance());
		
		//set fonts
		title.setTextSize(f.w(6));
		news.setTextSize(f.w(4));
	}

}
