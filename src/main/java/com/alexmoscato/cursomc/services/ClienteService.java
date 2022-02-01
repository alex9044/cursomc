package com.alexmoscato.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexmoscato.cursomc.domain.Cliente;
import com.alexmoscato.cursomc.repositories.ClienteRepository;
import com.alexmoscato.cursomc.services.exceptions.*;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	//Method para buscar uma categoria por Id.
	public Cliente findId(Integer id){
		Optional<Cliente> objeto = repository.findById(id); 
		
		return objeto.orElseThrow(() -> 
			new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName()));
	} 
	
}
