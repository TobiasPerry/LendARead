package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.webapp.auth.JwtTokenUtil;
import ar.edu.itba.paw.webapp.auth.ForbiddenDeniedHandler;
import ar.edu.itba.paw.webapp.auth.UnauthorizedRequestHandler;
import ar.edu.itba.paw.webapp.auth.filters.BasicTokenFilter;
import ar.edu.itba.paw.webapp.auth.filters.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:/application.properties")
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Autowired
    private BasicTokenFilter basicTokenFilter;


    private static final String ACCESS_CONTROL_USER = "@accessFunctions.checkUser(request, #id)";

    private static final String ACCESS_CONTROL_LOCATIONS = "@accessFunctions.locationOwner(request, #id)";

    private static final String ACCESS_CONTROL_LENDINGS = "@accessFunctions.lendingLenderOrBorrower(request, #id)";

    private static final String ACCESS_CONTROL_ASSET_INSTANCE_OWNER= "@accessFunctions.assetInstanceOwner(request, #id)";
    private static final String ACCESS_CONTROL_ASSET_INSTANCE_REVIEW_OWNER= "@accessFunctions.assetInstanceReviewOwner(request, #id, #idReview)";
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebExpressionVoter webExpressionVoter() {
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
        return  webExpressionVoter;
    }
    @Bean
    public JwtTokenUtil jwtTokenUtil(@Value("classpath:jwt.key") Resource jwtKeyResource) throws IOException {
        return new JwtTokenUtil(jwtKeyResource);
    }
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_LENDER > ROLE_BORROWER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                webExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter()
        );
        return new UnanimousBased(decisionVoters);
    }
    @Override
    protected void configure(final HttpSecurity http)	throws	Exception {
        http.cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedRequestHandler())
                .accessDeniedHandler(new ForbiddenDeniedHandler())
                .and().authorizeRequests().expressionHandler(webSecurityExpressionHandler())

                 // User endpoints
                .antMatchers(HttpMethod.DELETE,"/api/users/{id}").access(ACCESS_CONTROL_USER)
                .antMatchers(HttpMethod.PATCH,"/api/users/{id}").access(ACCESS_CONTROL_USER)
                .antMatchers(HttpMethod.PUT,"/api/users/{id}/password").access(ACCESS_CONTROL_USER)
                .antMatchers(HttpMethod.PUT,"/api/users/{id}/profilePic").access(ACCESS_CONTROL_USER)

                // Location endpoints
                .antMatchers(HttpMethod.PATCH,"/api/locations/{id}").access(ACCESS_CONTROL_LOCATIONS)
                .antMatchers(HttpMethod.DELETE,"/api/locations/{id}").access(ACCESS_CONTROL_LOCATIONS)


                // Lendings endpoints
                .antMatchers(HttpMethod.PATCH,"/api/lendings/{id}").access(ACCESS_CONTROL_LENDINGS)

                // AssetInstance endpoints
                .antMatchers(HttpMethod.PATCH,"/api/assetInstances/{id}").access(ACCESS_CONTROL_ASSET_INSTANCE_OWNER)
                .antMatchers(HttpMethod.DELETE,"/api/assetInstances/{id}").access(ACCESS_CONTROL_ASSET_INSTANCE_OWNER)

                // Review endpoints
                .antMatchers(HttpMethod.DELETE,"/api/assetInstances/{id}/reviews/{idReview}").access(ACCESS_CONTROL_ASSET_INSTANCE_REVIEW_OWNER)

                .antMatchers(HttpMethod.PATCH,"/api/assets/{id}").hasRole(Behaviour.ADMIN.toString())
                .antMatchers(HttpMethod.POST,"/api/assetInstances","/api/assetInstances/").authenticated()
                .antMatchers(HttpMethod.POST,"/api/assetInstances/{id}/reviews","/api/assetInstances/{id}/reviews/").authenticated()
                .antMatchers(HttpMethod.POST,"/api/users/{id}/lender_reviews","/api/users/{id}/lender_reviews/").authenticated()
                .antMatchers(HttpMethod.POST,"/api/users/{id}/borrower_reviews","/api/users/{id}/borrower_reviews/").authenticated()
                .antMatchers(HttpMethod.POST,"/api/locations","/api/locations/").authenticated()
                .antMatchers(HttpMethod.PATCH,"/api/lendings/{id}","/api/lendings/{id}/").authenticated()
                .antMatchers(HttpMethod.POST,"/api/lendings","/api/lendings/").authenticated()
                .antMatchers(HttpMethod.POST,"/api/**").permitAll()
                .and().addFilterBefore(
                        basicTokenFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
            );

    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Collections.singletonList("*"));
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(Collections.singletonList(ALL));
        cors.setExposedHeaders(Arrays.asList("X-JWT", "X-XSS-Protection", "authorization", "Location", "Content-Disposition", "Link","ETag","WWW-Authenticate"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)	throws	Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
        @Override
    public	void configure(final WebSecurity web){
        web.ignoring().antMatchers("/static/css/**", "/static/js/**", "/static/images/**", "/favicon.ico", "/errors/403");
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()throws Exception{
        return super.authenticationManagerBean();
    }

}
