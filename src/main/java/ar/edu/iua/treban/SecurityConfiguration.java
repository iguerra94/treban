package ar.edu.iua.treban;

import ar.edu.iua.treban.auth.CustomAuthenticationProvider;
import ar.edu.iua.treban.utils.RoleUtils;
import ar.edu.iua.treban.web.services.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Value("${recursos.estaticos}")
    private String recursosEstaticos;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] resources = recursosEstaticos.split(",");

        http.csrf().disable();

        http.authorizeRequests().antMatchers(resources).permitAll()
                .antMatchers(HttpMethod.GET, Constantes.URL_LISTS).hasAnyAuthority(RoleUtils.RoleName.DEVELOPER.getValue(), RoleUtils.RoleName.PROJECT_LEADER.getValue())
                .antMatchers(HttpMethod.POST, Constantes.URL_LISTS).hasAuthority(RoleUtils.RoleName.PROJECT_LEADER.getValue())
                .antMatchers(HttpMethod.GET, Constantes.URL_TASKS + "/*").hasAnyAuthority(RoleUtils.RoleName.DEVELOPER.getValue(), RoleUtils.RoleName.PROJECT_LEADER.getValue())
                .antMatchers(HttpMethod.POST, Constantes.URL_TASKS).hasAuthority(RoleUtils.RoleName.PROJECT_LEADER.getValue())
                .antMatchers(HttpMethod.PUT, Constantes.URL_TASKS + "/*").hasAnyAuthority(RoleUtils.RoleName.DEVELOPER.getValue(), RoleUtils.RoleName.PROJECT_LEADER.getValue())
                .antMatchers(HttpMethod.DELETE, Constantes.URL_TASKS + "/*").hasAuthority(RoleUtils.RoleName.PROJECT_LEADER.getValue())
                .antMatchers(HttpMethod.POST, Constantes.URL_USERS).permitAll()
                .anyRequest().authenticated();

        http.authorizeRequests().antMatchers(Constantes.URL_SIGNIN).permitAll().antMatchers(Constantes.URL_PROCESSING_SIGNIN).permitAll().anyRequest()
                .authenticated();

        http.formLogin()
                .loginPage(Constantes.URL_DENY)
                .defaultSuccessUrl(Constantes.URL_LOGINOK)
                .failureUrl(Constantes.URL_SIGNIN_FAILURE)
                .loginProcessingUrl(Constantes.URL_PROCESSING_SIGNIN).permitAll();

        http.logout().logoutSuccessUrl(Constantes.URL_LOGOUT).deleteCookies("JSESSIONID", "rmtreban")
                .clearAuthentication(true);

        http.rememberMe().tokenRepository(rmRepository()).rememberMeParameter("rmparam").alwaysRemember(true)
                .rememberMeCookieName("rmtreban").tokenValiditySeconds(60 * 60 * 24);
    }

    @Autowired
    private DataSource ds;

    public PersistentTokenRepository rmRepository() {
        JdbcTokenRepositoryImpl r = new JdbcTokenRepositoryImpl();
        r.setDataSource(ds);
        return r;
    }
}





