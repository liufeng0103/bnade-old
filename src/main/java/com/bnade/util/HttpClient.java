package com.bnade.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {

	private int connectionTimeout = 10000;
	private int readTimeout = 10000;

	public String get(String url) throws MalformedURLException, IOException {
		StringBuffer sb = new StringBuffer();
		HttpURLConnection con = null;
		InputStream is = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			con.setConnectTimeout(connectionTimeout);
			con.setReadTimeout(readTimeout);
			con.connect();
			is = con.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			if (is != null) {
				is.close();
			}
			if (con != null) {
				con.disconnect();
			}
		}
		return sb.toString();
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

}
