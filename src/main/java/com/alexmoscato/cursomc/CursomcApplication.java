package com.alexmoscato.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alexmoscato.cursomc.domain.Categoria;
import com.alexmoscato.cursomc.domain.Cidade;
import com.alexmoscato.cursomc.domain.Estado;
import com.alexmoscato.cursomc.domain.Produto;
import com.alexmoscato.cursomc.repositories.CategoriaRepository;
import com.alexmoscato.cursomc.repositories.CidadeRepository;
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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 =  new Categoria(null, "Escritorio");
		
		Produto prod1 = new Produto(null, "Computador", 2.000);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1,prod2,prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null, "Minas Gerais");
 		Estado est2 = new Estado(null, "São Paulo");
		
 		Cidade cid1 = new Cidade(null, "Uberlandia", est1);
 		Cidade cid2 = new Cidade(null, "São Paulo", est2);
 		Cidade cid3 = new Cidade(null, "Capinas", est2);
 		
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(prod1,prod2,prod3));
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(cid1,cid2,cid3));
		
	}
	
	

}
