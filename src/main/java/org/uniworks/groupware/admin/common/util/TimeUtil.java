/**
 *
 */
package org.uniworks.groupware.admin.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author linuxwan
 *
 */
public class TimeUtil {
	public int HOUR;
	public int MINUTE;
	public int SECOND;
	public int MILLISECOND;

	/**
	 * 생성자
	 */
	public TimeUtil() {
	    HOUR = 12;
	    MINUTE = SECOND = MILLISECOND = 0;
	}

	/**
	 * 생성자
	 * @param int hour : 시간
	 * @param int min : 분
	 * @param int sec : 초
	 */
	public TimeUtil(int hour, int min, int sec) {
		HOUR = hour;
		MINUTE = min;
		SECOND = sec;
		MILLISECOND = 0;
	}
	
	/**
	 * 생성자
	 * @param hour : 시간
	 * @param min : 분
	 * @param sec : 초
	 * @param millisecond : 밀리세컨드
	 */
	public TimeUtil(int hour, int min, int sec, int millisecond) {
		HOUR = hour;
		MINUTE = min;
		SECOND = sec;
		MILLISECOND = millisecond;
	}

	/**
	 * 생성자
	 * @param java.util.Date
	 */
	public TimeUtil(Date dt) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dt);
		HOUR = calendar.get(Calendar.HOUR);
		MINUTE = calendar.get(Calendar.MINUTE);
		SECOND = calendar.get(Calendar.SECOND);
		MILLISECOND = calendar.get(Calendar.MILLISECOND);
	}

	/**
	 * 입력한 문자열을 DateUtil에 설정한다.
	 * @param java.lang.String s : 시간은 반드시 'HHMMSS'로 입력한다.
	 */
	public void parseTime(String s) {
		try {
			String hour = s.substring(0, 2);
			String min = s.substring(2, 4);
			String sec = s.substring(4, 6);
			HOUR = Integer.parseInt(hour);
			MINUTE = Integer.parseInt(min);
			SECOND = Integer.parseInt(sec);
			MILLISECOND = 0;
	    } catch(NumberFormatException numberformatexception) {
	    	HOUR = MINUTE = SECOND = MILLISECOND = 0;
	    } catch(IndexOutOfBoundsException IndexOutOfBoundsException) {
	    	HOUR = MINUTE = SECOND = MILLISECOND = 0;
	    }
	}

	/**
	 * 설정된 시간을 문자열로 가져온다.
	 * @return java.lang.String : 'HHMMSS'형태로 return
	 */
	public String getString() {
		String time = (HOUR >= 10) ? "" : "0"; 
	    int Time = HOUR * 10000 + MINUTE * 100 + SECOND;
	    time = time + String.valueOf(Time);
	    return time;
	}
	
	/**
	 * 설정된 시간을 문자열로 가져온다.
	 * @return java.lang.String : "HHMMSS MLS' 형태로 return
	 */
	public String getStringMillisecond() {
		String time = getString();
		time += " " + String.valueOf(MILLISECOND);
		return time;
	}

	/**
	 * 설정된 시간을 특정기호에 포맷을 맞춰서 문자열로 가져온다.
	 * @return java.lang.String : 예) HH:MM:SS, HH-MM-SS
	 * @param java.lang.String indc : 예) ":", "-" 등
	 */
	public String getStringTimeFormat(String indc) {
	  	String vTime = getString();

	    String ret = null;
	    if (vTime != null && vTime.length () == 6) {
	    	ret = vTime.substring (0, 2) + indc;
	    	ret += vTime.substring (2, 4) + indc;
	    	ret += vTime.substring (4, 6);
	    } else if (vTime != null && vTime.length() == 4) {
	    	ret = vTime.substring(0,2) + indc;
	    	ret += vTime.substring(2,4);
	    }

	    return ret;
	}

	/**
	 * 서버의 현재시간을 셋팅한다.
	 */
	public void setCurrentTime() {
	    Calendar calendar = Calendar.getInstance();
		HOUR = calendar.get(Calendar.HOUR);
		MINUTE = calendar.get(Calendar.MINUTE);
		SECOND = calendar.get(Calendar.SECOND);
	}

	/**
	 * 입력되는 시간을 특정기호와 함께 가져온다.
	 * @return java.lang.String
	 * @param java.lang.String indc : ":", "-" 등
	 */
	public static String getTimeFormat(String vTime, String indc) {
	    String ret = null;
	    if (indc.trim().equals("")) indc = ":";

	    if (vTime != null && vTime.length () == 6) {
	    	ret = vTime.substring (0, 2) + indc;
	    	ret += vTime.substring (2, 4) + indc;
	    	ret += vTime.substring (4, 6);
	    } else if (vTime != null && vTime.length() == 4) {
	    	ret = vTime.substring(0,2) + indc;
	    	ret += vTime.substring(2,4);
	    }

	    return ret;
	}

	/**
	 * 입력되는 시간을 특정기호와 함께 가져온다.
	 * @return java.lang.String
	 * @param char indc : ':', '-' 등
	 */
	public static String getTimeString(String tm, char indc) {
		char ch;
	  	String str = "";

	  	for (int i = 0; i < tm.length(); i++) {
	  		ch = tm.charAt(i);
	  		if (ch != indc) {
	  			str += String.valueOf(ch);
	  		}
	  	}

	  	return str;
	}
}
