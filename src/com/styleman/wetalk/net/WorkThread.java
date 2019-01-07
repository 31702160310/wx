package com.styleman.wetalk.net;

 
import android.os.Handler;
import android.util.Log;

import com.styleman.wetalk.packet.pull;

public class WorkThread {
	
	public String TAG = "WorkThread";

 

	public static void start(){
		//只启动一次线程。。
		if(instance==null) {
			instance=new WorkThread();
			 
		}
		
	}
	
	private WorkThread(){
		objHandler.postDelayed(mTasks, 5000);
	}
	private static WorkThread instance = null;
	
	 
	
	private Handler objHandler = new Handler();

	private int intCounter = 0;
	private Runnable mTasks = new Runnable() {

		public void run() {

			intCounter++;
			
			
			/* �HLog����bLogCat�̿�Xlog�T���A�ʬݪA�Ȱ��污�p */
			Log.i(TAG, "Counter:" + Integer.toString(intCounter));

			if(Global.background_working)
			{
				//;
				pull.request();
			}
			
			
			/* 5秒 */
			objHandler.postDelayed(mTasks, 5000);
		}
	};
	 

}
