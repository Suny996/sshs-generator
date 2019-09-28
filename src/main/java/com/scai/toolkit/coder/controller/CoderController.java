package com.scai.toolkit.coder.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scai.core.base.controller.BaseController;
import com.scai.core.exception.BusinessException;
import com.scai.core.message.Message;
import com.scai.core.page.Page;
import com.scai.core.util.ReflectHelper;
import com.scai.core.util.Serializabler;
import com.scai.core.util.UuidUtil;
import com.scai.toolkit.coder.helper.CoderGenerator;
import com.scai.toolkit.coder.model.Coder;
import com.scai.toolkit.coder.model.Column;
import com.scai.toolkit.coder.service.ICoderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 用户管理控制类
 *
 * @author Suny
 * @date 2017年7月5日 上午10:40:10
 */
@Api(tags = "工具")
@RestController
@RequestMapping(value = "/api/coders")
public class CoderController extends BaseController {
    @Autowired
    private ICoderService coderService;

    Log logger = LogFactory.getLog(CoderController.class);

    /**
     * 保存代码
     */
    @ApiOperation(value = "代碼生成", notes = "")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "tableName", value = "表名", required = true),})
    @PostMapping("/run")
    public Message save(@RequestBody Coder coder) {
        try {
            logger.debug("开始保存代码信息……");
            coder.setCoderId(UuidUtil.get32UUID());
           logger.debug("===========>" + coder.getTableName());
            for (Column col : coder.getFields()) {
                if (StringUtils.isEmpty(coder.getTableName())) {
                    coder.setTableName(col.getTableName());
                    coder.setTableComment(col.getTableComment());
                }
                col.setPropertyName(ReflectHelper.getPropertyName(col.getColumnName()));
                col.setPropertyType(CoderGenerator.getPropertyType(col.getColumnType()));
            }

            coder.setColumns(Serializabler.object2Bytes(coder.getFields()));

            CoderGenerator.generate(coder);
            coderService.save(coder);
            return Message.success(coder);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存代码信息异常！");
            throw new BusinessException("SY0001");
        }
    }
    /**
     * 保存代码
     */
    @ApiOperation(value = "代碼生成", notes = "")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "tableName", value = "表名", required = true),})
    @GetMapping("/gen")
    public Message run(String tableName) {
        try {
            logger.debug("开始生成代码信息……");
            Coder coder =new Coder();
            coder.setCoderId(UuidUtil.get32UUID());
            coder.setTableName(tableName);
            logger.debug("===========>" + coder.getTableName());
            coder.setFields(coderService.findColumnForList(tableName));
            for (Column col : coder.getFields()) {
                if (StringUtils.isEmpty(coder.getTableName())) {
                    coder.setTableName(col.getTableName());
                    coder.setTableComment(col.getTableComment());
                }
                col.setPropertyName(ReflectHelper.getPropertyName(col.getColumnName()));
                col.setPropertyType(CoderGenerator.getPropertyType(col.getColumnType()));
            }

            coder.setColumns(Serializabler.object2Bytes(coder.getFields()));

            CoderGenerator.generate(coder);
           // coderService.save(coder);
            return Message.success(coder);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("保存代码信息异常！");
            throw new BusinessException("SY0001");
        }
    }
    /**
     * 修改代码
     */
    @PutMapping
    public Message update(@RequestBody Coder coder) {
        try {
            logger.debug("开始更新代码信息……");
            return coderService.update(coder);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新代码信息异常！");
            throw new BusinessException("SY0002");
        }
    }

    /**
     * 删除代码
     */
    @DeleteMapping("/{coderId}")
    public Message delete(@PathVariable("coderId") String coderId) {
        try {
            logger.debug("开始删除代码信息……");
            return coderService.deleteById(coderId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除代码信息异常！");
            throw new BusinessException("SY0003");
        }
    }

    /**
     * 根据主键查找代码信息
     */
    @GetMapping("/{coderId}")
    public Message getById(@PathVariable("coderId") String coderId) {
        try {
            logger.debug("开始查询代码信息……");
           Message message =  Message.success(coderService.getById(coderId));
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询代码信息异常！");
            throw new BusinessException("SY0001");
        }
    }

    /**
     * 查询表信息列表
     */
    @GetMapping("/columnList/{tableName}")
    public Message queryList(@PathVariable("tableName") String tableName) {
        try {
            logger.debug("开始查询表列表信息……");
            List<Column> columnList = coderService.findColumnForList(tableName);
            return Message.success(columnList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询表信息异常！");
            throw new BusinessException("SY0001");
        }
    }

    /**
     * 分页查表户信息
     */
    @PostMapping("/tableList")
    public Message queryPageList(@RequestBody Page<Coder> page) {
        try {
            logger.debug("开始分页查询表信息……");
            Message message = coderService.findForPageList("com.scai.toolkit.coder.mapper.CoderMapper.findDbTableForPageList", page);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("分页查询表信息异常！");
            throw new BusinessException("SY0005");
        }
    }
}
