package com.tysong.bird.admin.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class UserEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",columnDefinition = "bigint(64) ")
    Long id;

    @Column(unique = true)
    String userName;

    String passWord;

    LocalDateTime dateAdd;

    LocalDateTime dateUpdate;

    String roles;

    String icon;
}
