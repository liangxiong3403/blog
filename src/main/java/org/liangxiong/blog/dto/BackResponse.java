package org.liangxiong.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liangxiong
 * @Description
 */
@Getter
@Setter
public class BackResponse implements Serializable {

    private String attach_path;

    private String theme_path;

    private String sql_path;

}
