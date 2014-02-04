package spiddish.androidtwitterclient;

import java.util.List;

import com.codepath.apps.restclienttemplate.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import spiddish.androidtwitterclient.models.Tweet;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, int resource, List<Tweet> objects) {
		super(context, resource, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		
		Tweet tweet = this.getItem(position);
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfilePhoto);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String screenName = v.getTag().toString();
				Intent i = new Intent(v.getContext(), ProfileActivity.class);
				i.putExtra("screen_name", screenName);
				v.getContext().startActivity(i);
				Toast.makeText(v.getContext(), "click user: " + screenName, Toast.LENGTH_SHORT).show();
			}	
		});
		imageView.setTag(tweet.getUser().getScreenName());
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileBackgroundImageUrl(), imageView);
		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + "<small><font color='#777777'>@" + tweet.getUser().getScreenName() + "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		return view;
	}
}
