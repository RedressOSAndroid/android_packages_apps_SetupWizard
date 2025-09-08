/*
 * SPDX-FileCopyrightText: 2025
 * SPDX-License-Identifier: Apache-2.0
 */
package org.lineageos.setupwizard;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
	private static final String TAG = "MusicService";
	private MediaPlayer mediaPlayer;

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			mediaPlayer = MediaPlayer.create(this, R.raw.redress_setup_activity);
			if (mediaPlayer != null) {
				mediaPlayer.setLooping(true);
				mediaPlayer.start();
				Log.d(TAG, "Setup music started.");
			}
		} catch (Exception e) {
			Log.w(TAG, "Failed to start setup music", e);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// We don’t restart if the system kills it
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			if (mediaPlayer != null) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
				}
				mediaPlayer.release();
				mediaPlayer = null;
			}
			Log.d(TAG, "Setup music stopped.");
		} catch (Exception e) {
			Log.w(TAG, "Failed to stop setup music", e);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null; // Not a bound service
	}
}

