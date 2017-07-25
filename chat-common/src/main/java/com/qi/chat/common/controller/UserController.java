package com.qi.chat.common.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qi.chat.common.bean.User;
import com.qi.chat.common.service.UserServices;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserServices userServices;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(User userInfo, Model model) throws SQLException {
		User user = userServices.getUser(userInfo.name);
		if (user != null) {
			if (user.password.equals(userInfo.password)) {
				return "redirect:/user/list";
			}
		}
		model.addAttribute("error", "用户名或密码错误");
		return "login";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model m) throws SQLException {
		List<User> list = userServices.list();
		m.addAttribute("list", list);
		return "list";
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

}