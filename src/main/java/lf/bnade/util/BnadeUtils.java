package lf.bnade.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class BnadeUtils {

	private static final int CONNECTION_TIMEOUT = 10000;
	private static final int READ_TIMEOUT = 10000;
	
	private static Scanner scan;

	public static String urlToString(String url) throws Exception {	
		scan = new Scanner(new URL(url).openStream());
		StringBuffer sb = new StringBuffer();
		while (scan.hasNextLine()) {
			sb.append(scan.nextLine().trim());
		}
		return sb.toString();
	}
	
	public static String urlToString2(String url) throws Exception {
		
		trustAllHttpsCertificates();
		HostnameVerifier hv = new HostnameVerifier() {  
	        public boolean verify(String urlHostName, SSLSession session) {  
	            System.out.println("Warning: URL Host: " + urlHostName + " vs. "  
	                               + session.getPeerHost());  
	            return true;  
	        }  
	    };  
	    HttpsURLConnection.setDefaultHostnameVerifier(hv);
		StringBuffer sb = new StringBuffer();
		HttpURLConnection con = null;
		InputStream is = null;
		try {
			con = (HttpURLConnection)new URL(url).openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
			con.setConnectTimeout(CONNECTION_TIMEOUT);
			con.setReadTimeout(READ_TIMEOUT);
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
	
	public static List<String> fileToStringList(String filePath) {	
		List<String> list = new ArrayList<String>();		
		try {
			scan = new Scanner(new FileInputStream(filePath));
			while (scan.hasNextLine()) {
				list.add(scan.nextLine().trim());
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}		
		return list;
	}
	
	public static String fileToString(String filePath) {	
		StringBuffer sb = new StringBuffer();		
		try {
			scan = new Scanner(new FileInputStream(filePath));
			while (scan.hasNextLine()) {
				sb.append(scan.nextLine());
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}		
		return sb.toString();
	}
	
	public static void stringToFile(String s, String filePath) throws Exception {
		PrintWriter out = new PrintWriter(new FileOutputStream(filePath, true));
		out.println(s);
		out.close();
	}
	
	private static void trustAllHttpsCertificates() throws Exception {  
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];  
        javax.net.ssl.TrustManager tm = new miTM();  
        trustAllCerts[0] = tm;  
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext  
                .getInstance("SSL");  
        sc.init(null, trustAllCerts, null);  
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc  
                .getSocketFactory());  
    }  
  
    static class miTM implements javax.net.ssl.TrustManager,  
            javax.net.ssl.X509TrustManager {  
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
            return null;  
        }  
  
        public boolean isServerTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }  
  
        public boolean isClientTrusted(  
                java.security.cert.X509Certificate[] certs) {  
            return true;  
        }  
  
        public void checkServerTrusted(  
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }  
  
        public void checkClientTrusted(  
                java.security.cert.X509Certificate[] certs, String authType)  
                throws java.security.cert.CertificateException {  
            return;  
        }  
    }  
}
