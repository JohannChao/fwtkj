package com.johann.designPattern.interceptingFilter;

/**
 * 过滤器实现类,对请求和响应进行过滤处理.
 * 当前过滤器对请求和响应进行过滤处理后,通过FilterChain.doFilter()方法调用下一个过滤器
 * @author Johann
 * @version 1.0
 * @see
 **/
public class HtmlFilter implements Filter{
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        String content = request.getContent();
        content = content.replaceAll("<", "[")
                .replace(">", "]");
        request.setContent(content);

        filterChain.doFilter(request, response);

        content = content.replaceAll("\\[", "<")
                .replaceAll("\\]", ">");
        response.setContent(content);
    }
}
