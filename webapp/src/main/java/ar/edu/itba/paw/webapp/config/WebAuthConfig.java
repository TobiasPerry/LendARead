package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(final HttpSecurity http)	throws	Exception {
        http.sessionManagement().invalidSessionUrl("/login")
                .and().authorizeRequests().
                antMatchers("/login").anonymous().
                antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").authenticated().
                and().formLogin().loginPage("/login")
                .usernameParameter("email").passwordParameter("password")
                .defaultSuccessUrl("/", false) // Me va a volver a donde estaba antes
                .and().rememberMe().rememberMeParameter("rememberme")
                .userDetailsService(userDetailsService).key("mysupersecretketthatnobodyknowsabout") // Esto esta mal deperiamos usar una key generada en un proppierties
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)).
                and().logout().logoutUrl("/logout").
                logoutSuccessUrl("/login").
                and().exceptionHandling().accessDeniedPage("/403")
                .and().csrf().disable();
    }
    @Override	protected	void	configure(AuthenticationManagerBuilder auth)	throws	Exception {
        auth.userDetailsService(userDetailsService);
    }
        @Override
    public	void configure(final WebSecurity web)	throws	Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
    }
}
