package com.example.floatwindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

public class FloatWindowService extends Service {

	private static final int TYPE_GAME = 0;

    private static final int TYPE_UNKNOWN = 0;

    /**
	 * 用于在线程中创建或移除悬浮窗。
	 */
	private Handler handler = new Handler() {
	    @Override
        public void handleMessage(Message msg) {
	        switch (msg.what) {

	        }
	    }
	};

	/**
	 * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
	 */
	private Timer timer;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 开启定时器，每隔0.5秒刷新一次
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Service被终止的同时也停止定时器继续运行
		timer.cancel();
		timer = null;
	}

	class RefreshTask extends TimerTask {

		@Override
		public void run() {
			// 当前界面是应用，且没有悬浮窗显示，则创建悬浮窗。
			if (isApp() && !MyWindowManager.isWindowShowing()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						MyWindowManager.createSmallWindow(getApplicationContext());
					}
				});
			}
			// 当前是桌面，且有悬浮窗显示，则移除悬浮窗。
			else if (!isApp() && MyWindowManager.isWindowShowing()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						MyWindowManager.removeSmallWindow(getApplicationContext());
					}
				});
			}
		}
	}
	
	/**
     *判断当前界面程序，是否为应用
     */
    public boolean isApp() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        ComponentName topActivity = tasks.get(0).topActivity;
        ComponentName cn = am.getRunningTasks(2).get(0).topActivity;
        //Log.i("-----name-------", cn.getPackageName());
        
        // TODO: 接入助手接口
        int type = TYPE_GAME;

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sp.getInt(topActivity.getPackageName(), TYPE_UNKNOWN);
        Editor editor = sp.edit();
        
        AsyncTask<Void, Integer, Void> task = new AsyncTask<Void, Integer, Void>() {
            
            @Override
            protected void onProgressUpdate(Integer... progress) {

            }

            @Override
            protected Void doInBackground(Void... pearams) {
                // TODO Auto-generated method stub
                publishProgress(null);
                return null;
            }
            
            @Override
            protected void onPostExecute(Void result) {
                handler.sendEmptyMessage(TRIM_MEMORY_BACKGROUND);
            }
        };
        

        editor.putInt(topActivity.getPackageName(), type);
        return topActivity.getPackageName().equals("sh.lilith.dgame.s37wan")||
        		topActivity.getPackageName().equals("com.sina.weibo");
    }

	/**
	 * 获得属于桌面的应用的应用包名称
	 * 
	 * @return 返回包含所有包名的字符串列表
	 */
	private List<String> getHomes() {
		List<String> names = new ArrayList<String>();
		PackageManager packageManager = this.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo ri : resolveInfo) {
			names.add(ri.activityInfo.packageName);
		}
		return names;
	}
}
