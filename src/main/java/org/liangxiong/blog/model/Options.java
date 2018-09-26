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
@Table(name = "t_options", pk = "name")
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String value;

    private String description;

}