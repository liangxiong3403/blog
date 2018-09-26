package org.liangxiong.blog.interceptor;

import com.blade.ioc.annotation.Inject;
import com.blade.kit.IPKit;
import com.blade.kit.StringKit;
import com.blade.kit.UUID;
import com.blade.mvc.annotation.Intercept;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.interceptor.Interceptor;
import org.liangxiong.blog.dto.Types;
import org.liangxiong.blog.init.TaleConst;
import org.liangxiong.blog.model.Users;
import org.liangxiong.blog.service.UsersService;
import org.liangxiong.blog.utils.MapCache;
import org.liangxiong.blog.utils.TaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liangxiong
 * @Description 拦截器
 */
@Intercept
public class BaseInterceptor implements Interceptor {

    private static final Logger LOGGE = LoggerFactory.getLogger(BaseInterceptor.class);

    @Inject
    private UsersService usersService;

    private MapCache cache = MapCache.single();

    @Override
    public boolean before(Request request, Response response) {

        String uri = request.uri();
        String ip = IPKit.getIpAddrByRequest(request.raw());

        // 禁止该ip访问
        if (TaleConst.BLOCK_IPS.contains(ip)) {
            response.text("You have been banned, brother");
            return false;
        }

        LOGGE.info("UserAgent: {}", request.userAgent());
        LOGGE.info("用户访问地址: {}, 来路地址: {}", uri, ip);

        if (!TaleConst.INSTALL && !uri.startsWith("/install")) {
            response.go("/install");
            return false;
        }

        if (TaleConst.INSTALL) {
            Users user = TaleUtils.getLoginUser();
            if (null == user) {
                Integer uid = TaleUtils.getCookieUid(request);
                if (null != uid) {
                    user = usersService.byId(Integer.valueOf(uid));
                    request.session().attribute(TaleConst.LOGIN_SESSION_KEY, user);
                }
            }

            if (uri.startsWith("/admin") && !uri.startsWith("/admin/login")) {
                if (null == user) {
                    response.go("/admin/login");
                    return false;
                }
                request.attribute("plugin_menus", TaleConst.plugin_menus);
            }
        }
        String method = request.method();
        if (method.equals("GET")) {
            String csrf_token = UUID.UU64();
            // 默认存储30分钟
            cache.hset(Types.CSRF_TOKEN, csrf_token, uri, TaleConst.BCONF.getInt("app.csrf-token-timeout", 30) * 60);
            request.attribute("_csrf_token", csrf_token);
        }
        return true;
    }


    @Override
    public boolean after(Request request, Response response) {
        String _csrf_token = request.query("_csrf_token");
        if (StringKit.isNotBlank(_csrf_token)) {
            // 移除本次token
            cache.hdel(Types.CSRF_TOKEN, _csrf_token);
        }
        return true;
    }

}
