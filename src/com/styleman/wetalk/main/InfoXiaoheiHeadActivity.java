package com.styleman.wetalk.main;



import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleman.wetalk.R;
import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.packet.User;

public class InfoXiaoheiHeadActivity extends Activity{
	String TAG="InfoXiaoheiHeadActivity";
	
	public static int friendID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.info_xiaohei_head);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);   //ȫ����ʾ
		//Toast.makeText(getApplicationContext(), "���ӣ��úñ��У�", Toast.LENGTH_LONG).show();
		//overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);

		 
	   	 try{
	   		  
	   		 
	   		 User u=Global.GetUser(friendID);
	   		 ImageView v=(ImageView)this.findViewById(R.id.add_friend);
	   		
	   		 v.setImageResource( Global.imgid(u.face) );
	   		 
	   		 
	   	 
	   		
	   	 }catch(Exception e){
	   		 e.printStackTrace();
	   	 }
	   	 
   }
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
}