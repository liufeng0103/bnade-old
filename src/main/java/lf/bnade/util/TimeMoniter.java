package lf.bnade.util;


public class TimeMoniter {
	
	private long startTime;
	
	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public String spend() {
		long curTime = System.currentTimeMillis();
		String s = "用时" + TimeHelper.format(curTime - startTime);
		startTime = curTime;
		return s;
	}
}
