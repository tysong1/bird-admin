package com.tysong.bird.admin.common.bean;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserBean {
    Long id;
    String name;
    String dateAdd;
    String dateUpdate;
    Set<String> perms;
    Set<String> roles;
    String icon;
}
