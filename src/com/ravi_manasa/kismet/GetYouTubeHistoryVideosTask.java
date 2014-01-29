package com.ravi_manasa.kismet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;



/**
 * This is the task that will ask YouTube for a list of videos for a specified user</br>
 * This class implements Runnable meaning it will be ran on its own Thread</br>
 * Because it runs on it's own thread we need to pass in an object that is notified when it has finished
 *
 * @author ravi_manasa
 */
public class GetYouTubeHistoryVideosTask implements Runnable {
	// A reference to retrieve the data when this task finishes
	public static final String RELATEDLIBRARY = "Library";
	// A handler that will be notified when the task is finished
	private final Handler relatedReplyTo;
	// The user we are querying on YouTube for videos
	//private final String videoId;
	private HashMap<String, String> hmap ;
	/**
	 * Don't forget to call run(); to start this task
	 * @param replyTo - the handler you want to receive the response when this task has finished
	 * @param videoId - the videoId of YouTube video
	 */
	public GetYouTubeHistoryVideosTask(Handler relatedReplyTo, HashMap hmap) {
		this.relatedReplyTo = relatedReplyTo;
		this.hmap = hmap;
		//this.videoId = videoId;
		
	}
	
	@Override
	public void run() {
		try {
			// Get a httpclient to talk to the internet
			HttpClient client = new DefaultHttpClient();
			// Perform a GET request to YouTube for a JSON list of all the videos by a specific user
			String url_request_rel;
		
			List<Video> relatedvideos = new ArrayList<Video>();
			   Map<String,String> sortedMap=sortByComparator(hmap);

		        for (Map.Entry entry : sortedMap.entrySet()) 
		        {
			  
			   // do something
			
			 
				  
				 
				   url_request_rel = "https://gdata.youtube.com/feeds/api/videos/"+entry.getValue()+"?v=2&alt=jsonc";
					HttpUriRequest request_rel = new HttpGet(url_request_rel);
					HttpResponse response_rel = client.execute(request_rel);
					// Convert this response into a readable string
					String jsonString_rel = StreamUtils.convertToString(response_rel.getEntity().getContent());
					// Create a JSON object that we can use from the String 
					JSONObject json_rel = new JSONObject(jsonString_rel);
					JSONObject data =json_rel.getJSONObject("data");						
					String id_rel = data.optString("id");			
					String title_rel = " "+data.optString("title") + "\n\n Likes: " +data.optString("likeCount")+ "\n Views: " + data.optString("viewCount");

					
					String url_rel;
					try {
						url_rel = data.getJSONObject("player").getString("mobile");
					} catch (JSONException ignore) {
						url_rel = data.getJSONObject("player").getString("default");
					}
					String thumbUrl_rel = data.getJSONObject("thumbnail").getString("hqDefault");
					Log.e(id_rel);
					// Create the video object and add it to our list
					relatedvideos.add(new Video(title_rel, url_rel, thumbUrl_rel, id_rel));
				}

				

			

			Library relatedlib = new Library("asdf", relatedvideos);
			// Pack the Library into the bundle to send back to the Activity

			Bundle reldata = new Bundle();
			reldata.putSerializable(RELATEDLIBRARY, relatedlib);

			
			Message relmsg = Message.obtain();
			relmsg.setData(reldata);//we call the populateListWithVideos here ************ imp
			relatedReplyTo.sendMessage(relmsg);

		// We don't do any error catching, just nothing will happen if this task falls over
		// an idea would be to reply to the handler with a different message so your Activity can act accordingly
		} catch (ClientProtocolException e) {
			Log.e("Feck", e);
		} catch (IOException e) {
			Log.e("Feck", e);
		} catch (JSONException e) {
			Log.e("Feck", e);
		}
	}
	  private static Map sortByComparator(Map unsortMap)
	   {
	        List list=new LinkedList(unsortMap.entrySet());
	        //sort list based on comparator
	        Collections.sort(list,new Comparator()
	        {
	             public int compare(Object o1,Object o2)
	             {
	     return ((Comparable)((Map.Entry)(o2)).getKey()).compareTo(((Map.Entry) (o1)).getKey());
	             }
	        });

	        //put sorted list into map again
	    Map sortedMap=new LinkedHashMap();
	    for (Iterator it=list.iterator();it.hasNext();)
	    {
	         Map.Entry entry=(Map.Entry)it.next();
	         sortedMap.put(entry.getKey(),entry.getValue());
	    }
	    return sortedMap;
	   }   
	
}
