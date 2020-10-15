/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.common.util.ApplicationConfigReader;

/**
 * @author Park Chungwan
 *
 */
public class SessionCheckInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SessionCheckInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (logger.isDebugEnabled()) logger.debug("SessionCheckInterceptor preHandle start");
		
		//Session정보를 가져온다. 세션 정보가 없을 경우 로그인 페이지로 이동.
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		String url = request.getRequestURI();
		logger.debug("SessionCheckInterceptor preHandle url : " + url);
		if (url.indexOf("loginForm") > -1 || url.indexOf("logout") > -1) {
			if (logger.isDebugEnabled()) logger.debug("SessionCheckInterceptor preHandle end");
			return true;
		}
		
		if (userSession == null) {
			String redirectUrl = ApplicationConfigReader.get("webAppRoot") + "/loginForm";
			response.sendRedirect(redirectUrl);
			if (logger.isDebugEnabled()) logger.debug("SessionCheckInterceptor preHandle end");
			return true;
		}
		if (logger.isDebugEnabled()) logger.debug("SessionCheckInterceptor preHandle end");
	    return true;
	}
	 
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (logger.isDebugEnabled()) logger.debug("SessionCheckInterceptor postHandle start");
    	UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
    	String url = request.getRequestURI();
    	logger.debug("SessionCheckInterceptor afterCompletion url : " + url);
    	if (url.indexOf("loginForm") > -1 || url.indexOf("logout") > -1) {			
			if (userSession != null) {
				if (logger.isDebugEnabled()) logger.debug("userSession not null : SessionCheckInterceptor postHandle end");
				//String redirectUrl = ApplicationConfigReader.get("webAppRoot") + "/main";
				//response.sendRedirect(redirectUrl);
			}
		}

        super.postHandle(request, response, handler, modelAndView);
        if (logger.isDebugEnabled()) logger.debug("SessionCheckInterceptor postHandle end");
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	if (logger.isDebugEnabled()) logger.debug("SessionCheckInterceptor afterCompletion start");
		
        super.afterCompletion(request, response, handler, ex);
        if (logger.isDebugEnabled()) logger.debug("SessionCheckInterceptor afterCompletion end");
    }
 
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
