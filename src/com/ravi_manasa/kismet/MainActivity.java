package com.ravi_manasa.kismet;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;


/**
 * The Activity can retrieve Videos for a specific username from YouTube</br>
 * It then displays them into a list including the Thumbnail preview and the title</br>
 * There is a reference to each video on YouTube as well but this isn't used in this tutorial</br>
 * </br>
 * <b>Note<b/> orientation change isn't covered in this tutorial, you will want to override
 * onSaveInstanceState() and onRestoreInstanceState() when you come to this
 * </br>
 * @author ravi_manasa
 */
public class MainActivity extends Activity 
	{
    // A reference to our list that will hold the video details
	private VideosListView listView;
	private VideosListView2 relatedListView;
	private Handler mHandler = new Handler();
	private int rnd;
	private HashMap<String, String> hmap = new HashMap<String, String>() ;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_main2);
        Button bt = (Button)findViewById(R.id.roll);
        bt.performClick();               
       
        
        listView = (VideosListView) findViewById(R.id.videosListView);
        relatedListView = (VideosListView2) findViewById(R.id.relatedVideosListView);
    	listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Video selection = (Video)parent.getItemAtPosition(position)
						;
				
				 
				Intent intent = new Intent(context, MainActivity2.class);
				Bundle b = new Bundle();
				
				
				/*String selection = parent.getItemAtPosition(position)
						.toString();
				WebLinks testLink = (WebLinks) parent.getItemAtPosition(position);
			
				
				WebLinks w = db.getLink(selection);
*/
				b.putString("key", selection.getid());

				intent.putExtras(b);

				startActivity(intent);
			}
			
		});
    	relatedListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Video selection = (Video)parent.getItemAtPosition(position)
						;
				
				 
				Intent intent = new Intent(context, MainActivity2.class);
				Bundle b = new Bundle();
				
				
				/*String selection = parent.getItemAtPosition(position)
						.toString();
				WebLinks testLink = (WebLinks) parent.getItemAtPosition(position);
			
				
				WebLinks w = db.getLink(selection);
*/
				b.putString("key", selection.getid());

				intent.putExtras(b);

				startActivity(intent);
			}
			
		});
    }    

    // This is the XML onClick listener to retreive a users video feed
    public void getUserYouTubeFeed(View v){
    	// We start a new task that does its work on its own thread
    	// We pass in a handler that will be called when the task has finished
    	// We also pass in the name of the user we are searching YouTube for
    	
    	String[] users = {"sribalajimovies", "shalimarcinema", "rajshritelugu", "thesantoshvideos",  
    			"sribalajimovies", 
    			"geethaarts","idreammovies", "sribalajimovies", "thesantoshvideos",  
    			"geethaarts", "shemarootelugu", "adityacinema", "sribalajimovies",  
    			"mangoVideos", "thesantoshvideos",
    			 "newvolgavideo",  "geethaarts",  "rajshritelugu", "shalimarcinema", 
    			 "thecinecurrytelugu", "rajshritelugu", "sribalajimovies", "shalimarcinema"
    	};

    	new GetYouTubeUserVideosTask(responseHandler, responseRelatedHandler, users[rnd], hmap).run();
        if(rnd > (users.length-2)){
        	rnd=0;
        }
        else{
        	rnd++;
        }
    }
   
    // This is the handler that receives the response when the YouTube task has finished
	Handler responseHandler = new Handler() {
		public void handleMessage(Message msg) {
			populateListWithVideos(msg);
		};
	};
	Handler responseRelatedHandler = new Handler() {
		public void handleMessage(Message relmsg) {
			populateListWithRelatedVideos(relmsg);
		};
	};

	/**
	 * This method retrieves the Library of videos from the task and passes them to our ListView
	 * @param msg
	 */
	private void populateListWithVideos(Message msg) {
		// Retreive the videos are task found from the data bundle sent back
		Library lib = (Library) msg.getData().get(GetYouTubeUserVideosTask.LIBRARY);
		
		// Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		// we can just call our custom method with the list of items we want to display
		listView.setVideos(lib.getVideos());
		
	}
	
	private void populateListWithRelatedVideos(Message relmsg) {
		// Retreive the videos are task found from the data bundle sent back
		Library relatedlib = (Library) relmsg.getData().get(GetYouTubeUserVideosTask.RELATEDLIBRARY);
		
		// Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		// we can just call our custom method with the list of items we want to display
		relatedListView.setVideos(relatedlib.getVideos());
		
	}
	
	
}