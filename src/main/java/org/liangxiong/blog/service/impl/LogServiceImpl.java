package org.liangxiong.blog.service.impl;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;
import org.liangxiong.blog.init.TaleConst;
import org.liangxiong.blog.model.Logs;
import org.liangxiong.blog.service.LogService;
import org.liangxiong.blog.utils.DateUtil;

import java.util.List;

/**
 * @author liangxiong
 * @Description
 */
@Service
public class LogServiceImpl implements LogService {

    @Inject
    private ActiveRecord activeRecord;

    @Override
    public void save(String action, String data, String ip, Integer author_id) {
        Logs logs = new Logs();
        logs.setAction(action);
        logs.setData(data);
        logs.setIp(ip);
        logs.setAuthor_id(author_id);
        logs.setCreated(DateUtil.getUTC8Time());
        activeRecord.insert(logs);
    }

    @Override
    public List<Logs> getLogs(int page, int limit) {
        if (page <= 0) {
            page = 1;
        }

        if (limit < 1 || limit > TaleConst.MAX_POSTS) {
            limit = 10;
        }
        Paginator<Logs> logsPaginator = activeRecord.page(new Take(Logs.class).page(page, limit, "id desc"));
        return logsPaginator.getList();
    }
}
