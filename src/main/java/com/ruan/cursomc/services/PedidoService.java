package com.ruan.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ruan.cursomc.domain.enums.EstadoPagamento;
import com.ruan.cursomc.domains.Categoria;
import com.ruan.cursomc.domains.Cliente;
import com.ruan.cursomc.domains.ItemPedido;
import com.ruan.cursomc.domains.PagamentoComBoleto;
import com.ruan.cursomc.domains.Pedido;
import com.ruan.cursomc.repositories.ClienteRepository;
import com.ruan.cursomc.repositories.ItemPedidoRepository;
import com.ruan.cursomc.repositories.PagamentoRepository;
import com.ruan.cursomc.repositories.PedidoRepository;
import com.ruan.cursomc.security.UserSS;
import com.ruan.cursomc.services.exceptions.AuthorizationException;
import com.ruan.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository repository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	
	//Ao utilizar @Autowired o spring procura se existe algum @Bean para fazer a instanciação da ingeção de dependencia 
	@Autowired
	private EmailService emailService;
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		//orElseThrow recebe função que instancia uma exception customizada utilizando uma expressão lambda
		return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " 
									+ id + ", Tipo: " + Cliente.class.getName())); 
	}
	
	
	public Pedido insert (Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		UserSS user = UserService.authenticated();
		if(user == null)
			throw new AuthorizationException("Acesso negado");
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findByCliente(clienteService.find(user.getId()), pageRequest);
	}
}
