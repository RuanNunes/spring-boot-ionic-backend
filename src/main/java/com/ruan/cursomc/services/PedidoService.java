package com.ruan.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruan.cursomc.domains.Cliente;
import com.ruan.cursomc.domains.Pedido;
import com.ruan.cursomc.repositories.PedidoRepository;
import com.ruan.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository repository;
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		//orElseThrow recebe função que instancia uma exception customizada utilizando uma expressão lambda
		return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " 
									+ id + ", Tipo: " + Cliente.class.getName())); 
	}

}
