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
@Table(name = "t_relationships", pk = "mid")
public class Relationships implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;

    private Integer mid;

}