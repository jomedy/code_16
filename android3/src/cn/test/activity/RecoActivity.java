package cn.test.activity;

import com.test.model.Guide;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class RecoActivity extends Activity
{
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			//Ӧ���Ƽ���Activity��չʾ
			Guide g = (Guide)msg.obj;
			
			TextView tv1 = (TextView)findViewById(R.id.guideText1);
			tv1.setText(g.getGuideText1());
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend_activity);

		Thread t = new Thread(downloadThread);
		t.start();
	}
	
	Runnable downloadThread = new Runnable()
	{
		public void run()
		{
			
			//ʹ��HttpClient��ȡ�ֻ����ֵ�REST
			/*
			String url = "";
			String result = WinFloatUtils.download(url);
			if(result == null)
			{
				//.................
				return;
			}
			*/
			
			//��String����JSON������ת��Ϊ����
			//.................
			
			
			//��ŵ�Message��
			Message msg = handler.obtainMessage();
			Guide guide = new Guide();
			guide.setGuideText1("guide1");
			msg.obj = guide;
			
			handler.sendMessage(msg);
		}
	};
	
	
	
}
