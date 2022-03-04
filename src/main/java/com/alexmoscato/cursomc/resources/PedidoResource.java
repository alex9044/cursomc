package com.alexmoscato.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alexmoscato.cursomc.domain.Pedido;
import com.alexmoscato.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos") // Uri de dominio da coleção
public class PedidoResource {

	@Autowired
	private PedidoService service; // Instanciação da classe de serviços.

	// Crindo uri para buscar um pedido por Id.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido objeto = service.findId(id);
		return ResponseEntity.ok().body(objeto);
	}

	// Crindo uri para inserir um novo pedido
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido objeto) {
		objeto = service.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
