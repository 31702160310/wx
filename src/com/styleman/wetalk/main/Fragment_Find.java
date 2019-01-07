package com.styleman.wetalk.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleman.wetalk.R;

public class Fragment_Find extends Fragment {
	String TAG="Fragment_Contact";
	
	private View mMainView;
	
	 ;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.m_tab_find);
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.m_tab_find, (ViewGroup)getActivity().findViewById(R.id.viewpager), false);
		
		  
		//添加好友。
		TextView    add_friend=(TextView)  mMainView.findViewById(R.id.find_add_friend2);
		add_friend.setOnClickListener(new OnClickListener(){
	 		   
			@Override
			public void onClick(View v) {
				  
				Intent intent = new Intent (
						 getActivity() //	MainActivity.g_MainActivity
						,AddFriendActivity.class);	 
				  startActivity(intent);
				
				
			}
		});
		  
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		ViewGroup p = (ViewGroup) mMainView.getParent(); 
        if (p != null) { 
            p.removeAllViewsInLayout(); 
        } 
		
		return mMainView;
	}
}
