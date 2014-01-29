package com.ravi_manasa.kismet;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class HistoryActivity extends Activity {
	private HashMap<String, String> hmap = new HashMap<String, String>() ;
	private VideosListView2 historyListView;

    @SuppressLint("NewApi") 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_history);        
        historyListView = (VideosListView2) findViewById(R.id.historyVideosListView);
       
        DatabaseHandler db = new DatabaseHandler(this);
       
        // Inserting Contacts
        // Log.d("Insert: "); 
         //db.addLink(new WebLinks("ClbKeWAvm8w"));         
        // Reading all contacts
        // Log.d("Reading: "); 
        String id= "1";
      //  WebLinks Links = db.getLinkByID(id);   
          
        List<WebLinks> allLinks = db.getLimLinks();   
        for (WebLinks cn : allLinks) {
	        String log = "Id: "+cn.get_id()+" ,VideoId: " + cn.get_videoid();
	        //Writing Contacts to log
	        Log.d(log);
	        String str = String.valueOf(cn.get_videoid());
	        hmap.put(String.valueOf(cn.get_id()), str);
        }
        
        new GetYouTubeHistoryVideosTask(responseRelatedHandler, hmap).run();
        
                
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        historyListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {				
				 
				Video selection = (Video)parent.getItemAtPosition(position);
					 
				Intent intent = new Intent(context, MainActivity2.class);
				Bundle b = new Bundle();
					
				/*String selection = parent.getItemAtPosition(position).toString();
				WebLinks testLink = (WebLinks) parent.getItemAtPosition(position);				
				WebLinks w = db.getLink(selection);*/
				
				b.putString("key", selection.getid());
				intent.putExtras(b);
				startActivity(intent);
			}
			
		});
    }
    Handler responseRelatedHandler = new Handler() {
		public void handleMessage(Message relmsg) {
			populateListWithRelatedVideos(relmsg);
		};
	};
	
    private void populateListWithRelatedVideos(Message relmsg) {
		// Retrieve the videos are task found from the data bundle sent back
		Library relatedlib = (Library) relmsg.getData().get(GetYouTubeHistoryVideosTask.RELATEDLIBRARY);		
		// Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		// we can just call our custom method with the list of items we want to display
		historyListView.setVideos(relatedlib.getVideos());
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
