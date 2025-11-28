package kr.ac.jbnu.ksh.wsdteaching.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String method = httpReq.getMethod();
        String uri = httpReq.getRequestURI();

        System.out.printf("[LoggingFilter] 요청 시작 : %s %s%n", method, uri);

        chain.doFilter(request, response);

        int status = httpRes.getStatus();
        System.out.printf("[LoggingFilter] 요청 완료, status=%d%n", status);
    }
}
