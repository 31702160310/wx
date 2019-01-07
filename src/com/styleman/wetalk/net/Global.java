package com.styleman.wetalk.net;

import java.util.List;

import android.util.Log;

import com.styleman.wetalk.R;
import com.styleman.wetalk.db.DBManager;
import com.styleman.wetalk.packet.User;

public class Global {
	static String TAG="Global";
	
	//后台更新工作？
	public static boolean background_working=false;

	
	//用户的令牌
	public static String token;
	
	//自己
	public static User me;
	
	//好友
	public static List<User> userlist;
	
	public static User GetUser(int uid)
	{
		Log.i(TAG,"GetUser : "+uid );
		for(int i=0;i<userlist.size();i++){
			if(userlist.get(i).uid==uid) return userlist.get(i);
		}
		
		Log.e(TAG,"GetUser : "+uid );
		return null;
	}
	
	public static int imgid(String s)
	{
		 if(s==null) return R.drawable.face1;
		if(s.contains("face1")){
			return R.drawable.face1;
		}
		if(s.contains("face2")){
			return R.drawable.face2;
		}
		if(s.contains("face3")){
			return R.drawable.face3;
		}
		if(s.contains("face4")){
			return R.drawable.face4;
		}

		Log.e(TAG,"imgid : "+s );
		return R.drawable.face1;
	}
}
