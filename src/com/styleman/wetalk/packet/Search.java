package com.styleman.wetalk.packet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.NetHandler;

public class Search {
	public int code; 
 
	public String test;
	
	public List<User> users = new ArrayList<User>();  
	
	
	public static void request(String v ){
	    	List <NameValuePair> params = new ArrayList <NameValuePair>(); 
			params.add(new BasicNameValuePair("type", "2" )); 
			params.add(new BasicNameValuePair("d",  v )); 
			
			HttpPostAsyncTask task = new HttpPostAsyncTask("http://192.168.1.100/s/search.php",params,
					NetHandler.instance.m_handler ,
					NetHandler.CMD_SEARCH ); 
			task.execute("");
	}
}
