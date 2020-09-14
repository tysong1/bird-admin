package com.tysong.bird.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.tysong.bird.admin.dao.entity.OperatorLogEntity;
import com.tysong.bird.admin.dao.entity.RoleEntity;
import com.tysong.bird.admin.dao.entity.UserEntity;
import com.tysong.bird.admin.service.base.BaseRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    BaseRoleService baseRoleService;

    @Autowired
    OperatorLogService operatorLogService;

    public RoleEntity findRoleEntityByName(String name) {
        return baseRoleService.findRoleEntityByName(name);
    }

    public List<RoleEntity> findAll() {
        return baseRoleService.findAll();
    }

    public void save(RoleEntity roleEntity) {
        Subject currentUser = SecurityUtils.getSubject();
        UserEntity userEntity= (UserEntity) currentUser.getPrincipal();
        OperatorLogEntity operatorLogEntity=new OperatorLogEntity();
        operatorLogEntity.setOperatorUserName(userEntity.getUserName());
        operatorLogEntity.setContent("save role");
        operatorLogEntity.setChangeBefore("");
        operatorLogEntity.setChangeAfter(JSONObject.toJSONString(roleEntity));
        operatorLogService.save(operatorLogEntity);
        baseRoleService.save(roleEntity);
    }

    public void delete(RoleEntity roleEntity) {
        Subject currentUser = SecurityUtils.getSubject();
        UserEntity userEntity= (UserEntity) currentUser.getPrincipal();
        OperatorLogEntity operatorLogEntity=new OperatorLogEntity();
        operatorLogEntity.setOperatorUserName(userEntity.getUserName());
        operatorLogEntity.setContent("delete role");
        operatorLogEntity.setChangeBefore("");
        operatorLogEntity.setChangeAfter(JSONObject.toJSONString(roleEntity));
        operatorLogService.save(operatorLogEntity);
        baseRoleService.delete(roleEntity);
    }
}
