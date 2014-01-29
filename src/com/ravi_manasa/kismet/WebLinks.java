package com.ravi_manasa.kismet;

public class WebLinks {
	int _id;
    String _videoid;

    public WebLinks(){
    	
    }
    public WebLinks(int id, String videoid){
        this._id = id;
        this._videoid = videoid;	        
    }
    public WebLinks( String videoid){	        
        this._videoid = videoid;	        
    }
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_videoid() {
		return _videoid;
	}
	public void set_videoid(String _videoid) {
		this._videoid = _videoid;
	}
		
}
