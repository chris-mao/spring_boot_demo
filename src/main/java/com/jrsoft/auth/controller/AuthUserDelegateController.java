/**
 * 
 */
package com.jrsoft.auth.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 身份代理控控制器类
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 * 
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/delegate")
public class AuthUserDelegateController {

	/**
	 * 
	 */
	@Autowired
	private AuthUserService authUserService;

	/**
	 * 
	 */
	@Autowired
	private AuthUserDelegateService authUserDelegateService;

	/**
	 * 身份委托首页，显示“我的委托”与“我的受托”列表，亦显示出所有委托候选人列表方便用户将其设为委托人
	 * 当用户切换到委托人身份时，不再显示委托候选人列表，不允许添加、删除委托人自己设置的委托关系
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("authUser:delegate")
	public String delegateList(@RequestParam(defaultValue = "1") int page, Model model) {
		// System.out.println("当前身份是： " + AuthUtils.getUser());
		// System.out.println("前一个身份是：" + AuthUtils.getPreviousUser());
		// 前一个身份
		model.addAttribute("previousUser", AuthUtils.getPreviousUser());
		// 当前用户的代理人（受托人）
		List<AuthUserDelegate> agents = this.authUserDelegateService.findAllByFromUser(AuthUtils.getCurrentUser());
		model.addAttribute("agents", agents);
		// 当前用户的委托人
		List<AuthUserDelegate> delegates = this.authUserDelegateService.findAllByToUser(AuthUtils.getCurrentUser());
		model.addAttribute("delegates", delegates);

		// 只有回到自己的身份的时候，再显示委托候选人供其选择；
		// 切换到委托人身份时，不允许替委托人设置委托
		if (AuthUtils.getPreviousUser() == null) {
			List<AuthUser> candidates = authUserService.findAll(true);
			candidates.remove(AuthUtils.getCurrentUser());
			// 将我已委托的用户从委托候选列表中移除
			for (AuthUserDelegate user : agents) {
				// System.out.println("从列表中移除我的受托人: " + user.getToUser());
				candidates.remove(user.getToUser());
			}
			// 将已委托给我的用户从委托候选列表中移除
			for (AuthUserDelegate user : delegates) {
				// System.out.println("从列表中移除我的委托人: " + user.getFromUser());
				candidates.remove(user.getFromUser());
			}
			model.addAttribute("candidates", candidates);
		}
		return "auth/delegate";
	}

	/**
	 * 将身份委托给指定的用户 如果被委托人不存在，则抛出{@link DataNotFoundException}异常
	 * 如果被委托人的帐号被锁、过期或是失效，则抛出{@link IllegalDelegateException}异常
	 * 如果出现委托给自己或是循环委托，也会抛出{@link IllegalDelegateException}异常
	 * 
	 * @param toUserId
	 * @return
	 * @throws DataNotFoundException
	 * @throws IllegalDelegateException
	 */
	@GetMapping("/grant/{toUserId}")
	@RequiresPermissions("authUser:delegate")
	public String grant(@PathVariable("toUserId") Integer toUserId)
			throws DataNotFoundException, IllegalDelegateException {
		AuthUser toUser = new AuthUser(toUserId);
		toUser = authUserService.findOne(toUser);
		if (null == toUser) {
			throw new DataNotFoundException("代理用户不存在！ID：" + toUserId);
		} else if (toUser.getState() == AuthUserStateEnum.LOCKED) {
			throw new IllegalDelegateException(String.format("代理用户 %s 的帐号已被锁，无法设为您的代理人！！", toUser.getNickName()));
		} else if (toUser.getState() == AuthUserStateEnum.EXPIRED) {
			throw new IllegalDelegateException(String.format("代理用户 %s 的帐号已过期，无法设为您的代理人！！", toUser.getNickName()));
		} else if (toUser.getState() == AuthUserStateEnum.INACTIVE) {
			throw new IllegalDelegateException(String.format("代理用户 %s 的帐号已失效，无法设为您的代理人！！", toUser.getNickName()));
		} else if (AuthUtils.getCurrentUser().equals(toUser)) {
			throw new IllegalDelegateException("不能将身份设为自己的代理人！！");
		}
		if (this.authUserDelegateService.exists(toUser, AuthUtils.getCurrentUser())) {
			throw new IllegalDelegateException(
					String.format("循环委托：您已是用户 %s 的代理人，不允许再将身份委托给他（她）！", toUser.getNickName()));
		}
		if (this.authUserDelegateService.exists(AuthUtils.getCurrentUser(), toUser)) {
			throw new IllegalDelegateException(String.format("不可以将身份重复委托给同一同户！ID：%d", toUserId));
		}

		this.authUserDelegateService.grantDelegate(AuthUtils.getCurrentUser(), toUser);
		return "redirect:/delegate";
	}

	/**
	 * 取消委托关系
	 * 
	 * @param toUserId
	 * @return
	 */
	@GetMapping("/revoke/{toUserId}")
	@RequiresPermissions("authUser:delegate")
	public String revoke(@PathVariable("toUserId") Integer toUserId) {
		AuthUser toUser = new AuthUser(toUserId);
		this.authUserDelegateService.revokeDelegate(AuthUtils.getCurrentUser(), toUser);
		return "redirect:/delegate";
	}

	/**
	 * 切换身份
	 * 
	 * 切换到委托人身份 如果委托人不存在，则抛出{@link DataNotFoundException}异常
	 * 如果委托人的帐号被锁、过期或是失效，则抛出{@link IllegalDelegateException}异常
	 * 
	 * @param toUserId
	 * @return
	 * @throws DataNotFoundException
	 * @throws IllegalDelegateException
	 */
	@GetMapping("/switchTo/{toUserId}")
	@RequiresPermissions("authUser:delegate")
	public String switchTo(@PathVariable("toUserId") Integer toUserId)
			throws DataNotFoundException, IllegalDelegateException {
		AuthUser toUser = new AuthUser(toUserId);
		toUser = authUserService.findOne(toUser);
		if (null == toUser) {
			throw new DataNotFoundException("委托者帐号不存在！ID：" + toUserId);
		} else if (toUser.getState() == AuthUserStateEnum.LOCKED) {
			throw new IllegalDelegateException(String.format("委托者 %s 的帐号已被锁，请与系统管理员联系！！", toUser.getNickName()));
		} else if (toUser.getState() == AuthUserStateEnum.EXPIRED) {
			throw new IllegalDelegateException(String.format("委托者 %s 的帐号已过期，请与系统管理员联系！！", toUser.getNickName()));
		} else if (toUser.getState() == AuthUserStateEnum.INACTIVE) {
			throw new IllegalDelegateException(String.format("委托者 %s 的帐号已失效，请与系统管理员联系！！", toUser.getNickName()));
		}

		SimplePrincipalCollection spc = new SimplePrincipalCollection(toUser, "");
		AuthUtils.getCredential().runAs(spc);
		System.out.println(AuthUtils.getCurrentUser());
		return "redirect:/delegate";
	}

	/**
	 * 返回身份
	 * 
	 * 身份委托是以栈的形式保存，所以在返回身份时，只能返回上一个身份，直至栈为空为止
	 * 
	 * @return
	 */
	@GetMapping("/switchBack")
	@RequiresPermissions("authUser:delegate")
	public String switchBack() {
		if (AuthUtils.getCredential().isRunAs()) {
			AuthUtils.getCredential().releaseRunAs();
		}
		return "redirect:/delegate";
	}
}
