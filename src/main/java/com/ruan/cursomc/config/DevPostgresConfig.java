package com.ruan.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ruan.cursomc.services.DbService;
import com.ruan.cursomc.services.EmailService;
import com.ruan.cursomc.services.MockEmailService;
import com.ruan.cursomc.services.SmtpEmailService;

@Configuration
@Profile("devpostgres")
public class DevPostgresConfig {
	@Autowired
	private DbService dbService;
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	@Bean
	public boolean instantiateDateBase() throws ParseException {
		if(!"create".equals(strategy))
			return false;
		
		dbService.instatiateTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
