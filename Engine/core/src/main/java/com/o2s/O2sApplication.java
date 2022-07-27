package com.o2s;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication (exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class O2sApplication {

	public static void main(String[] args) {
		SpringApplication.run(O2sApplication.class, args);
	}

}
