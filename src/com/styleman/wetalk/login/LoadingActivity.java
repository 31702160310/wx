package com.styleman.wetalk.login;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.styleman.wetalk.R;
import com.styleman.wetalk.main.Fragment_Main;

/*
 * ����ֻ�� ProgressBar ����xx  ��ת�Ľ��Ȧ��
 * 
 * */
public class LoadingActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.loading);
			
		//延迟200ms 进入主界面。
	new Handler().postDelayed(new Runnable(){
		@Override
		public void run(){
			Intent intent = new Intent (LoadingActivity.this,Fragment_Main.class);			
			startActivity(intent);			
			LoadingActivity.this.finish();
			Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
		}
	}, 200);
   }
}