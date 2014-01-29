package com.ravi_manasa.kismet;

import java.util.ArrayList;
import java.util.List;

import com.ravi_manasa.kismet.WebLinks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "historyManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "history";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_VIDEOID = "videoId";
    
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_VIDEOID + " TEXT)";
       
        db.execSQL(CREATE_CONTACTS_TABLE);
     	//	createData(db);
      
    }
 
	// Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
    
    public List<WebLinks> getAllLinks() {
        List<WebLinks> linkList = new ArrayList<WebLinks>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
                WebLinks link = new WebLinks();
                link.set_id(Integer.parseInt(cursor.getString(0)));
                link.set_videoid(cursor.getString(1));
                // Adding contact to list
                linkList.add(link);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return linkList;
    }
    public List<WebLinks> getLimLinks() {
        List<WebLinks> linkList = new ArrayList<WebLinks>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +" ORDER BY id DESC LIMIT 5";// "select * from ( SELECT  * FROM " + TABLE_CONTACTS +" ORDER BY id DESC LIMIT 5)T order by T.id DESC";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
       
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
                WebLinks link = new WebLinks();
                link.set_id(Integer.parseInt(cursor.getString(0)));
                link.set_videoid(cursor.getString(1));
                // Adding contact to list
                linkList.add(link);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return linkList;
    }
    
   WebLinks getLink(String videoId) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_VIDEOID}, KEY_VIDEOID + "=?",
                new String[] { videoId }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        WebLinks link = new WebLinks(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return link;
    }
    
    WebLinks getLinkByID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                KEY_VIDEOID }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        WebLinks link = new WebLinks(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        return link;
    }
    
   
    
    public void addLink(WebLinks link, SQLiteDatabase db) {        
        ContentValues values = new ContentValues();
        values.put(KEY_VIDEOID, link.get_videoid()); 
        db.insert(TABLE_CONTACTS, null, values);
        
    }
    
    public void addLink(WebLinks link) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_VIDEOID, link.get_videoid()); 
        //Log.d("Name: ", "asdf");
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        
    }
    
    // Deleting single contact
	public void deleteLink(String id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { id });
	    db.close();
	}
    
    private void createData(SQLiteDatabase db) {    	
    	//addLink(new WebLinks("pSDoWvzsRPY"), db);
        //addLink(new WebLinks("pSDoWvzsRPY"), db);	
    }
}