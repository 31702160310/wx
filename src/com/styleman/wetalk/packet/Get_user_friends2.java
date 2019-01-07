package com.styleman.wetalk.packet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.NetHandler;


 
public class Get_user_friends2 
{  
	public int code; 
	
	public String test;   
	
	public List<Group> groups = new ArrayList<Group>();  
	
	public List<User> users = new ArrayList<User>();  
  
	public static void request(){
		//请求好友分组列表
		 
			List <NameValuePair> params = new ArrayList <NameValuePair>(); 
			params.add(new BasicNameValuePair("uid",  Global.me.uid+"" )); 
		 
			HttpPostAsyncTask task = new HttpPostAsyncTask("http://192.168.1.100/s/get_user_friends2.php",params,
					NetHandler.instance.m_handler ,
					NetHandler.CMD_GetFriendsList ); 
			task.execute("");
	 
	}

} 