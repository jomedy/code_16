package com.example.alarmclock;

import com.example.floatwindow.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;
import android.app.Service;

public class AlarmActivity extends Activity {
	private Vibrator vibrator = null;
	private MediaPlayer alarmMusic = null;
	boolean hasVibrator = false;
	boolean set_ring;
	boolean set_vibrator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras(); 
		set_ring = extras.getBoolean("set_ring");
		set_vibrator = extras.getBoolean("set_vibrator");
		
		Toast.makeText(AlarmActivity.this, "vibrator="+set_vibrator+",ring="+set_ring,
				Toast.LENGTH_SHORT).show();
/*		
		// ������
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
		hasVibrator = vibrator.hasVibrator();
		if (hasVibrator && set_vibrator) {
			vibrator.vibrate(new long[] { 1000, 2000, 1000, 2000, 1000, 2000 },
					-1);
		}

		// ��������
		if (set_ring) {
			alarmMusic = MediaPlayer.create(this, R.raw.alarm);
			alarmMusic.setLooping(true);
			alarmMusic.start();
		}
*/
		// ��ʾ�Ի���
		new AlertDialog.Builder(AlarmActivity.this).setTitle("����").// ���ñ���
				setMessage("ʱ�䵽�ˣ�\tvibrator="+set_vibrator+",ring="+set_ring).// ��������
				setPositiveButton("֪����", new OnClickListener() {// ���ð�ť
							public void onClick(DialogInterface dialog,
									int which) {
								/*
								if (hasVibrator && set_vibrator) {
									vibrator.cancel();
									set_vibrator=false;
								}
								if (set_ring)
								{
									alarmMusic.stop();
									set_ring=false;
								}
								*/
								AlarmActivity.this.finish();// �ر�Activity
							}
						}).create().show();

	}

}
