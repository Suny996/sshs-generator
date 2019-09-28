package com.scai.core.customise.controller;

import com.scai.core.base.controller.BaseController;
import com.scai.core.customise.mapper.CommonMapper;
import com.scai.core.message.Message;
import com.scai.core.util.DictionaryUtil;
import com.scai.core.util.SystemUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称： 自定义查询
 *
 * @author Suny
 * @date 2017年10月23日
 */
@RestController
public class CommonController extends BaseController {
    Log logger = LogFactory.getLog(CommonController.class);
    @Resource
    CommonMapper commonMapper;

    /**
     * 数据字典查询所有
     *
     * @throws Exception
     */
    @GetMapping("/plist")
    public Message getPlist() {
        return Message.success(DictionaryUtil.getAllList());
    }

    /**
     * 数据字典查询
     *
     * @param dictCode
     * @throws Exception
     */
    @GetMapping("/plist/{dictCode}")
    public Message getPlistByDictCode(@PathVariable(value = "dictCode", required = true) String dictCode, @RequestParam(value = "locale", required = false) String locale) {
        if (StringUtils.isEmpty(locale)) {
            locale = SystemUtil.getLocale();
        } else {
            locale = locale.replace("-", "_");
        }
        return Message.success(DictionaryUtil.getList(dictCode, null, locale));
    }

    /**
     * 数据字典查询
     *
     * @param dictCode
     * @throws Exception
     */
    @GetMapping("/plist/{dictCode}/{subCode}")
    public Message getPlistByDictCode2(@PathVariable(value = "dictCode", required = true) String dictCode, @PathVariable(value = "subCode", required = false) String subCode, @RequestParam(value = "locale", required = false) String locale) {
        if (StringUtils.isEmpty(locale)) {
            locale = SystemUtil.getLocale();
        }
        return Message.success(DictionaryUtil.getList(dictCode, subCode, locale));
    }

    /**
     * 岗位列表查询
     */
    @GetMapping("/postlist")
    @Cacheable("postList_cache")
    public Message getPostList() {
        List<Map<String, Object>> posts = commonMapper.findPosts();
        return Message.success(posts);
    }

    /**
     * 机构列表查询
     *
     * @param orgCode
     * @throws Exception
     */
    @GetMapping("/orglist/{orgCode}")
    @Cacheable("orgList_cache")
    public Message getOrgTreeByOrgCode(@PathVariable("orgCode") String orgCode) {
        Map<String, Object> org = commonMapper.findOrgInfoByOrgCode(orgCode);
        return Message.success(getOrgListByOrgCode(org));
    }

    private Map<String, Object> getOrgListByOrgCode(Map<String, Object> org) {
        if (org != null) {
            List<Map<String, Object>> list = commonMapper.findOrgListByOrgCode((String) org.get("ORG_CODE"));
            if (list != null && list.size() > 0) {
                for (Map<String, Object> o : list) {
                    getOrgListByOrgCode(o);
                }
                org.put("children", list);
                return org;
            } else {
                return org;
            }
        } else {
            return new HashMap<String, Object>();
        }
    }

    /**
     * 根据用户编号获取菜单数据
     *
     * @param userCode
     * @throws Exception
     */
    @GetMapping("/menus/{userCode}/{parentId}")
    public Message getUserMenu(@PathVariable("userCode") String userCode, @PathVariable("parentId") String parentId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userCode", userCode);
        params.put("parentId", parentId);
        return Message.success(initMenu(userCode, parentId));
    }

    /**
     * 初始化菜单
     *
     * @param userName
     * @param parentId
     * @return
     */
    List<Map<String, Object>> initMenu(String userName, String parentId) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("parentId", parentId);
        params.put("userCode", userName);
        List<Map<String, Object>> menus = commonMapper.findUserMenus(params);
        for (Map<String, Object> menu : menus) {
            menu.put("children", initMenu(userName, (String) menu.get("code")));
        }
        return menus;
    }
}