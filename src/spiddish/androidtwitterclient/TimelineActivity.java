package spiddish.androidtwitterclient;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import spiddish.androidtwitterclient.models.Tweet;
import spiddish.androidtwitterclient.models.User;

import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class TimelineActivity extends Activity {
	private static final int REQUEST_CODE = 20;
	private User appUser;
	private TweetsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		TwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweet) {
				Log.d("D", "onSuccess() getHomeTimeline");
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweet);
				ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
				adapter = new TweetsAdapter(getBaseContext(), R.id.lvTweets, tweets);
				lvTweets.setAdapter(adapter);
				Log.d("D", jsonTweet.toString());
			}

			@Override
			protected void handleFailureMessage(Throwable arg0, String arg1) {
				Log.d("D", "fail exception");
			}

			@Override
			public void onFailure(Throwable arg0, JSONArray arg1) {
				Log.d("D", "fail1");
			}

			@Override
			public void onFailure(Throwable arg0, JSONObject arg1) {
				Log.d("D", "fail2");
			}

			@Override
			public void onSuccess(int arg0, JSONArray arg1) {
				Log.d("D", "success1");
			}

			@Override
			public void onSuccess(int arg0, JSONObject arg1) {
				Log.d("D", "success2");
			}

			@Override
			public void onSuccess(JSONObject arg0) {
				Log.d("D", "success3");
			}
			
		});
		TwitterApp.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				Log.d("D", "onSuccess() getUserInfo");
				appUser = User.fromJson(jsonUser);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.miCompose:
	    	composeTweet();
	    	Toast.makeText(this, "i wanna compose!", Toast.LENGTH_SHORT).show();
	    	break;
	    default:
	    	break;
	    }

	    return true;
	}
	
	private void composeTweet() {
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
		i.putExtra("blah", true);
		i.putExtra("user", appUser);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			String tweet = data.getStringExtra("tweet");
			//if (!tweet.equals(""))
				//adapter.insert(new Tweet(tweet, appUser), 0);
			Toast.makeText(this, "activity returned: " + tweet, Toast.LENGTH_SHORT).show();
		} 
	}

}
