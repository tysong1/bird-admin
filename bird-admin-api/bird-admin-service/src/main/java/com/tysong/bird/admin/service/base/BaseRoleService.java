package com.tysong.bird.admin.service.base;

import com.tysong.bird.admin.dao.RoleDao;
import com.tysong.bird.admin.dao.entity.RoleEntity;
import com.tysong.bird.admin.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseRoleService {
    @Autowired
    RoleDao roleDao;

    @Cacheable(cacheNames = {"roleEntity_name"}, key = "#name")
    public RoleEntity findRoleEntityByName(String name){
        return roleDao.findRoleEntityByName(name);
    }

    @Cacheable(cacheNames = {"roleEntity_all"})
    public List<RoleEntity> findAll(){
        return roleDao.findAll();
    }

    @Caching(put = {
            @CachePut(cacheNames = {"roleEntity_name"}, key = "#roleEntity.name")
    },evict = {
            @CacheEvict(cacheNames = {"roleEntity_all"}, allEntries = true),
    })
    public RoleEntity save(RoleEntity roleEntity) {
        RoleEntity entity = roleDao.save(roleEntity);
        return entity;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = {"roleEntity_name"}, key = "#roleEntity.name"),
            @CacheEvict(cacheNames = {"roleEntity_all"}, allEntries = true),
    })
    public void delete(RoleEntity roleEntity) {
        roleDao.delete(roleEntity);
    }
}
