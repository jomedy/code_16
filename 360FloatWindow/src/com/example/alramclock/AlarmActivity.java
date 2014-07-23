package com.example.alramclock;


import com.example.floatwindow.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Service;


public class AlarmActivity extends Activity {
	private Vibrator vibrator=null;
	private MediaPlayer alarmMusic=null;
	boolean hasVibrator=false;
	

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //设置振动
        vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        hasVibrator=vibrator.hasVibrator(); 
        if(hasVibrator)
        {
        	vibrator.vibrate(new long[]{1000,2000,1000,2000},0);
        }
        
        //设置铃声
        alarmMusic = MediaPlayer.create(this, R.raw.alarm);  
        alarmMusic.setLooping(true);  
        alarmMusic.start();

        //显示对话框
        new AlertDialog.Builder(AlarmActivity.this).
            setTitle("闹钟").//设置标题
            setMessage("时间到了！").//设置内容            
            setPositiveButton("知道了", new OnClickListener(){//设置按钮
                public void onClick(DialogInterface dialog, int which) {
                	if(hasVibrator)
                	{
                		vibrator.cancel();
                	}
                	alarmMusic.stop();  
                    AlarmActivity.this.finish();//关闭Activity
                }
            }).create().show();
        
        
    }


}
