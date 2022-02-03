package com.alexmoscato.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alexmoscato.cursomc.domain.Categoria;
import com.alexmoscato.cursomc.domain.Cidade;
import com.alexmoscato.cursomc.domain.Cliente;
import com.alexmoscato.cursomc.domain.Endereco;
import com.alexmoscato.cursomc.domain.Estado;
import com.alexmoscato.cursomc.domain.ItemPedido;
import com.alexmoscato.cursomc.domain.Pagamento;
import com.alexmoscato.cursomc.domain.PagamentoComBoleto;
import com.alexmoscato.cursomc.domain.PagamentoComCartao;
import com.alexmoscato.cursomc.domain.Pedido;
import com.alexmoscato.cursomc.domain.Produto;
import com.alexmoscato.cursomc.domain.enums.EstadoPagamento;
import com.alexmoscato.cursomc.domain.enums.TipoCliente;
import com.alexmoscato.cursomc.repositories.CategoriaRepository;
import com.alexmoscato.cursomc.repositories.CidadeRepository;
import com.alexmoscato.cursomc.repositories.ClienteRepository;
import com.alexmoscato.cursomc.repositories.EnderecoRepository;
import com.alexmoscato.cursomc.repositories.EstadoRepository;
import com.alexmoscato.cursomc.repositories.ItemPedidoRepository;
import com.alexmoscato.cursomc.repositories.PagamentoRepository;
import com.alexmoscato.cursomc.repositories.PedidoRepository;
import com.alexmoscato.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
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

		// Instanciando objetos Categorias
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Mesa, cama e  banhao");
		Categoria cat4 = new Categoria(null, "Chaves");
		Categoria cat5 = new Categoria(null, "Decoração");
		Categoria cat6 = new Categoria(null, "Computadores");
		Categoria cat7 = new Categoria(null, "Sala de estar");
		Categoria cat8 = new Categoria(null, "Dormitorios");
		

		// Instanciando objetos Produtos.
		Produto prod1 = new Produto(null, "Computador", 2.000);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);

		// Adicionando produtos nas categorias
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));

		// Adiconando categorias nos produtos
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));

		// Instanciando objetos Estados
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		// Instanciando objetos Cidades
		Cidade cid1 = new Cidade(null, "Uberlandia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Capinas", est2);

		// Instanciando objetos Clientes
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria3gmail.com", "521486325798", TipoCliente.PESSOA_FISICA);

		// Adicionando telefones aos clientes
		cli1.getTelefones().addAll(Arrays.asList("521463285", "451632548"));

		// Instanciando objetos Endereços
		Endereco e1 = new Endereco(null, "Rua flores ", "300", "apto 300", "Jardim", "38220834", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida mattos", "102", "Sala 800", "Centro", "61151311", cli1, cid2);

		// Adicionando enderecos aos clientes
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		// Salvando Categorias no repositorio
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8));
		// Salvando Produtos no repositorio
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		// Salvando Estados no repositorio
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		// Salvandp Cidades no repositorio
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		// Salvando Clientes no repositorio
		clienteRepository.saveAll(Arrays.asList(cli1));
		// Salvando Enderecos no repositorio
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		// Mascara de Data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		// Instanciando objetos pedidos
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		// Adicionando pedidos aos clientes
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		// Instanciando Pagamento e adiconando aos pedidos
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		// Salvando Pedidos no repositorio
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		// Salvando Pagamentos no repositorio
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		// Instanciando objetos ItemPedido
		ItemPedido ip1 = new ItemPedido(ped1, prod1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, prod3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, prod2, 100.00, 1, 800.00);

		// Adicionando items aos pedidos
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		// Adicionando items aos produtos
		prod1.getItens().addAll(Arrays.asList(ip1));
		prod2.getItens().addAll(Arrays.asList(ip3));
		prod3.getItens().addAll(Arrays.asList(ip2));

		// Salvando pedidos no repositorio
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
