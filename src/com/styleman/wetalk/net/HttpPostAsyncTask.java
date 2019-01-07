package com.styleman.wetalk.net;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
 
/*

	异步httppost类

//post提交
List <NameValuePair> params = new ArrayList <NameValuePair>(); 
params.add(new BasicNameValuePair("username", "sm")); 
params.add(new BasicNameValuePair("password", "123")); 
HttpPostAsyncTask task = new HttpPostAsyncTask("http://192.168.1.100/s/login.php",params,m_handler); 
task.execute("");


//消息handler.接收异步的结果
public Handler m_handler=new Handler(){
		@Override
		public   void handleMessage(Message msg) {
			Log.i("TAG","handleMessage  "); 
			super.handleMessage(msg);
			switch(msg.what){ 
			case HttpPostAsyncTask.HttpPostAsyncTask_MSG: 	  
				HttpPostAsyncTask o=(HttpPostAsyncTask)msg.obj;
				if(o.m_e==null)//如果post异常 则会保存异常值。
					Toast.makeText(MainActivity.this  ,  o.ret   , Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(MainActivity.this  ,  o.m_e.getMessage()   , Toast.LENGTH_SHORT).show();
				
				break; 
			}
		}
	 };


*/


public class HttpPostAsyncTask extends AsyncTask<String, Integer, String> {
	private static final String	TAG			= "HttpPostAsyncTask";
	
	public static final int HttpPostAsyncTask_MSG=1001;// id要保证唯一。
	
	public String					m_url;
	public List <NameValuePair> 			m_params;
	public Handler m_handler;
	public String ret;
	public  Exception m_e;
	public int m_cmd;
	
	public HttpPostAsyncTask(String  url , List <NameValuePair> params ,Handler handler,int cmd) {
		m_e=null;
		m_url=url;
		m_cmd=cmd;
		m_params=params;
		m_handler=handler;
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		   
			try {
				Log.i(TAG,"doInBackground post begin");
				
				  ret=HttpUtil.Post( m_url , m_params ,"utf-8");
				//ret=URLDecoder.decode(ret, "utf-8");
			} catch ( Exception e) {
				 
				//e.printStackTrace();
				m_e=e;
				
				Log.e(TAG, "doInBackground Exception"+e.getMessage());
				
				return ret;
			} 
			
			Log.i(TAG,"doInBackground post recv["+ret+"]");
			Log.i(TAG,"doInBackground post end");
			
			return ret;
		
		/*String downURL = "http://" + GDF.UrlLogin + "/" + "CustomFace.aspx?UserID=" + lUserID + "&CustomID=" + lCustomID;
		Util.i(TAG, "ͷ��downURL:" + downURL);
		InputStream bitmapIs = null;
		Bitmap bitmap = null;
		try {
			bitmapIs = Util.getStreamFromURL(downURL);
			if (bitmapIs != null) {
				byte[] buffer = new byte[9216];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = bitmapIs.read(temp)) > 0) {
					System.arraycopy(temp, 0, buffer, destPos, readLen);
					destPos += readLen;
				}
				if (destPos == data_size) {
					int[] nARGB = changebyte(buffer);
					bitmap = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888);
					for (int i = 0; i < 48; i++) {
						for (int j = 0; j < 48; j++) {
							bitmap.setPixel(j, i, nARGB[i * 48 + j]);
						}
					}

					if (CustomFaceManage.getInstance() != null) {
						CustomFaceManage.getInstance().onAddBitmap(lUserID + "-" + lCustomID, bitmap);
					}
					SDCardHelp.SaveImage(bitmap, GDF.GAME_NAME + "/" + lUserID, lCustomID + ".png");
					if (GameClientActivity.getInstance() != null) {
						GameClientActivity.getInstance().m_ClockHandler.obtainMessage(GameClientActivity.IDI_UPDATEVIEW).sendToTarget();
					}
					Util.i(TAG, "�����ɹ�");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bitmapIs != null)
					bitmapIs.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				bitmapIs = null;
			}
		}*/
		

		  
	}
	 /** 
	68.     * Get data from stream 
	69.     * @param inStream 
	70.     * @return byte[] 
	71.     * @throws Exception 
	72.     */  
	    public static byte[] readStream(InputStream inStream) throws Exception{  
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] buffer = new byte[1024];  
	        int len = 0;  
	        while( (len=inStream.read(buffer)) != -1){  
	            outStream.write(buffer, 0, len);  
	        }  
	        outStream.close();  
	        inStream.close();  
	        return outStream.toByteArray();  
	    }  

	 /** 
	52.     * Get image from newwork 
	53.     * @param path The path of image 
	54.     * @return InputStream 
	55.     * @throws Exception 
	56.     */  
	    public InputStream getImageStream(String path) throws Exception{  
	        URL url = new URL(path);  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5 * 1000);  
	        conn.setRequestMethod("GET");  
	        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
	            return conn.getInputStream();  
	        }  
	        return null;  
	   }  
 


	@Override	//doInBackground 执行完后调用 onPostExecute  
	protected void onPostExecute(String result) {
		//Log.i(TAG, " onPostExecute 异步操作执行结束  " + result);  
		Message msg=new Message();
		msg.what=HttpPostAsyncTask_MSG; // 
		msg.obj=this;
		m_handler.sendMessage(msg); 
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onProgressUpdate(Integer... ainteger) {
	}

	@Override
	protected void onCancelled() {
	}


}
