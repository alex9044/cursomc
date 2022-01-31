package com.alexmoscato.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexmoscato.cursomc.domain.Categoria;
import com.alexmoscato.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	//Method para buscar uma categoria por Id.
	public Categoria findId(Integer id){
		
		Optional<Categoria> objeto = repository.findById(id); 
		return objeto.orElse(null);
	} 
	
}
