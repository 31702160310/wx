package com.styleman.wetalk.packet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.NetHandler;

public class addfriend {
	
	public int code;
	public String desc;
	public String test;
	
	// //求加好友
		public static void request( int to,String desc)
		{
			List <NameValuePair> params = new ArrayList <NameValuePair>(); 
			params.add(new BasicNameValuePair("uid",  Global.me.uid +"" )); 
			params.add(new BasicNameValuePair("to",  to +"" ));  
			params.add(new BasicNameValuePair("desc",  desc  )); 
			
			HttpPostAsyncTask task = new HttpPostAsyncTask("http://192.168.1.100/s/addfriend.php",params,
					NetHandler.instance.m_handler ,
					NetHandler.CMD_ADDFRIEND ); 
			task.execute("");
		}
}
