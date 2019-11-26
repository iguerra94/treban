package ar.edu.iua.treban;

import ar.edu.iua.treban.auth.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
		//auth.authenticationProvider(customAuthenticationProvider);
	}

	@Value("${recursos.estaticos}")
	private String recursosEstaticos;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] resources = recursosEstaticos.split(",");

		http.csrf().disable();

		http.authorizeRequests().antMatchers(resources).permitAll().anyRequest().permitAll();

//		http.formLogin()
//				.loginPage(Constantes.URL_SIGNIN)
//				.defaultSuccessUrl(Constantes.URL_HOME)
//				.failureUrl("/signin?error="+true)
//				.permitAll();

//		http.logout().logoutSuccessUrl(Constantes.URL_LOGOUTOK).deleteCookies("JSESSIONID", "rmiw3")
//				.clearAuthentication(true);
//		http.rememberMe().tokenRepository(rmRepository()).rememberMeParameter("rmparam").alwaysRemember(true)
//				.rememberMeCookieName("rmiw3").tokenValiditySeconds(60 * 60 * 24);
	}

//	@Autowired
//	private DataSource ds;

//	public PersistentTokenRepository rmRepository() {
//		JdbcTokenRepositoryImpl r = new JdbcTokenRepositoryImpl();
//		r.setDataSource(ds);
//		return r;
//	}
}

/*
 * CREATE TABLE `persistent_logins` ( `username` varchar(100) NOT NULL, `series`
 * varchar(64) NOT NULL, `token` varchar(64) NOT NULL, `last_used` timestamp NOT
 * NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, PRIMARY KEY
 * (`series`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 * 
 * 
 */
