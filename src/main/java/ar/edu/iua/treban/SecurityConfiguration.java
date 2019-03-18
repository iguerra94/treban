package ar.edu.iua.treban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("user").password(pwdEncoder().encode("password")).roles("USER").and()
		// .withUser("admin").password(pwdEncoder().encode("password")).roles("ADMIN");

		auth.userDetailsService(userDetailService);

	}

	@Bean
	public PasswordEncoder pwdEncoder() {
		// return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}

	@Value("${recursos.estaticos}")
	private String recursosEstaticos;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.httpBasic();

		String[] resources = recursosEstaticos.split(",");

//		http.authorizeRequests().antMatchers(resources).permitAll().anyRequest().authenticated();
//		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/dologin").permitAll().anyRequest()
//				.authenticated();
//
//		http.formLogin().loginPage(Constantes.URL_DENY).defaultSuccessUrl(Constantes.URL_LOGINOK)
//				.loginProcessingUrl("/dologin").permitAll().failureForwardUrl(Constantes.URL_DENY);
//
//		http.logout().logoutSuccessUrl(Constantes.URL_LOGOUTOK).deleteCookies("JSESSIONID", "rmiw3")
//				.clearAuthentication(true);
//		http.rememberMe().tokenRepository(rmRepository()).rememberMeParameter("rmparam").alwaysRemember(true)
//				.rememberMeCookieName("rmiw3").tokenValiditySeconds(60 * 60 * 24);
//
//		http.csrf().disable();

	}

	@Autowired
	private DataSource ds;

	public PersistentTokenRepository rmRepository() {
		JdbcTokenRepositoryImpl r = new JdbcTokenRepositoryImpl();
		r.setDataSource(ds);
		return r;
	}
}

/*
 * CREATE TABLE `persistent_logins` ( `username` varchar(100) NOT NULL, `series`
 * varchar(64) NOT NULL, `token` varchar(64) NOT NULL, `last_used` timestamp NOT
 * NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, PRIMARY KEY
 * (`series`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 * 
 * 
 */
