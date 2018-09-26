package org.liangxiong.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liangxiong
 * @Description 后台统计
 */
@Getter
@Setter
public class Statistics implements Serializable {

    /**
     * 文章数量
     */
    private int articles;
    /**
     * 页面数量
     */
    private int pages;
    /**
     * 评论数量
     */
    private int comments;
    /**
     * 文章分类数量
     */
    private int categories;
    /**
     * 标签数量
     */
    private int tags;
    /**
     * 连接数量
     */
    private int links;
    /**
     * 附件数量
     */
    private int attachs;

}
