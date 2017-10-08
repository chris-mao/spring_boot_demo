package com.jrsoft.auth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jrsoft.app.exception.DataNotFoundException;
import com.jrsoft.auth.AuthUserStateEnum;
import com.jrsoft.auth.entity.AuthUser;
import com.jrsoft.auth.service.AuthUserService;

/**
 * 系统用户控制器类
 * 
 * com.jrsoft.auth.controller AuthUserController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/users")
public class AuthUserController {

	@Resource
	private AuthUserService authUserService;

	/**
	 * 按ID查询用户，如果用户不存在则抛出DataNotFoundException异常
	 * 
	 * @param id
	 * @return AuthUser
	 * @throws DataNotFoundException
	 */
	private AuthUser findUser(Integer id) throws DataNotFoundException {
		AuthUser u = new AuthUser();
		u.setUserId(id);
		AuthUser user = this.authUserService.findOne(u);
		if (null == user) {
			throw new DataNotFoundException();
		}
		return user;
	}

	/**
	 * 系统用户列表
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({ "", "/index" })
	@RequiresPermissions("authUser:list")
	public String userList(@RequestParam(defaultValue = "1") int page, Model model) {
		PageInfo<AuthUser> users = this.authUserService.findAll(page);
		model.addAttribute("page", users);
		return "auth/user/index";
	}

	/**
	 * 查看用户详情
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("authUser:detail")
	public String viewUser(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
		model.addAttribute("user", findUser(id));
		return "auth/user/detail";
	}

	/**
	 * 新增系统用户
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/new")
	@RequiresPermissions("authUser:new")
	public String newUser(Model model) {
		model.addAttribute("authUser", new AuthUser());
		return "auth/user/new";
	}

	/**
	 * 编辑用户信息
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}/edit")
	@RequiresPermissions("authUser:edit")
	public String editUser(@PathVariable("id") Integer id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		AuthUser user = findUser(id);

		// 将用户状态枚举值转为字符串
		List<String> states = new ArrayList<String>();
		for (AuthUserStateEnum userState : AuthUserStateEnum.values()) {
			states.add(userState.getText().toUpperCase());
		}

		model.addAttribute("authUser", user);
		model.addAttribute("states", states);
		model.addAttribute("userStates", AuthUserStateEnum.values());
		return "auth/user/edit";
	}

	/**
	 * 保存用户信息，新增用户之前会判断用户名是否已存在
	 * 
	 * 这里有个坑：参数BindingResult result 必须根在要验证的对象参数之后，否则会抛出异常！
	 * 
	 * @param authUser
	 * @param result
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping("/save")
	@RequiresPermissions("authUser:save")
	public String saveUser(@Valid AuthUser authUser, BindingResult result, HttpServletRequest request, Model model) {
		model.addAttribute("authUser", authUser);
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error.getCode() + "---" + error.getArguments() + "---" + error.getDefaultMessage());
			}
			if ("insert".equals(request.getParameter("action"))) {
				return "auth/user/new";
			}
			if ("update".equals(request.getParameter("action"))) {
				return "auth/user/edit";
			}
		}
		if ("insert".equals(request.getParameter("action"))) {
			if (this.authUserService.findOne(authUser) != null) { // 用户名已存在
				result.rejectValue("userName", "duplicate", "此用户名已被使用，请使用其他用户名");
				return "auth/user/new";
			}
			this.authUserService.insert(authUser);
		}
		if ("update".equals(request.getParameter("action"))) {
			this.authUserService.update(authUser);
			return "redirect:/users/" + authUser.getUserId();
		}
		return "auth/user/save";
	}

	/**
	 * 删除系统用户
	 * 
	 * todo: 删除之前需要判断用户是否被关联到相应的客户或是员工
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping("/{id}/del")
	@RequiresPermissions("authUser:delete")
	public String deleteUser(@PathVariable("id") Integer id, HttpServletRequest request) {
		this.authUserService.delete(id);
		return "redirect:/users";
	}

	/**
	 * 修改登录密码
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws DataNotFoundException
	 */
	@GetMapping("/{id}/change-psd")
	@RequiresPermissions("authUser:change-password")
	public String chanegPassword(@PathVariable("id") Integer id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		model.addAttribute("authUser", findUser(id));
		return "auth/user/change-psd";
	}

	/**
	 * 保存修改后的密码，如果修改成功返跳转到用户详情页面，否则还留在当前页面
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("/{id}/change-psd")
	public String savePassword(@PathVariable("id") Integer id, HttpServletRequest request, Model model)
			throws DataNotFoundException {
		String oldPassword = request.getParameter("old_password");
		String newPassword = request.getParameter("password");
		if (true == this.authUserService.changePassword(id, oldPassword, newPassword)) {
			return "redirect:/users/" + id;
		}
		
		model.addAttribute("msg", "旧密码不正确，请重新输入");
		return chanegPassword(id, request, model);
	}
	
	@GetMapping("/json")
	@ResponseBody
	public List<AuthUser> jsonData() {
		return this.authUserService.findAll();
	}

}
