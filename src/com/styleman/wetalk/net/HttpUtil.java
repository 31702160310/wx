package com.styleman.wetalk.net;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtil {
	String TAG="HttpUtil";

    public static String eregi_replace(String strFrom, String strTo, String strTarget)
    {
      String strPattern = "(?i)"+strFrom;
      Pattern p = Pattern.compile(strPattern);
      Matcher m = p.matcher(strTarget);
      if(m.find())
      {
        return strTarget.replaceAll(strFrom, strTo);
      }
      else
      {
        return strTarget;
      }
    }

	public static String Get(String uriAPI,String charset)
	{ 
		String strResult="";
        HttpGet httpRequest = new HttpGet(uriAPI); 
        try 
        { 
        	HttpClient httpclient = new DefaultHttpClient();  
        	// 连接超时  
      	  httpclient.getParams().setParameter(     CoreConnectionPNames. CONNECTION_TIMEOUT, 5000);  
        	 // 读取超时  
      	  httpclient.getParams().setParameter(     CoreConnectionPNames. SO_TIMEOUT, 5000);  
        	 
      	  							//new DefaultHttpClient()
          HttpResponse httpResponse = httpclient.execute(httpRequest); 
           
          if(httpResponse.getStatusLine().getStatusCode() == 200)  
          { 
        	  // 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1   
        	    
              strResult = EntityUtils.toString(httpResponse.getEntity(), charset);
              strResult=eregi_replace("(\r\n|\r|\n|\n\r)","",strResult);
            
              return  strResult;
           
          } 
          else 
          { 
        	  
          } 
        } 
        catch (Exception e) 
        {   
          e.printStackTrace();  
        }  
       
        return  strResult;	
	}
	

    public static   String  Post(String uriAPI,List <NameValuePair> params ,String charset) throws ClientProtocolException, IOException  
    { 
    	String strResult="";
      
      HttpPost httpRequest = new HttpPost(uriAPI); 
       
     // try 
      { 
    	  HttpClient httpclient = new DefaultHttpClient();  
      	// 连接超时  
    	  httpclient.getParams().setParameter(     CoreConnectionPNames. CONNECTION_TIMEOUT, 5000);  
      	 // 读取超时  
    	  httpclient.getParams().setParameter(     CoreConnectionPNames. SO_TIMEOUT, 5000);  
      	 
        httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
        					//new DefaultHttpClient()
        HttpResponse httpResponse = httpclient.execute(httpRequest); 
        
        if(httpResponse.getStatusLine().getStatusCode() == 200)  
        {  
        	// 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1   
        	
    	  //  Log.i("httputil","httpResponse.getEntity()["+httpResponse.getEntity().toString()+"]" );
    	    
            strResult = EntityUtils.toString(httpResponse.getEntity(), charset);
           // strResult=eregi_replace("(\r\n|\r|\n|\n\r)","",strResult);
          //  Log.i("httputil","httpResponse.getEntity()["+strResult +"]" );
        } 
        else 
        { 
        	//mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString()); 
        } 
      } 
       
      //catch (Exception e) 
      {  
        
        //e.printStackTrace();  
      }  
       
     return strResult;
  } 

	
	
}
