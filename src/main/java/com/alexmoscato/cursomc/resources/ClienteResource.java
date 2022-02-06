package com.alexmoscato.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alexmoscato.cursomc.domain.Cliente;
import com.alexmoscato.cursomc.dtos.ClienteDTO;
import com.alexmoscato.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes") // Uri de dominio da coleção
public class ClienteResource {

	@Autowired
	private ClienteService service; // Instanciação da classe de serviços.

	//Metodo para buscar um cliente por Id
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente objeto = service.findId(id);
		return ResponseEntity.ok().body(objeto);
	}

	// Criando uri para consultar todas as cliente
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		// Convertendo lista para lista
		List<ClienteDTO> listDto = list.stream().map(objeto -> new ClienteDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	// Criando uri para consultar uma page de cliente
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "ordeyBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		// Convertendo lista para lista
		Page<ClienteDTO> listDto = list.map(objeto -> new ClienteDTO(objeto));
		return ResponseEntity.ok().body(listDto);
	}

	// Crindo uri para inserir um novo cliente
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO objetoDTO) {
		Cliente objeto = service.fromDTO(objetoDTO);
		//objeto = service.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	// Crindo uri para atualizar um cliente
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objetoDTO, @PathVariable Integer id) {
		Cliente objeto = service.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = service.update(objeto);
		return ResponseEntity.noContent().build();
	}

	// Criando uri para eliminar uma cliente
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
