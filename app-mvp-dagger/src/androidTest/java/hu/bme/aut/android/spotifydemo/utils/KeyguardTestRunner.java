package hu.bme.aut.android.spotifydemo.utils;

import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

public class KeyguardTestRunner extends android.support.test.runner.AndroidJUnitRunner {
	private PowerManager.WakeLock mWakeLock;

	@Override
	public void callApplicationOnCreate(Application app) {
		// Unlock the screen
		KeyguardManager keyguard = (KeyguardManager) app.getSystemService(Context.KEYGUARD_SERVICE);
		keyguard.newKeyguardLock(getClass().getSimpleName()).disableKeyguard();

		// Start a wake lock
		PowerManager power = (PowerManager) app.getSystemService(Context.POWER_SERVICE);
		mWakeLock = power.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, getClass().getSimpleName());
		mWakeLock.acquire();

		super.callApplicationOnCreate(app);
	}

	@Override
	public void onDestroy() {
		mWakeLock.release();

		super.onDestroy();
	}
}