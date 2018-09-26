package org.liangxiong.blog.service;

import org.liangxiong.blog.model.Logs;

import java.util.List;

/**
 * @author liangxiong
 * @Description
 */
public interface LogService {

    /**
     * 记录日志
     *
     * @param action
     * @param data
     * @param ip
     * @param author_id
     */
    void save(String action, String data, String ip, Integer author_id);

    /**
     * 读取日志
     *
     * @param page
     * @param limit
     * @return
     */
    List<Logs> getLogs(int page, int limit);
}
