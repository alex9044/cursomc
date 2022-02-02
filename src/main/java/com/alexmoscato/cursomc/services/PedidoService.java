package com.alexmoscato.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexmoscato.cursomc.domain.Pedido;
import com.alexmoscato.cursomc.repositories.PedidoRepository;
import com.alexmoscato.cursomc.services.exceptions.*;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	//Method para buscar uma categoria por Id.
	public Pedido findId(Integer id){
		Optional<Pedido> objeto = repository.findById(id); 
		
		return objeto.orElseThrow(() -> 
			new ObjectNotFoundException("Objeto não encontrado! ID:"+id+", tipo: " +Pedido.class.getName()));
	} 
	
}
