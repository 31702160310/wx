package com.styleman.wetalk.login;


import com.styleman.wetalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
 * 
 * ��ʾ ����� ��½  ע�� ��ť
 * 
 * */
public class WelcomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }
    public void welcome_login(View v) {  
      	Intent intent = new Intent();
		intent.setClass(WelcomeActivity.this,LoginActivity.class);
		startActivity(intent);
		//this.finish();
      }  
    
    //ע��
    public void welcome_register(View v) {  
//      	Intent intent = new Intent();
//		intent.setClass(Welcome.this,MainWeixin.class);
//		startActivity(intent);
		//this.finish();
      }  
   
}
