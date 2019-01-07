package com.styleman.wetalk.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.styleman.wetalk.R;
import com.styleman.wetalk.db.DBManager;
import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.packet.User;
import com.styleman.wetalk.packet.pull_msg;
import com.styleman.wetalk.packet.pull_session;
import com.styleman.wetalk.packet.send_msg;


/**
 * 
 	1对1 聊天界面
 	
 */
public class ChatActivity extends Activity implements OnClickListener , OnScrollListener{
    /** Called when the activity is first created. */
	String TAG="ChatActivity";
	
	public static ChatActivity instance=null;
	
	//聊天db
	public static DBManager mgr;  
	
	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	TextView chat_friend_nickname;
	
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private LinkedList<ChatMsgEntity> mDataArrays= new LinkedList<ChatMsgEntity>();
	int lastID=0;//最新聊天消息id
	public static int friendID;
	
	ArrayList<ImageView> pointList=null;
	ArrayList<ArrayList<HashMap<String,Object>>> listGrid=null;
 
	int[] faceId={R.drawable.f_static_000,R.drawable.f_static_001,R.drawable.f_static_002,R.drawable.f_static_003
			,R.drawable.f_static_004,R.drawable.f_static_005,R.drawable.f_static_006,R.drawable.f_static_009,R.drawable.f_static_010,R.drawable.f_static_011
			,R.drawable.f_static_012,R.drawable.f_static_013,R.drawable.f_static_014,R.drawable.f_static_015,R.drawable.f_static_017,R.drawable.f_static_018};
	String[] faceName={"\\呲牙","\\淘气","\\流汗","\\偷笑","\\再见","\\敲打","\\擦汗","\\流泪","\\掉泪","\\小声","\\炫酷","\\发狂"
			 ,"\\委屈","\\便便","\\菜刀","\\微笑","\\色色","\\害羞"};
	
	HashMap<String,Integer> faceMap=null;
 
	protected ViewFlipper viewFlipper=null;
	protected ImageButton chatBottomLook=null;
	protected RelativeLayout faceLayout=null;
	protected LinearLayout pagePoint=null,fillGapLinear=null;
	private boolean expanded=false;
	
	Handler handler=new Handler();
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        instance=this;
        
        setContentView(R.layout.chat_xiaohei);
         
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        
    	
    	//聊天db
        mgr=new DBManager(this);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);
    	
    	faceMap=new HashMap<String,Integer>();	 
		listGrid=new ArrayList<ArrayList<HashMap<String,Object>>>();
        pointList=new ArrayList<ImageView>();
	 
        
    	viewFlipper=(ViewFlipper)findViewById(R.id.faceFlipper);
		chatBottomLook=(ImageButton)findViewById(R.id.chat_bottom_look);
		faceLayout=(RelativeLayout)findViewById(R.id.faceLayout);
		pagePoint=(LinearLayout)findViewById(R.id.pagePoint);
		fillGapLinear=(LinearLayout)findViewById(R.id.fill_the_gap);
    	
    	mEditTextContent = (EditText) findViewById(R.id.chat_bottom_edittext);
    	
    	chat_friend_nickname=(TextView) findViewById(R.id.chat_friend_nickname);
    	
    	
    	mListView.setOnScrollListener(this);
    	   
      mListView.setOnTouchListener(new OnTouchListener(){

	   @Override
	    public boolean onTouch(View v, MotionEvent event) {
	
	            // TODO Auto-generated method stub
	
	             //触摸list 关闭软键盘。
	           collapseSoftInputMethod();
	           
	           if(expanded)
	           {
					//关闭表情弹出界面
					setFaceLayoutExpandState(false);
					expanded=false;
				}
	
	            return false;
	        }
	
	    }); 
    	
    	
    	//查询缓存的msg     query保证返回对象不为空。但可能list里item是空
    	mDataArrays=mgr.query(lastID, Global.me.uid, friendID );
    	 
    	//加载到界面
    		mAdapter = new ChatMsgViewAdapter( this ,  handler  ,mDataArrays);
    		mListView.setAdapter(mAdapter);
    	 
    		//记录最后的消息id
    		if(mDataArrays.size()>0){
    			lastID=mDataArrays.get(mDataArrays.size()-1).id;
    			
    			Log.v(TAG, "查询缓存的msg lastid "+lastID );
    		}
     
    	 
		
      	
      	/**
		 * 为表情Map添加数据
		 */
		for(int i=0; i<faceId.length; i++){
			faceMap.put(faceName[i], faceId[i]);
		}
		
		
		addFaceData();
		addGridView();
		
		mEditTextContent.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(expanded){
					
					setFaceLayoutExpandState(false);
					expanded=false;
				}
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
	 					 
	 					 //请求和好友的聊天msg
	 			        pull_msg.request(friendID,lastID);
	 			        
	 					Thread.sleep(4000);
	 					 
	 				} catch (InterruptedException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	 			}
	 		}
		}).start();
        
        
         
        
	   	 try{
	   		   
	   		 User u=Global.GetUser(friendID); 
	   		 
	   		chat_friend_nickname.setText(u.nickName);//ui 显示好友昵称
	   		 
	   	 }catch(Exception e){
	   		 e.printStackTrace();
	   	 }
	   	 
	 	chatBottomLook.setOnClickListener(new OnClickListener(){
	 		  
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(expanded){
					setFaceLayoutExpandState(false);
					expanded=false;
					
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  

					/**height不设为0是因为，希望可以使再次打开时viewFlipper已经初始化为第一页 避免
					*再次打开ViewFlipper时画面在动的结果,
					*为了避免因为1dip的高度产生一个白缝，所以这里在ViewFlipper所在的RelativeLayout
					*最上面添加了一个1dip高的黑色色块
					*/
					
					
				}
				else{

					setFaceLayoutExpandState(true);  
					expanded=true;
				    setPointEffect(0);

				}
			}
			
		});
	   	 
    }
    
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount!=0){
            //滚到底，载入更多的联系人
//判断不精确 到底触发后 上拉 也触发很多次。
//要精确的判断可以参考：  http://blog.csdn.net/hellogv/article/details/6615487
           // loadContact();
        	Log.v("onScroll", "滚到底，载入更多 ");
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        
    }

	private void addFaceData(){
		ArrayList<HashMap<String,Object>> list=null;
		for(int i=0; i<faceId.length; i++){
			if(i%14==0){
				list=new ArrayList<HashMap<String,Object>>();
				listGrid.add(list);
			}  
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("image", faceId[i]);
			map.put("faceName", faceName[i]);
			
			/**
			 * 这里把表情对应的名字也添加进数据对象中，便于在点击时获得表情对应的名称
			 */
			listGrid.get(i/14).add(map);		
		}
		System.out.println("listGrid size is "+listGrid.size());
	}
	
	
	private void addGridView(){
		for(int i=0; i< listGrid.size();i++){
			View view=LayoutInflater.from(this).inflate(R.layout.view_item, null);
			GridView gv=(GridView)view.findViewById(R.id.myGridView);
			gv.setNumColumns(5);
			gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
			MyGridAdapter adapter=new MyGridAdapter(this, listGrid.get(i), R.layout.chat_grid_item, new String[]{"image"}, new int[]{R.id.gridImage});
			gv.setAdapter(adapter);
			gv.setOnTouchListener(new MyTouchListener(viewFlipper));
			viewFlipper.addView(view);
		//	ImageView image=new ImageView(this);
		//	ImageView image=(ImageView)LayoutInflater.from(this).inflate(R.layout.image_point_layout, null);
            /**
             * 这里不喜欢用Java代码设置Image的边框大小等，所以单独配置了一个Imageview的布局文件
             */
			View pointView=LayoutInflater.from(this).inflate(R.layout.point_image_layout, null);
			ImageView image=(ImageView)pointView.findViewById(R.id.pointImageView);
			image.setBackgroundResource(R.drawable.qian_point);
			pagePoint.addView(pointView);   
			/**
			 * 这里验证了LinearLayout属于ViewGroup类型，可以采用addView 动态添加view
			 */
			
			pointList.add(image);
			/**
			 * 将image放入pointList，便于修改点的颜色
			 */
		}
	
	}
	
    
    /**
     *  收起软输入法
     */
    public void collapseSoftInputMethod(){
    // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
     //imm.hideSoftInputFromWindow(mEditTextContent.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
     
     ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).  
     hideSoftInputFromWindow(mEditTextContent.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.listview:
			collapseSoftInputMethod();
			break;
		case R.id.btn_send:
			send();
			break;
		case R.id.btn_back:
			 //Intent intent = new Intent (   this ,MainActivity.class );			
			 //startActivity(intent);	
			
			 this.finish();
			break;
		}
	}
	
	private void send()
	{
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0)
		{
			/*
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.createTime="2014-12-16";//(getDate());
			entity.content =contString ;
			entity._to=friendID; 
			entity.send=Global.me.uid;
			
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();*/
			
			mEditTextContent.setText("");
			
			//设置list显示位置
			mListView.setSelection(mListView.getCount() - 1);
			
			//发送聊天
			send_msg.request(friendID, contString);
			
		 
			
		}
	}
	
//    private String getDate() {
//        Calendar c = Calendar.getInstance();
//
//        String year = String.valueOf(c.get(Calendar.YEAR));
//        String month = String.valueOf(c.get(Calendar.MONTH));
//        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
//        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
//        String mins = String.valueOf(c.get(Calendar.MINUTE));
//        
//        
//        StringBuffer sbBuffer = new StringBuffer();
//        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
//        						
//        						
//        return sbBuffer.toString();
//    }
    
    //   
	public void On_PULL_MSG(HttpPostAsyncTask o)
	{ 
		if(o.m_e!=null){
			Toast.makeText(  this  ,  o.m_e.getMessage()   , Toast.LENGTH_SHORT).show();
			return;
		}
		   
		pull_msg g = JSON.parseObject( o.ret , pull_msg.class);
			 
		Toast.makeText(Fragment_Main.instance  ,  g.test   , Toast.LENGTH_SHORT).show();
			    
		
		//本地消息记录还是空的。
	 
		//没有最新消息。
		if(g.msgs==null) return;
			
			//添加最新的消息在尾部
			for(int i=0;i<g.msgs.size();i++){
				
				//检查msg是否已经缓存
				if(mgr.getMsg(g.msgs.get(i).id)==null){
					//需要保证msg id是唯一的。 
					mDataArrays.addLast(g.msgs.get(i)); 
					
					//保存到db
					 mgr.add(g.msgs.get(i));
				}
				 
			}
			
			
			ChatMsgEntity   LastMsg= mgr.getLastMsg(Global.me.uid, friendID);
			//记录最后的消息id
			if(LastMsg!=null)
				lastID=LastMsg.id; 
			
			
			mAdapter.notifyDataSetChanged();
			  
		
 	}
	
	public void On_SEND_MSG(HttpPostAsyncTask o)
	{ 
		if(o.m_e!=null){
			Toast.makeText(  this  ,  o.m_e.getMessage()   , Toast.LENGTH_SHORT).show();
			return;
		}
		   
		send_msg g = JSON.parseObject( o.ret , send_msg.class);
			 
		if(g.code!=1)
			Toast.makeText(  this  ,   "send err"  , Toast.LENGTH_SHORT).show();
 
		 pull_msg.request(friendID,lastID);
 	}
	 
    
    public void head_xiaohei(View v) {     
    	InfoXiaoheiActivity.friendID=friendID;
    	Intent intent = new Intent (ChatActivity.this,InfoXiaoheiActivity.class);			
		startActivity(intent);	
      } 
    
    @Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		instance=null;
		
		//关闭时应释放DB  
		mgr.closeDB();  

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
	/**
	 * 打开或者关闭软键盘，之前若打开，调用该方法后关闭；之前若关闭，调用该方法后打开
	 */
	
	//private void setSoftInputState(){
	//	((InputMethodManager)ChatActivity.this.getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//	}
	
	private void setFaceLayoutExpandState(boolean isexpand){
		if(isexpand==false){

			viewFlipper.setDisplayedChild(0);	
			ViewGroup.LayoutParams params=faceLayout.getLayoutParams();
			params.height=1;
			faceLayout.setLayoutParams(params);	
			/**height不设为0是因为，希望可以使再次打开时viewFlipper已经初始化为第一页 避免
			*再次打开ViewFlipper时画面在动的结果,
			*为了避免因为1dip的高度产生一个白缝，所以这里在ViewFlipper所在的RelativeLayout中ViewFlipper
			*上层添加了一个1dip高的黑色色块
			*
			*viewFlipper必须在屏幕中有像素才能执行setDisplayedChild()操作
			*/
			//chatBottomLook.setBackgroundResource(R.drawable.chat_bottom_look);
			
			
		}
		else{
			/**
			 * 让软键盘消失
			 */
		//	((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
		//	(ChatActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

			
			
			ViewGroup.LayoutParams params=faceLayout.getLayoutParams();
			params.height=150;
			faceLayout.setLayoutParams(params);    
		  //  chatBottomLook.setBackgroundResource(R.drawable.chat_bottom_keyboard);

		}
	}
	
	/**
	 * 设置游标（小点）的显示效果
	 * @param darkPointNum
	 */
	private void setPointEffect(int darkPointNum){
		for(int i=0; i<pointList.size(); i++){
			pointList.get(i).setBackgroundResource(R.drawable.qian_point);
		}
		pointList.get(darkPointNum).setBackgroundResource(R.drawable.shen_point);
	}
	
	/**
	 * GridViewAdapter
	 * @param textView
	 * @param text
	 */
	
	class MyGridAdapter extends BaseAdapter{

		Context context;
		ArrayList<HashMap<String,Object>> list;
		int layout;
		String[] from;
		int[] to;
		
		
		public MyGridAdapter(Context context,
				ArrayList<HashMap<String, Object>> list, int layout,
				String[] from, int[] to) {
			super();
			this.context = context;
			this.list = list;
			this.layout = layout;
			this.from = from;
			this.to = to;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		class ViewHolder{
			ImageView image=null;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			if(convertView==null){
				convertView=LayoutInflater.from(context).inflate(layout, null);
				holder=new ViewHolder();
				holder.image=(ImageView)convertView.findViewById(to[0]);
				convertView.setTag(holder);
			}
			else{
				holder=(ViewHolder)convertView.getTag();
			}
			holder.image.setImageResource((Integer)list.get(position).get(from[0]));
			class MyGridImageClickListener implements OnClickListener{

				int position;
				
				public MyGridImageClickListener(int position) {
					super();
					this.position = position;
				}


				@Override
				public void onClick(View v) {
					Log.i("图标！","MyGridImageClickListener onClick"+position );
					// TODO Auto-generated method stub
					mEditTextContent.append((String)list.get(position).get("faceName"));
				}
				
			}
			//这里创建了一个方法内部类
			holder.image.setOnClickListener(new MyGridImageClickListener(position));
			
			
			
			return convertView;
		}
		
	}
	
	
	private boolean moveable=true;
	private float startX=0;
	
	/**
	 * 用到的方法 viewFlipper.getDisplayedChild()  获得当前显示的ChildView的索引
	 * @author Administrator
	 *
	 */
	class MyTouchListener implements OnTouchListener{

		ViewFlipper viewFlipper=null;
		
		
		public MyTouchListener(ViewFlipper viewFlipper) {
			super();
			this.viewFlipper = viewFlipper;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:startX=event.getX(); moveable=true; break;
			case MotionEvent.ACTION_MOVE:
				if(moveable){
					if(event.getX()-startX>60){
						moveable=false;
						int childIndex=viewFlipper.getDisplayedChild();
						/**
						 * 这里的这个if检测是防止表情列表循环滑动
						 */
						if(childIndex>0){
						  //  viewFlipper.setInAnimation(AnimationUtils.loadAnimation(ChatActivity.this, R.anim.left_in));
						  //  viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(ChatActivity.this, R.anim.right_out));						
							viewFlipper.showPrevious();
							setPointEffect(childIndex-1);
						}
					}
					else if(event.getX()-startX<-60){
						moveable=false;
						int childIndex=viewFlipper.getDisplayedChild();
						/**
						 * 这里的这个if检测是防止表情列表循环滑动
						 */
						if(childIndex<listGrid.size()-1){
							//viewFlipper.setInAnimation(AnimationUtils.loadAnimation(ChatActivity.this, R.anim.right_in));
							//viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(ChatActivity.this, R.anim.left_out));
							viewFlipper.showNext();
							setPointEffect(childIndex+1);
						}
					}
				}
			    break;
			case MotionEvent.ACTION_UP:moveable=true;break;
			default:break;
			}
			
			return false;
		}
		
	}
	
	
}