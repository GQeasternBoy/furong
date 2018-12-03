package com.xueqiu.service;

import com.xueqiu.entity.SecurityUser;
import com.xueqiu.entity.User;
import com.xueqiu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author:ggq
 * @Date:2018/12/3
 * @Description:自定义用户配置
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByName(userName);

        if (user == null){
            throw new UsernameNotFoundException("用户名 "+userName +"未找到");
        }
        return new SecurityUser(user);
    }
}
