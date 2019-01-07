package com.styleman.wetalk.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.styleman.wetalk.R;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.packet.Session;
import com.styleman.wetalk.packet.pull_session;
 

/*
 * 
 * 会话
 * 
 * */
public class Fragment_Session extends Fragment {
	private View mMainView;
	
	public static Fragment_Session instance=null;
	
	ListView m_SessionListView;
	SessionAdapter m_SessionAdapter  ;
//	public List< Session >		m_ProductList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		instance=this;
		super.onCreate(savedInstanceState);
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.m_tab_wetalk, (ViewGroup)getActivity().findViewById(R.id.viewpager), false);
		
		//会话列表
		m_SessionListView = (ListView) mMainView.findViewById(R.id.shop_list_goods);
		
	//	m_SessionAdapter = new SessionAdapter(m_ProductList , this.getActivity().getApplicationContext());
	//	m_SessionListView.setAdapter(m_SessionAdapter);
		 
//		m_SessionListView.setOnClickListener(new OnClickListener() {	 
//			public void onClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				// TODO Auto-generated method stub         //触发的事件    		
//				}		
//		});
			 
		 

		//*为ListView添加点击事件 
		m_SessionListView.setOnItemClickListener(new OnItemClickListener() { 
 
	          @Override  
	           public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) { 
 
	               Log.v("MyListViewBase", "你点击了ListView条目" + arg2);//在LogCat中输出信息                 
	               
	               Session s=(Session)m_SessionAdapter.getItem(arg2);
	               if(s!=null  && s.type==1)
	               {
	            	   ChatActivity.friendID=s.friendID;
	            	   Intent intent = new Intent (
								 getActivity() //	MainActivity.g_MainActivity
								,ChatActivity.class);	 
						 getActivity().startActivity(intent);
						
	               }else if(s!=null  && s.type==2){
	            	   
	               }
	               
	           } 


	  	}); 

		 
	
	            // TODO Auto-generated method stub
		   		
	         
//		m_SessionListView.setOnClickListener(new AdapterView.OnItemClickListener()
//		{	/**    其中position就是你点击的第position个选项**/
//			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
//			{
//			 //	m_SessionListView.setOnItemClickListener(m_SessionAdapter);
//				Session s=(Session)m_SessionAdapter.getItem(position);
//				if(s!=null)
//				{
//					Intent intent = new Intent (
//							 getActivity() //	MainActivity.g_MainActivity
//							,ChatActivity.class);			
//					
//					 getActivity().startActivity(intent);
//					
//				}
//			} 
//		} );  
//		 
		//m_SessionAdapter.notifyDataSetChanged();
		
		//setContentView(R.layout.m_tab_wetalk);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		ViewGroup p = (ViewGroup) mMainView.getParent(); 
        if (p != null) { 
            p.removeAllViewsInLayout(); 
        } 
		
		return mMainView;
	}
 
	
	//  会话列表
	public void On_SessionList(HttpPostAsyncTask o){
		 
		if(o.m_e!=null){
			Toast.makeText(Fragment_Main.instance  ,  o.m_e.getMessage()   , Toast.LENGTH_SHORT).show();
			return;
		}
		   
		pull_session g = JSON.parseObject( o.ret , pull_session.class);
			 
		Toast.makeText(Fragment_Main.instance  ,  g.test   , Toast.LENGTH_SHORT).show();
		
		if(g.sessions!=null){
			m_SessionAdapter = new SessionAdapter(g.sessions , this.getActivity().getApplicationContext());
			m_SessionListView.setAdapter(m_SessionAdapter); 
			m_SessionAdapter.notifyDataSetChanged();
			
			int unread=0;
			for(int  i=0;i<g.sessions.size();i++){
				unread+=g.sessions.get(i).state;
			}
			//更新 未读num
			Fragment_Main.instance.dimage.unread_msgcount=Math.abs(unread);
			Fragment_Main.instance.dimage.auto_layout();
		}
		 
		
		 
		
 	}
	
	//进入1对1聊天
	public void stsartchat(  ) {      //С��  �Ի�����
		Intent intent = new Intent (
				 getActivity() //	MainActivity.g_MainActivity
				,ChatActivity.class);			
		
		 getActivity().startActivity(intent);
		
		//startActivityForResult(intent, 0); //startActivity(intent);	
		
		//Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_LONG).show();
      }  
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("huahua", "fragment1-->onResume()");
		
		
		//延迟1秒 
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				pull_session.request(); 
			}
		}, 1000);
		 
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		instance=null;
		super.onDestroy();
		Log.v("huahua", "fragment1-->onDestroy()");
	}
	
}
