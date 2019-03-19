package ar.edu.iua.treban;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import ar.edu.iua.treban.web.services.Constantes;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true, 
		securedEnabled = true, 
		jsr250Enabled = true) 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Qualifier("persistenceUserDetailService")
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Value("${recursos.estaticos}")
	private String recursosNoProtegidos;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] resources = recursosNoProtegidos.split(",");
		http.authorizeRequests().antMatchers(resources).permitAll().anyRequest().authenticated();


        http.formLogin().loginPage(Constantes.URL_DENY).defaultSuccessUrl(Constantes.URL_LOGINOK)
				.loginProcessingUrl("/dologin").permitAll().failureUrl(Constantes.URL_DENY);
		http.logout().logoutSuccessUrl(Constantes.URL_LOGOUTOK)
			.deleteCookies("rmdemo", "SESSION").clearAuthentication(true);
		
		//http.rememberMe().tokenRepository(persistentTokenRepository()).alwaysRemember(true)
		//		.rememberMeCookieName("rmdemo").tokenValiditySeconds(60 * 60 * 24);
		http.csrf().disable();
	}

	
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		final JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}

}







