package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.BorrowerViewVoter;
import ar.edu.itba.paw.webapp.auth.ChangeAssetStatusVoter;
import ar.edu.itba.paw.webapp.auth.DeleteAssetVoter;
import ar.edu.itba.paw.webapp.auth.LenderViewOwnerVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private ChangeAssetStatusVoter changeAssetStatusVoter;
    @Autowired
    private Environment environment;

    @Autowired
    LenderViewOwnerVoter lenderViewOwnerVoter;
    @Autowired
    BorrowerViewVoter borrowerViewVoter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
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
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                deleteAssetVoter,
                changeAssetStatusVoter,
                lenderViewOwnerVoter,
                borrowerViewVoter

        );
        return new UnanimousBased(decisionVoters);
    }
    @Override
    protected void configure(final HttpSecurity http)	throws	Exception {
        http.sessionManagement().invalidSessionUrl("/")
                .and().authorizeRequests().expressionHandler(webSecurityExpressionHandler()).accessDecisionManager(accessDecisionManager())
                .antMatchers("/login","/register","/forgotPassword","/changePassword").anonymous()
                .antMatchers("/borrowAssetView","borrowAsset","/borrowedBookDetails").hasRole("BORROWER")
                .antMatchers("/addAsset","/lentBookDetails/**").hasRole("LENDER")
                .antMatchers(HttpMethod.POST,"/deleteAsset/**","/confirmAsset","/returnAsset","/rejectAsset").hasRole("LENDER")
                .antMatchers("/userHome","/changeStatus","/changeRole","/requestAsset/**","/addAssetView/**").authenticated()
                .antMatchers("/**").permitAll()
                .and().formLogin().loginPage("/login")
                .usernameParameter("email").passwordParameter("password")
                .defaultSuccessUrl("/discovery",false)
                .and().rememberMe().rememberMeParameter("rememberme")
                .userDetailsService(userDetailsService)
                .key(environment.getProperty("persistence.salt"))
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)).
                and().logout().logoutUrl("/logout").
                logoutSuccessUrl("/login").
                and().exceptionHandling().accessDeniedPage("/errors/403")
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)	throws	Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
        @Override
    public	void configure(final WebSecurity web)	throws	Exception {
        web.ignoring().antMatchers("/static/css/**", "/static/js/**", "/static/images/**", "/favicon.ico", "/errors/403");
    }
}
