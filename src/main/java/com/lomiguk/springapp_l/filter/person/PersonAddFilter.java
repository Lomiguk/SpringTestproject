package com.lomiguk.springapp_l.filter.person;

import com.lomiguk.springapp_l.tool.filter.FilterChecker;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("/person/add")
public class PersonAddFilter implements Filter {

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
