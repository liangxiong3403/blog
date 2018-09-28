package org.liangxiong.blog;

import org.junit.Test;
import org.liangxiong.blog.utils.DateUtil;

import java.text.ParseException;

/**
 * @author liangxiong
 * @Description
 * @Date 2018-09-28 15:39
 */
public class TimeTest {

    @Test
    public void testGetGMT8() throws ParseException {
        System.out.println(DateUtil.getUTC8Time());
    }
}
