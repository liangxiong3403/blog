package org.liangxiong.blog.ext;

import com.blade.kit.StringKit;
import jetbrick.template.runtime.JetTagContext;

import java.io.IOException;

/**
 * @author liangxiong
 * @Description 主题公共标签
 */
public class JetTag {

    public static void social(JetTagContext ctx, String name) throws IOException {
        String value = Commons.site_option("social_" + name);
        if (StringKit.isNotBlank(value)) {
            value = ctx.getBodyContent();
        }
        ctx.getWriter().print(value.toString());
    }

}
