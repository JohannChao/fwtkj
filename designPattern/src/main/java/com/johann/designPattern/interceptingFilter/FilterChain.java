package com.johann.designPattern.interceptingFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器链
 * @author Johann
 * @version 1.0
 * @see
 **/
public class FilterChain {
    private List<Filter> filters;
    private int index = 0;

    public FilterChain addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    public FilterChain() {
        filters = new ArrayList<>();
    }

    /**
     * 根据当前过滤器链的索引,执行下一个过滤器.并将自身【过滤器链】传入下一个过滤器
     * @params [request, response]
     * @return void
     * @author Johann
     */
    public void doFilter(Request request, Response response) {
        if (index == filters.size()) {
            return;
        }
        Filter filter = filters.get(index);
        index++;
        filter.doFilter(request, response, this);
    }
}
