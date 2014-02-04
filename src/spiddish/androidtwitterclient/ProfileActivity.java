package spiddish.androidtwitterclient;

import org.json.JSONObject;

import spiddish.androidtwitterclient.fragments.UserTimeLineFragment;
import spiddish.androidtwitterclient.models.User;

import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		String screenName = null;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
			screenName = bundle.getString("screen_name");
		loadProfileInfo(screenName);
		loadUserTweets(screenName);
	}

	private void loadUserTweets(String screenName) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.frameProfileContainer, UserTimeLineFragment.newInstance(screenName));
		ft.commit();
	}

	private void loadProfileInfo(String screenName) {
		TwitterApp.getRestClient().getMyInfo(screenName, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				Log.d("D", "onSuccess() getMyInfo");
				User u = User.fromJson(jsonUser);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(u);
			}
		});
	}
	
	private void populateProfileHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(u.getName());
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		tvTagline.setText(u.getTagline());
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowers.setText(u.getFollowersCount() + " followers");
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);	
		tvFollowing.setText(u.getFriendsCount() + " following");
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		ImageLoader.getInstance().displayImage(u.getProfileBackgroundImageUrl(), ivProfileImage);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
