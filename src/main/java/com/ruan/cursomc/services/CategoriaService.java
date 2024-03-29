package com.ruan.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ruan.cursomc.domains.Categoria;
import com.ruan.cursomc.dto.CategoriaDTO;
import com.ruan.cursomc.repositories.CategoriaRepository;
import com.ruan.cursomc.services.exceptions.DataIntegrityException;
import com.ruan.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repository;
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		//orElseThrow recebe função que instancia uma exception customizada utilizando uma expressão lambda
		return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " 
									+ id + ", Tipo: " + Categoria.class.getName())); 
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	public Categoria update (Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateDate(newObj, obj);
		return repository.save(newObj);
	}
	
	public void deleteById(Integer id) {
		find(id);
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
	private void updateDate(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
	
}
