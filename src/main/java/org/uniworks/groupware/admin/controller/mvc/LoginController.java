/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.controller.mvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;
import org.uniworks.groupware.admin.common.UserSession;
import org.uniworks.groupware.admin.common.util.ApplicationConfigReader;
import org.uniworks.groupware.admin.common.util.UserSessionCreator;
import org.uniworks.groupware.admin.domain.Cm010c;
import org.uniworks.groupware.admin.domain.CommonCode;
import org.uniworks.groupware.admin.service.Cm010cService;
import org.uniworks.groupware.admin.service.CommonService;

/**
 * @author Park Chungwan
 * 로그인, 로그아웃 기능을 수행하는 Controller
 */
@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired Cm010cService cm010cService;
	@Autowired CommonService cmmService;
		
	@RequestMapping( value = "/loginForm", method = RequestMethod.GET )
	public ModelAndView loginForm(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, 
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		//Session정보를 가져온다. 세션 정보가 없을 경우 로그인 페이지로 이동.
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
				
		if (error != null) {
			mav.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			mav.addObject("msg", "You've been logged out successfully.");
		}
		
		if (userSession != null) {
			String redirectUrl = ApplicationConfigReader.get("webAppRoot") + "/main";
			try {
				response.sendRedirect(redirectUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		mav.setViewName("loginForm");

		return mav;
	}
	
	/**
	 * Spring Security에서 정상적인 로그인 후 사용자 세션 생성 및 로그인 후 메인 화면으로 이동.
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/main")
	public ModelAndView main(@RequestParam(required=false) UserSession userSession, HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView("main");
		//로그인 사용자 ID가져 오기.		
		String userId = authentication.getName();
		logger.debug("authentication name : " + authentication.getName());		
		
		Locale locale = request.getLocale();
		Map<String, Object> map = new HashMap<String, Object>();		
		String lang = null;
		
		if (locale.getLanguage() == null) {
			lang = ApplicationConfigReader.get("default.language");
		} else {
			lang = locale.getLanguage();
		}				
		
		Cm010c cm010c = null;
		if (userId != null) {
			if (userSession == null) {
				StringTokenizer st = new StringTokenizer(userId, ":");
				String coId = st.nextToken();
				String adminId = st.nextToken();
				
				map.put("coId", coId);
				map.put("adminId", adminId);
				
				//관리자 사용자 정보 가져오기
				cm010c = cm010cService.getCm010c(map);								
				
				//세션 생성
				UserSessionCreator.createUserSession(cm010c, request);
				//쿠기 생성
				String key = ApplicationConfigReader.get("cookieId");
				String cookieValue = userId;
				UserSessionCreator.createCookie(request, response, key, cookieValue);
			} else {
				logger.debug("Session Delete !!!");
			}
		} else {
			String redirectUrl = ApplicationConfigReader.get("webAppRoot") + "/logoutProc";
			response.sendRedirect(redirectUrl);
			return null;
		}	
						
		//다국어 선택을 위한 지원 언어를 가져온다.
		Map<String, Object> codeMap = new HashMap<String, Object>();
		codeMap.put("majCode", "CD001");
		codeMap.put("lang", lang);
		List<CommonCode> codeList = cmmService.getCommonSubCodeList(codeMap);
		
		mav.addObject("langList", codeList);
		mav.addObject("lang", lang);
		return mav;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping( value = "/logout")
	public ModelAndView logoutProc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		//Session 정보 제거
		request.getSession().removeAttribute("userSession");
		request.getSession().invalidate();
		
		//Cookie 정보 제거
		String key = ApplicationConfigReader.get("cookieId");
		Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		//Spring Security  세션 정보 삭제.
		SecurityContextHolder.getContext().setAuthentication(null);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {			
			logger.debug("logout : {}", authentication.getName());
			request.getSession().invalidate();
		}
		
		mav.setViewName("loginForm");
		return mav;
	}		
	
	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		ModelAndView model = new ModelAndView();
		
		//check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println("/403 : " + userDetail);
		
			model.addObject("username", userDetail.getUsername());			
		}
		
		model.setViewName("/error/403");
		return model;

	}
	
	/**
	 * 언어 변경
	 * @param lang
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/changeLanguage")
	public ModelAndView changeLanguage(@RequestParam(required=false) String lang,
										HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("main");
		//Session정보를 가져온다.
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
		
		if (userSession != null) {			
			userSession.setLang(lang);
			logger.debug("userSession.getLanguage() : " + userSession.getLang());
			WebUtils.setSessionAttribute(request, "userSession", userSession);
			
			Locale locale = null;
			
			//지원하고자하는 언어를 로케일에 설정.
			//새로운 언어를 추가할 경우 아래의 if문에 추가하면 됨.
			if (lang.equals("en")) {
				locale = Locale.ENGLISH;
			} else if (lang.equals("ko")) {
				locale = Locale.KOREAN;
			}
						
			HttpSession session = request.getSession();
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			
			//다국어 선택을 위한 지원 언어를 가져온다.
			Map<String, Object> codeMap = new HashMap<String, Object>();
			codeMap.put("majCode", "CD001");
			codeMap.put("lang", userSession.getLang());
			List<CommonCode> codeList = cmmService.getCommonSubCodeList(codeMap);
			
			mav.addObject("langList", codeList);			
			mav.addObject("lang", lang);
		}
		return mav;
	}
}