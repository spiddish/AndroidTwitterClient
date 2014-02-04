package spiddish.androidtwitterclient;

import org.json.JSONObject;

import spiddish.androidtwitterclient.fragments.HomeTimeLineFragment;
import spiddish.androidtwitterclient.fragments.MentionsFragment;
import spiddish.androidtwitterclient.models.User;

import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TimelineActivity extends FragmentActivity implements TabListener {
	private static final int REQUEST_CODE = 20;
	private User appUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
		TwitterApp.getRestClient().getUserInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				Log.d("D", "onSuccess() getUserInfo");
				appUser = User.fromJson(jsonUser);
			}
		});
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimeLineFragment").setIcon(R.drawable.ic_home).setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsFragment").setIcon(R.drawable.ic_mentions).setTabListener(this);
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
		
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
	    case R.id.miProfile:
	    	viewProfile();
	    	Toast.makeText(this, "i wanna view my profile!", Toast.LENGTH_SHORT).show();
	    	break;
	    default:
	    	break;
	    }

	    return true;
	}
	
	private void viewProfile() {
		Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
		startActivity(i);
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
				//fragmentTweets.getAdapter().insert(new Tweet(tweet, appUser), 0);
			Toast.makeText(this, "activity returned: " + tweet, Toast.LENGTH_SHORT).show();
		} 
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = this.getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "HomeTimeLineFragment") {
			fts.replace(R.id.frameContainer, new HomeTimeLineFragment());
		} else {
			fts.replace(R.id.frameContainer, new MentionsFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
