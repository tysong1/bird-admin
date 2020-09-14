package com.tysong.bird.admin.service.base;

import com.tysong.bird.admin.dao.OperatorLogDao;
import com.tysong.bird.admin.dao.entity.OperatorLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseOperatorLogService {
    @Autowired
    OperatorLogDao operatorLogDao;

    @Cacheable(cacheNames = {"operatorLogEntity_all"})
    public List<OperatorLogEntity> findAll(){
        return operatorLogDao.findAll();
    }

    @Caching(put = {
    },evict = {
            @CacheEvict(cacheNames = {"operatorLogEntity_all"}, allEntries = true),
    })
    public void save(OperatorLogEntity operatorLogEntity){
        operatorLogDao.save(operatorLogEntity);
    }
}
