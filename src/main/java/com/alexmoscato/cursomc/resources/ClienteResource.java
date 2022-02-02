package com.alexmoscato.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alexmoscato.cursomc.domain.Cliente;
import com.alexmoscato.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes") // Uri de dominio da coleção 
public class ClienteResource {
	
	@Autowired 
	private ClienteService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente objeto = service.findId(id);
		return ResponseEntity.ok().body(objeto);
	}
	
}
