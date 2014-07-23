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
        
        //������
        vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        hasVibrator=vibrator.hasVibrator(); 
        if(hasVibrator)
        {
        	vibrator.vibrate(new long[]{1000,2000,1000,2000},0);
        }
        
        //��������
        alarmMusic = MediaPlayer.create(this, R.raw.alarm);  
        alarmMusic.setLooping(true);  
        alarmMusic.start();

        //��ʾ�Ի���
        new AlertDialog.Builder(AlarmActivity.this).
            setTitle("����").//���ñ���
            setMessage("ʱ�䵽�ˣ�").//��������            
            setPositiveButton("֪����", new OnClickListener(){//���ð�ť
                public void onClick(DialogInterface dialog, int which) {
                	if(hasVibrator)
                	{
                		vibrator.cancel();
                	}
                	alarmMusic.stop();  
                    AlarmActivity.this.finish();//�ر�Activity
                }
            }).create().show();
        
        
    }


}
