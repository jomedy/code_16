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
    final int DIALOG_TIME = 0;    //���öԻ���id
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
                showDialog(DIALOG_TIME);//��ʾʱ��ѡ��Ի���
            	//onCreateDialog(DIALOG_TIME).show();
                
            }
        });
        
        btn_stop=(Button)findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View view) {
        		Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);    //����Intent����                            
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
                            Calendar c=Calendar.getInstance();//��ȡ���ڶ���    
                            c.setTimeInMillis(System.currentTimeMillis());        //����Calendar����
                            
                            DateFormat mDateFormat = SimpleDateFormat.getTimeInstance();                            
                            String s=mDateFormat.format(c.getTime());
                            
                            c.set(Calendar.HOUR, hourOfDay);        //��������Сʱ��
                            c.set(Calendar.MINUTE, minute);            //�������ӵķ�����
                            c.set(Calendar.SECOND, 0);                //�������ӵ�����
                            c.set(Calendar.MILLISECOND, 0);            //�������ӵĺ�����
                            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);    //����Intent����                            
                            intent.setAction("com.alarm.action_alarm_on");
                            PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);    //����PendingIntent
                            
                            //Intent intent2 = new Intent("android.alarm.demo.action");
                            //PendingIntent pi2 = PendingIntent.getBroadcast(
                            //		MainActivity.this, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
                            
                            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);        //��������
                            //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);        //�������ӣ���ǰʱ��ͻ���
                            
                            //ʱ�����ô���δ�޸�
                            Toast.makeText(MainActivity.this, "�������óɹ�:"+s+" "+mDateFormat.format(c.getTime()), Toast.LENGTH_LONG).show();//��ʾ�û�
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
