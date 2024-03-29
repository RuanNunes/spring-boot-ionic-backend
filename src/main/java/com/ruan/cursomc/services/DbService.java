package com.ruan.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ruan.cursomc.domain.enums.EstadoPagamento;
import com.ruan.cursomc.domain.enums.Perfil;
import com.ruan.cursomc.domain.enums.TipoCliente;
import com.ruan.cursomc.domains.Categoria;
import com.ruan.cursomc.domains.Cidade;
import com.ruan.cursomc.domains.Cliente;
import com.ruan.cursomc.domains.Endereco;
import com.ruan.cursomc.domains.Estado;
import com.ruan.cursomc.domains.ItemPedido;
import com.ruan.cursomc.domains.Pagamento;
import com.ruan.cursomc.domains.PagamentoComBoleto;
import com.ruan.cursomc.domains.PagamentoComCartao;
import com.ruan.cursomc.domains.Pedido;
import com.ruan.cursomc.domains.Produto;
import com.ruan.cursomc.repositories.CategoriaRepository;
import com.ruan.cursomc.repositories.CidadeRepository;
import com.ruan.cursomc.repositories.ClienteRepository;
import com.ruan.cursomc.repositories.EnderecoRepository;
import com.ruan.cursomc.repositories.EstadoRepository;
import com.ruan.cursomc.repositories.ItemPedidoRepository;
import com.ruan.cursomc.repositories.PagamentoRepository;
import com.ruan.cursomc.repositories.PedidoRepository;
import com.ruan.cursomc.repositories.ProdutoRepository;

@Service
public class DbService {
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void instatiateTestDataBase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
//		List<Categoria> listCat = new ArrayList<>();
//		for (int i = 0; i < 100; i++) {
//			listCat.add(new Categoria(null, "Categoria" + i));
//		}
//		categoriaRepository.saveAll(listCat);
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);

		//Asociando os produtos as categorias
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4));
		//Associando os produtos as categorias
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		//associando estados as cidades
		Cidade c1 = new Cidade(null, "Uberlandia");
		c1.setEstado(est1);
		Cidade c2 = new Cidade(null, "São Paulo");
		c2.setEstado(est2);
		Cidade c3 = new Cidade(null, "Campinas");
		c3.setEstado(est2);
		
		//associando cidades aos estados
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "ruanschwanz@gmail.com", "19186529968", TipoCliente.PESSOAFISICA, encoder.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("88425264","23441234"));
		
		Cliente cli2 = new Cliente(null, "Ana Costa", "ruansasd'chwanz@gmail.com", "19186529968", TipoCliente.PESSOAFISICA, encoder.encode("123"));
		cli2.getTelefones().addAll(Arrays.asList("88425264","23441234"));
		cli2.addPerfil(Perfil.ADMIN);
		
		Endereco e1= new Endereco(null, "Rua Flores", "300", "Ao lado do bar", "jardins", "29260000", cli1, c1);
		Endereco e2= new Endereco(null, "Avenida Matos", "3120", "Em frente a escola", "Centro", "22260000", cli1, c2);
		Endereco e3= new Endereco(null, "Avenida Floriano", "3120", "Em frente a escola", "Centro", "22260000", cli2, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2019 10:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2019 20:30"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2019 00:00"),null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().add(ip3);
		
		p1.getItens().add(ip1);
		p2.getItens().add(ip3);
		p3.getItens().add(ip2);
		
		
		//salvando os objetos
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		//primeiro salva os estados pois uma cidade pertence a um estado
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		//salvando os cliente primeiro pois os endereços precisão dos cliente para fazer ligação
		clienteRepository.saveAll(Arrays.asList(cli1,cli2));
		enderecoRepository.saveAll(Arrays.asList(e1,e2,e3));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));

	}
}
