package com.ruan.cursomc.dto;

import java.io.Serializable;

import com.ruan.cursomc.domains.Produto;

public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private Double preco;
	
	public ProdutoDTO() {
		
	}
	
	public ProdutoDTO(Produto obj) {
		this.id =  obj.getId();
		this.nome = obj.getNome();
		this.preco = obj.getPreco();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return preco;
	}

	public void setValor(Double valor) {
		this.preco = valor;
	}

}
