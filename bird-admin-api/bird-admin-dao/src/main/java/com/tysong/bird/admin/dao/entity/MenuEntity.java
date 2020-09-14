package com.tysong.bird.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String label;
    String icon;
    String toJump;
    String permission;
    String role;
    Long parentId;
}
