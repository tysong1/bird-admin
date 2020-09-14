package com.tysong.bird.admin.service.base;

import com.tysong.bird.admin.dao.MenuDao;
import com.tysong.bird.admin.dao.entity.MenuEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseMenuService {
    @Autowired
    MenuDao menuDao;

    @Cacheable(cacheNames = {"menuEntity_all"})
    public List<MenuEntity> findAll(){
        return menuDao.findAll();
    }

    @Caching(put = {
    },evict = {
            @CacheEvict(cacheNames = {"menuEntity_all"}, allEntries = true),
    })
    public MenuEntity save(MenuEntity menuEntityInput) {
        MenuEntity menuEntity = menuDao.save(menuEntityInput);
        return menuEntity;
    }

    @Caching(put = {
    },evict = {
            @CacheEvict(cacheNames = {"menuEntity_all"}, allEntries = true),
    })
    public void delete(MenuEntity menuEntity) {
        menuDao.delete(menuEntity);
    }
}
