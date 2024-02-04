package ar.edu.itba.paw.webapp.auth.filters;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.webapp.auth.JwtTokenUtil;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final UserService userService;

    private final PawUserDetailsService pawUserDetailsService;

    @Autowired
    public BasicTokenFilter(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, PawUserDetailsService pawUserDetailsService, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.pawUserDetailsService = pawUserDetailsService;
        this.userService = userService;
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
        final String posiblePassword;
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
            String token = new String(decoded, StandardCharsets.UTF_8);
            username = token.split(":")[0].trim();
            posiblePassword = token.split(":")[1].trim();
        } catch (Exception e) {
            chain.doFilter(request, response);
            return;
        }
        try{
            UserDetails userDetails;
            Authentication authentication;
            userDetails = pawUserDetailsService.loadUserByUsername(username);
            if(this.isTokenReset(username,posiblePassword)){
                 authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                 authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                posiblePassword,
                                userDetails.getAuthorities()
                        )

                );
            }
        User user = userService.getUser(username);
        response.setHeader(JwtTokenUtil.JWT_HEADER, jwtTokenUtil.generateJwtToken(authentication,JwtTokenUtil.getBaseUrl(request) + "/api/users/" + user.getId()));
        response.setHeader(JwtTokenUtil.JWT_REFRESH_HEADER, jwtTokenUtil.generateRefreshToken( authentication,JwtTokenUtil.getBaseUrl(request) + "/api/users/" + user.getId()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception e){
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isTokenReset(String email,String posiblePassword) throws UserNotFoundException {
        User user = userService.getUser(email);
        String token = userService.getUserResetPasswordToken(email);
        boolean isValid =  token != null && token.equals(posiblePassword) && userService.isTokenValid(user.getId(), token);
        if(isValid){
            userService.deleteToken(token);
        }
        return isValid;
    }
}