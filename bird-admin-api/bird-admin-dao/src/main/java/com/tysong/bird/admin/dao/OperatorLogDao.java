package com.tysong.bird.admin.dao;


import com.tysong.bird.admin.dao.entity.OperatorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface OperatorLogDao extends JpaRepository<OperatorLogEntity, Long>, JpaSpecificationExecutor<OperatorLogEntity> {

}
