package spiddish.androidtwitterclient.fragments;

import java.util.ArrayList;
import org.json.JSONArray;
import spiddish.androidtwitterclient.TwitterApp;
import spiddish.androidtwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import android.os.Bundle;

public class HomeTimeLineFragment extends TweetsListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int id, JSONArray jsonTweet) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweet);
				getAdapter().addAll(tweets);
			}			
		});
	}
	
	public void addTweet(Tweet tweet) {
		this.getAdapter().insert(tweet, 0);
	}
}
