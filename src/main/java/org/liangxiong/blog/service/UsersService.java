package org.liangxiong.blog.service;

import org.liangxiong.blog.model.Users;

/**
 * @author liangxiong
 * @Description
 */
public interface UsersService {

    /**
     * 根据用户id查询
     *
     * @param uid
     * @return
     */
    Users byId(Integer uid);

    /**
     * 更新用户信息
     *
     * @param users
     */
    void update(Users users);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    Users login(String username, String password);
}
