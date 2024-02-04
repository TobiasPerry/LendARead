package ar.edu.itba.paw.webapp.auth.filters;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.webapp.auth.JwtTokenUtil;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

import static org.thymeleaf.util.StringUtils.isEmpty;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final PawUserDetailsService pawUserDetailsService;

    private final UserService userService;


    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, PawUserDetailsService pawUserDetailsService,UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.pawUserDetailsService = pawUserDetailsService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        final String token;
        try {
            token = header.split(" ")[1].trim();
            if (!jwtTokenUtil.validateJwtToken(token)) {
                chain.doFilter(request, response);
                return;
            }
        }
        catch(Exception e){
            chain.doFilter(request, response);
            return;
        }

        UserDetails userDetails;
        userDetails = pawUserDetailsService.loadUserByUsername(jwtTokenUtil.getUserNameFromJwtToken(token));
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        User user;
        try {
            user = userService.getUser(userDetails.getUsername());
        } catch (UserNotFoundException e) {
            chain.doFilter(request, response);
            return;
        }
        try {
        if (jwtTokenUtil.isRefreshToken(token)) {
            response.setHeader(JwtTokenUtil.JWT_HEADER, jwtTokenUtil.generateJwtToken(authentication,JwtTokenUtil.getBaseUrl(request) + "/api/users/" + user.getId()));
        }
        } catch (Exception e) {
            chain.doFilter(request, response);
            return;
        }

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
