package com.tysong.bird.admin.dao;


import com.tysong.bird.admin.dao.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuDao extends JpaRepository<MenuEntity, Long>, JpaSpecificationExecutor<MenuEntity> {

}
