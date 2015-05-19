package com.example.sandiegoweather;

import com.example.sandiegoweather.MyClass.MyBroadcastReceiver;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class MyIntentService extends IntentService {
	public static final String PARAM_OUT = "p_out";
	public static final String PARAM_IN = "p_in";
	public MyIntentService() {
		super("WeatherIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(MyBroadcastReceiver.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(PARAM_OUT, new EndpointConection().GetEndpoint());
		Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		 // Vibrate for 500 milliseconds
		 v.vibrate(500);
		sendBroadcast(broadcastIntent);
	}

}
