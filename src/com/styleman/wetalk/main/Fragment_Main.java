package com.styleman.wetalk.main;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.styleman.wetalk.R;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.WorkThread;
import com.styleman.wetalk.packet.pull;
import com.styleman.wetalk.packet.pull_msg;
import com.styleman.wetalk.packet.pull_session;
import com.styleman.wetalk.packet.send_msg;
 

public class Fragment_Main extends FragmentActivity {
	String TAG	=	"MainActivity";
	
	private ViewPager viewPager=null;  
    
	public static Fragment_Main instance=null;
	
	//ҳ���б�
	private ArrayList<Fragment> fragmentList;
	
  
    DotImage dimage=new DotImage();
    
    RadioGroup radioGroup;
	   
    //4��tab btn
	RadioButton rb1;
	RadioButton rb2;
	RadioButton rb3;
	RadioButton rb4;
	
	void setradiobtn_icon(RadioButton b,int resid)
	{ 
		Drawable drawable = getResources().getDrawable(  resid );
      // / ��һ������Ҫ��,���򲻻���ʾ.
      drawable.setBounds(0, 0, drawable.getMinimumWidth(),  drawable.getMinimumHeight());
      b.setCompoundDrawables(null, drawable, null, null);
	}
	
    void tab_update(int checkedId)
    {
    	  //��ȫ�����û���icon
		setradiobtn_icon(rb1, R.drawable.tab_weixin_normal );
		setradiobtn_icon(rb2, R.drawable.tab_address_normal);
		setradiobtn_icon(rb3, R.drawable.tab_find_frd_normal);
		setradiobtn_icon(rb4, R.drawable.tab_settings_normal);
		
		// TODO Auto-generated method stub
		switch (checkedId) 
		{
		case R.id.main_tab_weitalk:// 
			rb1.setChecked(true);
			viewPager.setCurrentItem(0);//viewpager�л���
			setradiobtn_icon(rb1, R.drawable.tab_weixin_pressed  );
			break;
		case R.id.main_tab_contact: 
			rb2.setChecked(true);
			viewPager.setCurrentItem(1);//viewpager�л���
			setradiobtn_icon(rb2, R.drawable.tab_address_pressed  ); 
			break;
		case R.id.main_tab_find: 
			rb3.setChecked(true);
			viewPager.setCurrentItem(2);//viewpager�л���
			setradiobtn_icon(rb3, R.drawable.tab_find_frd_pressed  ); 
			break;
		case R.id.main_tab_settings://����
			rb4.setChecked(true);
			setradiobtn_icon(rb4, R.drawable.tab_settings_pressed  ); 
			viewPager.setCurrentItem(3);//viewpager�л���
			break;
		default: 
			break;
		}
		   
    }
    
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	instance=this;
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_main);  
          
        
        //main = (ViewGroup)inflater.inflate(R.layout.m_main, null);  
        
         
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        
        init_once();
    }
    
    void init_once()
    {
    	 
    	if(viewPager!=null) return;
    	 
    	 
    	
    	
    	
        LayoutInflater inflater = getLayoutInflater();  
        
        viewPager = (ViewPager)findViewById(R.id.viewpager);  
       
        
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new Fragment_Session());
		fragmentList.add(new Fragment_Contact());
		fragmentList.add(new Fragment_Find());
		fragmentList.add(new Fragment_Setting());
 
		
		viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
		
       
      //  viewPager.setAdapter(new GuidePageAdapter());  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());  
       
   
        
   
        //tab host ��radiogroup ģ��
       //tab btn
       rb1=(RadioButton) this.findViewById(R.id.main_tab_weitalk);//
       rb2=(RadioButton) this.findViewById(R.id.main_tab_contact);
       rb3=(RadioButton) this.findViewById(R.id.main_tab_find);
       rb4=(RadioButton) this.findViewById(R.id.main_tab_settings);
       
      
        
       
         radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
         
       // ��עradiobtn��onCheckedChanged�¼�
       radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Log.i("RadioGroup onCheckedChanged:" , checkedId+"" );
				 
				 tab_update(checkedId);
				 
				   
			}
		});
       
       //Ĭ��tab1 ѡ��
       rb1.setChecked(true);
        
        
        
        dimage.load(this, rb1);
        
    }
    
    @Override  
    protected void onResume() {  
        super.onResume();  
        Log.v(TAG, "onResume");  
        
        //����activityʱ���Զ����������
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        
        init_once();
        
        dimage.auto_layout();
   }  
  
    @Override
	public void onDestroy() {
    	instance=null;
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v(TAG, " onDestroy()");
	} 
    
    public void On_PULL(HttpPostAsyncTask o)
	{ 
		if(o.m_e!=null){
			Toast.makeText(  this  ,  o.m_e.getMessage()   , Toast.LENGTH_SHORT).show();
			return;
		}
		   
		pull g = JSON.parseObject( o.ret , pull.class);
			 
		if(g.code!=1)
			Toast.makeText(  this  ,   "PULL err"  , Toast.LENGTH_SHORT).show();
 
		//有新的消息(会话)
		if(g.has_new_session) {
			pull_session.request();
		}
		 
 	}
	 
     
    
@Override  
  public void onWindowFocusChanged(boolean hasFocus)  
  {  
	 super.onWindowFocusChanged(hasFocus);  
	 dimage.auto_layout();
   }  

	public void startchat(View v) {      //С��  �Ի�����
		Intent intent = new Intent ( this ,ChatActivity.class);			
		startActivity(intent);	
		//Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_LONG).show();
     }  	
	
	@Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
     	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  //��ȡ back��
     		
         	  
         		Intent intent = new Intent();
             	intent.setClass(this,Exit.class);
             	startActivity(intent);
             	
              
     	}
     	
     	return false;
 	}
    
    
     public class MyViewPagerAdapter extends FragmentPagerAdapter{
 		public MyViewPagerAdapter(FragmentManager fm) {
 			super(fm);
 			// TODO Auto-generated constructor stub
 		}
 		
 		@Override
 		public Fragment getItem(int arg0) {
 			return fragmentList.get(arg0);
 		}

 		@Override
 		public int getCount() {
 			return fragmentList.size();
 		}

// 		@Override
// 		public CharSequence getPageTitle(int position) {
// 			// TODO Auto-generated method stub
// 			return titleList.get(position);
// 		}

 		
 	}
 
     
    /** ָ��ҳ��ļ����� */
    class GuidePageChangeListener implements OnPageChangeListener {  
   
        @Override 
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
   
        }  
   
        @Override 
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
   
        }  
   
        @Override 		//page�л� arg0������
        public void onPageSelected(int arg0) {  
            for (int i = 0; i < fragmentList.size() ; i++)
            {  
                
                Log.i("RadioGroup onCheckedChanged:" , arg0+"" );
				 
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0: 
					 tab_update(R.id.main_tab_weitalk);
					break;
				case 1: 
					 tab_update(R.id.main_tab_contact); 
					break;
				case 2: 
					tab_update(R.id.main_tab_find); 
					break;
				case 3: 
					tab_update(R.id.main_tab_settings); 
					break;
				default:
					 
					break;
				}
				
                
            }
   
        }  
   
    }  
     


}
