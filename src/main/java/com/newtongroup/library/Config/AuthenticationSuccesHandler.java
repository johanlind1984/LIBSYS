package com.newtongroup.library.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthenticationSuccesHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Set<String> authoritiesList = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(authoritiesList.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin/");
        } else if (authoritiesList.contains("ROLE_BOSS")) {
            httpServletResponse.sendRedirect("/boss/");
        } else if (authoritiesList.contains("ROLE_LIBRARIAN")) {
            httpServletResponse.sendRedirect("/librarian/");
        } else if (authoritiesList.contains("ROLE_VISITOR")) {
            httpServletResponse.sendRedirect("/visitor/");
        }
        else {
            httpServletResponse.sendRedirect("/error-processing-login/");
        }
    }
}
