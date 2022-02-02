package com.alexmoscato.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alexmoscato.cursomc.domain.Pedido;
import com.alexmoscato.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos") // Uri de dominio da coleção 
public class PedidoResource {
	
	@Autowired 
	private PedidoService service;
	
	//Método para buscar um pedido por Id.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido objeto = service.findId(id);
		return ResponseEntity.ok().body(objeto);
	}
	
}
