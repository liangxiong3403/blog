package org.liangxiong.blog.service.impl;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;
import org.liangxiong.blog.model.Attach;
import org.liangxiong.blog.service.AttachService;
import org.liangxiong.blog.utils.DateUtil;

/**
 * @author liangxiong
 * @Description
 */
@Service
public class AttachServiceImpl implements AttachService {

    @Inject
    private ActiveRecord activeRecord;

    @Override
    public void save(String fname, String fkey, String ftype, Integer author) {
        Attach attach = new Attach();
        attach.setFname(fname);
        attach.setAuthor_id(author);
        attach.setFkey(fkey);
        attach.setFtype(ftype);
        attach.setCreated(DateUtil.getUTC8Time());
        activeRecord.insert(attach);
    }

    @Override
    public Attach byId(Integer id) {
        if (null != id) {
            return activeRecord.byId(Attach.class, id);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (null != id) {
            activeRecord.delete(Attach.class, id);
        }
    }

    @Override
    public Paginator<Attach> getAttachs(Take take) {
        if (null != take) {
            return activeRecord.page(take);
        }
        return null;
    }
}
