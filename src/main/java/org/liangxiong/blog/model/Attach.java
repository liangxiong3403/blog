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
@Table(name = "t_attach")
public class Attach implements Serializable {

    private Integer id;

    private String fname;

    private String ftype;

    private String fkey;

    private Integer author_id;

    private Integer created;

}
