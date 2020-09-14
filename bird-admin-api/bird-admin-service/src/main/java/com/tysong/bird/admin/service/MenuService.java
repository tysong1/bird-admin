package com.tysong.bird.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tysong.bird.admin.common.bean.MenuBean;
import com.tysong.bird.admin.common.bean.UserBean;
import com.tysong.bird.admin.dao.entity.MenuEntity;
import com.tysong.bird.admin.dao.entity.OperatorLogEntity;
import com.tysong.bird.admin.dao.entity.UserEntity;
import com.tysong.bird.admin.service.base.BaseMenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    BaseMenuService baseMenuService;

    @Autowired
    UserService userService;

    @Autowired
    OperatorLogService operatorLogService;

    private MenuBean convert(MenuEntity menuEntity){
        MenuBean menuBean=new MenuBean();
        BeanUtils.copyProperties(menuEntity,menuBean);
        menuBean.setTo(menuEntity.getToJump());
        return menuBean;
    }
    public List<MenuEntity> findAll(){
        return baseMenuService.findAll();
    }

    public List<MenuBean> getMenu(){
        Subject currentUser = SecurityUtils.getSubject();
        UserEntity userEntity= (UserEntity) currentUser.getPrincipal();
        UserBean userBean = userService.convertToBean(userEntity);
        List<MenuEntity> menuEntities = baseMenuService.findAll();
        List<MenuBean> menuBeanList = Lists.newArrayList();
        for (int i = 0; i < menuEntities.size(); i++) {
            MenuEntity menuEntity = menuEntities.get(i);
            MenuBean menuBean = convert(menuEntity);
            if (StringUtils.isNotBlank(menuBean.getRole())){
                if (!userBean.getRoles().contains(menuBean.getRole())){
                    continue;
                }
            }
            if (StringUtils.isNotBlank(menuBean.getPermission())){
                if (!userBean.getPerms().contains(menuBean.getPermission())){
                    continue;
                }
            }
            if (menuBean.getParentId() != null) {
                addParentNode(menuBeanList, menuBean.getParentId(), menuEntities);
                for (int j = 0; j < menuBeanList.size(); j++) {
                    MenuBean menuParentBean = menuBeanList.get(j);
                    if (menuParentBean.getId().longValue() == menuBean.getParentId().longValue()) {
                        menuParentBean.getItems().add(menuBean);
                    }
                }
            }else{
                menuBeanList.add(menuBean);
            }
        }
        return menuBeanList;
    }
    /**
     * 支持2层菜单
     * @param rootList
     * @param parentId
     * @param inputEntityList
     */
    private void addParentNode(List<MenuBean> rootList, Long parentId, List<MenuEntity> inputEntityList) {
        boolean containsParentNode = false;
        for (int i = 0; i < rootList.size(); i++) {
            if (rootList.get(i).getId().longValue() == parentId.longValue()) {
                containsParentNode = true;
                break;
            }
        }
        if (containsParentNode) {
            return;
        }
        for (int i = 0; i < inputEntityList.size(); i++) {
            if (inputEntityList.get(i).getId().longValue() == parentId.longValue()) {
                MenuEntity parentEntity = inputEntityList.get(i);
                MenuBean parentBean = convert(parentEntity);
                rootList.add(parentBean);
                break;
            }
        }
    }

    public void save(MenuEntity menuEntityInput) {
        Subject currentUser = SecurityUtils.getSubject();
        UserEntity userEntity= (UserEntity) currentUser.getPrincipal();
        OperatorLogEntity operatorLogEntity=new OperatorLogEntity();
        operatorLogEntity.setOperatorUserName(userEntity.getUserName());
        operatorLogEntity.setContent("save MenuEntity");
        operatorLogEntity.setChangeBefore("");
        operatorLogEntity.setChangeAfter(JSONObject.toJSONString(menuEntityInput));
        operatorLogService.save(operatorLogEntity);
        baseMenuService.save(menuEntityInput);
    }

    public void delete(MenuEntity menuEntity) {
        Subject currentUser = SecurityUtils.getSubject();
        UserEntity userEntity= (UserEntity) currentUser.getPrincipal();
        OperatorLogEntity operatorLogEntity=new OperatorLogEntity();
        operatorLogEntity.setOperatorUserName(userEntity.getUserName());
        operatorLogEntity.setContent("delete MenuEntity");
        operatorLogEntity.setChangeBefore("");
        operatorLogEntity.setChangeAfter(JSONObject.toJSONString(menuEntity));
        operatorLogService.save(operatorLogEntity);
        baseMenuService.delete(menuEntity);
    }
}
