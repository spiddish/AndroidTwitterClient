package spiddish.androidtwitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "Azyd93VEqX8QjpuFhLYFYg";       // Change this
    public static final String REST_CONSUMER_SECRET = "THoOtyqOh68fmM7CGBa0Z4cLiLIw0ZpqbMwlFA1w"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://spiddishtweets"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", "25");
        client.get(apiUrl, params, handler);
        Log.d("D", "getHomeTimeline(): " + apiUrl);
    }
 
    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", "25");
        if (screenName != null)
        	params.put("screen_name", screenName);
        client.get(apiUrl, params, handler);
        Log.d("D", "getUserTimeline(): " + apiUrl);
    }
    
    public void getMentions(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", "25");
        client.get(apiUrl, params, handler);
        Log.d("D", "getMentions(): " + apiUrl);
    }
    
    public void getMyInfo(String screenName, AsyncHttpResponseHandler handler) {
    	if (screenName == null) {
    		Log.d("D", "screenName null, calling getUserInfo");
    		getUserInfo(handler);
    		return;
    	}
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName); // find a way of figuring out the user
        params.put("include_entities", "false");
        client.get(apiUrl, params, handler);
        Log.d("D", "getMyInfo(): " + apiUrl);
    }
    
    public void getUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        client.get(apiUrl, null, handler);
        Log.d("D", "getUserInfo(): " + apiUrl);
    }
    
    public void postStatus(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
        client.post(apiUrl, params, handler);
        Log.d("D", "postStatus()");
    } 
    
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}