package com.styleman.wetalk.main;
 
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.styleman.wetalk.R;
import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.packet.Session;
 
public class SessionAdapter extends BaseAdapter implements OnClickListener   {

	@SuppressWarnings("unused")
	private static final String	TAG	= "SessionAdapter";
	
	List<Session>			SessionList;
	
	Context m_context;
	 
	public class  ViewHolder {
		ImageView	img_pic;
		TextView	txt_name;
		TextView	txt_describe;
		TextView session_content;
		ImageView new_msg;
	} 
  
	public SessionAdapter(List<Session> i ,Context context ) {
		SessionList = i;
		m_context=context;
	}

	@Override
	public int getCount() {
		if (SessionList != null)
			return SessionList.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (SessionList != null && SessionList.size() > 0)
			return SessionList.get(SessionList.size() - position - 1);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final  ViewHolder itemview;

		//观察convertView随ListView滚动情况             


		Log.v("MyListViewBase", "getView " + position + " " + convertView); 

		if (convertView == null) {
			itemview = new ViewHolder();
			convertView = LayoutInflater.from( m_context ).inflate(R.layout.m_session_item, null);
			 
			
			//itemview.layout=(LinearLayout) convertView.findViewById(R.id.session_layout);
			
			itemview.img_pic = (ImageView) convertView.findViewById(R.id.session_head);
			itemview.txt_name = (TextView) convertView.findViewById(R.id.session_nickname);
			itemview.txt_describe = (TextView) convertView.findViewById(R.id.session_time);
			itemview.session_content = (TextView) convertView.findViewById(R.id.session_content);
			itemview.new_msg= (ImageView) convertView.findViewById(R.id.session_new_message);
			convertView.setTag(itemview);  //绑定ViewHolder对象  
		} else {
			itemview = (ViewHolder) convertView.getTag();//取出ViewHolder对象       
			 
		}
		 
		if (SessionList == null) return convertView;
		
	 

		Session info = SessionList.get(SessionList.size() - position - 1);
		//	itemview.img_pic.setImageDrawable(GameActivity.getInstance().getResources().getDrawable(R.drawable.shop_qian));
 
		  
		try
		{
			itemview.img_pic.setBackgroundResource( Global.imgid( Global.GetUser(info.friendID).face ) );
			itemview.txt_name.setText(  Global.GetUser( info.friendID).nickName   );
			
			//
			 
			if(info.state<0){
				itemview.new_msg.setVisibility(View.VISIBLE);
			}else{
				itemview.new_msg.setVisibility(View.INVISIBLE  );
			}
			
		}catch(Exception e)
		{
			
		}
		 
		itemview.session_content.setText(info.lastContent );
		itemview.txt_describe.setText(info.lastTime );
	//	itemview.bt_buy.setTag(SessionList.size() - position - 1);
	//	itemview.setOnClickListener(this);

       
		return convertView;
	}

	//����btn
	@Override
	public void onClick(View view) {

//		int index = (Integer) view.getTag();
//		
//		Log.v(TAG, " onClick "+index);
//		
//		Session info = SessionList.get(index);
//		
//		Toast.makeText(m_context, "点击 uid:"+ info.friendID , Toast.LENGTH_LONG).show(); 
		 
	}

 
 
}   
