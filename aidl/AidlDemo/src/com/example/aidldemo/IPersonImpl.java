package com.example.aidldemo;

import android.os.RemoteException;

public class IPersonImpl extends IPerson.Stub{
	//声明两个变量
	private int age;
	private String name;
	@Override
	public void setAge(int age) throws RemoteException {
		this.age=age;
	}

	@Override
	public void setName(String name) throws RemoteException {
		this.name=name;
	}

	@Override
	public String display() throws RemoteException {
		return "name="+name+";age="+age;
	}

}
