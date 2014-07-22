package com.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class WinFloatUtils
{
	/**
	 * ����
	 * @param url ��ַ
	 * @return
	 */
	public static String download(String url)
	{
		HttpGet httpGet = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		InputStream is = null;
		
		try
		{
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			is = entity.getContent();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String result = "";
			String line = "";
			
			while((line = br.readLine()) != null)
			{
				result = result + line;
			}
			return result;
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(is != null)
				{
					is.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * JSON�ַ���ת��Ϊ����
	 * @param str JSON�ַ���
	 * @return
	 */
	public static Object str2Obj(String str)
	{
		return null;
	}
}
