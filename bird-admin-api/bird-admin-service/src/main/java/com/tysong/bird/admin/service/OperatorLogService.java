package com.tysong.bird.admin.service;

import com.tysong.bird.admin.dao.entity.OperatorLogEntity;
import com.tysong.bird.admin.service.base.BaseOperatorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorLogService {

    @Autowired
    BaseOperatorLogService baseOperatorLogService;

    public List<OperatorLogEntity> findAll(){
        return baseOperatorLogService.findAll();
    }

    @Async("taskExecutor")
    public void save(OperatorLogEntity operatorLogEntity){
        baseOperatorLogService.save(operatorLogEntity);
    }
}
