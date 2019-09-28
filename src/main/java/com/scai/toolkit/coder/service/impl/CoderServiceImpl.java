package com.scai.toolkit.coder.service.impl;

import com.scai.core.base.service.impl.BaseServiceImpl;
import com.scai.core.message.Message;
import com.scai.core.util.UuidUtil;
import com.scai.toolkit.coder.mapper.CoderMapper;
import com.scai.toolkit.coder.model.Coder;
import com.scai.toolkit.coder.model.Column;
import com.scai.toolkit.coder.service.ICoderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类名称：CreateCodeService 代码生成器
 *
 * @author Suny
 * @version 1.0
 * @date 2015年11月24日
 */
@Service("coderService")
public class CoderServiceImpl extends BaseServiceImpl<Coder> implements ICoderService {

    Log logger = LogFactory.getLog(CoderServiceImpl.class);
    @Resource
    private CoderMapper coderMapper;

    /**
     * 新增
     *
     * @param coder
     */
    @Override
    public Message save(Coder coder) {
        coderMapper.deleteByTableName(coder.getTableName());
        coder.setCoderId(UuidUtil.get32UUID());
        try {
            return super.save(coder);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存代码信息异常！");
            return Message.failure("-110002");
        }
    }

    /**
     * 查询列表
     *
     * @param parameter
     * @return Message
     */
    @Override
    public List<Coder> queryList(Map<String, Object> parameter) {
        return null;
    }

    /**
     * 列表(主表)
     *
     * @param tableName
     */
    @Override
    public List<Column> findColumnForList(String tableName) {

        return coderMapper.findColumnAll(tableName);
    }

}
