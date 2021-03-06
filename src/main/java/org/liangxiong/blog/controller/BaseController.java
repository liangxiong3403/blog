package org.liangxiong.blog.controller;

import com.blade.mvc.http.Request;
import org.liangxiong.blog.model.Users;
import org.liangxiong.blog.utils.MapCache;
import org.liangxiong.blog.utils.TaleUtils;

/**
 * @author liangxiong
 * @Description 基础Controller
 */
public abstract class BaseController {

    public static String THEME = "themes/default";

    protected MapCache cache = MapCache.single();

    public String render(String viewName) {
        return THEME + "/" + viewName;
    }

    public BaseController title(Request request, String title) {
        request.attribute("title", title);
        return this;
    }

    public BaseController keywords(Request request, String keywords) {
        request.attribute("keywords", keywords);
        return this;
    }

    public Users user() {
        return TaleUtils.getLoginUser();
    }

    public Integer getUid() {
        return this.user().getUid();
    }

    public String render_404() {
        return "/comm/error_404";
    }

}
