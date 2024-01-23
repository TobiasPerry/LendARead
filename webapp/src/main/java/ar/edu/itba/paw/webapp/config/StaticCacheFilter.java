package ar.edu.itba.paw.webapp.config;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaticCacheFilter extends OncePerRequestFilter {

    public static final int CACHE_TIME = 60 * 60 * 24 * 365;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setHeader("Cache-Control", String.format("public, max-age=%d, inmutable", CACHE_TIME));
        chain.doFilter(request, response);
    }
}