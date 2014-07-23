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
	final int DIALOG_TIME = 0; // ���öԻ���id
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
				showDialog(DIALOG_TIME);// ��ʾʱ��ѡ��Ի���
				// onCreateDialog(DIALOG_TIME).show();

			}
		});

		btn_stop = (Button) findViewById(R.id.btn_stop);
		btn_stop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(AlarmMainActivity.this,
						AlarmReceiver.class); // ����Intent����
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
							Calendar c = Calendar.getInstance();// ��ȡ���ڶ���
							c.setTimeInMillis(System.currentTimeMillis()); // ����Calendar����

							DateFormat mDateFormat = SimpleDateFormat
									.getTimeInstance();
							String s = mDateFormat.format(c.getTime());

							c.set(Calendar.HOUR, hourOfDay); // ��������Сʱ��
							c.set(Calendar.MINUTE, minute); // �������ӵķ�����
							c.set(Calendar.SECOND, 0); // �������ӵ�����
							c.set(Calendar.MILLISECOND, 0); // �������ӵĺ�����
							Intent intent = new Intent(AlarmMainActivity.this,
									AlarmReceiver.class); // ����Intent����
							intent.setAction("com.alarm.action_alarm_on");
							PendingIntent pi = PendingIntent.getBroadcast(
									AlarmMainActivity.this, 0, intent, 0); // ����PendingIntent

							// Intent intent2 = new
							// Intent("android.alarm.demo.action");
							// PendingIntent pi2 = PendingIntent.getBroadcast(
							// MainActivity.this, 0, intent2,
							// PendingIntent.FLAG_CANCEL_CURRENT);

							alarmManager.set(AlarmManager.RTC_WAKEUP,
									c.getTimeInMillis(), pi); // ��������
							// alarmManager.set(AlarmManager.RTC_WAKEUP,
							// System.currentTimeMillis(), pi); //�������ӣ���ǰʱ��ͻ���

							// ʱ�����ô���δ�޸�
							tv.setText("�������óɹ�:" + s + " "
									+ mDateFormat.format(c.getTime()));
							Toast.makeText(
									AlarmMainActivity.this,
									"�������óɹ�:" + s + " "
											+ mDateFormat.format(c.getTime()),
									Toast.LENGTH_LONG).show();// ��ʾ�û�
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
