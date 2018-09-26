package org.liangxiong.blog;

import org.liangxiong.blog.init.TaleLoader;

import static com.blade.Blade.$;

/**
 * @author liangxiong
 * @Description 项目启动类
 */
public class Application {

    public static void main(String[] args) {
        TaleLoader.init();
        $().start(Application.class);
    }

}