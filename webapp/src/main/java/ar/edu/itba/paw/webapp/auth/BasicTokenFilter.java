package ar.edu.itba.paw.webapp.auth;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.thymeleaf.util.StringUtils.isEmpty;

@Component
public class BasicTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;


    private final AuthenticationManager authenticationManager;

    public BasicTokenFilter(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Basic ")) {
            chain.doFilter(request, response);
            return;
        }
        byte[] base64Token = header.split(" ")[1].trim().getBytes(StandardCharsets.UTF_8);
        final String username;
        final String password;
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
            String token = new String(decoded, StandardCharsets.UTF_8);
            username = token.split(":")[0].trim();
            password = token.split(":")[1].trim();
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        try{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )

        );
        response.setHeader(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateJwtToken(authentication));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}