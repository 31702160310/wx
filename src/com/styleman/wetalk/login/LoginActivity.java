package com.styleman.wetalk.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.styleman.wetalk.R;
import com.styleman.wetalk.db.DBManager;
import com.styleman.wetalk.main.Fragment_Main;
import com.styleman.wetalk.net.Global;
import com.styleman.wetalk.net.HttpPostAsyncTask;
import com.styleman.wetalk.net.NetHandler;
import com.styleman.wetalk.net.WorkThread;
import com.styleman.wetalk.packet.Login;


/*
 * 
 * ��½ �����˺� ����
 * 
 * */
public class LoginActivity extends Activity {
	private EditText mUser; // �ʺű༭��
	private EditText mPassword; // ����༭��
	String TAG ="LoginActivity";
	public static LoginActivity instance=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	instance=this;
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        mUser = (EditText)findViewById(R.id.login_user_edit);
        mPassword = (EditText)findViewById(R.id.login_passwd_edit);
        mUser.setText("sm");
        mPassword.setText("123");
    }

    public void login_mainweixin(View v) {
    	
    	Login.request(mUser.getText().toString(),mPassword.getText().toString());
    	
    /*	
    	if(true)
    	//if("buaa".equals(mUser.getText().toString()) && "123".equals(mPassword.getText().toString()))   //�ж� �ʺź�����
        {
             Intent intent = new Intent();
             intent.setClass(LoginActivity.this,LoadingActivity.class);
             startActivity(intent);
             //Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
          }
        else if("".equals(mUser.getText().toString()) || "".equals(mPassword.getText().toString()))   //�ж� �ʺź�����
        {
        	new AlertDialog.Builder(LoginActivity.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("��¼����")
			.setMessage("΢���ʺŻ������벻��Ϊ�գ�\n��������ٵ�¼��")
			.create().show();
         }
        else{
           
        	new AlertDialog.Builder(LoginActivity.this)
			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
			.setTitle("��¼ʧ��")
			.setMessage("΢���ʺŻ������벻��ȷ��\n������������룡")
			.create().show();
        }
    	*/
    	//��¼��ť
    	/*
      	Intent intent = new Intent();
		intent.setClass(Login.this,Whatsnew.class);
		startActivity(intent);
		Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
		this.finish();*/
      }  
    public void login_back(View v) {     //������ ���ذ�ť
      	this.finish();
      }  
    public void login_pw(View v) {     //������밴ť
    	Uri uri = Uri.parse("http://3g.qq.com"); 
    	Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
    	startActivity(intent);
    	//Intent intent = new Intent();
    	//intent.setClass(Login.this,Whatsnew.class);
        //startActivity(intent);
      }  
    
   
    public void On_LOGIN( HttpPostAsyncTask o)  
    {       
    	if(o.m_e==null)
    	{	
    		 Login lg = JSON.parseObject( o.ret , Login.class);
    		if( lg.code ==1 )
    		{
    			Toast.makeText( this  ,  lg.desc   , Toast.LENGTH_SHORT).show();
    			Toast.makeText( this  ,  o.ret   , Toast.LENGTH_SHORT).show();
    			
    			Global.me=lg.me;
    			Global.token=lg.token;
    			Global.background_working=true;
    			 
    			WorkThread.start();
    			
    			//进入加载界面。
    			  Intent intent = new Intent();
    	         intent.setClass(LoginActivity.this,LoadingActivity.class);
    	           startActivity(intent);
    	             //Toast.makeText(getApplicationContext(), "��¼�ɹ�", Toast.LENGTH_SHORT).show();
    		}else{
    			new AlertDialog.Builder(LoginActivity.this)
    			.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
    			.setTitle("login")
    			.setMessage( lg.desc )
    			.create().show();
    		}
    	}else
    		//ex说明网络错误
			Toast.makeText(LoginActivity.this  ,  o.m_e.getMessage()   , Toast.LENGTH_SHORT).show();
		
	}  
    @Override
  	public void onDestroy() {
      	instance=null;
  		// TODO Auto-generated method stub
  		super.onDestroy();
  		Log.v(TAG, " onDestroy()");
  	} 
}
