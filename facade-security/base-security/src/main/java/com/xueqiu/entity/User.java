package com.xueqiu.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author:ggq
 * @Date:2018/12/3
 * @Description:
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String userName;

    @Column
    private String passWord;
}
