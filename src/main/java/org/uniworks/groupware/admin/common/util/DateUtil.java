/**
 *
 */
package org.uniworks.groupware.admin.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author linuxwan
 *
 */

public class DateUtil {
	public int YEAR;
	public int MONTH;
	public int DAY;
	private TimeUtil tm;

	/**
	 * 생성자
	 */
	public DateUtil() {
		Calendar calendar = Calendar.getInstance();
		tm = new TimeUtil(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
	    YEAR = calendar.get(Calendar.YEAR);
	    MONTH = calendar.get(Calendar.MONTH) + 1;
	    DAY = calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 생성자
	 * @param int year : 년도
	 * @param int month : 월
	 * @param int day : 일
	 */
	public DateUtil(int year, int month, int day) {
		set(year, month, day);
		tm = null;
	}

	/**
	 * 생성자
	 * @param java.util.Calendar
	 */
	public DateUtil(Calendar calendar) {
	    tm = new TimeUtil(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
	    YEAR = calendar.get(Calendar.YEAR);
	    MONTH = calendar.get(Calendar.MONTH) + 1;
	    DAY = calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 생성자
	 * @param java.util.Date
	 */
	public DateUtil(Date date) {
	    tm = null;
	    setDate(date);
	}
	
	/**
	 * 생성자
	 * @param dt String
	 */
	public DateUtil(String dt) {		
		parseDate(dt);
		tm = null;
	}

	/**
	 * 특정일자를 셋팅한다.
	 * @param int year : 년도
	 * @param int month : 월
	 * @param int day : 일
	 */
	public void set(int year, int month, int day) {
	    YEAR = year;
	    MONTH = month;
	    DAY = day;
	}

	/**
	 * 특정일자를 셋팅한다.
	 * @param java.util.Date
	 */
	public void setDate(Date dt) {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dt);
	    YEAR = calendar.get(Calendar.YEAR);
	    MONTH = calendar.get(Calendar.MONTH) + 1;
	    DAY = calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 입력한 날자를 셋팅한다.이때 날자는 YYYYMMDD형태로 입력되어야 한다.
	 * @param java.lang.String dt : 특정일자
	 * @return boolean : 셋팅 성공여부
	 */
	public boolean parseDate(String dt) {
	    boolean flag = false;
	    if(dt.length() == 8) {
	    	Calendar calendar = Calendar.getInstance();
	    	try {
	    		String year = dt.substring(0, 4);
	    		String month = dt.substring(4, 6);
	    		String day = dt.substring(6, 8);
	    		calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
	    		YEAR = calendar.get(Calendar.YEAR);
	    		MONTH = calendar.get(Calendar.MONTH) + 1;
	    		DAY = calendar.get(Calendar.DAY_OF_MONTH);
	    		flag = true;
	    	} catch(NumberFormatException numberformatexception) { }
	        catch(IndexOutOfBoundsException indexoutofboundsexception) { }
	    }
	    return flag;
	}

	/**
	 * 입력한 날자를 특정 pattern으로 변경한다.
	 * @param java.lang.String dt : 특정일자
	 * @param java.lang.String pattern : 표시형식(예, ".", "/", "-" 등)
	 * @return boolean : 성공여부
	 */
	public boolean parseDate(String dt, String pattern) {
	    boolean flag = false;
	    SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern, Locale.KOREA);

	    try {
	    	Date date = simpledateformat.parse(dt);
	    	if (simpledateformat.format(date).equals(dt)) {
	    		setDate(date);
	    		flag = true;
	    	}
	    }catch(ParseException parseexception) { }
	    return flag;
	}

	/**
	 * 특정일자의 주중 순서를 가져온다.
	 * @return int : 성공여부
	 */
	public int getDayofWeek() {
	    Calendar calendar = getCalendar();
	    return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Date Type의 날자를 가져온다.
	 * @return java.util.Date
	 */
	public Date getDate() {
		Calendar calendar = getCalendar();
	    return calendar.getTime();
	}

	/**
	 * Calendar Type의 날자를 가져온다.
	 * 지정된 날짜외의 시간은 0시 0분 0초로 설정한다.
	 * @return java.util.Calendar
	 */
	public Calendar getCalendar() {
	    Calendar calendar = Calendar.getInstance();
	    if (tm == null)
	    	calendar.set(YEAR, MONTH - 1, DAY, 0, 0, 0);
	    else
	    	calendar.set(YEAR, MONTH - 1, DAY, tm.HOUR, tm.MINUTE, tm.SECOND);
	    return calendar;
	}

	/**
	 * 설정된 날짜를 String으로 가져온다.(YYYYMMDD형태)
	 * @return java.lang.String
	 */
	public String getString() {
	    int i = YEAR * 10000 + MONTH * 100 + DAY;
	    return String.valueOf(i);
	}
	
	/**
	 * 설정된 일자의 년월정보를 가져온다.(YYYYMM형태)
	 * @return java.lang.String
	 */
	public String getStringYYYYMM() {
		int i = YEAR * 100 + MONTH;
		return String.valueOf(i);
	}
	
	/**
	 * 설정된 일자의 년월일시분초 정보를 문자열로 반환한다.(yyyyMMddHHmmss)
	 * @return
	 */
	public String getStringYYYYMMDDHHMMSS() {
		int d = YEAR * 10000 + MONTH * 100 + DAY;
		String t = null;
		if (tm != null) {
			t = String.valueOf(tm.HOUR) + String.valueOf(tm.MINUTE) + String.valueOf(tm.SECOND);
		} else {
			Calendar calendar = Calendar.getInstance();
			tm = new TimeUtil(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
			t = String.valueOf(tm.HOUR) + String.valueOf(tm.MINUTE) + String.valueOf(tm.SECOND);
		}
		return String.valueOf(d) + t;
	}
	
	/**
	 * 설정된 날짜의 년도를 가져온다.(YYYY)
	 * @return
	 */
	public String getYear() {
		return String.valueOf(YEAR);
	}
	
	/**
	 * 설정된 날짜의 년도를 끝에서 2자리만 가져온다.(YY)
	 * @return
	 */
	public String getYearShort() {
		return String.valueOf(YEAR).substring(2);
	}

	/**
	 * 설정된 날자를 Date Format에 맞추어 String으로 가져온다.
	 * 예, 2003.11.21
	 * @return java.lang.String
	 * @param java.lang.String indc : 구분문자(예, ".", "/", "-" 등)
	 */
	public String getStringDateFormat(String indc) {
	  	String str = getString();
	    int strLen = str.trim().length();

	    if (strLen == 8) {
	    	return str.substring(0, 4) + indc + str.substring(4,6) + indc + str.substring(6,8);
	    } else if (strLen == 6) {
	    	return str.substring(0, 4) + indc + str.substring(4,6);
	    } else {
	    	return str;
	    }
	}

	/**
	 * 입력받은 날자를 Date Format에 맞추어 String으로 가져온다.
	 * 예, 2003.11.21
	 * @return java.lang.String
	 * @param java.lang.String str : 특정일자, 반드시 YYYYMM 또는 YYYYMMDD형식으로 입력해야 함.
	 * @param java.lang.String indc : 구분문자(예, ".", "/", "-" 등)
	 */
	public static String getDateFormat(String str, String indc) {
		if (indc.trim().equals("")) indc = ".";
		int strLen = str.trim().length();

	    if (strLen == 8) {
	    	return str.substring(0, 4) + indc + str.substring(4,6) + indc + str.substring(6,8);
	    } else if (strLen == 6) {
	    	return str.substring(0, 4) + indc + str.substring(4,6);
	    } else {
	    	return str;
	    }
	}

	/**
	 * 입력받은 날자에서 특정 기호를 제거한다.
	 * @return java.lang.String
	 * @param java.lang.String dt : 날자
	 * @param char indc : 특정 기호(예, '.', '/', '-' 등)
	 */
	public static String getDateString(String dt, char indc) {
	  	char ch;
	  	String str = "";

	  	for (int i = 0; i < dt.length(); i++) {
	  		ch = dt.charAt(i);
	  		if (ch != indc) {
	  			str += String.valueOf(ch);
	  		}
	  	}

	  	return str;
	}

	/**
	 * 멤버클래스인 TimeUtil tm을 제거한다.
	 */
	public void removeTime() {
	    tm = null;
	}

	/**
	 * 현재시간을 셋팅한다.(서버의 시간으로)
	 */
	public void setCurrentTime() {
	    Calendar calendar = Calendar.getInstance();
	    tm = new TimeUtil(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
	}

	/**
	 * 멤버클래스인 TimeUtil tm에 시간을 셋팅한다.
	 * @param int hour : 시간
	 * @param int min : 분
	 * @param int sec : 초
	 */
	public void setTime(int hour, int min, int sec) {
	    if (tm == null) tm = new TimeUtil();
	    tm.HOUR = hour;
	    tm.MINUTE = min;
	    tm.SECOND = sec;
	}

	/**
	 * 멤버클래스인 TimeUtil tm을 설정한다.
	 * @param jada.common.util.TimeUtil timeUtil
	 */
	public void setTime(TimeUtil timeUtil) {
	    tm = timeUtil;
	}

	/**
	 * 멤버클래스인 TimeUtil tm을 얻어온다.
	 * @return jada.common.util.TimeUtil
	 */
	public TimeUtil getTime() {
	    return tm;
	}

	/**
	 * 설정된 날자와 입력된 날자를 비교한다.
	 * @param int : 동일한 날자일 경우 0, 현재일자가 클경우 양수, 적을경우 음수를 return
	 * @param jada.common.util.DateUtil
	 */
	public int compare(DateUtil dateUtil) {
	    int first = 0;
	    int second = 0;
	    try {
	    	first = Integer.parseInt(getString());
	    	second = Integer.parseInt(dateUtil.getString());
	    } catch(NumberFormatException numberformatexception) { }

	    return first - second;
	}
	
	/**
	 * 현재 시스템 일자를 가져온다.
	 * @return
	 */
	public static Date getCurrentDate() {		
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 특정 일자 만큼 더하거나 뺀다.
	 * @param field  년: Calendar.YEAR, 월: Calendar.MONTH, 일: Calendar.DAY_OF_MONTH
	 * @param amount 더하거나 빼고자 하는 
	 */
	public void add(int field, int amount) {
		Calendar cal = getCalendar();
		cal.add(field, amount);
		setDate(cal.getTime());
	}
	
	/**
	 * 현재일자를 Timestamp 값으로 변환.
	 * @return
	 */
	public Timestamp getCurrentDateTimeConvertToTimestamp() {
		Calendar cal;
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = getStringYYYYMMDDHHMMSS();
		try {
			sd.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal = sd.getCalendar();
		Timestamp time = new Timestamp(cal.getTime().getTime());
		return time;
	}
}