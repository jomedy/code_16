package com.example.aidldemo;

import com.example.aidldemo.IPerson.Stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyRemoteService extends Service {
	private Stub iPerson=new IPersonImpl();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return iPerson;
	}

}
