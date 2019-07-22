package com.ruan.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruan.cursomc.domains.Cliente;
import com.ruan.cursomc.repositories.ClienteRepository;
import com.ruan.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repository;
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		//orElseThrow recebe função que instancia uma exception customizada utilizando uma expressão lambda
		return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " 
									+ id + ", Tipo: " + Cliente.class.getName())); 
	}

}
