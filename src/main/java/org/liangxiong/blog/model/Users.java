package org.liangxiong.blog.model;

import com.blade.jdbc.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liangxiong
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_users", pk = "uid")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private String username;

    private String password;

    private String email;

    private String home_url;

    private String screen_name;

    private Integer created;

    private Integer activated;

    private Integer logged;

    private String group_name;

}