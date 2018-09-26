package org.liangxiong.blog.controller.admin;

import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.view.RestResponse;
import org.liangxiong.blog.controller.BaseController;
import org.liangxiong.blog.dto.LogActions;
import org.liangxiong.blog.dto.Types;
import org.liangxiong.blog.exception.TipException;
import org.liangxiong.blog.init.TaleConst;
import org.liangxiong.blog.model.Contents;
import org.liangxiong.blog.model.Users;
import org.liangxiong.blog.service.ContentsService;
import org.liangxiong.blog.service.LogService;
import org.liangxiong.blog.service.MetasService;
import org.liangxiong.blog.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by biezhi on 2017/2/21.
 */
@Controller("admin/page")
public class PageController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);

    @Inject
    private ContentsService contentsService;

    @Inject
    private MetasService metasService;

    @Inject
    private LogService logService;

    @Inject
    private SiteService siteService;

    @Route(value = "", method = HttpMethod.GET)
    public String index(Request request) {
        Paginator<Contents> contentsPaginator = contentsService.getArticles(new Take(Contents.class).eq("type", Types.PAGE).page(1, TaleConst.MAX_POSTS, "created desc"));
        request.attribute("articles", contentsPaginator);
        return "admin/page_list";
    }

    @Route(value = "new", method = HttpMethod.GET)
    public String newPage(Request request) {
        return "admin/page_edit";
    }

    @Route(value = "/:cid", method = HttpMethod.GET)
    public String editPage(@PathParam String cid, Request request) {
        Contents contents = contentsService.getContents(cid);
        request.attribute("contents", contents);
        return "admin/page_edit";
    }

    @Route(value = "publish", method = HttpMethod.POST)
    @JSON
    public RestResponse publishPage(@QueryParam String title, @QueryParam String content,
                                    @QueryParam String status, @QueryParam String slug,
                                    @QueryParam Integer allow_comment, @QueryParam Integer allow_ping) {

        Users users = this.user();
        Contents contents = new Contents();
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setType(Types.PAGE);
        if (null != allow_comment) {
            contents.setAllow_comment(allow_comment == 1);
        }
        if (null != allow_ping) {
            contents.setAllow_ping(allow_ping == 1);
        }
        contents.setAuthor_id(users.getUid());

        try {
            contentsService.publish(contents);
            siteService.cleanCache(Types.C_STATISTICS);
        } catch (Exception e) {
            String msg = "页面发布失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }

    @Route(value = "modify", method = HttpMethod.POST)
    @JSON
    public RestResponse modifyArticle(@QueryParam Integer cid, @QueryParam String title,
                                      @QueryParam String content,
                                      @QueryParam String status, @QueryParam String slug,
                                      @QueryParam Integer allow_comment, @QueryParam Integer allow_ping) {

        Users users = this.user();
        Contents contents = new Contents();
        contents.setCid(cid);
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setType(Types.PAGE);
        if (null != allow_comment) {
            contents.setAllow_comment(allow_comment == 1);
        }
        if (null != allow_ping) {
            contents.setAllow_ping(allow_ping == 1);
        }
        contents.setAuthor_id(users.getUid());
        try {
            contentsService.updateArticle(contents);
        } catch (Exception e) {
            String msg = "页面编辑失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }

    @Route(value = "delete")
    @JSON
    public RestResponse delete(@QueryParam int cid, Request request) {
        try {
            contentsService.delete(cid);
            siteService.cleanCache(Types.C_STATISTICS);
            logService.save(LogActions.DEL_PAGE, cid+"", request.address(), this.getUid());
        } catch (Exception e) {
            String msg = "页面删除失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }
}
