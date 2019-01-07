package com.styleman.wetalk.main;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.utils.FaceData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView{


	/**
	 * @author Dragon
	 * SpanInfo �����ڴ洢һ��Ҫ��ʾ��ͼƬ����̬��̬������Ϣ�������ֽ���ÿһ֡mapList��������ֵ���ʼλ�á���ֹλ��
	 * ��֡������ǰ��Ҫ��ʾ��֡��֡��֮֡���ʱ���� 
	 */
	private class SpanInfo{
		ArrayList<Bitmap> mapList;
		int start,end,frameCount,currentFrameIndex,delay;
		public SpanInfo(){
			mapList=new ArrayList<Bitmap>();
			start=end=frameCount=currentFrameIndex=delay=0;	
		}
	}
	
	/**
	 * spanInfoList ��һ��SpanInfo��list ,���ڴ���һ��TextView�г��ֶ��Ҫƥ���ͼƬ�����
	 */
	private ArrayList<SpanInfo> spanInfoList=null;
	private Handler handler;           //���ڴ�������߳�TextView��������Ϣ
	private String myText;             //�洢textViewӦ����ʾ���ı�
	
	/**
	 * ��������췽��һ��Ҳ��Ҫ�٣���������CastException��ע������������캯���ж�ΪspanInfoListʵ�������Щ�˷�
	 * ������֤�����п�ָ���쳣
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		spanInfoList=new ArrayList<SpanInfo>();
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		spanInfoList=new ArrayList<SpanInfo>();
	}

	public MyTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		spanInfoList=new ArrayList<SpanInfo>();
	}
	
	
	
	/**
	 * ��Ҫ��ʾ��textView�ϵ��ı����н����������Ƿ��ı�������Ҫ��Gif���߾�̬ͼƬƥ����ı�
	 * ���У���ô����parseGif �Ը��ı���Ӧ��GifͼƬ���н��� ��������parseBmp������̬ͼƬ
	 * @param inputStr
	 */
	private void parseText(String inputStr){
		myText=inputStr;
		Pattern mPattern=Pattern.compile("\\\\..");
		Matcher mMatcher=mPattern.matcher(inputStr);
		while(mMatcher.find()){
			String faceName=mMatcher.group();
			Integer faceId=null;
			/**
			 * ����ƥ��ʱ�õ���ͼƬ�⣬��һ��ר�Ŵ��ͼƬid����ƥ�����Ƶľ�̬������������̬���������FaceData.java
			 * �У��������˾�̬��ķ��������˳�ʼ���������п�ָ���쳣
			 */
			
			if((faceId=FaceData.gifFaceInfo.get(faceName))!=null){
				parseGif(faceId, mMatcher.start(), mMatcher.end());
			}
			else if((faceId=FaceData.staticFaceInfo.get(faceName))!=null){
				parseBmp(faceId, mMatcher.start(), mMatcher.end());
			}
			
		}
		
		
	}
	
	/**
	 * �Ծ�̬ͼƬ���н�����
	 * ����һ��SpanInfo����֡����Ϊ1����������Ĳ������ã����Ҫ��ǽ�SpanInfo������ӽ�spanInfoList�У�
	 * ���򲻻���ʾ
	 * @param resourceId
	 * @param start
	 * @param end
	 */
	private void parseBmp(int resourceId,int start, int end){
		Bitmap bitmap=BitmapFactory.decodeResource(getContext().getResources(), resourceId);
		ImageSpan imageSpan=new ImageSpan(getContext(),bitmap);
		SpanInfo spanInfo=new SpanInfo();
		spanInfo.currentFrameIndex=0;
		spanInfo.frameCount=1;
		spanInfo.start=start;
		spanInfo.end=end;
		spanInfo.delay=100;
		spanInfo.mapList.add(bitmap);
		spanInfoList.add(spanInfo);
		
	}
	
	/**
	 * ����GifͼƬ���뾲̬ͼƬΨһ�Ĳ�ͬ��������Ҫ����GifOpenHelper���ȡGif����һϵһ��bitmap����for ѭ������һ
	 * ���bitmap�洢��SpanInfo.mapList�У���ʱ��frameCount����Ҳ����1�ˣ�
	 * @param resourceId
	 * @param start
	 * @param end
	 */
	private void parseGif(int resourceId ,int start, int end){   
	
		GifOpenHelper helper=new GifOpenHelper();
		helper.read(getContext().getResources().openRawResource(resourceId));
		SpanInfo spanInfo=new SpanInfo();
		spanInfo.currentFrameIndex=0;
		spanInfo.frameCount=helper.getFrameCount();
		spanInfo.start=start;
		spanInfo.end=end;
		spanInfo.mapList.add(helper.getImage());
		for(int i=1; i<helper.getFrameCount(); i++){
			spanInfo.mapList.add(helper.nextBitmap());
		}
		spanInfo.delay=helper.nextDelay();        //���ÿһ֮֡����ӳ�
		spanInfoList.add(spanInfo);

	}
	
	/**
	 * MyTextView ���ⲿ����Ľӿڣ��Ժ������ı�����ʱʹ��setSpanText() ������setText();
	 * @param handler
	 * @param text
	 */
	public void setSpanText(Handler handler, String text){
		this.handler=handler;      //���UI��Handler
		parseText(text);           //��String������н���
		TextRunnable r=new TextRunnable();   //���Runnable����
		handler.post(r);       //����UI�̵߳�Handler ��r��ӽ���Ϣ�����С�
		
	}
	
	
	public class TextRunnable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			SpannableString sb=new SpannableString(""+myText);   //���Ҫ��ʾ���ı�
			int gifCount=0;
			SpanInfo info=null;
			for(int i=0; i<spanInfoList.size(); i++){  //forѭ����������ʾ���ͼƬ������
				info=spanInfoList.get(i);
				if(info.mapList.size()>1){       
					/*
					 * gifCount���������Gif����BMP������gif gifCount>0 ,����gifCount=0
					 */
					gifCount++;
					
				}
				 
				 
				
				try{
					Bitmap bitmap=info.mapList.get(info.currentFrameIndex);
					info.currentFrameIndex=0 ;//(info.currentFrameIndex+1)%(info.frameCount);//大量数据刷列表时，会导致这里还是上面索引越界异常
					/**
					 * currentFrameIndex ���ڿ��Ƶ�ǰӦ����ʾ��֡����ţ�ÿ����ʾ֮��currentFrameIndex
					 * Ӧ�ü�1 ���ӵ�frameCount���ٱ��0ѭ����ʾ
					 */
					
					if(gifCount!=0){
					    bitmap=Bitmap.createScaledBitmap(bitmap, 60, 60, true);
					
					}
					else{
						bitmap=Bitmap.createScaledBitmap(bitmap, 30, 30, true);
					}
					
					ImageSpan imageSpan=new ImageSpan(getContext(),bitmap);   
					sb.setSpan(imageSpan, info.start, info.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			//�����е�ͼƬ��Ӧ��ImageSpan������ú󣬵���TextView��setText���������ı�
			MyTextView.this.setText(sb);  
			
			/**
			 * ��һ����Ϊ�˽�ʡ�ڴ�����ã�������ı���ֻ�о�̬ͼƬû�ж�̬ͼƬ����ô���߳̾ʹ���ֹ�������ظ�ִ��
			 * ��������ж�ͼ����ô��һֱִ��
			 */
			if(gifCount!=0){
				handler.postDelayed(this,info.delay );
			
			}
			
		}
		
	}
	
	

}
