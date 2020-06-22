package com.mtechlabs.soundbeatzsaloneapp;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class MainFragment extends Fragment {

	RelativeLayout[] bar = new RelativeLayout[3];
	ImageView[] social_image = new ImageView[3];
	HorizontalScrollView home_picture_scrolview;
	ImageView home_picture;
	Handler handler = new Handler();
	int dir = 0;
	Timer scrollTimer;
	FragmentActivity activity;
	LayoutParams params;
	Functions f;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.mainfragment, container, false);

		bar[0] = (RelativeLayout) v.findViewById(R.id.bar_1);
		bar[1] = (RelativeLayout) v.findViewById(R.id.bar_2);
		bar[2] = (RelativeLayout) v.findViewById(R.id.bar_3);
		social_image[0] = (ImageView) v.findViewById(R.id.facebook);
		social_image[1] = (ImageView) v.findViewById(R.id.google_plus);
		social_image[2] = (ImageView) v.findViewById(R.id.twitter);
		home_picture_scrolview = (HorizontalScrollView) v.findViewById(R.id.home_picture_scrolview);
		home_picture = (ImageView) v.findViewById(R.id.home_picture);

		//auto scroll feature
		TimerTask scrollerSchedule = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						dir++;
						if (dir > 1000)
							dir = 0;
						home_picture_scrolview.smoothScrollBy((dir > 500) ? (-1) : (1), 0);
					}
				});
			}
		};
		scrollTimer = new Timer();
		scrollTimer.schedule(scrollerSchedule, 0, 50);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = this.getActivity();
		f = new Functions(activity);
		for (int i = 0; i < 3; i++) {
			final int pos = i;
			//set heights of social media bars
			params = bar[i].getLayoutParams();
			params.height = f.h(15);
			params = bar[i].getLayoutParams();
			params.width = f.h(15);

			social_image[i].setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						bar[pos].setBackgroundColor(activity.getResources().getColor(R.color.white));
						social_image[pos].setBackgroundColor(activity.getResources().getColor(R.color.white));
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						if (pos == 0) {
							bar[pos].setBackgroundColor(activity.getResources().getColor(R.color.green));
							social_image[pos].setBackgroundColor(activity.getResources().getColor(R.color.green));
						}
						if (pos == 1) {
							bar[pos].setBackgroundColor(activity.getResources().getColor(R.color.yellow));
							social_image[pos].setBackgroundColor(activity.getResources().getColor(R.color.yellow));
						}
						if (pos == 2) {
							bar[pos].setBackgroundColor(activity.getResources().getColor(R.color.pink));
							social_image[pos].setBackgroundColor(activity.getResources().getColor(R.color.pink));
						}
					}
					return false;
				}
			});
		}

		ConnectivityManager connMgr = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			// fetch data
//			mWebView.loadUrl(Config.WEBSITE_URL);

			// Enable Javascript
//			WebSettings webSettings = mWebView.getSettings();
//			webSettings.setJavaScriptEnabled(true);

			//facebook clicked
			social_image[0].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//open image
					WebViewFragment WebView = WebViewFragment.newInstance(activity.getResources().getString(R.string.FacebookUrl));
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.add(R.id.realtabcontent, WebView);
					ft.addToBackStack(null);
					ft.commit();
				}
			});

			// Force links and redirects to open in the WebView instead of in a browser
//			mWebView.setWebViewClient(new WebViewClient());
		} else {
			Toast.makeText(getContext(),
					"Sorry, No internet connectivity found", Toast.LENGTH_SHORT)
					.show();
		}



		//googleplus clicked
		social_image[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//open image
				WebViewFragment WebView = WebViewFragment.newInstance(activity.getResources().getString(R.string.GooglePlusUrl));
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.add(R.id.realtabcontent, WebView);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		//twitter clicked
		social_image[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//open image
				WebViewFragment WebView = WebViewFragment.newInstance(activity.getResources().getString(R.string.TwitterUrl));
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.add(R.id.realtabcontent, WebView);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

	}

	@Override
	public void onStop() {
		super.onStop();
		scrollTimer.cancel();
	}

}
