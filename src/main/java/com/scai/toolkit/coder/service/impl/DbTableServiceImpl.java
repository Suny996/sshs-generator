package com.scai.toolkit.coder.service.impl;

import org.springframework.stereotype.Service;

import com.scai.core.base.service.impl.BaseServiceImpl;
import com.scai.toolkit.coder.model.DbTable;
import com.scai.toolkit.coder.service.IDbTableService;

import java.util.List;
import java.util.Map;

/**
 * 类名称：CreateCodeService 代码生成器
 * 
 * @author Suny
 * @date 2015年11月24日
 * 
 * @version
 */
@Service("dbTableService")
public class DbTableServiceImpl extends BaseServiceImpl<DbTable> implements IDbTableService {
    /**
     * 查询列表
     *
     * @param parameter
     * @return Message
     */
    @Override
    public List<DbTable> queryList(Map<String, Object> parameter) {
        return null;
    }
}
