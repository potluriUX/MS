package com.ravi_manasa.kismet;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OurArrayAdapter extends ArrayAdapter{

    Context context;
    int layoutResourceId;   
    WebLinks data[] = null;
   
    public OurArrayAdapter(Context context, int layoutResourceId, WebLinks[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
           
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
           
            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }
    
        WebLinks weather = (WebLinks)getItem(position);
        if(weather._countryFilteringFlag){
        holder.txtTitle.setText(Character.toUpperCase(weather.get_country().charAt(0)) + weather.get_country().substring(1) + " - "+weather.get_name());
        }
        else{
        	//holder.txtTitle.setText(weather.get_name());
        	holder.txtTitle.setText(weather.get_name()  + " - "+ Character.toUpperCase(weather.get_country().charAt(0))+ weather.get_country().substring(1));
        }
        //+" " +weather._country set text is different its displayname. 
        //filtering and other aspects work based on adapter. we pass the tostring method return value to webviewactivity
        holder.imgIcon.setImageResource(R.drawable.arrow_icon);
      
        return row;
    }
   
    static class WeatherHolder
    {
        ImageView imgIcon;
       
        TextView txtTitle;
    }
}

