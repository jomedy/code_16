//Œﬁ”√
package com.example.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.widget.Toast;
public class AlarmReceiver extends BroadcastReceiver{

	
	
	private Vibrator vibrator=null;
	private MediaPlayer alarmMusic=null;
	boolean hasVibrator=false;
    @Override
    public void onReceive(Context context, Intent intent) {
    	if("com.alarm.action_alarm_on".equals(intent.getAction()))
    	{
    		//Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
    		Intent i=new Intent(context, AlarmActivity.class);
    		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		context.startActivity(i);
    		
    		
    	}
    }
}