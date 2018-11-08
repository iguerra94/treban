package ar.edu.iua.treban;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class TrebanApplication implements CommandLineRunner {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(TrebanApplication.class, args);
	}

	@Autowired
	private DataSource dataSource;

	@Override
	public void run(String... args) throws Exception {
		log.warn("DataSource={}", dataSource);
	}
}