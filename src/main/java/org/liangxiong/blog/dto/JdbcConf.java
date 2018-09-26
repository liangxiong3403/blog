package org.liangxiong.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liangxiong
 * @Description
 */
@Getter
@Setter
@AllArgsConstructor
public class JdbcConf {

    private String db_host;

    private String db_name;

    private String db_user;

    private String db_pass;

}
