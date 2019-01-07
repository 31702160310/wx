package com.styleman.wetalk.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.styleman.wetalk.login.LoginActivity;
import com.styleman.wetalk.main.AddFriendActivity;
import com.styleman.wetalk.main.ChatActivity;
import com.styleman.wetalk.main.Fragment_Contact;
import com.styleman.wetalk.main.Fragment_Main;
import com.styleman.wetalk.main.Fragment_Session;
 

public class NetHandler {
	String TAG="NetHandler";
	
	//��½
	public final static int CMD_LOGIN=1;//登陆
	public final static int CMD_GetFriendsList=2;//获得好友列表 
	public final static int CMD_GetSessionList=3; //获得会话列表
	public final static int CMD_PULL_MSG=4;//获得1对1聊天信息
	public final static int CMD_SEND_MSG=5;//发送聊天
	public final static int CMD_PULL=6;//获得状态 
	public final static int CMD_SEARCH=7;//搜索
	public final static int CMD_ADDFRIEND=8;//加好友请求
	
	public static NetHandler instance=new NetHandler();
	
	public   Handler m_handler=new Handler()
	{
		@Override
		public   void handleMessage(Message msg) 
		{
			Log.i("TAG","handleMessage  "); 
			super.handleMessage(msg);
			 switch(msg.what)
			 { 
				case HttpPostAsyncTask.HttpPostAsyncTask_MSG: 	  
					handleNet(msg); 
					break; 
				
			 }
			 
		}
		
	 };
	  
	 
	 private   void handleNet(Message msg)
	 {
		 HttpPostAsyncTask o=(HttpPostAsyncTask)msg.obj;
		
		 Log.i("TAG","handleNet cmd: " + o.m_cmd); 
		  
		 switch(o.m_cmd  )
		 { 
			case CMD_LOGIN: 	  
			 
				LoginActivity.instance.On_LOGIN(o);
			 
				break; 
			case CMD_GetFriendsList: 	  
				 
				Fragment_Contact.instance.On_friendlist(o);
			 
				break; 
			case CMD_GetSessionList:
				Fragment_Session.instance.On_SessionList(o); 
				break;
				 
			case CMD_PULL_MSG:
				ChatActivity.instance.On_PULL_MSG(o); 
				break;
				
			case CMD_SEND_MSG:
				ChatActivity.instance.On_SEND_MSG(o); 
				break;
			case CMD_PULL:
				Fragment_Main.instance.On_PULL(o); 
				break;
			case CMD_SEARCH:
				AddFriendActivity.instance.On_SEARCH(o); 
				break;
			case CMD_ADDFRIEND:
				AddFriendActivity.instance.On_ADDFRIEND(o); 
				break;
				 
		 }
		 
		 
		 
	 }
	 
}
