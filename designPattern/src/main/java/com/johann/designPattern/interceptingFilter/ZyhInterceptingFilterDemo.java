package com.johann.designPattern.interceptingFilter;

/**
 * 拦截过滤器模式用于对应用程序的请求或响应做一些预处理/后处理.
 * 该模式由多个过滤器组成，每个过滤器都可以对请求或响应进行处理和修改，然后将处理结果传递给下一个过滤器。
 * 这样，通过组合不同的过滤器，可以构建出一个功能强大的过滤器链，对请求或响应进行多重处理和过滤。
 *
 * <p> 拦截过滤器模式包含以下角色:
 * 1. 过滤器(Filter): 过滤器在请求处理程序执行请求之前或之后,执行某些任务.
 * 2. 过滤器链(Filter Chain): 过滤器链带有多个过滤器,并在Target上按照定义的顺序执行这些过滤器.
 * 3. Target: Target对象是请求处理程序.
 * 4. 过滤管理器(Filter Manager): 过滤管理器管理过滤器和过滤器链.
 * 5. 客户端(Client): Client是向Target对象发送请求的对象.</p>
 *
 * @author Johann
 * @version 1.0
 * @see
 **/
public class ZyhInterceptingFilterDemo {
    public static void main(String[] args) {
        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new HtmlFilter())
                .addFilter(new SensitiveFilter());

        Request request = new Request();
        request.setContent("<html><body><p>Hello, fuck!</p></body></html>");
        Response response = new Response();

        filterChain.doFilter(request, response);

        System.out.println("request content: " + request.getContent());
        System.out.println("response content: " + response.getContent());
    }
}
