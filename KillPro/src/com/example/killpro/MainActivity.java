package com.example.killpro;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity 
{
	/** Called when the activity is first created. */

	private Button button;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button);

		button.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				killAll(getApplicationContext());
			}

			
		});
	}
	class rekillinfo
	{
		public int m_killpronum; //ɱ��������
		public long m_freememsize;//�ͷ��ڴ���
	}
	private rekillinfo killAll(Context context) 
	{
		long firstmem = getAvailMemory(context);
		//		��ȡһ��ActivityManager ����
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
      //��ȡϵͳ�������������еĽ���
        List<RunningAppProcessInfo> appProcessInfos = activityManager
                .getRunningAppProcesses();
        int firstpronum = appProcessInfos.size();
        System.out.println("ApplicationInfo-->��̨������ ��"+firstpronum);
      //��ȡ��ǰactivity���ڵĽ���
	    String currentProcess=context.getApplicationInfo().processName;
	  //ά��һ����ɱ���̰�������
	    ArrayList<String> baimindanList = new ArrayList<String>();
	    baimindanList.add("com.huawei.android.launcher");
	    baimindanList.add(currentProcess);
      //��ϵͳ�������������еĽ��̽��е�����������������ǰ��������̣���Kill��
        for (RunningAppProcessInfo appProcessInfo : appProcessInfos)
        {
        	String processName=appProcessInfo.processName;
        	// һ����ֵ����RunningAppProcessInfo.IMPORTANCE_SERVICE�Ľ��̶���ʱ��û�û��߿ս�����  
            // һ����ֵ����RunningAppProcessInfo.IMPORTANCE_VISIBLE�Ľ��̶��Ƿǿɼ����̣�Ҳ�����ں�̨������  
        	String strname = appProcessInfo.processName;
        	if(appProcessInfo.importance > RunningAppProcessInfo.IMPORTANCE_VISIBLE 
        		 && !(-1!= baimindanList.indexOf(strname)))
        	 {
        		activityManager.killBackgroundProcesses(processName);
        		//forceStopAPK(processName);
	        	 System.out.println("Killed -->PID:"+appProcessInfo.pid+"--ProcessName:"+processName);
        	 }
        }
        appProcessInfos.clear();
        appProcessInfos = activityManager
                .getRunningAppProcesses();
        int lastpronum = appProcessInfos.size();
        System.out.println("ApplicationInfo-->��̨������ ��"+lastpronum);
        
        long lastmem = getAvailMemory(context);
        rekillinfo ret = new rekillinfo();
        ret.m_killpronum = firstpronum - lastpronum;
        ret.m_freememsize =lastmem - firstmem;
        System.out.println("ApplicationInfo-->ɱ����̨������ ��"+ret.m_killpronum);
        System.out.println("ApplicationInfo-->�ͷ��ڴ� ��"+ret.m_freememsize);
        
		return ret;
	}
	

	private long getAvailMemory(Context context) 
    {
        // ��ȡandroid��ǰ�����ڴ��С 
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; ��ǰϵͳ�Ŀ����ڴ� 
        System.out.println("ApplicationInfo-->��ǰ�����ڴ� "+mi.availMem/(1024*1024)+"MB");
        //return Formatter.formatFileSize(context, mi.availMem);// ����ȡ���ڴ��С��� 
        return mi.availMem/(1024*1024);
    }

    private long getTotalMemory(Context context) 
    {
        String str1 = "/proc/meminfo";// ϵͳ�ڴ���Ϣ�ļ� 
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try 
        {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
            localFileReader, 8192);
            str2 = localBufferedReader.readLine();// ��ȡmeminfo��һ�У�ϵͳ���ڴ��С 

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// ���ϵͳ���ڴ棬��λ��KB������1024ת��ΪByte 
            localBufferedReader.close();

        } catch (IOException e) {
        }
        //return Formatter.formatFileSize(context, initial_memory);// Byteת��ΪKB����MB���ڴ��С��� 
        return initial_memory/(1024*1024);
    }
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}
