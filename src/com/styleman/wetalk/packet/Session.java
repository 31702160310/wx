package com.styleman.wetalk.packet;

//会话信息表
public class Session {
	public int session_id ;//会话id
	public int  uid;//自己id
	public int   friendID;//会话的好友id
	public String  lastContent;//
	public String   lastTime ;//datetime DEFAULT NULL, 
	public int   state;
	public int   type;//1 好友消息 2请求添加好友消息
	
	 

}
