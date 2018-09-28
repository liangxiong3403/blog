package org.liangxiong.blog.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.QueryParam;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.http.wrapper.Session;
import com.blade.mvc.view.RestResponse;
import org.liangxiong.blog.dto.LogActions;
import org.liangxiong.blog.exception.TipException;
import org.liangxiong.blog.init.TaleConst;
import org.liangxiong.blog.model.Users;
import org.liangxiong.blog.service.LogService;
import org.liangxiong.blog.service.UsersService;
import org.liangxiong.blog.utils.DateUtil;
import org.liangxiong.blog.utils.TaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liangxiong
 * @Description 访问权限控制器
 */
@Controller("admin")
public class AuthController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Inject
    private UsersService usersService;

    @Inject
    private LogService logService;

    @Route(value = "login", method = HttpMethod.GET)
    public String login(Response response) {
        if (null != this.user()) {
            response.go("/admin/index");
            return null;
        }
        return "admin/login";
    }

    @Route(value = "login", method = HttpMethod.POST)
    @JSON
    public RestResponse doLogin(@QueryParam String username,
                                @QueryParam String password,
                                @QueryParam String remeber_me,
                                Request request,
                                Session session, Response response) {

        Integer error_count = cache.get("login_error_count");
        try {
            Users user = usersService.login(username, password);
            session.attribute(TaleConst.LOGIN_SESSION_KEY, user);
            if (StringKit.isNotBlank(remeber_me)) {
                TaleUtils.setCookie(response, user.getUid());
            }
            Users temp = new Users();
            temp.setUid(user.getUid());
            temp.setLogged(DateUtil.getUTC8Time());
            usersService.update(temp);
            logService.save(LogActions.LOGIN, null, request.address(), user.getUid());
        } catch (Exception e) {
            error_count = null == error_count ? 1 : error_count + 1;
            if (null != error_count && error_count > 3) {
                return RestResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
            }
            cache.set("login_error_count", error_count, 10 * 60);
            String msg = "登录失败";
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
