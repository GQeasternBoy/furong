package com.xueqiu.security.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author:ggq
 * @Date:2018/11/30
 * @Description:记住用户登录状态的实体建模
 */
@Entity
public class PersistentLogins implements Serializable{

    @Id
    @Column(name="id",length = 11,nullable = false)
    private Integer id;

    @Column(name = "user_name",length = 50,nullable = false)
    private String userName;

    @Column(name="token",length = 64,nullable = false)
    private String token;

    @Temporal(TemporalType.DATE)
    @Column(name="last_used",nullable = false)
    private Date lastUsed;
}
