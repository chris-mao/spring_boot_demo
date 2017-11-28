package com.jrsoft.auth.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.entity.AuthUserDelegate;
import com.jrsoft.auth.service.AuthUserDelegateService;
import com.jrsoft.auth.service.AuthUserService;
import com.jrsoft.auth.utils.AuthUtils;

/**
 * <p>
 * 系统用户控制器类，提供系统用户维护页面入口
 * </p>
 * 
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/users")
public class AuthUserController {

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
	 * 系统用户页面访问入口
	 * 
	 */
	@GetMapping({ "", "/", "/index" })
	@RequiresPermissions("authUser:list")
	public String userList() {
		return "auth/user";
	}
	
	/**
	 * 身份委托首页，显示“我的代理”与“我的委托”列表
	 * 当用户切换到委托人身份时，不再显示委托候选人列表，不允许添加、删除委托人自己设置的委托关系
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/delegate")
	@RequiresPermissions("authUser:delegate")
	public String delegateList(@RequestParam(defaultValue = "1") int page, Model model) {
		// System.out.println("当前身份是： " + AuthUtils.getUser());
		// System.out.println("前一个身份是：" + AuthUtils.getPreviousUser());
		//当前身份
		model.addAttribute("currentUser", AuthUtils.getCurrentUser());
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
}
