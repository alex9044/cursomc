package com.alexmoscato.cursomc;

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
import com.alexmoscato.cursomc.domain.Produto;
import com.alexmoscato.cursomc.domain.enums.TipoCliente;
import com.alexmoscato.cursomc.repositories.CategoriaRepository;
import com.alexmoscato.cursomc.repositories.CidadeRepository;
import com.alexmoscato.cursomc.repositories.ClienteRepository;
import com.alexmoscato.cursomc.repositories.EnderecoRepository;
import com.alexmoscato.cursomc.repositories.EstadoRepository;
import com.alexmoscato.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

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
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Instanciando objetos Categorias
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 =  new Categoria(null, "Escritorio");
		
		//Instanciando objetos Produtos.
		Produto prod1 = new Produto(null, "Computador", 2.000);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		//Adicionando produtos nas categorias
		cat1.getProdutos().addAll(Arrays.asList(prod1,prod2,prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		//Adiconando categorias nos produtos 
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		//Instanciando objetos Estados
		Estado est1 = new Estado(null, "Minas Gerais");
 		Estado est2 = new Estado(null, "São Paulo");
		
 		//Instanciando objetos Cidades
 		Cidade cid1 = new Cidade(null, "Uberlandia", est1);
 		Cidade cid2 = new Cidade(null, "São Paulo", est2);
 		Cidade cid3 = new Cidade(null, "Capinas", est2);
 		
 		//Instanciando objetos Clientes
 		Cliente cli1 = new Cliente(null, "Maria Silva", "maria3gmail.com", "521486325798", TipoCliente.PESSOA_FISICA);
 		
 		//Adicionando telefones aos clientes
 		cli1.getTelefones().addAll(Arrays.asList("521463285","451632548"));
 		
 		//Instanciando objetos Endereços
 		Endereco e1 = new Endereco(null, "Rua flores ", "300", "apto 300", "Jardim", "38220834", cli1, cid1);
 		Endereco e2 = new Endereco(null, "Avenida mattos", "102", "Sala 800", "Centro", "61151311", cli1, cid2);
 		
 		//Adicionando enderecos aos clientes 
 		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
 		
 		
		//Salvando Categorias no repositorio 
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		//Salvando Produtos no repositorio 
		produtoRepository.saveAll(Arrays.asList(prod1,prod2,prod3));
		//Salvando Estados no repositorio
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		//Salvandp Cidades no repositorio
		cidadeRepository.saveAll(Arrays.asList(cid1,cid2,cid3));
		//Salvando Clientes no repositorio
		clienteRepository.saveAll(Arrays.asList(cli1));
		//Salvando Enderecos no repositorio
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
	}
	
	

}
