package com.konkerlabs.platform.registry.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ContextPathFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("contextPath", HttpServletRequest.class.cast(request).getContextPath());
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
