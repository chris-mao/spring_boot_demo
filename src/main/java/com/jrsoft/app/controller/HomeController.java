/**
 * 
 */
package com.jrsoft.app.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jrsoft.auth.entity.AuthUserDelegate;
import com.jrsoft.auth.helper.AuthHelper;
import com.jrsoft.auth.service.AuthUserDelegateService;

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
	 * 
	 */
	@Autowired
	private AuthUserDelegateService authUserDelegateService;

	/**
	 * 首页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping({ "/", "/index" })
	public String index(HttpServletRequest request, Model model) {
		// 委托人
		List<AuthUserDelegate> clients = this.authUserDelegateService.findAllByToUser(AuthHelper.getCurrentUser());
		model.addAttribute("clients", clients);
		return "index";
	}

}
