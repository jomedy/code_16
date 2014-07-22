package com.example.aidlclient;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.aidldemo.IPerson;

public class MainActivity extends Activity {
	private IPerson iPerson;
	private Button btn;
	// 实例化ServiceConnection
		private ServiceConnection conn = new ServiceConnection() {
			@Override
			synchronized public void onServiceConnected(ComponentName name, IBinder service) {
				// 获得IPerson接口
				iPerson = IPerson.Stub.asInterface(service);
				System.out.println("iperson----------:"+iPerson);
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				iPerson=null;
			}
		};

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// 设置当前视图布局
			setContentView(R.layout.activity_main);
			// 实例化Button
			btn = (Button) findViewById(R.id.button1);
			//为Button添加单击事件监听器
			
			// 实例化Intent
			Intent intent = new Intent("com.example.aidldemo.action.MY_REMOTE_SERVICE");
			// 设置Intent Action 属性
			bindService(intent, conn, Service.BIND_AUTO_CREATE);
			
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					
					try{
						iPerson.setAge(20);
						iPerson.setName("南瓜饼");
						String msg = iPerson.display();
						// 显示方法调用返回值
						Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		}
		
		@Override
		protected void onDestroy()
		{
			// TODO Auto-generated method stub
			unbindService(conn);
			super.onDestroy();
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
