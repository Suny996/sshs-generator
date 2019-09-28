package com.scai.toolkit.coder.mapper;

import com.scai.core.page.Page;
import com.scai.toolkit.coder.model.DbTable;

import tk.mybatis.mapper.common.Mapper;

/**
 * 
 * @author Suny
 *
 */
public interface DbTableMapper extends Mapper<DbTable> {
	/**
	 * 根据ID查询对象
	 * @param tableName
	 * @return
	 */
	DbTable findById(String tableName);

	/**
	 * 查询table表列表
	 * @param page
	 */
	void findForPageList(Page<DbTable> page);
}
