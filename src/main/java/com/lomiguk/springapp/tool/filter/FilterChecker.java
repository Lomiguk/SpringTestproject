package com.lomiguk.springapp.tool.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterChecker {
    ServletRequest servletRequest;
    ServletResponse servletResponse;
    FilterChain filterChain;
    HttpSession session;

    List<Boolean> conditions = new ArrayList<Boolean>();

    public FilterChecker(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
        this.filterChain = filterChain;
        session = ((HttpServletRequest) servletRequest).getSession();
    }

    public void checkForPath(String path) throws ServletException, IOException {
        if (conditions.contains(false)){
            servletRequest.getRequestDispatcher(path)
                    .forward(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public FilterChecker addCondition(String statusName){
        Boolean condition = (Boolean) session.getAttribute(statusName);
        if (condition == null) condition = false;
        conditions.add(condition);
        return this;
    }
}
