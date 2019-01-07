package com.styleman.wetalk.main;

import com.styleman.wetalk.R;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class DotImage {
	
	RadioButton rb1=null;
 
	TextView main_tab_new_message;
	int unread_msgcount=0;
	
	/*
	 * 
	 * @Override  
      public void onWindowFocusChanged(boolean hasFocus)  
      {  
    	 super.onWindowFocusChanged(hasFocus);  
    	 auto_layout();
       }  
	 * 
	 * */  
	
    
   
	public void load(Activity ac,RadioButton rb)
	{
		rb1=rb;
		
		//TextView main_tab_new_message=new TextView();
        main_tab_new_message=(TextView) ac.findViewById(R.id.main_tab_new_message);
        main_tab_new_message.setVisibility(View.VISIBLE);
        main_tab_new_message.setText(" "+unread_msgcount+" ");
         
	}
	
	//��̬����СԲ�����
	public	void auto_layout()
	{
		if(rb1==null) return;
		
		rb1.getTop();
		rb1.getBottom();
		 int width=rb1.getRight()-  rb1.getLeft();
		   
		  main_tab_new_message.setX(rb1.getLeft()+width/2+3);
		  main_tab_new_message.setY(rb1.getTop());
		  
		//  Toast.makeText(getApplicationContext(),  main_tab_new_message.getX()+"", Toast.LENGTH_SHORT).show();
		 
		  
		//Ϊ�˳��ȶ��롣��
			if(unread_msgcount<=9)
				main_tab_new_message.setText("  "+unread_msgcount+"  ");
			else
				main_tab_new_message.setText(" "+unread_msgcount+" ");
			
			 
			 
			
			//δ����Ϣ����� С����ʾ�Ƿ���ʾ
		 	if(unread_msgcount<=0)
		 		 main_tab_new_message.setVisibility(View.GONE);//setText(" "+unread_msgcount+" ");
		 	else 
				 main_tab_new_message.setVisibility(View.VISIBLE);
	}
}
