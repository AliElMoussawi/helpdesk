/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author CLICK ONCE
 */
@Component
public class CorsInterceptor extends HandlerInterceptorAdapter {

    private static final String ORIGIN = "Origin";
    private static final String AC_REQUEST_METHOD = "Access-Control-Request-Method";
    private static final String AC_REQUEST_HEADERS = "Access-Control-Request-Headers";

    private static final String AC_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String AC_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String AC_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    public static final String AC_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    public static final String AC_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String HEADER_CONNECTION = "Connection";

    private CorsData corsData;

    private String origin;
    private String allowHeaders;
    private String allowMethods = "POST, GET, OPTIONS, DELETE, PUT,PATCH";

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setAllowMethods(String allowMethods) {
        this.allowMethods = allowMethods;
    }

    public void setAllowHeaders(String allowHeaders) {
        this.allowHeaders = allowHeaders;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.corsData = new CorsData(request);
        origin = request.getHeader(ORIGIN);
        response.setHeader(AC_ALLOW_ORIGIN, request.getHeader(ORIGIN));
        if (this.corsData.isPreflighted()) {

            allowMethods = request.getHeader(AC_REQUEST_METHOD);
            response.setHeader(AC_ALLOW_METHODS, allowMethods);
            allowHeaders = request.getHeader(AC_REQUEST_HEADERS);
            response.setHeader(AC_ALLOW_HEADERS, allowHeaders);
            response.setHeader(AC_ALLOW_CREDENTIALS, "false");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setStatus(200);
            response.setHeader(HEADER_CONNECTION, "keep-alive");
            return false;

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        response.setHeader(AC_ALLOW_ORIGIN, request.getHeader(ORIGIN));
        if (this.corsData.isSimple()) {
            response.setHeader(AC_ALLOW_ORIGIN, origin);
        }
    }

    class CorsData {

        private String origin;
        private String requestMethods;
        private String requestHeaders;

        CorsData(HttpServletRequest request) {
            this.origin = request.getHeader(ORIGIN);
            this.requestMethods = request.getHeader(AC_REQUEST_METHOD);
            this.requestHeaders = request.getHeader(AC_REQUEST_HEADERS);
        }

        public boolean hasOrigin() {
            return origin != null && !origin.isEmpty();
        }

        public boolean hasRequestMethods() {
            return requestMethods != null && !requestMethods.isEmpty();
        }

        public boolean hasRequestHeaders() {
            return requestHeaders != null && !requestHeaders.isEmpty();
        }

        public String getOrigin() {
            return origin;
        }

        public String getRequestMethods() {
            return requestMethods;
        }

        public String getRequestHeaders() {
            return requestHeaders;
        }

        public boolean isPreflighted() {
            return hasOrigin() && hasRequestHeaders() && hasRequestMethods();
        }

        public boolean isSimple() {
            return hasOrigin() && !hasRequestHeaders();
        }
    }
}
