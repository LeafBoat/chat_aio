package com.qi.chat.common.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.qi.chat.common.bean.User;

public interface UserDao {

	public User queryObject(String parameter) throws SQLException;

	public List<User> queryList() throws SQLException;

	public void insert(User user);

	@Transactional
	public void addObj(String parameter) throws SQLException;

	@Transactional
	public void editObj(String parameter) throws SQLException;

	@Transactional
	public void del(String parameter) throws SQLException;

}
