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
@Table(name = "t_comments", pk = "coid")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer coid;

    private Integer cid;

    private Integer created;

    private String author;

    private Integer author_id;

    private Integer owner_id;

    private String mail;

    private String url;

    private String ip;

    private String agent;

    private String content;

    private String type;

    private String status;

    private Integer parent;

}