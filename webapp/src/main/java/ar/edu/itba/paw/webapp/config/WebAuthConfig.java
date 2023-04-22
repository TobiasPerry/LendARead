package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:/application.properties")
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private Environment environment;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(final HttpSecurity http)	throws	Exception {
        http.sessionManagement().invalidSessionUrl("/login")
                .and().authorizeRequests().
                antMatchers("/login","/register").anonymous()
                .antMatchers("/**").authenticated().
                and().formLogin().loginPage("/login")
                .usernameParameter("email").passwordParameter("password")
                .defaultSuccessUrl("/",   false) // Me va a volver a donde estaba antes
                .and().rememberMe().rememberMeParameter("rememberme")
                .userDetailsService(userDetailsService)
                .key(environment.getProperty("persistence.key"))
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)).
                and().logout().logoutUrl("/logout").
                logoutSuccessUrl("/login").
                and().exceptionHandling().accessDeniedPage("/errors/403")
                .and().csrf().disable();
    }
    @Override
    protected	void	configure(AuthenticationManagerBuilder auth)	throws	Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
        @Override
    public	void configure(final WebSecurity web)	throws	Exception {
        web.ignoring().antMatchers("/static/css/**", "/static/js/**", "/static/images/**", "/favicon.ico", "/errors/403");
    }
}
