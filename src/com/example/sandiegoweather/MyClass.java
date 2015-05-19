package com.example.sandiegoweather;

import android.support.v7.app.ActionBarActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MyClass extends ActionBarActivity {
	private MyBroadcastReceiver receiver;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.forecast, menu);
        iniciarBotoews();
        return true;
    }

    private void iniciarBotoews() {
    	Button asynctaskButton = (Button)findViewById(R.id.button_asynctask);
		Button intentserviceButton = (Button)findViewById(R.id.button_intentservice);
		asynctaskButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyAsyncTask at = new MyAsyncTask(v.getContext());
				at.execute();
			}
		});
		
		intentserviceButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent msgIntent = new Intent(v.getContext(), MyIntentService.class);
				startService(msgIntent);
				IntentFilter filter = new IntentFilter(MyBroadcastReceiver.ACTION_RESP);
		        filter.addCategory(Intent.CATEGORY_DEFAULT);
		        receiver = new MyBroadcastReceiver();
		        registerReceiver(receiver, filter);
			}
		});
		
		Button page2Button = (Button)findViewById(R.id.button_page2);
		page2Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MyClass2.class);
				startActivity(intent);
			}
		});
	}


	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	public class MyAsyncTask extends AsyncTask<Void, Void, String>
	{
		private Context mContext;
		public MyAsyncTask(Context context)
		{
			this.mContext = context;
		}
		@Override
		protected void onPreExecute() {
		    super.onPreExecute();

		}

		@Override
		protected String doInBackground(Void... params) {
			return new EndpointConection().GetEndpoint();
		}
		
		@Override
		protected void onPostExecute(String result) {
			((TextView)findViewById(R.id.textAsyncTask)).setText(result);
			 Vibrator v = (Vibrator) this.mContext.getSystemService(Context.VIBRATOR_SERVICE);
			 // Vibrate for 500 milliseconds
			 v.vibrate(500);
		}
	}
	
	public class MyBroadcastReceiver extends BroadcastReceiver {

		public static final String ACTION_RESP = "Response";

		@Override
		public void onReceive(Context context, Intent intent) {
			((TextView)findViewById(R.id.textIntentService)).setText(intent.getStringExtra(MyIntentService.PARAM_OUT));

		}

	}
}
