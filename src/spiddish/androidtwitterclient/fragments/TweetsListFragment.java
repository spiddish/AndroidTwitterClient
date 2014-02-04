package spiddish.androidtwitterclient.fragments;

import java.util.ArrayList;

import spiddish.androidtwitterclient.TweetsAdapter;
import spiddish.androidtwitterclient.models.Tweet;

import com.codepath.apps.restclienttemplate.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TweetsListFragment extends Fragment {
	private TweetsAdapter adapter;
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d("D", "onActivityCreated()");
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		ListView lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
		adapter = new TweetsAdapter(getActivity(), 1, tweets);
		lvTweets.setAdapter(adapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tweets_list, container, false);
	}

}
