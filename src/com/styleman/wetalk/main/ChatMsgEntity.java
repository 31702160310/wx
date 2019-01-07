
package com.styleman.wetalk.main;

import com.styleman.wetalk.net.Global;

public class ChatMsgEntity {
    private static final String TAG = ChatMsgEntity.class.getSimpleName();
   
    public int id;
  
    public String createTime;
    
    public int state;
    
    public int send;
    
    public int _to;
    
    public String content;

   // private boolean isComMeg = true;
    //true表示 别人发来的消息。
    public boolean getMsgType()
    {
    	return _to==Global.me.uid;//接收人==自己 表示是 发来的消息
    }
//    public ChatMsgEntity() {
//    }

//    public ChatMsgEntity(String name, String date, String text, boolean isComMsg) {
//        super();
//        this.name = name;
//        this.date = date;
//        this.text = text;
//        this.isComMeg = isComMsg;
//    }

}
