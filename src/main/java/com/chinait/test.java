package com.chinait;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class test {
	public static void main(String[] args) {
		for(int i=0;i<20;i++){
			HttpPost post = new HttpPost();
			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				post.setURI(new URI("http://test.m.zhisland.com/wz/account/mobile/check"));
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
		        nvps.add(new BasicNameValuePair("dcc", "0086"));  
		        nvps.add(new BasicNameValuePair("mobile", "18583623392")); 
		        post.setEntity(new UrlEncodedFormEntity(nvps));
				CloseableHttpResponse  closeableHttpResponse  = httpclient.execute(post);
				HttpEntity entity2 = closeableHttpResponse.getEntity();
				System.out.println(entity2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
