/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.controller.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Park Chung Wan
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class CompanyManagement {
	@RequestMapping(value = "/companyMgr", method = RequestMethod.GET)
	public ModelAndView companyMgr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("company/company_mgr_01");
		
		return mav;
	}
}
