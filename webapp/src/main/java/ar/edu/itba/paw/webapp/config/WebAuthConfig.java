package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.*;
import ar.edu.itba.paw.webapp.auth.acessControlFunctions.AccessFunctions;
import ar.edu.itba.paw.webapp.auth.filters.BasicTokenFilter;
import ar.edu.itba.paw.webapp.auth.filters.JwtTokenFilter;
import ar.edu.itba.paw.webapp.auth.voters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:/application.properties")
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DeleteAssetVoter deleteAssetVoter;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;

    @Autowired
    private ChangeAssetStatusVoter changeAssetStatusVoter;
    @Autowired
    private Environment environment;

    @Autowired
    private LenderViewOwnerVoter lenderViewOwnerVoter;
    @Autowired
    private BorrowerViewVoter borrowerViewVoter;

    @Autowired
    private BorrowerReviewVoter borrowerReviewVoter;

    @Autowired
    private LenderReviewVoter lenderReviewVoter;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Autowired
    private BasicTokenFilter basicTokenFilter;

    @Autowired
    private AccessFunctions accessFunctions;

    private static final String ACCESS_CONTROL_USER = "@accessFunctions.checkUser(request, #email)";

    private static final String ACCESS_CONTROL_LOCATIONS = "@accessFunctions.locationOwner(request, #id)";

    private static final String ACCESS_CONTROL_LENDINGS = "@accessFunctions.lendingOwner(request, #id)";

    private static final String ACCESS_CONTROL_ASSET_INSTANCE_OWNER= "@accessFunctions.assetInstanceOwner(request, #id)";
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
    public JwtTokenUtil jwtTokenUtil(@Value("classpath:jwt.salt") Resource jwtKeyResource) throws IOException {
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
        String hierarchy = "ROLE_LENDER > ROLE_BORROWER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                webExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                deleteAssetVoter,
                changeAssetStatusVoter,
                lenderViewOwnerVoter,
                borrowerViewVoter,
                lenderReviewVoter,
                borrowerReviewVoter
        );
        return new UnanimousBased(decisionVoters);
    }
    @Override
    protected void configure(final HttpSecurity http)	throws	Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedRequestHandler())
                .accessDeniedHandler(new UnauthorizedRequest())
                .and().authorizeRequests().expressionHandler(webSecurityExpressionHandler())

                 // User endpoints
                .antMatchers(HttpMethod.GET,"/api/users/{email}").access(ACCESS_CONTROL_USER)
                .antMatchers(HttpMethod.DELETE,"/api/users/{email}").access(ACCESS_CONTROL_USER)
                .antMatchers(HttpMethod.PATCH,"/api/users/{email}").access(ACCESS_CONTROL_USER)
                .antMatchers(HttpMethod.POST,"/api/users/{email}/reset-password-token").access(ACCESS_CONTROL_USER)
                .antMatchers(HttpMethod.PUT,"/api/users/{email}/profilePic").access(ACCESS_CONTROL_USER)

                // Location endpoints
                .antMatchers(HttpMethod.PATCH,"/api/locations/{id}").access(ACCESS_CONTROL_LOCATIONS)
                .antMatchers(HttpMethod.DELETE,"/api/locations/{id}").access(ACCESS_CONTROL_LOCATIONS)

                // Lendings endpoints
                .antMatchers(HttpMethod.PATCH,"/api/lendings/{id}").access(ACCESS_CONTROL_LENDINGS)

                // AssetInstance endpoints
                .antMatchers(HttpMethod.PATCH,"/api/assetInstances/{id}").access(ACCESS_CONTROL_ASSET_INSTANCE_OWNER)
                .antMatchers(HttpMethod.DELETE,"/api/assetInstances/{id}").access(ACCESS_CONTROL_ASSET_INSTANCE_OWNER)


                .and().cors().and().csrf().disable()
                .addFilterBefore(
                        basicTokenFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
            );

    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandler();
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
