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
@Table(name = "t_logs")
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String action;

    private String data;

    private Integer author_id;

    private String ip;

    private Integer created;

}