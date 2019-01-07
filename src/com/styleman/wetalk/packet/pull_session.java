package com.styleman.wetalk.packet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.NetHandler;

public class pull_session {
	
	public int code; 
	
	public String test;  
	
	public List<Session> sessions = new ArrayList<Session>();  
	
	//请求会话列表
	public static void request()
	{
		List <NameValuePair> params = new ArrayList <NameValuePair>(); 
		params.add(new BasicNameValuePair("uid",  Global.me.uid+"" )); 
	 
		HttpPostAsyncTask task = new HttpPostAsyncTask("http://192.168.1.100/s/pull_session.php",params,
				NetHandler.instance.m_handler ,
				NetHandler.CMD_GetSessionList ); 
		task.execute("");
	}
	
}
