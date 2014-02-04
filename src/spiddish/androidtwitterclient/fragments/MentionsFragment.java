package spiddish.androidtwitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import spiddish.androidtwitterclient.TwitterApp;
import spiddish.androidtwitterclient.models.Tweet;
import android.os.Bundle;

import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int id, JSONArray jsonTweet) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweet);
				getAdapter().addAll(tweets);
			}			
		});
	}
}
