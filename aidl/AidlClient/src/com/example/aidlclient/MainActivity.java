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
	// ʵ����ServiceConnection
		private ServiceConnection conn = new ServiceConnection() {
			@Override
			synchronized public void onServiceConnected(ComponentName name, IBinder service) {
				// ���IPerson�ӿ�
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
			// ���õ�ǰ��ͼ����
			setContentView(R.layout.activity_main);
			// ʵ����Button
			btn = (Button) findViewById(R.id.button1);
			//ΪButton��ӵ����¼�������
			
			// ʵ����Intent
			Intent intent = new Intent("com.example.aidldemo.action.MY_REMOTE_SERVICE");
			// ����Intent Action ����
			bindService(intent, conn, Service.BIND_AUTO_CREATE);
			
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					
					try{
						iPerson.setAge(20);
						iPerson.setName("�Ϲϱ�");
						String msg = iPerson.display();
						// ��ʾ�������÷���ֵ
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
