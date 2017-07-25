package com.qi.chat.common.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.qi.chat.common.bean.User;
import com.qi.chat.common.dao.UserDao;
import com.qi.chat.common.service.UserServices;

@Controller("userServices")
public class UserServicesImpl implements UserServices {
	@Resource
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getUser(String uname) throws SQLException {
		return userDao.queryObject(uname);
	}

	@Override
	public List<User> list() throws SQLException {
		return userDao.queryList();
	}

	public void insert(User user) {
		userDao.insert(user);
	}
}
