package com.example.floatwindow;

import java.lang.reflect.Field;

import com.example.floatwindow.R;
import com.example.floatwindow.MyAnimations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FloatWindowSmallView extends LinearLayout {
	
	private boolean areButtonsShowing;
	private RelativeLayout composerButtonsWrapper;
	private ImageView composerButtonsShowHideButtonIcon;
	private RelativeLayout composerButtonsShowHideButton;
	
	ImageButton btn_skin;
	SharedPreferences sp_skin;

	/**
	 * 记录悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录悬浮窗的高度
	 */
	public static int viewHeight;

	/**
	 * 记录系统状态栏的高度
	 */
	 private static int statusBarHeight;

	/**
	 * 用于更新悬浮窗的位置
	 */
	private WindowManager windowManager;

	/**
	 *悬浮窗的参数
	 */
	private WindowManager.LayoutParams mParams;

	/**
	 * 记录当前手指位置在屏幕上的横坐标值
	 */
	private float xInScreen;

	/**
	 * 记录当前手指位置在屏幕上的纵坐标值
	 */
	private float yInScreen;

	/**
	 * 记录手指按下时在屏幕上的横坐标的值
	 */
	private float xDownInScreen;

	/**
	 * 记录手指按下时在屏幕上的纵坐标的值
	 */
	private float yDownInScreen;

	/**
	 * 记录手指按下时在悬浮窗的View上的横坐标的值
	 */
	private float xInView;

	/**
	 * 记录手指按下时在悬浮窗的View上的纵坐标的值
	 */
	private float yInView;

	public FloatWindowSmallView(Context context) {
		super(context);
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater.from(context).inflate(R.layout.float_window, this);
		View view = findViewById(R.id.relate);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
			xInView = event.getX();
			yInView = event.getY();
			xDownInScreen = event.getRawX();
			yDownInScreen = event.getRawY() - getStatusBarHeight();
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			break;
		case MotionEvent.ACTION_MOVE:
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			// 手指移动的时候更新悬浮窗的位置
			updateViewPosition();
			break;
		case MotionEvent.ACTION_UP:
			// 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
			if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
				//openBigWindow();
				
				initPath();
			}
			break;
		default:
			break;
		}
		return true;
	}
	
	/**
	 * 显示其他按钮
	 * 
	 * 
	 */
	public void initPath() {
		MyAnimations.initOffset(FloatWindowSmallView.this);
		//btn_skin = (ImageButton) findViewById(R.id.composer_button_sleep);
		//sp_skin = getSharedPreferences("skin", MODE_PRIVATE);
		//btn_skin.setBackgroundResource(sp_skin.getBoolean("id", true)?R.drawable.composer_sleep:R.drawable.composer_sun);
		composerButtonsWrapper = (RelativeLayout) findViewById(R.id.composer_buttons_wrapper);
		composerButtonsShowHideButton = (RelativeLayout) findViewById(R.id.composer_buttons_show_hide_button);
		composerButtonsShowHideButtonIcon = (ImageView) findViewById(R.id.composer_buttons_show_hide_button_icon);
		//
		composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!areButtonsShowing) {
					composerButtonsShowHideButtonIcon
					.startAnimation(MyAnimations.getRotateAnimation(0, -270,300));
					MyAnimations.startAnimationsIn(composerButtonsWrapper, 300);
				} else {
					composerButtonsShowHideButtonIcon
					.startAnimation(MyAnimations.getRotateAnimation(-270,0, 300));
					MyAnimations.startAnimationsOut(composerButtonsWrapper, 300);
				}
				areButtonsShowing = !areButtonsShowing;
			}
		});
		
		//响应各个图标的点击事件
		for (int i = 0; i < composerButtonsWrapper.getChildCount(); i++) {
			final int position=i;
			composerButtonsWrapper.getChildAt(i).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					switch(position){
					case 0:
						Log.i("0", "------0-----");
						break;
					case 1:
						Log.i("1", "------1-----");
						break;
					case 2:
						Log.i("2", "------2----");
						break;
					case 3:
						Log.i("3", "------3----");
						break;
					case 4:
						Log.i("4", "------4-----");
						break;
					case 5:
						Log.i("5", "------5-----");
						break;					
					}
//					if(position==5){
//						sp_skin.edit().putBoolean("id", !sp_skin.getBoolean("id", true)).commit();
//						btn_skin.setBackgroundResource(sp_skin.getBoolean("id", true)?R.drawable.composer_sleep:R.drawable.composer_sun);
//						//Toast.makeText(MiLaucherActivity.this,!sp_skin.getBoolean("id", true)? "已开启夜间模式":"夜间模式已关闭", 3000).show();
//					}
//					else{
//						Intent intent = new Intent(FloatWindowSmallView.this, classes[position]);
//						startActivity(intent);
//						overridePendingTransition(R.anim.anim_fromright_toup6, R.anim.anim_down_toleft6);
//					}
				}
			});
		}    
}

	/**
	 * 将悬浮窗的参数传入，用于更新悬浮窗的位置。
	 * 
	 * @param params
	 *            悬浮窗的参数
	 */
	public void setParams(WindowManager.LayoutParams params) {
		mParams = params;
	}

	/**
	 * 更新悬浮窗在屏幕中的位置。
	 */
	private void updateViewPosition() {
		mParams.x = (int) (xInScreen - xInView);
		mParams.y = (int) (yInScreen - yInView);
		windowManager.updateViewLayout(this, mParams);
	}

//	/**
//	 * 打开大悬浮窗，同时关闭悬浮窗。
//	 */
//	private void openBigWindow() {
//		MyWindowManager.createBigWindow(getContext());
//		MyWindowManager.removeSmallWindow(getContext());
//	}

	/**
	 * 用于获取状态栏的高度。
	 * 
	 * @return 返回状态栏高度的像素值。
	 */
	private int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}

}
