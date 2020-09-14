package com.tysong.bird.admin.api.config.shiro;

import com.tysong.bird.admin.common.bean.UserBean;
import com.tysong.bird.admin.dao.entity.UserEntity;
import com.tysong.bird.admin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserEntity user = (UserEntity) getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserBean userBean = userService.convertToBean(user);
        simpleAuthorizationInfo.addRoles(userBean.getRoles());
        simpleAuthorizationInfo.addStringPermissions(userBean.getPerms());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        UserEntity user = userService.findUserEntityByUserName(username);
        if (user ==null|| !StringUtils.equals(user.getPassWord(),password)) {
            return null;
        }else{
            SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(user, user.getPassWord(),getName());
            return simpleAuthenticationInfo;
        }
    }
}
