/**
 * 
 */
package com.example.demo.app.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * com.example.demo.app.controller HomeController
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
@Controller
public class HomeController {
	
	@GetMapping("/")
	public String index(HttpServletRequest request, Map<String, Object> map) {
		return "index";
	}

}
