package com.tysong.bird.admin.dao;


import com.tysong.bird.admin.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleDao extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {

    RoleEntity findRoleEntityByName(String name);
}
