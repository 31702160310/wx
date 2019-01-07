package com.styleman.wetalk.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.styleman.wetalk.R;
import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.NetHandler;
import com.styleman.wetalk.packet.Get_user_friends2;
import com.styleman.wetalk.packet.Group;
import com.styleman.wetalk.packet.User;
import com.styleman.wetalk.packet.pull_msg;
/*
 * 
 * �����б� ����
 * 
 * */
public class Fragment_Contact extends Fragment {
	String TAG="Fragment_Contact";
	
	public static Fragment_Contact instance=null;
	
	private ExpandableListView expandableListView;
	
	//static 保持组信息在程序生命周期。只需每次清理子成员 达到刷新目的。
	private static List<GroupItem> group_list  = new ArrayList<GroupItem>();
	
	
	private View mMainView;
	
	//组全清 但保留组。
	public void  GroupRemoveAllChild( )
	{
		for(int i=0;i<group_list.size();i++){
			 group_list.get(i).item_list.clear() ; 
		}
 
	}
	
	public void addGroup(String groupname,int groupid)
	{
		for(int i=0;i<group_list.size();i++){
			if(group_list.get(i).fgid==groupid){
				Log.e(TAG,"addGroup 失败，已经存在gid:"+groupid);
				//组id存在，则更新 组名称。
				group_list.get(i).name=groupname;
				return;
			}
		}
		group_list.add( new GroupItem(groupid,groupname ) );
	}
	
  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance=this;
		
		//setContentView(R.layout.m_tab_contact);
 
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.m_tab_contact, (ViewGroup)getActivity().findViewById(R.id.viewpager), false);
		
	// ���һ�Ѳ������
			
//			group_list.add( new GroupItem(10,"����") );
//			group_list.add( new GroupItem(20,"İ����") );
//			group_list.add( new GroupItem(30,"����") );
//
//			group_list.get(0).addChild( new UserItem( "����1",123, R.drawable.face1+"") );
//			group_list.get(0).addChild( new UserItem( "����2",123, R.drawable.face1+"") ); 
//			  
//			group_list.get(1).addChild( new UserItem( "��7",123, R.drawable.face1+"") );
//			 
//			group_list.get(2).addChild( new UserItem( "xxx",123, R.drawable.face1+"") );
//			
		//	item_list.add( new UserItem(1,"����1",123, R.drawable.ic_launcher+""));
		//	item_list.add( new UserItem(1,"ɵ��2",123, R.drawable.ic_launcher+"")); 
		//	item_list.add( new UserItem(1,"����3",123, R.drawable.ic_launcher+"")); 

		 
			expandableListView = (ExpandableListView) mMainView.findViewById(R.id.expendlist);
			expandableListView.setAdapter(new MyExpandableListViewAdapter(getActivity()));
			 
	         
		        //group�����չ��
		        expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override	//展开事件
					public void onGroupExpand(int groupPosition) {
					//	for (int i = 0, count = expandableListView .getExpandableListAdapter().getGroupCount(); i < count; i++) {
					//		if (groupPosition != i) {// �ر��������
					//			expandableListView.collapseGroup(i);
					//		}
					//	}
						group_list.get(groupPosition).expand=1;
						
						 Toast.makeText( getActivity() ,
			                        "������" + group_list.get(groupPosition).name  ,
			                        Toast.LENGTH_SHORT).show();
					}
					
				});
		        
		         
		        //group������رա�
		        expandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener () { 
					   
					@Override	//收缩事件
					public void onGroupCollapse(int groupPosition) {
						 
						group_list.get(groupPosition).expand=0;
					}
					
				});
		        
		        //子item点击事件 
		        expandableListView.setOnChildClickListener(new OnChildClickListener() {

		            @Override
		            public boolean onChildClick(ExpandableListView parent, View v,
		                    int groupPosition, int childPosition, long id) {

		            	
		                Toast.makeText(
		                		getActivity() ,
		                        "onChildClick" +group_list.get(groupPosition).getChild( childPosition).nickName ,
		                        Toast.LENGTH_SHORT).show();
	 
		                /*
		                //进入聊天界面。 其实这里应该是点人头像 进入人物介绍界面。
		                ChatActivity.friendID= group_list.get(groupPosition).getChild( childPosition).uid;
		            	   Intent intent = new Intent (
									 getActivity() //	MainActivity.g_MainActivity
									,ChatActivity.class);	 
							 getActivity().startActivity(intent);
		                */
		                
		                //好友信息界面
		                InfoXiaoheiActivity.friendID= group_list.get(groupPosition).getChild( childPosition).uid;
		                Intent intent = new Intent (
		                		 getActivity() //	MainActivity.g_MainActivity
								,InfoXiaoheiActivity.class);	 
						 getActivity().startActivity(intent);
						 
		                return false;
		            }
		        });
			
			
		        // 内部更新线程 
		        new Thread(new Runnable() 
				{ 
					public void run() 
			 		{ 
			 			while(instance!=null)
			 			{
			 				try 
			 				{ 
			 					 //请求 好友 列表
			 					Get_user_friends2.request();
			 			        
			 					//30秒刷新
			 					Thread.sleep(30000);
			 					 
			 				} catch (InterruptedException e) {
			 					// TODO Auto-generated catch block
			 					e.printStackTrace();
			 				}
			 			}
			 		}
				}).start();
		        
		        
		}
	
	public GroupItem getGroupByID(int fgid)
	{
		for(int i=0;i<group_list.size();i++){
			if(group_list.get(i).fgid ==fgid ){
				 
				return group_list.get(i);
			}
		}
		return null;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		ViewGroup p = (ViewGroup) mMainView.getParent(); 
        if (p != null) { 
            p.removeAllViewsInLayout(); 
        } 
		
		return mMainView;
	}
	
 
	
	// 好友分组列表
	public void On_friendlist(HttpPostAsyncTask o){
		 
		if(o.m_e!=null){
			Toast.makeText(Fragment_Main.instance  ,  o.m_e.getMessage()   , Toast.LENGTH_SHORT).show();
			return;
		}
		 
		GroupRemoveAllChild();//group_list.clear();
		
			Get_user_friends2 g = JSON.parseObject( o.ret , Get_user_friends2.class);
			
			Toast.makeText(Fragment_Main.instance  ,  g.test   , Toast.LENGTH_SHORT).show();
			
			//保存好友列表
			Global.userlist=g.users;
			
			 
			
			//添加组
			for(int i=0;i<g.groups.size();i++){
				Group group= g.groups.get(i);
				addGroup( group.GroupName , group.fgid);
			}
			
			//好友列表  
			if(Global.userlist!=null)  
			{
				//添加成员
				for(int i=0;i<g.users.size();i++){
					User u=g.users.get(i);  
					getGroupByID(u.fgid).addChild(u);
				}
			}
			 
			 
			expandableListView.setAdapter(new MyExpandableListViewAdapter(getActivity()));
			
			//展开,关闭  状态恢复。
			for(int i=0;i<group_list.size();i++){
				
				if(group_list.get(i).expand==0) expandableListView.collapseGroup(i);
				else  expandableListView.expandGroup(i);
				 
			}
			
			 
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		instance=null;
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
	
	 
	
		// �ù�ListView����һ������Ϥ��ֻ����������BaseExpandableListAdapter
		class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

			private Context context;

			public MyExpandableListViewAdapter(Context context) {
				this.context = context;
			}

			@Override
			public int getGroupCount() {
				return group_list.size();
			}
		 
			@Override
			public int getChildrenCount(int groupPosition) { 
				return group_list.get(groupPosition).getChildrenCount();
			}

			@Override
			public Object getGroup(int groupPosition) {
				return group_list.get(groupPosition);
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				return group_list.get(groupPosition).getChild(childPosition);
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				GroupHolder groupHolder = null;
				if (convertView == null) { 
					convertView = (View) getActivity().getLayoutInflater().from(context).inflate( R.layout.m_expendlist_group, null);
					groupHolder = new GroupHolder();
					groupHolder.txt = (TextView) convertView.findViewById(R.id.txt);
					groupHolder.count = (TextView) convertView.findViewById(R.id.count);
				    groupHolder.img = (ImageView) convertView.findViewById(R.id.img);
				    
					convertView.setTag(groupHolder);
				} else {
					groupHolder = (GroupHolder) convertView.getTag();
				}
				groupHolder.txt.setText(group_list.get(groupPosition).name);
				groupHolder.count.setText(group_list.get(groupPosition).get_online_string());
				 
				//���groupչ��״̬  ����icon
				if(group_list.get(groupPosition).expand==0)
				groupHolder.img.setBackgroundResource( R.drawable.skin_indicator_unexpanded );
				if(group_list.get(groupPosition).expand==1)
				groupHolder.img.setBackgroundResource( R.drawable.skin_indicator_expanded );
					
				return convertView;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				ItemHolder itemHolder = null;
				if (convertView == null) {
					convertView = (View) getActivity().getLayoutInflater().from(context).inflate( R.layout.m_expendlist_item, null);
					itemHolder = new ItemHolder();
					itemHolder.nickname = (TextView) convertView.findViewById(R.id.nickname);
					itemHolder.maxim = (TextView) convertView.findViewById(R.id.maxim);
					itemHolder.face = (ImageView) convertView.findViewById(R.id.face);
					convertView.setTag(itemHolder);
				} else {
					itemHolder = (ItemHolder) convertView.getTag();
				}
				
				itemHolder.maxim.setText(group_list.get(groupPosition).getChild( childPosition).maxim);
				itemHolder.nickname.setText(group_list.get(groupPosition).getChild( childPosition).nickName);
				  
				itemHolder.face.setBackgroundResource(
						Global.imgid( group_list.get(groupPosition).getChild( childPosition).face )
						);
				return convertView;
			}

			@Override
			public boolean isChildSelectable(int groupPosition, int childPosition) {
				return true;
			}

		}

		class GroupHolder {
			public TextView txt;//组标题
			public TextView count;//旁边显示 在线/总人数
			public ImageView img;//组icon
		}

		class ItemHolder {
			public ImageView face;
			public TextView maxim;
			public TextView nickname;
		}
		
		class  GroupItem {
			public int fgid;//��id
			public String name;//�����
			public int expand;//��չ���� 
			
			//��ӵ�е��Ӷ����б�
			private List<User> item_list;
			
			public String get_online_string(){
				String r=get_online_count()+"/"+item_list.size();
				return r;
			}
			public int get_online_count()
			{
				int c=0;
				 for(int i=0;i<item_list.size();i++){
					 if(item_list.get(i).online  ){ 
						c++; 
					 }
				}
				 return c;
			}
			
			GroupItem(int gid,String nam  ){
				fgid=gid;
				name=nam; 
				
				item_list=new ArrayList<User>();
				
				expand=0;//Ĭ��δչ����
			}
			
			public void addChild(User u){
				item_list.add(u);
			}
			public int getChildrenCount(){
				return item_list.size();
			}
			public User  getChild(int pos){
				return item_list.get(pos);
			}
		}
		
		 

}
