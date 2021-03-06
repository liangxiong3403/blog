package org.liangxiong.blog.init;

import com.blade.Blade;
import com.blade.config.BConfig;
import com.blade.context.WebContextListener;
import com.blade.ioc.BeanProcessor;
import com.blade.ioc.Ioc;
import com.blade.ioc.annotation.Inject;
import com.blade.kit.FileKit;
import com.blade.kit.StringKit;
import com.blade.mvc.view.ViewSettings;
import com.blade.mvc.view.template.JetbrickTemplateEngine;
import jetbrick.template.JetGlobalContext;
import jetbrick.template.resolver.GlobalResolver;
import org.liangxiong.blog.controller.AttachController;
import org.liangxiong.blog.controller.BaseController;
import org.liangxiong.blog.dto.Types;
import org.liangxiong.blog.ext.AdminCommons;
import org.liangxiong.blog.ext.Commons;
import org.liangxiong.blog.ext.JetTag;
import org.liangxiong.blog.ext.Theme;
import org.liangxiong.blog.service.OptionsService;
import org.liangxiong.blog.service.SiteService;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liangxiong
 * @Description 初始化
 */
public class WebContext implements BeanProcessor, WebContextListener {

    @Inject
    private OptionsService optionsService;

    private static boolean dbIsOk = false;

    @Override
    public void init(BConfig bConfig, ServletContext sec) {
        JetbrickTemplateEngine templateEngine = new JetbrickTemplateEngine();

        List<String> macros = new ArrayList<>(8);
        macros.add("/comm/macros.html");
        // 扫描主题下面的所有自定义宏
        String themeDir = AttachController.CLASSPATH + "templates/themes";
        try {
            themeDir = new URI(themeDir).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File[] dir = new File(themeDir).listFiles();
        for (File f : dir) {
            if (f.isDirectory() && FileKit.exist(f.getPath() + "/macros.html")) {
                String macroName = "/themes/" + f.getName() + "/macros.html";
                macros.add(macroName);
            }
        }
        StringBuffer macroBuf = new StringBuffer();
        macros.forEach(s -> macroBuf.append(',').append(s));
        templateEngine.addConfig("jetx.import.macros", macroBuf.substring(1));

        GlobalResolver resolver = templateEngine.getGlobalResolver();
        resolver.registerFunctions(Commons.class);
        resolver.registerFunctions(Theme.class);
        resolver.registerFunctions(AdminCommons.class);
        resolver.registerTags(JetTag.class);

        JetGlobalContext context = templateEngine.getGlobalContext();
        context.set("version", bConfig.config().get("app.version", "v1.0"));

        TaleConst.MAX_FILE_SIZE = bConfig.config().getInt("app.max-file-size", 20480);

        ViewSettings.$().templateEngine(templateEngine);
        if (dbIsOk) {
            TaleConst.OPTIONS.addAll(optionsService.getOptions());
            TaleConst.INSTALL = TaleConst.OPTIONS.getInt("site_is_install", 0) == 1;
            BaseController.THEME = "themes/" + Commons.site_option("site_theme");

            String ips = TaleConst.OPTIONS.get(Types.BLOCK_IPS, "");
            if (StringKit.isNotBlank(ips)) {
                TaleConst.BLOCK_IPS.addAll(Arrays.asList(StringKit.split(ips, ",")));
            }

            Commons.setSiteService(Blade.$().ioc().getBean(SiteService.class));
        }
        if (FileKit.exist(AttachController.CLASSPATH + "install.lock")) {
            TaleConst.INSTALL = true;
        }
        TaleConst.BCONF = bConfig.config();
    }

    @Override
    public void register(Ioc ioc) {
        dbIsOk = TaleJdbc.injection(ioc);
    }

}