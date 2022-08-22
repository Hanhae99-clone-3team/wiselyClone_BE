package com.hanghae.wisely;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WiselyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WiselyApplication.class, args);
	}

}
