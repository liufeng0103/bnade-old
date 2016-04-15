package lf.bnade.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BnadeProperties {
	
	private static final String JDBC_FILE_PATH = "jdbc.properties";
	private static final String BNADE_FILE_PATH = "bnade.properties";

	private static Properties properties;
	private static Properties bnadeProperties;
	
	private static void load() {
		if (properties == null) {
			properties = new Properties();
			try {
				InputStream is = BnadeProperties.class.getClassLoader().getResourceAsStream(JDBC_FILE_PATH);
				if (is == null) {
					is = new FileInputStream(JDBC_FILE_PATH);
				}
				properties.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bnadeProperties == null) {
			bnadeProperties = new Properties();
			try {
				InputStream is = BnadeProperties.class.getClassLoader().getResourceAsStream(BNADE_FILE_PATH);
				if (is == null) {
					is = new FileInputStream(BNADE_FILE_PATH);
				}
				bnadeProperties.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getDriverClassName() {
		load();
		return properties.getProperty("jdbc.driverClassName");
	}
	
	public static String getUrl() {
		load();
		return properties.getProperty("jdbc.url");
	}
	
	public static String getUsername() {
		load();
		return properties.getProperty("jdbc.username");
	}
	
	public static String getPassword() {
		load();
		return properties.getProperty("jdbc.password");
	}
	
	public static int getMaxActive() {
		load();
		return Integer.valueOf(properties.getProperty("jdbc.maxActive", "8"));
	}
	
	public static String getJdbcProperty(String key) {
		load();
		return properties.getProperty(key);
	}
	
	public static long getInterval() {
		load();
		return Long.valueOf(bnadeProperties.getProperty("interval", "2100000"));
	}
	
	public static int getTask1ThreadCount() {
		load();
		return Integer.valueOf(bnadeProperties.getProperty("task1_thread_count", "8"));
	}
	
	public static int getTask2RunCountPerTime() {
		load();
		return Integer.valueOf(bnadeProperties.getProperty("task2_realm_count_per_time", "30"));
	}
	
	public static String getApiKey() {
		load();
		return bnadeProperties.getProperty("api_key");
	}
	
	public static String getAuctionHistoryDir() {
		load();
		return bnadeProperties.getProperty("auction_history_dir", "./auction_history");
	}
	
	public static String getRegion() {
		load();
		return bnadeProperties.getProperty("region","GF");
	}
	
	public static long getTask5WaitTime(){
		load();
		return Long.valueOf(bnadeProperties.getProperty("task_5_waittime", "300000"));
	}
	
}
