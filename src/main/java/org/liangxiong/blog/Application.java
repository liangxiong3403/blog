package org.liangxiong.blog;

import org.liangxiong.blog.init.TaleLoader;

import static com.blade.Blade.$;

public class Application {

    public static void main(String[] args) throws Exception {
        TaleLoader.init();
        $().start(Application.class);
    }

}