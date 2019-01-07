package com.styleman.wetalk.main;


import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.styleman.wetalk.R;

public class Exit extends Activity {
	//private MyDialog dialog;
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog);
		//dialog=new MyDialog(this);
		layout=(LinearLayout)findViewById(R.id.exit_layout);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", 
						Toast.LENGTH_SHORT).show();	
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	//否
	public void exitbutton1(View v) {  
    	this.finish();    	
      }  
	
	//是
	public void exitbutton0(View v) {  
    	//this.finish();
    	
    	System.exit(1);
    	//MainWeixin.instance.finish();//关闭Main 这个Activity  
	}  
	
}
