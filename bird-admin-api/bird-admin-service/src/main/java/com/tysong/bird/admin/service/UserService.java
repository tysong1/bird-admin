package com.tysong.bird.admin.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.tysong.bird.admin.common.bean.UserBean;
import com.tysong.bird.admin.common.config.TimeFormatConfig;
import com.tysong.bird.admin.dao.entity.OperatorLogEntity;
import com.tysong.bird.admin.dao.entity.RoleEntity;
import com.tysong.bird.admin.dao.entity.UserEntity;
import com.tysong.bird.admin.service.base.BaseUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    BaseUserService baseUserService;

    @Autowired
    RoleService roleService;

    @Autowired
    OperatorLogService operatorLogService;

    public UserEntity findUserEntityByUserName(String userName){
        return baseUserService.findUserEntityByUserName(userName);
    }

    public List<UserEntity> findAll(){
        List<UserEntity> all = baseUserService.findAll();
        return all;
    }

    public UserBean convertToBean(UserEntity userEntity){
        UserBean userBean=new UserBean();
        Set<String> perms= Sets.newHashSet();
        Set<String> roles= Sets.newHashSet();
        BeanUtils.copyProperties(userEntity,userBean);
        userBean.setName(userEntity.getUserName());
        userBean.setDateAdd(TimeFormatConfig.formatter3.format(userEntity.getDateAdd()));
        userBean.setDateUpdate(TimeFormatConfig.formatter3.format(userEntity.getDateUpdate()));
        JSONArray userRoleArray= JSONObject.parseArray(userEntity.getRoles());
        if (CollectionUtils.isNotEmpty(userRoleArray)){
            for (int i=0;i<userRoleArray.size();i++){
                String roleName = userRoleArray.getString(i);
                roles.add(roleName);
                RoleEntity roleEntity = roleService.findRoleEntityByName(roleName);
                if (roleEntity!=null) {
                    JSONArray permissionArray = JSONObject.parseArray(roleEntity.getPermissions());
                    if (CollectionUtils.isNotEmpty(permissionArray)) {
                        for (int j = 0; j < permissionArray.size(); j++) {
                            String permissionName = permissionArray.getString(j);
                            perms.add(permissionName);
                        }
                    }
                }
            }
        }
        userBean.setRoles(roles);
        userBean.setPerms(perms);
        return userBean;
    }

    public void save(UserEntity userEntityInput) {
        Subject currentUser = SecurityUtils.getSubject();
        UserEntity userEntity= (UserEntity) currentUser.getPrincipal();
        OperatorLogEntity operatorLogEntity=new OperatorLogEntity();
        operatorLogEntity.setOperatorUserName(userEntity.getUserName());
        operatorLogEntity.setContent("save user");
        operatorLogEntity.setChangeBefore("");
        operatorLogEntity.setChangeAfter(JSONObject.toJSONString(userEntityInput));
        operatorLogService.save(operatorLogEntity);
        baseUserService.save(userEntityInput);
    }

    public void delete(UserEntity userEntityInput) {
        Subject currentUser = SecurityUtils.getSubject();
        UserEntity userEntity= (UserEntity) currentUser.getPrincipal();
        OperatorLogEntity operatorLogEntity=new OperatorLogEntity();
        operatorLogEntity.setOperatorUserName(userEntity.getUserName());
        operatorLogEntity.setContent("delete user");
        operatorLogEntity.setChangeBefore("");
        operatorLogEntity.setChangeAfter(JSONObject.toJSONString(userEntity));
        operatorLogService.save(operatorLogEntity);
        baseUserService.delete(userEntityInput);
    }
}
