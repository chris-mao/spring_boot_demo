/**
 * 
 */
package com.jrsoft.auth.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthUserDelegateService;
import com.jrsoft.auth.service.AuthUserService;
import com.jrsoft.auth.utils.AuthUtils;

/**
 * com.jrsoft.auth.controller AuthUserDelegateController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/delegate")
public class AuthUserDelegateController {

	@Resource
	private AuthUserService authUserService;

	@Resource
	private AuthUserDelegateService authUserDelegateService;

	@GetMapping({ "", "/index" })
	@RequiresPermissions("authUser:delegate")
	public String runAsList(@RequestParam(defaultValue = "1") int page, Model model) {
		System.out.println("当前身份是： " + AuthUtils.getUser());
		System.out.println("前一个身份是：" + AuthUtils.getPreviousUser());
		model.addAttribute("previousUser", AuthUtils.getPreviousUser());
		model.addAttribute("clients", this.authUserDelegateService.findAllByToUser(AuthUtils.getUser()));
		model.addAttribute("delegates", this.authUserDelegateService.findAllByFromUser(AuthUtils.getUser()));
		return "auth/delegate/index";
	}

	@GetMapping("/grant/{toUserId}")
	@RequiresPermissions("authUser:delegate")
	public String grant(@PathVariable("toUserId") Integer toUserId) {
		AuthUser toUser = new AuthUser(toUserId);
		this.authUserDelegateService.grantDelegate(AuthUtils.getUser(), toUser);
		return "redirect:/delegate";
	}

	@GetMapping("/revoke/{toUserId}")
	@RequiresPermissions("authUser:delegate")
	public String revoke(@PathVariable("toUserId") Integer toUserId) {
		AuthUser toUser = new AuthUser(toUserId);
		this.authUserDelegateService.revokeDelegate(AuthUtils.getUser(), toUser);
		return "redirect:/delegate";
	}

	@GetMapping("/switchTo/{toUserId}")
	@RequiresPermissions("authUser:delegate")
	public String switchTo(@PathVariable("toUserId") Integer toUserId) {
		AuthUser toUser = new AuthUser(toUserId);
		toUser = authUserService.findOne(toUser);
		if (null == toUser) {
			// 目标用户不存在
		}
		if (AuthUtils.getUser().equals(toUser)) {
			// 自己不能切换到自己
		}
		if (!this.authUserDelegateService.exists(AuthUtils.getUser(), toUser)) {
			// 没有被授予身份
		}

		SimplePrincipalCollection spc = new SimplePrincipalCollection(toUser, "");
		AuthUtils.getCredential().runAs(spc);
		System.out.println(AuthUtils.getUser());
		return "redirect:/delegate";
	}

	@GetMapping("/switchBack")
	@RequiresPermissions("authUser:delegate")
	public String switchBack() {
		if (AuthUtils.getCredential().isRunAs()) {
			AuthUtils.getCredential().releaseRunAs();
		}
		return "redirect:/delegate";
	}
}
