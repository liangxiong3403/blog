package org.liangxiong.blog.controller.admin;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.QueryParam;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.view.RestResponse;
import org.liangxiong.blog.controller.BaseController;
import org.liangxiong.blog.dto.MetaDto;
import org.liangxiong.blog.dto.Types;
import org.liangxiong.blog.exception.TipException;
import org.liangxiong.blog.init.TaleConst;
import org.liangxiong.blog.service.MetasService;
import org.liangxiong.blog.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by biezhi on 2017/2/21.
 */
@Controller("admin/category")
public class CategoryController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Inject
    private MetasService metasService;

    @Inject
    private SiteService siteService;

    @Route(value = "", method = HttpMethod.GET)
    public String index(Request request) {
        List<MetaDto> categories = siteService.getMetas(Types.RECENT_META, Types.CATEGORY, TaleConst.MAX_POSTS);
        List<MetaDto> tags = siteService.getMetas(Types.RECENT_META, Types.TAG, TaleConst.MAX_POSTS);
        request.attribute("categories", categories);
        request.attribute("tags", tags);
        return "admin/category";
    }

    @Route(value = "save", method = HttpMethod.POST)
    @JSON
    public RestResponse saveCategory(@QueryParam String cname, @QueryParam Integer mid) {
        try {
            metasService.saveMeta(Types.CATEGORY, cname, mid);
            siteService.cleanCache(Types.C_STATISTICS);
        } catch (Exception e) {
            String msg = "分类保存失败";
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
    public RestResponse delete(@QueryParam int mid) {
        try {
            metasService.delete(mid);
            siteService.cleanCache(Types.C_STATISTICS);
        } catch (Exception e) {
            String msg = "删除失败";
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
