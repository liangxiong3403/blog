package org.liangxiong.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.liangxiong.blog.model.Contents;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liangxiong
 * @Description
 */
@Getter
@Setter
public class Archive implements Serializable {

    private String date_str;

    private Date date;

    private String count;

    private List<Contents> articles;

}
