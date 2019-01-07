package com.styleman.wetalk.db;
 

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.styleman.wetalk.main.ChatMsgEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;
	
	public DBManager(Context context) {
		helper = new DBHelper(context);
		//因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
		//所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}
	
	/**
	 * add persons
	 * @param persons
	 */
	public void add(List<ChatMsgEntity> persons) {
        db.beginTransaction();	//开始事务
        try {
        	for (ChatMsgEntity o : persons) {
        		db.execSQL("INSERT INTO tb_friendmsg VALUES(?, ?, ?, ?, ?)", 
        				new Object[]{o.id, o.createTime, o.send,o._to,o.content});
        	}
        	db.setTransactionSuccessful();	//设置事务成功完成
        }catch(Exception e)
        {
        	e.printStackTrace();
        }finally {
        	db.endTransaction();	//结束事务
        }
	}
	
	
	public void add(  ChatMsgEntity  o) {
        db.beginTransaction();	//开始事务
        try {
        	 
        		db.execSQL("INSERT INTO tb_friendmsg VALUES(?, ?, ?, ?, ?)", 
        				new Object[]{o.id, o.createTime, o.send,o._to,o.content});
        	 
        	db.setTransactionSuccessful();	//设置事务成功完成
        } catch(Exception e)
        {
        	e.printStackTrace();
        }finally {
        	db.endTransaction();	//结束事务
        }
	}
	/**
	 * update person's age
	 * @param person
	 */
//	public void updateAge(Person person) {
//		ContentValues cv = new ContentValues();
//		cv.put("age", person.age);
//		db.update("tb_friendmsg", cv, "name = ?", new String[]{person.name});
//	}
	
	/**
	 * delete old person
	 * @param person
	 */
//	public void deleteOldPerson(Person person) {
//		db.delete("tb_friendmsg", "age >= ?", new String[]{String.valueOf(person.age)});
//	}
	
	/**
	 * query all persons, return list
	 * int startID,消息开始点
	 * int uid,自己id
	 * int fid 好友id
	 * 查询自己和好友的聊天消息记录
	 * @return List<Person>
	 */
	public LinkedList<ChatMsgEntity> query(int startID,int uid,int fid) {
		LinkedList<ChatMsgEntity> persons = new LinkedList<ChatMsgEntity>();
		Cursor c = queryTheCursor(  startID,  uid,  fid);
        while (c.moveToNext()) {
        	ChatMsgEntity person = new ChatMsgEntity();
        	person.id = c.getInt(c.getColumnIndex("id"));
        	person.send = c.getInt(c.getColumnIndex("send"));
        	person._to=c.getInt(c.getColumnIndex("_to"));
        	person.createTime = c.getString(c.getColumnIndex("createTime"));
        	person.content = c.getString(c.getColumnIndex("content"));
        	persons.add(person);
        }
        c.close();
        return persons;
	}
	
	/**
	 * query all persons, return cursor
	 * @return	Cursor
	 */
	public Cursor queryTheCursor(int startID,int uid,int fid) {
		String s="SELECT * FROM tb_friendmsg  where id>"+startID
				+" and ( ( _to="+uid+" and send="+fid+" ) or ( _to="+fid+" and send="+uid+" )) order by id ";
       
		Cursor c = db.rawQuery(s, null);
        return c;
	}
	
	
	public ChatMsgEntity getMsg(int id){
		String s="SELECT * FROM tb_friendmsg  where  id ="+id;
       
		Cursor c = db.rawQuery(s, null);
		  while (c.moveToNext()) {
	        	ChatMsgEntity person = new ChatMsgEntity();
	        	person.id = c.getInt(c.getColumnIndex("id"));
	        	person.send = c.getInt(c.getColumnIndex("send"));
	        	person._to=c.getInt(c.getColumnIndex("_to"));
	        	person.createTime = c.getString(c.getColumnIndex("createTime"));
	        	person.content = c.getString(c.getColumnIndex("content"));
	        	  
	        	 c.close();
	        	 
	        	 return person;
	        }
	        
		return null;
		 
	}
	
	public ChatMsgEntity getLastMsg(int uid,int fid){
		String s="SELECT * FROM tb_friendmsg  where" 
				+"   ( ( _to="+uid+" and send="+fid+" ) or ( _to="+fid+" and send="+uid+" )) order by id desc";
       
		Cursor c = db.rawQuery(s, null);
		  while (c.moveToNext()) {
	        	ChatMsgEntity person = new ChatMsgEntity();
	        	person.id = c.getInt(c.getColumnIndex("id"));
	        	person.send = c.getInt(c.getColumnIndex("send"));
	        	person._to=c.getInt(c.getColumnIndex("_to"));
	        	person.createTime = c.getString(c.getColumnIndex("createTime"));
	        	person.content = c.getString(c.getColumnIndex("content"));
	        	 
	        	
	        	 c.close();
	        	 
	        	 return person;
	        }
	        
		return null;
	}
	
	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}
}
