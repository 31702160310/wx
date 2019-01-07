
package com.styleman.wetalk.main;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styleman.wetalk.R;
import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.packet.User;

public class ChatMsgViewAdapter extends BaseAdapter {
	
	public static interface IMsgViewType
	{
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}
	
    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();

    private List<ChatMsgEntity> coll;

    private Context ctx;
    
    private Handler handler;
	
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context,Handler handler, List<ChatMsgEntity> coll) {
        ctx = context;
        this.coll = coll;
        this.handler=handler;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
    	if(coll==null) return 0;
        return coll.size();
    }

    public Object getItem(int position) {
    	if(coll==null) return null;
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    


	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	 	ChatMsgEntity entity = coll.get(position);
	 	
	 	if (entity.getMsgType())
	 	{
	 		return IMsgViewType.IMVT_COM_MSG;
	 	}else{
	 		return IMsgViewType.IMVT_TO_MSG;
	 	}
	 	
	}


	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	if(coll==null) return null;
    	
    	ChatMsgEntity entity = coll.get(position);
 
    		
    	ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {
	    	  if (entity.getMsgType())
			  {
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
			  }else{
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
			  }

	    	  viewHolder = new ViewHolder();
			  viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			  viewHolder.tvContent = (MyTextView) convertView.findViewById(R.id.tv_chatcontent);
			  viewHolder.tvhead = (ImageView) convertView.findViewById(R.id.iv_userhead);  
			  viewHolder.msg = entity;
			  
			  convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
	
	    
	     //
	    if(entity.getMsgType()){
	    	User u=Global.GetUser(entity.send);
	    	viewHolder.tvUserName.setText(u.nickName);
	    	 try{
	    		 viewHolder.tvhead.setImageResource( Global.imgid(u.face) );
	    	 }catch(Exception e){
	    		 e.printStackTrace();
	    	 }
	    		    
	    }else{
	    	//自己的昵称 
	    	viewHolder.tvUserName.setText(Global.me.nickName);
	    	 try{//自己的头像
	    		 viewHolder.tvhead.setImageResource( Global.imgid(Global.me.face) );
	    	 }catch(Exception e){
	    		 e.printStackTrace();
	    	 }
	    }
	    viewHolder.tvSendTime.setText(entity.createTime);
	    
	    viewHolder.tvContent.setSpanText  (handler,entity.content);//setText
	    
	    
	    return convertView;
    }
    

    static class ViewHolder { 
        public TextView tvSendTime;
        public TextView tvUserName;
        public MyTextView tvContent;
        public ImageView tvhead;
        public ChatMsgEntity msg;
        
    }


}
