package com.styleman.wetalk.main;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleman.wetalk.R;
import com.styleman.wetalk.main.Fragment_Contact.ItemHolder;
import com.styleman.wetalk.main.SessionAdapter.ViewHolder;
import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.packet.Session;
import com.styleman.wetalk.packet.User;

public class AddFriendAdapter extends BaseAdapter implements OnClickListener   {
	List<User>			SessionList;
	
	Context m_context;
	 
	public class  ViewHolder {
		public ImageView face;
		public TextView maxim;
		public TextView nickname;
	 
		 
	} 
  
	public AddFriendAdapter(List<User> i ,Context context ) {
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
 
		Log.v("AddFriendAdapter", "getView " + position + " " + convertView); 

		if (convertView == null) {
			itemview = new ViewHolder();
			convertView = LayoutInflater.from( m_context ).inflate(R.layout.m_expendlist_item, null);
			 
			
			//itemview.layout=(LinearLayout) convertView.findViewById(R.id.session_layout);
			
			//convertView = (View) getActivity().getLayoutInflater().from(context).inflate( R.layout.m_expendlist_item, null);
			 
			itemview.nickname = (TextView) convertView.findViewById(R.id.nickname);
			itemview.maxim = (TextView) convertView.findViewById(R.id.maxim);
			itemview.face = (ImageView) convertView.findViewById(R.id.face);
			  
			convertView.setTag(itemview);  //绑定ViewHolder对象  
		} else {
			itemview = (ViewHolder) convertView.getTag();//取出ViewHolder对象       
			 
		}
		 
		if (SessionList == null) return convertView;
		
	 

		User info = SessionList.get(SessionList.size() - position - 1);
		//	itemview.img_pic.setImageDrawable(GameActivity.getInstance().getResources().getDrawable(R.drawable.shop_qian));
 
		  
		try
		{
			itemview.face.setBackgroundResource( Global.imgid(info.face ) );
			itemview.nickname.setText(  info.nickName   );
			itemview.maxim.setText(info.maxim );
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		 
		 
		 
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
