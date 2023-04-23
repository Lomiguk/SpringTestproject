package com.lomiguk.springapp.filter.profile;

import com.lomiguk.springapp.tool.filter.FilterChecker;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/profile/admin/*")
public class AdminProfileFilter implements Filter{
    public static final String PROFILE_LOGIN_PATH = "/profile/login";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        new FilterChecker(servletRequest, servletResponse, filterChain)
                .addCondition("isAuthorised")
                .addCondition("isAdmin")
                .checkForPath(PROFILE_LOGIN_PATH);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
