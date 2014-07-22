package com.example.myalarm;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btn=null;
	private Button btn_stop=null;
    private AlarmManager alarmManager=null;    
    Calendar cal=Calendar.getInstance();
    final int DIALOG_TIME = 0;    //设置对话框id
    boolean hasAlarm=false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener(){            
			@SuppressWarnings("deprecation")
			public void onClick(View view) {
                showDialog(DIALOG_TIME);//显示时间选择对话框
            	//onCreateDialog(DIALOG_TIME).show();
                
            }
        });
        
        btn_stop=(Button)findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View view) {
        		Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);    //创建Intent对象                            
                intent.setAction("com.alarm.action_alarm_on");
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                alarmManager.cancel(pi);
        		Toast.makeText(MainActivity.this, "Alarm Stop", Toast.LENGTH_SHORT).show();
        		
            }
        });
        
    }

   /* public void showTimePickerDialog() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
         
        TimePickerDialog dialog = new TimePickerDialog(MainActivity.this, new OnTimeSetListener(){     
            @Override
            public void onTimeSet(TimePicker arg0, int hour, int minute){
                System.out.println(hour + ":" + minute);
            }
             
        }, hour, minute, true);
        dialog.show();
    }*/
 
    
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog=null;
        switch (id) {
        case DIALOG_TIME:
            dialog=new TimePickerDialog(
                    this, 
                    new TimePickerDialog.OnTimeSetListener(){
                        public void onTimeSet(TimePicker timePicker, int hourOfDay,int minute) {
                            Calendar c=Calendar.getInstance();//获取日期对象    
                            c.setTimeInMillis(System.currentTimeMillis());        //设置Calendar对象
                            
                            DateFormat mDateFormat = SimpleDateFormat.getTimeInstance();                            
                            String s=mDateFormat.format(c.getTime());
                            
                            c.set(Calendar.HOUR, hourOfDay);        //设置闹钟小时数
                            c.set(Calendar.MINUTE, minute);            //设置闹钟的分钟数
                            c.set(Calendar.SECOND, 0);                //设置闹钟的秒数
                            c.set(Calendar.MILLISECOND, 0);            //设置闹钟的毫秒数
                            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);    //创建Intent对象                            
                            intent.setAction("com.alarm.action_alarm_on");
                            PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);    //创建PendingIntent
                            
                            //Intent intent2 = new Intent("android.alarm.demo.action");
                            //PendingIntent pi2 = PendingIntent.getBroadcast(
                            //		MainActivity.this, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
                            
                            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);        //设置闹钟
                            //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);        //设置闹钟，当前时间就唤醒
                            
                            //时间设置错误，未修改
                            Toast.makeText(MainActivity.this, "闹钟设置成功:"+s+" "+mDateFormat.format(c.getTime()), Toast.LENGTH_LONG).show();//提示用户
                        }
                    }, 
                    cal.get(Calendar.HOUR_OF_DAY), 
                    cal.get(Calendar.MINUTE),
                    false);
            
            break;
        }
        return dialog;
    }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
