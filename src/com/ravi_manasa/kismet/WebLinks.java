package com.ravi_manasa.kismet;

public class WebLinks {
	  int _id;
	    String _name;
	    String _link;
	    String _country;
	    Boolean _countryFilteringFlag = false;
	    public WebLinks(){
	    	
	    }
	    public WebLinks(int id, String name, String link, String country){
	        this._id = id;
	        this._name = name;
	        this._link = link;
	        this._country = country;
	        
	    }
	    public WebLinks( String name, String link, String country){
	        
	        this._name = name;
	        this._link = link;
	        this._country = country;
	        
	    }
		public int get_id() {
			return _id;
		}
		public void set_id(int _id) {
			this._id = _id;
		}
		public String get_name() {
			return _name;
		}
		public void set_name(String _name) {
			this._name = _name;
		}
		public String get_link() {
			return _link;
		}
		public void set_link(String _link) {
			this._link = _link;
		}
		public String get_country() {
			return _country;
		}
		public void set_country(String _country) {
			this._country = _country;
		}
		
		public String toString(){
			if(this._countryFilteringFlag)
				return this.get_country();
			else
			return this.get_name();
		}
}
