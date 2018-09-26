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
@Table(name = "t_metas", pk = "mid")
public class Metas implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer mid;

    private String name;

    private String slug;

    private String type;

    private String description;

    private Integer sort;

    private Integer parent;


}