/**
 * 박충완이 작성한 소스입니다.
 * 이 소스는 모든 개발자들이 자유롭게 수정/배포할 수 있습니다.
 * 단, 이소스를 근간으로 애플리케이션을 개발하실 때에는 꼭 출처를 명시해 주세요.
 */
package org.uniworks.groupware.admin.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

/**
 * @author Chungwan Park
 * CommonUtil.java 2011. 4. 12.
 */
public class WebUtil {
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
	
	/**
	 * 
	 * @param request
	 * @param obj
	 */
	public static void bind(HttpServletRequest request, Object obj) {
		Enumeration<?> params = request.getParameterNames();
		
		while(params.hasMoreElements()) {
			String name = (String) params.nextElement();
			String value = StringUtil.null2String(request.getParameter(name), "");
			
			if (!value.equals("")) {
				try {
					//BeanUtils는 기본적으로 ConvertUtilsBean에서 설정된 default값을 가지고 변환이 이루어진다.
					//변환 과정이 구현에 따라 바뀔 필요가 있을 경우, ConvertUtilsBean에서 구현이 바뀌어야 할 type을 deregister함수로 빼고
					//난 뒤에 새롭게 정의한 Converter를 해당 type으로 register함수를 이용해서 등록.
					/*
					ConvertUtilsBean cub = new ConvertUtilsBean();
					cub.deregister(Date.class);
					cub.register(new DateConverter(), Date.class);
					*/
					DateTimeConverter dtConverter = new DateConverter();
					dtConverter.setPattern("yyyyMMdd");
					ConvertUtilsBean cub = new ConvertUtilsBean();
					cub.deregister(Date.class);
					cub.register(dtConverter, Date.class);
					
					BeanUtilsBean bub = new BeanUtilsBean(cub, new PropertyUtilsBean());
					bub.setProperty(obj, name, value);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param map
	 * @param obj
	 */
	public static void bind(Map<String, Object> map, Object obj) {
		DateTimeConverter dtConverter = new DateConverter();
		dtConverter.setPattern("yyyyMMdd");
		ConvertUtilsBean cub = new ConvertUtilsBean();
		cub.deregister(Date.class);
		cub.register(dtConverter, Date.class);
		
		BeanUtilsBean bub = new BeanUtilsBean(cub);
		try {
			bub.populate(obj, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
