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
import org.liangxiong.blog.dto.Types;
import org.liangxiong.blog.exception.TipException;
import org.liangxiong.blog.model.Metas;
import org.liangxiong.blog.service.MetasService;
import org.liangxiong.blog.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by biezhi on 2017/2/21.
 */
@Controller("admin/links")
public class LinksController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinksController.class);

    @Inject
    private MetasService metasService;

    @Inject
    private SiteService siteService;

    @Route(value = "", method = HttpMethod.GET)
    public String index(Request request) {
        List<Metas> metass = metasService.getMetas(Types.LINK);
        request.attribute("links", metass);
        return "admin/links";
    }

    @Route(value = "save", method = HttpMethod.POST)
    @JSON
    public RestResponse saveLink(@QueryParam String title, @QueryParam String url,
                                 @QueryParam String logo, @QueryParam Integer mid,
                                 @QueryParam(value = "sort", defaultValue = "0") int sort) {
        try {
            Metas metas = new Metas();
            metas.setName(title);
            metas.setSlug(url);
            metas.setDescription(logo);
            metas.setSort(sort);
            metas.setType(Types.LINK);
            if (null != mid) {
                metas.setMid(mid);
                metasService.update(metas);
            } else {
                metasService.saveMeta(metas);
            }
            siteService.cleanCache(Types.C_STATISTICS);
        } catch (Exception e) {
            String msg = "友链保存失败";
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
            String msg = "友链删除失败";
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
