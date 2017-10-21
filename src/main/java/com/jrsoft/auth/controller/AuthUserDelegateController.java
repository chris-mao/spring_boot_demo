/**
 * 
 */
package com.jrsoft.auth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserDelegate;
import com.jrsoft.auth.exception.IllegalDelegateException;
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
		// System.out.println("当前身份是： " + AuthUtils.getUser());
		// System.out.println("前一个身份是：" + AuthUtils.getPreviousUser());
		// 前一个身份
		model.addAttribute("previousUser", AuthUtils.getPreviousUser());
		// 委托人
		model.addAttribute("clients", this.authUserDelegateService.findAllByToUser(AuthUtils.getUser()));
		List<AuthUserDelegate> delegates = this.authUserDelegateService.findAllByFromUser(AuthUtils.getUser());
		// 代理人（被委人）
		model.addAttribute("delegates", delegates);
		
		// 如果已切换到委托人身份，则不再需要显示出所有候选代理人供其选择
		if (AuthUtils.getPreviousUser() == null) {
			List<AuthUser> candidates = authUserService.findAllAvailableUser();
			candidates.remove(AuthUtils.getUser());
			for (AuthUserDelegate user : delegates) {
				candidates.remove(user.getToUser());
			}
			model.addAttribute("candidates", candidates);
		}
		return "auth/delegate/index";
	}

	@GetMapping("/grant/{toUserId}")
	@RequiresPermissions("authUser:delegate")
	public String grant(@PathVariable("toUserId") Integer toUserId)
			throws DataNotFoundException, IllegalDelegateException {
		AuthUser toUser = new AuthUser(toUserId);
		toUser = authUserService.findOne(toUser);
		if (null == toUser) {
			throw new DataNotFoundException("您指定的代理用户不存在！ID：" + toUserId);
		} else if (toUser.getState() == AuthUserStateEnum.LOCKED) {
			throw new IllegalDelegateException("代理用户的帐号已被锁，无法设为您的代理人！！");
		} else if (toUser.getState() == AuthUserStateEnum.EXPIRED) {
			throw new IllegalDelegateException("代理用户的帐号已过期，无法设为您的代理人！！");
		} else if (toUser.getState() == AuthUserStateEnum.INACTIVE) {
			throw new IllegalDelegateException("代理用户的帐号已失效，无法设为您的代理人！！");
		} else if (AuthUtils.getUser().equals(toUser)) {
			throw new IllegalDelegateException("不能将身份设为自己的代理人！！");
		}
		if (this.authUserDelegateService.exists(toUser, AuthUtils.getUser())) {
			throw new IllegalDelegateException(
					String.format("循环委托：您已是用户 %s 的代理人，不允许再将身份委托给他（她）！", toUser.getNickName()));
		}
		if (this.authUserDelegateService.exists(AuthUtils.getUser(), toUser)) {
			throw new IllegalDelegateException(String.format("不可以将身份重复委托给同一同户！ID：%d",toUserId));
		}

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
	public String switchTo(@PathVariable("toUserId") Integer toUserId)
			throws DataNotFoundException, IllegalDelegateException {
		AuthUser toUser = new AuthUser(toUserId);
		toUser = authUserService.findOne(toUser);
		if (null == toUser) {
			throw new DataNotFoundException("代理用户不存在！ID：" + toUserId);
		} else if (toUser.getState() == AuthUserStateEnum.LOCKED) {
			throw new IllegalDelegateException("委托者的帐号已被锁，请与系统管理员联系！！");
		} else if (toUser.getState() == AuthUserStateEnum.EXPIRED) {
			throw new IllegalDelegateException("委托者的帐号已过期，请与系统管理员联系！！");
		} else if (toUser.getState() == AuthUserStateEnum.INACTIVE) {
			throw new IllegalDelegateException("委托者的帐号已失效，请与系统管理员联系！！");
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
