package spiddish.androidtwitterclient;



import spiddish.androidtwitterclient.models.User;

import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeActivity extends Activity {
	private String status;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		User user = (User) getIntent().getSerializableExtra("user");
		setupUserDisplay(user);
	}

	private void setupUserDisplay(User user) {
		ImageView profile = (ImageView) findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(user.getProfileBackgroundImageUrl(), profile);
		TextView name = (TextView) findViewById(R.id.tvName);
		name.setText(Html.fromHtml(user.getName()));
		Toast.makeText(this, "setupUserDisplay()", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra("tweet", status);
		setResult(RESULT_OK, data);
		super.finish();
	}
	
	public void onPostTweet(View v) {
		EditText tweet = (EditText) findViewById(R.id.etTweet);
		status = tweet.getText().toString();
		if (!status.equals(""))
			TwitterApp.getRestClient().postStatus(status, new JsonHttpResponseHandler());
		finish();
	}
	
	public void onCancelTweet(View v) {
		status = "";
		finish();
	}

}
