/**
 * 박충완이 작성한 소스입니다.
 * 이 소스는 모든 개발자들이 자유롭게 수정/배포할 수 있습니다.
 * 단, 이소스를 근간으로 애플리케이션을 개발하실 때에는 꼭 출처를 명시해 주세요.
 */
package org.uniworks.groupware.admin.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chungwan
 *
 */
public class StringUtil {
    public StringUtil() {}

    /**
     * HttpServletRequest로 넘어온 값이 없을 경우 초기값을 세팅
     *
     * @param req       HttpServletRequest
     * @param key       넘어온 Key값
     * @param initValue 값이 없을 경우의 초기값
     * @return          치환된 문자열
     */
    public static String getParam(HttpServletRequest req, String key, String initValue) {
        String value = req.getParameter(key);
        if (value == null) {
            return initValue;
        } else {
            return value;
        }
	}

    /**
     * null 값을 "" 형태로 치환해주는 메소드
     *
     * @param param     Object 유형의 치환 대상
     * @return          치환된 문자열
     */
    public static String null2String(Object param,String strNewValue) {
        if (param == null || ((String) param).trim().length() == 0)
            return strNewValue;
        else
            return trim( ( (String) param).trim());
    }

    /**
     * null 값을 "" 형태로 치환해주는 메소드
     * 좌우측 공백도 없애준다.
     * @param param     Object 유형의 치환 대상
     * @return          치환된 문자열
     */
    public static String null2void(Object param) {
        if (param == null)
            return "";
        if ( ( (String) param).trim().equals(""))
            return "";
        else
            return trim( ( (String) param).trim());
    }

    /**
     * null값을 0으로 치환해주는 메소드
     *
     * @param param     Object 유형의 치환 대상
     * @return          치환된 숫자
     */
    public static String null2zeroString(Object param) {
        if (param == null)
            return "0";
        if ( ( (String) param).trim().equals(""))
            return "0";
        else
            return (String) param;
    }

    /**
     * null값을 0으로 치환해주는 메소드
     *
     * @param param     Object 유형의 치환 대상
     * @return          치환된 숫자
     */
    public static BigDecimal null2zeroBigDecimal(Object param) {
        if (param == null) return new BigDecimal("0");

        return (BigDecimal) param;
    }

    /**
     * null값을 0으로 치환해주는 메소드
     *
     * @param param     Object 유형의 치환 대상
     * @return          치환된 숫자
     */
    public static int null2zeroint(Object param) {
        if (param == null)
            return 0;
        if ( ( (String) param).trim().equals(""))
            return 0;
        else
            return Integer.parseInt( (String) param);
    }

    /**
     * null값을 0으로 치환해주는 메소드
     *
     * @param param     Object 유형의 치환 대상
     * @return          치환된 숫자
     */
    public static Integer null2zeroInt(Object param) {
        if (param == null)
            return Integer.getInteger("0");
        if ( ( (String) param).trim().equals(""))
            return Integer.getInteger("0");
        else
            return Integer.getInteger( (String) param);
    }

    /**
     * null값을 0으로 치환해주는 메소드
     *
     * @param param     Object 유형의 치환 대상
     * @return          치환된 숫자
     */
    public static long null2zerolong(Object param) {
        if (param == null)
            return 0;
        if ( ( (String) param).trim().equals(""))
            return 0;
        else
            return Long.parseLong( (String) param);
    }

    /**
     * null값을 0으로 치환해주는 메소드
     *
     * @param param     Object 유형의 치환 대상
     * @return          치환된 숫자
     */
    public static Long null2zeroLong(Object param) {
        if (param == null) {
            return Long.getLong("0");
        }

        if ( ( (String) param).trim().equals("")) {
            return Long.getLong("0");
        }
        else {
            return Long.getLong( (String) param);
        }
    }

    /**
     * 입력받은 문자열을 세자리마다 끊어 화폐단위표기형식으로 변환해주는 메소드
     *
     * @param str   처리대상 문자열
     * @return      화폐단위형식표기로 치환되어진 문자열
     */
    public static String getMoneyForm(String str) {
        if (str == null)
            return "";

        int offset = str.indexOf(".");
        String work_str = "";
        String nonwork_str = "";

        if (offset > 0) {
            work_str = str.substring(0, offset);
            nonwork_str = str.substring(offset, str.length());
        }
        else {
            work_str = str;
        }

        DecimalFormat df = new DecimalFormat("###,##0");
        double d = 0.0D;

        try {
            d = Double.valueOf(work_str).doubleValue();
        }
        catch (Exception e) {
            d = 0.0D;
        }
        return df.format(d) + nonwork_str;
    }

    /**
     * 소숫점이 들어간 str을 받아서 소숫점 아래 자리를 cnt수로 맞춰주는 메소드
     *
     * @param str       소숫점을 포함한 치환대상
     * @param cnt       치환이 될 소숫점 아래 자릿수
     * @return          str를 소숫점 아래 cnt자리로 맞춘 문자열
     */
    public static String getRateForm(String str, int cnt) {
        if (str == null || str.trim().equals("")) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        if (str.indexOf(".") < 0) {
            sb.append(".");
            for (; cnt > 0; cnt--) {
                sb.append("0");
            }
        }
        else {
            for (; cnt >= str.length() - str.indexOf("."); cnt--) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    /**
     * 문자열을 입력받아 문자열 앞의 0을 제거하는 메소드
     *
     * @param str       치환대상 문자열
     * @return String   문자열의 앞에 포함된 0을 제거한 문자열
     */
    public static String rmZero(String str) {
        if (str == null)
            return "";

        char indexChr = ' ';
        int index = 0;
        while (index < str.length()) {
            if (str.charAt(index) == '0') {
                index++;
                continue;
            }
            indexChr = str.charAt(index);
            break;
        }
        if (index < str.length())
            return str.substring(indexChr != '.' ? index : index - 1);
        else
            return "0";
    }

    /**
     * 입력받은 문자열을 은행계좌번호 형식으로 변경시켜주는 메소드
     * @param acct      치환 대상 문자열
     * @return          은행계좌번호 형식으로 치환된 문자열
     */
    public static String getAcctForm(String acct) {
        if (acct == null)
            return "";

        acct = acct.trim();
        if (acct.length() < 12)
            return acct;
        else
            return putDash(acct, 3, 7, 2);
    }

    /**
     * 입력받은 문자열을 카드번호 형식으로 변경시켜주는 메소드
     * @param card      치환대상 문자열
     * @return          카드번호 형식으로 치환된 문자열
     */
    public static String getCardForm(String card) {
        if (card == null)
            return "";

        card = card.trim();
        if (card.length() < 16)
            return card;
        else
            return putDash(card, 4, 4, 4);
    }

    /**
     * str의 offset 자리 뒤에 '-'문자를 넣어주는 메소드
     *
     * @param str       치환대상 문자열
     * @param offset    치환대상 문자열 '-'문자를 넣어줄 위치
     * @return          str의 offset자리 뒤에 '-'를 첨가시킨 문자열
     */
    public static String putDash(String str, int offset) {
        if (str == null)
            return "";

        if (str.length() < offset || offset <= 0)
            return str;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i == offset) {
                sb.append("-");
            }
            if (c != ' ') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * str의 offset1 자리뒤와 offset1+cnt2자리 뒤에 '-'문자를 넣어주는 메소드
     * @param str       치환대상 문자열
     * @param offset1   첫번째 '-'가 들어갈 위치
     * @param cnt2      offset1 뒤에 두번째 '-'가 들어갈 위치
     * @return          str에 offset1, cnt2 자리 뒤에 '-'를 추가한 문자열
     */
    public static String putDash(String str, int offset1, int cnt2) {
        if (str == null)
            return "";

        int offset2 = offset1 + cnt2;
        if (str.length() < offset2 || offset2 <= 0)
            return str;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i == offset1 || i == offset2)
                sb.append("-");
            if (c != ' ')
                sb.append(c);
        }
        return sb.toString();
    }

    /**
     * str의 offset1 자리뒤와 offset1+cnt2자리 뒤, offset1+cnt2+cnt3자리뒤에 '-'문자를 넣어주는 메소드
     * @param str       치환대상 문자열
     * @param offset1   첫번째 '-'가 들어갈 위치
     * @param cnt2      offset1 뒤에 두번째 '-'가 들어갈 위치
     * @param cnt3      cnt3 뒤에 세번째 '-'가 들어갈 위치
     * @return          str에 offset1, cnt2, cnt3위치 뒤에 '-'를 추가한 문자열
     */
    public static String putDash(String str, int offset1, int cnt2, int cnt3) {
        if (str == null)
            return "";

        int offset2 = offset1 + cnt2;
        int offset3 = offset2 + cnt3;
        if (str.length() < offset3 || offset3 <= 0)
            return str;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i == offset1 || i == offset2 || i == offset3)
                sb.append("-");
            if (c != ' ')
                sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 문자열 앞뒤의 공백을 잘라주는 메소드
     * @param s     치환대상 문자열
     * @return      공백을 잘라낸 문자열
     */
    public static String trim(String s) {
        if (s == null)
            return "";

        int st = 0;
        char val[] = s.toCharArray();
        int count = val.length;
        int len;
        for (len = count; st < len && (val[st] <= ' ' || val[st] == '\u3000'); st++) {
            ;
        }
        for (; st < len && (val[len - 1] <= ' ' || val[len - 1] == '\u3000'); len--) {
            ;
        }
        return st <= 0 && len >= count ? s : s.substring(st, len);
    }

    /**
     * 입력받은 문자열을 length가 될때까지 빈 공백을 붙여주는 메소드
     * @param str       치환대상 문자열
     * @param length    공백을 덧붙여 완성할 문자열의 길이
     * @return          공백을 붙여 length의 길이가 된 문자열
     */
    public static String getANstring(String str, int length) {
        if (str == null)
            return "";

        for (int i = length - str.length(); i > 0; i--) {
            str = str + " ";
        }
        return str;
    }

    /**
     * 입력받은 숫자를 length가 될때까지 빈 공백을 붙여주는 메소드
     * @param intstr        치환대상 숫자열
     * @param length        공백을 덧붙여 완성할 숫자열의 길이
     * @return              공백을 붙여 length의 길이가 된 문자열
     */
    public static String getNstring(int intstr, int length) {
        String str = Integer.toString(intstr);
        for (int i = length - str.length(); i > 0; i--) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * 계좌번호 형식의 문자열에서 '-'문자를 제거해주는 메소드
     * @param str       치환 대상 문자열
     * @return String   '-'가 제거된 문자열
     */
    public static String delDashAccNo(String str) {
        String temp = null;
        str = str.trim();
        int len = str.length();
        switch (len) {
            case 17: // '\021'
                temp = str.substring(0, 3) + str.substring(4, 10) +
                    str.substring(11, 13) + str.substring(14, 17);
                break;
            case 13: // '\r'
                temp = str.substring(0, 3) + str.substring(4, 6) +
                    str.substring(7, 13);
                break;
            case 14: // '\016'
            case 15: // '\017'
            case 16: // '\020'
            default:
                temp = str;
                break;
        }
        return temp;
    }

    /**
     * 화폐단위 표기법대로 문자열 사이에 ','를 추가해주는 메소드
     *
     * @param str       치환 대상 문자열
     * @return String   화폐단위 표기법대로 ','가 추가된 문자열
     */
    public static String addComma(String str) {
        String temp = null;
        if (str == null || str.equals("")) {
            temp = "0";
        }
        else {
            double change = Double.valueOf(str.trim()).doubleValue();
            DecimalFormat decimal = new DecimalFormat("###,###,###,###");
            temp = decimal.format(change);
        }
        return temp;
    }

    /**
     * 소숫점이 포함된 문자열의 소숫점 앞자리를 화폐단위 표기법대로 치환해주는 메소드
     * @param str       치환 대상 문자열
     * @return String   화폐단위 표기법대로 소숫점 앞자리에 ','가 추가된 문자열
     */
    public static String eRateFormat(String str) {
        String temp = null;
        if (str == null || str.equals("")) {
            temp = "0";
        }
        else {
            double change = Double.valueOf(str.trim()).doubleValue();
            DecimalFormat decimal = new DecimalFormat("###,###,###.##");
            temp = decimal.format(change);
        }
        return temp;
    }

    /**
     * 문자열에서 ','를 제거해주는 메소드
     *
     * @param str       치환대상 문자열
     * @return String   ','를 제거한 문자열
     */
    public static String delComma(String str) {
        if (str == null) {
            return "";
        }
        StringBuffer dest = new StringBuffer();
        for (int i = 0; i < str.length(); ) {
            int c = str.charAt(i);
            switch (c) {
                default:
                    dest.append( (char) c);
                    // fall through
                case 44: // ','
                    i++;
                    break;
            }
        }
        return dest.toString();
    }

    /**
     * 문자열에서 '-'를 제거해주는 문자열
     * @param s         치환대상 문자열
     * @return String   '-'를 제거한 문자열
     */
    public static String delDash(String s) {
        if (s == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '-') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 문자열에서 '.'를 제거해주는 문자열
     * @param str       치환대상 문자열
     * @return String   '.'를 제거한 문자열
     */
    public static String delDot(String str) {
        if (str == null) {
            return "";
        }
        StringBuffer dest = new StringBuffer();
        for (int i = 0; i < str.length(); ) {
            int c = str.charAt(i);
            switch (c) {
                default:
                    dest.append( (char) c);
                    // fall through
                case 46: // '.'
                    i++;
                    break;
            }
        }
        return dest.toString();
    }

    /**
     * 문자열에서 ' '를 제거해주는 문자열
     * @param s       치환대상 문자열
     * @return String   ' '를 제거한 문자열
     */
    public static String delSpace(String s) {
        if (s == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 문자열에서 특정 char를 제거해주는 메소드
     * @param str       치환대상 문자열
     * @return String   '-',' ',':'이 제거된 문자열
     */
    public static String delChar(String str) {
        if (str == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != '-' && c != ' ' && c != ':') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 공백을 &nbsp태그로 치환해주는 메소드
     * @param str       치환대상 문자열
     * @return String   공백을 &nbsp로 치환한 문자열
     */
    public static String null2Nbsp(String str) {
        String ret = null;
        try {
            ret = str;
            if (str == null)
                ret = "&nbsp;";
        }
        catch (NullPointerException e) {
            ret = "&nbsp;";
            return ret;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Enter를 br태그로 변경해주는 메소드
     *
     * @param str       치환대상 문자열
     * @return String   Enter가 br태그로 변경된 문자열
     */
    public static String enterToBr(String str) {
        String ret = "";
        try {
            for (StringTokenizer st = new StringTokenizer(str, "\n"); st.hasMoreTokens(); ) {
                ret = ret + st.nextToken() + "<br>";
            }
            return ret;
        }
        catch (Exception e) {
            return str;
        }
    }

    /**
     * 문자열에 포함된 공백을 제거해주는 메소드
     * @param param     치환 대상 문자열
     * @return String   공백이 제거된 문자열
     */
    public static String mTrim(String param) {
        try {
            for (int i = 0; i < param.length(); ) {
                if (param.substring(i, i + 1).equals(" ") || param.substring(i, i + 1).equals("-"))
                    param = param.substring(0, i) + param.substring(i + 1, param.length());
                else
                    i++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.toString());
        }
        return param;
    }

    /**
     * 전화번호를 입력받아 v_flag 단계에 해당하는 값을 리턴해주는 메소드
     * @param v_telno       처리대상 문자열
     * @param v_flag        리턴을 원하는 문자열 단계
     * @return String       v_flag 단계에 해당하는 문자열
     */
    public static String getTelSep(String v_telno, int v_flag) {
        String telno = "";
        String d_telno = "";
        String r_telno = "";
        String telno1 = "";
        String telno2 = "";
        String telno3 = "";
        try {
            if (v_telno != null) {
                telno = mTrim(v_telno);
                if (telno.length() >= 9) {
                    d_telno = telno.substring(0, 2);
                    if (d_telno.equals("02")) {
                        telno1 = telno.substring(0, 2);
                    }
                    else {
                        telno1 = telno.substring(0, 3);
                    }
                    telno2 = telno.substring(telno1.length(),
                                             telno.length() - 4);
                    telno3 = telno.substring(telno.length() - 4, telno.length());
                    if (v_flag == 1) {
                        r_telno = telno1;
                    }
                    else
                    if (v_flag == 2) {
                        r_telno = telno2;
                    }
                    else
                    if (v_flag == 3) {
                        r_telno = telno3;
                    }
                    else
                    if (v_flag == 4) {
                        r_telno = telno1 + "-" + telno2 + "-" + telno3;
                    }
                }
                else {
                    r_telno = telno;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.toString());
        }
        return r_telno;
    }

    /**
     * 입력받은 문자열에서 out_len의 길이만큼 문자열을 잘라 리턴하는 메소드
     *
     * @param str           치환 대상 문자열
     * @param out_len       잘라낼 문자열의 길이
     * @return String       잘라낸 문자열
     */
    public static String getFixLen(String str, int out_len) {
        return getFixLen(str, out_len, "");
    }

    /**
     * 입력받은 문자열에서 out_len의 길이만큼 문자열을 잘라 리턴하는 메소드
     *
     * @param str           치환 대상 문자열
     * @param out_len       잘라낼 문자열의 길이
     * @param type          대상길이 초과했을때 붙일 문자
     * @return String       잘라낸 문자열
     */
    public static String getFixLen(String str, int out_len, String type) {
        if (str == null || str.trim().equals("") || str.equals(null)) {
            return "";
        }
        byte input[] = str.getBytes();
        //byte temp[] = new byte[out_len];
        int in_len = input.length;
        if (in_len > out_len)
            return bytesToText(input, 0, out_len) + type;
        else
            return str;
    }
    /**
     * byte를 문자열로 치환해주는 메소드
     *
     * @param b             바이트 배열
     * @param bytesStart
     * @param textLength
     * @return String       문자열로 치환된 결과값
     */
    public static String bytesToText(byte b[], int bytesStart, int textLength) {
        int len = 0;
        int i;
        for (i = bytesStart; i < b.length && len < textLength; i++) {
            if (b[i] >= 0 && b[i] <= 255) {
                len++;
                continue;
            }
            if ( ( -95 > b[i] || b[i] > -84) && ( -80 > b[i] || b[i] > -56) &&
                ( -54 > b[i] || b[i] > -3)) {
                break;
            }
            i++;
            try {
                if ( -95 <= b[i] && b[i] <= -2) {
                    len++;
                    continue;
                }
                i--;
            }
            catch (Exception e) {
                i--;
            }
            break;
        }
        if (i == bytesStart) {
            return "";
        }
        else {
            return new String(b, bytesStart, i);
        }
    }

    /**
     * 입력받은 문자열의 숫자.영어.문자 여부를 비교하는 메소드
     *
     * @param input     치환대상 문자열
     * @return String   검사 결과
     */
    public static String isEngDigitOrLetter(String input) {
        if (input == null || input.equals(""))
            return "";

        byte binput[] = input.getBytes();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (!Character.isDigit(ch) && !Character.isLetter(ch) && !Character.isWhitespace(ch)) {
                return "";
            }
        }
        for (int i = 0; i < binput.length; i++) {
            if ( (binput[i] < 0 || binput[i] > 255) &&
                ( -95 <= binput[i] && binput[i] <= -84 ||
                 -80 <= binput[i] && binput[i] <= -56 ||
                 -54 <= binput[i] && binput[i] <= -3)) {
                return "";
            }
        }
        return input;
    }

    /**
     * 문자열 속의 특정 값을 대체문자로 변경해주는 메소드
     *
     * @param in        치환 대상 문자열
     * @param from      치환 대상 문자
     * @param to        치환 될 문자
     * @return String   치환된 문자열
     */
    public static String replace(String in, String from, String to) {
        StringBuffer sb = new StringBuffer(in.length() * 2);
        String posString = in.toLowerCase();
        String cmpString = from.toLowerCase();
        int i = 0;
        for (boolean done = false; i < in.length() && !done; ) {
            int start = posString.indexOf(cmpString, i);
            if (start == -1) {
                done = true;
            }
            else {
                sb.append(in.substring(i, start) + to);
                i = start + from.length();
            }
        }
        if (i < in.length()) {
            sb.append(in.substring(i));
        }
        return sb.toString();
    }

    /**
     * 입력받은 문자열을 정의한 문자열로 변환해주는 메소드
     * @param sTagString    치환 대상 문자열
     * @return String       치환 결과 문자열
     */
    public static String replaceStr(String sTagString) {
        String sTextString = null;
        sTextString = replace(sTagString, "\"", "&quot;");
        sTextString = replace(sTextString, "& ", "&amp; ");
        sTextString = replace(sTextString, "<", "&lt;");
        sTextString = replace(sTextString, ">", "&gt;");
        sTextString = replace(sTextString, "\n", "<br>\n");
        return sTextString;
    }

    /**
     * 구분값 사이에 값이 없을 때 존재하는 값만 리턴
     *
     * @param strSplit_String       치환 대상 문자열
     * @param chrGubun_Character    구분값
     * @return Vector               구분값을 기준으로 나눈 문자열을 담고 있는 벡터
     */
    public static Vector split(String strSplit_String, char chrGubun_Character) {
        int intFrom = 0; // substring시에 From 자리
        int intTo = 0; // substing시에 To 자리
        int index = 0;
        Vector vc = new Vector();
        for (intTo = 0; intTo < strSplit_String.length(); intTo++) {
            if (strSplit_String.charAt(intTo) == chrGubun_Character) {
                if (intFrom < intTo) {
                    vc.addElement(strSplit_String.substring(intFrom, intTo));
                    index++;
                    intFrom = intTo + 1;
                }
            }
        }
        vc.addElement(strSplit_String.substring(intFrom, strSplit_String.length()));
        return vc;
    }

    /**
     * 구분값 사이에 값이 없을 때 공백값도 같이 리턴
     *
     * @param strSplit_String       치환 대상 문자열
     * @param chrGubun_Character    구분값
     * @return Vector               구분값을 기준으로 나눈 문자열을 담고 있는 벡터
     */
    public static Vector null2split(String strSplit_String, char chrGubun_Character) {
        int intFrom = 0; // substring시에 From 자리
        int intTo = 0; // substing시에 To 자리
        int index = 0;
        Vector vc = new Vector();
        for (intTo = 0; intTo < strSplit_String.length(); intTo++) {
            if (strSplit_String.charAt(intTo) == chrGubun_Character) {
                vc.addElement(strSplit_String.substring(intFrom, intTo));
                index++;
                intFrom = intTo + 1;
            }
        }
        vc.addElement(strSplit_String.substring(intFrom, strSplit_String.length()));
        return vc;
    }

    /**
     *
     * 입력된 스트링에서 구분자(delimiter)에 나열된 모든 문자를 기준으로 문자열을 분리하고 분리된 문자열을 배열에 할당하여 반환한다.
     *
     * <pre>
     *
     * [사용 예제]
     *
     * split2Array("ABCDEABCDE", "BE")
     * ===> A
     *         CD
     *         A
     *         CD
     *
     * </pre>
     *
     * @param strTarget
     * @param delimiter
     * @return java.lang.String[]
     * @roseuid 403A9A6E036B
     */
    
    public static String[] split2Array(String strTarget, String delimiter) {
        if(strTarget == null) return null;

        StringTokenizer st = new StringTokenizer(strTarget, delimiter);
        String[] names = new String[st.countTokens()];
        for(int i = 0; i < names.length; i++) {
            names[i] = st.nextToken().trim();
        }

        return names;
    }

    /**
     *
     * 입력된 스트링에서 구분자(delimiter)를 하나의 단어로 인식하고 이 단어를 기준으로 문자열을 분리, 분리된 문자열을 배열에 할당하여 반환한다.
     *
     * <pre>
     *
     * [사용 예제]
     *
     * split2Array("AA-BBB--DDDD", "-",true)
     * ===> AA
     *         BBB
     *
     *         DDDD
     *
     * split2Array("AA-BBB--DDDD", "-", false);
     * ===> AA
     *         BBB
      *         DDDD
     *
     * split2Array("ABCDEABCDE", "BE", true)
     * ===> ABCDEABCDE
     *
     * </pre>
     *
     * @param strTarget
     * @param delimiter 구분자(delimiter)로 인식할 단어로서 결과 문자열에는 포함되지 않는다.
     * @param isIncludedNull 구분자로 구분된 문자열이 Null일 경우 결과값에 포함여부 ( true : 포함, false : 포함하지 않음. )
     * @return java.lang.String[]
     * @roseuid 403A9A6E0399
     */
    public static String[] split2Array(String strTarget, String delimiter, boolean isIncludedNull) {

        //int index = 0;
        String[] resultStrArray = null;

        try {
            Vector v =  new Vector();

            String strCheck = new String(strTarget);
            while (strCheck.length() != 0) {
                int begin = strCheck.indexOf(delimiter);
                if (begin == -1) {
                    v.add(strCheck);
                    break;
                } else {
                    int end = begin + delimiter.length();
                    //	StringTokenizer는 구분자가 연속으로 중첩되어 있을 경우 공백 문자열을 반환하지 않음.
                    // 따라서 아래와 같이 작성함.
                    if (isIncludedNull) {
                        v.add(strCheck.substring(0, begin));
                        strCheck = strCheck.substring(end);
                        if (strCheck.length() == 0 ) {
                            v.add(strCheck);
                            break;
                        }
                    } else{
                        if (! CommUtil.isEmpty(strCheck.substring(0, begin))){
                            v.add(strCheck.substring(0, begin));
                        }
                        strCheck = strCheck.substring(end);
                    }

                }
            }

            String[] tempString = new String[0];
            resultStrArray = (String[]) v.toArray(tempString);

        } catch (Exception e) {
            return resultStrArray;
        }

        return resultStrArray;
    }

    /**
     *
     * 입력된 스트링에서 제거할 문자(elimination)에 나열한 모든 문자를 제거한다.
     *
     * <pre>
     *
     * [사용 예제]
     *
     * stringRemove("02)2344-5555", "-# /)(:;")	===> 0223445555
     * stringRemove("ABCDEABCDE", "BE")		===> ACDACD
     *
     * </pre>
     * @param strTarget
     * @param elimination
     * @return java.lang.String
     */
    public static String stringRemove(String strTarget, String elimination) {
        if (strTarget == null || strTarget.length() == 0 || elimination == null)
            return strTarget;
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(strTarget, elimination);
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
        }
        return sb.toString();
    }

    /**
     *
     * 입력한 문자열 앞뒤에  특정문자를 Left Padding한 문자열을 반환한다.
     *
     * <pre>
     *
     * [사용 예제]
     *
     * padLeft("AAAAAA", 'Z', 10) )	===> ZZZZAAAAAA
     *
     * </pre>
     *
     * @param str
     * @param padValue
     * @param length
     * @param value
     * @return java.lang.String
     */
    public static String padLeft(String value, char padValue, int length) {

        if (value == null)
            value = "";

        byte[] orgByte = value.getBytes();
        int orglength = orgByte.length;

        if (orglength < length) { //add Padding character
            byte[] paddedBytes = new byte[length];

            int padlength = length - orglength;

            for (int i = 0; i < padlength; i++) {
                paddedBytes[i] = (byte) padValue;
            }

            System.arraycopy(orgByte, 0, paddedBytes, padlength, orglength);

            return new String(paddedBytes);
        }
        else if (orglength > length) { //주어진 길이보다 남는다면, 주어진 길이만큼만 잘른다.
            byte[] paddedBytes = new byte[length];
            System.arraycopy(orgByte, 0, paddedBytes, 0, length);
            return new String(paddedBytes);
        }

        return new String(orgByte);
    }

    /**
     *
     * 입력한 문자열 앞뒤에  특정문자를 Right Pading한 문자열을 반환한다.
     *
     * <pre>
     *
     * [사용 예제]
     *
     * padRight("AAAAAA", 'Z', 10) )	===> AAAAAAZZZZ
     *
     * </pre>
     *
     * @param str
     * @param padValue
     * @param length
     * @param value
     * @return java.lang.String
     */
    public static String padRight(String value, char padValue, int length) {

        if (value == null)
            value = "";

        byte[] orgByte = value.getBytes();
        int orglength = orgByte.length;

        if (orglength < length) { //add Padding character
            byte[] paddedBytes = new byte[length];

            System.arraycopy(orgByte, 0, paddedBytes, 0, orglength);
            while (orglength < length) {
                paddedBytes[orglength++] = (byte) padValue;
            }
            return new String(paddedBytes);
        }
        else if (orglength > length) { //주어진 길이보다 남는다면, 주어진 길이만큼만 잘른다
            byte[] paddedBytes = new byte[length];
            System.arraycopy(orgByte, 0, paddedBytes, 0, length);
            return new String(paddedBytes);
        }

        return new String(orgByte);
    }

    /**
     * "(쌍따옴표)를 Chr(34)로 변환하여 리턴한다.(화면에서의 오류방지를 위해서)
     * @param value
     * @return String
     * @throws MainException
     */
    public static String replaceQuotToChr(String value){
        String tmpValue = "";
        String strGubun = "Chr(34)";
        String chrRootGubun = "\"";

        if (value != null) {
            tmpValue = value.replaceAll(chrRootGubun, strGubun);
        }
        return tmpValue;

    }

    /**
     * '(홋따옴표)를 "(쌍따옴표)로 변환하여 리턴한다.(SQL오류 방지를 위해서)
     * @param value
     * @return String
     * @throws MainException
     */
    public static String replaceQuotToDoubleQuot(String value){
        String tmpValue = "";
        String strGubun = "''";
        String chrRootGubun = "'";

        if (value != null) {
            tmpValue = value.replaceAll(chrRootGubun, strGubun);
        }
        return tmpValue;
    }

    /**
     * 8자리의 포멧이 없는 날짜를 포멧을 추가하여 리턴하는 메소드
     * (서식민원의 웹서비스에서 리턴하는 날짜가 포멧이 없기때문에 사용)
     * @param param     String 포멧이 없는 날짜
     * @return          포멧이 있는 날짜
     */
    public static String getFormatDate(String value, String delim) {
        // null 이거나 공백이거나, 8자리가 아닐 경우
        if (value == null || value.trim().equals(""))
            return "";
        else if(value.length() != 8)
            return value;
        else if (value.substring(0,1).equals("0"))
            return "";
        else
            return value.substring(0,4) + delim + value.substring(4,6) + delim + value.substring(6);
    }

    /**
     * 14자리의 포멧이 없는 일시를 포멧을 추가하여 리턴하는 메소드
     * (서식민원의 웹서비스에서 리턴하는 일시가 포멧이 없기때문에 사용)
     * @param param     String 포멧이 없는 일시
     * @return          포멧이 있는 일시
     */
    public static String getFormatDateTime(String value) {
        // null 이거나 공백이거나, 14자리가 아닐 경우
        if (value == null || value.trim().equals(""))
            return "";
        else if( value.length() != 14)
            return value;
        else if (value.substring(0,1).equals("0"))
            return "";
        else
            return value.substring(0,4) +"-"+ value.substring(4,6) +"-"+ value.substring(6,8)
                +" "+ value.substring(8,10) +":"+ value.substring(10,12) +":"+ value.substring(12);
    }

    /**
     * n일 후 또는 전 날짜를 계산하는 하는 메소드
     * @param param     String 기준일자
     * @param           int n일(전일 경우 '-', 후일경우 없음)
     *                  예) 30일전 : -30, 10일 후 : 10
     * @return          계산된 날짜
     */
    public static String calculateDate(String sDate, int iDay) {

        sDate = delDash(sDate);  // 포멧이 있을 경우 제거(StringUtil)  delChar

        Calendar oDate = Calendar.getInstance();
        oDate.set(Integer.parseInt(sDate.substring(0,4)), Integer.parseInt(sDate.substring(4,6))-1, Integer.parseInt(sDate.substring(6)));

        // 날짜 계산
        oDate.add(Calendar.DATE, iDay);

        // 날짜 저장
        String year  = String.valueOf( oDate.get(Calendar.YEAR) );
        String month = String.valueOf( oDate.get(Calendar.MONTH) + 1 );
        String days  = String.valueOf( oDate.get(Calendar.DATE) );

        // Days를 두자리로 바꿈 [예 : 1 -> 01]
        if( days.length() == 1 ){
            days = "0" + days;
        }

        // Month를 두자리로 바꿈 [예 : 1 -> 01]
        if( month.length() == 1 ) {
            month = "0" + month;
        }

        // Step4. 날짜 반환
        return year + month + days;

	}

    /**
     * 전화번호 잘라주는 함수
     * @param param     String 전화번호
     * @param
     */

    public static String[] getDivTelephoneNumber(String oriTelephoneNumber) {

        //변수선언
        String[] divPhoneNumber = {"","", ""};

        //null 처리
        if (oriTelephoneNumber == null || oriTelephoneNumber.equals("")) {return divPhoneNumber;}

        //전화번호 분석
        if (oriTelephoneNumber.length() < 9) { return divPhoneNumber;}
        else {
            if (oriTelephoneNumber.startsWith("02")) {
                divPhoneNumber[0] = "02";
                divPhoneNumber[1] = oriTelephoneNumber.substring(2, oriTelephoneNumber.length()-4);
                divPhoneNumber[2] = oriTelephoneNumber.substring(oriTelephoneNumber.length()-4, oriTelephoneNumber.length());
            }
            else {
                divPhoneNumber[0] = oriTelephoneNumber.substring(0, 3);
                divPhoneNumber[1] = oriTelephoneNumber.substring(3, oriTelephoneNumber.length()-4);
                divPhoneNumber[2] = oriTelephoneNumber.substring(oriTelephoneNumber.length()-4, oriTelephoneNumber.length());
            }

        }

        //결과 반환
        return divPhoneNumber;

    }


    //-----------------------------------------
    // Html 특수 문자 해결
    //-----------------------------------------
    public static String convJS(Object pObject) {
        if (pObject == null) {
            return "";
        }

        String value = (String) pObject;

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '\\':
                    buffer.append("\\\\");
                    break;
                case '\'':
                    buffer.append("\\\'");
                    break;
                case '"':
                    buffer.append("\\\"");
                    break;
                case '\r':
                    buffer.append("\\r");
                    break;
                case '\n':
                    buffer.append("\\n");
                    break;
                default:
                    buffer.append(ch);
            }
        }

        return buffer.toString();
    }


    /**
     * <input type=text name=n value="xxxxxxxx"> or <TEXTAREA>xxxxxxxx</TEXTAREA>
     * xxxxxxxx에 해당하는 값을 넣을 때 변환해 줘야 할 것들
     * created by singi
     * @param str    원본 문자열
     * @return        변환된 문자열
     */
    public static String convertHTML(String str)
    {
        if(str == null) return "";

        String tempStr = str;
        tempStr = replace(tempStr, "&", "&amp;", true);        //&
        tempStr = replace(tempStr, "\"", "&quot;", true);    //큰 따옴표
        tempStr = replace(tempStr, "'", "&#39;", true);        //작은 따옴표
        tempStr = replace(tempStr, "<", "&lt;", true);        //<

        return tempStr;
    }



    /**
     * 주어진 문자열중에 oldStr을 newStr으로 치환한 문자열을 리턴하는 Static 메서드
     * 작성 날짜: (2003-01-04 오후 1:38)
     * 작성자 : Bark Chulhui (solsin@cein.or.kr)
     * @param src        원본 문자열
     * @param oldStr    바꾸고자 하는 문자열
     * @param newStr    바꿀 문자열
     * @param ignoreCase 대소문자 구별을 할것인지의 여부 : true (구별을 함), false (구별을 안함)
    */
    public static String replace( final String src, String oldStr, String newStr, boolean ignoreCase) {
        StringBuffer strbuf = new StringBuffer();
        String tmpstr = null;    // ignoreCase가 true일 경우에는 src를, false일 경우에는 src.toLowerCase()를 대입

        // 대소문자 구별에 따른 대입
        if ( ignoreCase ) {
            tmpstr = src;
        } else {
            tmpstr = src.toLowerCase();
            oldStr = oldStr.toLowerCase();
        }

        int first = 0;
        int next = 0;
        while ( (next = tmpstr.indexOf(oldStr, first)) != -1 ) {
            strbuf.append(src.substring(first, next));
            strbuf.append(newStr);
            first = next+oldStr.length();
        }
        strbuf.append(src.substring(first, src.length()));

        return strbuf.toString();
    }

    /**
    *
    * 입력한 값이 null 또는 null String 일 경우 true를 return 한다.
    *
    * <pre>
    *
    * [사용 예제]
    *
    * ValidationUtil.isEmpty("")        ===> true
    * ValidationUtil.isEmpty(null)  ===> true
    * ValidationUtil.isEmpty("1")       ===> false
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

   public static String ascToKsc(String value) {
       try {
           String strRet = new String(value.getBytes("8859_1"), "KSC5601");
           return strRet;
       } catch (UnsupportedEncodingException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
           return null;
       }
   }

   public static String kscToAsc(String value) {
       try {
           String strRet = new String(value.getBytes("KSC5601"), "8859_1");
           return strRet;
       } catch (UnsupportedEncodingException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();

           return null;
       }
   }
}
