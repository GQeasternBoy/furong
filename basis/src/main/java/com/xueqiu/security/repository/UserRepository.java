package com.xueqiu.security.repository;

import com.xueqiu.security.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Author:ggq
 * @Date:2018/11/21
 * @Description:用户信息数据库操作
 */
@Repository
public interface UserRepository {

    User findByName(String userName);
}
