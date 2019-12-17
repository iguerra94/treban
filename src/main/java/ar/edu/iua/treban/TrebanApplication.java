package ar.edu.iua.treban;

import ar.edu.iua.treban.model.User;
import ar.edu.iua.treban.model.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import javax.sql.DataSource;

@SpringBootApplication(exclude= { SecurityAutoConfiguration.class })
public class TrebanApplication implements CommandLineRunner {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(TrebanApplication.class, args);
	}

	@Autowired
	private DataSource dataSource;

//	@Autowired
//	private PasswordEncoder pe;

	@Override
	public void run(String... args) throws Exception {
		log.debug("DataSource actual = {}", dataSource);

//		log.debug("La password 'ivang94' codificada es: {}", pe.encode("ivang94"));
	}
}