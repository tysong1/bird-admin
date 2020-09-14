package com.tysong.bird.admin.service.base;

import com.tysong.bird.admin.dao.UserDao;
import com.tysong.bird.admin.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseUserService {
    @Autowired
    UserDao userDao;

    @Cacheable(cacheNames = {"userEntity_name"}, key = "#userName")
    public UserEntity findUserEntityByUserName(String userName){
        return userDao.findUserEntityByUserName(userName);
    }

    @Cacheable(cacheNames = {"userEntity_all"})
    public List<UserEntity> findAll(){
        return userDao.findAll();
    }

    @Caching(put = {
      @CachePut(cacheNames = {"userEntity_name"}, key = "#userEntityInput.userName")
    },evict = {
            @CacheEvict(cacheNames = {"userEntity_all"}, allEntries = true),
    })
    public UserEntity save(UserEntity userEntityInput) {
        UserEntity userEntity = userDao.save(userEntityInput);
        return userEntity;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = {"userEntity_name"}, key = "#userEntityInput.userName"),
            @CacheEvict(cacheNames = {"userEntity_all"}, allEntries = true),
    })
    public void delete(UserEntity userEntityInput) {
        userDao.delete(userEntityInput);
    }
}
