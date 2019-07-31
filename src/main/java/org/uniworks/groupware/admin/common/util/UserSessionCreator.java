/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.domain.Cm010c;

/**
 * @author Park Chungwan
 *
 */
@Controller
public class UserSessionCreator {
	private static final Logger logger = LoggerFactory.getLogger(UserSessionCreator.class);
	
	public static void createUserSession(Cm010c cm010c, HttpServletRequest request) {
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		if (userSession == null) {
			userSession = new UserSession();
			//사용자 정보를 세션에 설정.
			userSession.setCoId(cm010c.getCoId());	//회사 구분
			userSession.setAdminId(cm010c.getAdminId());//사용자 ID
			userSession.setPswd(cm010c.getPswd());	//로그인 비밀번호
			userSession.setAdminType(cm010c.getAdminType()); //관리자 유형
			userSession.setLang(request.getLocale().getLanguage());
			
			if (request.getLocale().getCountry() == null || request.getLocale().getCountry() == "") {
				userSession.setLocale(ApplicationConfigReader.get("default.country"));
				userSession.setLowCaseLocale(ApplicationConfigReader.get("default.country"));
			} else {
				userSession.setLocale(request.getLocale().getCountry());
				userSession.setLowCaseLocale(request.getLocale().getCountry());
			}
			
			//HttpSession 생성
			HttpSession session = request.getSession(true);
			// HttpSeesion Timeout 설정 (12시간 유지로 설정 - 시간 * 분 * 초)
			// application.properties 파일에서 세션 타임아웃 시간을 가져온다.
			String strSessionTimeout = ApplicationConfigReader.get("session.timeout");
			Integer timeout = Integer.parseInt(strSessionTimeout);
			session.setMaxInactiveInterval(timeout.intValue() * 60 * 60);
			session.setAttribute("userSession", userSession);
		}
	}
	
	/**
	 * 쿠키를 생성한다.
	 * @param request
	 * @param response
	 * @param key Cookie 변수명
	 * @param value Cookie 
	 */
	public static void createCookie(HttpServletRequest request, HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);
	}
	
	/**
	 * 쿠키 정보를 가져온다.
	 * @param request
	 * @param key Cookie 변수명
	 * @return Cookie 변수명에 저장된 쿠키 값을 반환
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;		
		String userId = null;

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals(key)) userId = (String) cookie.getValue();
			}
		}
		
		return userId;
	}		
}
