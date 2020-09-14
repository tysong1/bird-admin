package com.tysong.bird.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "operator_log")
@Getter
@Setter
public class OperatorLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String operatorUserName;
    private String changeBefore;
    private String changeAfter;
    private String content;
}
