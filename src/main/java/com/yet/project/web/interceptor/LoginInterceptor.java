package com.yet.project.web.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yet.project.domain.service.user.UserService;
import com.yet.project.web.dto.login.AuthError;
import com.yet.project.web.sessionconst.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        //log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            //log.error("미인증 사용자 요청");
            if (request.getHeader("referer") == null) {
                response.sendRedirect("/login?requestURI=" + requestURI);
                return false;
            } else {
                AuthError authError = new AuthError(HttpStatus.UNAUTHORIZED.value(), "noneAuthorize", requestURI);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(authError));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (handler instanceof HandlerMethod) {
            log.info("HandlerMethod = {}", handler);
        }

        if (handler instanceof ResourceHttpRequestHandler) {
            log.info("ResourceHttpRequestHandler = {}", handler);
        }

        if (ex != null) {
            log.info(request.getRequestURI());
            log.error("ex", ex);
        }

    }
}
