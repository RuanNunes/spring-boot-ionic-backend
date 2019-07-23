package com.ruan.cursomc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ruan.cursomc.domain.enums.EstadoPagamento;
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

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");
		List<Categoria> listCat = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			listCat.add(new Categoria(null, "Categoria" + i));
		}
		categoriaRepository.saveAll(listCat);
		
		
		Produto p1 = new Produto(null, "computador", 2000);
		Produto p2 = new Produto(null, "impressora", 800);
		Produto p3 = new Produto(null, "Mouse", 80);
		//Asociando os produtos as categorias
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		//Associando os produtos as categorias
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
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
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "19186529968", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("88425264","23441234"));
		
		Endereco e1= new Endereco(null, "Rua Flores", "300", "Ao lado do bar", "jardins", "29260000", cli1, c1);
		Endereco e2= new Endereco(null, "Avenida Matos", "3120", "Em frente a escola", "Centro", "22260000", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
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
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		//primeiro salva os estados pois uma cidade pertence a um estado
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		//salvando os cliente primeiro pois os endereços precisão dos cliente para fazer ligação
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}	

}
