package com.tysong.bird.admin.common.bean;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuBean {
    Long id;
    Long parentId;
    String label;
    String icon;
    String to;
    String permission;
    String role;
    List<MenuBean> items= Lists.newArrayList();
}
