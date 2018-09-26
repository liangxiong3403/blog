package org.liangxiong.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.liangxiong.blog.model.Comments;

import java.util.List;

/**
 * @author liangxiong
 * @Description
 */
@Getter
@Setter
public class Comment extends Comments {

    private int levels;

    private List<Comments> children;

    public Comment(Comments comments) {
        setAuthor(comments.getAuthor());
        setMail(comments.getMail());
        setCoid(comments.getCoid());
        setAuthor_id(comments.getAuthor_id());
        setUrl(comments.getUrl());
        setCreated(comments.getCreated());
        setAgent(comments.getAgent());
        setIp(comments.getIp());
        setContent(comments.getContent());
        setOwner_id(comments.getOwner_id());
        setCid(comments.getCid());
    }

}
