package com.alpha.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Properties;

/**********************
 * 1、get请求，请求参数采取URLEncode编码（取决于客户端）
 * 2、get请求，Tomcat没办法知道前端用的是哪种编码？默认是采用UTF-8
 * 3、post请求，前端按content-type编码,如果没有指定，默认是iso8859-1
 * 4、乱码问题：一个是服务端解析请求信息要正确；另一个是返回给浏览器，浏览器要正确解析响应
 * 5、post请求：用setCharacterEncoding
 * 6、get请求：只能手工转码
 * 7、这个filter的作用是在请求处理前和响应前，按指定的编码进行处理
 * 9、用户指定的编码，可以通过filter的参数传入
 * *
 * @author Administrator
 * */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /*如何从属性文件或者环境变量读入配置参数*/
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        InputStream resource = getClass().getClassLoader().getResourceAsStream("config.properties");

        Properties properties = new Properties();
        try {
            properties.load(resource);
            String enable = properties.getProperty("enable");
            if ("true".equals(enable)) {
                String charSet = properties.getProperty("charSet");
                doEncode(request, response, charSet, chain);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    private void doEncode(HttpServletRequest request, HttpServletResponse response, String charSet, FilterChain chain) throws IOException, ServletException {
        String method = request.getMethod();
        switch (method) {
            case "POST":
                request.setCharacterEncoding(charSet);
                response.setCharacterEncoding(charSet);
                chain.doFilter(request, response);
                break;
            case "GET":
                String queryString = request.getQueryString();
                HashMap<String, String> params = parseParams(queryString, charSet);


                HttpServletRequest requestProxy = (HttpServletRequest) Proxy.newProxyInstance(getClass().getClassLoader(),
                        new Class[]{HttpServletRequest.class},
                        new RequestProxy(request, params));
                response.setCharacterEncoding(charSet);
                chain.doFilter(requestProxy, response);
                break;
            default:
        }
    }

    private HashMap<String, String> parseParams(String queryString, String charSet) throws UnsupportedEncodingException {
        String decodeQueryString = URLDecoder.decode(queryString, charSet);
        HashMap<String, String> params = new HashMap<>();
        int i, j;
        boolean parseEnd = false;
        String paramStr, param, value;
        while (!decodeQueryString.isEmpty() && !parseEnd) {
            i = decodeQueryString.indexOf('&');
            if (i == -1) {
                paramStr = decodeQueryString;
                parseEnd = true;
            } else {
                paramStr = decodeQueryString.substring(0, i);
                decodeQueryString = decodeQueryString.substring(i + 1);
            }
            j = paramStr.indexOf('=');
            param = paramStr.substring(0, j);
            value = paramStr.substring(j + 1);
            params.put(param, value);
        }
        return params;
    }

    private class RequestProxy implements InvocationHandler {
        private Object request;
        private HashMap<String, String> params;

        public RequestProxy(Object request, HashMap<String, String> params) {
            this.request = request;
            this.params = params;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("getParameter")) {
                return getParameter((String) args[0]);
            }
            return method.invoke(request, args);
        }

        private String getParameter(String name) {
            return params.get(name);
        }
    }

}
