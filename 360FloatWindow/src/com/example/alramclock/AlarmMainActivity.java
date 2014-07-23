package com.example.alramclock;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.example.floatwindow.R;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmMainActivity extends Activity {

	private Button btn = null;
	private Button btn_stop = null;
	private Button btn_alarm = null;
	private TextView tv = null;
	private AlarmManager alarmManager = null;
	Calendar cal = Calendar.getInstance();
	final int DIALOG_TIME = 0; // 设置对话框id
	boolean hasAlarm = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_main);
		tv = (TextView) this.findViewById(R.id.tv);

		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View view) {
				showDialog(DIALOG_TIME);// 显示时间选择对话框
				// onCreateDialog(DIALOG_TIME).show();

			}
		});

		btn_stop = (Button) findViewById(R.id.btn_stop);
		btn_stop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(AlarmMainActivity.this,
						AlarmReceiver.class); // 创建Intent对象
				intent.setAction("com.alarm.action_alarm_on");
				PendingIntent pi = PendingIntent.getBroadcast(
						AlarmMainActivity.this, 0, intent, 0);
				alarmManager.cancel(pi);
				Toast.makeText(AlarmMainActivity.this, "Alarm Stop",
						Toast.LENGTH_SHORT).show();

			}
		});

		btn_alarm = (Button) findViewById(R.id.btn_alarm);
		btn_alarm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AlarmMainActivity.this,
						SetAlarmActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_TIME:
			dialog = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {
						public void onTimeSet(TimePicker timePicker,
								int hourOfDay, int minute) {
							Calendar c = Calendar.getInstance();// 获取日期对象
							c.setTimeInMillis(System.currentTimeMillis()); // 设置Calendar对象

							DateFormat mDateFormat = SimpleDateFormat
									.getTimeInstance();
							String s = mDateFormat.format(c.getTime());

							c.set(Calendar.HOUR, hourOfDay); // 设置闹钟小时数
							c.set(Calendar.MINUTE, minute); // 设置闹钟的分钟数
							c.set(Calendar.SECOND, 0); // 设置闹钟的秒数
							c.set(Calendar.MILLISECOND, 0); // 设置闹钟的毫秒数
							Intent intent = new Intent(AlarmMainActivity.this,
									AlarmReceiver.class); // 创建Intent对象
							intent.setAction("com.alarm.action_alarm_on");
							PendingIntent pi = PendingIntent.getBroadcast(
									AlarmMainActivity.this, 0, intent, 0); // 创建PendingIntent

							// Intent intent2 = new
							// Intent("android.alarm.demo.action");
							// PendingIntent pi2 = PendingIntent.getBroadcast(
							// MainActivity.this, 0, intent2,
							// PendingIntent.FLAG_CANCEL_CURRENT);

							alarmManager.set(AlarmManager.RTC_WAKEUP,
									c.getTimeInMillis(), pi); // 设置闹钟
							// alarmManager.set(AlarmManager.RTC_WAKEUP,
							// System.currentTimeMillis(), pi); //设置闹钟，当前时间就唤醒

							// 时间设置错误，未修改
							tv.setText("闹钟设置成功:" + s + " "
									+ mDateFormat.format(c.getTime()));
							Toast.makeText(
									AlarmMainActivity.this,
									"闹钟设置成功:" + s + " "
											+ mDateFormat.format(c.getTime()),
									Toast.LENGTH_LONG).show();// 提示用户
						}
					}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
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
