package com.xueqiu.security.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author:ggq
 * @Date:2018/11/20
 * @Description:
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final Pattern ALLOWEDMETHODS = Pattern.compile("^(GET|POST|PUT)$");

    /**
     * 需要排除的url列表
     */
    @Getter
    @Setter
    private List<String> execludeUrls;

    @Override
    public boolean matches(HttpServletRequest request) {
        if (ObjectUtils.isEmpty(request)){
            String servletPath = request.getServletPath();
            for (String url : execludeUrls){
                if (servletPath.contains(url)){
                    logger.info("-------->"+servletPath);
                    return false;
                }
            }
        }
        return !ALLOWEDMETHODS.matcher(request.getMethod()).matches();
    }
}
