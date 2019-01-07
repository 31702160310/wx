package com.styleman.wetalk.main;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.styleman.wetalk.R;
import com.styleman.wetalk.R.layout;
import com.styleman.wetalk.R.menu;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.packet.Search;
import com.styleman.wetalk.packet.Session;
import com.styleman.wetalk.packet.User;
import com.styleman.wetalk.packet.addfriend;
import com.styleman.wetalk.packet.pull;
import com.styleman.wetalk.packet.pull_session;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/*
 * 
 * 添加好友界面
 */

public class AddFriendActivity extends Activity implements OnClickListener {
	public static AddFriendActivity instance=null;
	
	
	private Button mBtnBack,add_friend_search;
	private EditText mEditTextContent;
	ListView lv ;
	AddFriendAdapter m_SessionAdapter ;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		instance=this;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);
		
	   	mBtnBack = (Button) findViewById(R.id.add_friend_btn_back);
    	mBtnBack.setOnClickListener(this);
    	
    	add_friend_search = (Button) findViewById(R.id.add_friend_search);
    	add_friend_search.setOnClickListener(this);
    	
    	
    	mEditTextContent =(EditText) findViewById(R.id.add_friend_input);
    	
    	lv =(ListView) findViewById(R.id.add_friend_lv);
    	//*为ListView添加点击事件 
    	lv.setOnItemClickListener(new OnItemClickListener() { 
    	 
    		          @Override  
    		           public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) { 
    	 
    		               Log.v("MyListViewBase", "你点击了ListView条目" + arg2);//在LogCat中输出信息                 
    		               
    		               User s=(User)m_SessionAdapter.getItem(arg2);
    		               if(s!=null  )
    		               {
    		            	   Log.v("MyListViewBase", "你点击了uid:" + s.uid);
    		            	   
    		            	   addfriend.request(s.uid, "haha");
    		            	   
//    		            	   InfoXiaoheiActivity.friendID=s.uid;
//    		            	   Intent intent = new Intent (  AddFriendActivity.this
//    									,InfoXiaoheiActivity.class);	 
//    							 startActivity(intent);
//    							
    		               
    		               }
    		               
    		           } 


    		  	}); 
    	 
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.listview:
			 
			break;
		case R.id.add_friend_search:
			 //搜索
			Search.request(mEditTextContent.getText().toString());
			break;
		case R.id.add_friend_btn_back:
			 //Intent intent = new Intent (   this ,MainActivity.class );			
			 //startActivity(intent);	
			
			 this.finish();
			break;
		}
	}
	
	//  搜索的结果 列表
	public void On_SEARCH(HttpPostAsyncTask o){
		 
		if(o.m_e!=null){
			Toast.makeText(Fragment_Main.instance  ,  o.m_e.getMessage()   , Toast.LENGTH_SHORT).show();
			return;
		}
		   
		Search g = JSON.parseObject( o.ret , Search.class);
			 
		Toast.makeText( instance  ,  g.test   , Toast.LENGTH_SHORT).show();
		
		if(g.users!=null){
			  m_SessionAdapter = new AddFriendAdapter(g.users ,   getApplicationContext());
			lv.setAdapter(m_SessionAdapter); 
			m_SessionAdapter.notifyDataSetChanged();
			
			 
		}
		 
		
		 
		
 	}
	
// 请求发送 server的响应
	public void On_ADDFRIEND(HttpPostAsyncTask o){
		 
		if(o.m_e!=null){
			Toast.makeText(this ,  o.m_e.getMessage()   , Toast.LENGTH_SHORT).show();
			return;
		}
		   
		addfriend g = JSON.parseObject( o.ret , addfriend.class);
			 
		if(g.code<=0)
			Toast.makeText( instance  ,  g.desc   , Toast.LENGTH_SHORT).show();
		if(g.code==1)
			Toast.makeText( instance  ,  g.test   , Toast.LENGTH_SHORT).show();
		
		 
		
 	}
	
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_friend, menu);
		return true;
	}
	@Override
	public void onDestroy() {
		instance=null;
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("huahua", "fragment1-->onDestroy()");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("huahua", "fragment1-->onPause()");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 
		Log.v("huahua", "fragment1-->onResume()");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("huahua", "fragment1-->onStart()");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("huahua", "fragment1-->onStop()");
	}
}
