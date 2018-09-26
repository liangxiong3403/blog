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
@Table(name = "t_contents", pk = "cid")
public class Contents implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;

    private String title;

    private String slug;

    private Integer created;

    private Integer modified;

    private String content;

    private Integer author_id;

    private Integer hits;

    private String type;

    private String tags;

    private String categories;

    private String status;

    private Integer comments_num;

    private Boolean allow_comment;

    private Boolean allow_ping;

    private Boolean allow_feed;

}