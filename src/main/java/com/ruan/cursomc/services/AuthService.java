package com.ruan.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ruan.cursomc.domains.Cliente;
import com.ruan.cursomc.repositories.ClienteRepository;
import com.ruan.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) 
			throw new ObjectNotFoundException("Email não encontrado");
		
		String newPass = newPassWord();
		cliente.setSenha(encoder.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
		
	}

	private String newPassWord() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		
		if(opt == 0) // gera um digito
			return (char) (rand.nextInt(10) + 48);
		
		if(opt == 1) // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		
		return (char) (rand.nextInt(26) + 97); // gera letra minuscula
	}
	
	
}