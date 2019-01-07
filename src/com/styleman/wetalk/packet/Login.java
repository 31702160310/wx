package com.styleman.wetalk.packet;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.NetHandler;

public class Login {
	public int code;//=1;
	public String token;//=$md5;
	 
	public String desc;//="��½�ɹ�";
	
	public User me;//如果登陆ok,则有user信息
	
	public static void request(String user,String pwd){
	    	List <NameValuePair> params = new ArrayList <NameValuePair>(); 
			params.add(new BasicNameValuePair("username", user )); 
			params.add(new BasicNameValuePair("password", pwd )); 
			HttpPostAsyncTask task = new HttpPostAsyncTask("http://192.168.1.100/s/login.php",params,
					NetHandler.instance.m_handler ,
					NetHandler.CMD_LOGIN ); 
			task.execute("");
	}
	
}
