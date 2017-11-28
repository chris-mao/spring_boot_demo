/**
 * 
 */
package com.jrsoft.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jrsoft.auth.utils.AuthUtils;

/**
 * com.jrsoft.app.controller HomeController
 * 
 * 默认控制器
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
public class HomeController {

	/**
	 * 首页
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@GetMapping({ "/", "/index" })
	public String index(HttpServletRequest request, Model model) {
		// 当前身份
		model.addAttribute("currentUser", AuthUtils.getCurrentUser());
		// 前一个身份
		model.addAttribute("previousUser", AuthUtils.getPreviousUser());
		return "index";
	}

}
