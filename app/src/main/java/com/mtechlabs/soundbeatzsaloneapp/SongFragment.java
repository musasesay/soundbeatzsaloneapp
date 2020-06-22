package com.mtechlabs.soundbeatzsaloneapp;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SongFragment extends Fragment {
	TextView lyrics, title;
	ImageButton play, stop;
	String soundurl;
	Boolean error = false;
	SeekBar seeker;
	Runnable r = null;
	RelativeLayout player;
	LinearLayout player_parent;
	FragmentActivity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Main.mp = new MediaPlayer();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.songfragment, container, false);
		title = (TextView) v.findViewById(R.id.title);
		lyrics = (TextView) v.findViewById(R.id.lirics);

		play = (ImageButton) v.findViewById(R.id.play);
		stop = (ImageButton) v.findViewById(R.id.stop);
		seeker = (SeekBar) v.findViewById(R.id.seeker);
		player = (RelativeLayout) v.findViewById(R.id.player);
		player_parent = (LinearLayout) v.findViewById(R.id.player_parent);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = this.getActivity();
		Functions f = new Functions(activity);

		//get bundles
		title.setText(Html.fromHtml(getArguments().getString("title")));
		title.setMovementMethod(LinkMovementMethod.getInstance());
		lyrics.setText(Html.fromHtml(getArguments().getString("lyrics")));
		lyrics.setMovementMethod(LinkMovementMethod.getInstance());
		soundurl = getArguments().getString("soundurl");

		//set fonts
		title.setTextSize(f.w(6));
		lyrics.setTextSize(f.w(4));

		if (soundurl.length() > 5) {

			play.getLayoutParams().height = f.h(10);
			play.getLayoutParams().width = f.h(10);
			stop.getLayoutParams().height = f.h(10);
			stop.getLayoutParams().width = f.h(10);

			//play button pressed
			play.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!error) {
						if (Main.prepared) {
							if (Main.mp.isPlaying()) {
								Main.mp.pause();
								play.setBackgroundResource(R.drawable.play);
							} else {
								Main.mp.start();
								play.setBackgroundResource(R.drawable.pause);
							}
						} else {
							Toast.makeText(activity, R.string.AudioNotPrepared, Toast.LENGTH_LONG).show();
						}
					}else{
						Toast.makeText(activity, R.string.Unsupported, Toast.LENGTH_LONG).show();
					}

				}
			});
			//stop button pressed
			stop.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Main.prepared && !error) {
						if (Main.mp.isPlaying()) {
							
							Main.mp.pause();
							Main.mp.seekTo(0);
							play.setBackgroundResource(R.drawable.play);
							seeker.setProgress(0);

						}
					}
				}
			});

			if (isNetworkAvailable()) {
				//load sound and buffer it
				try {
					Main.mp.setDataSource(soundurl);
					Main.mp.prepareAsync();

					Main.mp.setOnErrorListener(new OnErrorListener() {

						@Override
						public boolean onError(MediaPlayer mp, int what, int extra) {
							Toast.makeText(activity, R.string.Unsupported, Toast.LENGTH_LONG).show();
							error = true;
							Main.mp.release();
							return false;
						}

					});

					Main.mp.setOnPreparedListener(new OnPreparedListener() {
						public void onPrepared(MediaPlayer mp) {
							Main.prepared = true;

							//set seeker
							seeker.setMax(mp.getDuration());

						}
					});
					Main.mp.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							play.setBackgroundResource(R.drawable.play);
							mp.pause();
							mp.seekTo(0);
							seeker.setProgress(0);
						}
					});

					//set seeker
					seeker.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
						}

						@Override
						public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
							if (fromUser && Main.prepared) {
								if (Main.mp.isPlaying()) {
									if (progress > 0)
										Main.mp.seekTo(progress);
								}
							}

						}
					});

					//set seeker to follow media player
					Handler handler = new Handler();
					r = new Runnable() {
						@Override
						public void run()
						{
							if (Main.prepared) {
								if (Main.mp.isPlaying()) {
									seeker.setProgress(Main.mp.getCurrentPosition());
								}
							}
							seeker.postDelayed(r, 200);
						}
					};
					handler.postDelayed(r, 1000);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				Toast.makeText(activity, R.string.NoInternet, Toast.LENGTH_LONG).show();

			}
		} else {
			player_parent.removeView(player);
		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
