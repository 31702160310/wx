package com.styleman.wetalk.packet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.styleman.wetalk.main.ChatMsgEntity;
import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.NetHandler;

public class pull_msg {
	
	public int code; 
	
	public String test;  
	
	public LinkedList<ChatMsgEntity> msgs = new LinkedList<ChatMsgEntity>();  
	
	//请求 列表
	public static void request(int friendID,int startID)
	{
		List <NameValuePair> params = new ArrayList <NameValuePair>(); 
		params.add(new BasicNameValuePair("uid",  Global.me.uid +"" )); 
		params.add(new BasicNameValuePair("fid",  friendID +"" )); 
		params.add(new BasicNameValuePair("startID",  startID +"" )); 
		
		HttpPostAsyncTask task = new HttpPostAsyncTask("http://192.168.1.100/s/pull_msg.php",params,
				NetHandler.instance.m_handler ,
				NetHandler.CMD_PULL_MSG ); 
		task.execute("");
	}
	
}
