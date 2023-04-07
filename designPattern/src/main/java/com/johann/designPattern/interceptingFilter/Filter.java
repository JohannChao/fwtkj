package com.johann.designPattern.interceptingFilter;

/**
 * 过滤器接口
 * @author Johann
 */
public interface Filter {
    /**
     * 过滤器方法,对请求和响应进行过滤处理
     * @params [request, response, filterChain]
     * @return void
     * @author Johann
     */
    void doFilter(Request request, Response response, FilterChain filterChain);
}
