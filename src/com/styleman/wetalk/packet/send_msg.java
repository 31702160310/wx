package com.styleman.wetalk.packet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.NetHandler;

public class send_msg {
	
	public int code;
	public String test;
	
	// //发送聊天
		public static void request(int friendID,String content)
		{
			List <NameValuePair> params = new ArrayList <NameValuePair>(); 
			params.add(new BasicNameValuePair("from",  Global.me.uid +"" )); 
			params.add(new BasicNameValuePair("to",  friendID +"" ));  
			params.add(new BasicNameValuePair("content",  content  )); 
			
			HttpPostAsyncTask task = new HttpPostAsyncTask("http://192.168.1.100/s/send_msg.php",params,
					NetHandler.instance.m_handler ,
					NetHandler.CMD_SEND_MSG ); 
			task.execute("");
		}
}
