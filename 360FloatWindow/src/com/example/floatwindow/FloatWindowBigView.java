package com.example.floatwindow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class FloatWindowBigView extends LinearLayout {

	/**
	 * ��¼������������
	 */
	public static int viewWidth;

	/**
	 * ��¼���������߶�
	 */
	public static int viewHeight;

	public FloatWindowBigView(final Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
		View view = findViewById(R.id.big_window_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		Button close = (Button) findViewById(R.id.close);
		Button back = (Button) findViewById(R.id.back);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 鐐瑰嚮鍏抽棴鎮诞绐楃殑鏃跺�欙紝绉婚櫎鎵�鏈夋偓娴獥锛屽苟鍋滄Service
				MyWindowManager.removeBigWindow(context);
				MyWindowManager.removeSmallWindow(context);
				Intent intent = new Intent(getContext(), FloatWindowService.class);
				context.stopService(intent);
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 鐐瑰嚮杩斿洖鐨勬椂鍊欙紝绉婚櫎澶ф偓娴獥锛屽垱寤哄皬鎮诞绐�?
				MyWindowManager.removeBigWindow(context);
				MyWindowManager.createSmallWindow(context);
			}
		});
	}
}
