/**
 * 系统用户控制器类
 */
package com.example.demo.auth.controller;

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

import com.example.demo.auth.AuthUserStateEnum;
import com.example.demo.auth.entity.AuthUser;
import com.example.demo.auth.service.AuthUserService;
import com.github.pagehelper.PageInfo;

/**
 * com.example.demo.auth.controller AuthUserController
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
	 * 系统用户列表
	 * 
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@GetMapping("")
	@RequiresPermissions("authUser:list")
	public String findAllUser(@RequestParam(defaultValue = "1") int pageNum, Model model) {
		PageInfo<AuthUser> page = this.authUserService.findAll(pageNum);
		model.addAttribute("page", page);
		return "auth/user/index";
	}

	/**
	 * 查看用户详情
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/{id}")
	@RequiresPermissions("authUser:detail")
	public String findUser(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		AuthUser user = this.authUserService.findById(id);
		if (null != user) {
			model.addAttribute("user", user);
		}
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
	 */
	@GetMapping("/{id}/edit")
	@RequiresPermissions("authUser:edit")
	public String editUser(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		AuthUser authUser = this.authUserService.findById(id);
		if (null != authUser) {
			model.addAttribute("authUser", authUser);
		}
		model.addAttribute("userStates", AuthUserStateEnum.values());
		return "auth/user/edit";
	}

	/**
	 * 保存用户信息
	 * 
	 * @param authUser
	 * @param request
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/save")
	@RequiresPermissions("authUser:save")
	public String saveUser(@Valid AuthUser authUser, HttpServletRequest request, BindingResult result, Model model) {
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
			this.authUserService.insert(authUser);
		}
		if ("update".equals(request.getParameter("action"))) {
			this.authUserService.update(authUser);
		}
		return "auth/user/save";
	}
	
	/**
	 * 修改登录密码
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@GetMapping("/{id}/change-psd")
	@RequiresPermissions("authUser:change-password")
	public String chanegPassword(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		AuthUser authUser = this.authUserService.findById(id);
		if (null != authUser) {
			model.addAttribute("authUser", authUser);
		}
		return "auth/user/change-psd";
	}

	/**
	 * 删除系统用户
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PostMapping("/{id}/del")
	@RequiresPermissions("authUser:delete")
	public String deleteUser(@PathVariable("id") Integer id, HttpServletRequest request) {
		this.authUserService.delete(id);
		return "redirect:/users";
	}

}
