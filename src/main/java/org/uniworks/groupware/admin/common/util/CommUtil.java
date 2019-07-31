/**
 * 박충완이 작성한 소스입니다.
 * 이 소스는 모든 개발자들이 자유롭게 수정/배포할 수 있습니다.
 * 단, 이소스를 근간으로 애플리케이션을 개발하실 때에는 꼭 출처를 명시해 주세요.
 */
package org.uniworks.groupware.admin.common.util;

import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.codec.Base64;

/**
 * @author chungwan
 *
 */
public class CommUtil {
    /**
     * <pre>
     * 메세지의 Console창 출력을 제어합니다
     * false로 설정하면 Console 출력을 하지 않습니다
     * </pre>
     */
    public static final boolean isPrintable = true;

    /**
     * Sequence No. 생성
     * 년월일 + 밀리세컨드 + 랜덤(1자리)한 숫자로 조합해서 만듬.
     * @return
     */
    public static String createSequenceNo() {
    	long current = System.currentTimeMillis();
    	Random random = new Random();
    	int num = random.nextInt(10);
    	String seqNo = CommUtil.getCurrentDate() + current + num;
    	
    	return seqNo;
    }
    
    /**
     * Sequence No. 생성
     * 년월일 + 밀리세컨드 + flag +  정보로 만듬.
     * flag : F - 파일, A - 결재, B - 게시판, C - 회의록
     * @return
     */
    public static String createSequenceNo(String flag) {
    	long current = System.currentTimeMillis();
    	Random random = new Random();
    	int num = random.nextInt(10);
    	String seqNo = CommUtil.getCurrentDate() + current + flag + num;
    	
    	return seqNo;
    }
    
    /**
     * 현재시간을 리턴합니다
     * @return
     */
    public static String getCurrentTime() {
        long current = System.currentTimeMillis();
        java.sql.Timestamp stamp = new java.sql.Timestamp(current);
        return stamp.toString();
    }

    /**
     * <pre>
     * 특정 스트링을 받아서 dot 을 기준으로 prefix를 가져오는 메소드
     * request 객체에 담겨져 있는 action값에서 추출하고자 함.
     * </pre>
     */
    public static String getPrefix(String str) {
        if (str.indexOf(".") == -1) {
            return str.toLowerCase();
        }
        return (str.substring(0, str.indexOf("."))).toLowerCase();
    }

    /**
     * <pre>
     * 입력받은 문자열에 대해 공백문자열을 제거한 후 리턴시켜줍니다<br>
     * </pre>
     * @param str dot를 포함하는 문자열
     * @return
     */
    public static String getWithOutBlank(String str) {

        String value = "";

        if (str == null || str.equals("")) {
            return "";
        }

        char[] c = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            //Character cc = new Character(c[i]);
            if (!Character.isWhitespace(c[i])) {
                value += c[i] + "";
            }
        }
        return value;
    }

    /**
     * <pre>
     * 스트링을 받아서  스페이스를 기준으로 분리를 시킨후 해당 index 번째의 값을 리턴
     * </pre>
     * @param str 컴마로 구분되는 스트링 ex) "aaa,bbb,ccc"
     * @param index return 시킬 index 번호
     */
    public static String getSeparator(String str, int index) {
        if (str.length() == 0) {
            return "";
        }
        String value = "";
        StringTokenizer token = new StringTokenizer(str, ",");
        int loop = 1;
        while (token.hasMoreTokens()) {
            if (index == loop) {
                return token.nextToken();
            }
            token.nextToken();
            loop++;
        }
        return value;
    }

    public static String getSeparatorWithPipe(String str, int index) {
        if (str.length() == 0) {
            return "";
        }

        String value = "";
        StringTokenizer token = new StringTokenizer(str, "|");
        int loop = 1;
        while (token.hasMoreTokens()) {
            if (index == loop) {
                return token.nextToken();
            }
            token.nextToken();
            loop++;
        }
        return value;
    }

    public static String getSeparatorStar(String str, int index) {
        if (str.length() == 0) {
            return "";
        }

        String value = "";
        StringTokenizer token = new StringTokenizer(str, "*");
        int loop = 1;
        while (token.hasMoreTokens()) {
            if (index == loop) {
                return token.nextToken();
            }
            token.nextToken();
            loop++;
        }
        return value;
    }

    /**
     * <pre>
     * Hashtable에 저장되어 있는 데이터를 출력합니다
     * </pre>
     * @param hashTable 출력하고자 하는 Hashtable객체
     */
    public void mapPrint(Hashtable hashTable) {
        Enumeration enumeration = hashTable.keys();
        while (enumeration.hasMoreElements()) {
            //String key = (String) enumeration.nextElement();
            //String value = (String) hashTable.get(key);
        }
    }

    /**
     * <pre>
     * Context 객체를 가져오는 메소드
     * </pre>
     **/
    public static Context getContext() throws Exception {
        Context ctx = null;
        try {
            Properties prop = new Properties();
            prop.put(Context.INITIAL_CONTEXT_FACTORY,
                     "weblogic.jndi.WLInitialContextFactory");
            prop.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
            ctx = new InitialContext(prop);
        }
        catch (Exception e) {
            throw new Exception("CommUtil - getContext:" + e.toString());
        }
        return ctx;
    }

     /**
     * <pre>
     * 주어진 double값을 가지고 해당 위치에 대한 반올림을 실행합니다
     * </pre>
     */
    public static double roundNumber(double dNum, int iPos) {
        double d1 = Math.pow(10D, iPos);
        return ( (double) Math.round(dNum * d1) / d1);
    }

    /**
     * <pre>
     * 3자리마다 콤마를 삽입합니다
     * </pre>
     */
    public static String format(double dNum) {
        NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
        return nf.format(dNum);
    }

    /**
     * 현재날짜를 8자리로 가져옵니다
     */
    public static String getCurrentDate() {
        long current = System.currentTimeMillis();
        java.sql.Date tempDate = new java.sql.Date(current);
        StringBuffer sb = new StringBuffer(tempDate.toString());
        sb.deleteCharAt(4);
        sb.deleteCharAt(6);
        return sb.toString();
    }

    /**
     * <pre>
     * 36.916600000000000 형태의 값을 가지고 도 분 초로 나타냄
     * </pre>
     */
    public static String getDegree(String src) {
        String rStr = src;
        try {
            java.text.DecimalFormat fmt = new java.text.DecimalFormat("0.##");
            int pos = src.indexOf(".");
            String degree = src.substring(0, pos);
            String tmpStr = src.substring(pos + 1);
            String minute = "" + (fmt.format(Double.parseDouble(tmpStr) * 60));
            minute = minute.substring(0, 2);
            String second = "" + (fmt.format(Double.parseDouble(tmpStr) * 3600));
            second = second.substring(0, 2);
            rStr = degree + " &deg; " + minute + " &#8217; " + second;
        }
        catch (Exception e) {
        }
        return rStr;
    }

    /**
     * <pre>
     * 주어진 객체가 null 값인지 체크해서 null 이면 빈스트링을 리턴시킵니다
     * </pre>
     */
    public static String isNullCheck(Object obj) {
        if (obj == null) {
            return "";
        }
        else {
            return (String) obj;
        }
    }

    /**
     *
     * 입력한 값이 null 또는 null String 일 경우 true를 return 한다.
     *
     * <pre>
     *
     * [사용 예제]
     *
     * ValidationUtil.isEmpty("")		===> true
     * ValidationUtil.isEmpty(null)	===> true
     * ValidationUtil.isEmpty("1")		===> false
     *
     * </pre>
     *
     * @param value
     * @return boolean
     * @roseuid 40394A4B0251
     */
    public static boolean isEmpty(String value)
    {
        if (value == null || "".equals(value.trim()))
            return true;
        return false;
    }

    /**
     *	데이터 포멧을 1,000,000 처럼 3자리 마다 , 추가
     *	총 4개의 메소드로 오버로딩 되어있슴
     */
    public static String NumberFormat(Object obj) {
        String str = (String) obj;
        if (str == null || str.equals(""))return "0";
        StringBuffer sf = new StringBuffer(str);
        int strLen = str.length();
        int dotCount = (strLen - 1) / 3;
        for (int i = 1; i <= dotCount; i++) {
            sf.insert( (strLen - 3), ',');
            strLen -= 3;
        }
        return sf.toString();
    }

    public static String NumberFormat(String str) {
        if (str == null || str.equals("null") || str.equals(""))return "0";
        if (str.indexOf(".") != -1) {
            return NumberFormatLarge(str);
        }
        StringBuffer sf = new StringBuffer(str);
        int strLen = str.length();
        int dotCount = (strLen - 1) / 3;
        for (int i = 1; i <= dotCount; i++) {
            sf.insert( (strLen - 3), ',');
            strLen -= 3;
        }
        return sf.toString();
    }

    public static String NumberFormat(int intStr) {
        String str = Integer.toString(intStr);
        StringBuffer sf = new StringBuffer(str);
        int strLen = str.length();
        int dotCount = (strLen - 1) / 3;
        for (int i = 1; i <= dotCount; i++) {
            sf.insert( (strLen - 3), ',');
            strLen -= 3;
        }
        return sf.toString();
    }

    public static String NumberFormatLarge(String str) {
        if (str == null || str.equals("null") || str.equals(""))return "0";
        StringBuffer sf = new StringBuffer(str);
        int strLen = str.lastIndexOf(".");
        int dotCount = (strLen - 1) / 3;
        for (int i = 1; i <= dotCount; i++) {
            sf.insert( (strLen - 3), ',');
            strLen -= 3;
        }
        return sf.toString();
    }

    public static String NumberFormat(double doubleStr) {
        String str = Double.toString(doubleStr);
        StringBuffer sf = new StringBuffer(str);
        int strLen = str.lastIndexOf(".");
        int dotCount = (strLen - 1) / 3;
        for (int i = 1; i <= dotCount; i++) {
            sf.insert( (strLen - 3), ',');
            strLen -= 3;
        }
        return sf.toString();
    }

    /**
     * 주어진 스트링과 주어진 구분자를 기준으로 토큰 개수를 리턴
     */
    public static int getTokenSize(String str, String spStr) {
        if (str == null)return 0;
        StringTokenizer token = new StringTokenizer(str, spStr);
        return token.countTokens();
    }

    /**
     * <pre>
     * 현재 시간을 리턴 (14자리)<br>
     * 19990102123112 1999년 01월 02일 12시 31분 12초
     * </pre>
     */
    public static String getCurrentTimeSecond() {
        long current = System.currentTimeMillis();
        java.sql.Timestamp stamp = new java.sql.Timestamp(current);
        String time = stamp.toString();
        StringBuffer sb = new StringBuffer();
        char[] c = time.substring(0, time.lastIndexOf(".")).toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (c[i] == ':' || c[i] == ' ' || c[i] == '-')continue;
            sb.append(c[i]);
        }
        return sb.toString();
    }

    /**
     * get the parameter values from request..
     * @param  : request, parameter name, parameter's initial value.
     * @return : parameter value.
     */
    public static String getParam(HttpServletRequest req, String paramName,
                                  String initValue) {
        String rep;
        try {
            rep = req.getParameter(paramName).trim();
        }
        catch (Exception npe) {
            rep = initValue;
        }

        return rep;
    }

    /**
     * <pre>
     * 주어진 스트링에서 특정 부분을 replace  (14자리)<br>
     * </pre>
     */
    public static String strReplace(String str, String pattern, String replace) {
        StringBuffer result = new StringBuffer();
        try {
            int s = 0;
            int e = 0;

            while ( (e = str.indexOf(pattern, s)) >= 0) {
                result.append(str.substring(s, e));
                result.append(replace);
                s = e + pattern.length();
            }
            result.append(str.substring(s));
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result.toString();
    }

    /**
    *
    * 입력된 스트링(s)을 HTML 형태로 변환한다.
    *
    * <pre>
    *
    * [사용 예제]
    *
    * java2Html("\r\n \r\n")
    * ===> <br>
    *           <br>
    *
    * </pre>
    *
    * @param s
    * @return java.lang.String
    */
   public static String java2Html(String s) {
       if (s == null)
           return "";

       StringBuffer buf = new StringBuffer();
       char[] c = s.toCharArray();
       int len = c.length;
       for (int i = 0; i < len; i++) {
           if (c[i] == '&')
               buf.append("&amp;");
           else if (c[i] == '<')
               buf.append("&lt;");
           else if (c[i] == '>')
               buf.append("&gt;");
           else if (c[i] == '"')
               buf.append("&quot;");
           else if (c[i] == '\'')
               buf.append("&#039;");
           else if (c[i] == '\n')
               buf.append("<br>");
           else
               buf.append(c[i]);
       }
       return buf.toString();
   }

   /**
    *
    * 입력된 스트링(s)에서 carriage return 과 new line을 제거한 스트링을 반환한다.
    *
    * @param s
    * @return java.lang.String
    */
   public static String removeCRLF(String str) {
       return StringUtil.stringRemove(str, "\r\n\"");
   }

   /**
    *
    * 입력된 스트링(s)에서 carriage return 과 new line을 제거한 스트링을 반환한다.
    *
    * @param s
    * @return java.lang.String
    */
   public static String convertGridData(String str) {
       String tmpString = "";
       tmpString = str.replaceAll("\n" , "gridEnter");
       tmpString = tmpString.replaceAll("\r" , "");
       return tmpString;
   }    
   
   /**
    * Base64로 encode
    * @param str
    * @return
    */
   public static byte[] encodeBase64(String str) {
	  if (str == null) return null;
	  byte[] encode = Base64.encode(str.getBytes());
	  return encode;
   }   
   
   /**
    * Base64로 encode해서 String으로 반환
    * @param str
    * @return
    */
   public static String encodeBase64String(String str) {
	  if (str == null) return null;
	  byte[] encode = Base64.encode(str.getBytes());
	  return new String(encode);
   }   
   /**
    * Base64로 decode
    * @param str
    * @return
    */
   public static byte[] decodeBase64(String str) {
	  if (str == null) return null;
	  byte[] decode = Base64.decode(str.getBytes());
	  return decode;
   }
   
   /**
    * Base64로 decode해서 String으로 반환
    * @param str
    * @return
    */
   public static String decodeBase64String(String str) {
	  if (str == null) return null;
	  byte[] decode = Base64.decode(str.getBytes());
	  return new String(decode);
   }
}
