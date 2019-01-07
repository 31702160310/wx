package com.styleman.wetalk.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleman.wetalk.R;
import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.packet.User;

public class InfoXiaoheiActivity extends Activity {
	String TAG="InfoXiaoheiActivity";
	
	
	public static int friendID;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_xiaohei);    
        
          
    	 
    	 try{
    		  
    		 
    		 User u=Global.GetUser(friendID);
    		 ImageView v=(ImageView)this.findViewById(R.id.info_head);
    		 
    		 v.setImageResource( Global.imgid(u.face) );
    		 
    		 
 	   		TextView v2=(TextView)this.findViewById(R.id.info_nickname); 
 	   		v2.setText(u.nickName);
 	   		
 	   		v2=(TextView)this.findViewById(R.id.info_maxim); 
 	   		v2.setText(u.maxim);
 	   		
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    }

   public void btn_back(View v) {     //������ ���ذ�ť
      	this.finish();
      } 
   public void btn_back_send(View v) {     //1v1聊天
     	//this.finish();
	   ChatActivity.friendID=friendID;
   		Intent intent = new Intent (this,ChatActivity.class);			
		startActivity(intent);	
     } 
   
   //点头像
   public void head_xiaohei(View v) {     //ͷ��ť
	   InfoXiaoheiHeadActivity.friendID=friendID;
	   Intent intent = new Intent();
		intent.setClass(this,InfoXiaoheiHeadActivity.class);
		startActivity(intent);
    } 
    
}
