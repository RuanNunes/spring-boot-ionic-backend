package com.ruan.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ruan.cursomc.services.DbService;
import com.ruan.cursomc.services.EmailService;
import com.ruan.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	@Autowired
	private DbService dbService;
	@Bean
	public boolean instantiateDateBase() throws ParseException {
		dbService.instatiateTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
