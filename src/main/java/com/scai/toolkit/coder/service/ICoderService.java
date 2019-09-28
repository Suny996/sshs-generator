package com.scai.toolkit.coder.service;

import com.scai.core.base.service.IBaseService;
import com.scai.core.message.Message;
import com.scai.toolkit.coder.model.Coder;
import com.scai.toolkit.coder.model.Column;

import java.util.List;

/**
 * 类名称：代码生成器接口类
 * 
 * @author Suny
 * @date 2017年10月24日
 * @version
 */
public interface ICoderService extends IBaseService<Coder> {
 	/**
	 * 列表(主表)
	 * 
	 * @param tableName
	 * @throws Exception
	 * @return
	 */
    List<Column> findColumnForList(String tableName) throws Exception;

}
