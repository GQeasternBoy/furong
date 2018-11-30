package com.xueqiu.security.service;

import com.xueqiu.security.config.SecurityUser;
import com.xueqiu.security.entity.User;
import com.xueqiu.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author:ggq
 * @Date:2018/11/30
 * @Description:
 */
@Component
public class CustomUserDetailsService implements UserDetailsService{

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
