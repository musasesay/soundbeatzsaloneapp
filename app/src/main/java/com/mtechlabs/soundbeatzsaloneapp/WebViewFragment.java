package com.mtechlabs.soundbeatzsaloneapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class WebViewFragment extends Fragment {
	LinearLayout layout;
	Activity activity;

	Typeface colab;
	Typeface colab_bold;

	//create new details fragment
	public static WebViewFragment newInstance(String type) {
		WebViewFragment f = new WebViewFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putString("type", type);
		f.setArguments(args);

		return f;
	}

	public String getShownType() {
		return getArguments().getString("type");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		this.activity = getActivity();

		WebView webView = new WebView(activity);
		webView.setBackgroundColor(0);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(getArguments().getString("type"));
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setBackgroundColor(getResources().getColor(R.color.white));
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_UP:
					//The key is this line. 
					v.requestFocusFromTouch();
					break;
				}
				return false;
			}
		});

		if (getArguments().getString("type").contains("youtube")) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getArguments().getString("type"))));
		} else {
			webView.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});
		}

		return webView;
	}

}