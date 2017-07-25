package com.qi.chat.common.service;

import java.sql.SQLException;
import java.util.List;

import com.qi.chat.common.bean.User;

public interface UserServices {
	/**
	 * 通过id查询User
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	User getUser(String id) throws SQLException;

	/**
	 * 列出所有用户
	 * @return
	 * @throws SQLException
	 */
	List<User> list() throws SQLException;
}
